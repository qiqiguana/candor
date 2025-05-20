package org.quickserver.util.pool.thread;

import java.util.*;
import org.quickserver.util.pool.*;
import org.apache.commons.pool.*;
import org.quickserver.net.server.*;
import org.quickserver.util.xmlreader.PoolConfig;
import java.util.logging.*;

/**
 * This is a class for managing the pool of threads for
 * handling clients.
 *
 * @author Akshathkumar Shetty
 * @since 1.3
 */
public class ClientPool {

    private static final Logger logger = Logger.getLogger(ClientPool.class.getName());

    protected List clients = new ArrayList(3);

    protected ObjectPool pool;

    protected PoolConfig poolConfig;

    //v1.4.6
    private int countNioWriteThreads;

    private int maxThreadsForNioWrite = 10;

    public ClientPool(QSObjectPool objectPool, PoolConfig poolConfig) {
    }

    public ObjectPool getObjectPool();

    public void addClient(Runnable r) throws NoSuchElementException;

    public synchronized void addClient(Runnable r, boolean keepObjOnFail) throws NoSuchElementException;

    public synchronized void returnObject(Object object);

    public synchronized Runnable getClient();

    /**
     * @since 1.4.5
     */
    public boolean isClientAvailable();

    protected void finalize() throws Throwable;

    public void close() throws Exception;

    public void clear() throws Exception;

    /**
     * Return the number of instances currently borrowed from my pool.
     * @since 1.4.1
     */
    public int getNumActive();

    /**
     * Return the number of instances currently idle in my pool.
     * @since 1.4.1
     */
    public int getNumIdle();

    /**
     * Returns iterator containing all the active
     * threads i.e ClientHandler handling connected clients.
     * @since 1.3.1
     */
    public final Iterator getAllClientThread();

    public Object getObjectToSynchronize();

    /**
     * Returns PoolConfig object that configured this pool
     * @since 1.4.5
     */
    public PoolConfig getPoolConfig();

    /**
     * Sets the maximum threads allowed for nio write. If set to 0 or less no limit is
     * imposed.
     * @since 1.4.6
     */
    public void setMaxThreadsForNioWrite(int count);

    /**
     * Returns the maximum threads allowed for nio write
     * @since 1.4.6
     */
    public int getMaxThreadsForNioWrite();

    /**
     * Notifies when NIO write is complete.
     * @since 1.4.6
     */
    protected void nioWriteEnd();

    /**
     * Notifies when NIO write is about to start.
     * @since 1.4.6
     */
    protected void nioWriteStart();

    /**
     * Method to suggest if nio write should be sent for processing.
     * @since 1.4.6
     */
    public boolean shouldNioWriteHappen();
}
