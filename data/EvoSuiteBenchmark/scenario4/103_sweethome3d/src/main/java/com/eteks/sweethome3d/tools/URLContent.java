package com.eteks.sweethome3d.tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import com.eteks.sweethome3d.model.Content;

/**
 * URL content for files, images...
 *
 * @author Emmanuel Puybaret
 */
public class URLContent implements Content {

    private static final long serialVersionUID = 1L;

    private URL url;

    public URLContent(URL url) {
    }

    /**
     * Returns the URL of this content.
     */
    public URL getURL();

    /**
     * Returns an InputStream on the URL content.
     * @throws IOException if URL stream can't be opened.
     */
    public InputStream openStream() throws IOException;

    /**
     * Returns <code>true</code> if the URL stored by this content
     * references an entry in a JAR.
     */
    public boolean isJAREntry();

    /**
     * Returns the URL base of a JAR entry.
     * @throws IllegalStateException if the URL of this content
     *                    doesn't reference an entry in a JAR.
     */
    public URL getJAREntryURL();

    /**
     * Returns the name of a JAR entry.
     * If the JAR entry in the URL given at creation time was encoded in application/x-www-form-urlencoded format,
     * this method will return it unchanged and not decoded.
     * @throws IllegalStateException if the URL of this content
     *                    doesn't reference an entry in a JAR URL.
     */
    public String getJAREntryName();

    /**
     * Returns <code>true</code> if the object in parameter is an URL content
     * that references the same URL as this object.
     */
    @Override
    public boolean equals(Object obj);

    @Override
    public int hashCode();
}
