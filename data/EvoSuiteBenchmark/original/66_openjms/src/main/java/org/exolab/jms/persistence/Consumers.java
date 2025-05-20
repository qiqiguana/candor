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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsTopic;


/**
 * This class provides persistency for ConsumerState objects in an RDBMS
 * database.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2007/04/16 02:50:21 $
 */
class Consumers {

    /**
     * The seed generator.
     */
    private final SeedGenerator _seeds;

    /**
     * The destination manager.
     */
    private Destinations _destinations;

    /**
     * A cache for all durable consumers.
     */
    private final HashMap _consumers = new HashMap();

    /**
     * The name of the column that uniquely identifies the consumer.
     */
    private static final String CONSUMER_ID_SEED = "consumerId";

    /**
     * The name of the table that maintains a list of message handles per
     * consumer.
     */
    private static final String CONSUMER_MESSAGE = "message_handles";

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(Consumers.class);


    /**
     * Construct a new <code>Consumers</code>.
     *
     * @param seeds the seed generator
     * @param connection the connection to use
     * @throws PersistenceException if initialisation fails
     */
    public Consumers(SeedGenerator seeds,
                     Connection connection) throws PersistenceException {
        _seeds = seeds;
        init(connection);
    }

    /**
     * Sets the destination manager
     *
     * @param destinations the destination manager
     */
    public void setDestinations(Destinations destinations) {
        _destinations = destinations;
    }

    /**
     * Add a new durable consumer to the database if it does not already exist.
     * A durable consumer is specified by a destination name and a consumer
     * name.
     * <p/>
     * The destination must resolve to a valid JmsDestination object
     *
     * @param connection the database connection to use
     * @param dest       the name of the destination
     * @param consumer   the name of the consumer
     * @throws PersistenceException if the consumer cannot be added
     */
    public synchronized void add(Connection connection, String dest,
                                 String consumer)
            throws PersistenceException {

        JmsDestination destination = null;
        long destinationId = 0;

        synchronized (_destinations) {
            destination = _destinations.get(dest);
            if (destination == null) {
                raise("add", consumer, dest, "destination is invalid");
            }
            destinationId = _destinations.getId(dest);
        }

        // check that for a topic the consumer name is not the same as the
        // destination name
        if ((destination instanceof JmsTopic) &&
                (consumer.equals(dest))) {
            raise("add", consumer, dest,
                  "The consumer name and destination name cannot be the same");
        }

        // get the next id from the seed table
        long consumerId = _seeds.next(connection, CONSUMER_ID_SEED);

        PreparedStatement insert = null;
        try {
            insert = connection.prepareStatement(
                    "insert into consumers values (?,?,?,?)");

            long created = (new Date()).getTime();
            insert.setString(1, consumer);
            insert.setLong(2, destinationId);
            insert.setLong(3, consumerId);
            insert.setLong(4, created);
            insert.executeUpdate();

            Consumer map = new Consumer(consumer, consumerId,
                                        destinationId, created);

            // check to see if the durable consumer already exists. If it
            // does then do not add it but signal and error
            if (!_consumers.containsKey(consumer)) {
                _consumers.put(consumer, map);
            } else {
                _log.error("Durable consumer with name " + consumer
                           + " already exists.");
            }
        } catch (Exception exception) {
            throw new PersistenceException("Failed to add consumer, destination="
                                           + dest +
                                           ", name=" + consumer, exception);
        } finally {
            SQLHelper.close(insert);
        }
    }

