package parsing.xml.original;

import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class VypisDovoleniek {

    private static final String fileName = "dovolenky.xml";

    public static void main(String[] args){
        String fileName = null;
        if (args.length < 1) {
            fileName = VypisDovoleniek.fileName;
        }else {
            fileName = args[0];
        }

        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setValidating(false);
            SAXParser p = spf.newSAXParser();
            XMLReader parser = p.getXMLReader();
            parser.setErrorHandler(new VypisChyb());
            ParserDovoleniek d = new ParserDovoleniek();
            parser.setContentHandler(d);
            parser.parse(fileName);
            d.vypisDovolenky();
            d.vypisCelkovyPocetDni();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
