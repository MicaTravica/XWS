package com.example.xmlScientificPublicationEditor.util.DOMParser;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 
 * Kao rezultat parsiranja generiše se objektni reprezent XML dokumenta u vidu
 * DOM stabla njegovih elemenata. 
 * 
 * Primer demonstrira upotrebu metoda API-ja za potrebe pristupanja pojedinim
 * elementima DOM stabla. 
 * 
 * Primer omogućuje validaciju XML fajla u odnosu na XML šemu, koja se svodi 
 * na postavljanje svojstava factory objekta uz opcionu implementaciju error 
 * handling metoda.
 * 
 * NAPOMENA: za potrebe testiranja validacije dodati nepostojeći element ili 
 * atribut (npr. "test") u XML fajl koji se parsira.
 * 
 */
public class DOMParser {
	
	/**
	 * Generates document object model for a given XML file.
	 * 
	 * @param filePath XML document file path
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	public static Document buildDocument(String xmlString, String schemaPath) throws SAXException, ParserConfigurationException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document document;
		factory.setValidating(false);
		
		factory.setNamespaceAware(true);
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);
		File file = new File(schemaPath);

        // create schema
        String constant = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory xsdFactory = SchemaFactory.newInstance(constant);
        Schema schema = null;
		schema = xsdFactory.newSchema(file);
		
		
		factory.setSchema(schema);
			
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		/* Postavlja error handler. */
		builder.setErrorHandler(new ErrorHandlerImpl());
		
		document = builder.parse(new InputSource(new StringReader(xmlString)));

		/* Detektuju eventualne greske */
		if (document != null)
			System.out.println("[INFO] File parsed with no errors.");
		else
			System.out.println("[WARN] Document is null.");

		return document;
	}


	public static Document buildDocumentWithOutSchema(String xmlString) throws Exception
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document document;
		factory.setValidating(false);
		
		factory.setNamespaceAware(true);
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);
			
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		/* Postavlja error handler. */
		builder.setErrorHandler(new ErrorHandlerImpl());
		
		document = builder.parse(new InputSource(new StringReader(xmlString)));

		/* Detektuju eventualne greske */
		if (document != null)
			System.out.println("[INFO] File parsed with no errors.");
		else
			System.out.println("[WARN] Document is null.");
			
		return document;
	}

	public static String parseDocument(Document document) throws Exception{

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);

		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.setErrorHandler(new ErrorHandlerImpl());
		DOMSource domSource = new DOMSource(document);		

		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.transform(domSource, result);
		return writer.toString();
	}


}
