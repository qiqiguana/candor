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
     * Get the namespace URI for the attribute at the specified index.
     *
     * @param index the index as returned from {@link #lookupAttribute}
     * @return the namespace URI, may be (and is usually) the empty string,
     *         or <code>null</code> if index is -1
     * @throws IndexOutOfBoundsException if no such attribute exist.
     */
    public String getAttributeNamespaceURI(int index) throws IndexOutOfBoundsException {
        if (index == -1)
            return null;
        String s = (String) attrName.elementAt(index);
        return s.substring(s.indexOf('^') + 1);
    }
}
