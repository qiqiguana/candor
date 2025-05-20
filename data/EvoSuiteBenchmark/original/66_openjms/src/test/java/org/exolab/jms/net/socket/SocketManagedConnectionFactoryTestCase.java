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
 * $Id: SocketManagedConnectionFactoryTestCase.java,v 1.4 2005/12/01 13:44:39 tanderson Exp $
 */
package org.exolab.jms.net.socket;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.net.connector.ConnectionRequestInfo;
import org.exolab.jms.net.connector.ManagedConnection;
import org.exolab.jms.net.connector.ManagedConnectionAcceptor;
import org.exolab.jms.net.connector.ManagedConnectionFactory;
import org.exolab.jms.net.connector.ManagedConnectionFactoryTestCase;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.connector.TestAcceptorEventListener;
import org.exolab.jms.net.connector.TestInvocationHandler;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.uri.URIHelper;


/**
 * Tests the {@link SocketManagedConnectionFactory}.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2005/12/01 13:44:39 $
 */
public abstract class SocketManagedConnectionFactoryTestCase extends
        ManagedConnectionFactoryTestCase {

    /**
     * The acceptor URI.
     */
    private final URI _uri;

    /**
     * The logger.
     */
    static final Log _log
            = LogFactory.getLog(SocketManagedConnectionFactoryTestCase.class);


    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name the name of test case
     * @param uri  the acceptor URI
     * @throws Exception for any error
     */
    public SocketManagedConnectionFactoryTestCase(String name, String uri)
            throws Exception {
        super(name);
        _uri = new URI(uri);
    }

    /**
     * Verifies that clients can connect via the the alternative URI specified
     * by {@link SocketRequestInfo#getAlternativeHost()}, if the primary URI
     * cannot be reached.
     *
     * @throws Exception for any error
     */
    public void testAlternativeURI() throws Exception {
        URI uri = new URI(_uri);
        uri.setHost("anonexistenthost1");
        checkAlternativeURI(uri, _uri.getHost());
    }

    /**
     * Verifies that clients can connect via the the alternative URI specified
     * by {@link SocketRequestInfo#getAlternativeHost()}, if the primary URI
     * cannot be reached.
     * In this case, a SecurityException is thrown when attempting to connect
     * to the primary URI.
     *
     * @throws Exception for any error
     */
    public void testAlternativeURIWithSecMgr() throws Exception {
        final URI uri= new URI(_uri);
        uri.setHost("anonexistenthost2");

        SecurityManager manager = new SecurityManager() {
            public void checkConnect(String host, int port) {
                if (host.equals(uri.getHost()) && port == uri.getPort()) {
                    throw new SecurityException(
                            "Cant connect to " + host + ":" + port);
                }
                // let everthing else connect
            }
        };
        SecurityManager original = System.getSecurityManager();
        System.setSecurityManager(manager);
        try {
            checkAlternativeURI(uri, _uri.getHost());
        } finally {
            System.setSecurityManager(original);
        }
    }

    /**
     * Tests the behaviour of setting {@link SocketRequestInfo#getBindAll} to
     * <code>false</code> to restrict connections to a single address.
     *
     * @throws Exception for any error
     */
    public void testBindSingle() throws Exception {
        String scheme = _uri.getScheme();
        int port = _uri.getPort();

        // set up the acceptor to only accept connections via
        // "127.0.0.1"
        final String loopbackIP = "127.0.0.1";
        if (InetAddress.getLocalHost().getHostAddress().equals(loopbackIP)) {
            fail("Local host address must not be the same as "
                 + loopbackIP + " in order for this test case to run");

        }
        URI loopback = URIHelper.create(scheme, loopbackIP, port);
        SocketRequestInfo acceptInfo = getSocketRequestInfo(loopback);
        acceptInfo.setBindAll(false);

        // create the acceptor
        ManagedConnectionAcceptor acceptor
                = createAcceptor(null, acceptInfo);
        TestAcceptorEventListener listener = new TestAcceptorEventListener(
                new TestInvocationHandler());
        acceptor.accept(listener);

        // connections to this should fail
        String host = InetAddress.getLocalHost().getHostName();
        URI localhost = URIHelper.create(scheme, host, port);
        SocketRequestInfo failInfo = getSocketRequestInfo(localhost);

        // verify that a connection can't be established
        try {
            createConnection(null, failInfo);
            fail("Expected connection to " + localhost + " to fail");
        } catch (ResourceException exception) {
            // the expected behaviour
        }

        // verify that a connection can be established via the loopback URI
        SocketRequestInfo info = getSocketRequestInfo(loopback);
        ManagedConnection connection = null;
        try {
            connection = createConnection(null, info);
        } catch (Exception exception) {
            fail("Expected connections to " + loopback + " to succeed:" +
                 exception);
        }

        // clean up
        connection.destroy();

        // NB: the ServerSocket doesn't seem to close down under JDK 1.3.1
        //  and 1.4.1 if the accepted sockets aren't closed first.
        // This only happens when the ServerSocket is bound to a single address
        listener.destroy();

        acceptor.close();
    }

    /**
     * Tests connection matching when the alternative URI is used.
     *
     * @throws Exception for any error
     */
    public void testMatchManagedConnectionsWithAlternativeURI()
            throws Exception {
        // create the acceptor
        SocketRequestInfo info = getSocketRequestInfo(_uri);
        ManagedConnectionAcceptor acceptor = createAcceptor(null, info);
        TestAcceptorEventListener listener = new TestAcceptorEventListener(
                new TestInvocationHandler());
        acceptor.accept(listener);

        // create a connection
        List connections = new ArrayList();
        ManagedConnection connection = connection = createConnection(null, info);
        connections.add(connection);

        // verify connection matching
        ManagedConnectionFactory factory = getManagedConnectionFactory();
        ManagedConnection match = null;

        // make sure that the created connection matches the info used
        // to establish it
        match = factory.matchManagedConnections(connections, null, info);
        assertEquals(connection, match);

        // make sure connection matching works when the alternative URI
        // is the same as the acceptors.
        URI failURI = getUnusedURI();
        SocketRequestInfo altInfo = getSocketRequestInfo(failURI);
        altInfo.setAlternativeHost(_uri.getHost());

        // make sure there is no match when none of the URIs are the same.
        SocketRequestInfo failInfo = getSocketRequestInfo(failURI);
        match = factory.matchManagedConnections(connections, null, failInfo);
        assertNull(match);

        // clean up
        acceptor.close();
        listener.destroy();
        connection.destroy();
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
        return getSocketRequestInfo(_uri);
    }

    /**
     * Returns connection request info suitable for creating a managed
     * connection acceptor.
     * <p/>
     * This implementation returns that returned by {@link
     * #getManagedConnectionRequestInfo()}.
     *
     * @return connection request info for creating a managed connection
     *         acceptor
     * @throws Exception for any error
     */
    protected ConnectionRequestInfo getAcceptorConnectionRequestInfo()
            throws Exception {
        return getManagedConnectionRequestInfo();
    }

    /**
     * Returns socket request info, for the specified URI, suitable for creating
     * a managed connection and connection acceptor.
     *
     * @return socket request info for creating a managed connection
     * @throws Exception for any error
     */
    protected SocketRequestInfo getSocketRequestInfo(URI uri)
            throws Exception {
        return new SocketRequestInfo(uri);
    }

    /**
     * Returns a unused acceptor URI, for use by the {@link
     * #testAlternativeURI()} test case.
     * <p/>
     * This implementation uses the acceptor URI supplied at construction,
     * with an invalid host name
     *
     * @return an unused acceptor URI
     * @throws Exception for any error
     */
    protected URI getUnusedURI() throws Exception {
        URI result = new URI(_uri);
        result.setHost("someinvalidhostname");
        return result;
    }

    /**
     * Verifies that clients can connect via the the alternative URI determined
     * by {@link SocketRequestInfo#getAlternativeHost()}, if the primary URI
     * cannot be reached.
     *
     * @param uri an URI with host that cannot be connected to
     * @param alternativeHost the alternative host to connect to
     * @throws Exception for any error
     */
    private void checkAlternativeURI(URI uri, String alternativeHost)
            throws Exception {
        URI successURI = new URI(uri);
        successURI.setHost(alternativeHost);
        SocketRequestInfo acceptInfo = getSocketRequestInfo(successURI);

        SocketRequestInfo failInfo = getSocketRequestInfo(uri);
        // connections to this should fail

        SocketRequestInfo successInfo = getSocketRequestInfo(uri);
        successInfo.setAlternativeHost(successURI.getHost());
        // connections to this should succeed (on second attempt)

        // create the acceptor
        ManagedConnectionAcceptor acceptor = createAcceptor(null, acceptInfo);
        TestAcceptorEventListener listener = new TestAcceptorEventListener(
                new TestInvocationHandler());
        acceptor.accept(listener);

        // verify that a connection can't be established to uri
        try {
            createConnection(null, failInfo);
            fail("Expected connection to " + uri + " to fail");
        } catch (ResourceException exception) {
            // the expected behaviour
        }

        // verify that a connection can be established via the alternative URI
        ManagedConnection connection = null;
        try {
            connection = createConnection(null, successInfo);
        } catch (Exception exception) {
            fail("Expected connections to " + successURI + " to succeed:" +
                 exception);
        }

        // clean up
        connection.destroy();
        acceptor.close();
    }

}
