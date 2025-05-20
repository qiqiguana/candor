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
 * $Id: JmsConnection.java,v 1.4 2005/05/24 13:35:18 tanderson Exp $
 */
package org.exolab.jms.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.jms.Connection;
import javax.jms.ConnectionConsumer;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.IllegalStateException;
import javax.jms.InvalidDestinationException;
import javax.jms.InvalidSelectorException;
import javax.jms.JMSException;
import javax.jms.ServerSessionPool;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.InvalidClientIDException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.server.ServerConnection;


/**
 * Client side implementation of the <code>javax.jms.Connection</code>
 * interface.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2005/05/24 13:35:18 $
 */
class JmsConnection implements Connection {

    /**
     * The connection factory that constructed this.
     */
    private JmsConnectionFactory _factory;

    /**
     * The proxy for the remote connection implementation.
     */
    private ServerConnection _connection;

    /**
     * The connection identifier, assigned by the server.
     */
    private final long _connectionId;

    /**
     * This flag indicates whether or not the connection is closed.
     */
    private boolean _closed = false;

    /**
     * This flag indicates whether the connection is in the start or stopped
     * state.
     */
    private boolean _stopped = true;

    /**
     * This flag indicates whether the connection has been modified. If so,
     * subsequent attempts to invoke {@link #setClientID} will cause an
     * <code>IllegalStateException</code> being thrown
     */
    private boolean _modified = false;

    /**
     * The identity associated with the client, set via the {@link
     * JmsConnectionFactory} or {@link #setClientID}
     */
    private String _clientId;

    /**
     * Gates the setting of the clientId more than once.
     */
    private boolean _clientIdSet = false;

    /**
     * The exception listener for this connection.
     */
    private ExceptionListener _exceptionListener;

    /**
     * The active sessions managed by this connection.
     */
    private List _sessions = new ArrayList();

    /**
     * The connection data is immutable at this stage. This enables us to cache
     * a single copy in memory.
     */
    private static final JmsConnectionMetaData _metaData =
            new JmsConnectionMetaData();

    /**
     * The logger
     */
    private static final Log _log = LogFactory.getLog(JmsConnection.class);


    /**
     * Construct a new <code>JmsConnection</code>.
     * <p/>
     * This attempts to establish a connection to the JMS server
     *
     * @param factory  the connection factory responsible for creating this
     * @param clientID the pre-configured client identifier. May be
     *                 <code>null</code>
     * @param username the client username
     * @param password the client password
     * @throws JMSException if a connection cannot be established
     */
    protected JmsConnection(JmsConnectionFactory factory, String clientID,
                            String username, String password)
            throws JMSException {

        if (factory == null) {
            throw new IllegalArgumentException("Argument 'factory' is null");
        }
        _factory = factory;
        _clientId = clientID;

        _stopped = true;

        // use the factory object to retrieve the proxy that
        // will be used to get a JmsConnectionStubIfc instance
        // and cache its identity locally
        _connection = factory.getProxy().createConnection(_clientId, username,
                                                          password);
        _connectionId = _connection.getConnectionId();
    }

    /**
     * Returns the connection identifier.
     *
     * @return the connection identifier
     */
    public long getConnectionId() {
        return _connectionId;
    }

    /**
     * Gets the client identifier for this connection.
     *
     * @return the unique client identifier
     * @throws JMSException if the JMS provider fails to return the client ID
     *                      for this connection due to some internal error.
     */
    public String getClientID() throws JMSException {
        ensureOpen();
        setModified();

        return _clientId;
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
        ensureOpen();

        // check if the client id has already been set
        if (_clientIdSet) {
            throw new IllegalStateException(
                    "The client id has already been set");
        }

        if (_modified) {
            throw new IllegalStateException(
                    "The client identifier must be set before any other "
                    + "operation is performed");
        }

        _connection.setClientID(clientID);
        _clientId = clientID;
        _clientIdSet = true; // prevent client id from being set more than once.

    }


