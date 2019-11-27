package com.example.xmlScientificPublicationEditor.controller;

import com.example.xmlScientificPublicationEditor.api.RetrieveExample3;
import com.example.xmlScientificPublicationEditor.api.StoreExample1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * bookController
 */

@RestController
public class bookController {

    @Autowired
    private StoreExample1 storeExample1;

	@Autowired
	private RetrieveExample3 retrieveExample3;

    @GetMapping(value = "/api/book/write", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> writeHall() throws Exception{
        storeExample1.run();
		return new ResponseEntity<>("pisem", HttpStatus.OK);
	}

    @GetMapping(value = "/api/book/read", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> readHall() throws Exception{

		String retVal = retrieveExample3.run();
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}
    
}