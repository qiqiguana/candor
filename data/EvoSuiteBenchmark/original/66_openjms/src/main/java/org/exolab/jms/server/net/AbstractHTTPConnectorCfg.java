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
 * $Id: AbstractHTTPConnectorCfg.java,v 1.5 2005/12/01 13:53:23 tanderson Exp $
 */
package org.exolab.jms.server.net;

import org.exolab.jms.config.ConfigHelper;
import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.HttpConfigurationType;
import org.exolab.jms.config.ServerConfiguration;
import org.exolab.jms.config.types.SchemeType;
import org.exolab.jms.net.socket.SocketRequestInfo;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.util.Properties;
import org.exolab.jms.net.connector.ResourceException;


/**
 * Configuration for the HTTP/HTTPS connectors.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.5 $ $Date: 2005/12/01 13:53:23 $
 */
abstract class AbstractHTTPConnectorCfg extends AbstractConnectorCfg {

    /**
     * The HTTP/HTTPS configuration.
     */
    private HttpConfigurationType _config;


    /**
     * Construct a new <code>AbstractHTTPConnectorCfg</code>.
     *
     * @param scheme     the connector scheme
     * @param config     the configuration to use
     * @param httpConfig the HTTP configuration
     */
    public AbstractHTTPConnectorCfg(SchemeType scheme, Configuration config,
                                    HttpConfigurationType httpConfig) {
        super(scheme, config);
        if (httpConfig == null) {
            throw new IllegalArgumentException("Argument 'httpConfig' is null");
        }
        _config = httpConfig;
    }

    /**
     * Returns the URI used to establsh connections to remote services.
     *
     * @return the URI used to establish connections to remote services
     */
    public String getConnectURI() {
        return ConfigHelper.getServerURL(getScheme(), getConfiguration());
    }

    /**
     * Returns the URI that services are exported on.
     *
     * @return the URI for exporting services
     */
    public String getExportURI() {
        return getExportURI(_config.getPort());
    }

    /**
     * Returns the URI that JNDI service is exported on.
     *
     * @return the JNDI service URI
     */
    public String getJNDIExportURI() {
        String uri;
        if (_config.getJndiPort() != 0) {
            uri = getExportURI(_config.getJndiPort());
        } else {
            uri = getExportURI();
        }
        return uri;
    }

    /**
     * Returns the URI the administration service is exported on.
     *
     * @return the administration service URI
     */
    public String getAdminExportURI() {
        String uri;
        if (_config.getAdminPort() != 0) {
            uri = getExportURI(_config.getAdminPort());
        } else {
            uri = getExportURI();
        }
        return uri;
    }

    /**
     * Populates the supplied properties with connection accept properties.
     *
     * @param properties the properties to populate
     */
    protected void populateAcceptProperties(Properties properties) {
        URI uri = getURI(getExportURI());
        try {
            SocketRequestInfo info = new SocketRequestInfo(uri);
            info.setBindAll(_config.getBindAll());
            info.export(properties);
        } catch (ResourceException exception) {
            // should never happen.
            throw new IllegalStateException(exception.getMessage());
        }
    }

    /**
     * Generates an export URI for a particular port.
     *
     * @param port the port
     * @return an export URI
     */
    private String getExportURI(int port) {
        SchemeType scheme = getScheme();
        String acceptScheme = scheme.toString() + "-server";
        ServerConfiguration server
                = getConfiguration().getServerConfiguration();
        return getURI(acceptScheme, server.getHost(), port).toString();
    }

}
