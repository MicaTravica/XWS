package com.example.xmlScientificPublicationEditor.serviceImpl;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.repository.ScientificPublicationRepository;
import com.example.xmlScientificPublicationEditor.service.ScientificPublicationService;
import com.example.xmlScientificPublicationEditor.util.XSLFOTransformer.XSLFOTransformer;

@Service
public class ScientificPublicationServiceImpl implements ScientificPublicationService {

	@Autowired
	private XSLFOTransformer xslFoTransformer;

	@Autowired
	private ScientificPublicationRepository scientificPublicationRepository;

	@Override
	public String findOne(String id) throws Exception {
		String sp = scientificPublicationRepository.findOne(id);
		if (sp == null) {
			throw new ResourceNotFoundException(String.format("Scientific publication with id %s", id));
		}
		return sp;
	}

	@Override
	public String findOneHTML(String id) throws Exception {
		String sp = scientificPublicationRepository.findOne(id);
		if (sp == null) {
			throw new ResourceNotFoundException(String.format("Scientific publication with id %s", id));
		}
		String spHTML = xslFoTransformer.generateHTML(sp, ScientificPublicationRepository.ScientificPublicationXSLPath);
		return spHTML;
	}

	@Override
	public ByteArrayOutputStream findOnePDF(String id) throws Exception {
		String sp = scientificPublicationRepository.findOne(id);
		if (sp == null) {
			throw new ResourceNotFoundException(String.format("Scientific publication with id %s", id));
		}
		ByteArrayOutputStream spPDF = xslFoTransformer.generatePDF(sp,
				ScientificPublicationRepository.ScientificPublicationXSL_FO_PATH);
		return spPDF;
	}

	@Override
	public String save(String scientificPublication) throws Exception {
		return scientificPublicationRepository.save(scientificPublication);
	}

	@Override
	public String update(String scientificPublication) throws Exception {
		return scientificPublicationRepository.update(scientificPublication);
	}

	@Override
	public void delete(String id) throws Exception {
		scientificPublicationRepository.delete(id);
	}

}
