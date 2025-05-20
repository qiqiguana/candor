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
 * $Id: RMIManagedConnectionAcceptor.java,v 1.4 2006/12/16 12:37:17 tanderson Exp $
 */
package org.exolab.jms.net.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.net.connector.ManagedConnectionAcceptor;
import org.exolab.jms.net.connector.ManagedConnectionAcceptorListener;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.uri.URIHelper;


/**
 * <code>RMIManagedConnectionAcceptor</code> is responsible for accepting
 * connections, and constructing new <code>RMIManagedConnection</code> instances
 * to serve them.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2006/12/16 12:37:17 $
 */
class RMIManagedConnectionAcceptor implements ManagedConnectionAcceptor {

    /**
     * The connection authenticator.
     */
    private final Authenticator _authenticator;

    /**
     * The URI of this acceptor.
     */
    private final URI _uri;

    /**
     * Determines if the registry is embedded or not.
     */
    private final boolean _embedRegistry;

    /**
     * The registry.
     */
    private Registry _registry;

    /**
     * Determines if the registry was created by this.
     */
    private boolean _created = false;

    /**
     * The factory for invokers.
     */
    private RMIInvokerFactory _factory;

    /**
     * The logger.
     */
    private static final Log _log =
            LogFactory.getLog(RMIManagedConnectionAcceptor.class);


    /**
     * Construct a new <code>RMIManagedConnectionAcceptor</code>.
     *
     * @param authenticator the connection authenticator
     * @param info          the connection request info
     */
    public RMIManagedConnectionAcceptor(Authenticator authenticator,
                                        RMIRequestInfo info) {
        _authenticator = authenticator;
        _uri = URIHelper.convertHostToAddress(info.getURI());        
        _embedRegistry = info.getEmbedRegistry();
    }

    /**
     * Start accepting connections.
     *
     * @param listener the listener to delegate accepted connections to
     * @throws ResourceException if connections cannot be accepted
     */
    public void accept(ManagedConnectionAcceptorListener listener)
            throws ResourceException {
        Registry registry = null;

        int port = _uri.getPort();
        if (_embedRegistry) {
            try {
                registry = LocateRegistry.createRegistry(port);
                _created = true;
            } catch (RemoteException exception) {
                if (_log.isDebugEnabled()) {
                    _log.debug("Failed to create registry on port=" + port
                               + ", attempting to locate one", exception);
                }
            }
        }
        if (registry == null) {
            try {
                registry = LocateRegistry.getRegistry(port);
                port = 0;
            } catch (RemoteException nested) {
                throw new ResourceException(
                        "Failed to create or locate a registry, port=" + port,
                        nested);
            }
        }

        _factory = new RMIInvokerFactoryImpl(_authenticator, this, listener);

        try {
            UnicastRemoteObject.exportObject(_factory, port);
        } catch (RemoteException exception) {
            throw new ResourceException("Failed to export object", exception);
        }

        RegistryHelper.bind(_factory, _uri, registry);
        _registry = registry;
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
     * Stop accepting connection requests, and clean up any allocated resources.
     *
     * @throws ResourceException if the acceptor cannot be closed
     */
    public synchronized void close() throws ResourceException {
        if (_registry != null) {
            try {
                RegistryHelper.unbind(_factory, _uri, _registry);
                if (!UnicastRemoteObject.unexportObject(_factory, true)) {
                    _log.warn("Failed to unexport invoker factory");
                }

                if (_created && !RegistryHelper.hasBindings(_registry)) {
                    // if this created the registry, and there are no
                    // other bindings, unexport the registry

                    if (!UnicastRemoteObject.unexportObject(_registry, true)) {
                        _log.warn("Failed to unexport registry");
                    }
                }
            } catch (RemoteException exception) {
                throw new ResourceException(
                        "Failed to close connection acceptor", exception);
            } finally {
                _factory = null;
                _registry = null;
            }
        }
    }

}
