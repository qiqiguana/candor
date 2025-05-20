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
 * Copyright 2000-2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: JmsAdminServerImpl.java,v 1.3 2005/08/30 05:51:03 tanderson Exp $
 */
package org.exolab.jms.server.net;

import java.rmi.RemoteException;
import javax.jms.JMSException;

import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.orb.UnicastObject;
import org.exolab.jms.server.AdminConnection;
import org.exolab.jms.server.AdminConnectionManager;


/**
 * This class implements the RemoteJmsAdminServerIfc and simply delegates all
 * the request to the JmsAdmin singleton instance.
 *
 * @author <a href="mailto:jima@exoffice.com">Jim Alateras</a>
 * @version $Revision: 1.3 $ $Date: 2005/08/30 05:51:03 $
 */
public class JmsAdminServerImpl
        extends UnicastObject
        implements RemoteJmsAdminServerIfc {

    /**
     * The admin connection manager.
     */
    private final AdminConnectionManager _manager;


    /**
     * Construct a new <code>JmsAdminServerImpl</code>
     *
     * @param manager the admin connection manager
     * @param orb     the ORB to export this with
     * @param uri     the URI to export this on
     * @throws RemoteException if this can't be exported
     */
    public JmsAdminServerImpl(AdminConnectionManager manager, ORB orb,
                              String uri) throws RemoteException {
        super(orb, uri);
        if (manager == null) {
            throw new IllegalArgumentException("Argument 'manager' is null");
        }
        _manager = manager;
    }

    /**
     * Create a connection to the specified server. This will create an instance
     * of a AdminConnection and then return a remote reference to it.
     *
     * @param username the client's user name
     * @param password the client's password
     * @return a new connection
     * @throws JMSException    if the connection cannot be created
     * @throws RemoteException if the connection cannot be created
     */
    public RemoteJmsAdminConnectionIfc createConnection(String username,
                                                        String password)
            throws JMSException, RemoteException {
        AdminConnection connection = _manager.createConnection(username,
                                                               password);

        JmsAdminConnectionImpl result =
                new JmsAdminConnectionImpl(connection, getORB());
        return (RemoteJmsAdminConnectionIfc) result.getProxy();
    }

}



