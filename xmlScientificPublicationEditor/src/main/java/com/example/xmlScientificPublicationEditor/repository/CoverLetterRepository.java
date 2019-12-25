package com.example.xmlScientificPublicationEditor.repository;

import static com.example.xmlScientificPublicationEditor.util.template.XUpdateTemplate.TARGET_NAMESPACE;

import java.io.StringWriter;

import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotDeleted;
import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.service.IdGeneratorService;
import com.example.xmlScientificPublicationEditor.util.DOMParser.DOMParser;
import com.example.xmlScientificPublicationEditor.util.RDF.StoreToRDF;
import com.example.xmlScientificPublicationEditor.util.RDF.UpdateRDF;
import com.example.xmlScientificPublicationEditor.util.existAPI.RetriveFromDB;
import com.example.xmlScientificPublicationEditor.util.existAPI.StoreToDB;
import com.example.xmlScientificPublicationEditor.util.existAPI.UpdateDB;

@Repository
public class CoverLetterRepository {

	@Autowired
	private IdGeneratorService idGeneratorService;

	public static String coverLetterCollectionId = "/db/sample/coverLetter";
	public static String coverLetterSchemaPath = "src/main/resources/data/schemas/coverLetter.xsd";
	public static String CoverLetterXSLPath = "src/main/resources/data/xslt/coverLetter.xsl";
	public static String CoverLetterXSL_FO_PATH = "src/main/resources/data/xsl-fo/coverLetter_fo.xsl";
	public static String CV_NAMED_GRAPH_URI_PREFIX = "/example/coverLetter/";

	public String findOne(String id) throws Exception {
		String retVal = null;
		String xpathExp = "//coverLetter[@id=\"" + id + "\"]";
		ResourceSet resultSet = RetriveFromDB.executeXPathExpression(coverLetterCollectionId, xpathExp,
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

	public String save(String coverLetter) throws Exception {
		Document document = DOMParser.buildDocument(coverLetter, coverLetterSchemaPath);
		String id = "cl" + idGeneratorService.getId("coverLetter");
		document.getElementsByTagName("coverLetter").item(0).getAttributes().getNamedItem("id").setTextContent(id);
		String toSave = DOMParser.parseDocument(document);
		StoreToDB.store(coverLetterCollectionId, id, toSave);
		return id;
	}

	public String update(String coverLetter) throws Exception {
		Document document = DOMParser.buildDocument(coverLetter, coverLetterSchemaPath);
		String id = document.getDocumentElement().getAttribute("id");

		String oldCoverLetterData = this.findOne(id);
		if (oldCoverLetterData == null) {
			throw new ResourceNotFoundException("Cover letter with id: " + id);
		}
		this.delete(id);
		StoreToDB.store(coverLetterCollectionId, id, coverLetter);
		return id;
	}

	public void delete(String id) throws Exception {
		String xpathExp = "/coverLetter";
		long mods = UpdateDB.delete(coverLetterCollectionId, id, xpathExp);
		if (mods == 0) {
			throw new ResourceNotDeleted(String.format("Cover letter with documentId %s", id));
		}
		deleteMetadata(id);
	}

	public void saveMetadata(StringWriter metadata, String cvId) throws Exception {
		StoreToRDF.store(metadata, CV_NAMED_GRAPH_URI_PREFIX + cvId);
	}

	public void deleteMetadata(String cvId) throws Exception {
		UpdateRDF.delete(CV_NAMED_GRAPH_URI_PREFIX + cvId);
	}

	public void updateMetadata(StringWriter metadata, String cvId) throws Exception {
		String url = CV_NAMED_GRAPH_URI_PREFIX + cvId;
		deleteMetadata(cvId);
		StoreToRDF.store(metadata, url);
	}

}
