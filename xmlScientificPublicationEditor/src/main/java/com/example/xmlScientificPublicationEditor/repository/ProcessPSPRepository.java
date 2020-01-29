package com.example.xmlScientificPublicationEditor.repository;

import static com.example.xmlScientificPublicationEditor.util.template.XUpdateTemplate.TARGET_NAMESPACE;

import java.util.ArrayList;

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
import com.example.xmlScientificPublicationEditor.model.ProcessState;
import com.example.xmlScientificPublicationEditor.service.IdGeneratorService;
import com.example.xmlScientificPublicationEditor.util.DOMParser.DOMParser;
import com.example.xmlScientificPublicationEditor.util.existAPI.RetriveFromDB;
import com.example.xmlScientificPublicationEditor.util.existAPI.StoreToDB;
import com.example.xmlScientificPublicationEditor.util.existAPI.UpdateDB;

@Repository
public class ProcessPSPRepository {

	

	@Autowired
	private IdGeneratorService idGeneratorService;

	public static String ScientificPublicationFiled = "ns:scientificPublication";
	public static String RedactorFiled = "ns:idRedactor";
	public static String CoverLetterFiled = "ns:coverLetter";
	public static String PROCESS_ID = "id";
	public static String PROCESS_ROOT = "ns:processPSP";
	public static final String PROCESS_LAST_VERSION = "lastVersion";
	public static final String PROCESS_STATE = "state";
	public static final String PROCESS_AUTHOR_SP = "authorEmail";
	

	public static final String ProcessPSPXSLSPId = "src/main/resources/data/xslt/processPSPgetLastSCId.xsl";

    public static String ProcessPSPTemplatePath = "src/main/resources/data/xml/processPSPTemplate.xml";
	public static String ProcessPSPCollectionId = "/db/sample/processPSP";
	public static String ProcessPSPSchemaPath = "src/main/resources/data/schemas/processPSP.xsd";

	public static String ProcessPSPXSLForReview = "src/main/resources/data/xslt/processforRevision.xsl";


	public String findOneByScientificPublicationID(String id) throws Exception {
		String retVal = null;
		String xpathExp = "//ns:processPSP[ns:scientificPublication=\"" + id + "\"]";
		ResourceSet resultSet = RetriveFromDB.executeXPathExpression(ProcessPSPCollectionId, xpathExp,
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

    public Document setRedactor(Document process, String redactorId) {
		process.getElementsByTagName(RedactorFiled).
			item(0).setTextContent(redactorId);
		return process;
	}
	
	public String getProceesId(Document process)
	{
		Element root = process.getDocumentElement();
		return root.getAttribute(PROCESS_ID);
	}

	public void setProceesId(Document process, String id)
	{
		process.getElementsByTagName(PROCESS_ROOT).item(0).getAttributes().getNamedItem(PROCESS_ID).setTextContent(id);
	}

	// TODO: kako ce front znati koji id je slobodan za Notification????
	public Document save(Document process) throws Exception {
		String id = idGeneratorService.getId("process");
		this.setProceesId(process, id);
		String processS = DOMParser.parseDocument(process,ProcessPSPSchemaPath);
		StoreToDB.store(ProcessPSPCollectionId, id, processS);
		return process;
	}

	public Document update(Document process) throws Exception
	{
		String processId = this.getProceesId(process);
		this.delete(processId);
		String processS = DOMParser.parseDocument(process, ProcessPSPSchemaPath);
		StoreToDB.store(ProcessPSPCollectionId, processId, processS);
		return process;
	}

	public void delete(String id) throws Exception {
		String xpathExp = "/ns:processPSP";
		long mods = UpdateDB.delete(ProcessPSPCollectionId, id, xpathExp);
		if (mods == 0) {
			throw new ResourceNotDeleted(String.format("processPSP with documentId %s", id));
		}
	}


	// SET METHODS
    public Document setScientificPublicationId(Document process, String scientificPublciationID) throws Exception
    {
		Node lastVersion = this.getLastVersion(process);
		NodeList list = lastVersion.getChildNodes();
		for(int i=0; i < list.getLength(); i++){
			Node n = list.item(i);
			if(n.getNodeName().equals(ScientificPublicationFiled)){
				n.setTextContent(scientificPublciationID);
			}
		}
        return process;
	}
	
	public Document setCoverLetter(Document process, String coverLetterId) {
		process.getElementsByTagName(CoverLetterFiled).
				item(0).setTextContent(coverLetterId);
        return process;
	}

	// vrati deo dokumenta process tj vrati deo koji cini poslednja verzija
	public Node getLastVersion(Document process) {
		
		String lastVersionNumber = process.getElementsByTagName(PROCESS_ROOT).item(0).
			getAttributes().getNamedItem(PROCESS_LAST_VERSION).getTextContent();
		
		NodeList l = process.getElementsByTagName(PROCESS_ROOT).item(0).getChildNodes();
		for(int i = 0; i < l.getLength(); i++){
			Node n = l.item(i);
			if( n.getNodeName().equals("ns:versions")) {
				NodeList versions =  n.getChildNodes();
				if(versions.getLength() == 1)
				{
					return versions.item(0);
				}
				for(int j=0; j < versions.getLength();j++){
					Node version = versions.item(j);
					if( version.getNodeName().equals("ns:version")) {
						String versionNumber = version.getAttributes().getNamedItem("version").getTextContent();
						if(versionNumber.equals(lastVersionNumber)) {
							return version;
						}
					}
				}
			}
		}
		return null;
	}

	public ArrayList<String> findProcessByState(ProcessState state) throws Exception
	{
		ArrayList<String> retVal = new ArrayList<>();
		String xpathExp = "//processPSP[@state=\"" + state.getAction() + "\"]";
		ResourceSet resultSet = RetriveFromDB.executeXPathExpression(ProcessPSPCollectionId, xpathExp,
				TARGET_NAMESPACE);
		if (resultSet == null) {
			return retVal;
		}
		ResourceIterator i = resultSet.getIterator();
		XMLResource res = null;
		while (i.hasMoreResources()) {
			try {
				res = (XMLResource) i.nextResource();
				retVal.add(res.getContent().toString());
			} finally {
				// don't forget to cleanup resources
				try {
					((EXistResource) res).freeResources();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
		return retVal;
	}

	public ArrayList<String> findForPublishing()  throws Exception {
		ArrayList<String> retVal = findProcessByState(ProcessState.FOR_REVIEW);
		findProcessByState(ProcessState.SCORED)
			.forEach(p -> { retVal.add(p);} );
		return retVal;
	}

	public ArrayList<String> findByOwnerEmail(String email) throws Exception{
		ArrayList<String> retVal = new ArrayList<>();
		String xpathExp = "//processPSP[@authorEmail=\"" + email + "\"]";
		ResourceSet resultSet = RetriveFromDB.executeXPathExpression(ProcessPSPCollectionId, xpathExp,
				TARGET_NAMESPACE);
		if (resultSet == null) {
			return retVal;
		}
		ResourceIterator i = resultSet.getIterator();
		XMLResource res = null;
		while (i.hasMoreResources()) {
			try {
				res = (XMLResource) i.nextResource();
				retVal.add(res.getContent().toString());
			} finally {
				// don't forget to cleanup resources
				try {
					((EXistResource) res).freeResources();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
		return retVal;

    }

}