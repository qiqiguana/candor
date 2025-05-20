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
 * $Id: JmsSession.java,v 1.6 2007/01/24 12:00:28 tanderson Exp $
 */
package org.exolab.jms.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.IllegalStateException;
import javax.jms.InvalidDestinationException;
import javax.jms.InvalidSelectorException;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.message.BytesMessageImpl;
import org.exolab.jms.message.MapMessageImpl;
import org.exolab.jms.message.MessageConverter;
import org.exolab.jms.message.MessageConverterFactory;
import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.message.MessageSessionIfc;
import org.exolab.jms.message.ObjectMessageImpl;
import org.exolab.jms.message.StreamMessageImpl;
import org.exolab.jms.message.TextMessageImpl;
import org.exolab.jms.server.ServerSession;


/**
 * Client implementation of the <code>javax.jms.Session</code> interface.
 *
 * @author <a href="mailto:jima@exoffice.com">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.6 $ $Date: 2007/01/24 12:00:28 $
 */
class JmsSession implements Session, JmsMessageListener, MessageSessionIfc {

    /**
     * The owner of the session.
     */
    private JmsConnection _connection;

    /**
     * The proxy to the remote session implementation.
     */
    private ServerSession _session = null;

    /**
     * If true, indicates that the session has been closed.
     */
    private volatile boolean _closed = false;

    /**
     * Determines if this session is being closed.
     */
    private boolean _closing = false;

    /**
     * Synchronization helper, used during close().
     */
    private final Object _closeLock = new Object();

    /**
     * This flag determines whether message delivery is enabled or disabled.
     * Message delivery if disabled if the enclosing connection is stopped.
     */
    private boolean _stopped = true;

    /**
     * Indicates whether the consumer or the client will acknowledge any
     * messages it receives. Ignored if the session is transacted. Legal values
     * are <code>Session.AUTO_ACKNOWLEDGE</code>, <code>Session.CLIENT_ACKNOWLEDGE</code>
     * and <code>Session.DUPS_OK_ACKNOWLEDGE</code>.
     */
    private final int _ackMode;

    /**
     * Maintains the a map of JmsMessageConsumer.getConsumerId() ->
     * JmsMessageConsumer objects.
     */
    private HashMap _consumers = new HashMap();

    /**
     * Maintains a list of producers for the session.
     */
    private List _producers = new ArrayList();

    /**
     * Maintain a collection of acked messages for a transacted session. These
     * messages are only sent to the server on commit.
     */
    private List _messagesToSend = new ArrayList();

    /**
     * This is the session's session listener which is used to receive all
     * messages associated with all consumers registered with this session.
     */
    private MessageListener _listener = null;

    /**
     * The message cache holds all messages for the session, allocated by a
     * JmsConnectionConsumer.
     */
    private Vector _messageCache = new Vector();

    /**
     * Monitor used to block consumers, if the session has been stopped, or no
     * messages are available.
     */
    private final Object _receiveLock = new Object();

    /**
     * The identitifier of the consumer performing a blocking receive, or
     * <code>-1</code> if no consumer is currently performing a blocking
     * receive.
     */
    private long _blockingConsumer = -1;

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(JmsSession.class);


    /**
     * Construct a new <code>JmsSession</code>
     *
     * @param connection the owner of the session
     * @param transacted if <code>true</code>, the session is transacted.
     * @param ackMode    indicates whether the consumer or the client will
     *                   acknowledge any messages it receives. This parameter
     *                   will be ignored if the session is transacted. Legal
     *                   values are <code>Session.AUTO_ACKNOWLEDGE</code>,
     *                   <code>Session.CLIENT_ACKNOWLEDGE</code> and
     *                   <code>Session.DUPS_OK_ACKNOWLEDGE</code>.
     * @throws JMSException if the session cannot be created
     */
    public JmsSession(JmsConnection connection, boolean transacted,
                      int ackMode) throws JMSException {
        if (connection == null) {
            throw new IllegalArgumentException("Argument 'connection' is null");
        }

        _connection = connection;
        _ackMode = (transacted) ? SESSION_TRANSACTED : ackMode;

        // construct the remote stub
        _session = connection.getServerConnection().createSession(_ackMode,
                                                                  transacted);

        // set up this instance to be a message listener
        _session.setMessageListener(this);

        // now we need to check whether we should start the session
        if (!connection.isStopped()) {
            start();
        }
    }

    /**
     * Creates a <code>BytesMessage</code> object. A <code>BytesMessage</code>
     * object is used to send a message containing a stream of uninterpreted
     * bytes.
     *
     * @throws JMSException if the JMS provider fails to create this message due
     *                      to some internal error.
     */
    public BytesMessage createBytesMessage() throws JMSException {
        ensureOpen();
        return new BytesMessageImpl();
    }

