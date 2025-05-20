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
 * $Id: MessageHeader.java,v 1.2 2005/03/18 03:50:12 tanderson Exp $
 */
package org.exolab.jms.message;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.jms.Destination;
import javax.jms.JMSException;


/**
 * This class implements message header fields for messages.
 *
 * @author <a href="mailto:mourikis@exolab.org">Jim Mourikis</a>
 * @version $Revision: 1.2 $ $Date: 2005/03/18 03:50:12 $
 * @see javax.jms.Message
 */
class MessageHeader implements Externalizable, Cloneable {

    static final long serialVersionUID = 1;

    private DestinationImpl _replyTo = null;
    private Timestamp _timestamp = null;
    private CorrelationId _correlationId = null;
    private boolean _redelivered = false;
    private long _expiration = 0;
    private Priority _priority = null;
    private Type _type = null;
    private DestinationImpl _destination = null;
    private DeliveryModeImpl _mode = null;


    /**
     * A message identity the uniquely identifies the message. This is assigned
     * when the message is sent.
     */
    private MessageId _id = null;

    /**
     * The identity of the message when it was received, used for message
     * acknowledgement. This is required as <code>_id</code> changes when a
     * message is republished, but the original identifier of the message must
     * be used to acknowledge the message.
     */
    private transient String _ackId;

    /**
     * if this message is being delivered as a result of a wildcard, this will
     * contain the original wildcard name that the consumer subscribed with.
     */
    private String _wildcard = null;

    /**
     * Field kept for serialization compatibility
     * @deprecated
     */
    private String _unused = null;

    /**
     * The unique id of the consumer endpoint that sent this message. This is
     * used for sending back acks etc.
     */
    private long _consumerId;


    public MessageHeader() {
    }

    /**
     * Clone an instance of this object.
     *
     * @return a copy of this object
     * @throws CloneNotSupportedException if object or attributes not cloneable
     */
    public Object clone() throws CloneNotSupportedException {
        MessageHeader result = (MessageHeader) super.clone();
        result._replyTo = _replyTo;
        result._timestamp = _timestamp;
        result._correlationId = _correlationId;
        result._priority = _priority;
        result._type = _type;
        result._destination = _destination;
        result._mode = _mode;
        result._id = _id;
        result._ackId = _ackId;
        result._wildcard = (_wildcard == null ? null : _wildcard);
        result._consumerId = _consumerId;
        return result;
    }


