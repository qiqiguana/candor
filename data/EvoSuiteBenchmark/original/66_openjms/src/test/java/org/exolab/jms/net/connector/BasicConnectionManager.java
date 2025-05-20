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
 * $Id: BasicConnectionManager.java,v 1.3 2005/06/04 14:56:40 tanderson Exp $
 */
package org.exolab.jms.net.connector;

import java.util.Map;


/**
 * Basic implementation of the {@link ConnectionManager} interface.
 *
 * @version     $Revision: 1.3 $ $Date: 2005/06/04 14:56:40 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
public class BasicConnectionManager extends AbstractConnectionManager {

    /**
     * The managed connection factory.
     */
    private final ManagedConnectionFactory _factory;

    /**
     * Construct a new <code>BasicConnectionManager</code>.
     *
     * @param factory the managed connection factory
     * @param handler       the invocation handler
     * @param authenticator the connection authenticator
     * @param properties    configuration properties. May be <code>null</code>
     * @throws ResourceException for any error
     */
    public BasicConnectionManager(ManagedConnectionFactory factory,
                                  InvocationHandler handler,
                                  Authenticator authenticator,
                                  Map properties)
            throws ResourceException {
        super(handler, authenticator, properties);
        addManagedConnectionFactory(factory);
        _factory = factory;
    }

    /**
     * Construct a new <code>BasicConnectionManager</code>,
     * with a dummy invocation handler.
     *
     * @param factory the managed connection factory
     * @param authenticator the authenticator for incoming connections
     * @throws ResourceException if the factory can't be registered
     */
    public BasicConnectionManager(ManagedConnectionFactory factory,
                                  Authenticator authenticator)
        throws ResourceException {
        this(factory, new TestInvocationHandler(), authenticator, null);
    }

    /**
     * Returns the connection pool.
     *
     * @return the connection pool
     * @throws ResourceException if the pool doesn't exist
     */
    public TestConnectionPool getConnectionPool() throws ResourceException {
        return (TestConnectionPool) getConnectionPool(_factory);
    }

    /**
     * Creates a new connection pool.
     *
     * @param factory  the managed connection factory
     * @param handler  the invocation handler, assigned to each new managed
     *                 connection
     * @param resolver the connection factory for resolving connections via
     *                 their URI, assigned to each new managed connection
     * @throws ResourceException if the pool can't be created
     */
    protected ConnectionPool createConnectionPool(
            ManagedConnectionFactory factory, InvocationHandler handler,
            ConnectionFactory resolver) throws ResourceException {
        return new TestConnectionPool(factory, handler, resolver);
    }

}

