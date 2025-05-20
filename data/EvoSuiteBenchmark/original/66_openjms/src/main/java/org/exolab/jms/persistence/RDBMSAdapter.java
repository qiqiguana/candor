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
 * $Id: RDBMSAdapter.java,v 1.7 2007/04/16 02:50:21 tanderson Exp $
 */
package org.exolab.jms.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import EDU.oswego.cs.dl.util.concurrent.FIFOReadWriteLock;
import EDU.oswego.cs.dl.util.concurrent.ReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.authentication.User;
import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsQueue;
import org.exolab.jms.client.JmsTopic;
import org.exolab.jms.config.DatabaseConfiguration;
import org.exolab.jms.config.RdbmsDatabaseConfiguration;
import org.exolab.jms.events.BasicEventManager;
import org.exolab.jms.events.EventHandler;
import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.messagemgr.MessageHandle;


/**
 * This adapter is a wrapper class around the persistency mechanism.
 * It isolates the client from the working specifics of the database, by
 * providing a simple straight forward interface. Furure changes to
 * the database will only require changes to the adapter.
 *
 * @author <a href="mailto:mourikis@exolab.org">Jim Mourikis</a>
 * @version $Revision: 1.7 $ $Date: 2007/04/16 02:50:21 $
 */

public class RDBMSAdapter
    extends PersistenceAdapter
    implements EventHandler {

    /**
     * The seed generator.
     */
    private final SeedGenerator _seeds;

    /**
     * The destination manager.
     */
    private final Destinations _destinations;

    /**
     * The consumer manager.
     */
    private final Consumers _consumers;

    /**
     * The message manager.
     */
    private final Messages _messages;

    /**
     * The message handles manager.
     */
    private final MessageHandles _handles;

    /**
     * The user manager.
     */
    private final Users _users;

    /**
     * The schema version number. Note this must be incremented whenever
     * The schema changes.
     */
    public static final String SCHEMA_VERSION = "V0.7.6";

    /**
     *  The JDBC ConnectionManager.
     */
    private DBConnectionManager _connectionManager = null;

    /**
     * Lock to help prevent deadlocks when administratively removing
     * destinations, while producers and consumers are actively sending
     * and receiving messages. It ensures that when a destination is in the
     * process of being removed, no other changes are occuring on the
     * messages and message_handles tables.
     */
    private ReadWriteLock _destinationLock = new FIFOReadWriteLock();

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(RDBMSAdapter.class);


    /**
     * Connects to the given db.
     *
     * @throws PersistenceException if a connection cannot be establised to the
     *                              database
     */
    public RDBMSAdapter(DatabaseConfiguration dbConfig, String driver, String url,
                 String userName, String password)
            throws PersistenceException {

        RdbmsDatabaseConfiguration config =
                dbConfig.getRdbmsDatabaseConfiguration();

        // create the connection manager, and configure it
        _connectionManager = getConnectionManager(config.getClazz());
        _connectionManager.setUser(userName);
        _connectionManager.setPassword(password);
        _connectionManager.setDriver(driver);
        _connectionManager.setURL(url);
        _connectionManager.setMaxActive(config.getMaxActive());
        _connectionManager.setMaxIdle(config.getMaxIdle());
        _connectionManager.setMinIdleTime(config.getMinIdleTime());
        _connectionManager.setEvictionInterval(config.getEvictionInterval());
        _connectionManager.setTestQuery(config.getTestQuery());
        _connectionManager.setTestBeforeUse(config.getTestBeforeUse());

        // initialisze the connection manager
        _connectionManager.init();

        Connection connection = null;
        try {
            // initialize the various caches and helper classes used to
            // execute the various SQL.
            connection = getConnection();

            String version = getSchemaVersion(connection);
            if (version == null) {
                initSchemaVersion(connection);
            } else if (!version.equals(SCHEMA_VERSION)) {
                throw new PersistenceException(
                    "Schema needs to be converted from version=" + version
                    + " to version=" + SCHEMA_VERSION
                    + "\nBack up your database, and run 'dbtool -migrate'"
                    + "to convert the schema");
            }

            _seeds = new SeedGenerator();
            _consumers = new Consumers(_seeds, connection);
            _destinations = new Destinations(_seeds, _consumers, connection);
            _consumers.setDestinations(_destinations);
            _messages = new Messages(_destinations);
            _handles = new MessageHandles(_destinations, _consumers);
            _users = new Users();
            connection.commit();
        } catch (PersistenceException exception) {
            SQLHelper.rollback(connection);
            throw exception;
        } catch (Exception exception) {
            throw new PersistenceException(
                    "Failed to initialise database adapter", exception);
        } finally {
            SQLHelper.close(connection);

        }

/*
        // check whether we should initiate automatic garbage collection
        if (dbConfig.hasGarbageCollectionInterval()) {
            _gcInterval = dbConfig.getGarbageCollectionInterval() * 1000;
            registerEvent();
        }

        if (dbConfig.hasGarbageCollectionBlockSize()) {
            _gcBlockSize = dbConfig.getGarbageCollectionBlockSize();
        }

        if (dbConfig.hasGarbageCollectionThreadPriority()) {
            _gcThreadPriority = dbConfig.getGarbageCollectionBlockSize();
            if (_gcThreadPriority < Thread.MIN_PRIORITY) {
                _gcThreadPriority = Thread.MIN_PRIORITY;
            } else if (_gcThreadPriority > Thread.MAX_PRIORITY) {
                _gcThreadPriority = Thread.MAX_PRIORITY;
            }
        }
*/
    }

    /**
     * Close the database.
     */
    public void close() {
        _consumers.close();
        _destinations.close();
    }

    // implementation of PersistenceAdapter.getLastId
    public long getLastId(Connection connection)
        throws PersistenceException {

        long lastId = -1;
        PreparedStatement query = null;
        ResultSet result = null;
        PreparedStatement insert = null;
        try {
            query = connection.prepareStatement(
                    "select maxid from message_id where id = 1");
            result = query.executeQuery();

            if (result.next()) {
                lastId = result.getInt(1);
            } else {
                // first entry create.
                insert = connection.prepareStatement(
                        "insert into message_id values (?,?)");
                insert.setInt(1, 1);
                insert.setLong(2, 0);
                insert.executeUpdate();
                lastId = 0;
            }
        } catch (Exception exception) {
            throw new PersistenceException("Failed to get last message id",
                                           exception);
        } finally {
            SQLHelper.close(result);
            SQLHelper.close(insert);
            SQLHelper.close(query);
        }

        return lastId;
    }

    // implementation of PersistenceAdapter.updateIds
    public void updateIds(Connection connection, long id)
            throws PersistenceException {
        PreparedStatement insert = null;
        try {
            insert = connection.prepareStatement(
                    "update message_id set maxId = ? where id = 1");

            insert.setLong(1, id);
            insert.executeUpdate();
        } catch (Exception exception) {
            throw new PersistenceException("Failed to update message id",
                                           exception);
        } finally {
            SQLHelper.close(insert);
        }
    }

    // implementation of PersistenceMessage.addMessage
    public void addMessage(Connection connection, MessageImpl message)
            throws PersistenceException {

        long start = 0;

        if (_log.isDebugEnabled()) {
            start = System.currentTimeMillis();
        }

        try {
            _destinationLock.readLock().acquire();
            _messages.add(connection, message);
        } catch (InterruptedException exception) {
            throw new PersistenceException("Failed to acquire lock",
                                           exception);
        } finally {
            _destinationLock.readLock().release();

            if (_log.isDebugEnabled()) {
                _log.debug("addMessage," +
                           (System.currentTimeMillis() - start));
            }
        }
    }

    // implementation of PersistenceMessage.addMessage
    public void updateMessage(Connection connection, MessageImpl message)
            throws PersistenceException {
        long start = 0;
        if (_log.isDebugEnabled()) {
            start = System.currentTimeMillis();
        }

        try {
            _destinationLock.readLock().acquire();
            _messages.update(connection, message);
        } catch (InterruptedException exception) {
            throw new PersistenceException("Failed to acquire lock",
                                           exception);
        } finally {
            _destinationLock.readLock().release();
            if (_log.isDebugEnabled()) {
                _log.debug("updateMessage," +
                           (System.currentTimeMillis() - start));
            }
        }
    }

    // implementation of PersistenceAdapter.getUnprocessedMessages
    public Vector getUnprocessedMessages(Connection connection)
            throws PersistenceException {
        long start = 0;
        if (_log.isDebugEnabled()) {
            start = System.currentTimeMillis();
        }

        try {
            return _messages.getUnprocessedMessages(connection);
        } finally {
            if (_log.isDebugEnabled()) {
                _log.debug(
                        "getUnprocessedMessages,"
                        + (System.currentTimeMillis() - start));
            }
        }
    }


    // implementation of PersistenceAdapter.removeMessage
    public void removeMessage(Connection connection, String id)
            throws PersistenceException {
        long start = 0;
        if (_log.isDebugEnabled()) {
            start = System.currentTimeMillis();
        }

        try {
            _destinationLock.readLock().acquire();
            _messages.remove(connection, id);
        } catch (InterruptedException exception) {
            throw new PersistenceException("Failed to acquire lock",
                                           exception);
        } finally {
            _destinationLock.readLock().release();
            if (_log.isDebugEnabled()) {
                _log.debug("removeMessage," +
                           (System.currentTimeMillis() - start));
            }
        }
    }

    // implementation of PersistenceAdapter.getMessage
    public MessageImpl getMessage(Connection connection, String id)
            throws PersistenceException {
        long start = 0;
        if (_log.isDebugEnabled()) {
            start = System.currentTimeMillis();
        }

        try {
            return _messages.get(connection, id);
        } finally {
            if (_log.isDebugEnabled()) {
                _log.debug(
                        "getMessage," + (System.currentTimeMillis() - start));
            }
        }
    }

    // implementation of PersistenceAdapter.getMessages
    public Vector getMessages(Connection connection, MessageHandle handle)
            throws PersistenceException {
        long start = 0;
        if (_log.isDebugEnabled()) {
            start = System.currentTimeMillis();
        }

        try {
            return _messages.getMessages(connection,
                                                   handle.getDestination()
                                                   .getName(), handle.getPriority(),
                                                   handle.getAcceptedTime());
        } finally {
            if (_log.isDebugEnabled()) {
                _log.debug(
                        "getMessages," + (System.currentTimeMillis() - start));
            }
        }
    }

    // implementation of PersistenceAdapter.addMessageHandle
    public void addMessageHandle(Connection connection, MessageHandle handle)
            throws PersistenceException {
        long start = 0;
        if (_log.isDebugEnabled()) {
            start = System.currentTimeMillis();
        }

        try {
            _destinationLock.readLock().acquire();
            _handles.addMessageHandle(connection, handle);
        } catch (InterruptedException exception) {
            throw new PersistenceException("Failed to acquire lock",
                                           exception);
        } finally {
            _destinationLock.readLock().release();
            if (_log.isDebugEnabled()) {
                _log.debug(
                        "addMessageHandle,"
                        + (System.currentTimeMillis() - start));
            }
        }
    }

    // implementation of PersistenceAdapter.updateMessageHandle
    public void updateMessageHandle(Connection connection,
                                    MessageHandle handle)
            throws PersistenceException {
        long start = 0;
        if (_log.isDebugEnabled()) {
            start = System.currentTimeMillis();
        }

        try {
            _destinationLock.readLock().acquire();
            _handles.updateMessageHandle(connection, handle);
        } catch (InterruptedException exception) {
            throw new PersistenceException("Failed to acquire lock",
                                           exception);
        } finally {
            _destinationLock.readLock().release();
            if (_log.isDebugEnabled()) {
                _log.debug(
                        "updateMessageHandle,"
                        + (System.currentTimeMillis() - start));
            }
        }
    }

    // implementation of PersistenceAdapter.removeMessageHandle
    public void removeMessageHandle(Connection connection,
                                    MessageHandle handle)
            throws PersistenceException {
        long start = 0;
        if (_log.isDebugEnabled()) {
            start = System.currentTimeMillis();
        }

        try {
            _destinationLock.readLock().acquire();
            _handles.removeMessageHandle(connection, handle);
        } catch (InterruptedException exception) {
            throw new PersistenceException("Failed to acquire lock",
                                           exception);
        } finally {
            _destinationLock.readLock().release();
            if (_log.isDebugEnabled()) {
                _log.debug(
                        "removeMessageHandle,"
                        + (System.currentTimeMillis() - start));
            }
        }
    }

    // implementation of PersistenceAdapter.getMessageHandles
    public Vector getMessageHandles(Connection connection,
                                    JmsDestination destination, String name)
            throws PersistenceException {
        long start = 0;
        if (_log.isDebugEnabled()) {
            start = System.currentTimeMillis();
        }

        try {
            return _handles.getMessageHandles(connection,
                                                               destination.getName(),
                                                               name);
        } finally {
            if (_log.isDebugEnabled()) {
                _log.debug("getMessageHandles,"
                           + (System.currentTimeMillis() - start));
            }
        }
    }

    // implementation of PersistenceAdapter.addDurableConsumer
    public void addDurableConsumer(Connection connection, String topic,
                                   String consumer)
            throws PersistenceException {

        try {
            _destinationLock.readLock().acquire();
            _consumers.add(connection, topic, consumer);
        } catch (InterruptedException exception) {
            throw new PersistenceException("Failed to acquire lock",
                                           exception);
        } finally {
            _destinationLock.readLock().release();
        }
    }

    // implementation of PersistenceAdapter.removeDurableConsumer
    public void removeDurableConsumer(Connection connection, String consumer)
            throws PersistenceException {

        try {
            _destinationLock.readLock().acquire();
            _consumers.remove(connection, consumer);
        } catch (InterruptedException exception) {
            throw new PersistenceException("Failed to acquire lock",
                                           exception);
        } finally {
            _destinationLock.readLock().release();
        }
    }

    // implementation of PersistenceAdapter.getDurableConsumers
    public Enumeration getDurableConsumers(Connection connection, String topic)
            throws PersistenceException {
        return _consumers.getDurableConsumers(topic).elements();
    }

    // implementation of PersistenceAdapter.getAllDurableConsumers
    public HashMap getAllDurableConsumers(Connection connection)
            throws PersistenceException {

        return _consumers.getAllDurableConsumers();
    }

    // implementation of PersistenceAdapter.durableConsumerExists
    public boolean durableConsumerExists(Connection connection, String name)
            throws PersistenceException {

        return _consumers.exists(name);
    }

    // implementation of PersistenceAdapter.addDestination
    public void addDestination(Connection connection, String name,
                               boolean queue)
            throws PersistenceException {

        JmsDestination destination = (queue)
                ? (JmsDestination) new JmsQueue(name)
                : (JmsDestination) new JmsTopic(name);

        // create the destination. If the destination is also
        // a queue create a special consumer for it.
        try {
            _destinationLock.readLock().acquire();
            _destinations.add(connection, destination);
            if (queue) {
                _consumers.add(connection, name, name);
            }
        } catch (InterruptedException exception) {
            throw new PersistenceException("Failed to acquire lock",
                                           exception);
        } finally {
            _destinationLock.readLock().release();
        }
    }

    // implementation of PersistenceAdapter.removeDestination
    public void removeDestination(Connection connection, String name)
            throws PersistenceException {

        JmsDestination destination = _destinations.get(name);
        if (destination != null) {
            try {
                _destinationLock.writeLock().acquire();
                _destinations.remove(connection, destination);
            } catch (InterruptedException exception) {
                throw new PersistenceException("Failed to acquire lock",
                                               exception);
            } finally {
                _destinationLock.writeLock().release();
            }
        }
    }

    // implementation of PersistenceAdapter.getAllDestinations
    public Enumeration getAllDestinations(Connection connection)
            throws PersistenceException {

        return _destinations.getDestinations().elements();
    }

    // implementation of PersistenceAdapter.checkDestination
    public boolean checkDestination(Connection connection, String name)
            throws PersistenceException {

        return (_destinations.get(name) != null);
    }

    // implementation of getQueueMessageCount
    public int getQueueMessageCount(Connection connection, String name)
            throws PersistenceException {

        return _handles.getMessageCount(connection, name,
                                                         name);
    }

    // implementation of PersistenceAdapter.getQueueMessageCount
    public int getDurableConsumerMessageCount(Connection connection,
                                              String destination, String name)
            throws PersistenceException {

        return _handles.getMessageCount(connection,
                                                         destination, name);
    }

    // implementation of PersistenceAdapter.getQueueMessageCount
    public void removeExpiredMessages(Connection connection)
            throws PersistenceException {

        _messages.removeExpiredMessages(connection);
    }

    // implementation of PersistenceAdapter.removeExpiredMessageHandles
    public void removeExpiredMessageHandles(Connection connection,
                                            String consumer)
            throws PersistenceException {

        _handles.removeExpiredMessageHandles(connection,
                                                              consumer);
    }

    // implementation of PersistenceAdapter.getNonExpiredMessages
    public Vector getNonExpiredMessages(Connection connection,
                                        JmsDestination destination)
            throws PersistenceException {

        return _messages.getNonExpiredMessages(connection,
                                                         destination);
    }

    // implementation of EventHandler.handleEvent
    public void handleEvent(int event, Object callback, long time) {
        // disabled, as per bug 816895 - Exception in purgeMessages
//          if (event == COLLECT_DATABASE_GARBAGE_EVENT) {
//              // collect garbage now, but before doing so change the thread
//              // priority to low.
//              try {
//                  Thread.currentThread().setPriority(_gcThreadPriority);
//                  purgeMessages();
//              } finally {
//                  Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
//                  registerEvent();
//              }
//          }
    }

    /**
     * Return a connection to the database from the pool of connections. It will
     * throw an PersistenceException if it cannot retrieve a connection. The
     * client should close the connection normally, since the pool is a
     * connection event listener.
     *
     * @return Connection - a pooled connection or null
     * @throws PersistenceException - if it cannot retrieve a connection
     */
    public Connection getConnection()
            throws PersistenceException {
        return _connectionManager.getConnection();
    }

    /**
     * Return a reference to the DBConnectionManager
     *
     * @return DBConnectionManager
     */
    public DBConnectionManager getDBConnectionManager() {
        return _connectionManager;
    }

    public void addUser(Connection connection, User user)
            throws PersistenceException {
        _users.add(connection, user);
    }

    public Enumeration getAllUsers(Connection connection)
            throws PersistenceException {
        return _users.getAllUsers(connection).elements();
    }

    public User getUser(Connection connection, User user)
            throws PersistenceException {
        return _users.get(connection, user);
    }

    public void removeUser(Connection connection, User user)
            throws PersistenceException {
        _users.remove(connection, user);
    }

    public void updateUser(Connection connection, User user)
            throws PersistenceException {
        _users.update(connection, user);
    }

    /**
     * Incrementally purge all processed messages from the database.
     * @todo this needs to be revisited. See bug 816895
     * - existing expired messages are purged at startup
     * - messages received that subsequently expire while the server is
     *   running are removed individually.
     * - not clear how the previous implementation ever worked.
     *   The Messages.getMessageIds() method returns all messages, not
     *   just those processed, nor is it clear that the processed flag
     *   is ever non-zero.
     *   The current implementation (as a fix for bug 816895 - Exception in
     *   purgeMessages) simply delegates to removeExpiredMessages()
     *
     * @return the number of messages deleted
     */
    public synchronized int purgeMessages() {
        // int deleted = 0;

        Connection connection = null;
        try {
            connection = getConnection();
            removeExpiredMessages(connection);
            connection.commit();
        } catch (Exception exception) {
            _log.error("Exception in purgeMessages", exception);
        } finally {
            SQLHelper.close(connection);
        }
        return 0;

//          if (connection == null) {
//              return 0;
//          }

//          // we have a valid connection so we can proceed
//          try {
//              long stime = System.currentTimeMillis();
//              HashMap msgids = _messages.getMessageIds(
//                  connection, _lastTime, _gcBlockSize);

//              // if there are no messages then reset the last time to
//              // 0 and break;
//              if (msgids.size() > 0) {
//                  // find the minimum and maximum..we can improve the way we
//                  // do this.
//                  Iterator iter = msgids.values().iterator();
//                  long min = -1;
//                  long max = -1;

//                  while (iter.hasNext()) {
//                      Long id = (Long) iter.next();
//                      if ((min == -1) &&
//                          (max == -1)) {
//                          min = id.longValue();
//                          max = id.longValue();
//                      }

//                      if (id.longValue() < min) {
//                          min = id.longValue();
//                      } else if (id.longValue() > max) {
//                          max = id.longValue();
//                      }
//                  }

//                  // set the last time for the next iteration unless the
//                  // the size of the msgids is less than the gcBlockSize.
//                  // If the later is the case then reset the last time.
//                  // This is in preparation for the next pass through this
//                  // method.
//                  if (msgids.size() < _gcBlockSize) {
//                      _lastTime = 0;
//                  } else {
//                      _lastTime = max;
//                  }

//                  // now iterate through the message list and delete the
//                  // messages that do not have corresponding handles.
//                  Vector hdlids = _handles.getMessageIds(connection, min, max);
//                  iter = msgids.keySet().iterator();
//                  while (iter.hasNext()) {
//                      String id = (String) iter.next();
//                      if (!hdlids.contains(id)) {
//                          // this message is not referenced by anyone so we can
//                          // delete it
//                          _messages.remove(connection, id);
//                          deleted++;
//                      }
//                  }
//                  connection.commit();
//              } else {
//                  // reset the lastTime
//                  _lastTime = 0;
//              }
//              _log.debug("DBGC Deleted " + deleted + " messages and took "
//                  + (System.currentTimeMillis() - stime) +
//                  "ms to complete.");
//          } catch (Exception exception) {
//              try {
//                  connection.rollback();
//              } catch (Exception nested) {
//                  // ignore this exception
//              }
//              _log.error("Exception in purgeMessages", exception);
//              deleted = 0;
//          } finally {
//              try {
//                  connection.close();
//              } catch (Exception nested) {
//                  // ignore
//              }
//          }
//
//        return deleted;
    }

    /**
     * Get the schema version
     *
     * @param connection the connection to use
     * @return the schema version, or null, if no version has been initialised
     * @throws PersistenceException for any related persistence exception
     */
    private String getSchemaVersion(Connection connection)
            throws PersistenceException {

        String version = null;
        PreparedStatement query = null;
        ResultSet result = null;
        try {
            query = connection.prepareStatement(
                    "select version from system_data where id = 1");
            result = query.executeQuery();
            if (result.next()) {
                version = result.getString(1);
            }
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to get the schema version",
                                           exception);
        } finally {
            SQLHelper.close(result);
            SQLHelper.close(query);

        }
        return version;
    }

    /**
     * Initialise the schema version
     *
     * @param connection the connection to use
     */
    private void initSchemaVersion(Connection connection)
            throws PersistenceException {

        _log.info("Initialising schema version " + SCHEMA_VERSION);
        PreparedStatement insert = null;
        try {
            insert = connection.prepareStatement("insert into system_data (id, version, creationDate) "
                                                 + "values (?,?,?)");
            insert.setInt(1, 1);
            insert.setString(2, SCHEMA_VERSION);
            insert.setDate(3, new Date(System.currentTimeMillis()));
            insert.executeUpdate();

        } catch (SQLException exception) {
            throw new PersistenceException(
                    "Failed to initialise schema version", exception);
        } finally {
            SQLHelper.close(insert);
        }
    }

    /**
     * Register an event to collect and remove processed messages with the
     * {@link BasicEventManager}
     */
