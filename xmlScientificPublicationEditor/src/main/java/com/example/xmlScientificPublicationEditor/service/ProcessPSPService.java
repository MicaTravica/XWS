package com.example.xmlScientificPublicationEditor.service;

import com.example.xmlScientificPublicationEditor.model.ProcessState;
import com.example.xmlScientificPublicationEditor.model.person.TPersons;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * ProcessPSPService
 */
public interface ProcessPSPService {

    String create(String scientificPublicationId,
        String authorEmail, String scientificPublicationName) throws Exception;

    Document findOneById(String processId) throws Exception;

    String findOneByScientificPublicationID(String scientificPublicationId) throws Exception;

    void delete(String processId) throws Exception;
    
    public String generateProcessXMLTemplate() throws Exception;
    
    String setCoverLetter(String processId, String coverLetterId) throws Exception;

    String setRewiever(String personId) throws Exception;

    String setReview(String questionnaireId) throws Exception;

    Document setLastVersionNumber(Document process, String version) throws Exception;

    String getLastVersionNumber(String processId) throws Exception;
    
    Document setProcessPSPState(Document process, ProcessState processState) throws Exception;

    Document setProcessPSPCSAuthor(Document process, String email);

    String getProcessPSPCSAuthor(Document process);

	String findForPublishing() throws Exception;

    String findMyReviewAssigments(String email) throws Exception;
    
    String findMyPublications(String name) throws Exception;
    
	void addReviewers(TPersons reviewers, String processId) throws Exception;

	String getProcessPSPState(Document document) throws Exception;

	String findOne(String processId) throws Exception;

	void setProcessPSPStateFromScored(String xml) throws Exception;

	String findMySPProcess(String id, String name) throws Exception;

	Node getLastVersion(Document document) throws Exception;

	String newVersionSP(String scID, String scName, String name, String processId) throws Exception;

	String getAuthor(Document process);

    boolean acceptRejectReviewAssigment(String acceptanceData, String name) throws  Exception;
    String generateReviewAssigmentXMLTemplate() throws  Exception;

    boolean saveQuestionnaireToProcessPSP(String processId, String reviewerEmail, String qId) throws  Exception;
}