package parsing.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import parsing.model.Way;
import parsing.xml.maps.FrameDemo;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by dusky on 3/2/17.
 */
public class XMLParsing {
    Set<Long> usedNodeIds = new HashSet<Long>();
    List<Way> goodWays = new ArrayList<Way>();
    Map<Long, parsing.model.Node> nodes = new HashMap<Long, parsing.model.Node>();

    public void parse() throws ParserConfigurationException, IOException, SAXException {
        File inputFile = new File("map.xml");
        DocumentBuilderFactory dbFactory
                = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();

        NodeList ways = doc.getElementsByTagName("way");
        getGoodWays(ways);

        NodeList nodes = doc.getElementsByTagName("node");
        getGoodNodes(nodes);
        
        
        zobrazCesty();
    }

    private void zobrazCesty() {
        new FrameDemo(goodWays, nodes).showMap();
    }

    private void getGoodNodes(NodeList nodes) {
        for (int temp = 0; temp < nodes.getLength(); temp++) {
            Node node = nodes.item(temp);

            long id = Long.parseLong(node.getAttributes().getNamedItem("id").getNodeValue());
            if (usedNodeIds.contains(id)){
                double lat = Double.parseDouble(node.getAttributes().getNamedItem("lat").getNodeValue());
                double lon = Double.parseDouble(node.getAttributes().getNamedItem("lon").getNodeValue());

                parsing.model.Node modelNode = new parsing.model.Node(id, lat, lon);
                this.nodes.put(id, modelNode);
            }
        }
    }

    private void getGoodWays(NodeList ways) {
        for (int temp = 0; temp < ways.getLength(); temp++) {
            Node way = ways.item(temp);

            NodeList tags = ((Element) way).getElementsByTagName("tag");


            for (int tagIndex = 0; tagIndex < tags.getLength(); tagIndex++){
                Node tag = tags.item(tagIndex);

                String k = tag.getAttributes().getNamedItem("k").getNodeValue();
                String v = tag.getAttributes().getNamedItem("v").getNodeValue();

                if (k.equals("highway") && v.equals("footway")){
                    long id = Long.parseLong(way.getAttributes().getNamedItem("id").getNodeValue());
                    List<Long> nodeIntegers = new ArrayList<Long>();

                    NodeList nodes = ((Element) way).getElementsByTagName("nd");
                    for (int nodeIndex = 0; nodeIndex < nodes.getLength(); nodeIndex++){
                        Node node = nodes.item(nodeIndex);

                        long nodeId = Long.parseLong(node.getAttributes().getNamedItem("ref").getNodeValue());
                        nodeIntegers.add(nodeId);
                        usedNodeIds.add(nodeId);
                    }

                    Way rightWay = new Way(id, nodeIntegers);
                    goodWays.add(rightWay);
                }
            }
        }
    }
}
