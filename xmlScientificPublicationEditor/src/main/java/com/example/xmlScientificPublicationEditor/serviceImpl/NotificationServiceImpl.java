package com.example.xmlScientificPublicationEditor.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.example.xmlScientificPublicationEditor.repository.NotificationRepository;
import com.example.xmlScientificPublicationEditor.service.MailService;
import com.example.xmlScientificPublicationEditor.service.NotificationService;
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
	public String makeNotification(String notification) throws Exception {
		Document savedNotification = notificationRepository.save(notification);
		this.sendEmailNotification(savedNotification, notification);
		return savedNotification.getDocumentElement().getAttribute("id");
	}

	@Override
	public String findOne(String notificationId) throws Exception {
		return notificationRepository.findOne(notificationId);
	}

	@Override
	public String update(String notification) throws Exception {
		return notificationRepository.update(notification);
	}

	@Override
	public void delete(String notificationId) throws Exception {
		notificationRepository.delete(notificationId);
	}

	@Override
	public void sendEmailNotification(Document document, String notification) throws Exception {
		String notifHTML = xslFoTransformer.generateHTML(notification, NotificationRepository.NotificationXSLPath);
		String email = document.getElementsByTagName("email").item(0).getTextContent().trim();
		mailService.sendEmail(email, notifHTML);
		System.out.println(notifHTML);
	}
}