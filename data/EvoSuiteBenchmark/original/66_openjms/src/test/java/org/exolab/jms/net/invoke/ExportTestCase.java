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
 * $Id: ExportTestCase.java,v 1.2 2005/05/27 13:52:23 tanderson Exp $
 */
package org.exolab.jms.net.invoke;

import java.rmi.server.ExportException;
import java.rmi.NoSuchObjectException;
import java.util.Map;

import org.exolab.jms.common.security.BasicPrincipal;
import org.exolab.jms.net.EchoService;
import org.exolab.jms.net.EchoServiceImpl;
import org.exolab.jms.net.Callback;
import org.exolab.jms.net.CallbackService;
import org.exolab.jms.net.CallbackServiceImpl;
import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.net.connector.TestAuthenticator;
import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.proxy.Proxy;
import org.exolab.jms.net.proxy.RemoteInvocationException;
import org.exolab.jms.net.registry.Registry;


/**
 * Tests the ORB.exportObject() and ORB.exportObjectTo() methods.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/05/27 13:52:23 $
 */
public abstract class ExportTestCase extends ORBTestCase {

    /**
     * Construct a new <code>ExportTestCase</code>.
     *
     * @param name the name of the test to run
     * @param uri  the server export URI
     */
    public ExportTestCase(String name, String uri) {
        super(name, uri);
    }

    /**
     * Construct a new <code>ExportTestCase</code>.
     *
     * @param name the name of the test to run
     * @param uri  the server export URI
     * @param properties connection properties. May be <code>null</code>
     */
    public ExportTestCase(String name, String uri, Map properties) {
        super(name, uri, properties);
    }

    /**
     * Construct a new <code>ExportTestCase</code>.
     *
     * @param name     the name of test case
     * @param uri      the server export URI
     * @param routeURI the route URI
     */
    public ExportTestCase(String name, String uri, String routeURI) {
        super(name, uri, routeURI);
    }

    /**
     * Construct a new <code>ExportTestCase</code>.
     *
     * @param name            the name of test case
     * @param uri             the export URI
     * @param routeURI        the route URI
     * @param connectionProps connection properties. May be <code>null</code>
     * @param acceptorProps   acceptor properites. May be <code>null</code>
     */
    public ExportTestCase(String name, String uri, String routeURI,
                            Map connectionProps, Map acceptorProps) {
        super(name, uri, routeURI, connectionProps, acceptorProps);
    }

    /**
     * Verifies that methods can be invoked on an object exported via {@link
     * ORB#exportObject(Object)}.
     *
     * @throws Exception for any error
     */
    public void testExportObject() throws Exception {
        checkExportObject(null);
    }

    /**
     * Verifies that methods can be invoked by an authenticated user, on an
     * object exported via {@link ORB#exportObject(Object)}.
     *
     * @throws Exception for any error
     */
    public void testExportObjectWithAuth() throws Exception {
        BasicPrincipal principal = new BasicPrincipal("first", "secret");
        checkExportObject(principal);
    }

    /**
     * Verifies that methods can be invoked on an object exported via {@link
     * ORB#exportObject(Object, String)}.
     *
     * @throws Exception for any error
     */
    public void testExportObjectURI() throws Exception {
        checkExportObjectURI(null);
    }

    /**
     * Verifies that methods can be invoked by an authenticated user, on an
     * object exported via {@link ORB#exportObject(Object, String)}.
     *
     * @throws Exception for any error
     */
    public void testExportObjectURIWithAuth() throws Exception {
        BasicPrincipal principal = new BasicPrincipal("second", "secret");
        checkExportObjectURI(principal);
    }

    /**
     * Verifies that {@link ORB#exportObjectTo(Object)} throws
     * <code>ExportException</code> when there is no current caller.
     *
     * @throws Exception for any error
     */
    public void testExportObjectToNoCaller() throws Exception {
        ORB orb = getORB();
        EchoServiceImpl impl = new EchoServiceImpl();
        try {
            orb.exportObjectTo(impl);
            fail("Expected exportObjectTo() to fail with ExportException");
        } catch (ExportException expected) {
            // expected behaviour
        }
    }

    /**
     * Verifies that an object can be exported via {@link
     * ORB#exportObjectTo(Object)}.
     *
     * @throws Exception for any error
     */
    public void testExportObjectTo() throws Exception {
        checkExportObjectTo(null);
    }

    /**
     * Verifies that methods can be invoked by an authenticated user, on an
     * object exported via {@link ORB#exportObjectTo(Object)}.
     *
     * @throws Exception for any error
     */
    public void testExportObjectToWithAuth() throws Exception {
        BasicPrincipal principal = new BasicPrincipal("third", "secret");
        checkExportObjectTo(principal);
    }

    /**
     * Verifies that an object can be exported via {@link
     * ORB#exportObjectTo(Object, String)}.
     *
     * @throws Exception for any error
     */
    public void testExportObjectToURI() throws Exception {
        checkExportObjectToURI(null);
    }

    /**
     * Verifies that an object can be invoked by an authenticated user. on an
     * object exported via {@link ORB#exportObjectTo(Object, String)}.
     *
     * @throws Exception for any error
     */
    public void testExportObjectToURIWithAuth() throws Exception {
        BasicPrincipal principal = new BasicPrincipal("fourth", "secret");
        checkExportObjectToURI(principal);
    }

