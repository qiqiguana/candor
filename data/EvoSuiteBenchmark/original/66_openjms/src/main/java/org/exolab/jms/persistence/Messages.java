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
 */
package org.exolab.jms.persistence;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsTopic;
import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.messagemgr.PersistentMessageHandle;


/**
 * This class manages the persistence of message objects.
 *
 * @version     $Revision: 1.5 $ $Date: 2007/04/16 02:50:21 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
class Messages {

    /**
     * The destination manager.
     */
    private final Destinations _destinations;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(Messages.class);


    /**
     * Construct a new <code>Messages</code>.
     *
     * @param destinations the destinations manager
     */
    public Messages(Destinations destinations) {
        _destinations = destinations;
    }

    /**
     * Add a message to the database, in the context of the specified
     * transaction and connection.
     *
     * @param connection - execute on this connection
     * @param message - the message to add
     * @throws PersistenceException - an sql related error
     */
    public void add(Connection connection, MessageImpl message)
        throws PersistenceException {

        PreparedStatement insert = null;

        // extract the identity of the message
        String messageId = message.getMessageId().getId();

        // check that the destination is actually registered
        // and map the name to the corresponding id
        String name;
        try {
            name = ((JmsDestination) message.getJMSDestination()).getName();
        } catch (JMSException exception) {
            throw new PersistenceException(
                "Failed to get destination for message=" +
                message.getMessageId(), exception);
        }

        long destinationId = _destinations.getId(name);
        if (destinationId == 0) {
            throw new PersistenceException(
                "Cannot add message=" + message.getMessageId() +
                ", destination=" + name + " (" + destinationId +
                "): destination does not exist");
        }

        try {
            // create, populate and execute the insert
            insert = connection.prepareStatement(
                "insert into messages (messageId, destinationId, priority, "
                + "createTime, expiryTime, processed, messageBlob) values "
                + "(?,?,?,?,?,?,?)");
            insert.setString(1, messageId);
            insert.setLong(2, destinationId);
            insert.setInt(3, message.getJMSPriority());
            insert.setLong(4, message.getAcceptedTime());
            insert.setLong(5, message.getJMSExpiration());
            insert.setInt(6, (message.getProcessed()) ? 1 : 0);

            // serialize the message
            byte[] bytes = serialize(message);
            insert.setBinaryStream(7, new ByteArrayInputStream(bytes),
                bytes.length);
            //insert.setBytes(8, bytes);

            // execute the insert
            if (insert.executeUpdate() != 1) {
                throw new PersistenceException(
                    "Failed to add message=" + message.getMessageId() +
                    ", destination=" + name + " (" + destinationId + ")");
            }
        } catch (PersistenceException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new PersistenceException(
                "Failed to add message=" + message.getMessageId() +
                ", destination=" + name + " (" + destinationId + ")",
                exception);
        } finally {
            SQLHelper.close(insert);
        }
    }

    /**
     * Update the message state in the database. This will be called to set
     * the message state to processed by the provider
     *
     * @param connection - execute on this connection
     * @param message - the message to update
     * @throws PersistenceException - an sql related error
     */
    public void update(Connection connection, MessageImpl message)
        throws PersistenceException {

        PreparedStatement update = null;

        // extract the identity of the message
        String messageId = message.getMessageId().getId();

        try {
            update = connection.prepareStatement(
                "update messages set processed=? where messageId=?");
            update.setInt(1, message.getProcessed() ? 1 : 0);
            update.setString(2, messageId);

            // execute the update
            if (update.executeUpdate() != 1) {
                _log.error("Cannot update message=" + messageId);
            }
        } catch (SQLException exception) {
            throw new PersistenceException(
                "Failed to update message, id=" + messageId, exception);
        } finally {
            SQLHelper.close(update);
        }
    }

    /**
     * Remove a message with the specified identity from the database
     *
     * @param connection - execute on this connection
     * @param messageId - the message id of the message to remove
     * @throws PersistenceException - an sql related error
     */
    public void remove(Connection connection, String messageId)
        throws PersistenceException {

        PreparedStatement delete = null;
        try {
            delete = connection.prepareStatement(
                "delete from messages where messageId=?");
            delete.setString(1, messageId);

            // execute the delete
            if (delete.executeUpdate() != 1) {
                _log.error("Cannot remove message=" + messageId);
            }
        } catch (SQLException exception) {
            throw new PersistenceException(
                "Failed to remove message, id=" + messageId, exception);
        } finally {
            SQLHelper.close(delete);
        }
    }

    /**
     * Return the message identified by the message Id
     *
     * @param connection - execute on this connection
     * @param messageId - id of message to retrieve
     * @return MessageImpl - the associated message
     * @throws PersistenceException - an sql related error
     */
    public MessageImpl get(Connection connection, String messageId)
        throws PersistenceException {

        MessageImpl result = null;
        PreparedStatement select = null;
        ResultSet set = null;
        try {
            select = connection.prepareStatement(
                "select messageBlob, processed from messages where messageId=?");

            select.setString(1, messageId);
            set = select.executeQuery();
            if (set.next()) {
                result = deserialize(set.getBytes(1));
                result.setProcessed((set.getInt(2) == 1 ? true : false));
            }
        } catch (SQLException exception) {
            throw new PersistenceException(
                "Failed to retrieve message, id=" + messageId, exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }

        return result;
    }

    /**
     * Delete all messages for the given destination
     *
     * @param connection - execute on this connection
     * @param destination the destination to remove messages for
     * @return int - the number of messages purged
     * @throws PersistenceException - an sql related error
     */
    public int removeMessages(Connection connection, String destination)
        throws PersistenceException {

        int result = 0;
        PreparedStatement delete = null;

        // map the destination name to an id
        long destinationId = _destinations.getId(destination);
        if (destinationId == 0) {
            throw new PersistenceException("Cannot delete messages for " +
                "destination=" + destination +
                ": destination does not exist");
        }

        try {
            delete = connection.prepareStatement(
                "delete from messages where destinationId = ?");
            delete.setLong(1, destinationId);
            result = delete.executeUpdate();
        } catch (SQLException exception) {
            throw new PersistenceException(
                "Failed to remove messages for destination=" + destination,
                exception);
        } finally {
            SQLHelper.close(delete);
        }

        return result;
    }

    /**
     * Retrieve the next set of messages for the specified destination with
     * an acceptance time greater or equal to that specified. It will retrieve
     * around 200 or so messages depending on what is available.
     *
     * @param connection - execute on this connection
     * @param destination - the destination
     * @param priority - the priority of the messages
     * @param time - with timestamp greater or equal to this
     * @return Vector - one or more MessageImpl objects
     * @throws PersistenceException - if an SQL error occurs
     */
    public Vector getMessages(Connection connection, String destination,
                              int priority, long time)
        throws PersistenceException {

        PreparedStatement select = null;
        ResultSet set = null;
        Vector messages = new Vector();

        try {
            JmsDestination dest = _destinations.get(destination);
            if (dest == null) {
                throw new PersistenceException(
                    "Cannot getMessages for destination=" + destination
                    + ": destination does not exist");
            }

            long destinationId = _destinations.getId(destination);
            if (destinationId == 0) {
                throw new PersistenceException(
                    "Cannot getMessages for destination=" + destination
                    + ": destination does not exist");
            }

            if ((dest instanceof JmsTopic) &&
                (((JmsTopic) dest).isWildCard())) {
                // if the destination is a wildcard then we can't only select
                // on timestamp. This will fault in any message greater than
                // or equal to the specified timestamp.
                select = connection.prepareStatement(
                    "select createTime,processed,messageBlob from messages "
                    + "where priority=? and createTime>=? "
                    + "order by createTime asc");
                select.setInt(1, priority);
                select.setLong(2, time);
            } else {
                // if the destination is more specific then we can execute a
                // more specialized query and fault in other messages for
                // the same destination.
                select = connection.prepareStatement(
                    "select createTime,processed,messageBlob from messages "
                    + "where destinationId=? and priority=? and createTime>=? "
                    + "order by createTime asc");
                select.setLong(1, destinationId);
                select.setInt(2, priority);
                select.setLong(3, time);
            }
            set = select.executeQuery();

            // now iterate through the result set
            int count = 0;
            long lastTimeStamp = time;
            while (set.next()) {
                MessageImpl m = deserialize(set.getBytes(3));
                m.setProcessed((set.getInt(2) == 1 ? true : false));
                messages.add(m);
                if (++count > 200) {
                    // if there are more than two hundred rows then exist
                    // the loop after 200 messages have been retrieved
                    // and the timestamp has changed.
                    if (set.getLong(1) > lastTimeStamp) {
                        break;
                    }
                } else {
                    lastTimeStamp = set.getLong(1);
                }
            }
        } catch (SQLException exception) {
            throw new PersistenceException(
                "Failed to retrieve messages", exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }

        return messages;
    }

    /**
     * Retrieve the specified number of message ids from the database with a
     * time greater than that specified. The number of items to retrieve
     * is only a hint and does not reflect the number of messages actually
     * returned.
     *
     * @param connection - execute on this connection
     * @param time - with timestamp greater than
     * @param hint - an indication of the number of messages to return.
     * @return a map of messageId Strings to their creation time
     * @throws PersistenceException - if an SQL error occurs
     */
    public HashMap getMessageIds(Connection connection, long time, int hint)
        throws PersistenceException {

        PreparedStatement select = null;
        ResultSet set = null;
        HashMap messages = new HashMap();

        try {
            select = connection.prepareStatement(
                "select messageId,createTime from messages where createTime>? "
                + "order by createTime asc");
            select.setLong(1, time);
            set = select.executeQuery();

            // now iterate through the result set
            int count = 0;
            long lastTimeStamp = time;
            while (set.next()) {
                messages.put(set.getString(1), new Long(set.getLong(2)));
                if (++count > hint) {
                    if (set.getLong(2) > lastTimeStamp) {
                        break;
                    }
                } else {
                    lastTimeStamp = set.getLong("createTime");
                }

            }
        } catch (SQLException exception) {
            throw new PersistenceException(
                "Failed to retrieve message identifiers", exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }

        return messages;
    }

    /**
     * Retrieve a list of unprocessed messages and return them to the client.
     * An unprocessed message has been accepted by the system but not
     * processed.
     *
     * @param connection - execute on this connection
     * @return Vector - one or more MessageImpl objects
     * @throws PersistenceException - if an SQL error occurs
     */
    public Vector getUnprocessedMessages(Connection connection)
        throws PersistenceException {

        PreparedStatement select = null;
        ResultSet set = null;
        Vector messages = new Vector();

        try {
            select = connection.prepareStatement(
                "select messageBlob from messages where processed=0");
            set = select.executeQuery();
            // now iterate through the result set
            while (set.next()) {
                MessageImpl m = deserialize(set.getBytes(1));
                m.setProcessed(false);
                messages.add(m);
            }
        } catch (SQLException exception) {
            throw new PersistenceException(
                "Failed to retrieve unprocessed messages", exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }

        return messages;
    }

    /**
     * Retrieve the message handle for all unexpired messages
     *
     * @param connection - execute on this connection
     * @param destination - the destination in question
     * @return Vector - collection of PersistentMessageHandle objects
     * @throws  PersistenceException - sql releated exception
     */
    public Vector getNonExpiredMessages(Connection connection,
                                        JmsDestination destination)
        throws PersistenceException {

        Vector result = new Vector();
        PreparedStatement select = null;
        ResultSet set = null;

        try {
            long destinationId = _destinations.getId(destination.getName());

            if (destinationId == 0) {
                throw new PersistenceException(
                    "Cannot getMessages for destination=" + destination
                    + ": destination does not exist");
            }

            select = connection.prepareStatement(
                "select messageId,destinationId,priority,createTime,"
                + "sequenceNumber,expiryTime "
                + "from messages "
                + "where expiryTime>0 and destinationId=? "
                + "order by expiryTime asc");
            select.setLong(1, destinationId);
            set = select.executeQuery();

            while (set.next()) {
                String messageId = set.getString(1);
                int priority = set.getInt(3);
                long acceptedTime = set.getLong(4);
                long sequenceNumber = set.getLong(5);
                long expiryTime = set.getLong(6);
                PersistentMessageHandle handle = new PersistentMessageHandle(
                        messageId, priority, acceptedTime, sequenceNumber,
                        expiryTime, destination);
                result.add(handle);
            }
        } catch (SQLException exception) {
            throw new PersistenceException(
                "Failed to retrieve non-expired messages", exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }

        return result;
    }

    /**
     * Delete all expired messages and associated message handles.
     *
     * @param connection - execute on this connection
     * @throws PersistenceException - if an SQL error occurs
     */
    public void removeExpiredMessages(Connection connection)
        throws PersistenceException {

        PreparedStatement delete = null;
        try {
            long time = System.currentTimeMillis();

            // delete from the messages
            delete = connection.prepareStatement(
                "delete from messages where expiryTime > 0 and expiryTime < ?");
            delete.setLong(1, time);
            delete.executeUpdate();
            delete.close();

            // delete the message handles
            delete = connection.prepareStatement(
                "delete from message_handles where expiryTime > 0 and expiryTime < ?");
            delete.setLong(1, time);
            delete.executeUpdate();
        } catch (SQLException exception) {
            throw new PersistenceException(
                "Failed to remove expired messages", exception);
        } finally {
            SQLHelper.close(delete);
        }
    }

    /**
     * Get the message as a serialized blob
     *
     * @param       message             the message to serialize
     * @return      byte[]              the serialized message
     */
    public byte[] serialize(MessageImpl message)
        throws PersistenceException {

        byte[] result = null;
        ObjectOutputStream ostream = null;
        try {
            ByteArrayOutputStream bstream = new ByteArrayOutputStream();
            ostream = new ObjectOutputStream(bstream);
            ostream.writeObject(message);
            result = bstream.toByteArray();
        } catch (Exception exception) {
            throw new PersistenceException("Failed to serialize message",
                exception);
        } finally {
            SQLHelper.close(ostream);
        }

        return result;
    }

    /**
     * Set the message from a serialized blob
     *
     * @param blob the serialized message
     * @return the re-constructed message
     */
    public MessageImpl deserialize(byte[] blob) throws PersistenceException {
        MessageImpl message = null;

        if (blob != null) {
            ObjectInputStream istream = null;
            try {
                ByteArrayInputStream bstream = new ByteArrayInputStream(blob);
                istream = new ObjectInputStream(bstream);
                message = (MessageImpl) istream.readObject();
            } catch (Exception exception) {
                throw new PersistenceException(
                    "Failed to de-serialize message", exception);
            } finally {
                SQLHelper.close(istream);
            }
        } else {
            throw new PersistenceException(
                "Cannot de-serialize null message blob");
        }

        return message;
    }

}
