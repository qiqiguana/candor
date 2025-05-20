package com.allenstudio.ir.util;

import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * XML IO reading and writing utility.
 *
 * @author fdietz
 */
public class XmlIO extends DefaultHandler {

    private static final Logger LOG = Logger.getLogger("org.columba.core.xml");

    private static final String ROOT_XML_ELEMENT_NAME = "__INSPIRENTO_XML_TREE_TOP__";

    // List of sub-elements
    @SuppressWarnings("unused")
    private List<XmlElement> elements;

    // Top level element (Used to hold everything else)
    private XmlElement rootElement;

    // The current element you are working on
    private XmlElement currentElement;

    // Indent for each level
    private int writeIndent = 2;

    // Maximum data to put on a "one liner"
    private int maxOneLineData = 20;

    // The SAX 2 parser...
    @SuppressWarnings("unused")
    private XMLReader xr;

    // the "characters" SAX event.
    private CharArrayWriter contents = new CharArrayWriter();

    private URL url = null;

    /*
  // setup and load constructor
  public XmlIO(String FilePath) {
          currentElement = null;


  }
  */
    public XmlIO(URL url) {
    }

    // setup and load constructor
    public XmlIO() {
    }

    /**
     * Creates a XmlIO object with the specified element at the top.
     * @param element the element at the top.
     */
    public XmlIO(XmlElement element) {
    }

    public void setURL(URL url);

    public boolean load();

    /**
     * Loads from the InputStream into the root Xml Element.
     * @param input the input stream to load from.
     */
    public boolean load(InputStream input);

    /**
     * Load a file. This is what starts things off.
     * @param inputURL the URL to load XML from.
     */
    public boolean load(URL inputURL);

    // will delegate SAX events to the tag tracker network.
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs) throws SAXException;

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException;

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException;

    /**
     * Returns the root for the XmlElement hiearchy.
     * Note that this Xml Element will always have the name <code>__COLUMBA_XML_TREE_TOP__</code>.
     * <p>
     * Methods that want to retrieve elements from this root should use
     * the {@link XmlElement#getElement(String)} in order to get the wanted
     * element.
     * @return a XmlElement if it has been loaded or initialized with it; null otherwise.
     */
    public XmlElement getRoot();

    public void errorDialog(String Msg);

    public void warningDialog(String Msg);

    public void infoDialog(String Msg);

    public void save() throws Exception;

    //
    public void write(OutputStream out) throws IOException;

    private void _writeSubNode(Writer out, XmlElement element, int indent) throws IOException;

    private void _writeSpace(Writer out, int numSpaces) throws IOException;
}
