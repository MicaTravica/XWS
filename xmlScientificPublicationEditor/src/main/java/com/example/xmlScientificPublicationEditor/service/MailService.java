package com.example.xmlScientificPublicationEditor.service;

import javax.mail.MessagingException;

public interface MailService {

	void sendEmail(String[] emails, String message) throws MessagingException;

}
