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
 */
package org.exolab.jms.server;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.Vector;
import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.authentication.AuthenticationMgr;
import org.exolab.jms.authentication.User;
import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsQueue;
import org.exolab.jms.client.JmsTopic;
import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.Connector;
import org.exolab.jms.config.types.SchemeType;
import org.exolab.jms.messagemgr.ConsumerEndpoint;
import org.exolab.jms.messagemgr.ConsumerManager;
import org.exolab.jms.messagemgr.DestinationCache;
import org.exolab.jms.messagemgr.DestinationManager;
import org.exolab.jms.messagemgr.DurableConsumerEndpoint;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.service.ServiceException;
import org.exolab.jms.service.Services;


/**
 * A connection is created for every adminclient connecting to the JmsServer.
 *
 * @author <a href="mailto:knut@lerpold.no">Knut Lerpold</a>
 * @version $Revision: 1.7 $ $Date: 2005/12/23 12:17:45 $
 * @see org.exolab.jms.server.AdminConnectionManager
 */
public class AdminConnection {

    /**
     * The configuration.
     */
    private final Configuration _config;

    /**
     * The authentication manager.
     */
    private final AuthenticationMgr _authenticator;

    /**
     * The destination manager.
     */
    private final DestinationManager _destinations;

    /**
     * The consumer manager.
     */
    private final ConsumerManager _consumers;

    /**
     * The database service.
     */
    private final DatabaseService _database;

    /**
     * The services.
     */
    private final Services _services;

    /**
     * The logger
     */
    private static final Log _log = LogFactory.getLog(AdminConnection.class);


    /**
     * Construct a new <code>AdminConnection</code>.
     *
     * @param config        the configuration
     * @param authenticator the authentication manager
     * @param destinations  the destination manager
     * @param database      the database service
     * @param services      the services
     */
    protected AdminConnection(Configuration config, AuthenticationMgr authenticator,
                              DestinationManager destinations, ConsumerManager consumers,
                              DatabaseService database,
                              Services services) {
        if (config == null) {
            throw new IllegalArgumentException("Argument 'config' is null");
        }
        if (authenticator == null) {
            throw new IllegalArgumentException(
                    "Argument 'authenticator' is null");
        }
        if (destinations == null) {
            throw new IllegalArgumentException(
                    "Argument 'destinations' is null");
        }
        if (consumers == null) {
            throw new IllegalArgumentException("Argument 'consumers' is null");
        }
        if (database == null) {
            throw new IllegalArgumentException("Argument 'database' is null");
        }
        if (services == null) {
            throw new IllegalArgumentException("Argument 'services' is null");
        }
        _config = config;
        _authenticator = authenticator;
        _destinations = destinations;
        _consumers = consumers;
        _database = database;
        _services = services;
    }

    /**
     * Close the admin connection
     */
    public void close() {
    }

    /**
     * Return the number of messages for a durable consumer.
     *
     * @param topic name of the topic
     * @param name  consumer name
     * @return int                 number of unsent or unacked messages
     */
    public int getDurableConsumerMessageCount(String topic, String name) {
        int count = -1;
        try {
            // first see if the cache is loaded in memory
            JmsDestination dest = _destinations.getDestination(topic);
            ConsumerEndpoint endpoint = null;
            if ((dest != null)
                    && ((name != null)
                    || (name.length() > 0))) {

                endpoint = _consumers.getConsumerEndpoint(name);
                if ((endpoint != null)
                        && (endpoint.getDestination().equals(dest))) {
                    // retrieve the number of handles for the endpoint, which
                    // reflects the number of messages
                    count = endpoint.getMessageCount();
                } else {
                    // there is no cache with this name stored in memory. If
                    // this is an administered destination then read the count
                    //  directly from the database.
                    if (dest.getPersistent()) {
                        try {
                            _database.begin();
                            Connection connection = _database.getConnection();
                            count = _database.getAdapter().
                                    getDurableConsumerMessageCount(connection, topic,
                                                                   name);
                            _database.commit();
                        } catch (PersistenceException exception) {
                            _log.error(exception, exception);
                            try {
                                _database.rollback();
                            } catch (PersistenceException error) {
                                // no-op
                            }
                        }
                    }
                }
            }
        } catch (Exception exception) {
            _log.error("Failed to get message count for topic=" + topic,
                       exception);
        } finally {
        }

        return count;
    }

