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
 * $Id: SessionConsumer.java,v 1.4 2007/01/24 12:00:28 tanderson Exp $
 */
package org.exolab.jms.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.jms.client.JmsMessageListener;
import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.messagemgr.Condition;
import org.exolab.jms.messagemgr.ConsumerEndpoint;
import org.exolab.jms.messagemgr.ConsumerEndpointListener;
import org.exolab.jms.messagemgr.Flag;
import org.exolab.jms.messagemgr.MessageHandle;
import org.exolab.jms.messagemgr.QueueBrowserEndpoint;
import org.exolab.jms.messagemgr.TimedCondition;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.scheduler.Scheduler;
import org.exolab.jms.scheduler.SerialTask;

import javax.jms.JMSException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * Manages all consumers for a session.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2007/01/24 12:00:28 $
 */
class SessionConsumer implements ConsumerEndpointListener {

    /**
     * The message listener is the reference to a remote client that will
     * receive the messages.
     */
    private JmsMessageListener _listener;

    /**
     * Maintain a set of ConsumerEndpoint instances, keyed on id.
     */
    private final HashMap _consumers = new HashMap();

    /**
     * Caches all sent messages.
     */
    private final SentMessageCache _sent;

    /**
     * The database service.
     */
    private final DatabaseService _database;

    /**
     * The set of consumer endpoints with messages pending.
     */
    private final LinkedList _pending = new LinkedList();

    /**
     * Determines if the sender is stopping/stopped.
     */
    private Flag _stop = new Flag(true);

    /**
     * Stop/start lock.
     */
    private final Object _restartLock = new Object();

    /**
     * The active consumer lock.
     */
    private final Object _removeLock = new Object();

    /**
     * The consumer currently being dispatched to.
     */
    private long _consumerId = -1;

    /**
     * The maximum number of messages that a dispatch can deliver at any one
     * time
     */
    private final int MAX_MESSAGES = 200;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(SessionConsumer.class);


    private final SerialTask _runner;

    /**
     * Construct a new <code>SessionConsumer</code>.
     *
     * @param ackMode   the message acknowledgement mode, or
     *                  <code>Session.TRANSACTED_SESSION</code>
     *                  if the session is transactional
     * @param database  the database service
     * @param scheduler the scheduler
     */
    public SessionConsumer(int ackMode, DatabaseService database,
                           Scheduler scheduler) {
        _database = database;
        _sent = new SentMessageCache(ackMode);
        Runnable task = new Runnable() {
            public void run() {
                dispatch();
            }
        };

        _runner = new SerialTask(task, scheduler);
    }

    /**
     * Set the listener for this session.
     * <p/>
     * The listener is notified whenever a message for the session is present.
     *
     * @param listener the message listener
     */
    public synchronized void setMessageListener(JmsMessageListener listener) {
        _listener = listener;
    }

    /**
     * Register a consumer.
     *
     * @param consumer the consumer to add
     */
    public synchronized void addConsumer(ConsumerEndpoint consumer) {
        final long id = consumer.getId();
        _consumers.put(new Long(id), consumer);
        consumer.setListener(this);
    }

    /**
     * Deregister a consumer.
     *
     * @param consumerId the consumer identifier
     * @return the consumer
     * @throws JMSException if the consumer can't be removed
     */
    public ConsumerEndpoint removeConsumer(long consumerId)
            throws JMSException {
        ConsumerEndpoint consumer;
        synchronized (_removeLock) {
            while (consumerId == _consumerId) {
                try {
                    _removeLock.wait();
                } catch (InterruptedException ignore) {
                    // do nothing
                }
            }
            synchronized (this) {
                consumer = (ConsumerEndpoint) _consumers.remove(
                        new Long(consumerId));
                if (consumer == null) {
                    throw new JMSException("No consumer with id=" + consumerId);
                }
                consumer.setListener(null);
            }
            synchronized (_pending) {
                _pending.remove(consumer);
            }
        }

        return consumer;
    }

    /**
     * Returns the consumers.
     *
     * @return the consumers
     */
    public synchronized ConsumerEndpoint[] getConsumers() {
        return (ConsumerEndpoint[]) _consumers.values()
                .toArray(new ConsumerEndpoint[0]);
    }

