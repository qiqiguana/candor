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
 * $Id: VersionInfo.java,v 1.1 2005/10/20 14:07:03 tanderson Exp $
 */
package org.exolab.jms.tools.migration.proxy;

import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.tools.migration.proxy.PropertyStore;


/**
 * Migration version information.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/10/20 14:07:03 $
 */
public class VersionInfo {

    /**
     * The property store.
     */
    private final PropertyStore _properties;

    /**
     * OpenJMS version property.
     */
    private final String OPENJMS_VERSION = "openjmsVersion";

    /**
     * Proxy database schema version property.
     */
    private final String PROXY_SCHEMA_VERSION = "proxySchemaVersion";

    /**
     * Proxy database creation timestamp.
     */
    private final String CREATION_TIMESTAMP = "creationTimestamp";


    /**
     * Construct a new <code>VersionInfo</code>.
     *
     * @param properties the property store to access
     */
    public VersionInfo(PropertyStore properties) {
        _properties = properties;
    }

    /**
     * Sets the OpenJMS version that the proxy database was created with.
     *
     * @param version the version
     * @throws PersistenceException for any persistence error
     */
    public void setOpenJMSVersion(String version) throws PersistenceException {
        _properties.add(OPENJMS_VERSION, version);
    }

    /**
     * Returns the OpenJMS version that the proxy database was created with.
     *
     * @return the OpenJMS version
     * @throws PersistenceException for any persistence error
     */
    public String getOpenJMSVersion() throws PersistenceException {
        return _properties.get(OPENJMS_VERSION);
    }

    /**
     * Sets the proxy schema version.
     *
     * @param version the version
     * @throws PersistenceException for any persistence error
     */
    public void setProxySchemaVersion(String version)
            throws PersistenceException {
        _properties.add(PROXY_SCHEMA_VERSION, version);
    }

    /**
     * Returns the proxy schema version.
     *
     * @return the proxy schema version.
     * @throws PersistenceException for any persistence error
     */
    public String getProxySchemaVersion() throws PersistenceException {
        return _properties.get(PROXY_SCHEMA_VERSION);
    }

    /**
     * Sets the timestamp when the proxy database was created.
     *
     * @param timestamp the timestamp, in milliseconds
     * @throws PersistenceException for any persistence error
     */
    public void setCreationTimestamp(long timestamp)
            throws PersistenceException {
        _properties.add(CREATION_TIMESTAMP, Long.toString(timestamp));
    }

    /**
     * Returns the timestamp when the proxy database was created.
     *
     * @return the proxy database creation timestamp
     * @throws PersistenceException for any persistence error
     */
    public long getCreationTimestamp() throws PersistenceException {
        String value = _properties.get(CREATION_TIMESTAMP);
        return Long.valueOf(value).longValue();
    }
}
