package com.example.xmlScientificPublicationEditor.serviceImpl;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.xs.XSModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.model.ProcessState;
import com.example.xmlScientificPublicationEditor.model.authPerson.TRole;
import com.example.xmlScientificPublicationEditor.model.person.TPersons;
import com.example.xmlScientificPublicationEditor.repository.ProcessPSPRepository;
import com.example.xmlScientificPublicationEditor.repository.ScientificPublicationRepository;
import com.example.xmlScientificPublicationEditor.service.PersonService;
import com.example.xmlScientificPublicationEditor.service.ProcessPSPService;
import com.example.xmlScientificPublicationEditor.service.ScientificPublicationService;
import com.example.xmlScientificPublicationEditor.util.DOMParser.DOMParser;
import com.example.xmlScientificPublicationEditor.util.XSLFOTransformer.XSLFOTransformer;

import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;

@Service
public class ProcessPSPServiceImpl implements ProcessPSPService {

    @Autowired
    private ScientificPublicationService scService;

    @Autowired
    private ProcessPSPRepository processPSPRepo;

    @Autowired
    private PersonService personService;

    @Autowired
	private XSLFOTransformer xslFoTransformer;

    @Override
    public String create(
            String scientificPublicationId, String authorEmail,
            String scientificPublicationName) throws Exception {
        String doc = this.generateProcessXMLTemplate();
        Document newProcess = DOMParser.buildDocumentWithOutSchema(doc);
        String redactorId = this.personService.findUsersByRole(TRole.ROLE_REDACTOR).get(0);
        newProcess = processPSPRepo.setRedactor(newProcess, redactorId);
        processPSPRepo.setScientificPublicationId(newProcess, scientificPublicationId);
        processPSPRepo.setScientificPublicationName(newProcess,scientificPublicationName);
        this.setProcessPSPState(newProcess, ProcessState.IN_PROGRESS);
        this.setProcessPSPCSAuthor(newProcess, authorEmail);
        processPSPRepo.save(newProcess);
        return newProcess.getDocumentElement().getAttribute("id");
    }

    @Override
    public Document findOneById(String processId) throws Exception {
        Document process = processPSPRepo.findOneById(processId);
        if(process == null) {
            throw new Exception("process not found with id");
        }
        return process;
    }

    @Override
    public String findOneByScientificPublicationID(String scientificPublicationId) throws Exception {
        String procesStr = processPSPRepo.findOneByScientificPublicationID(scientificPublicationId);

        if (procesStr == null) {
            throw new ResourceNotFoundException("ProcessPSP");
        }
        return procesStr;
    }

    
    @Override
    public String setCoverLetter(String processId, String coverLetterId) throws Exception {
        Document process = this.findOneById(processId);
        process = processPSPRepo.setCoverLetter(process, coverLetterId);
        process = this.setProcessPSPState(process, ProcessState.FOR_REVIEW);
        processPSPRepo.update(process);
        return null;
    }

