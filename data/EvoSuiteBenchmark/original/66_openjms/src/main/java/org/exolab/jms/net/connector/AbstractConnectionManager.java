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
 * Copyright 2003-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: AbstractConnectionManager.java,v 1.6 2005/06/04 14:43:59 tanderson Exp $
 */
package org.exolab.jms.net.connector;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import org.exolab.jms.net.uri.URI;


/**
 * Abstract implementation of the {@link ConnectionManager} interface.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.6 $ $Date: 2005/06/04 14:43:59 $
 * @todo - code smell - implements ConnectionFactory
 */
public abstract class AbstractConnectionManager
        implements ConnectionManager, ConnectionFactory {

    /**
     * The set of managed connection factories, and their corresponding
     * ConnectionPools.
     */
    private final Map _factories = new HashMap();

    /**
     * The invocation handler.
     */
    private final InvocationHandler _handler;

    /**
     * The connection authenticator.
     */
    private final Authenticator _authenticator;

    /**
     * The set of known connection factories, and their associated
     * ManagedConnectionFactorys.
     */
    private final Map _connectionFactories = new HashMap();
    
    /**
     * Configuration properties. May be <code>null</code>.
     */
    private final Map _properties;

    /**
     * The caller event listener.
     */
    private CallerListener _listener;


    /**
     * Construct a new <code>AbstractConnectionManager</code>.
     *
     * @param handler       the invocation handler
     * @param authenticator the connection authenticator
     * @param properties    configuration properties. May be <code>null</code>
     */
    public AbstractConnectionManager(InvocationHandler handler,
                                     Authenticator authenticator,
                                     Map properties) {
        if (handler == null) {
            throw new IllegalArgumentException("Argument 'handler' is null");
        }
        if (authenticator == null) {
            throw new IllegalArgumentException(
                    "Argument 'authenticator' is null");
        }
        _handler = handler;
        _authenticator = authenticator;
        _properties = properties;
    }

    /**
     * Allocate a new connection.
     *
     * @param factory   used by application server to delegate connection
     *                  matching/creation
     * @param principal the security principal
     * @param info      the connection request info
     * @return the new connection
     * @throws ResourceException if the connection cannot be allocated
     */
    public Connection allocateConnection(ManagedConnectionFactory factory,
                                         Principal principal,
                                         ConnectionRequestInfo info)
            throws ResourceException {

        ConnectionPool pool = getConnectionPool(factory);
        ManagedConnection connection = pool.matchManagedConnections(principal,
                                                                    info);
        if (connection == null) {
            connection = pool.createManagedConnection(principal, info);
        }
        return connection.getConnection();
    }

    /**
     * Start accepting connections.
     *
     * @param factory used to delegate acceptor matching/creation
     * @param info    connection request info
     * @throws ResourceException if the connections cannot be accepted
     */
    public void accept(ManagedConnectionFactory factory,
                       ConnectionRequestInfo info) throws ResourceException {
        ConnectionPool pool = getConnectionPool(factory);
        ManagedConnectionAcceptor acceptor =
                pool.matchManagedConnectionAcceptors(info);
        if (acceptor == null) {
            acceptor = pool.createManagedConnectionAcceptor(_authenticator,
                                                            info);
            acceptor.accept(pool.getManagedConnectionAcceptorListener());
        }
    }

    /**
     * Determines if this factory supports connections to the specified URI.
     *
     * @param uri the connection address
     * @return <code>true</code> if this factory supports the URI;
     *         <code>false</code> otherwise
     */
    public boolean canConnect(URI uri) {
        ConnectionFactory factory = getFactoryForConnect(uri);
        return (factory != null);
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
        ConnectionFactory factory = getFactoryForConnect(uri);
        if (factory == null) {
            throw new ResourceException("No connector for URI=" + uri);
        }
        return factory.getConnection(principal, uri, properties);
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
        ConnectionFactory factory = getFactoryForAccept(uri);
        return (factory != null);
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
    public void accept(URI uri, Map properties)
            throws ResourceException {
        ConnectionFactory factory = getFactoryForAccept(uri);
        if (factory == null) {
            throw new ResourceException("No connector for URI=" + uri);
        }
        factory.accept(uri, properties);
    }

    /**
     * Sets the caller event listener.
     *
     * @param listener the listener
     */
    public synchronized void setCallerListener(CallerListener listener) {
        _listener = listener;
        Iterator iterator = _factories.values().iterator();
        while (iterator.hasNext()) {
            ConnectionPool pool = (ConnectionPool) iterator.next();
            pool.setCallerListener(_listener);
        }
    }

    /**
     * Close this connection manager.
     *
     * @throws ResourceException if a connection pool cannot be closed
     */
    public synchronized void close() throws ResourceException {
        Iterator iterator = _factories.values().iterator();
        while (iterator.hasNext()) {
            ConnectionPool pool = (ConnectionPool) iterator.next();
            pool.close();
        }
        _factories.clear();
    }

    /**
     * Returns the first factory which can support connections to the specified
     * URI.
     *
     * @param uri the URI
     * @return the first factory which can support connections to
     *         <code>uri</code>, or <code>null</code> if none support it
     */
    protected synchronized ConnectionFactory getFactoryForConnect(URI uri) {
        ConnectionFactory result = null;
        Iterator iterator = _connectionFactories.keySet().iterator();
        while (iterator.hasNext()) {
            ConnectionFactory factory = (ConnectionFactory) iterator.next();
            if (factory.canConnect(uri)) {
                result = factory;
                break;
            }
        }
        return result;
    }

    /**
     * Returns the first factory which can accept connections on the specified
     * URI.
     *
     * @param uri the URI
     * @return the first factory which can accept connections on
     *         <code>uri</code>, or <code>null</code> if none support it
     */
    protected synchronized ConnectionFactory getFactoryForAccept(URI uri) {
        ConnectionFactory result = null;
        Iterator iterator = _connectionFactories.keySet().iterator();
        while (iterator.hasNext()) {
            ConnectionFactory factory = (ConnectionFactory) iterator.next();
            if (factory.canAccept(uri)) {
                result = factory;
                break;
            }
        }
        return result;
    }

    /**
     * Register a managed connection factory.
     *
     * @param factory the factory to register
     * @throws ResourceException if the registration fails
     */
    protected synchronized void addManagedConnectionFactory(
            ManagedConnectionFactory factory) throws ResourceException {
        ConnectionPool pool = createConnectionPool(factory, _handler, this);
        pool.setCallerListener(_listener);
        _factories.put(factory, pool);
        _connectionFactories.put(factory.createConnectionFactory(this),
                                 factory);
    }

    /**
     * Returns all registered managed connection factories.
     *
     * @return all registered managed connection factories
     */
    protected synchronized Collection getManagedConnectionFactories() {
        return _factories.keySet();
    }
    
    /**
     * Creates a new connection pool.
     * 
     * @param factory  the managed connection factory
     * @param handler  the invocation handler, assigned to each new managed
     *                 connection
     * @param resolver the connection factory for resolving connections via
     *                 their URI
     * @throws ResourceException if the pool can't be created
     */
    protected ConnectionPool createConnectionPool(
            ManagedConnectionFactory factory, InvocationHandler handler,
            ConnectionFactory resolver) throws ResourceException {
        return new DefaultConnectionPool(factory, handler, resolver, 
                                         _properties);
    }

    /**
     * Returns the {@link ConnectionPool} which pools connections for the
     * specified factory.
     *
     * @param factory the factory to locate the pool for
     * @return the connection pool for <code>factory</code>
     * @throws ResourceException if no connection pool exists
     */
    protected synchronized ConnectionPool getConnectionPool(
            ManagedConnectionFactory factory) throws ResourceException {
        ConnectionPool pool = (ConnectionPool) _factories.get(factory);
        if (pool == null) {
            throw new ResourceException("Connection pool not found");
        }
        return pool;
    }

}
