package com.example.xmlScientificPublicationEditor.repository;

import static com.example.xmlScientificPublicationEditor.util.template.XUpdateTemplate.TARGET_NAMESPACE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.xmlScientificPublicationEditor.util.DOMParser.DOMParser;
import com.example.xmlScientificPublicationEditor.util.existAPI.RetriveFromDB;
import com.example.xmlScientificPublicationEditor.util.existAPI.StoreToDB;

@Repository
public class IdGeneratorRepository {

	public static String idGeneratorPath = "/db/sample/idGenerator";
	public static String idGeneratorSchemaPath = "src/main/resources/data/schemas/idGenerator.xsd";
	public static String idGeneratorId = "idgenerator";
	public static String idGeneratorTamplate = "src/main/resources/data/xml/idGeneratorTemplate.xml";

	public String findOne(String element) throws Exception {
		String retVal = null;
		String xpathExp = "/";
		ResourceSet resultSet = RetriveFromDB.executeXPathExpression(idGeneratorPath, xpathExp, TARGET_NAMESPACE);
		if (resultSet == null) {
			String glt = initIdGenerator();
			return save(element, glt);
		}

		ResourceIterator i = resultSet.getIterator();
		XMLResource res = null;
		while (i.hasMoreResources()) {
			try {
				res = (XMLResource) i.nextResource();
				retVal = res.getContent().toString();
				return save(element, retVal);
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

	public String initIdGenerator() throws Exception {
		File file = new File(idGeneratorTamplate);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String glt = "";
		String st;
		while ((st = br.readLine()) != null)
			glt += st;
		br.close();
		return glt;
	}

	public String save(String element, String glt) throws Exception {
		Document document = DOMParser.buildDocument(glt, idGeneratorSchemaPath);
		String id = document.getElementsByTagName(element).item(0).getTextContent().trim();
		document.getElementsByTagName(element).item(0).setTextContent((Integer.parseInt(id) + 1) + "");
		String toSave = DOMParser.parseDocument(document);
		StoreToDB.store(idGeneratorPath, idGeneratorId, toSave);
		return id;
	}

}
