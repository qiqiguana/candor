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
 * $Id: PersistenceAdapter.java,v 1.2 2005/03/18 04:05:52 tanderson Exp $
 */
package org.exolab.jms.persistence;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import org.exolab.jms.authentication.User;
import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.messagemgr.MessageHandle;


/**
 * This adapter is a wrapper class around the persistency mechanism.
 * It isolates the client from the working specifics of the database, by
 * providing a simple straight forward interface. Future changes to
 * the database will only require changes to the adapter.
 *
 * @version     $Revision: 1.2 $ $Date: 2005/03/18 04:05:52 $
 * @author      <a href="mailto:mourikis@exolab.org">Jim Mourikis</a>
 * @see			org.exolab.jms.persistence.RDBMSAdapter
 */
public abstract class PersistenceAdapter {

    /**
     * Close the database if open.
     *
     */
    public abstract void close();

    /**
     * Check to see if the root is created. If its not then create it
     * and initialise it to 0.
     * Return the value of this root id.
     *
     * @return long The id of the last batch.
     * @throws PersistenceException
     */
    public abstract long getLastId(Connection connection)
        throws PersistenceException;

    /**
     * Update the given id.
     *
     * @param connection - the connection to use
     * @param id The id to set in the database.
     * @throws PersistenceException
     */
    public abstract void updateIds(Connection connection, long id)
        throws PersistenceException;

    /**
     * Add a new message to the database.
     *
     * @param connection  the connection to use
     * @param message the new message to add
     * @throws PersistenceException
     */
    public abstract void addMessage(Connection connection,
                                    MessageImpl message)
        throws PersistenceException;

    /**
     * Update this message in the database
     *
     * @param connection the connection to use
     * @param message the message to update
     * @throws PersistenceException
     */
    public abstract void updateMessage(Connection connection,
                                       MessageImpl message)
        throws PersistenceException;

    /**
     * Remove the message with the specified identity from the database
     *
     * @param connection - the connection to use
     * @param id the identity of the message to remove
     * @throws PersistenceException
     */
    public abstract void removeMessage(Connection connection,
                                       String id)
        throws PersistenceException;

    /**
     * Remove all expired messages and associated references from the
     * database. It uses the current time to determine messages that
     * have exipred.
     *
     * @param connection - the connection to use
     * @throws PersistenceException
     */
    public abstract void removeExpiredMessages(Connection connection)
        throws PersistenceException;

    /**
     * Remove all expired messages handles associated with this durable
     * consumer.
     *
     * @param connection - the connection to use
     * @param consumer - the durable consumer name
     * @throws PersistenceException
     */
    public abstract void removeExpiredMessageHandles(Connection connection,
                                                     String consumer)
        throws PersistenceException;

    /**
     * Retrieve a list of unexpired {@link MessageHandle} objects,
     * for the specified destination.
     *
     * @param connection - the connection to use
     * @param destination - the destination in question
     * @return Vector - collection of unexpired message handles
     * @throws PersistenceException
     */
    public abstract Vector getNonExpiredMessages(Connection connection,
                                                 JmsDestination destination)
        throws PersistenceException;

    /**
     * Get a message from the persistence store.
     *
     * @param connection - the connection to use
     * @param id the id of the message to search for
     * @return MessageImpl	 The message if found otherwise null
     * @throws PersistenceException
     */
    public abstract MessageImpl getMessage(Connection connection,
                                           String id)
        throws PersistenceException;

    /**
     * Get at least the next message given the specified persistent
     * handle. The handle encodes all the information, including destination
     * and timestamp, required to fetch that and successive messages. This
     * will fault in more than one message for performance
     *
     * @param connection - the connection to use
     * @param handle - the persistent handle to resolve
     * @return Vector - a vector of MessageImpl
     * @throws PersistenceException
     */
    public abstract Vector getMessages(Connection connection,
                                       MessageHandle handle)
        throws PersistenceException;

    /**
     * Return a list of unprocessed messages. These are messages that have
     * been stored in the database but not processed.
     *
     * @param connection - the connection to use
     * @return Vector - a collection of un processed messages
     * @throws PersistenceException
     */
    public abstract Vector getUnprocessedMessages(Connection connection)
        throws PersistenceException;

    /**
     * Add the specified persistent message handle.
     *
     * @param connection - the connection to use
     * @param handle - the persistent handle to add
     * @throws PersistenceException
     */
    public abstract void addMessageHandle(Connection connection,
                                          MessageHandle handle)
        throws PersistenceException;

    /**
     * Update the specified persistent message handle.
     *
     * @param connection - the connection to use
     * @param handle - the persistent handle to update
     * @throws PersistenceException
     */
    public abstract void updateMessageHandle(Connection connection,
                                             MessageHandle handle)
        throws PersistenceException;

    /**
     * Remove the specified persistent message handle.
     *
     * @param connection - the connection to use
     * @param handle - the persistent handle to remove
     * @throws PersistenceException
     * @throws PersistenceException
     */
    public abstract void removeMessageHandle(Connection connection,
                                             MessageHandle handle)
        throws PersistenceException;

