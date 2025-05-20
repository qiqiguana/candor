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
 * $Id: ServerConnectionImpl.java,v 1.2 2005/08/30 14:24:26 tanderson Exp $
 */
package org.exolab.jms.server;

import java.util.HashSet;
import java.util.Iterator;
import javax.jms.InvalidClientIDException;
import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.messagemgr.ConsumerManager;
import org.exolab.jms.messagemgr.MessageManager;
import org.exolab.jms.messagemgr.ResourceManager;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.scheduler.Scheduler;

/**
 * Server implementation of the <code>javax.jms.Connection</code> interface.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/08/30 14:24:26 $
 * @see ServerConnectionManagerImpl
 */
public class ServerConnectionImpl implements ServerConnection {

    /**
     * The connection manager responsible for this.
     */
    private final ServerConnectionManagerImpl _manager;

    /**
     * The connection identifier.
     */
    private final long _connectionId;

    /**
     * The client identifier. May be <code>null</code>.
     */
    private String _clientId;

    /**
     * The sessions associated with the connection.
     */
    private HashSet _sessions = new HashSet();

    /**
     * Indicates if message delivery has been stopped for this connection.
     */
    private boolean _stopped = true;

    /**
     * The message manager.
     */
    private final MessageManager _messages;

    /**
     * The consumer manager.
     */
    private final ConsumerManager _consumers;

    /**
     * The resource manager.
     */
    private final ResourceManager _resources;

    /**
     * The database service.
     */
    private final DatabaseService _database;

    /**
     * The scheduler.
     */
    private final Scheduler _scheduler;

    /**
     * The logger.
     */
    private static final Log _log =
            LogFactory.getLog(ServerConnectionImpl.class);

    /**
     * Construct a new <code>ServerConnectionImpl</code>.
     *
     * @param manager      the connection manager
     * @param connectionId the identifier for this connection
     * @param clientId     the client identifier. May be <code>null</code>
     * @param messages     the message manager
     * @param consumers    the consumer manager
     * @param resources    the resource manager
     */
    protected ServerConnectionImpl(ServerConnectionManagerImpl manager,
                                  long connectionId, String clientId,
                                  MessageManager messages,
                                  ConsumerManager consumers,
                                  ResourceManager resources,
                                  DatabaseService database,
                                  Scheduler scheduler) {
        if (manager == null) {
            throw new IllegalArgumentException("Argument 'manager' is null");
        }
        if (messages == null) {
            throw new IllegalArgumentException("Argument 'messages' is null");
        }
        if (consumers == null) {
            throw new IllegalArgumentException("Argument 'consumers' is null");
        }
        if (resources == null) {
            throw new IllegalArgumentException("Argument 'resources' is null");
        }
        if (database == null) {
            throw new IllegalArgumentException("Argument 'database' is null");
        }
        if (scheduler == null) {
            throw new IllegalArgumentException("Argument 'scheduler' is null");
        }
        _manager = manager;
        _connectionId = connectionId;
        _clientId = clientId;
        _messages = messages;
        _consumers = consumers;
        _resources = resources;
        _database = database;
        _scheduler = scheduler;
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
     * Returns the client identifier.
     *
     * @return the client identifier
     */
    public String getClientID() {
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
        if (clientID == null) {
            throw new InvalidClientIDException("Invalid clientID: " + clientID);
        }
        _manager.addClientID(clientID);
        _clientId = clientID;
    }

    /**
     * Create a new session
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
    public synchronized ServerSession createSession(int acknowledgeMode,
                                                    boolean transacted)
            throws JMSException {
        ServerSessionImpl session = new ServerSessionImpl(
                this, acknowledgeMode, transacted, _messages, _consumers,
                _resources, _database, _scheduler);
        _sessions.add(session);
        if (!_stopped) {
            session.start();
        }

        return session;
    }

    /**
     * Closes the connection.
     */
    public synchronized void close() {
        Iterator iterator = _sessions.iterator();
        while (iterator.hasNext()) {
            ServerSessionImpl session = (ServerSessionImpl) iterator.next();
            try {
                session.close();
            } catch (JMSException exception) {
                _log.debug("Failed to close session", exception);
            }
        }
        _sessions.clear();
        _manager.closed(this);
    }

    /**
     * Notify closure of a session
     *
     * @param session the closed session
     */
    public synchronized void closed(ServerSessionImpl session) {
        _sessions.remove(session);
    }

}
