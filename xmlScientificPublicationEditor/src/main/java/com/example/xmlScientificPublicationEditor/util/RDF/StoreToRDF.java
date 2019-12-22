package com.example.xmlScientificPublicationEditor.util.RDF;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.example.xmlScientificPublicationEditor.util.AuthenticationUtilities;
import com.example.xmlScientificPublicationEditor.util.AuthenticationUtilities.ConnectionPropertiesFusekiJena;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

/**
 * StoreToRDF
 */
public  class StoreToRDF {

    public static String rdfFilePath = "src/main/resources/data/rdfa/output.rdf";

    public static void store(StringWriter rdfData, String SPARQL_NAMED_GRAPH_URI) throws Exception
    {

        PrintWriter prw = new PrintWriter(new File(rdfFilePath));
        prw.write(rdfData.toString());
        prw.flush();

        ConnectionPropertiesFusekiJena conn = AuthenticationUtilities.loadPropertiesFusekiJena();
        
        Model model = ModelFactory.createDefaultModel();
        model.read(rdfFilePath);
        
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		model.write(out, SparqlUtil.NTRIPLES);
		
		System.out.println("[INFO] Extracted metadata as RDF/XML...");
		model.write(System.out, SparqlUtil.RDF_XML);

		
		// Writing the named graph
		System.out.println("[INFO] Populating named graph \"" + SPARQL_NAMED_GRAPH_URI + "\" with extracted metadata.");
		String sparqlUpdate = SparqlUtil.insertData(conn.dataEndpoint + SPARQL_NAMED_GRAPH_URI, new String(out.toByteArray()));
		System.out.println(sparqlUpdate);
		
		// UpdateRequest represents a unit of execution
		UpdateRequest update = UpdateFactory.create(sparqlUpdate);

		UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint);
		processor.execute();
		
    }
}