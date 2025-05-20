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
 * Copyright 2005-2006 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: DefaultConnectionPool.java,v 1.11 2008/01/07 13:00:16 tanderson Exp $
 */
package org.exolab.jms.net.connector;

import EDU.oswego.cs.dl.util.concurrent.ClockDaemon;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.jms.common.threads.ThreadFactory;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.util.Properties;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Manages a pool of {@link ManagedConnection} instances, for a particular
 * {@link ManagedConnectionFactory}.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.11 $ $Date: 2008/01/07 13:00:16 $
 * @see AbstractConnectionManager
 */
class DefaultConnectionPool
        implements ManagedConnectionAcceptorListener,
                   ManagedConnectionListener, ConnectionPool {

    /**
     * The connection factory.
     */
    private final ManagedConnectionFactory _factory;

    /**
     * Invocation handler to assign to each new connection.
     */
    private final InvocationHandler _handler;

    /**
     * The connection factory for resolving connections via their URI.
     */
    private final ConnectionFactory _resolver;

    /**
     * The set of allocated connections.
     */
    private final List _connections
            = Collections.synchronizedList(new ArrayList());

    /**
     * A map of ManagedConnection -> ManagedConnectionHandle. The handles are
     * used to reap idle connections.
     */
    private Map _handles = Collections.synchronizedMap(new HashMap());

    /**
     * The set of connection acceptors.
     */
    private List _acceptors = Collections.synchronizedList(new ArrayList());

    /**
     * The set of accepted connections.
     */
    private List _accepted = Collections.synchronizedList(new ArrayList());

    /**
     * The set of all connections, as a map of ManagedConnection -> PoolEntry
     * instances.
     */
    private Map _entries = Collections.synchronizedMap(new HashMap());

    /**
     * Reap thread synchronization helper.
     */
    private final Object _reapLock = new Object();

    /**
     * Clock daemon for periodically running the reaper.
     */
    private ClockDaemon _daemon;

    /**
     * Interval between pinging and reaping connections, in milliseconds.
     * If <code>0</code> indicates not to reap connections.
     */
    private final long _reapInterval;

    /**
     * Iterations before a connection that hasn't responded to a ping
     * is declared dead.
     */
    private final int _reapDeadIterations;

    /**
     * The maximum period that a connection may be idle before it is reaped,
     * in milliseconds.
     */
    private final long _idlePeriod;

    /**
     * The caller event listener.
     */
    private volatile CallerListener _listener;

    /**
     * Property name prefix for pool configuration items.
     */
    private static final String POOL_PREFIX = "org.exolab.jms.net.pool.";

    /**
     * Configuration property to indicate the no. of reaps to wait before
     * reaping a connection that hasn't responded to a ping.
     */
    private static final String DEAD_ITERATIONS = "reapDeadIterations";

    /**
     * Configuration property to indicate the reap interval.
     */
    private static final String REAP_INTERVAL = "reapInterval";

    /**
     * Configuration property to indicate the idle time for connections
     * before they may be reaped.
     */
    private static final String IDLE_PERIOD = "idlePeriod";

    /**
     * The logger.
     */
    private static final Log _log
            = LogFactory.getLog(DefaultConnectionPool.class);


    /**
     * Construct a new <code>DefaultConnectionPool</code>.
     *
     * @param factory    the managed connection factory
     * @param handler    the invocation handler, assigned to each new managed
     *                   connection
     * @param resolver   the connection factory for resolving connections via
     *                   their URI
     * @param properties configuration properties. May be <code>null</code>
     * @throws ResourceException if any configuration property is invalid
     */
    public DefaultConnectionPool(ManagedConnectionFactory factory,
                                 InvocationHandler handler,
                                 ConnectionFactory resolver,
                                 Map properties) throws ResourceException {
        if (factory == null) {
            throw new IllegalArgumentException("Argument 'factory' is null");
        }
        if (handler == null) {
            throw new IllegalArgumentException("Argument 'handler' is null");
        }
        if (resolver == null) {
            throw new IllegalArgumentException("Argument 'resolver' is null");
        }
        _factory = factory;
        _handler = handler;
        _resolver = resolver;

        Properties config = new Properties(properties, POOL_PREFIX);
        _reapInterval = getPropertyMillis(config, REAP_INTERVAL, 60);
        _reapDeadIterations = config.getInt(DEAD_ITERATIONS, 5);
        _idlePeriod = getPropertyMillis(config, IDLE_PERIOD, 5);
    }

    /**
     * Creates a new connection.
     *
     * @param principal the security principal
     * @param info      the connection request info
     * @return a new connection
     * @throws ResourceException if a connection cannot be established
     */
    public ManagedConnection createManagedConnection(Principal principal,
                                                     ConnectionRequestInfo info)
            throws ResourceException {
        ManagedConnection connection = _factory.createManagedConnection(
                principal, info);
        return add(connection, false);
    }

    /**
     * Creates an acceptor for connections.
     *
     * @param authenticator authenticates incoming connections
     * @param info          the connection request info
     * @return a new connection acceptor
     * @throws ResourceException if an acceptor cannot be created
     */
    public ManagedConnectionAcceptor createManagedConnectionAcceptor(
            Authenticator authenticator, ConnectionRequestInfo info)
            throws ResourceException {

        ManagedConnectionAcceptor acceptor;

        acceptor = _factory.createManagedConnectionAcceptor(authenticator,
                                                            info);
        _acceptors.add(acceptor);
        return acceptor;
    }

    /**
     * Returns a matched connection from the set of pooled connections.
     *
     * @param principal the security principal
     * @param info      the connection request info
     * @return the first acceptable match, or <code>null</code> if none is
     *         found
     * @throws ResourceException for any error
     */
    public ManagedConnection matchManagedConnections(Principal principal,
                                                     ConnectionRequestInfo info)
            throws ResourceException {

        ManagedConnection result = null;
        synchronized (_reapLock) {
            // synchronize on the reaper to ensure idle connections aren't being
            // reaped while matching

            // get the list of initialised connections for matching 
            List connections = new ArrayList(_connections);
            Iterator iter = connections.iterator();
            while (iter.hasNext()) {
                ManagedConnection connection
                            = (ManagedConnection) iter.next();
                PoolEntry entry = (PoolEntry) _entries.get(connection);
                if (entry == null || !entry.isInitialised()) {
                    iter.remove();
                }
            }
            if (!connections.isEmpty()) {
                result = _factory.matchManagedConnections(connections,
                                                          principal, info);
            }
            if (result != null) {
                // return the handle corresponding to the connection
                result = (ManagedConnection) _handles.get(result);
            } else {
                List accepted = new ArrayList(_accepted);
                result = _factory.matchManagedConnections(accepted, principal,
                                                          info);
            }
        }
        return result;
    }

    /**
     * Returns a matched acceptor from the set of pooled connections.
     *
     * @param info the connection request info
     * @return the first acceptable match, or <code>null</code> if none is
     *         found
     * @throws ResourceException for any error
     */
    public ManagedConnectionAcceptor matchManagedConnectionAcceptors(
            ConnectionRequestInfo info) throws ResourceException {

        return _factory.matchManagedConnectionAcceptors(_acceptors, info);
    }

    /**
     * Returns a listener for handling accepted connections.
     *
     * @return a listener for handling accepted connections
     */
    public ManagedConnectionAcceptorListener
            getManagedConnectionAcceptorListener() {
        return this;
    }

    /**
     * Invoked when a new connection is accepted.
     *
     * @param acceptor   the acceptor which created the connection
     * @param connection the accepted connection
     */
    public void accepted(ManagedConnectionAcceptor acceptor,
                         ManagedConnection connection) {
        try {
            add(connection, true);
        } catch (ResourceException exception) {
            _log.debug("Failed to accept connection", exception);
        }
    }

    /**
     * Notifies closure of a connection. The <code>ManagedConnection</code>
     * instance invokes this to notify its registered listeners when the peer
     * closes the connection.
     *
     * @param source the managed connection that is the source of the event
     */
    public void closed(ManagedConnection source) {
        if (_log.isDebugEnabled()) {
            _log.debug("Connection " + source + " closed by peer, destroying");
        }
        remove(source);
    }

    /**
     * Notifies a connection related error. The <code>ManagedConnection</code>
     * instance invokes this to notify of the occurrence of a physical
     * connection-related error.
     *
     * @param source    the managed connection that is the source of the event
     * @param throwable the error
     */
    public void error(ManagedConnection source, Throwable throwable) {
        if (_log.isDebugEnabled()) {
            _log.debug("Error on connection " + source + ", destroying",
                       throwable);
        }
        remove(source);
    }

    /**
     * Notifies of a successful ping.
     *
     * @param source the managed connection that is the source of the event
     */
    public void pinged(ManagedConnection source) {
        ManagedConnectionHandle handle
                = (ManagedConnectionHandle) _handles.get(source);
        if (handle != null) {
            handle.pinged();
        }
    }

    /**
     * Closes this connection pool, cleaning up any allocated resources.
     *
     * @throws ResourceException for any error
     */
    public void close() throws ResourceException {
        ManagedConnectionAcceptor[] acceptors =
                (ManagedConnectionAcceptor[]) _acceptors.toArray(
                        new ManagedConnectionAcceptor[0]);
        _acceptors.clear();

        for (int i = 0; i < acceptors.length; ++i) {
            acceptors[i].close();
        }

        ManagedConnection[] connections =
                (ManagedConnection[]) _entries.keySet().toArray(
                        new ManagedConnection[0]);
        for (int i = 0; i < connections.length; ++i) {
            connections[i].destroy();
        }
        _entries.clear();

        _accepted.clear();
        _connections.clear();

        stopReaper();
    }

    /**
     * Invoked when an acceptor receives an error.
     *
     * @param acceptor  the acceptor which received the error
     * @param throwable the error
     */
    public void error(ManagedConnectionAcceptor acceptor,
                      Throwable throwable) {
        _acceptors.remove(acceptor);

        String uri = "<unknown>";
        try {
            uri = acceptor.getURI().toString();
        } catch (ResourceException ignore) {
            // no-op
        }
        _log.error("Failed to accept connections on URI=" + uri,
                   throwable);

        try {
            acceptor.close();
        } catch (ResourceException exception) {
            if (_log.isDebugEnabled()) {
                _log.debug("Failed to close acceptor, URI=" + uri, exception);
            }
        }
    }

    /**
     * Sets the listener for caller events.
     *
     * @param listener the listener
     */
    public void setCallerListener(CallerListener listener) {
        _listener = listener;
    }

    /**
     * Notifies when a managed connection is idle.
     *
     * @param connection the idle connection
     */
    protected synchronized void idle(ManagedConnectionHandle connection) {
        connection.clearUsed();
        if (_daemon != null) {
            _daemon.executeAfterDelay(_idlePeriod, new IdleReaper());
        }
    }

    /**
     * Adds a connection to the pool. If the connection was created, a {@link
     * ManagedConnectionHandle} will be returned, wrapping the supplied
     * connection.
     *
     * @param connection the connection to add
     * @param accepted   if <code>true</code> the connection was accepted via an
     *                   {@link ManagedConnectionAcceptor}, otherwise it was
     *                   created via
     *                   {@link ManagedConnectionFactory#createManagedConnection}
     * @return the (possibly wrapped) connection
     * @throws ResourceException if the connection cannot be added
     */
    protected ManagedConnection add(ManagedConnection connection,
                                    boolean accepted) throws ResourceException {
        ManagedConnection result;

        PoolEntry entry = new PoolEntry(connection, accepted);
        _entries.put(connection, entry);
        ManagedConnection handle = new ManagedConnectionHandle(
                this, connection, _resolver);
        _handles.put(connection, handle);
        if (accepted) {
            _accepted.add(connection);
            result = connection;
        } else {
            _connections.add(connection);
            result = handle;
        }
        ContextInvocationHandler handler = new ContextInvocationHandler(
                _handler, _resolver, result);
        try {
            connection.setInvocationHandler(handler);
            connection.setConnectionEventListener(this);
        } catch (ResourceException exception) {
            try {
                _log.debug("Failed to initialise connection, destroying",
                           exception);
                connection.destroy();
            } catch (ResourceException nested) {
                _log.debug("Failed to destroy connection", nested);
            } finally {
                _entries.remove(connection);
                _handles.remove(connection);
                if (accepted) {
                    _accepted.remove(connection);
                } else {
                    _connections.remove(connection);
                }
            }
            // propagate the exception
            throw exception;
        }

        // mark the connection as initialised and therefore available for
        // reaping
        entry.setInitialised();

        startReaper();

        return result;
    }

    /**
     * Remove a connection from the pool.
     *
     * @param connection the connection to remove
     */
    protected void remove(ManagedConnection connection) {
        PoolEntry entry = (PoolEntry) _entries.remove(connection);
        if (entry != null) {
            _handles.remove(connection);
            if (entry.getAccepted()) {
                _accepted.remove(connection);
            } else {
                _connections.remove(connection);
            }
            URI remoteURI = null;
            URI localURI = null;
            try {
                remoteURI = connection.getRemoteURI();
                localURI = connection.getLocalURI();
            } catch (ResourceException exception) {
                _log.debug("Failed to get connection URIs", exception);
            }

            try {
                connection.destroy();
            } catch (ResourceException exception) {
                _log.debug("Failed to destroy connection", exception);
            }
            if (remoteURI != null && localURI != null) {
                notifyDisconnection(remoteURI, localURI);
            }
        } else {
            _log.debug("ManagedConnection not found");
        }
        if (_entries.isEmpty()) {
            stopReaper();
        }
    }

    /**
     * Notify of a disconnection.
     *
     * @param remoteURI the remote address that the client is calling from
     * @param localURI  the local address that the client is calling to
     */
    private void notifyDisconnection(URI remoteURI, URI localURI) {
        CallerListener listener = _listener;
        if (listener != null) {
            listener.disconnected(new CallerImpl(remoteURI, localURI));
        }
    }

    /**
     * Starts the reaper for dead/idle connections, if needed.
     */
    private synchronized void startReaper() {
        if (_daemon == null) {
            _daemon = new ClockDaemon();
            ThreadFactory creator =
                    new ThreadFactory(null, "ManagedConnectionReaper", false);
            _daemon.setThreadFactory(creator);

            if (_reapInterval > 0) {
                _daemon.executePeriodically(_reapInterval, new DeadReaper(),
                                            false);
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
     * Reap idle connections.
     */
    private void reapIdleConnections() {
        Map.Entry[] entries = (Map.Entry[]) _handles.entrySet().toArray(
                new Map.Entry[0]);
        for (int i = 0; i < entries.length && !stopReaping(); ++i) {
            Map.Entry entry = entries[i];
            ManagedConnection connection =
                    (ManagedConnection) entry.getKey();
            PoolEntry pooled = (PoolEntry) _entries.get(connection);
            if (pooled != null && pooled.isInitialised()) {
                ManagedConnectionHandle handle =
                        (ManagedConnectionHandle) entry.getValue();
                if (handle.canDestroy()) {
                    if (_log.isDebugEnabled()) {
                        try {
                            _log.debug("Reaping idle connection, URI="
                                    + connection.getRemoteURI()
                                    + ", local URI="
                                    + connection.getLocalURI());
                        } catch (ResourceException ignore) {
                            // do nothing
                        }
                    }
                    remove(connection);
                }
            }
        }
    }

    /**
     * Reap dead connections.
     */
    private void reapDeadConnections() {
        Map.Entry[] entries = (Map.Entry[]) _handles.entrySet().toArray(
                new Map.Entry[0]);
        for (int i = 0; i < entries.length && !stopReaping(); ++i) {
            Map.Entry entry = entries[i];
            ManagedConnection connection =
                    (ManagedConnection) entry.getKey();
            PoolEntry pooled = (PoolEntry) _entries.get(connection);
            if (pooled != null && pooled.isInitialised()) {
                ManagedConnectionHandle handle =
                        (ManagedConnectionHandle) entry.getValue();
                if (!handle.used()) {
                    // if the handle is unused, and is not waiting on a ping
                    // reply, ping the connection
                    if (handle.pinging()) {
                        if (handle.incPingWaits() > _reapDeadIterations) {
                            remove(connection);
                        }
                    } else {
                        try {
                            handle.ping();
                        } catch (ResourceException exception) {
                            if (_log.isDebugEnabled()) {
                                try {
                                    _log.debug(
                                            "Failed to ping connection, URI="
                                                    + connection.getRemoteURI()
                                                    + ", localURI="
                                                    + connection.getLocalURI());
                                } catch (ResourceException ignore) {
                                    // do nothing
                                }
                            }
                            remove(connection);
                        }
                    }
                } else {
                    handle.clearUsed();
                }
            }
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
     * Helper to return a property in seconds as a millisecond value.
     *
     * @param config the properties
     * @param key the property name
     * @param defaultValue the value to return if the property doesn't exist.
     * @return the property in milliseconds
     * @throws ResourceException if the property isn't a valid integer
     */
    private long getPropertyMillis(Properties config, String key,
                                   int defaultValue) throws ResourceException {
        int seconds = config.getInt(key, defaultValue);
        if (seconds < 0) {
            seconds = 0;
        }
        return seconds * 1000;
    }

    /**
     * Helper class for reaping idle connections.
     */
    private class IdleReaper implements Runnable {

        /**
         * Run the reaper.
         */
        public void run() {
            synchronized (_reapLock) {
                try {
                    reapIdleConnections();
                } catch (Throwable exception) {
                    _log.error(exception, exception);
                }
            }
        }
    }

    /**
     * Helper class for reaping dead connections.
     */
    private class DeadReaper implements Runnable {

        /**
         * Run the reaper.
         */
        public void run() {
            try {
                reapDeadConnections();
            } catch (Throwable exception) {
                _log.error(exception, exception);
            }
        }
    }

}
