package com.example.xmlScientificPublicationEditor.exception;

public class ResourceNotDeleted extends Exception {

    private static final long serialVersionUID = 5409679831150278252L;

    public ResourceNotDeleted(String entity) {
		super(String.format("%s is not deleted!", entity));
	}
}
