package com.example.xmlScientificPublicationEditor.service;

import java.io.ByteArrayOutputStream;
import java.security.Principal;
import java.util.ArrayList;

import org.w3c.dom.Document;

public interface ScientificPublicationService {

	String findOne(String id) throws Exception;

	String save(String scientificPublication, String authorEmail) throws Exception;

	String update(String scientificPublication) throws Exception;

	void delete(String id) throws Exception;

	String findOneHTML(String id) throws Exception;

	ByteArrayOutputStream findOnePDF(String id) throws Exception;

	String generateSPXMLTemplate() throws Exception;


	String getScientificPublicationID(Document sc);

	String getScientificPublicationName(Document sc);

	String findOneNotPub(String id, String name) throws Exception;

	String saveNewVersion(String scientificPublication, String name, String processId) throws Exception;

	void addAcceptedAt(String idSp) throws Exception;

	String search(String param, Principal user) throws Exception;

	String metadataSearch(String param, Principal user) throws Exception;

	String getSPReview(String processId, String email) throws Exception;

	void saveComments(String file, String name, String processId) throws Exception;

	String findOneByProcessId(String id, Object object) throws Exception;

	String findOneByProcessIdHTML(String id, Object object) throws Exception;

	ByteArrayOutputStream findOneByProcessIdPDF(String id, Object object) throws Exception;

	String findOnePub(String id) throws Exception;

	String findOneByVersion(String id, String name) throws Exception;

	String findOneByVersionHTML(String id, String name) throws Exception;

	ByteArrayOutputStream findOneByVersionPDF(String id, String name) throws Exception;

	String getSPReviewHTML(String processId, String name) throws Exception;

	ByteArrayOutputStream getSPReviewPDF(String processId, String name) throws Exception;
	
	String getMetadataSPXML(String spId) throws Exception;

	String getMetadataSPJSON(String spId) throws Exception;

}
