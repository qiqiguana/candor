package httpanalyzer;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @author vlad
 */
public class HeaderSettings {

    public static String[] userAgents = { "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)", "msnbot/1.1 (+http://search.msn.com/msnbot.htm)", "Mozilla/5.0 (compatible; Yahoo! Slurp; http://help.yahoo.com/help/us/ysearch/slurp)", "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16" };

    public static final int FIREFOX = 0;

    public static final int IE6 = 1;

    public static final int IE7 = 2;

    public static final int IE8 = 3;

    public static final int GOOGLEBOT = 4;

    public static final int MSNBOT = 5;

    public static final int YAHOOBOT = 6;

    public static final int IPHONE = 7;

    public static final int CUSTOM = 255;

    final String contentCharset = "utf-8";

    String setUserAgent;

    private static String presetCookie;

    private static Header[] headersSet;

    /**
     * Get User-Agent&Referer and set
     * internal headers
     */
    public static void initHeaders(int idAgent, String referer);

    /**
     * Get all headers from user from
     * and set internal headers
     */
    public static void initHeaders(String myHeaders);

    /**
     * Send all internal headers
     */
    public static Header[] setHeaders();

    /**
     * Set internal header Cookie:
     */
    public static void setCookie(String cookie);

    /**
     * Disable headers Cookie
     */
    public static void clearCookie();

    public static String getHeaders();

    public static String showHeaders(HttpRequestBase baseRequest);

    public static String showHeaders(Header[] headers);
}
