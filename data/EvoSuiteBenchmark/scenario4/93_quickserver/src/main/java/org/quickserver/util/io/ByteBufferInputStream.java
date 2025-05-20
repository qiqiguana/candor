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

    private static final Logger logger = Logger.getLogger(ByteBufferInputStream.class.getName());

    static {
    }

    /**
     * Sets the debug flag.
     */
    public static void setDebug(boolean flag);

    /**
     * @since 1.4.7
     */
    public static boolean isLoggable(Level level);

    private final ArrayList bufferList;

    private ClientHandler handler;

    private CharsetDecoder decoder;

    private CharsetEncoder encoder;

    private StringBuilder strings;

    private int pos = 0;

    private int index = -1;

    private int start = 0;

    private boolean lookingForLineFeed = false;

    public ByteBufferInputStream(ArrayList bufferList, ClientHandler handler, String charset) {
    }

    public synchronized int availableOnlyInByteBuffer();

    public synchronized int available();

    public synchronized void close() throws IOException;

    public boolean markSupported();

    public synchronized int read() throws IOException;

    public int read(byte[] b) throws IOException;

    public synchronized int read(byte[] b, int off, int len) throws IOException;

    public long skip(long n) throws IOException;

    private void addStringsBackAsBuffer();

    private void returnBufferBack();

    private void returnBufferBack(ByteBuffer byteBuffer);

    /**
     * Checks if a line of String is ready to be read.
     * @throws IOException if connection is lost or closed.
     */
    public synchronized boolean isLineReady() throws IOException;

    private boolean isLineReadyForStringBuilder();

    /**
     * Reads a line of String if ready. If line is not yet ready this will
     * block. To find out if the line is ready use <code>isLineReady()</code>
     * @see #isLineReady()
     */
    public synchronized String readLine() throws IOException;

    public void dumpContent();
}
