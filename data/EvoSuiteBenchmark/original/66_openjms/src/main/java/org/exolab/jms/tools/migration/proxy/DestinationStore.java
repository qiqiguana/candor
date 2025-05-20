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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.jms.JMSException;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsQueue;
import org.exolab.jms.client.JmsTopic;
import org.exolab.jms.persistence.SQLHelper;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.tools.migration.Store;
import org.exolab.jms.tools.migration.StoreIterator;
import org.exolab.jms.tools.migration.IteratorAdapter;


/**
 * Provides persistency for {@link JmsDestination} instances.
 *
 * @version     $Revision: 1.2 $ $Date: 2005/10/20 14:07:03 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
public class DestinationStore implements Store, DBConstants {

    /**
     * The database conection.
     */
    private final Connection _connection;

    /**
     * Map of destinations keyed on name.
     */
    private final HashMap _destinations = new HashMap();

    /**
     * Map of destinations keyed on identity.
     */
    private final HashMap _ids = new HashMap();

    /**
     * The seed to generate identifiers for destination instances.
     */
    private long _seed = 0;


    /**
     * Construct a new <code>DestinationStore</code>.
     *
     * @param connection the database connection
     * @throws PersistenceException for any persistence error
     */
    public DestinationStore(Connection connection) throws PersistenceException {
        _connection = connection;
        load();
    }

    /**
     * Export the destinations.
     *
     * @return an iterator over the collection
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     */
    public StoreIterator exportCollection() throws JMSException,
            PersistenceException {
        List destinations = getDestinations();
        return new IteratorAdapter(destinations.iterator());
    }

    /**
     * Import destinations into the store.
     *
     * @param iterator an iterator over the collection
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     */
    public void importCollection(StoreIterator iterator) throws JMSException,
            PersistenceException {
        while (iterator.hasNext()) {
            JmsDestination destination = (JmsDestination) iterator.next();
            add(destination);
        }
    }

    /**
     * Returns the number of elements in the collection.
     *
     * @return the number of elements in the collection
     */
    public int size() throws PersistenceException {
        return _destinations.size();
    }

    /**
     * Add a new destination to the database, assigning it a unique identity.
     * 
     * @param destination the destination to add
     * @throws PersistenceException for any persistence error
     */
    public synchronized void add(JmsDestination destination) 
        throws PersistenceException {

        PreparedStatement insert = null;
        try {
            long destinationId = ++_seed;
            String name = destination.getName();
            boolean isQueue = (destination instanceof JmsQueue);

            insert = _connection.prepareStatement(
                "insert into " + DESTINATION_TABLE + " values (?, ?, ?)");
            insert.setLong(1, destinationId);
            insert.setString(2, name);
            insert.setBoolean(3, isQueue);
            insert.executeUpdate();
            cache(destination, destinationId);
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to add destination="
                                           + destination.getName(),
                                           exception);
        } finally {
            SQLHelper.close(insert);
        }
    }

    /**
     * Returns the destination associated with the specified identifier.
     * 
     * @param destinationId the destination identifier
     * @return the destination, or <code>null</code> if no corresponding
     * destination exists
     */
    public synchronized JmsDestination get(long destinationId) {
        Pair pair = (Pair) _ids.get(new Long(destinationId));
        return (pair != null) ? pair.destination : null;
    }

    /**
     * Returns the destination identifier for a given destination.
     * 
     * @param destination the destination
     * @return the destination identifier, or <code>-1</code> if no 
     * corresponding destination exists
     */
    public synchronized long getId(JmsDestination destination) {
        Pair pair = (Pair) _destinations.get(destination.getName());
        return (pair != null) ? pair.destinationId : -1;
    }

    /**
     * Returns the list of destination objects.
     * 
     * @return a list of <code>javax.jms.Destination</code> instances
     */
    public synchronized List getDestinations() {
        List result = new ArrayList(_destinations.size());

        Iterator iterator = _destinations.values().iterator();
        while (iterator.hasNext()) {
            Pair pair = (Pair) iterator.next();
            result.add(pair.destination);
        }
            
        return result;
    }

    /**
     * Load all destinations.
     *
     * @throws PersistenceException for any persistence error
     */
    protected void load() throws PersistenceException {
        PreparedStatement select = null;
        ResultSet set = null;
        try {
            select = _connection.prepareStatement(
                    "select * from " + DESTINATION_TABLE);

            set = select.executeQuery();
            while (set.next()) {
                long destinationId = set.getLong("destination_id");
                String name = set.getString("name");
                boolean isQueue = set.getBoolean("is_queue");
                JmsDestination destination = (isQueue) 
                    ? (JmsDestination) new JmsQueue(name) 
                    : (JmsDestination) new JmsTopic(name);
                destination.setPersistent(true);
                cache(destination, destinationId);
            }
        } catch (SQLException exception) {
            throw new PersistenceException("FGailed to load destinations",
                                           exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }
    }

    /**
     * Cache a destination.
     *
     * @param destination the destination to cache
     * @param destinationId the destination identity
     */
    private void cache(JmsDestination destination, long destinationId) {
        Pair pair = new Pair(destination, destinationId);

        _destinations.put(destination.getName(), pair);
        _ids.put(new Long(destinationId), pair);
    }

    /**
     * Helper class to hold the name and identity of a destination.
     */
    private static class Pair {

        public JmsDestination destination;

        public long destinationId;

        public Pair(JmsDestination destination, long destinationId) {
            this.destination = destination;
            this.destinationId = destinationId;
        }      
  
    }

}
