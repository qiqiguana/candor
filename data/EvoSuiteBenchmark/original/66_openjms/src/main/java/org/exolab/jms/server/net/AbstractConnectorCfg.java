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
 * $Id: AbstractConnectorCfg.java,v 1.2 2005/07/22 23:40:38 tanderson Exp $
 */
package org.exolab.jms.server.net;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.exolab.jms.config.ConfigHelper;
import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.ConnectionFactories;
import org.exolab.jms.config.Connector;
import org.exolab.jms.config.ServerConfiguration;
import org.exolab.jms.config.types.SchemeType;
import org.exolab.jms.net.connector.AbstractConnectionFactory;
import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.uri.InvalidURIException;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.uri.URIHelper;
import org.exolab.jms.net.util.Properties;


/**
 * Abstract implementation of the {@link ConnectorCfg} interface.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/07/22 23:40:38 $
 */
abstract class AbstractConnectorCfg implements ConnectorCfg {

    /**
     * The connector scheme.
     */
    private final SchemeType _scheme;

    /**
     * The underlying configuration.
     */
    private final Configuration _config;


    /**
     * Construct a new <code>AbstractConnectorCfg</code>.
     *
     * @param scheme the connector scheme
     * @param config the configuration to use
     */
    public AbstractConnectorCfg(SchemeType scheme, Configuration config) {
        if (scheme == null) {
            throw new IllegalArgumentException("Argument 'scheme' is null");
        }
        if (config == null) {
            throw new IllegalArgumentException("Argument 'config' is null");
        }
        _scheme = scheme;
        _config = config;
    }

    /**
     * Returns the connector scheme.
     *
     * @return the connector scheme
     */
    public SchemeType getScheme() {
        return _scheme;
    }

    /**
     * Returns the URI used to establsh connections to remote services.
     * <p/>
     * This implementation returns {@link #getExportURI}
     *
     * @return the URI used to establish connections to remote services
     */
    public String getConnectURI() {
        return getExportURI();
    }

    /**
     * Returns the URI that services are exported on.
     *
     * @return the URI for exporting services
     */
    public String getExportURI() {
        return ConfigHelper.getServerURL(_scheme, _config);
    }

    /**
     * Returns the URI that JNDI service is exported on.
     *
     * @return the JNDI service URI
     */
    public String getJNDIExportURI() {
        return ConfigHelper.getJndiURL(_scheme, _config);
    }

    /**
     * Returns the URI the administration service is exported on.
     * <p/>
     * Typically, this will be the same as that returned by {@link
     * #getExportURI}.
     *
     * @return the administration service URI
     */
    public String getAdminExportURI() {
        return ConfigHelper.getAdminURL(_scheme, _config);
    }

    /**
     * Returns properties to configure the ORB to enable it to establish a
     * connection to the remote ORB.
     *
     * @return a map of String properties
     */
    public Map getConnectProperties() {
        Properties properties = getProperties();
        populateConnectProperties(properties);
        return properties.getProperties();
    }

    /**
     * Returns properties to configure the ORB to enable it to accept
     * connections from remote ORBs.
     *
     * @return a map of String properties
     */
    public Map getAcceptProperties() {
        Properties properties = getProperties();
        populateAcceptProperties(properties);
        return properties.getProperties();
    }

    /**
     * Returns connection factories associated with this configuration.
     *
     * @return associated connection factories.
     */
    public ConnectionFactories getConnectionFactories() {
        ConnectionFactories result = null;
        Connector[] connectors = _config.getConnectors().getConnector();
        for (int i = 0; i < connectors.length; ++i) {
            if (connectors[i].getScheme().equals(_scheme)) {
                result = connectors[i].getConnectionFactories();
                break;
            }
        }
        return result;
    }

    /**
     * Returns the underlying configuration.
     *
     * @return the underlying configuration
     */
    protected Configuration getConfiguration() {
        return _config;
    }

    /**
     * Populates the supplied properties with connect properties.
     *
     * @param properties the properties to populate
     */
    protected void populateConnectProperties(Properties properties) {
        properties.set(ORB.PROVIDER_URI, getConnectURI());
    }

    /**
     * Populates the supplied properties with connection accept properties.
     *
     * @param properties the properties to populate
     */
    protected void populateAcceptProperties(Properties properties) {
        properties.set(ORB.PROVIDER_URI, getExportURI());

        ServerConfiguration server = _config.getServerConfiguration();
        properties.set("org.exolab.jms.net.orb.threads.max",
                       server.getMaxThreads());
    }

    /**
     * Helper to create a new {@link Properties} instance.
     *
     * @return a new <code>Properties</code> instance
     */
    protected Properties getProperties() {
        String prefix = AbstractConnectionFactory.PROPERTY_PREFIX;
        if (_scheme.equals(SchemeType.EMBEDDED)) {
            prefix += "vm";
        } else {
            prefix += _scheme.toString();
        }
        prefix += ".";
        return new Properties(prefix);
    }

    /**
     * Constructs a URI with no path.
     *
     * @param scheme the connector scheme
     * @param host   the host
     * @param port   the port
     * @return a new <code>URI</code>
     */
    protected URI getURI(String scheme, String host, int port) {
        URI result;
        try {
            result = URIHelper.create(scheme, getHost(host), port);
        } catch (InvalidURIException exception) {
            throw new IllegalStateException("Failed to create URI: "
                                            + exception);
        }
        return result;
    }

    /**
     * Constructs a URI with a path.
     *
     * @param scheme the connector scheme
     * @param host   the host
     * @param port   the port
     * @param path   the path
     * @return a new <ocde>URI</ocde>
     */
    protected URI getURI(String scheme, String host, int port,
                         String path) {
        URI result;
        try {
            result = URIHelper.create(scheme, getHost(host), port, path);
        } catch (InvalidURIException exception) {
            throw new IllegalStateException("Failed to create URI: "
                                            + exception);
        }
        return result;
    }

    /**
     * Helper to change the supplied host to its address, iff it is
     * <em>localhost</em>.
     *
     * @param host the host
     * @return the host address if <code>host</code> is <em>localhost</em>
     *         otherwise returns <code>host</code> unchanged
     */
    protected String getHost(String host) {
        if (host.equals("localhost")) {
            try {
                host = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException ignore) {
            }
        }
        return host;
    }

    /**
     * Helper to parse a URI.
     *
     * @param uri the URI to parse.
     * @return the parsed URI
     * @throws IllegalStateException if the URI is invalid
     */
    protected URI getURI(String uri) {
        URI result;
        try {
            result = URIHelper.parse(uri);
        } catch (InvalidURIException exception) {
            throw new IllegalStateException("Failed to parse URI: " + uri);
        }
        return result;
    }

}
