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
 * $Id: AbstractORB.java,v 1.8 2005/11/18 03:25:51 tanderson Exp $
 */
package org.exolab.jms.net.orb;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.StubNotFoundException;
import java.rmi.server.ExportException;
import java.rmi.server.ObjID;
import java.util.HashMap;
import java.util.Map;

import org.exolab.jms.net.proxy.Proxy;
import org.exolab.jms.net.uri.InvalidURIException;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.uri.URIHelper;


/**
 * Abstract implementation of the {@link ORB} interface.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.8 $ $Date: 2005/11/18 03:25:51 $
 */
public abstract class AbstractORB implements ORB {

    /**
     * A map of ObjID -> ObjectRef instances.
     */
    private HashMap _objIDMap = new HashMap();

    /**
     * A map of Object -> ObjectRef instances.
     */
    private HashMap _objectMap = new HashMap();

    /**
     * Configuration properties.
     */
    private final Map _properties;

    /**
     * The default URI for exported objects.
     */
    private final String _defaultURI;

    /**
     * A map of routes for exported objects. The key is a URI representing the
     * URI the objects are listening on. The value is the URI of the router.
     */
    private HashMap _routes = new HashMap();

    /**
     * The class loader used to load proxies.
     */
    private ClassLoader _loader;


    /**
     * Construct a new <code>AbstractORB</code>.
     *
     * @param loader the class loader to load proxies
     * @param properties properties to configure this with. May be
     * <code>null</code>.
     */
    public AbstractORB(ClassLoader loader, Map properties) {
        if (loader == null) {
            throw new IllegalArgumentException("Argument 'loader' is null");
        }
        _loader = loader;
        if (properties != null) {
            _properties = properties;
            _defaultURI = (String) properties.get(PROVIDER_URI);
        } else {
            _properties = new HashMap();
            _defaultURI = null;
        }
    }

    /**
     * Add a route for exported objects.
     *
     * @param uri   the URI to route
     * @param toURI the URI to route to
     * @throws RemoteException for any error
     */
    public synchronized void addRoute(String uri, String toURI)
            throws RemoteException {
        if (uri == null) {
            throw new IllegalArgumentException("Argument 'uri' is null");
        }
        if (toURI == null) {
            throw new IllegalArgumentException("Argument 'toURI' is null");
        }
        _routes.put(URIHelper.parse(uri), URIHelper.parse(toURI));
    }

    /**
     * Returns the proxy associated with the specified object, and URI.
     *
     * @param object the object to look up the proxy for
     * @param uri    the URI the object was exported on
     * @return the proxy corresponding to <code>object</code> and
     *         <code>uri</code>
     * @throws NoSuchObjectException if the object hasn't been exported on the
     *                               specified URI
     */
    public synchronized Proxy getProxy(Object object, String uri)
            throws NoSuchObjectException {

//         if (uri == null) {
//             throw new IllegalArgumentException("Argument 'uri' is null");
//         }
        ObjectRef ref = (ObjectRef) _objectMap.get(object);
        if (ref == null) {
            throw new NoSuchObjectException("Object not exported");
        }
        URI parsed = null;
        if (uri != null) {
            try {
                parsed = URIHelper.parse(uri);
            } catch (InvalidURIException exception) {
                throw new NoSuchObjectException(exception.getMessage());
            }
        }
        return ref.getProxy(parsed);
    }

    /**
     * Returns the object associated with the specified ID, and URI.
     *
     * @param objID the identifier of the object
     * @param uri   the URI the object was exported on
     * @return the object corresponding to <code>objID</code> and
     *         <code>uri</code>
     * @throws NoSuchObjectException if the object hasn't been exported on the
     *                               specified URI
     */
    public synchronized Object getObject(ObjID objID, String uri)
            throws NoSuchObjectException {

//         if (uri == null) {
//             throw new IllegalArgumentException("Argument 'uri' is null");
//         }
        ObjectRef ref = (ObjectRef) _objIDMap.get(objID);
        if (ref == null) {
            throw new NoSuchObjectException("Object not exported");
        }
        // ref.getProxy(uri);
        // ensures it has been exported on the specified uri
        return ref.getObject();
    }

    /**
     * Export an object on a default URI.
     *
     * @param object the object to export
     * @return a proxy which may be used to invoke methods on the object
     * @throws ExportException       if the object cannot be exported
     * @throws StubNotFoundException if the proxy class cannot be found
     */
    public Proxy exportObject(Object object)
            throws ExportException, StubNotFoundException {
        return exportObject(object, _defaultURI);
    }

