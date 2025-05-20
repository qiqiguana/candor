package nu.staldal.xtree;

import java.util.*;
import java.io.*;
import java.net.URL;
import javax.xml.parsers.*;
import org.xml.sax.*;

/**
 * Build an XTree from a SAX2 event stream, or by parsing an XML document.
 *
 * The TreeBuilder ensures that the tree will not have two adjacent Text nodes.
 */
public class TreeBuilder implements ContentHandler, ErrorHandler {

    public static Element parseXML(InputSource xmlInput, boolean validateDTD) throws SAXParseException, SAXException, IOException {
        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            parserFactory.setNamespaceAware(true);
            parserFactory.setValidating(validateDTD);
            parserFactory.setFeature("http://xml.org/sax/features/namespaces", true);
            parserFactory.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
            parserFactory.setFeature("http://xml.org/sax/features/validation", validateDTD);
            XMLReader xmlReader = parserFactory.newSAXParser().getXMLReader();
            TreeBuilder tb = new TreeBuilder();
            xmlReader.setContentHandler(tb);
            xmlReader.setErrorHandler(tb);
            xmlReader.parse(xmlInput);
            return tb.getTree();
        } catch (javax.xml.parsers.ParserConfigurationException e) {
            throw new Error("XML parser configuration error: " + e.getMessage());
        }
    }
}
