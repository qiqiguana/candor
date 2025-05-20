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
 * $Id: ObjectRef.java,v 1.2 2005/11/16 12:32:49 tanderson Exp $
 */
package org.exolab.jms.net.orb;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.rmi.NoSuchObjectException;
import java.rmi.server.ExportException;
import java.rmi.server.ObjID;
import java.util.HashMap;

import org.exolab.jms.net.proxy.Delegate;
import org.exolab.jms.net.proxy.Proxy;
import org.exolab.jms.net.uri.URI;


/**
 * Maintains state information for an exported object.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/11/16 12:32:49 $
 */
class ObjectRef {

    /**
     * The identity of the object.
     */
    private ObjID _objID;

    /**
     * The exported object.
     */
    private Object _object;

    /**
     * The proxy class of the exported object, implementing the {@link Proxy}
     * interface.
     */
    private Class _proxyClass;

    /**
     * The set of {@link UnicastDelegate} instances, keyed on URI.
     */
    private HashMap _proxies = new HashMap();

    /**
     * Helper for Proxy constructor resolution.
     */
    private static final Class[] PROXY_ARGS = new Class[]{Delegate.class};

    /**
     * Construct a new <code>ObjectRef</code>.
     *
     * @param objID      the object identity
     * @param object     the exported object
     * @param proxyClass the proxy class of the exported object, implementing
     *                   the {@link Proxy} interface
     */
    public ObjectRef(ObjID objID, Object object, Class proxyClass) {
        _objID = objID;
        _object = object;
        _proxyClass = proxyClass;
    }

    /**
     * Returns the identity of the exported object.
     *
     * @return the object identity
     */
    public ObjID getObjID() {
        return _objID;
    }

    /**
     * Returns the exported object.
     *
     * @return the exported object
     */
    public Object getObject() {
        return _object;
    }

    /**
     * Returns the proxy class of the exported object. This implements the
     * {@link Proxy} interface.
     *
     * @return the proxy class
     */
    public Class getProxyClass() {
        return _proxyClass;
    }

    /**
     * Add a proxy for the object.
     *
     * @param uri the connection URI
     * @return a proxy for the object
     * @throws ExportException if the proxy can't be constructed
     */
    public synchronized Proxy addProxy(URI uri) throws ExportException {
        Proxy proxy;
        try {
            Delegate delegate = new UnicastDelegate(_objID, uri.toString());
            Constructor constructor = _proxyClass.getConstructor(PROXY_ARGS);
            proxy = (Proxy) constructor.newInstance(new Object[]{delegate});
        } catch (InvocationTargetException exception) {
            if (exception.getTargetException() instanceof Exception) {
                Exception nested = (Exception) exception.getTargetException();
                throw new ExportException(nested.getMessage(), nested);
            } else {
                throw new ExportException(exception.getMessage(), exception);
            }
        } catch (Exception exception) {
            throw new ExportException(exception.getMessage(), exception);
        }
        _proxies.put(uri, proxy);
        return proxy;
    }

    /**
     * Returns the proxy for the specified URI.
     *
     * @param uri the connection URI
     * @return a proxy for the object
     * @throws NoSuchObjectException if the object isn't exported on the
     *                               specified URI
     */
    public synchronized Proxy getProxy(URI uri)
            throws NoSuchObjectException {

        Proxy proxy = (Proxy) _proxies.get(uri);
        if (proxy == null) {
            throw new NoSuchObjectException(
                    "Object not exported on URI=" + uri);
        }
        return proxy;
    }

    /**
     * Returns a list of URIs that the object is exported on.
     *
     * @return a list of URIs that the object is exported on
     */
    public synchronized URI[] getURIs() {
        return (URI[]) _proxies.keySet().toArray(new URI[0]);
    }

    /**
     * Determines if this equals another object.
     *
     * @param object the object to compare
     * @return <code>true</code> if this is equal, otherwise <code>false</code>
     */
    public boolean equals(Object object) {
        boolean equal = (this == object);
        if (!equal && (object instanceof ObjectRef)) {
            equal = _objID.equals(((ObjectRef) object)._objID);
        }
        return equal;
    }

    /**
     * Returns the hash code of this.
     *
     * @return the hash code of this
     */
    public int hashCode() {
        return _objID.hashCode();
    }

}
