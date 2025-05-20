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
 * $Id: ServerSessionImpl.java,v 1.2 2005/11/18 03:29:41 tanderson Exp $
 */
package org.exolab.jms.server;

import java.util.Iterator;
import java.util.List;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsMessageListener;
import org.exolab.jms.client.JmsQueue;
import org.exolab.jms.client.JmsTopic;
import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.messagemgr.ConsumerEndpoint;
import org.exolab.jms.messagemgr.ConsumerManager;
import org.exolab.jms.messagemgr.Flag;
import org.exolab.jms.messagemgr.MessageManager;
import org.exolab.jms.messagemgr.ResourceManager;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.scheduler.Scheduler;


/**
 * A session represents a server side endpoint to the JMSServer. A client can
 * create producers, consumers and destinations through the session in addi-
 * tion to other functions. A session has a unique identifer which is a comb-
 * ination of clientId-connectionId-sessionId.
 * <p/>
 * A session represents a single-threaded context which implies that it cannot
 * be used with more than one thread concurrently. Threads registered with this
 * session are synchronized.
 *
 * @author <a href="mailto:jima@exoffice.com">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/11/18 03:29:41 $
 * @see ServerConnectionImpl
 */
class ServerSessionImpl implements ServerSession, XAResource {

    /**
     * The connection that created this session.
     */
    private final ServerConnectionImpl _connection;

    /**
     * The message manager.
     */
    private final MessageManager _messages;

    /**
     * The consumer manager.
     */
    private final ConsumerManager _consumerMgr;

    /**
     * The resource manager.
     */
    private final ResourceManager _resources;

    /**
     * Holds the current xid that this session is associated with. A session can
     * olny be associated with one xid at any one time.
     */
    private Xid _xid = null;

    /**
     * Indicates that the session has been closed.
     */
    private Flag _closed = new Flag(false);

    /**
     * The session consumer. All consumers fdr the session are managed by
     * this.
     */
    private final SessionConsumer _consumer;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(ServerSessionImpl.class);


    /**
     * Construct a new <code>ServerSessionImpl</code>.
     *
     * @param connection  the connection that created this session
     * @param ackMode     the acknowledgement mode for the session
     * @param transacted  <code>true</code> if the session is transactional
     * @param messageMgr  the message manager
     * @param consumerMgr the consumer manager
     * @param resourceMgr the resource manager
     * @param database    the database service
     * @param scheduler   the scheduler
     */
    public ServerSessionImpl(ServerConnectionImpl connection, int ackMode,
                             boolean transacted,
                             MessageManager messageMgr,
                             ConsumerManager consumerMgr,
                             ResourceManager resourceMgr,
                             DatabaseService database,
                             Scheduler scheduler) {
        _connection = connection;
        if (transacted) {
            ackMode = Session.SESSION_TRANSACTED;
        }
        _consumer = new SessionConsumer(ackMode, database, scheduler);
        _messages = messageMgr;
        _consumerMgr = consumerMgr;
        _resources = resourceMgr;
    }

    /**
     * Returns the identifier of the connection that created this session.
     *
     * @return the connection identifier
     */
    public long getConnectionId() {
        return _connection.getConnectionId();
    }

    /**
     * Acknowledge that a message has been processed.
     *
     * @param consumerId the identity of the consumer performing the ack
     * @param messageId  the message identifier
     * @throws JMSException for any error
     */
    public void acknowledgeMessage(long consumerId, String messageId)
            throws JMSException {
        _consumer.acknowledge(consumerId, messageId);
    }

