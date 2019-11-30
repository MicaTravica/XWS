package com.example.xmlScientificPublicationEditor.repository.person;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.example.xmlScientificPublicationEditor.model.person.TPerson;

import org.xmldb.api.modules.XMLResource;

public class PersonUnmarshalling {

    public static String JAXBContextPath = "com.example.xmlScientificPublicationEditor.model.person";

    public static TPerson unmarshalling(XMLResource res) throws Exception {
        JAXBContext context = JAXBContext.newInstance(JAXBContextPath);        
    	Unmarshaller unmarshaller = context.createUnmarshaller();
    	TPerson person = (TPerson) unmarshaller.unmarshal(res.getContentAsDOM());
    	return person;
    }
}