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
 * Copyright 2004-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: ManagedConnectionHandle.java,v 1.9 2008/01/07 13:00:16 tanderson Exp $
 */
package org.exolab.jms.net.connector;

import org.exolab.jms.net.uri.URI;

import java.security.Principal;


/**
 * A handle to a {@link ManagedConnection} that tracks its utilisation.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.9 $ $Date: 2008/01/07 13:00:16 $
 */
final class ManagedConnectionHandle implements ManagedConnection {

    /**
     * The connection pool that owns this.
     */
    private final DefaultConnectionPool _pool;

    /**
     * The connection to delegate all requests to.
     */
    private final ManagedConnection _connection;

    /**
     * The connection factory for resolving connections via their URI.
     */
    private final ConnectionFactory _resolver;

    /**
     * The no. of active ConnectionHandle instances.
     */
    private int _connectionCount = 0;

    /**
     * Determines if the connection has been since {@link #clearUsed} was
     * last invoked.
     */
    private boolean _used = false;

    /**
     * Determines if a ping is in progress.
     */
    private boolean _pinging = false;

    /**
     * The no. of times {@link #incPingWaits} has been invoked since the last
     * {@link #ping}.
     */
    private int _pingWaits;


    /**
     * Construct a new <code>ManagedConnectionHandle</code>.
     *
     * @param pool       the pool that owns this
     * @param connection the connection to delegate requests to
     * @param resolver   the connection factory for resolving connections via
     *                   their URI.
     */
    public ManagedConnectionHandle(DefaultConnectionPool pool,
                                   ManagedConnection connection,
                                   ConnectionFactory resolver) {
        _pool = pool;
        _connection = connection;
        _resolver = resolver;
    }

    /**
     * Registers a handler for handling invocations on objects exported via this
     * connection. Once a handler is registered, it cannot be de-registered.
     *
     * @param handler the invocation handler
     * @throws IllegalStateException if a handler is already registered
     * @throws ResourceException     for any error
     */
    public void setInvocationHandler(InvocationHandler handler)
            throws ResourceException {
        _connection.setInvocationHandler(handler);
    }

    /**
     * Registers a connection event listener.
     *
     * @param listener the connection event listener
     * @throws ResourceException for any error
     */
    public void setConnectionEventListener(ManagedConnectionListener listener)
            throws ResourceException {
        _connection.setConnectionEventListener(listener);
    }

    /**
     * Creates a new connection handle for the underlying physical connection.
     *
     * @return a new connection handle
     * @throws IllegalStateException if an <code>InvocationHandler</code> hasn't
     *                               been registered
     * @throws ResourceException     for any error
     */
    public Connection getConnection() throws ResourceException {
        Connection connection = _connection.getConnection();
        return new ConnectionHandle(connection);
    }

    /**
     * Ping the connection. The connection event listener will be notified
     * if the ping succeeds.
     *
     * @throws ResourceException for any error
     */
    public synchronized void ping() throws ResourceException {
        try {
            _pinging = true;
            _pingWaits = 0;
            _connection.ping();
        } catch (ResourceException exception) {
            _pinging = false;
            throw exception;
        }
    }

    /**
     * Determines if a ping has been sent.
     *
     * @return <code>true</code> if a ping has been sent
     */
    public synchronized boolean pinging() {
        return _pinging;
    }

    /**
     * Notifies of a ping response.
     * Clears the ping status, and resets the ping wait to <code>0</code>.
     */
    public synchronized void pinged() {
        _pinging = false;
        _pingWaits = 0;
    }

    /**
     * Increments the no. of times this connection has waited for a ping
     * response.
     *
     * @return the no. of times this connection has waited for a ping response
     */
    public synchronized int incPingWaits() {
        return ++_pingWaits;
    }

    /**
     * Returns the remote address to which this is connected.
     *
     * @return the remote address to which this is connected
     * @throws ResourceException for any error
     */
    public URI getRemoteURI() throws ResourceException {
        return _connection.getRemoteURI();
    }

    /**
     * Returns the local address that this connection is bound to.
     *
     * @return the local address that this connection is bound to
     * @throws ResourceException for any error
     */
    public URI getLocalURI() throws ResourceException {
        return _connection.getLocalURI();
    }

    /**
     * Returns the principal associated with this connection.
     *
     * @return the principal associated with this connection,
     *         or <code>null<code> if none is set
     * @throws ResourceException for any error
     */
    public Principal getPrincipal() throws ResourceException {
        return _connection.getPrincipal();
    }

    /**
     * Destroys the physical connection.
     *
     * @throws ResourceException for any error
     */
    public void destroy() throws ResourceException {
        _connection.destroy();
    }

    /**
     * Marks the connection as being used.
     */
    public synchronized void setUsed() {
        _used = true;
    }

    /**
     * Determines if the connection has been used since {@link #clearUsed}
     * was last invoked.
     */
    public synchronized boolean used() {
        return _used;
    }

    /**
     * Marks the connection as being unused.
     */
    public synchronized void clearUsed() {
        _used = false;
    }

    /**
     * Determines if the connection can be destroyed. The connection
     * can be destroyed if there are no associated {@link Connection} instances,
     * and it has been used since {@link #clearUsed} was invoked.
     *
     * @return <code>true</code> if the connection may be destroyed
     */
    public synchronized boolean canDestroy() {
        return (_connectionCount == 0) && (!_used);
    }

    /**
     * Increment the no. of references to this connection.
     */
    private synchronized void incActiveConnections() {
        ++_connectionCount;
    }

    /**
     * Decrement the no. of references to this connection.
     */
    private synchronized void decActiveConnections() {
        --_connectionCount;
        if (_connectionCount <= 0) {
            _pool.idle(this);
        }
    }

    public boolean usedSinceLastPing() {
        return false;
    }

    /**
     * Helper class reference count a {@link Connection} instance.
     */
    private class ConnectionHandle implements Connection {

        /**
         * The connection to delegate all requests to.
         */
        private Connection _connection;

        /**
         * Construct a new <code>ConnectionHandle</code>.
         *
         * @param connection the connection to delegate requests to
         */
        public ConnectionHandle(Connection connection) {
            _connection = connection;
            incActiveConnections();
        }

        /**
         * Invoke a method on a remote object.
         *
         * @param request the request
         * @return the result of the invocation
         * @throws Throwable for any transport error
         */
        public Response invoke(Request request) throws Throwable {
            Response response = null;
            setUsed();
            try {
                ConnectionContext.push(getPrincipal(), _resolver);
                response = _connection.invoke(request);
            } finally {
                ConnectionContext.pop();
            }
            return response;
        }

        /**
         * Returns the remote address to which this is connected.
         *
         * @return the remote address to which this is connected
         * @throws ResourceException for any error
         */
        public URI getRemoteURI() throws ResourceException {
            return _connection.getRemoteURI();
        }

        /**
         * Returns the local address that this connection is bound to.
         *
         * @return the local address that this connection is bound to
         * @throws ResourceException for any error
         */
        public URI getLocalURI() throws ResourceException {
            return _connection.getLocalURI();
        }

        /**
         * Close this connection, releasing any allocated resources.
         *
         * @throws ResourceException for any error.
         */
        public void close() throws ResourceException {
            try {
                _connection.close();
            } finally {
                _connection = null;
                decActiveConnections();
            }
        }

        /**
         * Called by the garbage collector when there are no more references to
         * the object.
         */
        protected void finalize() throws Throwable {
            if (_connection != null) {
                decActiveConnections();
            }
            super.finalize();
        }
    }
}
