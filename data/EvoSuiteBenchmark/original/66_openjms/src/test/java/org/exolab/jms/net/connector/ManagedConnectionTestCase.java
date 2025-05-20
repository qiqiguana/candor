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
 * $Id: ManagedConnectionTestCase.java,v 1.3 2006/12/16 12:37:17 tanderson Exp $
 */
package org.exolab.jms.net.connector;

import junit.framework.TestCase;

import java.security.Principal;


/**
 * Tests the {@link ManagedConnectionFactory} interface.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2006/12/16 12:37:17 $
 */
public abstract class ManagedConnectionTestCase extends TestCase {

    /**
     * The managed connection factory.
     */
    private ManagedConnectionFactory _factory;


    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name the name of test case
     */
    public ManagedConnectionTestCase(String name) {
        super(name);
    }

    /**
     * Tests {@link ManagedConnection#getConnection}.
     *
     * @throws Exception for any error
     */
    public void testGetConnection() throws Exception {
        Principal principal = null;

        // set up an acceptor to handle the connection request
        ManagedConnectionAcceptor acceptor = createAcceptor(principal);
        InvocationHandler handler = new TestInvocationHandler();
        TestAcceptorEventListener listener = new TestAcceptorEventListener(
                handler);
        acceptor.accept(listener);

        // create the managed connection
        ConnectionRequestInfo info = getManagedConnectionRequestInfo();
        ManagedConnection managed = _factory.createManagedConnection(
                principal, info);

        // verify that getConnection() fails if no InvocationHandler
        // is registered
        try {
            managed.getConnection();
            fail("Expected " + IllegalStateException.class.getName()
                    + " to be thrown");
        } catch (IllegalStateException exception) {
            // the expected behaviour
        } catch (Exception exception) {
            fail("Expected " + IllegalStateException.class.getName()
                    + " to be thrown, but got exception="
                    + exception.getClass().getName()
                    + ", message=" + exception.getMessage());
        }

        // register an InvocationHandler and verify that getConnection()
        // succeeds
        managed.setInvocationHandler(new TestInvocationHandler());

        Connection connection = managed.getConnection();
        assertNotNull(connection);

        // clean up
        managed.destroy();
        listener.destroy();
        acceptor.close();
    }

    /**
     * Verifies that an <code>InvocationHandler</code> can be registered,
     * and that <code>IllegalStateException</code> is thrown if
     * {@link ManagedConnection#setInvocationHandler} is invoked more
     * than once.
     *
     * @throws Exception for any error
     */
    public void testSetInvocationHandler() throws Exception {
        Principal principal = null;

        // set up an acceptor to handle the connection request
        ManagedConnectionAcceptor acceptor = createAcceptor(principal);
        InvocationHandler handler = new TestInvocationHandler();
        TestAcceptorEventListener listener = new TestAcceptorEventListener(
                handler);
        acceptor.accept(listener);

        // create the managed connection
        ManagedConnection managed = createConnection(principal);
        try {
            managed.setInvocationHandler(null);
            fail("Expected " + IllegalStateException.class.getName()
                    + " to be thrown");
        } catch (IllegalStateException exception) {
            // the expected behaviour
        } catch (Exception exception) {
            fail("Expected " + IllegalStateException.class.getName()
                    + " to be thrown, but got exception="
                    + exception.getClass().getName()
                    + ", message=" + exception.getMessage());
        }

        try {
            managed.setInvocationHandler(new TestInvocationHandler());
            fail("Expected " + IllegalStateException.class.getName()
                    + " to be thrown");
        } catch (IllegalStateException exception) {
            // the expected behaviour
        } catch (Exception exception) {
            fail("Expected " + IllegalStateException.class.getName()
                    + " to be thrown, but got exception="
                    + exception.getClass().getName()
                    + ", message=" + exception.getMessage());
        }

        // clean up
        managed.destroy();
        listener.destroy();
        acceptor.close();
    }

