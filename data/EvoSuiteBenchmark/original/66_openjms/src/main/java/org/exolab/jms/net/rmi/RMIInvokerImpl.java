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
 * $Id: RMIInvokerImpl.java,v 1.2 2006/12/16 12:37:17 tanderson Exp $
 */
package org.exolab.jms.net.rmi;

import java.rmi.MarshalException;
import java.rmi.MarshalledObject;
import java.rmi.server.RemoteObject;
import java.rmi.server.Unreferenced;


/**
 * Implementation of the {@link RMIInvoker} interface
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2006/12/16 12:37:17 $
 */
public class RMIInvokerImpl extends RemoteObject implements RMIInvoker,
                                                            Unreferenced {

    /**
     * The physical connection
     */
    private RMIManagedConnection _connection;


    /**
     * Construct a new <code>RMIInvokerImpl</code>
     */
    public RMIInvokerImpl() {
    }

    /**
     * Set the connection to delegate requests to
     *
     * @param connection the connection to delegate requests to
     */
    public void setConnection(RMIManagedConnection connection) {
        _connection = connection;
    }

    /**
     * Invoke a method on a remote object
     *
     * @param request the wrapped <code>Request</code>
     * @return the result of the invocation
     * @throws MarshalException if the request can't be unmarshalled or the
     *                          response can't be marshalled
     */
    public MarshalledObject invoke(MarshalledObject request)
            throws MarshalException {
        return _connection.invokeLocal(request);
    }

    /**
     * Ping the connection to determine if it is alive
     */
    public void ping() {
        // If the invocation isn't successfully, a RemoteException will
        // be generated in the client
    }

    /**
     * Notifies that the remote peer is disconnecting.
     */
    public void disconnect() {
        _connection.disconnect();
    }

    /**
     * Called by the RMI runtime sometime after the runtime determines that the
     * reference list, the list of clients referencing the remote object,
     * becomes empty.
     */
    public void unreferenced() {
        _connection.disconnect();
    }
}
