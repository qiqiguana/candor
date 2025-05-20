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
 * $Id: DisconnectionTestCase.java,v 1.4 2006/12/16 12:37:17 tanderson Exp $
 */
package org.exolab.jms.net.invoke;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.HashMap;

import EDU.oswego.cs.dl.util.concurrent.Latch;

import org.exolab.jms.net.Callback;
import org.exolab.jms.net.CallbackService;
import org.exolab.jms.net.CallbackServiceImpl;
import org.exolab.jms.net.connector.Caller;
import org.exolab.jms.net.connector.CallerListener;
import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.proxy.Proxy;
import org.exolab.jms.net.registry.Registry;


/**
 * Tests disconnection.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2006/12/16 12:37:17 $
 */
public class DisconnectionTestCase extends ORBTestCase {

    /**
     * Construct a new <code>DisconnectionTestCase</code>.
     *
     * @param name the name of test case
     * @param uri  the server export URI
     */
    public DisconnectionTestCase(String name, String uri) {
        super(name, uri);
    }

    /**
     * Construct a new <code>DisconnectionTestCase</code>.
     *
     * @param name       the name of test case
     * @param uri        the server export URI
     * @param properties connection properties. May be <code>null</code>
     */
    public DisconnectionTestCase(String name, String uri, Map properties) {
        super(name, uri, properties);
    }

    /**
     * Construct a new <code>DisconnectionTestCase</code>.
     *
     * @param name     the name of test case
     * @param uri      the server export URI
     * @param routeURI the route URI
     */
    public DisconnectionTestCase(String name, String uri, String routeURI) {
        super(name, uri, routeURI);
    }

    /**
     * Construct a new <code>DisconnectionTestCase</code>.
     *
     * @param name            the name of test case
     * @param uri             the export URI
     * @param routeURI        the route URI
     * @param connectionProps connection properties. May be <code>null</code>
     * @param acceptorProps   acceptor properites. May be <code>null</code>
     */
    public DisconnectionTestCase(String name, String uri, String routeURI,
                                 Map connectionProps, Map acceptorProps) {
        super(name, uri, routeURI, connectionProps, acceptorProps);
    }

    /**
     * Verifies that the client is notified when the server is shut down.
     *
     * @throws Exception for any error
     */
    public void testServerDisconnect() throws Exception {
        final Latch latch = new Latch();
        CallerListener listener = new CallerListener() {
            public void disconnected(Caller caller) {
                latch.release();
            }
        };
        ORB client = getClientORB();
        client.addCallerListener(getServerURI(), listener);

        ORB server = getORB();
        server.getRegistry();

        // get the registry proxy. This will establish a connection to the
        // server.
        Registry registry = getRegistry();
        assertNotNull(registry);

        server.shutdown();

        if (!latch.attempt(10 * 1000)) {
            fail("CallerListener not notified of disconnection");
        }
    }

    /**
     * Verifies that the server is notified when the client is shut down.
     *
     * @throws Exception for any error
     */
    public void testClientDisconnect() throws Exception {
        Latch latch = new Latch();
        ORB server = getORB();
        CallbackServer serviceImpl = new CallbackServer(server, latch);

        Proxy proxy = server.exportObject(serviceImpl);
        server.getRegistry().bind("service", proxy);

        ORB client = getClientORB();
        Registry registry = client.getRegistry(getConnectionProperties());
        CallbackService service = (CallbackService) registry.lookup("service");

        LoggingCallback callback = new LoggingCallback();
        Callback callbackProxy = (Callback) client.exportObjectTo(callback,
                                                                  getServerURI());
        service.addCallback(callbackProxy);

        assertNull(serviceImpl.getException());
        client.shutdown();

        if (!latch.attempt(10 * 1000)) {
            fail("CallerListener not notified of disconnection");
        }
        assertNull(serviceImpl.getException());
    }

