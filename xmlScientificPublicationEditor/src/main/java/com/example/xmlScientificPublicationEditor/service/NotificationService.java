package com.example.xmlScientificPublicationEditor.service;

import org.w3c.dom.Document;

/**
 * notificationService
 */
public interface NotificationService {

	String makeNotification(String notification) throws Exception;

	String findOne(String notifcationId) throws Exception;

	String update(String notification) throws Exception;

	void delete(String notification) throws Exception;

	void sendEmailNotification(Document notification) throws Exception;

}