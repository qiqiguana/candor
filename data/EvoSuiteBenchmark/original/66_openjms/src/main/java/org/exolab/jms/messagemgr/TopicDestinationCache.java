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
 * $Id: TopicDestinationCache.java,v 1.6 2005/12/20 20:39:45 tanderson Exp $
 */
package org.exolab.jms.messagemgr;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.jms.JMSException;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsTopic;
import org.exolab.jms.lease.LeaseManager;
import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.persistence.PersistenceException;


/**
 * A {@link DestinationCache} for topics.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.6 $ $Date: 2005/12/20 20:39:45 $
 */
class TopicDestinationCache extends AbstractDestinationCache {

    /**
     * Construct a new <code>TopicDestinationCache</code>.
     *
     * @param topic    the topic to cache messages for
     * @param database the database service
     * @param leases   the lease manager
     */
    public TopicDestinationCache(JmsTopic topic, DatabaseService database,
                                 LeaseManager leases) {
        super(topic, database, leases);
    }

    /**
     * Register a consumer with this cache.
     *
     * @param consumer the message consumer for this destination
     * @return <code>true</code> if registered; otherwise <code>false</code>
     */
    public boolean addConsumer(ConsumerEndpoint consumer) {

        boolean result = false;

        // check to see that the consumer can actually subscribe to
        // this destination
        JmsTopic cdest = (JmsTopic) consumer.getDestination();
        JmsTopic ddest = (JmsTopic) getDestination();

        if (cdest.match(ddest)) {
            result = super.addConsumer(consumer);
        }

        return result;
    }

    /**
     * Invoked when the {@link MessageMgr} receives a non-persistent message.
     *
     * @param destination the message's destination
     * @param message     the message
     * @throws JMSException if the listener fails to handle the message
     */
    public void messageAdded(JmsDestination destination, MessageImpl message)
            throws JMSException {
        boolean processed = false;
        MessageRef reference =
                new CachedMessageRef(message, false, getMessageCache());

        reference.reference(); // temporary reference to ensure the message has
                               // a non-zero reference while passing it to each
                               // of the consumers, to avoid premature
                               // destruction
        addMessage(reference, message);
        MessageHandle handle = new SharedMessageHandle(this, reference,
                                                       message);

        ConsumerEndpoint[] consumers = getConsumerArray();
        for (int index = 0; index < consumers.length; index++) {
            ConsumerEndpoint consumer = consumers[index];
            processed |= consumer.messageAdded(handle, message);
        }

        // create a lease iff one is required and the message has actually
        // been accepted by at least one endpoint
        if (processed) {
            checkMessageExpiry(reference, message);
            reference.dereference(); // remove temporary reference
        } else {
            // no consumer picked up the message, so toss it
            reference.destroy();
            // @todo - inefficient. Don't really want to add the message
            // just to remove it again if there are no consumers for it
        }
    }

    /**
     * Invoked when the {@link MessageMgr} receives a persistent message.
     *
     * @param destination the message's destination
     * @param message     the message
     * @throws JMSException         if the listener fails to handle the message
     * @throws PersistenceException if there is a persistence related problem
     */
    public void persistentMessageAdded(JmsDestination destination,
                                       MessageImpl message)
            throws JMSException, PersistenceException {
        boolean processed = false;
        MessageRef reference = new CachedMessageRef(message, true,
                                                    getMessageCache());
        reference.reference(); // temporary reference to ensure the message has
                               // a non-zero reference while passing it to each
                               // of the consumers, to avoid premature
                               // destruction
        addMessage(reference, message);
        SharedMessageHandle handle = new SharedMessageHandle(this, reference,
                                                             message);

        // now send the message to all active consumers
        ConsumerEndpoint[] consumers = getConsumerArray();
        for (int index = 0; index < consumers.length; index++) {
            ConsumerEndpoint consumer = consumers[index];
            processed |= consumer.persistentMessageAdded(handle, message);
        }

        // for each inactive durable consumer, add a persistent handle
        // @todo - possible race condition between inactive subscription
        // becoming active again - potential for message loss?
/*
        JmsTopic topic = (JmsTopic) getDestination();
        List inactive = _consumers.getInactiveSubscriptions(
                topic);
        if (!inactive.isEmpty()) {
            Iterator iterator = inactive.iterator();
            while (iterator.hasNext()) {
                String name = (String) iterator.next();
                TopicConsumerMessageHandle durable
                        = new TopicConsumerMessageHandle(handle, name);
                durable.add(connection);
            }
            processed = true;
        }
*/

        // create a lease iff one is required and the message has actually
        // been accepted by at least one endpoint
        if (processed) {
            checkMessageExpiry(reference, message);
            reference.dereference(); // remove temporary reference
        } else {
            // no consumer picked up the message, so toss it
            handle.destroy();
            // @todo - inefficient. Don't really want to make the message
            // persistent, just to remove it again if there are no consumers
            // for it
        }

    }

    /**
     * Return a message handle back to the cache, to recover unsent or
     * unacknowledged messages.
     *
     * @param handle the message handle to return
     */
    public void returnMessageHandle(MessageHandle handle) {
        long consumerId = handle.getConsumerId();
        AbstractTopicConsumerEndpoint endpoint =
                (AbstractTopicConsumerEndpoint) getConsumerEndpoint(consumerId);
        // if the endpoint is still active then return the message
        // back to it
        if (endpoint != null) {
            endpoint.returnMessage(handle);
        } else {
            // @todo - need to destroy the handle?
        }
    }

    /**
     * Load the state of a durable consumer.
     *
     * @param name       the durable subscription name
     * @return a list of {@link MessageHandle} instances
     * @throws JMSException         for any JMS error
     */
    public List getDurableMessageHandles(String name)
            throws JMSException, PersistenceException {
        DatabaseService service = DatabaseService.getInstance();
        Connection connection = service.getConnection();
        Vector handles = service.getAdapter().getMessageHandles(
                connection, getDestination(), name);
        List result = new ArrayList(handles.size());

        MessageCache cache = getMessageCache();

        Iterator iterator = handles.iterator();
        while (iterator.hasNext()) {
            PersistentMessageHandle handle =
                    (PersistentMessageHandle) iterator.next();
            String messageId = handle.getMessageId();
            MessageRef reference = cache.getMessageRef(messageId);
            if (reference == null) {
                reference = new CachedMessageRef(messageId, true, cache);
            }
            cache.addMessageRef(reference);
            handle.reference(reference);
            handle.setDestinationCache(this);
            result.add(handle);

            checkMessageExpiry(reference, handle.getExpiryTime());
        }
        return result;
    }

    /**
     * Remove an expired persistent message, and notify any listeners.
     *
     * @param reference  a handle to the expired message
     * @throws JMSException         if a listener fails to handle the
     *                              expiration
     * @throws PersistenceException if there is a persistence related problem
     */
    protected void persistentMessageExpired(MessageRef reference)
            throws JMSException, PersistenceException {
        String messageId = reference.getMessageId();
        ConsumerEndpoint[] consumers = getConsumerArray();

        for (int i = 0; i < consumers.length; ++i) {
            consumers[i].persistentMessageRemoved(messageId);
        }
    }

}

