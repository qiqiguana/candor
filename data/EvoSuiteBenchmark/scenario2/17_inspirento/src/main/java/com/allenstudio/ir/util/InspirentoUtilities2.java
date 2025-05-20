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
     * Utility method to replace all the specified <code>token</code> in
     * string <code>source</code> with the specified <code>
     * replacement</code>.
     *
     * @param source the source string
     * @param token the token to be replaced
     * @param replacement the string used to replace the token
     * @return a <code>StringBuffer</code> object that contains
     *         the modified string
     * @see java.lang.StringBuffer#replace(int, int, java.lang.String)
     */
    public static StringBuffer stringReplaceAll(StringBuffer source, char token, String replacement) {
        for (int i = 0; i < source.length(); i++) {
            if (source.charAt(i) == token) {
                //++i?
                source = source.replace(i, ++i, replacement);
            }
        }
        return source;
    }
}
