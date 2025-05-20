package nu.staldal.lagoon.util;

import java.util.*;

/**
 * Wildcard processing methods. All methods in this class are static.
 *
 * A wildcard pattern consists of one or more simple patterns, separated by ';'.
 * A simple pattern may contain one '*' which match any string
 * (including the empty string).
 */
public final class Wildcard {

    public static String matchWildcard(String pattern, String filename) {
        StringTokenizer st = new StringTokenizer(pattern, ";");
        while (st.hasMoreTokens()) {
            String pat = st.nextToken();
            int star = pat.indexOf('*');
            if (star < 0) {
                if (pat.equals(filename))
                    return "";
            } else {
                if (!filename.startsWith(pat.substring(0, star)))
                    continue;
                if (!filename.endsWith(pat.substring(star + 1)))
                    continue;
                return filename.substring(star, star + filename.length() - pat.length() + 1);
            }
        }
        return null;
    }
}
