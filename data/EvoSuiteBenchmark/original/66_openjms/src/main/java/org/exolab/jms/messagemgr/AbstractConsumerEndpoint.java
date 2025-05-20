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

import javax.jms.InvalidSelectorException;
import javax.jms.JMSException;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.selector.Selector;


/**
 * Abstract implementation of the {@link ConsumerEndpoint} interface.
 *
 * @author <a href="mailto:jima@exoffice.com">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/08/30 06:25:47 $
 */
public abstract class AbstractConsumerEndpoint implements ConsumerEndpoint {

    /**
     * The identity of this consumer.
     */
    private final long _id;

    /**
     * The destination the consumer is acceesing.
     */
    private final JmsDestination _destination;

    /**
     * The message selector associated with this consumer. May be
     * <code>null</code>.
     */
    private Selector _selector;

    /**
     * If true, and the destination is a topic, inhibits the delivery of
     * messages published by its own connection.
     */
    private boolean _noLocal;

    /**
     * Determines if this consumer is asynchronous.
     */
    private boolean _asynchronous = false;

    /**
     * The receive timeout, if the client is performing a blocking receive. A
     * value of <code>0</code> indicates the client is blocking indefinitely.
     */
    private Condition _waitingForMessage;

    /**
     * The listener to notify when a message is available.
     */
    private ConsumerEndpointListener _listener = null;

    /**
     * Determines if this is (or is in the process of being) closed.
     */
    private final Flag _closed = new Flag(false);


    /**
     * Construct a new <code>ConsumerEndpoint</code>.
     * <p/>
     * The destination and selector determine where it will be sourcing its
     * messages from, and scheduler is used to asynchronously deliver messages
     * to the consumer.
     *
     * @param consumerId  the identity of this consumer
     * @param destination the destination to access
     * @param selector    the message selector. May be <code>null</code>
     * @param noLocal     if true, and the destination is a topic, inhibits the
     *                    delivery of messages published by its own connection.
     * @throws InvalidSelectorException if the selector is not well formed
     */
    public AbstractConsumerEndpoint(long consumerId, JmsDestination destination,
                                    String selector, boolean noLocal)
            throws InvalidSelectorException {
        if (destination == null) {
            throw new IllegalArgumentException(
                    "Argument 'destination' is null");
        }
        _id = consumerId;
        _destination = destination;
        setSelector(selector);
        _noLocal = noLocal;
    }

    /**
     * Returns the identity of this consumer.
     *
     * @return the identity of this consumer
     */
    public long getId() {
        return _id;
    }

    /**
     * Determines if this is a persistent or non-persistent consumer.
     * <p/>
     * If persistent, then the consumer is persistent accross subscriptions and
     * server restarts, and {@link #getPersistentId} returns a non-null value.
     *
     * @return <code>false</code>
     */
    public boolean isPersistent() {
        return false;
    }

    /**
     * Returns the persistent identifier for this consumer. This is the identity
     * of the consumer which is persistent across subscriptions and server
     * restarts.
     *
     * @return <code>null</code>
     */
    public String getPersistentId() {
        return null;
    }

    /**
     * Return the destination that this consumer is accessing.
     *
     * @return the destination that this consumer is accessing
     */
    public JmsDestination getDestination() {
        return _destination;
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
        return _destination.equals(destination);
    }

    /**
     * Returns the message selector.
     *
     * @return the message selector, or <code>null</code> if none was specified
     *         by the client
     */
    public Selector getSelector() {
        return _selector;
    }

    /**
     * Determines if a message is selected by the consumer.
     *
     * @param message the message to check
     * @return <code>true</code> if the message is selected; otherwise
     *         <code>false</code>
     */
    public boolean selects(MessageImpl message) {
        return (_selector == null || _selector.selects(message));
    }

    /**
     * Returns if locally produced messages are being inhibited.
     *
     * @return <code>true</code> if locally published messages are being
     *         inhibited.
     */
    public boolean getNoLocal() {
        return _noLocal;
    }