    /**
     * Tests {@link ManagedConnection#ping}, from the client perspective.
     *
     * @throws Exception for any error
     */
    public void testClientIsAlive() throws Exception {
        Principal principal = null;

        // set up an acceptor to handle the connection request
        ManagedConnectionAcceptor acceptor = createAcceptor(principal);
        InvocationHandler handler = new TestInvocationHandler();
        TestAcceptorEventListener listener = new TestAcceptorEventListener(
                handler);
        acceptor.accept(listener);

        // create the client connection
        ManagedConnection client = createConnection(principal);
        TestConnectionEventListener mcListener
                = new TestConnectionEventListener();
        client.setConnectionEventListener(mcListener);

        client.ping();
        Thread.sleep(1000);
        assertEquals(1, mcListener.getPinged());

        // destroy the server connection, and verify its dead from the
        // client perspective
        ManagedConnection server = listener.getConnection();
        assertNotNull(server);
        server.destroy();

        try {
            client.ping();
            Thread.sleep(1000);
            assertEquals(1, mcListener.getPinged());
        } catch (ResourceException alternative) {
            // ping could also throw an exception
        }

        // destroy the client
        client.destroy();

        try {
            client.ping();
            fail("Expected IllegalStateException to be thrown");
        } catch (IllegalStateException expected) {
            // expected behaviour
        }

        // clean up
        acceptor.close();
    }

    /**
     * Tests {@link ManagedConnection#ping}, from the server perspective.
     *
     * @throws Exception for any error
     */
    public void testServerIsAlive() throws Exception {
        Principal principal = null;

        // set up an acceptor to handle the connection request
        ManagedConnectionAcceptor acceptor = createAcceptor(principal);
        InvocationHandler handler = new TestInvocationHandler();
        TestAcceptorEventListener listener = new TestAcceptorEventListener(
                handler);
        acceptor.accept(listener);

        // create the client connection
        ManagedConnection client = createConnection(principal);

        // delay to enable the listener to get notified
        Thread.sleep(1000);

        // get the server connection
        ManagedConnection server = listener.getConnection();
        assertNotNull(server);
        TestConnectionEventListener mcListener
                = new TestConnectionEventListener();
        server.setConnectionEventListener(mcListener);

        server.ping();
        Thread.sleep(1000);
        assertEquals(1, mcListener.getPinged());

        // destroy the client connection, and verify its dead from the
        // server perspective
        client.destroy();

        try {
            server.ping();
            Thread.sleep(1000);
            assertEquals(1, mcListener.getPinged());
        } catch (ResourceException alternative) {
            // ping could also throw an exception
        }

        // destroy the server connection
        server.destroy();

        try {
            server.ping();
        } catch (IllegalStateException expected) {
            // the expected behaviour
        }

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
     *         acceptor
     * @throws Exception for any error
     */
    protected abstract ConnectionRequestInfo getAcceptorConnectionRequestInfo()
            throws Exception;

    /**
     * Helper to return the cached managed connection factory
     *
     * @return the cached managed connection factory
     */
    protected ManagedConnectionFactory getManagedConnectionFactory() {
        return _factory;
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
        ManagedConnection connection = _factory.createManagedConnection(
                principal, info);
        connection.setInvocationHandler(new TestInvocationHandler());
        return connection;
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
        return _factory.createManagedConnectionAcceptor(authenticator, info);
    }

    private class TestConnectionEventListener
            implements ManagedConnectionListener {

        /**
         * Determines the no. of times the connection has been pinged.
         */
        private int _pinged;

        /**
         * Notifies closure of a connection. The <code>ManagedConnection</code>
         * instance invokes this to notify its registered listeners when
         * the peer closes the connection.
         *
         * @param source the managed connection that is the source of the event
         */
        public void closed(ManagedConnection source) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        /**
         * Notifies a connection related error. The <code>ManagedConnection</code>
         * instance invokes this to notify its registered listeners of the
         * occurrence of a physical connection-related error.
         *
         * @param source    the managed connection that is the source of the event
         * @param throwable the error
         */
        public void error(ManagedConnection source, Throwable throwable) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        /**
         * Notifies of a successful ping.
         *
         * @param source the managed connection that is the source of the event
         */
        public synchronized void pinged(ManagedConnection source) {
            ++_pinged;
        }

        /**
         * Returns the no. of times a ping has been replied to.
         *
         * @return the no. of times a ping has been replied to
         */
        public synchronized int getPinged() {
            return _pinged;
        }
    }

}
