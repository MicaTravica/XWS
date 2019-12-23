package com.example.xmlScientificPublicationEditor.serviceImpl;

import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.repository.ProcessPSPRepository;
import com.example.xmlScientificPublicationEditor.service.ProcessPSPService;

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
    public String findOne(String processId) throws Exception {
        String procesStr = processPSPRepo.findOne(processId);
		if(procesStr == null) {
			throw new ResourceNotFoundException(String.format("ProcesPSP with id %s", processId));
		}
		return procesStr;
    }

    @Override
    public String addCoverLetter(String processId, String coverLetterId) throws Exception {
        String processStr = this.findOne(processId);
        // processPSPRepo.setCoverLetter("a", coverLetterId);
        return null;
    }

    @Override
    public String addRewiever(String personId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String addReview(String processId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(String processId) throws Exception {
        processPSPRepo.delete(processId);
    }
    
}