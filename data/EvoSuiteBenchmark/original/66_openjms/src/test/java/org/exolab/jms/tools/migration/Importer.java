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
 * $Id: Importer.java,v 1.3 2005/11/12 12:47:37 tanderson Exp $
 */
package org.exolab.jms.tools.migration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.derby.jdbc.EmbeddedDataSource;

import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.ConfigurationReader;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.service.ServiceException;
import org.exolab.jms.tools.db.Database;
import org.exolab.jms.tools.db.RDBMSTool;
import org.exolab.jms.tools.db.SchemaHelper;
import org.exolab.jms.tools.migration.master.MasterConsumerStore;
import org.exolab.jms.tools.migration.master.MasterDestinationStore;
import org.exolab.jms.tools.migration.master.MasterMessageStore;
import org.exolab.jms.tools.migration.master.MasterUserStore;
import org.exolab.jms.tools.migration.proxy.ConsumerStore;
import org.exolab.jms.tools.migration.proxy.DestinationStore;
import org.exolab.jms.tools.migration.proxy.MessageStore;
import org.exolab.jms.tools.migration.proxy.PropertyStore;
import org.exolab.jms.tools.migration.proxy.UserStore;
import org.exolab.jms.tools.migration.proxy.VersionInfo;
import org.exolab.jms.util.CommandLine;


/**
 * Imports data from a proxy database into a master.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/11/12 12:47:37 $
 * @see Exporter
 */

public class Importer {

    /**
     * The database service.
     */
    private final DatabaseService _database;

    /**
     * The connection factory for the source database.
     */
    private final EmbeddedDataSource _dataSource;

    /**
     * The logger.
     */
    private final Log _log = LogFactory.getLog(Importer.class);


    /**
     * Construct a new <code>Importer</code>.
     *
     * @param config   the configuration to use
     * @param database the database name
     * @param delete   if <code>true</code>, indicates to delete data in the
     *                 master database, if it exists
     * @throws PersistenceException for any persistence error
     */
    public Importer(Configuration config, String database, boolean delete)
            throws PersistenceException {
        if (config == null) {
            throw new IllegalArgumentException("Argument 'config' is null");
        }
        if (database == null) {
            throw new IllegalArgumentException("Argument 'database' is null");
        }
        _database = new DatabaseService(config);
        _dataSource = new EmbeddedDataSource();
        _dataSource.setDatabaseName(database);
        _dataSource.setCreateDatabase("create");
        init(config, delete);
    }

    /**
     * Import the data from the proxy database to the master database.
     *
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     * @throws ServiceException     for any service error
     */
    public void apply()
            throws JMSException, PersistenceException, ServiceException {
        // get a connection to the proxy database
        Connection source;
        try {
            source = _dataSource.getConnection();
        } catch (SQLException exception) {
            throw new PersistenceException(
                    "Failed to get connection to source database",
                    exception);
        }

        _database.start();

        // init master stores
        MasterDestinationStore masterDestinations
                = new MasterDestinationStore(_database);
        MasterMessageStore masterMessages = new MasterMessageStore(_database);
        MasterConsumerStore masterConsumers
                = new MasterConsumerStore(_database);
        MasterUserStore masterUsers = new MasterUserStore(_database);

        // init proxy stores
        PropertyStore properties = new PropertyStore(source);
        VersionInfo info = new VersionInfo(properties);
        String schemaVersion = info.getProxySchemaVersion();
        if (schemaVersion == null || !schemaVersion.equals("1.0")) {
            throw new PersistenceException("Cannot import data: unsupported schema version: "
                                           + schemaVersion);
        }
        Date created = new Date(info.getCreationTimestamp());
        _log.info("Importing data created on " + created + " by OpenJMS "
                  + info.getOpenJMSVersion());

        DestinationStore destinations = new DestinationStore(source);
        ConsumerStore consumers = new ConsumerStore(destinations, source);
        MessageStore messages = new MessageStore(destinations, source);
        UserStore users = new UserStore(source);

        // import data from the proxy database to the master database
        _log.info("Importing destinations...");
        apply(destinations, masterDestinations);
        _log.info("Imported " + masterDestinations.size() + " destinations");

        _log.info("Importing messages...");
        apply(messages, masterMessages);
        _log.info("Imported " + masterMessages.size() + " messages");

        _log.info("Importing consumers...");
        apply(consumers, new MasterConsumerStore(_database));
        _log.info("Imported " + masterConsumers.size() + " consumers");

        _log.info("Importing users...");
        apply(users, new MasterUserStore(_database));
        _log.info("Imported " + masterUsers.size() + " users");

        try {
            source.close();
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to close source",
                                           exception);
        }
        _database.stop();
        _dataSource.setShutdownDatabase("shutdown");

        _log.info("Import complete");
    }

    /**
     * Main line.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        CommandLine commands = new CommandLine(args);

        String path = commands.value("config");
        if (path == null) {
            usage();
            System.exit(1);
        } else {
            try {
                Configuration config = ConfigurationReader.read(path);

                String database = commands.value("db");
                if (database == null) {
                    database = "openjms_migdb";
                }
                boolean delete = commands.exists("delete");
                Importer importer = new Importer(config, database, delete);
                importer.apply();
                System.exit(0);
            } catch (Exception exception) {
                exception.printStackTrace();
                System.exit(1);
            }
        }
    }

    /**
     * Initialise the target database.
     *
     * @param config the datbase configuration
     * @param delete if <code>true</code>, indicates to delete data in the
     *               master database, if it exists
     * @throws PersistenceException for any persistence error
     */
    private void init(Configuration config, boolean delete)
            throws PersistenceException {
        // init master tables
        RDBMSTool tool = new RDBMSTool(config);
        Database schema = SchemaHelper.getSchema();

        try {
            if (tool.hasTables(schema.getTable())) {
                if (delete) {
                    tool.delete(schema);
                } else {
                    throw new PersistenceException(
                            "Cannot import data: master database already exists "
                            + "but delete not specified");
                }
            } else {
                tool.create(schema);
            }
        } finally {
            tool.close();
        }
    }

    /**
     * Export data from a source store, and import it to a target store.
     *
     * @param source the source store
     * @param target the target store
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     */
    private void apply(Store source, Store target)
            throws JMSException, PersistenceException {

        StoreIterator iterator = source.exportCollection();
        target.importCollection(iterator);
    }

    /**
     * Displays usage information for this tool when invoked from the command
     * line.
     */
    private static void usage() {
        System.err.println("usage: " + Importer.class.getName()
                           + " <arguments> [options]\n" +
                           "arguments:\n"
                           + "  -config <path>  specifies the path to an OpenJMS "
                           + "configuration file\n"
                           + "  -import <path>  specifies the path to import data from\n");
    }

}