    /**
     * Returns the metadata for this connection.
     *
     * @return the connection metadata
     * @throws JMSException if the JMS provider fails to get the connection
     *                      metadata for this connection.
     */
    public ConnectionMetaData getMetaData() throws JMSException {
        ensureOpen();
        setModified();
        return _metaData;
    }

    /**
     * Returns the <code>ExceptionListener</code> for this connection.
     *
     * @return the <code>ExceptionListener</code> for this connection, or
     * <code>null</code> if none is associated with this connection.
     * @throws JMSException if the JMS provider fails to get the
     *                      <code>ExceptionListener</code> for this connection.
     */
    public ExceptionListener getExceptionListener() throws JMSException {
        ensureOpen();
        setModified();
        return _exceptionListener;
    }

    /**
     * Sets an exception listener for this connection.
     *
     * @param listener the exception listener
     * @throws JMSException if the JMS provider fails to set the exception
     *                      listener for this connection.
     */
    public void setExceptionListener(ExceptionListener listener)
            throws JMSException {
        ensureOpen();
        setModified();
        _exceptionListener = listener;
    }

    /**
     * Notify the exception listener of a JMSException. If the exception
     * listener is not set then ignore it.
     *
     * @param message message to deliver
     */
    public void notifyExceptionListener(JMSException message) {
        // check the error code
        if (message.getErrorCode() != null &&
                message.getErrorCode().equals(
                        JmsErrorCodes.CONNECTION_TO_SERVER_DROPPED)) {
            // the connection to the server has been dropped so we need to
            // release all local resources.
            try {
                close();
            } catch (JMSException exception) {
                _log.error(exception.getMessage(), exception);
            }
        }

        // finally notify registered exception listener
        if (_exceptionListener != null) {
            _exceptionListener.onException(message);
        }
    }

    /**
     * Starts (or restarts) a connection's delivery of incoming messages. A call
     * to <code>start</code> on a connection that has already been started is
     * ignored.
     *
     * @throws JMSException if the JMS provider fails to start message delivery
     *                      due to some internal error.
     */
    public synchronized void start() throws JMSException {
        ensureOpen();
        setModified();

        try {
            if (_stopped) {
                // start the associated sessions
                Iterator iterator = _sessions.iterator();
                while (iterator.hasNext()) {
                    JmsSession session = (JmsSession) iterator.next();
                    session.start();
                }
                // set the state of the connection to start
                _stopped = false;
            }
        } catch (JMSException exception) {
            // do we need to change _stopped to true if the any of the
            // sessions fail to start
            throw exception;
        }
    }

    /**
     * Temporarily stops a connection's delivery of incoming messages. Delivery
     * can be restarted using the connection's <code>start</code> method. When
     * the connection is stopped, delivery to all the connection's message
     * consumers is inhibited: synchronous receives block, and messages are not
     * delivered to message listeners.
     * <p/>
     * <P>This call blocks until receives and/or message listeners in progress
     * have completed.
     * <p/>
     * <P>Stopping a connection has no effect on its ability to send messages. A
     * call to <code>stop</code> on a connection that has already been stopped
     * is ignored.
     *
     * @throws JMSException if the JMS provider fails to stop message delivery
     *                      due to some internal error.
     */
    public synchronized void stop() throws JMSException {
        ensureOpen();
        setModified();

        if (!_stopped) {
            // stop the associated sessions
            synchronized (_sessions) {
                Iterator iterator = _sessions.iterator();
                while (iterator.hasNext()) {
                    JmsSession session = (JmsSession) iterator.next();
                    session.stop();
                }
            }
            // set the state of the connection to stopped
            _stopped = true;
        }
    }

