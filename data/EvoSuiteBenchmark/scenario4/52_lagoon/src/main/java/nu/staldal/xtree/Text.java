package nu.staldal.xtree;

import org.xml.sax.*;

/**
 * Character content in an XML document. This class is immutible.
 */
public class Text extends Node {

    static final long serialVersionUID = -128692223369356277L;

    final String value;

    transient char[] charArrayCache;

    /**
     * Constructs a text node from a char[] buffer.
     *
     * @param data  a char[] buffer
     * @param start  the offset to read from in the buffer
     * @param length  the number of characters to read from the buffer
     * @param forceCopy  force copying of the data, if false a reference
     *                   to the buffer may be keept.
     */
    public Text(char[] data, int start, int length, boolean forceCopy) {
    }

    /**
     * Constructs a text node from a String.
     *
     * @param value  the string
     */
    public Text(String value) {
    }

    /**
     * Get the charater content as a string
     */
    public String getValue();

    private void obtainCharArray();

    /**
     * Get the charater content as a char[].
     */
    public char[] asCharArray();

    public void toSAX(ContentHandler sax) throws SAXException;

    public boolean isWhitespaceNode();
}
