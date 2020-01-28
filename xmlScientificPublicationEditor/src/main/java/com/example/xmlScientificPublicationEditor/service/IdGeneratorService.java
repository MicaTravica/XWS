package com.example.xmlScientificPublicationEditor.service;

import org.w3c.dom.Node;

public interface IdGeneratorService {

	String getId(String element) throws Exception;

	void generateElementId(Node item, String id, String type) throws Exception;

	void generateParagraphId(Node node, String id) throws Exception;

	void generateChapterId(Node node, String id) throws Exception;
}
