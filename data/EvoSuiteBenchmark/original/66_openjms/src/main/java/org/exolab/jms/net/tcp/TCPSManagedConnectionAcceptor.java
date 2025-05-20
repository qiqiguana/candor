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
 * Copyright 2003-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: TCPSManagedConnectionAcceptor.java,v 1.7 2006/12/16 12:37:17 tanderson Exp $
 */
package org.exolab.jms.net.tcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.net.connector.ManagedConnection;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.connector.URIRequestInfo;
import org.exolab.jms.net.socket.SocketManagedConnectionAcceptor;
import org.exolab.jms.net.socket.SocketRequestInfo;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.util.SSLProperties;
import org.exolab.jms.net.util.SSLHelper;


/**
 * Accepts SSL socket connections. constructing new
 * <code>TCPSManagedConnection</code> instances to serve them.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.7 $ $Date: 2006/12/16 12:37:17 $
 */
class TCPSManagedConnectionAcceptor
        extends SocketManagedConnectionAcceptor {

    /**
     * Construct a new <code>TCPSConnectionAcceptor</code>.
     * <p/>
     * This creates a server socket with the specified port and listen backlog.
     * <p/>
     * If {@link SocketRequestInfo#getBindAll()} flag can be used on multi-homed
     * hosts to limit the addresses on which connections are accepted.
     * If <code>false</code>, the socket will only accept connections on the
     * address specified by {@link SocketRequestInfo#getHostAddress}.
     * If <code>true</code> it will accept connections on all local addresses.
     * <p/>
     * The port returned by
     * {@link URIRequestInfo#getPort} must be between 0 and 65535, inclusive
     *
     * @param authenticator the connection authenticator
     * @param info          the connection request info
     * @throws ResourceException if a server socket cannot be created
     */
    public TCPSManagedConnectionAcceptor(Authenticator authenticator,
                                         TCPSRequestInfo info)
            throws ResourceException {
        super(authenticator, info);
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
        TCPSRequestInfo info = (TCPSRequestInfo) getRequestInfo();
        SSLProperties properties = info.getSSLProperties();
        if (properties != null) {
            SSLHelper.configure(properties);    
        }
        ServerSocketFactory factory =
                SSLServerSocketFactory.getDefault();
        SSLServerSocket socket = (SSLServerSocket) factory.createServerSocket(
                port, backlog, host);
        socket.setNeedClientAuth(info.getNeedClientAuth());
        return socket;
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
    protected ManagedConnection createManagedConnection(
            URI uri, Socket socket, Authenticator authenticator)
            throws ResourceException {
        return new TCPSManagedConnection(uri, socket, authenticator);
    }

}
