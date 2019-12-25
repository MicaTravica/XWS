package com.example.xmlScientificPublicationEditor.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xmlScientificPublicationEditor.repository.IdGeneratorRepository;
import com.example.xmlScientificPublicationEditor.service.IdGeneratorService;

@Service
public class IdGeneratorServiceImpl implements IdGeneratorService {

	@Autowired
	private IdGeneratorRepository idGeneratorRepository;
	
	@Override
	public String getId(String element) throws Exception {
		return idGeneratorRepository.findOne(element);
	}
}
