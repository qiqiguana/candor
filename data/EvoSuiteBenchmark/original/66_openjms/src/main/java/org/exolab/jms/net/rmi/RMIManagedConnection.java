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
 * $Id: RMIManagedConnection.java,v 1.8 2006/12/16 12:37:17 tanderson Exp $
 */
package org.exolab.jms.net.rmi;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.MarshalException;
import java.rmi.MarshalledObject;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.Principal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.common.uuid.UUIDGenerator;
import org.exolab.jms.net.connector.AbstractManagedConnection;
import org.exolab.jms.net.connector.Caller;
import org.exolab.jms.net.connector.CallerImpl;
import org.exolab.jms.net.connector.ConnectException;
import org.exolab.jms.net.connector.Connection;
import org.exolab.jms.net.connector.IllegalStateException;
import org.exolab.jms.net.connector.InvocationHandler;
import org.exolab.jms.net.connector.Request;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.connector.Response;
import org.exolab.jms.net.connector.SecurityException;
import org.exolab.jms.net.connector.MarshalledInvocation;
import org.exolab.jms.net.connector.ManagedConnectionListener;
import org.exolab.jms.net.uri.InvalidURIException;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.uri.URIHelper;


/**
 * <code>RMIManagedConnection</code> manages multiple <code>RMIConnection</code>
 * instances.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.8 $ $Date: 2006/12/16 12:37:17 $
 */
class RMIManagedConnection extends AbstractManagedConnection {

    /**
     * The invoker for serving invocations from the remote managed connection.
     */
    private RMIInvokerImpl _localInvoker;

    /**
     * The invoker for delegating invocations to the remote managed connection.
     */
    private RMIInvoker _remoteInvoker;

    /**
     * The invocation handler.
     */
    private InvocationHandler _invoker;

    /**
     * The remote address to which this is connected.
     */
    private URI _remoteURI;

    /**
     * The the local address that this connection is bound to.
     */
    private URI _localURI;

    /**
     * The security principal.
     */
    private Principal _principal;

    /**
     * Cached caller instance. Non-null if this is a server-side instance.
     */
    private Caller _caller;

    /**
     * The logger.
     */
    private static final Log _log =
            LogFactory.getLog(RMIManagedConnection.class);


    /**
     * Construct a new client <code>RMIManagedConnection</code>.
     *
     * @param principal the security principal
     * @param info      the connection request info
     * @throws ResourceException if a connection cannot be established
     */
    protected RMIManagedConnection(Principal principal, RMIRequestInfo info)
            throws ResourceException {

        Registry registry;
        _remoteURI = URIHelper.convertHostToAddress(info.getURI());
        _localURI = generateLocalURI();

        try {
            registry = LocateRegistry.getRegistry(info.getHost(),
                                                  info.getPort());
        } catch (RemoteException exception) {
            throw new ConnectException("Failed to get registry"
                                       + ", host=" + info.getHost()
                                       + ", port=" + info.getPort(),
                                       exception);
        }

        String name = RegistryHelper.getName(_remoteURI);
        RMIInvokerFactory factory;
        try {
            factory = (RMIInvokerFactory) registry.lookup(name);
        } catch (RemoteException exception) {
            throw new ConnectException("Failed to lookup connection proxy"
                                       + ", host=" + info.getHost()
                                       + ", port=" + info.getPort(),
                                       exception);
        } catch (NotBoundException exception) {
            throw new ConnectException("Connection proxy=" + name
                                       + " not bound in "
                                       + "registry, host=" + info.getHost()
                                       + ", port=" + info.getPort(),
                                       exception);
        }

        _localInvoker = new RMIInvokerImpl();
        _localInvoker.setConnection(this);
        try {
            UnicastRemoteObject.exportObject(_localInvoker);
        } catch (RemoteException exception) {
            throw new ResourceException("Failed to export invocation handler",
                                        exception);
        }
        try {
            _remoteInvoker = factory.createInvoker(principal, _localInvoker,
                                                   _localURI.toString());
        } catch (AccessException exception) {
            throw new SecurityException(exception.getMessage(), exception);
        } catch (RemoteException exception) {
            if (exception.detail instanceof AccessException) {
                throw new SecurityException(exception.getMessage(),
                                            exception.detail);
            }
            throw new ResourceException("Failed to create invocation handler",
                                        exception);
        }
        _principal = principal;
    }

    /**
     * Construct a new server <code>RMIManagedConnection</code>. This is
     * responsible for exporting the supplied local invoker on the port
     * specified by the URI.
     *
     * @param principal     the security principal
     * @param localInvoker  the invoker which delegates invocations to this
     * @param localURI      the URI to export the connection proxy on
     * @param remoteInvoker the invoker which delegates invocations to the
     *                      remote managed connection
     * @param remoteURI     the URI representing the remote connection
     * @throws RemoteException if the connection proxy can't be exported
     */
    protected RMIManagedConnection(Principal principal,
                                   RMIInvokerImpl localInvoker,
                                   URI localURI,
                                   RMIInvoker remoteInvoker,
                                   URI remoteURI)

            throws RemoteException {
        localInvoker.setConnection(this);
        UnicastRemoteObject.exportObject(localInvoker, localURI.getPort());
        _localInvoker = localInvoker;
        _localURI = localURI;
        _remoteInvoker = remoteInvoker;
        _remoteURI = remoteURI;
        _principal = principal;
        _caller = new CallerImpl(_remoteURI, _localURI);
    }

