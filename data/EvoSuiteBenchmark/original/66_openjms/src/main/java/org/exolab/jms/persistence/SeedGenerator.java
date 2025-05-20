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
 * Copyright 2000-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 */
package org.exolab.jms.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * This class generates seeds.
 *
 * @version     $Revision: 1.4 $ $Date: 2005/08/31 05:45:50 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
class SeedGenerator {

    /**
     * Return the next seed value for the given name.
     *
     * @param connection the connection to use
     * @param name - the name of the seed
     * @return long - the next seed value
     * @exception PersistenceException - if the seed cannot be retrieved
     */
    public synchronized long next(Connection connection, String name)
        throws PersistenceException {

        PreparedStatement select = null;
        PreparedStatement update = null;
        PreparedStatement insert = null;
        ResultSet result = null;
        long value = 0;

        try {
            select = connection.prepareStatement(
                "select seed from seeds where name=?");
            select.setString(1, name);

            result = select.executeQuery();
            if (result.next()) {
                value = result.getLong(1);
                value++;
                update = connection.prepareStatement(
                    "update seeds set seed=? where name=?");
                update.setLong(1, value);
                update.setString(2, name);
                update.executeUpdate();
            } else {
                value = 1;
                insert = connection.prepareStatement(
                    "insert into seeds (name, seed) values (?,?)");
                insert.setString(1, name);
                insert.setLong(2, value);
                insert.executeUpdate();
            }
        } catch (Exception exception) {
            throw new PersistenceException("Failed to generate seed="+ name,
                                           exception);
        } finally {
            SQLHelper.close(result);
            SQLHelper.close(select);
            SQLHelper.close(update);
            SQLHelper.close(insert);
        }

        return value;
    }

    /**
     * Remove all seeds.
     *
     * @param connection the connection to use
     * @exception PersistenceException if the seeds can't be removed
     */
    public void removeAll(Connection connection)
        throws PersistenceException {

        PreparedStatement delete = null;
        try {
            delete = connection.prepareStatement("delete from seeds");
            delete.executeUpdate();
        } catch (Exception error) {
            throw new PersistenceException("Failed in removeAll with " +
                error.toString());
        } finally {
            SQLHelper.close(delete);
        }
    }

}
