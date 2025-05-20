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
 * Copyright 2003-2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: RegistryService.java,v 1.1 2004/11/26 01:51:04 tanderson Exp $
 */
package org.exolab.jms.net.orb;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ObjID;

import org.exolab.jms.net.proxy.Proxy;
import org.exolab.jms.net.registry.LocalRegistry;


/**
 * The <code>RegistryService</code> provides a simple interface for binding and
 * retrieving remote object references by name.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2004/11/26 01:51:04 $
 */
class RegistryService implements LocalRegistry {

    /**
     * The registry implementation
     */
    private RegistryImpl _registry = new RegistryImpl();


    /**
     * Construct a new <code>RegistryService</code> and export the registry on
     * its well known address
     *
     * @param orb the orb
     * @throws RemoteException if the service cannot be exported
     */
    public RegistryService(ORB orb) throws RemoteException {
        if (orb == null) {
            throw new IllegalArgumentException("Argument 'orb' is null");
        }
        orb.exportObject(_registry, new ObjID(ObjID.REGISTRY_ID));
    }

    /**
     * Returns a proxy for the remote object associated with the specified name
     *
     * @param name the name the proxy is bound under
     * @return proxy the proxy for the remote object
     * @throws NotBoundException if <code>name<code> is not bound
     */
    public Proxy lookup(String name) throws NotBoundException {
        return _registry.lookup(name);
    }

    /**
     * Binds the specified name to a remote object.
     *
     * @param name  the name of the binding
     * @param proxy the proxy for the remote object
     * @throws AlreadyBoundException if <code>name</code> is already bound
     */
    public void bind(String name, Proxy proxy) throws AlreadyBoundException {
        _registry.doBind(name, proxy);
    }

    /**
     * Removes the binding for the specified name
     *
     * @param name the name of the binding
     * @throws NotBoundException if <code>name<code> is not bound
     */
    public synchronized void unbind(String name) throws NotBoundException {
        _registry.doUnbind(name);
    }

    /**
     * Set if the registry is read-only
     *
     * @param readOnly if <code>true</code>, prevent remote users from modifying
     *                 bindings
     */
    public void setReadOnly(boolean readOnly) {
        _registry.setReadOnly(readOnly);
    }

    /**
     * Determines if the registry is read-only
     *
     * @return <code>true</code> if the registry is read-only
     */
    public boolean getReadOnly() {
        return _registry.getReadOnly();
    }

}
