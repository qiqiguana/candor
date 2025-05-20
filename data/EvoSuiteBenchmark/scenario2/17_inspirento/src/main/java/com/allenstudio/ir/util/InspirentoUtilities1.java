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
}