    /**
     * Creates a <code>MapMessage</code> object. A <code>MapMessage</code>
     * object is used to send a self-defining set of name-value pairs, where
     * names are <code>String</code> objects and values are primitive values in
     * the Java programming language.
     *
     * @throws JMSException if the JMS provider fails to create this message due
     *                      to some internal error.
     */
    public MapMessage createMapMessage() throws JMSException {
        ensureOpen();
        return new MapMessageImpl();
    }

    /**
     * Creates a <code>Message</code> object. The <code>Message</code> interface
     * is the root interface of all JMS messages. A <code>Message</code> object
     * holds all the standard message header information. It can be sent when a
     * message containing only header information is sufficient.
     *
     * @throws JMSException if the JMS provider fails to create this message due
     *                      to some internal error.
     */
    public Message createMessage() throws JMSException {
        ensureOpen();
        return new MessageImpl();
    }

    /**
     * Creates an <code>ObjectMessage</code> object. An <code>ObjectMessage</code>
     * object is used to send a message that contains a serializable Java
     * object.
     *
     * @throws JMSException if the JMS provider fails to create this message due
     *                      to some internal error.
     */
    public ObjectMessage createObjectMessage() throws JMSException {
        ensureOpen();
        return new ObjectMessageImpl();
    }

    /**
     * Creates an initialized <code>ObjectMessage</code> object. An
     * <code>ObjectMessage</code> object is used to send a message that contains
     * a serializable Java object.
     *
     * @param object the object to use to initialize this message
     * @throws JMSException if the JMS provider fails to create this message due
     *                      to some internal error.
     */
    public ObjectMessage createObjectMessage(Serializable object)
            throws JMSException {
        ensureOpen();
        ObjectMessageImpl result = new ObjectMessageImpl();
        result.setObject(object);
        return result;
    }

    /**
     * Creates a <code>StreamMessage</code> object. A <code>StreamMessage</code>
     * object is used to send a self-defining stream of primitive values in the
     * Java programming language.
     *
     * @throws JMSException if the JMS provider fails to create this message due
     *                      to some internal error.
     */
    public StreamMessage createStreamMessage() throws JMSException {
        ensureOpen();
        return new StreamMessageImpl();
    }

    /**
     * Creates a <code>TextMessage</code> object. A <code>TextMessage</code>
     * object is used to send a message containing a <code>String</code>
     * object.
     *
     * @throws JMSException if the JMS provider fails to create this message due
     *                      to some internal error.
     */
    public TextMessage createTextMessage() throws JMSException {
        ensureOpen();
        return new TextMessageImpl();
    }

    /**
     * Creates an initialized <code>TextMessage</code> object. A
     * <code>TextMessage</code> object is used to send a message containing a
     * <code>String</code>.
     *
     * @param text the string used to initialize this message
     * @throws JMSException if the JMS provider fails to create this message due
     *                      to some internal error.
     */
    public TextMessage createTextMessage(String text) throws JMSException {
        ensureOpen();
        TextMessageImpl result = new TextMessageImpl();
        result.setText(text);
        return result;
    }

    /**
     * Determines if the session is transacted.
     *
     * @return <code>true</code> if the session is transacted
     * @throws JMSException if the session is closed
     */
    public boolean getTransacted() throws JMSException {
        ensureOpen();
        return (_ackMode == SESSION_TRANSACTED);
    }

    /**
     * Returns the acknowledgement mode of the session. The acknowledgement mode
     * is set at the time that the session is created. If the session is
     * transacted, the acknowledgement mode is ignored.
     *
     * @return If the session is not transacted, returns the current
     *         acknowledgement mode for the session. If the session is
     *         transacted, returns SESSION_TRANSACTED.
     * @throws JMSException if the JMS provider fails to return the
     *                      acknowledgment mode due to some internal error.
     * @see Connection#createSession
     */
    public int getAcknowledgeMode() throws JMSException {
        ensureOpen();
        return _ackMode;
    }

    /**
     * Creates a <code>MessageProducer</code> to send messages to the specified
     * destination.
     *
     * @param destination the <code>Destination</code> to send to, or null if
     *                    this is a producer which does not have a specified
     *                    destination.
     * @throws JMSException                if the session fails to create a
     *                                     MessageProducer due to some internal
     *                                     error.
     * @throws InvalidDestinationException if an invalid destination is
     *                                     specified.
     */
    public MessageProducer createProducer(Destination destination)
            throws JMSException {
        ensureOpen();
        return new JmsMessageProducer(this, destination);
    }

    /**
     * Creates a <code>MessageConsumer</code> for the specified destination.
     *
     * @param destination the <code>Destination</code> to access.
     * @throws JMSException                if the session fails to create a
     *                                     consumer due to some internal error.
     * @throws InvalidDestinationException if an invalid destination is
     *                                     specified.
     */

    public MessageConsumer createConsumer(Destination destination)
            throws JMSException {
        return createConsumer(destination, null);
    }

