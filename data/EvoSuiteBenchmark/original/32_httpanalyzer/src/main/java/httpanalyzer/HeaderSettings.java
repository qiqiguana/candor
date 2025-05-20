/*
 * GNU GENERAL PUBLIC LICENSE
 * Version 3, 29 June 2007
 * 
 * Copyright (C) 2010, vlad
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package httpanalyzer;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

/**
 *
 * @author vlad
 */
public class HeaderSettings {

    public static String userAgents[] = {
        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)",
        "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)",
        "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)",
        "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)",
        "msnbot/1.1 (+http://search.msn.com/msnbot.htm)",
        "Mozilla/5.0 (compatible; Yahoo! Slurp; http://help.yahoo.com/help/us/ysearch/slurp)",
        "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16"
    };
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
    public static void initHeaders(int idAgent, String referer) {
        HttpGet baseRequest = new HttpGet();
        baseRequest.addHeader("User-Agent", userAgents[idAgent]);
        baseRequest.addHeader("Accept", "text/html,application/xhtml+xml,"
                + "application/xml;q=0.9,*/*;q=0.8");
        baseRequest.addHeader("Referer", referer);
        baseRequest.addHeader("Connection", "Keep-Alive");
        baseRequest.addHeader("Keep-Alive", "300");
        if (presetCookie != null) {
            baseRequest.addHeader("Cookie", presetCookie);
        }
        headersSet = baseRequest.getAllHeaders();
    }
    /**
     * Get all headers from user from
     * and set internal headers
     */
    public static void initHeaders(String myHeaders) {
        HttpGet baseRequest = new HttpGet();
        String [] headersArray = myHeaders.split("\n");
        for (int i=0; i<headersArray.length; i++){
            if (!headersArray[i].isEmpty()) {
            int indexStr = headersArray[i].indexOf(":");
            String key = headersArray[i].substring(0, indexStr);
            String val = headersArray[i].substring(indexStr+1).trim();
            baseRequest.addHeader(key,val);
            System.out.println("Key ="+key+", val="+val);
            }
        }
        headersSet = baseRequest.getAllHeaders();
    }

    /**
     * Send all internal headers
     */
    public static Header[] setHeaders(){
        return headersSet;
    }

    /**
     * Set internal header Cookie:
     */
    public static void setCookie(String cookie) {
        presetCookie = cookie;
    }

    /**
     * Disable headers Cookie
     */
    public static void clearCookie() {
        presetCookie = null;
    }

    public static String getHeaders() {
        Header[] headers = headersSet;
        String headersString = null;
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < headers.length; i++) {
            strBuf.append(headers[i].toString() + "\n");
        }
        headersString = strBuf.toString();
        return headersString;
    }

    public static String showHeaders(HttpRequestBase baseRequest) {
        Header[] headers = baseRequest.getAllHeaders();
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < headers.length; i++) {
            strBuf.append(headers[i].toString() + "\n");
        }
        String headersString = strBuf.toString();
        return headersString;
    }

    public static String showHeaders(Header[] headers) {
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < headers.length; i++) {
            strBuf.append(headers[i].toString() + "\n");
        }
        String headersString = strBuf.toString();
        return headersString;
    }
}
