package org.quickserver.util.io;

import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.util.*;
import org.quickserver.net.server.ClientHandler;
import java.util.logging.*;
import org.quickserver.util.*;

/**
 * This is an InputStream constructed from list of ByteBuffers. This is
 * used in non-blocking mode.
 *
 * @since 1.4.5
 * @author Akshathkumar Shetty
 */
public class ByteBufferInputStream extends InputStream {

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
}
