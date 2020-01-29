package com.example.xmlScientificPublicationEditor.service;

import java.io.ByteArrayOutputStream;

public interface ScientificPublicationService {

	String findOne(String id) throws Exception;

	String save(String scientificPublication, String authorEmail) throws Exception;

	String update(String scientificPublication) throws Exception;

	void delete(String id) throws Exception;

	String findOneHTML(String id) throws Exception;

	ByteArrayOutputStream findOnePDF(String id) throws Exception;

	String generateSPXMLTemplate() throws Exception;

}