    /**
     * Verifies that the client is notified when the connection is closed
     * through inactivity.
     *
     * @throws Exception for any error
     */
    public void testInactive() throws Exception {
        final Latch latch = new Latch();
        CallerListener listener = new CallerListener() {
            public void disconnected(Caller caller) {
                latch.release();
            }
        };

        ORB server = getORB();
        CallbackServer serviceImpl = new CallbackServer(server, latch);

        Proxy proxy = server.exportObject(serviceImpl);
        server.getRegistry().bind("service", proxy);

        ORB client = getClientORB();
        client.addCallerListener(getServerURI(), listener);

        Registry registry = getRegistry(); // will establish a connection
        assertNotNull(registry);
        CallbackService service = (CallbackService) registry.lookup("service");
        assertNotNull(service);

        // make sure the connection isn't reaped through inactivity
        // while there are proxies associated with it.
        for (int i = 0; i < 10; ++i) {
            Runtime.getRuntime().gc();
            if (latch.attempt(1000)) {
                break;
            }
        }
        if (latch.attempt(0)) {
            fail("Connection terminated when there were active proxies");
        }

        // clear registry proxy and ensure the connection isn't reaped.
        // Registry proxy is constructed differently to those serialized
        // over the wire.

        registry = null;
        for (int i = 0; i < 10; ++i) {
            Runtime.getRuntime().gc();
            if (latch.attempt(1000)) {
                break;
            }
        }
        if (latch.attempt(0)) {
            fail("Connection terminated when there were active proxies");
        }

        // clear proxy reference so the connection can be GC'ed
        service = null;

        // wait for the notification. Need to force the GC to run...
        for (int i = 0; i < 10; ++i) {
            Runtime.getRuntime().gc();
            if (latch.attempt(1000)) {
                break;
            }
        }
        if (!latch.attempt(0)) {
            fail("CallerListener not notified of disconnection");
        }
    }

   /**
     * Returns properties for configuring the client ORB.
     * This configures the default connection pool to reap connections every
     * 5 seconds.
     *
     * @return the properties for configuring the client ORB.
     */
    protected Map getClientProperties() {
       Map properties = super.getClientProperties();
       return addReapIntervalProperty(properties);
    }

    /**
     * Returns the acceptor properties to use when accepting connections.
     * This configures the default connection pool to reap connections every
     * 5 seconds.
     *
     * @return the acceptor properties, or <code>null</code> if the default
     *         connection properties should be used
     * @throws Exception for any error
     */
    protected Map getAcceptorProperties() throws Exception {
        Map properties = super.getAcceptorProperties();
        return addReapIntervalProperty(properties);
    }

    /**
     * Adds a 5 second reap interval property to ORB configuration properties.
     *
     * @param properties the properties to add to. May be <code>null</code>
     * @return the updated configuration properties
     */
    private Map addReapIntervalProperty(Map properties) {
        if (properties == null) {
            properties = new HashMap();
        }
        properties.put("org.exolab.jms.net.pool.reapInterval", "5");
        return properties;
    }

    /**
     * {@link CallbackService} implementation that detects disconnection of its
     * client.
     */
    private static class CallbackServer extends CallbackServiceImpl
            implements CallerListener {

        /**
         * The ORB.
         */
        private final ORB _orb;

        /**
         * The latch to notify when disconnect() is invoked.
         */
        private final Latch _latch;

        /**
         * Any exception raised during the test. Should be null.
         */
        private Exception _exception;


        /**
         * Construct a new <code>CallbackServer</code>.
         *
         * @param orb   the ORB to use
         * @param latch the latch to notify when disconnect() is invoked.
         */
        public CallbackServer(ORB orb, Latch latch) {
            _orb = orb;
            _latch = latch;
        }

        /**
         * Register a callback.
         *
         * @param callback the callback to register
         */
        public synchronized void addCallback(Callback callback) {
            super.addCallback(callback);
            try {
                Caller caller = _orb.getCaller();
                _orb.addCallerListener(caller.getRemoteURI().toString(), this);
            } catch (RemoteException exception) {
                _exception = exception;
            }
        }

        /**
         * Notifies that a caller has been disconnected.
         *
         * @param caller the caller that was disconnected
         */
        public void disconnected(Caller caller) {
            try {
                _latch.release();
                _orb.removeCallerListener(caller.getRemoteURI().toString(),
                                          this);
            } catch (RemoteException exception) {
                _exception = exception;
            }
        }

        /**
         * Returns any exception raised.
         *
         * @return any exception raised, or <code>null</code>
         */
        public Exception getException() {
            return _exception;
        }
    }
}