    /**
     * First use the destination manager to return the number of persistent and
     * non-persistent messages in a queue.
     *
     * @param queue name of the queue
     * @return int - the number of messages for that destination or -1 if the
     *         destination is invalid
     */
    public int getQueueMessageCount(String queue) {
        int count = -1;

        try {
            // first see if the cache is loaded in memory
            JmsDestination dest = _destinations.getDestination(queue);
            DestinationCache cache = null;
            if (dest != null) {
                _database.begin();
                cache = _destinations.getDestinationCache(dest);
                // retrieve the number of handles for the cache, which
                // reflects the number of messages
                count = cache.getMessageCount();
                _database.commit();
            }
        } catch (Exception exception) {
            _log.error("Failed to get message count for queue=" + queue,
                       exception);
            rollback();
        }
        return count;
    }

    /**
     * Add the specified durable consumer to the database.
     *
     * @param topic name of the destination
     * @param name  name of the consumer
     * @return boolean             true if successful
     */
    public boolean addDurableConsumer(String topic, String name) {
        boolean result = false;
        try {
            JmsTopic t = new JmsTopic(topic);
            t.setPersistent(true);
            _consumers.subscribe(t, name, null);
            result = true;
        } catch (JMSException exception) {
            _log.error("Failed to add durable consumer=" + name
                       + " for topic=" + topic, exception);
        }

        return result;
    }

    /**
     * Remove the consumer attached to the specified destination and with the
     * passed in name.
     *
     * @param name name of the consumer
     * @return boolean             true if successful
     */
    public boolean removeDurableConsumer(String name) {
        boolean result = false;
        try {
            _consumers.unsubscribe(name, null);
            result = true;
        } catch (JMSException exception) {
            _log.debug("Failed to remove durable consumer=" + name, exception);
        }

        return result;
    }

    /**
     * Check if the durable consumer exists.
     *
     * @param name name of the durable conusmer
     * @return boolean             true if it exists and false otherwise
     */
    public boolean durableConsumerExists(String name) {
        return (_consumers.getConsumerEndpoint(name) != null);
    }

    /**
     * Return the collection of durable consumer names for a particular topic
     * destination.
     *
     * @param topic the topic name
     * @return Vector              collection of strings
     */
    public Vector getDurableConsumers(String topic) {
        Enumeration iter = null;
        Vector result = new Vector();

        try {
            _database.begin();
            Connection connection = _database.getConnection();

            iter = _database.getAdapter().getDurableConsumers(connection,
                                                              topic);
            // copy the elements into the vector
            while (iter.hasMoreElements()) {
                result.addElement(iter.nextElement());
            }
            _database.commit();
        } catch (Exception exception) {
            _log.error("Failed on get durable consumers for topic=" + topic,
                       exception);
            rollback();
        }

        return result;
    }

    /**
     * De-Activate an active persistent consumer.
     *
     * @param name name of the consumer
     * @return boolean             true if successful
     */
    public boolean unregisterConsumer(String name) {
        boolean success = false;

        ConsumerEndpoint endpoint = _consumers.getConsumerEndpoint(name);
        if (endpoint != null) {
            _consumers.closeConsumer(endpoint);
        }
        success = true;

        return success;
    }

    /**
     * Check to see if the given consumer is currently connected to the
     * OpenJMSServer. This is only valid when in online mode.
     *
     * @param name The name of the onsumer.
     * @return boolean True if the consumer is connected.
     */
    public boolean isConnected(String name) {
        boolean result = false;
        ConsumerEndpoint endpoint = _consumers.getConsumerEndpoint(name);
        if (endpoint != null && endpoint instanceof DurableConsumerEndpoint) {
            result = ((DurableConsumerEndpoint) endpoint).isActive();
        }
        return result;
    }

    /**
     * Return a list of all registered destinations.
     *
     * @return Vector     collection of strings
     */
    public Vector getAllDestinations() {
        Enumeration iter = null;
        Vector result = new Vector();

        try {
            _database.begin();
            Connection connection = _database.getConnection();

            iter = _database.getAdapter().getAllDestinations(connection);
            // copy the elements into the vector
            while (iter.hasMoreElements()) {
                result.addElement(iter.nextElement());
            }
            _database.commit();
        } catch (Exception exception) {
            _log.error("Failed to get all destinations", exception);
            rollback();
        }

        return result;
    }

