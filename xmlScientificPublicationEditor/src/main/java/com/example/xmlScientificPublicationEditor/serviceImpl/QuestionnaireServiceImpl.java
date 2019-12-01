package com.example.xmlScientificPublicationEditor.serviceImpl;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.repository.Questionnaire.QuestionnaireRepository;
import com.example.xmlScientificPublicationEditor.service.QuestionnaireService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * QuestionnaireServiceImpl
 */
@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
	private QuestionnaireRepository questionnaireRepository;
	
	@Override
	public String findOne(String id) throws Exception {
		String cl = questionnaireRepository.findOne(id);
		if(cl == null) {
			throw new ResourceNotFoundException(String.format("Questionnaire with id %s", id));
		}
		return cl;
	}

	@Override
	public String save(String cl) throws Exception {
		return questionnaireRepository.save(cl);
	}

	@Override
	public String update(String coverLetter) throws Exception {
		return questionnaireRepository.update(coverLetter);
	}

	@Override
	public void delete(String id) throws Exception {
		questionnaireRepository.delete(id);
	}
}