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
     * Reads an XML stream from the specified InputStream and returns a
     * JDOM Document object. If validation is requested, then the XML is
     * validated while reading it into a Document.
     *
     * @param istream the InputStream to read from.
     * @param validate XML is validated against schema if true.
     * @param validationProperties a Map of name value pairs specifying
     * the schema and the namespaces to validate against.
     * @exception IFXExveption if there was a problem reading the document.
     */
    public static Document read(InputStream istream, boolean validate, Map validationProperties) throws IFXException;
}