    /**
     * Send a message.
     *
     * @param message the message to send
     * @throws JMSException for any error
     */
    public void send(MessageImpl message) throws JMSException {
        if (message == null) {
            throw new JMSException("Argument 'message' is null");
        }

        try {
            // set the connection identity and then let the message manager
            // process it
            message.setConnectionId(_connection.getConnectionId());

            // if there is a global transaction currently in process then
            // we must send the message to the resource manager, otherwise
            // send it directly to the message manager
            if (_xid != null) {
                _resources.logPublishedMessage(_xid, message);
            } else {
                _messages.add(message);
            }
        } catch (JMSException exception) {
            _log.error("Failed to process message", exception);
            throw exception;
        } catch (OutOfMemoryError exception) {
            String msg =
                    "Failed to process message due to out-of-memory error";
            _log.error(msg, exception);
            throw new JMSException(msg);
        } catch (Exception exception) {
            String msg = "Failed to process message";
            _log.error(msg, exception);
            throw new JMSException(msg);
        }
    }

    /**
     * Send a set of messages.
     *
     * @param messages a list of <code>MessageImpl</code> instances
     * @throws JMSException for any JMS error
     */
    public void send(List messages) throws JMSException {
        if (messages == null) {
            throw new JMSException("Argument 'messages' is null");
        }

        Iterator iterator = messages.iterator();
        while (iterator.hasNext()) {
            MessageImpl message = (MessageImpl) iterator.next();
            send(message);
        }
    }

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
    public MessageImpl receiveNoWait(long consumerId) throws JMSException {
        return _consumer.receiveNoWait(consumerId);
    }

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
     * @return the next message or <code>null</code> if none is available
     * @throws JMSException for any JMS error
     */
    public MessageImpl receive(long consumerId, long wait) throws JMSException {
        return _consumer.receive(consumerId, wait);
    }

    /**
     * Browse up to count messages.
     *
     * @param consumerId the consumer identifier
     * @param count      the maximum number of messages to receive
     * @return a list of {@link MessageImpl} instances
     * @throws JMSException for any JMS error
     */
    public List browse(long consumerId, int count) throws JMSException {
        return _consumer.browse(consumerId, count);
    }

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
    public long createConsumer(JmsDestination destination, String selector,
                               boolean noLocal) throws JMSException {
        if (_log.isDebugEnabled()) {
            _log.debug("createConsumer(destination=" + destination
                       + ", selector=" + selector + ", noLocal=" + noLocal
                       + ") [session=" + this + "]");
        }

        if (destination == null) {
            throw new InvalidDestinationException(
                    "Cannot create MessageConsumer for null destination");
        }

        ConsumerEndpoint consumer = _consumerMgr.createConsumer(
                destination, _connection.getConnectionId(), selector, noLocal);
        _consumer.addConsumer(consumer);
        return consumer.getId();
    }

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
    public long createDurableConsumer(JmsTopic topic, String name,
                                      String selector, boolean noLocal)
            throws JMSException {
        if (_log.isDebugEnabled()) {
            _log.debug("createDurableConsumer(topic=" + topic + ", name="
                       + name
                       + ", selector=" + selector + ", noLocal=" + noLocal
                       + ") [session=" + this + "]");
        }

        // if a durable subscriber with the specified name is
        // already active then this method will throw an exception.
        ConsumerEndpoint consumer = _consumerMgr.createDurableConsumer(topic,
                                                                       name,
                                                                       _connection.getClientID(),
                                                                       _connection.getConnectionId(),
                                                                       noLocal,
                                                                       selector);
        _consumer.addConsumer(consumer);
        return consumer.getId();
    }

    /**
     * Create a queue browser for this session. This allows clients to browse a
     * queue without removing any messages.
     *
     * @param queue    the queue to browse
     * @param selector the message selector. May be <code>null</code>
     * @return the identity of the queue browser
     * @throws JMSException for any JMS error
     */
    public long createBrowser(JmsQueue queue, String selector)
            throws JMSException {
        if (_log.isDebugEnabled()) {
            _log.debug("createBrowser(queue=" + queue + ", selector="
                       + selector
                       + ") [session=" + this + "]");
        }

        if (queue == null) {
            throw new JMSException("Cannot create QueueBrowser for null queue");
        }

        ConsumerEndpoint consumer = _consumerMgr.createQueueBrowser(queue,
                                                                    selector);

        _consumer.addConsumer(consumer);
        return consumer.getId();
    }

