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
 * $Id: AbstractConsumerMessageHandle.java,v 1.2 2005/08/30 07:26:49 tanderson Exp $
 */
package org.exolab.jms.messagemgr;

import java.sql.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.persistence.PersistenceException;


/**
 * A {@link MessageHandle} for a consumer.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/08/30 07:26:49 $
 */
abstract class AbstractConsumerMessageHandle implements MessageHandle {

    /**
     * The underlying handle.
     */
    private final MessageHandle _handle;

    /**
     * The consumer's identity.
     */
    private long _consumerId;

    /**
     * The consumer's persistent identity.
     */
    private final String _persistentId;

    /**
     * Detetmines if this handle is persistent.
     */
    private boolean _persistent;

    /**
     * The logger.
     */
    private static final Log _log
            = LogFactory.getLog(AbstractConsumerMessageHandle.class);


    /**
     * Construct a new <code>AbstractConsumerMessageHandle</code>.
     *
     * @param handle   the underlying handle
     * @param consumer the consumer of the handle
     * @throws JMSException if the underlying message can't be referenced
     */
    public AbstractConsumerMessageHandle(MessageHandle handle,
                                         ConsumerEndpoint consumer)
            throws JMSException {
        this(handle, consumer.getId(), consumer.getPersistentId());
    }

    /**
     * Construct a new <code>AbstractConsumerMessageHandle</code>.
     *
     * @param handle       the underlying handle
     * @param persistentId the persistent identity of the consumer. May be
     *                     <code>null</code>
     * @throws JMSException if the underlying message can't be referenced
     */
    public AbstractConsumerMessageHandle(MessageHandle handle,
                                         String persistentId)
            throws JMSException {
        this(handle, -1L, persistentId);
    }

    /**
     * Construct a new <code>AbstractConsumerMessageHandle</code>.
     *
     * @param handle       the underlying handle
     * @param consumerId   the consumer identifier
     * @param persistentId the persistent identity of the consumer. May be
     *                     <code>null</code>
     * @throws JMSException if the underlying message can't be referenced
     */
    protected AbstractConsumerMessageHandle(MessageHandle handle, long consumerId,
                                            String persistentId)
            throws JMSException {
        if (handle == null) {
            throw new IllegalArgumentException("Argument 'handle' is null");
        }
        _handle = handle;
        _consumerId = consumerId;
        _persistentId = persistentId;
        _handle.getMessageRef().reference();
    }

    /**
     * Returns the message identifier.
     *
     * @return the message identifier
     */
    public String getMessageId() {
        return _handle.getMessageId();
    }

    /**
     * Indicates if a message has been delivered to a {@link MessageConsumer},
     * but not acknowledged.
     *
     * @param delivered if <code>true</code> indicates that an attempt has been
     *                  made to deliver the message
     */
    public void setDelivered(boolean delivered) {
        _handle.setDelivered(delivered);
    }

    /**
     * Returns if an attempt has already been made to deliver the message.
     *
     * @return <code>true</code> if delivery has been attempted
     */
    public boolean getDelivered() {
        return _handle.getDelivered();
    }

    /**
     * Returns the priority of the message.
     *
     * @return the message priority
     */
    public int getPriority() {
        return _handle.getPriority();
    }

    /**
     * Returns the time that the corresponding message was accepted, in
     * milliseconds.
     *
     * @return the time that the corresponding message was accepted
     */
    public long getAcceptedTime() {
        return _handle.getAcceptedTime();
    }

    /**
     * Returns the time that the message expires.
     *
     * @return the expiry time
     */
    public long getExpiryTime() {
        return _handle.getExpiryTime();
    }

    /**
     * Determines if the message has expired.
     *
     * @return <code>true</code> if the message has expired, otherwise
     *         <code>false</code>
     */
    public boolean hasExpired() {
        return _handle.hasExpired();
    }