    @Override
    public String setRewiever(String personId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String setReview(String processId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(String processId) throws Exception {
        processPSPRepo.delete(processId);
    }

    @Override
    public String generateProcessXMLTemplate() throws Exception {
        StringWriter sw = new StringWriter();
        XSModel xsModel = new XSParser().parse(ProcessPSPRepository.ProcessPSPSchemaPath);
        XSInstance xsInstance = new XSInstance();
        xsInstance.maximumElementsGenerated = 1;
        xsInstance.maximumListItemsGenerated = 0;
        xsInstance.maximumRecursionDepth = 0;
        xsInstance.generateOptionalAttributes = Boolean.TRUE;
        xsInstance.generateDefaultAttributes = Boolean.TRUE;
        xsInstance.generateOptionalElements = Boolean.TRUE; // null means rando
        QName rootElement = new QName("http://www.uns.ac.rs/Tim1", "processPSP");
        XMLDocument sampleXml = new XMLDocument(new StreamResult(sw), true, 4, null);
        xsInstance.generate(xsModel, rootElement, sampleXml);

        System.out.println(sw.toString());
        return sw.toString();
    }

    @Override
    public Document setLastVersionNumber(Document process, String version) throws Exception {
        process.getElementsByTagName(ProcessPSPRepository.PROCESS_ROOT).item(0)
            .getAttributes().getNamedItem(ProcessPSPRepository.PROCESS_LAST_VERSION)
                .setTextContent(version);
        return process;
    }

    @Override
    public Node getLastVersion(String processId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Document setProcessPSPState(Document process, ProcessState processState) throws Exception {
        process.getElementsByTagName(ProcessPSPRepository.PROCESS_ROOT).item(0)
            .getAttributes().getNamedItem(ProcessPSPRepository.PROCESS_STATE)
                .setTextContent(processState.getAction());
        return process;
    }

    @Override
    public String getProcessPSPState(Document document) throws Exception {
    	return document.getElementsByTagName(ProcessPSPRepository.PROCESS_ROOT).item(0)
        .getAttributes().getNamedItem(ProcessPSPRepository.PROCESS_STATE).getTextContent();
    }

    @Override
    public String getLastVersionNumber(String processId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Document setProcessPSPCSAuthor(Document process, String email) {
        process.getElementsByTagName(ProcessPSPRepository.PROCESS_ROOT).item(0)
        .getAttributes().getNamedItem(ProcessPSPRepository.PROCESS_AUTHOR_SP)
            .setTextContent(email);
    return process;
    }

    @Override
    public String getProcessPSPCSAuthor(Document process) {
        return null;
    }






    @Override
    public String findForPublishing() throws Exception{
        StringBuilder retVal = new StringBuilder();
        retVal.append("<processes>");
        ArrayList<String> forPublishing = this.processPSPRepo.findForPublishing();
        for(String p: forPublishing) {

            String lastSPId =  xslFoTransformer.applyTemplate(p, ProcessPSPRepository.ProcessPSPXSLSPId );
            String sp = scService.findOne(lastSPId);

            String spData = xslFoTransformer.applyTemplate(sp, ScientificPublicationRepository.DATA_PROCESS_XSL );

            String transformed_p =
                xslFoTransformer.applyTemplate(p, ProcessPSPRepository.ProcessPSPXSLForReview );
            
            String[] s = transformed_p.split("</processPSP>");
            transformed_p =  s[0] + spData + "\n</processPSP>";
            
            retVal.append(transformed_p);
            retVal.append("\n");
        }
        retVal.append("</processes>");
        return retVal.toString();
    }

	@Override
	public void addReviewers(TPersons reviewers, String processId) throws Exception {
		Document document = findOneById(processId);
		setProcessPSPState(document, ProcessState.WAITING_FOR_REVIEWERS);
		processPSPRepo.addReviewAssigment(document, reviewers);
	}
    
    @Override
    public String findMyPublications(String email) throws Exception {
        StringBuilder retVal = new StringBuilder();
        retVal.append("<processes>");
        ArrayList<String> myPublications = this.processPSPRepo.findByOwnerEmail(email);
        for(String p: myPublications) {

            String lastSPId =  xslFoTransformer.applyTemplate(p, ProcessPSPRepository.ProcessPSPXSLSPId );
            String sp = scService.findOne(lastSPId);

            String spData = xslFoTransformer.applyTemplate(sp, ScientificPublicationRepository.DATA_PROCESS_XSL );

            String transformed_p =
                xslFoTransformer.applyTemplate(p, ProcessPSPRepository.ProcessPSPXSLForReview );
            
            String[] s = transformed_p.split("</processPSP>");
            transformed_p =  s[0] + spData + "\n</processPSP>";
            
            retVal.append(transformed_p);
            retVal.append("\n");
        }
        retVal.append("</processes>");
        return retVal.toString();
    }


    @Override
    public String findMyReviewAssigments(String email) throws Exception {
        StringBuilder retVal = new StringBuilder();
        retVal.append("<processes>");
        ArrayList<String> myPublications = this.processPSPRepo.findMyReviewAssigments(email);
        for(String p: myPublications) {
            retVal.append(p);
            retVal.append("\n");
        }
        retVal.append("</processes>");
        return retVal.toString();
    }

}