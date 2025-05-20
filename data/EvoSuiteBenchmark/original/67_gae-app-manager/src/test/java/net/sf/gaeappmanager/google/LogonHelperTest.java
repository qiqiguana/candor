package net.sf.gaeappmanager.google;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class LogonHelperTest {

	public static void main(String[] args) throws Exception {
		String authCookie = LogonHelper.loginToGoogleAppEngine(
				"alois.belaska@gmail.com", "", "eShops-WatchDog");

		DefaultHttpClient client = new DefaultHttpClient();

		try {
			HttpGet get = new HttpGet(
					"https://appengine.google.com/dashboard/quotadetails?&app_id=eshopsengine");
			get.setHeader("Cookie", "ACSID=" + authCookie);

			HttpResponse response = client.execute(get);

			LineNumberReader reader = new LineNumberReader(new BufferedReader(
					new InputStreamReader(response.getEntity().getContent())));
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
}
