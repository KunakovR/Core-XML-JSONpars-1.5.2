import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
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
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Main {

    private static long id;
    private static String firstName;
    private static String lastName;
    private static String country;
    private static int age;


    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, SAXException {

        String fileName = "data.xml";
        List<Employee> list = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element staff = document.createElement("staff");
        document.appendChild(staff);
        for (int i = 1; i < 3; i++) {
            Element employee = document.createElement("employee");
            staff.appendChild(employee);
            Element id = document.createElement("id");
            if (i == 1) {
                id.appendChild(document.createTextNode("1"));
            } else {
                id.appendChild(document.createTextNode("2"));
            }
            employee.appendChild(id);
            Element firstName = document.createElement("firstName");
            if (i == 1) {
                firstName.appendChild(document.createTextNode("John"));
            } else {
                firstName.appendChild(document.createTextNode("Ivan"));
            }
            employee.appendChild(firstName);
            Element lastName = document.createElement("lastName");
            if (i == 1) {
                lastName.appendChild(document.createTextNode("Smith"));
            } else {
                lastName.appendChild(document.createTextNode("Petrov"));
            }
            employee.appendChild(lastName);
            Element country = document.createElement("country");
            if (i == 1) {
                country.appendChild(document.createTextNode("USA"));
            } else {
                country.appendChild(document.createTextNode("RU"));
            }
            employee.appendChild(country);
            Element age = document.createElement("age");
            if (i == 1) {
                age.appendChild(document.createTextNode("25"));
            } else {
                age.appendChild(document.createTextNode("23"));
            }
            employee.appendChild(age);
        }
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("data.xml"));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(domSource, streamResult);


        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        DocumentBuilder buil = fac.newDocumentBuilder();
        Document doc = buil.parse(new File(fileName));

        NodeList nodeList = doc.getDocumentElement().getElementsByTagName("employee");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            NodeList nodeListEmployee = node.getChildNodes();
            for (int j = 0; j < nodeListEmployee.getLength(); j++){
                Node node_ = nodeListEmployee.item(j);
                Element element = (Element) node_;
                if (element.getNodeName() == "id") {
                    id = Long.parseLong(element.getTextContent());
                }
                if (element.getNodeName() == "firstName"){
                    firstName = element.getTextContent();
                }
                if (element.getNodeName() == "lastName"){
                    lastName = element.getTextContent();
                }
                if (element.getNodeName() == "country"){
                    country = element.getTextContent();
                }
                if (element.getNodeName() == "age"){
                    age = Integer.parseInt(element.getTextContent());
                }
            }
            Employee employee = new Employee(id, firstName, lastName, country, age);
            list.add(employee);
        }

        Type listType = new TypeToken<List<Employee>>() {}.getType();
        GsonBuilder build = new GsonBuilder();
        build.setPrettyPrinting();
        Gson gson = build.create();
        String json = gson.toJson(list, listType);

        try (FileWriter file = new
                FileWriter("data.json")) {
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}





