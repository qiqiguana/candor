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
 * $Id: CallbackTestCase.java,v 1.6 2005/05/24 05:48:30 tanderson Exp $
 */
package org.exolab.jms.net.invoke;

import java.util.Map;

import org.exolab.jms.net.Callback;
import org.exolab.jms.net.CallbackService;
import org.exolab.jms.net.CallbackServiceImpl;
import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.proxy.Proxy;
import org.exolab.jms.net.registry.Registry;


/**
 * Tests callbacks.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.6 $ $Date: 2005/05/24 05:48:30 $
 */
public abstract class CallbackTestCase extends ORBTestCase {

    /**
     * The callback service.
     */
    private CallbackServiceImpl _service = new CallbackServiceImpl();

    /**
     * Callback service name.
     */
    private static final String CALLBACK_SERVICE = "callback";


    /**
     * Construct a new <code>CallbackTestCase</code>.
     *
     * @param name the name of test case
     * @param uri  the server export URI
     */
    public CallbackTestCase(String name, String uri) {
        super(name, uri);
    }

    /**
     * Construct a new <code>CallbackTestCase</code>.
     *
     * @param name       the name of test case
     * @param uri        the server export URI
     * @param properties connection properties. May be <code>null</code>
     */
    public CallbackTestCase(String name, String uri, Map properties) {
        super(name, uri, properties);
    }

    /**
     * Construct a new <code>CallbackTestCase</code>.
     *
     * @param name     the name of test case
     * @param uri      the server export URI
     * @param routeURI the route URI
     */
    public CallbackTestCase(String name, String uri, String routeURI) {
        super(name, uri, routeURI);
    }

    /**
     * Construct a new <code>CallbackTestCase</code>.
     *
     * @param name       the name of test case
     * @param uri        the export URI
     * @param routeURI   the route URI
     * @param properties connection properties. May be <code>null</code>
     */
    public CallbackTestCase(String name, String uri, String routeURI,
                            Map properties) {
        super(name, uri, routeURI, properties);
    }

    /**
     * Construct a new <code>CallbackTestCase</code>.
     *
     * @param name            the name of test case
     * @param uri             the export URI
     * @param routeURI        the route URI
     * @param connectionProps connection properties. May be <code>null</code>
     * @param acceptorProps   acceptor properites. May be <code>null</code>
     */
    public CallbackTestCase(String name, String uri, String routeURI,
                            Map connectionProps, Map acceptorProps) {
        super(name, uri, routeURI, connectionProps, acceptorProps);
    }

    /**
     * Tests a single callback.
     *
     * @throws Exception for any error
     */
    public void testCallback() throws Exception {
        final int count = 10;
        ORB client = getClientORB();
        Registry registry = client.getRegistry(getConnectionProperties());
        CallbackService service =
                (CallbackService) registry.lookup(CALLBACK_SERVICE);

        LoggingCallback callback = new LoggingCallback();
        Callback proxy = (Callback) client.exportObjectTo(callback,
                                                          getServerURI());
        service.addCallback(proxy);

        for (int i = 0; i < count; ++i) {
            _service.invoke(new Integer(i));
        }

        Integer[] objects = (Integer[]) callback.getObjects().toArray(
                new Integer[0]);
        assertEquals(count, objects.length);
        for (int i = 0; i < count; ++i) {
            assertEquals(i, objects[i].intValue());
        }
    }

    /**
     * Tests callback recursion.
     *
     * @throws Exception for any error
     */
    public void testRecursion() throws Exception {
        final int count = 10;
        final int depth = 4; // recursion depth

        ORB client = getClientORB();
        Registry registry = client.getRegistry(getConnectionProperties());
        CallbackService service =
                (CallbackService) registry.lookup(CALLBACK_SERVICE);

        RecursiveCallback callback = new RecursiveCallback(service, depth);
        Callback proxy = (Callback) client.exportObjectTo(callback,
                                                          getServerURI());
        service.addCallback(proxy);

        for (int i = 0; i < count; ++i) {
            _service.invoke(new Integer(i));
        }

        Integer[] objects = (Integer[]) callback.getObjects().toArray(
                new Integer[0]);
        assertEquals(count * depth, objects.length);
        for (int i = 0, index = 0; i < count; ++i) {
            for (int j = 0; j < depth; ++j, ++index) {
                assertEquals(i, objects[index].intValue());
            }
        }
    }

    /**
     * Sets up the test case.
     *
     * @throws Exception for any error
     */
    protected void setUp() throws Exception {
        super.setUp();

        ORB server = getORB();
        Proxy proxy = server.exportObject(_service);
        server.getRegistry().bind(CALLBACK_SERVICE, proxy);
    }

}
