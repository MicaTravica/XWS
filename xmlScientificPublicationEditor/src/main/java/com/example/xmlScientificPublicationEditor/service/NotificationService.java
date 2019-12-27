package com.example.xmlScientificPublicationEditor.service;

import org.w3c.dom.Document;

/**
 * notificationService
 */
public interface NotificationService {

	void sendEmailNotification(String[] emails, Document document) throws Exception;

	Document setData(Document document, String content, String spUrl);

	void questionnaireReviewers(String[] emails, String spUrl) throws Exception;

	void addedQuestionnaire(String[] emails, String spUrl) throws Exception;

	void addedCoverLetter(String[] emails, String spUrl) throws Exception;

	void publicationRejected(String[] emails, String spUrl) throws Exception;

	void publicationAccepted(String[] emails, String spUrl) throws Exception;

	void letterOfThanks(String[] emails, String spUrl) throws Exception;

}