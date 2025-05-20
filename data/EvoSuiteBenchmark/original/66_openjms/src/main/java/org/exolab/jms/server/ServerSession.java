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
 * $Id: ServerSession.java,v 1.2 2005/08/30 05:23:16 tanderson Exp $
 */
package org.exolab.jms.server;

import java.util.List;
import javax.jms.JMSException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsMessageListener;
import org.exolab.jms.client.JmsQueue;
import org.exolab.jms.client.JmsTopic;
import org.exolab.jms.message.MessageImpl;


/**
 * Indicates the methods clients can call on the server-side implementation of
 * the {@link javax.jms.Session} interface
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/08/30 05:23:16 $
 */
public interface ServerSession {

    /**
     * Close and release any resource allocated to this session.
     *
     * @throws JMSException for any JMS error
     */
    void close() throws JMSException;

    /**
     * Acknowledge that a message has been processed.
     *
     * @param consumerId the identity of the consumer performing the ack
     * @param messageId  the message identifier
     * @throws JMSException for any error
     */
    void acknowledgeMessage(long consumerId, String messageId)
            throws JMSException;

    /**
     * Send a message.
     *
     * @param message the message to send
     * @throws JMSException for any error
     */
    void send(MessageImpl message) throws JMSException;

    /**
     * Send a set of messages.
     *
     * @param messages a list of <code>MessageImpl</code> instances
     * @throws JMSException for any JMS error
     */
    void send(List messages) throws JMSException;

    /**
     * Return the next available mesage to the specified consumer.
     * <p/>
     * This method is non-blocking. If no messages are available, it will return
     * immediately.
     *
     * @param consumerId the consumer identifier
     * @return the next message or <code>null</code> if none is available
     * @throws JMSException for any JMS error
     */
    MessageImpl receiveNoWait(long consumerId) throws JMSException;

    /**
     * Return the next available message to the specified consumer.
     * <p/>
     * This method is non-blocking. However, clients can specify a
     * <code>wait</code> interval to indicate how long they are prepared to wait
     * for a message. If no message is available, and the client indicates that
     * it will wait, it will be notified via the registered {@link
     * JmsMessageListener} if one subsequently becomes available.
     *
     * @param consumerId the consumer identifier
     * @param wait       number of milliseconds to wait. A value of <code>0
     *                   </code> indicates to wait indefinitely
     * @return the next message, or <code>null</code>
     * @return the next message or <code>null</code> if none is available
     * @throws JMSException for any JMS error
     */
    MessageImpl receive(long consumerId, long wait) throws JMSException;

    /**
     * Browse up to count messages.
     *
     * @param consumerId the consumer identifier
     * @param count      the maximum number of messages to receive
     * @return a list of {@link MessageImpl} instances
     * @throws JMSException for any JMS error
     */
    List browse(long consumerId, int count) throws JMSException;

    /**
     * Create a new message consumer.
     *
     * @param destination the destination to consume messages from
     * @param selector    the message selector. May be <code>null</code>
     * @param noLocal     if true, and the destination is a topic, inhibits the
     *                    delivery of messages published by its own connection.
     *                    The behavior for <code>noLocal</code> is not specified
     *                    if the destination is a queue.
     * @return the identifty of the message consumer
     * @throws JMSException for any JMS error
     */
    long createConsumer(JmsDestination destination, String selector,
                        boolean noLocal)
            throws JMSException;

    /**
     * Create a new durable consumer. Durable consumers may only consume from
     * non-temporary <code>Topic</code> destinations.
     *
     * @param topic    the non-temporary <code>Topic</code> to subscribe to
     * @param name     the name used to identify this subscription
     * @param selector only messages with properties matching the message
     *                 selector expression are delivered.  A value of null or an
     *                 empty string indicates that there is no message selector
     *                 for the message consumer.
     * @param noLocal  if set, inhibits the delivery of messages published by
     *                 its own connection
     * @return the identity of the durable consumer
     * @throws JMSException for any JMS error
     */
    long createDurableConsumer(JmsTopic topic, String name, String selector,
                               boolean noLocal)
            throws JMSException;

    /**
     * Create a queue browser for this session. This allows clients to browse a
     * queue without removing any messages.
     *
     * @param queue    the queue to browse
     * @param selector the message selector. May be <code>null</code>
     * @return the identity of the queue browser
     * @throws JMSException for any JMS error
     */
    long createBrowser(JmsQueue queue, String selector) throws JMSException;

    /**
     * Close a message consumer.
     *
     * @param consumerId the identity of the consumer to close
     * @throws JMSException for any JMS error
     */
    void closeConsumer(long consumerId) throws JMSException;