    /**
     * Closes the connection.
     * <P>When this method is invoked, it should not return until message
     * processing has been shut down in an orderly fashion. This means that all
     * message listeners that may have been running have returned, and that all
     * pending receives have returned. A close terminates all pending message
     * receives on the connection's sessions' consumers. The receives may return
     * with a message or with null, depending on whether there was a message
     * available at the time of the close. If one or more of the connection's
     * sessions' message listeners is processing a message at the time when
     * connection <code>close</code> is invoked, all the facilities of the
     * connection and its sessions must remain available to those listeners
     * until they return control to the JMS provider.
     * <p/>
     * <P>Closing a connection causes any of its sessions' transactions in
     * progress to be rolled back. In the case where a session's work is
     * coordinated by an external transaction manager, a session's
     * <code>commit</code> and <code>rollback</code> methods are not used and
     * the result of a closed session's work is determined later by the
     * transaction manager.
     * <p/>
     * Closing a connection does NOT force an acknowledgment of
     * client-acknowledged sessions.
     * <p/>
     * <P>Invoking the <code>acknowledge</code> method of a received message
     * from a closed connection's session must throw an
     * <code>IllegalStateException</code>.
     * Closing a closed connection must NOT throw an exception.
     *
     * @throws JMSException if the JMS provider fails to close the connection
     *                      due to some internal error.
     */
    public synchronized void close() throws JMSException {
        if (!_closed) {
            // before we close we should stop the connection and any
            // associated sessions
            stop();

            // close the sessions
            JmsSession[] sessions = null;
            synchronized (_sessions) {
                sessions = (JmsSession[]) _sessions.toArray(new JmsSession[0]);
            }
            for (int i = 0; i < sessions.length; ++i) {
                sessions[i].close();
                // the session deregisters itself with the connection via
                // removeSession()
            }

            // notify the server, and null the proxy
            getServerConnection().close();
            _connection = null;

            // remove this from the list of connections managed by the
            // connection factory and then null the factory.
            _factory.removeConnection(this);
            _factory = null;

            // set the closed flag so calling it multiple times is
            // cool
            _closed = true;
        }
    }

    /**
     * Creates a <code>Session</code> object.
     *
     * @param transacted      indicates whether the session is transacted
     * @param acknowledgeMode indicates whether the consumer or the client will
     *                        acknowledge any messages it receives; ignored if
     *                        the session is transacted. Legal values are
     *                        <code>Session.AUTO_ACKNOWLEDGE</code>,
     *                        <code>Session.CLIENT_ACKNOWLEDGE</code>, and
     *                        <code>Session.DUPS_OK_ACKNOWLEDGE</code>.
     * @return a newly created session
     * @throws JMSException if the <code>Connection</code> object fails to
     *                      create a session due to some internal error or lack
     *                      of support for the specific transaction and
     *                      acknowledgement mode.
     * @see Session#AUTO_ACKNOWLEDGE
     * @see Session#CLIENT_ACKNOWLEDGE
     * @see Session#DUPS_OK_ACKNOWLEDGE
     */
    public Session createSession(boolean transacted, int acknowledgeMode)
            throws JMSException {
        ensureOpen();
        setModified();

        JmsSession session = new JmsSession(this, transacted, acknowledgeMode);

        // if the connection is started then also start the session
        if (!isStopped()) {
            session.start();
        }

        // add it to the list of managed sessions for this connection
        addSession(session);

        return session;
    }

    /**
     * Creates a connection consumer for this connection (optional operation).
     * This is an expert facility not used by regular JMS clients.
     *
     * @param destination     the destination to access
     * @param messageSelector only messages with properties matching the message
     *                        selector expression are delivered.  A value of
     *                        null or an empty string indicates that there is no
     *                        message selector for the message consumer.
     * @param sessionPool     the server session pool to associate with this
     *                        connection consumer
     * @param maxMessages     the maximum number of messages that can be
     *                        assigned to a server session at one time
     * @return the connection consumer
     * @throws JMSException    if the <code>Connection</code> object fails to
     *                         create a connection consumer due to some internal
     *                         error or invalid arguments for
     *                         <code>sessionPool</code> and
     *                         <code>messageSelector</code>.
     * @throws InvalidDestinationException if an invalid destination is
     *                                     specified.
     * @throws InvalidSelectorException    if the message selector is invalid.
     */
    public ConnectionConsumer createConnectionConsumer(
            Destination destination, String messageSelector,
            ServerSessionPool sessionPool, int maxMessages)
            throws JMSException {
        ensureOpen();
        setModified();
        return new JmsConnectionConsumer(this, destination, sessionPool,
                                         messageSelector, maxMessages);
    }

