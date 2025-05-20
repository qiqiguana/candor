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

    /**
     * Convenience method
     *
     * @param name which parameter to get
     * @return the parameter value or null if there is no parameter corresponding to the indicated key
     * or BINARY_VALUE if this is a multipart request and there is binary data for the indicate key,
     * which can be retrieved as a byte array via the getAttribute method.
     * @see #getParamMap
     * @see #getParamValues
     * @see #getParamNames
     */
    public String getParam(String name) {
        if (hasMultipartContent) {
            Object o = request.getAttribute(name);
            return (o != null && o instanceof String) ? (String) o : BINARY_VALUE;
        } else {
            return request.getParameter(name);
        }
    }
}
