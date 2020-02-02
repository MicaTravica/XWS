package com.example.xmlScientificPublicationEditor.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.xs.XSModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.repository.CoverLetterRepository;
import com.example.xmlScientificPublicationEditor.service.CoverLetterService;
import com.example.xmlScientificPublicationEditor.service.ProcessPSPService;
import com.example.xmlScientificPublicationEditor.util.XSLFOTransformer.XSLFOTransformer;

import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;

@Service
public class CoverLetterServiceImpl implements CoverLetterService {

	@Autowired
	private XSLFOTransformer xslFoTransformer;
	
	@Autowired
	private CoverLetterRepository coverLetterRepository;

	@Autowired
	private ProcessPSPService processPSPService;

	@Override
	public String findOne(String id) throws Exception {
		String cl = coverLetterRepository.findOne(id);
		if (cl == null) {
			throw new ResourceNotFoundException(String.format("Cover letter with id %s", id));
		}
		return cl;
	}

	@Override
	public String findOneHTML(String id) throws Exception {
		String cl = coverLetterRepository.findOne(id);
		if (cl == null) {
			throw new ResourceNotFoundException(String.format("Cover letter with id %s", id));
		}
		String clHTML = xslFoTransformer.generateHTML(cl, CoverLetterRepository.CoverLetterXSLPath);
		return clHTML;
	}

	@Override
	public ByteArrayOutputStream findOnePDF(String id) throws Exception {
		String cl = coverLetterRepository.findOne(id);
		if (cl == null) {
			throw new ResourceNotFoundException(String.format("Cover letter with id %s", id));
		}
		ByteArrayOutputStream clPDF = xslFoTransformer.generatePDF(cl, CoverLetterRepository.CoverLetterXSL_FO_PATH);
		return clPDF;
	}

	@Override
	public String save(String cl, String processId) throws Exception {
		String cvId = coverLetterRepository.save(cl, processId);
		processPSPService.setCoverLetter(processId, cvId);
		return cvId;
	}

	@Override
	public String update(String coverLetter) throws Exception {
		String cvId = coverLetterRepository.update(coverLetter);
		return cvId;
	}

	@Override
	public void delete(String id) throws Exception {
		coverLetterRepository.delete(id);
	}

	
	@Override
	public String generateCoverLetterXMLTemplate() throws Exception {
		StringWriter sw = new StringWriter();
		XSModel xsModel = new XSParser().parse(CoverLetterRepository.coverLetterSchemaPath);
		XSInstance xsInstance = new XSInstance();
		xsInstance.maximumElementsGenerated = 0;
		xsInstance.maximumListItemsGenerated = 0;
		xsInstance.maximumRecursionDepth = 0;
		xsInstance.generateOptionalAttributes = Boolean.FALSE;
		xsInstance.generateDefaultAttributes = Boolean.TRUE;
		xsInstance.generateOptionalElements = Boolean.FALSE; // null means rando
		QName rootElement = new QName("http://www.uns.ac.rs/Tim1", "coverLetter");
		XMLDocument sampleXml = new XMLDocument(new StreamResult(sw), true, 4, null);
		xsInstance.generate(xsModel, rootElement, sampleXml);
		return sw.toString();
	}

}
