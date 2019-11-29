package com.example.xmlScientificPublicationEditor.repository.person;

import com.example.xmlScientificPublicationEditor.model.person.TPerson;
import com.example.xmlScientificPublicationEditor.util.RetriveFromDB;
import com.example.xmlScientificPublicationEditor.util.StoreToDB;

import org.springframework.stereotype.Service;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.exist.xmldb.EXistResource;

@Service
public class PersonRepository {

    public static String personCollectionId = "/db/sample/person";
    public static String PERSON_TARGET_NAMESPACE = "http://www.uns.ac.rs/Tim1";
    
    public TPerson save(TPerson person) throws Exception{
        String fileName = PersonMarshalling.marshalPerson(person);
        if(fileName != null)
        {
            StoreToDB.store(personCollectionId, fileName);
        }
        return person;
    }

    public TPerson findOne(String email) throws Exception
    {
        TPerson retVal = null;

        String xpathExp = "//person[email=\"" + email + "\"]";
        ResourceSet resultSet =
            RetriveFromDB.executeXPathExpression(personCollectionId, xpathExp, PERSON_TARGET_NAMESPACE);
        
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
}