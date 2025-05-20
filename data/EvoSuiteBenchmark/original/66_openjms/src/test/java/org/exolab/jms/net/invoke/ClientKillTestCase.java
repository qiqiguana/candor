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
 * Copyright 2007 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: ClientKillTestCase.java,v 1.1 2008/01/07 13:00:17 tanderson Exp $
 */

package org.exolab.jms.net.invoke;

import EDU.oswego.cs.dl.util.concurrent.Latch;
import org.exolab.jms.net.CallbackServiceImpl;
import org.exolab.jms.net.connector.Caller;
import org.exolab.jms.net.connector.CallerListener;
import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.proxy.Proxy;

import java.util.HashMap;
import java.util.Map;


/**
 * Tests behaviour when the client is killed.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $LastChangedDate: 2006-05-02 05:16:31Z $
 */
public abstract class ClientKillTestCase extends RemoteClientTestCase {

    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name            the name of test case
     * @param uri             the export URI
     * @param embeddedService determines if the service will be run in the
     *                        current JVM
     */
    public ClientKillTestCase(String name, String uri,
                              boolean embeddedService) {
        this(name, uri, embeddedService, null);
    }

    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name            the name of test case
     * @param uri             the export URI
     * @param embeddedService determines if the service will be run in the
     *                        current JVM
     * @param properties      connection properties. May be <code>null</code>
     */
    public ClientKillTestCase(String name, String uri, boolean embeddedService,
                              Map properties) {
        this(name, uri, null, embeddedService, properties);
    }

    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name            the name of test case
     * @param uri             the export URI
     * @param routeURI        the route URI
     * @param embeddedService determines if the service will be run in the
     *                        current JVM
     */
    public ClientKillTestCase(String name, String uri, String routeURI,
                              boolean embeddedService) {
        this(name, uri, routeURI, embeddedService, null);
    }

    /**
     * Construct an instance of this class for a specific test case.
     *
     * @param name            the name of test case
     * @param uri             the export URI
     * @param routeURI        the route URI
     * @param embeddedService determines if the service will be run in the
     *                        current JVM
     * @param properties connection properties. May be <code>null</code>
     */
    public ClientKillTestCase(String name, String uri, String routeURI,
                              boolean embeddedService, Map properties) {
        super(name, uri, routeURI, embeddedService, properties);
    }

    /**
     * Verifies that the server is notified when the client is killed.
     *
     * @throws Exception for any error
     */
    public void testClientKill() throws Exception {
        final Latch latch = new Latch();
        CallerListener listener = new CallerListener() {
            public void disconnected(Caller caller) {
                latch.release();
            }
        };
        ORB orb = getORB();
        Proxy service = orb.exportObject(new CallbackServiceImpl());
        orb.getRegistry().bind("callback", service);
        orb.addCallerListener(listener);
        startClient(ClientKillTester.class, "callback");

        if (!latch.attempt(10 * 1000)) {
            fail("CallerListener not notified of disconnection");
        }
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

}
