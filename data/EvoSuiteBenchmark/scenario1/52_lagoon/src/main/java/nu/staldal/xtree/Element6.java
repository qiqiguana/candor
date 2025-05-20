package nu.staldal.xtree;

import java.util.Vector;
import java.net.URL;
import org.xml.sax.*;
import org.xml.sax.helpers.AttributesImpl;

/**
 * An XML Element.
 */
public class Element extends NodeWithChildren {

    public int numberOfNamespaceMappings() {
        return namespacePrefixes.size();
    }
}
