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
 * $Id: AbstractMessageHandler.java,v 1.2 2005/10/20 14:07:03 tanderson Exp $
 */
package org.exolab.jms.tools.migration.proxy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.persistence.SQLHelper;


/**
 * Abstract implementation of the  <code>MessageHandler</code> interface.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/10/20 14:07:03 $
 */
abstract class AbstractMessageHandler implements MessageHandler, DBConstants {

    /**
     * The destination store.
     */
    private final DestinationStore _destinations;

    /**
     * The database connection.
     */
    private Connection _connection;


    /**
     * Construct a new <code>AbstractMessageHandler</code>.
     *
     * @param destinations the destination store
     * @param connection   the database connection
     */
    public AbstractMessageHandler(DestinationStore destinations,
                                  Connection connection) {
        _destinations = destinations;
        _connection = connection;
    }

    /**
     * Add a message.
     *
     * @param message the message to add
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     */
    public void add(Message message) throws JMSException, PersistenceException {
        add(message, getType());
    }

    /**
     * Returns a message given its identifier.
     *
     * @param messageId the identifier of the message to retrieve
     * @return the message corresponding to <code>messageId</code>
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     */
    public Message get(String messageId) throws JMSException,
            PersistenceException {
        Message message = newMessage();
        get(messageId, message);
        return message;
    }

    /**
     * Returns the type of message that this handler supports.
     *
     * @return the type of message
     */
    protected abstract String getType();

    /**
     * Create a new message.
     *
     * @return a new message
     * @throws JMSException for any JMS error
     */
    protected abstract Message newMessage() throws JMSException;

    /**
     * Populate the message body.
     *
     * @param body    the message body
     * @param message the message to populate
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     */
    protected abstract void setBody(Object body, Message message)
            throws JMSException, PersistenceException;

    /**
     * Returns the body of the message.
     *
     * @param message the message
     * @return the body of the message
     * @throws JMSException for any JMS error
     */
    protected abstract Object getBody(Message message) throws JMSException;

    /**
     * Populate a message.
     *
     * @param messageId the message identifier
     * @param message   the message to populate
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     */
    protected void get(String messageId, Message message)
            throws JMSException, PersistenceException {
        PreparedStatement select = null;
        ResultSet set = null;
        try {
            select = _connection.prepareStatement(
                    "select * from " + MESSAGE_TABLE + " where message_id = ?");
            select.setString(1, messageId);
            set = select.executeQuery();
            if (!set.next()) {
                throw new PersistenceException(
                        "Message not found, JMSMessageID=" + messageId);
            }
            String correlationId = set.getString("correlation_id");
            int deliveryMode = set.getInt("delivery_mode");
            long destinationId = set.getLong("destination_id");
            long expiration = set.getLong("expiration");
            int priority = set.getInt("priority");
            boolean redelivered = set.getBoolean("redelivered");
            long replyToId = set.getLong("reply_to_id");
            long timestamp = set.getLong("timestamp");
            String type = set.getString("type");

            Destination destination = _destinations.get(destinationId);

            message.setJMSMessageID(messageId);
            message.setJMSCorrelationID(correlationId);
            message.setJMSDeliveryMode(deliveryMode);
            message.setJMSDestination(destination);
            message.setJMSExpiration(expiration);
            message.setJMSPriority(priority);
            message.setJMSRedelivered(redelivered);
            if (replyToId != 0) {
                Destination replyTo = _destinations.get(replyToId);
                message.setJMSReplyTo(replyTo);
            }
            message.setJMSTimestamp(timestamp);
            message.setJMSType(type);

            Blob blob = set.getBlob("body");
            Object body;
            try {
                body = deserialize(blob);
            } catch (Exception exception) {
                throw new PersistenceException(
                        "Failed to deserialize message body, JMSMessageID="
                        + messageId, exception);
            }
            setBody(body, message);
        } catch (SQLException exception) {
            throw new PersistenceException(
                    "Failed to populate message, JMSMessageID="
                    + messageId, exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }

        getProperties(messageId, message);
    }

    /**
     * Populate message properties.
     *
     * @param messageId the message identifier
     * @param message   the message to populate
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     */
    protected void getProperties(String messageId, Message message)
            throws JMSException, PersistenceException {

        Map properties = getProperties(messageId);
        Iterator iterator = properties.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String name = (String) entry.getKey();
            Object value = entry.getValue();
            message.setObjectProperty(name, value);
        }
    }

