package com.example.xmlScientificPublicationEditor.util.existAPI;

import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.xmlScientificPublicationEditor.util.AuthenticationUtilities;
import com.example.xmlScientificPublicationEditor.util.AuthenticationUtilities.ConnectionProperties;

public class StoreToDB {
    
	private static ConnectionProperties conn;
    
    public static void store(String collectionId, String documentId, String xmlEntity) throws Exception {
        conn = AuthenticationUtilities.loadProperties();
        
        if(collectionId.isEmpty() || xmlEntity.isEmpty()){
            System.out.println("missing collectionId or documentId or xmlEntity");
        }
        // initialize database driver
    	System.out.println("[INFO] Loading driver class: " + conn.driver);
    	Class<?> cl = Class.forName(conn.driver);
                
        // encapsulation of the database driver functionality
    	Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        
        // entry point for the API which enables you to get the Collection reference
        DatabaseManager.registerDatabase(database);
        // a collection of Resources stored within an XML database
        Collection col = null;
        XMLResource res = null;
        
        try { 
        	
            System.out.println("[INFO] Retrieving the collection: " + collectionId);
            col = DBUtil.getOrCreateCollection(conn, collectionId); 
            /*
             *  create new XMLResource with a given id
             *  an id is assigned to the new resource if left empty (null)
             */
            System.out.println("[INFO] Inserting the document: " + documentId);
            res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);
            
            res.setContent(xmlEntity);
            System.out.println("[INFO] Storing the document: " + res.getId());
            col.storeResource(res);

        } finally {
            
        	//don't forget to cleanup
            if(res != null) {
                try { 
                	((EXistResource)res).freeResources(); 
                } catch (XMLDBException xe) {
                	xe.printStackTrace();
                }
            }
            
            if(col != null) {
                try { 
                	col.close(); 
                } catch (XMLDBException xe) {
                	xe.printStackTrace();
                }
            }
        }
    }
    
  
}
