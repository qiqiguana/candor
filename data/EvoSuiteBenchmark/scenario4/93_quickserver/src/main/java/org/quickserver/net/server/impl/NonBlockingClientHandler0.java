package org.quickserver.net.server.impl;

import org.quickserver.net.server.*;
import org.quickserver.net.*;
import org.quickserver.util.*;
import org.quickserver.util.io.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import java.nio.*;
import java.nio.channels.*;
import javax.net.ssl.*;

public class NonBlockingClientHandler extends BasicClientHandler {

    private static final Logger logger = Logger.getLogger(NonBlockingClientHandler.class.getName());

    //v1.4.5
    protected ClientWriteHandler clientWriteHandler;

    private SocketChannel socketChannel;

    protected ArrayList readByteBuffer = new ArrayList();

    protected ArrayList writeByteBuffer = new ArrayList();

    protected SelectionKey selectionKey;

    protected volatile int threadAccessCount = 0;

    protected volatile boolean willReturn;

    protected volatile boolean waitingForFinalWrite;

    //one for each event ACCEPT, WRITE, READ
    private static int maxThreadAccessCount = 5;

    private static boolean wakeupSelectorAfterRegisterWrite = true;

    private static boolean wakeupSelectorAfterRegisterRead = true;

    //private final SSLSession session;
    private boolean initialHandshakeStatus = false;

    private SSLEngineResult.HandshakeStatus handshakeStatus;

    private SSLEngineResult.Status status = null;

    private ByteBuffer dummyByteBuffer = ByteBuffer.allocate(0);

    private ByteBuffer peerNetData = null;

    private boolean sslShutdown = false;

    /**
     * Sets the flag to wakeup Selector After RegisterForWrite is called.
     * @since 1.4.7
     */
    public static void setWakeupSelectorAfterRegisterWrite(boolean flag);

    /**
     * Returns wakeupSelectorAfterRegisterWrite the flag that controls if wakeup is called on Selector
     * after RegisterForWrite is called.
     * @since 1.4.7
     */
    public static boolean getWakeupSelectorAfterRegisterWrite();

    /**
     * Sets the flag to wakeup Selector After RegisterForRead is called.
     * @since 1.4.7
     */
    public static void setWakeupSelectorAfterRegisterRead(boolean flag);

    /**
     * Returns wakeupSelectorAfterRegisterRead the flag that controls if wakeup is called on Selector
     * after RegisterForRead is called.
     * @since 1.4.7
     */
    public static boolean getWakeupSelectorAfterRegisterRead();

    /**
     * Sets the maximum count of thread allowed to run objects of this class at a time.
     * @since 1.4.7
     */
    public static void setMaxThreadAccessCount(int count);

    /**
     * Returns the maximum count of thread allowed to run objects of this class at a time.
     * @since 1.4.7
     */
    public static int getMaxThreadAccessCount();

    //v1.4.7
    private ByteBufferOutputStream byteBufferOutputStream;

    public NonBlockingClientHandler(int instanceCount) {
    }

    public NonBlockingClientHandler() {
    }

    public void clean();

    protected void finalize() throws Throwable;

    public void handleClient(TheClient theClient) throws Exception;

    protected void setInputStream(InputStream in) throws IOException;

    public BufferedReader getBufferedReader();

    public void closeConnection();

    private void doPostCloseActivity() throws IOException;

    public boolean closeIfSSLOutboundDone();

    /**
     * waitTillFullyWritten
     * @since 1.4.7
     */
    public void waitTillFullyWritten();

    public void run();

    protected boolean checkReturnClientHandler();

    /**
     * Process read
     * @return value indicates if the thread should return form run()
     */
    private boolean processRead() throws Exception;

    private boolean doRead() throws Exception;

    /**
     * Process write
     * @return value indicates if the thread should return form run()
     */
    private boolean processWrite() throws IOException;

    private boolean doWrite() throws IOException;

    protected void returnThread();

    protected void returnClientHandler();

    public void setDataMode(DataMode dataMode, DataType dataType) throws IOException;

    private void setDataModeNonBlocking(DataMode dataMode, DataType dataType) throws IOException;

    protected byte[] readInputStream() throws IOException;

    public void updateInputOutputStreams() throws IOException;

    public boolean getBlockingMode();

    public void setSocketChannel(SocketChannel socketChannel);

    public SocketChannel getSocketChannel();

    public void setSelectionKey(SelectionKey selectionKey);

    public SelectionKey getSelectionKey();

    private void processGotDataInBuffers() throws AppException, ConnectionLostException, ClassNotFoundException, IOException;

    public void registerForRead() throws IOException, ClosedChannelException;

    public void registerForWrite() throws IOException, ClosedChannelException;

    public void registerWrite() throws IOException;

    protected void setClientWriteHandler(ClientWriteHandler handler);

    /**
     * Returns number of thread currently in this object.
     * @since 1.4.6
     */
    public int getThreadAccessCount();

    private void doHandshake() throws Exception;

    private void doTasks();

    private void finishInitialHandshake() throws IOException;

    public boolean getInitialHandshakeStatus();

    public ByteBuffer encrypt(ByteBuffer src) throws IOException;
}
