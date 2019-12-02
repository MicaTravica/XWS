package com.example.xmlScientificPublicationEditor.repository;

import static com.example.xmlScientificPublicationEditor.util.template.XUpdateTemplate.TARGET_NAMESPACE;

import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotDeleted;
import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.util.DOMParser.DOMParser;
import com.example.xmlScientificPublicationEditor.util.RetriveFromDB;
import com.example.xmlScientificPublicationEditor.util.StoreToDB;
import com.example.xmlScientificPublicationEditor.util.UpdateDB;


@Repository
public class NotificationRepository {

    public static String NotificationCollectionId = "/db/sample/notification";
    public static String NotificationSchemaPath = "src/main/resources/data/schemas/notification.xsd";

	public String findOne(String id) throws Exception {
		String retVal =  null;
		String xpathExp = "//notification[@id=\"" + id + "\"]";
		ResourceSet resultSet = RetriveFromDB.executeXPathExpression(NotificationCollectionId, xpathExp, TARGET_NAMESPACE);
		if(resultSet == null)
        {
            return retVal;
        } 
        ResourceIterator i = resultSet.getIterator();
        XMLResource res = null;
        while(i.hasMoreResources()) {
        	try {
                res = (XMLResource)i.nextResource();
                retVal =  res.getContent().toString();
                return retVal;
            } finally {
            	// don't forget to cleanup resources
                try { 
                	((EXistResource)res).freeResources(); 
                } catch (XMLDBException xe) {
                	xe.printStackTrace();
                }
            }
        }
        return null;
	}

    // TODO: kako ce front znati koji id je slobodan za Notification????
	public String save(String notification) throws Exception {
		Document document = DOMParser.buildDocument(notification, NotificationSchemaPath);
		String id = document.getDocumentElement().getAttribute("id");
		StoreToDB.store(NotificationCollectionId, id, notification);
		return id;
	}

	public String update(String notification) throws Exception {
		Document document = DOMParser.buildDocument(notification, NotificationSchemaPath);
		String id = document.getDocumentElement().getAttribute("id");
		
        String oldNotificationData = this.findOne(id);
        if(oldNotificationData == null)
        {
            throw new ResourceNotFoundException("Notification with id: " + id);
        }
        this.delete(id);
        StoreToDB.store(NotificationCollectionId, id, notification);
        return id;
	}

	public void delete(String id) throws Exception {
		String xpathExp = "/notification";
        long mods = UpdateDB.delete(NotificationCollectionId, id, xpathExp);   
        if(mods == 0)
        {
            throw new ResourceNotDeleted(String.format("Notification with documentId %s", id));
        }
	}

	public void markNotificationAsSeen(String notificationId) {
	}

}