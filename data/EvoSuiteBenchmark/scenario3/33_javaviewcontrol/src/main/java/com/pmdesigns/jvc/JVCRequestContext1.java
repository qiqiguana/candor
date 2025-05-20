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
     *
     * @param name the prefix to be used to select parameters
     * @return a map of all paramters of the form 'name[<key>]'
     * where the <key> strings are the keys of the map and the
     * values are the corresponding parameter values.
     * @see #getParam
     * @see #getParamValues
     * @see #getParamNames
     */
    public Map<String, String> getParamMap(String name);
}
