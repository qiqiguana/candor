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
 * $Id: ConnectionPool.java,v 1.5 2005/06/04 14:51:06 tanderson Exp $
 */
package org.exolab.jms.net.connector;

import java.security.Principal;

import org.exolab.jms.net.uri.URI;


/**
 * Manages a pool of {@link ManagedConnection} instances, for a particular
 * {@link ManagedConnectionFactory}.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.5 $ $Date: 2005/06/04 14:51:06 $
 */
public interface ConnectionPool {

    /**
     * Creates a new connection.
     *
     * @param principal the security principal
     * @param info      the connection request info
     * @return a new connection
     * @throws ResourceException if a connection cannot be established
     */
    ManagedConnection createManagedConnection(Principal principal,
                                              ConnectionRequestInfo info)
            throws ResourceException;

    /**
     * Creates an acceptor for connections
     *
     * @param authenticator authenticates incoming connections
     * @param info          the connection request info
     * @return a new connection acceptor
     * @throws ResourceException if an acceptor cannot be created
     */
    ManagedConnectionAcceptor createManagedConnectionAcceptor(
            Authenticator authenticator, ConnectionRequestInfo info)
            throws ResourceException;

    /**
     * Returns a matched connection from the set of pooled connections.
     *
     * @param principal the security principal
     * @param info      the connection request info
     * @return the first acceptable match, or <code>null</code> if none is
     *         found
     * @throws ResourceException for any error
     */
    ManagedConnection matchManagedConnections(Principal principal,
                                              ConnectionRequestInfo info)
            throws ResourceException;

    /**
     * Returns a matched acceptor from the set of pooled connections.
     *
     * @param info the connection request info
     * @return the first acceptable match, or <code>null</code> if none is
     *         found
     * @throws ResourceException for any error
     */
    ManagedConnectionAcceptor matchManagedConnectionAcceptors(
            ConnectionRequestInfo info) throws ResourceException;

    /**
     * Returns a listener for handling accepted connections.
     *
     * @return a listener for handling accepted connections
     * @throws ResourceException for any error
     */
    ManagedConnectionAcceptorListener getManagedConnectionAcceptorListener()
            throws ResourceException;

    /**
     * Sets the listener for caller events.
     *
     * @param listener the listener
     */
    void setCallerListener(CallerListener listener);
 
    /**
     * Closes this connection pool, cleaning up any allocated resources.
     *
     * @throws ResourceException for any error
     */
    void close() throws ResourceException;

}
