package httpanalyzer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * @author vlad
 */
public class ParamsUrlTools {

    /**
     * Transform long String line with parameters
     * to List (key, value)
     *
     * @param params String
     * @return List<NameValuePair>
     */
    public List<NameValuePair> getPairParams(String params) {
        String[] requestParams = params.split("&");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (int i = 0; i < requestParams.length; i++) {
            String workVal = requestParams[i];
            System.out.println("Val =" + workVal + "=");
            if (!workVal.isEmpty()) {
                int f_index = requestParams[i].indexOf("=");
                String key = requestParams[i].substring(0, f_index);
                String val = requestParams[i].substring(f_index + 1);
                //System.out.println("i="+i+", string="+requestParams[i]);
                System.out.println("key = " + key + ", val=" + val);
                nvps.add(new BasicNameValuePair(key, val));
            }
        }
        System.out.println("");
        return nvps;
    }
}
