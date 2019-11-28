package com.example.xmlScientificPublicationEditor.serviceImpl;

import java.util.Collection;

import com.example.xmlScientificPublicationEditor.model.person.TPerson;
import com.example.xmlScientificPublicationEditor.repository.person.PersonRepository;
import com.example.xmlScientificPublicationEditor.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired PersonRepository personRepository;


    @Override
    public TPerson registration(TPerson person) throws Exception {
        return personRepository.save(person);
    }

    @Override
    public TPerson findOne(String email) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Collection<TPerson> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TPerson update(TPerson person) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    
}