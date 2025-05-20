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

    /**
     * Parse an XML document into an XTree.
     * Uses JAXP to find a parser.
     * Will not support xml:base.
     *
     * @param xmlInput the input to parse
     * @param validateDTD validate using DTD
     * @return an XTree representation of the XML data
     * @throws SAXParseException if the XML data is not valid
     * @throws SAXException if any other error occurs while parsing the XML data
     * @throws IOException if there was some I/O error while reading the input.
     */
    public static Element parseXML(InputSource xmlInput, boolean validateDTD) throws SAXParseException, SAXException, IOException;
}
