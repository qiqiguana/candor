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
 * Copyright 2003-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: RegistryTestCase.java,v 1.4 2005/05/27 13:53:52 tanderson Exp $
 */
package org.exolab.jms.net.registry;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.net.EchoService;
import org.exolab.jms.net.EchoServiceImpl;
import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.net.connector.TestAuthenticator;
import org.exolab.jms.net.invoke.ORBTestCase;
import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.proxy.Proxy;
import org.exolab.jms.common.security.BasicPrincipal;


/**
 * Tests the behaviour of the {@link Registry}.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2005/05/27 13:53:52 $
 */
public abstract class RegistryTestCase extends ORBTestCase {

    /**
     * The service.
     */
    private Proxy _service;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(RegistryTestCase.class);

    /**
     * Echo service name
     */
    private static final String ECHO_SERVICE = "echo";


    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name the name of test case
     * @param uri  the export URI
     */
    public RegistryTestCase(String name, String uri) {
        super(name, uri);
    }

    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name       the name of test case
     * @param uri        the export URI
     * @param properties connection properties. May be <code>null</code>
     */
    public RegistryTestCase(String name, String uri, Map properties) {
        super(name, uri, properties);
    }

    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name     the name of test case
     * @param uri      the export URI
     * @param routeURI the route URI
     */
    public RegistryTestCase(String name, String uri, String routeURI) {
        super(name, uri, routeURI);
    }

    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name       the name of test case
     * @param uri        the export URI
     * @param routeURI   the route URI
     * @param properties connection properties. May be <code>null</code>
     */
    public RegistryTestCase(String name, String uri, String routeURI,
                            Map properties) {
        super(name, uri, routeURI, properties);
    }

    /**
     * Verifies that the registry can be accessed via proxy.
     *
     * @throws Exception for any error
     */
    public void testGetRegistry() throws Exception {
        Registry registry = getRegistry();
        assertNotNull(registry);
        assertTrue(registry instanceof Proxy);

        // shouldn't be an instance of LocalRegistry as that would
        // enable remote clients to change the read-only state
        assertTrue(!(registry instanceof LocalRegistry));
    }

    /**
     * Verifies that the registry can be accessed by an authenticated user.
     *
     * @throws Exception for any error
     */
    public void testGetRegistryWithAuth() throws Exception {
        getORB().shutdown(); // default ORB setup withouth auth

        // set up the orb
        BasicPrincipal principal = new BasicPrincipal("user", "password");
        Authenticator authenticator = new TestAuthenticator(principal);
        ORB orb = createORB(authenticator);

        // set up the echo service
        EchoService service = new EchoServiceImpl();
        _service = orb.exportObject(service);
        assertTrue(_service instanceof EchoService);
        orb.getRegistry().bind(ECHO_SERVICE, _service);

        // make sure a valid user can perform a lookup
        Registry registry = getRegistry(principal);
        assertNotNull(registry);
        Proxy proxy = registry.lookup(ECHO_SERVICE);
        assertTrue(proxy instanceof EchoService);

        // make sure an invalid user throws AccessException
        try {
            registry = getRegistry();
            fail("Expected AccessException to be thrown");
        } catch (AccessException exception) {
            // expected behaviour
        } catch (Exception exception) {
            fail("Expected AccessException to be thrown, but got " + exception);
        }


    }

    /**
     * Verifies that an object can be bound and subsequently looked up.
     *
     * @throws Exception for any error
     */
    public void testLookup() throws Exception {
        Registry registry = getRegistry();
        checkLookup(registry);
    }

    /**
     * Verifies that an object can be bound via the local registry, and
     * subsequently looked up.
     *
     * @throws Exception for any error
     */
    public void testLocalLookup() throws Exception {
        ORB orb = getORB();
        Registry registry = orb.getRegistry();
        checkLookup(registry);
    }

    /**
     * Verifies that a lookup on unbound object throws <code>NotBoundException</code>.
     *
     * @throws Exception for any error
     */
    public void testNotBound() throws Exception {
        Registry registry = getRegistry();
        checkNotBound(registry);
    }

    /**
     * Verifies that a lookup on unbound object throws <code>NotBoundException</code>.
     * via the local registry
     *
     * @throws Exception for any error
     */
    public void testLocalNotBound() throws Exception {
        ORB orb = getORB();
        Registry registry = orb.getRegistry();
        checkNotBound(registry);
    }

    /**
     * Verifies that attempting to bind to an already bound name throws
     * <code>AlreadyBoundException</code>.
     *
     * @throws Exception for any error
     */
    public void testAlreadyBound() throws Exception {
        Registry registry = getRegistry();
        checkAlreadyBound(registry);
    }

    /**
     * Verifies that attempting to bind to an already bound name throws
     * <code>AlreadyBoundException</code>, via the local registry.
     *
     * @throws Exception for any error
     */
    public void testLocalAlreadyBound() throws Exception {
        ORB orb = getORB();
        Registry registry = orb.getRegistry();
        checkAlreadyBound(registry);
    }

