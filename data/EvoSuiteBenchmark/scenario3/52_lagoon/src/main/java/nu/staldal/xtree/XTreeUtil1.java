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
    public static Element parseXMLSequential(InputSource xmlInput, boolean validateDTD, String schemaType, InputSource schema, ElementHandler handler) throws SAXParseException, SAXException, IOException;
}
