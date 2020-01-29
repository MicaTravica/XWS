package com.example.xmlScientificPublicationEditor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.xmlScientificPublicationEditor.service.ProcessPSPService;

/**
 * ProcessPSPController
 */
@RestController
@RequestMapping("/api")
public class ProcessPSPController {

    @Autowired
    private ProcessPSPService processPSPService;

    @GetMapping(value="/processPSP/getForPublishing", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> getProcessPSPForPublishing() throws Exception{
		String sp = processPSPService.findForPublishing();
		return new ResponseEntity<>(sp, HttpStatus.OK);
	}
    
    @PostMapping(value="/processPSP/addReviewers/{id}", 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> addReviewers(@RequestBody String reviewers, @PathVariable("id") String processId) throws Exception {
    	processPSPService.addReviewers(reviewers, processId);
		return new ResponseEntity<>(String.format("You succesfully add reviewers!"), HttpStatus.OK);
	}
	
    
}