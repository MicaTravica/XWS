package com.example.xmlScientificPublicationEditor.controller; 


import com.example.xmlScientificPublicationEditor.model.person.TPerson;
import com.example.xmlScientificPublicationEditor.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PersonController extends BaseController {

	@Autowired
	private PersonService personService;

	@GetMapping(value = "/person/{email}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<TPerson> getPersonById(@PathVariable("email") String email ) throws Exception {
		TPerson person = personService.findOne(email);
        return new ResponseEntity<>(person, HttpStatus.OK);
	}
	@PostMapping(value="/registration", 
				consumes = MediaType.APPLICATION_XML_VALUE,
				produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String>registration(@RequestBody TPerson person) throws Exception {
		personService.registration(person);
		return new ResponseEntity<>("You are registred, now you need to verify your email", HttpStatus.OK);
	}

}



