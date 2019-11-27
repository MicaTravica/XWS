package com.example.xmlScientificPublicationEditor.api;

import java.util.Scanner;

import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;

import com.example.xmlScientificPublicationEditor.util.AuthenticationUtilities;
import com.example.xmlScientificPublicationEditor.util.AuthenticationUtilities.ConnectionProperties;

public class XPathExample5 {
    
	private static final String TARGET_NAMESPACE = "http://www.ftn.uns.ac.rs/examples/xmldb/bookstore";
	
	// public static void main(String[] args) throws Exception {
	// 	XPathExample5.run(AuthenticationUtilities.loadProperties(), args);
	// }

	/**
	 * conn XML DB connection properties
     * args[0] Should be the collection ID to access
     */
    public static void run(ConnectionProperties conn, String args[]) throws Exception {
        
    	System.out.println("[INFO] " + XPathExample5.class.getSimpleName());
    	
    	// initialize collection and document identifiers
        String collectionId = null;
		String xpathExp = null; 
        
        if (args.length == 1) {
        	
        	System.out.println("[INFO] Passing the arguments... ");
        	collectionId = args[0];
        	
        } else {
        	
        	System.out.println("[INFO] Using defaults.");
        	collectionId = "/db/sample/library";
        	
        }

        System.out.println("\t- collection ID: " + collectionId);
        
    	// initialize database driver
    	System.out.println("[INFO] Loading driver class: " + conn.driver);
        Class<?> cl = Class.forName(conn.driver);
        
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        
        DatabaseManager.registerDatabase(database);
        
        Collection col = null;
        
        try { 

        	// get the collection
        	System.out.println("[INFO] Retrieving the collection: " + collectionId);
            col = DatabaseManager.getCollection(conn.uri + collectionId);
        	
            // get an instance of xpath query service
            XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xpathService.setProperty("indent", "yes");
            
            // make the service aware of namespaces, using the default one
            xpathService.setNamespace("", TARGET_NAMESPACE);
         
            Scanner scanner = new Scanner(System.in);
   			System.out.println("\n[INPUT] Enter an XPath expression (e.g. doc(\"1.xml\")//book[@category=\"WEB\" and price>35]/title): ");
   			
    		xpathExp = scanner.nextLine();
    		scanner.close();
            
            // execute xpath expression 
            System.out.println("[INFO] Invoking XPath query service for: " + xpathExp);
            ResourceSet result = xpathService.query(xpathExp);
            
            // handle the results
            System.out.println("[INFO] Handling the results... ");
            
            ResourceIterator i = result.getIterator();
            Resource res = null;
            
            while(i.hasMoreResources()) {
            
            	try {
                    res = i.nextResource();
                    System.out.println(res.getContent());
                    
                } finally {
                    
                	// don't forget to cleanup resources
                    try { 
                    	((EXistResource)res).freeResources(); 
                    } catch (XMLDBException xe) {
                    	xe.printStackTrace();
                    }
                }
            }
            
        } finally {
        	
            // don't forget to cleanup
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
