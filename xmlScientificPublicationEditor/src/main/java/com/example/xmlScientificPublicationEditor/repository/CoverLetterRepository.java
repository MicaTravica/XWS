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
public class CoverLetterRepository {

    public static String coverLetterCollectionId = "/db/sample/coverLetter";
    public static String coverLetterSchemaPath = "src/main/resources/data/schemas/coverLetter.xsd";

	
	public String findOne(String id) throws Exception {
		String retVal =  null;
		String xpathExp = "//coverLetter[@id=\"" + id + "\"]";
		ResourceSet resultSet = RetriveFromDB.executeXPathExpression(coverLetterCollectionId, xpathExp, TARGET_NAMESPACE);
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

    // TODO: kako ce front znati koji id je slobodan za coverLetter????
	public String save(String cl) throws Exception {
		Document document = DOMParser.buildDocument(cl,coverLetterSchemaPath);
		String id = document.getDocumentElement().getAttribute("id");
		StoreToDB.store(coverLetterCollectionId, id, cl);
		return id;
	}

	public String update(String coverLetter) throws Exception {
		Document document = DOMParser.buildDocument(coverLetter, coverLetterSchemaPath);
		String id = document.getDocumentElement().getAttribute("id");
		
        String oldCoverLetterData = this.findOne(id);
        if(oldCoverLetterData == null)
        {
            throw new ResourceNotFoundException("Cover letter with id: " + id);
        }
        this.delete(id);
        StoreToDB.store(coverLetterCollectionId, id, coverLetter);
        return id;
	}

	public void delete(String id) throws Exception {
		String xpathExp = "/coverLetter";
        long mods = UpdateDB.delete(coverLetterCollectionId, id, xpathExp);   
        if(mods == 0)
        {
            throw new ResourceNotDeleted(String.format("Cover letter with documentId %s", id));
        }
	}

}