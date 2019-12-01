package com.example.xmlScientificPublicationEditor.repository.person;

import static com.example.xmlScientificPublicationEditor.util.template.XUpdateTemplate.TARGET_NAMESPACE;

import java.util.Calendar;

import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.xmlScientificPublicationEditor.exception.MarshallerException;
import com.example.xmlScientificPublicationEditor.exception.ResourceNotDeleted;
import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.model.person.TPerson;
import com.example.xmlScientificPublicationEditor.util.RetriveFromDB;
import com.example.xmlScientificPublicationEditor.util.StoreToDB;
import com.example.xmlScientificPublicationEditor.util.UpdateDB;

@Repository
public class PersonRepository {

    public static String personCollectionId = "/db/sample/person";
    
    public TPerson save(TPerson person) throws Exception{
        String personId = person.getName() + person.getSurname() + Calendar.getInstance().getTimeInMillis();
        person.setId(personId);
        String xmlPerson = PersonMarshalling.marshalPerson(person);
        if(xmlPerson == null)
        {
                throw new MarshallerException("Person");
        }
        StoreToDB.store(personCollectionId, personId, xmlPerson);
        return person;
    }

    public TPerson findOne(String personId) throws Exception
    {
        TPerson retVal = null;

        String xpathExp = "//person[@id=\"" + personId + "\"]";
        ResourceSet resultSet =
            RetriveFromDB.executeXPathExpression(personCollectionId, xpathExp, TARGET_NAMESPACE);
        // treba isprolaziti kroz
        if(resultSet == null)
        {
            return retVal;
        } 
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
        String personId = person.getId();
        TPerson oldPersonData = this.findOne(personId);
        if(oldPersonData == null)
        {
            throw new ResourceNotFoundException("Person with id: " + personId);
        }
        this.deletePerson(personId);
        String xmlPerson = PersonMarshalling.marshalPerson(person);
        if(xmlPerson == null)
        {
            throw new MarshallerException("Person");
        }
        StoreToDB.store(personCollectionId, personId, xmlPerson);
        return person;
    }

    public void deletePerson(String documentId) throws Exception
    {
        String xpathExp = "/person";
        long mods = UpdateDB.delete(personCollectionId,documentId, xpathExp);   
        if(mods == 0)
        {
            throw new ResourceNotDeleted(String.format("person with documentId %s",documentId));
        }
    }

}