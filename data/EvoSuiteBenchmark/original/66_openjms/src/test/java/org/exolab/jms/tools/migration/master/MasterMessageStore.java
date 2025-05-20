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
 * $Id: MasterMessageStore.java,v 1.2 2005/10/20 14:07:03 tanderson Exp $
 */
package org.exolab.jms.tools.migration.master;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.jms.JMSException;

import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.persistence.SQLHelper;
import org.exolab.jms.tools.migration.Store;
import org.exolab.jms.tools.migration.StoreIterator;


/**
 * <code>MasterMessageStore</code> manages a collection of persistent
 * messages.
 *
 * @author <a href="mailto:tma#netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/10/20 14:07:03 $
 */
public class MasterMessageStore implements Store {

    /**
     * The database service.
     */
    private DatabaseService _database;


    /**
     * Construct a new <code>MasterDestinationStore</code>.
     *
     * @param database the database service
     */
    public MasterMessageStore(DatabaseService database) {
        _database = database;
    }

    /**
     * Export the messages.
     *
     * @return an iterator over the collection
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     */
    public StoreIterator exportCollection() throws JMSException,
                                                   PersistenceException {
        List ids = getMessageIds();
        return new MessageIterator(ids);
    }

    /**
     * Import messages into the store.
     *
     * @param iterator an iterator over the collection
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     */
    public void importCollection(StoreIterator iterator)
            throws JMSException, PersistenceException {
        Connection connection = _database.getConnection();
        while (iterator.hasNext()) {
            MessageImpl message = (MessageImpl) iterator.next();
            _database.getAdapter().addMessage(connection, message);
        }
        _database.commit();
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
     * Returns a list of all message identifiers.
     *
     * @return a list of all message identifiers.
     * @throws PersistenceException for any persistence error
     */
    private List getMessageIds() throws PersistenceException {
        List result = new ArrayList(1000);
        final String query
                = "select messageId from messages order by createTime";
        Connection connection = _database.getConnection();
        PreparedStatement select = null;
        ResultSet set = null;

        try {
            select = connection.prepareStatement(query);
            set = select.executeQuery();
            while (set.next()) {
                result.add(set.getString(1));
            }
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to execute query: " + query,
                                           exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }
        _database.commit();
        return result;
    }


    private class MessageIterator implements StoreIterator {
        private final Iterator _iterator;

        public MessageIterator(List ids) {
            _iterator = ids.iterator();
        }

        public boolean hasNext() {
            return _iterator.hasNext();
        }

        public Object next() throws PersistenceException {
            MessageImpl result = null;

            String id = (String) _iterator.next();

            Connection connection = _database.getConnection();
            result = _database.getAdapter().getMessage(connection, id);
            _database.commit();
            return result;
        }

    }

}
