package parsing.xml.original;

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

/**
 * Created by dusky on 3/30/17.
 */
public class VypisDovoleniekDOM {

    public static void main() throws IOException, SAXException, ParserConfigurationException {
        main(new String[] {VypisDovoleniek.fileName});
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String fileName = null;
        if (args.length < 1) {
            fileName = VypisDovoleniek.fileName;
        }else {
            fileName = args[0];
        }


        File inputFile = new File(fileName);
        DocumentBuilderFactory dbFactory
                = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();

        NodeList dovolenky = doc.getElementsByTagName("dovolenka");
        int celkovyPocetDni = 0;

        for (int indexDovolenka = 0; indexDovolenka < dovolenky.getLength(); indexDovolenka++) {
            Node dovolenka = dovolenky.item(indexDovolenka);

            Node miestoNode = ((Element) dovolenka).getElementsByTagName("miesto").item(0);
            Node rokNode = ((Element) dovolenka).getElementsByTagName("rok").item(0);
            Node pocetDniNode = ((Element) dovolenka).getElementsByTagName("pocetDni").item(0);

            int rok = Integer.parseInt(rokNode.getTextContent());
            String miesto = miestoNode.getTextContent().trim();
            String krajina = miestoNode.getAttributes().getNamedItem("krajina").getNodeValue();
            celkovyPocetDni = celkovyPocetDni + Integer.parseInt(pocetDniNode.getTextContent());

            System.out.println(rok + ": " + miesto + " (" + krajina + ")");
        }
        System.out.println("Celkovy pocet dni: " + celkovyPocetDni);
    }

    public static void createXML() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        File inputFile = new File(VypisDovoleniek.fileName);
        DocumentBuilderFactory dbFactory
                = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();

        DocumentBuilderFactory dbFactory2 =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder2 =
                dbFactory.newDocumentBuilder();
        Document doc2 = dBuilder.newDocument();
        // root element
        Element rootElement = doc2.createElement("vysledok");
        doc2.appendChild(rootElement);

        NodeList dovolenky = doc.getElementsByTagName("dovolenka");
        int celkovyPocetDni = 0;

        for (int indexDovolenka = 0; indexDovolenka < dovolenky.getLength(); indexDovolenka++) {
            Node dovolenka = dovolenky.item(indexDovolenka);

            Node miestoNode = ((Element) dovolenka).getElementsByTagName("miesto").item(0);
            Node rokNode = ((Element) dovolenka).getElementsByTagName("rok").item(0);
            Node pocetDniNode = ((Element) dovolenka).getElementsByTagName("pocetDni").item(0);

            int rok = Integer.parseInt(rokNode.getTextContent());
            String miesto = miestoNode.getTextContent().trim();
            String krajina = miestoNode.getAttributes().getNamedItem("krajina").getNodeValue();
            celkovyPocetDni = celkovyPocetDni + Integer.parseInt(pocetDniNode.getTextContent());

            //  supercars element
            Element supercar = doc2.createElement("dovolenka");
            supercar.setTextContent(rok + ": " + miesto + " (" + krajina + ")");
            rootElement.appendChild(supercar);
        }
        Element supercar = doc2.createElement("celkovyPocetDni");
        supercar.setTextContent("Celkovy pocet dni: " + celkovyPocetDni);
        rootElement.appendChild(supercar);

        TransformerFactory transformerFactory =
                TransformerFactory.newInstance();
        Transformer transformer =
                transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc2);
        StreamResult result =
                new StreamResult(new File("vysledokParsovania.xml"));
        transformer.transform(source, result);
        // Output to console for testing
        StreamResult consoleResult =
                new StreamResult(System.out);
        transformer.transform(source, consoleResult);
    }
}
