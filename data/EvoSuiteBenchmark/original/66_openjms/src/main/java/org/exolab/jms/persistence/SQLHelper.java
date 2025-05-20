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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * SQL helper routines.
 *
 * @version     $Revision: 1.2 $ $Date: 2005/06/09 14:39:52 $
 * @author      <a href="mailto:tima@intalio.com">Tim Anderson</a>
 */
public class SQLHelper {

    public static boolean rollback(Connection connection) {
        boolean rolledback = false;
        if (connection != null) {
            try {
                connection.rollback();
                rolledback = true;
            } catch (SQLException ignore) {
            }
        }
        return rolledback;
    }

    public static boolean close(Connection connection) {
        boolean closed = false;
        if (connection != null) {
            try {
                connection.close();
                closed = true;
            } catch (SQLException ignore) {
            }
        }
        return closed;
    }

    public static boolean close(Statement statement) {
        boolean closed = false;
        if (statement != null) {
            try {
                statement.close();
                closed = true;
            } catch (SQLException ignore) {
            }
        }
        return closed;
    }

    public static boolean close(ResultSet set) {
        boolean closed = false;
        if (set != null) {
            try {
                set.close();
                closed = true;
            } catch (SQLException ignore) {
            }
        }
        return closed;
    }

    public static boolean close(InputStream is) {
        boolean closed = false;
        if (is != null) {
                try {
                    is.close();
                    closed = true;
                } catch (IOException ignore) {}
        }
        return closed;
    }

    public static boolean close(OutputStream os) {
        boolean closed = false;
        if (os != null) {
                try {
                    os.close();
                    closed = true;
                } catch (IOException ignore) {}
        }
        return closed;
    }

}
