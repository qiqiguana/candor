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
 * Copyright 2002-2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: V061toV072SchemaConverter.java,v 1.1 2004/11/26 01:51:16 tanderson Exp $
 */
package org.exolab.jms.tools.db.migration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.persistence.SQLHelper;
import org.exolab.jms.tools.db.Attribute;
import org.exolab.jms.tools.db.Database;
import org.exolab.jms.tools.db.InvalidTypeException;
import org.exolab.jms.tools.db.RDBMSTool;
import org.exolab.jms.tools.db.SchemaBrowser;
import org.exolab.jms.tools.db.SchemaConverter;
import org.exolab.jms.tools.db.SchemaHelper;
import org.exolab.jms.tools.db.Table;
import org.exolab.jms.tools.db.Type;


/**
 * A schema converter for converting from the 0.6.1 schema to the 0.7.2 schema
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:16 $
 * @author      <a href="mailto:tima@intalio.com">Tim Anderson</a>
 */
public class V061toV072SchemaConverter implements SchemaConverter {

    /**
     * The database connection
     */
    private Connection _connection;

    /**
     * The RDBMS tool
     */
    private RDBMSTool _tool;

    /**
     * The name of the destinations table
     */
    private static final String DESTINATIONS_TABLE = "destinations";

    /**
     * The name of the isQueue column
     */
    private static final String ISQUEUE_COLUMN = "isQueue";

    /**
     * The logger
     */
    private static final Log _log =
        LogFactory.getLog(V061toV072SchemaConverter.class);


    /**
     * Construct a new <code>V061toV072SchemaConverter</code>
     *
     * @param connection the connection to use
     */
    public V061toV072SchemaConverter(Connection connection) {
        _connection = connection;
    }

    public void convert() throws PersistenceException {
        Database schema = SchemaHelper.getSchema();
        try {
            if (_connection.getAutoCommit()) {
                _connection.setAutoCommit(false);
            }
            _tool = new RDBMSTool(_connection);
        } catch (SQLException exception) {
            throw new PersistenceException(exception.getMessage());
        }

        try {
            if (needsConversion(schema)) {
                doConvert(schema);
            }
            SchemaHelper.setVersion(_connection, "V0.7.2");
            _connection.commit();
        } catch (SQLException exception) {
            SQLHelper.rollback(_connection);
            throw new PersistenceException(exception);
        }
    }

    private boolean needsConversion(Database schema)
        throws PersistenceException {
        boolean result = false;
        SchemaBrowser browser = _tool.getSchemaBrowser();

        // get the expected type of the isQueue column
        Table table = SchemaHelper.getTable(schema, DESTINATIONS_TABLE);
        Attribute column = SchemaHelper.getAttribute(table, ISQUEUE_COLUMN);
        Type expected = browser.getType(column);

        // get the actual type of the isQueue column
        try {
            Table currentTable = browser.getTable(DESTINATIONS_TABLE);
            Attribute currentColumn =
                SchemaHelper.getAttribute(currentTable, ISQUEUE_COLUMN);
            Type currentType = browser.getType(currentColumn);
            result = (currentType.getType() != expected.getType());
        } catch (InvalidTypeException exception) {
            // this will only occur if the JDBC driver is buggy (its amazing
            // home many are - MM.MySQL 2.0.x and Oracle are 2 examples)
            // Try and perform a conversion anyway - and hope for the best...
            _log.warn(exception);
            result = true;
        }
        return result;
    }

    private void doConvert(Database schema) throws PersistenceException {
        Table table = SchemaHelper.getTable(schema, DESTINATIONS_TABLE);

        // create a temporary table to perform conversion
        Table tmpTable = new Table();
        String tmpName = "openjms_tmp_" + DESTINATIONS_TABLE;
        tmpTable.setName(tmpName);
        tmpTable.setAttribute(table.getAttribute());

        _tool.drop(tmpTable);
        _tool.create(tmpTable);

        // convert the destinations table, inserting converted records into
        // the temporary table
        PreparedStatement select = null;
        ResultSet set = null;
        try {
            select = _connection.prepareStatement(
                "select * from " + DESTINATIONS_TABLE);
            set = select.executeQuery();
            while (set.next()) {
                String name = set.getString(1);
                boolean isQueue = (set.getInt(2) > 0);
                long id = set.getLong(3);
                insert(tmpName, name, isQueue, id);
            }
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to convert destinations",
                exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }

        // recreate the destinations table
        _tool.drop(table);
        _tool.create(table);

        // copy the data from the temporary table into the destinations table
        PreparedStatement insert = null;
        try {
            insert = _connection.prepareStatement(
                "insert into " + DESTINATIONS_TABLE + " select * from " +
                tmpName);
            insert.executeQuery();
        } catch (SQLException exception) {
            throw new PersistenceException(
                "Failed to copy converted destinations", exception);
        } finally {
            SQLHelper.close(insert);
        }

        // drop the temporary table
        _tool.drop(tmpTable);
    }

    private void insert(String table, String name, boolean isQueue, long id)
        throws SQLException {
        PreparedStatement insert = _connection.prepareStatement(
            "insert into " + table + " values (?, ?, ?)");
        insert.setString(1, name);
        insert.setBoolean(2, isQueue);
        insert.setLong(3, id);
        insert.executeUpdate();
    }


} //-- V061toV072SchemaConverter