    /**
     * Add an administered destination with the specified name.
     *
     * @param name  destination name
     * @param queue whether it is queue or a topic
     * @return boolean             true if successful
     */
    public boolean addDestination(String name, Boolean queue) {

        boolean success = false;

        // create the appropriate destination object
        JmsDestination destination = (queue.booleanValue())
                ? (JmsDestination) new JmsQueue(name)
                : (JmsDestination) new JmsTopic(name);
        destination.setPersistent(true);

        // create the administered destination
        try {
            if (_destinations.getDestination(name) == null) {
                _destinations.createDestination(destination);
                success = true;
            }
        } catch (JMSException exception) {
            _log.error("Failed to add destination=" + name, exception);
        }

        return success;
    }

    /**
     * Destroy the specified destination and all associated messsages and
     * consumers. This is a very dangerous operation to execute while there are
     * clients online
     *
     * @param name destination to destroy
     * @return boolean             true if successful
     */
    public boolean removeDestination(String name) {

        boolean success = false;
        JmsDestination dest = _destinations.getDestination(name);

        // ensure that the destination actually translates to a valid
        // object.
        if (dest != null) {
            try {
                _destinations.removeDestination(dest);
                success = true;
            } catch (JMSException exception) {
                _log.error("Failed to remove destination=" + name, exception);
            }
        }

        return success;
    }

    /**
     * Check whether the specified destination exists
     *
     * @param name - the name of the destination to check
     * @return boolean - true if it does and false otherwise
     */
    public boolean destinationExists(String name) {

        JmsDestination dest = _destinations.getDestination(name);
        return (dest != null);
    }

    /**
     * Terminate the JMS Server. If it is running as a standalone application
     * then exit the application. It is running as an embedded application then
     * just terminate the thread
     */
    public void stopServer() {
        boolean isEmbedded = false;
        Connector[] connectors = _config.getConnectors().getConnector();
        for (int i = 0; i < connectors.length; ++i) {
            if (connectors[i].getScheme().equals(SchemeType.EMBEDDED)) {
                isEmbedded = true;
                break;
            }
        }

        final boolean exit = !isEmbedded;

        Runnable r = new Runnable() {

            public void run() {
                try {
                    // give the caller a chance to return before shutting
                    // down services
                    Thread.sleep(1000);
                } catch (InterruptedException ignore) {
                }
                _log.info("Stopping all services");
                try {
                    _services.stop();
                } catch (ServiceException exception) {
                    _log.error(exception, exception);
                }

                if (exit) {
                    _log.info("Server shutdown scheduled for 5 secs");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ignore) {
                    }
                    System.exit(0);
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    /**
     * Purge all processed messages from the database
     *
     * @return int         number of messages purged
     */
    public int purgeMessages() {
        return _database.getAdapter().purgeMessages();
    }

    /**
     * Add a user with the specified name
     *
     * @param username the users name
     * @param password the users password
     * @return <code>true</code> if the user is added otherwise
     *         <code>false</code>
     */
    public boolean addUser(String username, String password) {
        return _authenticator.addUser(new User(username, password));
    }

    /**
     * Change password for the specified user
     *
     * @param username the users name
     * @param password the users password
     * @return <code>true</code> if the password is changed otherwise
     *         <code>false</code>
     */
    public boolean changePassword(String username, String password) {
        return _authenticator.updateUser(new User(username, password));
    }

    /**
     * Remove the specified user
     *
     * @param username the users name
     * @return <code>true</code> if the user is removed otherwise
     *         <code>false</code>
     */
    public boolean removeUser(String username) {
        return _authenticator.removeUser(new User(username, null));
    }

    /**
     * Return a list of all registered users.
     *
     * @return Vector of users
     */
    public Vector getAllUsers() {
        Enumeration iter = null;
        Vector result = new Vector();

        try {
            _database.begin();
            Connection connection = _database.getConnection();

            iter = _database.getAdapter().getAllUsers(connection);
            // copy the elements into the vector
            while (iter.hasMoreElements()) {
                result.addElement(iter.nextElement());
            }
            _database.commit();
        } catch (Exception exception) {
            _log.error("Failed on get all users", exception);
            rollback();
        }

        return result;
    }

    /**
     * Rollback the current transaction, logging any error.
     */
    private void rollback() {
        try {
            _database.rollback();
        } catch (PersistenceException exception) {
            _log.error(exception, exception);
        }
    }

}