    /**
     * Creates a <code>MessageProducer</code> to receive messages from the
     * specified destination, matching particular selection criteria
     *
     * @param destination     the <code>Destination</code> to access
     * @param messageSelector only messages with properties matching the message
     *                        selector expression are delivered. A value of null
     *                        or an empty string indicates that there is no
     *                        message selector for the message consumer.
     * @throws JMSException                if the session fails to create a
     *                                     MessageConsumer due to some internal
     *                                     error.
     * @throws InvalidDestinationException if an invalid destination is
     *                                     specified.
     * @throws InvalidSelectorException    if the message selector is invalid.
     */
    public MessageConsumer createConsumer(Destination destination,
                                          String messageSelector)
            throws JMSException {
        return createConsumer(destination, messageSelector, false);
    }

    /**
     * Creates a <code>MessageConsumer</code> to receive messages from the
     * specified destination, matching particular selection criteria. This
     * method can specify whether messages published by its own connection
     * should be delivered to it, if the destination is a topic. <P>In some
     * cases, a connection may both publish and subscribe to a topic. The
     * consumer <code>noLocal</code> attribute allows a consumer to inhibit the
     * delivery of messages published by its own connection. The default value
     * for this attribute is false. The <code>noLocal</code> value must be
     * supported by destinations that are topics.
     *
     * @param destination     the <code>Destination</code> to access
     * @param messageSelector only messages with properties matching the message
     *                        selector expression are delivered. A value of null
     *                        or an empty string indicates that there is no
     *                        message selector for the message consumer.
     * @param noLocal         if true, and the destination is a topic, inhibits
     *                        the delivery of messages published by its own
     *                        connection.  The behavior for <code>noLocal</code>
     *                        is not specified if the destination is a queue.
     * @throws JMSException                if the session fails to create a
     *                                     MessageConsumer due to some internal
     *                                     error.
     * @throws InvalidDestinationException if an invalid destination is
     *                                     specified.
     * @throws InvalidSelectorException    if the message selector is invalid.
     */
    public MessageConsumer createConsumer(Destination destination,
                                          String messageSelector,
                                          boolean noLocal) throws JMSException {
        long consumerId = allocateConsumer(destination, messageSelector,
                                           noLocal);
        JmsMessageConsumer consumer = new JmsMessageConsumer(this, consumerId,
                                                             destination,
                                                             messageSelector);
        addConsumer(consumer);
        return consumer;
    }

    /**
     * Creates a queue identity given a <code>Queue</code> name.
     * <p/>
     * <P>This facility is provided for the rare cases where clients need to
     * dynamically manipulate queue identity. It allows the creation of a queue
     * identity with a provider-specific name. Clients that depend on this
     * ability are not portable.
     * <p/>
     * <P>Note that this method is not for creating the physical queue. The
     * physical creation of queues is an administrative task and is not to be
     * initiated by the JMS API. The one exception is the creation of temporary
     * queues, which is accomplished with the <code>createTemporaryQueue</code>
     * method.
     *
     * @param queueName the name of this <code>Queue</code>
     * @return a <code>Queue</code> with the given name
     * @throws JMSException if the session fails to create a queue due to some
     *                      internal error.
     */
    public Queue createQueue(String queueName) throws JMSException {
        ensureOpen();

        JmsQueue queue;

        if (queueName != null && queueName.length() > 0) {
            queue = new JmsQueue(queueName);
        } else {
            throw new JMSException(
                    "Cannot create a queue with null or empty name");
        }

        return queue;
    }

    /**
     * Creates a topic identity given a <code>Topic</code> name.
     * <p/>
     * <P>This facility is provided for the rare cases where clients need to
     * dynamically manipulate topic identity. This allows the creation of a
     * topic identity with a provider-specific name. Clients that depend on this
     * ability are not portable.
     * <p/>
     * <P>Note that this method is not for creating the physical topic. The
     * physical creation of topics is an administrative task and is not to be
     * initiated by the JMS API. The one exception is the creation of temporary
     * topics, which is accomplished with the <code>createTemporaryTopic</code>
     * method.
     *
     * @param topicName the name of this <code>Topic</code>
     * @return a <code>Topic</code> with the given name
     * @throws JMSException if the session fails to create a topic due to some
     *                      internal error.
     */
    public Topic createTopic(String topicName) throws JMSException {
        ensureOpen();

        JmsTopic topic;

        if (topicName != null && topicName.length() > 0) {
            topic = new JmsTopic(topicName);
        } else {
            throw new JMSException("Invalid or null topic name specified");
        }

        return topic;
    }