    /**
     * Unsubscribe a durable subscription.
     *
     * @param name the name used to identify the subscription
     * @throws JMSException for any JMS error
     */
    void unsubscribe(String name) throws JMSException;

    /**
     * Start message delivery to this session.
     *
     * @throws JMSException for any JMS error
     */
    void start() throws JMSException;

    /**
     * Stop message delivery to this session.
     *
     * @throws JMSException for any JMS error
     */
    void stop() throws JMSException;

    /**
     * Set the listener for this session.
     * <p/>
     * The listener is notified whenever a message for the session is present.
     *
     * @param listener the message listener
     */
    void setMessageListener(JmsMessageListener listener);

    /**
     * Enable or disable asynchronous message delivery for a particular
     * consumer.
     *
     * @param consumerId the consumer identifier
     * @param enable     true to enable; false to disable
     * @throws JMSException for any JMS error
     */
    void setAsynchronous(long consumerId, boolean enable)
            throws JMSException;

    /**
     * Recover the session.
     * <p/>
     * All unacknowledged messages are re-delivered with the JMSRedelivered flag
     * set.
     *
     * @throws JMSException if the session cannot be recovered
     */
    void recover() throws JMSException;

    /**
     * Commit the session.
     * <p/>
     * This will acknowledge all delivered messages.
     *
     * @throws JMSException if the session cannot be committed
     */
    void commit() throws JMSException;

    /**
     * Rollback the session.
     * <p/>
     * All messages delivered to the client will be redelivered with the
     * JMSRedelivered flag set.
     *
     * @throws JMSException if the session cannot be rolled back
     */
    void rollback() throws JMSException;

    /**
     * Start work on behalf of a transaction branch specified in xid If TMJOIN
     * is specified, the start is for joining a transaction previously seen by
     * the resource manager
     *
     * @param xid   the xa transaction identity
     * @param flags One of TMNOFLAGS, TMJOIN, or TMRESUME
     * @throws XAException if there is a problem completing the call
     */
    void start(Xid xid, int flags) throws XAException;

    /**
     * Ask the resource manager to prepare for a transaction commit of the
     * transaction specified in xid.
     *
     * @param xid the xa transaction identity
     * @return XA_RDONLY or XA_OK
     * @throws XAException if there is a problem completing the call
     */
    int prepare(Xid xid) throws XAException;

    /**
     * Commits an XA transaction that is in progress.
     *
     * @param xid      the xa transaction identity
     * @param onePhase true if it is a one phase commit
     * @throws XAException if there is a problem completing the call
     */
    void commit(Xid xid, boolean onePhase) throws XAException;

    /**
     * Ends the work performed on behalf of a transaction branch. The resource
     * manager disassociates the XA resource from the transaction branch
     * specified and let the transaction be completedCommits an XA transaction
     * that is in progress.
     *
     * @param xid   the xa transaction identity
     * @param flags one of TMSUCCESS, TMFAIL, or TMSUSPEND
     * @throws XAException if there is a problem completing the call
     */
    void end(Xid xid, int flags) throws XAException;

    /**
     * Tell the resource manager to forget about a heuristically completed
     * transaction branch.
     *
     * @param xid the xa transaction identity
     * @throws XAException if there is a problem completing the call
     */
    void forget(Xid xid) throws XAException;

    /**
     * Obtain a list of prepared transaction branches from a resource manager.
     * The transaction manager calls this method during recovery to obtain the
     * list of transaction branches that are currently in prepared or
     * heuristically completed states.
     *
     * @param flag One of TMSTARTRSCAN, TMENDRSCAN, TMNOFLAGS. TMNOFLAGS
     * @return the set of Xids to recover
     * @throws XAException if there is a problem completing the call
     */
    Xid[] recover(int flag) throws XAException;

    /**
     * Inform the resource manager to roll back work done on behalf of a
     * transaction branch.
     *
     * @param xid the xa transaction identity
     * @throws XAException if there is a problem completing the call
     */
    void rollback(Xid xid) throws XAException;

    /**
     * Return the transaction timeout for this instance of the resource
     * manager.
     *
     * @return the timeout in seconds
     * @throws XAException if there is a problem completing the call
     */
    int getTransactionTimeout() throws XAException;

    /**
     * Set the current transaction timeout value for this XAResource instance.
     *
     * @param seconds timeout in seconds
     * @return if the new transaction timeout was accepted
     * @throws XAException if there is a problem completing the call
     */
    boolean setTransactionTimeout(int seconds) throws XAException;

    /**
     * Return the identity of the associated resource manager.
     *
     * @return the identity of the resource manager
     * @throws XAException if there is a problem completing the call
     */
    String getResourceManagerId() throws XAException;

}
