package com.example.xmlScientificPublicationEditor.service;

import org.xmldb.api.modules.XMLResource;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;

public interface CoverLetterService {

	XMLResource findOne(String id) throws ResourceNotFoundException;

	XMLResource save(String cl) throws Exception;

	XMLResource update(XMLResource coverLetter);

	void delete(String id);

}
