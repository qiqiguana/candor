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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.exolab.jms.authentication.User;


/**
 * This class provides persistency for Users objects
 * in an RDBMS database
 *
 * @version     $Revision: 1.4 $ $Date: 2005/08/31 05:45:50 $
 * @author      <a href="mailto:knut@lerpold.no">Knut Lerpold</a>
 */
class Users {

    /**
     * Construct a new <code>Users</code>.
     */
    public Users() {
    }

    /**
     * Add a new user to the database.
     *
     * @param connection - the connection to use.
     * @param user - the user to add
     * @throws PersistenceException - if the user cannot be added
     */
    public synchronized void add(Connection connection,
                                 User user)
        throws PersistenceException {

        PreparedStatement insert = null;
        try {
            insert = connection.prepareStatement(
                "insert into users values (?, ?)");
            insert.setString(1, user.getUsername());
            insert.setString(2, user.getPassword());
            insert.executeUpdate();
        } catch (Exception error) {
            throw new PersistenceException("Users.add failed with "
                + error.toString());
        } finally {
            SQLHelper.close(insert);
        }
    }

    /**
     * Update a a user in the database.
     *
     * @param connection - the connection to use
     * @param user - the user
     * @throws PersistenceException - if the request fails
     */
    public synchronized void update(Connection connection,
                                    User user)
        throws PersistenceException {

        PreparedStatement update = null;
        try {
            update = connection.prepareStatement(
                "update users set password=? where username=?");
            update.setString(1, user.getPassword());
            update.setString(2, user.getUsername());
            update.executeUpdate();
        } catch (Exception error) {
            throw new PersistenceException("Users.add failed with "
                + error.toString());
        } finally {
            SQLHelper.close(update);
        }
    }

    /**
     * Remove a user from the database.
     *
     * @param connection - the connection to use
     * @param user - the user
     * @return boolean - <tt>true</tt> if it was removed
     * @throws PersistenceException - if the request fails
     */
    public synchronized boolean remove(Connection connection,
                                       User user)
        throws PersistenceException {

        boolean success = false;
        PreparedStatement deleteUsers = null;

        if (user != null) {
            try {
                deleteUsers = connection.prepareStatement(
                    "delete from users where username=?");
                deleteUsers.setString(1, user.getUsername());
                deleteUsers.executeUpdate();
            } catch (Exception error) {
                throw new PersistenceException("Users.remove failed "
                    + error.toString());
            } finally {
                SQLHelper.close(deleteUsers);
            }
        }

        return success;
    }

    /**
     * Get a user from DB.
     *
     * @param connection - the connection to use
     * @param user - the user
     * @return boolean - <tt>true</tt> if it was removed
     * @throws PersistenceException - if the request fails
     */
    public synchronized User get(Connection connection,
                                 User user)
        throws PersistenceException {

        PreparedStatement getUser = null;
        ResultSet set = null;
        User result = null;

        if (user != null) {
            try {
                getUser = connection.prepareStatement(
                    "select * from users where username=?");
                getUser.setString(1, user.getUsername());
                set = getUser.executeQuery();
                if (set.next()) {
                    result = new User(set.getString(1), set.getString(2));
                }
            } catch (Exception error) {
                throw new PersistenceException("Users.get failed "
                    + error.toString());
            } finally {
                SQLHelper.close(set);
                SQLHelper.close(getUser);
            }
        }

        return result;
    }

    /**
     * List of all users from DB.
     *
     * @param connection - the connection to use
     * @return Vector - all users
     * @throws PersistenceException - if the request fails
     */
    public synchronized Vector getAllUsers(Connection connection)
        throws PersistenceException {

        PreparedStatement getUsers = null;
        ResultSet set = null;
        User user = null;
        Vector result = new Vector();

        try {
            getUsers = connection.prepareStatement(
                "select * from users");
            set = getUsers.executeQuery();
            while (set.next()) {
                user = new User(set.getString(1), set.getString(2));
                result.add(user);
            }
        } catch (Exception error) {
            throw new PersistenceException("Users.getAllUsers failed ",
                error);
        } finally {
            SQLHelper.close(set);
            SQLHelper.close(getUsers);
        }

        return result;
    }

}
