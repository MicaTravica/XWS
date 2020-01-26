package com.example.xmlScientificPublicationEditor.controller;

import java.io.ByteArrayOutputStream;

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

import com.example.xmlScientificPublicationEditor.service.NotificationService;
import com.example.xmlScientificPublicationEditor.service.ScientificPublicationService;

@RestController
@RequestMapping("/api")
public class ScientificPublicationController extends BaseController {

	@Autowired
	private ScientificPublicationService scientificPublicationService;
	
//	obrisati ksanije
	@Autowired
	private NotificationService nService;
	
	@GetMapping(value="/notifications", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<?> getNotificaions() throws Exception{
		String[] emails = new String[] {"mica97@email.com", "dusanbzr@gmail.com"};
		String spUrl = "localhost:8081/api/scientificPublication/sp1";
		nService.letterOfThanks(emails, spUrl);
		nService.addedCoverLetter(emails, spUrl);
		nService.addedQuestionnaire(emails, spUrl);
		nService.publicationAccepted(emails, spUrl);
		nService.publicationRejected(emails, spUrl);
		nService.questionnaireReviewers(emails, spUrl);
		return new ResponseEntity<>(HttpStatus.OK);
	}
//	dovde
	
	@GetMapping(value="/scientificPublication/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> getScientificPublicationById(@PathVariable("id") String id) throws Exception{
		String sp = scientificPublicationService.findOne(id);
		return new ResponseEntity<>(sp, HttpStatus.OK);
	}
	
	@GetMapping(value="/scientificPublication/html/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getScientificPublicationByIdHTML(@PathVariable("id") String id) throws Exception{
		String sp = scientificPublicationService.findOneHTML(id);
		return new ResponseEntity<>(sp, HttpStatus.OK);
	}

	@GetMapping(value="/scientificPublication/pdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> getScientificPublicationByIdPDF(@PathVariable("id") String id) throws Exception{
		ByteArrayOutputStream sp = scientificPublicationService.findOnePDF(id);
		return new ResponseEntity<>(sp.toByteArray(), HttpStatus.OK);
	}

	@PostMapping(value="/scientificPublication", 
			consumes = MediaType.APPLICATION_XML_VALUE,
			produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> addScientificPublication(@RequestBody String scientificPublication) throws Exception {
		String id = scientificPublicationService.save(scientificPublication);
		return new ResponseEntity<>(String.format("You succesfully add scientific publication with id %s", id), HttpStatus.OK);
	}
	
	@PutMapping(value="/scientificPublication", consumes = MediaType.APPLICATION_XML_VALUE,produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> updateScientificPublication(@RequestBody String scientificPublication) throws Exception {
		String sp = scientificPublicationService.update(scientificPublication);
		return new ResponseEntity<>(sp, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/scientificPublication/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> deleteScientificPublication(@PathVariable("id")String id) throws Exception{
		scientificPublicationService.delete(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

}
