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
     * @return encoded s
     */
    public static String encode(String s) {
        try {
            return URLEncoder.encode(s, MediaWiki.CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
