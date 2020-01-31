package com.example.xmlScientificPublicationEditor.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

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
	public void letterOfThanks(String[] emails, String sp) throws Exception {
		Document document = notificationRepository.getNotification();
		String content = "Thanks for participating in the publication of scientific publication!";
		document = setData(document, content, sp);
		sendEmailNotification(emails, document);
	}

	@Override
	public void publicationAccepted(String[] emails, String sp) throws Exception {
		Document document = notificationRepository.getNotification();
		String content = "Your scientific publication has been accepted!";
		document = setData(document, content, sp);
		sendEmailNotification(emails, document);
	}

	@Override
	public void publicationRejected(String[] emails, String sp) throws Exception {
		Document document = notificationRepository.getNotification();
		String content = "Your scientific publication has been rejected!";
		document = setData(document, content, sp);
		sendEmailNotification(emails, document);
	}
	
	@Override
	public void publicationRevised(String[] emails, String sp) throws Exception {
		Document document = notificationRepository.getNotification();
		String content = "Your scientific publication has been revised!";
		document = setData(document, content, sp);
		sendEmailNotification(emails, document);
	}

	@Override
	public void questionnaireReviewers(String[] emails, String sp) throws Exception {
		Document document = notificationRepository.getNotification();
		String content = "You have been selected as a reviewer for a scientific publication!";
		document = setData(document, content, sp);
		sendEmailNotification(emails, document);
	}

	@Override
	public Document setData(Document document, String content, String sp) {
		Node con = document.getElementsByTagName(IdGeneratorServiceImpl.CONTENT).item(0);
		Element cont = (Element) con;
		
		Element text = document.createElement(IdGeneratorServiceImpl.TEXT);
		Element cur = document.createElement("ns:cursive");
		cur.appendChild(document.createTextNode(content));
		
		text.appendChild(cur);
		cont.appendChild(text);
		document.getElementsByTagName("ns:sp").item(0).setTextContent(sp);
		Date date = new Date();
		document.getElementsByTagName("ns:date").item(0)
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