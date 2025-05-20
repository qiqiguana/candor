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
 * $Id: DBTool.java,v 1.4 2005/11/12 12:47:37 tanderson Exp $
 */
package org.exolab.jms.tools.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;

import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.ConfigurationReader;
import org.exolab.jms.config.RdbmsDatabaseConfiguration;
import org.exolab.jms.persistence.DBCPConnectionManager;
import org.exolab.jms.persistence.DBConnectionManager;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.persistence.RDBMSAdapter;
import org.exolab.jms.util.CommandLine;


/**
 * This class provides support for creating and destroying OpenJMS tables in
 * RDBMS databases.
 *
 * @author <a href="mailto:tima@intalio.com">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2005/11/12 12:47:37 $
 */
public class DBTool {

    /**
     * The connection manager.
     */
    private DBConnectionManager _connections;

    /**
     * The RDBMS tool.
     */
    private RDBMSTool _tool;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(DBTool.class);


    /**
     * Construct a new <code>DBTool</code>.
     *
     * @param config the configuration to use.
     * @throws PersistenceException for any error
     */
    public DBTool(Configuration config) throws PersistenceException {
        if (config == null) {
            throw new IllegalArgumentException("Argument 'config' is null");
        }
        init(config);
    }

    /**
     * Construct an instance with the path to an openjms XML configuration file.
     *
     * @param path the path to an openjms XML configuration file
     * @throws PersistenceException if a connection cannot be established
     */
    public DBTool(String path) throws PersistenceException {
        Configuration config;
        try {
            config = ConfigurationReader.read(path);
        } catch (Exception exception) {
            throw new PersistenceException(exception.getMessage());
        }
        DOMConfigurator.configure(config.getLoggerConfiguration().getFile());
        init(config);
    }

    /**
     * Creates the database tables using the default schema.
     *
     * @throws PersistenceException if the tables cannot be created
     */
    public void create() throws PersistenceException {
        Database schema = SchemaHelper.getSchema();
        _tool.create(schema);
    }

    /**
     * Creates the database tables from the specified schema.
     *
     * @param path the path to an XML database schema file
     * @throws PersistenceException if the tables cannot be created
     */
    public void create(String path) throws PersistenceException {
        if (path == null) {
            throw new IllegalArgumentException("Argument 'path' is null");
        }
        Database schema = SchemaHelper.getSchema(path);
        _tool.create(schema);
    }

    /**
     * Drop the database tables from the default schema.
     *
     * @throws PersistenceException if the tables cannot be dropped
     */
    public void drop() throws PersistenceException {
        Database schema = SchemaHelper.getSchema();
        _tool.drop(schema);
    }

    /**
     * Drop the database tables from the specified schema.
     *
     * @param path the path to an XML database schema file
     * @throws PersistenceException if the tables cannot be dropped
     */
    public void drop(String path) throws PersistenceException {
        if (path == null) {
            throw new IllegalArgumentException("Argument 'path' is null");
        }
        Database schema = SchemaHelper.getSchema(path);
        _tool.drop(schema);
    }

    /**
     * Deletes all data from the database tables.
     *
     * @throws PersistenceException if the tables can't be deleted
     */
    public void delete() throws PersistenceException {
        Database schema = SchemaHelper.getSchema();
        _tool.delete(schema);
    }

    /**
     * Migrates the database tables to the latest version.
     *
     * @throws PersistenceException if the database cannot be migrated
     */
    public void migrate() throws PersistenceException {
        Connection connection = _connections.getConnection();

        String fromVersion = SchemaHelper.getSchemaVersion(connection);
        if (fromVersion == null) {
            throw new PersistenceException(
                    "Cannot migrate schema - existing schema version cannot be "
                    + "determined");
        }
        String toVersion = RDBMSAdapter.SCHEMA_VERSION;
        SchemaConverter converter =
                SchemaConverterFactory.create(fromVersion, toVersion,
                                              connection);
        if (converter != null) {
            try {
                _log.info("Migrating schema from version=" +
                          fromVersion + " to version=" + toVersion);
                converter.convert();
                _log.info("Successfully migrated schema");
            } catch (PersistenceException exception) {
                _log.error("Schema migration from version=" + fromVersion +
                           " to version=" + toVersion + " failed",
                           exception);
                throw exception;
            }
        } else {
            throw new PersistenceException(
                    "Incompatible schema types. Expected schema version="
                    + fromVersion + ", but got schema version=" + toVersion);
        }
    }

