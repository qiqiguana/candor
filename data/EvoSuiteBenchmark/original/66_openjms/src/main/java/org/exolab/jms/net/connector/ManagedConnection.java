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
 * $Id: ManagedConnection.java,v 1.4 2006/12/16 12:37:17 tanderson Exp $
 */
package org.exolab.jms.net.connector;

import org.exolab.jms.net.uri.URI;

import java.security.Principal;


/**
 * A <code>ManagedConnection</code> represents a physical connection. <br/> It
 * is responsible for managing multiple {@link Connection} instances, which
 * perform method invocations over the physical connection.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2006/12/16 12:37:17 $
 * @see Connection
 */
public interface ManagedConnection {

    /**
     * Registers a handler for handling invocations on objects exported via this
     * connection. Once a handler is registered, it cannot be de-registered.
     *
     * @param handler the invocation handler
     * @throws IllegalStateException if a handler is already registered
     * @throws ResourceException     for any error
     */
    void setInvocationHandler(InvocationHandler handler)
            throws ResourceException;

    /**
     * Registers a connection event listener.
     *
     * @param listener the connection event listener
     * @throws ResourceException for any error
     */
    void setConnectionEventListener(ManagedConnectionListener listener)
            throws ResourceException;

    /**
     * Creates a new connection handle for the underlying physical connection.
     *
     * @return a new connection handle
     * @throws IllegalStateException if an <code>InvocationHandler</code> hasn't
     *                               been registered
     * @throws ResourceException     for any error
     */
    Connection getConnection() throws ResourceException;

    /**
     * Ping the connection. The connection event listener will be notified
     * if the ping succeeds.
     * NOTE: the notification may occur prior to this call returning.
     *
     * @throws IllegalStateException if no invocation handler has been
     *                               registered, or if the connection has been
     *                               destroyed
     * @throws ResourceException     for any error
     */
    void ping() throws ResourceException;

    /**
     * Returns the remote address to which this is connected.
     *
     * @return the remote address to which this is connected
     * @throws ResourceException for any error
     */
    URI getRemoteURI() throws ResourceException;

    /**
     * Returns the local address that this connection is bound to.
     *
     * @return the local address that this connection is bound to
     * @throws ResourceException for any error
     */
    URI getLocalURI() throws ResourceException;

    /**
     * Returns the principal associated with this connection.
     *
     * @return the principal associated with this connection,
     *         or <code>null<code> if none is set
     * @throws ResourceException for any error
     */
    Principal getPrincipal() throws ResourceException;

    /**
     * Destroys the physical connection.
     *
     * @throws ResourceException for any error
     */
    void destroy() throws ResourceException;

}
