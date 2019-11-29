package com.example.xmlScientificPublicationEditor.exception;

public class UserNotFoundByEmailException extends Exception {

	private static final long serialVersionUID = 325420815083654461L;

	public UserNotFoundByEmailException(String username) {
		super(String.format("User with %s email is not found!", username));
	}

}
