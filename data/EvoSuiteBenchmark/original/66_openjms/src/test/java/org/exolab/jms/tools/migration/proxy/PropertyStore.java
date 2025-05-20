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
 * $Id: PropertyStore.java,v 1.2 2005/10/20 14:07:03 tanderson Exp $
 */
package org.exolab.jms.tools.migration.proxy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.persistence.SQLHelper;


/**
 * Stores migration version information.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/10/20 14:07:03 $
 */
public class PropertyStore implements DBConstants {

    /**
     * The properties.
     */
    private final Map _properties = new HashMap();

    /**
     * The database connection.
     */
    private final Connection _connection;

    /**
     * Construct a new <code>PropertyStore</code>.
     *
     * @param connection the database connection
     * @throws PersistenceException for any persistence error
     */
    public PropertyStore(Connection connection) throws PersistenceException {
        _connection = connection;
        init();
    }

    /**
     * Add a property.
     *
     * @param name the property name
     * @param value the property value
     * @throws PersistenceException for any persistence error
     */
    public void add(String name, String value) throws PersistenceException {
        PreparedStatement insert = null;
        try {
            insert = _connection.prepareStatement(
                    "insert into " + PROPERTIES_TABLE + " values (?, ?)");
            insert.setString(1, name);
            insert.setString(2, value);
            insert.execute();

            _properties.put(name, value);
        } catch (SQLException exception) {
            throw new PersistenceException(
                    "Failed to insert property, name=" + name
                    + ", value=" + value, exception);
        } finally {
            SQLHelper.close(insert);
        }
    }

    /**
     * Returns the value of a property.
     *
     * @param name the property name
     * @return the value of the property, or <code>null</code> if it doesn't
     * exist
     */
    public String get(String name) {
        return (String) _properties.get(name);
    }

    /**
     * Loads the properties from the database.
     *
     * @throws PersistenceException for any error
     */
    private void init() throws PersistenceException {
        PreparedStatement select = null;
        ResultSet set = null;
        try {
            select = _connection.prepareStatement(
                    "select name, value from " + PROPERTIES_TABLE);
            set = select.executeQuery();
            while (set.next()) {
                String name = set.getString(1);
                String value = set.getString(2);
                _properties.put(name, value);
            }
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to load properties",
                                           exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }
    }
}
