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
