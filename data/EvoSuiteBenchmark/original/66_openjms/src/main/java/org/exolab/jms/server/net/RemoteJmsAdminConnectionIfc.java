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
 * $Id: RemoteJmsAdminConnectionIfc.java,v 1.1 2004/11/26 01:51:01 tanderson Exp $
 */
package org.exolab.jms.server.net;

import java.rmi.RemoteException;
import java.util.Vector;


/**
 * This specifies all the administration methods that can be used to control
 * the JMS server through an RMI connector. The control logic is all at the
 * org.exolab.jms.server package level
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:01 $
 * @author      <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 */
public interface RemoteJmsAdminConnectionIfc {

    /**
     * Add a consumer for the specified topic
     *
     * @param       topic               name of the destination
     * @param       name                name of the consumer
     * @return      boolean             true if successful
     * @throws RemoteException if the connection cannot be created
     */
    boolean addDurableConsumer(String topic, String name)
        throws RemoteException;

    /**
     * Remove the consumer attached to the specified destination and with
     * the passed in name
     *
     * @param       name                name of the consumer
     * @return      boolean             true if successful
     * @throws RemoteException if the connection cannot be created
     */
    boolean removeDurableConsumer(String name) throws RemoteException;

    /**
     * Check if a durable consumer, with the specified name, already exists
     *
     * @param       name                name of the consumer
     * @return      boolean             true if it exists
     * @throws RemoteException if the connection cannot be created
     */
    boolean durableConsumerExists(String name) throws RemoteException;

    /**
     * Return the collection of durable consumer names for a particular
     * topic destination.
     *
     * @param       destination          the destination name
     * @return      Vector              collection of strings
     * @throws RemoteException if the connection cannot be created
     */
    Vector getDurableConsumers(String destination) throws RemoteException;

    /**
     * Deactivate an active persistent consumer.
     *
     * @param       name                name of the consumer
     * @return      boolean             true if successful
     * @throws RemoteException if the consumer cannot be unregistered
     */
    boolean unregisterConsumer(String name) throws RemoteException;

    /**
     * Check to see if the given consumer is currently connected to the
     * OpenJMSServer. This is only valid when in online mode.
     *
     * @param name The name of the onsumer.
     * @return boolean True if the consumer is connected.
     * @throws RemoteException if the connection cannot be created
     */
    boolean isConnected(String name) throws RemoteException;

    /**
     * Add a specific destination with the specified name
     *
     * @param       destination                destination name
     * @param       queue               whether it is queue or a topic
     * @return      boolean             true if successful
     * @throws RemoteException if the connection cannot be created
     */
    boolean addDestination(String destination, Boolean queue)
        throws RemoteException;

    /**
     * Destroy the specified destination and all associated messsages and
     * consumers. This is a very dangerous operation to execute while there
     * are clients online
     *
     * @param       name         destination to destroy
     * @return boolean           if the destination got destroyed
     * @throws RemoteException if the connection cannot be created
     */
    boolean removeDestination(String name) throws RemoteException;

    /**
     * Determine whether the destination with the specified name exists
     *
     * @param  name - the destination to check
     * @return boolean - if the destination exists
     * @throws RemoteException if the connection cannot be created
     */
    boolean destinationExists(String name) throws RemoteException;

    /**
     * Return a list of all registered destinations.
     *
     * @return      Vector              collection of strings
     * @throws RemoteException if the connection cannot be created
     */
    Vector getAllDestinations() throws RemoteException;

    /**
     * Return the number of outstanding messages for a particular destination.
     *
     * @param       topic                name of the topic
     * @param       name                durable consumer name
     * @return      int                 message count
     * @throws RemoteException if the connection cannot be created
     */
    int getDurableConsumerMessageCount(String topic, String name)
        throws RemoteException;

    /**
     * Return the number of outstanding messages for a particular queue.
     *
     * @param       queue               the queue name
     * @return      int                 message count
     * @throws RemoteException if the connection cannot be created
     */
    int getQueueMessageCount(String queue) throws RemoteException;

    /**
     * Purge all processed messages from the database
     *
     * @return      int                 the number of purged messages
     * @throws RemoteException if the connection cannot be created
     */
    int purgeMessages() throws RemoteException;

    /**
     * Terminate the JMS Server. If it is running as a standalone application
     * then exit the application. It is running as an embedded application then
     * just terminate the thread
     * @throws RemoteException if the connection cannot be created
     */
    void stopServer() throws RemoteException;


    /**
     * Add a user with the specified name
     *
     * @param username    the users name
     * @param password    the users password
     * @return <code>true</code> if the user is added
     * otherwise <code>false</code>
     * @throws RemoteException if the connection cannot be created
     */
    boolean addUser(String username, String password) throws RemoteException;

    /**
     * Change password for the specified user
     *
     * @param username    the users name
     * @param password    the users password
     * @return <code>true</code> if the password is changed
     * otherwise <code>false</code>
     * @throws RemoteException if the connection cannot be created
     */
    boolean changePassword(String username, String password)
        throws RemoteException;

    /**
     * Remove the specified user
     *
     * @param username    the users name
     * @return <code>true</code> if the user is removed
     * otherwise <code>false</code>
     * @throws RemoteException if the connection cannot be created
     */
    boolean removeUser(String username) throws RemoteException;

    /**
     * Return a list of all registered users.
     *
     * @return Vector of users
     * @throws RemoteException if the connection cannot be created
     */
    Vector getAllUsers() throws RemoteException;

}
