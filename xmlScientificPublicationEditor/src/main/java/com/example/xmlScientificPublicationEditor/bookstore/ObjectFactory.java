package com.example.xmlScientificPublicationEditor.bookstore;
import javax.xml.bind.annotation.XmlRegistry;


@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: rs.ac.uns.ftn.examples.xquery.bookstore
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Book }
     * 
     */
    public Book createBook() {
        return new Book();
    }

    /**
     * Create an instance of {@link Bookstore }
     * 
     */
    public Bookstore createBookstore() {
        return new Bookstore();
    }

    /**
     * Create an instance of {@link Book.Title }
     * 
     */
    public Book.Title createBookTitle() {
        return new Book.Title();
    }

}
