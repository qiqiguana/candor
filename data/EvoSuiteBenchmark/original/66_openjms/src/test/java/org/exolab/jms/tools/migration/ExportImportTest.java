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
 * $Id: ExportImportTest.java,v 1.2 2005/11/12 12:52:06 tanderson Exp $
 */
package org.exolab.jms.tools.migration;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.derby.jdbc.EmbeddedDataSource;

import junit.framework.TestCase;
import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.ConfigurationReader;
import org.exolab.jms.config.DatabaseConfiguration;
import org.exolab.jms.config.RdbmsDatabaseConfiguration;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.tools.db.Database;
import org.exolab.jms.tools.db.RDBMSTool;
import org.exolab.jms.tools.db.SchemaHelper;


/**
 * Tests the {@link Exporter} and {@link Importer}.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/11/12 12:52:06 $
 */
public class ExportImportTest extends TestCase {

    /**
     * Migration database name.
     */
    private static final String DB_NAME = "openjms_migdb";

    /**
     * Destinations table query. Ignores destination identifiers as these
     * may be different between databases.
     */
    private static final String DESTINATIONS_QUERY
            = "select name, isQueue from destinations order by name";
    /**
     * Message handles table query. Translates destination and consumer
     * identifiers to their corresponding names, as these may be different
     * between databases.
     */
    private static final String MESSAGE_HANDLES_QUERY
            = "select messageId, d.name destination, c.name consumer, "
              + "     priority, acceptedTime, sequenceNumber, expiryTime, "
              + "     delivered "
              + "from message_handles m, destinations d, consumers c "
              + "where m.destinationId = d.destinationId and "
              + "      m.consumerId = c.consumerId "
              + "order by messageId, destination, consumer";

    /**
     * Consumers table query. Ignores the 'created' column which is no longer
     * used.
     */
    private static final String CONSUMERS_QUERY
            = "select c.name name, d.name destination "
              + "from consumers c, destinations d "
              + "where c.destinationId = d.destinationId "
              + "order by name, destination";

    /**
     * Verifies that the exporter creates the tables in the target proxy,
     * if they don't exist.
     *
     * @throws Exception for any error
     */
    public void testExporterCreateTables() throws Exception {
        // load the configuration for the master database
        Configuration config = read("/openjmstest.xml");

        // ensure the tables don't exist
        EmbeddedDataSource ds = MigrationHelper.getDataSource(DB_NAME);
        RDBMSTool tool = new RDBMSTool(ds.getConnection());
        Database schema = MigrationHelper.getSchema();
        tool.drop(schema);
        assertFalse(tool.hasTables(schema.getTable()));

        // Create the exporter, and verify it has created the tables.
        new Exporter(config, DB_NAME, false);
        assertTrue(tool.hasTables(schema.getTable()));
    }

    /**
     * Verifies that the exporter will only export data if the delete flag
     * is true when the tables exists.
     *
     * @throws Exception for any error
     */
    public void testExporterDelete() throws Exception {
        // load the configuration for the master database
        Configuration config = read("/openjmstest.xml");

        // create the target database.
        EmbeddedDataSource ds = MigrationHelper.getDataSource(DB_NAME);
        RDBMSTool tool = new RDBMSTool(ds.getConnection());
        Database schema = MigrationHelper.getSchema();
        tool.drop(schema);
        tool.create(schema);

        // Create the exporter
        try {
            new Exporter(config, DB_NAME, false);
            fail("Expected Exporter construction to fail, as tables exist");
        } catch (PersistenceException expected) {
            // expected behaviour
        }

        try {
            new Exporter(config, DB_NAME, true);
        } catch (PersistenceException unexpected) {
            fail("Expected Exporter construction to succeed");
        }
    }

    /**
     * Verifies that the importer creates the tables in the target master,
     * if they don't exist.
     *
     * @throws Exception for any error
     */
    public void testImporterCreateTables() throws Exception {
        // load the configuration for the target master database 'migrated'
        Configuration config = read("/openjmstest_migrated.xml");

        // ensure the tables don't exist
        RDBMSTool tool = new RDBMSTool(config);
        Database schema = SchemaHelper.getSchema();
        tool.drop(schema);
        assertFalse(tool.hasTables(schema.getTable()));

        // Create the importer, and verify it has created the tables.
        new Importer(config, DB_NAME, false);
        assertTrue(tool.hasTables(schema.getTable()));
    }

