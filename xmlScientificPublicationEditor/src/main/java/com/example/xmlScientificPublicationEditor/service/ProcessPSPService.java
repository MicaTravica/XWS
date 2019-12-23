package com.example.xmlScientificPublicationEditor.service;

/**
 * ProcessPSPService
 */
public interface ProcessPSPService {

	String create(String scientificPublicationId) throws Exception;

    String findOne(String processId) throws Exception;
    
    String addCoverLetter(String processId, String coverLetterId) throws Exception;

    String addRewiever(String personId) throws Exception;

    String addReview(String questionnaireId) throws Exception;

	void delete(String processId) throws Exception;
}