package com.example.xmlScientificPublicationEditor.service;

import com.example.xmlScientificPublicationEditor.model.ProcessState;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * ProcessPSPService
 */
public interface ProcessPSPService {

	String create(String scientificPublicationId) throws Exception;

    String findOneByScientificPublicationID(String scientificPublicationId) throws Exception;

    void delete(String processId) throws Exception;
    
    public String generateProcessXMLTemplate() throws Exception;
    
    String setCoverLetter(String processId, String coverLetterId) throws Exception;

    String setRewiever(String personId) throws Exception;

    String setReview(String questionnaireId) throws Exception;

    Document setLastVersionNumber(Document process, String version) throws Exception;

    String getLastVersionNumber(String processId) throws Exception;

    Node getLastVersion(String processId) throws Exception;
    
    Document setProcessPSPState(Document process, ProcessState processState) throws Exception;

    String getProcessPSPState(String processId) throws Exception;

	String findForPublishing() throws Exception;

	String findMyPublications(String name) throws Exception;





}