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
 * $Id: MessageImpl.java,v 1.2 2005/03/18 03:50:12 tanderson Exp $
 */
package org.exolab.jms.message;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Enumeration;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageNotReadableException;
import javax.jms.MessageNotWriteableException;


/**
 * This class implements the javax.jms.Message interface.
 *
 * @version     $Revision: 1.2 $ $Date: 2005/03/18 03:50:12 $
 * @author      <a href="mailto:mourikis@exolab.org">Jim Mourikis</a>
 * @see         javax.jms.Message
 */
public class MessageImpl implements
        Message, Externalizable, Cloneable {

    /**
     * Object version no. for serialization.
     */
    static final long serialVersionUID = 1;

    /**
     * This is a reference to the session that created the message. It
     * is used for acknowledgment
     */
    private MessageSessionIfc _session = null;

    /**
     * Contains the message header information as specified by the JMS
     * specifications.
     */
    private MessageHeader _messageHeader = new MessageHeader();

    /**
     * The message properties
     */
    private MessageProperties _messageProperties = new MessageProperties();

    /**
     * If true, message properties are read-only.
     */
    protected boolean _propertiesReadOnly = false;

    /**
     * If true, the message body is read-only.
     */
    protected boolean _bodyReadOnly = false;

    /**
     * The time that the message was accepted by the server.
     */
    protected long _acceptedTime;

    /**
     * The sequence number assigned to the message by server when the message
     * is accepted.
     */
    protected long _sequenceNumber;

    /**
     * The identity of the connection that this was received on.
     */
    protected transient long _connectionId;

    /**
     * This flag indicates that the message has been processed by the provider.
     */
    protected boolean _processed = false;

    /**
     * Empty byte array for initialisation purposes.
     */
    protected static final byte[] EMPTY = new byte[0];


    /**
     * Default constructor, required to support externalization.
     */
    public MessageImpl() {
    }

    /**
     * Clone an instance of this object.
     *
     * @return a new copy of this object
     * @throws CloneNotSupportedException if object or attributesare not
     * cloneable
     */
    public Object clone() throws CloneNotSupportedException {
        MessageImpl result = (MessageImpl) super.clone();
        result._messageHeader = (MessageHeader) _messageHeader.clone();
        result._messageProperties =
                (MessageProperties) _messageProperties.clone();
        return result;
    }

    // implementation of Externalizable.writeExternal
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(serialVersionUID);
        // the individual read-only states are meaningless when streaming;
        // they only affect the client when clearProperties or clearBody is
        // is invoked
        out.writeBoolean(_propertiesReadOnly || _bodyReadOnly);
        out.writeBoolean(_processed);
        out.writeLong(_acceptedTime);
        out.writeLong(_sequenceNumber);
        out.writeObject(_messageHeader);
        out.writeObject(_messageProperties);
    }

    // implementation of Externalizable.readExternal
    public void readExternal(ObjectInput in)
            throws IOException, ClassNotFoundException {
        long version = in.readLong();
        if (version == serialVersionUID) {
            boolean readOnly = in.readBoolean();
            _propertiesReadOnly = readOnly;
            _bodyReadOnly = readOnly;
            _processed = in.readBoolean();
            _acceptedTime = in.readLong();
            _sequenceNumber = in.readLong();
            _messageHeader = (MessageHeader) in.readObject();
            _messageProperties = (MessageProperties) in.readObject();
        } else {
            throw new IOException("Incorrect version enountered: " +
                                  version + ". This version = " +
                                  serialVersionUID);
        }
    }

    public void setSession(MessageSessionIfc session) {
        _session = session;
        MessageId id = _messageHeader.getMessageId();
        if (id != null) {
            _messageHeader.setAckMessageID(id.getId());
        }
    }

    public String getJMSMessageID() throws JMSException {
        return _messageHeader.getJMSMessageID();
    }

    public void setJMSMessageID(String id) throws JMSException {
        _messageHeader.setJMSMessageID(id);
    }

    /**
     * Returns the identifier of the message for acknowledgment.
     * This will typically be the same as that returned by
     * {@link #getJMSMessageID}, unless the message was republished after
     * its receipt. If the message is republished, this method will return
     * the original message identifier, whereas {@link #getJMSMessageID} will
     * return that of the last publication.
     *
     * @return the identifier of the message for acknowledgment
     */
    public String getAckMessageID() {
        return _messageHeader.getAckMessageID();
    }


    public long getJMSTimestamp() throws JMSException {
        return _messageHeader.getJMSTimestamp();
    }

    public void setJMSTimestamp(long timestamp) throws JMSException {
        _messageHeader.setJMSTimestamp(timestamp);
    }

    /**
     * Return the wildcard value if there is one.
     *
     * @return the wildcard string
     */
    public String getWildcard() {
        return _messageHeader.getWildcard();
    }

    /**
     * Return the message id
     *
     * @return MessageId
     */
    public MessageId getMessageId() {
        return _messageHeader.getMessageId();
    }

    /**
     * Set the wildcard string.
     *
     * @param wildcard The wildcard.
     */
    public void setWildcard(String wildcard) {
        _messageHeader.setWildcard(wildcard);
    }

    /**
     * Returns the value of the consumer identifier
     *
     * @return the value of the consumer identifier
     */
    public long getConsumerId() {
        return _messageHeader.getConsumerId();
    }

    /**
     * Set the value of the consumer identifer
     *
     * @param consumerId the consumer identifier
     */
    public void setConsumerId(long consumerId) {
        _messageHeader.setConsumerId(consumerId);
    }

    // Not supported
    public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
        return _messageHeader.getJMSCorrelationIDAsBytes();
    }

    // Not supported
    public void setJMSCorrelationIDAsBytes(byte[] correlationID)
            throws JMSException {
        _messageHeader.setJMSCorrelationIDAsBytes(correlationID);
    }

    public void setJMSCorrelationID(String correlationID) throws JMSException {
        _messageHeader.setJMSCorrelationID(correlationID);
    }

    public String getJMSCorrelationID() throws JMSException {
        return _messageHeader.getJMSCorrelationID();
    }

    public Destination getJMSReplyTo() throws JMSException {
        return _messageHeader.getJMSReplyTo();
    }

    public void setJMSReplyTo(Destination replyTo) throws JMSException {
        _messageHeader.setJMSReplyTo(replyTo);
    }

    public Destination getJMSDestination() throws JMSException {
        return _messageHeader.getJMSDestination();
    }

    public void setJMSDestination(Destination destination)
            throws JMSException {
        _messageHeader.setJMSDestination(destination);
    }

    public int getJMSDeliveryMode() throws JMSException {
        return _messageHeader.getJMSDeliveryMode();
    }

    public void setJMSDeliveryMode(int deliveryMode) throws JMSException {
        _messageHeader.setJMSDeliveryMode(deliveryMode);
    }

    public boolean getJMSRedelivered() throws JMSException {
        return _messageHeader.getJMSRedelivered();
    }

    public void setJMSRedelivered(boolean redelivered) throws JMSException {
        _messageHeader.setJMSRedelivered(redelivered);
    }

    public String getJMSType() throws JMSException {
        return _messageHeader.getJMSType();
    }

    public void setJMSType(String type) throws JMSException {
        _messageHeader.setJMSType(type);
    }

    public long getJMSExpiration() throws JMSException {
        return _messageHeader.getJMSExpiration();
    }

    public void setJMSExpiration(long expiration) throws JMSException {
        _messageHeader.setJMSExpiration(expiration);
    }

    public int getJMSPriority() throws JMSException {
        return _messageHeader.getJMSPriority();
    }

    public void setJMSPriority(int priority) throws JMSException {
        _messageHeader.setJMSPriority(priority);
    }

    public void clearProperties() throws JMSException {
        _messageProperties.clearProperties();
        _propertiesReadOnly = false;
    }

    public boolean propertyExists(String name) throws JMSException {
        return _messageProperties.propertyExists(name);
    }

    public boolean getBooleanProperty(String name) throws JMSException {
        return _messageProperties.getBooleanProperty(name);
    }

    public byte getByteProperty(String name) throws JMSException {
        return _messageProperties.getByteProperty(name);
    }

    public short getShortProperty(String name) throws JMSException {
        return _messageProperties.getShortProperty(name);
    }

    public int getIntProperty(String name) throws JMSException {
        return _messageProperties.getIntProperty(name);
    }

    public long getLongProperty(String name) throws JMSException {
        return _messageProperties.getLongProperty(name);
    }

    public float getFloatProperty(String name) throws JMSException {
        return _messageProperties.getFloatProperty(name);
    }

    public double getDoubleProperty(String name) throws JMSException {
        return _messageProperties.getDoubleProperty(name);
    }

    public String getStringProperty(String name) throws JMSException {
        return _messageProperties.getStringProperty(name);
    }

    public Object getObjectProperty(String name) throws JMSException {
        return _messageProperties.getObjectProperty(name);
    }

    public Enumeration getPropertyNames() throws JMSException {
        return _messageProperties.getPropertyNames();
    }

    public void setBooleanProperty(String name, boolean value)
            throws JMSException {
        checkPropertyWrite();
        _messageProperties.setBooleanProperty(name, value);
    }

    public void setByteProperty(String name, byte value) throws JMSException {
        checkPropertyWrite();
        _messageProperties.setByteProperty(name, value);
    }

    public void setShortProperty(String name, short value)
            throws JMSException {
        checkPropertyWrite();
        _messageProperties.setShortProperty(name, value);
    }

    public void setIntProperty(String name, int value) throws JMSException {
        checkPropertyWrite();
        _messageProperties.setIntProperty(name, value);
    }

    public void setLongProperty(String name, long value) throws JMSException {
        checkPropertyWrite();
        _messageProperties.setLongProperty(name, value);
    }

    public void setFloatProperty(String name, float value)
            throws JMSException {
        checkPropertyWrite();
        _messageProperties.setFloatProperty(name, value);
    }

    public void setDoubleProperty(String name, double value)
            throws JMSException {
        checkPropertyWrite();
        _messageProperties.setDoubleProperty(name, value);
    }

    public void setStringProperty(String name, String value)
            throws JMSException {
        checkPropertyWrite();
        _messageProperties.setStringProperty(name, value);
    }

    public void setObjectProperty(String name, Object value)
            throws JMSException {
        checkPropertyWrite();
        _messageProperties.setObjectProperty(name, value);
    }

    /**
     * Acknowledge the message through the session that dispatched it.
     * Throw JMSException is there is no session attached to the message
     *
     * @throws JMSException if acknowledgement fails
     */
    public void acknowledge() throws JMSException {
        if (getAckMessageID() == null) {
            throw new JMSException(
                    "Cannot acknowledge message: no identifier");
        }
        if (_session == null) {
            throw new JMSException(
                    "Cannot acknowledge message: unknown session");
        }
        _session.acknowledgeMessage(this);
    }

    public void clearBody() throws JMSException {
        _bodyReadOnly = false;
    }

    public final void checkPropertyWrite()
            throws MessageNotWriteableException {
        if (_propertiesReadOnly) {
            throw new MessageNotWriteableException(
                    "Message in read-only mode");
        }
    }

    public final void checkWrite() throws MessageNotWriteableException {
        if (_bodyReadOnly) {
            throw new MessageNotWriteableException(
                    "Message in read-only mode");
        }
    }

    public final void checkRead() throws MessageNotReadableException {
        if (_bodyReadOnly == false) {
            throw new MessageNotReadableException(
                    "Message in write-only mode");
        }
    }

    // implementation of Identifiable.getId()
    public String getId() {
        return _messageHeader.getMessageId().getId();
    }

    /**
     * Set the time that the message was accepted by the server. This is
     * different to the JMSTimestamp, which denotes the time that the message
     * was handed off to the provider.
     *
     * @param time the time that the message was accepted by the server
     */
    public void setAcceptedTime(long time) {
        _acceptedTime = time;
    }

    /**
     * Return the time that the messages was accepted by the server
     *
     * @return time in milliseconds
     */
    public long getAcceptedTime() {
        return _acceptedTime;
    }

    /**
     * Set the sequence number for this message. Not mandatory.
     *
     * @param seq the sequence number, which is used for ordering
     */
    public void setSequenceNumber(long seq) {
        _sequenceNumber = seq;
    }

    /**
     * Return the sequence number associated with this message
     *
     * @return the sequence number
     */
    public long getSequenceNumber() {
        return _sequenceNumber;
    }

    /**
     * Set the id of the connection that this message was received on
     *
     * @param id the connection id
     */
    public void setConnectionId(long id) {
        _connectionId = id;
    }

    /**
     * Return the id of the connection that this messaged was received on
     *
     * @return the connection id
     */
    public long getConnectionId() {
        return _connectionId;
    }

    /**
     * Set the processed state of the message
     *
     * @param state true if message has been processed by provider
     */
    public void setProcessed(boolean state) {
        _processed = state;
    }

    /**
     * Check whether the message has been processed
     *
     * @return true if the message has been processed
     */
    public boolean getProcessed() {
        return _processed;
    }

    /**
     * Set the read-only state of the message
     *
     * @param readOnly if true, make the message body and properties read-only
     * @throws JMSException if the read-only state cannot be changed
     */
    public void setReadOnly(boolean readOnly) throws JMSException {
        _propertiesReadOnly = readOnly;
        _bodyReadOnly = readOnly;
    }

    /**
     * Get the read-only state of the message. Note that this only returns true
     * if both properties and body are read-only
     *
     * @return true if the message is read-only
     */
    public final boolean getReadOnly() {
        return _propertiesReadOnly && _bodyReadOnly;
    }

    /**
     * Set the JMSXRcvTimestamp property. This bypasses the read-only
     * check to avoid unwanted exceptions.
     */
    public void setJMSXRcvTimestamp(long timestamp) {
        _messageProperties.setJMSXRcvTimestamp(timestamp);
    }

}
