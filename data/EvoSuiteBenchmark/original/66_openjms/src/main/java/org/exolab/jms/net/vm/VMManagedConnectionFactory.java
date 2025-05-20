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
 * $Id: VMManagedConnectionFactory.java,v 1.2 2005/04/17 14:01:31 tanderson Exp $
 */
package org.exolab.jms.net.vm;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;

import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.net.connector.ConnectionFactory;
import org.exolab.jms.net.connector.ConnectionManager;
import org.exolab.jms.net.connector.ConnectionRequestInfo;
import org.exolab.jms.net.connector.ManagedConnection;
import org.exolab.jms.net.connector.ManagedConnectionAcceptor;
import org.exolab.jms.net.connector.ManagedConnectionFactory;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.connector.URIRequestInfo;
import org.exolab.jms.net.uri.URI;


/**
 * A factory for {@link VMConnectionFactory}, {@link VMManagedConnection} and
 * {@link VMManagedConnectionAcceptor} instances.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/04/17 14:01:31 $
 */
public class VMManagedConnectionFactory implements ManagedConnectionFactory {

    /**
     * Creates a new connection factory.
     *
     * @param manager the connection manager
     * @return a new connection factory
     * @throws ResourceException if the factory cannot be created
     */
    public ConnectionFactory createConnectionFactory(ConnectionManager manager)
            throws ResourceException {
        return new VMConnectionFactory(this, manager);
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
        if (!(info instanceof URIRequestInfo)) {
            throw new ResourceException("Argument 'info' must be of type "
                                        + URIRequestInfo.class.getName());
        }

        return new VMManagedConnection(principal, (URIRequestInfo) info);
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
        if (!(info instanceof URIRequestInfo)) {
            throw new ResourceException("Argument 'info' must be of type "
                                        + URIRequestInfo.class.getName());
        }
        return new VMManagedConnectionAcceptor(authenticator,
                                               (URIRequestInfo) info);
    }

    /**
     * Returns a matched connection from the candidate set of connections.
     *
     * @param connections the candidate connections
     * @param principal   the security principal
     * @param info        the connection request info
     * @return the first acceptable match, or <code>null</code> if none is
     *         found
     * @throws ResourceException for any error
     */
    public ManagedConnection matchManagedConnections(List connections,
                                                     Principal principal,
                                                     ConnectionRequestInfo info)
            throws ResourceException {
        ManagedConnection result = null;

        if (info instanceof URIRequestInfo) {
            URIRequestInfo requestInfo = (URIRequestInfo) info;
            URI uri = requestInfo.getURI();
            Iterator iterator = connections.iterator();
            while (iterator.hasNext()) {
                VMManagedConnection connection =
                        (VMManagedConnection) iterator.next();
                if (connection.hasPrincipal(principal) &&
                    uri.equals(connection.getRemoteURI())) {
                    result = connection;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Returns a matched connection acceptor from the candidate set of
     * acceptors.
     *
     * @param acceptors the candidate connection acceptors
     * @param info      the connection request info
     * @return the first acceptable match, or <code>null</code> if none is
     *         found
     * @throws ResourceException for any error
     */
    public ManagedConnectionAcceptor matchManagedConnectionAcceptors(
            List acceptors, ConnectionRequestInfo info)
            throws ResourceException {
        ManagedConnectionAcceptor result = null;

        if (info instanceof URIRequestInfo) {
            URIRequestInfo requestInfo = (URIRequestInfo) info;
            URI uri = requestInfo.getURI();
            Iterator iterator = acceptors.iterator();
            while (iterator.hasNext()) {
                VMManagedConnectionAcceptor acceptor =
                        (VMManagedConnectionAcceptor) iterator.next();
                if (uri.equals(acceptor.getURI())) {
                    result = acceptor;
                    break;
                }
            }
        }
        return result;
    }

}
