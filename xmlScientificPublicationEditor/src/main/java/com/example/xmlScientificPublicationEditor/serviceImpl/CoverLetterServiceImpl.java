package com.example.xmlScientificPublicationEditor.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.repository.CoverLetterRepository;
import com.example.xmlScientificPublicationEditor.service.CoverLetterService;
import com.example.xmlScientificPublicationEditor.util.RDF.MetadataExtractor;
import com.example.xmlScientificPublicationEditor.util.XSLFOTransformer.XSLFOTransformer;

@Service
public class CoverLetterServiceImpl implements CoverLetterService {

	@Autowired
	private XSLFOTransformer xslFoTransformer;

	@Autowired
	private MetadataExtractor metadataExtractor;
	
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
		String cvId = coverLetterRepository.save(cl);
		coverLetterRepository.saveMetadata(this.extractMetadata(cl), cvId);
		return cvId;
	}

	@Override
	public String update(String coverLetter) throws Exception {
		String cvId = coverLetterRepository.update(coverLetter);
		coverLetterRepository.updateMetadata(this.extractMetadata(coverLetter), cvId);
		return cvId;
	}

	@Override
	public void delete(String id) throws Exception {
		coverLetterRepository.delete(id);
	}

	@Override
	public StringWriter extractMetadata(String cl) throws Exception{
		StringWriter out = new StringWriter(); 
		StringReader in = new StringReader(cl); 
		metadataExtractor.extractMetadata(in, out);
		return out;
	}

}
