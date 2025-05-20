package com.eteks.sweethome3d.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.tools.OperatingSystem;
import com.eteks.sweethome3d.tools.URLContent;

/**
 * An <code>InputStream</code> filter that reads a home from a stream
 * at .sh3d file format.
 *
 * @see DefaultHomeOutputStream
 */
public class DefaultHomeInputStream extends FilterInputStream {

    /**
     * Reads home from a zipped stream.
     */
    public Home readHome() throws IOException, ClassNotFoundException;
}
