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
 * $Id: MasterUserStore.java,v 1.1 2005/10/20 14:07:03 tanderson Exp $
 */
package org.exolab.jms.tools.migration.master;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.jms.JMSException;

import org.exolab.jms.authentication.User;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.tools.migration.IteratorAdapter;
import org.exolab.jms.tools.migration.Store;
import org.exolab.jms.tools.migration.StoreIterator;


/**
 * <code>MasterMessageStore</code> manages a collection of persistent users.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/10/20 14:07:03 $
 */
public class MasterUserStore implements Store {

    /**
     * The database service.
     */
    private DatabaseService _database;


    /**
     * Construct a new <code>MasterUserStore</code>.
     *
     * @param database the database service
     */
    public MasterUserStore(DatabaseService database) {
        _database = database;
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
        Connection connection = _database.getConnection();
        while (iterator.hasNext()) {
            User user = (User) iterator.next();
            _database.getAdapter().addUser(connection, user);
        }
        _database.commit();
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
     * Returns the users.
     *
     * @return a list of {@link User} instances
     * @throws PersistenceException for any persistence error
     */
    private List getUsers() throws PersistenceException {
        List result = new ArrayList();
        Enumeration users;
        Connection connection = _database.getConnection();
        users = _database.getAdapter().getAllUsers(connection);
        while (users.hasMoreElements()) {
            result.add(users.nextElement());
        }
        _database.commit();
        return result;
    }
}
