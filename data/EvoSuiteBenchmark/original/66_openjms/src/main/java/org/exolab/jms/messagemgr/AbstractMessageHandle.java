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
 * $Id: AbstractMessageHandle.java,v 1.2 2005/08/30 07:26:49 tanderson Exp $
 */
package org.exolab.jms.messagemgr;

import javax.jms.IllegalStateException;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.server.ServerConnection;


/**
 * Abstract implementation of the {@link MessageHandle} interface.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/08/30 07:26:49 $
 */
public abstract class AbstractMessageHandle implements MessageHandle {

    /**
     * The cache that manages this handle.
     */
    private DestinationCache _cache;

    /**
     * The message reference.
     */
    private MessageRef _reference;

    /**
     * The message identifier.
     */
    private final String _messageId;

    /**
     * If <code>true</code>, indicates that the message associated with the
     * handle has been delivered, but not acknowledged.
     */
    private boolean _delivered = false;

    /**
     * The message priority.
     */
    private final int _priority;

    /**
     * The time that the message was accepted by the server, in milliseconds.
     */
    private long _acceptedTime;

    /**
     * The message sequence number, assigned by the {@link MessageMgr}, used to
     * help order the message. It also allows us to overcome the millisecond
     * resolution problem of _acceptedTime, when ordering messages
     */
    private final long _sequenceNumber;

    /**
     * The time that the message expires, in milliseconds.
     */
    private long _expiryTime;

    /**
     * The destination that this handle belongs to.
     */
    private final JmsDestination _destination;

    /**
     * The identity of the {@link ConsumerEndpoint} associated with the message,
     * or  <code>-1</code> if it isn't associated with any consumer.
     */
    private final long _consumerId;

    /**
     * The identity of the {@link ServerConnection} associated with the message,
     * or <code>-1</code> if it isn't associated with any connection.
     */
    private final long _connectionId;


    /**
     * Construct a new <code>AbstractMessageHandle</code>.
     *
     * @param cache     the destination cache that owns this
     * @param reference the reference to the message
     * @param message   the message for which the handle is created
     * @throws JMSException if the handle cannot be constructed
     */
    public AbstractMessageHandle(DestinationCache cache, MessageRef reference,
                                 MessageImpl message)
            throws JMSException {
        if (cache == null) {
            throw new IllegalArgumentException("Argument 'cache' is null");
        }
        if (reference == null) {
            throw new IllegalArgumentException("Argument 'reference' is null");
        }
        if (message == null) {
            throw new IllegalArgumentException("Argument 'message' is null");
        }
        _cache = cache;
        _messageId = message.getMessageId().getId();
        _delivered = message.getJMSRedelivered();
        _priority = message.getJMSPriority();
        _acceptedTime = message.getAcceptedTime();
        _sequenceNumber = message.getSequenceNumber();
        _expiryTime = message.getJMSExpiration();
        _destination = (JmsDestination) message.getJMSDestination();
        _consumerId = message.getConsumerId();
        _connectionId = message.getConnectionId();
        _reference = reference;
    }

    /**
     * Construct a new <code>AbstractMessageHandle</code>.
     *
     * @param messageId      the message identifier
     * @param priority       the message priority
     * @param acceptedTime   the time the message was accepted by the server
     * @param sequenceNumber the message sequence number
     * @param expiryTime     the time that the message will expire
     */
    public AbstractMessageHandle(String messageId, int priority,
                                 long acceptedTime, long sequenceNumber,
                                 long expiryTime, JmsDestination destination) {
        if (messageId == null) {
            throw new IllegalArgumentException("Argument 'messageId' is null");
        }
        if (destination == null) {
            throw new IllegalArgumentException(
                    "Argument 'destination' is null");
        }
        _messageId = messageId;
        _priority = priority;
        _acceptedTime = acceptedTime;
        _sequenceNumber = sequenceNumber;
        _expiryTime = expiryTime;
        _destination = destination;
        _consumerId = -1;
        _connectionId = -1;
    }

    /**
     * Returns the message identifier.
     *
     * @return the message identifier
     */
    public String getMessageId() {
        return _messageId;
    }

    /**
     * Returns the message associated with this handle.
     *
     * @return the associated message, or <code>null</code> if the handle is no
     *         longer valid
     * @throws JMSException for any error
     */
    public MessageImpl getMessage() throws JMSException {
        if (_reference == null) {
            throw new JMSException("Cannot get message with identifier="
                                   + _messageId + ": MessageRef null");
        }
        return _reference.getMessage();
    }


