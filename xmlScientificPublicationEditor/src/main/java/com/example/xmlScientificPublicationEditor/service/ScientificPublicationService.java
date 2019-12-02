package com.example.xmlScientificPublicationEditor.service;

public interface ScientificPublicationService {

	String findOne(String id) throws Exception;

	String save(String scientificPublication) throws Exception;

	String update(String scientificPublication) throws Exception;

	void delete(String id) throws Exception;

}
