package net.sf.gaeappmanager.google;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

/**
 * Google login helper.
 *
 * Based on work of Cheers Geoff.
 *
 * http://groups.google.com/group/google-appengine
 * -java/browse_thread/thread/c96d4fff73117e1d?pli=1
 *
 * @author Alois Belaska
 */
public class LogonHelper {

    /**
     * Returns the ACSID string to be set as the Cookie field in the request
     * header.
     *
     * @param userid full gmail address for user
     * @param password password
     * @param source name of application requesting quota details
     * @return the ACSID field value
     * @throws Exception if any error occurs getting the ACSID
     */
    public static String loginToGoogleAppEngine(String userid, String password, String source) throws Exception;
}
