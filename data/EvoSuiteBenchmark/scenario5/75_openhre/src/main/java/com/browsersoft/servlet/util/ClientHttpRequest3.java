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

    /**
     * posts the requests to the server, with all the cookies and parameters that were added before (if any), and with cookies and parameters that are passed in the arguments
     *
     * @param cookies request cookies
     * @param parameters request parameters
     * @return input stream with the server response
     * @throws IOException
     * @see setParameters
     * @see setCookies
     */
    public InputStream post(Map cookies, Map parameters) throws IOException {
        setCookies(cookies);
        setParameters(parameters);
        return post();
    }

    /**
     * adds cookies to the request
     * @param cookies the cookie "name-to-value" map
     * @throws IOException
     */
    public void setCookies(Map cookies) throws IOException;

    /**
     * adds parameters to the request
     * @param parameters "name-to-value" map of parameters; if a value is a file, the file is uploaded, otherwise it is stringified and sent in the request
     * @throws IOException
     */
    public void setParameters(Map parameters) throws IOException;

    /**
     * posts the requests to the server, with all the cookies and parameters that were added
     * @return input stream with the server response
     * @throws IOException
     */
    public InputStream post() throws IOException;
}
