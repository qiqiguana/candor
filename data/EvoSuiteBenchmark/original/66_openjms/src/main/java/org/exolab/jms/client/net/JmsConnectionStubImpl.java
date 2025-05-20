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
 * Copyright 2000-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: JmsConnectionStubImpl.java,v 1.5 2005/11/18 03:29:41 tanderson Exp $
 */
package org.exolab.jms.client.net;

import java.rmi.RemoteException;
import javax.jms.InvalidClientIDException;
import javax.jms.JMSException;

import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.proxy.Proxy;
import org.exolab.jms.server.ServerConnection;
import org.exolab.jms.server.ServerSession;


/**
 * Wraps a {@link ServerConnection}.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @version $Revision: 1.5 $ $Date: 2005/11/18 03:29:41 $
 */
public class JmsConnectionStubImpl
        implements ServerConnection {

    /**
     * The connection to delegate calls to.
     */
    private ServerConnection _connection;

    /**
     * The ORB to export objects with.
     */
    private final ORB _orb;

    /**
     * The URI to export objects to.
     */
    private final String _uri;

    /**
     * The security principal. May be <code>null</code>
     */
    private final String _principal;

    /**
     * The security credentials. May be <code>null</code>.
     */
    private final String _credentials;


    /**
     * Construct a new <code>JmsConnectionStubImpl</code>.
     *
     * @param connection  the connection to delegate calls to
     * @param orb         the ORB to export objects with
     * @param uri         the URI to export objects on
     * @param principal   the security principal. May be <code>null</code>
     * @param credentials the security credentials. May be <code>null</code>
     */
    public JmsConnectionStubImpl(ServerConnection connection,
                                 ORB orb, String uri, String principal,
                                 String credentials) {
        if (connection == null) {
            throw new IllegalArgumentException("Argument 'connection' is null");
        }
        _connection = connection;
        _orb = orb;
        _uri = uri;
        _principal = principal;
        _credentials = credentials;
    }

    /**
     * Returns the connection identifier
     *
     * @return the connection identifier
     * @throws JMSException for any JMS error
     */
    public long getConnectionId() throws JMSException {
        return _connection.getConnectionId();
    }

    /**
     * Returns the client identifier
     *
     * @return the client identifier
     * @throws JMSException for any JMS error
     */
    public String getClientID() throws JMSException {
        return _connection.getClientID();
    }

    /**
     * Sets the client identifier for this connection.
     *
     * @param clientID the unique client identifier
     * @throws JMSException             if the JMS provider fails to set the
     *                                  client ID for this connection due to
     *                                  some internal error.
     * @throws InvalidClientIDException if the JMS client specifies an invalid
     *                                  or duplicate client ID.
     * @throws IllegalStateException    if the JMS client attempts to set a
     *                                  connection's client ID at the wrong time
     *                                  or when it has been administratively
     *                                  configured.
     */
    public void setClientID(String clientID) throws JMSException {
        _connection.setClientID(clientID);
    }

    /**
     * Create a new session.
     *
     * @param acknowledgeMode indicates whether the consumer or the client will
     *                        acknowledge any messages it receives; ignored if
     *                        the session is transacted. Legal values are
     *                        <code>Session.AUTO_ACKNOWLEDGE</code>,
     *                        <code>Session.CLIENT_ACKNOWLEDGE</code>, and
     *                        <code>Session.DUPS_OK_ACKNOWLEDGE</code>.
     * @param transacted      indicates whether the session is transacted
     * @return a newly created session
     * @throws JMSException for any JMS error
     */
    public ServerSession createSession(int acknowledgeMode, boolean transacted)
            throws JMSException {
        JmsSessionStubImpl result = null;
        try {
            ServerSession session = _connection.createSession(acknowledgeMode,
                                                              transacted);
            result = new JmsSessionStubImpl(session, _orb, _uri, _principal,
                                            _credentials);
        } catch (RemoteException exception) {
            // rethrow as a JMSException
            throw new JMSException("Failed to create session: " + exception);
        }

        return result;
    }

    /**
     * Closes the connection.
     *
     * @throws JMSException for any JMS error
     */
    public void close() throws JMSException {
        try {
            _connection.close();
        } finally {
            if (_connection instanceof Proxy) {
                ((Proxy) _connection).disposeProxy();
            }
            _connection = null;
        }
    }


}
