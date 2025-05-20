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
 * $Id: DestinationManagerImpl.java,v 1.2 2005/11/12 10:49:48 tanderson Exp $
 */
package org.exolab.jms.messagemgr;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsQueue;
import org.exolab.jms.client.JmsTopic;
import org.exolab.jms.gc.GarbageCollectionService;
import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.persistence.PersistenceAdapter;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.service.Service;
import org.exolab.jms.service.ServiceException;


/**
 * The destination manager is responsible for creating and managing the
 * lifecycle of {@link DestinationCache} objects. The destination manager is
 * also responsible for managing messages, that are received by the message
 * manager, which do not have any registered {@link DestinationCache}.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/11/12 10:49:48 $
 */
public class DestinationManagerImpl extends Service
        implements DestinationManager {

    /**
     * The set of persistent and non-persistent destinations, keyed on name.
     */
    private final HashMap _destinations = new HashMap();

    /**
     * The set of active DestinationCache instances, keyed on destination.
     */
    private final HashMap _caches = new HashMap();

    /**
     * Synchronization helper. Should be synchronized on whenever accessing
     * _destinations, or _caches
     */
    private final Object _lock = _destinations;

    /**
     * Maintains a linked list of DestinationEventListener objects. These
     * listeners will be informed when destinations are added or destroyed.
     */
    private LinkedList _listeners = new LinkedList();

    /**
     * The message manager.
     */
    private final MessageManager _messages;

    /**
     * The destination cache factory.
     */
    private final DestinationCacheFactory _factory;

    /**
     * The consumer manager.
     */
    private ConsumerManager _consumers;

    /**
     * The database service.
     */
    private final DatabaseService _database;

    /**
     * The garbage collection service.
     */
    private final GarbageCollectionService _collector;

    /**
     * The logger.
     */
    private static final Log _log =
            LogFactory.getLog(DestinationManagerImpl.class);

    /**
     * Construct a new <code>DestinationManagerImpl</code>.
     *
     * @param messages  the message manager
     * @param factory   the destination cache factory
     * @param database  the database service
     * @param collector the garbage collection service
     */
    public DestinationManagerImpl(MessageManager messages,
                                  DestinationCacheFactory factory,
                                  DatabaseService database,
                                  GarbageCollectionService collector) {
        if (messages == null) {
            throw new IllegalArgumentException("Argument 'messages' is null");
        }
        if (factory == null) {
            throw new IllegalArgumentException("Argument 'factory' is null");
        }
        if (database == null) {
            throw new IllegalArgumentException("Argument 'database' is null");
        }
        if (collector == null) {
            throw new IllegalArgumentException("Argument 'collector' is null");
        }
        _messages = messages;
        _factory = factory;
        _database = database;
        _collector = collector;
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
     * Returns the cache for the supplied destination.
     * <p/>
     * If the cache doesn't exist, it will be created, and any registered {@link
     * DestinationEventListener}s will be notified.
     *
     * @param destination the destination of the cache to return
     * @return the cache associated with <code>destination</code>
     * @throws InvalidDestinationException if <code>destination</code> doesn't
     *                                     exist
     * @throws JMSException                if the cache can't be created
     */
    public DestinationCache getDestinationCache(JmsDestination destination)
            throws JMSException {
        DestinationCache result;
        boolean created = false;

        synchronized (_lock) {
            final String name = destination.getName();

            // make sure the managed destination instance is used.
            destination = getExistingDestination(name);

            result = (DestinationCache) _caches.get(destination);
            if (result == null) {
                checkWildcard(destination);
                result = _factory.createDestinationCache(destination);
                _caches.put(destination, result);
                _messages.addEventListener(destination, result);
                created = true;
            }
        }

        if (created) {
            // notify the listeners that a new cache has been added,
            // outside the sync of _lock
            notifyCacheAdded(result);
        }

        return result;
    }

    /**
     * Returns a destination given its name.
     *
     * @param name the name of the destination
     * @return the destination corresponding to <code>name</code> or
     *         <code>null</code> if none exists
     */
    public JmsDestination getDestination(String name) {
        synchronized (_lock) {
            return (JmsDestination) _destinations.get(name);
        }
    }

    /**
     * Register an event listener to be notified when destinations are created
     * and destroyed.
     *
     * @param listener the listener to add
     */
    public void addDestinationEventListener(DestinationEventListener listener) {
        synchronized (_listeners) {
            if (!_listeners.contains(listener)) {
                _listeners.add(listener);
            }
        }
    }

    /**
     * Remove an event listener.
     *
     * @param listener the listener to remove
     */
    public void removeDestinationEventListener(
            DestinationEventListener listener) {
        synchronized (_listeners) {
            _listeners.remove(listener);
        }
    }

    /**
     * Create a destination.
     * <p/>
     * Any registered {@link DestinationEventListener}s will be notified.
     *
     * @param destination the destination to create
     * @throws InvalidDestinationException if the destination already exists or
     *                                     is a wildcard destination
     * @throws JMSException                if the destination can't be created
     */
    public void createDestination(JmsDestination destination)
            throws JMSException {
        checkWildcard(destination);
        synchronized (_lock) {
            if (exists(destination.getName())) {
                throw new InvalidDestinationException(
                        "Destination already exists: " + destination.getName());
            }
            if (destination.getPersistent()) {
                createPersistentDestination(destination);
            }
            addToDestinations(destination);
        }

        notifyDestinationAdded(destination);
    }

    /**
     * Remove a destination.
     * <p/>
     * All messages and durable consumers will be removed. Any registered {@link
     * DestinationEventListener}s will be notified.
     *
     * @param destination the destination to remove
     * @throws InvalidDestinationException if the destination is invalid
     * @throws JMSException                if the destination can't be removed
     */
    public void removeDestination(JmsDestination destination)
            throws JMSException {

        if (_log.isDebugEnabled()) {
            _log.debug("removeDestination(destination=" + destination + ")");
        }

        // make sure the managed destination instance is used.
        destination = getExistingDestination(destination.getName());

        boolean queue = (destination instanceof JmsQueue) ? true : false;

        if (!queue) {
            // If its a topic, unsubscribe any inactive durable subscribers.
            // The following will fail if there are active subscribers
            _consumers.unsubscribe((JmsTopic) destination);
        }

        synchronized (_lock) {
            DestinationCache cache =
                    (DestinationCache) _caches.get(destination);
            // make sure there are no consumers
            if (cache != null && cache.hasConsumers()) {
                throw new JMSException("Cannot delete destination"
                                       + destination + " since there are "
                                       + " active consumers.");
            }
        }

        // now that we have removed all the durable consumers we can remove
        // the administered topic. First delete it from memory and then
        // from the persistent store
        try {
            _database.begin();
            Connection connection = _database.getConnection();

            _database.getAdapter().removeDestination(connection,
                                                     destination.getName());
            destroyDestinationCache(destination);
            removeFromDestinations(destination);
            _database.commit();
        } catch (Exception exception) { // JMSException, PersistenceException
            String msg = "Failed to remove destination "
                    + destination.getName();
            cleanup(msg, exception);
        }

        notifyDestinationRemoved(destination);
    }

    /**
     * Invoked when the {@link MessageManager} receives a non-persistent
     * message.
     *
     * @param destination the message's destination
     * @param message     the message
     * @throws JMSException if the listener fails to handle the message
     */
    public void messageAdded(JmsDestination destination,
                             MessageImpl message)
            throws JMSException {
        if (destination instanceof JmsTopic) {
            // check to see whether there are active consumers interested
            // in the specified destination. If there are then we need to
            // create a destination cache and pass the message to it.
            if (_consumers.hasActiveConsumers(destination)) {
                if (!exists(destination.getName())) {
                    createDestination(destination);
                }
                DestinationCache cache = getDestinationCache(destination);
                cache.messageAdded(destination, message);
            }
        } else {
            // destination is a queue. Since the message is non-persistent,
            // create the cache and pass the message to it.
            if (!exists(destination.getName())) {
                createDestination(destination);
            }
            DestinationCache cache = getDestinationCache(destination);
            cache.messageAdded(destination, message);
        }
    }

    /**
     * Invoked when the {@link MessageManager} receives a persistent message.
     *
     * @param destination the message's destination
     * @param message     the message
     * @throws JMSException         if the listener fails to handle the message
     * @throws PersistenceException if there is a persistence related problem
     */
    public void persistentMessageAdded(JmsDestination destination,
                                       MessageImpl message)
            throws JMSException, PersistenceException {
        DestinationCache cache = getDestinationCache(destination);
        cache.persistentMessageAdded(destination, message);
    }

    /**
     * Returns all destinations.
     *
     * @return a list of JmsDestination instances.
     * @throws JMSException for any JMS error
     */
    public List getDestinations() throws JMSException {
        synchronized (_lock) {
            return new ArrayList(_destinations.values());
        }
    }

    /**
     * Returns a map of all destinations that match the specified topic.
     * <p/>
     * If the topic represents a wildcard then it may match none, one or more
     * destinations.
     *
     * @param topic the topic
     * @return a map of topics to DestinationCache instances
     */
    public Map getTopicDestinationCaches(JmsTopic topic) {
        HashMap result = new HashMap();

        synchronized (_lock) {
            Iterator iter = _caches.keySet().iterator();
            while (iter.hasNext()) {
                JmsDestination dest = (JmsDestination) iter.next();
                if ((dest instanceof JmsTopic) &&
                        (topic.match((JmsTopic) dest))) {
                    result.put(dest, _caches.get(dest));
                }
            }
        }

        return result;
    }

    /**
     * Perform any garbage collection on this resource. This will have the
     * effect of releasing system resources.  If the 'aggressive' flag is set to
     * true then the garbage collection should do more to release memory related
     * resources since it is called when the application memory is low.
     *
     * @param aggressive <code>true</code> for aggressive garbage collection
     */
    public void collectGarbage(boolean aggressive) {
        int gcCaches = 0;
        int gcDestinations = 0;

        DestinationCache[] caches;
        synchronized (_lock) {
            caches = (DestinationCache[]) _caches.values().toArray(
                    new DestinationCache[0]);
        }
        for (int index = 0; index < caches.length; index++) {
            DestinationCache cache = caches[index];
            if (cache.canDestroy()) {
                if (_log.isDebugEnabled()) {
                    _log.debug("Garbage collecting destination cache="
                               + cache);
                }
                destroyDestinationCache(cache);
                gcCaches++;
            } else {
                // the cache is active, so issue a garbage collection
                // request on it
                cache.collectGarbage(aggressive);
            }
        }

        // get rid of non-persistent destinations, without associated caches.
        synchronized (_lock) {
            JmsDestination[] destinations
                    = (JmsDestination[]) _destinations.values().toArray(
                            new JmsDestination[0]);
            for (int i = 0; i < destinations.length; ++i) {
                JmsDestination dest = destinations[i];
                if (!dest.getPersistent() && !_caches.containsKey(dest)) {
                    gcDestinations++;
                    _destinations.remove(dest.getName());
                }
            }

            // log the information
            _log.info("DMGC Collected " + gcCaches + " caches, "
                      + _caches.size()
                      + " remaining.");
            _log.info("DMGC Collected " + gcDestinations + " destinations, "
                      + _destinations.size() + " remaining.");
        }

    }

    /**
     * Start the service.
     *
     * @throws ServiceException if the service fails to start
     */
    protected void doStart() throws ServiceException {
        if (_consumers == null) {
            throw new ServiceException(
                    "ConsumerManager hasn't been initialised");
        }
        init();
        _collector.register(this);
    }

    /**
     * Stop the service.
     *
     * @throws ServiceException if the service fails to stop
     */
    protected void doStop() throws ServiceException {
        _collector.unregister(this);

        JmsDestination[] destinations;
        synchronized (_lock) {
            destinations = (JmsDestination[]) _caches.keySet().toArray(
                    new JmsDestination[0]);
        }
        for (int index = 0; index < destinations.length; index++) {
            destroyDestinationCache(destinations[index]);
        }

        _caches.clear();

        _destinations.clear();

        // remove all the listeners
        synchronized (_listeners) {
            _listeners.clear();
        }
    }

    /**
     * Initialises the destination manager.
     *
     * @throws ServiceException if the service cannot be initialised
     */
    protected void init() throws ServiceException {
        Enumeration iter;
        try {
            _database.begin();
            Connection connection = _database.getConnection();

            // return a list of JmsDestination objects.
            iter = _database.getAdapter().getAllDestinations(connection);
            _database.commit();
        } catch (PersistenceException exception) {
            _log.error(exception, exception);
            rollback();
            throw new ServiceException("Failed to get destinations", exception);
        }

        while (iter.hasMoreElements()) {
            // add each destination to the cache
            JmsDestination dest = (JmsDestination) iter.nextElement();
            addToDestinations(dest);
        }
    }

    /**
     * Determines if a destination exists.
     *
     * @param name the destination name
     * @return <code>true</code> if the destination exists, otherwise
     *         <code>false
     */
    protected boolean exists(String name) {
        return getDestination(name) != null;
    }

    /**
     * Delete the specfied destination.
     *
     * @param cache the destination to destroy
     */
    protected void destroyDestinationCache(DestinationCache cache) {
        destroyDestinationCache(cache.getDestination());
    }

    /**
     * Delete the specfied destination.
     *
     * @param dest the destination to destroy
     */
    protected void destroyDestinationCache(JmsDestination dest) {
        synchronized (_lock) {
            DestinationCache cache = (DestinationCache) _caches.remove(dest);
            if (cache != null) {
                // deregister the cache from message manager.
                _messages.removeEventListener(dest);

                // notify the listeners that a cache has been removed from
                // the destination manager
                notifyCacheRemoved(cache);

                cache.destroy();
            }
        }
    }

    /**
     * Create a persistent destination.
     *
     * @param destination the destination to create
     * @throws JMSException if the destination cannot be created
     */
    private void createPersistentDestination(JmsDestination destination)
            throws JMSException {
        if (_log.isDebugEnabled()) {
            _log.debug("createPersistentDestination(destination="
                       + destination + ")");
        }

        boolean queue = (destination instanceof JmsQueue) ? true : false;
        PersistenceAdapter adapter = _database.getAdapter();

        // check that the destination does not exist. If it exists then return
        // false. If it doesn't exists the create it and bind it to the jndi
        // context

        try {
            _database.begin();
            Connection connection = _database.getConnection();
            adapter.addDestination(connection, destination.getName(), queue);
            _database.commit();
        } catch (Exception exception) { // JMSException, PersistenceException
            cleanup("Failed to create persistent destination "
                    + destination.getName(), exception);
        }
    }

    /**
     * Notify the list of {@link DestinationEventListener} objects that the
     * specified destination has been added.
     *
     * @param destination the added destination
     * @throws JMSException if a listener fails to be notified
     */
    private void notifyDestinationAdded(JmsDestination destination)
            throws JMSException {
        DestinationEventListener[] listeners = getListeners();
        for (int i = 0; i < listeners.length; ++i) {
            listeners[i].destinationAdded(destination);
        }
    }

    /**
     * Notify the list of {@link DestinationEventListener} objects that the
     * specified destination has been removed.
     *
     * @param destination the added destination
     * @throws JMSException if a listeners fails to be notified
     */
    private void notifyDestinationRemoved(JmsDestination destination)
            throws JMSException {
        DestinationEventListener[] listeners = getListeners();
        for (int i = 0; i < listeners.length; ++i) {
            listeners[i].destinationRemoved(destination);
        }
    }

    /**
     * Notify the list of {@link DestinationEventListener} objects that the
     * specified message cache has been added.
     *
     * @param cache the added cache
     */
    private void notifyCacheAdded(DestinationCache cache) {
        JmsDestination destination = cache.getDestination();
        DestinationEventListener[] listeners = getListeners();
        for (int i = 0; i < listeners.length; ++i) {
            listeners[i].cacheAdded(destination, cache);
        }
    }

    /**
     * Notify the list of {@link DestinationEventListener} objects that the
     * specified message cache has been removed.
     *
     * @param cache the added cache
     */
    private void notifyCacheRemoved(DestinationCache cache) {
        JmsDestination destination = cache.getDestination();
        DestinationEventListener[] listeners = getListeners();
        for (int i = 0; i < listeners.length; ++i) {
            listeners[i].cacheRemoved(destination, cache);
        }
    }

    /**
     * Add the specified destination to the destination cache.
     *
     * @param destination the destination to add
     */
    private void addToDestinations(JmsDestination destination) {
        synchronized (_lock) {
            if (!_destinations.containsKey(destination.getName())) {
                _destinations.put(destination.getName(), destination);
            }
        }
    }

    /**
     * Remove the specified destination from the cache.
     *
     * @param destination the destination to remove
     */
    private void removeFromDestinations(JmsDestination destination) {
        synchronized (_lock) {
            _destinations.remove(destination.getName());
        }
    }

    /**
     * Returns a destination given its name.
     *
     * @param name the name of the destination
     * @return the destination corresponding to <code>name</code>
     * @throws InvalidDestinationException if the named destination doesn't
     *                                     exist
     */
    private JmsDestination getExistingDestination(String name)
            throws InvalidDestinationException {
        JmsDestination destination = getDestination(name);
        if (destination == null) {
            throw new InvalidDestinationException(
                    "Destination does not exist:" + name);
        }
        return destination;
    }

    /**
     * Ensures that the specified destination isn't a wildcard.
     *
     * @param destination the destination to check
     * @throws InvalidDestinationException if the destination is a wildcard
     */
    private void checkWildcard(JmsDestination destination)
            throws InvalidDestinationException {
        if (destination instanceof JmsTopic
                && ((JmsTopic) destination).isWildCard()) {
            throw new InvalidDestinationException(
                    "Wildcarded topics cannot be managed: "
                    + destination.getName());
        }
    }

    /**
     * Returns the registered {@link DestinationEventListener}s.
     *
     * @return the registered {@link DestinationEventListener}s.
     */
    private DestinationEventListener[] getListeners() {
        synchronized (_listeners) {
            return (DestinationEventListener[]) _listeners.toArray(
                    new DestinationEventListener[0]);
        }
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

    /**
     * Cleanup a failed transaction, and propagate the exception as a
     * JMSException.
     *
     * @param message   the message to log
     * @param exception the exception propagate
     * @throws JMSException <code>exception</code> if it is an instance of
     *                      JMSException, else a new JMSException containing
     *                      <code>message</code>
     */
    private void cleanup(String message, Exception exception)
            throws JMSException {
        _log.error(message, exception);
        rollback();
        if (exception instanceof JMSException) {
            throw (JMSException) exception;
        } else {
            throw new JMSException(message + ": " + exception.getMessage());
        }
    }
}
