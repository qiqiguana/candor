package nu.staldal.xtree;

import java.util.Vector;
import java.net.URL;
import org.xml.sax.*;
import org.xml.sax.helpers.AttributesImpl;

/**
 * An XML Element.
 */
public class Element extends NodeWithChildren {

    public String getAttributeValue(int index) throws IndexOutOfBoundsException {
        if (index == -1)
            return null;
        return (String) attrValue.elementAt(index);
    }
}
