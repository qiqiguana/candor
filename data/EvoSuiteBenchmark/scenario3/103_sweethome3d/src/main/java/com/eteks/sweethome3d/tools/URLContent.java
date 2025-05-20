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

    /**
     * Returns an InputStream on the URL content.
     *
     * @throws IOException if URL stream can't be opened.
     */
    public InputStream openStream() throws IOException;
}
