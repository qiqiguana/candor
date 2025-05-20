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
 * $Id: ORBTestCase.java,v 1.2 2005/06/04 14:53:25 tanderson Exp $
 */
package org.exolab.jms.net.invoke;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

import org.exolab.jms.common.security.BasicPrincipal;
import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.orb.ORBFactory;
import org.exolab.jms.net.registry.Registry;
import org.exolab.jms.net.util.SSLUtil;


/**
 * <code>TestCase</code> implementation with helpers for {@link ORB} related
 * tests.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/06/04 14:53:25 $
 */
public abstract class ORBTestCase extends TestCase {

    /**
     * The server ORB.
     */
    private ORB _orb;

    /**
     * The client ORB.
     */
    private ORB _client;

    /**
     * The default export URI.
     */
    private String _uri;

    /**
     * The route URI.
     */
    private String _routeURI;

    /**
     * Connection properties used when establishing a connection to the remote
     * ORB. May be <code>null</code>
     */
    private final Map _connectionProps;

    /**
     * Connection properties used when constructing the local ORB. May be
     * <code>null</code>
     */
    private final Map _acceptorProps;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(ORBTestCase.class);


    /**
     * Construct a new <code>ORBTestCase</code>.
     *
     * @param name the name of test case
     * @param uri  the server export URI
     */
    public ORBTestCase(String name, String uri) {
        this(name, uri, null, null);
    }

    /**
     * Construct a new <code>ORBTestCase</code>.
     *
     * @param name       the name of test case
     * @param uri        the server export URI
     * @param properties connection properties. May be <code>null</code>
     */
    public ORBTestCase(String name, String uri, Map properties) {
        this(name, uri, null, properties);
    }

    /**
     * Construct a new <code>ORBTestCase</code>.
     *
     * @param name     the name of test case
     * @param uri      the server export URI
     * @param routeURI the route URI
     */
    public ORBTestCase(String name, String uri, String routeURI) {
        this(name, uri, routeURI, null, null);
    }

    /**
     * Construct a new <code>ORBTestCase</code>.
     *
     * @param name       the name of test case
     * @param uri        the export URI
     * @param routeURI   the route URI
     * @param properties connection properties. May be <code>null</code>
     */
    public ORBTestCase(String name, String uri, String routeURI,
                       Map properties) {
        this(name, uri, routeURI, properties, properties);
    }

    /**
     * Construct a new <code>ORBTestCase</code>.
     *
     * @param name            the name of test case
     * @param uri             the export URI
     * @param routeURI        the route URI
     * @param connectionProps connection properties. May be <code>null</code>
     * @param acceptorProps   acceptor properites. May be <code>null</code>
     */
    public ORBTestCase(String name, String uri, String routeURI,
                       Map connectionProps, Map acceptorProps) {
        super(name);
        _uri = uri;
        _routeURI = routeURI;
        _connectionProps = connectionProps;
        _acceptorProps = acceptorProps;
    }

    /**
     * Returns connection properties for establishing a connection to the remote
     * ORB, for the given security principal.
     *
     * @param principal the security principal. If <code>null</code>, indicates
     *                  no authentication is required.
     * @throws Exception for any error
     */
    protected Map getConnectionProperties(BasicPrincipal principal)
            throws Exception {
        Map properties = getConnectionProperties();
        if (principal != null) {
            properties.put(ORB.SECURITY_PRINCIPAL, principal.getName());
            properties.put(ORB.SECURITY_CREDENTIALS, principal.getPassword());
        }
        return properties;
    }

    /**
     * Returns connection properties for establishing a connection to the remote
     * ORB.
     *
     * @return the connection properties, or <code>null</code> if the default
     *         connection properties should be used
     * @throws Exception for any error
     */
    protected Map getConnectionProperties() throws Exception {
        Map properties = new HashMap();
        properties.put(ORB.PROVIDER_URI, getServerURI());
        if (_connectionProps != null) {
            properties.putAll(_connectionProps);
        }
        return properties;
    }

    /**
     * Returns properties for configuring the client ORB.
     *
     * @return the configuration properties, or <code>null</code if the default
     *         properties should be used.
     */
    protected Map getClientProperties() {
        return null;
    }

    /**
     * Returns the acceptor properties to use when accepting connections.
     *
     * @return the acceptor properties, or <code>null</code> if the default
     *         connection properties should be used
     * @throws Exception for any error
     */
    protected Map getAcceptorProperties() throws Exception {
        Map properties = new HashMap();
        properties.put(ORB.PROVIDER_URI, _uri);
        if (_acceptorProps != null) {
            properties.putAll(_acceptorProps);
        } else if (_connectionProps != null) {
            properties.putAll(_connectionProps);
        }
        return properties;
    }

    /**
     * Returns the server ORB, creating it if it doesn't exist.
     *
     * @return the orb
     * @throws Exception for any error
     */
    protected synchronized ORB getORB() throws Exception {
        if (_orb == null) {
            createORB(null);
        }
        return _orb;
    }

    /**
     * Creates the server ORB.
     *
     * @param authenticator the authenticator. May be <code>null</code>
     * @throws Exception for any error
     */
    protected synchronized ORB createORB(Authenticator authenticator)
            throws Exception {
        if (authenticator != null) {
            _orb
                    = ORBFactory.createORB(authenticator,
                                           getAcceptorProperties());
        } else {
            _orb = ORBFactory.createORB(getAcceptorProperties());
        }
        if (_routeURI != null) {
            _orb.addRoute(_uri, _routeURI);
        }
        return _orb;
    }

    /**
     * Returns the default export URI.
     *
     * @return the default export URI
     */
    protected String getExportURI() {
        return _uri;
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
     * Returns the client ORB.
     *
     * @return the client ORB
     */
    protected ORB getClientORB() {
        return _client;
    }

    /**
     * Helper to return a reference to the remote registry service, for the
     * given security principal.
     *
     * @param principal the security principal.
     * @throws Exception for any error
     */
    protected Registry getRegistry(BasicPrincipal principal)
            throws Exception {
        return _client.getRegistry(getConnectionProperties(principal));
    }

    /**
     * Helper to return a reference to the remote registry service.
     *
     * @throws Exception for any error
     */
    protected Registry getRegistry() throws Exception {
        return _client.getRegistry(getConnectionProperties());
    }

    /**
     * Sets up the test case.
     *
     * @throws Exception for any error
     */
    protected void setUp() throws Exception {
        _log.debug("setUp() [test=" + getName() + ", URI=" + _uri + "]");

        // set up the client
        _client = ORBFactory.createORB(getClientProperties());
    }

    /**
     * Cleans up the test case.
     *
     * @throws Exception for any error
     */
    protected void tearDown() throws Exception {
        _log.debug("tearDown() [test=" + getName() + ", URI=" + _uri + "]");
        if (_orb != null) {
            _orb.shutdown();
        }
        _client.shutdown();

        // reset any SSL properties that may have been set.
        SSLUtil.clearProperties();
    }

}
