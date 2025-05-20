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
     * Get the value of the attribute at the specified index.
     *
     * @return the attribute value,
     *         or <code>null</code> if index is -1
     * @param index the index as returned from {@link #lookupAttribute}
     * @throws IndexOutOfBoundsException if no such attribute exist.
     */
    public String getAttributeValue(int index) throws IndexOutOfBoundsException {
        if (index == -1)
            return null;
        return (String) attrValue.elementAt(index);
    }
}
