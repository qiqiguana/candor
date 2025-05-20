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
     * @param input the input <code>String</code>
     * @return an String array containing all tokens
     * @see java.util.StringTokenizer
     */
    public static String[] tokenize(String input);

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
     * <br>This method offers customized delim.
     * @param input the input <code>String</code>
     * @return an String array containing all tokens
     * @see java.util.StringTokenizer
     */
    public static String[] tokenize(String input, String delim);

    /**
     * Replaces all characters that may
     * affect program's normal running in the string
     * with the escape strings in HTML.
     * @param str source string
     * @return a string which is processed
     */
    public static String escapeText(String str);

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
