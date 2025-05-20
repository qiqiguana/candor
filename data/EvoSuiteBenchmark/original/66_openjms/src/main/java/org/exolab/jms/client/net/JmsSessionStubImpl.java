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
 * Copyright 2000-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: JmsSessionStubImpl.java,v 1.6 2007/01/24 12:00:27 tanderson Exp $
 */
package org.exolab.jms.client.net;

import java.rmi.RemoteException;
import java.util.List;
import javax.jms.JMSException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsMessageListener;
import org.exolab.jms.client.JmsQueue;
import org.exolab.jms.client.JmsTopic;
import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.proxy.Proxy;
import org.exolab.jms.server.ServerSession;


/**
 * Wraps an {@link ServerSession}.
 *
 * @author <a href="mailto:jima@exoffice.com">Jim Alateras</a>
 * @version $Revision: 1.6 $ $Date: 2007/01/24 12:00:27 $
 */
public class JmsSessionStubImpl
        implements ServerSession, JmsMessageListener {

    /**
     * This is the message listener for JMS messages. The MessageListener is
     * then responsible for delivering it to the actual consumer.
     */
    private JmsMessageListener _listener;

    /**
     * The ORB to export this with.
     */
    private ORB _orb;

    /**
     * The reference to the server session.
     */
    private ServerSession _session;


    /**
     * Construct a new <code>JmsSessionStubImpl</code>.
     *
     * @param session     the reference to the server session
     * @param orb         the ORB to export this with
     * @param uri         the URI to export this on
     * @param principal   the security principal. May be <code>null</code>
     * @param credentials the security credentials. May be <code>null</code>
     */
    protected JmsSessionStubImpl(ServerSession session, ORB orb,
                                 String uri, String principal,
                                 String credentials)
            throws RemoteException {
        if (session == null) {
            throw new IllegalArgumentException("Argument 'session' is null");
        }
        if (orb == null) {
            throw new IllegalArgumentException("Argument 'orb' is null");
        }
        _session = session;
        _orb = orb;
        Proxy proxy = orb.exportObjectTo(this, uri, principal, credentials);
        _session.setMessageListener((JmsMessageListener) proxy);
    }

    /**
     * Close and release any resource allocated to this session.
     *
     * @throws JMSException for any JMS error
     */
    public void close() throws JMSException {
        try {
            _session.close();
            _orb.unexportObject(this);
        } catch (RemoteException exception) {
            rethrow(exception);
        } finally {
            if (_session  instanceof Proxy) {
                ((Proxy) _session).disposeProxy();
            }
            _session = null;
            _listener = null;
        }
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
        _session.acknowledgeMessage(consumerId, messageId);
    }

    /**
     * Send a message.
     *
     * @param message the message to send
     * @throws JMSException for any error
     */
    public void send(MessageImpl message) throws JMSException {
        _session.send(message);
    }

    /**
     * Send a set of messages.
     *
     * @param messages a list of <code>MessageImpl</code> instances
     * @throws JMSException for any JMS error
     */
    public void send(List messages) throws JMSException {
        _session.send(messages);
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
        return _session.receiveNoWait(consumerId);
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
        return _session.receive(consumerId, wait);
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
        return _session.browse(consumerId, count);
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
        return _session.createConsumer(destination, selector, noLocal);
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
        return _session.createDurableConsumer(topic, name, selector, noLocal);
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
        return _session.createBrowser(queue, selector);
    }

    /**
     * Close a message consumer.
     *
     * @param consumerId the identity of the consumer to close
     * @throws JMSException for any JMS error
     */
    public void closeConsumer(long consumerId) throws JMSException {
        _session.closeConsumer(consumerId);
    }

    /**
     * Unsubscribe a durable subscription.
     *
     * @param name the name used to identify the subscription
     * @throws JMSException for any JMS error
     */
    public void unsubscribe(String name) throws JMSException {
        _session.unsubscribe(name);
    }

    /**
     * Start message delivery to this session.
     *
     * @throws JMSException for any JMS error
     */
    public void start() throws JMSException {
        _session.start();
    }

    /**
     * Stop message delivery to this session.
     *
     * @throws JMSException for any JMS error
     */
    public void stop() throws JMSException {
        _session.stop();
    }

    /**
     * Set the listener for this session.
     * <p/>
     * The listener is notified whenever a message for the session is present.
     *
     * @param listener the message listener
     */
    public void setMessageListener(JmsMessageListener listener) {
        _listener = listener;
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
        _session.setAsynchronous(consumerId, enable);
    }

    /**
     * Recover the session. This means all unacknowledged messages are resent
     * with the redelivery flag set
     *
     * @throws JMSException if the session cannot be recovered
     */
    public void recover() throws JMSException {
        _session.recover();
    }

    /**
     * Commit the session.
     *
     * @throws JMSException if the session cannot be committed
     */
    public void commit() throws JMSException {
        _session.commit();
    }

    /**
     * Rollback the session.
     *
     * @throws JMSException if the session cannot be rolled back
     */
    public void rollback() throws JMSException {
        _session.rollback();
    }

    /**
     * Start work on behalf of a transaction branch specified in xid. If TMJOIN
     * is specified, the start is for joining a transaction previously seen by
     * the resource manager.
     *
     * @param xid   the xa transaction identity
     * @param flags One of TMNOFLAGS, TMJOIN, or TMRESUME
     * @throws XAException if there is a problem completing the call
     */
    public void start(Xid xid, int flags) throws XAException {
        _session.start(xid, flags);
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
        return _session.prepare(xid);
    }

    /**
     * Commits an XA transaction that is in progress.
     *
     * @param xid      the xa transaction identity
     * @param onePhase true if it is a one phase commit
     * @throws XAException if there is a problem completing the call
     */
    public void commit(Xid xid, boolean onePhase) throws XAException {
        _session.commit(xid, onePhase);
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
        _session.end(xid, flags);
    }

    /**
     * Tell the resource manager to forget about a heuristically completed
     * transaction branch.
     *
     * @param xid the xa transaction identity
     * @throws XAException if there is a problem completing the call
     */
    public void forget(Xid xid) throws XAException {
        _session.forget(xid);
    }

    /**
     * Inform the resource manager to roll back work done on behalf of a
     * transaction branch.
     *
     * @param xid the xa transaction identity
     * @throws XAException if there is a problem completing the call
     */
    public void rollback(Xid xid) throws XAException {
        _session.rollback(xid);
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
        return _session.recover(flag);
    }

    /**
     * Return the transaction timeout for this instance of the resource
     * manager.
     *
     * @return the timeout in seconds
     * @throws XAException if there is a problem completing the call
     */
    public int getTransactionTimeout() throws XAException {
        return _session.getTransactionTimeout();
    }

    /**
     * Set the current transaction timeout value for this XAResource instance.
     *
     * @param seconds timeout in seconds
     * @return if the new transaction timeout was accepted
     * @throws XAException if there is a problem completing the call
     */
    public boolean setTransactionTimeout(int seconds) throws XAException {
        return _session.setTransactionTimeout(seconds);
    }

    /**
     * Return the identity of the associated resource manager.
     *
     * @return the identity of the resource manager
     * @throws XAException if there is a problem completing the call
     */
    public String getResourceManagerId() throws XAException {
        return _session.getResourceManagerId();
    }

    /**
     * Deliver a message.
     *
     * @param message the message to deliver
     * @throws RemoteException if the message can't be delivered
     */
    public boolean onMessage(MessageImpl message) throws RemoteException {
        return _listener.onMessage(message);
    }

    /**
     * Inform the session that there is a message available for a synchronous
     * consumer.
     */
    public void onMessageAvailable() throws RemoteException {
        _listener.onMessageAvailable();
    }

    /**
     * Rethrows a <code>RemoteException</code> as a <code>JMSException</code>.
     *
     * @param exception the exception to rethrow
     * @throws JMSException the rethrown exception
     */
    private void rethrow(RemoteException exception) throws JMSException {
        JMSException error = new JMSException(exception.getMessage());
        error.setLinkedException(exception);
        throw error;
    }

}
