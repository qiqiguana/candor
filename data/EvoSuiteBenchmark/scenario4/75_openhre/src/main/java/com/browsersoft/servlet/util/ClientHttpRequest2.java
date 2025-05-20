package com.browsersoft.servlet.util;

import java.net.URLConnection;
import java.net.URL;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.InputStream;
import java.util.Random;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.util.Iterator;

/**
 * <p>Title: Client HTTP Request class</p>
 * <p>Description: this class helps to send POST HTTP requests with various form data,
 * including files. Cookies can be added to be included in the request.</p>
 *
 * @author Vlad Patryshev
 * @version 1.0
 */
public class ClientHttpRequest {

    URLConnection connection;

    OutputStream os = null;

    Map cookies = new HashMap();

    protected void connect() throws IOException;

    protected void write(char c) throws IOException;

    protected void write(String s) throws IOException;

    protected void newline() throws IOException;

    protected void writeln(String s) throws IOException;

    private static Random random = new Random();

    protected static String randomString();

    String boundary = "---------------------------" + randomString() + randomString() + randomString();

    private void boundary() throws IOException;

    /**
     * Creates a new multipart POST HTTP request on a freshly opened URLConnection
     *
     * @param connection an already open URL connection
     * @throws IOException
     */
    public ClientHttpRequest(URLConnection connection) throws IOException {
    }

    /**
     * Creates a new multipart POST HTTP request for a specified URL
     *
     * @param url the URL to send request to
     * @throws IOException
     */
    public ClientHttpRequest(URL url) throws IOException {
    }

    /**
     * Creates a new multipart POST HTTP request for a specified URL string
     *
     * @param urlString the string representation of the URL to send request to
     * @throws IOException
     */
    public ClientHttpRequest(String urlString) throws IOException {
    }

    private void postCookies();

    /**
     * adds a cookie to the requst
     * @param name cookie name
     * @param value cookie value
     * @throws IOException
     */
    public void setCookie(String name, String value) throws IOException;

    /**
     * adds cookies to the request
     * @param cookies the cookie "name-to-value" map
     * @throws IOException
     */
    public void setCookies(Map cookies) throws IOException;

    /**
     * adds cookies to the request
     * @param cookies array of cookie names and values (cookies[2*i] is a name, cookies[2*i + 1] is a value)
     * @throws IOException
     */
    public void setCookies(String[] cookies) throws IOException;

    private void writeName(String name) throws IOException;

    /**
     * adds a string parameter to the request
     * @param name parameter name
     * @param value parameter value
     * @throws IOException
     */
    public void setParameter(String name, String value) throws IOException;

    private static void pipe(InputStream in, OutputStream out) throws IOException;

    /**
     * adds a file parameter to the request
     * @param name parameter name
     * @param filename the name of the file
     * @param is input stream to read the contents of the file from
     * @throws IOException
     */
    public void setParameter(String name, String filename, InputStream is) throws IOException;

    /**
     * adds a file parameter to the request
     * @param name parameter name
     * @param file the file to upload
     * @throws IOException
     */
    public void setParameter(String name, File file) throws IOException;

    /**
     * adds a parameter to the request; if the parameter is a File, the file is uploaded, otherwise the string value of the parameter is passed in the request
     * @param name parameter name
     * @param object parameter value, a File or anything else that can be stringified
     * @throws IOException
     */
    public void setParameter(String name, Object object) throws IOException;

    /**
     * adds parameters to the request
     * @param parameters "name-to-value" map of parameters; if a value is a file, the file is uploaded, otherwise it is stringified and sent in the request
     * @throws IOException
     */
    public void setParameters(Map parameters) throws IOException;

    /**
     * adds parameters to the request
     * @param parameters array of parameter names and values (parameters[2*i] is a name, parameters[2*i + 1] is a value); if a value is a file, the file is uploaded, otherwise it is stringified and sent in the request
     * @throws IOException
     */
    public void setParameters(Object[] parameters) throws IOException;

    /**
     * posts the requests to the server, with all the cookies and parameters that were added
     * @return input stream with the server response
     * @throws IOException
     */
    public InputStream post() throws IOException;

    /**
     * posts the requests to the server, with all the cookies and parameters that were added before (if any), and with parameters that are passed in the argument
     * @param parameters request parameters
     * @return input stream with the server response
     * @throws IOException
     * @see setParameters
     */
    public InputStream post(Map parameters) throws IOException;

    /**
     * posts the requests to the server, with all the cookies and parameters that were added before (if any), and with parameters that are passed in the argument
     * @param parameters request parameters
     * @return input stream with the server response
     * @throws IOException
     * @see setParameters
     */
    public InputStream post(Object[] parameters) throws IOException;

    /**
     * posts the requests to the server, with all the cookies and parameters that were added before (if any), and with cookies and parameters that are passed in the arguments
     * @param cookies request cookies
     * @param parameters request parameters
     * @return input stream with the server response
     * @throws IOException
     * @see setParameters
     * @see setCookies
     */
    public InputStream post(Map cookies, Map parameters) throws IOException;

