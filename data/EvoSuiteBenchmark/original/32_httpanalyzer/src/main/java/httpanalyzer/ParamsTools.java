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

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author vlad
 */
public class ParamsTools {

    public static List<NameValuePair> getPairParams(String params) {
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
