package com.allenstudio.ir.util;

import java.util.StringTokenizer;
import java.util.Vector;

/*
 * @(#)InspirentoUtilities.java
 * Created on 2005-8-1
 * Inspirento, Copyright AllenStudio, All Rights Reserved
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

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
    public static String[] tokenize(String input) {
        Vector<String> v = new Vector<String>();
        StringTokenizer t = new StringTokenizer(input);
        String cmd[];

        while (t.hasMoreTokens())
            v.addElement(t.nextToken());
        cmd = new String[v.size()];
        for (int i = 0; i < cmd.length; i++)
            cmd[i] = v.elementAt(i);

        return cmd;
    }
    
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
    public static String[] tokenize(String input, String delim) {
        Vector<String> v = new Vector<String>();
        StringTokenizer t = new StringTokenizer(input, " \t\n\r\f" +
                delim);
        String cmd[];

        while (t.hasMoreTokens())
            v.addElement(t.nextToken());
        cmd = new String[v.size()];
        for (int i = 0; i < cmd.length; i++)
            cmd[i] = v.elementAt(i);

        return cmd;
    }
    
    /**
     * Replaces all characters that may
     * affect program's normal running in the string
     * with the escape strings in HTML.
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
    public static StringBuffer stringReplaceAll(StringBuffer source, char token,
        String replacement) {
        for (int i = 0; i < source.length(); i++) {
            if (source.charAt(i) == token) {
                source = source.replace(i, ++i, replacement);//++i?
            }
        }

        return source;
    }
}
