package nu.staldal.xtree;

import java.util.*;
import java.io.*;
import java.net.URL;
import javax.xml.parsers.*;
import org.xml.sax.*;

/**
 * Build a list of XTree:s from a SAX2 event stream, or by parsing an XML document.
 * <p>
 * Useful to process a large document sequentially, without need to store the whole
 * document in memory at the same time.
 * <p>
 * The SequentialTreeBuilder ensures that the tree will not have two adjacent Text nodes.
 */
public class SequentialTreeBuilder implements ContentHandler, ErrorHandler {

    public static Element parseXMLSequential(InputSource xmlInput, boolean validateDTD, ElementHandler handler) throws SAXParseException, SAXException, IOException {
        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            parserFactory.setNamespaceAware(true);
            parserFactory.setValidating(validateDTD);
            parserFactory.setFeature("http://xml.org/sax/features/namespaces", true);
            parserFactory.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
            parserFactory.setFeature("http://xml.org/sax/features/validation", validateDTD);
            XMLReader xmlReader = parserFactory.newSAXParser().getXMLReader();
            SequentialTreeBuilder tb = new SequentialTreeBuilder(handler);
            xmlReader.setContentHandler(tb);
            xmlReader.setErrorHandler(tb);
            xmlReader.parse(xmlInput);
            return tb.getRootElement();
        } catch (javax.xml.parsers.ParserConfigurationException e) {
            throw new Error("XML parser configuration error: " + e.getMessage());
        }
    }
}
