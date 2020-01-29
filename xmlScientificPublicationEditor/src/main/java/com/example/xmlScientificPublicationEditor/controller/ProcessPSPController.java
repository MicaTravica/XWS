package com.example.xmlScientificPublicationEditor.controller;

import java.security.Principal;

import com.example.xmlScientificPublicationEditor.service.ProcessPSPService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping(value="/processPSP/getMyPublication", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> getMyPublication(Principal principal) throws Exception{
		String sp = processPSPService.findMyPublications(principal.getName());
		return new ResponseEntity<>(sp, HttpStatus.OK);
	}
	
	
	@GetMapping(value="/processPSP/getMyReviewAssigments", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> getMyReviewAssigments(Principal principal) throws Exception{
		String sp = processPSPService.findMyReviewAssigments(principal.getName());
		return new ResponseEntity<>(sp, HttpStatus.OK);
	}
}