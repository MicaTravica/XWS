package com.example.xmlScientificPublicationEditor.util.RDF;

import java.io.IOException;
import java.util.Iterator;

import com.example.xmlScientificPublicationEditor.util.AuthenticationUtilities;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.RDFNode;

public class GetRDF {
//import rs.ac.uns.ftn.examples.util.AuthenticationUtilities;
//import rs.ac.uns.ftn.examples.util.AuthenticationUtilities.ConnectionProperties;
//import rs.ac.uns.ftn.examples.util.SparqlUtil;

    /**
     *
     * [PRIMER 2]
     *
     * Primer demonstrira rad sa Apache Jena programskim API-em za izvrsavanje CRUD
     * operacija nad semantickim grafovima skladistenim u Apache Jena Fuseki RDF storu.
     *
     * - Preuzimanje tripleta iz imenovanog grafa
     * - Hendlanje rezultata kao SPARQL response XML
     * - Iteriranje po result setu
     *
     */
    private static final String TEST_NAMED_GRAPH_URI = "/example/scientificPublication/";

    public static String getRDF(String spId) throws  Exception{
        AuthenticationUtilities.ConnectionPropertiesFusekiJena conn = AuthenticationUtilities.loadPropertiesFusekiJena();

        String graphName = TEST_NAMED_GRAPH_URI + spId;
        // Querying the first named graph with a simple SPARQL query
        System.out.println("[INFO] Selecting the triples from the named graph \"" + TEST_NAMED_GRAPH_URI + "\".");
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + graphName, "?s ?p ?o");
        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);
        // Query the SPARQL endpoint, iterate over the result set...
        ResultSet results = query.execSelect();
        String varName;
        RDFNode varValue;
        StringBuilder sb = new StringBuilder();
        while(results.hasNext()) {

            // A single answer from a SELECT query
            QuerySolution querySolution = results.next() ;
            String line = querySolution.toString();
            String after = line.trim().replaceAll(" +", " ");
            sb.append(after);
            sb.append("\n");
//            Iterator<String> variableBindings = querySolution.varNames();
//
//                // Retrieve variable bindings
//            while (variableBindings.hasNext()) {
//
//                varName = variableBindings.next();
//                varValue = querySolution.get(varName);
//
//                System.out.println(varName + ": " + varValue);
//            }
//            System.out.println();
        }
        query.close();
        System.out.println("[INFO] End.");
        return sb.toString();
    }

}
