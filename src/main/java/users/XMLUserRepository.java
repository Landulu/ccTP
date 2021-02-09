package users;

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
import java.util.ArrayList;

public class XMLUserRepository implements IUserRepository {


    private Document document;
    private boolean documentOpened = false;

    public XMLUserRepository(String filePath) {

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            File xmlFile = new File(filePath);
            document = dBuilder.parse(xmlFile);
            documentOpened = true;
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private User convertNodeToUser(Node userNode) {
        if (userNode.getNodeType() == Node.ELEMENT_NODE) {
            Element UserElement = (Element) userNode;
            String loginString = UserElement.getAttribute("login");
            String rightsString = UserElement.getAttribute("rights");
            return new User(
                    UserElement.getAttribute("login"),
                    getRightsByKey(UserElement.getAttribute("rights")));
        }
        return null;
    }


    @Override
    public ArrayList<User> getAll() {
        NodeList nList = document.getElementsByTagName("user");
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            users.add(convertNodeToUser(node));
        }
        return users;
    }

    private UserRight getRightsByKey(String rightsKey) {
        if( rightsKey.equals("ADMIN")) {
            return UserRight.ADMIN;
        }
        if (rightsKey.equals("MEMBER")){
            return UserRight.MEMBER;
        }
        return UserRight.GUEST;
    }
}
