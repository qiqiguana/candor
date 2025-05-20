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
 * Copyright 2001-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: RDBMSTool.java,v 1.4 2005/11/12 12:47:37 tanderson Exp $
 */
package org.exolab.jms.tools.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.RdbmsDatabaseConfiguration;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.persistence.SQLHelper;


/**
 * This class provides support for creating and destroying tables in RDBMS
 * databases.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2005/11/12 12:47:37 $
 */
public class RDBMSTool {

    /**
     * The connection to the database.
     */
    private Connection _connection = null;

    /**
     * The schema browser.
     */
    private SchemaBrowser _browser = null;

    /**
     * The logger
     */
    private static final Log _log = LogFactory.getLog(RDBMSTool.class);


    /**
     * Construct a new <code>RDBMSTool</code>.
     *
     * @param connection the JDBC connection
     * @throws PersistenceException if database meta-data can't be obtained
     */
    public RDBMSTool(Connection connection) throws PersistenceException {
        init(connection);
    }

    /**
     * Construct a new <code>RDBMSTool</code>.
     *
     * @param config the configuration
     * @throws PersistenceException for any error
     */
    public RDBMSTool(Configuration config) throws PersistenceException {
        RdbmsDatabaseConfiguration rdbms =
                config.getDatabaseConfiguration()
                .getRdbmsDatabaseConfiguration();
        if (rdbms == null) {
            throw new PersistenceException(
                    "Configuration not configured to use an RDBMS");
        }
        Connection connection = null;
        try {
            Class.forName(rdbms.getDriver());
            connection = DriverManager.getConnection(rdbms.getUrl(),
                                                      rdbms.getUser(),
                                                      rdbms.getPassword());
        } catch (SQLException exception) {
            throw new PersistenceException(exception);
        } catch (ClassNotFoundException exception) {
            throw new PersistenceException(exception);
        }
        init(connection);
    }

    /**
     * Determines if a set of tables are present in the dataase.
     *
     * @param tables the tables
     * @return <code>true</code> if all tables are present
     * @throws PersistenceException for any error
     */
    public boolean hasTables(Table[] tables) throws PersistenceException {
        boolean result = true;
        for (int i = 0; i < tables.length; ++i) {
            if (!_browser.getTableExists(tables[i].getName())) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Creates the database.
     *
     * @param schema the database schema
     * @throws PersistenceException if elements cannot be created in the
     *                              database
     */
    public void create(Database schema) throws PersistenceException {
        Table[] tables = schema.getTable();
        for (int i = 0; i < tables.length; ++i) {
            create(tables[i]);
        }
    }

    /**
     * Drops tables from the database.
     *
     * @param schema the database schema
     * @throws PersistenceException if elements cannot be dropped from the
     *                              database
     */
    public void drop(Database schema) throws PersistenceException {
        Table[] tables = schema.getTable();
        for (int i = 0; i < tables.length; ++i) {
            drop(tables[i]);
        }
        Deprecated[] redundant = schema.getDeprecated();
        for (int i = 0; i < redundant.length; ++i) {
            dropTable(redundant[i].getName());
        }
    }

    /**
     * Deletes data from all tables in the database.
     *
     * @param schema the database schema
     * @throws PersistenceException if a table can't be truncated
     */
    public void delete(Database schema) throws PersistenceException {
        Table[] tables = schema.getTable();
        for (int i = 0; i < tables.length; ++i) {
            deleteTable(tables[i].getName());
        }
    }

    /**
     * Close the connection to the database.
     */
    public void close() {
        SQLHelper.close(_connection);
    }

    /**
     * Creates a table in the database.
     *
     * @param table the table to create
     * @throws PersistenceException if the table exists, or cannot be created
     */
    public void create(Table table) throws PersistenceException {
        String name = table.getName();
        if (_browser.getTableExists(name)) {
            throw new PersistenceException(
                    "An object already exists in the database named " + name);
        }

        StringBuffer sql = new StringBuffer("create table ");
        sql.append(name);
        sql.append(" (");

        _log.debug("Creating table: " + name);
        Attribute[] attributes = table.getAttribute();
        for (int i = 0; i < attributes.length; ++i) {
            if (i > 0) {
                sql.append(", ");
            }
            Attribute attribute = attributes[i];
            sql.append(attribute.getName());
            sql.append(" ");
            sql.append(getSQLType(attribute));
            if (attribute.getNotNull()) {
                sql.append(" not null");
            }
            if (attribute.getPrimaryKey()) {
                sql.append(" primary key");
            }
            if (attribute.getUnique()) {
                sql.append(" unique");
            }
        }
        PrimaryKey key = table.getPrimaryKey();
        if (key != null) {
            sql.append(", primary key (");
            Column[] columns = key.getColumn();
            for (int i = 0; i < columns.length; ++i) {
                if (i > 0) {
                    sql.append(", ");
                }
                sql.append(columns[i].getName());
            }
            sql.append(")");
        }
        sql.append(")");

        _log.debug("SQL=" + sql);
        Statement statement = null;
        try {
            statement = _connection.createStatement();
            statement.executeUpdate(sql.toString());
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to create table=" + name,
                                           exception);
        } finally {
            SQLHelper.close(statement);
        }
        createIndexes(table);
    }

    /**
     * Drops a table from the database. If the table doesn't exist, then
     * it will be ignored.
     *
     * @param table the table to drop
     * @throws PersistenceException for any database error
     */
    public void drop(Table table) throws PersistenceException {
        dropTable(table.getName());
    }

    /**
     * Returns the schema browser.
     *
     * @return the schema browser
     */
    public SchemaBrowser getSchemaBrowser() {
        return _browser;
    }

    /**
     * Initialise this.
     *
     * @param connection the connection to use
     * @throws PersistenceException for any error
     *
      */
    private void init(Connection connection) throws PersistenceException {
        _connection = connection;
        try {
            _connection.setAutoCommit(true);
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to set auto-commit on",
                                           exception);
        }
        _browser = new SchemaBrowser(_connection);
    }

