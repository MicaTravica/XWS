package com.example.xmlScientificPublicationEditor.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.xs.XSModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
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

	@Override
	public String findOne(String id) throws Exception {
		String sp = scientificPublicationRepository.findOne(id);
		if (sp == null) {
			throw new ResourceNotFoundException(String.format("Scientific publication with id %s", id));
		}
		return sp;
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
	public String save(String scientificPublication) throws Exception {
		String scID = scientificPublicationRepository.save(scientificPublication);
		processPSPService.create(scID);
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
		xsInstance.minimumElementsGenerated = 1;
		xsInstance.maximumElementsGenerated = 1;
		xsInstance.generateOptionalElements = Boolean.FALSE; // null means rando
		QName rootElement = new QName("http://www.uns.ac.rs/Tim1", "scientificPublication");
		XMLDocument sampleXml = new XMLDocument(new StreamResult(sw), true, 4, null);
		xsInstance.generate(xsModel, rootElement, sampleXml);
		return sw.toString();
	}
}
