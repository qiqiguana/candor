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

    public static String[] tokenize(String input) {
        Vector<String> v = new Vector<String>();
        StringTokenizer t = new StringTokenizer(input);
        String[] cmd;
        while (t.hasMoreTokens()) v.addElement(t.nextToken());
        cmd = new String[v.size()];
        for (int i = 0; i < cmd.length; i++) cmd[i] = v.elementAt(i);
        return cmd;
    }
}
