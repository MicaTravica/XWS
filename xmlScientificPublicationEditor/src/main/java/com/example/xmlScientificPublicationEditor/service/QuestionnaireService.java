package com.example.xmlScientificPublicationEditor.service;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;

/**
 * QuestionnaireService
 */
public interface QuestionnaireService {

    String findOne(String id, String name) throws Exception;

	String save(String questionnaire, String processId, String reviewerEmail) throws Exception;

	String update(String questionnaire) throws Exception;

	void delete(String id) throws Exception;

	public StringWriter extractMetadata(String questionnaire) throws Exception;

	String findOneHTML(String id, String name) throws Exception;

	ByteArrayOutputStream findOnePDF(String id, String name) throws Exception;

	String generateQuestionnaireXMLTemplate() throws Exception;

	String findMerged(String id, String version, String name) throws Exception;

	String findMergedHTML(String id, String version, String name) throws Exception;

	ByteArrayOutputStream findMergedPDF(String id, String version, String name) throws Exception;

}