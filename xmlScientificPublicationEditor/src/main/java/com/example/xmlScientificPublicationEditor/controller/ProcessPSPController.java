package com.example.xmlScientificPublicationEditor.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.xmlScientificPublicationEditor.model.person.TPersons;
import com.example.xmlScientificPublicationEditor.service.ProcessPSPService;

/**
 * ProcessPSPController
 */
@RestController
@RequestMapping("/api")
public class ProcessPSPController {

    @Autowired
    private ProcessPSPService processPSPService;

    @GetMapping(value="/processPSP/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    @PreAuthorize("hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> getReviewAssignments(@PathVariable("id")String id) throws Exception{
		String sp = processPSPService.findOne(id);
		return new ResponseEntity<>(sp, HttpStatus.OK);
	}
    
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
    
    @PostMapping(value="/processPSP/addReviewers", 
			consumes = MediaType.APPLICATION_XML_VALUE,
			produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> addReviewers(@RequestBody TPersons reviewers, @RequestParam(("id")) String processId) throws Exception {
    	processPSPService.addReviewers(reviewers, processId);
		return new ResponseEntity<>(String.format("You succesfully add reviewers!"), HttpStatus.OK);
	}
    
	@PostMapping(value = "/processPSP/changeState", consumes=MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    @PreAuthorize("hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> setChangeState(@RequestBody String xml) throws Exception {
		processPSPService.setProcessPSPStateFromScored(xml);
		return new ResponseEntity<>("Scientific publication state is changed!", HttpStatus.OK);
	}
	
	@GetMapping(value="/processPSP/myPSP/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> getMySPProcess(@PathVariable("id")String id, Principal principal) throws Exception{
		String process = processPSPService.findMySPProcess(id, principal.getName());
		return new ResponseEntity<>(process, HttpStatus.OK);
	}

	@GetMapping(value="/processPSP/acceptRejectAssigmentReviewTemplate", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> getMySPProcess() throws Exception{
		String template = processPSPService.generateReviewAssigmentXMLTemplate();
		return new ResponseEntity<>(template, HttpStatus.OK);
	}

	@PostMapping(value="/processPSP/acceptRejectAssigmentReview",
			consumes = MediaType.APPLICATION_XML_VALUE,
			produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> addReviewers(@RequestBody String acceptanceData, Principal reviewer) throws Exception {
		processPSPService.acceptRejectReviewAssigment(acceptanceData, reviewer.getName());
		return new ResponseEntity<>(String.format("You succesfully accepted/rejected review assigment!"), HttpStatus.OK);
	}

	@PutMapping(value="/processPSP/retract",
			produces = MediaType.APPLICATION_XML_VALUE,
			consumes = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> retractScientificPublication(
			@RequestParam(name = "processId") String processId) throws  Exception{
    	processPSPService.retractPSP(processId);
    	return  new ResponseEntity<>(
    			String.format("You succesfully retracted scientific publication!"),
				HttpStatus.OK);
	}

	@PutMapping(value="/processPSP/delete",
			produces = MediaType.APPLICATION_XML_VALUE,
			consumes = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> deleteScientificPublication(
			@RequestParam(name = "processId") String processId) throws  Exception{
		processPSPService.deletePSP(processId);
		return  new ResponseEntity<>(
				String.format("You succesfully deleted scientific publication!"),
				HttpStatus.OK);
	}
}