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
 */
package org.exolab.jms.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;


/**
 * Wrapper around the DBCP connection manager.
 *
 * @version     $Revision: 1.2 $ $Date: 2005/12/01 13:50:17 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
public class DBCPConnectionManager extends AbstractConnectionManager {

    /**
     * The data source.
     */
    private DataSource _dataSource;

    /**
     * Default constructor.
     */
    public DBCPConnectionManager() {
    }

    /**
     * Initialise the connection manager. This must be called before a call to
     * {@link #getConnection} is made and after all the properties have been
     * set.
     */
    public void init() throws PersistenceException {
        long evictionIntervalMS = getEvictionInterval() * 1000;
        long minIdleTimeMS = getMinIdleTime() * 1000;
        
        String testQuery = getTestQuery();
        boolean testWhileIdle = false;

        if (testQuery != null) {
            testWhileIdle = true;
        }
        
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername(getUser());
        dataSource.setPassword(getPassword());
        dataSource.setDriverClassName(getDriver());
        dataSource.setUrl(getURL());
        dataSource.setDefaultAutoCommit(false);
        dataSource.setPoolPreparedStatements(true);

        // configure pooling
        dataSource.setMaxActive(getMaxActive());
        dataSource.setMaxIdle(getMaxIdle());

        dataSource.setMaxWait(60 * 1000);
        // force the pool to timeout to avoid deadlocks

        dataSource.setValidationQuery(testQuery);
        dataSource.setTestOnBorrow(getTestBeforeUse());
        dataSource.setTimeBetweenEvictionRunsMillis(evictionIntervalMS);
        dataSource.setMinEvictableIdleTimeMillis(minIdleTimeMS);
        dataSource.setTestWhileIdle(testWhileIdle);

        _dataSource = dataSource;
    }

    /**
     * Retrieve a connection to the underlying database from the pool of
     * connections.
     *
     * @return a connection to the database
     * @throws PersistenceException if a connection cannot be retrieved
     */
    public Connection getConnection() throws PersistenceException {
        Connection connection;
        try {
            connection = _dataSource.getConnection();
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to get pooled connection",
                exception);
        }

        return connection;
    }

}