    /**
     * Creates a new connection handle for the underlying physical connection.
     *
     * @return a new connection handle
     * @throws IllegalStateException if an invocation handler hasn't been
     *                               registered
     */
    public synchronized Connection getConnection()
            throws IllegalStateException {
        if (_invoker == null) {
            throw new IllegalStateException("No InvocationHandler registered");
        }
        return new RMIConnection(this);
    }

    /**
     * Registers a handler for handling invocations.
     *
     * @param handler the invocation handler
     * @throws IllegalStateException if a handler is already registered
     */
    public synchronized void setInvocationHandler(InvocationHandler handler)
            throws IllegalStateException {
        if (_invoker != null) {
            throw new IllegalStateException(
                    "An invocation handler is already registered");
        }
        _invoker = handler;
    }

    /**
     * Ping the connection. The connection event listener will be notified
     * if the ping succeeds.
     * NOTE: the notification may occur prior to this call returning.
     *
     * @throws ResourceException for any error
     */
    public void ping() throws ResourceException {
        RMIInvoker invoker;
        synchronized (this) {
            invoker = _remoteInvoker;
        }
        if (invoker != null) {
            try {
                invoker.ping();
                ManagedConnectionListener listener
                        = getConnectionEventListener();
                if (listener != null) {
                    listener.pinged(this);
                }
            } catch (RemoteException exception) {
                throw new ResourceException(exception);
            }
        } else {
            throw new IllegalStateException("Connection not established");
        }
    }

    /**
     * Destroys the physical connection.
     *
     * @throws ResourceException for any error
     */
    public void destroy() throws ResourceException {
        RMIInvoker localInvoker;
        RMIInvoker remoteInvoker;
        synchronized (this) {
            localInvoker = _localInvoker;
            remoteInvoker = _remoteInvoker;
        }
        if (remoteInvoker != null) {
            // notify peer of disconnection
            try {
                remoteInvoker.disconnect();
            } catch (RemoteException ignore) {
                // no-op
            }
        }
        try {
            if (localInvoker != null) {
                if (!UnicastRemoteObject.unexportObject(localInvoker, true)) {
                    _log.warn("Failed to unexport invocation handler");
                }
            }
        } catch (RemoteException exception) {
            throw new ResourceException(
                    "Failed to unexport invocation handler", exception);
        } finally {
            synchronized (this) {
                _localInvoker = null;
                _remoteInvoker = null;
            }
        }
    }

    /**
     * Returns the remote address to which this is connected.
     *
     * @return the remote address to which this is connected
     */
    public URI getRemoteURI() {
        return _remoteURI;
    }

    /**
     * Returns the local address that this connection is bound to.
     *
     * @return the local address that this connection is bound to
     */
    public URI getLocalURI() {
        return _localURI;
    }

    /**
     * Returns the principal associated with this connection.
     *
     * @return the principal associated with this connection,
     *         or <code>null<code> if none is set
     */
    public Principal getPrincipal() {
        return _principal;
    }

    /**
     * Determines if the security principal that owns this connection is the
     * same as that supplied.
     *
     * @param principal the principal to compare. May be <code>null</code>.
     * @return <code>true</code> if the principal that owns this connection is
     *         the same as <code>principal</code>
     */
    public boolean hasPrincipal(Principal principal) {
        boolean result = false;
        if ((_principal != null && _principal.equals(principal))
                || (_principal == null && principal == null)) {
            result = true;
        }
        return result;
    }

    /**
     * Invoke a method on a remote object.
     *
     * @param connection the connection performing the invocation
     * @param request    the request
     * @return the result of the invocation
     * @throws RemoteException if the distributed call cannot be made
     */
    protected Response invoke(Connection connection, Request request)
            throws RemoteException {
        Response response;
        try {
            MarshalledObject wrappedRequest = new MarshalledObject(request);
            MarshalledObject wrappedResponse =
                    _remoteInvoker.invoke(wrappedRequest);
            response = (Response) wrappedResponse.get();
        } catch (ClassNotFoundException exception) {
            response = new Response(exception);
        } catch (IOException exception) {
            response = new Response(exception);
        }
        return response;
    }

    /**
     * Invoke a method on a local object.
     *
     * @param request the wrapped <code>Request</code>
     * @return the wrapped <code>Response</code>
     * @throws MarshalException if the response can't be marshalled
     */
    protected MarshalledObject invokeLocal(MarshalledObject request)
            throws MarshalException {
        MarshalledInvocation invocation
                = new MarshalledInvocation(request, _caller);
        _invoker.invoke(invocation);

        MarshalledObject response;
        try {
            response = invocation.getMarshalledResponse();
        } catch (Exception exception) {
            throw new MarshalException("Failed to marshal response",
                                       exception);
        }
        return response;
    }

    /**
     * Invoked when the remote peer disconnects.
     */
    protected void disconnect() {
        synchronized (this) {
            _remoteInvoker = null;
        }
        notifyClosed();
    }

    /**
     * Helper to generate a URI for a client RMIManagedConnection instance.
     *
     * @return a URI that uniquely identifies a client RMIManagedConnection
     * @throws ResourceException if the URI cannot be generated
     */
    private URI generateLocalURI() throws ResourceException {
        URI result;
        String path = UUIDGenerator.create();
        try {
            result = URIHelper.create("rmi", null, -1, path);
        } catch (InvalidURIException exception) {
            throw new ResourceException("Failed to generate local URI",
                                        exception);
        }
        return result;
    }
}
