package com.example.xmlScientificPublicationEditor.service;

/**
 * ProcessPSPService
 */
public interface ProcessPSPService {

	String create(String scientificPublicationId) throws Exception;

    String findOneByScientificPublicationID(String scientificPublicationId) throws Exception;
    
    String setCoverLetter(String processId, String coverLetterId) throws Exception;

    String setRewiever(String personId) throws Exception;

    String setReview(String questionnaireId) throws Exception;

    void delete(String processId) throws Exception;
    
    public String generateProcessXMLTemplate() throws Exception;
}