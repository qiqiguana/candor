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
 * Copyright 2001-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: ConnectorHelper.java,v 1.2 2005/08/30 05:05:55 tanderson Exp $
 */
package org.exolab.jms.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.config.types.SchemeType;


/**
 * ConnectorHelper is a utility class that returns {@link ConnectorResource}
 * objects for a given scheme.
 *
 * @version     $Revision: 1.2 $ $Date: 2005/08/30 05:05:55 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
public class ConnectorHelper {

    /**
     * The default connector resource configurations.
     */
    private static final ConnectorResource[] _connectors;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(ConnectorHelper.class);

    /**
     * The connector configuration path as a resource.
     */
    private static final String RESOURCE =
        "/org/exolab/jms/config/connectors.xml";


    /**
     * Returns the connector resource for the supplied scheme, from
     * the supplied configuration. If the configuration doesn't define
     * any connector resources, the default connector for the scheme will be
     * returned.
     *
     * @param scheme the connector scheme
     * @param config the configuration
     * @return the connector configuration, or null if none exists
     */
    public static ConnectorResource getConnectorResource(
        SchemeType scheme, Configuration config) {

        if (scheme == null) {
            throw new IllegalArgumentException("Argument 'scheme' is null");
        }
        if (config == null) {
            throw new IllegalArgumentException("Argument 'config' is null");
        }

        ConnectorResource result = null;
        ConnectorResource[] connectors = _connectors;
        if (config.getConnectorResources() != null) {
            // use the specified resources, if any
            connectors = config.getConnectorResources().getConnectorResource();
        }

        String name = scheme.toString();
        for (int i = 0; i < connectors.length; ++i) {
            if (connectors[i].getScheme().toString().equals(name)) {
                result = connectors[i];
                break;
            }
        }
        return result;
    }

    static {
        // load the default connector resources

        InputStream stream = null;
        try {
            ConnectorResources connectors = null;
            stream = ConnectorHelper.class.getResourceAsStream(RESOURCE);
            connectors = ConnectorResources.unmarshal(
                new InputStreamReader(stream));
            _connectors = connectors.getConnectorResource();
        } catch (Exception exception) {
            _log.error(exception, exception);
            throw new RuntimeException(exception.getMessage());
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ignore) {
                }
            }
        }
    }

}