    /**
     * Deallocate any resources.
     *
     * @throws SQLException if the database connection cannot be closed
     */
    public void close() throws SQLException {
        _tool.close();
    }

    public static void main(String args[]) {
        CommandLine commands = new CommandLine(args);

        DBTool tool = null;
        String config = commands.value("config");
        if (config != null) {
            try {
                tool = new DBTool(config);
            } catch (Exception exception) {
                _log.error(exception, exception);
                System.exit(1);
            }
        } else {
            usage();
            System.exit(1);
        }
        boolean create = commands.exists("create");
        boolean drop = commands.exists("drop");
        boolean recreate = commands.exists("recreate");
        boolean delete = commands.exists("delete");
        boolean migrate = commands.exists("migrate");
        String schema = commands.value("schema");
        if (create) {
            try {
                if (schema != null) {
                    tool.create(schema);
                } else {
                    tool.create();
                }
                System.out.println("Successfully created tables");
            } catch (Exception exception) {
                _log.error(exception, exception);
                System.exit(1);
            }
        } else if (drop) {
            try {
                if (schema != null) {
                    tool.drop(schema);
                } else {
                    tool.drop();
                }
                System.out.println("Successfully dropped tables");
            } catch (Exception exception) {
                _log.error(exception, exception);
                System.exit(1);
            }
        } else if (recreate) {
            try {
                if (schema != null) {
                    tool.drop(schema);
                    tool.create(schema);
                } else {
                    tool.drop();
                    tool.create();
                }
                System.out.println("Successfully recreated tables");
            } catch (Exception exception) {
                _log.error(exception, exception);
                System.exit(1);
            }
        } else if (delete) {
            try {
                tool.delete();
            } catch (Exception exception) {
                _log.error(exception, exception);
                System.exit(1);
            }
            System.out.println("Sucessfully deleted data");
        } else if (migrate) {
            try {
                tool.migrate();
            } catch (Exception exception) {
                _log.error(exception, exception);
                System.exit(1);
            }
            System.out.println("Sucessfully migrated database");
        } else {
            usage();
            System.exit(1);
        }
        try {
            tool.close();
        } catch (Exception exception) {
            _log.error(exception, exception);
        }
    }

    /**
     * Initialise this.
     *
     * @param config the configuration to use
     * @throws PersistenceException for any error
     */
    private void init(Configuration config) throws PersistenceException {
        RdbmsDatabaseConfiguration rdbms =
                config.getDatabaseConfiguration()
                .getRdbmsDatabaseConfiguration();
        if (rdbms == null) {
            throw new PersistenceException(
                    "Configuration not configured to use an RDBMS");
        }
        _connections = new DBCPConnectionManager();
        _connections.setDriver(rdbms.getDriver());
        _connections.setURL(rdbms.getUrl());
        _connections.setUser(rdbms.getUser());
        _connections.setPassword(rdbms.getPassword());
        _connections.init();
        _tool = new RDBMSTool(_connections.getConnection());
    }


    /**
     * Displays usage information for this tool when invoked from the command
     * line.
     */
    private static void usage() {
        System.err.println(
                "usage: " + DBTool.class.getName()
                + " <arguments> [options]\n"
                + "arguments:\n"
                + "  -create -config <path>   creates the database tables\n"
                + "  -drop -config <path>     drops the database tables\n"
                + "  -recreate -config <path> recreates the database tables\n"
                + "  -delete -config <path>   deletes all data, leaving tables"
                + "\n"
                + "  -migrate -config <path>  migrates the database to the "
                + "latest schema version\n\n"
                + "options:\n"
                + "  -schema <schema>\n");
        System.err.println(
                "where:\n"
                + "  path      is the path to an OpenJMS configuration file\n"
                + "  schema    is an XML document specifying the database "
                + "schema\n"
                + "            If not specified, the default schema will be "
                + "used");
    }

}
