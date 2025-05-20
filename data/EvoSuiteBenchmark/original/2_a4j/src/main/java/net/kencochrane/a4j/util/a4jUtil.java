/*
Copyright (c) 2003, Ken Cochrane
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted
provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
    this list of conditions and the following disclaimer.

    * Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

    * Neither the name of Ken Cochrane nor the names of its contributors may be used to endorse
    or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/
package net.kencochrane.a4j.util;

//import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Properties;


/**
 * http://www.KenCochrane.net
 * Ken Cochrane
 * Date: May 31, 2003
 * Time: 6:00:08 PM
 *
 *
 */
public class a4jUtil {
    //Logger log = Logger.getLogger(this.getClass());
    public String URLFriendlyName(String name) {
        StringBuffer uString = new StringBuffer();
        Properties props = LoadProperties.instance().getProperties();
        String URLSeperator = props.getProperty("URLSeperator");
        String allowedChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-~|+";
        name = name.replaceAll(" ", URLSeperator);
        String tempString = stripString(allowedChars, name);
        uString.append(tempString);
        return uString.toString();
    }

    public String stripString(String allowedChars, String string) {
        StringBuffer returnString = new StringBuffer();
        String validString = allowedChars;
        char checkChar;
        for (int x = 0; x < string.length(); x++) {
            checkChar = string.charAt(x);
            if (validString.indexOf(checkChar) != -1) {
                returnString.append(checkChar);
            }
        }
        return returnString.toString();
    }

    public BigDecimal getPrice(String price) {
        String strippedString = null;
        String allowedString = ".0123456789";
        strippedString = stripString(allowedString, price);

        try {
            BigDecimal intPrice = new BigDecimal(Double.parseDouble(strippedString));
            intPrice = intPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
            return intPrice;
        } catch (Exception e) {
            // log.error("error = " + e.toString());
            return new BigDecimal(0.00);
        }

    }

    public String arrayToCommaString(ArrayList list) {
        StringBuffer out = new StringBuffer();
        if (list != null && list.size() > 0) {
            String item;
            for (int y = 0; y < list.size(); y++) {
                if (list.get(y) != null) {
                    item = (String) list.get(y);
                    out.append(item);
                    if (y != list.size() - 1) {
                        out.append(", ");
                    }
                }
            }
        }
        return out.toString();
    }

    public String encodeString(String searchTerm) {
        try {
            searchTerm = URLEncoder.encode(searchTerm, "UTF-8");
        } catch (UnsupportedEncodingException ue) {
            //    log.debug("Encoding Scheme isn't available");
            searchTerm = URLEncoder.encode(searchTerm);
        }
        return searchTerm;
    }

    public String dencodeString(String searchTerm) {
        try {
            searchTerm = URLDecoder.decode(searchTerm, "UTF-8");
        } catch (UnsupportedEncodingException ue) {
            //     log.debug("Decoding Scheme isn't available");
            searchTerm = URLDecoder.decode(searchTerm);
        }
        return searchTerm;
    }

}

