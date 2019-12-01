package com.example.xmlScientificPublicationEditor.repository.coverLetter;

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
import com.example.xmlScientificPublicationEditor.util.RetriveFromDB;
import com.example.xmlScientificPublicationEditor.util.StoreToDB;
import com.example.xmlScientificPublicationEditor.util.UpdateDB;

@Repository
public class CoverLetterRepository {

	public static String coverLetterCollectionId = "/db/sample/coverLetter";
	
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

	public String save(String cl) throws Exception {
		Document document = DOMParser.buildDocument(cl);
		String id = document.getDocumentElement().getAttribute("id");
		StoreToDB.store(coverLetterCollectionId, id, cl.toString());
		return id;
	}

	public String update(String coverLetter) throws Exception {
		Document document = DOMParser.buildDocument(coverLetter);
		String id = document.getDocumentElement().getAttribute("id");
		
        String oldPersonData = this.findOne(id);
        if(oldPersonData == null)
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
