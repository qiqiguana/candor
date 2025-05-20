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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsTopic;
import org.exolab.jms.messagemgr.PersistentMessageHandle;
import org.exolab.jms.messagemgr.MessageHandle;


/**
 * This class provides persistency for MessageHandle objects
 * in an RDBMS database
 *
 * @version     $Revision: 1.5 $ $Date: 2007/04/16 02:50:21 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
class MessageHandles {

    /**
     * The destination manager.
     */
    private final Destinations _destinations;

    /**
     * The consumer manager.
     */
    private final Consumers _consumers;

    /**
     * prepared statement for inserting a message handle
     */
    private static final String INSERT_MSG_HANDLE_STMT =
            "insert into message_handles (messageId, destinationId, consumerId, "
            + "priority, acceptedTime, sequenceNumber, expiryTime, delivered) "
            + "values (?,?,?,?,?,?,?,?)";

    /**
     * prepared statements for deleting message handle
     */
    private static final String DELETE_MSG_HANDLE_STMT1 =
        "delete from message_handles where messageId=? and consumerId=?";
    private static final String DELETE_MSG_HANDLE_STMT2 =
        "delete from message_handles where messageId=? and destinationId=? " +
        "and consumerId=?";

    /**
     * Delete all message handles with the specified message id
     */
    private static final String DELETE_MSG_HANDLES_STMT =
        "delete from message_handles where messageId=?";

    /**
     * Update a row in the message handles table
     */
    private static final String UPDATE_MSG_HANDLE_STMT =
        "update message_handles set delivered=? where messageId=? and " +
        "destinationId=? and consumerId=?";

    /**
     * Delete all message handles for a destination
     */
    private static final String DELETE_MSG_HANDLES_FOR_DEST =
        "delete from message_handles where destinationId=?";

    /**
     * Retrieve all message handles for a particular consumer
     */
    private static final String GET_MSG_HANDLES_FOR_DEST =
        "select messageId, destinationId, consumerId, priority, acceptedTime, "
        + "sequenceNumber, expiryTime, delivered from message_handles "
        + "where consumerId=? order by acceptedTime asc";

    /**
     * Retrieve a range of message handles between the specified times
     */
    private static final String GET_MESSAGE_HANDLES_IN_RANGE =
        "select distinct messageId from message_handles where " +
        " acceptedTime >= ? and acceptedTime <=?";

    /**
     * Retrieve a handle with the specified id
     */
    private static final String GET_MESSAGE_HANDLE_WITH_ID =
        "select distinct messageId from message_handles where messageId=?";

    /**
     * Return the number of messages and a specified destination and cousmer
     */
    private static final String GET_MSG_HANDLE_COUNT_FOR_DEST_AND_CONSUMER =
        "select count(messageId) from message_handles where destinationId=? " +
        "and consumerId=?";

    /**
     * Return the number of messages and a specified consumer
     */
    private static final String GET_MSG_HANDLE_COUNT_FOR_CONSUMER =
        "select count(messageId) from message_handles where consumerId=?";

    /**
     * Delete all expired messages
     */
    private static final String DELETE_EXPIRED_MESSAGES =
        "delete from message_handles where consumerId=? and expiryTime != 0 " +
        "and expiryTime<?";

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(MessageHandles.class);


    /**
     * Construct a new <code>MessageHandles</code>.
     *
     * @param destinations the destinations manager
     * @param consumers the consumers manager
     */
    public MessageHandles(Destinations destinations, Consumers consumers) {
        _destinations = destinations;
        _consumers = consumers;
    }

    /**
     * Add the specified message handle to the database.
     *
     * @param connection - the connection to use
     * @param handle - message handle to add
     * @throws PersistenceException - if add does not complete
     */
    public void addMessageHandle(Connection connection,
                                 MessageHandle handle)
        throws PersistenceException {

        if (_log.isDebugEnabled()) {
            _log.debug("addMessageHandle(handle=[consumer="
                       + handle.getConsumerPersistentId()
                       + ", destination=" + handle.getDestination() 
                       + ", id=" + handle.getMessageId() + "])");
        }

        PreparedStatement insert = null;
        try {
            // map the destination name to an actual identity
            long destinationId = _destinations.getId(
                handle.getDestination().getName());
            if (destinationId == 0) {
                throw new PersistenceException(
                    "Cannot add message handle id=" + handle.getMessageId() +
                    " for destination=" + handle.getDestination().getName() +
                    " and consumer=" + handle.getConsumerPersistentId() +
                    " since the destination cannot be mapped to an id");
            }

            // map the consumer name ot an identity
            long consumerId = _consumers.getConsumerId(
                handle.getConsumerPersistentId());
            if (consumerId == 0) {
                throw new PersistenceException(
                    "Cannot add message handle id=" + handle.getMessageId() +
                    " for destination=" + handle.getDestination().getName() +
                    " and consumer=" + handle.getConsumerPersistentId() +
                    " since the consumer cannot be mapped to an id");
            }

            insert = connection.prepareStatement(INSERT_MSG_HANDLE_STMT);
            insert.setString(1, handle.getMessageId());
            insert.setLong(2, destinationId);
            insert.setLong(3, consumerId);
            insert.setInt(4, handle.getPriority());
            insert.setLong(5, handle.getAcceptedTime());
            insert.setLong(6, handle.getSequenceNumber());
            insert.setLong(7, handle.getExpiryTime());
            insert.setInt(8, (handle.getDelivered()) ? 1 : 0);

            // execute the insert
            if (insert.executeUpdate() != 1) {
                _log.error(
                    "Failed to execute addMessageHandle for handle="
                    + handle.getMessageId() + ", destination Id="
                    + destinationId);
            }
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to add message handle=" +
                handle, exception);
        } finally {
            SQLHelper.close(insert);
        }
    }

    /**
     * Remove the specified message handle from the database. Once the handle
     * has been removed check to see whether there are any more message handles
     * referencing the same message. If there are not then remove the
     * corresponding message from the messages tables.
     *
     * @param connection - the connection to use
     * @param  handle - the handle to remove
     * @throws  PersistenceException - sql releated exception
     */
    public void removeMessageHandle(Connection connection,
                                    MessageHandle handle)
        throws PersistenceException {

        if (_log.isDebugEnabled()) {
            _log.debug("removeMessageHandle(handle=[consumer="
                       + handle.getConsumerPersistentId()
                       + ", destination=" + handle.getDestination() 
                       + ", id=" + handle.getMessageId() + "])");
        }

        PreparedStatement delete = null;
        PreparedStatement select = null;
        ResultSet rs = null;

        try {
            // first check to see that the consumer exists and only
            // proceed if it non-zero.
            long consumerId = _consumers.getConsumerId(
                handle.getConsumerPersistentId());
            if (consumerId != 0) {
                // get the message id
                String id = handle.getMessageId();

                // map the destination name to an actual identity. If it is
                // null then the destination does not currently exist but we
                // may need to delete orphaned handles
                long destinationId = _destinations.getId(
                    handle.getDestination().getName());

                if (destinationId == 0) {
                    delete = connection.prepareStatement(
                        DELETE_MSG_HANDLE_STMT1);
                    delete.setString(1, id);
                    delete.setLong(2, consumerId);

                } else {
                    delete = connection.prepareStatement(
                        DELETE_MSG_HANDLE_STMT2);
                    delete.setString(1, id);
                    delete.setLong(2, destinationId);
                    delete.setLong(3, consumerId);
                }

                // execute the delete
                if (delete.executeUpdate() != 1 && !handle.hasExpired()) {
                    // only log if the message hasn't been garbage
                    // collected
                    _log.error("Failed to execute removeMessageHandle for "
                        + "handle=" + id + " destination id="
                        + destinationId + " consumer id=" + consumerId);
                }
            }
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to remove message handle=" +
                handle, exception);
        } finally {
            SQLHelper.close(rs);
            SQLHelper.close(delete);
            SQLHelper.close(select);
        }
    }

    /**
     * Update the specified message handle from the database
     *
     * @param connection - the connection to use
     * @param  handle - the handle to update
     * @throws  PersistenceException - sql releated exception
     */
    public void updateMessageHandle(Connection connection,
                                    MessageHandle handle)
        throws PersistenceException {
        PreparedStatement update = null;

        if (_log.isDebugEnabled()) {
            _log.debug("updateMessageHandle(handle=[consumer="
                       + handle.getConsumerPersistentId()
                       + ", destination=" + handle.getDestination() 
                       + ", id=" + handle.getMessageId() + "])");
        }

        try {
            // get the message id
            String id = handle.getMessageId();

            // map the destination name to an actual identity
            long destinationId = _destinations.getId(
                handle.getDestination().getName());
            if (destinationId == 0) {
                throw new PersistenceException(
                    "Cannot update message handle id=" +
                    handle.getMessageId() + " for destination=" +
                    handle.getDestination().getName() + " and consumer=" +
                    handle.getConsumerPersistentId() +
                    " since the destination cannot be mapped to an id");
            }

            // map the consumer name to an identity
            long consumerId = _consumers.getConsumerId(
                handle.getConsumerPersistentId());
            if (consumerId == 0) {
                throw new PersistenceException(
                    "Cannot update message handle id=" +
                    handle.getMessageId() + " for destination=" +
                    handle.getDestination().getName() + " and consumer=" +
                    handle.getConsumerPersistentId() +
                    " since the consumer cannot be mapped to an id");
            }

            update = connection.prepareStatement(UPDATE_MSG_HANDLE_STMT);
            update.setInt(1, handle.getDelivered() ? 1 : 0);
            update.setString(2, id);
            update.setLong(3, destinationId);
            update.setLong(4, consumerId);

            // execute the delete
            if (update.executeUpdate() != 1 && !handle.hasExpired()) {
                // only log if the message hasn't been garbage collected
                _log.error(
                    "Failed to execute updateMessageHandle for handle=" +
                    id + ", destination id=" + destinationId +
                    ", consumer id=" + consumerId);
            }
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to update message handle=" +
                handle, exception);
        } finally {
            SQLHelper.close(update);
        }
    }

    /**
     * Remove all the message handles associated with the specified destination
     *
     * @param connection - the connection to use
     * @param  destination the name of the destination
     * @throws  PersistenceException - sql releated exception
     */
    public void removeMessageHandles(Connection connection, String destination)
        throws PersistenceException {

        PreparedStatement delete = null;

        try {
            // map the destination name to an actual identity
            long destinationId = _destinations.getId(destination);
            if (destinationId == 0) {
                throw new PersistenceException(
                    "Cannot remove message handles for destination=" +
                    destination + " since the destination cannot be " +
                    "mapped to an id");
            }

            delete = connection.prepareStatement(DELETE_MSG_HANDLES_FOR_DEST);
            delete.setLong(1, destinationId);
            delete.executeUpdate();
        } catch (SQLException exception) {
            throw new PersistenceException(
                "Failed to remove message handles for destination=" +
                destination, exception);
        } finally {
            SQLHelper.close(delete);
        }
    }

    /**
     * Remove all the message handles for the specified messageid
     *
     * @param connection - the connection to use
     * @param messageId the message identity
     * @throws  PersistenceException - sql releated exception
     */
    public void removeMessageHandles(Connection connection, long messageId)
        throws PersistenceException {

        PreparedStatement delete = null;

        try {
            delete = connection.prepareStatement(DELETE_MSG_HANDLES_STMT);
            delete.setLong(1, messageId);
            delete.executeUpdate();
        } catch (SQLException exception) {
            throw new PersistenceException(
                "Failed to remove message handles for message id=" + messageId,
                exception);
        } finally {
            SQLHelper.close(delete);
        }
    }

    /**
     * Retrieve the message handle for the specified desitation and consumer
     * name
     *
     * @param connection - the connection to use
     * @param destination - destination name
     * @param name - consumer name
     * @return Vector - collection of MessageHandle objects
     * @throws  PersistenceException - sql releated exception
     */
    public Vector getMessageHandles(Connection connection, String destination,
                                    String name)
        throws PersistenceException {

        Vector result = new Vector();
        PreparedStatement select = null;
        ResultSet set = null;

        // if the consumer and/or destination cannot be mapped then
        // return an empty vector
        long destinationId = _destinations.getId(destination);
        long consumerId = _consumers.getConsumerId(name);
        if ((consumerId == 0) ||
            (destinationId == 0)) {
            return result;
        }

        // all preprequisites have been met so continue processing the
        // request.
        try {
            select = connection.prepareStatement(GET_MSG_HANDLES_FOR_DEST);
            select.setLong(1, consumerId);

            // iterate through the result set and construct the corresponding
            // MessageHandles
            set = select.executeQuery();
            while (set.next()) {
                // Attempt to retrieve the corresponding destination
                JmsDestination dest = _destinations.get(set.getLong(2));
                if (dest == null) {
                    throw new PersistenceException(
                        "Cannot create persistent handle, because " +
                        "destination mapping failed for " + set.getLong(2));
                }

                String consumer = _consumers.getConsumerName(set.getLong(3));
                if (name == null) {
                    throw new PersistenceException(
                        "Cannot create persistent handle because " +
                        "consumer mapping failed for " + set.getLong(3));
                }

                String messageId = set.getString(1);
                int priority = set.getInt(4);
                long acceptedTime = set.getLong(5);
                long sequenceNumber = set.getLong(6);
                long expiryTime = set.getLong(7);
                boolean delivered = (set.getInt(8) == 0) ? false : true;
                MessageHandle handle = new PersistentMessageHandle(
                        messageId, priority, acceptedTime, sequenceNumber,
                        expiryTime, dest, consumer);
                handle.setDelivered(delivered);
                result.add(handle);
            }
        } catch (SQLException exception) {
            throw new PersistenceException(
                "Failed to get message handles for destination=" +
                destination + ", consumer=" + name, exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }

        return result;
    }

    /**
     * Retrieve a distint list of message ids, in this table, between the min
     * and max times inclusive.
     *
     * @param connection - the connection to use
     * @param min - the minimum time in milliseconds
     * @param max - the maximum time in milliseconds
     * @return Vector - collection of String objects
     * @throws  PersistenceException - sql related exception
     */
    public Vector getMessageIds(Connection connection, long min, long max)
        throws PersistenceException {

        Vector result = new Vector();
        PreparedStatement select = null;
        ResultSet set = null;

        try {
            select = connection.prepareStatement(GET_MESSAGE_HANDLES_IN_RANGE);
            select.setLong(1, min);
            select.setLong(2, max);

            // iterate through the result set and construct the corresponding
            // MessageHandles
            set = select.executeQuery();
            while (set.next()) {
                result.add(set.getString(1));
            }

           
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to retrieve message ids",
                exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }

        return result;
    }

    /**
     * Check if a message with the specified messageId exists in the
     * table
     *
     * @param connection - the connection to use
     * @param messageId the  message Identifier
     * @return Vector - collection of MessageHandle objects
     * @throws  PersistenceException - sql releated exception
     */
    public boolean messageExists(Connection connection, long messageId)
        throws PersistenceException {

        boolean result = false;
        PreparedStatement select = null;
        ResultSet set = null;

        try {
            select = connection.prepareStatement(GET_MESSAGE_HANDLE_WITH_ID);
            select.setLong(1, messageId);
            set = select.executeQuery();

            if (set.next()) {
                result = true;
            }
            
        } catch (SQLException exception) {
            throw new PersistenceException(
                "Failed to determine if message exists, id=" + messageId,
                exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }
        return result;
    }

    /**
     * Returns the number of messages for the specified destination and
     * consumer
     *
     * @param connection - the connection to use
     * @param destination - destination name
     * @param name - consumer name
     * @return Vector - collection of MessageHandle objects
     * @throws  PersistenceException - sql releated exception
     */
    public int getMessageCount(Connection connection, String destination,
                               String name)
        throws PersistenceException {

        int result = -1;
        boolean destinationIsWildCard = false;

        // map the destination name to an actual identity
        long destinationId = _destinations.getId(destination);
        if (destinationId == 0) {
            if (JmsTopic.isWildCard(destination)) {
                destinationIsWildCard = true;
            } else {
                throw new PersistenceException(
                    "Cannot get message handle count for destination=" +
                    destination + " and consumer=" + name +
                    " since the destination cannot be mapped to an id");
            }
        }

        // map the consumer name to an identity
        long consumerId = _consumers.getConsumerId(name);
        if (consumerId == 0) {
            throw new PersistenceException(
                "Cannot get message handle count for destination=" +
                destination + " and consumer=" + name +
                " since the consumer cannot be mapped to an id");
        }

        PreparedStatement select = null;
        ResultSet set = null;

        try {
            if (destinationIsWildCard) {
                select = connection.prepareStatement(
                    GET_MSG_HANDLE_COUNT_FOR_DEST_AND_CONSUMER);
                select.setLong(1, destinationId);
                select.setLong(2, consumerId);
            } else {
                select = connection.prepareStatement(
                    GET_MSG_HANDLE_COUNT_FOR_CONSUMER);
                select.setLong(1, consumerId);
            }

            set = select.executeQuery();
            if (set.next()) {
                result = set.getInt(1);
            }
        } catch (SQLException exception) {
            throw new PersistenceException(
                "Failed to count messages for destination=" + destination +
                ", consumer=" + name, exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }

        return result;
    }

    /**
     * Remove all expired handles for the specified consumer
     *
     * @param connection - the connection to use
     * @param consumer - consumer name
     * @throws  PersistenceException - sql releated exception
     */
    public void removeExpiredMessageHandles(Connection connection,
                                            String consumer)
        throws PersistenceException {

        PreparedStatement delete = null;

        // map the consumer name ot an identity
        long consumerId = _consumers.getConsumerId(consumer);
        if (consumerId != 0) {
            try {
                delete = connection.prepareStatement(DELETE_EXPIRED_MESSAGES);
                delete.setLong(1, consumerId);
                delete.setLong(2, System.currentTimeMillis());
                delete.executeUpdate();
            } catch (SQLException exception) {
                throw new PersistenceException(
                    "Failed to remove expired message handles",
                    exception);
            } finally {
                SQLHelper.close(delete);
            }
        }
    }

}
