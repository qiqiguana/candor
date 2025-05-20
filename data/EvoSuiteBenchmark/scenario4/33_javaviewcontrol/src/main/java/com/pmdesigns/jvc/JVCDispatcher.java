package com.pmdesigns.jvc;

import java.io.*;
import java.util.*;
import java.net.HttpURLConnection;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import com.pmdesigns.jvc.tools.Base64Coder;
import com.pmdesigns.jvc.tools.JVCGenerator;

/**
 * JVC request dispatching servlet
 *
 * @author mike dooley
 */
public class JVCDispatcher extends HttpServlet {

    private Map<String, Class> generatorClasses;

    private String pkgPrefix;

    public static final String PKG_PREFIX_KEY = "pkg_prefix";

    private static Class requestContextClass;

    private static Class classNotFound;

    private Destroyable application;

    private static final boolean TRACE = false;

    private static ThreadLocal<JVCRequestContext> ctxHolder = new ThreadLocal<JVCRequestContext>() {

        protected synchronized JVCRequestContext initialValue() {
            return null;
        }
    };

    /**
     * Return the thread local request context
     * @return the JVCRequestContext associated with the current thread or null
     */
    public static JVCRequestContext getRC();

    /**
     * Get the package prefix (from config) so we know the fully qualified
     * name of page generators and controllers.  Also create and instance
     * of the Application object.
     */
    public void init();

    /**
     * Notify the Application that its shutdown time.
     */
    public void destroy();

    /**
     * Handle a GET request. Called by servlet container.
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

    /**
     * Handle a POST request. Called by servlet container.
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

    /**
     * Private implementation to handle a GET or POST request by invoking the appropriate
     * PageGenerator and Controller objects, or by serving static content.
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

    /**
     * Copy an input stream to an output stream.
     */
    private void copy(InputStream in, OutputStream out) throws IOException;

    /**
     * Append a package string to a prefix if the prefix isn't empty
     */
    private static String appendPkg(String prefix, String pkg);

    private static final String FLASH_COOKIE = "jvc_flash";

    /**
     * Look for a 'flash cookie' in the request.  If found deserialize it, clear the cookie
     * and return it, otherwise just return an empty Map.
     * @see #makeFlashCookie
     * @see #serializeMap
     * @see #deserializeMap
     */
    private static Map<String, String> getFlash(HttpServletRequest request, HttpServletResponse response);

    /**
     * Serialize the indicated map and return it in a 'flash cookie'
     * @see #getFlash
     * @see #serializeMap
     * @see #deserializeMap
     */
    private static Cookie makeFlashCookie(Map<String, String> map);

    /**
     * Return a string representation of the map.
     * This method encodes the map by writing its keys and values
     * separated by the 0 character (the end of the list is indicated
     * by an empty key) and then base64 encoding this string.
     * @see #getFlash
     * @see #makeFlashCookie
     * @see #deserializeMap
     */
    private static String serializeMap(Map<String, String> map);

    /**
     * Return the map representation of the indicated string (see serializeMap())
     * @see #getFlash
     * @see #makeFlashCookie
     * @see #serializeMap
     */
    private static Map<String, String> deserializeMap(String s);
}
