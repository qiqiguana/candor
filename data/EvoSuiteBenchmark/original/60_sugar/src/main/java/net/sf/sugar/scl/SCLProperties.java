package net.sf.sugar.scl;

import static net.sf.sugar.scl.PathConversionUtils.convertToXPath;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.w3c.dom.*;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.net.URI;
import java.util.*;

/**
 * SCLProperties is java.util.Map implementation which reads SCL (Simple Configuration Language) files.
 * It is intended to be an alternative to the standard Java Properties file format, and provides the ability to
 * create properties files using a simple nested structure that makes the file more manageable and readable.
 * <p/>
 * <pre>
 * a {
 *   b: "value for b"
 *   c: "value for c"
 *   d {
 *     e: "value for e"
 *   } //single line comment
 * }
 * </pre>
 * User: kbishop
 * Date: 20-Jul-2008
 * Time: 00:59:48
 */
public class SCLProperties implements Map<String, String> {

    private Document doc;

    private final String allTextNodesExpr = "//*[count(./*) = 0]";

    private static final String DEFAULT_ENCODING = "UTF-8";

    public SCLProperties(String filename) throws SCLParseException, FileNotFoundException {
        this.load(new File(filename));
    }

    public SCLProperties(File sclFile) throws SCLParseException, FileNotFoundException {
        this.load(sclFile);
    }

//    public SCLProperties(InputStream inputStream) throws SCLParseException {
//        this();
//        this.load(inputStream);
//    }

    public SCLProperties(Reader reader) throws SCLParseException {
        this.load(reader);
    }

    public void load(File file) throws SCLParseException, FileNotFoundException {
        InputStream is = new FileInputStream(file);
        this.load(is, file.toURI());
    }

    public void load(InputStream inputStream, URI rootSCLFileLocation) throws SCLParseException {
        this.doc = doParse(inputStream, rootSCLFileLocation);
    }

    public void load(Reader reader) throws SCLParseException {
        this.doc = doParse(reader);
    }

    private static Document doParse(InputStream inputStream, URI rootSCLFileLocation) throws SCLParseException {
        try {
            SCLLexer lexer = new SCLLexer(new ANTLRInputStream(inputStream, DEFAULT_ENCODING));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SCLParser parser = new SCLParser(tokens);
            parser.setRootSCLFile(rootSCLFileLocation);
            parser.setIncludeProcessor(new DefaultIncludeProcessor(rootSCLFileLocation, parser.getRootDocument()));
            parser.setLocalReferenceProcessor(new DefaultLocalReferenceProcessor());

            return parser.scl();

        } catch (Exception e) {
            throw new SCLParseException("SCLParser unable to parse input", e);
        }
    }

    private static Document doParse(Reader reader) throws SCLParseException {
        try {
            SCLLexer lexer = new SCLLexer(new ANTLRReaderStream(reader));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SCLParser parser = new SCLParser(tokens);

            return parser.scl();

        } catch (Exception e) {
            throw new SCLParseException("SCLParser unable to parse input", e);
        }
    }

    public int size() {
        try {
            return ((Double) getXPath().evaluate("count(" + allTextNodesExpr + ")", this.doc, XPathConstants.NUMBER)).intValue();
        } catch (XPathExpressionException xpee) {
            return -1;
        }
    }

    public boolean isEmpty() {
        return this.size() < 1;
    }

    public boolean containsKey(Object key) {
        String xpr = convertToXPath((String) key);
        try {
            return (Boolean) getXPath().evaluate("count(" + xpr + ") > 0", this.doc, XPathConstants.BOOLEAN);
        } catch (XPathExpressionException e) {
            return false;
        }
    }

    public boolean containsValue(Object value) {
        try {
            return (Boolean) getXPath().evaluate(allTextNodesExpr + " = '" + (String) value + "'", this.doc, XPathConstants.BOOLEAN);
        } catch (XPathExpressionException e) {
            return false;
        }
    }