    /**
     * Return the next available message to the client.
     *
     * @param cancel
     * @return the next message, or <code>null</code> if none is available
     * @throws JMSException for any error
     */
    public final synchronized MessageHandle receive(final Condition cancel)
            throws JMSException {
        MessageHandle result = null;
        if (!_closed.get()) {
            Condition condition = new Condition() {
                public boolean get() {
                    return _closed.get() || cancel.get();
                }
            };
            result = doReceive(condition);
        }
        return result;
    }

    /**
     * Indicates if this is an asynchronous consumer.
     * <p/>
     * An asynchronous consumer has a client <code>MessageConsumer</code> with
     * an associated <code>MessageListener</code>.
     *
     * @param asynchronous if <code>true</code> marks this as an asynchronous
     *                     consumer
     */
    public synchronized void setAsynchronous(boolean asynchronous) {
        _asynchronous = asynchronous;
    }

    /**
     * Determines if this is an asynchronous consumer.
     *
     * @return <code>true</code> if this is an asynchronous consumer; otherwise
     *         <code>false</code>
     */
    public synchronized boolean isAsynchronous() {
        return _asynchronous;
    }

    /**
     * Indicates that the client is currently waiting for a message.
     *
     * @param condition the condition to evaluate to determine if the client is
     *                  waiting for message. May be <code>null</code>.
     */
    public synchronized void setWaitingForMessage(Condition condition) {
        _waitingForMessage = condition;
    }

    /**
     * Determines if the client is currently waiting for a message.
     *
     * @return <code>true</code> if the client is waiting for messages;
     *         otherwise <code>false</code>
     */
    public synchronized boolean isWaitingForMessage() {
        return _waitingForMessage != null && _waitingForMessage.get();
    }

    /**
     * Set the listener for this consumer. If a listener is set, it is notified
     * when messages become available.
     *
     * @param listener the listener to add, or <code>null</code> to remove an
     *                 existing listener
     */
    public synchronized void setListener(ConsumerEndpointListener listener) {
        _listener = listener;
    }

    /**
     * Determines if this consumer is closed, or in the process of being
     * closed.
     *
     * @return <code>true</code> if this consumer is closed; otherwise
     *         <code>false</code>
     */
    public final boolean isClosed() {
        return _closed.get();
    }

    /**
     * Close this endpoint.
     */
    public final void close() {
        _closed.set(true);
        synchronized (this) {
            _listener = null;
            doClose();
        }
    }

    /**
     * Returns a stringified version of the consumer.
     *
     * @return a stringified version of the consumer
     */
    public String toString() {
        return _id + ":" + getDestination();
    }

    /**
     * Return the next available message to the client.
     * <p/>
     * This method will not be invoked if the consumer is being closed, however
     * it is possible for {@link #close()} to be invoked while this method is in
     * progress. Implementations should therefore invoke isClosed() to determine
     * if the consumer is in the process of being closed, and if so, return
     * <code>null</code>.
     *
     * @param cancel
     * @return the next message, or <code>null</code> if none is available
     * @throws JMSException for any error
     */
    protected abstract MessageHandle doReceive(Condition cancel)
            throws JMSException;

    /**
     * Closes the endpoint.
     */
    protected abstract void doClose();

    /**
     * Notify the listener that a message is available for this consumer.
     */
    protected synchronized void notifyMessageAvailable() {
        if (_listener != null && !_closed.get()) {
            _listener.messageAvailable(this);
        }
    }

    /**
     * Sets the message selector.
     *
     * @param selector the message selector. May be <code>null</code>
     * @throws InvalidSelectorException if the selector is not well formed
     */
    protected void setSelector(String selector)
            throws InvalidSelectorException {
        _selector = (selector != null) ? new Selector(selector) : null;
    }

    /**
     * Determines if locally produced messages are being inhibited.
     *
     * @param noLocal if <code>true</code>, locally published messages are
     *                inhibited.
     */
    protected void setNoLocal(boolean noLocal) {
        _noLocal = noLocal;
    }

}
