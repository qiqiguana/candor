/**
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "Exolab" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of Exoffice Technologies.  For written permission,
 *    please contact info@exolab.org.
 *
 * 4. Products derived from this Software may not be called "Exolab"
 *    nor may "Exolab" appear in their names without prior written
 *    permission of Exoffice Technologies. Exolab is a registered
 *    trademark of Exoffice Technologies.
 *
 * 5. Due credit should be given to the Exolab Project
 *    (http://www.exolab.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY EXOFFICE TECHNOLOGIES AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * EXOFFICE TECHNOLOGIES OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 2004-2006 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: SocketManager.java,v 1.1 2006/01/11 13:24:05 tanderson Exp $
 */
package org.exolab.jms.net.tunnel;

import java.io.IOException;
import java.net.Socket;
import java.rmi.server.ObjID;
import java.util.HashMap;
import java.util.Map;

import EDU.oswego.cs.dl.util.concurrent.ClockDaemon;
import EDU.oswego.cs.dl.util.concurrent.ThreadFactory;
import org.apache.log4j.Logger;


/**
 * Manages connections for {@link TunnelServlet}.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2006/01/11 13:24:05 $
 */
class SocketManager {

    /**
     * Clock daemon for periodically running the reaper.
     */
    private ClockDaemon _daemon;

    /**
     * A map of <code>SocketInfo</code>, keyed on identifier.
     */
    private Map _sockets = new HashMap();

    /**
     * The logger.
     */
    private static final Logger _log = Logger.getLogger(SocketManager.class);

    /**
     * Reap thread synchronization helper.
     */
    private final Object _reapLock = new Object();

    /**
     * The maximum period that a connection may be idle before it is reaped, in
     * milliseconds. If <code>0</code> indicates not to reap connections.
     */
    private long _idlePeriod = 30 * 1000;


    /**
     * Create a new socket.
     *
     * @param host the host to connect to
     * @param port the port to connect to
     * @return the identifier of the new endpoint
     * @throws IOException for any I/O error
     */
    public synchronized String open(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        String id = new ObjID().toString();
        SocketInfo result = new SocketInfo(id, socket);
        _sockets.put(id, result);
        startReaper();
        return id;
    }

    /**
     * Returns a socket given its identifier.
     *
     * @param id the endpoint identifier
     * @return the endpoint corresponding to <code>id</code> or
     *         <code>null</code>
     */
    public synchronized Socket getSocket(String id) {
        Socket result = null;
        synchronized (_reapLock) {
            SocketInfo info = getSocketInfo(id);
            if (info != null) {
                info.setUsed();
                result = info.getSocket();
            }
        }
        return result;
    }

    /**
     * Close a connection given its identifier.
     *
     * @param id the connection identifier
     * @throws IOException for any I/O error
     */
    public synchronized void close(String id) throws IOException {
        Socket socket = getSocket(id);
        if (socket != null) {
            try {
                socket.close();
            } finally {
                _sockets.remove(id);
                if (_sockets.isEmpty()) {
                    stopReaper();
                }
            }
        }
    }

    /**
     * Sets the the maximum period that a connection may be idle before it is
     * reaped, in seconds.
     *
     * @param period the idle period, in seconds
     */
    public void setIdlePeriod(int period) {
        _idlePeriod = period * 1000;
    }

    /**
     * Returns a {@link SocketInfo} given its identifier.
     *
     * @param id the endpoint identifier
     * @return the connection corresponding to <code>id</code> or
     *         <code>null</code> if none exists
     */
    protected SocketInfo getSocketInfo(String id) {
        return (SocketInfo) _sockets.get(id);
    }


    /**
     * Reap idle connections.
     */
    private void reapIdleConnections() {
        Map.Entry[] entries;
        synchronized (this) {
            entries = (Map.Entry[]) _sockets.entrySet().toArray(new Map.Entry[0]);
        }
        synchronized (_reapLock) {
            for (int i = 0; i < entries.length && !stopReaping(); ++i) {
                Map.Entry entry = entries[i];
                SocketInfo info = (SocketInfo) entry.getValue();
                long current = System.currentTimeMillis();
                long unused = current - info.getUsed();
                if (unused > _idlePeriod) {
                    if (_log.isDebugEnabled()) {
                        _log.debug("Reaping idle connection=" + info.getId());
                    }
                    try {
                        close(info.getId());
                    } catch (IOException ignore) {
                    }
                }
            }
        }
    }

    /**
     * Starts the reaper for dead/idle connections, if needed.
     */
    private synchronized void startReaper() {
        if (_daemon == null) {
            _daemon = new ClockDaemon();
            if (_idlePeriod > 0) {
                _daemon.setThreadFactory(new ThreadFactory() {
                    public Thread newThread(Runnable command) {
                        Thread thread = new Thread(command, "Reaper");
                        thread.setDaemon(true);
                        return thread;
                    }
                });

                _daemon.executePeriodically(_idlePeriod, new Reaper(), false);
            }
        }
    }

    /**
     * Stops the reaper for dead/idle connections, if needed.
     */
    private synchronized void stopReaper() {
        if (_daemon != null) {
            _daemon.shutDown();
            _daemon = null;
        }
    }

    /**
     * Helper to determines if a reaper should terminate, by checking the
     * interrupt status of the current thread.
     *
     * @return <code>true</code> if the reaper should terminate
     */
    private boolean stopReaping() {
        return Thread.currentThread().isInterrupted();
    }

    /**
     * Helper class for reaping idle connections.
     */
    private class Reaper implements Runnable {

        /**
         * Run the reaper.
         */
        public void run() {
            try {
                reapIdleConnections();
            } catch (Throwable exception) {
                _log.error(exception, exception);
            }
        }
    }

}

