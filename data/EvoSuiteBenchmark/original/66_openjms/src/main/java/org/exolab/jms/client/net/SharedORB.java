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
 * $Id: SharedORB.java,v 1.6 2005/05/27 14:04:01 tanderson Exp $
 */
package org.exolab.jms.client.net;

import java.util.Map;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.StubNotFoundException;
import java.rmi.server.ExportException;
import java.rmi.server.ObjID;

import org.exolab.jms.net.connector.CallerListener;
import org.exolab.jms.net.connector.Caller;
import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.orb.ORBFactory;
import org.exolab.jms.net.proxy.Proxy;
import org.exolab.jms.net.registry.LocalRegistry;
import org.exolab.jms.net.registry.Registry;


/**
 * Shared {@link ORB} instance.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.6 $ $Date: 2005/05/27 14:04:01 $
 */
public class SharedORB implements ORB {

    /**
     * The ORB to delegate requests to.
     */
    private final ORB _orb;

    /**
     * The singleton instance.
     */
    private static org.exolab.jms.net.orb.ORB _instance;


    /**
     * Construct a new <code>SharedORB</code>.
     *
     * @throws RemoteException for any error
     */
    private SharedORB() throws RemoteException {
        _orb = ORBFactory.createORB();
    }

    /**
     * Add a route for exported objects.
     *
     * @param uri   the URI to route
     * @param toURI the URI to route to
     * @throws RemoteException for any error
     */
    public void addRoute(String uri, String toURI) throws RemoteException {
        _orb.addRoute(uri, toURI);
    }

    /**
     * Returns a reference to the registry service.
     *
     * @return the registry service
     * @throws RemoteException if the service cannot be exported
     */
    public LocalRegistry getRegistry() throws RemoteException {
        return _orb.getRegistry();
    }

    /**
     * Returns a reference to a remote registry service.
     *
     * @param properties the connection properties. May be <code>null</code>.
     * @return the registry service
     * @throws RemoteException for any error
     */
    public Registry getRegistry(Map properties) throws RemoteException {
        return _orb.getRegistry(properties);
    }

    /**
     * Export an object on the default URI.
     *
     * @param object the object to export
     * @return a proxy which may be used to invoke methods on the object
     * @throws ExportException       if the object cannot be exported
     * @throws StubNotFoundException if the proxy class cannot be found
     */
    public Proxy exportObject(Object object)
            throws ExportException, StubNotFoundException {
        return _orb.exportObject(object);
    }

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
    public Proxy exportObject(Object object, String uri)
            throws ExportException, StubNotFoundException {
        return _orb.exportObject(object, uri);
    }

    /**
     * Export an object with a well known identifier on the default URI.
     *
     * @param object the object to export
     * @param objID  the well known object identifier
     * @return a proxy which may be used to invoke methods on the object
     * @throws ExportException       if the object cannot be exported
     * @throws StubNotFoundException if the proxy class cannot be found
     */
    public Proxy exportObject(Object object, ObjID objID)
            throws ExportException, StubNotFoundException {
        return _orb.exportObject(object, objID);
    }

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
    public Proxy exportObject(Object object, ObjID objID, String uri)
            throws ExportException, StubNotFoundException {
        return _orb.exportObject(object, objID, uri);
    }

    /**
     * Export an object to the current remote caller. Only the remote caller may
     * perform invocations.
     *
     * @param object the object to export
     * @return a proxy which may be used to invoke methods on the object
     * @throws ExportException       if the object cannot be exported
     * @throws StubNotFoundException if the proxy class cannot be found
     */
    public Proxy exportObjectTo(Object object) throws ExportException,
            StubNotFoundException {
        return _orb.exportObjectTo(object);
    }

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
    public Proxy exportObjectTo(Object object, String uri)
            throws ExportException, StubNotFoundException {
        return _orb.exportObjectTo(object, uri);
    }

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
    public Proxy exportObjectTo(Object object, String uri, String principal,
                                String credentials)
            throws ExportException, StubNotFoundException {
        return _orb.exportObjectTo(object, uri, principal, credentials);
    }

    /**
     * Unexport an object.
     *
     * @param object the object to export
     * @throws NoSuchObjectException if the object isn't exported
     */
    public void unexportObject(Object object) throws NoSuchObjectException {
        _orb.unexportObject(object);
    }

    /**
     * Returns the current caller.
     *
     * @return the current caller, or <code>null</code> if no call is in
     *         progress
     * @throws RemoteException for any error
     */
    public Caller getCaller() throws RemoteException {
        return _orb.getCaller();
    }

    /**
     * Register a caller event listener.
     *
     * @param uri      the remote URI to listen on
     * @param listener the listener to notify
     * @throws RemoteException for any error
     */
    public void addCallerListener(String uri, CallerListener listener)
            throws RemoteException {
        _orb.addCallerListener(uri, listener);
    }

    /**
     * Deregister a caller event listener.
     *
     * @param uri      the remote URI the listener is listening for events on
     * @param listener the listener to remove
     * @throws RemoteException for any error
     */
    public void removeCallerListener(String uri, CallerListener listener)
            throws RemoteException {
        _orb.removeCallerListener(uri, listener);
    }
    
    public void addCallerListener(CallerListener listener) throws RemoteException {
      _orb.addCallerListener(listener);
    }
    public void removeCallerListener(CallerListener listener) throws RemoteException {
      _orb.removeCallerListener(listener);
    }

    /**
     * Shuts down the ORB.
     *
     * @throws RemoteException for any error
     */
    public void shutdown() throws RemoteException {
        _orb.shutdown();
    }

    /**
     * Returns the singleton ORB instance.
     *
     * @return the singleton ORB instance
     * @throws RemoteException for any error
     */
    public static synchronized ORB getInstance() throws RemoteException {
        if (_instance == null) {
            _instance = new SharedORB();
        }
        return _instance;
    }

}
