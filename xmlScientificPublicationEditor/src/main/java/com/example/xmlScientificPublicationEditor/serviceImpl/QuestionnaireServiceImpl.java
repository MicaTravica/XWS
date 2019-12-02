package com.example.xmlScientificPublicationEditor.serviceImpl;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.repository.QuestionnaireRepository;
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
		String questionnaire = questionnaireRepository.findOne(id);
		if(questionnaire == null) {
			throw new ResourceNotFoundException(String.format("Questionnaire with id %s", id));
		}
		return questionnaire;
	}

	@Override
	public String save(String questionnaire) throws Exception {
		return questionnaireRepository.save(questionnaire);
	}

	@Override
	public String update(String questionnaire) throws Exception {
		return questionnaireRepository.update(questionnaire);
	}

	@Override
	public void delete(String id) throws Exception {
		questionnaireRepository.delete(id);
	}
}