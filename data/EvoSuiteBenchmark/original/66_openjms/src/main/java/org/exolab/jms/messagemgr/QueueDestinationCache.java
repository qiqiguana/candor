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
 * Copyright 2001-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: QueueDestinationCache.java,v 1.9 2006/06/09 12:58:56 tanderson Exp $
 */
package org.exolab.jms.messagemgr;

import java.sql.Connection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsQueue;
import org.exolab.jms.client.JmsTemporaryDestination;
import org.exolab.jms.lease.LeaseManager;
import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.selector.Selector;
import org.exolab.jms.server.ServerConnectionManager;


/**
 * A {@link DestinationCache} for queues.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.9 $ $Date: 2006/06/09 12:58:56 $
 */
public class QueueDestinationCache extends AbstractDestinationCache {

    /**
     * Maintains a list of {@link QueueConsumerMessageHandle} instances.
     */
    private final MessageQueue _handles = new MessageQueue();

    /**
     * Maintains a list of queue browsers for this cache.
     */
    private final List _browsers
            = Collections.synchronizedList(new LinkedList());

    /**
     * The connection manager.
     */
    private final ServerConnectionManager _connections;

    /**
     * Synchronization helper.
     */
    private final Object _lock = new Object();

    /**
     * Index of the last {@link QueueConsumerEndpoint} that received a message
     * from this destination. If multiple consumers are attached to this queue
     * then messages will be sent to each in a round robin fashion
     */
    private int _lastConsumerIndex = 0;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(
            QueueDestinationCache.class);


    /**
     * Construct a new <code>QueueDestinationCache</code>.
     *
     * @param queue       the queue to cache messages for
     * @param database    the database service
     * @param leases      the lease manager
     * @param connections the connection manager
     * @throws JMSException if the cache can't be initialised
     */
    public QueueDestinationCache(JmsQueue queue,
                                 DatabaseService database,
                                 LeaseManager leases,
                                 ServerConnectionManager connections)
            throws JMSException {
        super(queue, database, leases);
        if (connections == null) {
            throw new IllegalArgumentException(
                    "Argument 'connections' is null");
        }
        _connections = connections;

        if (queue.getPersistent()) {
            init();
        }
    }

    /**
     * A Queue can also hav a queue listener, which simply gets informed of all
     * messages that arrive at this destination.
     *
     * @param listener - queue listener
     */
    public void addQueueListener(QueueBrowserEndpoint listener) {
        // add if not present
        if (!_browsers.contains(listener)) {
            _browsers.add(listener);
        }
    }

    /**
     * Remove the queue listener associated with this cache
     *
     * @param listener - queue listener to remove
     */
    public void removeQueueListener(QueueBrowserEndpoint listener) {
        // add if not present
        if (_browsers.contains(listener)) {
            _browsers.remove(listener);
        }
    }

    /**
     * Invoked when the {@link MessageMgr} receives a non-persistent message.
     *
     * @param destination the message's destination
     * @param message     the message
     * @throws JMSException if the listener fails to handle the message
     */
    public void messageAdded(JmsDestination destination, MessageImpl message)
            throws JMSException {
        MessageRef reference = new CachedMessageRef(message, false,
                getMessageCache());
        MessageHandle shared = new SharedMessageHandle(this, reference,
                message);
        MessageHandle handle = new QueueConsumerMessageHandle(shared);

        // all messages are added to this queue. Receivers will
        // then pick messages from it as required.
        addMessage(reference, message, handle);

        // if we have any registered consumers then we need to
        // send the message to one of them first.
        ConsumerEndpoint consumer = getConsumerForMessage(message);
        if (consumer != null) {
            consumer.messageAdded(handle, message);
        }
    }

    /**
     * Invoked when the {@link MessageMgr} receives a persistent message.
     *
     * @param destination the message's destination
     * @param message     the message
     * @throws JMSException         if the listener fails to handle the message
     * @throws PersistenceException if there is a persistence related problem
     */
    public void persistentMessageAdded(JmsDestination destination,
                                       MessageImpl message)
            throws JMSException, PersistenceException {
        MessageRef reference = new CachedMessageRef(message, true,
                getMessageCache());
        MessageHandle shared = new SharedMessageHandle(this, reference,
                message);
        MessageHandle handle = new QueueConsumerMessageHandle(shared);
        handle.add();

        addMessage(reference, message, handle);

        // if there are any registered consumers, notify one of them that
        // a message has arrived
        ConsumerEndpoint consumer = getConsumerForMessage(message);
        if (consumer != null) {
            consumer.persistentMessageAdded(handle, message);
        }
    }

