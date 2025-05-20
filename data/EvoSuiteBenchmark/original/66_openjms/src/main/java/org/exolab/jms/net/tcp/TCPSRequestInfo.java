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
 * $Id: TCPSRequestInfo.java,v 1.2 2005/12/01 13:44:38 tanderson Exp $
 */
package org.exolab.jms.net.tcp;

import org.exolab.jms.net.socket.SocketRequestInfo;
import org.exolab.jms.net.util.SSLProperties;
import org.exolab.jms.net.util.Properties;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.uri.URI;


/**
 * Implementation of the {@link org.exolab.jms.net.connector.ConnectionRequestInfo}
 * interface that enables the TCPS connector to pass data across the
 * connection request flow.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/12/01 13:44:38 $
 */
public class TCPSRequestInfo extends SocketRequestInfo {

    /**
     * Properties to configure the secure socket layer. May be
     * <code>null</code>.
     */
    private SSLProperties _sslProperties = null;

    /**
     * Determines if connections which are accepted must include client
     * authentication. By default, clients do not need to provide
     * authentication information.
     */
    private boolean _needCientAuth = false;

    /**
     * Connection property to indicate if clients must authentication
     * themselves on connection.
     */
    private static final String NEED_CLIENT_AUTH = "needClientAuth";


    /**
     * Construct a new <code>TCPSRequestInfo</code>.
     *
     * @param uri the URI
     * @throws ResourceException if <code>uri</code> has an invalid query string
     */
    public TCPSRequestInfo(URI uri) throws ResourceException {
        super(uri);
    }

    /**
     * Construct a new <code>TCPSRequestInfo</code>.
     *
     * @param uri the URI
     * @param properties the properties to populate this from
     * @throws ResourceException if any of the properties are invalid
     */
    public TCPSRequestInfo(URI uri, Properties properties)
            throws ResourceException {
        super(uri, properties);
        SSLProperties ssl = new SSLProperties(properties);
        if (!ssl.isEmpty()) {
            setSSLProperties(ssl);
        }
        setNeedClientAuth(
                properties.getBoolean(NEED_CLIENT_AUTH, _needCientAuth));
    }

    /**
     * Returns the properties used to configure the secure socket layer (SSL).
     *
     * @return the SSL configuration properties, or <code>null</code> if unset
     */
    public SSLProperties getSSLProperties() {
        return _sslProperties;
    }

    /**
     * Sets the properties used to configure the secure socket layer (SSL).
     *
     * @param properties the SSL configuration properties
     */
    public void setSSLProperties(SSLProperties properties) {
        _sslProperties = properties;
    }

    /**
     * Sets if clients must authenticate themselves on connection.
     *
     * @param required if <code>true</code>, the clients must authenticate
     * themselves.
     */
    public void setNeedClientAuth(boolean required) {
        _needCientAuth = required;
    }

    /**
     * Determines if clients must authenticate themselves on connection.
     *
     * @return <code>true</code> if clients must authenticate themselves.
     */
    public boolean getNeedClientAuth() {
        return _needCientAuth;
    }

    /**
     * Helper to export this to a {@link Properties} instance.
     *
     * @param properties the properties to export to.
     */
    public void export(Properties properties) {
        super.export(properties);
        SSLProperties ssl = getSSLProperties();
        if (ssl != null) {
            ssl.export(properties);
        }
        properties.set(NEED_CLIENT_AUTH, getNeedClientAuth());
    }

    /**
     * Checks whether this instance is equal to another.
     *
     * @param other the object to compare
     * @return <code>true</code> if the two instances are equal; otherwise
     *         <code>false</code>
     */
    public boolean equals(Object other) {
        boolean equal = false;
        if (other instanceof TCPSRequestInfo && super.equals(other)) {
            TCPSRequestInfo info = (TCPSRequestInfo) other;
            if (_needCientAuth == info._needCientAuth
                && equals(_sslProperties, info._sslProperties)) {
                equal = true;
            }
        }
        return equal;
    }

}
