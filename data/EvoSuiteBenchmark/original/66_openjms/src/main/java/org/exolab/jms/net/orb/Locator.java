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
 * $Id: Locator.java,v 1.3 2005/05/24 05:55:18 tanderson Exp $
 */
package org.exolab.jms.net.orb;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.rmi.ConnectIOException;
import java.rmi.RemoteException;
import java.rmi.StubNotFoundException;
import java.rmi.AccessException;
import java.rmi.server.ObjID;
import java.security.Principal;
import java.util.Map;

import org.exolab.jms.net.connector.Connection;
import org.exolab.jms.net.connector.ConnectionFactory;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.connector.SecurityException;
import org.exolab.jms.net.proxy.Delegate;
import org.exolab.jms.net.proxy.Proxy;
import org.exolab.jms.net.registry.Registry;
import org.exolab.jms.net.uri.InvalidURIException;
import org.exolab.jms.net.uri.URIHelper;


/**
 * Helper class for constructing proxies for exported objects with a known
 * identifier.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/05/24 05:55:18 $
 */
final class Locator {

    /**
     * Prevent construction of helper class.
     */
    private Locator() {
    }

    /**
     * Returns a proxy for a remote {@link Registry}.
     *
     * @param principal  the security principal. May be <code>null</code>
     * @param uri        the connection URI
     * @param factory    the connection factory
     * @param loader     the loader for the proxy class
     * @param properties the connection properties. May be <code>null</code>.
     * @return a proxy for the registry exported at <code>uri</code>
     * @throws InvalidURIException if <code>uri</code> is invalid
     * @throws RemoteException     if the object cannot be located
     */
    public static Registry getRegistry(Principal principal, String uri,
                                       ConnectionFactory factory,
                                       ClassLoader loader, Map properties)
            throws InvalidURIException, RemoteException {

        ObjID objId = new ObjID(ObjID.REGISTRY_ID);
        String className = RegistryImpl.PROXY;

        return (Registry) getProxy(objId, principal, uri, factory, className,
                                   loader, properties);
    }

    /**
     * Returns a proxy for the specified object.
     *
     * @param objId      the object's identifier
     * @param principal  the security principal
     * @param uri        the connection URI
     * @param factory    the connection factory
     * @param className  the proxy class name
     * @param loader     the loader for the proxy class
     * @param properties the connection properties. May be <code>null</code>.
     * @return a proxy for <code>objId</code> exported at <code>uri</code>
     * @throws InvalidURIException if <code>uri</code> is invalid
     * @throws RemoteException     if the object cannot be located
     */
    public static Proxy getProxy(ObjID objId, Principal principal, String uri,
                                 ConnectionFactory factory,
                                 String className, ClassLoader loader,
                                 Map properties)
            throws InvalidURIException, RemoteException {

        Proxy proxy;

        Connection connection;
        try {
            connection = factory.getConnection(principal,
                                               URIHelper.parse(uri),
                                               properties);
        } catch (SecurityException exception) {
            throw new AccessException(exception.getMessage(), exception);
        } catch (ResourceException exception) {
            throw new ConnectIOException("Failed to create connection",
                                         exception);
        }

        UnicastDelegate delegate = new UnicastDelegate(objId, connection);

        try {
            Class proxyClass = loader.loadClass(className);
            Constructor constructor = proxyClass.getConstructor(
                    new Class[]{Delegate.class});
            proxy = (Proxy) constructor.newInstance(new Object[]{delegate});
        } catch (ClassNotFoundException exception) {
            throw new StubNotFoundException(exception.getMessage(), exception);
        } catch (IllegalAccessException exception) {
            throw new RemoteException(exception.getMessage(), exception);
        } catch (InstantiationException exception) {
            throw new RemoteException(exception.getMessage(), exception);
        } catch (InvocationTargetException exception) {
            // unwrap the target exception, if non-null
            Throwable target = exception.getTargetException();
            if (target != null) {
                throw new RemoteException(exception.getMessage(), target);
            } else {
                throw new RemoteException(exception.getMessage(), exception);
            }
        } catch (NoSuchMethodException exception) {
            throw new RemoteException(exception.getMessage(), exception);
        }
        return proxy;
    }

}
