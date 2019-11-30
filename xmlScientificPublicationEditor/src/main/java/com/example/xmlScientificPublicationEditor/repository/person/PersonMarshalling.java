package com.example.xmlScientificPublicationEditor.repository.person;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.example.xmlScientificPublicationEditor.model.person.TPerson;

public class PersonMarshalling {

    public static String marshalPerson(TPerson person) throws Exception {
        try {
            StringWriter sw = new StringWriter();
            JAXBContext context = JAXBContext.newInstance("com.example.xmlScientificPublicationEditor.model.person");
            // Marshaller je objekat zadužen za konverziju iz objektnog u XML model
            Marshaller marshaller = context.createMarshaller();
            // Podešavanje marshaller-a
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // Umesto System.out-a, može se koristiti FileOutputStream
            marshaller.marshal(person, sw); 

            return sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
