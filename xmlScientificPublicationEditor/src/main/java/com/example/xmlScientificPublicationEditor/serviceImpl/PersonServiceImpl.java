package com.example.xmlScientificPublicationEditor.serviceImpl;

import java.util.Collection;

import com.example.xmlScientificPublicationEditor.exception.ResourceExistsException;
import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
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
        TPerson foundPerson =
            personRepository.findOne(
                PersonRepository.makeXpathQueryByEmail(person.getEmail())
            );
        if(foundPerson != null){
            throw new ResourceExistsException(
                String.format("Person with email: %s", person.getEmail())
            );
        }
        return personRepository.save(person);
    }

    @Override
    public TPerson findOne(String id) throws Exception {
        TPerson person =
            personRepository.findOne(PersonRepository.makeXpathQueryById(id));
        if(person == null){
            throw new ResourceNotFoundException(String.format("Person with id %s", id));
        }
        return person;
    }

    @Override
    public void delete(String personId) throws Exception {
        personRepository.deletePerson(personId);
    }
    
    @Override
    public Collection<TPerson> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TPerson update(TPerson person) throws Exception {
        return this.personRepository.update(person);
    }
    
}