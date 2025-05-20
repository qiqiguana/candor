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
     * post the POST request to the server, with the specified parameters
     *
     * @param name1 first parameter name
     * @param value1 first parameter value
     * @param name2 second parameter name
     * @param value2 second parameter value
     * @return input stream with the server response
     * @throws IOException
     * @see setParameter
     */
    public InputStream post(String name1, Object value1, String name2, Object value2) throws IOException {
        setParameter(name1, value1);
        return post(name2, value2);
    }

    /**
     * adds a parameter to the request; if the parameter is a File, the file is uploaded, otherwise the string value of the parameter is passed in the request
     * @param name parameter name
     * @param object parameter value, a File or anything else that can be stringified
     * @throws IOException
     */
    public void setParameter(String name, Object object) throws IOException;

    /**
     * post the POST request to the server, with the specified parameter
     * @param name parameter name
     * @param value parameter value
     * @return input stream with the server response
     * @throws IOException
     * @see setParameter
     */
    public InputStream post(String name, Object value) throws IOException;
}
