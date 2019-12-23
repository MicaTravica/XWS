package com.example.xmlScientificPublicationEditor.service;

import java.io.StringWriter;

/**
 * QuestionnaireService
 */
public interface QuestionnaireService {

    String findOne(String id) throws Exception;

	String save(String questionnaire) throws Exception;

	String update(String questionnaire) throws Exception;

	void delete(String id) throws Exception;

	public StringWriter extractMetadata(String questionnaire) throws Exception;
}