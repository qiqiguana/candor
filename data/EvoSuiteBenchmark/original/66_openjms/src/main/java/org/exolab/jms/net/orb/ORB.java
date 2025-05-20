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
 * $Id: ORB.java,v 1.6 2008/01/07 13:00:16 tanderson Exp $
 */
package org.exolab.jms.net.orb;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.StubNotFoundException;
import java.rmi.server.ExportException;
import java.rmi.server.ObjID;
import java.util.Map;

import org.exolab.jms.net.connector.CallerListener;
import org.exolab.jms.net.connector.Caller;
import org.exolab.jms.net.proxy.Proxy;
import org.exolab.jms.net.registry.LocalRegistry;
import org.exolab.jms.net.registry.Registry;


/**
 * The <code>ORB</code> enables objects to be distributed.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.6 $ $Date: 2008/01/07 13:00:16 $
 */
public interface ORB {

    /**
     * Constant that holds the name of the connection property for specifying
     * the ORB provider URI.
     */
    final String PROVIDER_URI
            = "org.exolab.jms.net.orb.provider.uri";

    /**
     * Constant that holds the name of the connection property for specifying
     * the identity of the principal for authenticating the caller to the ORB.
     * The format of the principal depends on the authentication scheme.
     */
    final String SECURITY_PRINCIPAL
            = "org.exolab.jms.net.orb.security.principal";

    /**
     * Constant that holds the name of the connection property for specifying
     * the credentials of the principal for authenticating the caller to the
     * ORB. The value of the property depends on the authentication scheme.
     */
    final String SECURITY_CREDENTIALS
            = "org.exolab.jms.net.orb.security.credentials";

    /**
     * Add a route for exported objects.
     *
     * @param uri   the URI to route
     * @param toURI the URI to route to
     * @throws RemoteException for any error
     */
    void addRoute(String uri, String toURI) throws RemoteException;

    /**
     * Returns a reference to the registry service.
     *
     * @return the registry service
     * @throws RemoteException if the service cannot be exported
     */
    LocalRegistry getRegistry() throws RemoteException;

    /**
     * Returns a reference to a remote registry service.
     *
     * @param properties the connection properties. May be <code>null</code>.
     * @return the registry service
     * @throws RemoteException for any error
     */
    Registry getRegistry(Map properties) throws RemoteException;

    /**
     * Export an object on the default URI.
     *
     * @param object the object to export
     * @return a proxy which may be used to invoke methods on the object
     * @throws ExportException       if the object cannot be exported
     * @throws StubNotFoundException if the proxy class cannot be found
     */
    Proxy exportObject(Object object)
            throws ExportException, StubNotFoundException;

    /**
     * Export an object on a specific URI.
     *
     * @param object the object to export
     * @param uri    the URI via which connections to the object are made. If
     *               <code>null</code>, the default URI is used.
     * @return a proxy which may be used to invoke methods on the object
     * @throws ExportException       if the object cannot be exported
     * @throws StubNotFoundException if the proxy class cannot be found
     */
    Proxy exportObject(Object object, String uri)
            throws ExportException, StubNotFoundException;

    /**
     * Export an object with a well known identifier on the default URI.
     *
     * @param object the object to export
     * @param objID  the well known object identifier
     * @return a proxy which may be used to invoke methods on the object
     * @throws ExportException       if the object cannot be exported
     * @throws StubNotFoundException if the proxy class cannot be found
     */
    Proxy exportObject(Object object, ObjID objID)
            throws ExportException, StubNotFoundException;

    /**
     * Export an object with a well known identifier on a specific URI.
     *
     * @param object the object to export
     * @param objID  the well known object identifier
     * @param uri    the URI via which connections to the object are made
     * @return a proxy which may be used to invoke methods on the object
     * @throws ExportException       if the object cannot be exported
     * @throws StubNotFoundException if the proxy class cannot be found
     */
    Proxy exportObject(Object object, ObjID objID, String uri)
            throws ExportException, StubNotFoundException;

    /**
     * Export an object to the current remote caller. Only the remote caller may
     * perform invocations.
     *
     * @param object the object to export
     * @return a proxy which may be used to invoke methods on the object
     * @throws ExportException       if the object cannot be exported
     * @throws StubNotFoundException if the proxy class cannot be found
     */
    Proxy exportObjectTo(Object object)
            throws ExportException, StubNotFoundException;

    /**
     * Export an object to a specific URI. Only callers from the target URI may
     * perform invocations.
     *
     * @param object the object to export
     * @param uri    the target URI from which connections to the object are
     *               made.
     * @return a proxy which may be used to invoke methods on the object
     * @throws ExportException       if the object cannot be exported
     * @throws StubNotFoundException if the proxy class cannot be found
     */
    Proxy exportObjectTo(Object object, String uri)
            throws ExportException, StubNotFoundException;

    /**
     * Export an object to a specific URI. Only callers from the target URI may
     * perform invocations.
     *
     * @param object      the object to export
     * @param uri         the target URI from which connections to the object
     *                    are made.
     * @param principal   the security principal. May be <code>null</code>
     * @param credentials the security credentials. May be <code>null</code>
     * @return a proxy which may be used to invoke methods on the object
     * @throws ExportException       if the object cannot be exported
     * @throws StubNotFoundException if the proxy class cannot be found
     */
    Proxy exportObjectTo(Object object, String uri, String principal,
                         String credentials)
            throws ExportException, StubNotFoundException;

    /**
     * Unexport an object.
     *
     * @param object the object to export
     * @throws NoSuchObjectException if the object isn't exported
     */
    void unexportObject(Object object) throws NoSuchObjectException;

    /**
     * Returns the current caller.
     *
     * @return the current caller, or <code>null</code> if no call is in
     * progress
     * @throws RemoteException for any error
     */
    Caller getCaller() throws RemoteException;

    /**
     * Registers a caller event listener.
     *
     * @param listener the listener to notify
     * @throws RemoteException for any error
     */
    void addCallerListener(CallerListener listener) throws RemoteException;

    /**
     * Register a caller event listener.
     *
     * @param uri      the remote URI to listen on
     * @param listener the listener to notify
     * @throws RemoteException for any error
     */
    void addCallerListener(String uri, CallerListener listener)
            throws RemoteException;

    /**
     * Deregisters a caller event listener.
     *
     * @param listener the listener to remove
     * @throws RemoteException for any error
     */
    void removeCallerListener(CallerListener listener) throws RemoteException;

    /**
     * Deregister a caller event listener.
     *
     * @param uri      the remote URI the listener is listening for events on
     * @param listener the listener to remove
     * @throws RemoteException for any error
     */
    void removeCallerListener(String uri, CallerListener listener)
            throws RemoteException;

    /**
     * Shuts down the ORB.
     *
     * @throws RemoteException for any error
     */
    void shutdown() throws RemoteException;

}
