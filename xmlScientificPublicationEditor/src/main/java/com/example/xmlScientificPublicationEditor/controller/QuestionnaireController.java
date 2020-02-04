package com.example.xmlScientificPublicationEditor.controller;

import java.io.ByteArrayOutputStream;
import java.security.Principal;

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

import com.example.xmlScientificPublicationEditor.service.QuestionnaireService;
import com.example.xmlScientificPublicationEditor.util.MyFile;


@RestController
@RequestMapping("/api")
public class QuestionnaireController extends BaseController {

	@Autowired
	private QuestionnaireService questionnaireService;
	
	@GetMapping(value="/questionnaire/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> getQuestionnaireById(@PathVariable("id") String id) throws Exception{
		String questionnaire = questionnaireService.findOne(id);
		return new ResponseEntity<>(questionnaire, HttpStatus.OK);
	}
	@GetMapping(value="/questionnaire/html/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getQuestionnaireByIdHTML(@PathVariable("id") String id) throws Exception{
		String questionnaire = questionnaireService.findOneHTML(id);
		return new ResponseEntity<>(questionnaire, HttpStatus.OK);
	}

	@GetMapping(value="/questionnaire/pdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> getQuestionnaireByIdPDF(@PathVariable("id") String id) throws Exception{
		ByteArrayOutputStream questionnaire = questionnaireService.findOnePDF(id);
		return new ResponseEntity<>(questionnaire.toByteArray(), HttpStatus.OK);
	}
	@PostMapping(value="/questionnaire", 
			consumes = MediaType.APPLICATION_XML_VALUE,
			produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> addQuestionnaire(
			@RequestParam(("willComment")) Boolean willComment,
			@RequestParam(("processId")) String processId,
			@RequestBody String q,
			Principal reviewer) throws Exception {
		String id = questionnaireService.save(q, processId, reviewer.getName(), willComment);
		return new ResponseEntity<>(String.format("You succesfully add Questionnaire with id %s", id), HttpStatus.OK);
	}

	@PostMapping(value="/questionnaire/upload", 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> uploadQuestionnaire(
			@RequestParam(("willComment")) Boolean willComment,
			@RequestParam(("processId")) String processId,
			@RequestParam(("file")) MultipartFile q,
			Principal reviewer) throws Exception {
		String file = MyFile.readFile(q);
		String id = questionnaireService.save(file, processId, reviewer.getName(), willComment);
		return new ResponseEntity<>(String.format("You succesfully add Questionnaire with id %s",id), HttpStatus.OK);
	}

	
	@PutMapping(value="/questionnaire", consumes = MediaType.APPLICATION_XML_VALUE,produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> updateQuestionnaire(@RequestBody String questionnaire) throws Exception {
		String q = questionnaireService.update(questionnaire);
		return new ResponseEntity<>(q, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/questionnaire/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> delete(@PathVariable("id")String id) throws Exception{
		questionnaireService.delete(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value = "/questionnaire/getQuestionnaireTemplate", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> getQuestionnaireTemplate() throws Exception {
		String questionnaire = questionnaireService.generateQuestionnaireXMLTemplate();
        return new ResponseEntity<>(questionnaire, HttpStatus.OK);
	}

}
