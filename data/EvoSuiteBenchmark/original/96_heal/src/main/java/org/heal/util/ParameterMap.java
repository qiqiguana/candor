package org.heal.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Convenience class for manipulating a ServletRequest.getParameterMap()
 *
 * @version 1.0
 * @author Brad Schaefer (<A HREF="mailto:schaefer@lib.med.utah.edu">schaefer@lib.med.utah.edu</A>) 
 */
public class ParameterMap extends HashMap {

    public ParameterMap() {
        super();
    }

    public ParameterMap(Map map) {
        super(map);
    }

    /**
     * Initializes a ParameterMap with keys/values parsed from a
     * query string.<br/><br/>
     *
     * For example, given the URL
     * <code>"http://www.healcentral.org/healapp/searchResults?searchtype=simple&keywords=foo&page=1"</code>
     * the query string would be <code>"searchtype=simple&keywords=foo&page=1"</code>.<br/><br/>
     *
     *
     * The preferred constructor is ParameterMap(java.util.Map), but this is provided for
     * cases where an initial map is not available, but a query string is.
     *
     * @param queryString A String representation of a ParameterMap.
     */
    public ParameterMap(String queryString) {
        StringTokenizer pairs = new StringTokenizer(queryString, "&");
        while(pairs.hasMoreTokens()) {
            String pair = pairs.nextToken();
            int equalIndex = pair.indexOf("=");

            // Don't go any further for this pair if a '=' cannot be found
            if(-1 == equalIndex) { continue; }

            String key = pair.substring(0, equalIndex);
            String value;
            if((equalIndex + 1) < pair.length()) {
                value = pair.substring((equalIndex + 1), pair.length());
            } else {
                value = "";
            }

            String[] paramValue;
            if(!containsKey(key)) {
                paramValue = new String[1];
                paramValue[0] = value;
            } else {
                // copies the old array into a new array which is one
                // element larger
                String[] temp = (String[])get(key);
                paramValue = new String[temp.length+1];
                for(int i = 0; i < temp.length; ++i) {
                    paramValue[i] = temp[i];
                }
                paramValue[temp.length] = value;
            }
            put(key, paramValue);
        }
    }

    /**
     *
     * @param key
     * @param value
     * @return
     * @throws IllegalArgumentException Thrown when a key is not a String, or a value is
     *      not a String[].
     */
    public Object put(Object key, Object value) {
        if(!(value instanceof String[])) {
            throw new IllegalArgumentException("Cannot put a non-String[] value into a ParameterMap");
        }
        if(!(key instanceof String)) {
            throw new IllegalArgumentException("Cannot put a non-String key into a ParameterMap");
        }
        return super.put(key, value);
    }

    /**
     * Over-ridden method to correctly put a single String value
     * into the parameter map as a String[] (since that's how
     * parameters need to be stored).
     *
     * @param key
     * @param value
     * @return
     */
    public Object put(String key, String value) {
        String[] temp = { value };
        return super.put(key, temp);
    }

    public String toString() {
        StringBuffer ret = new StringBuffer();
        Iterator iter = entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry pair = (Map.Entry)iter.next();
            String[] values = (String[])pair.getValue();
            for(int i = 0; i < values.length; ++i) {
                ret.append((String)pair.getKey()).append("=").append(values[i]);
                if(iter.hasNext() || (i+1) < values.length) {
                    // only adds a & if there are more parameters to add
                    ret.append("&");
                }
            }
        }
        return ret.toString();
    }
}
