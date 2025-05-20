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
 * $Id: ConsumerManager.java,v 1.4 2005/11/12 12:42:54 tanderson Exp $
 */
package org.exolab.jms.messagemgr;

import javax.jms.InvalidDestinationException;
import javax.jms.InvalidSelectorException;
import javax.jms.JMSException;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsQueue;
import org.exolab.jms.client.JmsTopic;


/**
 * <code>ConsumerManager</code> is responsible for creating and managing the
 * lifecycle of consumers.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2005/11/12 12:42:54 $
 */
public interface ConsumerManager {

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
    void subscribe(JmsTopic topic, String name, String clientID)
            throws JMSException;

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
    void unsubscribe(String name, String clientID) throws JMSException;

    /**
     * Remove all durable subscriptions for a destination.
     * <p/>
     * Subscriptions may only be removed if the associated {@link
     * ConsumerEndpoint}s are inactive.
     *
     * @param topic the topic to remove consumers for
     * @throws JMSException if the subscriptions can't be removed
     */
    void unsubscribe(JmsTopic topic) throws JMSException;

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
     * @throws InvalidSelectorException if the selector is not well formed
     * @throws JMSException             if the consumer can't be created
     */
    ConsumerEndpoint createConsumer(JmsDestination destination,
                                    long connectionId, String selector,
                                    boolean noLocal)
            throws JMSException;

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
    DurableConsumerEndpoint createDurableConsumer(JmsTopic topic, String name,
                                                  String clientID,
                                                  long connectionId,
                                                  boolean noLocal,
                                                  String selector)
            throws JMSException;

    /**
     * Create a new queue browser.
     *
     * @param queue    the queue to browse
     * @param selector the message selector. May be <code>null</code>
     * @return the queue browser endpoint
     * @throws InvalidSelectorException if the selector is not well formed
     * @throws JMSException             if the browser can't be created
     */
    ConsumerEndpoint createQueueBrowser(JmsQueue queue, String selector)
            throws JMSException;

    /**
     * Close a consumer.
     *
     * @param consumer the consumer to close
     */
    void closeConsumer(ConsumerEndpoint consumer);

    /**
     * Return the consumer with the specified identity.
     *
     * @param consumerId the identity of the consumer
     * @return the associated consumer, or <code>null</code> if none exists
     */
    ConsumerEndpoint getConsumerEndpoint(long consumerId);

    /**
     * Return the consumer with the specified persistent identity.
     *
     * @param persistentId the persistent identity of the consumer
     * @return the associated consumer, or <code>null</code> if none exists
     */
    ConsumerEndpoint getConsumerEndpoint(String persistentId);

    /**
     * Determines if there are any active consumers for a destination.
     *
     * @param destination the destination
     * @return <code>true</code> if there is at least one consumer
     */
    boolean hasActiveConsumers(JmsDestination destination);

}
