package com.example.xmlScientificPublicationEditor.util.XSLFOTransformer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import net.sf.saxon.TransformerFactoryImpl;

@Component
public class XSLFOTransformer {
	
	private FopFactory fopFactory;
	
	private TransformerFactory transformerFactory;
	
	public static final String FOP_XCONF = "src/main/java/com/example/xmlScientificPublicationEditor/util/XSLFOTransformer/fop.xconf";
	

	public XSLFOTransformer() throws SAXException, IOException {
		
		// Initialize FOP factory object
		fopFactory = FopFactory.newInstance(new File(FOP_XCONF));
		
		// Setup the XSLT transformer factory
		transformerFactory = new TransformerFactoryImpl();
	}

	public String applyTemplate(String source, String xsltTemplatePath) throws Exception
	{
		File tf = new File(xsltTemplatePath); // template file
		StringWriter out = new StringWriter(); // result
		StringReader src = new StringReader(source); // source string

		Transformer t = transformerFactory.newTransformer(new StreamSource(tf));

		Source s = new StreamSource(src);
		Result r = new StreamResult(out);
		t.transform(s,r);
		return out.toString();
	}

	public String generateHTML(String source, String xsltTemplatePath) throws Exception
	{

		File tf = new File(xsltTemplatePath); // template file
		StringWriter out = new StringWriter(); // result
		StringReader src = new StringReader(source); // source string

		Transformer t = transformerFactory.newTransformer(new StreamSource(tf));

		Source s = new StreamSource(src);
		Result r = new StreamResult(out);
		t.transform(s,r);
		return out.toString();
	}


	public ByteArrayOutputStream generatePDF(String sourceStr, String xslt_fo_TemplatePath) throws Exception {

		// xslt_fo_TemplatePath = XSL_FILE2;
		// File ssourceStr = new File(INPUT_FILE2);
		
		// Point to the XSL-FO file
		File xslFile = new File(xslt_fo_TemplatePath);

		// Create transformation source
		StreamSource transformSource = new StreamSource(xslFile);
		
		// Initialize the transformation subject
		StreamSource source = new StreamSource(new StringReader(sourceStr));
		// StreamSource source = new StreamSource(ssourceStr);

		// Initialize user agent needed for the transformation
		FOUserAgent userAgent = fopFactory.newFOUserAgent();
		
		// Create the output stream to store the results
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		// Initialize the XSL-FO transformer object
		Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);
		
		// Construct FOP instance with desired output format
		Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);

		// Resulting SAX events 
		Result res = new SAXResult(fop.getDefaultHandler());

		// Start XSLT transformation and FOP processing
		xslFoTransformer.transform(source, res);

		return outStream;
	}

}
