package com.example.xmlScientificPublicationEditor.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.Principal;

import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamResult;

import com.example.xmlScientificPublicationEditor.repository.CoverLetterRepository;
import com.example.xmlScientificPublicationEditor.util.RDF.MetadataExtractor;
import org.apache.xerces.xs.XSModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.repository.ProcessPSPRepository;
import com.example.xmlScientificPublicationEditor.repository.ScientificPublicationRepository;
import com.example.xmlScientificPublicationEditor.service.ProcessPSPService;
import com.example.xmlScientificPublicationEditor.service.ScientificPublicationService;
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

	@Autowired
	private MetadataExtractor metadataExtractor;

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
	public String findOneHTML(String id) throws Exception {
		String sp = scientificPublicationRepository.findOne(id);
		if (sp == null) {
			throw new ResourceNotFoundException(String.format("Scientific publication with id %s", id));
		}
		String spHTML = xslFoTransformer.generateHTML(sp, ScientificPublicationRepository.ScientificPublicationXSLPath);
		return spHTML;
	}

	@Override
	public ByteArrayOutputStream findOnePDF(String id) throws Exception {
		String sp = scientificPublicationRepository.findOne(id);
		if (sp == null) {
			throw new ResourceNotFoundException(String.format("Scientific publication with id %s", id));
		}
		ByteArrayOutputStream spPDF = xslFoTransformer.generatePDF(sp,
				ScientificPublicationRepository.ScientificPublicationXSL_FO_PATH);
		return spPDF;
	}

	@Override
	public String save(String scientificPublication, String authorEmail) throws Exception {
		Document sc = scientificPublicationRepository.save(scientificPublication);
		String scName = this.getScientificPublicationName(sc);
		String scID = this.getScientificPublicationID(sc);
		scientificPublicationRepository.saveMetadata(this.extractMetadata(scientificPublication) ,scID);
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
	public StringWriter extractMetadata(String sp) throws Exception{
		StringWriter out = new StringWriter();
		sp = xslFoTransformer.applyTemplate(sp, ScientificPublicationRepository .XMLToRDFa);
		System.out.println(sp);
		StringReader in = new StringReader(sp);
		metadataExtractor.extractMetadata(in, out); // apply graddl transformatio
		return out;
	}

}