    // Write external interfaces called via serialisation.
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(serialVersionUID);
        out.writeObject(_replyTo);
        out.writeObject(_timestamp);
        out.writeObject(_correlationId);
        out.writeBoolean(_redelivered);
        out.writeLong(_expiration);
        out.writeObject(_priority);
        out.writeObject(_type);
        out.writeObject(_destination);
        out.writeObject(_mode);
        out.writeObject(_id);
        out.writeObject(_wildcard);
        out.writeObject(_unused);
        out.writeLong(_consumerId);
    }

    public void readExternal(ObjectInput in)
            throws IOException, ClassNotFoundException {
        long version = in.readLong();
        if (version == serialVersionUID) {
            _replyTo = (DestinationImpl) in.readObject();
            _timestamp = (Timestamp) in.readObject();
            _correlationId = (CorrelationId) in.readObject();
            _redelivered = in.readBoolean();
            _expiration = in.readLong();
            _priority = (Priority) in.readObject();
            _type = (Type) in.readObject();
            _destination = (DestinationImpl) in.readObject();
            _mode = (DeliveryModeImpl) in.readObject();
            _id = (MessageId) in.readObject();
            _wildcard = (String) in.readObject();
            _unused = (String) in.readObject();
            _consumerId = in.readLong();
        } else {
            throw new IOException("Incorrect version enountered: " +
                                  version + " This version = " +
                                  serialVersionUID);
        }
    }

    Destination getJMSReplyTo() throws JMSException {
        return _replyTo;
    }

    public void setJMSReplyTo(Destination replyTo) throws JMSException {
        if (replyTo instanceof DestinationImpl) {
            _replyTo = (DestinationImpl) replyTo;
        } else {
            throw new JMSException("Unknown Destination Type");
        }
    }

    void setJMSDestination(Destination destination) throws JMSException {
        if (destination instanceof DestinationImpl) {
            _destination = (DestinationImpl) destination;
        } else {
            throw new JMSException("Unknown Destination Type");
        }
    }

    public Destination getJMSDestination() throws JMSException {
        return _destination;
    }

    public void setJMSMessageID(String id) throws JMSException {
        if (id != null) {
            if (!id.startsWith(MessageId.PREFIX)) {
                throw new JMSException("Invalid JMSMessageID: " + id);
            }
            _id = new MessageId(id);
        } else {
            _id = null;
        }
    }

    public String getJMSMessageID() throws JMSException {
        return (_id != null) ? _id.toString() : null;
    }

    /**
     * Sets the identifier of the message for acknowledgement. This will
     * typically be the same as that returned by {@link #getJMSMessageID},
     * unless the message was republished after its receipt. If the message is
     * republished, this method will return the original message identifier,
     * whereas {@link #getJMSMessageID} will return that of the last
     * publication.
     *
     * @param id the identifier of the message for acknowledgement
     */
    public void setAckMessageID(String id) {
        _ackId = id;
    }

    /**
     * Returns the identifier of the message for acknowledgment.
     *
     * @return the identifier of the message for acknowledgment
     */
    public String getAckMessageID() {
        return _ackId;
    }

    public void setJMSTimestamp(long timestamp) throws JMSException {
        _timestamp = new Timestamp(timestamp);
    }

    public long getJMSTimestamp() throws JMSException {
        if (_timestamp != null) {
            return _timestamp.toLong();
        } else {
            throw new JMSException("No Timestamp set");
        }
    }

    public void setJMSCorrelationIDAsBytes(byte[] correlationID)
            throws JMSException {
        _correlationId = new CorrelationId(correlationID);
    }

    public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
        return (_correlationId != null ? _correlationId.getBytes() : null);
    }

    public void setJMSCorrelationID(String correlationID) throws JMSException {
        if (correlationID != null) {
            _correlationId = new CorrelationId(correlationID);
        } else {
            _correlationId = null;
        }
    }

    public String getJMSCorrelationID() throws JMSException {
        return (_correlationId != null ? _correlationId.getString() : null);
    }

    public void setJMSDeliveryMode(int mode) throws JMSException {
        _mode = new DeliveryModeImpl(mode);
    }

    public int getJMSDeliveryMode() throws JMSException {
        if (_mode != null) {
            return _mode.getDeliveryMode();
        } else {
            throw new JMSException("No Delivery Mode set");
        }
    }

    public boolean getJMSRedelivered() throws JMSException {
        return _redelivered;
    }

    public void setJMSRedelivered(boolean redelivered) throws JMSException {
        _redelivered = redelivered;
    }

    public void setJMSType(String type) throws JMSException {
        if (type != null) {
            _type = new Type(type);
        } else {
            _type = null;
        }
    }

    public String getJMSType() throws JMSException {
        return (_type != null) ? _type.getType() : null;
    }

    public void setJMSExpiration(long expiration) throws JMSException {
        _expiration = expiration;
    }

    public long getJMSExpiration() throws JMSException {
        return _expiration;
    }

    public void setJMSPriority(int priority) throws JMSException {
        _priority = new Priority(priority);
    }

    public int getJMSPriority() throws JMSException {
        if (_priority != null) {
            return _priority.getPriority();
        } else {
            return 0;
        }
    }

    /**
     * Returns the consumer identifier
     *
     * @return the consumer identifier
     */
    public long getConsumerId() {
        return _consumerId;
    }

    /**
     * Sets the consumer identifer
     *
     * @param consumerId the consumer identifier
     */
    public void setConsumerId(long consumerId) {
        _consumerId = consumerId;
    }

    /**
     * Return the message id for the object
     *
     * @return MessageId
     */
    public MessageId getMessageId() {
        return _id;
    }

    /**
     * Return the wildcard value if there is one.
     *
     * @return String The wildcard string
     */
    public String getWildcard() {
        return _wildcard;
    }

    /**
     * Set the wildcard string.
     *
     * @param wildcard The wildcard.
     */
    public void setWildcard(String wildcard) {
        _wildcard = wildcard;
    }

}
