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
 *    please contact jima@intalio.com.
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
 * $Id: TestAcceptorEventListener.java,v 1.4 2005/04/19 12:31:20 tanderson Exp $
 */
package org.exolab.jms.net.connector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * A test listener for {@link ManagedConnectionAcceptor} events.
 *
 * @version     $Revision: 1.4 $ $Date: 2005/04/19 12:31:20 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
public class TestAcceptorEventListener
        implements ManagedConnectionAcceptorListener {

    /**
     * The set of active connections.
     */
    private List _connections = Collections.synchronizedList(new ArrayList());

    /**
     * The set of connection errors.
     */
    private List _errors = new ArrayList();

    /**
     * The invocation handler to register on accepted connections.
     * If <code>null</code>, no handler will be registered.
     */
    private InvocationHandler _handler = null;


    /**
     * Construct a new <code>TestAcceptorEventListener</code>.
     */
    public TestAcceptorEventListener() {
        this(null);
    }

    /**
     * Construct a new <code>TestAcceptorEventListener</code>.
     *
     * @param handler the invocation handler to register on accepted
     * connections. If <code>null</code>, no handler will be registered
     */
    public TestAcceptorEventListener(InvocationHandler handler) {
        _handler = handler;
    }

    /**
     * Invoked when a new connection is accepted.
     *
     * @param acceptor the acceptor which received the connection
     * @param connection the accepted connection
     */
    public void accepted(ManagedConnectionAcceptor acceptor,
                         ManagedConnection connection) {
        if (_handler != null) {
            try {
                connection.setInvocationHandler(_handler);
                _connections.add(connection);
            } catch (ResourceException exception) {
                _errors.add(exception);
                try {
                    connection.destroy();
                } catch (ResourceException ignore) {
                    // no-op
                }
            }
        } else {
            _connections.add(connection);
        }
    }

    /**
     * Invoked when the acceptor receives an error.
     *
     * @param acceptor the acceptor generating the event
     * @param throwable the error
     */
    public void error(ManagedConnectionAcceptor acceptor,
                      Throwable throwable) {
        _errors.add(throwable);
        try {
            acceptor.close();
        } catch (ResourceException ignore) {
        }
    }

    /**
     * Returns the set of active connections.
     *
     * @return a list of <code>ManagedConnection</code> instances
     */
    public List getConnections() {
        return _connections;
    }

    /**
     * Returns the first active connection.
     *
     * @return the first active connection, or <code>null</code>
     * if no connection has been accepted
     */
    public ManagedConnection getConnection() {
        ManagedConnection result = null;
        synchronized (_connections) {
            if (!_connections.isEmpty()) {
                result = (ManagedConnection) _connections.get(0);
            }
        }
        return result;
    }

    /**
     * Returns any errors raised by the connection acceptor, or during
     * the handling of accepted connections.
     *
     * @return a list of <code>Throwable</code> instances
     */
    public List getErrors() {
        return _errors;
    }

    /**
     * Destroys all accepted connections.
     *
     * @throws ResourceException if a connecion can't be destroyed
     */
    public void destroy() throws ResourceException {
        Iterator iterator = _connections.iterator();
        while (iterator.hasNext()) {
            ManagedConnection connection = (ManagedConnection) iterator.next();
            connection.destroy();
        }
    }

}
