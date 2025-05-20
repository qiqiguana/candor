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
 * Copyright 2001-2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: DatabaseService.java,v 1.4 2006/02/23 11:17:39 tanderson Exp $
 */
package org.exolab.jms.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.jms.common.threads.ThreadListener;
import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.DatabaseConfiguration;
import org.exolab.jms.config.RdbmsDatabaseConfiguration;
import org.exolab.jms.service.Service;
import org.exolab.jms.service.ServiceException;
import org.exolab.jms.service.ServiceThreadListener;


/**
 * The DatabaseService is used for managing the persistence aspect of this
 * project.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @version $Revision: 1.4 $ $Date: 2006/02/23 11:17:39 $
 */
public class DatabaseService extends Service {

    /**
     * The configuration.
     */
    private final DatabaseConfiguration _config;

    /**
     * The persistence adapter.
     */
    private PersistenceAdapter _adapter;

    /**
     * Thread listener.
     */
    private ServiceThreadListener _listener;

    /**
     * State monitor.
     */
    private ThreadListener _monitor;

    /**
     * The service state associated with the current thread.
     */
    private static final ThreadLocal _state = new ThreadLocal();

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(DatabaseService.class);


    /**
     * Construct a new <code>DatabaseService</code>.
     *
     * @param config the configuration
     */
    public DatabaseService(Configuration config) {
        super("DatabaseService");
        _config = config.getDatabaseConfiguration();
        _monitor = new Monitor();
    }

    /**
     * Sets the service thread listener.
     *
     * @param listener the service thread listener
     */
    public void setServiceThreadListener(ServiceThreadListener listener) {
        _listener = listener;
    }

    /**
     * Returns the database service associated with the current thread.
     *
     * @return the database service associated with the current thread
     * @throws PersistenceException if no instance is registered
     */
    public static DatabaseService getInstance() throws PersistenceException {
        State state = (State) _state.get();
        if (state == null) {
            throw new PersistenceException("No DatabaseService registered");
        }
        return state.getInstance();
    }

    /**
     * Returns the {@link PersistenceAdapter} created by this service.
     *
     * @return the persistence adapter
     */
    public PersistenceAdapter getAdapter() {
        return _adapter;
    }

    /**
     * Begin a transaction.
     *
     * @throws PersistenceException if a transaction cannot be started
     */
    public void begin() throws PersistenceException {
        State state = (State) _state.get();
        if (state == null) {
            _state.set(new State());
        } else {
            if (state.getInstance() != this) {
                throw new PersistenceException(
                        "State not associated with current service");
            }
            _log.error("Transaction in progress, allocated at ", state.STACK);
            throw new PersistenceException("Transaction already in progress");

        }
    }

    /**
     * Returns the connection associated with the current thread.
     *
     * @return the connection associated with the current thread
     * @throws PersistenceException if no connection is associated
     */
    public Connection getConnection() throws PersistenceException {
        State state = getState();
        if (state.getConnection() == null) {
            state.setConnection(_adapter.getConnection());
        }
        return state.getConnection();
    }

    /**
     * Commit the current transaction.
     *
     * @throws PersistenceException if the transaction can't be committed
     */
    public void commit() throws PersistenceException {
        State state = getState();
        Connection connection = state.getConnection();
        try {
            if (connection != null) {
                connection.commit();
            }
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to commit", exception);
        } finally {
            SQLHelper.close(connection);
            _state.set(null);
        }
    }

    /**
     * Rollback the current transaction.
     *
     * @throws PersistenceException if the transaction can't be rolled back
     */
    public void rollback() throws PersistenceException {
        State state = getState();
        Connection connection = state.getConnection();
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to rollback", exception);
        } finally {
            SQLHelper.close(connection);
            _state.set(null);
        }
    }

    /**
     * Determines if a transaction is in progress.
     *
     * @return <code>true</code> if a transaction is in progress; otherwise
     *         <code>false</code>
     */
    public boolean isTransacted() {
        return (_state.get() != null);
    }

    /**
     * Start the service.
     *
     * @throws ServiceException if the service fails to start
     */
    protected void doStart() throws ServiceException {
        if (_listener != null) {
            _listener.addThreadListener(_monitor);
        } else {
            _log.info("Not monitoring service threads");
        }

        _adapter = createAdapter(_config);

        // remove the expired messages
        try {
            begin();
            Connection connection = getConnection();

            getAdapter().removeExpiredMessages(connection);
            _log.info("Removed expired messages.");
            commit();
        } catch (PersistenceException exception) {
            try {
                rollback();
            } catch (PersistenceException ignore) {
                // no-op
            }
            throw exception;
        } catch (Exception exception) {
            // rethrow as an appropriate exception
            throw new ServiceException("Failed to start the DatabaseService",
                    exception);
        }
    }

    /**
     * Stop the service.
     *
     * @throws ServiceException if the service fails to stop
     */
    protected void doStop() throws ServiceException {
        if (_listener != null) {
            _listener.removeThreadListener(_monitor);
        }
        _adapter.close();
        _state.set(null);
    }

    /**
     * Returns the current transaction state.
     *
     * @return the current transaction state
     * @throws PersistenceException if there is no current transaction
     */
    private State getState() throws PersistenceException {
        State state = (State) _state.get();
        if (state == null) {
            throw new PersistenceException("No transaction in progress");
        }
        if (state.getInstance() != this) {
            throw new PersistenceException(
                    "State not associated with current service");
        }
        return state;
    }

    /**
     * Create an instance of an persistence adapter using the specified database
     * configuration.
     *
     * @param dbConfig database configuration
     * @return the created adapter
     * @throws PersistenceException if the adapter cant be created
     */
    private PersistenceAdapter createAdapter(
            DatabaseConfiguration dbConfig) throws PersistenceException {
        PersistenceAdapter adapter = null;
        RdbmsDatabaseConfiguration
                config = dbConfig.getRdbmsDatabaseConfiguration();

        _log.info("Creating RdbmsAdapter for "
                + config.getDriver());
        adapter = new RDBMSAdapter(dbConfig, config.getDriver(),
                config.getUrl(),
                config.getUser(),
                config.getPassword());

        return adapter;
    }

    class State {

        public final Exception STACK = new Exception();

        private Connection _connection;

        public DatabaseService getInstance() {
            return DatabaseService.this;
        }

        public Connection getConnection() {
            return _connection;
        }

        public void setConnection(Connection connection) {
            _connection = connection;
        }
    }

    static class Monitor implements ThreadListener {

        public void begin(Runnable command) {
        }

        public void end(Runnable command) {
            State state = (State) _state.get();
            if (state != null) {
                _state.set(null);
                _log.error("Transaction not finished by " + command
                        + ". Allocated at ", state.STACK);
                SQLHelper.close(state.getConnection());
            }
        }

    }

}


