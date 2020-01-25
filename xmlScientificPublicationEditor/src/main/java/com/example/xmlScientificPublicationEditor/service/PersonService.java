package com.example.xmlScientificPublicationEditor.service;

import java.util.Collection;

import com.example.xmlScientificPublicationEditor.model.authPerson.TAuthPerson;
import com.example.xmlScientificPublicationEditor.model.person.TPerson;


public interface PersonService {

	TAuthPerson registration(TAuthPerson person) throws Exception;

	Collection<TPerson> findAll();

	TPerson findOne(String email) throws Exception;

	TAuthPerson findOneAuth(String email) throws Exception;

	TPerson update(TPerson person) throws Exception;

	void delete(String email) throws Exception;

	String generatePersonXMLTemplate() throws Exception;

	String generateAuthXMLTemplate() throws Exception;

	// void changeUserPassword(PasswordChangeDTO pcDto, String username) throws Exception;

	// Person findOneByUsername(String name) throws Exception;

	// void verifiedUserEmail(String token);

}