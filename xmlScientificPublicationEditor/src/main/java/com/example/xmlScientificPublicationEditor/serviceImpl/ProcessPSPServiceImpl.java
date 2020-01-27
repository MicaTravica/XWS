package com.example.xmlScientificPublicationEditor.serviceImpl;

import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamResult;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.model.authPerson.TAuthPerson;
import com.example.xmlScientificPublicationEditor.model.authPerson.TRole;
import com.example.xmlScientificPublicationEditor.repository.CoverLetterRepository;
import com.example.xmlScientificPublicationEditor.repository.ProcessPSPRepository;
import com.example.xmlScientificPublicationEditor.service.PersonService;
import com.example.xmlScientificPublicationEditor.service.ProcessPSPService;
import com.example.xmlScientificPublicationEditor.util.DOMParser.DOMParser;

import org.apache.xerces.xs.XSModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;

@Service
public class ProcessPSPServiceImpl implements ProcessPSPService{

    @Autowired
    private ProcessPSPRepository processPSPRepo;

    @Autowired
    private PersonService personService;

    @Override
    public String create(String scientificPublicationId) throws Exception {
        String doc =  this.generateProcessXMLTemplate();
        Document newProcess = DOMParser.buildDocumentWithOutSchema(doc);
        String redactorId = this.personService.findUsersByRole(TRole.ROLE_REDACTOR).get(0);
        newProcess = processPSPRepo.setRedactor(newProcess, redactorId);
        processPSPRepo.setScientificPublicationId(newProcess, scientificPublicationId);
        processPSPRepo.save(newProcess);
        return newProcess.getDocumentElement().getAttribute("id");
    }

    @Override
    public String findOneByScientificPublicationID(String scientificPublicationId) throws Exception {
        String procesStr = 
                processPSPRepo.findOneByScientificPublicationID(scientificPublicationId);

		if(procesStr == null) {
			throw new ResourceNotFoundException("ProcessPSP");
		}
		return procesStr;
    }

    @Override
    public String setCoverLetter(String scientificPublicationId, String coverLetterId) throws Exception {
        String processStr = this.findOneByScientificPublicationID(scientificPublicationId);
        Document process = DOMParser.buildDocumentWithOutSchema(processStr);
        processPSPRepo.setCoverLetter(process, coverLetterId);
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
		xsInstance.generateOptionalElements = Boolean.FALSE; // null means rando
		QName rootElement = new QName("http://www.uns.ac.rs/Tim1", "processPSP");
		XMLDocument sampleXml = new XMLDocument(new StreamResult(sw), true, 4, null);
		xsInstance.generate(xsModel, rootElement, sampleXml);
		return sw.toString();
	}
    
}