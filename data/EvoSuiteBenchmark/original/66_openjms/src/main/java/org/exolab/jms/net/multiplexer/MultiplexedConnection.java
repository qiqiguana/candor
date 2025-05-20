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
 * $Id: MultiplexedConnection.java,v 1.2 2005/11/16 12:32:49 tanderson Exp $
 */
package org.exolab.jms.net.multiplexer;

import org.exolab.jms.net.connector.Connection;
import org.exolab.jms.net.connector.Request;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.connector.Response;
import org.exolab.jms.net.uri.URI;


/**
 * An <code>MultiplexedConnection</code> is a {@link Connection} which supports
 * method invocation over an multiplexed transport.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/11/16 12:32:49 $
 * @see MultiplexedManagedConnection
 */
class MultiplexedConnection implements Connection {

    /**
     * The physical connection.
     */
    private MultiplexedManagedConnection _connection;


    /**
     * Construct a new <code>MultiplexedConnection</code>.
     *
     * @param connection the physical connection
     */
    public MultiplexedConnection(MultiplexedManagedConnection connection) {
        _connection = connection;
    }

    /**
     * Invoke a method on a remote object.
     *
     * @param request the request
     * @return the result of the invocation
     */
    public Response invoke(Request request) {
        return _connection.invoke(this, request);
    }

    /**
     * Returns the remote address to which this is connected.
     *
     * @return the remote address to which this is connected
     * @throws ResourceException for any error
     */
    public URI getRemoteURI() throws ResourceException {
        return _connection.getRemoteURI();
    }

    /**
     * Returns the local address that this connection is bound to.
     *
     * @return the local address that this connection is bound to
     * @throws ResourceException for any error
     */
    public URI getLocalURI() throws ResourceException {
        return _connection.getLocalURI();
    }

    /**
     * Close this connection, releasing any allocated resources.
     */
    public void close() throws ResourceException {
        _connection = null;
    }

}
