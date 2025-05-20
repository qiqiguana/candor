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
 * $Id: ConnectorCfgFactory.java,v 1.1 2005/05/03 13:47:22 tanderson Exp $
 */
package org.exolab.jms.server.net;

import org.exolab.jms.config.types.SchemeType;
import org.exolab.jms.config.Configuration;


/**
 * Factory for {@link ConnectorCfg} instances.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/05/03 13:47:22 $
 */
public class ConnectorCfgFactory {

    /**
     * Creates a new <code>ConnectorCfg</code>.
     *
     * @param scheme the connector scheme
     * @param config the configuration to use
     * @return a new <code>ConnectorCfg</code>.
     */
    public static ConnectorCfg create(
            SchemeType scheme, Configuration config) {
        ConnectorCfg result = null;
        if (scheme.equals(SchemeType.TCP)) {
            result = new TCPConnectorCfg(config);
        } else if (scheme.equals(SchemeType.TCPS)) {
            result = new TCPSConnectorCfg(config);
        } else if (scheme.equals(SchemeType.RMI)) {
            result = new RMIConnectorCfg(config);
        } else if (scheme.equals(SchemeType.HTTP)) {
            result = new HTTPConnectorCfg(config);
        } else if (scheme.equals(SchemeType.HTTPS)) {
            result = new HTTPSConnectorCfg(config);
        } else if (scheme.equals(SchemeType.EMBEDDED)) {
            result = new VMConnectorCfg(config);
        }
        return result;
    }
}
