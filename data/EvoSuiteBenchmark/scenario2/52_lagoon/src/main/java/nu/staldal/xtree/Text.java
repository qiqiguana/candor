package nu.staldal.xtree;

import org.xml.sax.*;

/**
 * Character content in an XML document. This class is immutible.
 */
public class Text extends Node {

    /**
     * Get the charater content as a char[].
     */
    public char[] asCharArray() {
        obtainCharArray();
        return charArrayCache;
    }
}