    /**
     * Verifies that the importer will only import data if the delete flag
     * is true when the tables exists.
     *
     * @throws Exception for any error
     */
    public void testImporterDelete() throws Exception {
        // load the configuration for the target master database 'migrated'
        Configuration config = read("/openjmstest_migrated.xml");

        // create the target database.
        RDBMSTool tool = new RDBMSTool(config);
        Database schema = SchemaHelper.getSchema();
        tool.drop(schema);
        tool.create(schema);
        tool.close();

        // Create the importer
        try {
            new Importer(config, DB_NAME, false);
            fail("Expected Importer construction to fail, as tables exist");
        } catch (PersistenceException expected) {
            // expected behaviour
        }

        try {
            new Importer(config, DB_NAME, true);
        } catch (PersistenceException unexpected) {
            fail("Expected Importer construction to succeed");
        }
    }

    /**
     * Exports data from a database and imports it to another, verifying the
     * contents are the same between each.
     *
     * @throws Exception for any error
     */
    public void testRoundtrip() throws Exception {
        // load the configuration for the master database
        Configuration master = read("/openjmstest.xml");

        // export the data to a proxy database
        Exporter exporter = new Exporter(master, DB_NAME, true);
        exporter.apply();

        // load the configuration for the target master database 'migrated'
        Configuration migrated = read("/openjmstest_migrated.xml");

        // import the data from the proxy database into 'migrated'
        Importer importer = new Importer(migrated, DB_NAME, true);
        importer.apply();

        // verify data between the two master databases
        IDatabaseConnection masterConn = getConnection(master);
        IDatabaseConnection migratedConn = getConnection(migrated);
        IDataSet expectedDataSet = masterConn.createDataSet();
        IDataSet actualDataSet = migratedConn.createDataSet();

        // NOTE: don't care about the contents of the 'seeds' and 'system_data'
        // tables as neither are explicitly migrated, and the contents of
        // each may differ
        checkQuery("destinations", DESTINATIONS_QUERY, masterConn,
                   migratedConn);
        checkTable("messages", expectedDataSet, actualDataSet);
        checkQuery("message_handles", MESSAGE_HANDLES_QUERY, masterConn,
                   migratedConn);
        checkQuery("consumers", CONSUMERS_QUERY, masterConn, migratedConn);
        checkTable("users", expectedDataSet, actualDataSet);
    }

    /**
     * Reads a configuration from a resource.
     *
     * @param path the path to the resource
     * @throws Exception for any error
     */
    private Configuration read(String path) throws Exception {
        InputStream stream = ExportImportTest.class.getResourceAsStream(path);
        return ConfigurationReader.read(stream);
    }

    /**
     * Returns a dbunit connection for a database.
     *
     * @param config the configuration to use
     * @return the connection
     * @throws SQLException for any error
     */
    private IDatabaseConnection getConnection(Configuration config)
            throws SQLException {
        DatabaseConfiguration db = config.getDatabaseConfiguration();
        Connection connection
                = getConnection(db.getRdbmsDatabaseConfiguration());
        return new DatabaseConnection(connection);
    }

    /**
     * Returns a connection given the configuration.
     *
     * @param config the config to use
     * @throws SQLException for any error
     */
    private Connection getConnection(RdbmsDatabaseConfiguration config)
            throws SQLException {
        return DriverManager.getConnection(config.getUrl(), config.getUser(),
                                           config.getPassword());
    }

    /**
     * Verifies that the contents of the named table is the same in 2 datasets.
     *
     * @param table           the table name
     * @param expectedDataSet the data set containing the expected results
     * @param actualDataSet   the data st containing the actual results
     * @throws Exception for any error
     */
    private void checkTable(String table, IDataSet expectedDataSet,
                            IDataSet actualDataSet) throws Exception {
        ITable expected = expectedDataSet.getTable(table);
        ITable actual = actualDataSet.getTable(table);

        // make sure there is actually data to compare against
        assertTrue(expected.getRowCount() != 0);

        Assertion.assertEquals(expected, actual);
    }

    /**
     * Verifies that the results returned by a query are the same from two
     * different connections.
     *
     * @param table              the table name
     * @param query              the query to execute
     * @param expectedConnection the connection containing the expected results
     * @param actualConnection   the connection containing the actual results
     * @throws Exception for any error
     */
    private void checkQuery(String table, String query,
                            IDatabaseConnection expectedConnection,
                            IDatabaseConnection actualConnection)
            throws Exception {
        QueryDataSet expectedDataSet = new QueryDataSet(expectedConnection);
        expectedDataSet.addTable(table, query);

        QueryDataSet actualDataSet = new QueryDataSet(actualConnection);
        actualDataSet.addTable(table, query);

        // new FlatXmlWriter(System.out).write(expectedDataSet);
        // new FlatXmlWriter(System.out).write(actualDataSet);

        // make sure there is actually data to compare against
        assertTrue(expectedDataSet.getTable(table).getRowCount() != 0);

        Assertion.assertEquals(expectedDataSet, actualDataSet);
    }

}