//   private void registerEvent() {
//        try {
        // disabled, as per bug 816895 - Exception in purgeMessages
//              BasicEventManager.instance().registerEventRelative(
//                  new Event(COLLECT_DATABASE_GARBAGE_EVENT, this, null),
//                  _gcInterval);
//          } catch (IllegalEventDefinedException exception) {
//              _log.error("registerEvent failed", exception);
//          }
//   }

    /**
     * Creates a {@link DBConnectionManager} using its fully qualified class
     * name
     *
     * @param className the fully qualified class name
     * @throws PersistenceException if it cannot be created
     */
    private DBConnectionManager getConnectionManager(String className)
            throws PersistenceException {

        DBConnectionManager result = null;
        Class clazz = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            if (loader != null) {
                clazz = loader.loadClass(className);
            }
        } catch (ClassNotFoundException ignore) {
        }
        try {
            if (clazz == null) {
                clazz = Class.forName(className);
            }
        } catch (ClassNotFoundException exception) {
            throw new PersistenceException("Failed to locate connection manager implementation: "
                                           + className, exception);
        }

        try {
            result = (DBConnectionManager) clazz.newInstance();
        } catch (Exception exception) {
            throw new PersistenceException(
                    "Failed to create connection manager", exception);
        }

        return result;
    }

}
