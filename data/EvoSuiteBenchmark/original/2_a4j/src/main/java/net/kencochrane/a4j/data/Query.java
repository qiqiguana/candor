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

/**
 * http://www.KenCochrane.net
 * Ken Cochrane
 * Date: May 23, 2003
 * Time: 9:18:23 AM
 *
 *  Some of these methods are from the Amazon.com AWS Java examples that they include when you
 *  download the AWS bundle.
 */
package net.kencochrane.a4j.data;

//import org.apache.log4j.Logger;

import net.kencochrane.a4j.util.LoadProperties;
import net.kencochrane.a4j.util.a4jUtil;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Properties;

public class Query {
    //   Logger log = Logger.getLogger(this.getClass());

    protected String serverURL,
    associatesID,
    token = "DSB0XDDW1GQ3S", //don't change A4J won't work without this. Used for tracking tool use.
    searchType,
    type,
    page,
    offer;
    protected ArrayList searchValues;

    public Query() {
        Properties props = LoadProperties.instance().getProperties();
        this.serverURL = props.getProperty("amazonServerURL");
        this.associatesID = props.getProperty("associateID");
    }

    a4jUtil jawsUtil = new a4jUtil();

    /*****************************************************************************
     Method name: generateMultipleSearchString
     Function: used in AsinSearch only (since AsinSearch allows multiple searches)
     add ',' in between each Asin number
     Input Parameters: search type (String)
     asin numbers (ArrayList)
     Return Values: one string containing all asin numbers (String)
     *****************************************************************************/
    protected String generateMultipleSearchString(String searchType, ArrayList searchValues) {
        //	log.debug("genertateMulti in");
        StringBuffer resultString = new StringBuffer();
        //     log.debug("Generatemulti before if");
        // log.debug("searchValues = " + searchValues.isEmpty());
        if (searchType.equals("AsinSearch") && (searchValues != null) && !searchValues.isEmpty()) {
            //        log.debug("Generatemulti if");
            resultString.append(searchValues.get(0));
            for (int i = 1; i < searchValues.size(); i++) {
                resultString.append(",");
                resultString.append(searchValues.get(i));
            }
        }
        // other search methods don't allow multiple entries
        else {
            //        log.debug("Generatemulti else");
            resultString.append(searchValues.get(0));
        }
        //    log.debug("Generatemulti out");
        return new String(resultString);
    }

    /*****************************************************************************
     Method name: queryGenerator
     Function: generate the URI (query) for XML request
     Input Parameters: N/A
     Return Values: the URI string (String)
     *****************************************************************************/
    public String queryGenerator(String searchType, String type, String page, String offer, ArrayList searchValues) {
        //    log.debug("queryGenerator - in");
        StringBuffer buffer = new StringBuffer();
        buffer.append(serverURL);
        buffer.append("?");
        buffer.append("t=");
        buffer.append(associatesID);
        buffer.append("&");
        buffer.append("dev-t=");
        buffer.append(token);
        buffer.append("&");
        buffer.append(searchType);
        buffer.append("=");
        buffer.append(generateMultipleSearchString(searchType, searchValues));
        buffer.append("&");
        buffer.append("type=");
        buffer.append(type);
        buffer.append("&");
        buffer.append("offerpage=");
        buffer.append(page);
        buffer.append("&");
        buffer.append("offer=");
        buffer.append(offer);
        buffer.append("&");
        buffer.append("f=xml");
        //      log.debug("queryGenerator - out");
        return new String(buffer);
    }

    /*****************************************************************************
     Method name: sendRequest
     Function: send the request to server (both AsinSearch and Exchange
     use this method)
     Input Parameters: the URI string (String)
     Return Values: the response string from server (String)
     *****************************************************************************/
    public String sendRequest(String string) throws Exception {
        //        log.debug("sendRequest");
        // send the request through URL
        URL url = new URL(string);

        // receive the response
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        DataInputStream in = new DataInputStream(urlConnection.getInputStream());
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        byte[] buffer = new byte[100];
        do {
            int available = in.read(buffer);
            if (available == -1) break;
            bo.write(buffer, 0, available);
        } while (true);
        return bo.toString();
    }

