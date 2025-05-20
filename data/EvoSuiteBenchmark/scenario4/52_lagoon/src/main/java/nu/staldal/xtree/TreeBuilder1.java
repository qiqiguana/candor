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

    public static final boolean DEBUG = false;

    private Element rootElement = null;

    private Stack elementStack;

    private Vector nsPrefix = null;

    private Vector nsURI = null;

    private StringBuffer textBuffer = null;

    private String textSystemId = null;

    private int textLine = -1;

    private int textColumn = -1;

    private Locator locator = null;

    private URL baseURI;

    /**
     * Create an SAX InputSource from a File object.
     *
     * @param file  the file
     *
     * @return an InputSource
     * @throws FileNotFoundException  if the file doesn't exist
     * @throws FileNotFoundException  if some I/O error occurs
     */
    public static InputSource fileToInputSource(File file) throws FileNotFoundException, IOException;

    /**
     * Parse an XML document into an XTree.
     * Uses JAXP to find a parser.
     * Will not support xml:base.
     *
     * @param xmlInput    the input to parse
     * @param validateDTD validate using DTD
     *
     * @return an XTree representation of the XML data
     *
     * @throws SAXParseException if the XML data is not valid
     * @throws SAXException if any other error occurs while parsing the XML data
     * @throws IOException  if there was some I/O error while reading the input.
     */
    public static Element parseXML(InputSource xmlInput, boolean validateDTD) throws SAXParseException, SAXException, IOException;

    /**
     * Constructs a TreeBuilder, ready to receive SAX events.
     * Will not support xml:base.
     */
    public TreeBuilder() {
    }

    /**
     * Constructs a TreeBuilder, ready to receive SAX events.
     *
     * @param base  base URL for the document, to support xml:base.
     */
    public TreeBuilder(URL base) {
    }

    /**
     * Obtain the XTree built from SAX events.
     *
     * @throws IllegalStateException  if the SAX events received so far
     * doesn't constitues a well-formed XML document.
     */
    public Element getTree() throws IllegalStateException;

    void reset();

    private void addCharacters();

    public void setDocumentLocator(Locator locator);

    public void startDocument() throws SAXException;

    public void endDocument() throws SAXException;

    public void startElement(String namespaceURI, String localName, String qname, Attributes atts) throws SAXException;

    public void endElement(String namespaceURI, String localName, String qname) throws SAXException;

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
