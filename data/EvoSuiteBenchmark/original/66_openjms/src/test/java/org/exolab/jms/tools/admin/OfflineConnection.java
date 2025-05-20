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
 * Copyright 2000,2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: OfflineConnection.java,v 1.3 2006/02/23 11:02:57 tanderson Exp $
 *
 * Date         Author  Changes
 * $Date	    jimm    Created
 */


package org.exolab.jms.tools.admin;

import java.awt.*;
import java.sql.Connection;
import java.util.Enumeration;
import javax.swing.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.jms.authentication.User;
import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.DatabaseConfiguration;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.service.ServiceException;


/**
 * Connect directly to the Persistent store to retrieve information and perfrom
 * updates.
 * <p/>
 * <P> Note: If the OpenJMSServer is active, this connection will fail, since it
 * requires and exclusive lock on the database to avoid database corruption.
 * Similarly, if this connection is active the OpenJMSServer cannot be started
 * for the same reasons.
 *
 * @author <a href="mailto:mourikis@exolab.org">Jim Mourikis</a>
 * @version $Revision: 1.3 $ $Date: 2006/02/23 11:02:57 $
 * @see AbstractAdminConnection
 * @see AdminMgr
 */
public class OfflineConnection extends AbstractAdminConnection {


    // The parent Gui
    private Component _parent;

    /**
     * The database service.
     */
    private DatabaseService _database;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(OfflineConnection.class);


    /**
     * Connect to the RMIAdmin Server if in online mode, or open the database
     * and update the data directly in offline mode.
     *
     * @param parent The component parent.
     * @throws OfflineConnectionException When the database cannot be opened
     */
    public OfflineConnection(Component parent, Configuration config)
            throws OfflineConnectionException {
        try {
            if (_instance == null) {
                _parent = parent;
                _database = new DatabaseService(config);
                _database.start();

                DatabaseConfiguration dbconfig =
                        config.getDatabaseConfiguration();

                // determine the database type and instantiate the appropriate
                // database adapter
                if (dbconfig.getRdbmsDatabaseConfiguration() != null) {
                    _database.getAdapter();
                    _instance = this;
                }
            } else {
                throw new OfflineConnectionException("Already connected");
            }
        } catch (Exception err) {
            _log.error(err.getMessage(), err);
            throw new OfflineConnectionException
                    ("Database Error: " + err.getMessage());
        }
    }

    // implementation of AbstractAdminConnection.close
    public void close() {
        try {
            _database.stop();
        } catch (ServiceException exception) {
            _log.error(exception, exception);
        }
        _instance = null;
    }

    // implementation of AbstractAdminConnection.addDurableConsumer
    public boolean addDurableConsumer(String topic, String name) {
        boolean result = false;
        try {
            _database.begin();
            Connection connection = _database.getConnection();
            _database.getAdapter().addDurableConsumer(connection, topic,
                    name);
            _database.commit();
            result = true;
        } catch (PersistenceException exception) {
            error("Failed to add durable consumer=" + name + " for topic="
                    + topic, exception);
        }

        return result;
    }

    // implementation of AbstractAdminConnection.removeDurableConsumer
    public boolean removeDurableConsumer(String name) {
        boolean result = false;

        try {
            _database.begin();
            Connection connection = _database.getConnection();

            _database.getAdapter().removeDurableConsumer(connection, name);
            _database.commit();
            result = true;
        } catch (PersistenceException exception) {
            error("Failed to remove durable consumer=" + name, exception);
        }
        return result;
    }

    // implementation of AbstractAdminConnection.unregisterConsumer
    public boolean unregisterConsumer(String name) {
        return false;
    }

    // implementation of AbstractAdminConnection.isConnected
    public boolean isConnected(String name) {
        return false;
    }

    // implementation of AbstractAdminConnection.getAllDestinations
    public Enumeration getAllDestinations() {
        Enumeration result = null;
        try {
            _database.begin();
            Connection connection = _database.getConnection();
            result = _database.getAdapter().getAllDestinations(connection);
            _database.commit();
        } catch (PersistenceException exception) {
            error("Failed to get destinations", exception);
        }

        return result;
    }

    // implementation of AbstractAdminConnection.addDestination
    public boolean addDestination(String destination, boolean isQueue) {
        boolean success = false;

        try {
            _database.begin();
            Connection connection = _database.getConnection();

            _database.getAdapter().addDestination(connection,
                    destination, isQueue);

            _database.commit();
            success = true;
        } catch (PersistenceException exception) {
            error("Failed to add destination=" + destination, exception);
        }

        return success;
    }

