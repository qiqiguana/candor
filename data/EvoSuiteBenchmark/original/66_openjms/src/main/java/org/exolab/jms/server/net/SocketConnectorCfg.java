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
 * $Id: SocketConnectorCfg.java,v 1.3 2005/12/01 13:53:23 tanderson Exp $
 */
package org.exolab.jms.server.net;

import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.SocketConfigurationType;
import org.exolab.jms.config.types.SchemeType;
import org.exolab.jms.net.socket.SocketRequestInfo;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.util.Properties;
import org.exolab.jms.net.connector.ResourceException;


/**
 * Configuration for socket-based connectors.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/12/01 13:53:23 $
 */
abstract class SocketConnectorCfg extends AbstractConnectorCfg {

    /**
     * The socket configuration.
     */
    private SocketConfigurationType _config;


    /**
     * Construct a new <code>SocketConnectorCfg</code>.
     *
     * @param scheme       the connector scheme
     * @param config       the configuration to use
     * @param socketConfig the socket configuration
     */
    public SocketConnectorCfg(SchemeType scheme, Configuration config,
                              SocketConfigurationType socketConfig) {
        super(scheme, config);
        if (socketConfig == null) {
            throw new IllegalArgumentException(
                    "Argument 'socketConfig' is null");
        }
        _config = socketConfig;
    }

    /**
     * Populates the supplied properties with connection accept properties.
     *
     * @param properties the properties to populate
     */
    protected void populateAcceptProperties(Properties properties) {
        URI uri = getURI(getExportURI());
        SocketRequestInfo info;
        try {
            info = new SocketRequestInfo(uri);
        } catch (ResourceException exception) {
            // should never happen
            throw new IllegalStateException(exception.getMessage());
        }
        populateRequestInfo(info);
        info.export(properties);
    }

    /**
     * Populates the connection request info with data from the configuration.
     *
     * @param info the connection request info to populate
     */
    protected void populateRequestInfo(SocketRequestInfo info) {
        info.setBindAll(_config.getBindAll());
    }

    /**
     * Returns the socket configuration.
     *
     * @return the socket configuration
     */
    protected SocketConfigurationType getSocketConfiguration() {
        return _config;
    }

}
