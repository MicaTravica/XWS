package com.example.xmlScientificPublicationEditor.service;

public interface CoverLetterService {

	String findOne(String id) throws Exception;

	String save(String cl) throws Exception;

	String update(String coverLetter) throws Exception;

	void delete(String id) throws Exception;

}
