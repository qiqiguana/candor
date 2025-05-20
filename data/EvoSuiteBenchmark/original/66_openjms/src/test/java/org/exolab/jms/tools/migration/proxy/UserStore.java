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
 */
package org.exolab.jms.tools.migration.proxy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.jms.JMSException;

import org.exolab.jms.authentication.User;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.persistence.SQLHelper;
import org.exolab.jms.tools.migration.IteratorAdapter;
import org.exolab.jms.tools.migration.Store;
import org.exolab.jms.tools.migration.StoreIterator;


/**
 * Provides persistency for {@link User} instances.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/10/20 14:07:03 $
 */
public class UserStore implements Store, DBConstants {

    /**
     * The database connection.
     */
    private final Connection _connection;


    /**
     * Construct a new <code>UserStore</code>.
     *
     * @param connection the database connection
     */
    public UserStore(Connection connection) {
        _connection = connection;
    }

    /**
     * Export the users.
     *
     * @return an iterator over the collection
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     */
    public StoreIterator exportCollection() throws JMSException,
            PersistenceException {
        List users = getUsers();
        return new IteratorAdapter(users.iterator());
    }

    /**
     * Import users into the store.
     *
     * @param iterator an iterator over the collection
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     */
    public void importCollection(StoreIterator iterator) throws JMSException,
            PersistenceException {
        while (iterator.hasNext()) {
            User user = (User) iterator.next();
            add(user);
        }
    }

    /**
     * Returns the number of elements in the collection.
     *
     * @return the number of elements in the collection
     * @throws PersistenceException for any persistence error
     */
    public int size() throws PersistenceException {
        return getUsers().size();
    }

    /**
     * Add a new user.
     *
     * @param user the user to add
     * @throws PersistenceException for any persistence error
     */
    private void add(User user) throws PersistenceException {
        PreparedStatement insert = null;
        try {
            insert = _connection.prepareStatement(
                    "insert into " + USER_TABLE + " values (?, ?)");

            insert.setString(1, user.getUsername());
            insert.setString(2, user.getPassword());
            insert.executeUpdate();
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to add consumer",
                                           exception);
        } finally {
            SQLHelper.close(insert);
        }
    }

    /**
     * Returns the users.
     *
     * @return a list of {@link User} instances
     * @throws PersistenceException for any persistence error
     */
    private List getUsers() throws PersistenceException {
        ArrayList result = new ArrayList();

        PreparedStatement select = null;
        ResultSet set = null;
        try {
            select = _connection.prepareStatement(
                    "select * from " + USER_TABLE);

            set = select.executeQuery();
            while (set.next()) {
                String user = set.getString("username");
                String password = set.getString("password");
                result.add(new User(user, password));
            }
        } catch (SQLException exception) {
            throw new PersistenceException("Failed to retrieve users",
                                           exception);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(select);
        }
        return result;
    }

}



