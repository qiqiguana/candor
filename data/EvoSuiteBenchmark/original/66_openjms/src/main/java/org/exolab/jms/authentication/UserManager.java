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
 * Copyright 2003-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: UserManager.java,v 1.4 2005/12/23 12:17:45 tanderson Exp $
 */
package org.exolab.jms.authentication;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.SecurityConfiguration;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.persistence.PersistenceAdapter;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.service.Service;
import org.exolab.jms.service.ServiceException;


/**
 * The user manager is responsible for creating and managing users.
 *
 * @author <a href="mailto:knut@lerpold.no">Knut Lerpold</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2005/12/23 12:17:45 $
 */
public class UserManager extends Service {

    /**
     * Map of username -> User instances.
     */
    private HashMap _userCache = new HashMap();

    /**
     * The configuration.
     */
    private final Configuration _config;

    /**
     * The database service.
     */
    private final DatabaseService _database;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(UserManager.class);


    /**
     * Construct a new <code>UserManager</code>.
     *
     * @param config   the configuration
     * @param database the database service
     */
    public UserManager(Configuration config, DatabaseService database) {
        if (config == null) {
            throw new IllegalArgumentException("Argument 'config' is null");
        }
        if (database == null) {
            throw new IllegalArgumentException("Argument 'database' is null");
        }
        _config = config;
        _database = database;
    }

    /**
     * Create a new user.
     *
     * @param user the user to create
     * @return <code>true</code> if the user is created otherwise
     *         <code>false</code>
     */
    public synchronized boolean createUser(User user) {
        boolean success = false;
        PersistenceAdapter adapter = _database.getAdapter();

        if (_userCache.get(user.getUsername()) == null) {
            try {
                _database.begin();
                Connection connection = _database.getConnection();
                adapter.addUser(connection, user);
                addToUserCache(user);
                _database.commit();
                success = true;
            } catch (Exception exception) {
                _log.error("Failed to create user", exception);
                try {
                    _database.rollback();
                } catch (PersistenceException error) {
                    _log.error(error, error);
                }
            }
        }

        return success;
    }

    /**
     * Update an user. Only possible update is password.
     *
     * @param user the user to update
     * @return <code>true</code> if password is updated otherwise
     *         <code>false</code>
     */
    public synchronized boolean updateUser(User user) {
        boolean success = false;
        PersistenceAdapter adapter = _database.getAdapter();

        if (_userCache.get(user.getUsername()) != null) {
            try {
                _database.begin();
                Connection connection = _database.getConnection();
                adapter.updateUser(connection, user);
                _database.commit();
                addToUserCache(user);
                success = true;
            } catch (Exception exception) {
                _log.error("Failed to update user", exception);
                rollback();
            }
        }

        return success;
    }

    /**
     * Delete an user.
     *
     * @param user the userobject containing the username
     * @return <code>true</code> if the is removed otherwise <code>false</code>
     */
    public synchronized boolean deleteUser(User user) {
        boolean success = false;
        PersistenceAdapter adapter = _database.getAdapter();

        if (_userCache.get(user.getUsername()) != null) {
            try {
                _database.begin();
                Connection connection = _database.getConnection();
                adapter.removeUser(connection, user);
                removeFromUserCache(user);
                success = true;
                _database.commit();
            } catch (Exception exception) {
                _log.error("Failed to remove user", exception);
                rollback();
            }
        }
        return success;
    }

    /**
     * Return a user.
     *
     * @param user the user  containing the username
     * @return the user, or <code>null</code> if none exists.
     */
    public synchronized User getUser(User user) {
        return (User) _userCache.get(user.getUsername());
    }

    /**
     * Return a list of user names currently supported by the user manager. This
     * includes all types of users.
     *
     * @return an enumeration of the user names
     */
    public Iterator userNames() {
        return _userCache.keySet().iterator();
    }

    /**
     * Determines if a user's name and password are valid.
     *
     * @param username the user's name
     * @param password the user's password
     * @return <code>true</code> if the name and password are valid, otherwise
     *         <code>false</code>
     */
    public synchronized boolean validateUser(String username,
                                             String password) {
        boolean result = false;

        SecurityConfiguration config = _config.getSecurityConfiguration();
        if (!config.getSecurityEnabled()) {
            // security disabled
            result = true;
        } else {
            User user = (User) _userCache.get(username);
            if (user != null && user.getPassword().equals(password)) {
                result = true;
            }
        }

        return result;
    }

    /**
     * Start the service.
     *
     * @throws ServiceException if the service fails to start
     */
    protected void doStart() throws ServiceException {
        init();
    }

    /**
     * Stop the service.
     */
    protected synchronized void doStop() {
        _userCache.clear();
    }

    /**
     * Initialise user manager.
     *
     * @throws ServiceException if the user manager cannot be initialised
     */
    protected void init() throws ServiceException {
        try {
            _database.begin();
            Connection connection = _database.getConnection();

            Enumeration iter = _database.getAdapter().getAllUsers(connection);
            _database.commit();

            while (iter.hasMoreElements()) {
                // add each user to the cache
                User user = (User) iter.nextElement();
                addToUserCache(user);
            }
        } catch (Exception exception) {
            _log.error("Failed to initialise UserManager", exception);
            rollback();
            throw new ServiceException(exception);
        }

        registerConfiguredUsers();
    }

    /**
     * Add the specified entry to the user cache.
     *
     * @param user the user to add
     */
    protected void addToUserCache(User user) {
        _userCache.put(user.getUsername(), user);
    }

    /**
     * Remove the specified user from the cache.
     *
     * @param user the user to remove
     */
    protected void removeFromUserCache(User user) {
        _userCache.remove(user.getUsername());
    }

    /**
     * Registers users specified in the configuration.
     */
    protected void registerConfiguredUsers() {
        if (_config.getUsers() != null) {
            org.exolab.jms.config.User[] users = _config.getUsers().getUser();
            for (int i = 0; i < users.length; ++i) {
                User user = new User(users[i].getName(),
                                     users[i].getPassword());
                createUser(user);
            }
        }
    }

    /**
     * Rollback the current transaction, logging any error.
     */
    private void rollback() {
        try {
            _database.rollback();
        } catch (PersistenceException exception) {
            _log.error(exception, exception);
        }
    }

}
