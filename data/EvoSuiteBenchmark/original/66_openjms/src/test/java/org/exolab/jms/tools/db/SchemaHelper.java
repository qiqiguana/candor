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
 * $Id: SchemaHelper.java,v 1.2 2005/06/10 04:32:23 tanderson Exp $
 */
package org.exolab.jms.tools.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.persistence.SQLHelper;


/**
 * Schema utility class.
 *
 * @version     $Revision: 1.2 $ $Date: 2005/06/10 04:32:23 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
public class SchemaHelper {

    /**
     * The schema path
     */
    private static final String SCHEMA = "/org/exolab/jms/tools/db/schema.xml";


    /**
     * Get the schema version
     *
     * @param connection the connection to use
     * @return the schema version, or null, if no version has been initialised
     * @throws PersistenceException for any related persistence exception
     */
    public static String getSchemaVersion(Connection connection)
        throws PersistenceException {
        String version = null;
        PreparedStatement query = null;
        ResultSet result = null;
        try {
            query = connection.prepareStatement(
                "select version from system_data where id = 1");
            result = query.executeQuery();
            if (result.next()) {
                version = result.getString(1);
            }
        } catch (SQLException exception) {
            throw new PersistenceException(
                "Failed to get the schema version", exception);
        } finally {
            SQLHelper.close(result);
            SQLHelper.close(query);
        }
        return version;
    }

    public static void setVersion(Connection connection, String version)
        throws PersistenceException {
        PreparedStatement update = null;
        try {
            update = connection.prepareStatement(
                "update system_data set version=? where id = 1");
            update.setString(1, version);
            if (update.executeUpdate() != 1) {
                throw new PersistenceException(
                    "Failed to update system_data.version");
            }
        } catch (SQLException exception) {
            throw new PersistenceException(
                "Failed to update system_data.version", exception);
        } finally {
            SQLHelper.close(update);
        }
    }

    public static Table getTable(Database schema, String name) {
        Table result = null;
        Table[] tables = schema.getTable();
        for (int i = 0; i < tables.length; ++i) {
            if (tables[i].getName().equalsIgnoreCase(name)) {
                result = tables[i];
                break;
            }
        }
        return result;
    }

    public static Attribute getAttribute(Table table, String name) {
        Attribute result = null;
        Attribute[] attributes = table.getAttribute();
        for (int i = 0; i < attributes.length; ++i) {
            if (attributes[i].getName().equalsIgnoreCase(name)) {
                result = attributes[i];
                break;
            }
        }
        return result;
    }

    public static Database getSchema() throws PersistenceException {
        return getSchemaFromResource(SCHEMA);
    }

    public static Database getSchemaFromResource(String path)
        throws PersistenceException {
        Database schema = null;
        InputStream stream = SchemaHelper.class.getResourceAsStream(path);
        if (stream == null) {
            throw new PersistenceException("Cannot locate resource: " +
                path);
        }
        try {
            schema = Database.unmarshal(new InputStreamReader(stream));
        } catch (MarshalException exception) {
            throw new PersistenceException(exception.getMessage());
        } catch (ValidationException exception) {
            throw new PersistenceException(exception.getMessage());
        }
        return schema;
    }

    public static Database getSchema(String path) throws PersistenceException {
        Database schema = null;
        InputStream stream = null;
        try {
            stream = new FileInputStream(path);
        } catch (FileNotFoundException exception) {
            throw new PersistenceException(exception.getMessage(), exception);
        }

        try {
            schema = Database.unmarshal(new InputStreamReader(stream));
        } catch (MarshalException exception) {
            throw new PersistenceException(exception.getMessage());
        } catch (ValidationException exception) {
            throw new PersistenceException(exception.getMessage());
        }
        return schema;
    }

}
