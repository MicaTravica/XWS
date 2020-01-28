package com.example.xmlScientificPublicationEditor.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.example.xmlScientificPublicationEditor.repository.NotificationRepository;
import com.example.xmlScientificPublicationEditor.service.MailService;
import com.example.xmlScientificPublicationEditor.service.NotificationService;
import com.example.xmlScientificPublicationEditor.util.DOMParser.DOMParser;
import com.example.xmlScientificPublicationEditor.util.XSLFOTransformer.XSLFOTransformer;

/**
 * notificationServiceImpl
 */
@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private XSLFOTransformer xslFoTransformer;

	@Autowired
	private MailService mailService;

	@Override
	public void letterOfThanks(String[] emails, String spUrl) throws Exception {
		Document document = notificationRepository.getNotification();
		String content = "Thanks for participating in the publication of scientific publication!";
		document = setData(document, content, spUrl);
		sendEmailNotification(emails, document);
	}

	@Override
	public void publicationAccepted(String[] emails, String spUrl) throws Exception {
		Document document = notificationRepository.getNotification();
		String content = "Your scientific publication has been accepted!";
		document = setData(document, content, spUrl);
		sendEmailNotification(emails, document);
	}

	@Override
	public void publicationRejected(String[] emails, String spUrl) throws Exception {
		Document document = notificationRepository.getNotification();
		String content = "Your scientific publication has been rejected!";
		document = setData(document, content, spUrl);
		sendEmailNotification(emails, document);
	}

	@Override
	public void addedCoverLetter(String[] emails, String spUrl) throws Exception {
		Document document = notificationRepository.getNotification();
		String content = "A cover letter for the scientific publicaion has been added!";
		document = setData(document, content, spUrl);
		sendEmailNotification(emails, document);
	}

	@Override
	public void addedQuestionnaire(String[] emails, String spUrl) throws Exception {
		Document document = notificationRepository.getNotification();
		String content = "A questionnaire for the scientific publicaion has been added!";
		document = setData(document, content, spUrl);
		sendEmailNotification(emails, document);
	}

	@Override
	public void questionnaireReviewers(String[] emails, String spUrl) throws Exception {
		Document document = notificationRepository.getNotification();
		String content = "You have been selected as a reviewer for a scientific publication!";
		document = setData(document, content, spUrl);
		sendEmailNotification(emails, document);
	}

	@Override
	public Document setData(Document document, String content, String spUrl) {
		document.getElementsByTagName("cursive").item(0).setTextContent(content);
		document.getElementsByTagName("spUrl").item(0).setTextContent(spUrl);
		Date date = new Date();
		document.getElementsByTagName("date").item(0)
				.setTextContent((1900 + date.getYear()) + "-" + (date.getMonth() + 1) + "-" + date.getDate());
		return document;
	}

	@Override
	public void sendEmailNotification(String[] emails, Document document) throws Exception {
		String notification = DOMParser.parseDocument(
				document, NotificationRepository.NotificationSchemaPath);
		String notifHTML = xslFoTransformer.generateHTML(notification, NotificationRepository.NotificationXSLPath);
		mailService.sendEmail(emails, notifHTML);
	}
}