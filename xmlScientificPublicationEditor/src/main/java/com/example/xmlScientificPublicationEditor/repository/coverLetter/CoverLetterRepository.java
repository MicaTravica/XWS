package com.example.xmlScientificPublicationEditor.repository.coverLetter;

import org.springframework.stereotype.Repository;
import org.xmldb.api.modules.XMLResource;

import com.example.xmlScientificPublicationEditor.util.StoreToDB;

@Repository
public class CoverLetterRepository {

	public static String coverLetterCollectionId = "/db/sample/coverLetter";
	
	public XMLResource findOne(String id) {
		return null;
	}

	public XMLResource save(String cl) throws Exception {
//		String id = cl.getId();
		StoreToDB.store(coverLetterCollectionId, "cao", cl.toString());
		return null;
	}

	public XMLResource update(XMLResource coverLetter) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

}