    /**
     * Close a message consumer.
     *
     * @param consumerId the identity of the consumer to close
     * @throws JMSException for any JMS error
     */
    public void closeConsumer(long consumerId) throws JMSException {
        if (_log.isDebugEnabled()) {
            _log.debug("removeConsumer(consumerId=" + consumerId
                       + ") [session="
                       + this + "]");
        }

        ConsumerEndpoint consumer = _consumer.removeConsumer(consumerId);
        _consumerMgr.closeConsumer(consumer);
    }

    /**
     * Unsubscribe a durable subscription.
     *
     * @param name the name used to identify the subscription
     * @throws JMSException for any JMS error
     */
    public void unsubscribe(String name) throws JMSException {
        if (_log.isDebugEnabled()) {
            _log.debug("unsubscribe(name=" + name + ") [session=" + this + "]");
        }

        _consumerMgr.unsubscribe(name, _connection.getClientID());
    }

    /**
     * Start the message delivery for the session.
     *
     * @throws JMSException for any JMS error
     */
    public void start() throws JMSException {
        if (_log.isDebugEnabled()) {
            _log.debug("start() [session=" + this + "]");
        }
        _consumer.start();
    }

    /**
     * Stop message delivery for the session.
     */
    public void stop() {
        if (_log.isDebugEnabled()) {
            _log.debug("stop() [session=" + this + "]");
        }
        _consumer.stop();
    }

    /**
     * Set the listener for this session.
     * <p/>
     * The listener is notified whenever a message for the session is present.
     *
     * @param listener the message listener
     */
    public void setMessageListener(JmsMessageListener listener) {
        _consumer.setMessageListener(listener);
    }

    /**
     * Enable or disable asynchronous message delivery for a particular
     * consumer.
     *
     * @param consumerId the consumer identifier
     * @param enable     true to enable; false to disable
     * @throws JMSException for any JMS error
     */
    public void setAsynchronous(long consumerId, boolean enable)
            throws JMSException {
        _consumer.setAsynchronous(consumerId, enable);
    }

    /**
     * Close and release any resource allocated to this session.
     *
     * @throws JMSException if the session cannot be closed
     */
    public void close() throws JMSException {
        boolean closed;
        synchronized (_closed) {
            closed = _closed.get();
        }

        if (!closed) {
            _closed.set(true);
            if (_log.isDebugEnabled()) {
                _log.debug("close() [session=" + this + "]");
            }

            _consumer.stop();
            ConsumerEndpoint[] consumers = _consumer.getConsumers();
            for (int i = 0; i < consumers.length; ++i) {
                ConsumerEndpoint consumer = consumers[i];
                _consumer.removeConsumer(consumer.getId());
                _consumerMgr.closeConsumer(consumer);
            }

            _consumer.close();

            // de-register the session from the connection
            _connection.closed(this);
        } else {
            if (_log.isDebugEnabled()) {
                _log.debug("close() [session=" + this +
                           "]: session already closed");
            }
        }
    }

    /**
     * Recover the session.
     * <p/>
     * All unacknowledged messages are re-delivered with the JMSRedelivered flag
     * set.
     *
     * @throws JMSException if the session cannot be recovered
     */
    public void recover() throws JMSException {
        _consumer.recover();
    }

    /**
     * Commit the session.
     * <p/>
     * This will acknowledge all delivered messages.
     *
     * @throws JMSException if the session cannot be committed
     */
    public void commit() throws JMSException {
        _consumer.commit();
    }

    /**
     * Rollback the session.
     * <p/>
     * All messages delivered to the client will be redelivered with the
     * JMSRedelivered flag set.
     *
     * @throws JMSException - if there are any problems
     */
    public void rollback() throws JMSException {
        _consumer.rollback();
    }

    /**
     * Start work on behalf of a transaction branch specified in xid If TMJOIN
     * is specified, the start is for joining a transaction previously seen by
     * the resource manager
     *
     * @param xid   the xa transaction identity
     * @param flags One of TMNOFLAGS, TMJOIN, or TMRESUME
     * @throws XAException if there is a problem completing the call
     */
    public void start(Xid xid, int flags) throws XAException {
        _resources.start(xid, flags);

        // set this as the current xid for this session
        _xid = xid;
    }

