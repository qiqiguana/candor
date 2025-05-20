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
    public Home readHome() throws IOException, ClassNotFoundException {
        if (this.contentRecording != ContentRecording.INCLUDE_NO_CONTENT) {
            // Copy home stream in a temporary file
            this.tempFile = OperatingSystem.createTemporaryFile("open", ".sweethome3d");
            checkCurrentThreadIsntInterrupted();
            OutputStream tempOut = null;
            try {
                tempOut = new FileOutputStream(this.tempFile);
                byte[] buffer = new byte[8192];
                int size;
                while ((size = this.in.read(buffer)) != -1) {
                    tempOut.write(buffer, 0, size);
                }
            } finally {
                if (tempOut != null) {
                    tempOut.close();
                }
            }
        }
        ZipInputStream zipIn = null;
        try {
            // Open a zip input from temp file
            zipIn = new ZipInputStream(this.contentRecording == ContentRecording.INCLUDE_NO_CONTENT ? this.in : new FileInputStream(this.tempFile));
            // Read Home entry
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null && !"Home".equals(entry.getName())) {
            }
            if (entry == null) {
                throw new IOException("Missing entry \"Home\"");
            }
            checkCurrentThreadIsntInterrupted();
            // Use an ObjectInputStream that replaces temporary URLs of Content objects
            // by URLs relative to file
            ObjectInputStream objectStream = new HomeObjectInputStream(zipIn);
            return (Home) objectStream.readObject();
        } finally {
            if (zipIn != null) {
                zipIn.close();
            }
        }
    }
}
