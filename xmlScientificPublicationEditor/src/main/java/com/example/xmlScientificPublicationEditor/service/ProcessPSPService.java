package com.example.xmlScientificPublicationEditor.service;

import com.example.xmlScientificPublicationEditor.model.ProcessState;
import com.example.xmlScientificPublicationEditor.model.person.TPersons;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * ProcessPSPService
 */
public interface ProcessPSPService {

	String create(String scientificPublicationId, String authorEmail) throws Exception;

    Document findOneById(String processId) throws Exception;

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

    Document setProcessPSPCSAuthor(Document process, String email);

    String getProcessPSPCSAuthor(Document process);

	String findForPublishing() throws Exception;

	String findMyPublications(String name) throws Exception;

	void addReviewers(TPersons reviewers, String processId) throws Exception;

	String getProcessPSPState(Document document) throws Exception;

}