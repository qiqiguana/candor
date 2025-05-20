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
     * Builds a JDOM Document object from an IFXObject.
     *
     * @param obj the IFXObject object to format.
     * @param namespacePrefix the namespace prefix to use in the output. A
     * value of null implies no prefix. If the namespaceURI is specified it
     * will be treated as the default namespace.
     * @param namespaceURI the namespace URI to use in the output. If specified
     * an xmlns declaration will be prepended to the output.
     * @return a JDOM Document object.
     * @exception IFXException if there was a problem building the document.
     */
    public static Document build(IFXObject obj, String namespacePrefix, String namespaceURI) throws IFXException {
        IFXEncoder encoder = null;
        if (namespaceURI != null) {
            encoder = new IFXEncoder(namespacePrefix, namespaceURI);
        } else {
            encoder = new IFXEncoder();
        }
        Element element = encoder.encode(obj);
        Document doc = new Document(element);
        return doc;
    }
}

/**
 * Encodes an IFXObject to its equivalent IFX XML Element.
 *
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class IFXEncoder {

    /**
     * Builds a JDOM Element for the IFXObject supplied. No validation is
     * performed, regardless of the setting. To validate, use the encode()
     * methods instead.
     *
     * @param obj an IFXObject.
     * @return a JDOM Element.
     * @exception IFXException if an exception was encountered in encoding.
     */
    public Element encode(IFXObject obj) throws IFXException;
}
