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
     * Tokenizes a input string to produce
     * an array of type <code>String</code>.<br>
     * This method is used in building menus and
     * toolbar using a resource file. For instance,
     * the following code will get an array of contents
     * {"file", "edit", "view"}:
     * <pre>
     * String str = "file edit view";
     * String[] array = tokenize(str);
     * </pre>
     *
     * @param input the input <code>String</code>
     * @return an String array containing all tokens
     * @see java.util.StringTokenizer
     */
    public static String[] tokenize(String input);
}
