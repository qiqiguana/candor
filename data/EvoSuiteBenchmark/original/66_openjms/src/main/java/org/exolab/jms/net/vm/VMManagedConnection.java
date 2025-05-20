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
 * $Id: VMManagedConnection.java,v 1.7 2006/12/16 12:37:17 tanderson Exp $
 */
package org.exolab.jms.net.vm;

import org.exolab.jms.common.uuid.UUIDGenerator;
import org.exolab.jms.net.connector.AbstractManagedConnection;
import org.exolab.jms.net.connector.Caller;
import org.exolab.jms.net.connector.CallerImpl;
import org.exolab.jms.net.connector.Connection;
import org.exolab.jms.net.connector.IllegalStateException;
import org.exolab.jms.net.connector.InvocationHandler;
import org.exolab.jms.net.connector.ManagedConnectionListener;
import org.exolab.jms.net.connector.MarshalledInvocation;
import org.exolab.jms.net.connector.Request;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.connector.Response;
import org.exolab.jms.net.connector.URIRequestInfo;
import org.exolab.jms.net.uri.InvalidURIException;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.uri.URIHelper;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.MarshalledObject;
import java.security.Principal;


/**
 * <code>VMManagedConnection</code> manages multiple <code>VMConnection</code>
 * instances.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.7 $ $Date: 2006/12/16 12:37:17 $
 */
class VMManagedConnection extends AbstractManagedConnection {

    /**
     * The invoker for delegating invocations to the remote managed connection.
     */
    private VMInvoker _remoteInvoker;

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
     * Construct a new client <code>VMManagedConnection</code>.
     *
     * @param principal the security principal
     * @param info      the connection request info
     * @throws ResourceException for any error
     */
    protected VMManagedConnection(Principal principal, URIRequestInfo info)
            throws ResourceException {
        _remoteURI = info.getURI();
        try {
            _localURI = URIHelper.create("vm", null, -1,
                                         UUIDGenerator.create());
        } catch (InvalidURIException exception) {
            throw new ResourceException("Failed to generate local URI",
                                        exception);
        }
        VMInvoker invoker = new VMInvoker(this);
        _remoteInvoker = VMManagedConnectionAcceptor.connect(principal, info,
                                                             invoker,
                                                             _localURI);
        _principal = principal;
    }

    /**
     * Construct a new server <code>VMManagedConnection</code>.
     *
     * @param principal the security principal
     * @param info      the connection request info
     * @param client    the invoker which delegates invocations to the client
     *                  managed connection
     * @param uri       the URI representing the client
     */
    protected VMManagedConnection(Principal principal, URIRequestInfo info,
                                  VMInvoker client, URI uri) {
        _localURI = info.getURI();
        _remoteInvoker = client;
        _remoteURI = uri;
        _caller = new CallerImpl(_remoteURI, _localURI);
        _principal = principal;
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
        return new VMConnection(this);
    }

    /**
     * Registers a handler for handling invocations on objects exported via this
     * connection.
     *
     * @param handler the invocation handler
     * @throws IllegalStateException if a handler is already registered
     * @throws ResourceException     for any error
     */
    public synchronized void setInvocationHandler(InvocationHandler handler)
            throws ResourceException {
        if (_invoker != null) {
            throw new IllegalStateException(
                    "An invocation handler is already registered");
        }
        _invoker = handler;
    }

    /**
     * Ping the connection. The connection event listener will be notified
     * if the ping succeeds.
     *
     * @throws ResourceException for any error
     */
    public void ping() throws ResourceException {
        VMInvoker invoker;
        synchronized (this) {
            invoker = _remoteInvoker;
        }
        if (invoker == null) {
            throw new IllegalStateException("No connection");
        }
        if (invoker.isAlive()) {
            ManagedConnectionListener listener = getConnectionEventListener();
            if (listener != null) {
                listener.pinged(this);
            }
        }
    }

    /**
     * Destroys the physical connection.
     *
     * @throws ResourceException for any error
     */
    public void destroy() throws ResourceException {
        VMInvoker invoker;
        synchronized (this) {
            invoker = _remoteInvoker;
            _remoteInvoker = null;
        }
        if (invoker != null) {
            invoker.destroy();
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
     */
    protected Response invoke(Connection connection, Request request) {
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
     * @throws MarshalException if the request can't be unmarshalled or the
     *                          response can't be marshalled
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
     * Determines if the local end of the connection is alive.
     *
     * @return <code>true</code> if the connection is alive
     */
    protected boolean isAliveLocal() {
        boolean alive;
        synchronized (this) {
            alive = (_remoteInvoker != null);
        }
        return alive;
    }

    /**
     * Destroys the connection.
     *
     * @throws ResourceException for any error
     */
    protected void destroyLocal() throws ResourceException {
        synchronized (this) {
            _remoteInvoker = null;
        }
        ManagedConnectionListener listener = getConnectionEventListener();
        if (listener != null) {
            listener.closed(this);
        }
    }

}