    /**
     * Remove a consumer from the database. If the destination is of type queue
     * then the destination name and the consumer name are identical.
     *
     * @param connection - the connection to use
     * @param name       - the consumer name
     * @throws PersistenceException - if the consumer cannot be removed
     */
    public synchronized void remove(Connection connection, String name)
            throws PersistenceException {

        PreparedStatement delete = null;

        // locate the consumer
        Consumer map = (Consumer) _consumers.get(name);
        if (map == null) {
            raise("remove", name, "consumer does not exist");
        }

        try {
            delete = connection.prepareStatement(
                    "delete from consumers where name=?");
            delete.setString(1, name);
            delete.executeUpdate();

            // now delete all the corresponding handles in the consumer
            // message table
            remove(CONSUMER_MESSAGE, map.consumerId, connection);

            // remove the consumer from the local cache
            _consumers.remove(name);
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to remove consumer=" + name,
                                           exception);
        } finally {
            SQLHelper.close(delete);
        }
    }

    /**
     * Return the id of the durable consumer.
     *
     * @param name the consumer name
     * @return the consumer identity
     */
    public synchronized long getConsumerId(String name) {
        Consumer map = (Consumer) _consumers.get(name);
        return (map != null) ? map.consumerId : 0;
    }

    /**
     * Return true if a consumer exists
     *
     * @param name - the consumer name
     */
    public synchronized boolean exists(String name) {
        return (_consumers.get(name) != null);
    }

    /**
     * Returns a list of consumer names associated with a topic.
     *
     * @param destination the topic to query
     */
    public synchronized Vector getDurableConsumers(String destination) {
        Vector result = new Vector();
        long destinationId = _destinations.getId(destination);
        if (destinationId != 0) {
            Iterator iter = _consumers.values().iterator();
            while (iter.hasNext()) {
                Consumer map = (Consumer) iter.next();
                if (map.destinationId == destinationId) {
                    result.add(map.name);
                }
            }
        }

        return result;
    }

    /**
     * Return a map of consumer names to destinations names.
     *
     * @return list of all durable consumers
     */
    public synchronized HashMap getAllDurableConsumers() {
        HashMap result = new HashMap();

        Iterator iter = _consumers.values().iterator();
        while (iter.hasNext()) {
            Consumer map = (Consumer) iter.next();
            JmsDestination dest = _destinations.get(map.destinationId);

            if (dest instanceof JmsTopic) {
                result.put(map.name, dest.getName());
            }
        }

        return result;
    }

    /**
     * Return the consumer name corresponding to the specified identity
     *
     * @param id - the consumer identity
     */
    public synchronized String getConsumerName(long id) {
        String name = null;
        Iterator iter = _consumers.values().iterator();

        while (iter.hasNext()) {
            Consumer map = (Consumer) iter.next();
            if (map.consumerId == id) {
                name = map.name;
                break;
            }
        }

        return name;
    }

    /**
     * Deallocates resources owned or referenced by the instance
     */
    public synchronized void close() {
        _consumers.clear();
    }

    /**
     * Removes all cached consumer details for a given destination
     *
     * @param destinationId the Id of the destination
     */
    protected synchronized void removeCached(long destinationId) {
        Object[] list = _consumers.values().toArray();
        for (int i = 0; i < list.length; i++) {
            Consumer map = (Consumer) list[i];
            if (map.destinationId == destinationId) {
                _consumers.remove(map.name);
            }
        }
    }

    /**
     * Initialises the cache of consumers.
     *
     * @param connection the connection to use
     * @throws PersistenceException if initialisation fails
     */
    private void init(Connection connection) throws PersistenceException {
        PreparedStatement select = null;
        ResultSet set = null;
        try {
            select = connection.prepareStatement(
                    "select name, consumerId, destinationId, created "
                    + "from consumers");
            set = select.executeQuery();
            String name = null;
            long consumerId = 0;
            long destinationId = 0;
            long created = 0;
            Consumer map = null;
            while (set.next()) {
                name = set.getString(1);
                consumerId = set.getLong(2);
                destinationId = set.getLong(3);
                created = set.getLong(4);
                map = new Consumer(name, consumerId, destinationId,
                                   created);
                _consumers.put(name, map);
            }
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to retrieve consumers",
                                           exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }
    }

    /**
     * Remove all the rows in the specified table with the corresponding
     * consumer identity.
     *
     * @param table      - the table to destroy
     * @param consumerId - the target consumerId
     * @param connection - the database connection to use
     * @throws SQLException - thrown on any error
     */
    private void remove(String table, long consumerId, Connection connection)
            throws SQLException {

        PreparedStatement delete = null;
        try {
            delete = connection.prepareStatement(
                    "delete from " + table + " where consumerId=?");
            delete.setLong(1, consumerId);
            delete.executeUpdate();
        } finally {
            SQLHelper.close(delete);
        }
    }

    /**
     * Raise a PersistenceException with the specified parameters
     *
     * @param operation   - operation that failed
     * @param name        - corresponding consumert name
     * @param destination - corresponding destination
     * @param reason      - the reason for the exception
     */
    private void raise(String operation, String name, String destination,
                       String reason)
            throws PersistenceException {
        throw new PersistenceException(
                "Cannot " + operation + " consumer=" + name
                + ", destination=" + destination + ": " + reason);
    }

    /**
     * Raise a PersistenceException with the specified parameters
     *
     * @param operation - operation that failed
     * @param name      - corresponding consumert name
     * @param reason    - the reasone for the exception
     */
    private void raise(String operation, String name, String reason)
            throws PersistenceException {
        throw new PersistenceException("Cannot " + operation + " consumer=" +
                                       name + ": " + reason);
    }

    /**
     * This is an internal class that is used to store consumer entries
     */
    private class Consumer {

        /**
         * The name of the consumer
         */
        public String name;

        /**
         * The unique consumer identity
         */
        public long consumerId;

        /**
         * The identity of the destination that this durable consumer is
         * subscribed too
         */
        public long destinationId;

        /**
         * The time that this durable consumer was created
         */
        public long created;


        public Consumer(String name, long consumerId, long destinationId,
                        long created) {

            this.name = name;
            this.consumerId = consumerId;
            this.destinationId = destinationId;
            this.created = created;
        }

        public String getKey() {
            return name;
        }
    }
}
