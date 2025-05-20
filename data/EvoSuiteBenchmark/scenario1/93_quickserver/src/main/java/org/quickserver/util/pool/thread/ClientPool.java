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

    public boolean shouldNioWriteHappen() {
        if (maxThreadsForNioWrite <= 0 || countNioWriteThreads < maxThreadsForNioWrite) {
            return true;
        } else {
            return false;
        }
    }
}
