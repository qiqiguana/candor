package com.allenstudio.ir.util;

import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Wraps some static utility method employed
 * in Inspirento.
 *
 * @author Allen Chue
 */
public class InspirentoUtilities {

    /**
     * Replaces all characters that may
     * affect program's normal running in the string
     * with the escape strings in HTML.
     *
     * @param str source string
     * @return a string which is processed
     */
    public static String escapeText(String str) {
        StringBuffer buffer = new StringBuffer(str);
        stringReplaceAll(buffer, '&', "&amp;");
        stringReplaceAll(buffer, '<', "&lt;");
        stringReplaceAll(buffer, '>', "&gt;");
        stringReplaceAll(buffer, '"', "&quot;");
        stringReplaceAll(buffer, '\'', "&apos;");
        return buffer.toString();
    }

    /**
     * Utility method to replace all the specified <code>token</code> in
     * string <code>source</code> with the specified <code>
     * replacement</code>.
     * @param source the source string
     * @param token the token to be replaced
     * @param replacement the string used to replace the token
     * @return a <code>StringBuffer</code> object that contains
     *         the modified string
     * @see java.lang.StringBuffer#replace(int, int, java.lang.String)
     */
    public static StringBuffer stringReplaceAll(StringBuffer source, char token, String replacement);
}
