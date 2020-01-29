package com.example.xmlScientificPublicationEditor.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
	public ResponseEntity<String> addCoverLetter(@RequestParam(("processId")) String processId, @RequestBody String cl) throws Exception {
		String id = coverLetterService.save(cl, processId);
		return new ResponseEntity<>(String.format("You succesfully add cover letter with id %s", id), HttpStatus.OK);
	}

	@PostMapping(value = "/coverLetter/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> uploadCoverLetter(
		@RequestParam(("processId")) String processId, @RequestParam(("file")) MultipartFile q) throws Exception {
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
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
		String id = coverLetterService.save(sb.toString(), processId);
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

	@GetMapping(value = "/coverLetter/getCoverLetterTemplate", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> getCoverLetterTemplate() throws Exception {
		String coverLetter = coverLetterService.generateCoverLetterXMLTemplate();
        return new ResponseEntity<>(coverLetter, HttpStatus.OK);
	}
}
