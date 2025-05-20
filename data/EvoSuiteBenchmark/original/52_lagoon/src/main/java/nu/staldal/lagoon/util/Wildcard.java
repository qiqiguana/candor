/*
 * Copyright (c) 2001, Mikael Ståldal
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the author nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 * Note: This is known as "the modified BSD license". It's an approved
 * Open Source and Free Software license, see
 * http://www.opensource.org/licenses/
 * and
 * http://www.gnu.org/philosophy/license-list.html
 */

package nu.staldal.lagoon.util;

import java.util.*;

/**
 * Wildcard processing methods. All methods in this class are static.
 *
 * A wildcard pattern consists of one or more simple patterns, separated by ';'.
 * A simple pattern may contain one '*' which match any string 
 * (including the empty string).
 */
public final class Wildcard
{
	/**
	 * Private default constructor to prevent instantiation.
	 */
	private Wildcard() {}

    /**
     * Check if a wildcard pattern can possibly match more than one filename.
     *
     * @param pattern  the wildcard pattern to test
     */
    public static boolean isWildcard(String pattern)
    {
        return (pattern.indexOf('*') >= 0) || (pattern.indexOf(';') >= 0);
    }

    /**
     * Attempt to match a filename matches to wildcard pattern.
     *
     * @param pattern   the wildcard pattern
     * @param filename  the filename
     *
     * @return the string substituted into the pattern,
     *         or null if no match could be made
     */
    public static String matchWildcard(String pattern, String filename)
    {
        StringTokenizer st = new StringTokenizer(pattern, ";");

        while (st.hasMoreTokens())
        {
            String pat = st.nextToken();
            int star = pat.indexOf('*');

            if (star < 0)
            {
                if (pat.equals(filename)) return "";
            }
            else
            {
                if (!filename.startsWith(pat.substring(0, star)))
                    continue;

                if (!filename.endsWith(pat.substring(star+1)))
                    continue;

                return filename.substring(star,
                    star+filename.length()-pat.length()+1);
            }
        }

        return null;
    }

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
    public static String instantiateWildcard(String pattern, String part)
    {
        int star = pattern.indexOf('*');

        if (star < 0)
            return pattern;

        return pattern.substring(0, star) + part + pattern.substring(star+1);
    }
}
