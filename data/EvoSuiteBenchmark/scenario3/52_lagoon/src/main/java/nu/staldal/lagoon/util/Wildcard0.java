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
     * Check if a wildcard pattern can possibly match more than one filename.
     *
     * @param pattern the wildcard pattern to test
     */
    public static boolean isWildcard(String pattern);
}
