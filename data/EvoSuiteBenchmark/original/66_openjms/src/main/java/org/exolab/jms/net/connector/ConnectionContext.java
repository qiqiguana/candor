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
 * $Id: ConnectionContext.java,v 1.5 2005/12/01 13:44:38 tanderson Exp $
 */
package org.exolab.jms.net.connector;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.net.uri.URI;


/**
 * <code>ConnectionContext</code> enables connectors to associate a {@link
 * Connection} and {@link ConnectionFactory} with th current thread, to enable
 * deserialized {@link org.exolab.jms.net.proxy.Proxy} instances to resolve a
 * {@link Connection} back to the server.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.5 $ $Date: 2005/12/01 13:44:38 $
 */
public final class ConnectionContext {

    /**
     * Maintains a List of {@link Context} instances on a per-thread basis.
     */
    private static ThreadLocal _contexts = new ThreadLocal();

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(ConnectionContext.class);


    /**
     * Prevent construction of helper class.
     */
    private ConnectionContext() {
    }

    /**
     * Adds the connection context for the current thread.
     *
     * @param principal the security principal
     * @param factory   the connection factory
     */
    public static void push(Principal principal, ConnectionFactory factory) {
        List stack = (List) _contexts.get();
        if (stack == null) {
            stack = new ArrayList(2);
            _contexts.set(stack);
        }
        stack.add(new Context(principal, factory));
    }

    /**
     * Removes the last-pushed context for the current thread.
     */
    public static void pop() {
        List stack = (List) _contexts.get();
        stack.remove(stack.size() - 1);
    }

    /**
     * Returns a connection using the principal and connection factory
     * associated with the current thread.
     *
     * @param uri the URI
     * @return a new connection, or <code>null</code> if a connection can't
     *         be established
     */
    public static Connection getConnection(URI uri) {
        Connection result = null;
        Context context = top();
        if (context != null) {
            ConnectionFactory factory = context.getConnectionFactory();
            try {
                result = factory.getConnection(context.getPrincipal(), uri);
            } catch (ResourceException exception) {
                _log.debug(exception, exception);
            }
        }
        return result;
    }

    /**
     * Returns the connection factory for the current thread.
     *
     * @return the connection factory for the current thread, or
     *         <code>null</code> if no factory is set
     */
    public static ConnectionFactory getConnectionFactory() {
        ConnectionFactory result = null;
        Context context = top();
        if (context != null) {
            result = context.getConnectionFactory();
        }
        return result;
    }

    /**
     * Returns the context at the top of the local threads stack.
     *
     * @return the top context, or <code>null</code> if there are no contexts
     *         associated with this thread.
     */
    private static Context top() {
        Context context = null;
        List stack = (List) _contexts.get();
        if (stack != null && !stack.isEmpty()) {
            context = (Context) stack.get(stack.size() - 1);
        }
        return context;
    }


    private static class Context {

        /**
         * The security principal.
         */
        private final Principal _principal;

        /**
         * The connection factory.
         */
        private final ConnectionFactory _factory;


        /**
         * Construct a new <code>Context</code>.
         *
         * @param principal the security principal
         * @param factory   the connection factory
         */
        public Context(Principal principal, ConnectionFactory factory) {
            _principal = principal;
            _factory = factory;
        }

        /**
         * Returns the security principal.
         *
         * @return the security principal
         */
        public Principal getPrincipal() {
            return _principal;
        }

        /**
         * Returns the connection factory.
         *
         * @return the connection factory.
         */
        public ConnectionFactory getConnectionFactory() {
            return _factory;
        }

    }
}
