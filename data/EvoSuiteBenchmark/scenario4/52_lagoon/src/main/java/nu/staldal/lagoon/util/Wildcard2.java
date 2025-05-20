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

    /**
     * Private default constructor to prevent instantiation.
     */
    private Wildcard() {
    }

    /**
     * Check if a wildcard pattern can possibly match more than one filename.
     *
     * @param pattern  the wildcard pattern to test
     */
    public static boolean isWildcard(String pattern);

    /**
     * Attempt to match a filename matches to wildcard pattern.
     *
     * @param pattern   the wildcard pattern
     * @param filename  the filename
     *
     * @return the string substituted into the pattern,
     *         or null if no match could be made
     */
    public static String matchWildcard(String pattern, String filename);

    /**
     * Instantiate a wildcard pattern to a filename.
     *
     * @param pattern   the wildcard pattern
     * @param part      the string to substitue into the pattern
     *                  (returned from matchWildcard)
     *
     * @return  the filename
     *
     * @see #matchWildcard
     */
    public static String instantiateWildcard(String pattern, String part);
}
