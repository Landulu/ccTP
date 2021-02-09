package book;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XMLBookRepository implements IBookRepository {

    private Document document;
    private boolean documentOpened = false;
    private File xmlFile;

    public XMLBookRepository(String filePath) {

        try {

            xmlFile = new File(filePath);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(xmlFile);
            documentOpened = true;
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private Book convertNodeToBook(Node bookNode) {
        if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
            Element bookElement = (Element) bookNode;
            return new Book(bookElement.getAttribute("title"), bookElement.getAttribute("author"));
        }
        return null;
    }

    @Override
    public ArrayList<Book> getAll() {
        NodeList nList = document.getElementsByTagName("book");
        ArrayList<Book> books = new ArrayList<>();
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            books.add(convertNodeToBook(node));
        }
        return books;
    }

    @Override
    public void sync(ArrayList<Book> books) {
        this.emptyLibrary();
        this.populateLibrary(books);
        this.writeToXML();
    }

    private void emptyLibrary() {
        Node root = document.getDocumentElement();
        removeChilds(root);
    }

    private void populateLibrary(ArrayList<Book> books) {
        Node root = document.getDocumentElement();
        for (Book book: books) {
            root.appendChild(createBookElement(book));
        }
    }

    private void writeToXML() {
        try{
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }



    // XML manipulation

    private Node createBookElement( Book book) {

        Element bookElement = document.createElement("book");


        bookElement.appendChild(createBookAttribute("title", book.getTitle()));
        bookElement.appendChild(createBookAttribute( "author", book.getAuthor()));

        return bookElement;
    }

    private Node createBookAttribute( String name, String value) {
        Element node = document.createElement(name);
        node.appendChild(document.createTextNode(value));
        return node;
    }


    private void removeChilds(Node node) {
        while (node.hasChildNodes())
            node.removeChild(node.getFirstChild());
    }
}