    /**
     * Creates a durable subscriber to the specified topic.
     * <p/>
     * <P>If a client needs to receive all the messages published on a topic,
     * including the ones published while the subscriber is inactive, it uses a
     * durable <code>TopicSubscriber</code>. The JMS provider retains a record
     * of this durable subscription and insures that all messages from the
     * topic's publishers are retained until they are acknowledged by this
     * durable subscriber or they have expired.
     * <p/>
     * <P>Sessions with durable subscribers must always provide the same client
     * identifier. In addition, each client must specify a name that uniquely
     * identifies (within client identifier) each durable subscription it
     * creates. Only one session at a time can have a <code>TopicSubscriber</code>
     * for a particular durable subscription.
     * <p/>
     * <P>A client can change an existing durable subscription by creating a
     * durable <code>TopicSubscriber</code> with the same name and a new topic
     * and/or message selector. Changing a durable subscriber is equivalent to
     * unsubscribing (deleting) the old one and creating a new one.
     * <p/>
     * <P>In some cases, a connection may both publish and subscribe to a topic.
     * The subscriber <code>noLocal</code> attribute allows a subscriber to
     * inhibit the delivery of messages published by its own connection. The
     * default value for this attribute is false.
     *
     * @param topic the non-temporary <code>Topic</code> to subscribe to
     * @param name  the name used to identify this subscription
     * @throws JMSException                if the session fails to create a
     *                                     subscriber due to some internal
     *                                     error.
     * @throws InvalidDestinationException if an invalid topic is specified.
     */
    public TopicSubscriber createDurableSubscriber(Topic topic, String name)
            throws JMSException {
        return createDurableSubscriber(topic, name, null, false);
    }

    /**
     * Creates a durable subscriber to the specified topic, using a message
     * selector and specifying whether messages published by its own connection
     * should be delivered to it.
     * <p/>
     * <P>If a client needs to receive all the messages published on a topic,
     * including the ones published while the subscriber is inactive, it uses a
     * durable <code>TopicSubscriber</code>. The JMS provider retains a record
     * of this durable subscription and insures that all messages from the
     * topic's publishers are retained until they are acknowledged by this
     * durable subscriber or they have expired.
     * <p/>
     * <P>Sessions with durable subscribers must always provide the same client
     * identifier. In addition, each client must specify a name which uniquely
     * identifies (within client identifier) each durable subscription it
     * creates. Only one session at a time can have a <code>TopicSubscriber</code>
     * for a particular durable subscription. An inactive durable subscriber is
     * one that exists but does not currently have a message consumer associated
     * with it.
     * <p/>
     * <P>A client can change an existing durable subscription by creating a
     * durable <code>TopicSubscriber</code> with the same name and a new topic
     * and/or message selector. Changing a durable subscriber is equivalent to
     * unsubscribing (deleting) the old one and creating a new one.
     *
     * @param topic           the non-temporary <code>Topic</code> to subscribe
     *                        to
     * @param name            the name used to identify this subscription
     * @param messageSelector only messages with properties matching the message
     *                        selector expression are delivered.  A value of
     *                        null or an empty string indicates that there is no
     *                        message selector for the message consumer.
     * @param noLocal         if set, inhibits the delivery of messages
     *                        published by its own connection
     * @throws JMSException                if the session fails to create a
     *                                     subscriber due to some internal
     *                                     error.
     * @throws InvalidDestinationException if an invalid topic is specified.
     * @throws InvalidSelectorException    if the message selector is invalid.
     */
    public TopicSubscriber createDurableSubscriber(Topic topic, String name,
                                                   String messageSelector,
                                                   boolean noLocal)
            throws JMSException {
        ensureOpen();

        if (topic == null) {
            throw new InvalidDestinationException("Cannot create durable subscriber: argument 'topic' is "
                                                  + " null");
        }
        if (name == null || name.trim().length() == 0) {
            throw new JMSException("Invalid subscription name specified");
        }

        // check to see if the topic is a temporary topic. You cannot
        // create a durable subscriber for a temporary topic
        if (((JmsTopic) topic).isTemporaryDestination()) {
            throw new InvalidDestinationException(
                    "Cannot create a durable subscriber for a temporary topic");
        }

        long consumerId = _session.createDurableConsumer((JmsTopic) topic, name,
                                                         messageSelector,
                                                         noLocal);
        JmsTopicSubscriber subscriber = new JmsTopicSubscriber(this,
                                                               consumerId,
                                                               topic,
                                                               messageSelector,
                                                               noLocal);
        addConsumer(subscriber);

        return subscriber;
    }

    /**
     * Creates a <code>QueueBrowser</code> object to peek at the messages on the
     * specified queue.
     *
     * @param queue the queue to access
     * @throws JMSException                if the session fails to create a
     *                                     browser due to some internal error.
     * @throws InvalidDestinationException if an invalid destination is
     *                                     specified
     */
    public QueueBrowser createBrowser(Queue queue) throws JMSException {
        return createBrowser(queue, null);
    }

