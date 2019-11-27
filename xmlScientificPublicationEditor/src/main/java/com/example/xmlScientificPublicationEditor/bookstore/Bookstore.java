package com.example.xmlScientificPublicationEditor.bookstore;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "book"
})
@XmlRootElement(name = "bookstore")
public class Bookstore {

    @XmlElement(required = true)
    protected List<Book> book;

    public List<Book> getBook() {
        if (book == null) {
            book = new ArrayList<Book>();
        }
        return this.book;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("");
		
		for (Book b : getBook()) {
			builder.append("\nTitle: " + b.getTitle().getValue());
			builder.append(" (language: " + b.getTitle().getLang() + ")");
			builder.append("\nCategory: " + b.getCategory());
			builder.append("\nYear: " + b.getYear());
			builder.append("\nPrice: " + b.getPrice().doubleValue());
			builder.append("\nAuthor(s):");
			
			for (String author : b.getAuthor())
				builder.append("\n - " + author);
			
			builder.append("\n");
		
		}
		return builder.toString();
	}

}
