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
 * $Id: HTTPSManagedConnectionTest.java,v 1.4 2005/05/03 13:46:00 tanderson Exp $
 */
package org.exolab.jms.net.http.connector;

import java.security.Principal;
import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.net.connector.ConnectionRequestInfo;
import org.exolab.jms.net.connector.ManagedConnectionAcceptor;
import org.exolab.jms.net.connector.ManagedConnectionFactory;
import org.exolab.jms.net.connector.ManagedConnectionTestCase;
import org.exolab.jms.net.connector.TestAuthenticator;
import org.exolab.jms.net.http.HTTPRequestInfo;
import org.exolab.jms.net.http.HTTPSManagedConnectionFactory;
import org.exolab.jms.net.socket.SocketRequestInfo;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.util.SSLProperties;
import org.exolab.jms.net.util.SSLUtil;


/**
 * Tests the <code>HTTPSManagedConnection</code> class.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2005/05/03 13:46:00 $
 */
public class HTTPSManagedConnectionTest extends ManagedConnectionTestCase {

    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name the name of test case
     */
    public HTTPSManagedConnectionTest(String name) {
        super(name);
    }

    /**
     * Sets up the test suite.
     *
     * @return a test suite
     */
    public static Test suite() {
        return new TestSuite(HTTPSManagedConnectionTest.class);
    }

    /**
     * The main line used to execute the test cases.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    /**
     * Creates a managed connection factory.
     *
     * @return the new managed connection factory
     * @throws Exception for any error
     */
    protected ManagedConnectionFactory createManagedConnectionFactory()
            throws Exception {
        return new HTTPSManagedConnectionFactory();
    }

    /**
     * Returns connection request info suitable for creating a managed
     * connection.
     *
     * @return connection request info for creating a managed connection
     * @throws Exception for any error
     */
    protected ConnectionRequestInfo getManagedConnectionRequestInfo()
            throws Exception {
        HTTPRequestInfo result = new HTTPRequestInfo(
                new URI("https://localhost:8443/openjms-tunnel/tunnel"));

        result.setSSLProperties(getSSLProperties());
        return result;
    }

    /**
     * Returns connection request info suitable for creating a managed
     * connection acceptor.
     *
     * @return connection request info for creating a managed connection
     *         acceptor
     * @throws Exception for any error
     */
    protected ConnectionRequestInfo getAcceptorConnectionRequestInfo()
            throws Exception {
        SocketRequestInfo result = new SocketRequestInfo(
                new URI("https-server://localhost:3030"));
        return result;
    }

    /**
     * Helper to create a managed connection acceptor.
     *
     * @param principal the principal to use. May be <code>null</code>
     * @throws Exception for any error
     */
    protected ManagedConnectionAcceptor createAcceptor(Principal principal)
            throws Exception {
        ConnectionRequestInfo info = getAcceptorConnectionRequestInfo();
        Authenticator authenticator = new TestAuthenticator(principal);
        ManagedConnectionFactory factory = new HTTPSManagedConnectionFactory();
        return factory.createManagedConnectionAcceptor(authenticator, info);
    }

    /**
     * Returns SSL connection properties.
     *
     * @return SSL connection properties.
     * @throws IOException if the keystore can't be found.
     */
    private SSLProperties getSSLProperties() throws IOException {
        return SSLUtil.getSSLProperties("test.keystore", "secret");
    }
}