    /**
     * Add a message.
     *
     * @param message the message to add
     * @param type    the type of the message
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     */
    protected void add(Message message, String type)
            throws JMSException, PersistenceException {

        PreparedStatement insert = null;
        String messageId = null;
        try {
            insert = _connection.prepareStatement(
                    "insert into " + MESSAGE_TABLE
                    + " values  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            messageId = message.getJMSMessageID();

            long destinationId = _destinations.getId(
                    (JmsDestination) message.getJMSDestination());
            
            // insert message header
            insert.setString(1, messageId);
            insert.setString(2, type);
            insert.setString(3, message.getJMSCorrelationID());
            insert.setInt(4, message.getJMSDeliveryMode());
            insert.setLong(5, destinationId);
            insert.setLong(6, message.getJMSExpiration());
            insert.setInt(7, message.getJMSPriority());
            insert.setBoolean(8, message.getJMSRedelivered());
            long replyToId = 0;
            if (message.getJMSReplyTo() != null) {
                JmsDestination replyTo =
                        (JmsDestination) message.getJMSReplyTo();
                replyToId = _destinations.getId(replyTo);
            }
            insert.setLong(9, replyToId);
            insert.setLong(10, message.getJMSTimestamp());
            insert.setString(11, message.getJMSType());
            Object body = getBody(message);
            byte[] blob;
            try {
                blob = serialize(body);
            } catch (Exception exception) {
                throw new PersistenceException(
                        "Failed to serialize message body, JMSMessageID="
                        + messageId, exception);
            }
            insert.setObject(12, blob);

            insert.executeUpdate();
            
            // insert header properties
            Enumeration iterator = message.getPropertyNames();
            while (iterator.hasMoreElements()) {
                String name = (String) iterator.nextElement();
                Object value = message.getObjectProperty(name);
                addProperty(messageId, name, value);
            }
            _connection.commit();
        } catch (SQLException exception) {
            throw new PersistenceException(
                    "Failed to add message, JMSMessageID=" + messageId,
                    exception);
        } finally {
            SQLHelper.close(insert);
        }
    }

    /**
     * Returns properties for a message.
     *
     * @param messageId the message identifier
     * @return a map of properties
     * @throws PersistenceException for any persistence error
     */
    protected Map getProperties(String messageId)
            throws PersistenceException {

        HashMap result = new HashMap();

        PreparedStatement select = null;
        ResultSet set = null;
        try {
            select = _connection.prepareStatement(
                    "select name, value from " + MESSAGE_PROPERTIES_TABLE
                    + " where message_id = ?");
            select.setString(1, messageId);
            set = select.executeQuery();
            while (set.next()) {
                String name = set.getString("name");
                Blob blob = set.getBlob("value");
                Object value;
                try {
                    value = deserialize(blob);
                } catch (Exception exception) {
                    String message = "Failed to destream property for "
                            + "message, JMSMessageID=" + messageId
                            + ", property=" + name;
                    throw new PersistenceException(message, exception);
                }
                result.put(name, value);
            }
        } catch (SQLException exception) {
            throw new PersistenceException(
                    "Failed to get properties for message, JMSMessageID="
                    + messageId, exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }
        return result;
    }

    /**
     * Add a property.
     *
     * @param messageId the message identifier
     * @param name      the property name
     * @param value     the property value
     * @throws PersistenceException for any persistence error
     */
    protected void addProperty(String messageId,
                               String name, Object value)
            throws PersistenceException {

        byte[] blob;
        try {
            blob = serialize(value);
        } catch (IOException exception) {
            String message = "Failed to serialize property for message, "
                    + "JMSMessageID=" + messageId + ", name=" + name;
            if (value != null) {
                message += " of type " + value.getClass().getName();
            }
            throw new PersistenceException(message, exception);
        }

        PreparedStatement insert = null;
        try {
            insert = _connection.prepareStatement(
                    "insert into " + MESSAGE_PROPERTIES_TABLE
                    + " values (?, ?, ?)");
            insert.setString(1, messageId);
            insert.setString(2, name);
            insert.setObject(3, blob);
            insert.executeUpdate();
        } catch (SQLException exception) {
            throw new PersistenceException(
                    "Failed to add property for message, JMSMessageID="
                    + messageId + ", name=" + name + ", value=" + value,
                    exception);
        } finally {
            SQLHelper.close(insert);
        }
    }

    /**
     * Helper to serialize an object to a byte array.
     *
     * @param object the object to serialize
     * @return the serialized object
     * @throws IOException if the object cannot be serialized
     */
    public byte[] serialize(Object object) throws IOException {
        byte[] result;
        ByteArrayOutputStream bstream = new ByteArrayOutputStream();
        ObjectOutputStream ostream = new ObjectOutputStream(bstream);
        ostream.writeObject(object);
        ostream.close();
        result = bstream.toByteArray();
        return result;
    }

    /**
     * Helper to deserialize an object from a byte array.
     *
     * @param blob the blob containing object to deserialize
     * @return the destreamed object
     * @throws ClassNotFoundException if the class of a serialized object cannot
     *                                be found.
     * @throws IOException            if the object cannot be deserialized
     * @throws SQLException           if there is an error accessing the
     *                                <code>blob</code>
     */
    protected Object deserialize(Blob blob)
            throws ClassNotFoundException, IOException, SQLException {
        Object result = null;

        if (blob != null) {
            ObjectInputStream istream = new ObjectInputStream(
                    blob.getBinaryStream());
            result = istream.readObject();
            istream.close();
        }
        return result;
    }

    /**
     * Returns the database connection.
     *
     * @return the connection to the database
     */
    protected Connection getConnection() {
        return _connection;
    }

}
