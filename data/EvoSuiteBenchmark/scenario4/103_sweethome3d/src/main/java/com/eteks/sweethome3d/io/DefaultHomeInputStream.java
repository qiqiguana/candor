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

    private final ContentRecording contentRecording;

    private File tempFile;

    /**
     * Creates a home input stream filter able to read a home and its content
     * from <code>in</code>.
     */
    public DefaultHomeInputStream(InputStream in) throws IOException {
    }

    /**
     * Creates a home input stream filter able to read a home and its content
     * from <code>in</code>.
     */
    public DefaultHomeInputStream(InputStream in, ContentRecording contentRecording) throws IOException {
    }

    /**
     * Throws an <code>InterruptedRecorderException</code> exception
     * if current thread is interrupted. The interrupted status of the current thread
     * is cleared when an exception is thrown.
     */
    private static void checkCurrentThreadIsntInterrupted() throws InterruptedIOException;

    /**
     * Reads home from a zipped stream.
     */
    public Home readHome() throws IOException, ClassNotFoundException;

    /**
     * <code>ObjectInputStream</code> that replaces temporary <code>URLContent</code>
     * objects by <code>URLContent</code> objects that points to file.
     */
    private class HomeObjectInputStream extends ObjectInputStream {

        public HomeObjectInputStream(InputStream in) throws IOException {
            super(in);
            if (contentRecording != ContentRecording.INCLUDE_NO_CONTENT) {
                enableResolveObject(true);
            }
        }

        @Override
        protected Object resolveObject(Object obj) throws IOException {
            if (obj instanceof URLContent) {
                URL tmpURL = ((URLContent) obj).getURL();
                String url = tmpURL.toString();
                if (url.startsWith("jar:file:temp!/")) {
                    // Replace "temp" in URL by current temporary file
                    String entryName = url.substring(url.indexOf('!') + 2);
                    URL fileURL = new URL("jar:file:" + tempFile.toString() + "!/" + entryName);
                    try {
                        // Check entry exists
                        fileURL.openStream().close();
                    } catch (IOException ex) {
                        throw new IOException("Missing entry \"" + entryName + "\"");
                    }
                    return new HomeURLContent(fileURL);
                } else {
                    return obj;
                }
            } else {
                return obj;
            }
        }
    }
}
