package com.example.xmlScientificPublicationEditor.serviceImpl;

import java.util.Collection;

import com.example.xmlScientificPublicationEditor.exception.ResourceExistsException;
import com.example.xmlScientificPublicationEditor.exception.UserNotFoundByEmailException;
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
        TPerson foundPerson = personRepository.findOne(person.getEmail());
        if(foundPerson != null){
            throw new ResourceExistsException(
                String.format("Person with email: %s", person.getEmail())
            );
        }
        return personRepository.save(person);
    }

    @Override
    public TPerson findOne(String email) throws Exception {
        TPerson person = personRepository.findOne(email);
        if(person == null){
            throw new UserNotFoundByEmailException(email);
        }
        return person;
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