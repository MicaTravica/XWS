package com.example.xmlScientificPublicationEditor.serviceImpl;

import com.example.xmlScientificPublicationEditor.repository.NotificationRepository;
import com.example.xmlScientificPublicationEditor.service.MailService;
import com.example.xmlScientificPublicationEditor.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public String makeNotification(String notification) throws Exception{
        String savedNotification =  notificationRepository.save(notification);
        this.sendEmailNotification(savedNotification);
        return savedNotification;
    }

    @Override
    public String findOne(String notificationId) throws Exception{
        return notificationRepository.findOne(notificationId);
    }

    @Override
    public String update(String notification) throws Exception{
        return notificationRepository.update(notification);
    }

    @Override
    public void delete(String notificationId) throws Exception{
        notificationRepository.delete(notificationId);
    }

    @Override
    public void sendEmailNotification(String notification) throws Exception {
        System.out.println("send emaill");
        mailService.sendEmail("xmltim1@gmail.com");
    }
}