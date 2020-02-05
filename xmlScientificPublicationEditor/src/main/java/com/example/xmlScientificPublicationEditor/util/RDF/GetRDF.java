package com.example.xmlScientificPublicationEditor.util.RDF;

import static com.example.xmlScientificPublicationEditor.util.MyFile.readFile;

import java.util.HashSet;
import java.util.Iterator;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;

import com.example.xmlScientificPublicationEditor.util.AuthenticationUtilities;

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
    private static final String QUERY_PATH = "src/main/resources/data/MetadataSearch/metadataSearch.rq";

    public static HashSet<String> getSPbyMetadata(String param) throws  Exception{
        HashSet<String> retVal = new HashSet<>();
        AuthenticationUtilities.ConnectionPropertiesFusekiJena conn = AuthenticationUtilities.loadPropertiesFusekiJena();
        String sparqlQuery = String.format(readFile(QUERY_PATH), param);
        System.out.println(sparqlQuery);
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);
        ResultSet results = query.execSelect();
        String varName;
        RDFNode varValue;
        StringBuilder sb = new StringBuilder();
        while(results.hasNext()) {
            // A single answer from a SELECT query
            QuerySolution querySolution = results.next() ;
            Iterator<String> variableBindings = querySolution.varNames();
                // Retrieve variable bindings
            while (variableBindings.hasNext()) {
                varName = variableBindings.next();
                if(varName.equals("sp")) {
                    varValue = querySolution.get(varName);
                    String[] url = varValue.toString().split("/");
                    retVal.add(url[url.length - 1]);
                }
            }
        }
        query.close();
        System.out.println("[INFO] End.");
        return retVal;
    }

}
