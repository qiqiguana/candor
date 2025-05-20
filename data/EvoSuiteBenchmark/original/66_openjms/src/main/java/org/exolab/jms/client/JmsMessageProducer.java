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
 * Copyright 2000-2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: JmsMessageProducer.java,v 1.2 2005/03/18 03:36:37 tanderson Exp $
 */
package org.exolab.jms.client;

import java.util.Date;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageFormatException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.exolab.jms.message.MessageId;


/**
 * Client implementation of the <code>javax.jms.MessageProducer</code>
 * interface
 *
 * @version     $Revision: 1.2 $ $Date: 2005/03/18 03:36:37 $
 * @author      <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
class JmsMessageProducer implements MessageProducer {

    /**
     * The destination that messages are delivered to, or <code>null</code>
     * if this is an unidentified producer.
     */
    private final Destination _destination;

    /**
     * The default priority for messages.
     */
    private int _defaultPriority = Message.DEFAULT_PRIORITY;

    /**
     * The default time to live for messages.
     */
    private long _defaultTtl = 0;

    /**
     * The default delivery mode for messages.
     */
    private int _deliveryMode = DeliveryMode.PERSISTENT;

    /**
     * This flag is used to indicate whether timestamps are enabled or
     * disabled.
     */
    private boolean _disableTimestamp = false;

    /**
     * This flag is used to indicate whether message ids are enabled or
     * disabled.
     */
    private boolean _disableMessageId = false;

    /**
     * The session that created this producer.
     */
    private JmsSession _session = null;


    /**
     * Construct a new <code>JmsMessageProducer</code>.
     *
     * @param session session responsible for this producer
     * @param destination   the destination that messages are delivered to,
     * or <code>null</code> if this is an unidentified producer
     */
    public JmsMessageProducer(JmsSession session, Destination destination) {
        if (session == null) {
            throw new IllegalArgumentException("Argument 'session' is null");
        }
        _session = session;
        _destination = destination;
    }

    /**
     * Gets the destination associated with this <code>MessageProducer</code>.
     *
     * @return this producer's <code>Destination/<code>
     */
    public Destination getDestination() {
        return _destination;
    }

    /**
     * Set whether message IDs are disabled.
     *
     * @param value indicates if message IDs are disabled
     */
    public void setDisableMessageID(boolean value) {
        _disableMessageId = value;
    }

    /**
     * Returns if message IDs are disabled
     *
     * @return <code>true</code> if message IDs are disabled
     */
    public boolean getDisableMessageID() {
        return _disableMessageId;
    }

    /**
     * Set whether message timestamps are disabled.
     *
     * @param value indicates if message timestamps are disabled
     */
    public void setDisableMessageTimestamp(boolean value) {
        _disableTimestamp = value;
    }

    /**
     * Returns if message timestamps are disabled.
     *
     * @return <code>true</code> if message timestamps are disabled
     */
    public boolean getDisableMessageTimestamp() {
        return _disableTimestamp;
    }

    /**
     * Set the producer's default delivery mode.
     *
     * @param deliveryMode the delivery mode. Legal values are
     * <code>DeliveryMode.NON_PERSISTENT</code> or
     * <code>DeliveryMode.PERSISTENT</code>
     */
    public void setDeliveryMode(int deliveryMode) {
        _deliveryMode = deliveryMode;
    }

    /**
     * Returns the producer's default delivery mode.
     *
     * @return the default delivery mode
     */
    public int getDeliveryMode() {
        return _deliveryMode;
    }

    /**
     * Set the producer's default priority.
     *
     * @param priority the priority. Must be a value between 0 and 9
     */
    public void setPriority(int priority) {
        _defaultPriority = priority;
    }

    /**
     * Returns the producer's default delivery mode.
     *
     * @return the default delivery mode
     */
    public int getPriority() {
        return _defaultPriority;
    }

    /**
     * Set the default time to live for messages.
     *
     * @param timeToLive the message time to live in milliseconds; zero is
     * unlimited
     */
    public void setTimeToLive(long timeToLive) {
        _defaultTtl = timeToLive;
    }

    /**
     * Returns the default time to live for messages.
     *
     * @return the default message time to live in milliseconds; zero is
     * unlimited
     */
    public long getTimeToLive() {
        return _defaultTtl;
    }

    /**
     * Sends a message using the <code>MessageProducer</code>'s default delivery
     * mode, priority, and time to live.
     *
     * @param message the message to send
     * @throws JMSException                  if the JMS provider fails to send
     *                                       the message due to some internal
     *                                       error.
     * @throws MessageFormatException        if an invalid message is
     *                                       specified.
     * @throws InvalidDestinationException   if a client uses this method with a
     *                                       <code>MessageProducer</code> with
     *                                       an invalid destination.
     * @throws UnsupportedOperationException if a client uses this method with a
     *                                       <code>MessageProducer</code> that
     *                                       did not specify a destination at
     *                                       creation time.
     */
    public void send(Message message) throws JMSException {
        send(getDestination(), message, getDeliveryMode(), getPriority(),
             getTimeToLive());
    }

    /**
     * Sends a message to the destination, specifying delivery mode, priority,
     * and time to live.
     *
     * @param message      the message to send
     * @param deliveryMode the delivery mode to use
     * @param priority     the priority for this message
     * @param timeToLive   the message's lifetime (in milliseconds)
     * @throws JMSException                  if the JMS provider fails to send
     *                                       the message due to some internal
     *                                       error.
     * @throws MessageFormatException        if an invalid message is
     *                                       specified.
     * @throws InvalidDestinationException   if a client uses this method with a
     *                                       <code>MessageProducer</code> with
     *                                       an invalid destination.
     * @throws UnsupportedOperationException if a client uses this method with a
     *                                       <code>MessageProducer</code> that
     *                                       did not specify a destination at
     *                                       creation time.
     */
    public void send(Message message, int deliveryMode, int priority,
                     long timeToLive) throws JMSException {
        send(getDestination(), message, deliveryMode, priority, timeToLive);
    }

    /**
     * Sends a message to a destination for an unidentified message producer.
     * Uses the <code>MessageProducer</code>'s default delivery mode, priority,
     * and time to live.
     * <p/>
     * <P>Typically, a message producer is assigned a destination at creation
     * time; however, the JMS API also supports unidentified message producers,
     * which require that the destination be supplied every time a message is
     * sent.
     *
     * @param destination the destination to send this message to
     * @param message     the message to send
     * @throws JMSException                  if the JMS provider fails to send
     *                                       the message due to some internal
     *                                       error.
     * @throws MessageFormatException        if an invalid message is
     *                                       specified.
     * @throws InvalidDestinationException   if a client uses this method with
     *                                       an invalid destination.
     * @throws UnsupportedOperationException if a client uses this method with a
     *                                       <code>MessageProducer</code> that
     *                                       specified a destination at creation
     *                                       time.
     */
    public void send(Destination destination, Message message)
            throws JMSException {
        send(destination, message, getDeliveryMode(), getPriority(),
             getTimeToLive());
    }

    /**
     * Sends a message to a destination for an unidentified message producer,
     * specifying delivery mode, priority and time to live.
     * <p/>
     * <P>Typically, a message producer is assigned a destination at creation
     * time; however, the JMS API also supports unidentified message producers,
     * which require that the destination be supplied every time a message is
     * sent.
     *
     * @param destination  the destination to send this message to
     * @param message      the message to send
     * @param deliveryMode the delivery mode to use
     * @param priority     the priority for this message
     * @param timeToLive   the message's lifetime (in milliseconds)
     * @throws JMSException                if the JMS provider fails to send the
     *                                     message due to some internal error.
     * @throws MessageFormatException      if an invalid message is specified.
     * @throws InvalidDestinationException if a client uses this method with an
     *                                     invalid destination.
     */
    public void send(Destination destination, Message message,
                     int deliveryMode, int priority, long timeToLive)
            throws JMSException {

        if (!(destination instanceof JmsDestination)) {
            // don't support non-OpenJMS or null destinations
            throw new InvalidDestinationException(
                "Invalid destination: " + destination);
        }
        if (message == null) {
            throw new MessageFormatException("Null message");
        }

        message.setJMSMessageID(MessageId.create());
        message.setJMSDestination(destination);
        message.setJMSTimestamp((new Date()).getTime());
        message.setJMSPriority(priority);

        if (timeToLive > 0) {
            message.setJMSExpiration(System.currentTimeMillis() + timeToLive);
        } else {
            message.setJMSExpiration(0);
        }

        // if the destination is a temporary one, override the delivery
        // mode to NON_PERSISTENT
        if (destination instanceof JmsTemporaryDestination) {
            message.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
        } else {
            message.setJMSDeliveryMode(deliveryMode);
        }

        _session.sendMessage(message);
    }

    /**
     * Close the producer.
     *
     * @throws JMSException if the producer can't be closed
     */
    public synchronized void close() throws JMSException {
        if (_session != null) {
            _session.removeProducer(this);
        }
        _session = null;
    }

}
