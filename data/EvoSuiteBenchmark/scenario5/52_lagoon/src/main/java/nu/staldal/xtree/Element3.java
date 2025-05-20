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
     * Get the name of the attribute at the specified index.
     *
     * @param index the index as returned from {@link #lookupAttribute}
     * @return the localName,
     *         or <code>null</code> if index is -1
     * @throws IndexOutOfBoundsException if no such attribute exist.
     */
    public String getAttributeLocalName(int index) throws IndexOutOfBoundsException {
        if (index == -1)
            return null;
        String s = (String) attrName.elementAt(index);
        return s.substring(0, s.indexOf('^'));
    }
}
