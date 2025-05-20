package common;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * basic functions for reading and writing xml files
 *
 * @author christian
 */
public class XmlFunctions {

    private static final Logger logger = Logger.getLogger(XmlFunctions.class);

    Document xml;

    String fileName;

    /**
     * constructor
     *
     * @param XmlFileName -
     *            path to xml file
     */
    public XmlFunctions(String XmlFileName) {
    }

    /**
     * sets value to specific xml node and saves the xml file
     *
     * @param element -
     *            name of xml element
     * @param value -
     *            String, value to be set
     */
    public void setXmlValue(String xmlPath, String value);

    /**
     * returns value of specific xml element
     *
     * @param XmlPath -
     *            element path (XPath)
     * @return String with value of element
     */
    public String getXmlValue(String XmlPath);

    /**
     * reads a xml file into a Document object
     *
     * @param fileName -
     *            path to xml file
     */
    private void openXmlFile(String fileName);

    /**
     * finds a xml node in the Document object and returns it
     *
     * @param XmlPath -
     *            element path (XPath)
     * @return Node - found node
     */
    private Node findXmlNode(String XmlPath);

    /**
     * returns a list of nodes matching to the element name
     *
     * @param NodeName -
     *            name of element(s)
     * @return NodeList with found nodes
     */
    public NodeList findXmlNodes(String NodeName);

    /**
     * writes global Document object xml (back) into xml file
     */
    private void writeXmlFile();
}
