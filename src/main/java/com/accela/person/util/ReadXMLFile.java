package com.accela.person.util;

import com.accela.person.entities.Person;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ReadXMLFile {

    public static List<Person> parseXML(String filePath){
        File file = new File(filePath);
        return parseXML(file);
    }

    public static List<Person> parseXML(File file){
        List<Person> personList = new LinkedList<>();

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("person");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Person person = new Person();

                    Element element = (Element) node;

                    person.setId(Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent()));
                    person.setName(element.getElementsByTagName("name").item(0).getTextContent());
                    person.setSurname(element.getElementsByTagName("surname").item(0).getTextContent());

                    personList.add(person);
                }
            }
        } catch (ParserConfigurationException e) {
            System.out.println("Archive format error.");
            //e.printStackTrace();
        } catch (SAXException e) {
            System.out.println("Archive format error (2).");
            //e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File Not Found.");
            //e.printStackTrace();
        }

        return personList;
    }

}
