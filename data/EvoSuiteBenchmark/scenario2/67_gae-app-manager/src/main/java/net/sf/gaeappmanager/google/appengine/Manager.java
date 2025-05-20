package net.sf.gaeappmanager.google.appengine;

import net.sf.gaeappmanager.google.LogonHelper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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
