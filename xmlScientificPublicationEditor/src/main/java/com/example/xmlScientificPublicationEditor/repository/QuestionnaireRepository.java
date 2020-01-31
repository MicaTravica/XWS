package com.example.xmlScientificPublicationEditor.repository;

import static com.example.xmlScientificPublicationEditor.util.template.XUpdateTemplate.TARGET_NAMESPACE;

import java.io.StringWriter;
import java.util.HashMap;

import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotDeleted;
import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.service.IdGeneratorService;
import com.example.xmlScientificPublicationEditor.serviceImpl.IdGeneratorServiceImpl;
import com.example.xmlScientificPublicationEditor.util.DOMParser.DOMParser;
import com.example.xmlScientificPublicationEditor.util.RDF.StoreToRDF;
import com.example.xmlScientificPublicationEditor.util.RDF.UpdateRDF;
import com.example.xmlScientificPublicationEditor.util.existAPI.RetriveFromDB;
import com.example.xmlScientificPublicationEditor.util.existAPI.StoreToDB;
import com.example.xmlScientificPublicationEditor.util.existAPI.UpdateDB;

@Repository
public class QuestionnaireRepository {

	@Autowired
	private IdGeneratorService idGeneratorService;

	public static String QuestionnaireCollectionId = "/db/sample/questionnaire";
	public static String QuestionnaireSchemaPath = "src/main/resources/data/schemas/questionnaire.xsd";
	public static String QUESTIONNAIRE_NAMED_GRAPH_URI_PREFIX = "/example/questionnaire/";
	public static String QuestionnairerXSLPath = "src/main/resources/data/xslt/questionnaire.xsl";
	public static String QuestionnaireXSL_FO_PATH = "src/main/resources/data/xsl-fo/questionnaire_fo.xsl";

	public String findOne(String id) throws Exception {
		String retVal = null;
		String xpathExp = "//ns1:questionnaire[@id=\"" + id + "\"]";
		ResourceSet resultSet = RetriveFromDB.executeXPathExpression(QuestionnaireCollectionId, xpathExp,
				TARGET_NAMESPACE);
		if (resultSet == null) {
			return retVal;
		}
		ResourceIterator i = resultSet.getIterator();
		XMLResource res = null;
		while (i.hasMoreResources()) {
			try {
				res = (XMLResource) i.nextResource();
				retVal = res.getContent().toString();
				return retVal;
			} finally {
				// don't forget to cleanup resources
				try {
					((EXistResource) res).freeResources();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
		return null;
	}

	public String save(String questionnaire) throws Exception {
		Document document = DOMParser.buildDocument(questionnaire, QuestionnaireSchemaPath);
		String id = "que" + idGeneratorService.getId("questionnaire");
		document.getDocumentElement().getAttributes().getNamedItem("id").setTextContent(id);

		generateIDs(document, id);
		
		String toSave = DOMParser.parseDocument(document, QuestionnaireSchemaPath);
		StoreToDB.store(QuestionnaireCollectionId, id, toSave);
		return id;
	}

	private void generateIDs(Document document, String id) throws Exception {
		Node reviewer = document.getElementsByTagName(IdGeneratorServiceImpl.REVIEWER).item(0);
		idGeneratorService.generateElementId(reviewer, id + "_reviewer", IdGeneratorServiceImpl.REVIEWER);

		Node institution = document.getElementsByTagName(IdGeneratorServiceImpl.INSTITUTION).item(0);
		idGeneratorService.generateElementId(institution, id + "_institution", IdGeneratorServiceImpl.INSTITUTION);

		Node address = document.getElementsByTagName(IdGeneratorServiceImpl.ADDRESS).item(0);
		idGeneratorService.generateElementId(address, id + "_address", IdGeneratorServiceImpl.ADDRESS);

		NodeList question = document.getElementsByTagName(IdGeneratorServiceImpl.QUESTION);
		for (int i = 0; i < question.getLength(); ++i) {
			idGeneratorService.generateElementId(question.item(i), id + "_question" + (i + 1), IdGeneratorServiceImpl.QUESTION);
		}
		
		NodeList answer = document.getElementsByTagName(IdGeneratorServiceImpl.ANSWER);
		for (int i = 0; i < answer.getLength(); ++i) {
			idGeneratorService.generateParagraphId(answer.item(i), id + "_answer" + (i + 1));
		}
		
		NodeList content = document.getElementsByTagName(IdGeneratorServiceImpl.CONTENT);
		for (int i = 0; i < content.getLength(); ++i) {
			idGeneratorService.generateParagraphId(content.item(i), id + "_content" + (i + 1));
		}
	}

	public String update(String questionnaire) throws Exception {
		Document document = DOMParser.buildDocument(questionnaire, QuestionnaireSchemaPath);
		String id = document.getDocumentElement().getAttribute("id");

		String oldQuestionnaireData = this.findOne(id);
		if (oldQuestionnaireData == null) {
			throw new ResourceNotFoundException("Questionnaire with id: " + id);
		}
		this.delete(id);
		StoreToDB.store(QuestionnaireCollectionId, id, questionnaire);
		return id;
	}

	public void delete(String id) throws Exception {
		String xpathExp = "/ns1:questionnaire";
		long mods = UpdateDB.delete(QuestionnaireCollectionId, id, xpathExp);
		if (mods == 0) {
			throw new ResourceNotDeleted(String.format("Questionnaire with documentId %s", id));
		}
		deleteMetadata(id);
	}

	public void saveMetadata(StringWriter metadata, String cvId) throws Exception {
		StoreToRDF.store(metadata, QUESTIONNAIRE_NAMED_GRAPH_URI_PREFIX + cvId);
	}

	public void deleteMetadata(String cvId) throws Exception {
		UpdateRDF.delete(QUESTIONNAIRE_NAMED_GRAPH_URI_PREFIX + cvId);
	}

	public void updateMetadata(StringWriter metadata, String cvId) throws Exception {
		String url = QUESTIONNAIRE_NAMED_GRAPH_URI_PREFIX + cvId;
		deleteMetadata(cvId);
		StoreToRDF.store(metadata, url);
	}


}