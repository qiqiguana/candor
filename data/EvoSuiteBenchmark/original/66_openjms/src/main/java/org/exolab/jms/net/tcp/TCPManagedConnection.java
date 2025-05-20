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
 * $Id: TCPManagedConnection.java,v 1.5 2005/07/22 23:40:37 tanderson Exp $
 */
package org.exolab.jms.net.tcp;

import java.net.Socket;
import java.security.Principal;

import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.socket.SocketManagedConnection;
import org.exolab.jms.net.socket.SocketRequestInfo;


/**
 * <code>TCPManagedConnection</code> manages multiple <code>Connection</code>
 * instances over a single TCP socket.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.5 $ $Date: 2005/07/22 23:40:37 $
 */
class TCPManagedConnection extends SocketManagedConnection {

    /**
     * Construct a new client <code>TCPManagedConnection</code>.
     *
     * @param principal the security principal
     * @param info      the connection request info
     * @throws ResourceException if a socket cannot be created
     */
    public TCPManagedConnection(Principal principal, SocketRequestInfo info)
            throws ResourceException {
        super(principal, info);
    }

    /**
     * Construct a new server <code>TCPManagedConnection</code>.
     *
     * @param uri           the URI the acceptor was listening on
     * @param socket        the tcp socket
     * @param authenticator the connection authenticator
     * @throws ResourceException if an error occurs accessing the socket
     */
    public TCPManagedConnection(URI uri, Socket socket,
                                Authenticator authenticator)
            throws ResourceException {
        super(uri, socket, authenticator);
    }

}
