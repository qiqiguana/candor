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
 * $Id: RemoteServerConnection.java,v 1.3 2005/08/30 05:51:03 tanderson Exp $
 */
package org.exolab.jms.server.net;

import java.rmi.RemoteException;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.jms.InvalidClientIDException;
import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.net.connector.Caller;
import org.exolab.jms.net.connector.CallerListener;
import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.orb.UnicastObject;
import org.exolab.jms.server.ServerConnection;
import org.exolab.jms.server.ServerSession;


/**
 * Implementation of the {@link ServerConnection{ interface which wraps an
 * {@link ServerConnection} to make it remotable.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/08/30 05:51:03 $
 * @see ServerConnectionImpl
 */
public class RemoteServerConnection
        extends UnicastObject
        implements ServerConnection, CallerListener {

    /**
     * The connection to delegate calls to.
     */
    private ServerConnection _connection;

    /**
     * The URI of the remote caller.
     */
    private final String _uri;

    /**
     * The set of {@link RemoteServerSession} instances created by this.
     */
    private List _sessions = Collections.synchronizedList(new ArrayList());

    /**
     * The logger.
     */
    private static final Log _log
            = LogFactory.getLog(RemoteServerConnection.class);


    /**
     * Construct a new <code>RemoteServerConnection</code>.
     *
     * @param connection the connection to delegate calls to
     * @param orb        the ORB to export this with
     * @throws RemoteException if this can't be exported
     */
    public RemoteServerConnection(ServerConnection connection, ORB orb)
            throws RemoteException {
        super(orb, null, true);
        if (connection == null) {
            throw new IllegalArgumentException("Argument 'connection' is null");
        }
        Caller caller = orb.getCaller();
        if (caller == null) {
            throw new ExportException("Can't determine remote caller");
        }
        _uri = caller.getRemoteURI().toString();
        orb.addCallerListener(_uri, this);
        _connection = connection;
    }

    /**
     * Returns the connection identifier.
     *
     * @return the connection identifier
     * @throws JMSException for any JMS error
     */
    public long getConnectionId() throws JMSException {
        return _connection.getConnectionId();
    }

    /**
     * Returns the client identifier.
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
        ServerSession session = _connection.createSession(acknowledgeMode,
                                                          transacted);
        RemoteServerSession remote = null;
        try {
            remote = new RemoteServerSession(getORB(), this, session);
            _sessions.add(remote);
        } catch (RemoteException exception) {
            throw new JMSException(exception.getMessage());
        }
        return (ServerSession) remote.getProxy();
    }

    /**
     * Closes the connection.
     *
     * @throws JMSException for any JMS error
     */
    public void close() throws JMSException {
        JMSException rethrow = null;
        // Need to catch exceptions and propagate last, to ensure all objects
        // are cleaned up and unexported.

        // Clean up sessions.
        RemoteServerSession[] sessions = (RemoteServerSession[])
                _sessions.toArray(new RemoteServerSession[0]);
        if (sessions.length != 0) {
            // There will only be sessions present if the client has disconnected
            // without closing its connection.
            _log.debug("Cleaning up active sessions");
            for (int i = 0; i < sessions.length; ++i) {
                try {
                    sessions[i].close();
                } catch (JMSException exception) {
                    rethrow = exception;
                    if (_log.isDebugEnabled()) {
                        _log.debug("Exception while cleaning up session",
                                   exception);
                    }
                }
            }
        }
        try {
            _connection.close();
        } finally {
            try {
                getORB().removeCallerListener(_uri, this);
                unexportObject();
            } catch (RemoteException exception) {
                throw new JMSException(exception.getMessage());
            }
        }
        if (rethrow != null) {
            throw rethrow;
        }
    }

    /**
     * Notifies that a caller has been disconnected. This implementation invokes
     * {@link #close}.
     *
     * @param caller the caller that was disconnected
     */
    public void disconnected(Caller caller) {
        if (_log.isDebugEnabled()) {
            _log.debug("Detected disconnection of caller="
                       + caller.getRemoteURI() + ". Cleaning up resources");
        }
        try {
            close();
        } catch (JMSException exception) {
            _log.debug("Failed to clean up resources of caller="
                       + caller.getRemoteURI(), exception);
        }

    }

    /**
     * Notify closure of a session.
     *
     * @param session the closed session
     */
    public void closed(RemoteServerSession session) {
        _sessions.remove(session);
    }
}
