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
 * $Id: SocketManagedConnection.java,v 1.5 2006/12/16 12:37:17 tanderson Exp $
 */
package org.exolab.jms.net.socket;

import java.io.IOException;
import java.net.Socket;
import java.security.Principal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.net.connector.ConnectException;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.multiplexer.Endpoint;
import org.exolab.jms.net.multiplexer.MultiplexedManagedConnection;
import org.exolab.jms.net.uri.InvalidURIException;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.uri.URIHelper;


/**
 * <code>SocketManagedConnection</code> manages multiple <code>Connection</code>
 * instances over a single socket.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.5 $ $Date: 2006/12/16 12:37:17 $
 */
public abstract class SocketManagedConnection
        extends MultiplexedManagedConnection {

    /**
     * The underlying socket.
     */
    private Socket _socket;

    /**
     * The remote address to which this is connected.
     */
    private URI _remoteURI;

    /**
     * The the local address that this connection is bound to.
     */
    private URI _localURI;

    /**
     * The alternative URI that the remote address is known as.
     */
    private URI _alternativeURI;

    /**
     * The logger.
     */
    protected static final Log _log =
            LogFactory.getLog(SocketManagedConnection.class);

    /**
     * Construct a new client <code>SocketManagedConnection</code>.
     *
     * @param principal the security principal
     * @param info      the connection request info
     * @throws ResourceException if a socket cannot be created
     */
    public SocketManagedConnection(Principal principal,
                                   SocketRequestInfo info)
            throws ResourceException {
        super(principal);
        if (info == null) {
            throw new IllegalArgumentException("Argument 'info' is null");
        }
        Socket socket = createSocket(info);
        init(info.getURI(), socket);
    }

    /**
     * Construct a new server <code>SocketManagedConnection</code>.
     *
     * @param uri           the URI the acceptor was listening on
     * @param socket        the socket
     * @param authenticator the connection authenticator
     * @throws ResourceException if an error occurs accessing the socket
     */
    public SocketManagedConnection(URI uri, Socket socket,
                                   Authenticator authenticator)
            throws ResourceException {
        super(authenticator);
        if (uri == null) {
            throw new IllegalArgumentException("Argument 'uri' is null");
        }
        if (socket == null) {
            throw new IllegalArgumentException("Argument 'socket' is null");
        }
        if (authenticator == null) {
            throw new IllegalArgumentException(
                    "Argument 'authenticator' is null");
        }
        init(uri, socket);
    }

    /**
     * Returns the remote address to which this is connected.
     *
     * @return the remote address to which this is connected
     */
    public URI getRemoteURI() {
        return _remoteURI;
    }

    /**
     * Returns the local address that this connection is bound to.
     *
     * @return the local address that this connection is bound to
     */
    public URI getLocalURI() {
        return _localURI;
    }

    /**
     * The alternative URI that the remote address is known as.
     *
     * @return alternative URI that the remote address is known as.
     * May be <code>null</code>.
     */
    public URI getAlternativeURI() {
        return _alternativeURI;
    }

    /**
     * Creates a new socket.
     *
     * @param info the connection request info
     * @return a new socket
     * @throws ResourceException if a socket can't be created
     */
    protected Socket createSocket(SocketRequestInfo info)
            throws ResourceException  {
        Socket result;
        try {
            result = createSocketProtected(info.getHost(), info.getPort());
            _alternativeURI = info.getAlternativeURI();
        } catch (ResourceException exception) {
            _alternativeURI = info.getURI();
            URI uri = info.getAlternativeURI();
            if (uri == null) {
                throw exception;
            }
            if (_log.isDebugEnabled()) {
                _log.debug("Failed to connect using URI=" + info.getURI()
                           + ", attempting URI=" + uri);
            }
            result = createSocketProtected(uri.getHost(), uri.getPort());
        }
        return result;
    }

    /**
     * Creates a new socket.
     *
     * @param host the host to connect to
     * @param port the port to connect to
     * @return a new socket
     * @throws IOException for any I/O error
     * @throws SecurityException if permission is denied
     */
    protected Socket createSocket(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        socket.setTcpNoDelay(true);
        return socket;
    }

    /**
     * Returns the endpoint to multiplex data over.
     *
     * @return the endpoint to multiplex data over
     * @throws IOException for any I/O error
     */
    protected Endpoint createEndpoint() throws IOException {
        return new SocketEndpoint(_remoteURI.getScheme(), _socket);
    }

    /**
     * Initialises this connection.
     *
     * @param uri    the URI representing this connection
     * @param socket the socket
     * @throws ResourceException for any error
     */
    protected void init(URI uri, Socket socket) throws ResourceException {
        _socket = socket;
        try {
            String localHost = socket.getLocalAddress().getHostAddress();
            int localPort = socket.getLocalPort();
            _localURI = URIHelper.create(uri.getScheme(), localHost, localPort);

            String remoteHost = socket.getInetAddress().getHostAddress();
            int remotePort = socket.getPort();
            _remoteURI = URIHelper.create(uri.getScheme(), remoteHost,
                                          remotePort);
        } catch (InvalidURIException exception) {
            throw new ResourceException("Failed to create URI", exception);
        }
    }

    /**
     * Creates a new socket, adapting exceptions to instances of
     * ResourceException.
     * <p/>
     * This adapts java.net.ConnectException to ConnectException and
     * all other exceptions (including SecurityExceptions) to
     * to an instance of ResourceException.
     *
     * @param host the host to connect to
     * @param port the port to connect to
     * @return a new socket
     * @throws ResourceException if the socket can't be created
     */
    private Socket createSocketProtected(String host, int port)
            throws ResourceException {
        try {
            return createSocket(host, port);
        } catch (Exception exception) { // IOException, SecurityException
            String msg = "Failed to connect to " + host + ":" + port;
            _log.debug(msg, exception);
            if (exception instanceof java.net.ConnectException) {
                throw new ConnectException(msg, exception);
            }
            throw new ResourceException(msg, exception);
        }
    }


}
