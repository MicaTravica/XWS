package com.example.xmlScientificPublicationEditor.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.example.xmlScientificPublicationEditor.model.authPerson.TAuthPerson;
import com.example.xmlScientificPublicationEditor.model.person.TPerson;
import com.example.xmlScientificPublicationEditor.model.person.TPersons;
import com.example.xmlScientificPublicationEditor.security.TokenUtils;
import com.example.xmlScientificPublicationEditor.service.PersonService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api")
public class PersonController extends BaseController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private PersonService personService;

	@GetMapping(value = "/person/getPersonTemplate", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> getPersonTemplate() throws Exception {
		String person = personService.generatePersonXMLTemplate();
        return new ResponseEntity<>(person, HttpStatus.OK);
	}

	@GetMapping(value = "/person/getAuthTemplate", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> getAuthTemplate() throws Exception {
		String auth = personService.generateAuthXMLTemplate();
        return new ResponseEntity<>(auth, HttpStatus.OK);
	}

	@GetMapping(value = "/person/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<TPerson> getPersonById(@PathVariable("id") String email ) throws Exception {
		TPerson person = personService.findOne(email);
        return new ResponseEntity<>(person, HttpStatus.OK);
	}
	
	@GetMapping(value = "/person", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<TPerson> getMe(Principal user) throws Exception {
		TPerson person = personService.findMe(user.getName());
        return new ResponseEntity<>(person, HttpStatus.OK);
	}

	@GetMapping(value = "/person/reviewer", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_REDACTOR')")
	public ResponseEntity<TPersons> getReviewers() throws Exception {
		TPersons reviewer = personService.findReviewers();
        return new ResponseEntity<>(reviewer, HttpStatus.OK);
	}

	@GetMapping(value = "/person/recommendedReviewer", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_REDACTOR')")
	public ResponseEntity<TPersons> getRecommendedReviewer(@RequestParam(("processId")) String processId) throws Exception {
		TPersons reviewer = personService.findRecommendedReviewers(processId);
		return new ResponseEntity<>(reviewer, HttpStatus.OK);
	}

	@PostMapping(value="/login", 
				consumes = MediaType.APPLICATION_XML_VALUE,
				produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String>login(@RequestBody TAuthPerson person) throws Exception {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(person.getEmail(), person.getPassword());
		authenticationManager.authenticate(token);
		UserDetails userDetails = userDetailsService.loadUserByUsername(person.getEmail());
		return new ResponseEntity<>(tokenUtils.generateToken(userDetails), HttpStatus.OK);
	}

	@PostMapping(value="/registration", 
				consumes = MediaType.APPLICATION_XML_VALUE,
				produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String>registration(@RequestBody TAuthPerson person) throws Exception {
		personService.registration(person);
		return new ResponseEntity<>("You are registred, now you need to verify your email", HttpStatus.OK);
	}

	@PutMapping(value="/person", consumes = MediaType.APPLICATION_XML_VALUE,produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<TPerson> updatePerson(@RequestBody TPerson person) throws Exception {
		TPerson p = personService.update(person);
		return new ResponseEntity<>(p, HttpStatus.OK);
	}

	@DeleteMapping(value="/person/{email}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> delete(@PathVariable("email")String email) throws Exception{
		personService.delete(email);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	
}