    /**
     * Returns the handle's sequence number.
     *
     * @return the sequence number
     */
    public long getSequenceNumber() {
        return _handle.getSequenceNumber();
    }

    /**
     * Returns the message destination.
     *
     * @return the message destination
     */
    public JmsDestination getDestination() {
        return _handle.getDestination();
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
     * Returns the name of the consumer endpoint that owns this handle. If it is
     * set, then a consumer owns it exclusively, otherwise the handle may be
     * shared across a number of consumers
     *
     * @return the consumer name, or <code>null</code>
     */
    public String getConsumerPersistentId() {
        return _persistentId;
    }

    /**
     * Returns the connection identity associated with the message.
     *
     * @return the connection identity associated with the message, or
     *         <code>-1</code> if the message isn't associated with a
     *         connection
     */
    public long getConnectionId() {
        return _handle.getConnectionId();
    }

    /**
     * Determines if the handle is persistent.
     *
     * @return <code>true</code> if the handle is persistent; otherwise
     *         <code>false</code>
     */
    public boolean isPersistent() {
        return _persistent;
    }

    /**
     * Returns the message associated with this handle.
     *
     * @return the associated message, or <code>null</code> if the handle is no
     *         longer valid
     * @throws JMSException for any error
     */
    public MessageImpl getMessage() throws JMSException {
        return _handle.getMessage();
    }

    /**
     * Makes the handle persistent.
     *
     * @throws JMSException for any persistence error
     */
    public void add() throws JMSException {
        try {
            DatabaseService service = DatabaseService.getInstance();
            Connection connection = service.getConnection();
            service.getAdapter().addMessageHandle(connection, this);
        } catch (PersistenceException exception) {
            final String msg = "Failed to make handle persistent";
            _log.error(msg, exception);
            throw new JMSException(msg + ": " + exception.getMessage());
        }
        _persistent = true;
    }

    /**
     * Update the persistent handle.
     *
     * @throws JMSException for any persistence error
     */
    public void update() throws JMSException {
        try {
            DatabaseService service = DatabaseService.getInstance();
            Connection connection = service.getConnection();
            service.getAdapter().updateMessageHandle(connection, this);
        } catch (PersistenceException exception) {
            final String msg = "Failed to update persistent handle";
            _log.error(msg, exception);
            throw new JMSException(msg + ": " + exception.getMessage());
        }
    }

    /**
     * Destroy this handle. If this is the last handle to reference the message,
     * also destroys the message
     *
     * @throws JMSException for any error
     */
    public void destroy() throws JMSException {
        if (_persistent) {
            try {
                DatabaseService service = DatabaseService.getInstance();
                Connection connection = service.getConnection();
                service.getAdapter().removeMessageHandle(connection, this);
            } catch (PersistenceException exception) {
                final String msg = "Failed to destroy persistent handle";
                _log.error(msg, exception);
                throw new JMSException(msg + ": " + exception.getMessage());
            }
        }
        _handle.destroy();
        _persistent = false;
    }

    /**
     * Release the message handle back to the cache, to recover an unsent or
     * unacknowledged message.
     *
     * @throws JMSException for any error
     */
    public void release() throws JMSException {
        if (_handle instanceof AbstractMessageHandle) {
            ((AbstractMessageHandle) _handle).release(this);
        } else {
            _handle.release();
        }
    }

    /**
     * Returns the message reference.
     *
     * @return the message reference, or <code>null</code> if none has been set
     */
    public MessageRef getMessageRef() {
        return _handle.getMessageRef();
    }

    /**
     * Set the consumer identifier.
     *
     * @param consumerId the consumer identifier
     */
    protected void setConsumerId(long consumerId) {
        _consumerId = consumerId;
    }

    /**
     * Indicates if the handle is persistent.
     *
     * @param persistent if <code>true</code> indicates the handle is
     *                   persistent
     */
    protected void setPersistent(boolean persistent) {
        _persistent = persistent;
    }

}

