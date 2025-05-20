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
 * Copyright 2001-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: ConsumerManagerImpl.java,v 1.3 2005/12/23 12:17:25 tanderson Exp $
 */
package org.exolab.jms.messagemgr;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.jms.InvalidDestinationException;
import javax.jms.InvalidSelectorException;
import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsQueue;
import org.exolab.jms.client.JmsTopic;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.persistence.PersistenceAdapter;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.service.Service;
import org.exolab.jms.service.ServiceException;


/**
 * The consumer manager is responsible for creating and managing the lifecycle
 * of consumers. The consumer manager maintains a list of all active consumers.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/12/23 12:17:25 $
 */
public class ConsumerManagerImpl extends Service implements ConsumerManager {

    /**
     * The destination manager.
     */
    private final DestinationManager _destinations;

    /**
     * The database service.
     */
    private final DatabaseService _database;

    /**
     * Maintains a list of all consumers, durable and non-durable. All durable
     * subscribers are maintained in memory until they are removed from the
     * system entirely. All non-durable subscribers are maintained in memory
     * until their endpoint is removed.
     */
    private HashMap _consumers = new HashMap();

    /**
     * The set of all consumer endpoints. This is a map of {@link
     * ConsumerEndpoint} instances, keyed on {@link ConsumerEndpoint#getPersistentId()}
     * if non null; otherwise {@link ConsumerEndpoint#getId()}.
     */
    private HashMap _endpoints = new HashMap();


    /**
     * Maintains a mapping between destinations and consumers. A destination can
     * have more than one consumer and a consumer can also be registered to more
     * than one destination
     */
    private HashMap _destToConsumerMap = new HashMap();

    /**
     * The set of all wildcard consumers, represented by a map of ConsumerEntry
     * -> JmsTopic instances.
     */
    private HashMap _wildcardConsumers = new HashMap();

    /**
     * The seed to allocate identifiers to new consumers.
     */
    private long _consumerIdSeed = 0;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(
            ConsumerManagerImpl.class);


    /**
     * Construct a new  <code>ConsumerManager</code>.
     *
     * @param destinations the destination manager
     * @param database     the database service
     */
    public ConsumerManagerImpl(DestinationManager destinations,
                               DatabaseService database) {
        if (destinations == null) {
            throw new IllegalArgumentException(
                    "Argument 'destinations' is null");
        }
        if (database == null) {
            throw new IllegalArgumentException("Argument 'database' is null");
        }
        _destinations = destinations;
        _database = database;
    }

    /**
     * Create a new durable subscription.
     * <p/>
     * A client can change an existing durable subscription by creating a new
     * subscription with the same name and a new topic. Changing a durable
     * subscriber is equivalent to unsubscribing the old one and creating a new
     * one.
     *
     * @param topic    the topic to subscribe to
     * @param name     the subscription name
     * @param clientID the client identifier. May be <code>null</code>
     * @throws InvalidDestinationException if <code>topic</code> is not a
     *                                     persistent destination, or
     *                                     <code>name</code> is an invalid
     *                                     subscription name
     * @throws JMSException                if the durable consumer can't be
     *                                     created
     */
    public synchronized void subscribe(JmsTopic topic, String name,
                                       String clientID)
            throws JMSException {
        createInactiveDurableConsumer(topic, name, clientID);
    }


    /**
     * Remove a durable subscription.
     * <p/>
     * A subscription may only be removed if the associated {@link
     * DurableConsumerEndpoint} is inactive.
     *
     * @param name     the subscription name
     * @param clientID the client identifier. May be <code>null</code>
     * @throws InvalidDestinationException if an invalid subscription name is
     *                                     specified.
     * @throws JMSException                if the durable consumer is active, or
     *                                     cannot be removed
     */
    public synchronized void unsubscribe(String name, String clientID)
            throws JMSException {
        if (_log.isDebugEnabled()) {
            _log.debug("unsubscribe(name=" + name + ", clientID="
                       + clientID + ")");
        }

        DurableConsumerEndpoint consumer
                = (DurableConsumerEndpoint) _endpoints.remove(name);
        if (consumer == null) {
            throw new InvalidDestinationException("Durable consumer " + name
                                                  + " is not defined.");
        }
        if (consumer.isActive()) {
            throw new JMSException("Cannot remove durable consumer=" + name
                                   + ": consumer is active");
        }
        consumer.close();

        // remove it from the persistent store.
        try {
            _database.begin();
            Connection connection = _database.getConnection();

            _database.getAdapter().removeDurableConsumer(connection, name);
            removeConsumerEntry(name);
            _database.commit();
        } catch (PersistenceException exception) {
            String msg = "Failed to remove durable consumer, name=" + name;
            rethrow(msg, exception);
        }
    }

