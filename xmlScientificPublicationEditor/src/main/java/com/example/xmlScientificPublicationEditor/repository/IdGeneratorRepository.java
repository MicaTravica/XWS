package com.example.xmlScientificPublicationEditor.repository;

import static com.example.xmlScientificPublicationEditor.util.template.XUpdateTemplate.TARGET_NAMESPACE;

import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.xs.XSModel;
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

import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;

@Repository
public class IdGeneratorRepository {

	public static String idGeneratorPath = "/db/sample/idGenerator";
	public static String idGeneratorSchemaPath = "src/main/resources/data/schemas/idGenerator.xsd";
	public static String idGeneratorId = "idgenerator";

	public String findOne(String element) throws Exception {
		String retVal = null;
		String xpathExp = "/";
		ResourceSet resultSet = RetriveFromDB.executeXPathExpression(idGeneratorPath, xpathExp, TARGET_NAMESPACE);
		if (resultSet == null) {
			StringWriter sw = new StringWriter();
			XSModel xsModel = new XSParser().parse(idGeneratorSchemaPath);
			XSInstance xsInstance = new XSInstance();
			QName rootElement = new QName("http://www.uns.ac.rs/Tim1", "idGenerator");
			XMLDocument sampleXml = new XMLDocument(new StreamResult(sw), true, 4, null);
			xsInstance.generate(xsModel, rootElement, sampleXml);
			String glt = sw.toString();
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

	public String save(String element, String glt) throws Exception {
		Document document = DOMParser.buildDocument(glt, idGeneratorSchemaPath);
		String id = document.getElementsByTagName("ns:" + element).item(0).getTextContent().trim();
		document.getElementsByTagName("ns:" + element).item(0).setTextContent((Integer.parseInt(id) + 1) + "");
		String toSave = DOMParser.parseDocument(document);
		StoreToDB.store(idGeneratorPath, idGeneratorId, toSave);
		return id;
	}

}
