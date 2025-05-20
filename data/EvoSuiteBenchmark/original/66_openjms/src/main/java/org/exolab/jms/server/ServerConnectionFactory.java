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
 * $Id: ServerConnectionFactory.java,v 1.1 2005/03/18 04:07:02 tanderson Exp $
 */
package org.exolab.jms.server;

import javax.jms.InvalidClientIDException;
import javax.jms.JMSException;
import javax.jms.JMSSecurityException;


/**
 * Indicates the methods clients can call on the server-side implementation of
 * the {@link javax.jms.ConnectionFactory} interface
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/03/18 04:07:02 $
 */
public interface ServerConnectionFactory {

    /**
     * Creates a connection with the specified user identity.
     * <p/>
     * The connection is created in stopped mode. No messages will be delivered
     * until the <code>Connection.start</code> method is explicitly called.
     * <p/>
     * If <code>clientID</code> is specified, it indicates the pre-configured
     * client identifier associated with the client <code>ConnectionFactory</code>
     * object.
     *
     * @param clientID the pre-configured client identifier. May be
     *                 <code>null</code>
     * @param userName the caller's user name
     * @param password the caller's password
     * @return a newly created connection
     * @throws InvalidClientIDException if the JMS client specifies an invalid
     *                                  or duplicate client ID.
     * @throws JMSException             if the JMS provider fails to create the
     *                                  connection due to some internal error.
     * @throws JMSSecurityException     if client authentication fails due to an
     *                                  invalid user name or password.
     */
    ServerConnection createConnection(String clientID, String userName,
                                      String password)
            throws JMSException;

}