    /**
     * Remove all durable subscriptions for a destination.
     * <p/>
     * Subscriptions may only be removed if the associated {@link
     * ConsumerEndpoint}s are inactive.
     *
     * @param topic the topic to remove consumers for
     * @throws JMSException if the subscriptions can't be removed
     */
    public synchronized void unsubscribe(JmsTopic topic) throws JMSException {
        List list = (List) _destToConsumerMap.get(topic);
        if (list != null) {
            ConsumerEntry[] consumers
                    = (ConsumerEntry[]) list.toArray(new ConsumerEntry[0]);
            for (int i = 0; i < consumers.length; ++i) {
                ConsumerEntry consumer = consumers[i];
                if (consumer.isDurable()) {
                    // remove the durable consumer. This operation
                    // will fail if the consumer is active.
                    unsubscribe(consumer.getName(), consumer.getClientID());
                }
            }
        }

        // remove all consumers for the specified destination
        removeFromConsumerCache(topic);
    }

    /**
     * Create a transient consumer for the specified destination.
     *
     * @param destination  the destination to consume messages from
     * @param connectionId the identity of the connection that owns this
     *                     consumer
     * @param selector     the message selector. May be <code>null</code>
     * @param noLocal      if true, and the destination is a topic, inhibits the
     *                     delivery of messages published by its own connection.
     *                     The behavior for <code>noLocal</code> is not
     *                     specified if the destination is a queue.
     * @return a new transient consumer
     */
    public synchronized ConsumerEndpoint createConsumer(
            JmsDestination destination, long connectionId,
            String selector,
            boolean noLocal)
            throws JMSException, InvalidSelectorException {

        if (_log.isDebugEnabled()) {
            _log.debug("createConsumerEndpoint(destination=" + destination
                       + ", connectionId=" + connectionId
                       + ", selector=" + selector
                       + ", noLocal=" + noLocal + ")");
        }

        ConsumerEndpoint consumer = null;

        // ensure that the destination is valid before proceeding
        getDestination(destination, true);

        long consumerId = getNextConsumerId();

        try {
            _database.begin();
            // determine what type of consumer to create based on the destination
            // it subscribes to.
            if (destination instanceof JmsTopic) {
                JmsTopic topic = (JmsTopic) destination;
                consumer = new TopicConsumerEndpoint(consumerId, connectionId,
                                                     topic, selector, noLocal,
                                                     _destinations);
            } else if (destination instanceof JmsQueue) {
                QueueDestinationCache cache;
                cache = (QueueDestinationCache) _destinations.getDestinationCache(
                        destination);
                consumer = new QueueConsumerEndpoint(consumerId, cache, selector);
            }

            if (consumer != null) {
                // add it to the list of managed consumers. If it has a persistent
                // identity, use that as the key, otherwise use its transient
                // identity.
                Object key = ConsumerEntry.getConsumerKey(consumer);
                _endpoints.put(key, consumer);
                addConsumerEntry(key, destination, null, false);
            }
            _database.commit();
        } catch (Exception exception) {
            rethrow("Failed to create consumer", exception);
        }

        return consumer;
    }

