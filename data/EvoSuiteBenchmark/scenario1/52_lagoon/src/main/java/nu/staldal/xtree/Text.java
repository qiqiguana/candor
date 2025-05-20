package nu.staldal.xtree;

import org.xml.sax.*;

/**
 * Character content in an XML document. This class is immutible.
 */
public class Text extends Node {

    public char[] asCharArray() {
        obtainCharArray();
        return charArrayCache;
    }
}
