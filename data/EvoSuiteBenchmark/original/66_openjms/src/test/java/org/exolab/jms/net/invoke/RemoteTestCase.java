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
 * $Id: RemoteTestCase.java,v 1.1 2008/01/07 13:00:18 tanderson Exp $
 */

package org.exolab.jms.net.invoke;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.jms.net.jvm.JVM;
import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.orb.ORBFactory;
import org.exolab.jms.net.util.SSLUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Base class for test cases where the client or server may run in another JVM.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $LastChangedDate: 2006-05-02 05:16:31Z $
 */
public abstract class RemoteTestCase extends TestCase {

    /**
     * The export URI.
     */
    private final String _uri;

    /**
     * The route URI.
     */
    private final String _routeURI;

    /**
     * Determines if the echo service will be run in the same JVM.
     */
    private final boolean _embeddedService;

    /**
     * Connection properties used when establishing a connection to the remote
     * ORB. May be <tt>null</tt>
     */
    private final Map _connectionProps;

    /**
     * Connection properties used when constructing the local ORB. May
     * be <tt>null</tt>
     */
    private final Map _acceptorProps;

    /**
     * The ORB.
     */
    private ORB _orb;

    /**
     * The JVM for running the echo service when <tt>_embeddedService ==
     * false</tt>.
     */
    private JVM _jvm;

    /**
     * The logger.
     */
    private static final Log _log
            = LogFactory.getLog(RemoteTestCase.class);


    /**
     * Creates a new <tt>RemoteTestCase</tt>.
     *
     * @param name            the name of test case
     * @param uri             the export URI
     * @param embeddedService determines if the service will be run in the
     *                        current JVM
     */
    public RemoteTestCase(String name, String uri,
                          boolean embeddedService) {
        this(name, uri, embeddedService, null);
    }

    /**
     * Creates a new <tt>RemoteTestCase</tt>.
     *
     * @param name            the name of test case
     * @param uri             the export URI
     * @param embeddedService determines if the service will be run in the
     *                        current JVM
     * @param properties      connection properties. May be <tt>null</tt>
     */
    public RemoteTestCase(String name, String uri,
                          boolean embeddedService, Map properties) {
        this(name, uri, null, embeddedService, properties);
    }

    /**
     * Creates a new <tt>RemoteTestCase</tt>.
     *
     * @param name            the name of test case
     * @param uri             the export URI
     * @param routeURI        the route URI
     * @param embeddedService determines if the service will be run in the
     *                        current JVM
     */
    public RemoteTestCase(String name, String uri, String routeURI,
                          boolean embeddedService) {
        this(name, uri, routeURI, embeddedService, null);
    }

    /**
     * Creates a new <tt>RemoteTestCase</tt>.
     *
     * @param name            the name of test case
     * @param uri             the export URI
     * @param routeURI        the route URI
     * @param embeddedService determines if the service will be run in the
     *                        current JVM
     * @param properties      connection properties. May be <tt>null</tt>
     */
    public RemoteTestCase(String name, String uri, String routeURI,
                          boolean embeddedService, Map properties) {
        this(name, uri, routeURI, embeddedService, properties, properties);
    }

    /**
     * Creates a new <tt>RemoteTestCase</tt>.
     *
     * @param name            the name of test case
     * @param uri             the export URI
     * @param routeURI        the route URI
     * @param embeddedService determines if the service will be run in the
     *                        current JVM
     * @param connectionProps connection properties. May be <tt>null</tt>
     * @param acceptorProps   acceptor properites. May be <tt>null</tt>
     */
    public RemoteTestCase(String name, String uri, String routeURI,
                          boolean embeddedService, Map connectionProps,
                          Map acceptorProps) {
        super(name);
        _uri = uri;
        _routeURI = routeURI;
        _embeddedService = embeddedService;
        _connectionProps = connectionProps;
        _acceptorProps = acceptorProps;
    }

    /**
     * Returns connection properties for establishing a connection to the remote
     * ORB.
     *
     * @return the connection properties, or <tt>null</tt> if the default
     *         connection properties should be used
     * @throws IOException if a store cannot be found
     */
    protected Map getConnectionProperties() throws IOException {
        Map properties = new HashMap();
        properties.put(ORB.PROVIDER_URI, getServerURI());
        if (_connectionProps != null) {
            properties.putAll(_connectionProps);
        }
        return properties;
    }

    /**
     * Returns the ORB.
     *
     * @return the orb
     */
    protected ORB getORB() {
        return _orb;
    }

    /**
     * Returns the acceptor properties to use when accepting connections.
     *
     * @return the acceptor properties, or <tt>null</tt> if the default
     *         connection properties should be used
     * @throws Exception for any error
     */
    protected Map getAcceptorProperties() throws Exception {
        Map properties = new HashMap();
        properties.put(ORB.PROVIDER_URI, _uri);
        if (_acceptorProps != null) {
            properties.putAll(_acceptorProps);
        }
        return properties;
    }

    /**
     * Determines if this JVM accepts connections.
     *
     * @return <tt>true</tt> if this JVM accepts connections
     */
    protected boolean isAcceptor() {
        return _embeddedService;
    }

    /**
     * Helper to return the server URI.
     *
     * @return the server URI
     */
    protected String getServerURI() {
        return (_routeURI != null) ? _routeURI : _uri;
    }

    /**
     * Shuts down the JVM and waits for it to terminate.
     */
    protected void shutdownJVM() {
        if (_jvm != null) {
            _jvm.stop();
            _jvm.waitFor();
        }
    }

    /**
     * Sets up the test case.
     *
     * @throws Exception for any error
     */
    protected void setUp() throws Exception {
        _log.debug("setUp() [test=" + getName() + ", uri=" + _uri + "]");
        if (isAcceptor()) {
            _orb = ORBFactory.createORB(getAcceptorProperties());
            if (_routeURI != null) {
                _orb.addRoute(_uri, _routeURI);
            }
        } else {
            _orb = ORBFactory.createORB(getConnectionProperties());
        }
    }

    /**
     * Cleans up the test case
     *
     * @throws Exception for any error
     */
    protected void tearDown() throws Exception {
        RemoteTestCase._log.debug(
                "tearDown() [test=" + getName() + ", uri=" + _uri + "]");
        _orb.shutdown();
        if (_jvm != null) {
            shutdownJVM();
        }

        // reset any SSL properties that may have been set.
        SSLUtil.clearProperties();
    }

    /**
     * Determines if the service will be run in the same JVM as the ORB server.
     *
     * @return <tt>true</tt> if the service will run in the same JVM
     */
    protected boolean isEmbeddedService() {
        return _embeddedService;
    }

    /**
     * Returns the URI.
     *
     * @return the URI
     */
    protected String getURI() {
        return _uri;
    }

    /**
     * Returns the route URI.
     *
     * @return the route URI
     */
    protected String getRouteURI() {
        return _routeURI;
    }


    protected void startJVM(Class main, String args) throws Exception {
        Properties props = new Properties();
        final String key = "log4j.configuration";
        String log4j = System.getProperty(key);
        if (log4j != null) {
            props.setProperty("log4j.configuration", key);
        }
        _jvm = new JVM(main.getName(), null, props, args);
        _jvm.start();
        // give the JVM time to start
        Thread.sleep(2000);
    }
}