    /**
     * Ask the resource manager to prepare for a transaction commit of the
     * transaction specified in xid.
     *
     * @param xid the xa transaction identity
     * @return XA_RDONLY or XA_OK
     * @throws XAException if there is a problem completing the call
     */
    public int prepare(Xid xid) throws XAException {
        return _resources.prepare(xid);
    }

    /**
     * Commits an XA transaction that is in progress.
     *
     * @param xid      the xa transaction identity
     * @param onePhase true if it is a one phase commit
     * @throws XAException if there is a problem completing the call
     */
    public void commit(Xid xid, boolean onePhase) throws XAException {
        try {
            _resources.commit(xid, onePhase);
        } finally {
            _xid = null;
        }
    }

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
    public void end(Xid xid, int flags) throws XAException {
        try {
            _resources.end(xid, flags);
        } finally {
            _xid = null;
        }
    }

    /**
     * Tell the resource manager to forget about a heuristically completed
     * transaction branch.
     *
     * @param xid the xa transaction identity
     * @throws XAException if there is a problem completing the call
     */
    public void forget(Xid xid) throws XAException {
        try {
            _resources.forget(xid);
        } finally {
            _xid = null;
        }
    }

    /**
     * Obtain a list of prepared transaction branches from a resource manager.
     * The transaction manager calls this method during recovery to obtain the
     * list of transaction branches that are currently in prepared or
     * heuristically completed states.
     *
     * @param flag One of TMSTARTRSCAN, TMENDRSCAN, TMNOFLAGS. TMNOFLAGS
     * @return the set of Xids to recover
     * @throws XAException - if there is a problem completing the call
     */
    public Xid[] recover(int flag) throws XAException {
        return _resources.recover(flag);
    }

    /**
     * Inform the resource manager to roll back work done on behalf of a
     * transaction branch
     *
     * @param xid the xa transaction identity
     * @throws XAException if there is a problem completing the call
     */
    public void rollback(Xid xid) throws XAException {
        try {
            _resources.rollback(xid);
        } finally {
            // clear the current xid
            _xid = null;
        }
    }

    /**
     * Return the transaction timeout for this instance of the resource
     * manager.
     *
     * @return the timeout in seconds
     * @throws XAException if there is a problem completing the call
     */
    public int getTransactionTimeout() throws XAException {
        return _resources.getTransactionTimeout();
    }

    /**
     * Set the current transaction timeout value for this XAResource instance.
     *
     * @param seconds timeout in seconds
     * @return if the new transaction timeout was accepted
     * @throws XAException if there is a problem completing the call
     */
    public boolean setTransactionTimeout(int seconds) throws XAException {
        return _resources.setTransactionTimeout(seconds);
    }

    /**
     * This method is called to determine if the resource manager instance
     * represented by the target object is the same as the resouce manager
     * instance represented by the parameter xares.
     *
     * @param xares an XAResource object whose resource manager instance is to
     *              be compared with the resource manager instance of the target
     *              object.
     * @return true if it's the same RM instance; otherwise false.
     * @throws XAException for any error
     */
    public boolean isSameRM(XAResource xares) throws XAException {
        boolean result = (xares instanceof ServerSessionImpl);
        if (result) {
            ServerSessionImpl other = (ServerSessionImpl) xares;
            result = (other.getResourceManagerId() == getResourceManagerId());
        }

        return result;
    }

    /**
     * Return the xid that is currently associated with this session or null if
     * this session is currently not part of a global transactions
     *
     * @return Xid
     */
    public Xid getXid() {
        return _xid;
    }

    /**
     * Return the identity of the {@link ResourceManager}. The transaction
     * manager should be the only one to initiating this call.
     *
     * @return the identity of the resource manager
     * @throws XAException - if it cannot retrieve the rid.
     */
    public String getResourceManagerId() throws XAException {
        return _resources.getResourceManagerId();
    }

}
