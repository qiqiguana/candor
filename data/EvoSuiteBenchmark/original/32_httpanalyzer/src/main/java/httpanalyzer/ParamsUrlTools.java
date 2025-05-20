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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author vlad
 */
public class ParamsUrlTools {
    /**
     * Transform long String line with parameters
     * to List (key, value)
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
    /**
     * Get URL and check it. Apply HTTP scheme only.
     * Cut URL on some parts and send them.
     * Return String array = {host, port, path, checkedUrl}
     * @return String[]
     */
    public String[] splitUrl(String startUrl, JFrame parentFrame) {
        String [] returnParams = new String[4];
        // Verify format of URL.
        URI verifiedUrl = null;
        try {
            verifiedUrl = new URI(startUrl);
            returnParams[0] = verifiedUrl.getHost();
            returnParams[1] = Integer.toString(verifiedUrl.getPort());
            returnParams[2] = verifiedUrl.getPath();
            String urlSchema = verifiedUrl.getScheme();
            returnParams[3] = verifiedUrl.toASCIIString();
            /*String urlPath = verifiedUrl.getPath();
            System.out.println("Host ="+targetHost);
            System.out.println("Path ="+urlPath);
            System.out.println("Checked ="+checkedUrl);
            System.out.println("Query ="+verifiedUrl.getQuery());
            System.out.println("Checked ="+verifiedUrl.getScheme());*/
            if (!urlSchema.startsWith("http")) {
                
                String message = "Sorry,\n but HTTP(S) protocols are permited only";
                new SwingTools(parentFrame).showErrorDialog("URL Error", message);
            }
        } catch (URISyntaxException ex) {
            String message = startUrl + "\n is not valid.";
            new SwingTools(parentFrame).showErrorDialog("URL Error", message);
        }
        return returnParams;
    }
}
