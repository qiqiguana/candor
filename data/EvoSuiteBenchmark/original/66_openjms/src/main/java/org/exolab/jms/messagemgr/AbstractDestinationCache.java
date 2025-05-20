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
 * $Id: AbstractDestinationCache.java,v 1.4 2007/01/24 12:00:28 tanderson Exp $
 */
package org.exolab.jms.messagemgr;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.lease.LeaseEventListenerIfc;
import org.exolab.jms.lease.LeaseManager;
import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.persistence.DatabaseService;


/**
 * Abstract implementation of the {@link DestinationCache} interface.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2007/01/24 12:00:28 $
 */
public abstract class AbstractDestinationCache implements DestinationCache,
        LeaseEventListenerIfc {

    /**
     * The destination to cache messages for.
     */
    private final JmsDestination _destination;

    /**
     * The message cache for this destination.
     */
    private DefaultMessageCache _cache = new DefaultMessageCache();

    /**
     * The set of consumers that have subscribed to this cache, keyed on id.
     */
    private Map _consumers = Collections.synchronizedMap(new HashMap());

    /**
     * A map of String -> MessageLease objects, representing the active leases
     * keyed on JMSMessageID.
     */
    private final HashMap _leases = new HashMap();

    /**
     * The database service.
     */
    private final DatabaseService _database;

    /**
     * The lease manager.
     */
    private final LeaseManager _leaseMgr;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(
            AbstractDestinationCache.class);


    /**
     * Construct a new <code>AbstractDestinationCache</code>.
     *
     * @param destination the destination to cache messages for
     * @param database    the database service
     * @param leases      the lease manager
     */
    public AbstractDestinationCache(JmsDestination destination,
                                    DatabaseService database,
                                    LeaseManager leases) {
        if (destination == null) {
            throw new IllegalArgumentException(
                    "Argument 'destination' is null");
        }
        if (database == null) {
            throw new IllegalArgumentException("Argument 'database' is null");
        }
        if (leases == null) {
            throw new IllegalArgumentException("Argument 'leases' is null");
        }
        _destination = destination;
        _database = database;
        _leaseMgr = leases;
    }

    /**
     * Returns the destination that messages are being cached for.
     *
     * @return the destination that messages are being cached for
     */
    public JmsDestination getDestination() {
        return _destination;
    }

    /**
     * Register a consumer with this cache.
     *
     * @param consumer the message consumer for this destination
     * @return <code>true</code> if registered; otherwise <code>false</code>
     */
    public boolean addConsumer(ConsumerEndpoint consumer) {
        boolean result = false;

        // check to see that the consumer is actually one for this
        // destination
        if (consumer.getDestination().equals(getDestination())) {
            Long key = new Long(consumer.getId());
            if (!_consumers.containsKey(key)) {
                _consumers.put(key, consumer);
                result = true;
            }
        }

        return result;
    }

    /**
     * Remove the consumer for the list of registered consumers.
     *
     * @param consumer the consumer to remove
     */
    public void removeConsumer(ConsumerEndpoint consumer) {
        Long key = new Long(consumer.getId());
        _consumers.remove(key);
    }

    /**
     * Determines if the cache has any consumers.
     *
     * @return <code>true</code> if the cache has consumers; otherwise
     *         <code>false</code>
     */
    public boolean hasConsumers() {
        return !_consumers.isEmpty();
    }

    /**
     * Returns the number of messages in the cache.
     *
     * @return the number of messages in the cache
     */
    public int getMessageCount() {
        return _cache.getMessageCount();
    }

    /**
     * Determines if this cache can be destroyed. This implementation returns
     * <code>true</code> if there are no active consumers.
     *
     * @return <code>true</code> if the cache can be destroyed, otherwise
     *         <code>false</code>
     */
    public boolean canDestroy() {
        return !hasConsumers();
    }

    /**
     * Destroy this cache.
     */
    public synchronized void destroy() {
        // clear the cache
        _cache.clear();

        // remove the consumers
        _consumers.clear();

        // remove the leases
        MessageLease[] leases;
        synchronized (_leases) {
            leases = (MessageLease[]) _leases.values().toArray(
                    new MessageLease[0]);
            _leases.clear();
        }

        for (int i = 0; i < leases.length; ++i) {
            MessageLease lease = leases[i];
            _leaseMgr.removeLease(lease);
        }
    }

    /**
     * Invoked when a message lease has expired.
     *
     * @param object an instance of {@link MessageRef}
     */
    public void onLeaseExpired(Object object) {
        MessageRef reference = (MessageRef) object;
        String messageId = reference.getMessageId();
        synchronized (_leases) {
            _leases.remove(messageId);
        }

        // determine whether the message is persistent or not and take
        // the corresponding action
        try {
            _database.begin();
            if (reference.isPersistent()) {
                persistentMessageExpired(reference);
            } else {
                messageExpired(reference);
            }
            reference.destroy();
            _database.commit();
        } catch (Exception exception) {
            _log.error("Failed to expire message", exception);
            try {
                _database.rollback();
            } catch (PersistenceException error) {
                _log.warn("Failed to rollback", error);
            }
        }
    }

    public void collectGarbage(boolean aggressive) {
        if (aggressive) {
            // clear all persistent messages in the cache
            _cache.clearPersistentMessages();
            if (_log.isDebugEnabled()) {
                _log.debug("Evicted all persistent messages from cache "
                           + getDestination().getName());
            }
        }

        if (_log.isDebugEnabled()) {
            _log.debug("DESTCACHE -" + getDestination().getName()
                       + " Messages: P[" + _cache.getPersistentCount()
                       + "] T[" + _cache.getTransientCount() + "] Total: ["
                       + _cache.getMessageCount() + "]");
        }
    }

    /**
     * Add a message reference and its corresponding message to the cache
     *
     * @param reference the reference to the message
     * @param message   the message
     */
    protected void addMessage(MessageRef reference, MessageImpl message) {
        if (_log.isDebugEnabled()) {
            _log.debug("addMessage(reference=[JMSMessageID="
                    + reference.getMessageId() + "])");
        }
        _cache.addMessage(reference, message);
    }

    /**
     * Returns the message cache.
     *
     * @return the message cache
     */
    protected DefaultMessageCache getMessageCache() {
        return _cache;
    }

    /**
     * Returns a consumer endpoint, given its id.
     *
     * @param consumerId the consumer identity
     * @return the consumer corresponding to <code>id</code>, or
     *         <code>null</code> if none is registered
     */
    protected ConsumerEndpoint getConsumerEndpoint(long consumerId) {
        return (ConsumerEndpoint) _consumers.get(new Long(consumerId));
    }

    /**
     * Helper to return the consumers as an array.
     *
     * @return the consumers of this cache
     */
    protected ConsumerEndpoint[] getConsumerArray() {
        return (ConsumerEndpoint[]) _consumers.values().toArray(
                new ConsumerEndpoint[0]);
    }

    /**
     * Remove an expired non-peristent message, and notify any listeners.
     *
     * @param reference the reference to the expired message
     * @throws JMSException for any error
     */
    protected void messageExpired(MessageRef reference)
            throws JMSException {
        // notify consumers
        String messageId = reference.getMessageId();
        ConsumerEndpoint[] consumers = getConsumerArray();
        for (int i = 0; i < consumers.length; ++i) {
            consumers[i].messageRemoved(messageId);
        }
    }

    /**
     * Remove an expired persistent message, and notify any listeners.
     *
     * @param reference  the reference to the expired message
     * @throws JMSException         if a listener fails to handle the
     *                              expiration
     * @throws PersistenceException if there is a persistence related problem
     */
    protected void persistentMessageExpired(MessageRef reference)
            throws JMSException, PersistenceException {
        // notify consumers
        String messageId = reference.getMessageId();
        ConsumerEndpoint[] consumers = getConsumerArray();

        for (int i = 0; i < consumers.length; ++i) {
            consumers[i].persistentMessageRemoved(messageId);
        }
    }

    /**
     * Check to see if the message has a TTL. If so then set up a lease for it.
     * An expiry time of 0 means that the message never expires
     *
     * @param reference a reference to the message
     * @param message   the message
     * @throws JMSException if the JMSExpiration property can't be accessed
     */
    protected void checkMessageExpiry(MessageRef reference,
                                      MessageImpl message) throws JMSException {
        checkMessageExpiry(reference, message.getJMSExpiration());
    }

    /**
     * Check to see if the message has a TTL. If so then set up a lease for it.
     * An expiry time of 0 means that the message never expires
     *
     * @param reference  a reference to the message
     * @param expiryTime the time when the message expires
     */
    protected void checkMessageExpiry(MessageRef reference,
                                      long expiryTime) {
        if (expiryTime != 0) {
            synchronized (_leases) {
                // ensure that a lease for this message does not already exist.
                if (!_leases.containsKey(reference.getMessageId())) {
                    long duration = expiryTime - System.currentTimeMillis();
                    if (duration <= 0) {
                        duration = 1;
                    }
                    MessageLease lease = new MessageLease(reference, duration,
                                                          this);
                    _leaseMgr.addLease(lease);
                    _leases.put(reference.getMessageId(), lease);
                }
            }
        }
    }

}
