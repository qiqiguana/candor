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
 * Copyright 2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: ContextInvocationHandler.java,v 1.4 2005/12/01 13:44:38 tanderson Exp $
 */
package org.exolab.jms.net.connector;

import org.exolab.jms.net.proxy.Proxy;


/**
 * <code>InvocationHandler</code> implementation that sets the current {@link
 * ConnectionContext}. before delegating invocations.<br/>
 * This ensures {@link Proxy} instances are associated with the appropriate
 * {@link Connection} and {@link ConnectionFactory} on deserialization.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2005/12/01 13:44:38 $
 * @see ConnectionContext
 */
public class ContextInvocationHandler implements InvocationHandler {

    /**
     * The invocation handler to delegate to.
     */
    private final InvocationHandler _handler;

    /**
     * The connection performing the invocation.
     */
    private ManagedConnection _connection;

    /**
     * The connection factory for resolving connections via their URI.
     */
    private final ConnectionFactory _resolver;


    /**
     * Construct a new <code>ContextInvocationHandler</code>.
     *
     * @param handler  the handler to delegate to
     * @param resolver the connection factory for resolving connections via
     *                 their URI
     * @param connection the connection performing the invocation
     */
    public ContextInvocationHandler(InvocationHandler handler,
                                    ConnectionFactory resolver,
                                    ManagedConnection connection) {
        _handler = handler;
        _resolver = resolver;
        _connection = connection;
    }

    /**
     * Perform an invocation.
     *
     * @param invocation the invocation
     */
    public void invoke(Invocation invocation) {
        _handler.invoke(new ContextInvocation(invocation));
    }

    private class ContextInvocation implements Invocation {

        /**
         * The invocation to delegate to.
         */
        private final Invocation _invocation;

        /**
         * Construct a new <code>ContextInvocation</code>.
         *
         * @param invocation the invocation
         */
        public ContextInvocation(Invocation invocation) {
            _invocation = invocation;
        }

        /**
         * Returns the request.
         *
         * @return the request
         * @throws Throwable for any error
         */
        public Request getRequest() throws Throwable {
            ConnectionContext.push(_connection.getPrincipal(), _resolver);
            return _invocation.getRequest();
        }

        /**
         * Returns the caller performing the invocation.
         *
         * @return the caller
         */
        public Caller getCaller() {
            return _invocation.getCaller();
        }

        /**
         * Sets the result of the invocation.
         *
         * @param response the result
         */
        public void setResponse(Response response) {
            try {
                _invocation.setResponse(response);
            } finally {
                ConnectionContext.pop();
            }
        }
    }
}
