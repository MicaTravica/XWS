package com.example.xmlScientificPublicationEditor.controller;

import com.example.xmlScientificPublicationEditor.service.QuestionnaireService;

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


@RestController
@RequestMapping("/api")
public class QuestionnaireController extends BaseController {

	@Autowired
	private QuestionnaireService questionnaireService;
	
	@GetMapping(value="/coverLetter/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> getQuestionnaireById(@PathVariable("id") String id) throws Exception{
		String questionnaire = questionnaireService.findOne(id);
		return new ResponseEntity<>(questionnaire, HttpStatus.OK);
	}
	
	@PostMapping(value="/coverLetter", 
			consumes = MediaType.APPLICATION_XML_VALUE,
			produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> addQuestionnaire(@RequestBody String q) throws Exception {
		String id = questionnaireService.save(q);
		return new ResponseEntity<>(String.format("You succesfully add Questionnaire with id %s", id), HttpStatus.OK);
	}
	
	@PutMapping(value="/coverLetter", consumes = MediaType.APPLICATION_XML_VALUE,produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> updateQuestionnaire(@RequestBody String questionnaire) throws Exception {
		String q = questionnaireService.update(questionnaire);
		return new ResponseEntity<>(q, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/coverLetter/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> delete(@PathVariable("id")String id) throws Exception{
		questionnaireService.delete(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

}
