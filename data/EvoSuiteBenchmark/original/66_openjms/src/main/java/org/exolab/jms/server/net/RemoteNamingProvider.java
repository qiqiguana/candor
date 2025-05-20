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
 */
package org.exolab.jms.server.net;

import java.rmi.RemoteException;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingException;

import org.codehaus.spice.jndikit.NamingProvider;
import org.codehaus.spice.jndikit.rmi.RMINamingProvider;

import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.orb.UnicastObject;


/**
 * Implementation of the {@link RMINamingProvider} interface, that provides
 * remoting via an {@link ORB}
 *
 * @version     $Revision: 1.1 $ $Date: 2005/03/18 04:07:03 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
class RemoteNamingProvider
    extends UnicastObject
    implements NamingProvider {

    /**
     * The provider implementation
     */
    private NamingProvider _provider;


    /**
     * Construct a new <code>RemoteNamingProvider</code>
     *
     * @param provider the provider to delegate to
     * @param orb the ORB to export this with
     * @param uri the URI to export this on
     * @throws RemoteException if the export fails
     */
    public RemoteNamingProvider(NamingProvider provider, ORB orb, String uri)
        throws RemoteException {
        super(orb, uri);
        _provider = provider;
    }

    public NameParser getNameParser() throws NamingException, Exception {
        return _provider.getNameParser();
    }

    public void bind(Name name, String className, Object object)
        throws NamingException, Exception {
        _provider.bind(name, className, object);
    }

    public void rebind(Name name, String className, Object object)
        throws NamingException, Exception {
        _provider.rebind(name, className, object);
    }

    public Context createSubcontext(Name name)
        throws NamingException, Exception {
        return _provider.createSubcontext(name);
    }

    public void destroySubcontext(Name name)
        throws NamingException, Exception {
        _provider.destroySubcontext(name);
    }

    public NameClassPair[] list(Name name) throws NamingException, Exception {
        return _provider.list(name);
    }

    public Binding[] listBindings(Name name)
        throws NamingException, Exception {
        return _provider.listBindings(name);
    }

    public Object lookup(Name name) throws NamingException, Exception {
        return _provider.lookup(name);
    }

    public void unbind(Name name) throws NamingException, Exception {
        _provider.unbind(name);
    }

}
