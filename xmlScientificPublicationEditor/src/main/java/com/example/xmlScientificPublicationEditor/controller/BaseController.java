package com.example.xmlScientificPublicationEditor.controller;

import com.example.xmlScientificPublicationEditor.exception.ResourceExistsException;
import com.example.xmlScientificPublicationEditor.exception.UserNotFoundByEmailException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;


public abstract class BaseController {

	@ExceptionHandler
	public ResponseEntity<String> handlException(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({UserNotFoundByEmailException.class})
	public ResponseEntity<String> authenticationException(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ResourceExistsException.class})
	public ResponseEntity<String> resourceExistsException(Exception e){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

}

