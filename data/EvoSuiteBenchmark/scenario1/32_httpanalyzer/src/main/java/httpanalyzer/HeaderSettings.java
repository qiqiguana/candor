package httpanalyzer;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @author vlad
 */
public class HeaderSettings {

    public static Header[] setHeaders() {
        return headersSet;
    }
}
