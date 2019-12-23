package com.example.xmlScientificPublicationEditor.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.repository.QuestionnaireRepository;
import com.example.xmlScientificPublicationEditor.service.QuestionnaireService;
import com.example.xmlScientificPublicationEditor.util.RDF.MetadataExtractor;
import com.example.xmlScientificPublicationEditor.util.XSLFOTransformer.XSLFOTransformer;

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
	public String save(String questionnaire) throws Exception {
		String qId = questionnaireRepository.save(questionnaire);
		questionnaireRepository.saveMetadata(this.extractMetadata(questionnaire), qId);
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
}