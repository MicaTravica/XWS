package com.example.xmlScientificPublicationEditor.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.example.xmlScientificPublicationEditor.repository.IdGeneratorRepository;
import com.example.xmlScientificPublicationEditor.service.IdGeneratorService;

@Service
public class IdGeneratorServiceImpl implements IdGeneratorService {

	public static String CAPTION = "ns:caption";
	public static String AUTHOR = "ns:author";
	public static String INSTITUTION = "ns:institution";
	public static String ADDRESS = "ns:address";
	public static String ABSTRACT = "ns:abstract";
	public static String PARAGRAPH = "ns:paragraph";
	public static String CHAPTER = "ns:chapter";
	public static String SUBCHAPTER = "ns:subchapter";
	public static String TEXT = "ns:text";
	public static String QUOTE = "ns:quote";
	public static String FORMULA = "ns:formula";
	public static String LIST = "ns:list";
	public static String TABLE = "ns:table";
	public static String IMAGE = "ns:image";
	public static String REFERENCE = "ns:reference";
	public static String CP = "ns:contactPerson";
	public static String CONTENT = "ns:content";
	
	@Autowired
	private IdGeneratorRepository idGeneratorRepository;
	
	@Override
	public String getId(String element) throws Exception {
		return idGeneratorRepository.findOne(element);
	}
	
	@Override
	public void generateElementId(Node node, String id, String type) throws Exception{
        if (node instanceof Element) {
            if (!node.getNodeName().equals(type)) {
                throw new Exception(String.format("Expected %s, instead got %s", type, node.getNodeName()));
            }
            Element element = (Element) node;

            element.setAttribute("id", id);
        }
        else {
            throw new Exception("Node object passed is not an Element");
        }
    }
	
	@Override
	public void generateChapterId(Node node, String id) throws Exception {
        if (node instanceof Element) {
            if (!node.getNodeName().equals(CHAPTER)) {
                throw new Exception("Expected chapter, instead got " + node.getNodeName());
            }
            Element chapter = (Element) node;

            chapter.setAttribute("id", id);

            NodeList paragraphs = chapter.getElementsByTagName(PARAGRAPH);
            for (int i = 0; i < paragraphs.getLength(); ++i) {
                generateParagraphId(paragraphs.item(i), id + "_paragraph" + (i+1));
            }
            
            NodeList subchapters = chapter.getElementsByTagName(SUBCHAPTER);
            for (int i = 0; i < subchapters.getLength(); ++i) {
                generateElementId(subchapters.item(i), id + "_subchapter" + (i+1), SUBCHAPTER);
            }
        }
        else {
            throw new Exception("Node object passed is not an Element");
        }
    }

	@Override
    public void generateParagraphId(Node node, String id) throws Exception {
        if (node instanceof Element) {
            if (!node.getNodeName().equals(PARAGRAPH) && !node.getNodeName().equals(CONTENT)) {
                throw new Exception("Expected paragraph/content, instead got " + node.getNodeName());
            }
            Element paragraph = (Element) node;

            paragraph.setAttribute("id", id);
            
            NodeList text = paragraph.getElementsByTagName(TEXT);
            for (int i = 0; i < text.getLength(); ++i) {
                generateElementId(text.item(i), id + "_text" + (i+1), TEXT);
            }
            
            NodeList quote = paragraph.getElementsByTagName(QUOTE);
            for (int i = 0; i < quote.getLength(); ++i) {
                generateElementId(quote.item(i), id + "_quote" + (i+1),QUOTE);
            }
            
            NodeList formula = paragraph.getElementsByTagName(FORMULA);
            for (int i = 0; i < formula.getLength(); ++i) {
                generateElementId(formula.item(i), id + "_formula" + (i+1),FORMULA);
            }
            
            NodeList list = paragraph.getElementsByTagName(LIST);
            for (int i = 0; i < list.getLength(); ++i) {
                generateElementId(list.item(i), id + "_list" + (i+1),LIST);
            }

            NodeList table = paragraph.getElementsByTagName(TABLE);
            for (int i = 0; i < table.getLength(); ++i) {
                generateElementId(table.item(i), id + "_table" + (i+1),TABLE);
            }
            
            NodeList image = paragraph.getElementsByTagName(IMAGE);
            for (int i = 0; i < image.getLength(); ++i) {
                generateElementId(image.item(i), id + "_image" + (i+1), IMAGE);
            }
        }
        else {
            throw new Exception("Node object passed is not an Element");
        }
    }

}
