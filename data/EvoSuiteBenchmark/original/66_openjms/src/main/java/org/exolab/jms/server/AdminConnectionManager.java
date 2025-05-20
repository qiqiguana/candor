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
package org.exolab.jms.server;

import java.security.Principal;
import javax.jms.JMSException;
import javax.jms.JMSSecurityException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.common.security.BasicPrincipal;

/**
 * <code>AdminConnectionManager</code> is responsible for creating
 * authenticated {@link AdminConnection} instances
 *
 *  The connection manager is a singleton (at this point anyway)
 * that is accessible through the instance class method. It is also responsible
 * for holding a list of adminconnections.
 *
 * @version     $Revision: 1.4 $ $Date: 2005/12/26 06:26:35 $
 * @author      <a href="mailto:knut@lerpold.no">Knut Lerpold</a>
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         AdminConnection
 */
public class AdminConnectionManager {

    /**
     * The authenticator.
     */
    private final Authenticator _authenticator;

    /**
     * The connection factory.
     */
    private final AdminConnectionFactory _factory;

    /**
     * The logger.
     */
    private final Log _log = LogFactory.getLog(AdminConnectionManager.class);


    /**
     * Construct a new <code>AdminConnectionManager</code>.
     */
    public AdminConnectionManager(Authenticator authenticator,
                                  AdminConnectionFactory factory) {
        if (authenticator == null) {
            throw new IllegalArgumentException(
                    "Argument 'authenticator' is null");
        }
        _authenticator = authenticator;
        _factory = factory;
    }

    /**
     * Create a new admin connection.
     *
     * @param username the client's username
     * @param password the client's password
     * @return a new admin connection
     * @throws JMSSecurityException if the client cannot be authenticated
     * @throws JMSException if the connection cannot be created
     */
    public AdminConnection createConnection(String username, String password)
        throws JMSSecurityException, JMSException {
        Principal principal = null;
        if (username != null) {
            principal = new BasicPrincipal(username, password);
        }
        try {
            if (!_authenticator.authenticate(principal)) {
                throw new JMSSecurityException("Failed to authenticate user " +
                                               username);
            }
        } catch (ResourceException exception) {
            _log.error(exception, exception);
            throw new JMSSecurityException("Failed to authenticate user "
                                           + username);
        }

        return _factory.create();
    }

}

