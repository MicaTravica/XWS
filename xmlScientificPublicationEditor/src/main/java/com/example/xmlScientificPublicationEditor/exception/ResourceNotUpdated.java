package com.example.xmlScientificPublicationEditor.exception;

public class ResourceNotUpdated extends Exception {

    private static final long serialVersionUID = 5409679831150278252L;

    public ResourceNotUpdated(String entity) {
		super(String.format("%s is not updated!", entity));
	}
}