    /**
     * Get all the persistent message handles for the specified destination
     * and consumer name.
     * <p>
     * The returned messages reference unacked or unsent messages
     * <p>
     * NEED A STRATEGY WHEN WE HAVE LOTS OF MESSAGE HANDLES
     *
     * @param connection - the connection to use
     * @param destination - the destination to reference
     * @param name - the consumer name
     * @throws PersistenceException
     */
    public abstract Vector getMessageHandles(Connection connection,
                                             JmsDestination destination, String name)
        throws PersistenceException;

    /**
     * Add the specified durable consumer
     *
     * @param connection - the connection to use
     * @param topic - the name of the topic
     * @param consumer the name of the consumer
     * @throws PersistenceException
     */
    public abstract void addDurableConsumer(Connection connection,
                                            String topic, String consumer)
        throws PersistenceException;

    /**
     * Remove the durable consumer for the specified topic.
     *
     * @param connection - the connection to use
     * @param consumer - the consumer name
     * @throws PersistenceException
     */
    public abstract void removeDurableConsumer(Connection connection,
                                               String consumer)
        throws PersistenceException;

    /**
     * Check if the durable consumer exists
     *
     * @param connection - the connection to use
     * @param name - durable consumer name
     * @return boolean - true if it exists and false otherwise
     * @throws PersistenceException
     */
    public abstract boolean durableConsumerExists(Connection connection,
                                                  String name)
        throws PersistenceException;

    /**
     * Get an enumerated list of all durable consumers for the
     * specified JmsTopic destination
     *
     * @param connection - the connection to use
     * @param topic - the topic to query
     * @return Vector - list of durable subscriber names
     * @throws PersistenceException
     */
    public abstract Enumeration getDurableConsumers(Connection connection,
                                                    String topic)
        throws PersistenceException;

    /**
     * Return a dictionary of all registered durable consumers. The
     * dictionary is keyed on consumer name and maps to the underlying
     * destination name. The destination name maybe a wildcard
     *
     * @param connection - the connection to use
     * @return HashMap key=consumer name and value is destination
     * @throws PersistenceException
     */
    public abstract HashMap getAllDurableConsumers(Connection connection)
        throws PersistenceException;

    /**
     * Add a new destination to the database.
     *
     * @param connection - the connection to use
     * @param name - the destination name
     * @param queue - true if it pertains to a queue
     * @throws PersistenceException
     */
    public abstract void addDestination(Connection connection,
                                        String name, boolean queue)
        throws PersistenceException;

    /**
     * Remove the destination with the specified name and all registered
     * consumers from the database.
     * Consumer registrations.
     *
     * @param connection - the connection to use
     * @param destination - the name of the destination
     * @throws PersistenceException
     */
    public abstract void removeDestination(Connection connection,
                                           String destination)
        throws PersistenceException;

    /**
     * Determine if a particular destination name exists
     *
     * @param connection - the connection to use
     * @param name - the name to query
     * @return boolean - true if it exists; false otherwise
     * @throws PersistenceException
     */
    public abstract boolean checkDestination(Connection connection,
                                             String name)
        throws PersistenceException;

    /**
     * Get a list of all destinations stored in the database
     *
     * @param connection - the connection to use
     * @return Enumeration - the list of destinations
     * @throws PersistenceException
     */
    public abstract Enumeration getAllDestinations(Connection connection)
        throws PersistenceException;

    /**
     * Get the number of unsent messages for a the specified queue
     *
     * @param connection - the connection to use
     * @param name - the name of the queue
     * @return int - the number of unsent or unacked messages
     * @throws PersistenceException
     */
    public abstract int getQueueMessageCount(Connection connection,
                                             String name)
        throws PersistenceException;

    /**
     * Return the number of unsent message for the specified durable
     * consumer.
     *
     * @param connection - the connection to use
     * @param destination - the destination name
     * @param name - the name of the durable subscriber
     * @return int - the nmber of unsent or unacked messages
     * @throws PersistenceException
     */
    public abstract int getDurableConsumerMessageCount(Connection connection,
                                                       String destination, String name)
        throws PersistenceException;

    /**
     * Purge all processed messages from the database.
     *
     * @return int - the number of messages purged
     */
    public abstract int purgeMessages();

    /**
     * Return a connection to this persistent data store.
     *
     * @return Connection - a connection to the persistent store or null
     * @throws PersistenceException - if it cannot retrieve a connection
     */
    public abstract Connection getConnection() throws PersistenceException;


    public abstract Enumeration getAllUsers(Connection connection)
        throws PersistenceException;

    public abstract void addUser(Connection connection, User user)
        throws PersistenceException;

    public abstract void removeUser(Connection connection,
                                    User user)
        throws PersistenceException;

    public abstract void updateUser(Connection connection,
                                    User user)
        throws PersistenceException;

    public abstract User getUser(Connection connection,
                                 User user)
        throws PersistenceException;

}




