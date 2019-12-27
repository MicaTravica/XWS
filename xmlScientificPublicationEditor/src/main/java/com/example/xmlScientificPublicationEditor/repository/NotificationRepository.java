package com.example.xmlScientificPublicationEditor.repository;

import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import com.example.xmlScientificPublicationEditor.util.MyFileReader;
import com.example.xmlScientificPublicationEditor.util.DOMParser.DOMParser;

@Repository
public class NotificationRepository {

	public static String NotificationSchemaPath = "src/main/resources/data/schemas/notification.xsd";
	public static String NotificationTamplate = "src/main/resources/data/xml/notificationTemplate.xml";
	public static String NotificationXSLPath = "src/main/resources/data/xslt/notification.xsl";

	public Document getNotification() throws Exception {
		String notification = MyFileReader.readFile(NotificationTamplate);
		Document document = DOMParser.buildDocument(notification, NotificationSchemaPath);
		return document;
	}

}