    /**
     * Enable or disable asynchronous message delivery for a consumer.
     *
     * @param consumerId the consumer identifier
     * @param enable     <code>true</code> to enable; <code>false</code> to
     *                   disable
     * @throws JMSException for any JMS error
     */
    public void setAsynchronous(long consumerId, boolean enable)
            throws JMSException {
        ConsumerEndpoint consumer = getConsumer(consumerId);
        consumer.setAsynchronous(enable);
        if (enable && consumer.getMessageCount() != 0) {
            messageAvailable(consumer);
        }

    }

    /**
     * Stop message delivery.
     */
    public void stop() {
        synchronized (_restartLock) {
            _stop.set(true);
            _runner.stop();
            _log.debug("stopped delivery");
        }
    }

    /**
     * Start message delivery.
     */
    public void start() throws JMSException {
        synchronized (_restartLock) {
            _log.debug("start");
            _stop.set(false);
            for (Iterator i = _consumers.values().iterator(); i.hasNext();) {
                ConsumerEndpoint consumer = (ConsumerEndpoint) i.next();
                if (needsScheduling(consumer)) {
                    queue(consumer);
                }
            }
            try {
                _runner.schedule();
            } catch (InterruptedException exception) {
                _log.error("Failed to start worker", exception);
                throw new JMSException("Failed to start worker: " + exception);
            }
        }
    }

    /**
     * Recover the session.
     * <p/>
     * This will cause all unacknowledged messages to be redelivered.
     *
     * @throws JMSException if the session can't be recovered
     */
    public synchronized void recover() throws JMSException {
        stop();             // stop message delivery
        try {
            _database.begin();
            _sent.clear();  // clear the messages in the sent message cache
            _database.commit();
        } catch (Exception exception) {
            rethrow(exception.getMessage(), exception);
        }
        start();           // restart message delivery
    }

    /**
     * Commit the sesion.
     * <p/>
     * This will acknowledge all sent messages for all consumers.
     *
     * @throws JMSException if the session fails to commit
     */
    public synchronized void commit() throws JMSException {
        try {
            _database.begin();
            _sent.acknowledgeAll();
            _database.commit();
        } catch (OutOfMemoryError exception) {
            rethrow("Failed to commit session due to out-of-memory error",
                    exception);
        } catch (Exception exception) {
            rethrow(exception.getMessage(), exception);
        }
    }

    /**
     * Rollback the session.
     * <p/>
     * This will cause all unacknowledged messages to be redelivered.
     *
     * @throws JMSException for any error
     */
    public synchronized void rollback() throws JMSException {
        stop();             // stop message delivery
        try {
            _database.begin();
            _sent.clear();  // clear the messages in the sent message cache
            _database.commit();
        } catch (Exception exception) {
            rethrow(exception.getMessage(), exception);
        }
        start();           // restart message delivery
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
        MessageImpl result = null;
        if (!_stop.get()) {
            result = doReceive(consumerId, null);
        }
        return result;
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
        MessageImpl result = null;
        Condition condition;
        if (wait > 0) {
            condition = TimedCondition.before(wait);
        } else {
            condition = new Flag(true);
        }
        if (!_stop.get()) {
            result = doReceive(consumerId, condition);
        } else {
            ConsumerEndpoint consumer = getConsumer(consumerId);
            consumer.setWaitingForMessage(condition);
        }
        return result;
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
        ConsumerEndpoint consumer = getConsumer(consumerId);
        if (!(consumer instanceof QueueBrowserEndpoint)) {
            throw new JMSException("Can't browse messages: invalid consumer");
        }

        List messages = new ArrayList(count);

        try {
            _database.begin();
            for (int i = 0; i < count && !_stop.get();) {
                MessageHandle handle = consumer.receive(_stop);
                if (handle == null) {
                    break;
                }
                MessageImpl orig = handle.getMessage();
                if (orig != null) {
                    messages.add(copy(orig, handle));
                    ++i;
                }
            }
            _database.commit();
        } catch (Exception exception) {
            rethrow("Failed to browse messages", exception);
        }
        return messages;
    }

    /**
     * Acknowledge that a message has been processed.
     *
     * @param consumerId the identity of the consumer performing the ack
     * @param messageId  the message identifier
     * @throws JMSException for any error
     */
    public synchronized void acknowledge(long consumerId, String messageId)
            throws JMSException {
        try {
            _database.begin();
            _sent.acknowledge(messageId, consumerId);
            _database.commit();
        } catch (Exception exception) {
            rethrow("Failed to acknowledge message", exception);
        }
    }

