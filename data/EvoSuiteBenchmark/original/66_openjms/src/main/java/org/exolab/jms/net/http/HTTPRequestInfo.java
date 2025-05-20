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
 * $Id: HTTPRequestInfo.java,v 1.5 2005/05/03 13:45:58 tanderson Exp $
 */
package org.exolab.jms.net.http;

import org.exolab.jms.net.connector.ConnectionRequestInfo;
import org.exolab.jms.net.connector.URIRequestInfo;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.util.SSLProperties;
import org.exolab.jms.net.util.Properties;


/**
 * Implementation of the {@link ConnectionRequestInfo} interface that enables
 * the HTTP connector to pass data across the connection request flow.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.5 $ $Date: 2005/05/03 13:45:58 $
 */
public class HTTPRequestInfo extends URIRequestInfo {

    /**
     * The proxy host, if the client needs to connect via a proxy.
     */
    private String _proxyHost;

    /**
     * The proxy port, if the client needs to connect via a proxy.
     */
    private int _proxyPort;

    /**
     * The proxy user, if the client needs to log in to the proxy.
     */
    private String _proxyUser;

    /**
     * The proxy password, if the client needs to log in to the proxy.
     */
    private String _proxyPassword;

    /**
     * Properties to configure the secure socket layer. May be
     * <code>null</code>.
     */
    private SSLProperties _sslProperties;

    /**
     * Connection property to indicate the proxy server to use.
     */
    private static final String PROXY_HOST = "proxyHost";

    /**
     * Connection property to indicate the proxy port to use.
     */
    private static final String PROXY_PORT = "proxyPort";

    /**
     * Connection property to indicate the proxy user to use.
     */
    private static final String PROXY_USER = "proxyUser";

    /**
     * Connection property to indicate the proxy password to use.
     */
    private static final String PROXY_PASSWORD = "proxyPassword";


    /**
     * Construct a new <code>HTTPRequestInfo</code>.
     *
     * @param uri the URI
     */
    public HTTPRequestInfo(URI uri) {
        super(uri);
    }

    /**
     * Construct a new <code>HTTPRequestInfo</code>.
     *
     * @param uri the URI
     * @param properties the properties to populate this from
     * @throws ResourceException if any of the properties are invalid
     */
    public HTTPRequestInfo(URI uri, Properties properties)
            throws ResourceException {
        super(uri);
        setProxyHost(properties.get(PROXY_HOST));
        setProxyPort(properties.getInt(PROXY_PORT, 0));
        setProxyUser(properties.get(PROXY_USER));
        setProxyPassword(properties.get(PROXY_PASSWORD));

        SSLProperties ssl = new SSLProperties(properties);
        if (!ssl.isEmpty()) {
            setSSLProperties(ssl);
        }
    }

    /**
     * Sets the proxy host.
     *
     * @param host the proxy host
     */
    public void setProxyHost(String host) {
        _proxyHost = host;
    }

    /**
     * Returns the proxy host.
     *
     * @return the proxy host, or <code>null</code> if none is set
     */
    public String getProxyHost() {
        return _proxyHost;
    }

    /**
     * Sets the proxy port.
     *
     * @param port the proxy port
     */
    public void setProxyPort(int port) {
        _proxyPort = port;
    }

    /**
     * Returns the proxy port.
     *
     * @return the proxy port, or <code>0</code> if none is set
     */
    public int getProxyPort() {
        return _proxyPort;
    }

    /**
     * Sets the proxy user.
     *
     * @param user the proxy user
     */
    public void setProxyUser(String user) {
        _proxyUser = user;
    }

    /**
     * Returns the proxy user.
     *
     * @return the proxy user, or <code>null</code> if none is set
     */
    public String getProxyUser() {
        return _proxyUser;
    }

    /**
     * Sets the proxy password.
     *
     * @param pwd the proxy password
     */
    public void setProxyPassword(String pwd) {
        _proxyPassword = pwd;
    }

    /**
     * Returns the proxy password.
     *
     * @return the proxy password, or <code>null</code> if none is set
     */
    public String getProxyPassword() {
        return _proxyPassword;
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
     * Helper to export this to a {@link Properties} instance.
     *
     * @param properties the properties to export to.
     */
    public void export(Properties properties) {
        super.export(properties);
        properties.setNonNull(PROXY_HOST, getProxyHost());
        properties.set(PROXY_PORT, getProxyPort());
        properties.setNonNull(PROXY_USER, getProxyUser());
        properties.setNonNull(PROXY_PASSWORD, getProxyPassword());

        SSLProperties ssl = getSSLProperties();
        if (ssl != null) {
            ssl.export(properties);
        }
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
        if (other instanceof HTTPRequestInfo && super.equals(other)) {
            HTTPRequestInfo info = (HTTPRequestInfo) other;
            if (equals(_proxyHost, info._proxyHost)
                    && (_proxyPort == info._proxyPort)
                    && equals(_proxyUser, info._proxyUser)
                    && equals(_proxyPassword, info._proxyPassword)
                    && equals(_sslProperties, info._sslProperties)) {
                equal = true;
            }
        }
        return equal;
    }

    /**
     * Helper to compare two objects for equality.
     *
     * @param o1 the first object to compare
     * @param o2 the second object to compare
     * @return <code>true</code> if the objects are equal, otherwise
     *         <code>false</code>
     */
    private boolean equals(Object o1, Object o2) {
        boolean equal = (o1 == null && o2 == null);
        if (!equal) {
            if (o1 != null && o1.equals(o2)) {
                equal = true;
            }
        }
        return equal;
    }

}