    /**
     * Create a durable consumer.
     *
     * @param topic        the topic to subscribe to
     * @param name         the subscription name
     * @param clientID     the client identifier. May be <code>null</code>.
     * @param connectionId the identity of the connection that owns this
     *                     consumer
     * @param noLocal      if true, and the destination is a topic, inhibits the
     *                     delivery of messages published by its own
     *                     connection.
     * @param selector     the message selector. May be <code>null</code>
     * @return the durable consumer endpoint
     * @throws InvalidDestinationException if <code>topic</code> is not a
     *                                     persistent destination
     * @throws InvalidSelectorException    if the selector is not well formed
     * @throws JMSException                if a durable consumer is already
     *                                     active with the same <code>name</code>
     */
    public synchronized DurableConsumerEndpoint createDurableConsumer(
            JmsTopic topic, String name, String clientID, long connectionId,
            boolean noLocal,
            String selector)
            throws JMSException {

        if (_log.isDebugEnabled()) {
            _log.debug("createDurableConsumer(topic=" + topic
                       + ", name=" + name + ", connectionId=" + connectionId
                       + ", selector=" + selector + ", noLocal=" + noLocal
                       + ")");
        }

        DurableConsumerEndpoint consumer
                = createInactiveDurableConsumer(topic, name, clientID);
        consumer.activate(connectionId, selector, noLocal);

        return consumer;
    }

    /**
     * Create a browser for the specified destination and the selector. A
     * browser is responsible for passing all messages back to the client that
     * reside on the queue
     *
     * @param queue    the queue to browse
     * @param selector the message selector. May be <code>null</code>
     * @return the queue browser endpoint
     * @throws JMSException             if the browser can't be created
     */
    public synchronized ConsumerEndpoint createQueueBrowser(JmsQueue queue,
                                                            String selector)
            throws JMSException {

        // ensure that the destination is valid before proceeding
        getDestination(queue, true);

        long consumerId = getNextConsumerId();


        ConsumerEndpoint consumer = null;
        try {
            _database.begin();
            QueueDestinationCache cache;
            cache = (QueueDestinationCache) _destinations.getDestinationCache(
                    queue);
            consumer = new QueueBrowserEndpoint(consumerId, cache, selector);
            Object key = ConsumerEntry.getConsumerKey(consumer);
            _endpoints.put(key, consumer);
            addConsumerEntry(key, queue, null, false);
            _database.commit();
        } catch (Exception exception) {
            rethrow("Failed to create browser", exception);
        }

        return consumer;
    }

    /**
     * Close a consumer.
     *
     * @param consumer the consumer to close
     */
    public synchronized void closeConsumer(ConsumerEndpoint consumer) {
        if (_log.isDebugEnabled()) {
            _log.debug("closeConsumerEndpoint(consumer=[Id="
                       + consumer.getId() + ", destination="
                       + consumer.getDestination() + ")");
        }

        Object key = ConsumerEntry.getConsumerKey(consumer);

        ConsumerEndpoint existing = (ConsumerEndpoint) _endpoints.get(key);
        if (existing != null) {
            try {
                 _database.begin();
                if (consumer.getId() != existing.getId()) {
                    // As a fix for bug 759752, only remove the consumer if it
                    // matches the existing one.
                    // @todo - not sure if this situation can arise any longer
                    _log.error("Existing endpoint doesn't match that to be closed "
                               + "- retaining");
                } else if (existing instanceof DurableConsumerEndpoint) {
                    DurableConsumerEndpoint durable
                            = (DurableConsumerEndpoint) existing;
                    if (durable.isActive()) {
                        try {
                            durable.deactivate();
                        } catch (JMSException exception) {
                            _log.error("Failed to deactivate durable consumer="
                                       + durable, exception);
                        }
                    }
                } else {
                    _endpoints.remove(key);
                    consumer.close();
                    removeConsumerEntry(key);
                }
                _database.commit();
            } catch (PersistenceException exception) {
                _log.error("Failed to close consumer=" + consumer, exception);
                rollback();
            }
        }
    }

    /**
     * Return the consumer with the specified identity.
     *
     * @param consumerId the identity of the consumer
     * @return the associated consumer, or <code>null</code> if none exists
     */
    public synchronized ConsumerEndpoint getConsumerEndpoint(long consumerId) {
        return (ConsumerEndpoint) _endpoints.get(new Long(consumerId));
    }

    /**
     * Return the consumer with the specified persistent identity.
     *
     * @param persistentId the persistent identity of the consumer
     * @return the associated consumer, or <code>null</code> if none exists
     */
    public synchronized ConsumerEndpoint getConsumerEndpoint(
            String persistentId) {
        return (ConsumerEndpoint) _endpoints.get(persistentId);
    }

