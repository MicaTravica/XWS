package com.example.xmlScientificPublicationEditor.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.modules.XMLResource;

import com.example.xmlScientificPublicationEditor.exception.ResourceExistsException;
import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.repository.coverLetter.CoverLetterRepository;
import com.example.xmlScientificPublicationEditor.service.CoverLetterService;

@Service
public class CoverLetterServiceImpl implements CoverLetterService {

	@Autowired
	private CoverLetterRepository coverLetterRepository;
	
	@Override
	public XMLResource findOne(String id) throws ResourceNotFoundException {
		XMLResource cl = coverLetterRepository.findOne(id);
		if(cl == null) {
			throw new ResourceNotFoundException(String.format("Cover letter with id %s", id));
		}
		return cl;
	}

	@Override
	public XMLResource save(String cl) throws Exception {
//		XMLResource coverLetter = coverLetterRepository.findOne(cl.getId());
//		if(coverLetter != null) {
//			throw new ResourceExistsException(String.format("Cover letter with id: %s", cl.getId()));
//			
//		}
		return coverLetterRepository.save(cl);
	}

	@Override
	public XMLResource update(XMLResource coverLetter) {
		return coverLetterRepository.update(coverLetter);
	}

	@Override
	public void delete(String id) {
		coverLetterRepository.delete(id);
	}

}
