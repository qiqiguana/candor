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
 * Copyright 2000,2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: AbstractAdminConnection.java,v 1.1 2004/11/26 01:51:14 tanderson Exp $
 */
package org.exolab.jms.tools.admin;

import java.util.Enumeration;


/**
 * The abstract class all AbstractAdminConnection objects must inherit from.
 * Currently there are only two object types. OfflineConnection, for objects
 * that directly connect to and use the persistency mechanism, and
 * OnlineConnection objects that connect to an OpenJMSServer for all
 * their requests.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:14 $
 * @author      <a href="mailto:mourikis@exolab.org">Jim Mourikis</a>
 * @see         OfflineConnection
 * @see	        OnlineConnection
 */
public abstract class AbstractAdminConnection {

    protected static AbstractAdminConnection _instance = null;

    /**
     * Returns the one and only instance of the connection object.
     *
     * @return AdminConnection The one and only instance.
     */
    public static AbstractAdminConnection instance() {
        return _instance;
    }

    /**
     * Return the number of outstanding messages for a particular destination.
     *
     * @param       topic       name of the topic
     * @param       name        durable consumer name
     * @return      int         message count
     */
    public abstract int getDurableConsumerMessageCount(String topic,
                                                       String name);

    /**
     * Return the number of outstanding messages for a particular queue.
     *
     * @param       queue       the queue name
     * @return      int         message count
     */
    public abstract int getQueueMessageCount(String queue);

    /**
     * Add a durable consumer for the specified name the passed in name
     *
     * @param       topic           name of the destination
     * @param       name            name of the consumer
     * @return      boolean         true if successful
     */
    public abstract boolean addDurableConsumer(String topic, String name);

    /**
     * Remove the consumer with the specified name
     *
     * @param       name            name of the consumer
     * @return      boolean         true if successful
     */
    public abstract boolean removeDurableConsumer(String name);

    /**
     * Return the collection of durable consumer names for a particular
     * topic destination.
     *
     * @param       topic           the topic name
     * @return      Vector          collection of strings
     */
    public abstract Enumeration getDurableConsumers(String destination);

    /**
     * Check if the durable consumer exists.
     *
     * @param       name            name of the durable conusmer
     * @return      boolean         true if it exists and false otherwise
     */
    public abstract boolean durableConsumerExists(String name);

    /**
     * De-Activate an active persistent consumer.
     *
     * @param       name            name of the consumer
     * @return      boolean         true if successful
     */
    public abstract boolean unregisterConsumer(String name);

    /**
     * Check to see if the given consumer is currently connected to the
     * OpenJMSServer. This is only valid when in online mode.
     *
     * @param name The name of the onsumer.
     * @return boolean True if the consumer is connected.
     *
     */
    public abstract boolean isConnected(String name);

    /**
     * Return a list of all registered destinations.
     *
     * @return      Enumeration     collection of JmsDestination objects
     */
    public abstract Enumeration getAllDestinations();

    /**
     * Add a specific destination with the specified name
     *
     * @param       name        destination name
     * @param       queue       whether it is queue or a topic
     * @return      boolean     true if successful
     */
    public abstract boolean addDestination(String destination,
                                           boolean isQueue);

    /**
     * Destroy the specified destination and all associated messsages and
     * consumers. This is a very dangerous operation to execute while there
     * are clients online
     *
     * @param       destination     destination to destroy
     */
    public abstract boolean removeDestination(String name);

    /**
     * Terminate the JMS Server. If it is running as a standalone application
     * then exit the application. It is running as an embedded application then
     * just terminate the thread
     */
    public abstract void stopServer();

    /**
     * Purge all processed messages from the database.
     *
     * @return      int         the number of messages purged.
     */
    public abstract int purgeMessages();

    /**
     * Close the connection.
     */
    public abstract void close();

    /**
     * Adds a new User to the DB.
     *
     * @param username    the users name
     * @param password    the users password
     * @return <code>true</code> if the user is added
     * otherwise <code>false</code>
     */
    public abstract boolean addUser(String username, String password);

    /**
     * Change the password for this user
     *
     * @param username    the users name
     * @param password    the users password
     * @return <code>true</code> if the password is changed
     * otherwise <code>false</code>
     */
    public abstract boolean changePassword(String username, String password);

    /**
     * Remove a user from the DB.
     *
     * @param username    the users name
     * @return <code>true</code> if the user is removed
     * otherwise <code>false</code>
     */
    public abstract boolean removeUser(String username);

    /**
     * List all users in the DB
     *
     * @return Enumeration of users
     */
    public abstract Enumeration getAllUsers();
}
