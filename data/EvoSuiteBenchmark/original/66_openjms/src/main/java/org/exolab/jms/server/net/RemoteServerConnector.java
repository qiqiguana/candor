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
 * $Id: RemoteServerConnector.java,v 1.7 2006/02/23 11:17:40 tanderson Exp $
 */
package org.exolab.jms.server.net;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Map;
import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.spice.jndikit.NamingProvider;

import org.exolab.jms.client.net.JmsServerStubImpl;
import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.types.SchemeType;
import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.orb.ORBFactory;
import org.exolab.jms.net.registry.LocalRegistry;
import org.exolab.jms.server.AdminConnectionManager;
import org.exolab.jms.server.NameService;
import org.exolab.jms.server.ServerConnectionFactory;
import org.exolab.jms.server.ServerConnector;
import org.exolab.jms.server.ServerException;
import org.exolab.jms.common.threads.ThreadPoolFactory;


/**
 * Implementation of the {@link ServerConnector} interface, that provides
 * remoting via an {@link ORB}.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.7 $ $Date: 2006/02/23 11:17:40 $
 */
public class RemoteServerConnector implements ServerConnector {

    /**
     * The configuration.
     */
    private final Configuration _config;

    /**
     * The connector configuration.
     */
    private final ConnectorCfg _connector;

    /**
     * The authenticator, for authenticating clients.
     */
    private final Authenticator _authenticator;

    /**
     * The factory for <code>ServerConnection</code> instances.
     */
    private final ServerConnectionFactory _factory;

    /**
     * The admin connection manager.
     */
    private final AdminConnectionManager _manager;

    /**
     * The name service.
     */
    private final NameService _names;

    /**
     * The thread pool factory.
     */
    private final ThreadPoolFactory _threads;

    /**
     * The URI to export the server on. This is the URI that the server accepts
     * connections from clients.
     */
    private final String _exportURI;

    /**
     * The URI that clients connect to the server on. This is different to the
     * {@link _exportURI) if clients connect via a webserver.
     */
    private final String _connectURI;

    /**
     * The ORB.
     */
    private ORB _orb;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(
            RemoteServerConnector.class);


    /**
     * Construct a new <code>RemoteServerConnector</code>.
     *
     * @param scheme        the type of the connector to use
     * @param config        the server configuration
     * @param authenticator the authenticator for authenticating clients
     * @param factory       the factory for <code>ServerConnection</code>
     *                      instances
     * @param names         the name service
     * @param threads       the thread pool factory
     */
    public RemoteServerConnector(SchemeType scheme, Configuration config,
                                 Authenticator authenticator,
                                 ServerConnectionFactory factory,
                                 AdminConnectionManager manager,
                                 NameService names,
                                 ThreadPoolFactory threads) {
        if (scheme == null) {
            throw new IllegalArgumentException("Argument 'scheme' is null");
        }
        if (config == null) {
            throw new IllegalArgumentException("Argument 'config' is null");
        }
        if (authenticator == null) {
            throw new IllegalArgumentException(
                    "Argument 'authenticator' is null");
        }
        if (factory == null) {
            throw new IllegalArgumentException("Argument 'factory' is null");
        }
        if (manager == null) {
            throw new IllegalArgumentException("Argument 'manager' is null");
        }
        if (names == null) {
            throw new IllegalArgumentException("Arguement 'names' is null");
        }
        if (threads == null) {
            throw new IllegalArgumentException("Arguement 'threads' is null");
        }
        _connector = ConnectorCfgFactory.create(scheme, config);
        _config = config;
        _authenticator = authenticator;
        _factory = factory;
        _manager = manager;
        _names = names;
        _threads = threads;
        _exportURI = _connector.getExportURI();
        _connectURI = _connector.getConnectURI();
    }

    /**
     * Initialises the server interface for the specified connector.
     *
     * @throws ServerException if the interface cannot be initialised
     */
    public void init() throws ServerException {
        try {
            Map properties = _connector.getAcceptProperties();
            properties.put("org.exolab.jms.net.orb.threads.factory", _threads);
            _orb = ORBFactory.createORB(_authenticator, properties);
            if (!_connectURI.equals(_exportURI)) {
                _orb.addRoute(_exportURI, _connectURI);
            }
        } catch (RemoteException exception) {
            throw new ServerException(
                    "Failed to create ORB for URI:" + _exportURI, exception);
        }
        try {
            LocalRegistry registry = _orb.getRegistry();

            RemoteServerConnectionFactory server =
                    new RemoteServerConnectionFactory(_factory, _orb,
                                                      _exportURI);
            registry.bind("server", server.getProxy());
            if (_log.isInfoEnabled()) {
                _log.info("Server accepting connections on " + _exportURI);
            }

            if (_config.getServerConfiguration().getEmbeddedJNDI()) {
                NamingProvider provider = _names.getNamingProvider();
                RemoteNamingProvider jndi = new RemoteNamingProvider(provider,
                                                                     _orb,
                                                                     _connector.getJNDIExportURI());
                registry.bind("jndi", jndi.getProxy());
                if (_log.isInfoEnabled()) {
                    _log.info("JNDI service accepting connections on "
                              + _connector.getJNDIExportURI());
                }
            }

            JmsAdminServerImpl admin = new JmsAdminServerImpl(_manager,
                                                              _orb,
                                                              _connector.getAdminExportURI());
            registry.bind("admin", admin.getProxy());
            if (_log.isInfoEnabled()) {
                _log.info("Admin service accepting connections on "
                          + _connector.getAdminExportURI());
            }

            registry.setReadOnly(true);
        } catch (Exception exception) {
            throw new ServerException(
                    "Failed to initialise the server interface", exception);
        }
    }

    /**
     * Bind any factory object specified in the configuration file to the
     * specified JNDI context.
     *
     * @param context context to bind factory objects
     * @throws NamingException if a naming error occurs
     */
    public void bindConnectionFactories(Context context)
            throws NamingException {
        // put together a list of parameters that the connection factories will
        // need to use to connect to this server
        Map properties = _connector.getConnectProperties();
        Hashtable env = new Hashtable();
        env.putAll(properties);
        ConnectionFactoryHelper.bind(context,
                                     _connector.getConnectionFactories(),
                                     JmsServerStubImpl.class, env);
    }

    /**
     * Close the interface, releasing any resources.
     *
     * @throws ServerException if the interface cannot be closed
     */
    public void close() throws ServerException {
        try {
            _orb.shutdown();
        } catch (RemoteException exception) {
            throw new ServerException(exception.getMessage(), exception);
        }
    }

}