    /**
     * Verifies that methods can be invoked an object exported via {@link
     * ORB#exportObject(Object)}.
     *
     * @param principal the security principal. If <code>null</code>, indicates
     *                  to not use authentication.
     * @throws Exception for any error
     */
    private void checkExportObject(BasicPrincipal principal)
            throws Exception {
        Authenticator authenticator = new TestAuthenticator(principal);
        ORB orb = createORB(authenticator);
        EchoServiceImpl impl = new EchoServiceImpl();
        Proxy proxy = orb.exportObject(impl);
        orb.getRegistry().bind("service", proxy);

        Registry registry = getRegistry(principal);
        EchoService service = (EchoService) registry.lookup("service");

        assertTrue(service.echoBoolean(true));

        checkUnexportObject(orb, impl, service);
    }

    /**
     * Verifies that methods can be invoked an object exported via {@link
     * ORB#exportObject(Object, String)}.
     *
     * @param principal the security principal. If <code>null</code>, indicates
     *                  to not use authentication.
     * @throws Exception for any error
     */
    private void checkExportObjectURI(BasicPrincipal principal)
            throws Exception {
        Authenticator authenticator = new TestAuthenticator(principal);
        ORB orb = createORB(authenticator);
        EchoServiceImpl impl = new EchoServiceImpl();
        Proxy proxy = orb.exportObject(impl, getExportURI());
        orb.getRegistry().bind("service", proxy);

        Registry registry = getRegistry(principal);
        EchoService service = (EchoService) registry.lookup("service");

        assertTrue(service.echoBoolean(true));

        checkUnexportObject(orb, impl, service);
    }

    /**
     * Verifies that methods can be invoked an object exported via {@link
     * ORB#exportObjectTo(Object)}.
     *
     * @param principal the security principal. If <code>null</code>, indicates
     *                  to not use authentication.
     * @throws Exception for any error
     */
    private void checkExportObjectTo(BasicPrincipal principal)
            throws Exception {
        Authenticator authenticator = new TestAuthenticator(principal);
        ORB orb = createORB(authenticator);
        EchoServiceImpl echoImpl = new EchoServiceImpl();

        ExportServiceImpl exporterImpl = new ExportServiceImpl(echoImpl, orb);
        Proxy proxy = orb.exportObject(exporterImpl);
        orb.getRegistry().bind("service", proxy);

        Registry registry = getRegistry(principal);
        ExportService exporter = (ExportService) registry.lookup("service");
        EchoService echoer = (EchoService) exporter.exportObjectTo();

        assertTrue(echoer.echoBoolean(true));

        checkUnexportObject(orb, echoImpl, echoer);
    }

    /**
     * Verifies that methods can be invoked an object exported via {@link
     * ORB#exportObjectTo(Object, String)}.
     *
     * @param principal the security principal. If <code>null</code>, indicates
     *                  to not use authentication.
     * @throws Exception for any error
     */
    private void checkExportObjectToURI(BasicPrincipal principal)
            throws Exception {
        final int count = 10;
        String user = null;
        String password = null;

        if (principal != null) {
            user = principal.getName();
            password = principal.getPassword();
        }

        Authenticator authenticator = new TestAuthenticator(principal);
        ORB orb = createORB(authenticator);

        CallbackService serviceImpl = new CallbackServiceImpl();
        Proxy proxy = orb.exportObject(serviceImpl);
        orb.getRegistry().bind("service", proxy);

        ORB client = getClientORB();
        Registry registry = getRegistry(principal);
        CallbackService service = (CallbackService) registry.lookup("service");

        LoggingCallback callback = new LoggingCallback();
        Callback callbackProxy = (Callback) client.exportObjectTo(
                callback, getServerURI(), user, password);
        service.addCallback(callbackProxy);

        for (int i = 0; i < count; ++i) {
            service.invoke(new Integer(i));
        }

        Integer[] objects = (Integer[]) callback.getObjects().toArray(
                new Integer[0]);
        assertEquals(count, objects.length);
        for (int i = 0; i < count; ++i) {
            assertEquals(i, objects[i].intValue());
        }

        client.unexportObject(callback);
        try {
            service.invoke(new Integer(0));
        } catch (RemoteInvocationException expected) {
            // expected behaviour
            assertTrue(expected.getTargetException()
                       instanceof NoSuchObjectException);
        }

        try {
            orb.unexportObject(client);
            fail("Expected NoSuchObjectException to be thrown");
        } catch (NoSuchObjectException expected) {
            // expected behaviour
        }        
    }

    /**
     * Verifies that an exported service can be unexported, and that
     * subsequent invocations on its proxy fail with a
     * <code>NoSuchObjectException</code>
     *
     * @param orb the orb to unexport the service with
     * @param service the object to unexport
     * @param proxy
     * @throws Exception for any error
     */
    private void checkUnexportObject(ORB orb, EchoServiceImpl service,
                                     EchoService proxy) throws Exception {
        orb.unexportObject(service);

        try {
            proxy.echoBoolean(true);
            fail("Managed to invoke method on unexported object");
        } catch (RemoteInvocationException expected) {
            // expected behaviour
            assertTrue(expected.getTargetException()
                       instanceof NoSuchObjectException);
        }

        try {
            orb.unexportObject(service);
            fail("Expected NoSuchObjectException to be thrown");
        } catch (NoSuchObjectException expected) {
            // expected behaviour
        }
    }

}
