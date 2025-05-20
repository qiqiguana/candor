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

import org.apache.http.HttpVersion;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

/**
 *
 * @author vlad
 */
public class InitBasicParams {

    /*
     * Initialize basic HTTP params
     *
     */
    void initHttpParams(HttpParams httpParams, HttpAnalyzerView mainView) {
        httpParams = new BasicHttpParams();
        ConnManagerParams.setTimeout(httpParams, 300);
        ConnManagerParams.setMaxTotalConnections(httpParams, 100);
        if (mainView.httpVersionComboBox.getSelectedIndex() == 0) {
            HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        } else {
            HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_0);
        }
        HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
        HttpProtocolParams.setUseExpectContinue(httpParams, false);
    }

    /**
     * Get proxy settings from enviroment
     * Search http_proxy variable
     * @return Proxy_Setting
     */
    public String getProxyEnv() {
        String proxyEnv = System.getenv("http_proxy");
        String proxyString = null;
        if (proxyEnv != null) {
            proxyString = proxyEnv.substring(7);
        }
        return proxyString;
    }
}
