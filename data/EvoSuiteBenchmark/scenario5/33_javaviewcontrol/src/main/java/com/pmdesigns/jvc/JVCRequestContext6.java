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
     * Convenience method to prepend the context path onto a servlet path
     *
     * @param path a servlet path
     * @return the context path plus the indicated path
     */
    public String absPath(String path) {
        return getContextPath() + (path.startsWith("/") ? path : "/" + path);
    }

    /**
     * Convenience method.  The request url path is broken into:
     * <ul>
     * <li>context path</li>
     * <li>servlet path</li>
     * <li>query string</li>
     * </ul>
     * @see #getServletPath
     * @see #getQueryString
     * @return the context path part of the url path
     */
    public String getContextPath();
}