    /**
     * Creates a <code>QueueBrowser</code> object to peek at the messages on the
     * specified queue using a message selector.
     *
     * @param queue           the <code>queue</code> to access
     * @param messageSelector only messages with properties matching the message
     *                        selector expression are delivered. A value of null
     *                        or an empty string indicates that there is no
     *                        message selector for the message consumer.
     * @throws JMSException                if the session fails to create a
     *                                     browser due to some internal error.
     * @throws InvalidDestinationException if an invalid destination is
     *                                     specified
     * @throws InvalidSelectorException    if the message selector is invalid.
     */
    public QueueBrowser createBrowser(Queue queue, String messageSelector)
            throws JMSException {
        ensureOpen();
        if (!(queue instanceof JmsQueue)) {
            throw new InvalidDestinationException("Cannot create QueueBrowser for destination="
                                                  + queue);
        }

        JmsQueue dest = (JmsQueue) queue;
        // check to see if the queue is temporary. A temporary queue
        // can only be used within the context of the owning connection
        if (!checkForValidTemporaryDestination(dest)) {
            throw new InvalidDestinationException(
                    "Cannot create a queue browser for a temporary queue "
                    + "that is not bound to this connection");
        }

        long consumerId = _session.createBrowser(dest, messageSelector);
        JmsQueueBrowser browser = new JmsQueueBrowser(this, consumerId, queue,
                                                      messageSelector);
        addConsumer(browser);
        return browser;
    }

    /**
     * Creates a <code>TemporaryQueue</code> object. Its lifetime will be that
     * of the <code>Connection</code> unless it is deleted earlier.
     *
     * @return a temporary queue identity
     * @throws JMSException if the session fails to create a temporary queue due
     *                      to some internal error.
     */
    public TemporaryQueue createTemporaryQueue() throws JMSException {
        ensureOpen();
        return JmsTemporaryQueue.create(getConnection());
    }

    /**
     * Creates a <code>TemporaryTopic</code> object. Its lifetime will be that
     * of the <code>Connection</code> unless it is deleted earlier.
     *
     * @return a temporary topic identity
     * @throws JMSException if the session fails to create a temporary topic due
     *                      to some internal error.
     */
    public TemporaryTopic createTemporaryTopic() throws JMSException {
        ensureOpen();
        return JmsTemporaryTopic.create(getConnection());
    }

    /**
     * Unsubscribes a durable subscription that has been created by a client.
     * <p/>
     * <P>This method deletes the state being maintained on behalf of the
     * subscriber by its provider.
     * <p/>
     * <P>It is erroneous for a client to delete a durable subscription while
     * there is an active <code>MessageConsumer</code> or
     * <code>TopicSubscriber</code> for the subscription, or while a consumed
     * message is part of a pending transaction or has not been acknowledged in
     * the session.
     *
     * @param name the name used to identify this subscription
     * @throws JMSException                if the session fails to unsubscribe
     *                                     to the durable subscription due to
     *                                     some internal error.
     * @throws InvalidDestinationException if an invalid subscription name is
     *                                     specified.
     */
    public void unsubscribe(String name) throws JMSException {
        ensureOpen();
        _session.unsubscribe(name);
    }

    /**
     * Commit all messages done in this transaction
     *
     * @throws JMSException if the transaction cannot be committed
     */
    public void commit() throws JMSException {
        ensureOpen();
        ensureTransactional();

        // send all the cached messages to the server
        getServerSession().send(_messagesToSend);
        _messagesToSend.clear();

        // commit the session
        getServerSession().commit();
    }

    /**
     * Rollback any messages done in this transaction
     *
     * @throws JMSException if the transaction cannot be rolled back
     */
    public void rollback() throws JMSException {
        ensureOpen();
        ensureTransactional();

        // clear all the cached messages
        _messagesToSend.clear();

        // rollback the session
        getServerSession().rollback();
    }

    /**
     * Close the session. This call will block until a receive or message
     * listener in progress has completed. A blocked message consumer receive
     * call returns <code>null</code> when this session is closed.
     *
     * @throws JMSException if the session can't be closed
     */
    public void close() throws JMSException {
        boolean closing;
        synchronized (_closeLock) {
            closing = _closing;
            _closing = true;
        }
        if (!closing) {
            // must stop first to ensure any active listener completes
            stop();

            // wake up any blocking consumer
            synchronized (_receiveLock) {
                _receiveLock.notifyAll();
            }

            _closed = true;

            // close the producers
            JmsMessageProducer[] producers =
                    (JmsMessageProducer[]) _producers.toArray(
                            new JmsMessageProducer[0]);
            for (int i = 0; i < producers.length; ++i) {
                JmsMessageProducer producer = producers[i];
                producer.close();
            }

            // close the consumers
            JmsMessageConsumer[] consumers =
                    (JmsMessageConsumer[]) _consumers.values().toArray(
                            new JmsMessageConsumer[0]);
            for (int i = 0; i < consumers.length; ++i) {
                JmsMessageConsumer consumer = consumers[i];
                consumer.close();
            }

            // deregister this with the connection
            _connection.removeSession(this);
            _connection = null;

            // clear any cached messages
            _messagesToSend.clear();

            // issue a close to the remote session. This will release any
            // allocated remote resources
            getServerSession().close();
            _session = null;
        }
    }

