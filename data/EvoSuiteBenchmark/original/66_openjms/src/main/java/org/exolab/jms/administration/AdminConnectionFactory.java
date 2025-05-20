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
 * Copyright 2001-2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: AdminConnectionFactory.java,v 1.1 2004/11/26 01:50:38 tanderson Exp $
 */
package org.exolab.jms.administration;

import java.net.MalformedURLException;

import javax.jms.JMSException;

import org.exolab.jms.administration.net.JmsAdminConnectionImpl;


/**
 * This class is a factory for creating {@link AdminConnection} instances.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:38 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @author      <a href="mailto:knut@lerpold.no">Knut Lerpold</a>
 * @see         JmsAdminServerIfc
 */
public class AdminConnectionFactory {

    /**
     * Attempt to establish an admin connection
     *
     * @param url the OpenJMS server URL
     * @param username the admin user name
     * @param password the admin user password
     * @return an admin connection
     * @throws JMSException if a connection cannot be established
     * @throws MalformedURLException if <code>url</code> is invalid
     */
    private static AdminConnection getConnection(String url, String username,
                                                 String password)
        throws JMSException, MalformedURLException {
        return new JmsAdminConnectionImpl(url, username, password);
    }

    /**
     * Create an unauthenticated admin connection, using the specified server
     * URL
     *
     * @param url the OpenJMS server URL
     * @return JmsAdminServerIfc the administration API
     * @throws JMSException if a connection cannot be established
     * @throws MalformedURLException if <code>url</code> is invalid
     */
    public static JmsAdminServerIfc create(String url)
        throws JMSException, MalformedURLException {
        return create(url, null, null);
    }

    /**
     * Create an authenticated admin connection, using the specified server
     * URL, name, and password
     *
     * @param url the OpenJMS server URL
     * @param username the admin user name
     * @param password the admin user password
     * @return JmsAdminServerIfc the administration API
     * @throws JMSException if a connection cannot be established
     */
    public static JmsAdminServerIfc create(String url, String username,
                                           String password)
        throws JMSException, MalformedURLException {
        return new JmsAdminConnectionImpl(url, username, password);
    }

}

