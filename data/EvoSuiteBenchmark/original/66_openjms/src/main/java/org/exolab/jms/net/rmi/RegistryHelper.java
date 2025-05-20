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
 * Copyright 2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: RegistryHelper.java,v 1.1 2004/11/26 01:51:06 tanderson Exp $
 */
package org.exolab.jms.net.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.uri.URI;


/**
 * Helper class for RMI registry operations
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2004/11/26 01:51:06 $
 */
final class RegistryHelper {

    /**
     * Bind suffix for invoker factories
     */
    private static final String BIND_SUFFIX =
            RMIInvokerFactory.class.getName();


    /**
     * Prevent construction of helper class
     */
    private RegistryHelper() {
    }

    /**
     * Bind an invoker factory in the registry
     *
     * @param factory  the invoker factory
     * @param uri      the URI of the managed connection, to uniquely identify
     *                 the factory
     * @param registry the registry to use
     * @throws ResourceException if the bind fails
     */
    public static void bind(RMIInvokerFactory factory, URI uri,
                            Registry registry) throws ResourceException {

        String name = getName(uri);

        try {
            registry.bind(name, factory);
        } catch (AlreadyBoundException exception) {
            throw new ResourceException("Binding exists for " + name,
                                        exception);
        } catch (RemoteException exception) {
            throw new ResourceException("Failed to bind connection factory",
                                        exception);
        }
    }

    /**
     * Unbind an invoker factory from the registry
     *
     * @param factory  the invoker factory
     * @param uri      the URI of the managed connection, to uniquely identify
     *                 the factory
     * @param registry the registry to use
     * @throws ResourceException if the unbind fails
     */
    public static void unbind(RMIInvokerFactory factory, URI uri,
                              Registry registry) throws ResourceException {
        String name = getName(uri);
        try {
            registry.unbind(name);
        } catch (NotBoundException exception) {
            throw new ResourceException("No binding exists for " + name,
                                        exception);
        } catch (RemoteException exception) {
            throw new ResourceException("Failed to unbind connection factory",
                                        exception);
        }
    }

    /**
     * Returns the invoker factory bind name. This is the path portion of the
     * connection URI suffixed with <code>"RMIInvokerFactory"</code>
     *
     * @param uri the connection URI
     * @return the invoker factory bind name
     */
    public static String getName(URI uri) {
        String path = uri.getPath();
        if (path == null) {
            path = "/";
        } else if (!path.startsWith("/")) {
            path = "/" + path;
        }
        if (!path.endsWith("/")) {
            path += "/";
        }

        return path + BIND_SUFFIX;
    }

    /**
     * Determines if there are any bindings in the registry
     *
     * @param registry the RMI registry to lookup
     * @return <code>true</code> if one or more bindings exist
     * @throws RemoteException if the operation fails
     */
    public static boolean hasBindings(Registry registry)
            throws RemoteException {
        String[] names = registry.list();
        return (names.length != 0);
    }

}