    public String get(Object key) {
        String xpr = convertToXPath((String) key);
        try {
            String result = (String) getXPath().evaluate(xpr + "/text()", this.doc, XPathConstants.STRING);
            return result.equals("") ? null : result;
        } catch (XPathExpressionException e) {
            return null;
        }
    }

    public String put(String key, final String value) {
        String[] elements = key.split("\\.");
        int step = 0;

        return this.createNode(elements, step, value, doc.getDocumentElement());
    }

    @SuppressWarnings({"AssignmentToMethodParameter"})
    private String createNode(String[] pathElements, int step, String value, Element currentElement) {

        NodeList children = currentElement.getElementsByTagName(pathElements[step]);
        Element nextElement;
        if (children.getLength() < 1) {
            nextElement = this.doc.createElement(pathElements[step]);
            currentElement.appendChild(nextElement);
        } else {
            nextElement = (Element) children.item(0);   //XXX we're assuming a constraint on SCL that node names are unique, amoung siblings
        }

        if (step == (pathElements.length - 1)) {
            String originalValue =
                    (nextElement.getTextContent() != null && !"".equals(nextElement.getTextContent()))
                            ? nextElement.getTextContent() : value;
            nextElement.setTextContent(value);
            return originalValue;
        }

        return createNode(pathElements, ++step, value, nextElement);
    }

    public String remove(Object key) {
        String xprr = convertToXPath((String) key);
        Element element;
        try {
            element = (Element) getXPath().evaluate(xprr, this.doc, XPathConstants.NODE);
            if (element != null) {
                Node parent = element.getParentNode();
                StringBuffer path = new StringBuffer();
                buildPropertyFullPath(element, path);
                parent.removeChild(element);
                return path.toString();
            }
            return null;
        } catch (XPathExpressionException e) {
            return null;
        }
    }

    public void putAll(Map<? extends String, ? extends String> m) {
        //todo 
    }

    public void clear() {
        NodeList childNodes = this.doc.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            this.doc.removeChild(childNodes.item(i));
        }
    }

    public Set<String> keySet() {
        NodeList nodes = this.getAttributes(this.doc);
        Set<String> keySet = new HashSet<String>();
        if (nodes != null) {
            for (int i = 0; i < nodes.getLength(); i++) {
                Node currentNode = (Element) nodes.item(i);
                StringBuffer path = new StringBuffer(currentNode.getNodeName());
                buildPropertyFullPath(currentNode, path);
                keySet.add(path.toString());
            }
        }
        return keySet;
    }

    private void buildPropertyFullPath(Node currentNode, StringBuffer path) {
        currentNode = currentNode.getParentNode();

        while (!currentNode.getNodeName().equals("scl")) {
            path.insert(0, currentNode.getNodeName() + ".");
            currentNode = currentNode.getParentNode();
        }
    }

    public Collection<String> values() {
        NodeList nodes = this.getAttributes(this.doc);
        Collection<String> values = new HashSet<String>();
        if (nodes != null) {
            for (int i = 0; i < nodes.getLength(); i++) {
                values.add(nodes.item(i).getNodeValue());
            }
        }
        return values;
    }

    public Set<Entry<String, String>> entrySet() {
        NodeList nodes = this.getAttributes(this.doc);
        Set<Entry<String, String>> entrySet = new HashSet<Entry<String, String>>();
        if (nodes != null) {
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = (Element) nodes.item(i);
                StringBuffer path = new StringBuffer(node.getNodeName());
                this.buildPropertyFullPath(node, path);
                entrySet.add(new AbstractMap.SimpleEntry<String, String>(path.toString(), node.getTextContent()));
            }
        }
        return entrySet;
    }

    protected NodeList getAttributes(Document doc) {
        try {
            return (NodeList) getXPath().evaluate(allTextNodesExpr, doc, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            return null;
        }
    }

    private XPath getXPath() {
        return XPathFactory.newInstance().newXPath();
    }

    public String toString() {
        return this.entrySet().toString();
    }
}
