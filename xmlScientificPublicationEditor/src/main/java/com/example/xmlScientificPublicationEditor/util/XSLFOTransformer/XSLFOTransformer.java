package com.example.xmlScientificPublicationEditor.util.XSLFOTransformer;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.xml.sax.SAXException;

import net.sf.saxon.TransformerFactoryImpl;

public class XSLFOTransformer {
	
	private FopFactory fopFactory;
	
	private TransformerFactory transformerFactory;
	
	public static final String INPUT_FILE = "data/xsl-fo/bookstore.xml";
	
	public static final String XSL_FILE = "data/xsl-fo/bookstore_fo.xsl";
	
	public static final String OUTPUT_FILE = "gen/fo/bookstore.pdf";
	
	public static final String FOP_XCONF = "src/main/java/com/example/xmlScientificPublicationEditor/util/XSLFOTransformer/fop.xconf";
	
	public XSLFOTransformer() throws SAXException, IOException {
		
		// Initialize FOP factory object
		System.out.println(new File("src/main/java/com/example/xmlScientificPublicationEditor/fop.xconf").getAbsolutePath());
		fopFactory = FopFactory.newInstance(new File(FOP_XCONF));
		
		// Setup the XSLT transformer factory
		transformerFactory = new TransformerFactoryImpl();
	}

	private void generatePDF() throws Exception {

		System.out.println("[INFO] " + XSLFOTransformer.class.getSimpleName());
		
		// Point to the XSL-FO file
		File xslFile = new File(XSL_FILE);

		// Create transformation source
		StreamSource transformSource = new StreamSource(xslFile);
		
		// Initialize the transformation subject
		StreamSource source = new StreamSource(new File(INPUT_FILE));

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

		// Generate PDF file
		File pdfFile = new File(OUTPUT_FILE);
		if (!pdfFile.getParentFile().exists()) {
			System.out.println("[INFO] A new directory is created: " + pdfFile.getParentFile().getAbsolutePath() + ".");
			pdfFile.getParentFile().mkdir();
		}
		
		OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));
		out.write(outStream.toByteArray());

		System.out.println("[INFO] File \"" + pdfFile.getCanonicalPath() + "\" generated successfully.");
		out.close();
		
		System.out.println("[INFO] End.");

	}
	
	public static void main(String[] args) throws Exception {
		new XSLFOTransformer().generatePDF();
	}

}
