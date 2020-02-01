package com.example.xmlScientificPublicationEditor.service;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;

/**
 * QuestionnaireService
 */
public interface QuestionnaireService {

    String findOne(String id) throws Exception;

	String save(String questionnaire, String processId, String reviewerEmail) throws Exception;

	String update(String questionnaire) throws Exception;

	void delete(String id) throws Exception;

	public StringWriter extractMetadata(String questionnaire) throws Exception;

	String findOneHTML(String id) throws Exception;

	ByteArrayOutputStream findOnePDF(String id) throws Exception;

	String generateQuestionnaireXMLTemplate() throws Exception;
}