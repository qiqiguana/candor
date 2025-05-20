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
 * $Id: ConnectorCfg.java,v 1.2 2005/05/07 14:17:22 tanderson Exp $
 */
package org.exolab.jms.server.net;

import java.util.Map;

import org.exolab.jms.config.types.SchemeType;
import org.exolab.jms.config.ConnectionFactories;


/**
 * <code>ConnectorConfig</code> provides configuration information for an
 * ORB connector.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/05/07 14:17:22 $
 */
public interface ConnectorCfg {

    /**
     * Returns the connector scheme.
     *
     * @return the connector scheme
     */
    SchemeType getScheme();

    /**
     * Returns the URI used to establsh connections to remote services.
     *
     * @return the URI used to establish connections to remote services
     */
    String getConnectURI();

    /**
     * Returns the URI that services are exported on.
     *
     * @return the URI for exporting services
     */
    String getExportURI();

    /**
     * Returns the URI that JNDI service is exported on.
     * <p/>
     * Typically, this will be the same as that returned by
     * {@link #getExportURI}.
     *
     * @return the JNDI service URI
     */
    String getJNDIExportURI();

    /**
     * Returns the URI the administration service is exported on.
     * <p/>
     * Typically, this will be the same as that returned by
     * {@link #getExportURI}.
     *
     * @return the administration service URI
     */
    String getAdminExportURI();

    /**
     * Returns properties to configure the ORB to enable it to establish
     * a connection to the remote ORB.
     *
     * @return a map of String properties
     */
    Map getConnectProperties();

    /**
     * Returns properties to configure the ORB to enable it to accept
     * connections from remote ORBs.
     *
     * @return a map of String properties
     */
    Map getAcceptProperties();

    /**
     * Returns connection factories associated with this configuration.
     *
     * @return associated connection factories.
     */
    ConnectionFactories getConnectionFactories();

}