    /**
     * Stop message delivery in this session, and restart sending messages with
     * the oldest unacknowledged message
     *
     * @throws JMSException if the session can't be recovered
     */
    public void recover() throws JMSException {
        ensureOpen();
        if (getTransacted()) {
            throw new IllegalStateException(
                    "Cannot recover from a transacted session");
        }

        getServerSession().recover();
    }

    /**
     * Returns the message listener associated with the session
     *
     * @return the message listener associated with the session, or
     *         <code>null</code> if no listener is registered
     * @throws JMSException if the session is closed
     */
    public MessageListener getMessageListener() throws JMSException {
        ensureOpen();
        return _listener;
    }

    /**
     * Sets the session's message listener.
     *
     * @param listener the session's message listener
     * @throws JMSException if the session is closed
     */
    public void setMessageListener(MessageListener listener)
            throws JMSException {
        ensureOpen();
        _listener = listener;
    }

    /**
     * Iterates through the list of messages added by an {@link
     * JmsConnectionConsumer}, sending them to the registered listener
     */
    public void run() {
        try {
            while (!_messageCache.isEmpty()) {
                Message message = (Message) _messageCache.remove(0);
                _listener.onMessage(message);
            }
        } catch (Exception exception) {
            _log.error("Error in the Session.run()", exception);
        } finally {
            // Clear message cache
            _messageCache.clear();
        }
    }

    /**
     * Set the message listener for a particular consumer.
     * <p/>
     * If a listener is already registered for the consumer, it will be
     * automatically overwritten
     *
     * @param listener the message listener
     * @throws JMSException if the listener can't be set
     */
    public void setMessageListener(JmsMessageConsumer listener)
            throws JMSException {
        ensureOpen();
        setAsynchronous(listener.getConsumerId(), true);
    }

    /**
     * Remove a message listener
     *
     * @param listener the message listener to remove
     * @throws JMSException if the listener can't be removed
     */
    public void removeMessageListener(JmsMessageConsumer listener)
            throws JMSException {
        ensureOpen();
        setAsynchronous(listener.getConsumerId(), false);
    }

    /**
     * This will start message delivery to this session. If message delivery has
     * already started, or the session is currently being closed then this is a
     * no-op.
     *
     * @throws JMSException if message delivery can't be started
     */
    public void start() throws JMSException {
        ensureOpen();
        synchronized (_closeLock) {
            if (_stopped && !_closing) {
                getServerSession().start();
                _stopped = false;
            }
        }
    }

    /**
     * This will stop message delivery to this session. If message delivery has
     * already stoped then this is a no-op.
     *
     * @throws JMSException if message delivery can't be stopped
     */
    public void stop() throws JMSException {
        ensureOpen();
        synchronized (_closeLock) {
            if (!_stopped) {
                getServerSession().stop();
                _stopped = true;
            }
        }
    }

    /**
     * Acknowledge the specified message. This is only applicable for
     * CLIENT_ACKNOWLEDGE sessions. For other session types, the request is
     * ignored.
     * <p/>
     * Acking a message automatically acks all those that have come before it.
     *
     * @param message the message to acknowledge
     * @throws JMSException if the message can't be acknowledged
     */
    public void acknowledgeMessage(Message message) throws JMSException {
        ensureOpen();
        if (_ackMode == Session.CLIENT_ACKNOWLEDGE) {
            MessageImpl impl = (MessageImpl) message;
            getServerSession().acknowledgeMessage(impl.getConsumerId(),
                                                  impl.getAckMessageID());
        }
    }

    /**
     * Enable or disable asynchronous message delivery for the specified
     * consumer.
     *
     * @param consumerId the consumer identifier
     * @param enable     <code>true</code> to enable; <code>false</code> to
     *                   disable
     * @throws JMSException if message delivery cannot be enabled or disabled
     */
    public void setAsynchronous(long consumerId, boolean enable)
            throws JMSException {
        ensureOpen();
        getServerSession().setAsynchronous(consumerId, enable);
    }

    /**
     * Deliver a message.
     *
     * @param message the message to deliver
     * @return <code>true</code> if the message was delivered; otherwise
     *         <code>false</code>.
     */
    public boolean onMessage(MessageImpl message) {
        boolean delivered = false;
        message.setJMSXRcvTimestamp(System.currentTimeMillis());

        long consumerId = message.getConsumerId();
        JmsMessageConsumer consumer
                = (JmsMessageConsumer) _consumers.get(new Long(consumerId));
        // tag the session that received this message
        message.setSession(this);
        if (consumer != null) {
            // if a listener is defined for the session then send all the
            // messages to that listener regardless if any consumers are
            // have registered listeners...bit confusing but this is what
            // I believe it should do
            if (_listener != null) {
                try {
                    _listener.onMessage(message);
                    delivered = true;
                } catch (Throwable exception) {
                    _log.error("MessageListener threw exception", exception);
                }
            } else {
                delivered = consumer.onMessage(message);
            }
        } else {
            _log.error("Received a message for an inactive consumer");
        }
        return delivered;
    }

