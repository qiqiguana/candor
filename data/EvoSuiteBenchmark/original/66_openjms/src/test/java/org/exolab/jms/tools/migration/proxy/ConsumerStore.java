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
 * Copyright 2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 */
package org.exolab.jms.tools.migration.proxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.jms.JMSException;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsQueue;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.persistence.SQLHelper;
import org.exolab.jms.tools.migration.Store;
import org.exolab.jms.tools.migration.StoreIterator;


/**
 * Provides persistency for {@link Consumer} instances.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/10/20 14:07:03 $
 */
public class ConsumerStore implements Store, DBConstants {

    /**
     * The destination store.
     */
    private final DestinationStore _destinations;

    /**
     * The database connection.
     */
    private final Connection _connection;

    /**
     * The seed used to generate identifiers for consumers.
     */
    private long _seed = 0;


    /**
     * Construct a new <code>ConsumerStore</code>.
     *
     * @param destinations the destination store
     * @param connection the database connection
     */
    public ConsumerStore(DestinationStore destinations, Connection connection) {
        _destinations = destinations;
        _connection = connection;
    }

    /**
     * Export the consumers.
     *
     * @return an iterator over the collection
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     */
    public StoreIterator exportCollection() throws JMSException,
            PersistenceException {
        List consumerIds = getConsumerIds();
        return new ConsumerIterator(consumerIds);
    }

    /**
     * Import consumers into the store.
     *
     * @param iterator an iterator over the collection
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     */
    public void importCollection(StoreIterator iterator) throws JMSException,
            PersistenceException {
        while (iterator.hasNext()) {
            Consumer consumer = (Consumer) iterator.next();
            add(consumer);
        }
    }

    /**
     * Returns the number of elements in the collection.
     *
     * @return the number of elements in the collection
     * @throws PersistenceException for any persistence error
     */
    public int size() throws PersistenceException {
        return getConsumerIds().size();
    }

