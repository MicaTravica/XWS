package com.example.xmlScientificPublicationEditor.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.security.Principal;

import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.xs.XSModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.repository.ProcessPSPRepository;
import com.example.xmlScientificPublicationEditor.repository.ScientificPublicationRepository;
import com.example.xmlScientificPublicationEditor.service.ProcessPSPService;
import com.example.xmlScientificPublicationEditor.service.ScientificPublicationService;
import com.example.xmlScientificPublicationEditor.util.DOMParser.DOMParser;
import com.example.xmlScientificPublicationEditor.util.XSLFOTransformer.XSLFOTransformer;

import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;

@Service
public class ScientificPublicationServiceImpl implements ScientificPublicationService {

	@Autowired
	private ProcessPSPService processPSPService;

	@Autowired
	private XSLFOTransformer xslFoTransformer;

	@Autowired
	private ScientificPublicationRepository scientificPublicationRepository;

	@Override
	public String findOne(String id) throws Exception {
		String sp = scientificPublicationRepository.findOne(id);
		if (sp == null) {
			throw new ResourceNotFoundException(String.format("Scientific publication with id %s", id));
		}
		return sp;
	}


	@Override
	public String findOneNotPub(String id, String email) throws Exception {
		Document document = processPSPService.findOneById(id);
		if (!processPSPService.getAuthor(document).equals(email)) {
			throw new Exception("You can not see this file");
		}
		Node lastVersion = processPSPService.getLastVersion(document);
		Element lv = (Element) lastVersion;
		return findOne(
				lv.getElementsByTagName(ProcessPSPRepository.ScientificPublicationFiled).item(0).getTextContent());
	}
	
	@Override
	public String findOnePub(String id) throws Exception {
		return scientificPublicationRepository.findOnePub(id);
	}
	
	@Override
	public String findOneHTML(String id) throws Exception {
		String sp = findOnePub(id);
		String spHTML = xslFoTransformer.generateHTML(sp, ScientificPublicationRepository.ScientificPublicationXSLPath);
		return spHTML;
	}

	@Override
	public ByteArrayOutputStream findOnePDF(String id) throws Exception {
		String sp = findOnePub(id);
		ByteArrayOutputStream spPDF = xslFoTransformer.generatePDF(sp,
				ScientificPublicationRepository.ScientificPublicationXSL_FO_PATH);
		return spPDF;
	}

	@Override
	public String save(String scientificPublication, String authorEmail) throws Exception {
		Document sc = scientificPublicationRepository.save(scientificPublication);
		String scName = this.getScientificPublicationName(sc);
		String scID = this.getScientificPublicationID(sc);
		processPSPService.create(scID, authorEmail, scName);
		return scID;
	}


	@Override
	public String saveNewVersion(String scientificPublication, String name, String processId) throws Exception {
		Document sc = scientificPublicationRepository.save(scientificPublication);
		String scName = this.getScientificPublicationName(sc);
		String scID = this.getScientificPublicationID(sc);
		String lastVersion = processPSPService.newVersionSP(scID, scName, name, processId);
		scientificPublicationRepository.setLastVersion(scID, lastVersion);
		return scID;
	}



	@Override
	public String update(String scientificPublication) throws Exception {
		return scientificPublicationRepository.update(scientificPublication);
	}

	@Override
	public void delete(String id) throws Exception {
		scientificPublicationRepository.delete(id);
	}
	
	@Override
	public String generateSPXMLTemplate() throws Exception {
		StringWriter sw = new StringWriter();
		XSModel xsModel = new XSParser().parse(ScientificPublicationRepository.scientificPublicationSchemaPath);
		XSInstance xsInstance = new XSInstance();
		xsInstance.maximumElementsGenerated = 0;
		xsInstance.maximumListItemsGenerated = 0;
		xsInstance.maximumRecursionDepth = 0;
		xsInstance.generateOptionalAttributes = Boolean.FALSE;
		xsInstance.generateDefaultAttributes = Boolean.TRUE;
		xsInstance.generateOptionalElements = Boolean.FALSE; // null means rando
		QName rootElement = new QName("http://www.uns.ac.rs/Tim1", "scientificPublication");
		XMLDocument sampleXml = new XMLDocument(new StreamResult(sw), true, 4, null);
		xsInstance.generate(xsModel, rootElement, sampleXml);
		return sw.toString();
	}


	// GET/SET METODE

	@Override
	public String getScientificPublicationID(Document sc) {
		return sc.getElementsByTagName(ScientificPublicationRepository.EL_ROOT).item(0)
            .getAttributes().getNamedItem("id").getTextContent();
	}

	@Override
	public String getScientificPublicationName(Document sc) {
		String retVal = sc.getElementsByTagName("ns:value").item(0).getTextContent();
		return retVal;
	}