    /**
     * Determines if there are any active consumers for a destination.
     *
     * @param destination the destination
     * @return <code>true</code> if there is at least one consumer
     */
    public synchronized boolean hasActiveConsumers(JmsDestination destination) {
        boolean result = false;
        ConsumerEndpoint[] consumers = getConsumers();
        for (int i = 0; i < consumers.length; ++i) {
            if (consumers[i].canConsume(destination)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Start the service.
     *
     * @throws ServiceException if the service fails to start
     */
    protected void doStart() throws ServiceException {
        try {
            _database.begin();
            Connection connection = _database.getConnection();

            PersistenceAdapter adapter = _database.getAdapter();

            // return a list of JmsDestination objects.
            HashMap map = adapter.getAllDurableConsumers(connection);
            Iterator iter = map.keySet().iterator();

            // Create an endpoint for each durable consumer
            while (iter.hasNext()) {
                String consumer = (String) iter.next();
                String deststr = (String) map.get(consumer);

                JmsDestination dest = _destinations.getDestination(deststr);
                if (dest == null) {
                    // this maybe a wildcard subscription
                    dest = new JmsTopic(deststr);
                    if (!((JmsTopic) dest).isWildCard()) {
                        dest = null;
                    }
                }

                if (consumer != null && dest != null &&
                        dest instanceof JmsTopic) {
                    // cache the consumer-destination mapping in memory.
                    addDurableConsumer((JmsTopic) dest, consumer, null);
                } else {
                    // @todo
                    _log.error("Failure in ConsumerManager.init : " + consumer +
                               ":" + dest);
                }
            }
            _database.commit();
        } catch (Exception exception) {
            rollback();
            throw new ServiceException("Failed to initialise ConsumerManager",
                                       exception);
        }
    }

    /**
     * Stop the service.
     */
    protected synchronized void doStop() {
        // clean up all the destinations
        Object[] endpoints = _endpoints.values().toArray();
        for (int index = 0; index < endpoints.length; index++) {
            closeConsumer((ConsumerEndpoint) endpoints[index]);
        }
        _endpoints.clear();

        // remove cache data structures
        _consumers.clear();
        _destToConsumerMap.clear();
        _wildcardConsumers.clear();
    }

    /**
     * Create an inactive durable consumer.
     * <p/>
     * If the consumer doesn't exist, it will created in the persistent store.
     * If it does exist, and is inactive, it will be recreated. If it does
     * exist, but is active, an exception will be raised.
     *
     * @param topic    the topic to subscribe to
     * @param name     the subscription name
     * @param clientID the client identifier. May be <code>null</code>.
     * @return the durable consumer
     * @throws InvalidDestinationException if <code>topic</code> is not a
     *                                     persistent destination
     * @throws JMSException                if a durable consumer is already
     *                                     active with the same <code>name</code>,
     *                                     or the consumer can't be created
     */
    private DurableConsumerEndpoint createInactiveDurableConsumer(
            JmsTopic topic, String name, String clientID)
            throws JMSException {
        DurableConsumerEndpoint endpoint;

        if (_log.isDebugEnabled()) {
            _log.debug("createInactiveDurableConsumer(topic=" + topic
                       + ", name=" + name + ", clientID=" + clientID + ")");
        }

        // check that the destination exists, if the topic is not a wildcard
        if (!topic.isWildCard()) {
            topic = (JmsTopic) getDestination(topic, false);
        }

        if (name == null || name.length() == 0) {
            throw new InvalidDestinationException(
                    "Invalid subscription name: " + name);
        }
        endpoint = (DurableConsumerEndpoint) _endpoints.get(name);
        if (endpoint != null) {
            if (endpoint.isActive()) {
                throw new JMSException(
                        "Durable subscriber already exists with name: " + name);
            }
            if (!endpoint.getDestination().equals(topic)) {
                // subscribing to a different topic. Need to re-subscribe.
                unsubscribe(name, clientID);
                endpoint = null;
            }
        }
        if (endpoint == null) {
            try {
                _database.begin();
                PersistenceAdapter adapter = _database.getAdapter();
                Connection connection = _database.getConnection();
                adapter.addDurableConsumer(connection, topic.getName(), name);
                endpoint = addDurableConsumer(topic, name, clientID);
                _database.commit();
            } catch (Exception exception) {
                String msg = "Failed to create durable consumer, name=" + name
                        + ", for topic=" + topic.getName();
                rethrow(msg, exception);
            }
        }

        return endpoint;
    }

    /**
     * Register an inactive durable consumer.
     *
     * @param topic    the topic to subscribe to
     * @param name     the subscription name
     * @param clientID the client identifier. May be <code>null</code>.
     * @return the durable consumer
     * @throws JMSException for any JMS error
     * @throws PersistenceException for any persistence error
     */
    private DurableConsumerEndpoint addDurableConsumer(JmsTopic topic,
                                                       String name,
                                                       String clientID)
            throws JMSException, PersistenceException {
        DurableConsumerEndpoint consumer;
        // cache the consumer locally
        addConsumerEntry(name, topic, clientID, true);

        long consumerId = getNextConsumerId();
        consumer = new DurableConsumerEndpoint(consumerId, topic, name,
                                               _destinations);
        _endpoints.put(consumer.getPersistentId(), consumer);
        return consumer;
    }

    /**
     * Add a consumer entry.
     *
     * @param key         a key to identify the entry.
     * @param destination the destination it is subscribed to. It can be a
     *                    wildcard
     * @param clientID    the client identifier. May be <code>null</code>
     * @param durable     indicates whether it is a durable subscription
     * @throws JMSException if key specifies a duplicate entry.
     */
    private void addConsumerEntry(Object key, JmsDestination destination,
                                  String clientID,
                                  boolean durable)
            throws JMSException {
        if (_log.isDebugEnabled()) {
            _log.debug("addConsumerEntry(key=" + key + ", destination="
                       + destination + ", clientID=" + clientID
                       + ", durable=" + durable + ")");
        }

        if (_consumers.containsKey(key)) {
            throw new JMSException("Duplicate consumer key:" + key);
        }

        ConsumerEntry entry = new ConsumerEntry(key, destination, clientID,
                                                durable);
        _consumers.put(key, entry);

        if (destination instanceof JmsTopic
                && ((JmsTopic) destination).isWildCard()) {
            // if the specified destination is a JmsTopic and also a wildcard
            // then we need to add it to all matching destinations
            _wildcardConsumers.put(entry, destination);
        } else {
            // we also need to add the reverse mapping
            List consumers = (List) _destToConsumerMap.get(destination);
            if (consumers == null) {
                consumers = new ArrayList();
                _destToConsumerMap.put(destination, consumers);
            }

            // add the mapping
            consumers.add(entry);
        }
    }

    /**
     * Remove the specified consumer from the cache.
     *
     * @param key the consumer key
     */
    private void removeConsumerEntry(Object key) {
        if (_log.isDebugEnabled()) {
            _log.debug("removeConsumerEntry(key=" + key + ")");
        }

        ConsumerEntry entry = (ConsumerEntry) _consumers.remove(key);
        if (entry != null) {
            JmsDestination dest = entry.getDestination();

            if (dest instanceof JmsTopic && ((JmsTopic) dest).isWildCard()) {
                // remove it from the wildcard cache.
                _wildcardConsumers.remove(entry);
            } else {
                // remove it from the specified destination
                List consumers = (List) _destToConsumerMap.get(dest);
                if (consumers != null) {
                    consumers.remove(entry);

                    // if consumers is of size 0 then remove it
                    if (consumers.isEmpty()) {
                        _destToConsumerMap.remove(dest);
                    }
                }
            }
        } else if (_log.isDebugEnabled()) {
            _log.debug("removeConsumerEntry(key=" + key
                       + "): consumer not found");
        }
    }

    /**
     * Remove all the consumers for the specified destination from the cache.
     *
     * @param destination the destination to remove
     */
    private void removeFromConsumerCache(JmsDestination destination) {
        _destToConsumerMap.remove(destination);
    }

    /**
     * Returns the next seed value to be allocated to a new consumer.
     *
     * @return a unique identifier for a consumer
     */
    private long getNextConsumerId() {
        return ++_consumerIdSeed;
    }

    /**
     * Returns the destination managed by {@link DestinationManager}
     * corresponding to that supplied, creating it if needed.
     *
     * @param destination the destination to look up
     * @param create      if <code>true</code> the destination may be created if
     *                    it doesn't exist
     * @return the destination managed by {@link DestinationManager}
     *         corresponding to <code>destination</code>.
     * @throws InvalidDestinationException if the destination doesn't exist and
     *                                     <code>create</code> is false; or the
     *                                     destination's properties don't match
     *                                     the existing destination
     * @throws JMSException                if the destination can't be created
     */
    private JmsDestination getDestination(JmsDestination destination,
                                          boolean create)
            throws InvalidDestinationException, JMSException {
        final String name = destination.getName();
        JmsDestination result;
        JmsDestination existing = _destinations.getDestination(name);

        if (existing == null) {
            if (!create) {
                throw new InvalidDestinationException(
                        "No destination with name=" + name + " exists");
            }
            // register the destination dynamically.
            _destinations.createDestination(destination);
            result = _destinations.getDestination(destination.getName());
        } else {
            // make sure the supplied destination has the same properties
            // as the existing one
            if (!destination.getClass().getName().equals(
                    existing.getClass().getName())) {
                throw new InvalidDestinationException(
                        "Mismatched destination properties for destination"
                        + " with name=" + name);
            }
            if (existing.getPersistent() != destination.getPersistent()) {
                throw new InvalidDestinationException(
                        "Mismatched destination properties for destination"
                        + " with name=" + name);
            }
            result = existing;
        }
        return result;
    }

    /**
     * Returns the consumers managed by this.
     *
     * @return an array of consumers
     */
    private ConsumerEndpoint[] getConsumers() {
        return (ConsumerEndpoint[]) _endpoints.values().toArray(
                new ConsumerEndpoint[0]);
    }

    /**
     * Rollback any transaction.
     */
    private void rollback() {
        try {
            if (_database.isTransacted()) {
                _database.rollback();
            }
        } catch (PersistenceException error) {
            _log.warn("Failed to rollback after error", error);
        }
    }

    /**
     * Helper to clean up after a failed call, and rethrow.
     * Any transaction will be rolled back.
     *
     * @param message   the message to log
     * @param exception the exception
     * @throws JMSException the original exception adapted to a
     *                      <code>JMSException</code> if necessary
     */
    private void rethrow(String message, Exception exception)
            throws JMSException {
        rollback();

        if (exception instanceof JMSException) {
            _log.debug(message, exception);
            throw (JMSException) exception;
        }
        // need to adapt the exception, so log as an error before rethrow
        _log.error(message, exception);
        throw new JMSException(exception.getMessage());
    }

    /**
     * Helper class used to maintain consumer information
     */
    private static final class ConsumerEntry {

        /**
         * An identifier for the consumer.
         */
        private final Object _key;

        /**
         * The destination that the consumer is subscribed to.
         */
        private final JmsDestination _destination;

        /**
         * The client identifier. May be <code>null</code>.
         */
        private final String _clientID;

        /**
         * Indicated whether this entry is for a durable subscriber
         */
        private final boolean _durable;


        /**
         * Construct a new <code>ConsumerEntry</code>.
         *
         * @param key         an identifier for the consumer
         * @param destination the destination consumer is subscribed to
         * @param clientID    the client identifier. May be <code>null</code>
         * @param durable     indicates whether it is a durable subscription
         */
        public ConsumerEntry(Object key, JmsDestination destination,
                             String clientID, boolean durable) {
            _key = key;
            _destination = destination;
            _clientID = clientID;
            _durable = durable;
        }

        public boolean equals(Object obj) {
            boolean result = false;
            if (obj instanceof ConsumerEntry) {
                result = ((ConsumerEntry) obj)._key.equals(_key);
            }

            return result;
        }

        public Object getKey() {
            return _key;
        }

        public String getName() {
            return (_key instanceof String) ? (String) _key : null;
        }

        public JmsDestination getDestination() {
            return _destination;
        }

        public String getClientID() {
            return _clientID;
        }

        public boolean isDurable() {
            return _durable;
        }

        /**
         * Helper to return a key for identifying {@link ConsumerEndpoint}
         * instances. This returns the consumers persistent identifier if it has
         * one; if not, it returns its transient identifier.
         *
         * @param consumer the consumer
         * @return a key for identifying <code>consumer</code>
         */
        public static Object getConsumerKey(ConsumerEndpoint consumer) {
            Object key = null;
            String id = consumer.getPersistentId();
            if (id != null) {
                key = id;
            } else {
                key = new Long(consumer.getId());
            }
            return key;
        }
    }

}

