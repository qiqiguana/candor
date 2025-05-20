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

    /**
     * Reads a line of String if ready. If line is not yet ready this will
     * block. To find out if the line is ready use <code>isLineReady()</code>
     *
     * @see #isLineReady()
     */
    public synchronized String readLine() throws IOException;
}
