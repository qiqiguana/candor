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
 * Copyright 2003-2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: URIRequestInfo.java,v 1.3 2005/05/03 13:45:58 tanderson Exp $
 */
package org.exolab.jms.net.connector;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Principal;

import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.uri.URIHelper;
import org.exolab.jms.net.util.Properties;
import org.exolab.jms.net.orb.ORB;


/**
 * Implementation of the {@link ConnectionRequestInfo} interface that enables a
 * connector to pass URI data across the connection request flow.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/05/03 13:45:58 $
 */
public class URIRequestInfo implements ConnectionRequestInfo {

    /**
     * The URI.
     */
    private URI _uri;

    /**
     * The security principal. May be null.
     */
    private Principal _principal;


    /**
     * Construct a new <code>URIRequestInfo</code>.
     *
     * @param uri the URI
     */
    public URIRequestInfo(URI uri) {
        _uri = uri;
        _principal = URIHelper.getPrincipal(uri);
    }

    /**
     * Returns the host.
     *
     * @return the host, or <code>null</code> if the host was not specified in
     *         the URI.
     */
    public String getHost() {
        return _uri.getHost();
    }

    /**
     * Returns the host as an <code>InetAddress</code>.
     *
     * @return the host or <code>null</code> if the host was not specified in
     *         the URI.
     * @throws UnknownHostException if no IP address for the host could be
     *                              found.
     */
    public InetAddress getHostAddress() throws UnknownHostException {
        String host = getHost();
        return (host != null) ? InetAddress.getByName(host) : null;
    }

    /**
     * Returns the port.
     *
     * @return the port
     */
    public int getPort() {
        return _uri.getPort();
    }

    /**
     * Returns the URI.
     *
     * @return the URI
     */
    public URI getURI() {
        return _uri;
    }

    /**
     * Returns the security principal.
     *
     * @return the security principal. May be null.
     */
    public Principal getPrincipal() {
        return _principal;
    }

    /**
     * Helper to export this to a {@link Properties} instance.
     *
     * @param properties the properties to export to.
     */
    public void export(Properties properties) {
        properties.set(ORB.PROVIDER_URI, _uri);
    }

    /**
     * Checks whether this instance is equal to another.
     *
     * @param other the object to compare
     * @return <code>true</code> if the two instances are equal; otherwise
     *         <code>false</code>
     */
    public boolean equals(Object other) {
        boolean equal = (this == other);
        if (!equal && other instanceof URIRequestInfo) {
            URIRequestInfo info = (URIRequestInfo) other;
            equal = _uri.equals(info._uri);
        }
        return equal;
    }

    /**
     * Returns a hashcode for this request info.
     *
     * @return a hashcode for this request info.
     */
    public int hashCode() {
        return _uri.hashCode();
    }

}
