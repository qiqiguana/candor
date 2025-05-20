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
 * $Id: ServerConnectionManagerImpl.java,v 1.3 2005/09/05 13:38:04 tanderson Exp $
 */
package org.exolab.jms.server;

import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import javax.jms.InvalidClientIDException;
import javax.jms.JMSException;
import javax.jms.JMSSecurityException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.common.security.BasicPrincipal;
import org.exolab.jms.messagemgr.ConsumerManager;
import org.exolab.jms.messagemgr.MessageManager;
import org.exolab.jms.messagemgr.ResourceManager;
import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.scheduler.Scheduler;


/**
 * The <code>ServerConnectionManagerImpl</code> is responsible for managing all
 * connections to the server.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/09/05 13:38:04 $
 * @see ServerConnectionImpl
 */
public class ServerConnectionManagerImpl implements ServerConnectionManager {

    /**
     * The authenticator.
     */
    private final Authenticator _authenticator;

    /**
     * The message manager.
     */
    private final MessageManager _messages;

    /**
     * The consumer manager.
     */
    private ConsumerManager _consumers;

    /**
     * The resource manager.
     */
    private ResourceManager _resources;

    /**
     * The database service.
     */
    private final DatabaseService _database;

    /**
     * The scheduler.
     */
    private final Scheduler _scheduler;

    /**
     * The set of active connections.
     */
    private HashMap _connections = new HashMap();

    /**
     * The set of client identifiers.
     */
    private HashSet _clientIDs = new HashSet();

    /**
     * Seed for connection identifiers.
     */
    private long _seed = 0;

    /**
     * The logger.
     */
    private final Log _log
            = LogFactory.getLog(ServerConnectionManagerImpl.class);


    /**
     * Construct a new <code>ServerConnectionManagerImpl</code>.
     *
     * @param authenticator the authenticator to verify users with
     */
    public ServerConnectionManagerImpl(Authenticator authenticator,
                                      MessageManager messages,
                                      DatabaseService database,
                                      Scheduler scheduler) {
        if (authenticator == null) {
            throw new IllegalArgumentException(
                    "Argument 'authenticator' is null");
        }
        if (messages == null) {
            throw new IllegalArgumentException("Argument 'messages' is null");
        }
        if (database == null) {
            throw new IllegalArgumentException("Argument 'database' is null");
        }
        if (scheduler == null) {
            throw new IllegalArgumentException("Argument 'scheduler' is null");
        }
        _authenticator = authenticator;
        _messages = messages;
        _database = database;
        _scheduler = scheduler;
    }

    /**
     * Sets the consumer manager.
     *
     * @param consumers the consumer manager
     */
    public void setConsumerManager(ConsumerManager consumers) {
        _consumers = consumers;
    }

    /**
     * Sets the resource manager.
     *
     * @param resources the resource manager.
     */
    public void setResourceManager(ResourceManager resources) {
        _resources = resources;
    }

    /**
     * Creates a connection with the specified user identity.
     * <p/>
     * The connection is created in stopped mode. No messages will be delivered
     * until the <code>Connection.start</code> method is explicitly called.
     * <p/>
     * If <code>clientID</code> is specified, it indicates the pre-configured
     * client identifier associated with the client <code>ConnectionFactory</code>
     * object.
     *
     * @param clientID the pre-configured client identifier. May be
     *                 <code>null</code> <code>null</code>.
     * @param userName the caller's user name
     * @param password the caller's password
     * @return a newly created connection
     * @throws InvalidClientIDException if the JMS client specifies an invalid
     *                                  or duplicate client ID.
     * @throws JMSException             if the JMS provider fails to create the
     *                                  connection due to some internal error.
     * @throws JMSSecurityException     if client authentication fails due to an
     *                                  invalid user name or password.
     */
    public ServerConnection createConnection(String clientID, String userName,
                                             String password)
            throws JMSException {
        Principal principal = null;
        if (userName != null) {
            principal = new BasicPrincipal(userName, password);
        }
        try {
            if (!_authenticator.authenticate(principal)) {
                throw new JMSSecurityException("Failed to authenticate user: " +
                                               userName);
            }
        } catch (ResourceException exception) {
            _log.error(exception, exception);
            throw new JMSSecurityException("Failed to authenticate user "
                                           + userName);
        }

        ServerConnectionImpl result = null;
        synchronized (_connections) {
            addClientID(clientID);
            long connectionId = ++_seed;
            result = new ServerConnectionImpl(
                    this, connectionId, clientID, _messages, _consumers,
                    _resources, _database, _scheduler);
            _connections.put(new Long(connectionId), result);
        }

        return result;
    }

    /**
     * Returns the connection associated with a particular connection
     * identifier.
     *
     * @param connectionId the connection identifier
     * @return the connection associated with <code>connectionId</code>, or
     *         <code>null</code> if none exists
     */
    public ServerConnectionImpl getConnection(long connectionId) {
        ServerConnectionImpl result = null;
        synchronized (_connections) {
            Long key = new Long(connectionId);
            result = (ServerConnectionImpl) _connections.get(key);
        }
        return result;
    }

    /**
     * Notify closure of a connection.
     *
     * @param connection the connection that has been closed
     */
    public void closed(ServerConnectionImpl connection) {
        synchronized (_connections) {
            Long key = new Long(connection.getConnectionId());
            _connections.remove(key);
            _clientIDs.remove(connection.getClientID());
        }
    }

    /**
     * Register a client identifer.
     *
     * @param clientID the client identifier. If <code>null</code, it is
     *                 ignored.
     * @throws InvalidClientIDException if the identifier is a duplicate.
     */
    public void addClientID(String clientID) throws InvalidClientIDException {
        synchronized (_connections) {
            if (clientID != null) {
                if (!_clientIDs.add(clientID)) {
                    throw new InvalidClientIDException(
                            "Duplicate clientID: " + clientID);
                }
            }
        }
    }

}
