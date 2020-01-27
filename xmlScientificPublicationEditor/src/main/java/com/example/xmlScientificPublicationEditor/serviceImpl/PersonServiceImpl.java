package com.example.xmlScientificPublicationEditor.serviceImpl;

import java.io.StringWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamResult;

import com.example.xmlScientificPublicationEditor.exception.ResourceExistsException;
import com.example.xmlScientificPublicationEditor.exception.ResourceNotFoundException;
import com.example.xmlScientificPublicationEditor.model.authPerson.TAuthPerson;
import com.example.xmlScientificPublicationEditor.model.authPerson.TRole;
import com.example.xmlScientificPublicationEditor.model.person.TAddress;
import com.example.xmlScientificPublicationEditor.model.person.TInstitution;
import com.example.xmlScientificPublicationEditor.model.person.TPerson;
import com.example.xmlScientificPublicationEditor.repository.person.PersonRepository;
import com.example.xmlScientificPublicationEditor.service.PersonService;

import org.apache.xerces.xs.XSModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public TAuthPerson registration(TAuthPerson person) throws Exception {
        TAuthPerson foundPerson = personRepository.findOneAuth(PersonRepository.makeXpathQueryByEmailAuthPerson(person.getEmail()));
        if (foundPerson != null) {
            throw new ResourceExistsException(String.format("Person with email: %s", person.getEmail()));
        }
        
        BCryptPasswordEncoder b =  new BCryptPasswordEncoder();
        person.setPassword(b.encode(person.getPassword()));
        person.getRoles().getRole().clear();
        person.getRoles().getRole().add(TRole.ROLE_USER);
        TPerson tp = generatePerson(person);
        TPerson saved = personRepository.save(tp);
        person.setPerson(saved.getId());
        return personRepository.save(person);
    }

    private TPerson generatePerson(TAuthPerson person) {
    	TPerson tp = new TPerson();
    	tp.setId("person");
        tp.setName("Name");
        tp.setSurname("Surname");
        tp.setPhone("123-123456");
        tp.setEmail(person.getEmail());
        TInstitution ti = new TInstitution();
        ti.setName("Institution name");
        TAddress ta = new TAddress();
        ta.setCity("City");
        ta.setFloorNumber(BigInteger.ZERO);
        ta.setStreetNumber("0");
        ta.setStreet("Street");
        ta.setCountry("Country");
        ti.setAddress(ta);
        tp.setInstitution(ti);
        return tp;
    }
    
    @Override
    public TPerson findOne(String id) throws Exception {
        TPerson person = personRepository.findOne(PersonRepository.makeXpathQueryById(id));
        if (person == null) {
            throw new ResourceNotFoundException(String.format("Person with id %s", id));
        }
        return person;
    }

    @Override
    public TAuthPerson findOneAuth(String email) throws Exception {
        TAuthPerson person = personRepository.findOneAuth(
                PersonRepository.makeXpathQueryByEmailAuthPerson(email));
        if (person == null) {
            throw new ResourceNotFoundException(String.format("Person with email %s", email));
        }
        return person;
    }

    @Override
    public ArrayList<String> findUsersByRole(TRole roleRedactor) throws Exception {
        ArrayList<String> retVal = new ArrayList<>();
        ArrayList<TAuthPerson> persons = personRepository.findByRole(
                PersonRepository.makeXpathQueryByRole(roleRedactor.toString()));

        for(TAuthPerson p: persons) {
            retVal.add(p.getId());
        }
        return retVal;
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

    @Override
    public String generatePersonXMLTemplate() throws Exception {
        StringWriter sw = new StringWriter();
        XSModel xsModel = new XSParser().parse(PersonRepository.personSchemaPath);
        XSInstance xsInstance = new XSInstance();
        xsInstance.minimumElementsGenerated = 1;
        xsInstance.maximumElementsGenerated = 1;
        xsInstance.generateOptionalElements = Boolean.TRUE; // null means rando
        QName rootElement = new QName("http://www.uns.ac.rs/Tim1", "person");
        XMLDocument sampleXml = new XMLDocument(new StreamResult(sw), true, 4, null);
        xsInstance.generate(xsModel, rootElement, sampleXml);
        return sw.toString();
    }

    @Override
    public String generateAuthXMLTemplate() throws Exception {
        StringWriter sw = new StringWriter();
        XSModel xsModel = new XSParser().parse(PersonRepository.userAuthSchemaPath);
        XSInstance xsInstance = new XSInstance();
        xsInstance.minimumElementsGenerated = 1;
        xsInstance.maximumElementsGenerated = 1;
        xsInstance.generateOptionalElements = Boolean.TRUE; // null means rando
        QName rootElement = new QName("http://www.uns.ac.rs/Tim1", "auth");
        XMLDocument sampleXml = new XMLDocument(new StreamResult(sw), true, 4, null);
        xsInstance.generate(xsModel, rootElement, sampleXml);
        return sw.toString();
    }
  
}