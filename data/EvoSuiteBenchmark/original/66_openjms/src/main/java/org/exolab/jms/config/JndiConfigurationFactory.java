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
 * Copyright 2001-2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: JndiConfigurationFactory.java,v 1.1 2004/11/26 01:50:41 tanderson Exp $
 */
package org.exolab.jms.config;

import javax.naming.Context;

import org.exolab.jms.config.types.SchemeType;


/**
 * This factory creates appropriate {@link JndiConfiguration} objects for a
 * given {@link SchemeType}.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:41 $
 * @author      <a href="mailto:tima@intalio.com">Tim Anderson</a>
 * @see         Configuration
 */
public class JndiConfigurationFactory {

    /**
     * Returns the JNDI configuration deriving the settings from the supplied
     * configuration. If more than one connector is configured, the first
     * will be selected
     *
     * @param config the configuration to derive settings from
     * @return the JNDI configuration
     * @throws IllegalArgumentException if config is null
     */
    public static JndiConfiguration create(Configuration config) {
        if (config == null) {
            throw new IllegalArgumentException("Argument config is null");
        }
        JndiConfiguration result = new JndiConfiguration();

        Connectors connectors = config.getConnectors();
        Connector connector = connectors.getConnector(0);
        return create(connector, config);
    }

    /**
     * Returns the JNDI configuration for a particular connector, deriving
     * the settings from the supplied configuration
     *
     * @param connector the connector to return the JNDI configuration for
     * @param config the configuration to derive settings from
     * @return the JNDI configuration
     * @throws IllegalArgumentException if any argument is null
     */
    public static JndiConfiguration create(Connector connector,
                                           Configuration config) {
        if (connector == null) {
            throw new IllegalArgumentException("Argument connector is null");
        }
        if (config == null) {
            throw new IllegalArgumentException("Argument config is null");
        }
        JndiConfiguration result = new JndiConfiguration();

        ServerConfiguration server = config.getServerConfiguration();
        SchemeType scheme = connector.getScheme();
        ConnectorResource resource = ConnectorHelper.getConnectorResource(
            scheme, config);

        Property context = new Property();
        context.setName(Context.INITIAL_CONTEXT_FACTORY);
        context.setValue(resource.getJndi().getInitialContextClass());
        result.addProperty(context);

        Property url = new Property();
        url.setName(Context.PROVIDER_URL);
        url.setValue(ConfigHelper.getJndiURL(scheme, config));
        result.addProperty(url);
        return result;
    }

} //-- JndiConfigurationFactory
