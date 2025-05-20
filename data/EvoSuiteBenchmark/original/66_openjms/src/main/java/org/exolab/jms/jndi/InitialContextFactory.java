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
 * $Id: InitialContextFactory.java,v 1.6 2005/12/20 21:42:06 tanderson Exp $
 */
package org.exolab.jms.jndi;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Hashtable;
import javax.naming.CommunicationException;
import javax.naming.ConfigurationException;
import javax.naming.Context;
import javax.naming.NameParser;
import javax.naming.NamingException;
import javax.naming.ServiceUnavailableException;

import org.codehaus.spice.jndikit.Namespace;
import org.codehaus.spice.jndikit.NamingProvider;
import org.codehaus.spice.jndikit.RemoteContext;
import org.codehaus.spice.jndikit.StandardNamespace;

import org.exolab.jms.client.net.SharedORB;
import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.registry.Registry;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.proxy.Proxy;


/**
 * A factory that creates an initial context to an embedded OpenJMS JNDI
 * provider.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.6 $ $Date: 2005/12/20 21:42:06 $
 */
public class InitialContextFactory
        implements javax.naming.spi.InitialContextFactory {

    /**
     * HTTP connector URI scheme.
     */
    private static final String HTTP_SCHEME = "http";

    /**
     * HTTPS connector URI scheme.
     */
    private static final String HTTPS_SCHEME = "https";

    /**
     * Default tunnel servlet path.
     */
    private static final String SERVLET = "/openjms-tunnel/tunnel";

    /**
     * Old style VM connector URI scheme.
     */
    private static final String EMBEDDED_SCHEME = "embedded";

    /**
     * VM connector URI scheme.
     */
    private static final String VM_SCHEME = "vm";

    /**
     * Default VM connector path.
     */
    private static final String VM_PATH = "openjms";


    /**
     * Creates an initial context for beginning name resolution, based on the
     * {@link Context#PROVIDER_URL} attribute.
     *
     * @param environment the environment specifying information to be used in
     *                    the creation of the initial context.
     * @return an initial context
     * @throws NamingException if the initial context cannot be created
     */
    public Context getInitialContext(Hashtable environment)
            throws NamingException {
        if (environment == null) {
            throw new ConfigurationException(
                    "Cannot connect to JNDI provider - environment not set");
        }
        String url = (String) environment.get(Context.PROVIDER_URL);
        if (url == null) {
            throw new ConfigurationException("Cannot connect to JNDI provider - "
                                             + Context.PROVIDER_URL
                                             + " not set ");
        }

        // map JNDI properties to the ORB equivalents, if unset
        Hashtable properties = new Hashtable(environment);
        properties.put(Context.PROVIDER_URL, getProviderURI(url));
        map(properties, Context.PROVIDER_URL, ORB.PROVIDER_URI);
        map(properties, Context.SECURITY_PRINCIPAL, ORB.SECURITY_PRINCIPAL);
        map(properties, Context.SECURITY_CREDENTIALS, ORB.SECURITY_CREDENTIALS);

        ORB orb;
        Registry registry;
        try {
            orb = SharedORB.getInstance();
            registry = orb.getRegistry(properties);
        } catch (RemoteException exception) {
            NamingException error = new CommunicationException(
                    "Failed to get registry service for URL: " + url);
            error.setRootCause(exception);
            throw error;
        }

        NamingProvider provider;
        try {
            provider = (NamingProvider) registry.lookup("jndi");
        } catch (NotBoundException exception) {
            throw new ServiceUnavailableException(
                    "JNDI service is not bound in the registry for URL: "
                    + url);
        } catch (RemoteException exception) {
            NamingException error = new CommunicationException(
                    "Failed to lookup JNDI provider for URL: " + url);
            error.setRootCause(exception);
            throw error;
        } finally {
            // get rid of the proxy now rather than waiting for GC
            if (registry instanceof Proxy) {
                ((Proxy) registry).disposeProxy();
            }
        }

        NameParser parser;
        try {
            parser = provider.getNameParser();
        } catch (NamingException exception) {
            throw exception;
        } catch (Exception exception) {
            NamingException error = new ServiceUnavailableException(
                    exception.getMessage());
            error.setRootCause(exception);
            throw error;
        }
        Namespace namespace = new StandardNamespace(parser);
        properties.put(RemoteContext.NAMING_PROVIDER, provider);
        properties.put(RemoteContext.NAMESPACE, namespace);
        RemoteContext root = new RemoteContext(properties, parser.parse(""));
        return new ORBRemoteContext(root);
    }

    /**
     * Modifies the supplied provider URI with default values if required
     * details haven't been specified.
     *
     * @param uri the provider URI
     * @return the modified provider URI
     * @throws ConfigurationException if <code>uri</code> is invalid
     */
    private String getProviderURI(String uri)
            throws ConfigurationException {
        URI parsed;
        try {
            parsed = new URI(uri);
            String scheme = parsed.getScheme();
            if (scheme.equals(HTTP_SCHEME) || scheme.equals(HTTPS_SCHEME)) {
                String path = parsed.getPath();
                if (path == null || path.length() == 0 || path.equals("/")) {
                    parsed.setPath(SERVLET);
                }
            } else if (scheme.equals(EMBEDDED_SCHEME)) {
                parsed.setScheme(VM_SCHEME);
                parsed.setPath(VM_PATH);
            }
        } catch (IOException exception) {
            throw new ConfigurationException(exception.getMessage());
        }
        return parsed.toString();
    }

    /**
     * Helper to copy a property from one to name to another, if it exists.
     *
     * @param properties the properties to examine
     * @param from       the property name to map from
     * @param to         the property name to map to
     */
    private void map(Hashtable properties, String from, String to) {
        String value = (String) properties.get(from);
        if (value != null) {
            properties.put(to, value);
        }
    }

}