    /**
     * Indicates if a message has been delivered to a {@link MessageConsumer},
     * but not acknowledged.
     *
     * @param delivered if <code>true</code> indicates that an attempt has been
     *                  made to deliver the message
     */
    public void setDelivered(boolean delivered) {
        _delivered = delivered;
    }

    /**
     * Returns if an attempt has already been made to deliver the message.
     *
     * @return <code>true</code> if delivery has been attempted
     */
    public boolean getDelivered() {
        return _delivered;
    }

    /**
     * Returns the priority of the message.
     *
     * @return the message priority
     */
    public int getPriority() {
        return _priority;
    }

    /**
     * Returns the time that the corresponding message was accepted, in
     * milliseconds.
     *
     * @return the time that the corresponding message was accepted
     */
    public long getAcceptedTime() {
        return _acceptedTime;
    }

    /**
     * Returns the time that the message expires.
     *
     * @return the expiry time
     */
    public long getExpiryTime() {
        return _expiryTime;
    }

    /**
     * Determines if the message has expired.
     *
     * @return <code>true</code> if the message has expired, otherwise
     *         <code>false</code>
     */
    public boolean hasExpired() {
        return (_expiryTime != 0 && _expiryTime <= System.currentTimeMillis());
    }

    /**
     * Returns the handle's sequence number.
     *
     * @return the sequence number
     */
    public long getSequenceNumber() {
        return _sequenceNumber;
    }

    /**
     * Returns the message destination.
     *
     * @return the message destination
     */
    public JmsDestination getDestination() {
        return _destination;
    }

    /**
     * Returns the consumer identity associated with the message.
     *
     * @return the consumer identity associated with the message, or *
     *         <code>-1</code> if the message isn't associated with a consumer
     */
    public long getConsumerId() {
        return _consumerId;
    }

    /**
     * Returns the persistent identity of the the consumer endpoint that owns
     * this handle. If it is set, then a consumer owns it exclusively, otherwise
     * the handle may be shared across a number of consumers.
     *
     * @return <code>null</code>
     */
    public String getConsumerPersistentId() {
        return null;
    }

    /**
     * Returns the connection identity associated with this handle.
     *
     * @return the connection identity associated with this handle, or
     *         <code>-1</code> if this isn't associated with a connection
     */
    public long getConnectionId() {
        return _connectionId;
    }

    /**
     * Determines if the handle is persistent.
     *
     * @return <code>false</code>
     */
    public boolean isPersistent() {
        return false;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param object the reference object with which to compare.
     * @return <code>true</code> if <code>object</code> is a MessageHandle, and
     *         has the same {@link #getMessageId()
     */
    public boolean equals(Object object) {
        boolean result = (object instanceof MessageHandle);
        if (result) {
            result
                    = _messageId.equals(
                            ((MessageHandle) object).getMessageId());
        }
        return result;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    public int hashCode() {
        return _messageId.hashCode();
    }

    /**
     * Return a stringified version of the handle.
     *
     * @return a stringified version of the handle
     */
    public String toString() {
        return "MessageHandle : " + _priority + ":" + getAcceptedTime() +
                ":" + getSequenceNumber() + ":" + _messageId;
    }

    /**
     * Destroy this handle. If this is the last handle to reference the message,
     * also destroys the message.
     *
     * @throws JMSException for any error
     */
    public void destroy() throws JMSException {
        getMessageRef().dereference();
    }

    /**
     * Release the message handle back to the cache, to recover an unsent or
     * unacknowledged message.
     *
     * @throws JMSException for any error
     */
    public void release() throws JMSException {
        if (_cache == null) {
            throw new IllegalStateException(
                    "Can't release message: not associated with any cache");
        }
        _cache.returnMessageHandle(this);
    }

    /**
     * Returns the message reference.
     *
     * @return the message reference, or <code>null</code> if none has been set
     */
    public MessageRef getMessageRef() {
        return _reference;
    }

    /**
     * Sets the message reference.
     *
     * @param reference the reference to the message
     */
    protected void setMessageRef(MessageRef reference) {
        _reference = reference;
    }

    /**
     * Sets the destination cache.
     *
     * @param cache the destination cache
     */
    protected void setDestinationCache(DestinationCache cache) {
        _cache = cache;
    }

    /**
     * Release the message handle back to the cache, to recover an unsent or
     * unacknowledged message.
     * <p/>
     * This should be used when the parent in a chain of handles needs to be
     * released.
     *
     * @param handle the handle to release
     * @throws JMSException for any error
     */
    protected void release(MessageHandle handle) throws JMSException {
        if (_cache == null) {
            throw new IllegalStateException(
                    "Can't release message: not associated with any cache");
        }
        _cache.returnMessageHandle(handle);
    }



}
