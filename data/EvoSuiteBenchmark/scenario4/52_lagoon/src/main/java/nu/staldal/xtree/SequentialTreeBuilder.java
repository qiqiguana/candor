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

    public static final boolean DEBUG = false;

    private static final String XML_NS = "http://www.w3.org/XML/1998/namespace";

    private URL baseURI;

    private Locator locator = null;

    private ElementHandler handler;

    private Element rootElement;

    private TreeBuilder subTreeBuilder;

    private Vector nsPrefix = null;

    private Vector nsURI = null;

    private int inSubTree = 0;

    /**
     * Parse an XML document into a list of XTree:s, one for each element under the root.
     * Uses JAXP to find a parser.
     * Will not support xml:base.
     *
     * @param xmlInput    the input to parse
     * @param validateDTD validate using DTD
     * @param handler     handler to invoke for each element
     *
     * @return the root element (without any children)
     *
     * @throws SAXParseException if the XML data is not valid
     * @throws SAXException if any other error occurs while parsing the XML data
     * @throws IOException  if there was some I/O error while reading the input.
     */
    public static Element parseXMLSequential(InputSource xmlInput, boolean validateDTD, ElementHandler handler) throws SAXParseException, SAXException, IOException;

    /**
     * Constructs a SequentialTreeBuilder, ready to receive SAX events.
     * Will not support xml:base.
     *
     * @param handler   handler to invoke for each element
     */
    public SequentialTreeBuilder(ElementHandler handler) {
    }

    /**
     * Constructs a SequentialTreeBuilder, ready to receive SAX events.
     *
     * @param handler   handler to invoke for each element
     * @param base      base URL for the document, to support xml:base.
     */
    public SequentialTreeBuilder(ElementHandler handler, URL base) {
    }

    /**
     * Obtain the root Element
     *
     * @throws IllegalStateException  if the SAX events received so far
     * doesn't constitues a well-formed XML document.
     */
    public Element getRootElement() throws IllegalStateException;

    public void setDocumentLocator(Locator locator);

    public void startDocument() throws SAXException;

    public void endDocument() throws SAXException;

    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException;

    public void endElement(String namespaceURI, String localName, String qName) throws SAXException;

    public void startPrefixMapping(String prefix, String uri) throws SAXException;

    public void endPrefixMapping(String prefix) throws SAXException;

    public void characters(char[] ch, int start, int length) throws SAXException;

    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException;

    public void processingInstruction(String target, String data) throws SAXException;

    public void skippedEntity(String name) throws SAXException;

    public void fatalError(SAXParseException e) throws SAXParseException;

    public void error(SAXParseException e) throws SAXParseException;

    public void warning(SAXParseException e);
}
