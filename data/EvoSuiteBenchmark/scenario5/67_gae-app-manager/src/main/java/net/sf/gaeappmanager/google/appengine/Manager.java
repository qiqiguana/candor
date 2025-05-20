package net.sf.gaeappmanager.google.appengine;

import net.sf.gaeappmanager.google.LogonHelper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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

/**
 * Google App Engine application manager.
 *
 * @author Alois Belaska
 */
public class Manager {

    /**
     * Retrieve quota details of application deployed in Google App Engine.
     *
     * @param userid full gmail address for user
     * @param password gmail account password
     * @param source name of application requesting quota details
     * @param application appspot application name
     * @return quota details of application
     * @throws Exception in case of failure
     */
    public static QuotaDetails retrieveAppQuotaDetails(String userid, String password, String source, String application) throws Exception {
        String authCookie = LogonHelper.loginToGoogleAppEngine(userid, password, source);
        DefaultHttpClient client = new DefaultHttpClient();
        try {
            HttpGet get = new HttpGet("https://appengine.google.com/dashboard/quotadetails?&app_id=" + application);
            get.setHeader("Cookie", "ACSID=" + authCookie);
            HttpResponse response = client.execute(get);
            return new QuotaDetailsParser().parse(response.getEntity().getContent());
        } finally {
            client.getConnectionManager().shutdown();
        }
    }
}
