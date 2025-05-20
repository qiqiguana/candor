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
 */
package org.exolab.jms.messagemgr;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.QueueBrowser;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.selector.Selector;
import org.exolab.jms.message.MessageImpl;


/**
 * <code>ConsumerEndpoint</code> represents the server-side view of of the
 * {@link MessageConsumer} and {@link QueueBrowser} interfaces
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/08/30 05:30:52 $
 */
public interface ConsumerEndpoint
        extends DestinationCacheEventListener {

    /**
     * Returns the identity of this consumer.
     *
     * @return the identity of this consumer
     */
    long getId();

    /**
     * Determines if this is a persistent or non-persistent consumer.
     * <p/>
     * If persistent, then the consumer is persistent accross subscriptions and
     * server restarts, and {@link #getPersistentId} returns a non-null value
     *
     * @return <code>true</code> if this is a persistent consumer; otherwise
     *         <code>false</code>
     */
    boolean isPersistent();

    /**
     * Returns the persistent identifier for this consumer. This is the identity
     * of the consumer which is persistent across subscriptions and server
     * restarts.
     *
     * @return the persistent identifier for this consumer, or <code>null</code>
     *         if this is a transient consumer
     */
    String getPersistentId();

    /**
     * Return the destination that this consumer is accessing.
     *
     * @return the destination that this consumer is accessing
     */
    JmsDestination getDestination();

    /**
     * Determines if this consumer can consume messages from the specified
     * destination.
     *
     * @param destination the destination
     * @return <code>true</code> if the consumer can consume messages from
     *         <code>destination</code>; otherwise <code>false</code>
     */
    boolean canConsume(JmsDestination destination);

    /**
     * Returns the message selector.
     *
     * @return the message selector, or <code>null</code> if none was specified
     *         by the client
     */
    Selector getSelector();

    /**
     * Determines if a message is selected by the consumer.
     *
     * @param message the message to check
     * @return <code>true</code> if the message is selected; otherwise
     *         <code>false</code>
     */
    boolean selects(MessageImpl message);

    /**
     * Returns if locally produced messages are being inhibited.
     *
     * @return <code>true</code> if locally published messages are being
     *         inhibited.
     */
    boolean getNoLocal();

    /**
     * Return the next available message to the client.
     *
     * @param cancel if set, indictates to cancel the receive
     * @return the next message, or <code>null</code> if none is available
     * @throws JMSException for any error
     */
    MessageHandle receive(Condition cancel) throws JMSException;

    /**
     * Indicates if this is an asynchronous consumer.
     * <p/>
     * An asynchronous consumer has a client <code>MessageConsumer</code> with
     * an associated <code>MessageListener</code>.
     *
     * @param asynchronous if <code>true</code> marks this as an asynchronous
     *                     consumer
     */
    void setAsynchronous(boolean asynchronous);

    /**
     * Determines if this is an asynchronous consumer.
     *
     * @return <code>true</code> if this is an asynchronous consumer; otherwise
     *         <code>false</code>
     */
    boolean isAsynchronous();

    /**
     * Indicates that the client is currently waiting for a message.
     *
     * @param condition the condition to evaluate to determine if the client is
     * waiting for message. May be <code>null</code>.
     */
    void setWaitingForMessage(Condition condition);

    /**
     * Determines if the client is currently waiting for a message.
     *
     * @return <code>true</code> if the client is waiting for messages;
     * otherwise <code>false</code>
     */
    boolean isWaitingForMessage();

    /**
     * Set the listener for this consumer. If a listener is set, it is notified
     * when messages become available.
     *
     * @param listener the listener to add, or <code>null</code> to remove an
     *                 existing listener
     */
    void setListener(ConsumerEndpointListener listener);

    /**
     * Returns the number of unsent messages in the cache.
     *
     * @return the number of unsent messages
     */
    int getMessageCount();

    /**
     * Determines if this consumer is closed, or in the process of being
     * closed.
     *
     * @return <code>true</code> if this consumer is closed; otherwise
     *         <code>false</code>
     */
    boolean isClosed();

    /**
     * Close and release any resource allocated to this endpoint.
     */
    void close();

}
