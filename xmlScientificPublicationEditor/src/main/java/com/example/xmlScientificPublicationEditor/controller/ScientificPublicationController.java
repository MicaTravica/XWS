package com.example.xmlScientificPublicationEditor.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.example.xmlScientificPublicationEditor.service.NotificationService;
import com.example.xmlScientificPublicationEditor.service.ScientificPublicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> addScientificPublication(@RequestBody String scientificPublication) throws Exception {
		String id = scientificPublicationService.save(scientificPublication);
		return new ResponseEntity<>(String.format("You succesfully add scientific publication with id %s", id), HttpStatus.OK);
	}
	@PostMapping(value="/scientificPublication/upload", 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> uploadScientificPublication(@RequestParam(("file")) MultipartFile q) throws Exception {
		BufferedReader br;
		StringBuilder sb = new StringBuilder();
		try {
     		String line;
     		InputStream is = q.getInputStream();
     		br = new BufferedReader(new InputStreamReader(is));
     		while ((line = br.readLine()) != null) {
				  sb.append(line);
				  sb.append("\n");
     		}
		} catch (IOException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
		}
		String id = scientificPublicationService.save(sb.toString());
		return new ResponseEntity<>(String.format("You succesfully add scientific publication with id %s",id), HttpStatus.OK);
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
	
	@GetMapping(value = "/scientificPublication/getSPTemplate", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> getSPTemplate() throws Exception {
		String sp = scientificPublicationService.generateSPXMLTemplate();
        return new ResponseEntity<>(sp, HttpStatus.OK);
	}

}