    /**
     * Close the consumer.
     *
     * @throws JMSException for any eror
     */
    public synchronized void close() throws JMSException {
        _log.debug("close");
        stop();
        _listener = null;
        try {
            _database.begin();
            _sent.clear();
            _database.commit();
        } catch (Exception exception) {
            rethrow(exception.getMessage(), exception);
        }
    }

    /**
     * Notifies that a message is available for a particular consumer.
     *
     * @param consumer the consumer
     */
    public void messageAvailable(ConsumerEndpoint consumer) {
        if (queue(consumer)) {
            try {
                _runner.schedule();
            } catch (InterruptedException exception) {
                _log.error("Failed to schedule worker", exception);
            }
        }
    }

    /**
     * Send messages to the client.
     */
    private void dispatch() {
        final Condition timeout = TimedCondition.after(30 * 1000);
        Condition done = new Condition() {
            public boolean get() {
                return _stop.get() || timeout.get();
            }
        };

        _log.debug("dispatch");
        int sent = 0;
        while (sent < MAX_MESSAGES && !done.get()) {
            ConsumerEndpoint consumer;
            synchronized (_pending) {
                if (!_pending.isEmpty()) {
                    consumer = (ConsumerEndpoint) _pending.removeFirst();
                } else {
                    break;
                }
            }
            if (wantsMessages(consumer)) {
                if (consumer.isAsynchronous()) {
                    if (send(consumer, done)) {
                        ++sent;
                    }
                    if (needsScheduling(consumer)) {
                        queue(consumer);
                    }
                } else {
                    notifyMessageAvailable();
                }
            }
        }
        boolean empty;
        synchronized (_pending) {
            empty = _pending.isEmpty();
        }
        if (!empty && !_stop.get()) {
            // reschedule this if needed
            try {
                _runner.schedule();
            } catch (InterruptedException exception) {
                _log.error("Failed to reschedule worker", exception);
            }
        }
        _log.debug("dispatch[sent=" + sent + "]");
    }

    private void notifyMessageAvailable() {
        try {
            // notify the client sesssion.
            _listener.onMessageAvailable();
        } catch (RemoteException exception) {
            _log.debug("Failed to notify client", exception);
        }
    }

    private boolean queue(ConsumerEndpoint consumer) {
        boolean queued = false;
        if (!_stop.get()) {
            synchronized (_pending) {
                if (!_pending.contains(consumer)) {
                    _pending.add(consumer);
                    queued = true;
                }
            }
        }
        return queued;
    }

    private boolean send(ConsumerEndpoint consumer, Condition cancel) {
        boolean sent = false;
        MessageHandle handle = null;
        try {
            _database.begin();
            try {
                synchronized (_removeLock) {
                    _consumerId = consumer.getId();
                }
                handle = consumer.receive(cancel);
                if (handle != null) {
                    MessageImpl message = handle.getMessage();
                    if (message != null) {
                        // send the client a copy.
                        message = copy(message, handle);

                        // clear any wait condition
                        // @todo - possible race condition? Could
                        // syncbronous client timeout and request again,
                        // and this trash subsequent wait?
                        consumer.setWaitingForMessage(null);

                        _sent.preSend(handle);
                        _database.commit();

                        // send the message
                        sent = send(message);

                        if (sent) {
                            _database.begin();
                            _sent.postSend(handle);
                            _database.commit();
                        }
                    }
                } else {
                    _database.commit();
                }
            } finally {
                synchronized (_removeLock) {
                    _consumerId = -1;
                    _removeLock.notify();
                }
            }
        } catch (Exception exception) {
            cleanup(exception.getMessage(), exception);
        }
        if (!sent && handle != null) {
            try {
                _database.begin();
                handle.release();
                _database.commit();
            } catch (Exception exception) {
                cleanup("Failed to release unsent message", exception);
            }
        }
        return sent;
    }

