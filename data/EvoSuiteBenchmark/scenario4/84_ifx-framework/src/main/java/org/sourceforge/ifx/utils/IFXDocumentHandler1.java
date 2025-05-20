package org.sourceforge.ifx.utils;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Iterator;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.sourceforge.ifx.basetypes.IFXObject;

/**
 * The IFXDocumentHandler provides methods for formatting and validating
 * IFX XML Documents.
 *
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class IFXDocumentHandler {

    /**
     * System default Line separator string.
     */
    public static final String DEFAULT_LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * Builds a JDOM Document object from an IFXObject.
     * @param obj the IFXObject object to format.
     * @param namespacePrefix the namespace prefix to use in the output. A
     * value of null implies no prefix. If the namespaceURI is specified it
     * will be treated as the default namespace.
     * @param namespaceURI the namespace URI to use in the output. If specified
     * an xmlns declaration will be prepended to the output.
     * @return a JDOM Document object.
     * @exception IFXException if there was a problem building the document.
     */
    public static Document build(IFXObject obj, String namespacePrefix, String namespaceURI) throws IFXException;

    /**
     * Writes a Document object to the specified output stream.
     * @param doc a JDOM Document object.
     * @param indentSize the size of the indent in the output. A value of
     * zero implies no indentation.
     * @param lineSeparator the line separator for the output. A value of
     * null implies no line separators and no newlines in the output. To use
     * the system default, use DEFAULT_LINE_SEPARATOR.
     * @param ostream the OutputStream to write the formatted output to.
     * @exception IFXException if there was a problem writing the Document.
     */
    public static void write(Document doc, int indentSize, String lineSeparator, OutputStream ostream) throws IFXException;

    /**
     * Parses a Document object into an IFXObject. This is a thin wrapper
     * over the IFXDecoder.decode() method.
     * @param doc the JDOM Document object to parse.
     * @exception IFXException if any problems were encountered in parsing.
     */
    public static IFXObject parse(Document doc) throws IFXException;

    /**
     * Reads an XML stream from the specified InputStream and returns a
     * JDOM Document object. If validation is requested, then the XML is
     * validated while reading it into a Document.
     * @param istream the InputStream to read from.
     * @param validate XML is validated against schema if true.
     * @param validationProperties a Map of name value pairs specifying
     * the schema and the namespaces to validate against.
     * @exception IFXExveption if there was a problem reading the document.
     */
    public static Document read(InputStream istream, boolean validate, Map validationProperties) throws IFXException;
}