    /**
     * Inform the session that there is a message available for a synchronous
     * consumer.
     */
    public void onMessageAvailable() {
        // wake up any blocking consumer
        notifyConsumer();
    }

    /**
     * Receive the next message that arrives within the specified timeout
     * interval. This call blocks until a message arrives, the timeout expires,
     * or this message consumer is closed. A timeout of <code>0</code> never
     * expires and the call blocks indefinitely.
     *
     * @param consumerId the consumer identifier
     * @param timeout    the timeout interval, in milliseconds
     * @return the next message produced for the consumer, or <code>null</code>
     *         if the timeout expires or the consumer concurrently closed
     * @throws JMSException if the next message can't be received
     */
    public MessageImpl receive(long consumerId, long timeout)
            throws JMSException {
        MessageImpl message = null;
        ensureOpen();

        synchronized (_receiveLock) {
            if (_blockingConsumer != -1) {
                throw new IllegalStateException(
                        "Session cannot be accessed concurrently");
            }

            _blockingConsumer = consumerId;

            long start = (timeout != 0) ? System.currentTimeMillis() : 0;
            try {
                while (message == null && !isClosed()) {
                    if (timeout == 0) {
                        message = getServerSession().receive(consumerId, 0);
                    } else {
                        message = getServerSession().receive(consumerId,
                                                             timeout);
                    }
                    if (message == null && !isClosed()) {
                        // no message received in the required time.
                        // Wait for a notification from the server that
                        // a message has become available.
                        try {
                            if (timeout == 0) {
                                _receiveLock.wait();
                            } else {
                                long elapsed = System.currentTimeMillis()
                                        - start;
                                if (elapsed >= timeout) {
                                    // no message received in the required time
                                    break;
                                } else {
                                    // adjust the timeout so that the client
                                    // only waits as long as the original
                                    // timeout
                                    timeout -= elapsed;
                                }
                                _receiveLock.wait(timeout);
                            }
                        } catch (InterruptedException ignore) {
                            // no-op
                        }
                    }
                }

                if (message != null) {
                    message.setSession(this);
                    if (_ackMode == AUTO_ACKNOWLEDGE
                            || _ackMode == DUPS_OK_ACKNOWLEDGE) {
                        getServerSession().acknowledgeMessage(
                                message.getConsumerId(),
                                message.getMessageId().toString());
                    }
                }
            } finally {
                _blockingConsumer = -1;
            }
        }
        return message;
    }

    /**
     * Receive the next message if one is immediately available.
     *
     * @param consumerId the consumer identifier
     * @return the next message produced for this consumer, or <code>null</code>
     *         if one is not available
     * @throws JMSException if the next message can't be received
     */
    public MessageImpl receiveNoWait(long consumerId) throws JMSException {
        ensureOpen();
        MessageImpl message = getServerSession().receiveNoWait(consumerId);
        if (message != null) {
            message.setSession(this);
            if (_ackMode == AUTO_ACKNOWLEDGE
                    || _ackMode == DUPS_OK_ACKNOWLEDGE) {
                getServerSession().acknowledgeMessage(
                        message.getConsumerId(),
                        message.getMessageId().toString());
            }
        }
        return message;
    }

    /**
     * Browse up to count messages.
     *
     * @param consumerId the consumer identifier
     * @param count      the maximum number of messages to receive
     * @return a list of {@link MessageImpl} instances
     * @throws JMSException for any JMS error
     */
    public List browse(long consumerId, int count)
            throws JMSException {
        ensureOpen();
        return getServerSession().browse(consumerId, count);
    }

    /**
     * Send the specified message to the server.
     *
     * @param message the message to send
     * @throws JMSException if the message can't be sent
     */
    protected void sendMessage(Message message) throws JMSException {
        if (getTransacted()) {
            // if the session is transacted then cache the message locally.
            // and wait for a commit or a rollback
            if (message instanceof MessageImpl) {
                try {
                    message = (Message) ((MessageImpl) message).clone();
                } catch (CloneNotSupportedException error) {
                    throw new JMSException(error.getMessage());
                }
            } else {
                message = convert(message);
            }
            _messagesToSend.add(message);
        } else {
            if (!(message instanceof MessageImpl)) {
                message = convert(message);
            }
            getServerSession().send((MessageImpl) message);
        }
    }

    /**
     * Returns the server session.
     *
     * @return the server session
     */
    protected ServerSession getServerSession() {
        return _session;
    }