    /**
     * Returns the first available message matching the supplied message
     * selector.
     *
     * @param selector the message selector to use. May be <code>null</code>
     * @param cancel
     * @return handle to the first message, or <code>null</code> if there are no
     *         messages, or none matching <code>selector</code>
     * @throws JMSException for any error
     */
    public synchronized MessageHandle getMessage(Selector selector,
                                                 Condition cancel)
            throws JMSException {
        QueueConsumerMessageHandle handle = null;
        if (selector == null) {
            // if no selector has been specified then remove and return
            // the first message
            handle = (QueueConsumerMessageHandle) _handles.removeFirst();
        } else {
            // for non null selector we must find the first matching
            MessageHandle[] handles = _handles.toArray();
            for (int i = 0; i < handles.length && !cancel.get(); ++i) {
                MessageHandle hdl = handles[i];
                MessageImpl message = hdl.getMessage();
                if (message != null && selector.selects(message)) {
                    handle = (QueueConsumerMessageHandle) hdl;
                    _handles.remove(handle);
                    break;
                }
            }
        }
        return handle;
    }

    /**
     * Playback all the messages in the cache to the specified {@link
     * QueueBrowserEndpoint}.
     *
     * @param browser the queue browser
     * @throws JMSException for any error
     */
    public void playbackMessages(QueueBrowserEndpoint browser)
            throws JMSException {
        MessageHandle[] handles = _handles.toArray();
        for (int i = 0; i < handles.length; ++i) {
            MessageHandle handle = handles[i];
            MessageImpl message = handle.getMessage();
            if (message != null) {
                browser.messageAdded(handle, message);
            }
        }
    }

    /**
     * Return a message handle back to the cache, to recover unsent or
     * unacknowledged messages.
     *
     * @param handle the message handle to return
     */
    public void returnMessageHandle(MessageHandle handle) {
        // add the message to the destination cache
        _handles.add(handle);
        try {
            MessageImpl message = handle.getMessage();
            if (message != null) {
                // if there are any registered consumers, notify one of them
                // that a message has arrived
                ConsumerEndpoint consumer = getConsumerForMessage(message);
                if (consumer != null) {
                    consumer.messageAdded(handle, message);
                }
            }
        } catch (JMSException exception) {
            _log.debug(exception, exception);
        }
    }

    /**
     * Determines if there are any registered consumers.
     *
     * @return <code>true</code> if there are registered consumers
     */
    public boolean hasConsumers() {
        boolean active = super.hasConsumers();
        if (!active && !_browsers.isEmpty()) {
            active = true;
        }
        if (_log.isDebugEnabled()) {
            _log.debug("hasActiveConsumers()[queue=" + getDestination() + "]="
                    + active);
        }
        return active;
    }

    /**
     * Returns the number of messages in the cache.
     *
     * @return the number of messages in the cache
     */
    public int getMessageCount() {
        return _handles.size();
    }

    /**
     * Determines if this cache can be destroyed. A <code>QueueDestinationCache</code>
     * can be destroyed if there are no active consumers and: <ul> <li>the queue
     * is persistent and there are no messages</li> <li> the queue is temporary
     * and the corresponding connection is closed </li> </ul>
     *
     * @return <code>true</code> if the cache can be destroyed, otherwise
     *         <code>false</code>
     */
    public boolean canDestroy() {
        boolean destroy = false;
        if (!hasConsumers()) {
            JmsDestination queue = getDestination();
            if (queue.getPersistent() && getMessageCount() == 0) {
                destroy = true;
            } else if (queue.isTemporaryDestination()) {
                // check if there is a corresponding connection. If
                // not, it has been closed, and the cache can be removed
                long connectionId =
                        ((JmsTemporaryDestination) queue).getConnectionId();
                if (_connections.getConnection(connectionId) == null) {
                    destroy = true;
                }
            }
        }
        return destroy;
    }

    /**
     * Destroy this object.
     */
    public void destroy() {
        super.destroy();
        _browsers.clear();
    }

