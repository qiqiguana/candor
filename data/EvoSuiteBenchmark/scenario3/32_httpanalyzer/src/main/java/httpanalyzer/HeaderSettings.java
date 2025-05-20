package httpanalyzer;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @author vlad
 */
public class HeaderSettings {

    /**
     * Send all internal headers
     */
    public static Header[] setHeaders();
}
