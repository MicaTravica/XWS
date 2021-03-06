package com.example.xmlScientificPublicationEditor.repository.person;

import java.io.File;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import com.example.xmlScientificPublicationEditor.model.authPerson.TAuthPerson;
import com.example.xmlScientificPublicationEditor.model.person.TPerson;

public class PersonMarshalling {

	public static String marshalPerson(TPerson person) throws Exception {
		StringWriter sw = new StringWriter();
		JAXBContext context = JAXBContext.newInstance("com.example.xmlScientificPublicationEditor.model.person");
		// Marshaller je objekat zadužen za konverziju iz objektnog u XML model
		Marshaller marshaller = context.createMarshaller();

		File file = new File(PersonRepository.personSchemaPath);
		String constant = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		SchemaFactory xsdFactory = SchemaFactory.newInstance(constant);
		Schema schema = null;
		schema = xsdFactory.newSchema(file);

		marshaller.setSchema(schema);
		// Podešavanje marshaller-a
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		// Umesto System.out-a, može se koristiti FileOutputStream
		marshaller.marshal(person, sw);

		return sw.toString();
	}

    public static String marshalAuthPerson(TAuthPerson person) throws Exception {
        try {
            StringWriter sw = new StringWriter();
            JAXBContext context = JAXBContext.newInstance("com.example.xmlScientificPublicationEditor.model.authPerson");
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
