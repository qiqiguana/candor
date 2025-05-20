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
 * $Id: JmsAdminConnectionImpl.java,v 1.2 2005/03/18 04:07:02 tanderson Exp $
 */
package org.exolab.jms.server.net;

import java.rmi.RemoteException;
import java.util.Vector;

import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.orb.UnicastObject;
import org.exolab.jms.server.AdminConnection;
import org.exolab.jms.server.AdminConnection;


/**
 * This class implements the RemoteJmsAdminServerIfc and simply delegates all
 * the request to the JmsAdmin singleton instance.
 *
 * @version     $Revision: 1.2 $ $Date: 2005/03/18 04:07:02 $
 * @author      <a href="mailto:jima@exoffice.com">Jim Alateras</a>
 * @see         java.rmi.Remote
 **/
public class JmsAdminConnectionImpl
    extends UnicastObject
    implements RemoteJmsAdminConnectionIfc {

    /**
     * The connection to delegate calls to
     */
    protected AdminConnection _connection;

    /**
     * Construct a new <code>JmsAdminConnectionImpl</code>
     *
     * @param connection the connection to calls delegate to
     * @param orb the ORB to export this with
     * @throws RemoteException if this can't be exported
     */
    protected JmsAdminConnectionImpl(AdminConnection connection, ORB orb)
        throws RemoteException {
        super(orb, null, true);
        if (connection == null) {
            throw new IllegalArgumentException("Argument connection is null");
        }

        _connection = connection;
    }

    // implementation of RemoteJmsAdminServerIfc.removeDurableConsumer
    public boolean addDurableConsumer(String topic, String name)
        throws RemoteException {
        return _connection.addDurableConsumer(topic, name);
    }

    // implementation of RemoteJmsAdminServerIfc.removeDurableConsumer
    public boolean removeDurableConsumer(String name)
        throws RemoteException {
        return _connection.removeDurableConsumer(name);
    }

    // implementation of RemoteJmsAdminServerIfc.durableConsumerExists
    public boolean durableConsumerExists(String name)
        throws RemoteException {
        return _connection.durableConsumerExists(name);
    }


    // implementation of RemoteJmsAdminServerIfc.removeDurableConsumer
    public boolean unregisterConsumer(String name)
        throws RemoteException {
        return _connection.unregisterConsumer(name);
    }

    // implementation of RemoteJmsAdminServerIfc.isConnected
    public boolean isConnected(String name)
        throws RemoteException {
        return _connection.isConnected(name);
    }

    // implementation of RemoteJmsAdminServerIfc.getAllDestinations
    public Vector getAllDestinations()
        throws RemoteException {
        return _connection.getAllDestinations();
    }

    // implementation of RemoteJmsAdminServerIfc.addDestination
    public boolean addDestination(String destination, Boolean queue)
        throws RemoteException {
        return _connection.addDestination(destination, queue);
    }

    // implementation of RemoteJmsAdminServerIfc.getDurableConsumerMessageCount
    public int getDurableConsumerMessageCount(String topic, String name)
        throws RemoteException {
        return _connection.getDurableConsumerMessageCount(topic, name);
    }

    // implementation of RemoteJmsAdminServerIfc.getDurableConsumerMessageCount
    public int getQueueMessageCount(String queue)
        throws RemoteException {
        return _connection.getQueueMessageCount(queue);
    }

    // implementation of RemoteJmsAdminServerIfc.getDurableConsumers
    public Vector getDurableConsumers(String topic)
        throws RemoteException {
        return _connection.getDurableConsumers(topic);
    }

    // implementation of RemoteJmsAdminServerIfc.removeDestination
    public boolean removeDestination(String name)
        throws RemoteException {
        return _connection.removeDestination(name);
    }

    // implementation of RemoteJmsAdminServerIfc.removeDestination
    public boolean destinationExists(String name)
        throws RemoteException {
        return _connection.destinationExists(name);
    }

    // implementation of RemoteJmsAdminServerIfc.purgeMessages
    public int purgeMessages()
        throws RemoteException {
        return _connection.purgeMessages();
    }

    // implementation of RemoteJmsAdminServerIfc.stopServer
    public void stopServer()
        throws RemoteException {
        _connection.stopServer();
    }

    // implementation of RemoteJmsAdminServerIfc.addUser
    public boolean addUser(String username, String password)
        throws RemoteException {
        return _connection.addUser(username, password);
    }

    // implementation of RemoteJmsAdminServerIfc.changePassword
    public boolean changePassword(String username, String password)
        throws RemoteException {
        return _connection.changePassword(username, password);
    }

    // implementation of RemoteJmsAdminServerIfc.removeUser
    public boolean removeUser(String username) throws RemoteException {
        return _connection.removeUser(username);
    }

    // implementation of RemoteJmsAdminServerIfc.getAllUsers
    public Vector getAllUsers() throws RemoteException {
        return _connection.getAllUsers();
    }

}



