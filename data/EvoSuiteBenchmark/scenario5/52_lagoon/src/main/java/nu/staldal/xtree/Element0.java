package nu.staldal.xtree;

import java.util.Vector;
import java.net.URL;
import org.xml.sax.*;
import org.xml.sax.helpers.AttributesImpl;

/**
 * An XML Element.
 */
public class Element extends NodeWithChildren {

    /**
     * Lookup the index of an attribute to this element. The returned index
     * may be used as argument to other methods in this class.
     *
     * @param namespaceURI the namespace URI, may be the empty string
     * @param localName the name
     * @return the index of the attribute, or -1 if no such attribute exists
     * @see #getAttributeValue
     * @see #getAttributeType
     * @see #removeAttribute
     */
    public int lookupAttribute(String namespaceURI, String localName) {
        return attrName.indexOf(localName + '^' + namespaceURI);
    }
}