    public String browseNodeQueryGenerator(String type, String page, String offer, String mode, String browseNode) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(serverURL);
        buffer.append("?");
        buffer.append("t=");
        buffer.append(associatesID);
        buffer.append("&");
        buffer.append("dev-t=");
        buffer.append(token);
        buffer.append("&");
        buffer.append("BrowseNodeSearch=");
        buffer.append(browseNode);
        buffer.append("&");
        buffer.append("mode=");
        buffer.append(mode);
        buffer.append("&");
        buffer.append("type=");
        buffer.append(type);
        buffer.append("&");
        buffer.append("page=");
        buffer.append(page);
        buffer.append("&");
        buffer.append("offer=");
        buffer.append(offer);
        buffer.append("&");
        buffer.append("f=xml");
        //     log.debug("URL = " + buffer.toString());
        return new String(buffer);
    }

    public String BlendedSearchGenerator(String type, String searchTerm) {
        StringBuffer buffer = new StringBuffer();

        searchTerm = jawsUtil.encodeString(searchTerm);

        buffer.append(serverURL);
        buffer.append("?");
        buffer.append("t=");
        buffer.append(associatesID);
        buffer.append("&");
        buffer.append("dev-t=");
        buffer.append(token);
        buffer.append("&");
        buffer.append("BlendedSearch=");
        buffer.append(searchTerm);
        buffer.append("&");
        buffer.append("type=");
        buffer.append(type);
        buffer.append("&");
        buffer.append("f=xml");
        //     log.debug("URL = " + buffer.toString());
        return new String(buffer);
    }

    public String KeywordSearchGenerator(String searchTerm, String productLine, String type, String page) {
        StringBuffer buffer = new StringBuffer();

        searchTerm = jawsUtil.encodeString(searchTerm);

        buffer.append(serverURL);
        buffer.append("?");
        buffer.append("t=");
        buffer.append(associatesID);
        buffer.append("&");
        buffer.append("dev-t=");
        buffer.append(token);
        buffer.append("&");
        buffer.append("KeywordSearch=");
        buffer.append(searchTerm);
        buffer.append("&");
        buffer.append("mode=");
        buffer.append(productLine);
        buffer.append("&");
        buffer.append("type=");
        buffer.append(type);
        buffer.append("&");
        buffer.append("page=");
        buffer.append(page);
        buffer.append("&");
        //buffer.append("offer="); buffer.append(offer); buffer.append("&"); // offer?
        buffer.append("f=xml");
        //      log.debug("URL = " + buffer.toString());
        return new String(buffer);
    }

    public String SearchQueryGenerator(String searchType, String searchTerm, String mode, String type, String page, String offer) {
//      log.debug("SearchQueryGenerator - in");
        StringBuffer buffer = new StringBuffer();

        searchTerm = jawsUtil.encodeString(searchTerm);

        buffer.append(serverURL);
        buffer.append("?");
        buffer.append("t=");
        buffer.append(associatesID);
        buffer.append("&");
        buffer.append("dev-t=");
        buffer.append(token);
        buffer.append("&");
        buffer.append(searchType);
        buffer.append("=");
        buffer.append(searchTerm);
        buffer.append("&");
        buffer.append("mode=");
        buffer.append(mode);
        buffer.append("&");
        buffer.append("type=");
        buffer.append(type);
        buffer.append("&");
        buffer.append("page=");
        buffer.append(page);
        buffer.append("&");
        buffer.append("offer=");
        buffer.append(offer);
        buffer.append("&");
        buffer.append("f=xml");
//     log.debug("SearchQueryGenerator - out");
        return new String(buffer);
    }

    public String SearchThirdPartyGenerator(String sellerId, String type, String page, String status) {
        //      log.debug("SearchQueryGenerator - in");
        StringBuffer buffer = new StringBuffer();
        buffer.append(serverURL);
        buffer.append("?");
        buffer.append("t=");
        buffer.append(associatesID);
        buffer.append("&");
        buffer.append("dev-t=");
        buffer.append(token);
        buffer.append("&");
        buffer.append("SellerSearch=");
        buffer.append(sellerId);
        buffer.append("&");
        buffer.append("type=");
        buffer.append(type);
        buffer.append("&");
        buffer.append("page=");
        buffer.append(page);
        buffer.append("&");
        buffer.append("offerstatus=");
        buffer.append(status);
        buffer.append("&");
        buffer.append("f=xml");
        //     log.debug("SearchQueryGenerator - out");
        return new String(buffer);
    }

    public String AddtoCart(String ASIN, String quantity) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(serverURL);
        buffer.append("?");
        buffer.append("ShoppingCart=add&f=xml&dev-t=");
        buffer.append(token);
        buffer.append("&t=");
        buffer.append(associatesID);
        buffer.append("&Asin.");
        buffer.append(ASIN);
        buffer.append("=");
        buffer.append(quantity);
        return new String(buffer);
    }

    public String AddToExistingCart(String ASIN, String quantity, String cartId, String hmac) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(serverURL);
        buffer.append("?");
        buffer.append("ShoppingCart=add&f=xml&dev-t=");
        buffer.append(token);
        buffer.append("&t=");
        buffer.append(associatesID);
        buffer.append("&Asin.");
        buffer.append(ASIN);
        buffer.append("=");
        buffer.append(quantity);
        buffer.append("&CartId=");
        buffer.append(cartId);
        buffer.append("&Hmac=");
        buffer.append(jawsUtil.encodeString(hmac));
        return new String(buffer);
        /*
          http://xml.amazon.com/onca/xml3?ShoppingCart=add&f=xml&
          dev-t=[ [developer's token goes here]
          &t=[associates ID goes here]
          &Asin.[ASIN goes here]=[quantity goes here]
          &CartId=[cart ID goes here] &Hmac=[HMAC goes here]
        */
    }

    public String ClearCart(String cartId, String hmac) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(serverURL);
        buffer.append("?");
        buffer.append("ShoppingCart=clear&f=xml&dev-t=");
        buffer.append(token);
        buffer.append("&t=");
        buffer.append(associatesID);
        buffer.append("&CartId=");
        buffer.append(cartId);
        buffer.append("&Hmac=");
        buffer.append(jawsUtil.encodeString(hmac));
        return new String(buffer);
        /*
          http://xml.amazon.com/onca/xml3?ShoppingCart=clear
          &f=xml& dev-t=[ [developer's token goes here]
          &t=[associates ID goes here]
          &CartId=[cart ID goes here]
          &Hmac=[HMAC goes here]
        */
    }

    public String GetItemsFromCart(String cartId, String hmac) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(serverURL);
        buffer.append("?");
        buffer.append("ShoppingCart=get&f=xml&dev-t=");
        buffer.append(token);
        buffer.append("&t=");
        buffer.append(associatesID);
        buffer.append("&CartId=");
        buffer.append(cartId);
        buffer.append("&Hmac=");
        buffer.append(jawsUtil.encodeString(hmac));
        return new String(buffer);
        /*
          http://xml.amazon.com/onca/xml3?ShoppingCart=get
          &f=xml& dev-t=[ [developer's token goes here]
          &t=[associates ID goes here]
          &CartId=[cart ID goes here]
          &Hmac=[HMAC goes here]
        */
    }

    public String ModifyCart(String itemId, String quantity, String cartId, String hmac) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(serverURL);
        buffer.append("?");
        buffer.append("ShoppingCart=modify&f=xml&dev-t=");
        buffer.append(token);
        buffer.append("&t=");
        buffer.append(associatesID);
        buffer.append("&Item.");
        buffer.append(itemId);
        buffer.append("=");
        buffer.append(quantity);
        buffer.append("&CartId=");
        buffer.append(cartId);
        buffer.append("&Hmac=");
        buffer.append(jawsUtil.encodeString(hmac));
        return new String(buffer);
        /*
          http://xml.amazon.com/onca/xml3?ShoppingCart=modify
          &f=xml& dev-t=[ [developer's token goes here]
          &t=[associates ID goes here]
          &Item.[itemID goes here]=[quantity goes here]
          &CartId=[cart ID goes here]
          &Hmac=[HMAC goes here]
        */
    }

    public String RemoveFromCart(String itemId, String cartId, String hmac) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(serverURL);
        buffer.append("?");
        buffer.append("ShoppingCart=remove&f=xml&dev-t=");
        buffer.append(token);
        buffer.append("&t=");
        buffer.append(associatesID);
        buffer.append("&Item.");
        buffer.append(itemId);
        buffer.append("&CartId=");
        buffer.append(cartId);
        buffer.append("&Hmac=");
        buffer.append(jawsUtil.encodeString(hmac));
        return new String(buffer);
        /*
          http://xml.amazon.com/onca/xml3?ShoppingCart=remove
          &CartId=CART
          &Hmac=HMAC=
          &Item.17120277375791359165
          &Item.1813019710362345961
          &dev-t=TOKEN&t=test&f=xml&type=lite
        */
    }
}
