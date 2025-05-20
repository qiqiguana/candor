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
 * Copyright 2004-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: HTTPSManagedConnectionFactory.java,v 1.2 2005/03/23 12:34:07 tanderson Exp $
 */
package org.exolab.jms.net.http;

import java.security.Principal;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.net.connector.ConnectionFactory;
import org.exolab.jms.net.connector.ConnectionManager;
import org.exolab.jms.net.connector.ConnectionRequestInfo;
import org.exolab.jms.net.connector.ManagedConnection;
import org.exolab.jms.net.connector.ManagedConnectionAcceptor;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.socket.SocketRequestInfo;


/**
 * A factory for {@link HTTPSConnectionFactory} and
 * {@link HTTPSManagedConnection} instances.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/03/23 12:34:07 $
 */
public class HTTPSManagedConnectionFactory
        extends AbstractHTTPManagedConnectionFactory {

    /**
     * Initicates if SSL has been initialised.
     */
    private static boolean _sslInit = false;

    /**
     * Protocol handler packages system property name
     */
    private static final String PROTOCOL_HANDLER_PKGS
            = "java.protocol.handler.pkgs";

    /**
     * Package of Sun SSL implementation.
     */
    private static final String SUN_PACKAGE
            = "com.sun.net.ssl.internal.www.protocol";

    /**
     * The logger.
     */
    private static final Log _log =
            LogFactory.getLog(HTTPSManagedConnectionFactory.class);

    /**
     * Creates a new connection factory.
     *
     * @param manager the connection manager
     * @return a new connection factory
     * @throws ResourceException if the factory cannot be created
     */
    public ConnectionFactory createConnectionFactory(ConnectionManager manager)
            throws ResourceException {
        return new HTTPSConnectionFactory(this, manager);
    }

    /**
     * Creates a new connection.
     *
     * @param principal the security principal
     * @param info      the connection request info
     * @return a new connection
     * @throws ResourceException if a connection cannot be established
     */
    public ManagedConnection createManagedConnection(Principal principal,
                                                     ConnectionRequestInfo info)
            throws ResourceException {
        if (!(info instanceof HTTPRequestInfo)) {
            throw new ResourceException("Argument 'info' must be of type "
                                        + HTTPRequestInfo.class.getName());
        }
        initSSL();

        return new HTTPSManagedConnection(principal, (HTTPRequestInfo) info);
    }

    /**
     * Creates an acceptor for connections.
     *
     * @param authenticator authenticates incoming connections
     * @param info          the connection request info
     * @return a new connection acceptor
     * @throws ResourceException if an acceptor cannot be created
     */
    public ManagedConnectionAcceptor createManagedConnectionAcceptor(
            Authenticator authenticator, ConnectionRequestInfo info)
            throws ResourceException {

        if (!(info instanceof SocketRequestInfo)) {
            throw new ResourceException("Argument 'info' must be of type "
                                        + SocketRequestInfo.class.getName());
        }

        return new HTTPSManagedConnectionAcceptor(authenticator,
                                                  (SocketRequestInfo) info);
    }

    /**
     * Initialise SSL. This is only applicable for Sun JDK 1.2 and 1.3,
     * which need to have the <code>java.protocol.handler.pkgs</code>
     * system property set in order for the <code>java.net.URL</code>
     * class to support https.
     * If the property can't be set due to security permissions, creation of
     * HTTPSManagedConnection instances will fail.
     * <p>
     * When running Non-Sun JREs, clients must set the
     * java.protocol.handler.pkgs property themselves
     */
    private static synchronized void initSSL() {
        if (!_sslInit) {
            try {
                String value = System.getProperty(PROTOCOL_HANDLER_PKGS);
                if (value == null) {
                    value = SUN_PACKAGE;
                } else if (value.indexOf(SUN_PACKAGE) == -1) {
                    if (value.length() > 0) {
                        value += "|";
                    }
                    value += SUN_PACKAGE;
                }
                System.setProperty(PROTOCOL_HANDLER_PKGS, SUN_PACKAGE);
                _sslInit = true;
            } catch (SecurityException exception) {
                if (_log.isDebugEnabled()) {
                    _log.debug("Failed to set property="
                               + PROTOCOL_HANDLER_PKGS, exception);
                }
            }
        }
    }


}
