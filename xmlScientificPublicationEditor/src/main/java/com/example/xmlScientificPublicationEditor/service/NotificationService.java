package com.example.xmlScientificPublicationEditor.service;

/**
 * notificationService
 */
public interface NotificationService {

    public String makeNotification(String notification) throws Exception;

    public String findOne(String notifcationId) throws Exception;

    public String update(String notification) throws Exception;

    public void delete(String notification) throws Exception;

    public void sendEmailNotification(String notification) throws Exception;

}