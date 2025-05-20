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
 * $Id: AbstractHTTPManagedConnection.java,v 1.7 2006/12/16 12:37:17 tanderson Exp $
 */
package org.exolab.jms.net.http;

import java.io.IOException;
import java.net.Socket;
import java.security.Principal;

import org.exolab.jms.common.uuid.UUIDGenerator;
import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.net.connector.ConnectException;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.connector.SecurityException;
import org.exolab.jms.net.multiplexer.Endpoint;
import org.exolab.jms.net.multiplexer.MultiplexedManagedConnection;
import org.exolab.jms.net.multiplexer.Multiplexer;
import org.exolab.jms.net.socket.SocketEndpoint;
import org.exolab.jms.net.uri.InvalidURIException;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.uri.URIHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <code>AbstractHTTPManagedConnection</code> manages multiple
 * <code>Connection</code> instances over a single HTTP connection.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.7 $ $Date: 2006/12/16 12:37:17 $
 */
abstract class AbstractHTTPManagedConnection
        extends MultiplexedManagedConnection {

    /**
     * The endpoint.
     */
    protected Endpoint _endpoint;

    /**
     * The remote address to which this is connected.
     */
    private URI _remoteURI;

    /**
     * The local address that this connection is bound to.
     */
    private URI _localURI;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(
            AbstractHTTPManagedConnection.class);


    /**
     * Construct a new client <code>HTTPManagedConnection</code>.
     *
     * @param principal the security principal.
     * @param info      the connection request info
     * @throws ResourceException if a socket cannot be created
     */
    public AbstractHTTPManagedConnection(Principal principal,
                                         HTTPRequestInfo info)
            throws ResourceException {
        super(principal);
        if (info == null) {
            throw new IllegalArgumentException("Argument 'info' is null");
        }
        final URI uri = info.getURI();
        try {
            _endpoint = new HTTPEndpoint(info);
        } catch (IOException exception) {
            _log.debug(exception, exception);
            throw new ConnectException("Failed to connect to URI="
                                       + info.getURI(), exception);
        }
        _remoteURI = URIHelper.convertHostToAddress(uri);

        try {
            _localURI = URIHelper.create(uri.getScheme(), null, -1,
                                         UUIDGenerator.create());
        } catch (InvalidURIException exception) {
            _log.debug(exception, exception);
             throw new ResourceException("Failed to generate local URI",
                                        exception);
        }

    }

    /**
     * Construct a new server <code>HTTPManagedConnection</code>.
     *
     * @param uri           the URI the acceptor was listening on
     * @param socket        the socket
     * @param authenticator the connection authenticator
     * @throws ResourceException if an error occurs accessing the socket
     */
    public AbstractHTTPManagedConnection(URI uri, Socket socket,
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
        String scheme = uri.getScheme();
        int localPort = socket.getLocalPort();

        try {
            _localURI = URIHelper.create(scheme, uri.getHost(), localPort);
        } catch (InvalidURIException exception) {
            _log.debug(exception, exception);
             throw new ResourceException("Failed to generate local URI",
                                        exception);
        }
        try {
            _endpoint = new SocketEndpoint(scheme, socket);
        } catch (IOException exception) {
            _log.debug(exception, exception);
             throw new ResourceException("Failed to create endpoint", exception);
        }
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
     * Returns the endpoint to multiplex data over.
     *
     * @return the endpoint to multiplex data over
     * @throws IOException for any I/O error
     */
    protected Endpoint createEndpoint() throws IOException {
        return _endpoint;
    }

    /**
     * Create a new client-side multiplexer.
     *
     * @param endpoint  the endpoint to multiplex messages over
     * @param principal the security principal
     * @return a new client-side multiplexer
     * @throws IOException       if an I/O error occurs
     * @throws SecurityException if connection is refused by the server
     */
    protected Multiplexer createMultiplexer(Endpoint endpoint,
                                            Principal principal)
            throws IOException, SecurityException {
        return new HTTPMultiplexer(this, endpoint, _localURI, principal);
    }

    /**
     * Create a new server-side multiplexer.
     *
     * @param endpoint      the endpoint to multiplex messages over
     * @param authenticator the connection authetnicator
     * @throws IOException       if an I/O error occurs
     * @throws ResourceException if the authenticator cannot authenticate
     * @return a new server-side multiplexer
     */
    protected Multiplexer createMultiplexer(Endpoint endpoint,
                                            Authenticator authenticator)
            throws IOException, ResourceException {
        HTTPMultiplexer result = new HTTPMultiplexer(this, endpoint,
                                                     authenticator);
        _remoteURI = result.getClientURI();
        return result;
    }

}
