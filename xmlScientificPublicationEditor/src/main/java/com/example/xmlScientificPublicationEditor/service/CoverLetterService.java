package com.example.xmlScientificPublicationEditor.service;

import java.io.ByteArrayOutputStream;

public interface CoverLetterService {

	String findOne(String id) throws Exception;

	String findOneHTML(String id) throws Exception;

	ByteArrayOutputStream findOnePDF(String id) throws Exception;
	
	String save(String cl) throws Exception;

	String update(String coverLetter) throws Exception;

	void delete(String id) throws Exception;

}