    /**
     * Verifies that objects can be unbound.
     *
     * @throws Exception for any error
     */
    public void testUnbind() throws Exception {
        Registry registry = getRegistry();
        checkUnbind(registry);
    }

    /**
     * Verifies that objects can be unbound via the local registry.
     *
     * @throws Exception for any error
     */
    public void testLocalUnbind() throws Exception {
        ORB orb = getORB();
        Registry registry = orb.getRegistry();
        checkUnbind(registry);
    }

    /**
     * Verifies that remote binds and unbinds fail when the registry is read
     * only.
     *
     * @throws Exception for any error
     */
    public void testReadOnly() throws Exception {
        ORB orb = getORB();
        LocalRegistry local = orb.getRegistry();
        Registry remote = getRegistry();

        // registry is read-write by default
        assertFalse(local.getReadOnly());

        // make the registry read only, for remote users
        local.setReadOnly(true);
        assertTrue(local.getReadOnly());

        // attempt to bind the service via the proxy
        try {
            remote.bind(ECHO_SERVICE, _service);
            fail("Expected bind to fail with exception "
                 + AccessException.class.getName());
        } catch (AccessException expected) {
            // the expected behaviour
        }

        // bind it via the local instance and verify it can be looked
        // up via the proxy
        local.bind(ECHO_SERVICE, _service);
        Proxy proxy = remote.lookup(ECHO_SERVICE);
        assertNotNull(proxy);
        assertTrue(proxy instanceof EchoService);        

        // attempt to unbind the service via the proxy
        try {
            remote.unbind(ECHO_SERVICE);
            fail("Expected unbind to fail with exception "
                 + AccessException.class.getName());
        } catch (AccessException expected) {
            // the expected behaviour
        }

        // make the registry read-write, and verify the proxy can unbind
        // and rebind the service, and look it up
        local.setReadOnly(false);
        remote.unbind(ECHO_SERVICE);
        remote.bind(ECHO_SERVICE, _service);
        proxy = remote.lookup(ECHO_SERVICE);
        assertNotNull(proxy);
        assertTrue(proxy instanceof EchoService);
    }

    /**
     * Sets up the test case.
     *
     * @throws Exception for any error
     */
    protected void setUp() throws Exception {
        super.setUp();
        // export the service
        ORB orb = getORB();
        EchoService service = new EchoServiceImpl();
        _service = orb.exportObject(service);
        assertTrue(_service instanceof EchoService);

        // make sure registry is available
        orb.getRegistry();
    }

    /**
     * Verifies that a service can be bound an subsequently looked up.
     *
     * @param registry the registry
     * @throws Exception for any error
     */
    private void checkLookup(Registry registry) throws Exception {
        // bind the service, and look it up
        registry.bind(ECHO_SERVICE, _service);
        Proxy proxy = registry.lookup(ECHO_SERVICE);
        assertNotNull(proxy);
        assertTrue(proxy instanceof EchoService);
    }

    /**
     * Verifies that a lookup on unbound object throws <code>NotBoundException</code>.
     *
     * @param registry the registry
     * @throws Exception for any error
     */
    private void checkNotBound(Registry registry) throws Exception {
        try {
            _log.debug("Looking up unbound object");
            registry.lookup("Foo");
            fail("Expected lookup of unbound object to throw "
                 + NotBoundException.class.getName());
        } catch (NotBoundException expected) {
            // expected behaviour
        } catch (Exception exception) {
            fail("Expected lookup of unbound object to throw "
                 + NotBoundException.class.getName() + ", but threw "
                 + exception.getClass().getName() + ": " + exception);
        }
    }

    /**
     * Verifies that attempting to bind to an already bound name throws
     * <code>AlreadyBoundException</code>.
     *
     * @param registry the registry
     * @throws Exception for any error
     */
    private void checkAlreadyBound(Registry registry) throws Exception {
        // bind the service
        registry.bind(ECHO_SERVICE, _service);

        try {
            registry.bind(ECHO_SERVICE, _service);
            fail("Expected attempt to bind to existing name to throw "
                 + AlreadyBoundException.class.getName());
        } catch (AlreadyBoundException ignore) {
            // expected behaviour
        } catch (Exception exception) {
            fail("Expected attempt to bind to existing name to throw "
                 + AlreadyBoundException.class.getName() + ", but threw "
                 + exception.getClass().getName() + ": " + exception);
        }
    }

    /**
     * Verifies that objects can be unbound.
     *
     * @param registry the registry
     * @throws Exception for any error
     */
    private void checkUnbind(Registry registry) throws Exception {
        // bind the service
        registry.bind(ECHO_SERVICE, _service);

        // unbind it, and verify it can't be looked up
        registry.unbind(ECHO_SERVICE);
        try {
            registry.lookup(ECHO_SERVICE);
            fail("unbind() failed. Expected lookup of unbound object to throw "
                 + NotBoundException.class.getName());
        } catch (NotBoundException ignore) {
            // expected behaviour
        } catch (Exception exception) {
            fail("unbind() failed. Expected lookup of unbound object to throw "
                 + NotBoundException.class.getName() + ", but threw "
                 + exception.getClass().getName() + ": " + exception);
        }
    }

}
