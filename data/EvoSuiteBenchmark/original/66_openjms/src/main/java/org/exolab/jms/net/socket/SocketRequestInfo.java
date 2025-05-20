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
 * $Id: SocketRequestInfo.java,v 1.8 2005/12/01 13:44:38 tanderson Exp $
 */
package org.exolab.jms.net.socket;

import java.util.Map;

import org.exolab.jms.net.connector.URIRequestInfo;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.uri.URIHelper;
import org.exolab.jms.net.uri.InvalidURIException;
import org.exolab.jms.net.util.Properties;
import org.exolab.jms.net.orb.ORB;


/**
 * Implementation of the {@link org.exolab.jms.net.connector.ConnectionRequestInfo}
 * interface that enables socket based connectors to pass data across the
 * connection request flow.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.8 $ $Date: 2005/12/01 13:44:38 $
 */
public class SocketRequestInfo extends URIRequestInfo {

    /**
     * The alternative host for clients to connect to, if connections to {@link
     * #getURI} fail. May be <code>null</code>.
     */
    private String _alternativeHost;

    /**
     * The maximum queue size for incoming connection indications (a request to
     * connect). If a connection indication arrives when the queue is full, the
     * connection is refused.
     */
    private int _connectionRequestQueueSize = 50;

    /**
     * Determines if connections should be accepted on all addresses, on a
     * multi-homed host. If <code>true</code>, server sockets will accept
     * connections on all local addresses. If <code>false</code>, only
     * connections to a specified address will be accepted.
     */
    private boolean _bindAll = true;

    /**
     * Connection property name to indicate the alternative host to connect
     * to, if a connection cannot be established to the primary host.
     */
    private static final String ALTERNATIVE_HOST = "alt";

    /**
     * Connection property name to indicate if connections should be accepted
     * on all addresses, on a multi-homed host.
     */
    private static final String BIND_ALL = "bindAll";


    /**
     * Construct a new <code>SocketRequestInfo</code>.
     *
     * @param uri the URI
     * @throws ResourceException if <code>uri</code> has an invalid query string
     */
    public SocketRequestInfo(URI uri) throws ResourceException {
        super(URIHelper.getURISansQuery(uri));
        init(uri);
    }

    /**
     * Construct a new <code>SocketRequestInfo</code>.
     *
     * @param uri the URI
     * @param properties the properties to populate this from
     * @throws ResourceException if any of the properties are invalid
     */
    public SocketRequestInfo(URI uri, Properties properties)
            throws ResourceException {
        super(URIHelper.getURISansQuery(uri));
        setBindAll(properties.getBoolean(BIND_ALL, _bindAll));
        init(uri);
    }

    /**
     * Sets the alternative host. This is used as an alternative address for
     * clients to connect to, if connections to {@link #getURI} fail.
     * <p/>
     * This can be useful if the server is behind a NAT firewall, and clients
     * need to connect from both outside and inside the firewall.
     *
     * @param host the alternative host. May be <code>null</code>
     */
    public void setAlternativeHost(String host) {
        _alternativeHost = host;
    }

    /**
     * Returns the alternative host.
     *
     * @return the alternative host, or <code>null</code> if none has been set.
     */
    public String getAlternativeHost() {
        return _alternativeHost;
    }

    /**
     * Helper to return the alternative URI. This is the URI returned by
     * {@link #getURI} with the host set to {@link #getAlternativeHost}.
     *
     * @return the alternative URI, or <code>null</code> if the alternative
     * host is not set.
     * @throws ResourceException if the alternative URI is invalid
     */
    public URI getAlternativeURI() throws ResourceException {
        URI result = null;
        if (_alternativeHost != null) {
            result = new URI(getURI());
            try {
                result.setHost(_alternativeHost);
            } catch (URI.MalformedURIException exception) {
                throw new ResourceException(exception);
            }
        }
        return result;
    }

    /**
     * Sets the maximum queue size for incoming connection indications (a
     * request to connect). If a connection indication arrives when the queue is
     * full, the connection is refused.
     *
     * @param size the queue size
     */
    public void setConnectionRequestQueueSize(int size) {
        _connectionRequestQueueSize = size;
    }

    /**
     * Returns the maximum queue size for incoming connection indications.
     *
     * @return the maximum queue size for incoming connection indications.
     */
    public int getConnectionRequestQueueSize() {
        return _connectionRequestQueueSize;
    }

    /**
     * Sets how socket connections should be accepted, on a multi-homed host.
     *
     * @param bindAll if <code>true</code>, server sockets will accept
     *                connections on all local addresses. If <code>false</code>,
     *                only connections to a specified address will be accepted.
     */
    public void setBindAll(boolean bindAll) {
        _bindAll = bindAll;
    }

    /**
     * Determines if socket connections should be accepted on all addresses, on
     * a multi-homed host.
     *
     * @return <code>true</code> if server sockets should accept connections on
     *         all local addresses; otherwise <code>false</code>, indicating
     *         that only connections to a specified address will be accepted.
     */
    public boolean getBindAll() {
        return _bindAll;
    }

    /**
     * Helper to export this to a {@link Properties} instance.
     *
     * @param properties the properties to export to.
     */
    public void export(Properties properties) {
        String uri = getURI().toString();
        if (_alternativeHost != null) {
            uri += "?" + ALTERNATIVE_HOST + "=" + _alternativeHost;
        }
        properties.set(ORB.PROVIDER_URI, uri);
        properties.set(BIND_ALL, getBindAll());
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
        if (other instanceof SocketRequestInfo && super.equals(other)) {
            SocketRequestInfo info = (SocketRequestInfo) other;
            if (equals(_alternativeHost, info._alternativeHost)
                    && _connectionRequestQueueSize
                    == info._connectionRequestQueueSize
                    && _bindAll == info._bindAll) {
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
    protected boolean equals(Object o1, Object o2) {
        boolean equal = (o1 == null && o2 == null);
        if (!equal) {
            if (o1 != null && o1.equals(o2)) {
                equal = true;
            }
        }
        return equal;
    }

    /**
     * Initialises this.
     *
     * @param uri the uri
     * @throws ResourceException
     */
    private void init(URI uri) throws ResourceException {
        String query = uri.getQueryString();
        if (query != null) {
            Map properties;
            try {
                properties = URIHelper.parseQuery(query);
            } catch (InvalidURIException exception) {
                throw new ResourceException(exception);
            }
            String host = (String) properties.get(ALTERNATIVE_HOST);
            setAlternativeHost(host);
        }
    }

}
