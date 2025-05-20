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
 * Copyright 2002-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: EmbeddedNameService.java,v 1.2 2005/08/30 05:41:44 tanderson Exp $
 */
package org.exolab.jms.server;

import java.util.Hashtable;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingException;

import org.codehaus.spice.jndikit.DefaultNameParser;
import org.codehaus.spice.jndikit.Namespace;
import org.codehaus.spice.jndikit.NamingProvider;
import org.codehaus.spice.jndikit.RemoteContext;
import org.codehaus.spice.jndikit.StandardNamespace;
import org.codehaus.spice.jndikit.memory.MemoryContext;
import org.codehaus.spice.jndikit.rmi.server.RMINamingProviderImpl;

/**
 * Embedded name service.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version     $Revision: 1.2 $ $Date: 2005/08/30 05:41:44 $
 */
public class EmbeddedNameService {

    /**
     * The naming provider.
     */
    private NamingProvider _provider;


    /**
     * Construct an <code>EmbeddedNameService</code>.
     */
    protected EmbeddedNameService() {
        DefaultNameParser parser = new DefaultNameParser();
        Namespace namespace = createNamespace(parser);
        Context context = new MemoryContext(namespace, null, null);
        _provider = new RMINamingProviderImpl(context);
    }

    /**
     * Returns the initial context for beginning name resolution.
     *
     * @return the initial context
     * @throws NamingException for any naming error
     */
    public Context getInitialContext() throws NamingException {
        Hashtable environment = new Hashtable();
        Namespace namespace;
        try {
            namespace = createNamespace(_provider.getNameParser());
        } catch (NamingException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CommunicationException(exception.toString());
        }

        environment.put(RemoteContext.NAMING_PROVIDER, _provider);
        environment.put(RemoteContext.NAMESPACE, namespace);

        final Name baseName = namespace.getNameParser().parse("");
        return new RemoteContext(environment, baseName);
    }

    /**
     * Returns the naming provider.
     *
     * @return the naming provider
     */
    public NamingProvider getNamingProvider() {
        return _provider;
    }

    /**
     * Creates a new namespace.
     *
     * @param parser the parser to use
     * @return a new namespace
     */
    private Namespace createNamespace(NameParser parser) {
        return new StandardNamespace(parser);
    }
}
