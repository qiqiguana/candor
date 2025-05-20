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
 * Copyright 2000-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: JmsServerStubImpl.java,v 1.5 2005/11/18 03:29:41 tanderson Exp $
 */
package org.exolab.jms.client.net;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import javax.jms.ExceptionListener;
import javax.jms.InvalidClientIDException;
import javax.jms.JMSException;
import javax.jms.JMSSecurityException;

import org.exolab.jms.client.JmsServerStubIfc;
import org.exolab.jms.net.connector.Caller;
import org.exolab.jms.net.connector.CallerListener;
import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.registry.Registry;
import org.exolab.jms.net.proxy.Proxy;
import org.exolab.jms.server.ServerConnection;
import org.exolab.jms.server.ServerConnectionFactory;


/**
 * This class is responsible for returning a reference to the remote JMS
 * server.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.5 $ $Date: 2005/11/18 03:29:41 $
 */
public class JmsServerStubImpl implements JmsServerStubIfc, CallerListener {

    /**
     * The ORB.
     */
    private ORB _orb;

    /**
     * Properties used to establish a connection to the remote server.
     */
    private final Map _properties;

    /**
     * The server URI;
     */
    private final String _serverURI;

    /**
     * Default user to connect to server. May be <code>null</code>.
     */
    private final String _defaultUser;

    /**
     * Default user's password. May be <code>null</code>.
     */
    private final String _defaultPassword;

    /**
     * The exception listener, which is shared by all the connections to the
     * server.
     */
    private ExceptionListener _listener = null;


    /**
     * Construct a new <code>JmsServerStubImpl</code>.
     *
     * @param properties  properties to initialise this with
     * @param environment the environment used. May be <code>null</code>
     */
    public JmsServerStubImpl(Map properties, Map environment) {
        if (properties == null) {
            throw new IllegalArgumentException("Argument 'properties' is null");
        }
        _properties = properties;

        _serverURI = (String) properties.get(ORB.PROVIDER_URI);
        if (_serverURI == null) {
            throw new IllegalArgumentException(
                    "Argument 'properties' does not contain property "
                    + ORB.PROVIDER_URI);
        }
        if (environment != null) {
            _defaultUser = (String) environment.get(ORB.SECURITY_PRINCIPAL);
            _defaultPassword = (String) environment.get(
                    ORB.SECURITY_CREDENTIALS);
        } else {
            _defaultUser = null;
            _defaultPassword = null;
        }
    }

    /**
     * Creates a connection with the specified user identity.
     * <p/>
     * The connection is created in stopped mode. No messages will be delivered
     * until the <code>Connection.start</code> method is explicitly called.
     * <p/>
     * If <code>clientID</code> is specified, it indicates the pre-configured
     * client identifier associated with the client
     * <code>ConnectionFactory</code> object.
     *
     * @param clientID the pre-configured client identifier. May be
     *                 <code>null</code>.
     * @param user     the caller's user name. May be <code>null</code>
     * @param password the caller's password. May be <code>null</code>
     * @return a newly created connection
     * @throws InvalidClientIDException if the JMS client specifies an invalid
     *                                  or duplicate client ID.
     * @throws JMSException             if the JMS provider fails to create the
     *                                  connection due to some internal error.
     * @throws JMSSecurityException     if client authentication fails due to an
     *                                  invalid user name or password.
     */
    public ServerConnection createConnection(String clientID, String user,
                                             String password)
            throws JMSException {
        ServerConnection stub;

        if (user == null) {
            user = _defaultUser;
            password = _defaultPassword;
        }

        ServerConnectionFactory factory
                = getServerConnectionFactory(user, password);
        try {
            ServerConnection connection
                    = factory.createConnection(clientID, user, password);
            stub = new JmsConnectionStubImpl(connection, _orb, _serverURI,
                                             user, password);
        } finally {
            if (factory instanceof Proxy) {
                ((Proxy) factory).disposeProxy();
            }
        }
        return stub;
    }

    /**
     * Set the exception listener so that the client can be notified of client
     * disconnection events.
     *
     * @param listener the exception listener
     */
    public void setExceptionListener(ExceptionListener listener) {
        _listener = listener;
    }

    /**
     * Notifies that a caller has been disconnected.
     *
     * @param caller the caller that was disconnected
     */
    public void disconnected(Caller caller) {
        if (_listener != null) {
            _listener.onException(new JMSException("Lost connection"));
        }
    }

    /**
     * Looks up and returns the {@link ServerConnectionFactory} instance bound
     * in the registry.
     *
     * @param user     the caller's user name. May be <code>null</code>
     * @param password the caller's password. May be <code>null</code>
     * @return the bound {@link ServerConnectionFactory}
     * @throws JMSException if lookup fails
     */
    private synchronized ServerConnectionFactory getServerConnectionFactory(
            String user, String password)
            throws JMSException {
        ServerConnectionFactory factory = null;
        Map properties = _properties;

        if (user != null) {
            properties = new HashMap(_properties);
            properties.put(ORB.SECURITY_PRINCIPAL, user);
            properties.put(ORB.SECURITY_CREDENTIALS, password);
        }
        Registry registry = null;
        try {
            if (_orb == null) {
                _orb = SharedORB.getInstance();
            }
            registry = _orb.getRegistry(properties);
        } catch (AccessException exception) {
            JMSSecurityException error = new JMSSecurityException(
                    exception.getMessage());
            error.setLinkedException(exception);
            throw error;
        } catch (RemoteException exception) {
            JMSException error = new JMSException(
                    "Failed to get registry service for URL: " + _serverURI);
            error.setLinkedException(exception);
            throw error;
        }

        try {
            factory = (ServerConnectionFactory) registry.lookup("server");
        } catch (NotBoundException exception) {
            throw new JMSException(
                    "Server is not bound in the registry for URL: "
                    + _serverURI);
        } catch (RemoteException exception) {
            JMSException error = new JMSException(
                    "Failed to lookup OpenJMS server for URL: " + _serverURI);
            error.setLinkedException(exception);
            throw error;
        }
        try {
            _orb.addCallerListener(_serverURI, this);
        } catch (RemoteException exception) {
            JMSException error = new JMSException(
                    "Failed to register for disconnection notification for "
                    + "URL: " + _serverURI);
            error.setLinkedException(exception);
            throw error;
        }

        if (registry instanceof Proxy) {
            ((Proxy) registry).disposeProxy();
        }
        return factory;
    }

}
