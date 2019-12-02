package com.example.xmlScientificPublicationEditor.service;

import javax.mail.MessagingException;

public interface MailService {

	void sendEmail(String email) throws MessagingException;

}
