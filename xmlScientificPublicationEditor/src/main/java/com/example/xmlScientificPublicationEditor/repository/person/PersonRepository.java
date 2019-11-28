package com.example.xmlScientificPublicationEditor.repository.person;

import java.io.OutputStream;

import com.example.xmlScientificPublicationEditor.model.person.TPerson;
import com.example.xmlScientificPublicationEditor.util.StoreToDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonRepository {

    public static String personCollectionId = "/db/sample/person";

    @Autowired
    PersonMarshalling personMarshalling;

    @Autowired
    StoreToDB storeToDB;

    public TPerson save(TPerson person) throws Exception{
        // da mu dodam id? 
        // da ga pretovrim u xml fajl
        // taj xml fajl da posaljem u bazu, i ako uspe da vratim objekat
        String fileName = personMarshalling.marshalPerson(person);
        if(fileName != null)
        {
            storeToDB.store(personCollectionId, fileName);
        }
        return person;
    }

    public String findOne(String email)
    {
        return null;
    }
}