package com.example.xmlScientificPublicationEditor.exception;

public class MarshallerException extends Exception {

    private static final long serialVersionUID = -837116719109228941L;

    public MarshallerException(String exist) {
		super(String.format("%s unsuccessfully marshalled", exist));
	}
}
