package net.sourceforge.jwbf.mediawiki.actions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Thomas Stock
 */
public final class MediaWiki {

    /**
     * @param s a
     * @return decoded s
     */
    public static String decode(final String s) {
        String out = HTMLEntities.unhtmlentities(s);
        out = HTMLEntities.unhtmlQuotes(out);
        return out;
    }
}

/**
 * Collection of static methods to convert special and extended
 * characters into HTML entitities and vice versa.<br/><br/>
 * Copyright (c) 2004-2005 Tecnick.com S.r.l (www.tecnick.com) Via Ugo Foscolo
 * n.19 - 09045 Quartu Sant'Elena (CA) - ITALY - www.tecnick.com -
 * info@tecnick.com<br/>
 * Project homepage: <a href="http://htmlentities.sourceforge.net" target="_blank">http://htmlentities.sourceforge.net</a>
 * <br/>
 *
 * License: http://www.gnu.org/copyleft/lesser.html LGPL
 *
 * @author Nicola Asuni [www.tecnick.com].
 * @version 1.0.004
 */
final class HTMLEntities {

    /**
     * Convert HTML entities to special and extended unicode characters
     * equivalents.
     *
     * @param str input string
     * @return formatted string
     * @see #htmlentities(String)
     */
    public static String unhtmlentities(String str);

    /**
     * Replace single and double quotes HTML entities with equivalent characters.
     *
     * @param str the input string
     * @return string with replaced quotes
     */
    public static String unhtmlQuotes(String str);
}
