package com.pmdesigns.jvc;

import java.io.*;
import java.util.*;
import java.net.HttpURLConnection;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * This class contains state information for processing an http request.
 * It wraps the standard HttpServletRequest, HttpServletResponse and HttpServlet
 * objects and provides convenience methods for accessing them.
 * <p>
 * It also provides access to the 'flash' which is a Map for storing temporary
 * key/value strings.  The scope of the flash is the current request or
 * the following request in the case of a redirect response.
 * <p>
 * It also contains some convenience methods for forming links and absolute paths.
 *
 * @author mike dooley
 */
public final class JVCRequestContext {

    public Map<String, String> getParamMap(String name) {
        Map<String, String> m = new HashMap<String, String>();
        String prefix = name + "[";
        int n = prefix.length();
        if (hasMultipartContent) {
            // for multipart requests the parameters have been stored as attributes (see constructor)
            Enumeration<String> e = request.getAttributeNames();
            while (e.hasMoreElements()) {
                String key = e.nextElement();
                if (key.startsWith(prefix) && key.endsWith("]")) {
                    m.put(key.substring(n, key.length() - 1), getParam(key));
                }
            }
        } else {
            Map<String, String[]> pm = request.getParameterMap();
            for (String key : pm.keySet()) {
                if (key.startsWith(prefix) && key.endsWith("]")) {
                    m.put(key.substring(n, key.length() - 1), getParam(key));
                }
            }
        }
        return m;
    }
}