    /**
     * Export an object on a specific URI.
     *
     * @param object the object to export
     * @param uri    the URI via which connections to the object are made
     * @return a proxy which may be used to invoke methods on the object
     * @throws ExportException       if the object cannot be exported
     * @throws StubNotFoundException if the proxy class cannot be found
     */
    public synchronized Proxy exportObject(Object object, String uri)
            throws ExportException, StubNotFoundException {

        if (object == null) {
            throw new IllegalArgumentException("Argument 'object' is null");
        }
        if (uri == null) {
            throw new IllegalArgumentException("Argument 'uri' is null");
        }

        URI parsed = null;
        try {
            parsed = URIHelper.parse(uri);
        } catch (InvalidURIException exception) {
            throw new ExportException(exception.getMessage(), exception);
        }

        Proxy proxy = null;
        ObjectRef ref = (ObjectRef) _objectMap.get(object);
        if (ref != null) {
            proxy = addProxy(ref, parsed, object, ref.getProxyClass());
        } else {
            ObjID objID = new ObjID();
            proxy = doExport(object, objID, parsed, getProxyClass(object));
        }

        return proxy;
    }

    /**
     * Export an object with a well known identifier on a default URI.
     *
     * @param object the object to export
     * @param objID  the well known object identifier
     * @return a proxy which may be used to invoke methods on the object
     * @throws ExportException       if the object cannot be exported
     * @throws StubNotFoundException if the proxy class cannot be found
     */
    public Proxy exportObject(Object object, ObjID objID)
            throws ExportException, StubNotFoundException {
        return exportObject(object, objID, _defaultURI);
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
    public synchronized Proxy exportObject(Object object, ObjID objID,
                                           String uri)
            throws ExportException, StubNotFoundException {

        if (object == null) {
            throw new IllegalArgumentException("Argument 'object' is null");
        }
        if (objID == null) {
            throw new IllegalArgumentException("Argument 'objID' is null");
        }
        if (uri == null) {
            throw new IllegalArgumentException("Argument 'uri' is null");
        }
        URI parsed = null;
        try {
            parsed = URIHelper.parse(uri);
        } catch (InvalidURIException exception) {
            throw new ExportException(exception.getMessage(), exception);

        }
        Proxy proxy = null;
        ObjectRef ref = (ObjectRef) _objectMap.get(object);
        if (ref != null) {
            proxy = addProxy(ref, parsed, object, ref.getProxyClass());
        } else {
            proxy = doExport(object, objID, parsed, getProxyClass(object));
        }
        return proxy;
    }

    /**
     * Export an object to a specific URI.
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
        return exportObjectTo(object, uri, null, null);
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
        if (object == null) {
            throw new IllegalArgumentException("Argument 'object' is null");
        }
        if (uri == null) {
            throw new IllegalArgumentException("Argument 'uri' is null");
        }
        URI remoteURI = null;
        URI localURI = null;
        try {
            remoteURI = URIHelper.parse(uri);
        } catch (InvalidURIException exception) {
            throw new ExportException(exception.getMessage(), exception);
        }

        localURI = connect(remoteURI, principal, credentials);

        return doExportTo(object, localURI);
    }

    /**
     * Unexport an object.
     *
     * @param object the object to export
     * @throws NoSuchObjectException if the object isn't exported
     */
    public synchronized void unexportObject(Object object)
            throws NoSuchObjectException {

        ObjectRef ref = (ObjectRef) _objectMap.remove(object);
        if (ref != null) {
            _objIDMap.remove(ref.getObjID());
        } else {
            throw new NoSuchObjectException("Object not exported");
        }
    }

    /**
     * Connect to the specified URI.
     *
     * @param uri         the URI to establish a connection with
     * @param principal   specifies the identity of the principal. If
     *                    <code>null</code>, indicates to connect anonymously.
     * @param credentials the credentials of the principal
     * @return the local address that the connection is bound to
     * @throws ExportException for any error
     */
    protected abstract URI connect(URI uri, String principal,
                                   String credentials) throws ExportException;

    /**
     * Accept connections on the specified URI.
     *
     * @param uri the URI to accept connections on
     * @throws ExportException for any error
     */
    protected abstract void accept(URI uri) throws ExportException;

    /**
     * Returns the proxy class loader.
     *
     * @return the proxy class loader
     */
    protected ClassLoader getProxyClassLoader() {
        return _loader;
    }

    /**
     * Returns the configuration properties.
     *
     * @return the configuration properties
     */
    protected Map getProperties() {
        return _properties;
    }

    /**
     * Export an object to a specific URI.
     * 
     * @param object the object to export
     * @param uri the URI via which connections to the object are made
     * @throws ExportException if the object cannot be exported
     * @throws StubNotFoundException if the proxy class cannot be found
     */ 
    protected Proxy doExportTo(Object object, URI uri)
            throws ExportException, StubNotFoundException {
        Proxy proxy = null;
        ObjectRef ref = (ObjectRef) _objectMap.get(object);
        if (ref != null) {
            proxy = addProxyTo(ref, uri, object, ref.getProxyClass());
        } else {
            ObjID objID = new ObjID();
            proxy = doExportTo(object, objID, uri, getProxyClass(object));
        }

        return proxy;
    }

    /**
     * Returns the no. of currently exported objects.
     *
     * @return the no. of exported objects
     */
    protected int getExported() {
        return _objectMap.size();
    }

    /**
     * Export an object on a specific URI.
     *
     * @param object     the object to export
     * @param objID      the identifier of the object
     * @param uri        the URI via which connections to the object are made
     * @param proxyClass the proxy class
     * @return a proxy which may be used to invoke methods on the object
     * @throws ExportException if the object cannot be exported
     */
    private Proxy doExport(Object object, ObjID objID, URI uri,
                           Class proxyClass) throws ExportException {
        accept(uri);
        ObjectRef ref = new ObjectRef(objID, object, proxyClass);
        Proxy proxy = ref.addProxy(getRoute(uri));
        _objIDMap.put(objID, ref);
        _objectMap.put(object, ref);
        return proxy;
    }
    
    /**
     * Export an object to a specific URI.
     *
     * @param object     the object to export
     * @param objID      the identifier of the object
     * @param uri        the URI via which connections to the object are made
     * @param proxyClass the proxy class
     * @return a proxy which may be used to invoke methods on the object
     * @throws ExportException if the object cannot be exported
     */
    private Proxy doExportTo(Object object, ObjID objID, URI uri,
                             Class proxyClass) throws ExportException {
        ObjectRef ref = new ObjectRef(objID, object, proxyClass);
        Proxy proxy = ref.addProxy(getRoute(uri));
        _objIDMap.put(objID, ref);
        _objectMap.put(object, ref);
        return proxy;
    }

    /**
     * Add a proxy for an exported object.
     *
     * @param ref        a reference to the exported object
     * @param uri        the URI via which connections to the object are made
     * @param object     the exported object
     * @param proxyClass the proxy class
     * @return a proxy which may be used to invoke methods on the object
     * @throws ExportException if the object cannot be exported
     */
    private Proxy addProxy(ObjectRef ref, URI uri, Object object,
                           Class proxyClass) throws ExportException {

        if (object != ref.getObject()) {
            throw new ExportException("Cannot export object on URI=" + uri
                                      + ": object mismatch");
        }
        if (proxyClass != ref.getProxyClass()) {
            throw new ExportException("Cannot export object on URI=" + uri
                                      + ": proxy class mismatch");
        }

        accept(uri);
        return ref.addProxy(getRoute(uri));
    }

    /**
     * Add a proxy for an exported object.
     *
     * @param ref        a reference to the exported object
     * @param uri        the URI via which connections to the object are made
     * @param object     the exported object
     * @param proxyClass the proxy class
     * @return a proxy which may be used to invoke methods on the object
     * @throws ExportException if the object cannot be exported
     */
    private Proxy addProxyTo(ObjectRef ref, URI uri, Object object,
                             Class proxyClass) throws ExportException {

        if (object != ref.getObject()) {
            throw new ExportException("Cannot export object on URI=" + uri
                                      + ": object mismatch");
        }
        if (proxyClass != ref.getProxyClass()) {
            throw new ExportException("Cannot export object on URI=" + uri
                                      + ": proxy class mismatch");
        }

        return ref.addProxy(uri);
    }

    /**
     * Loads the proxy class for the supplied object.
     *
     * @param object the object to load the proxy for
     * @return the proxy class corresponding to <code>object</code>
     * @throws StubNotFoundException if the proxy class cannot be loaded
     */
    private Class getProxyClass(Object object) throws StubNotFoundException {
        return getProxyClass(object.getClass());
    }

    /**
     * Loads the proxy class for the supplied class.
     *
     * @param clazz the class to load the proxy for
     * @return the proxy class corresponding to <code>class</code>
     * @throws StubNotFoundException if the proxy class cannot be loaded
     */
    private Class getProxyClass(Class clazz) throws StubNotFoundException {
        String proxyName = clazz.getName() + "__Proxy";
        Class proxyClass = null;
        try {
            proxyClass = _loader.loadClass(proxyName);
            if (!Proxy.class.isAssignableFrom(proxyClass)) {
                throw new StubNotFoundException(proxyName);
            }
        } catch (ClassNotFoundException exception) {
            Class superClass = clazz.getSuperclass();
            if (superClass != null && !superClass.isInterface()) {
                proxyClass = getProxyClass(superClass);
            } else {
                throw new StubNotFoundException(proxyName);
            }
        }
        return proxyClass;
    }

    /**
     * Returns the route address of a URI.
     *
     * @param uri the URI
     * @return the route address of <code>uri</code>, or <code>uri</code> if it
     *         isn't routed
     */
    private URI getRoute(URI uri) {
        URI result = (URI) _routes.get(uri);
        return (result == null) ? uri : result;
    }

}
