package com.example.xmlScientificPublicationEditor.util.RDF;

import com.example.xmlScientificPublicationEditor.util.AuthenticationUtilities;
import com.example.xmlScientificPublicationEditor.util.AuthenticationUtilities.ConnectionPropertiesFusekiJena;

import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

/**
 * StoreToRDF
 */
public class UpdateRDF {

    public static void delete(String SPARQL_NAMED_GRAPH_URI) throws Exception
    {
        ConnectionPropertiesFusekiJena conn = AuthenticationUtilities.loadPropertiesFusekiJena();
        String spargDelete = SparqlUtil.dropGraph(conn.dataEndpoint +  SPARQL_NAMED_GRAPH_URI);
        
		// UpdateRequest represents a unit of execution
		UpdateRequest update = UpdateFactory.create(spargDelete);

		UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint);
		processor.execute();
    }

}