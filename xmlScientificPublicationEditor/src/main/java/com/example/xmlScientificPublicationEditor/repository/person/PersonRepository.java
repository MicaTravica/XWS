package com.example.xmlScientificPublicationEditor.repository.person;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotDeleted;
import com.example.xmlScientificPublicationEditor.exception.ResourceNotUpdated;
import com.example.xmlScientificPublicationEditor.model.person.TPerson;
import com.example.xmlScientificPublicationEditor.util.RetriveFromDB;
import com.example.xmlScientificPublicationEditor.util.StoreToDB;
import com.example.xmlScientificPublicationEditor.util.UpdateDB;

import org.springframework.stereotype.Service;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.exist.xmldb.EXistResource;

import static com.example.xmlScientificPublicationEditor.util.template.XUpdateTemplate.TARGET_NAMESPACE;
import static com.example.xmlScientificPublicationEditor.util.template.XUpdateTemplate.UPDATE;

@Service
public class PersonRepository {

    public static String personCollectionId = "/db/sample/person";
    
    public TPerson save(TPerson person) throws Exception{
        String xmlPerson = PersonMarshalling.marshalPerson(person);
        if(xmlPerson != null)
        {
            
            String documentId = "";
            StoreToDB.store(personCollectionId, documentId, xmlPerson);
        }
        return person;
    }

    public TPerson findOne(String email) throws Exception
    {
        TPerson retVal = null;

        String xpathExp = "//person[email=\"" + email + "\"]";
        ResourceSet resultSet =
            RetriveFromDB.executeXPathExpression(personCollectionId, xpathExp, TARGET_NAMESPACE);
        
        // treba isprolaziti kroz 
        ResourceIterator i = resultSet.getIterator();
        XMLResource res = null;
        while(i.hasMoreResources()) {
        	try {
                res = (XMLResource)i.nextResource();
                // pretvori ga u TPerson
                retVal =  PersonUnmarshalling.unmarshalling(res);
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

    public TPerson update(TPerson person) throws Exception
    {
        String xmlPerson = PersonMarshalling.marshalPerson(person);
        if(xmlPerson != null)
        {
            String xpathExp = "//person[email=\"" + person.getEmail() + "\"]";
            long mods = UpdateDB.update(personCollectionId, xpathExp, xmlPerson, UPDATE);   
            if(mods == 0)
            {
                throw new ResourceNotUpdated(String.format( "person with email %s", person.getEmail()));
            }
        }
        return person;
    }

    public void deletePerson(String email) throws Exception
    {
        String xpathExp = "//person[email=\"" + email + "\"]";
        long mods = UpdateDB.delete(personCollectionId, xpathExp);   
        if(mods == 0)
        {
            throw new ResourceNotDeleted(String.format("person with email %s",email));
        }
    }

}