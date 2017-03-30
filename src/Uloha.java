import org.xml.sax.SAXException;
import parsing.xml.XMLParsing;
import parsing.xml.original.VypisDovoleniek;
import parsing.xml.original.VypisDovoleniekDOM;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;

import static parsing.json.JsonParsing.objectModelApi;
import static parsing.json.JsonParsing.streamApi;

/**
 * Created by dusky on 3/30/17.
 */
public class Uloha {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        uloha1();
        uloha2();
        uloha3();
        uloha4();
    }

    private static void uloha1() throws FileNotFoundException {
        streamApi();
        objectModelApi();
    }

    private static void uloha2() throws IOException, SAXException, ParserConfigurationException {
        VypisDovoleniek.main();
        new XMLParsing().parse();
    }

    private static void uloha3() throws ParserConfigurationException, SAXException, IOException {
        VypisDovoleniekDOM.main();
    }

    private static void uloha4() throws IOException, SAXException, ParserConfigurationException, TransformerException {
        VypisDovoleniekDOM.createXML();
    }
}
