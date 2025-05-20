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
     * Convenience method to create an html anchor link tag
     *
     * @param anchor the text in an anchor link
     * @param path the anchor target, if the path starts with a '/' character then
     * its assumed that this is an absolute link and the context path will be prepended to it
     * @param options extra html options to add to the anchor tag
     * @see #makeLink
     * @see #absPath
     */
    public String makeLink(String anchor, String path, String options);
}
