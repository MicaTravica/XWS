package com.example.xmlScientificPublicationEditor.service;

/**
 * QuestionnaireService
 */
public interface QuestionnaireService {

    String findOne(String id) throws Exception;

	String save(String questionnaire) throws Exception;

	String update(String questionnaire) throws Exception;

	void delete(String id) throws Exception;
}