    /**
     * Send the specified message to the client.
     *
     * @param message the message
     * @return <code>true</code> if the message was successfully sent
     */
    protected boolean send(MessageImpl message) {
        boolean delivered = false;
        try {
            // send the message to the listener.
            delivered = _listener.onMessage(message);
            if (_log.isDebugEnabled()) {
                _log.debug("send[JMSMessageID=" + message.getMessageId()
                        + ", delivered=" + delivered + "]");
            }
        } catch (RemoteException exception) {
            _log.info("Failed to notify client", exception);
        }
        return delivered;
    }

    private boolean wantsMessages(ConsumerEndpoint consumer) {
        boolean result = false;
        if (consumer.isAsynchronous() || consumer.isWaitingForMessage()) {
            result = true;
        }
        return result;
    }

    private boolean needsScheduling(ConsumerEndpoint consumer) {
        boolean result = false;
        if (wantsMessages(consumer) && consumer.getMessageCount() != 0) {
            result = true;
        }
        return result;
    }

    private MessageImpl doReceive(long consumerId, final Condition wait)
            throws JMSException {
        ConsumerEndpoint consumer = getConsumer(consumerId);

        Condition cancel;
        if (wait != null) {
            cancel = new Condition() {
                public boolean get() {
                    return _stop.get() || !wait.get();
                }
            };
        } else {
            cancel = _stop;
        }

        MessageImpl message = null;
        try {
            _database.begin();
            MessageHandle handle = consumer.receive(cancel);

            if (handle != null) {
                // retrieve the message and copy it
                message = handle.getMessage();
                if (message != null) {
                    message = copy(message, handle);
                }
            }
            if (message == null) {
                // no message available. Mark the consumer as (possibly) waiting
                // for a message.
                consumer.setWaitingForMessage(wait);
            } else {
                // clear any wait condition
                consumer.setWaitingForMessage(null);

                // if we have a non-null message then add it to the sent message
                // cache. Additionally, if we are part of a global transaction
                // then we must also send it to the ResourceManager for recovery.
                _sent.preSend(handle);
            }
            _database.commit();
        } catch (Exception exception) {
            rethrow(exception.getMessage(), exception);
        }
        if (_log.isDebugEnabled()) {
            if (message != null) {
                _log.debug("doReceive(consumerId=" + consumerId +
                        ") -> JMSMesssageID=" + message.getMessageId());
            }
        }

        return message;
    }

    /**
     * Helper to copy a message.
     *
     * @param message the message to copy
     * @param handle  the handle the message came from
     * @return a copy of the message
     * @throws JMSException if the copy fails
     */
    private MessageImpl copy(MessageImpl message, MessageHandle handle)
            throws JMSException {
        MessageImpl result;
        try {
            result = (MessageImpl) message.clone();
            result.setJMSRedelivered(handle.getDelivered());
            result.setConsumerId(handle.getConsumerId());
        } catch (JMSException exception) {
            throw exception;
        } catch (CloneNotSupportedException exception) {
            _log.error(exception, exception);
            throw new JMSException(exception.getMessage());
        }
        return result;
    }

    /**
     * Returns the consumer endpoint given its identifier.
     *
     * @param consumerId the consumer identifier
     * @return the consumer endpoint corresponding to <code>consumerId</code>
     * @throws JMSException if the consumer doesn't exist
     */
    private ConsumerEndpoint getConsumer(long consumerId)
            throws JMSException {
        ConsumerEndpoint consumer
                = (ConsumerEndpoint) _consumers.get(new Long(consumerId));
        if (consumer == null) {
            throw new JMSException("Consumer not registered: " + consumerId);
        }
        return consumer;
    }

    /**
     * Helper to clean up after a failed call.
     *
     * @param message   the message to log
     * @param exception the exception to log
     */
    private void cleanup(String message, Throwable exception) {
        _log.error(message, exception);
        try {
            if (_database.isTransacted()) {
                _database.rollback();
            }
        } catch (PersistenceException error) {
            _log.warn("Failed to rollback after error", error);
        }
    }

    /**
     * Helper to clean up after a failed call, and rethrow.
     *
     * @param message   the message to log
     * @param exception the exception
     * @throws JMSException the original exception adapted to a
     *                      <code>JMSException</code> if necessary
     */
    private void rethrow(String message, Throwable exception)
            throws JMSException {
        cleanup(message, exception);
        if (exception instanceof JMSException) {
            throw (JMSException) exception;
        }
        throw new JMSException(exception.getMessage());
    }

}
