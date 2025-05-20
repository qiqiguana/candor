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

    /**
     * Parse an XML document into a list of XTree:s, one for each element under the root.
     * Uses JAXP to find a parser.
     * Will not support xml:base.
     *
     * @param xmlInput the input to parse
     * @param validateDTD validate using DTD
     * @param handler handler to invoke for each element
     * @return the root element (without any children)
     * @throws SAXParseException if the XML data is not valid
     * @throws SAXException if any other error occurs while parsing the XML data
     * @throws IOException if there was some I/O error while reading the input.
     */
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