	@Override
	public void addAcceptedAt(String idSp) throws Exception {
		scientificPublicationRepository.addAcceptedAt(idSp);
	}


	@Override
	public String search(String param, Principal user) throws Exception {
		String email = "";
		if(user != null) {
			email = user.getName();
		}
		return scientificPublicationRepository.search(param, email);
	}

	@Override
	public void saveComments(String file, String email, String processId) throws Exception {
		Document comments = DOMParser.buildDocumentWithOutSchema(file);
		NodeList nodeComments = comments.getDocumentElement().getElementsByTagName(IdGeneratorServiceImpl.COMMENTS);
		if (nodeComments.getLength() < 1) {
			throw new Exception("You dont have comments to add");
		}
		Document document = processPSPService.findOneById(processId);
		Node lastVersion = processPSPService.getLastVersion(document);
		Element lv = (Element) lastVersion;
		Document doc = getSpFromProcessForReviewer(lv, email);
		Element com = (Element) nodeComments.item(0);
		scientificPublicationRepository.saveComments(doc, com);
	}

	private Document getSpFromProcessForReviewer(Element lastVersion, String email) throws Exception {
		NodeList ras = lastVersion.getElementsByTagName(ProcessPSPRepository.ReviewAssigment);
		boolean isReviewer = false;
		for (int i = 0; i < ras.getLength(); i++) {
			Element ra = (Element) ras.item(i);
			if(ra.getElementsByTagName(ProcessPSPRepository.IdReviewer).item(0).getTextContent().equals(email)
					&& ra.getAttributes().getNamedItem(ProcessPSPRepository.PROCESS_STATE).getTextContent().equals(ProcessPSPRepository.ACCEPTED)) {
				isReviewer = true;
				break;
			}
		}
		if(!isReviewer) {
			throw new Exception("You can not work with this publication!");
		};

		String sp = findOne(
				lastVersion.getElementsByTagName(ProcessPSPRepository.ScientificPublicationFiled).item(0).getTextContent());
		return DOMParser.buildDocument(sp, ScientificPublicationRepository.scientificPublicationSchemaPath);
	}


	@Override
	public String findOneByProcessId(String id, Object user) throws Exception {
		return scientificPublicationRepository.findOneByProcessId(id, user);
	}


	@Override
	public String findOneByProcessIdHTML(String id, Object user) throws Exception {
		String sp = findOneByProcessId(id, user);
		return xslFoTransformer.generateHTML(sp, ScientificPublicationRepository.ScientificPublicationXSLPath);	
	}


	@Override
	public ByteArrayOutputStream findOneByProcessIdPDF(String id, Object user) throws Exception {
		String sp = findOneByProcessId(id, user);
		return xslFoTransformer.generatePDF(sp,
				ScientificPublicationRepository.ScientificPublicationXSL_FO_PATH);
	}


	@Override
	public String findOneByVersion(String id, String name) throws Exception {
		return scientificPublicationRepository.findOneByVersion(id, name);
	}


	@Override
	public String findOneByVersionHTML(String id, String name) throws Exception {
		String sp = findOneByVersion(id, name);
		return xslFoTransformer.generateHTML(sp, ScientificPublicationRepository.ScientificPublicationXSLPath);	
	}


	@Override
	public ByteArrayOutputStream findOneByVersionPDF(String id, String name) throws Exception {
		String sp = findOneByVersion(id, name);
		return xslFoTransformer.generatePDF(sp,
				ScientificPublicationRepository.ScientificPublicationXSL_FO_PATH);
	}

	@Override
	public String getSPReview(String processId, String email) throws Exception {
		Document document = processPSPService.findOneById(processId);
		Node lastVersion = processPSPService.getLastVersion(document);
		Element lv = (Element) lastVersion;
		Document dom = getSpFromProcessForReviewer(lv, email);
		Element authors = (Element) dom.getDocumentElement().getElementsByTagName(IdGeneratorServiceImpl.AUTHORS)
				.item(0);
		dom.getDocumentElement().removeChild(authors);
		return DOMParser.parseDocumentWithoutSchema(dom);
	}
	
	@Override
	public String getSPReviewHTML(String processId, String name) throws Exception {
		String sp = getSPReview(processId, name);
		return xslFoTransformer.generateHTML(sp, ScientificPublicationRepository.ScientificPublicationXSLPath);	
	}


	@Override
	public ByteArrayOutputStream getSPReviewPDF(String processId, String name) throws Exception {
		String sp = getSPReview(processId, name);
		return xslFoTransformer.generatePDF(sp,
				ScientificPublicationRepository.ScientificPublicationXSL_FO_PATH);
	}


}
