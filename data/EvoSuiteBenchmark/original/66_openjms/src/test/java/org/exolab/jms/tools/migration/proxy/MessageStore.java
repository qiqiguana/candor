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
 */
package org.exolab.jms.tools.migration.proxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;

import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.persistence.SQLHelper;
import org.exolab.jms.tools.migration.Store;
import org.exolab.jms.tools.migration.StoreIterator;


/**
 * Provides persistency for {@link Message} instances.
 *
 * @author <a href="mailto:tma#netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/09/04 07:07:12 $
 */
public class MessageStore implements Store, DBConstants {

    /**
     * The destination store.
     */
    private final DestinationStore _destinations;

    /**
     * The database connection.
     */
    private final Connection _connection;


    /**
     * Construct a new <code>MessageStore</code>.
     *
     * @param destinations the destination store
     * @param connection the database connection.
     */
    public MessageStore(DestinationStore destinations, Connection connection) {
        _destinations = destinations;
        _connection = connection;
    }

    /**
     * Export the collection.
     *
     * @return an iterator over the collection
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     */
    public StoreIterator exportCollection() throws JMSException,
            PersistenceException {
        List messageIds = getMessageIds();
        return new MessageIterator(messageIds);
    }


    /**
     * Import a collection.
     *
     * @param iterator an iterator over the collection
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     */
    public void importCollection(StoreIterator iterator) throws JMSException,
            PersistenceException {

        while (iterator.hasNext()) {
            Message message = (Message) iterator.next();
            add(message);
        }
    }

    /**
     * Returns the number of elements in the collection.
     *
     * @return the number of elements in the collection
     * @throws PersistenceException for any persistence error
     */
    public int size() throws PersistenceException {
        return getMessageIds().size();
    }

    /**
     * Add a message.
     *
     * @param message the message to add
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     */
    public synchronized void add(Message message)
            throws JMSException, PersistenceException {

        MessageHandler handler = MessageHandlerFactory.create(
                message, _destinations, _connection);

        handler.add(message);
    }

    /**
     * Returns a message for a given identifier.
     *
     * @param messageId the identity of the message
     * @return the message corresponding to <code>messageId</code> or
     *         <code>null</code> if no such message exists
     * @throws PersistenceException for any persistence error
     */
    public Message get(String messageId)
            throws JMSException, PersistenceException {
        Message result = null;
        PreparedStatement select = null;
        ResultSet set = null;
        try {
            select = _connection.prepareStatement(
                    "select message_type from " + MESSAGE_TABLE
                    + " where message_id = ?");
            select.setString(1, messageId);
            set = select.executeQuery();
            if (set.next()) {
                String type = set.getString("message_type");
                String qualifiedType = "javax.jms." + type;
                MessageHandler handler = MessageHandlerFactory.create(
                        qualifiedType, _destinations, _connection);
                result = handler.get(messageId);
            }
        } catch (SQLException exception) {
            throw new PersistenceException(
                    "Failed to get message with JMSMessageID=" + messageId,
                    exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }
        return result;
    }

    /**
     * Returns all message identifiers.
     *
     * @return a list of message identifiers
     * @throws PersistenceException for any persistence error
     */
    public List getMessageIds() throws PersistenceException {
        ArrayList result = new ArrayList();

        PreparedStatement select = null;
        ResultSet set = null;
        try {
            select = _connection.prepareStatement(
                    "select message_id from " + MESSAGE_TABLE);

            set = select.executeQuery();
            while (set.next()) {
                String messageId = set.getString("message_id");
                result.add(messageId);
            }
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to get message ids",
                                           exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }
        return result;
    }

    private class MessageIterator implements StoreIterator {

        private Iterator _iterator;

        public MessageIterator(List messageIds) {
            _iterator = messageIds.iterator();
        }

        public boolean hasNext() {
            return _iterator.hasNext();
        }

        public Object next() throws JMSException, PersistenceException {
            String messageId = (String) _iterator.next();

            return get(messageId);
        }

    }

}



