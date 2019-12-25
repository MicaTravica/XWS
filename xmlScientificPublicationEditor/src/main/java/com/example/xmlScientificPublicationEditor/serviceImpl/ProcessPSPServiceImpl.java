package com.example.xmlScientificPublicationEditor.serviceImpl;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.repository.ProcessPSPRepository;
import com.example.xmlScientificPublicationEditor.service.ProcessPSPService;
import com.example.xmlScientificPublicationEditor.util.DOMParser.DOMParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

@Service
public class ProcessPSPServiceImpl implements ProcessPSPService{

    @Autowired
    private ProcessPSPRepository processPSPRepo;

    @Override
    public String create(String scientificPublicationId) throws Exception {
        Document newProcess =  processPSPRepo.initProcess();
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
    
}