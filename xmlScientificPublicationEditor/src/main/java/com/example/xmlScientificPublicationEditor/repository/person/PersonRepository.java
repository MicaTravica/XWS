package com.example.xmlScientificPublicationEditor.repository.person;

import static com.example.xmlScientificPublicationEditor.util.template.XUpdateTemplate.TARGET_NAMESPACE;

import java.util.ArrayList;
import java.util.HashMap;

import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.xmlScientificPublicationEditor.exception.MarshallerException;
import com.example.xmlScientificPublicationEditor.exception.ResourceNotDeleted;
import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.model.authPerson.TAuthPerson;
import com.example.xmlScientificPublicationEditor.model.person.TPerson;
import com.example.xmlScientificPublicationEditor.service.IdGeneratorService;
import com.example.xmlScientificPublicationEditor.util.existAPI.RetriveFromDB;
import com.example.xmlScientificPublicationEditor.util.existAPI.StoreToDB;
import com.example.xmlScientificPublicationEditor.util.existAPI.UpdateDB;

@Repository
public class PersonRepository {

	@Autowired
	private IdGeneratorService idGeneratorService;
	public static String personCollectionId = "/db/sample/person";
	public static String authCollectionId = "/db/sample/auth";
	public static String personSchemaPath = "src/main/resources/data/schemas/person.xsd";
	public static String userAuthSchemaPath = "src/main/resources/data/schemas/userAuth.xsd";

	public static String authPersonXQuery = "src/main/resources/data/xQuery/findAuthPerson.txt";

	public TPerson save(TPerson person) throws Exception {
		String id = "person" + idGeneratorService.getId("person");
		person.setId(id);
		String xmlPerson = PersonMarshalling.marshalPerson(person);
		if (xmlPerson == null) {
			throw new MarshallerException("Person");
		}
		StoreToDB.store(personCollectionId, id, xmlPerson);
		return person;
	}

	public TAuthPerson save(TAuthPerson person) throws Exception {
		String id = "auth" + idGeneratorService.getId("auth");
		person.setId(id);
		String xmlPerson = PersonMarshalling.marshalAuthPerson(person);
		if (xmlPerson == null) {
			throw new MarshallerException("AuthPerson");
		}
		StoreToDB.store(authCollectionId, id, xmlPerson);
		return person;
	}

	public TPerson findOne(String xpathExp) throws Exception {
		TPerson retVal = null;
		ResourceSet resultSet = RetriveFromDB.executeXPathExpression(personCollectionId, xpathExp, TARGET_NAMESPACE);
		// treba isprolaziti kroz
		if (resultSet == null) {
			return retVal;
		}
		ResourceIterator i = resultSet.getIterator();
		XMLResource res = null;
		while (i.hasMoreResources()) {
			try {
				res = (XMLResource) i.nextResource();
				// pretvori ga u TPerson
				retVal = PersonUnmarshalling.unmarshalling(res);
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

	public TAuthPerson findOneAuth(String xpathExp) throws Exception {
		TAuthPerson retVal = null;
		ResourceSet resultSet = RetriveFromDB.executeXPathExpression(authCollectionId, xpathExp, TARGET_NAMESPACE);
		// treba isprolaziti kroz
		if (resultSet == null) {
			return retVal;
		}
		ResourceIterator i = resultSet.getIterator();
		XMLResource res = null;
		while (i.hasMoreResources()) {
			try {
				res = (XMLResource) i.nextResource();
				// pretvori ga u TPerson
				retVal = PersonUnmarshalling.unmarshallingAuth(res);
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

	public ArrayList<TAuthPerson> findByRole(String xpathExp) throws Exception {
		ArrayList<TAuthPerson> retVal = new ArrayList<>();
		ResourceSet resultSet = RetriveFromDB.executeXPathExpression(authCollectionId, xpathExp, TARGET_NAMESPACE);
		// treba isprolaziti kroz
		if (resultSet == null) {
			return retVal;
		}
		ResourceIterator i = resultSet.getIterator();
		XMLResource res = null;
		while (i.hasMoreResources()) {
			try {
				res = (XMLResource) i.nextResource();
				// pretvori ga u TPerson
				retVal.add(PersonUnmarshalling.unmarshallingAuth(res));
			} finally {
				// don't forget to cleanup resources
				try {
					((EXistResource) res).freeResources();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
		return retVal;
	}




	public TPerson update(TPerson person) throws Exception {
		String personId = person.getId();
		TPerson oldPersonData = this.findOne(PersonRepository.makeXpathQueryById(personId));
		if (oldPersonData == null) {
			throw new ResourceNotFoundException("Person with id: " + personId);
		}
		String xmlPerson = PersonMarshalling.marshalPerson(person);
		if (xmlPerson == null) {
			throw new MarshallerException("Person");
		}
		this.deletePerson(personId);
		StoreToDB.store(personCollectionId, personId, xmlPerson);
		return person;
	}

	public void deletePerson(String documentId) throws Exception {
		String xpathExp = "/person";
		long mods = UpdateDB.delete(personCollectionId, documentId, xpathExp);
		if (mods == 0) {
			throw new ResourceNotDeleted(String.format("person with documentId %s", documentId));
		}
	}


	public static String makeXpathQueryByEmailAuthPerson(String parameter) {
		return String.format("//auth[email=\"%s\"]", parameter);
	}
	
	public static String makeXpathQueryByPersonIdAuthPerson(String parameter) {
		return String.format("//auth[person=\"%s\"]", parameter);
	}

	public static String makeXpathQueryByEmail(String parameter) {
		return String.format("//person[email=\"%s\"]", parameter);
	}

	public static String makeXpathQueryById(String parameter) {
		return String.format("//person[@id=\"%s\"]", parameter);
	}

	public static String makeXpathQueryByRole(String parameter) {
		return String.format("//auth[roles/role=(\"%s\")]", parameter);
	}

}