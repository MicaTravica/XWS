package com.example.xmlScientificPublicationEditor.repository;

import static com.example.xmlScientificPublicationEditor.util.template.XUpdateTemplate.TARGET_NAMESPACE;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
import com.example.xmlScientificPublicationEditor.util.RDF.MetadataExtractor;
import com.example.xmlScientificPublicationEditor.util.RDF.StoreToRDF;
import com.example.xmlScientificPublicationEditor.util.RDF.UpdateRDF;
import com.example.xmlScientificPublicationEditor.util.XSLFOTransformer.XSLFOTransformer;
import com.example.xmlScientificPublicationEditor.util.existAPI.RetriveFromDB;
import com.example.xmlScientificPublicationEditor.util.existAPI.StoreToDB;
import com.example.xmlScientificPublicationEditor.util.existAPI.UpdateDB;

@Repository
public class ScientificPublicationRepository {

	@Autowired
	private IdGeneratorService idGeneratorService;
	
	@Autowired
	private XSLFOTransformer xslFoTransformer;
	
	@Autowired
	private MetadataExtractor metadataExtractor;

	public static String scientificPublicationCollectionId = "/db/sample/scientificPublication";
	public static String scientificPublicationSchemaPath = "src/main/resources/data/schemas/scientificPublication.xsd";
	public static String ScientificPublicationXSLPath = "src/main/resources/data/xslt/scientificPublication.xsl";
	public static String ScientificPublicationRDFPath = "src/main/resources/data/xmlToRDFa/scientificPublicationToRDFa.xsl";
	public static String ScientificPublicationXSL_FO_PATH = "src/main/resources/data/xsl-fo/scientificPublication_fo.xsl";
	public static String ScientificPublicationXSL_PATH_NO_AUTHOR = "src/main/resources/data/xslt/scientificPublicationIDName.xsl";
	public static String DATA_PROCESS_XSL = "src/main/resources/data/xslt/scientificPublicationIdName.xsl";
	public static String SP_NAMED_GRAPH_URI_PREFIX = "/example/scientificPublication/";
	public static String EL_ROOT = "ns:scientificPublication";
	public static String DATE_METADATA = "ns:dateMetaData";
	public static String CREATED_AT = "ns:created_at";
	public static String ACCEPTED_AT = "ns:accepted_at";
	
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

	public Document save(String scientificPublication) throws Exception {
		Document document = DOMParser.buildDocument(scientificPublication, scientificPublicationSchemaPath);

		String id = "sp" + idGeneratorService.getId("scientificPublication");
		document.getDocumentElement().getAttributes().getNamedItem("id").setTextContent(id);

		generateIDs(document, id);
		
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		NodeList metadata = document.getDocumentElement().getElementsByTagName(DATE_METADATA);
		if(metadata.getLength() > 0) {
			document.getDocumentElement().removeChild(metadata.item(0));
		}
		Element dateMetadata = document.createElement(DATE_METADATA);
		Element createdAt = document.createElement(CREATED_AT);
		createdAt.appendChild(document.createTextNode(date));

		dateMetadata.appendChild(createdAt);
		document.getDocumentElement().appendChild(dateMetadata);
		
		document.getDocumentElement().getAttributes().getNamedItem("version").setTextContent("1");

		String toSave = DOMParser.parseDocument(document, scientificPublicationSchemaPath);
		StoreToDB.store(scientificPublicationCollectionId, id, toSave);

		toSave = xslFoTransformer.generateHTML(toSave, ScientificPublicationRDFPath);
		saveMetadata(metadataExtractor.extractMetadataXML(toSave), id);
		
		return document;
	}

	private void generateIDs(Document document, String id) throws Exception {

		NodeList references = document.getElementsByTagName(IdGeneratorServiceImpl.REFERENCE);
		Set<String> ids = new HashSet<>();
		Node idref;
		for (int i = 0; i < references.getLength(); ++i) {
			if ((idref = references.item(i).getAttributes().getNamedItem("id")) != null) {
				if (ids.contains(idref.getTextContent())) {
					throw new Exception("Must change id on references");
				}
				ids.add(idref.getTextContent());
			} else {
				throw new Exception("Reference must have id");
			}
		}

		NodeList qoutes = document.getElementsByTagName(IdGeneratorServiceImpl.QUOTE);
		for (int i = 0; i < qoutes.getLength(); ++i) {
			if ((idref = qoutes.item(i).getAttributes().getNamedItem("ref")) != null) {
				if (!ids.contains(idref.getTextContent())) {
					throw new Exception("Qoute must have ref value that is id of reference");
				}
			} else {
				throw new Exception("Qoute must have ref");
			}
		}

		Node caption = document.getElementsByTagName(IdGeneratorServiceImpl.CAPTION).item(0);
		idGeneratorService.generateElementId(caption, id + "_caption", IdGeneratorServiceImpl.CAPTION);

		NodeList authors = document.getElementsByTagName(IdGeneratorServiceImpl.AUTHOR);
		for (int i = 0; i < authors.getLength(); ++i) {
			idGeneratorService.generateElementId(authors.item(i), id + "_author" + (i + 1),
					IdGeneratorServiceImpl.AUTHOR);
		}

		NodeList institutions = document.getElementsByTagName(IdGeneratorServiceImpl.INSTITUTION);
		for (int i = 0; i < institutions.getLength(); ++i) {
			idGeneratorService.generateElementId(institutions.item(i), id + "_institution" + (i + 1),
					IdGeneratorServiceImpl.INSTITUTION);
		}

		NodeList address = document.getElementsByTagName(IdGeneratorServiceImpl.ADDRESS);
		for (int i = 0; i < address.getLength(); ++i) {
			idGeneratorService.generateElementId(address.item(i), id + "_address" + (i + 1),
					IdGeneratorServiceImpl.ADDRESS);
		}

		Node abstrac = document.getElementsByTagName(IdGeneratorServiceImpl.ABSTRACT).item(0);
		idGeneratorService.generateElementId(abstrac, id + "_abstract", IdGeneratorServiceImpl.ABSTRACT);

		Element abs = (Element) abstrac;
		NodeList absParagraphs = abs.getElementsByTagName(IdGeneratorServiceImpl.PARAGRAPH);
		for (int i = 0; i < absParagraphs.getLength(); ++i) {
			idGeneratorService.generateParagraphId(absParagraphs.item(i), id + "_abstract_paragraph" + (i + 1));
		}

		NodeList chapters = document.getElementsByTagName(IdGeneratorServiceImpl.CHAPTER);
		for (int i = 0; i < chapters.getLength(); ++i) {
			idGeneratorService.generateChapterId(chapters.item(i), id + "_chapter" + (i + 1));
		}
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

		String toSave = xslFoTransformer.generateHTML(scientificPublication, ScientificPublicationRDFPath);
		updateMetadata(metadataExtractor.extractMetadataXML(toSave), id);
		return id;
	}

