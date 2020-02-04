package com.example.xmlScientificPublicationEditor.service;

import java.io.ByteArrayOutputStream;

public interface CoverLetterService {

	String findOne(String id, String email) throws Exception;

	String findOneHTML(String id, String email) throws Exception;

	ByteArrayOutputStream findOnePDF(String id, String email) throws Exception;
	
	String save(String cl, String processId) throws Exception;

	String update(String coverLetter) throws Exception;

	void delete(String id) throws Exception;

	String generateCoverLetterXMLTemplate() throws Exception;

}
