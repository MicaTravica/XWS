package com.example.xmlScientificPublicationEditor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

import com.example.xmlScientificPublicationEditor.service.CoverLetterService;

@RestController
@RequestMapping("/api")
public class CoverLetterController extends BaseController {

	@Autowired
	private CoverLetterService coverLetterService;
	
	@GetMapping(value="/coverLetter/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> getCoverLetterById(@PathVariable("id") String id) throws Exception{
		String coverLetter = coverLetterService.findOne(id);
		return new ResponseEntity<>(coverLetter, HttpStatus.OK);
	}

	@GetMapping(value="/coverLetter/html/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getCoverLetterByIdHTML(@PathVariable("id") String id) throws Exception{
		String coverLetter = coverLetterService.findOneHTML(id);
		return new ResponseEntity<>(coverLetter, HttpStatus.OK);
	}

	@GetMapping(value="/coverLetter/pdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> getCoverLetterByIdPDF(@PathVariable("id") String id) throws Exception{
		ByteArrayOutputStream coverLetter = coverLetterService.findOnePDF(id);
		return new ResponseEntity<>(coverLetter.toByteArray(), HttpStatus.OK);
	}

	
	@PostMapping(value="/coverLetter", 
			consumes = MediaType.APPLICATION_XML_VALUE,
			produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> addCoverLetter(@RequestBody String cl) throws Exception {
		String id = coverLetterService.save(cl);
		return new ResponseEntity<>(String.format("You succesfully add cover letter with id %s", id), HttpStatus.OK);
	}
	
	@PutMapping(value="/coverLetter", consumes = MediaType.APPLICATION_XML_VALUE,produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> updateCoverLetter(@RequestBody String coverLetter) throws Exception {
		String cl = coverLetterService.update(coverLetter);
		return new ResponseEntity<>(cl, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/coverLetter/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> deleteCoverLetter(@PathVariable("id")String id) throws Exception{
		coverLetterService.delete(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

}
