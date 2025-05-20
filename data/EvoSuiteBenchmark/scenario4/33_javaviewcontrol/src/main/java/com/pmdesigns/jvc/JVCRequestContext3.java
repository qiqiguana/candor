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
     * The actual HttpServletRequest object
     */
    public final HttpServletRequest request;

    /**
     * The actual HttpServletResponse object
     */
    public final HttpServletResponse response;

    /**
     * The HttpServlet object (actually this is a JVCDispatcher)
     */
    public final HttpServlet servlet;

    /**
     * The controller is the servlet path up to the action.
     */
    public final String controller;

    /**
     * The action is the last part of the servlet path (before any query arguments)
     */
    public final String action;

    /**
     * Tells if this request has multipart content (ie file upload)
     */
    public final boolean hasMultipartContent;

    /**
     * Parameter value indicating that the real parameter value is binary (and should be
     * accessed via the getAttribute() method.
     */
    public static final String BINARY_VALUE = "BINARY_VALUE";

    /**
     * The 'flash' map for holding temporary key/value strings.
     */
    public final Map<String, String> flash;

    /**
     * Holds cached page generator fragments
     */
    private static Map<String, String> cacheMap;

    /**
     * Constructor for JVCRequestContext which is used to hold http request and response iformation
     * @param request
     * @param response
     * @param servlet
     * @param flash
     * @param controller
     * @param action
     */
    JVCRequestContext(HttpServletRequest request, HttpServletResponse response, HttpServlet servlet, Map<String, String> flash, String controller, String action) throws Exception {
    }

    /**
     * Convenience method
     * @return true if the request is secure
     */
    public boolean isSecure();

    /**
     * Convenience method
     * @return the request scheme, ie. http, https
     */
    public String getScheme();

    /**
     * Convenience method
     * @return the request method, ie. GET, POST
     */
    public String getMethod();

    /**
     * Convenience method
     * @return the server name for this request
     */
    public String getServerName();

    /**
     * Convenience method
     * @return the server port for this request
     */
    public int getServerPort();

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

    /**
     * Convenience method.  The request url path is broken into:
     * <ul>
     * <li>context path</li>
     * <li>servlet path</li>
     * <li>query string</li>
     * </ul>
     * @see #getContextPath
     * @see #getQueryString
     * @return the servlet path part of the url path
     */
    public String getServletPath();

    /**
     * Convenience method.  The request url path is broken into:
     * <ul>
     * <li>context path</li>
     * <li>servlet path</li>
     * <li>query string</li>
     * </ul>
     * @see #getContextPath
     * @see #getServletPath
     * @return the query string part of the url path
     */
    public String getQueryString();

    /**
     * Convenience method
     * @param name    which parameter to get
     * @return the parameter value or null if there is no parameter corresponding to the indicated key
     * or BINARY_VALUE if this is a multipart request and there is binary data for the indicate key,
     * which can be retrieved as a byte array via the getAttribute method.
     * @see #getParamMap
     * @see #getParamValues
     * @see #getParamNames
     */
    public String getParam(String name);

    /**
     * Convenience method which gathers all parameters of the
     * form '<name>[<key>]' and return then in a map where the
     * keys are the <key> strings and the values are the
     * corresponding parameter values.
     * <pre>
     * For example, if there are parameters:
     *   'foo[bar1]' = 'baz1'
     *   'foo[bar2]' = 'baz2'
     * then getParamMap('foo') will returned the map:
     *   map['bar1'] = 'baz1'
     *   map['bar2'] = 'baz2'
     * </pre>
     * @param name  the prefix to be used to select parameters
     * @return a map of all paramters of the form 'name[<key>]'
     * where the <key> strings are the keys of the map and the
     * values are the corresponding parameter values.
     * @see #getParam
     * @see #getParamValues
     * @see #getParamNames
     */
    public Map<String, String> getParamMap(String name);

    /**
     * Convenience method. Use this if you expect a parameter name
     * to map to multiple values.
     * @param name    the name of the parameter(s) to get
     * @return an array of paramter values corresponding to the indicated name
     * @see #getParam
     * @see #getParamMap
     * @see #getParamNames
     */
    public String[] getParamValues(String name);

    /**
     * Convenience method to get all the parameter names.
     * @return an array of all parameter names
     * @see #getParam
     * @see #getParamMap
     * @see #getParamValues
     */
    public String[] getParamNames();

    /**
     * Convenience method
     * @param name    which attribute to get
     * @return the attribute value or null if there is no attribute corresponding to the indicated key.
     * If the attribute corresponds to a file upload field then the return value will be a byte array
     */
    public Object getAttribute(String name);

    /**
     * Convenience method
     * @param name    name of attribute to be set
     * @param value   value of attribute to be set
     */
    public void setAttribute(String name, Object value);

    /**
     * Convenience method
     * @param name    the name of the attribute to retrieve
     * @return the attribute associated with the indicated name or null
     * @see #setSessionAttr
     * @see #getSessionAttrNames
     */
    public Object getSessionAttr(String name);

    /**
     * Convenience method
     * @return all the session attribute names.
     * @see #getSessionAttr
     * @see #setSessionAttr
     * @see #removeSessionAttr
     */
    public String[] getSessionAttrNames();

    /**
     * Convenience method
     * @param name  the key to store the indicated value
     * @param value  the object to be stored
     * @see #getSessionAttr
     * @see #removeSessionAttr
     */
    public void setSessionAttr(String name, Object value);

    /**
     * Convenience method
     * @param name  the key of
     * @see #setSessionAttr
     */
    public void removeSessionAttr(String name);

    /**
     * Convenience method to get cookie by name
     * @param name  the name of the cookie to get
     * @return the cookie with the indicated name, or null
     * @see #setCookie
     * @see #getCookieNames
     */
    public Cookie getCookie(String name);

    /**
     * Convenience method to get all the cookie names.
     * @return an array of the names of all the cookies in the request, possibly empty
     * @see #getCookie
     * @see #setCookie
     */
    public String[] getCookieNames();

    /**
     * Convenience method
     * @param cookie   the cookie to be added to the response
     * @see #getCookie
     * @see #getCookieNames
     */
    public void setCookie(Cookie cookie);

    /**
     * Cause a non-standard response (ie. redirect) to be returned.
     * @param httpCode   the http response code to be returned
     * @param arg    an argument or message to be returned (depends on response code)
     * @see #redirect
     * @throws NonStandardResponseException which is a subclass of RuntimeException.
     * If you call this method inside a try/catch block make sure you re-throw
     * the NonStandardResponseException.
     */
    public void nonStandardResponse(int httpCode, String arg);

    /**
     * Cause a redirect response to be returned
     * @param path  where to redirect to. If the path starts with a '/' character then
     * its assumed that this is an absolute link and the context path will be prepended to it
     * @see #nonStandardResponse
     * @throws NonStandardResponseException which is a subclass of RuntimeException.
     * If you call this method inside a try/catch block make sure you re-throw
     * the NonStandardResponseException.
     */
    public void redirect(String path);

    /**
     * Cause a binary response to be returned
     * @param in input stream containing binary data to be sent
     * @throws BinaryResponseException which is a subclass of RuntimeException.
     * If you call this method inside a try/catch block make sure you re-throw
     * the BinaryResponseException.
     */
    public void sendBinaryResponse(InputStream in);

    /**
     * Convenience method to prepend the context path onto a servlet path
     * @param path    a servlet path
     * @return the context path plus the indicated path
     */
    public String absPath(String path);

    /**
     * Convenience method to create an html anchor link tag
     * @param anchor    the text in an anchor link
     * @param path   the anchor target, if the path starts with a '/' character then
     * its assumed that this is an absolute link and the context path will be prepended to it
     * @see #makeLink
     * @see #absPath
     */
    public String makeLink(String anchor, String path);

    /**
     * Convenience method to create an html anchor link tag
     * @param anchor    the text in an anchor link
     * @param path   the anchor target, if the path starts with a '/' character then
     * its assumed that this is an absolute link and the context path will be prepended to it
     * @param options    extra html options to add to the anchor tag
     * @see #makeLink
     * @see #absPath
     */
    public String makeLink(String anchor, String path, String options);

    /**
     * Convenience method to check if this request is a POST
     */
    public boolean isPost();

    /**
     * Convenience method to check if this request is a GET
     */
    public boolean isGet();

    /**
     * Convenience method to retrieve a value from the flash
     * @param key  the key to use to lookup a flash value
     * @return the flash value associated with the indicated key or null
     * @see #setFlash
     */
    public String getFlash(String key);

    /**
     * Convenience method to add a value to the flash
     * @param key  the key to use to associate with the flash value
     * @param val  the value to store
     * @see #getFlash
     */
    public void setFlash(String key, String val);

    /**
     * Internal method used to retrieve a cached block
     * @see #setCachedBlock
     */
    public static String getCachedBlock(String key);

    /**
     * Internal method used to store a cached block
     * @see #getCachedBlock
     */
    public static void setCachedBlock(String key, String val);

    /**
     * For debugging
     * @return a string representation of this request context
     */
    public String toString();

    public String toString(String sep);

    /**
     * Copy an input stream to an output stream.
     */
    private byte[] readStream(InputStream in) throws IOException;
}
