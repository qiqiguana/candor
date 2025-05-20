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
 * $Id: AbstractConnectionFactory.java,v 1.5 2005/05/03 13:45:58 tanderson Exp $
 */
package org.exolab.jms.net.connector;

import java.util.Map;

import java.security.Principal;

import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.uri.URIHelper;
import org.exolab.jms.net.util.Properties;


/**
 * Abstract implementation of the {@link ConnectionFactory} interface.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.5 $ $Date: 2005/05/03 13:45:58 $
 */
public abstract class AbstractConnectionFactory implements ConnectionFactory {

    /**
     * Connection property prefix. Connection properties are prefixed with this,
     * followed by the connection scheme.
     */
    public static final String PROPERTY_PREFIX = "org.exolab.jms.net.";

    /**
     * The connect scheme that this factory supports.
     */
    private final String _connectScheme;

    /**
     * The accept scheme that this factory supports.
     */
    private final String _acceptScheme;

    /**
     * The managed connection factory.
     */
    private final ManagedConnectionFactory _factory;

    /**
     * The connection manager.
     */
    private final ConnectionManager _manager;


    /**
     * Construct a new <code>AbstractConnectionFactory</code>.
     *
     * @param scheme  the scheme that this factory supports, for both connect
     *                and accept
     * @param factory the managed connection factory
     * @param manager the connection manager
     */
    public AbstractConnectionFactory(String scheme,
                                     ManagedConnectionFactory factory,
                                     ConnectionManager manager) {
        this(scheme, scheme, factory, manager);
    }

    /**
     * Construct a new <code>AbstractConnectionFactory</code>.
     *
     * @param connectScheme the connect scheme that this factory supports
     * @param acceptScheme  the accept scheme that this factory supports
     * @param factory       the managed connection factory
     * @param manager       the connection manager
     */
    public AbstractConnectionFactory(String connectScheme,
                                     String acceptScheme,
                                     ManagedConnectionFactory factory,
                                     ConnectionManager manager) {
        _connectScheme = connectScheme;
        _acceptScheme = acceptScheme;
        _factory = factory;
        _manager = manager;
    }

    /**
     * Determines if this factory supports connections to the specified URI.
     *
     * @param uri the connection address
     * @return <code>true</code> if this factory supports the URI;
     *         <code>false</code> otherwise
     */
    public boolean canConnect(URI uri) {
        return _connectScheme.equals(uri.getScheme());
    }

    /**
     * Returns a connection to the specified URI, using the default connection
     * properties.
     *
     * @param principal the security principal. May be <code>null</code>
     * @param uri       the connection address
     * @return a connection to <code>uri</code>
     * @throws ResourceException if a connection cannot be established
     */
    public Connection getConnection(Principal principal, URI uri)
            throws ResourceException {
        return getConnection(principal, uri, null);
    }

    /**
     * Returns a connection to the specified URI, using the specified connection
     * properties.
     *
     * @param principal  the security principal. May be <code>null</code>
     * @param uri        the connection address
     * @param properties connection properties. If <code>null</code>, use the
     *                   default connection properties
     * @return a connection to <code>uri</code>
     * @throws ResourceException if a connection cannot be established
     */
    public Connection getConnection(Principal principal, URI uri,
                                    Map properties)
            throws ResourceException {
        ConnectionRequestInfo info =
                getConnectionRequestInfo(uri, properties);
        if (principal == null) {
            principal = URIHelper.getPrincipal(uri);
        }
        return _manager.allocateConnection(_factory, principal, info);
    }

    /**
     * Determines if this factory supports listening for new connections on the
     * specified URI.
     *
     * @param uri the connection address
     * @return <code>true</code> if this factory supports the URI;
     *         <code>false</code> otherwise
     */
    public boolean canAccept(URI uri) {
        return _acceptScheme.equals(uri.getScheme());
    }

    /**
     * Listen for new connections on the specified URI, using the default
     * connection acceptor properties.
     *
     * @param uri the connection address
     * @throws ResourceException if connections can't be accepted on the
     *                           specified URI
     */
    public void accept(URI uri) throws ResourceException {
        accept(uri, null);
    }

    /**
     * Listen for new connections on the specified URI, using the specified
     * acceptor properties.
     *
     * @param uri        the connection address
     * @param properties acceptor properties. May be <code>null</code>
     * @throws ResourceException if connections can't be accepted on the
     *                           specified URI
     */
    public void accept(URI uri, Map properties) throws ResourceException {
        ConnectionRequestInfo info = getAcceptorRequestInfo(uri, properties);
        _manager.accept(_factory, info);
    }

    /**
     * Returns connection request info for the specified URI and connection
     * properties.
     *
     * @param uri        the connection address
     * @param properties connection properties. If <code>null</code>, use the
     *                   default connection properties
     * @return connection request info corresponding to <code>uri</code> and
     *         <code>properties</code>
     * @throws ResourceException for any error
     */
    protected abstract ConnectionRequestInfo getConnectionRequestInfo(
            URI uri, Map properties)
            throws ResourceException;

    /**
     * Returns connection request info for the specified URI and connection
     * acceptor properties.
     * <p/>
     * This implementation returns {@link #getConnectionRequestInfo(URI, Map)}.
     *
     * @param uri        the connection address
     * @param properties acceptor properties. May be <code>null</code>
     * @return connection request info corresponding to <code>uri</code> and
     *         <code>properties</code>
     * @throws ResourceException for any error
     */
    protected ConnectionRequestInfo getAcceptorRequestInfo(
            URI uri, Map properties)
            throws ResourceException {
        return getConnectionRequestInfo(uri, properties);
    }

    /**
     * Returns the managed connection factory.
     *
     * @return the managed connection factory
     */
    protected ManagedConnectionFactory getManagedConnectionFactory() {
        return _factory;
    }

    /**
     * Returns the connection manager.
     *
     * @return the connection manager
     */
    protected ConnectionManager getConnectionManager() {
        return _manager;
    }

    /**
     * Helper to return a {@link Properties} instance for the supplied
     * map.
     * <p/>
     * All searches will be performed with properties prefixed by
     * "org.exolab.jms.net.&lt;scheme&gt;." .
     */
    protected Properties getProperties(Map properties) {
        String prefix = PROPERTY_PREFIX + _connectScheme + ".";
        return new Properties(properties, prefix);
    }

}
