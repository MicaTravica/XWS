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
import com.example.xmlScientificPublicationEditor.util.existAPI.RetriveFromDB;
import com.example.xmlScientificPublicationEditor.util.existAPI.StoreToDB;
import com.example.xmlScientificPublicationEditor.util.existAPI.UpdateDB;


@Repository
public class QuestionnaireRepository {

    public static String QuestionnaireCollectionId = "/db/sample/questionnaire";
    public static String QuestionnaireSchemaPath = "src/main/resources/data/schemas/questionnaire.xsd";

	public String findOne(String id) throws Exception {
		String retVal =  null;
		String xpathExp = "//questionnaire[@id=\"" + id + "\"]";
		ResourceSet resultSet = RetriveFromDB.executeXPathExpression(QuestionnaireCollectionId, xpathExp, TARGET_NAMESPACE);
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

    // TODO: kako ce front znati koji id je slobodan za Questionnaire????
	public String save(String q) throws Exception {
		Document document = DOMParser.buildDocument(q,QuestionnaireSchemaPath);
		String id = document.getDocumentElement().getAttribute("id");
		StoreToDB.store(QuestionnaireCollectionId, id, q);
		return id;
	}

	public String update(String questionnaire) throws Exception {
		Document document = DOMParser.buildDocument(questionnaire, QuestionnaireSchemaPath);
		String id = document.getDocumentElement().getAttribute("id");
		
        String oldQuestionnaireData = this.findOne(id);
        if(oldQuestionnaireData == null)
        {
            throw new ResourceNotFoundException("Questionnaire with id: " + id);
        }
        this.delete(id);
        StoreToDB.store(QuestionnaireCollectionId, id, questionnaire);
        return id;
	}

	public void delete(String id) throws Exception {
		String xpathExp = "/questionnaire";
        long mods = UpdateDB.delete(QuestionnaireCollectionId, id, xpathExp);   
        if(mods == 0)
        {
            throw new ResourceNotDeleted(String.format("Questionnaire with documentId %s", id));
        }
	}

}