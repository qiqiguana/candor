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
