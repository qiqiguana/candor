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
 *
 * $Id: MasterConsumerStore.java,v 1.2 2005/10/20 14:07:03 tanderson Exp $
 */
package org.exolab.jms.tools.migration.master;

import java.sql.Connection;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;
import javax.jms.JMSException;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsQueue;
import org.exolab.jms.client.JmsTopic;
import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.messagemgr.MessageHandle;
import org.exolab.jms.messagemgr.PersistentMessageHandle;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.persistence.PersistenceAdapter;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.tools.migration.Store;
import org.exolab.jms.tools.migration.StoreIterator;
import org.exolab.jms.tools.migration.proxy.Consumer;
import org.exolab.jms.tools.migration.proxy.MessageState;
import org.exolab.jms.tools.migration.proxy.Subscription;


/**
 * <code>MasterConsumerStore</code> manages a collection of persistent
 * consumers.
 *
 * @author <a href="mailto:tma#netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/10/20 14:07:03 $
 */
public class MasterConsumerStore implements Store {

    /**
     * The database service.
     */
    private DatabaseService _database;


    /**
     * Construct a new <code>MasterConsumerStore</code>.
     *
     * @param database the database service
     */
    public MasterConsumerStore(DatabaseService database) {
        _database = database;
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
        Collection consumers = getConsumers();
        return new ConsumerIterator(consumers);
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
        return getConsumers().size();
    }

    /**
     * Returns the list of consumers.
     *
     * @return a list of {@link Consumer} instances.
     * @throws PersistenceException for any PersistenceError
     */
    private Collection getConsumers() throws PersistenceException {
        Enumeration destinations;

        // Need to jump through some hoops to get a list of all consumers
        Connection connection = _database.getConnection();
        destinations = _database.getAdapter().getAllDestinations(connection);

        HashMap consumers = new HashMap();

        while (destinations.hasMoreElements()) {
            JmsDestination destination =
                    (JmsDestination) destinations.nextElement();
            if (destination instanceof JmsTopic) {
                Enumeration names = _database.getAdapter().getDurableConsumers(
                        connection, destination.getName());
                while (names.hasMoreElements()) {
                    String name = (String) names.nextElement();
                    Consumer consumer = (Consumer) consumers.get(name);
                    if (consumer == null) {
                        consumer = new Consumer(name, null);
                        consumers.put(name, consumer);
                    }
                    Subscription subscription = getSubscription(name,
                                                                destination);
                    consumer.addSubscription(subscription);
                }
            } else {
                final String name = destination.getName();
                Consumer consumer = (Consumer) consumers.get(name);
                if (consumer == null) {
                    consumer = new Consumer((JmsQueue) destination);
                    consumers.put(name, consumer);
                }
                Subscription subscription = getSubscription(name, destination);
                consumer.addSubscription(subscription);
            }
        }
        _database.commit();
        return consumers.values();
    }

    private Subscription getSubscription(String name,
                                         JmsDestination destination)
            throws PersistenceException {

        Subscription result = new Subscription(destination);
        Connection connection = _database.getConnection();

        Vector handles = _database.getAdapter().getMessageHandles(connection,
                                                                  destination,
                                                                  name);
        Iterator iterator = handles.iterator();
        while (iterator.hasNext()) {
            MessageHandle handle = (MessageHandle) iterator.next();
            String id = handle.getMessageId();
            result.addMessage(id, handle.getDelivered());
        }
        return result;
    }

    private void add(Consumer consumer) throws JMSException,
                                               PersistenceException {

        Iterator iterator = consumer.getSubscriptions().iterator();
        while (iterator.hasNext()) {
            Subscription subscription = (Subscription) iterator.next();
            add(consumer, subscription);
        }
    }

    private void add(Consumer consumer, Subscription subscription)
            throws JMSException, PersistenceException {

        String name = consumer.getName();
        JmsDestination destination = subscription.getDestination();
        Iterator iterator = subscription.getMessages().iterator();
        PersistenceAdapter adapter = _database.getAdapter();
        Connection connection = _database.getConnection();

        if (!consumer.isQueueConsumer()) {
            adapter.addDurableConsumer(connection, destination.getName(), name);

        }

        while (iterator.hasNext()) {
            MessageState state = (MessageState) iterator.next();
            MessageImpl message = adapter.getMessage(connection,
                                                     state.getMessageId());
            PersistentMessageHandle handle =
                    new PersistentMessageHandle(message.getJMSMessageID(),
                                                message.getJMSPriority(),
                                                message.getAcceptedTime(),
                                                message.getSequenceNumber(),
                                                message.getJMSExpiration(),
                                                destination,
                                                name);
            handle.setDelivered(state.getDelivered());
            handle.add();
        }
        _database.commit();
    }

    private static class ConsumerIterator implements StoreIterator {

        /**
         * The iterator over the consumer collection.
         */
        private final Iterator _iterator;

        /**
         * Construct a new <code>ConsumerIterator</code>.
         *
         * @param consumers a collection of {@link Consumer} instances.
         */
        public ConsumerIterator(Collection consumers) {
            _iterator = consumers.iterator();
        }

        /**
         * Returns <tt>true</tt> if the iterator has more elements.
         *
         * @return <tt>true</tt> if the iterator has more elements.
         */
        public boolean hasNext() {
            return _iterator.hasNext();
        }

        /**
         * Returns the next element in the interation.
         *
         * @return the next element in the iteration.
         * @throws PersistenceException   for any persistence error
         * @throws NoSuchElementException iteration has no more elements.
         */
        public Object next() throws PersistenceException {
            return _iterator.next();
        }
    }

}
