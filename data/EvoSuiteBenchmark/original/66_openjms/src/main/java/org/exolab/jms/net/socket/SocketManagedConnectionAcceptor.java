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
 * $Id: SocketManagedConnectionAcceptor.java,v 1.8 2006/12/16 12:37:17 tanderson Exp $
 */
package org.exolab.jms.net.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.net.connector.ManagedConnection;
import org.exolab.jms.net.connector.ManagedConnectionAcceptor;
import org.exolab.jms.net.connector.ManagedConnectionAcceptorListener;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.connector.URIRequestInfo;
import org.exolab.jms.net.uri.URI;


/**
 * A {@link ManagedConnectionAcceptor} for accepting socket connections.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.8 $ $Date: 2006/12/16 12:37:17 $
 */
public abstract class SocketManagedConnectionAcceptor
        implements ManagedConnectionAcceptor {

    /**
     * The connection authenticator.
     */
    private Authenticator _authenticator;

    /**
     * The underlying socket.
     */
    private ServerSocket _socket;

    /**
     * The URI denoting this acceptor.
     */
    private final URI _uri;

    /**
     * The thread group for all threads associated with this.
     */
    private final ThreadGroup _group;

    /**
     * The connection dispatcher.
     */
    private Dispatcher _dispatcher;

    /**
     * The connection request info used to match acceptors.
     */
    private final SocketRequestInfo _info;


    /**
     * The logger.
     */
    private static final Log _log =
            LogFactory.getLog(SocketManagedConnectionAcceptor.class);


    /**
     * Construct a new <code>SocketManagedConnectionAcceptor</code>.
     * <p/>
     * This creates a server socket with the specified port and listen backlog.
     * <p/>
     * If {@link SocketRequestInfo#getBindAll()} flag can be used on multi-homed
     * hosts to limit the addresses on which connections are accepted.
     * If <code>false</code>, the socket will only accept connections on the
     * address specified by {@link SocketRequestInfo#getHostAddress}.
     * If <code>true</code> it will accept connections on all local addresses.
     * <p/>
     * The port returned by {@link URIRequestInfo#getPort} must be between 0 and
     * 65535, inclusive
     *
     * @param authenticator the connection authenticator
     * @param info          the connection request info
     * @throws ResourceException if a server socket cannot be created
     */
    public SocketManagedConnectionAcceptor(Authenticator authenticator,
                                           SocketRequestInfo info)
            throws ResourceException {

        if (authenticator == null) {
            throw new IllegalArgumentException(
                    "Argument 'authenticator' is null");
        }
        if (info == null) {
            throw new IllegalArgumentException("Argument 'info' is null");
        }

        _authenticator = authenticator;
        _uri = info.getURI();
        _info = info;
        int port = info.getPort();
        try {
            InetAddress host = null;
            if (!info.getBindAll()) {
                host = info.getHostAddress();
            }
            int backlog = info.getConnectionRequestQueueSize();
            _socket = createServerSocket(port, backlog, host);
        } catch (IOException exception) {
            throw new ResourceException(
                    "Failed to create server socket for URI=" + info.getURI(),
                    exception);
        }

        _group = new ThreadGroup(_uri.toString());
        StringBuffer name = new StringBuffer();
        name.append(_uri.toString());
        name.append("[server]");
    }

    /**
     * Start accepting connections.
     *
     * @param listener the listener to delegate accepted connections to
     * @throws ResourceException if connections cannot be accepted
     */
    public synchronized void accept(ManagedConnectionAcceptorListener listener)
            throws ResourceException {
        if (_dispatcher != null) {
            throw new ResourceException(
                    "Acceptor is already accepting connections on URI=" + _uri);
        }

        _dispatcher = new Dispatcher(listener);
        _dispatcher.start();
        if (_log.isDebugEnabled()) {
            _log.debug("Acceptor accepting requests at URI=" + _uri);
        }
    }

    /**
     * Returns the connection request info used to construct this.
     *
     * @return the connection request info
     */
    public SocketRequestInfo getRequestInfo() {
        return _info;
    }

    /**
     * Returns the URI that this acceptor is accepting connections on.
     *
     * @return the URI that this acceptor is accepting connections on
     */
    public URI getURI() {
        return _uri;
    }

    /**
     * Stop accepting connection requests, and clean up any allocated
     * resources.
     *
     * @throws ResourceException generic exception if the operation fails
     */
    public synchronized void close() throws ResourceException {
        if (_log.isDebugEnabled()) {
            _log.debug("Acceptor shutting down at URI=" + _uri);
        }
        if (_dispatcher != null) {
            // dispatcher responsible for closing socket
            _dispatcher.close();
            if (Thread.currentThread() != _dispatcher) {
                try {
                    _dispatcher.join();  // wait for the dispatcher to terminate
                } catch (InterruptedException ignore) {
                    // don't care.
                }
            }
            _dispatcher = null;
            _socket = null;
        } else if (_socket != null) {
            try {
                _socket.close();
                _socket = null;
            } catch (IOException exception) {
                throw new ResourceException("Failed to close socket",
                                            exception);
            }
        }
    }

    /**
     * Create a new server socket.
     *
     * @param port    the port to listen on
     * @param backlog the listen backlog
     * @param host    if non-null, specifies to only accept connections to the
     *                specified address. If null, accept connections on any/all
     *                local addresses.
     * @return a new server socket, listening on <code>port</code>
     * @throws IOException if the socket can't be created
     */
    protected ServerSocket createServerSocket(int port, int backlog,
                                              InetAddress host)
            throws IOException {
        return new ServerSocket(port, backlog, host);
    }

    /**
     * Create a new server-side <code>ManagedConnection</code> for an accepted
     * socket connection.
     *
     * @param uri           the URI denoting this acceptor
     * @param socket        the accepted socket connection
     * @param authenticator the connection authenticator
     * @return a new server-side managed connection
     * @throws ResourceException if the managed connection can't be created
     */
    protected abstract ManagedConnection createManagedConnection(
            URI uri, Socket socket, Authenticator authenticator)
            throws ResourceException;

    /**
     * Accepts connections.
     */
    private class Dispatcher extends Thread {

        /**
         * The listener to delegate accepted connections to.
         */
        private final ManagedConnectionAcceptorListener _listener;

        /**
         * Determines if the dispatcher is closed.
         */
        private volatile boolean _closed = false;

        /**
         * Construct a new <code>Dispatcher</code>.
         *
         * @param listener the listener to delegate accepted connections to
         */
        public Dispatcher(ManagedConnectionAcceptorListener listener) {
            super(_group, getURI() + "[acceptor]");
            _listener = listener;
        }

        /**
         * Close the dispatcher.
         */
        public void close() {
            _closed = true;
            try {
                _socket.close();
            } catch (IOException exception) {
                _log.debug(exception);
            }
        }

        /**
         * Accept connections.
         */
        public void run() {
            while (!_closed) {
                try {
                    Socket socket = _socket.accept();
                    socket.setTcpNoDelay(true);
                    ManagedConnection connection = createManagedConnection(
                            _uri, socket, _authenticator);
                    _listener.accepted(SocketManagedConnectionAcceptor.this,
                                       connection);
                } catch (Exception exception) {
                    if (!_closed) {
                        _listener.error(SocketManagedConnectionAcceptor.this,
                                        exception);
                    }
                    break;
                }
            }
        }
    }
}
