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
 * $Id: MultiplexedManagedConnection.java,v 1.10 2006/12/16 12:37:17 tanderson Exp $
 */
package org.exolab.jms.net.multiplexer;

import java.io.IOException;
import java.security.Principal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.net.connector.AbstractManagedConnection;
import org.exolab.jms.net.connector.Authenticator;
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
import org.exolab.jms.net.connector.ManagedConnectionListener;
import org.exolab.jms.net.uri.URI;


/**
 * A <code>ManagedConnection</code> that uses a {@link Multiplexer} to multiplex
 * data over an {@link Endpoint}
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.10 $ $Date: 2006/12/16 12:37:17 $
 */
public abstract class MultiplexedManagedConnection
        extends AbstractManagedConnection
        implements MultiplexerListener {

    /**
     * The multiplexer.
     */
    private Multiplexer _multiplexer;

    /**
     * The thread used to run {@link #_multiplexer}.
     */
    private Thread _multiplexThread;

    /**
     * The endpoint to multiplex data over.
     */
    private Endpoint _endpoint;

    /**
     * The invocation handler.
     */
    private InvocationHandler _invoker;

    /**
     * The security principal.
     */
    private Principal _principal;

    /**
     * The connection authenticator, for server side instances.
     */
    private Authenticator _authenticator;

    /**
     * Cached caller instance. Non-null if this is a server-side instance.
     */
    private Caller _caller;

    /**
     * The thread group to associate any allocated threads with.
     */
    private ThreadGroup _group;

    /**
     * The logger.
     */
    private static final Log _log =
            LogFactory.getLog(MultiplexedManagedConnection.class);


    /**
     * Construct a new client <code>MultiplexedManagedConnection</code>.
     *
     * @param principal the security principal. May be <code>null</code>
     */
    public MultiplexedManagedConnection(Principal principal) {
        _principal = principal;
    }

    /**
     * Construct a new server <code>MultiplexedManagedConnection</code>.
     *
     * @param authenticator the connection authenticator
     */
    public MultiplexedManagedConnection(Authenticator authenticator) {
        if (authenticator == null) {
            throw new IllegalArgumentException(
                    "Argument 'authenticator' is null");
        }
        _authenticator = authenticator;
    }

    /**
     * Registers a handler for handling invocations on objects exported via this
     * connection. Once a handler is registered, it cannot be de-registered.
     *
     * @param handler the invocation handler
     * @throws IllegalStateException if a handler is already registered
     * @throws ResourceException     for any error
     */
    public void setInvocationHandler(InvocationHandler handler)
            throws ResourceException {
        if (_invoker != null) {
            throw new IllegalStateException(
                    "An invocation handler is already registered");
        }
        _invoker = handler;
        try {
            _endpoint = createEndpoint();
            if (isClient()) {
                _multiplexer = createMultiplexer(_endpoint, _principal);
            } else {
                _multiplexer = createMultiplexer(_endpoint, _authenticator);
                _principal = _multiplexer.getPrincipal();
                _caller = new CallerImpl(getRemoteURI(), getLocalURI());
            }
            String name = getDisplayName() + "-Multiplexer";
            _multiplexThread = new Thread(getThreadGroup(), _multiplexer,
                                          name);
            _multiplexThread.start();
        } catch (IOException exception) {
            throw new ConnectException("Failed to start multiplexer",
                                       exception);
        }
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
        return new MultiplexedConnection(this);
    }

    /**
     * Ping the connection. The connection event listener will be notified
     * if the ping succeeds.
     *
     * @throws IllegalStateException if a connection is not established
     * @throws ResourceException for any error
     */
    public void ping() throws ResourceException {
        Multiplexer multiplexer;
        synchronized (this) {
            multiplexer = _multiplexer;
        }
        if (multiplexer != null) {
            try {
                multiplexer.ping(0);
            } catch (IOException exception) {
                throw new ResourceException(exception.getMessage(), exception);
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
        Multiplexer multiplexer;
        Thread thread;
        Endpoint endpoint;

        synchronized (this) {
            multiplexer = _multiplexer;
            thread = _multiplexThread;
            endpoint = _endpoint;
        }
        try {
            if (multiplexer != null) {
                // multiplexer handles endpoint closure
                multiplexer.close();
                if (thread != Thread.currentThread()) {
                    try {
                        // wait for the multiplexer thread to terminate
                        thread.join();
                    } catch (InterruptedException exception) {
                        _log.debug(exception);
                    }
                }
            } else {
                if (endpoint != null) {
                    try {
                        endpoint.close();
                    } catch (IOException exception) {
                        throw new ResourceException("Failed to close endpoint",
                                                    exception);
                    }
                }
            }
        } finally {
            synchronized (this) {
                _multiplexer = null;
                _multiplexThread = null;
                _endpoint = null;
            }
        }
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
     * <p/>
     * NOTE: If this is a server-side instance, the principal is only available
     * once the connection has been established, by {@link
     * #setInvocationHandler}
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
     * Invoked for an invocation request.
     *
     * @param channel the channel the invocation is on
     */
    public void request(Channel channel) {
        _invoker.invoke(new ChannelInvocation(channel, getCaller()));
    }

    /**
     * Invoked when the connection is closed by the peer.
     */
    public void closed() {
        notifyClosed();
    }

    /**
     * Invoked when an error occurs on the multiplexer.
     *
     * @param error the error
     */
    public void error(Throwable error) {
        notifyError(error);
    }

    /**
     * Notifies of a successful ping.
     *
     * @param token the token sent in the ping
     */
    public void pinged(int token) {
        ManagedConnectionListener listener = getConnectionEventListener();
        if (listener != null) {
            listener.pinged(this);
        }
    }

    /**
     * Invoke a method on a remote object.
     *
     * @param connection the connection invoking the request
     * @param request    the request
     * @return the response
     */
    protected Response invoke(Connection connection, Request request) {
        Response response;
        Multiplexer multiplexer;
        synchronized (this) {
            multiplexer = _multiplexer;
        }
        if (multiplexer != null) {
            Channel channel = null;
            try {
                channel = multiplexer.getChannel();
                response = channel.invoke(request);
                channel.release();
            } catch (Exception exception) {
                _log.debug(exception, exception);
                response = new Response(exception);
                if (channel != null) {
                    channel.destroy();
                }
            }
        } else {
            response = new Response(new ResourceException("Connection lost"));
        }

        return response;
    }

    /**
     * Creates the endpoint to multiplex data over.
     *
     * @return the endpoint to multiplex data over
     * @throws IOException for any I/O error
     */
    protected abstract Endpoint createEndpoint() throws IOException;

    /**
     * Create a new client-side multiplexer.
     *
     * @param endpoint  the endpoint to multiplex messages over
     * @param principal the security principal
     * @return a new client-side multiplexer
     * @throws IOException       if an I/O error occurs
     * @throws SecurityException if connection is refused by the server
     */
    protected Multiplexer createMultiplexer(Endpoint endpoint,
                                            Principal principal)
            throws IOException, SecurityException {
        return new Multiplexer(this, endpoint, principal);
    }

    /**
     * Create a new server-side multiplexer.
     *
     * @param endpoint      the endpoint to multiplex messages over
     * @param authenticator the connection authetnicator
     * @return a new server-side multiplexer
     * @throws IOException       if an I/O error occurs
     * @throws ResourceException if the authenticator cannot authenticate
     */
    protected Multiplexer createMultiplexer(Endpoint endpoint,
                                            Authenticator authenticator)
            throws IOException, ResourceException {
        return new Multiplexer(this, endpoint, authenticator);
    }

    /**
     * Helper to determine if this is a client-side or server side instance.
     *
     * @return <code>true</code> if this is a client-side instance, otherwise
     *         <code>false</code>
     */
    protected boolean isClient() {
        return (_authenticator == null);
    }

    /**
     * Helper to return an {@link Caller} instance, denoting the client
     * performing a method invocation. Only applicable for server-side, and only
     * after the multiplexer has been created.
     *
     * @return the caller instance, or <code>null</code> if it hasn't been
     *         initialised
     */
    protected Caller getCaller() {
        return _caller;
    }

    /**
     * Returns the thread group to associate with allocated threads.
     *
     * @return the thread group to associate with allocated threads, or
     *         <code>null</code> to use the default thread group.
     */
    protected synchronized ThreadGroup getThreadGroup() {
        if (_group == null) {
            _group = new ThreadGroup(getDisplayName());
        }
        return _group;
    }

    /**
     * Helper to generate a descriptive name, for display purposes.
     * <p/>
     * This implementation returns the remote URI, concatenated with "[client]"
     * if this is a client connection, or "[server]" if it is a server
     * connection.
     *
     * @return the display name
     */
    protected String getDisplayName() {
        StringBuffer name = new StringBuffer();
        URI uri = null;
        try {
            uri = getRemoteURI();
        } catch (ResourceException ignore) {
            if (_log.isDebugEnabled()) {
                _log.debug("Failed to determine remote URI", ignore);
            }
        }
        if (uri != null) {
            name.append(uri.toString());
        } else {
            name.append("<unknown>");
        }
        if (isClient()) {
            name.append("[client]");
        } else {
            name.append("[server]");
        }
        return name.toString();
    }

}
