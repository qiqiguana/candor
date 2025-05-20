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
 * $Id: ConnectionFactoryTestCase.java,v 1.2 2005/05/03 13:46:00 tanderson Exp $
 */
package org.exolab.jms.net.connector;

import java.security.Principal;
import java.util.Map;

import junit.framework.TestCase;

import org.exolab.jms.net.uri.URI;
import org.exolab.jms.common.security.BasicPrincipal;


/**
 * Tests the {@link ConnectionFactory} interface.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/05/03 13:46:00 $
 */
public abstract class ConnectionFactoryTestCase extends TestCase {

    /**
     * The managed connection factory.
     */
    private final ManagedConnectionFactory _mcf;

    /**
     * The client connection manager.
     */
    private BasicConnectionManager _clientCM;

    /**
     * The server connection manager.
     */
    private BasicConnectionManager _serverCM;

    /**
     * The default connection URI.
     */
    private final URI _connectURI;

    /**
     * The default accept URI.
     */
    private final URI _acceptURI;

    /**
     * Principal to test with.
     */
    private static final Principal PRINCIPAL = new BasicPrincipal("foo", "bar");


    /**
     * Construct a new <code>ConnectionFactoryTestCase</code>, for a specific
     * test.
     *
     * @param name       the name of test case
     * @param factory    the managed connection factory
     * @param connectURI the connect URI
     * @param acceptURI  the accept URI
     * @throws Exception for any error
     */
    public ConnectionFactoryTestCase(String name,
                                     ManagedConnectionFactory factory,
                                     String connectURI, String acceptURI)
        throws Exception {
        super(name);
        _mcf = factory;
        _connectURI = new URI(connectURI);
        _acceptURI = new URI(acceptURI);
    }

    /**
     * Tests the {@link ConnectionFactory#canConnect} method.
     *
     * @throws Exception for any error
     */
    public void testCanConnect() throws Exception {
        ConnectionManager manager = initConnectionManagers(null);
        ConnectionFactory factory = _mcf.createConnectionFactory(manager);
        assertTrue(factory.canConnect(_connectURI));

        // make sure an invalid URI returns false
        URI invalid = new URI("xxx://");
        assertFalse(factory.canConnect(invalid));
    }

    /**
     * Tests the {@link ConnectionFactory#canAccept} method.
     *
     * @throws Exception for any error
     */
    public void testCanAccept() throws Exception {
        ConnectionManager manager = initConnectionManagers(null);
        ConnectionFactory factory = _mcf.createConnectionFactory(manager);
        assertTrue(factory.canAccept(_acceptURI));

        // make sure an invalid URI returns false
        URI invalid = new URI("xxx://");
        assertFalse(factory.canAccept(invalid));
    }

    /**
     * Tests the {@link ConnectionFactory#getConnection} method, for an
     * unauthenticated connection.
     *
     * @throws Exception for any error
     */
    public void testGetUnauthenticatedConnection() throws Exception {
        initConnectionManagers(null);

        ConnectionFactory serverCF= _mcf.createConnectionFactory(_serverCM);
        serverCF.accept(_acceptURI, getAcceptorProperties());

        Map properties = getConnectionProperties();
        ConnectionFactory clientCF = _mcf.createConnectionFactory(_clientCM);
        Connection connection = clientCF.getConnection(
                null, _connectURI, properties);
        assertNotNull(connection);

        // make sure can't connect when specifying a principal
        try {
            connection = clientCF.getConnection(PRINCIPAL, _connectURI,
                                                properties);
            fail("Expected ResourceException to be thrown");
        } catch (ResourceException expected) {
            // no-op
        }
    }

    /**
     * Tests the {@link ConnectionFactory#getConnection} method, for an
     * authenticated connection.
     *
     * @throws Exception for any error
     */
    public void testGetAuthenticatedConnection() throws Exception {
        Principal[] principals = new Principal[]{PRINCIPAL};

        initConnectionManagers(principals);
        ConnectionFactory serverCF= _mcf.createConnectionFactory(_serverCM);
        serverCF.accept(_acceptURI, getAcceptorProperties());

        Map properties = getConnectionProperties();
        ConnectionFactory clientCF = _mcf.createConnectionFactory(_clientCM);
        Connection connection = clientCF.getConnection(
                PRINCIPAL, _connectURI, properties);
        assertNotNull(connection);

        // make sure can't connect without a valid principal
        try {
            connection = clientCF.getConnection(new BasicPrincipal("x", "y"),
                                               _connectURI, properties);
            fail("Expected ResourceException to be thrown");
        } catch (ResourceException expected) {
            // no-op
        }
        try {
            connection = clientCF.getConnection(null, _connectURI, properties);
            fail("Expected ResourceException to be thrown");
        } catch (ResourceException expected) {
            // no-op
        }

        // make sure the client and server conneciton pools each have
        // a single physical connection
        checkPhysicalConnections(1);
    }

    /**
     * Returns the connection properties to use when creating {@link Connection}
     * instances.
     *
     * @return the connection properties, or <code>null</code>
     * @throws Exception for any error
     */
    protected Map getConnectionProperties() throws Exception {
        return null;
    }

    /**
     * Returns the acceptor properties to use when accepting connections.
     *
     * @return the acceptor properties, or <code>null</code>
     * @throws Exception for any error
     */
    protected Map getAcceptorProperties() throws Exception {
        return null;
    }

    /**
     * Initialises the connection managers.
     *
     * @param principals valid connection principals. May be <code>null</code>
     * @return the client connection manager
     * @throws ResourceException if the connection manager can't be initialised
     */
    protected BasicConnectionManager initConnectionManagers(
            Principal[] principals)
            throws ResourceException {
        Authenticator authenticator = new TestAuthenticator(principals);
        _serverCM = new BasicConnectionManager(_mcf, authenticator);
        _clientCM = new BasicConnectionManager(_mcf, new TestAuthenticator());
        return _clientCM;
    }

    /**
     * Sets up the test case.
     *
     * @throws Exception for any error
     */
    protected void setUp() throws Exception {
    }

    /**
     * Cleans up the test case.
     *
     * @throws Exception for any error
     */
    protected void tearDown() throws Exception {
        if (_clientCM != null) {
            _clientCM.close();
        }
        if (_serverCM != null) {
            _serverCM.close();
        }
    }

    /**
     * Verfifies that both the client and server connection pools each
     * have the expected no. of physical connections.
     *
     * @param expected the expected no. of physical connections
     * @throws ResourceException if a connection pool can't be found
     */
    private void checkPhysicalConnections(int expected) throws ResourceException {
        TestConnectionPool clientPool = _clientCM.getConnectionPool();
        assertEquals(expected, clientPool.getPooledConnections());

        TestConnectionPool serverPool = _serverCM.getConnectionPool();
        assertEquals(expected, serverPool.getPooledConnections());
    }

}
