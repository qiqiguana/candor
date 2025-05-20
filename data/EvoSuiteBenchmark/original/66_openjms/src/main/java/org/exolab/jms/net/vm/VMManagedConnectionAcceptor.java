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
 * $Id: VMManagedConnectionAcceptor.java,v 1.4 2005/11/12 12:42:54 tanderson Exp $
 */
package org.exolab.jms.net.vm;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.net.connector.ConnectException;
import org.exolab.jms.net.connector.ManagedConnectionAcceptor;
import org.exolab.jms.net.connector.ManagedConnectionAcceptorListener;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.connector.SecurityException;
import org.exolab.jms.net.connector.URIRequestInfo;
import org.exolab.jms.net.uri.URI;


/**
 * A <code>VMManagedConnectionAcceptor</code> is responsible for accepting
 * connections, and constructing new {@link VMManagedConnection} instances to
 * serve them.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2005/11/12 12:42:54 $
 */
class VMManagedConnectionAcceptor implements ManagedConnectionAcceptor {

    /**
     * The connection authenticator.
     */
    private final Authenticator _authenticator;

    /**
     * The URI denoting this acceptor.
     */
    private final URI _uri;

    /**
     * The listener to delegate accepted connections to.
     */
    private ManagedConnectionAcceptorListener _listener;

    /**
     * The set of acceptors, keyed on URI.
     */
    private static Map _acceptors = new HashMap();

    /**
     * The logger.
     */
    private static final Log _log =
            LogFactory.getLog(VMManagedConnectionAcceptor.class);


    /**
     * Construct a new <code>VMConnectionAcceptor</code>.
     *
     * @param authenticator the connection authenticator
     * @param info          the connection request info
     */
    public VMManagedConnectionAcceptor(Authenticator authenticator,
                                       URIRequestInfo info) {
        _authenticator = authenticator;
        _uri = info.getURI();
    }

    /**
     * Start accepting connections.
     *
     * @param listener the listener to delegate accepted connections to
     * @throws ResourceException if connections cannot be accepted
     */
    public synchronized void accept(ManagedConnectionAcceptorListener listener)
            throws ResourceException {
        if (listener == null) {
            throw new IllegalArgumentException("Argument 'listener' is null");
        }
        if (_listener != null) {
            throw new ResourceException(
                    "Acceptor already accepting connections at URI=" + _uri);
        }
        synchronized (_acceptors) {
            if (_acceptors.containsKey(_uri)) {
                throw new ResourceException("Cannot accept connections on URI="
                                            + _uri
                                            + ". Address in use.");
            }
            _acceptors.put(_uri, this);
        }
        _listener = listener;

        if (_log.isDebugEnabled()) {
            _log.debug("VM connector accepting requests at URI=" + _uri);
        }
    }

    /**
     * Stop accepting connection requests, and clean up any allocated
     * resources.
     */
    public synchronized void close() {
        if (_listener != null) {
            synchronized (_acceptors) {
                _acceptors.remove(_uri);
            }
        }
        _listener = null;
    }

    /**
     * Returns the URI that this acceptor is accepting connections on.
     *
     * @return the URI that this acceptor is accepting connections on
     */
    public URI getURI() {
        return _uri;
    }

    /**
     * Create a new <code>ManagedConnection>/code>.
     *
     * @param principal the security principal
     * @param info      the connection request info
     * @param client    the invoker for performing invocations back to the
     *                  client
     * @param uri       the URI representing the client
     * @return an invoker for delegating invocations from the client to the
     *         server ManagedConnection
     * @throws ConnectException  if the connection can't be accepted
     * @throws ResourceException for any error
     */
    protected static VMInvoker connect(Principal principal, URIRequestInfo info,
                                       VMInvoker client, URI uri)
            throws ResourceException {
        VMManagedConnectionAcceptor acceptor;
        URI acceptURI = info.getURI();
        synchronized (_acceptors) {
            acceptor = (VMManagedConnectionAcceptor) _acceptors.get(acceptURI);
        }
        if (acceptor == null) {
            throw new ConnectException("Connection refused, URI=" + acceptURI);
        }
        return acceptor.accept(principal, info, client, uri);
    }

    /**
     * Create a new <code>ManagedConnection</code>.
     *
     * @param principal the security principal
     * @param info      the connection request info
     * @param client    the invoker for performing invocations back to the
     *                  client
     * @param uri       the URI representing the client
     * @return an invoker for delegating invocations from the client to the
     *         server ManagedConnection
     * @throws ConnectException  if the connection can't be accepted
     * @throws ResourceException for any error
     */
    protected VMInvoker accept(Principal principal, URIRequestInfo info,
                               VMInvoker client, URI uri)
            throws ResourceException {
        if (!_authenticator.authenticate(principal)) {
            throw new SecurityException("Failed to authenticate: " + principal);
        }
        VMManagedConnection connection =
                new VMManagedConnection(principal, info, client, uri);
        VMInvoker invoker = new VMInvoker(connection);
        ManagedConnectionAcceptorListener listener;
        synchronized (this) {
            listener = _listener;
        }
        if (listener == null) {
            throw new ConnectException("Connection refused, URI=" + _uri);
        }
        listener.accepted(this, connection);
        return invoker;
    }
}