    /**
     * Create a durable connection consumer for this connection.
     *
     * @param topic            topic to access
     * @param subscriptionName durable subscription name
     * @param messageSelector  only messages with properties matching the
     *                         message selector expression are delivered.  A
     *                         value of null or an empty string indicates that
     *                         there is no message selector for the message
     *                         consumer.
     * @param sessionPool      the server session pool to associate with this
     *                         durable connection consumer
     * @param maxMessages      the maximum number of messages that can be
     *                         assigned to a server session at one time
     * @return the durable connection consumer
     * @throws JMSException    if the <code>Connection</code> object fails to
     *                         create a connection consumer due to some internal
     *                         error or invalid arguments for
     *                         <code>sessionPool</code> and
     *                         <code>messageSelector</code>.
     * @throws InvalidDestinationException if an invalid destination is
     *                                     specified.
     * @throws InvalidSelectorException    if the message selector is invalid.
     */
    public ConnectionConsumer createDurableConnectionConsumer(
            Topic topic, String subscriptionName, String messageSelector,
            ServerSessionPool sessionPool, int maxMessages)
            throws JMSException {
        ensureOpen();
        setModified();
        return new JmsConnectionConsumer(this, topic, subscriptionName,
                                         sessionPool, messageSelector,
                                         maxMessages);
    }

    /**
     * Returns the server connection.
     *
     * @return the server connection
     * @throws JMSException if the connection is <code>null</code>
     */
    protected ServerConnection getServerConnection() throws JMSException {
        if (_connection == null) {
            throw new JMSException("Connection closed");
        }

        return _connection;
    }

    /**
     * Add the specified session to the list of managed sessions.
     *
     * @param session session to register
     */
    protected void addSession(JmsSession session) {
        synchronized (_sessions) {
            _sessions.add(session);
        }
    }

    /**
     * Remove the specified session from the list of managed sessions. If it
     * doesn't exist then fail silently.
     *
     * @param session session to remove
     */
    protected void removeSession(JmsSession session) {
        synchronized (_sessions) {
            _sessions.remove(session);
        }
    }

    /**
     * Returns the running state of the connection.
     *
     * @return <code>true</code> if stopped
     */
    protected boolean isStopped() {
        return _stopped;
    }

    /**
     * Flags this connection as being modified. Subsequent attempts to invoke
     * {@link #setClientID} will result in an <code>IllegalStateException</code>
     * being thrown.
     */
    protected void setModified() {
        _modified = true;
    }

    /**
     * Delete the temporary destination and all the registered sessions
     * consumers waiting to receive messages from this destination will be
     * stopped.
     * <p/>
     * It will throw a JMSException if the specified destination is not
     * temporary or if the destination is null or if the destination is not
     * owned by this connection
     *
     * @param destination temporary destination to delete
     * @throws JMSException
     */
    protected synchronized void deleteTemporaryDestination(
            JmsDestination destination)
            throws JMSException {
        if ((destination != null) &&
                (destination instanceof JmsTemporaryDestination)) {
            JmsTemporaryDestination temp_dest =
                    (JmsTemporaryDestination) destination;

            // check to see that this destination was actually created by
            // this connection
            if (temp_dest.getOwningConnection() == this) {
                // this is currently a no-op but we probably need a way to
                // clean up on the server side
            } else {
                throw new JMSException(
                        "The temp destination cannot be used outside the scope "
                        + "of the connection creating it");
            }
        } else {
            throw new JMSException("The destination is not temporary");
        }
    }

    /**
     * Verifies that the connection is open.
     *
     * @throws IllegalStateException if the connection is closed
     */
    protected void ensureOpen() throws IllegalStateException {
        if (_closed) {
            throw new IllegalStateException(
                    "Cannot perform operation - session has been closed");
        }
    }

}

