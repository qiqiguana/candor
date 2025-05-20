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
 * $Id: ManagedConnectionFactoryTestCase.java,v 1.4 2005/04/19 12:29:58 tanderson Exp $
 */
package org.exolab.jms.net.connector;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.exolab.jms.common.security.BasicPrincipal;


/**
 * Tests the {@link ManagedConnectionFactory} interface.
 *
 * @version     $Revision: 1.4 $ $Date: 2005/04/19 12:29:58 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
public abstract class ManagedConnectionFactoryTestCase extends TestCase {

    /**
     * The managed connection factory.
     */
    private ManagedConnectionFactory _factory;


    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name the name of test case
     */
    public ManagedConnectionFactoryTestCase(String name) {
        super(name);
    }

    /**
     * Tests {@link ManagedConnectionFactory#createConnectionFactory}.
     *
     * @throws Exception for any error
     */
    public void testCreateConnectionFactory() throws Exception {
        ConnectionManager manager = new BasicConnectionManager(
            _factory, new TestAuthenticator());
        ConnectionFactory factory = _factory.createConnectionFactory(manager);
        assertNotNull(factory);
    }

    /**
     * Verifies that a connection attempt fails with {@link ConnectException}
     * if no acceptor is running.
     *
     * @throws Exception for any error
     */
    public void testConnectException() throws Exception {
        try {
            createConnection(null);
            fail("Expected " + ConnectException.class.getName()
                 + " to be thrown");
        } catch (ConnectException exception) {
            // the expected behaviour
        } catch (Exception exception) {
            fail("Expected " + ConnectException.class.getName()
                 + " to be thrown, but got exception="
                 + exception.getClass().getName() + ", message="
                 + exception.getMessage());
        }
    }

    /**
     * Tests {@link ManagedConnectionFactory#createManagedConnection}, for
     * an unauthenticated connection.
     *
     * @throws Exception for any error
     */
    public void testCreateUnauthenticatedManagedConnection() throws Exception {
        Principal invalid = new BasicPrincipal("foo", "bar");
        checkCreateManagedConnection(null, invalid);
    }

    /**
     * Tests {@link ManagedConnectionFactory#createManagedConnection}, for
     * an authenticated connection.
     *
     * @throws Exception for any error
     */
    public void testCreateAuthenticatedManagedConnection() throws Exception {
        Principal principal = new BasicPrincipal("foo", "bar");
        checkCreateManagedConnection(principal, null);
    }

    /**
     * Tests {@link ManagedConnectionFactory#createManagedConnectionAcceptor}.
     *
     * @throws Exception for any error
     */
    public void testCreateManagedConnectionAcceptor() throws Exception {
        ManagedConnectionAcceptor acceptor = createAcceptor(null);
        assertNotNull(acceptor);
        acceptor.close();
    }

    /**
     * Tests {@link ManagedConnectionFactory#matchManagedConnections}
     *
     * @throws Exception for any error
     */
    public void testMatchManagedConnections() throws Exception {
        ManagedConnection match = null;
        Principal first = new BasicPrincipal("first", "password");
        Principal second = new BasicPrincipal("second", "password");
        Principal[] principals = new Principal[] {first, second};

        ConnectionRequestInfo info = getManagedConnectionRequestInfo();

        // set up an acceptor to handle connection requests
        ManagedConnectionAcceptor acceptor = createAcceptor(principals);
        TestAcceptorEventListener listener = new TestAcceptorEventListener(
                new TestInvocationHandler());
        acceptor.accept(listener);

        List connections = new ArrayList();
        for (int i = 0; i < principals.length; ++i) {
            match = _factory.matchManagedConnections(connections, principals[i],
                                                     info);
            assertNull(match);
        }

        // create a connection
        ManagedConnection connection1 = createConnection(first);
        connections.add(connection1);

        // verify it matches for principal 'first', and doesn't match
        // for principal 'second'
        match = _factory.matchManagedConnections(connections, first, info);
        assertEquals(connection1, match);
        match = _factory.matchManagedConnections(connections, second, info);
        assertNull(match);

        // create another connection for a different user
        ManagedConnection connection2 = createConnection(second);
        connections.add(connection2);

        // verify it matches for principal 'second', and doesn't match
        // for principal 'first'
        match = _factory.matchManagedConnections(connections, second, info);
        assertEquals(connection2, match);
        match = _factory.matchManagedConnections(connections, first, info);
        assertEquals(connection1, match);

        // make sure no errors were raised
        assertEquals(0, listener.getErrors().size());

        // clean up
        acceptor.close();
        listener.destroy();
        connection1.destroy();
        connection2.destroy();
    }

    /**
     * Tests {@link ManagedConnectionFactory#matchManagedConnectionAcceptors}.
     *
     * @throws Exception for any error
     */
    public void testMatchManagedConnectionAcceptors() throws Exception {
        ManagedConnectionAcceptor match = null;
        ConnectionRequestInfo info = getAcceptorConnectionRequestInfo();

        List acceptors = new ArrayList();
        match = _factory.matchManagedConnectionAcceptors(acceptors, info);
        assertNull(match);

        // create an acceptor
        ManagedConnectionAcceptor acceptor = createAcceptor(null);
        acceptors.add(acceptor);

        // verify it matches
        match = _factory.matchManagedConnectionAcceptors(acceptors, info);
        assertEquals(acceptor, match);

        // clean up
        acceptor.close();
    }

    /**
     * Sets up the test case.
     *
     * @throws Exception for any error
     */
    protected void setUp() throws Exception {
        _factory = createManagedConnectionFactory();
    }

    /**
     * Creates a managed connection factory.
     *
     * @return the new managed connection factory
     * @throws Exception for any error
     */
    protected abstract ManagedConnectionFactory
        createManagedConnectionFactory() throws Exception;

    /**
     * Returns the cached managed connection factory instance.
     *
     * @return the cached managed connection factory instance
     */
    protected ManagedConnectionFactory getManagedConnectionFactory() {
        return _factory;
    }

    /**
     * Returns connection request info suitable for creating a managed
     * connection.
     *
     * @return connection request info for creating a managed connection
     * @throws Exception for any error
     */
    protected abstract ConnectionRequestInfo getManagedConnectionRequestInfo()
        throws Exception;

    /**
     * Returns connection request info suitable for creating a managed
     * connection acceptor.
     *
     * @return connection request info for creating a managed connection
     * acceptor
     * @throws Exception for any error
     */
    protected abstract ConnectionRequestInfo getAcceptorConnectionRequestInfo()
        throws Exception;

    /**
     * Tests {@link ManagedConnectionFactory#createManagedConnection}.
     *
     * @param principal the principal to use. May be <code>null</code>
     * @param invalidPrincipal an invalid principal. May be <code>null</code>
     * @throws Exception for any error
     */
    protected void checkCreateManagedConnection(Principal principal,
                                                Principal invalidPrincipal)
        throws Exception {

        // set up an acceptor to handle the connection request
        Principal[] principals = new Principal[]{principal};
        ManagedConnectionAcceptor acceptor = createAcceptor(principals);
        TestAcceptorEventListener listener = new TestAcceptorEventListener(
                new TestInvocationHandler());
        acceptor.accept(listener);

        // create the connection
        ManagedConnection connection = createConnection(principal);
        assertNotNull(connection);

        // delay to enable the listener to get notified
        Thread.sleep(5000);

        // clean up client connection
        connection.destroy();

        // try and create a connection for an invalid principal
        try {
            ManagedConnection invalid = createConnection(invalidPrincipal);
            invalid.destroy();
            fail("Expected connection creation to fail for invalid principal");
        } catch (ResourceException expected) {
            // the expected behaviour
        }

        // verify that a single connection was accepted
        assertEquals(1, listener.getConnections().size());

        // clean up the accepted connection
        listener.destroy();

        // clean up the acceptor
        acceptor.close();
    }

    /**
     * Helper to create a managed connection.
     *
     * @param principal the principal to use. May be <code>null</code>
     * @throws Exception for any error
     */
    protected ManagedConnection createConnection(Principal principal)
        throws Exception {
        ConnectionRequestInfo info = getManagedConnectionRequestInfo();
        return createConnection(principal, info);
    }

    /**
     * Helper to create a managed connection.
     *
     * @param principal the principal to use. May be <code>null</code>
     * @param info the connection request info
     * @throws Exception for any error
     */
    protected ManagedConnection createConnection(Principal principal,
                                                 ConnectionRequestInfo info)
        throws Exception {
        ManagedConnection connection =
                _factory.createManagedConnection(principal, info);
        connection.setInvocationHandler(new TestInvocationHandler());
        return connection;
    }

    /**
     * Helper to create a managed connection acceptor.
     *
     * @param principals the principals to use. May be <code>null</code>
     * @throws Exception for any error
     */
    protected ManagedConnectionAcceptor createAcceptor(Principal[] principals)
        throws Exception {
        ConnectionRequestInfo info = getAcceptorConnectionRequestInfo();
        return createAcceptor(principals, info);
    }

    /**
     * Helper to create a managed connection acceptor.
     *
     * @param principals the principals to use. May be <code>null</code>
     * @param info the connection request info
     * @throws Exception for any error
     */
    protected ManagedConnectionAcceptor createAcceptor(
            Principal[] principals, ConnectionRequestInfo info)
        throws Exception {
        Authenticator authenticator = new TestAuthenticator(principals);
        return _factory.createManagedConnectionAcceptor(authenticator, info);
    }
}
