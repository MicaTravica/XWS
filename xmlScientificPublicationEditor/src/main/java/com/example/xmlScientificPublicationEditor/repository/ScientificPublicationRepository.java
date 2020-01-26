package com.example.xmlScientificPublicationEditor.repository;

import static com.example.xmlScientificPublicationEditor.util.template.XUpdateTemplate.TARGET_NAMESPACE;

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
import com.example.xmlScientificPublicationEditor.util.existAPI.RetriveFromDB;
import com.example.xmlScientificPublicationEditor.util.existAPI.StoreToDB;
import com.example.xmlScientificPublicationEditor.util.existAPI.UpdateDB;

@Repository
public class ScientificPublicationRepository {

	@Autowired
	private IdGeneratorService idGeneratorService;

	public static String scientificPublicationCollectionId = "/db/sample/scientificPublication";
	public static String scientificPublicationSchemaPath = "src/main/resources/data/schemas/scientificPublication.xsd";
	public static String ScientificPublicationXSLPath = "src/main/resources/data/xslt/scientificPublication.xsl";
	public static String ScientificPublicationXSL_FO_PATH = "src/main/resources/data/xsl-fo/scientificPublication_fo.xsl";

	public String findOne(String id) throws Exception {
		String retVal = null;
		String xpathExp = "//scientificPublication[@id=\"" + id + "\"]";
		ResourceSet resultSet = RetriveFromDB.executeXPathExpression(scientificPublicationCollectionId, xpathExp,
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

	public String save(String scientificPublication) throws Exception {
		Document document = DOMParser.buildDocument(scientificPublication, scientificPublicationSchemaPath);
		String id = "sp" + idGeneratorService.getId("scientificPublication");
		document.getDocumentElement().getAttributes()
					.getNamedItem("id").setTextContent(id);
		String toSave = DOMParser.parseDocument(document);
		StoreToDB.store(scientificPublicationCollectionId, id, toSave);
		return id;
	}

	public String update(String scientificPublication) throws Exception {
		Document document = DOMParser.buildDocument(scientificPublication, scientificPublicationSchemaPath);
		String id = document.getDocumentElement().getAttribute("id");
		String oldCoverLetterData = this.findOne(id);
		if (oldCoverLetterData == null) {
			throw new ResourceNotFoundException("Scientific publication with id: " + id);
		}
		this.delete(id);
		StoreToDB.store(scientificPublicationCollectionId, id, scientificPublication);
		return id;
	}

	public void delete(String id) throws Exception {
		String xpathExp = "/scientificPublication";
		long mods = UpdateDB.delete(scientificPublicationCollectionId, id, xpathExp);
		if (mods == 0) {
			throw new ResourceNotDeleted(String.format("Scientific publication with id %s", id));
		}
	}

}
