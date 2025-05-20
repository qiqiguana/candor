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
    public List<NameValuePair> getPairParams(String params);
}
