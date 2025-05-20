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
 * Copyright 2000-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: JmsMessageConsumer.java,v 1.4 2007/01/24 12:00:28 tanderson Exp $
 */
package org.exolab.jms.client;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.message.MessageImpl;

import java.rmi.RemoteException;


/**
 * Client implementation of the <code>javax.jms.MessageConsumer</code>
 * interface.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2007/01/24 12:00:28 $
 */
class JmsMessageConsumer
        implements JmsMessageListener, MessageConsumer {

    /**
     * The session which created this.
     */
    private JmsSession _session = null;

    /**
     * The consumer's identity, allocated by the server.
     */
    private final long _consumerId;

    /**
     * The destination to receive messages from.
     */
    private final Destination _destination;

    /**
     * A message listener may be assigned to this session, for asynchronous
     * message delivery.
     */
    private MessageListener _listener = null;

    /**
     * The message selector, for filtering messages. May be <code>null</code>.
     */
    private String _selector = null;

    /**
     * Indicates if the session is closed.
     */
    private volatile boolean _closed = false;

    /**
     * The logger.
     */
    private static final Log _log =
            LogFactory.getLog(JmsMessageConsumer.class);


    /**
     * Construct a new <code>JmsMessageProducer</code>.
     *
     * @param session     the session responsible for the consumer
     * @param consumerId  the identity of this consumer
     * @param destination the destination to receive messages from
     * @param selector    the message selector. May be <code>null</code
     */
    public JmsMessageConsumer(JmsSession session, long consumerId,
                              Destination destination, String selector) {
        if (session == null) {
            throw new IllegalArgumentException("Argument 'session' is null");
        }
        if (destination == null) {
            throw new IllegalArgumentException(
                    "Argument 'destination' is null");
        }
        _session = session;
        _consumerId = consumerId;
        _destination = destination;
        _selector = selector;
    }

    /**
     * Return the message consumer's message selector expression.
     *
     * @return the selector expression, or <code>null</code> if one isn't set
     */
    public String getMessageSelector() {
        return _selector;
    }

    /**
     * Return the consumer's listener.
     *
     * @return the listener for the consumer, or <code>null</code> if there
     *         isn't one set
     */
    public MessageListener getMessageListener() {
        return _listener;
    }

    /**
     * Set the consumer's listener.
     *
     * @param listener the message listener, or <code>null</code> to deregister
     *                 an existing listener
     * @throws JMSException if the listener cannot be set
     */
    public void setMessageListener(MessageListener listener)
            throws JMSException {
        // if listener is not null then enable asynchronous delivery
        // otherwise disable it
        if (listener != null) {
            if (_listener == null) {
                // previously asynchronouse messaging was disabled
                _listener = listener;
                _session.setMessageListener(this);
            } else {
                // asynch message deliver is enabled, just changing the
                // client side receiving entity.
                _listener = listener;
            }
        } else {
            if (_listener != null) {
                _session.removeMessageListener(this);
                _listener = listener;
            }
        }
    }

    /**
     * Receive the next message produced for this consumer. This call blocks
     * indefinitely until a message is produced or until this message consumer
     * is closed.
     *
     * @return the next message produced for this consumer, or <code>null</code>
     *         if this consumer is concurrently closed
     * @throws JMSException if the next message can't be received
     */
    public Message receive() throws JMSException {
        return receive(0);
    }

    /**
     * Receive the next message that arrives within the specified timeout
     * interval. This call blocks until a message arrives, the timeout expires,
     * or this message consumer is closed. A timeout of zero never expires and
     * the call blocks indefinitely.
     *
     * @param timeout the timeout interval, in milliseconds
     * @return the next message produced for this consumer, or <code>null</code>
     *         if the timeout expires or the consumer concurrently closed
     * @throws JMSException if the next message can't be received
     */
    public Message receive(long timeout) throws JMSException {
        checkReceive();
        return _session.receive(_consumerId, timeout);
    }

    /**
     * Receive the next message if one is immediately available.
     *
     * @return the next message produced for this consumer, or <code>null</code>
     *         if one is not available
     * @throws JMSException if the next message can't be received
     */
    public Message receiveNoWait() throws JMSException {
        checkReceive();
        return _session.receiveNoWait(_consumerId);
    }

    /**
     * Close the consumer. This call blocks until a receive or message listener
     * in progress has completed. A blocked consumer receive call returns
     * <code>null</code> when this consumer is closed.
     *
     * @throws JMSException if this consumer can't be closed
     */
    public synchronized void close() throws JMSException {
        if (!_closed) {
            try {
                _closed = true;
                _session.removeConsumer(this);

                // wake up any blocked threads and let them complete
                notifyAll();
            } finally {
                _listener = null;
                _session = null;
                _selector = null;
            }
        }
    }

    /**
     * Deliver a message.
     *
     * @param message the message to deliver
     * @return <code>true</code> if the message was delivered; otherwise
     *         <code>false</code>.
     */
    public boolean onMessage(MessageImpl message) {
        boolean delivered = false;
        try {
            if (_listener != null) {
                _listener.onMessage(message);
                delivered = true;
            } else {
                _log.error("NessageListener no longer registered");
            }
        } catch (Throwable exception) {
            _log.error("MessageListener threw exception", exception);
        }
        return delivered;
    }

    /**
     * Informs the session that there is a message available for a synchronous
     * consumer.
     */
    public void onMessageAvailable() throws RemoteException {
        // no-op
    }

    /**
     * Returns the destination to receive messages from.
     *
     * @return the destination to receive messages from
     */
    protected Destination getDestination() {
        return _destination;
    }

    /**
     * Returns the identity of this consumer.
     *
     * @return the identity of this consumer
     */
    protected long getConsumerId() {
        return _consumerId;
    }

    /**
     * Returns the session that created this consumer.
     *
     * @return the session that created this consumer
     */
    protected JmsSession getSession() {
        return _session;
    }

    /**
     * Determines if the consumer can perform receives.
     *
     * @throws JMSException if the consumer can't perform a receive
     */
    private void checkReceive() throws JMSException {
        if (_listener != null) {
            // cannot call this method when a listener is defined
            throw new JMSException("Can't receive when listener defined");
        }

        if (_closed) {
            throw new JMSException("Can't receive when session closed");
        }
    }

}
