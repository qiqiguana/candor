package org.quickserver.util.io;

import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.util.*;
import org.quickserver.net.server.ClientHandler;
import java.util.logging.*;
import org.quickserver.util.*;

/**
 * Class to encapsulate Assertion and allows any back ports.
 *
 * @since 1.4.6
 */
public final class Assertion {

    public static void affirm(boolean test);
}

/**
 * This is an InputStream constructed from list of ByteBuffers. This is
 * used in non-blocking mode.
 *
 * @since 1.4.5
 * @author Akshathkumar Shetty
 */
public class ByteBufferInputStream extends InputStream {

    /**
     * Reads a line of String if ready. If line is not yet ready this will
     * block. To find out if the line is ready use <code>isLineReady()</code>
     *
     * @see #isLineReady()
     */
    public synchronized String readLine() throws IOException {
        if (index == -1) {
            while (isLineReady() == false) {
                try {
                    wait();
                } catch (InterruptedException ie) {
                    logger.warning("InterruptedException: " + ie);
                    return null;
                }
            }
        }
        int stringsLength = strings.length();
        Assertion.affirm(index <= stringsLength);
        String data = strings.substring(start, index);
        if (pos < stringsLength)
            strings.delete(0, pos);
        else
            strings.setLength(0);
        start = 0;
        pos = start;
        index = -1;
        return data;
    }

    /**
     * Checks if a line of String is ready to be read.
     * @throws IOException if connection is lost or closed.
     */
    public synchronized boolean isLineReady() throws IOException;
}
