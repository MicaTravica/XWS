package com.example.xmlScientificPublicationEditor.util.fuseki;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

import com.example.xmlScientificPublicationEditor.util.rdf.AuthenticationUtilities;
import com.example.xmlScientificPublicationEditor.util.rdf.AuthenticationUtilities.ConnectionProperties;
import com.example.xmlScientificPublicationEditor.util.rdf.SparqlUtil;

/**
 * 
 * [PRIMER 1]
 * 
 * Primer demonstrira rad sa Apache Jena programskim API-em za izvrsavanje CRUD
 * operacija nad semantickim grafovima skladistenim u Apache Jena Fuseki RDF
 * storu.
 * 
 * - Kreiranje RDF modela - Učitavanje RDF/XML u model - Kreiranje/brisanje
 * imenovanog grafa - Populisanje grafa tripletima iz RDF modela
 * 
 */
public class Fuseki {

	private static final String PERSON_NAMED_GRAPH_URI = "/example/person/metadata";

	private static final String TEST_NAMED_GRAPH_URI = "/example/test/metadata";

	private static final String BOOKS_NAMED_GRAPH_URI = "/example/bookstore/metadata";

	private static final String PREDICATE_NAMESPACE = "http://www.ftn.uns.ac.rs/rdf/examples/predicate/";

	public static void main(String[] args) throws Exception {
		runWrite(AuthenticationUtilities.loadProperties());
		runRead(AuthenticationUtilities.loadProperties());
		runUpdate(AuthenticationUtilities.loadProperties());
	}

	public static void runWrite(ConnectionProperties conn) throws IOException {

		System.out.println("[INFO] Loading triples from an RDF/XML to a model...");

		// RDF triples which are to be loaded into the model
		String rdfFilePath = "data/rdf/person_metadata.rdf";

		// Creates a default model
		Model model = ModelFactory.createDefaultModel();
		model.read(rdfFilePath);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		model.write(out, SparqlUtil.NTRIPLES);

		System.out.println("[INFO] Rendering model as RDF/XML...");
		model.write(System.out, SparqlUtil.RDF_XML);

		// Delete all of the triples in all of the named graphs
		UpdateRequest request = UpdateFactory.create();
		request.add(SparqlUtil.dropAll());

		/*
		 * Create UpdateProcessor, an instance of execution of an UpdateRequest.
		 * UpdateProcessor sends update request to a remote SPARQL update service.
		 */
		UpdateProcessor processor = UpdateExecutionFactory.createRemote(request, conn.updateEndpoint);
		processor.execute();

		// Creating the first named graph and updating it with RDF data
		System.out.println("[INFO] Writing the triples to a named graph \"" + PERSON_NAMED_GRAPH_URI + "\".");
		String sparqlUpdate = SparqlUtil.insertData(conn.dataEndpoint + PERSON_NAMED_GRAPH_URI,
				new String(out.toByteArray()));
		System.out.println(sparqlUpdate);

		// UpdateRequest represents a unit of execution
		UpdateRequest update = UpdateFactory.create(sparqlUpdate);

		processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint);
		processor.execute();

		// Creating the second named graph and updating it with RDF data
		System.out.println("[INFO] Writing the triples to a named graph \"" + TEST_NAMED_GRAPH_URI + "\".");
		sparqlUpdate = SparqlUtil.insertData(conn.dataEndpoint + TEST_NAMED_GRAPH_URI, new String(out.toByteArray()));
		System.out.println(sparqlUpdate);

		update = UpdateFactory.create(sparqlUpdate);

		processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint);
		processor.execute();

		// Creating the third named graph and updating it with RDF data
		System.out.println("[INFO] Writing the triples to a named graph \"" + BOOKS_NAMED_GRAPH_URI + "\".");
		sparqlUpdate = SparqlUtil.insertData(conn.dataEndpoint + BOOKS_NAMED_GRAPH_URI, new String(out.toByteArray()));
		System.out.println(sparqlUpdate);

		update = UpdateFactory.create(sparqlUpdate);

		processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint);
		processor.execute();

		// Dropping the third graph...
		System.out.println("[INFO] Dropping the named graph \"" + BOOKS_NAMED_GRAPH_URI + "\"...");

		UpdateRequest dropRequest = UpdateFactory.create();

		sparqlUpdate = SparqlUtil.dropGraph(conn.dataEndpoint + BOOKS_NAMED_GRAPH_URI);
		System.out.println(sparqlUpdate);

		dropRequest.add(sparqlUpdate);

		processor = UpdateExecutionFactory.createRemote(dropRequest, conn.updateEndpoint);
		processor.execute();

		System.out.println("[INFO] End.");
	}

	public static void runRead(ConnectionProperties conn) throws IOException {

		// Querying the first named graph with a simple SPARQL query
		System.out.println("[INFO] Selecting the triples from the named graph \"" + TEST_NAMED_GRAPH_URI + "\".");
		String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + TEST_NAMED_GRAPH_URI, "?s ?p ?o");

		// Create a QueryExecution that will access a SPARQL service over HTTP
		QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

		// Query the SPARQL endpoint, iterate over the result set...
		ResultSet results = query.execSelect();

		String varName;
		RDFNode varValue;

		while (results.hasNext()) {

			// A single answer from a SELECT query
			QuerySolution querySolution = results.next();
			Iterator<String> variableBindings = querySolution.varNames();

			// Retrieve variable bindings
			while (variableBindings.hasNext()) {

				varName = variableBindings.next();
				varValue = querySolution.get(varName);

				System.out.println(varName + ": " + varValue);
			}
			System.out.println();
		}

		// Querying the other named graph
		System.out.println("[INFO] Selecting the triples from the named graph \"" + PERSON_NAMED_GRAPH_URI + "\".");
		sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + PERSON_NAMED_GRAPH_URI, "?s ?p ?o");

		// Create a QueryExecution that will access a SPARQL service over HTTP
		query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

		// Query the collection, dump output response as XML
		results = query.execSelect();

		ResultSetFormatter.outputAsXML(System.out, results);

		query.close();

		System.out.println("[INFO] End.");
	}

	public static void runUpdate(ConnectionProperties conn) throws IOException {

		System.out.println("[INFO] Loading UPDATE triples from an RDF/XML to a model...");

		// RDF triples which are to be loaded into the model
		String rdfFilePath = "data/rdf/person_update.rdf";

		// Creates a default model
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("pred", PREDICATE_NAMESPACE);

		// Loading the changes from an RDF/XML
		model.read(rdfFilePath);

		// Making the changes manually
		Resource resource = model.createResource("http://www.ftn.uns.ac.rs/rdf/examples/person/Petar_Petrovic");

		Property property1 = model.createProperty(PREDICATE_NAMESPACE, "livesIn");
		Literal literal1 = model.createLiteral("Novi Sad");

		Property property2 = model.createProperty(PREDICATE_NAMESPACE, "profession");
		Literal literal2 = model.createLiteral("lawyer");

		Property property3 = model.createProperty(PREDICATE_NAMESPACE, "hobby");
		Literal literal3 = model.createLiteral("hiking");

		// Adding the statements to the model
		Statement statement1 = model.createStatement(resource, property1, literal1);
		Statement statement2 = model.createStatement(resource, property2, literal2);
		Statement statement3 = model.createStatement(resource, property3, literal3);

		model.add(statement1);
		model.add(statement2);
		model.add(statement3);

		System.out.println("[INFO] Rendering the UPDATE model as RDF/XML...");
		model.write(System.out, SparqlUtil.RDF_XML);

		// Issuing the SPARQL update...
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		model.write(out, SparqlUtil.NTRIPLES);

		// Updating the named graph with the triples from RDF model
		System.out.println("[INFO] Inserting the triples to a named graph \"" + PERSON_NAMED_GRAPH_URI + "\".");
		String sparqlUpdate = SparqlUtil.insertData(conn.dataEndpoint + PERSON_NAMED_GRAPH_URI,
				new String(out.toByteArray()));
		System.out.println(sparqlUpdate);

		// UpdateRequest represents a unit of execution
		UpdateRequest update = UpdateFactory.create(sparqlUpdate);

		// UpdateProcessor sends update request to a remote SPARQL update service.
		UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint);
		processor.execute();

		// Issuing a simple SPARQL query to make sure the changes were made...
		System.out.println(
				"[INFO] Making sure the changes were made in the named graph \"" + PERSON_NAMED_GRAPH_URI + "\".");
		String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + PERSON_NAMED_GRAPH_URI, "?s ?p ?o");

		// Create a QueryExecution that will access a SPARQL service over HTTP
		QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

		// Query the collection, dump output response with the use of ResultSetFormatter
		ResultSet results = query.execSelect();
		ResultSetFormatter.out(System.out, results);

		query.close();

		// Issuing another SPARQL query to check the other named graph
		System.out.println("[INFO] Making sure no changes took place in \"" + TEST_NAMED_GRAPH_URI + "\".");
		sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + TEST_NAMED_GRAPH_URI, "?s ?p ?o");

		// Create a QueryExecution that will access a SPARQL service over HTTP
		query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

		// Query the collection, dump output response with the use of ResultSetFormatter
		results = query.execSelect();
		ResultSetFormatter.out(System.out, results);

		query.close();

		model.close();

		System.out.println("[INFO] End.");
	}
}
