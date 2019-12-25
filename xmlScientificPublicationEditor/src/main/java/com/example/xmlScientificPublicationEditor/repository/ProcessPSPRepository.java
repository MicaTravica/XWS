package com.example.xmlScientificPublicationEditor.repository;

import static com.example.xmlScientificPublicationEditor.util.template.XUpdateTemplate.TARGET_NAMESPACE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotDeleted;
import com.example.xmlScientificPublicationEditor.util.DOMParser.DOMParser;
import com.example.xmlScientificPublicationEditor.util.existAPI.RetriveFromDB;
import com.example.xmlScientificPublicationEditor.util.existAPI.StoreToDB;
import com.example.xmlScientificPublicationEditor.util.existAPI.UpdateDB;

@Repository
public class ProcessPSPRepository {

    public static String ProcessPSPTemplatePath = "src/main/resources/data/xml/processPSPTemplate.xml";
    public static String ScientificPublicationFiled = "scientificPublication";
	public static String ProcessPSPCollectionId = "/db/sample/processPSP";
	public static String NotificationSchemaPath = "src/main/resources/data/schemas/processPSP.xsd";

	public String findOne(String id) throws Exception {
		String retVal = null;
		String xpathExp = "//processPSP[@id=\"" + id + "\"]";
		ResourceSet resultSet = RetriveFromDB.executeXPathExpression(ProcessPSPCollectionId, xpathExp,
				TARGET_NAMESPACE);
		if (resultSet == null) {
			return retVal;
		}
		ResourceIterator i = resultSet.getIterator();
		XMLResource res = null;
		while (i.hasMoreResources()) {
			try {
				res = (XMLResource) i.nextResource();
				retVal = res.getContent().toString();
				return retVal;
			} finally {
				// don't forget to cleanup resources
				try {
					((EXistResource) res).freeResources();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
		return null;
	}

    public Document initProcess() throws Exception
    {
        BufferedReader br = null;
        try{
            StringBuilder sb = new StringBuilder();
            File file = new File(ProcessPSPTemplatePath);
            br = new BufferedReader(new FileReader(file)); 
            String st; 
            while ((st = br.readLine()) != null){
                sb.append(st); 
            }    
            Document newProcess = DOMParser.buildDocumentWithOutSchema(sb.toString());
            //TODO: add processs ID
            return newProcess;
        }
        finally
        {
            if(br != null){
                br.close();
            }
        }
    }

    public Document setScientificPublicationId(Document process, String scientificPublciationID) throws Exception
    {
		process.getElementsByTagName(ScientificPublicationFiled).
				item(0).setTextContent(scientificPublciationID);
        return process;
    }

	// TODO: kako ce front znati koji id je slobodan za Notification????
	public Document save(Document document) throws Exception {
		String id = "1";
		//TODO: add generated ID
		String documentS = DOMParser.parseDocument(document);
		StoreToDB.store(ProcessPSPCollectionId, id, documentS);
		return document;
	}

	public void delete(String id) throws Exception {
		String xpathExp = "/processPSP";
		long mods = UpdateDB.delete(ProcessPSPCollectionId, id, xpathExp);
		if (mods == 0) {
			throw new ResourceNotDeleted(String.format("processPSP with documentId %s", id));
		}
	}


}