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
 * Copyright 2004-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: RMIRequestInfo.java,v 1.2 2005/05/03 13:45:58 tanderson Exp $
 */
package org.exolab.jms.net.rmi;

import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.connector.URIRequestInfo;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.util.Properties;


/**
 * Implementation of the {@link org.exolab.jms.net.connector.ConnectionRequestInfo}
 * interface that enables the RMI connector to pass data across the connection
 * request flow.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/05/03 13:45:58 $
 */
public class RMIRequestInfo extends URIRequestInfo {

    /**
     * Determines if the registry is embedded or not.
     */
    private boolean _embedRegistry = true;

    /**
     * Connection property name to indicate the whether the registry is embedded
     * or not.
     */
    protected static final String EMBED_REGISTRY = "embedRegistry";


    /**
     * Construct a new <code>RMIRequestInfo</code>.
     *
     * @param uri the URI
     */
    public RMIRequestInfo(URI uri) {
        super(uri);
    }

    /**
     * Construct a new <code>RMIRequestInfo</code>.
     *
     * @param uri        the URI
     * @param properties the properties to populate this from
     * @throws ResourceException if any of the properties are invalid
     */
    public RMIRequestInfo(URI uri, Properties properties)
            throws ResourceException {
        super(uri);
        setEmbedRegistry(properties.getBoolean(EMBED_REGISTRY, true));
    }

    /**
     * Sets if the registry is embedded or external.
     *
     * @param embedded if <code>true</code> the registry is embedded, otherwise
     *                 its external
     */
    public void setEmbedRegistry(boolean embedded) {
        _embedRegistry = embedded;
    }

    /**
     * Determines if the registry is embedded or external.
     *
     * @return <code>true</code> the registry is embedded, otherwise its
     *         external
     */
    public boolean getEmbedRegistry() {
        return _embedRegistry;
    }

    /**
     * Helper to export this to a {@link Properties} instance.
     *
     * @param properties the properties to export to.
     */
    public void export(Properties properties) {
        super.export(properties);
        properties.set(EMBED_REGISTRY, getEmbedRegistry());
    }

    /**
     * Checks whether this instance is equal to another.
     *
     * @param other the object to compare
     * @return <code>true</code> if the two instances are equal; otherwise
     *         <code>false</code>
     */
    public boolean equals(Object other) {
        boolean equal = super.equals(other);
        if (equal && other instanceof RMIRequestInfo) {
            RMIRequestInfo info = (RMIRequestInfo) other;
            if (_embedRegistry == info._embedRegistry) {
                equal = true;
            }
        } else {
            equal = false;
        }
        return equal;
    }

}