    /**
     * Create indexes for a table.
     *
     * @param table the table to add indexes for
     * @throws PersistenceException
     */
    private void createIndexes(Table table) throws PersistenceException {
        Index[] indexes = table.getIndex();
        for (int i = 0; i < indexes.length; ++i) {
            Index index = indexes[i];
            StringBuffer sql = new StringBuffer("create ");
            if (index.getUnique()) {
                sql.append("unique ");
            }
            sql.append("index ");
            sql.append(index.getName());
            sql.append(" on ");
            sql.append(table.getName());
            sql.append("(");
            Column[] columns = index.getColumn();
            for (int j = 0; j < columns.length; ++j) {
                if (j > 0) {
                    sql.append(", ");
                }
                sql.append(columns[j].getName());
            }
            sql.append(")");
            _log.debug("SQL=" + sql);
            Statement statement = null;
            try {
                statement = _connection.createStatement();
                statement.executeUpdate(sql.toString());
            } catch (SQLException exception) {
                throw new PersistenceException("Failed to create index="
                                               + index.getName()
                                               + " on table "
                                               + table.getName(), exception);
            } finally {
                SQLHelper.close(statement);
            }
        }
    }

    /**
     * Drop a table.
     *
     * @param name the name of the table to drop
     * @throws PersistenceException if the drop fails
     */
    private void dropTable(String name) throws PersistenceException {
        if (_browser.getTableExists(name)) {
            String sql = "drop table " + name;
            _log.debug("SQL=" + sql);
            Statement statement = null;
            try {
                statement = _connection.createStatement();
                statement.executeUpdate(sql);
            } catch (SQLException exception) {
                throw new PersistenceException("Failed to drop table=" + name,
                                               exception);
            } finally {
                SQLHelper.close(statement);
            }
        }
    }

    /**
     * Deletes all data in a table.
     *
     * @param name the name of the table to delete from
     * @throws PersistenceException if the delete fails
     */
    private void deleteTable(String name) throws PersistenceException {
        if (_browser.getTableExists(name)) {
            String sql = "delete from " + name;
            _log.debug("SQL=" + sql);
            Statement statement = null;
            try {
                statement = _connection.createStatement();
                statement.execute(sql);
            } catch (SQLException exception) {
                throw new PersistenceException("Failed to delete from table="
                                               + name, exception);
            } finally {
                SQLHelper.close(statement);
            }
        }
    }

    /**
     * Returns the SQL type for a given attribute.
     *
     * @param attribute the attribute
     * @return a string representation of the type
     * @throws PersistenceException if {@link Attribute#getType} is invalid, or
     *                              the RDBMS doesn't support the type
     */
    private String getSQLType(Attribute attribute)
            throws PersistenceException {
        Type result = _browser.getType(attribute);
        _log.debug("attribute=" + attribute.getName() + "->" + result);
        return result.getSQL();
    }

}
