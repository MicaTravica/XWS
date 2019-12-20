package com.example.xmlScientificPublicationEditor.serviceImpl;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.repository.CoverLetterRepository;
import com.example.xmlScientificPublicationEditor.service.CoverLetterService;
import com.example.xmlScientificPublicationEditor.util.XSLFOTransformer.XSLFOTransformer;

@Service
public class CoverLetterServiceImpl implements CoverLetterService {

	@Autowired
	private XSLFOTransformer xslFoTransformer;

	@Autowired
	private CoverLetterRepository coverLetterRepository;

	@Override
	public String findOne(String id) throws Exception {
		String cl = coverLetterRepository.findOne(id);
		if (cl == null) {
			throw new ResourceNotFoundException(String.format("Cover letter with id %s", id));
		}
		return cl;
	}

	@Override
	public String findOneHTML(String id) throws Exception {
		String cl = coverLetterRepository.findOne(id);
		if (cl == null) {
			throw new ResourceNotFoundException(String.format("Cover letter with id %s", id));
		}
		String clHTML = xslFoTransformer.generateHTML(cl, CoverLetterRepository.CoverLetterXSLPath);
		return clHTML;
	}

	@Override
	public ByteArrayOutputStream findOnePDF(String id) throws Exception {
		String cl = coverLetterRepository.findOne(id);
		if (cl == null) {
			throw new ResourceNotFoundException(String.format("Cover letter with id %s", id));
		}
		ByteArrayOutputStream clPDF = xslFoTransformer.generatePDF(cl, CoverLetterRepository.CoverLetterXSL_FO_PATH);
		return clPDF;

	}

	@Override
	public String save(String cl) throws Exception {
		return coverLetterRepository.save(cl);
	}

	@Override
	public String update(String coverLetter) throws Exception {
		return coverLetterRepository.update(coverLetter);
	}

	@Override
	public void delete(String id) throws Exception {
		coverLetterRepository.delete(id);
	}

}
