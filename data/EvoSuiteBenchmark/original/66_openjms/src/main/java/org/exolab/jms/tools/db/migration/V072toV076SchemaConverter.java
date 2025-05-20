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
 * Copyright 2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: V072toV076SchemaConverter.java,v 1.1 2004/11/26 01:51:16 tanderson Exp $
 */
package org.exolab.jms.tools.db.migration;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.persistence.SQLHelper;
import org.exolab.jms.tools.db.Database;
import org.exolab.jms.tools.db.RDBMSTool;
import org.exolab.jms.tools.db.SchemaConverter;
import org.exolab.jms.tools.db.SchemaHelper;
import org.exolab.jms.tools.db.Table;


/**
 * A schema converter for converting from the 0.7.2 schema to the 0.7.6 schema
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:16 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
public class V072toV076SchemaConverter implements SchemaConverter {

    /**
     * The database connection
     */
    private Connection _connection;

    /**
     * The RDBMS tool
     */
    private RDBMSTool _tool;

    /**
     * The name of the users table
     */
    private static final String USERS_TABLE = "users";

    /**
     * The name of the messages table
     */
    private static final String MESSAGES_TABLE = "messages";

    /**
     * The name of the handles table
     */
    private static final String HANDLES_TABLE = "message_handles";


    /**
     * Construct a new <code>V072toV076SchemaConverter</code>
     *
     * @param connection the connection to use
     */
    public V072toV076SchemaConverter(Connection connection) {
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
            convertMessagesTable(schema);
            convertHandlesTable(schema);
            createUsersTable(schema);
            SchemaHelper.setVersion(_connection, "V0.7.6");
            _connection.commit();
        } catch (PersistenceException exception) {
            SQLHelper.rollback(_connection);
            throw exception;
        } catch (SQLException exception) {
            SQLHelper.rollback(_connection);
            throw new PersistenceException(exception);
        }
    }

    /**
     * Converts the message identifier columns from long to string
     */
    private void convertMessagesTable(Database schema)
        throws PersistenceException, SQLException {
        Table table = SchemaHelper.getTable(schema, MESSAGES_TABLE);

        // create a temporary table to perform conversion
        Table tmpTable = new Table();
        String tmpName = "openjms_tmp_" + MESSAGES_TABLE;
        tmpTable.setName(tmpName);
        tmpTable.setAttribute(table.getAttribute());

        _tool.drop(tmpTable);
        _tool.create(tmpTable);

        // convert the messages table, inserting converted records into
        // the temporary table
        PreparedStatement select = _connection.prepareStatement(
            "select messageid, destinationid, priority, createTime,"
            + "expiryTime, processed, messageBlob from " + MESSAGES_TABLE);
        ResultSet set = select.executeQuery();
        while (set.next()) {
            long id = set.getLong(1);
            long destinationId = set.getLong(2);
            int priority = set.getInt(3);
            long createTime = set.getLong(4);
            long expiryTime = set.getLong(5);
            int processed = set.getInt(6);
            byte[] blob = set.getBytes(7);
            String messageId = "ID:" + id;
            migrateMessage(tmpName, messageId, destinationId, priority,
                createTime, expiryTime, processed, blob);
        }
        set.close();
        select.close();

        // recreate the destinations table
        _tool.drop(table);
        _tool.create(table);

        // copy the data from the temporary table into the messages table
        select = _connection.prepareStatement(
            "select messageid, destinationid, priority, createTime,"
            + "expiryTime, processed, messageBlob from " + tmpName);

        set = select.executeQuery();
        while (set.next()) {
            String messageId = set.getString(1);
            long destinationId = set.getLong(2);
            int priority = set.getInt(3);
            long createTime = set.getLong(4);
            long expiryTime = set.getLong(5);
            int processed = set.getInt(6);
            byte[] blob = set.getBytes(7);
            migrateMessage(tmpName, messageId, destinationId, priority,
                createTime, expiryTime, processed, blob);
        }
        set.close();
        select.close();

        // drop the temporary table
        _tool.drop(tmpTable);
    }

    private void migrateMessage(String table, String messageId,
                                long destinationId, int priority,
                                long createTime, long expiryTime,
                                int processed, byte[] blob)
        throws SQLException {
        PreparedStatement insert = null;
        try {
            // create, populate and execute the insert
            insert = _connection.prepareStatement(
                "insert into " + table + " values (?,?,?,?,?,?,?)");
            insert.setString(1, messageId);
            insert.setLong(2, destinationId);
            insert.setInt(3, priority);
            insert.setLong(4, createTime);
            insert.setLong(5, expiryTime);
            insert.setInt(6, processed);
            insert.setBinaryStream(7, new ByteArrayInputStream(blob),
                blob.length);

            // execute the insert
            if (insert.executeUpdate() != 1) {
                throw new SQLException("Failed to add message=" + messageId);
            }
        } finally {
            SQLHelper.close(insert);
        }
    }

    /**
     * Converts the message identifier columns from long to string
     */
    private void convertHandlesTable(Database schema)
        throws PersistenceException, SQLException {

        Table table = SchemaHelper.getTable(schema, HANDLES_TABLE);

        // create a temporary table to perform conversion
        Table tmpTable = new Table();
        String tmpName = "openjms_tmp_" + HANDLES_TABLE;
        tmpTable.setName(tmpName);
        tmpTable.setAttribute(table.getAttribute());

        _tool.drop(tmpTable);
        _tool.create(tmpTable);

        // convert the messages_handles table, inserting converted records into
        // the temporary table
        PreparedStatement select = _connection.prepareStatement(
            "select messageid, destinationid, consumerid, priority, "
            + " acceptedTime, sequenceNumber, expiryTime, delivered"
            + " from " + HANDLES_TABLE);
        ResultSet set = select.executeQuery();
        while (set.next()) {
            long messageId = set.getLong(1);
            long destinationId = set.getLong(2);
            long consumerId = set.getLong(3);
            int priority = set.getInt(4);
            long acceptedTime = set.getLong(5);
            long sequenceNo = set.getLong(6);
            long expiryTime = set.getLong(7);
            int delivered = set.getInt(8);
            migrateHandle(tmpName, messageId, destinationId, consumerId,
                priority, acceptedTime, sequenceNo, expiryTime,
                delivered);
        }
        set.close();
        select.close();

        // recreate the destinations table
        _tool.drop(table);
        _tool.create(table);

        // copy the data from the temporary table into the messages table
        select = _connection.prepareStatement(
            "insert into " + HANDLES_TABLE + " select * from " +
            tmpName);
        select.executeQuery();
        select.close();

        // drop the temporary table
        _tool.drop(tmpTable);
    }

    private void migrateHandle(String table, long messageId,
                               long destinationId, long consumerId,
                               int priority, long acceptedTime,
                               long sequenceNo, long expiryTime,
                               int delivered) throws SQLException {
        PreparedStatement insert = null;
        try {
            // create, populate and execute the insert
            insert = _connection.prepareStatement(
                "insert into " + table + " values (?,?,?,?,?,?,?,?)");
            insert.setString(1, "ID:" + messageId);
            insert.setLong(2, destinationId);
            insert.setLong(3, consumerId);
            insert.setInt(4, priority);
            insert.setLong(5, acceptedTime);
            insert.setLong(6, sequenceNo);
            insert.setLong(7, expiryTime);
            insert.setInt(8, delivered);

            // execute the insert
            if (insert.executeUpdate() != 1) {
                throw new SQLException("Failed to add handle=" + messageId);
            }
        } finally {
            SQLHelper.close(insert);
        }
    }

    private void createUsersTable(Database schema)
        throws PersistenceException {
        Table table = SchemaHelper.getTable(schema, USERS_TABLE);
        _tool.create(table);
    }


} //-- V072toV076SchemaConverter