    // implementation of AbstractAdminConnection.getDurableConsumerMessageCount
    public int getDurableConsumerMessageCount(String topic, String name) {
        int count = -1;

        try {
            _database.begin();
            Connection connection = _database.getConnection();

            count = _database.getAdapter().getDurableConsumerMessageCount(
                    connection, topic, name);
            _database.commit();
        } catch (PersistenceException exception) {
            error("Failed to get message count for topic=" + topic
                    + ", name=" + name, exception);
        }
        return count;
    }

    // implementation of AbstractAdminConnection.getQueueMessageCount
    public int getQueueMessageCount(String queue) {
        int count = -1;

        try {
            _database.begin();
            Connection connection = _database.getConnection();

            count = _database.getAdapter().getQueueMessageCount(connection,
                    queue);
            _database.commit();
        } catch (PersistenceException exception) {
            error("Failed to get message count for queue=" + queue, exception);
        }

        return count;
    }

    // implementation of AbstractAdminConnection.durableConsumerExists
    public boolean durableConsumerExists(String name) {
        boolean result = false;

        try {
            _database.begin();
            Connection connection = _database.getConnection();

            result = _database.getAdapter().durableConsumerExists(connection,
                    name);
            _database.commit();
        } catch (PersistenceException exception) {
            error("Failed to determine if consumer exists: " + name, exception);
        }

        return result;
    }

    // implementation of AbstractAdminConnection.getDurableConsumers
    public Enumeration getDurableConsumers(String topic) {
        Enumeration result = null;

        try {
            _database.begin();
            Connection connection = _database.getConnection();

            result = _database.getAdapter().getDurableConsumers(connection,
                    topic);
            _database.commit();
        } catch (PersistenceException exception) {
            error("Failed to retrieve durable consumers", exception);
        }
        return result;
    }

    // implementation of AbstractAdminConnection.removeDestination
    public boolean removeDestination(String destination) {
        boolean result = false;

        try {
            _database.begin();
            Connection connection = _database.getConnection();

            _database.getAdapter().removeDestination(connection, destination);
            _database.commit();
            result = true;
        } catch (PersistenceException exception) {
            error("Failed to remove destination=" + destination, exception);
        }

        return result;
    }

    // implementation of AbstractAdminConnection.purgeMessages
    public int purgeMessages() {
        return _database.getAdapter().purgeMessages();
    }

    // implementation of AbstractAdminConnection.stopServer
    public void stopServer() {
        JOptionPane.showMessageDialog(
                _parent, "Not available in offline mode",
                "Shutdown Error", JOptionPane.ERROR_MESSAGE);
    }

    // implementation of AbstractAdminConnection.addUser
    public boolean addUser(String username, String password) {
        boolean success = false;

        try {
            _database.begin();
            Connection connection = _database.getConnection();

            _database.getAdapter().addUser(connection,
                    new User(username, password));
            _database.commit();
            success = true;
        } catch (PersistenceException exception) {
            error("Failed to add user=" + username, exception);
        }
        return success;
    }

    // implementation of AbstractAdminConnection.changePassord
    public boolean changePassword(String username, String password) {

        boolean success = false;

        try {
            _database.begin();
            Connection connection = _database.getConnection();

            _database.getAdapter().updateUser(connection,
                    new User(username, password));
            _database.commit();
            success = true;
        } catch (PersistenceException exception) {
            error("Failed to change password for user=" + username, exception);
        }
        return success;
    }

    // implementation of AbstractAdminConnection.removeUser
    public boolean removeUser(String username) {
        boolean result = false;

        try {
            _database.begin();
            Connection connection = _database.getConnection();

            _database.getAdapter().removeUser(connection,
                    new User(username, null));
            _database.commit();
            result = true;
        } catch (PersistenceException exception) {
            error("Failed to remove user=" + username, exception);
        }

        return result;
    }

    // implementation of AbstractAdminConnection.getAllUsers
    public Enumeration getAllUsers() {
        Enumeration result = null;

        try {
            _database.begin();
            Connection connection = _database.getConnection();

            result = _database.getAdapter().getAllUsers(connection);
            _database.commit();
        } catch (PersistenceException exception) {
            rollback();
        }

        return result;
    }

    /**
     * Helper to log an error and rollback.
     *
     * @param message   the error messge
     * @param exception the exception
     */
    private void error(String message, PersistenceException exception) {
        _log.error(message, exception);
        rollback();
    }

    /**
     * Rollback the current transaction, logging any error.
     */
    private void rollback() {
        try {
            _database.rollback();
        } catch (PersistenceException exception) {
            _log.warn(exception, exception);
        }
    }

}
