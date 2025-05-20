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
 * Copyright 2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: AdminConnection.java,v 1.1 2004/11/26 01:50:38 tanderson Exp $
 */
package org.exolab.jms.administration;

import java.util.Vector;

import javax.jms.JMSException;


/**
 * This specifies all the administration methods that can be used to control
 * the JMS server. The control logic is all at the org.exolab.jms.server
 * package level.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:38 $
 * @author <a href="mailto:knut@lerpold.no">Knut Lerpold</a>
 */
public interface AdminConnection {

    /**
     * Add a durable consumer for the specified name
     *
     * @param       topic               name of the destination
     * @param       name                name of the consumer
     * @return      boolean             true if successful
     * @throws      JMSException
     */
    boolean addDurableConsumer(String topic, String name) throws JMSException;

    /**
     * Check if the specified durable consumer exists
     *
     * @param       name                durable consumer to query
     * @return      boolean             true if it exists
     * @throws      JMSException
     */
    boolean durableConsumerExists(String name) throws JMSException;

    /**
     * Remove the specified durable consumer
     *
     * @param       name                name of the consumer
     * @return      boolean             true if successful
     * @throws      JMSException
     */
    boolean removeDurableConsumer(String name) throws JMSException;

    /**
     * Check to see if the given consumer is currently connected
     *
     * @param       name the name of the onsumer.
     * @return      <code>true</code> if the consumer is connected
     * @throws      JMSException
     */
    boolean isConnected(String name) throws JMSException;

    /**
     * De-activate an active persistent consumer.
     *
     * @param       name                name of the consumer
     * @return      boolean             true if successful
     * @throws      JMSException
     */
    boolean unregisterConsumer(String name) throws JMSException;

    /**
     * Add a specific destination with the specified name
     *
     * @param       name                destination name
     * @param       queue               whether it is queue or a topic
     * @return      boolean             true if successful
     * @throws      JMSException
     */
    boolean addDestination(String destination, Boolean queue)
        throws JMSException;

    /**
     * Destroy the specified destination and all associated messsages and
     * consumers. This is a very dangerous operation to execute while there
     * are clients online
     *
     * @param       destination         destination to destroy
     * @throws      JMSException
     */
    boolean removeDestination(String name) throws JMSException;

    /**
     * Determine if the specified destination exists
     *
     * @param name - the destination to check
     * @return boolean - true if it exists
     * @throws JMSException
     */
    boolean destinationExists(String name) throws JMSException;

    /**
     * Terminate the JMS Server. If it is running as a standalone application
     * then exit the application. It is running as an embedded application then
     * just terminate the thread
     *
     * @throws JMSException
     */
    void stopServer() throws JMSException;

    /**
     * Purge all processed messages from the database
     *
     * @return      the number of purged messages
     * @throws      JMSException
     */
    int purgeMessages() throws JMSException;

    /**
     * Add a user with the specified name
     *
     * @param username    the users name
     * @param password    the users password
     * @return <code>true</code> if the user is added
     * otherwise <code>false</code>
     * @throws JMSException
     */
    boolean addUser(String username, String password) throws JMSException;

    /**
     * Change password for the specified user
     *
     * @param username    the users name
     * @param password    the users password
     * @return <code>true</code> if the password is changed
     * otherwise <code>false</code>
     * @throws JMSException
     */
    boolean changePassword(String username, String password)
        throws JMSException;

    /**
     * Remove the specified user
     *
     * @param username    the users name
     * @return <code>true</code> if the user is removed otherwise
     * <code>false</code>
     * @throws JMSException
     */
    boolean removeUser(String username) throws JMSException;

    /**
     * Return the number of outstanding messages for a particular destination.
     *
     * @param       topic                name of the topic
     * @param       name                durable consumer name
     * @return      int                 message count
     * @throws      JMSException        on error
     */
    int getDurableConsumerMessageCount(String topic, String name)
        throws JMSException;

    /**
     * Return the number of outstanding messages for a particular queue.
     *
     * @param       queue               the queue name
     * @return      int                 message count
     * @throws      JMSException        on error
     */
    int getQueueMessageCount(String queue) throws JMSException;

    /**
     * Return the collection of durable consumer names for a particular
     * topic destination.
     *
     * @param       destination         the destination name
     * @return      Vector              collection of strings
     * @throws      JMSException        on error
     */
    Vector getDurableConsumers(String destination) throws JMSException;

    /**
     * Return a list of all registered destinations.
     *
     * @return      Vector              collection of strings
     * @throws      JMSException        on error
     */
    Vector getAllDestinations() throws JMSException;

    /**
     * Return a list of all registered users.
     *
     * @return Vector of users
     * @throws      JMSException        on error
     */
    Vector getAllUsers() throws JMSException;

}
