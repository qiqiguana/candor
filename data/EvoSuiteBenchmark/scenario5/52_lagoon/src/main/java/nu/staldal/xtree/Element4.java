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
     * Get the type of the attribute at the specified index.
     *
     * The attribute type is one of the strings
     * "CDATA", "ID", "IDREF", "IDREFS", "NMTOKEN", "NMTOKENS",
     * "ENTITY", "ENTITIES", or "NOTATION" (always in upper case).
     *
     * @return the attribute type,
     *         or <code>null</code> if index is -1
     * @param index the index as returned from {@link #lookupAttribute}
     * @throws IndexOutOfBoundsException if no such attribute exist.
     */
    public String getAttributeType(int index) throws IndexOutOfBoundsException {
        if (index == -1)
            return null;
        return (String) attrType.elementAt(index);
    }
}
