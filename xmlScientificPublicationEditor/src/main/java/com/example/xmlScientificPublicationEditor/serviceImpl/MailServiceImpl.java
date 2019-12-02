package com.example.xmlScientificPublicationEditor.serviceImpl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.xmlScientificPublicationEditor.service.MailService;

@Service
public class MailServiceImpl implements MailService {


	@Autowired
	private JavaMailSender mailSender;
	
	@Async
	@Override
	public void sendEmail(String email) throws MessagingException {
		MimeMessage mimeMessage= mailSender.createMimeMessage();
		MimeMessageHelper mmHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
		String message = "<html><head><meta charset=\"UTF-8\"></head>"
				+ "<body><h3>XML</h3><br>";
        mmHelper.setText(message, true);
		mmHelper.setTo(email);
		mmHelper.setSubject("XML");
		mailSender.send(mimeMessage);
	}
}