	public void delete(String id) throws Exception {
		String xpathExp = "/scientificPublication";
		long mods = UpdateDB.delete(scientificPublicationCollectionId, id, xpathExp);
		if (mods == 0) {
			throw new ResourceNotDeleted(String.format("Scientific publication with id %s", id));
		}
	}

	public void setLastVersion(String scID, String lastVersion) throws Exception {
		String sp = findOne(scID);
		Document doc = DOMParser.buildDocument(sp, scientificPublicationSchemaPath);
		doc.getDocumentElement().getAttributes().getNamedItem("version").setTextContent(lastVersion);
		update(DOMParser.parseDocument(doc, scientificPublicationSchemaPath));
	}

	public void addAcceptedAt(String idSp) throws Exception {
		String sp = findOne(idSp);
		Document document = DOMParser.buildDocument(sp, scientificPublicationSchemaPath);
		
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		NodeList metadata = document.getDocumentElement().getElementsByTagName(DATE_METADATA);
		Element dateMetadata = (Element) metadata.item(0);
		Element acceptedAt = document.createElement(ACCEPTED_AT);
		
		acceptedAt.appendChild(document.createTextNode(date));
		dateMetadata.appendChild(acceptedAt);
		
		update(DOMParser.parseDocument(document, scientificPublicationSchemaPath));	
	}

	public String search(String param, String email) throws Exception {
		String retVal = "";
		String xQueryPath = "src/main/resources/data/xQuery/search.txt";

		HashMap<String, String> params = new HashMap<>();
		params.put("AUTH", email); 
		params.put("PARAM", param);

		ResourceSet resultSet = RetriveFromDB.executeXQuery(
			scientificPublicationCollectionId, xQueryPath, params, TARGET_NAMESPACE);
		if (resultSet == null || (resultSet.getSize() == 0 )) {
			return retVal;
		}
		ResourceIterator i = resultSet.getIterator();
		XMLResource res = null;
		while (i.hasMoreResources()) {
			try {
				res = (XMLResource) i.nextResource();
				retVal += res.getContent().toString();
			} finally {
				// don't forget to cleanup resources
				try {
					if(res != null) {
						((EXistResource) res).freeResources();
					}
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
		return retVal;
	}


	public String saveComments(Document doc, Element com) throws Exception {
		String id = "sp" + idGeneratorService.getId("scientificPublication");
		String spId = doc.getDocumentElement().getAttributes().getNamedItem("id").getTextContent();

		Element commentsElement = doc.createElement(IdGeneratorServiceImpl.COMMENTS);
		NodeList comments = com.getElementsByTagName(IdGeneratorServiceImpl.COMMENT);
		for (int i = 0; i < comments.getLength(); ++i) {
			Element commentEl = doc.createElement(IdGeneratorServiceImpl.COMMENT);
			idGeneratorService.generateElementId(commentEl, id + "_comment" + (i + 1), IdGeneratorServiceImpl.COMMENT);
			String ref = comments.item(i).getAttributes().getNamedItem("ref").getTextContent();

			if (ref.equals(spId)) {
				ref = id;
			} else {
				String xpathExp = "//scientificPublication[@id='" + spId + "']//*[@id='" + ref + "']";
				ResourceSet resultSet = RetriveFromDB.executeXPathExpression(scientificPublicationCollectionId,
						xpathExp, TARGET_NAMESPACE);
				if (!resultSet.getIterator().hasMoreResources()) {
					throw new Exception("You must use for ref comment id that exist in file!");
				}
			}
			commentEl.appendChild(doc.createTextNode(comments.item(i).getTextContent()));
			commentEl.setAttribute("ref", ref);
			commentsElement.appendChild(commentEl);
		}
		doc.getDocumentElement().appendChild(commentsElement);
		doc.getDocumentElement().getAttributes().getNamedItem("id").setTextContent(id);
		String toSave = DOMParser.parseDocument(doc, scientificPublicationSchemaPath);
		StoreToDB.store(scientificPublicationCollectionId, id, toSave);
		return id;
	}

	public void saveMetadata(StringWriter metadata, String id) throws Exception {
		StoreToRDF.store(metadata, SP_NAMED_GRAPH_URI_PREFIX + id);
	}

	public void deleteMetadata(String id) throws Exception {
		UpdateRDF.delete(SP_NAMED_GRAPH_URI_PREFIX + id);
	}
	
	public void updateMetadata(StringWriter metadata, String id) throws Exception {
		String url = SP_NAMED_GRAPH_URI_PREFIX + id;
		deleteMetadata(id);
		StoreToRDF.store(metadata, url);
	}
}