    /**
     * Add a new consumer.
     *
     * @param consumer the consumer to add
     * @throws PersistenceException for any persistence error
     */
    public synchronized void add(Consumer consumer)
            throws PersistenceException {
        PreparedStatement insert = null;
        try {
            long consumerId = ++_seed;

            insert = _connection.prepareStatement(
                    "insert into " + CONSUMER_TABLE + " values (?, ?, ?, ?)");

            insert.setLong(1, consumerId);
            insert.setString(2, consumer.getName());
            insert.setString(3, consumer.getClientID());
            insert.setBoolean(4, consumer.isQueueConsumer());
            insert.executeUpdate();

            addSubscriptions(consumerId, consumer);
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to add consumer",
                                           exception);
        } finally {
            SQLHelper.close(insert);
        }
    }

    /**
     * Returns a consumer for a given identifier.
     *
     * @param consumerId the identity of the consumer
     * @return the consumer corresponding to <code>consumerId</code> or
     *         <code>null</code> if no such consumer exists
     * @throws PersistenceException for any persistence error
     */
    public Consumer get(long consumerId) throws PersistenceException {
        Consumer result = null;
        PreparedStatement select = null;
        ResultSet set = null;
        try {
            select = _connection.prepareStatement(
                    "select name, client_id, queue_consumer from "
                    + CONSUMER_TABLE + " where consumer_id = ?");
            select.setLong(1, consumerId);
            set = select.executeQuery();
            if (set.next()) {
                String name = set.getString(1);
                String clientId = set.getString(2);
                boolean isQueue = set.getBoolean(3);
                if (isQueue) {
                    result = new Consumer(new JmsQueue(name));
                } else {
                    result = new Consumer(name, clientId);
                }
                getSubscriptions(consumerId, result);
            }
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to get consumer",
                                           exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }
        return result;
    }

    /**
     * Returns all consumer identifiers.
     *
     * @return a list of consumer identifiers
     * @throws PersistenceException for any persistence error
     */
    public List getConsumerIds() throws PersistenceException {
        ArrayList result = new ArrayList();

        PreparedStatement select = null;
        ResultSet set = null;
        try {
            select = _connection.prepareStatement(
                    "select consumer_id from " + CONSUMER_TABLE);

            set = select.executeQuery();
            while (set.next()) {
                long consumerId = set.getLong("consumer_id");
                result.add(new Long(consumerId));
            }
        } catch (SQLException exception) {
            throw new PersistenceException(
                    "Failed to retrieve consumer identifiers", exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }
        return result;
    }

    /**
     * Add subscriptions for a consumer.
     *
     * @param consumerId the identity of the consumer
     * @param consumer   the consumer
     * @throws PersistenceException for any persistence error
     */
    protected void addSubscriptions(long consumerId, Consumer consumer)
            throws PersistenceException {
        Iterator iterator = consumer.getSubscriptions().iterator();
        while (iterator.hasNext()) {
            Subscription subscription = (Subscription) iterator.next();
            long destinationId = _destinations.getId(
                    subscription.getDestination());
            if (destinationId == -1) {
                throw new PersistenceException(
                        "Destination identifier not found for destination="
                        + subscription.getDestination().getName());
            }

            PreparedStatement insert = null;
            try {
                insert = _connection.prepareStatement(
                        "insert into " + SUBSCRIPTION_TABLE + " values (?, ?)");

                insert.setLong(1, consumerId);
                insert.setLong(2, destinationId);
                insert.executeUpdate();
            } catch (SQLException exception) {
                throw new PersistenceException("Failed to insert subscription",
                                               exception);
            } finally {
                SQLHelper.close(insert);
            }

            addMessages(consumerId, destinationId, subscription);
        }
    }

    /**
     * Add messages for a subscription.
     *
     * @param consumerId    the identity of the consumer
     * @param destinationId the identify of the destination
     * @param subscription  the consumer subscription
     * @throws PersistenceException for any persistence error
     */
    protected void addMessages(long consumerId, long destinationId,
                               Subscription subscription)
            throws PersistenceException {

        Iterator iterator = subscription.getMessages().iterator();
        while (iterator.hasNext()) {
            MessageState message = (MessageState) iterator.next();

            PreparedStatement insert = null;
            try {
                insert = _connection.prepareStatement(
                        "insert into " + MESSAGE_HANDLE_TABLE + " "
                        + "(message_id, destination_id, consumer_id, delivered)"
                        + " values (?, ?, ?, ?)");
                insert.setString(1, message.getMessageId());
                insert.setLong(2, destinationId);
                insert.setLong(3, consumerId);
                insert.setBoolean(4, message.getDelivered());
                insert.executeUpdate();
            } catch (SQLException exception) {
                throw new PersistenceException(
                        "Failed to insert subscription state", exception);
            } finally {
                SQLHelper.close(insert);
            }
        }
    }

    /**
     * Get subscriptions for a consumer.
     *
     * @param consumerId the identity of the consumer
     * @param consumer   the consumer to populate
     * @throws PersistenceException for any persistence error
     */
    protected void getSubscriptions(long consumerId, Consumer consumer)
            throws PersistenceException {

        PreparedStatement select = null;
        ResultSet set = null;
        try {
            select = _connection.prepareStatement(
                    "select destination_id "
                    + "from " + SUBSCRIPTION_TABLE
                    + " where consumer_id = ?");
            select.setLong(1, consumerId);

            set = select.executeQuery();
            while (set.next()) {
                long destinationId = set.getLong("destination_id");
                JmsDestination destination = _destinations.get(destinationId);
                if (destination == null) {
                    throw new PersistenceException(
                            "Failed to locate destination for id="
                            + destinationId);
                }
                Subscription subscription = new Subscription(destination);
                getMessages(consumerId, destinationId, subscription);
                consumer.addSubscription(subscription);
            }
        } catch (SQLException exception) {
            throw new PersistenceException(
                    "Failed to get subscriptions for consumer=" + consumerId,
                    exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }
    }

    /**
     * Get messages for a subscription.
     *
     * @param consumerId    the identity of the consumer
     * @param destinationId the identify of the destination
     * @param subscription  the consumer subscription
     * @throws SQLException if a database error is encountered
     */
    protected void getMessages(long consumerId, long destinationId,
                               Subscription subscription)
            throws SQLException {

        PreparedStatement select = null;
        ResultSet set = null;
        try {
            select = _connection.prepareStatement(
                    "select message_id, delivered "
                    + "from " + MESSAGE_HANDLE_TABLE
                    + " where consumer_id = ? and destination_id = ?");
            select.setLong(1, consumerId);
            select.setLong(2, destinationId);

            set = select.executeQuery();
            while (set.next()) {
                String messageId = set.getString("message_id");
                boolean delivered = set.getBoolean("delivered");
                subscription.addMessage(messageId, delivered);
            }
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }
    }

    private class ConsumerIterator implements StoreIterator {

        private Iterator _iterator;

        public ConsumerIterator(List consumerIds) {
            _iterator = consumerIds.iterator();
        }

        public boolean hasNext() {
            return _iterator.hasNext();
        }

        public Object next() throws PersistenceException {
            Consumer result = null;

            Long consumerId = (Long) _iterator.next();

            result = get(consumerId.longValue());
            return result;
        }
    }

}