    /**
     * posts the requests to the server, with all the cookies and parameters that were added before (if any), and with cookies and parameters that are passed in the arguments
     * @param cookies request cookies
     * @param parameters request parameters
     * @return input stream with the server response
     * @throws IOException
     * @see setParameters
     * @see setCookies
     */
    public InputStream post(String[] cookies, Object[] parameters) throws IOException;

    /**
     * post the POST request to the server, with the specified parameter
     * @param name parameter name
     * @param value parameter value
     * @return input stream with the server response
     * @throws IOException
     * @see setParameter
     */
    public InputStream post(String name, Object value) throws IOException;

    /**
     * post the POST request to the server, with the specified parameters
     * @param name1 first parameter name
     * @param value1 first parameter value
     * @param name2 second parameter name
     * @param value2 second parameter value
     * @return input stream with the server response
     * @throws IOException
     * @see setParameter
     */
    public InputStream post(String name1, Object value1, String name2, Object value2) throws IOException;

    /**
     * post the POST request to the server, with the specified parameters
     * @param name1 first parameter name
     * @param value1 first parameter value
     * @param name2 second parameter name
     * @param value2 second parameter value
     * @param name3 third parameter name
     * @param value3 third parameter value
     * @return input stream with the server response
     * @throws IOException
     * @see setParameter
     */
    public InputStream post(String name1, Object value1, String name2, Object value2, String name3, Object value3) throws IOException;

    /**
     * post the POST request to the server, with the specified parameters
     * @param name1 first parameter name
     * @param value1 first parameter value
     * @param name2 second parameter name
     * @param value2 second parameter value
     * @param name3 third parameter name
     * @param value3 third parameter value
     * @param name4 fourth parameter name
     * @param value4 fourth parameter value
     * @return input stream with the server response
     * @throws IOException
     * @see setParameter
     */
    public InputStream post(String name1, Object value1, String name2, Object value2, String name3, Object value3, String name4, Object value4) throws IOException;

    /**
     * posts a new request to specified URL, with parameters that are passed in the argument
     * @param parameters request parameters
     * @return input stream with the server response
     * @throws IOException
     * @see setParameters
     */
    public static InputStream post(URL url, Map parameters) throws IOException;

    /**
     * posts a new request to specified URL, with parameters that are passed in the argument
     * @param parameters request parameters
     * @return input stream with the server response
     * @throws IOException
     * @see setParameters
     */
    public static InputStream post(URL url, Object[] parameters) throws IOException;

    /**
     * posts a new request to specified URL, with cookies and parameters that are passed in the argument
     * @param cookies request cookies
     * @param parameters request parameters
     * @return input stream with the server response
     * @throws IOException
     * @see setCookies
     * @see setParameters
     */
    public static InputStream post(URL url, Map cookies, Map parameters) throws IOException;

    /**
     * posts a new request to specified URL, with cookies and parameters that are passed in the argument
     * @param cookies request cookies
     * @param parameters request parameters
     * @return input stream with the server response
     * @throws IOException
     * @see setCookies
     * @see setParameters
     */
    public static InputStream post(URL url, String[] cookies, Object[] parameters) throws IOException;

    /**
     * post the POST request specified URL, with the specified parameter
     * @param name parameter name
     * @param value parameter value
     * @return input stream with the server response
     * @throws IOException
     * @see setParameter
     */
    public static InputStream post(URL url, String name1, Object value1) throws IOException;

    /**
     * post the POST request to specified URL, with the specified parameters
     * @param name1 first parameter name
     * @param value1 first parameter value
     * @param name2 second parameter name
     * @param value2 second parameter value
     * @return input stream with the server response
     * @throws IOException
     * @see setParameter
     */
    public static InputStream post(URL url, String name1, Object value1, String name2, Object value2) throws IOException;

    /**
     * post the POST request to specified URL, with the specified parameters
     * @param name1 first parameter name
     * @param value1 first parameter value
     * @param name2 second parameter name
     * @param value2 second parameter value
     * @param name3 third parameter name
     * @param value3 third parameter value
     * @return input stream with the server response
     * @throws IOException
     * @see setParameter
     */
    public static InputStream post(URL url, String name1, Object value1, String name2, Object value2, String name3, Object value3) throws IOException;

    /**
     * post the POST request to specified URL, with the specified parameters
     * @param name1 first parameter name
     * @param value1 first parameter value
     * @param name2 second parameter name
     * @param value2 second parameter value
     * @param name3 third parameter name
     * @param value3 third parameter value
     * @param name4 fourth parameter name
     * @param value4 fourth parameter value
     * @return input stream with the server response
     * @throws IOException
     * @see setParameter
     */
    public static InputStream post(URL url, String name1, Object value1, String name2, Object value2, String name3, Object value3, String name4, Object value4) throws IOException;
}
