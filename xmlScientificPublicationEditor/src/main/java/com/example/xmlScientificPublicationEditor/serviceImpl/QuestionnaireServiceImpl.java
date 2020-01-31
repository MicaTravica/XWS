package com.example.xmlScientificPublicationEditor.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamResult;

import com.example.xmlScientificPublicationEditor.service.NotificationService;
import com.example.xmlScientificPublicationEditor.service.ProcessPSPService;
import org.apache.xerces.xs.XSModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.repository.QuestionnaireRepository;
import com.example.xmlScientificPublicationEditor.service.QuestionnaireService;
import com.example.xmlScientificPublicationEditor.util.RDF.MetadataExtractor;
import com.example.xmlScientificPublicationEditor.util.XSLFOTransformer.XSLFOTransformer;

import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;

/**
 * QuestionnaireServiceImpl
 */
@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

	@Autowired
	private MetadataExtractor metadataExtractor;

	@Autowired
	private XSLFOTransformer xslFoTransformer;

	@Autowired
	private QuestionnaireRepository questionnaireRepository;

	@Autowired
	private ProcessPSPService processPSPService;

	@Autowired
	private NotificationService notificationService;

	@Override
	public String findOne(String id) throws Exception {
		String questionnaire = questionnaireRepository.findOne(id);
		if (questionnaire == null) {
			throw new ResourceNotFoundException(String.format("Questionnaire with id %s", id));
		}
		return questionnaire;
	}

	@Override
	public String findOneHTML(String id) throws Exception {
		String cl = questionnaireRepository.findOne(id);
		if (cl == null) {
			throw new ResourceNotFoundException(String.format("Questionnaire with id %s", id));
		}
		String clHTML = xslFoTransformer.generateHTML(cl, QuestionnaireRepository.QuestionnairerXSLPath);
		return clHTML;
	}

	@Override
	public ByteArrayOutputStream findOnePDF(String id) throws Exception {
		String cl = questionnaireRepository.findOne(id);
		if (cl == null) {
			throw new ResourceNotFoundException(String.format("Questionnaire with id %s", id));
		}
		ByteArrayOutputStream clPDF = xslFoTransformer.generatePDF(cl,
				QuestionnaireRepository.QuestionnaireXSL_FO_PATH);
		return clPDF;
	}

	@Override
	public String save(String questionnaire, String processId, String reviewerEmail) throws Exception {
		String[] emails = new String[1];
		emails[0] =  reviewerEmail;
		String qId = questionnaireRepository.save(questionnaire);
		String scName = processPSPService.saveQuestionnaireToProcessPSP(processId, reviewerEmail, qId);
//		questionnaireRepository.saveMetadata(this.extractMetadata(questionnaire), qId);
		if(scName != null) {
			this.notificationService.letterOfThanks(emails, scName);
		}
		return qId;
	}

	@Override
	public String update(String questionnaire) throws Exception {
		String qId = questionnaireRepository.update(questionnaire);
		questionnaireRepository.updateMetadata(this.extractMetadata(questionnaire), qId);
		return qId;
	}

	@Override
	public void delete(String id) throws Exception {
		questionnaireRepository.delete(id);
	}

	@Override
	public StringWriter extractMetadata(String questionnaire) throws Exception {
		StringWriter out = new StringWriter();
		StringReader in = new StringReader(questionnaire);
		metadataExtractor.extractMetadata(in, out);
		return out;
	}
	
	@Override
	public String generateQuestionnaireXMLTemplate() throws Exception {
		StringWriter sw = new StringWriter();
		XSModel xsModel = new XSParser().parse(QuestionnaireRepository.QuestionnaireSchemaPath);
		XSInstance xsInstance = new XSInstance();
		xsInstance.maximumElementsGenerated = 0;
		xsInstance.maximumListItemsGenerated = 0;
		xsInstance.maximumRecursionDepth = 0;
		xsInstance.generateOptionalAttributes = Boolean.FALSE;
		xsInstance.generateDefaultAttributes = Boolean.TRUE;
		xsInstance.generateOptionalElements = Boolean.FALSE; // null means rando
		QName rootElement = new QName("http://www.uns.ac.rs/Tim1", "questionnaire");
		XMLDocument sampleXml = new XMLDocument(new StreamResult(sw), true, 4, null);
		xsInstance.generate(xsModel, rootElement, sampleXml);
		return sw.toString();
	}

}