    /**
     * Return a reference to the connection that created this session.
     *
     * @return the owning connection
     */
    protected JmsConnection getConnection() {
        return _connection;
    }

    /**
     * Creates a new message consumer, returning its identity.
     *
     * @param destination the destination to access
     * @param selector    the message selector. May be <code>null</code>
     * @param noLocal     if true, and the destination is a topic, inhibits the
     *                    delivery of messages published by its own connection.
     *                    The behavior for <code>noLocal</code> is not specified
     *                    if the destination is a queue.
     * @throws JMSException                if the session fails to create a
     *                                     MessageConsumer due to some internal
     *                                     error.
     * @throws InvalidDestinationException if an invalid destination is
     *                                     specified.
     * @throws InvalidSelectorException    if the message selector is invalid.
     */
    protected long allocateConsumer(Destination destination,
                                    String selector, boolean noLocal)
            throws JMSException {
        ensureOpen();

        if (!(destination instanceof JmsDestination)) {
            throw new InvalidDestinationException("Cannot create MessageConsumer for destination="
                                                  + destination);
        }
        JmsDestination dest = (JmsDestination) destination;

        // check to see if the destination is temporary. A temporary destination
        // can only be used within the context of the owning connection
        if (!checkForValidTemporaryDestination(dest)) {
            throw new InvalidDestinationException(
                    "Trying to create a MessageConsumer for a temporary "
                    + "destination that is not bound to this connection");
        }

        return _session.createConsumer(dest, selector, noLocal);
    }

    /**
     * This method checks the destination. If the destination is not temporary
     * then return true. If it is a temporary destination and it is owned by
     * this session's connection then it returns true. If it is a tmeporary
     * destination and it is owned by another connection then it returns false
     *
     * @param destination the destination to check
     * @return <code>true</code> if the destination is valid
     */
    protected boolean checkForValidTemporaryDestination(
            JmsDestination destination) {
        boolean result = false;

        if (destination.isTemporaryDestination()) {
            JmsTemporaryDestination temp =
                    (JmsTemporaryDestination) destination;

            // check  that this temp destination is owned by the session's
            // connection.
            if (temp.validForConnection(getConnection())) {
                result = true;
            }
        } else {
            result = true;
        }

        return result;
    }

    /**
     * Add a consumer to the list of consumers managed by this session.
     *
     * @param consumer the consumer to add
     */
    protected void addConsumer(JmsMessageConsumer consumer) {
        _consumers.put(new Long(consumer.getConsumerId()), consumer);
    }

    /**
     * Remove a consumer, deregistering it on the server.
     *
     * @param consumer the consumer to remove
     * @throws JMSException if removal fails
     */
    protected void removeConsumer(JmsMessageConsumer consumer)
            throws JMSException {
        long consumerId = consumer.getConsumerId();
        try {
            _session.closeConsumer(consumerId);
        } finally {
            _consumers.remove(new Long(consumerId));
        }
    }

    /**
     * Add a producer to the list of producers managed by this session.
     *
     * @param producer the producer to add
     */
    protected void addProducer(JmsMessageProducer producer) {
        _producers.add(producer);
    }

    /**
     * Remove the producer from the list of managed producers.
     *
     * @param producer the producer to remove
     */
    protected void removeProducer(JmsMessageProducer producer) {
        _producers.remove(producer);
    }

    /**
     * Check if the session is closed.
     *
     * @return <code>true</code> if the session is closed
     */
    protected final boolean isClosed() {
        return _closed;
    }

    /**
     * Add a message to the message cache. This message will be processed when
     * the run() method is called.
     *
     * @param message the message to add.
     */
    protected void addMessage(Message message) {
        _messageCache.add(message);
    }

    /**
     * Verifies that the session isn't closed.
     *
     * @throws IllegalStateException if the session is closed
     */
    protected void ensureOpen() throws IllegalStateException {
        if (isClosed()) {
            throw new IllegalStateException(
                    "Cannot perform operation - session has been closed");
        }
    }

    /**
     * Verifies that the session is transactional.
     *
     * @throws IllegalStateException if the session isn't transactional
     */
    private void ensureTransactional() throws IllegalStateException {
        if (_ackMode != SESSION_TRANSACTED) {
            throw new IllegalStateException(
                    "Cannot perform operatiorn - session is not transactional");
        }
    }

    /**
     * Notifies any blocking synchronous consumer.
     */
    private void notifyConsumer() {
        synchronized (_receiveLock) {
            _receiveLock.notifyAll();
        }
    }

    /**
     * Convert a message to its corresponding OpenJMS implementation.
     *
     * @param message the message to convert
     * @return the OpenJMS implementation of the message
     * @throws JMSException for any error
     */
    private Message convert(Message message) throws JMSException {
        MessageConverter converter =
                MessageConverterFactory.create(message);
        return converter.convert(message);
    }

}

