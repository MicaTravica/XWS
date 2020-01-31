package com.example.xmlScientificPublicationEditor.repository;

import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.xs.XSModel;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import com.example.xmlScientificPublicationEditor.util.DOMParser.DOMParser;

import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;

@Repository
public class NotificationRepository {

	public static String NotificationSchemaPath = "src/main/resources/data/schemas/notification.xsd";
	public static String NotificationXSLPath = "src/main/resources/data/xslt/notification.xsl";

	public Document getNotification() throws Exception {
		StringWriter sw = new StringWriter();
		XSModel xsModel = new XSParser().parse(NotificationSchemaPath);
		XSInstance xsInstance = new XSInstance();
		xsInstance.maximumElementsGenerated = 0;
		xsInstance.maximumListItemsGenerated = 0;
		xsInstance.maximumRecursionDepth = 0;
		xsInstance.generateOptionalAttributes = Boolean.FALSE;
		xsInstance.generateDefaultAttributes = Boolean.TRUE;
		xsInstance.generateOptionalElements = Boolean.TRUE; // null means rando
		QName rootElement = new QName("http://www.uns.ac.rs/Tim1", "notification");
		XMLDocument sampleXml = new XMLDocument(new StreamResult(sw), true, 4, null);
		xsInstance.generate(xsModel, rootElement, sampleXml);
		Document document = DOMParser.buildDocument(sw.toString(), NotificationSchemaPath);
		return document;
	}

}