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
 * $Id: ConnectorService.java,v 1.4 2006/02/23 11:17:40 tanderson Exp $
 */

package org.exolab.jms.server;

import java.lang.reflect.Constructor;
import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.Connector;
import org.exolab.jms.config.ConnectorHelper;
import org.exolab.jms.config.ConnectorResource;
import org.exolab.jms.config.types.SchemeType;
import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.service.Service;
import org.exolab.jms.service.ServiceException;
import org.exolab.jms.common.threads.ThreadPoolFactory;


/**
 * Service that manages the connectors configured for the server.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2006/02/23 11:17:40 $
 */
public class ConnectorService extends Service {

    /**
     * The configuration.
     */
    private final Configuration _config;

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
     * The interfaces to this server. One interface is constructed for each
     * configured connector.
     */
    private ServerConnector[] _interfaces = null;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(ConnectorService.class);


    /**
     * Construct a new <code>ConnectorService</code>.
     *
     * @param config the configuration to use
     * @param names  the name service
     */
    public ConnectorService(Configuration config,
                            Authenticator authenticator,
                            ServerConnectionFactory factory,
                            AdminConnectionManager manager,
                            NameService names,
                            ThreadPoolFactory threads) {
        super("ConnectorService");
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
            throw new IllegalArgumentException("Argument 'threads' is null");
        }
        _config = config;
        _authenticator = authenticator;
        _factory = factory;
        _manager = manager;
        _names = names;
        _threads = threads;
    }

    /**
     * Start the service.
     *
     * @throws ServiceException if the service fails to start
     */
    protected void doStart() throws ServiceException {
        try {
            Context context = _names.getInitialContext();
            initConnectors(context);
        } catch (NamingException exception) {
            throw new ServiceException(exception.getMessage(), exception);
        }
    }

    /**
     * Stop the service.
     *
     * @throws ServiceException if the service fails to stop
     */
    protected void doStop() throws ServiceException {
        for (int i = 0; i < _interfaces.length; ++i) {
            _interfaces[i].close();
        }
    }

    /**
     * Creates an interface to the server for each configured connector.
     *
     * @param context the initial context
     * @throws NamingException  if administered objects cannot be bound in JNDI
     * @throws ServiceException if an interface can't be created
     */
    protected void initConnectors(Context context)
            throws NamingException, ServiceException {

        Connector[] connectors = _config.getConnectors().getConnector();
        _interfaces = new ServerConnector[connectors.length];

        for (int i = 0; i < connectors.length; ++i) {
            Connector connector = connectors[i];
            _interfaces[i] = initConnector(connector, context);
        }
    }

    /**
     * Create an interface to the server for the specified connector.
     *
     * @param connector the connector
     * @param context   the initial context
     * @return the interface corresponding to <code>connector</code>
     * @throws NamingException  if administered objects cannot be bound in JNDI
     * @throws ServiceException if the interface can't be created
     */
    protected ServerConnector initConnector(Connector connector,
                                            Context context)
            throws NamingException, ServiceException {

        _log.info("Creating server interface for the " + connector.getScheme()
                  + " connector");

        ServerConnector server;
        ConnectorResource resource = ConnectorHelper.getConnectorResource(
                connector.getScheme(), _config);

        String className = resource.getServer().getImplementationClass();
        Class clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException exception) {
            throw new ServiceException("Failed to load class " + className);
        }

        if (!ServerConnector.class.isAssignableFrom(clazz)) {
            throw new ServiceException(
                    "Class " + className
                    + " does not implement ServerConnector");
        }
        try {
            SchemeType scheme = connector.getScheme();
            Constructor ctor = clazz.getConstructor(new Class[]{
                SchemeType.class, Configuration.class,
                Authenticator.class, ServerConnectionFactory.class,
                AdminConnectionManager.class, NameService.class,
                ThreadPoolFactory.class});
            server = (ServerConnector) ctor.newInstance(new Object[]{
                scheme, _config, _authenticator, _factory, _manager, _names,
                _threads});
        } catch (NoSuchMethodException ignore) {
            // fall back to the default constructor
            try {
                server = (ServerConnector) clazz.newInstance();
            } catch (Exception exception) {
                throw new ServiceException(exception.getMessage(), exception);
            }
        } catch (Exception exception) {
            throw new ServiceException(exception.getMessage(), exception);
        }

        _log.debug("Created an instance of " + className
                   + " as a server interface");

        // initialise the interface
        server.init();

        // bind any configured connection factories
        server.bindConnectionFactories(context);

        return server;
    }


}
