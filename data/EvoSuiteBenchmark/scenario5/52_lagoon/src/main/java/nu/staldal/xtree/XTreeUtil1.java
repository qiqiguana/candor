package nu.staldal.xtree;

import java.util.*;
import java.io.*;
import java.net.URL;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.sax.*;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.*;
import org.iso_relax.verifier.*;
import nu.staldal.xmlutil.ContentHandlerFixer;

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
     * Obtain the root Element
     *
     * @throws IllegalStateException if the SAX events received so far
     * doesn't constitues a well-formed XML document.
     */
    public Element getRootElement() throws IllegalStateException;
}

/**
 * Some utility methods for XTree. All methods in this class are static.
 */
public final class XTreeUtil {

    /**
     * Parse an XML document into a list of XTree:s, one for each element under the root.
     * Uses JAXP to find a parser and JARV to find a validator.
     * Will not support xml:base.
     *
     * @param xmlInput the input to parse
     * @param validateDTD validate using DTD
     * @param schemaType the type of schema to use, or <code>null</code>
     *                    for no schema validation
     * @param schema the schema to use, or <code>null</code>
     *                    for no schema validation
     * @param handler handler to invoke for each element
     * @return the root element (without any children)
     * @throws SAXParseException if the XML data is not valid
     * @throws SAXException if any other error occurs while parsing the XML data
     * @throws IOException if there was some I/O error while reading the input.
     */
    public static Element parseXMLSequential(InputSource xmlInput, boolean validateDTD, String schemaType, InputSource schema, ElementHandler handler) throws SAXParseException, SAXException, IOException {
        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            parserFactory.setNamespaceAware(true);
            parserFactory.setValidating(validateDTD);
            XMLReader xmlReader = parserFactory.newSAXParser().getXMLReader();
            SequentialTreeBuilder tb = new SequentialTreeBuilder(handler);
            if (schema != null) {
                VerifierFactory vf = VerifierFactory.newInstance(schemaType);
                Verifier verifier = vf.newVerifier(schema);
                VerifierFilter filter = verifier.getVerifierFilter();
                filter.setParent(xmlReader);
                xmlReader = filter;
            }
            xmlReader.setContentHandler(tb);
            xmlReader.setErrorHandler(tb);
            xmlReader.parse(xmlInput);
            if ((schema != null) && !((VerifierFilter) xmlReader).isValid()) {
                throw new SAXParseException("Invalid XML data", null, null, -1, -1);
            }
            return tb.getRootElement();
        } catch (javax.xml.parsers.ParserConfigurationException e) {
            throw new Error("XML parser configuration error: " + e.getMessage());
        } catch (VerifierConfigurationException e) {
            throw new Error("XML verifier configuration error: " + e.getMessage());
        }
    }
}