    /**
     * Initialise the cache. This removes all the expired messages, and then
     * retrieves all unacked messages from the database and stores them
     * locally.
     *
     * @throws JMSException if the cache can't be initialised
     */
    protected void init() throws JMSException {
        JmsDestination queue = getDestination();

        List handles;
        DatabaseService service = null;
        try {
            service = DatabaseService.getInstance();
            Connection connection = service.getConnection();
            service.getAdapter().removeExpiredMessageHandles(connection,
                    queue.getName());
            handles = service.getAdapter().getMessageHandles(connection, queue,
                    queue.getName());
        } catch (PersistenceException exception) {
            _log.error(exception, exception);
            try {
                if (service != null) {
                    service.rollback();
                }
            } catch (PersistenceException error) {
                _log.error(error, error);
            }
            throw new JMSException(exception.getMessage());
        }

        Iterator iterator = handles.iterator();
        DefaultMessageCache cache = getMessageCache();
        while (iterator.hasNext()) {
            PersistentMessageHandle handle = (PersistentMessageHandle) iterator.next();
            String messageId = handle.getMessageId();
            MessageRef reference = cache.getMessageRef(messageId);
            if (reference == null) {
                reference = new CachedMessageRef(messageId, true, cache);
            }
            cache.addMessageRef(reference);
            handle.reference(reference);
            handle.setDestinationCache(this);
            _handles.add(new QueueConsumerMessageHandle(handle));

            checkMessageExpiry(reference, handle.getExpiryTime());
        }
    }

    /**
     * Add a message, and notify any listeners.
     *
     * @param reference a reference to the message
     * @param message   the message
     * @param handle    the handle to add
     * @throws JMSException for any error
     */
    protected void addMessage(MessageRef reference, MessageImpl message,
                              MessageHandle handle) throws JMSException {
        addMessage(reference, message);
        _handles.add(handle);

        // notify any queue listeners that a message has arrived
        notifyQueueListeners(handle, message);

        // create a lease iff one is required
        checkMessageExpiry(reference, message);
    }


    /**
     * Notify queue browsers that a message has arrived.
     *
     * @param handle  a handle to the message
     * @param message the message
     * @throws JMSException if a browser fails to handle the message
     */
    protected void notifyQueueListeners(MessageHandle handle,
                                        MessageImpl message)
            throws JMSException {
        QueueBrowserEndpoint[] browsers =
                (QueueBrowserEndpoint[]) _browsers.toArray(
                        new QueueBrowserEndpoint[0]);

        for (int index = 0; index < browsers.length; ++index) {
            QueueBrowserEndpoint browser = browsers[index];
            browser.messageAdded(handle, message);
        }
    }

    /**
     * Remove an expired non-peristent message, and notify any listeners.
     *
     * @param reference the reference to the expired message
     * @throws JMSException for any error
     */
    protected void messageExpired(MessageRef reference) throws JMSException {
        _handles.remove(reference.getMessageId());
        // @todo - notify browser
        super.messageExpired(reference);
    }

    /**
     * Remove an expired persistent message, and notify any listeners.
     *
     * @param reference the reference to the expired message
     * @throws JMSException         if a listener fails to handle the
     *                              expiration
     * @throws PersistenceException if there is a persistence related problem
     */
    protected void persistentMessageExpired(MessageRef reference)
            throws JMSException, PersistenceException {
        _handles.remove(reference.getMessageId());
        // @todo - notify browsers
        super.messageExpired(reference);
    }

    /**
     * Return the next QueueConsumerEndpoint that can consume the specified
     * message or null if there is none.
     *
     * @param message - the message to consume
     * @return the consumer who should receive this message, or null
     */
    private ConsumerEndpoint getConsumerForMessage(MessageImpl message) {
        ConsumerEndpoint result = null;

        ConsumerEndpoint[] consumers = getConsumerArray();
        final int size = consumers.length;
        if (size > 0) {
            synchronized (_lock) {
                // roll over the consumer index if it is greater
                // than the number of registered consumers
                if ((_lastConsumerIndex + 1) > size) {
                    _lastConsumerIndex = 0;
                }

                // look over the list of consumers and return the
                // first endpoint that can process this message
                int index = _lastConsumerIndex;
                do {
                    ConsumerEndpoint consumer = consumers[index];
                    // if the endpoint has a message listener registered
                    // or the endpoint is waiting for a message and the
                    // message satisfies the selector then return it to
                    // the client.
                    if ((consumer.isAsynchronous()
                            || consumer.isWaitingForMessage())
                            && consumer.selects(message)) {
                        _lastConsumerIndex = ++index;
                        result = consumer;
                        break;
                    }

                    // advance to the next consumer
                    if (++index >= size) {
                        index = 0;
                    }
                } while (index != _lastConsumerIndex);
            }
        }

        return result;
    }

}
