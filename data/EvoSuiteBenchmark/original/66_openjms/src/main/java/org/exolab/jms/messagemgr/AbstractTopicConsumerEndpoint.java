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
 * $Id: AbstractTopicConsumerEndpoint.java,v 1.3 2007/01/24 12:00:28 tanderson Exp $
 */

package org.exolab.jms.messagemgr;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.jms.InvalidSelectorException;
import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsTopic;
import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.server.ServerConnection;


/**
 * A {@link ConsumerEndpoint} for topics.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2007/01/24 12:00:28 $
 */
abstract class AbstractTopicConsumerEndpoint extends AbstractConsumerEndpoint
        implements DestinationEventListener {

    /**
     * The identity of the connection that owns this consumer, or
     * <code>-1</code> if this consumer isn't currently associated with a
     * connection.
     */
    private long _connectionId;

    /**
     * The destination manager.
     */
    private final DestinationManager _destinations;

    /**
     * Cache of all handles for this consumer.
     */
    private MessageQueue _handles = new MessageQueue();

    /**
     * Maintains a map of TopicDestinationCache that this endpoint subscribes
     * to, keyed on JmsTopic. A wildcard subscription may point to more than
     * one.
     */
    protected Map _caches = Collections.synchronizedMap(new HashMap());

    /**
     * The logger.
     */
    private static final Log _log =
            LogFactory.getLog(AbstractTopicConsumerEndpoint.class);


    /**
     * Construct a new <code>TopicConsumerEndpoint</code>.
     * <p/>
     * The destination and selector determine where it will be sourcing its
     * messages from, and scheduler is used to asynchronously deliver messages
     * to the consumer.
     *
     * @param consumerId   the identity of this consumer
     * @param connectionId the identity of the connection that owns this
     *                     consumer
     * @param topic        the topic(s) to access. May be a wildcarded topic.
     * @param selector     the message selector. May be <code>null</code>
     * @param noLocal      if true, inhibits the delivery of messages published
     *                     by its own connection.
     * @param destinations the destination manager
     * @throws InvalidSelectorException if the selector is invalid
     * @throws JMSException             if the destination caches can't be
     *                                  constructed
     */
    public AbstractTopicConsumerEndpoint(long consumerId, long connectionId,
                                         JmsTopic topic,
                                         String selector, boolean noLocal,
                                         DestinationManager destinations)
            throws JMSException {
        super(consumerId, topic, selector, noLocal);
        _connectionId = connectionId;
        _destinations = destinations;
    }

    /**
     * Returns the identity of the connection that owns this consumer.
     *
     * @return the identity of the connection, or <code>-1</code> if this is not
     *         currently associated with a connection.
     * @see ServerConnection#getConnectionId
     */
    public long getConnectionId() {
        return _connectionId;
    }

    /**
     * Determines if this consumer can consume messages from the specified
     * destination.
     *
     * @param destination the destination
     * @return <code>true</code> if the consumer can consume messages from
     *         <code>destination</code>; otherwise <code>false</code>
     */
    public boolean canConsume(JmsDestination destination) {
        boolean result = false;
        if (destination instanceof JmsTopic) {
            JmsTopic topic = (JmsTopic) getDestination();
            if (!topic.isWildCard()) {
                result = super.canConsume(destination);
            } else {
                result = topic.match((JmsTopic) destination);
            }
        }
        return result;
    }

    /**
     * Return a delivered, but unacknowledged message to the cache.
     *
     * @param handle the handle of the message to return
     */
    public void returnMessage(MessageHandle handle) {
        addMessage(handle);
    }

    /**
     * Return the number of unsent messages in the cache for this consumer.
     *
     * @return the number of unsent messages
     */
    public int getMessageCount() {
        return _handles.size();
    }

    /**
     * This event is called when a non-persistent message is added to the
     * <code>DestinationCache</code>.
     *
     * @param handle  a handle to the message
     * @param message the added message
     * @return <code>true</code> if the listener accepted the message; otherwise
     *         <code>false</ode>
     * @throws JMSException if the listener fails to handle the message
     */
    public boolean messageAdded(MessageHandle handle, MessageImpl message)
            throws JMSException {
        boolean accepted = true;

        // if the 'noLocal' indicator is set, and the message arrived on
        // the same connection, ignore the message
        if (getNoLocal() && message.getConnectionId() == getConnectionId()) {
            accepted = false;
        } else {
            // create a message handle for this consumer
            handle = new TopicConsumerMessageHandle(handle, this);

            if (!_handles.contains(handle)) {
                // if the message is not already in the cache then add it
                addMessage(handle);
            } else {
                accepted = false;
                _log.warn("Endpoint=" + this + " already has message cached: " +
                          handle);
            }
        }
        return accepted;
    }

    /**
     * This event is called when a message is removed from the
     * <code>DestinationCache</code>.
     *
     * @param messageId the identifier of the removed message
     * @throws JMSException if the listener fails to handle the message
     */
    public void messageRemoved(String messageId) throws JMSException {
        MessageHandle handle = _handles.remove(messageId);
        if (handle != null) {
            handle.destroy();
        }
    }

    /**
     * This event is called when a persistent message is added to the
     * <code>DestinationCache</code>.
     *
     * @param handle  a handle to the added message
     * @param message the added message
     * @return <code>true</code> if the listener accepted the message;
     * @throws JMSException         if the listener fails to handle the message
     * @throws PersistenceException if there is a persistence related problem
     */
    public boolean persistentMessageAdded(MessageHandle handle,
                                          MessageImpl message)
            throws JMSException, PersistenceException {
        boolean accepted = true;

        // if the 'noLocal' indicator is set, and the message arrived on
        // the same connection, ignore the message
        if (getNoLocal() && message.getConnectionId() == getConnectionId()) {
            accepted = false;
        } else {
            // create a message handle for this consumer
            handle = new TopicConsumerMessageHandle(handle, this);
            if (isPersistent()) {
                // and make it persistent if this is a durable consumer
                handle.add();
            }

            if (!_handles.contains(handle)) {
                // if the message is not already in the cache then add it
                addMessage(handle);
            } else {
                accepted = false;
                _log.warn("Endpoint=" + this + " already has message cached: " +
                          handle);
            }
        }
        return accepted;
    }

    /**
     * This event is called when a message is removed from the
     * <code>DestinationCache</code>.
     *
     * @param messageId the identifier of the removed message
     * @throws JMSException         if the listener fails to handle the message
     * @throws PersistenceException if there is a persistence related problem
     */
    public void persistentMessageRemoved(String messageId)
            throws JMSException, PersistenceException {
        MessageHandle handle = _handles.remove(messageId);
        if (handle != null) {
            handle.destroy();
        }
    }


    /**
     * Invoked when a destination is created.
     *
     * @param destination the destination that was added
     */
    public void destinationAdded(JmsDestination destination) {
        // no-op
    }

    /**
     * Invoked when a destination is removed.
     *
     * @param destination the destination that was removed
     */
    public void destinationRemoved(JmsDestination destination) {
        // no-op
    }

    /**
     * Invoked when a message cache is created.
     *
     * @param destination the destination that messages are being cached for
     * @param cache       the corresponding cache
     */
    public void cacheAdded(JmsDestination destination,
                           DestinationCache cache) {
        if (destination instanceof JmsTopic) {
            JmsTopic myTopic = (JmsTopic) getDestination();
            JmsTopic topic = (JmsTopic) destination;
            if (myTopic.match(topic) && !_caches.containsKey(topic)) {
                _caches.put(topic, cache);
                cache.addConsumer(this);
            }
        }
    }

    /**
     * Invoked when a message cache is removed.
     *
     * @param destination the destination that messages are no longer being
     *                    cached for
     * @param cache       the corresponding cache
     */
    public void cacheRemoved(JmsDestination destination,
                             DestinationCache cache) {
        if (destination instanceof JmsTopic) {
            _caches.remove(destination);
        }
    }

    /**
     * Registers this with the associated {@link DestinationCache}s. The
     * consumer may receive messages immediately.
     *
     * @throws JMSException for any JMS error
     */
    protected void init() throws JMSException {
        JmsTopic topic = (JmsTopic) getDestination();

        // register the endpoint with the destination
        if (topic.isWildCard()) {
            // if the topic is a wild card then we need to retrieve a
            // set of matching destination caches.
            _caches = _destinations.getTopicDestinationCaches(topic);
            // for each cache register this endpoint as a consumer of
            // it's messages. Before doing so register as a destination
            // event listener with the DestinationManager
            _destinations.addDestinationEventListener(this);
            DestinationCache[] caches = getDestinationCaches();
            for (int i = 0; i < caches.length; ++i) {
                caches[i].addConsumer(this);
            }
        } else {
            // if the topic is not a wildcard then we need to get the
            // destination cache. If one does not exist then we need to
            // create it.
            DestinationCache cache = _destinations.getDestinationCache(topic);
            _caches.put(topic, cache);
            cache.addConsumer(this);
        }
    }

    /**
     * Set the connection identifier.
     *
     * @param connectionId the identity of the connection that owns this
     *                     consumer
     * @see #getConnectionId
     */
    protected void setConnectionId(long connectionId) {
        _connectionId = connectionId;
    }

    /**
     * Add the handle to the cache.
     *
     * @param handle the message handle to add
     */
    protected void addMessage(MessageHandle handle) {
        _handles.add(handle);
        notifyMessageAvailable();
    }

    /**
     * Return the next available message to the client.
     *
     * @return the next message, or <code>null</code> if none is available
     * @throws JMSException for any error
     * @param cancel
     */
    protected MessageHandle doReceive(Condition cancel) throws JMSException {
        MessageHandle result = null;
        MessageHandle handle;
        while (!cancel.get() && (handle = _handles.removeFirst()) != null) {
            if (_log.isDebugEnabled()) {
                _log.debug("doReceive() - next available=" + handle.getMessageId());
            }
            // ensure that the message still exists
            MessageImpl message = handle.getMessage();
            if (message != null) {
                if (selects(message)) {
                    // got a message which is applicable for the endpoint
                    result = handle;
                    break;
                } else {
                    // message has been filtered out so destroy the handle.
                    handle.destroy();
                }
            }
        }
        if (_log.isDebugEnabled()) {
            _log.debug("doReceive() - result=" + (result != null ? result.getMessageId() : null));
        }
        return result;
    }

    /**
     * Closes this endpoint.
     */
    protected void doClose() {
        // unregister as a destination event listener
        _destinations.removeDestinationEventListener(this);

        // unregister from the destination before continuing
        DestinationCache[] caches = getDestinationCaches();
        for (int i = 0; i < caches.length; ++i) {
            caches[i].removeConsumer(this);
        }
        _caches.clear();

        if (!isPersistent()) {
            // for non-persistent consumers, destroy all outstanding message
            // handles
            MessageHandle[] handles = _handles.toArray();
            for (int i = 0; i < handles.length; ++i) {
                MessageHandle handle = handles[i];
                try {
                    handle.destroy();
                } catch (JMSException exception) {
                    _log.error(exception, exception);
                }
            }
        }
    }

    /**
     * Returns the destination manager.
     *
     * @return the destination manager
     */
    protected DestinationManager getDestinationManager() {
        return _destinations;
    }

    /**
     * Returns the destination caches.
     *
     * @return the destination caches
     */
    protected DestinationCache[] getDestinationCaches() {
        return (DestinationCache[]) _caches.values().toArray(
                new DestinationCache[0]);
    }

}
