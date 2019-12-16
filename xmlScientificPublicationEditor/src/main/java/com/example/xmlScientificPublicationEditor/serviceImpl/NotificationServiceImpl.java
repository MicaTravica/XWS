package com.example.xmlScientificPublicationEditor.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.example.xmlScientificPublicationEditor.repository.NotificationRepository;
import com.example.xmlScientificPublicationEditor.service.MailService;
import com.example.xmlScientificPublicationEditor.service.NotificationService;

/**
 * notificationServiceImpl
 */
@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private MailService mailService;

	@Override
	public String makeNotification(String notification) throws Exception {
		Document savedNotification = notificationRepository.save(notification);
		this.sendEmailNotification(savedNotification);
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
	public void sendEmailNotification(Document notification) throws Exception {
		System.out.println("send emaill");
		NodeList emails = notification.getElementsByTagName("email");
		for (int i = 0; i < emails.getLength(); i++) {
			String email = emails.item(i).getTextContent().trim();
			System.out.println(email);
			mailService.sendEmail(email);
		}
	}
}