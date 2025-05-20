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
 * $Id: JmsTopicSession.java,v 1.2 2005/03/18 03:36:37 tanderson Exp $
 */
package org.exolab.jms.client;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.TemporaryQueue;
import javax.jms.Topic;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;


/**
 * Client implementation of the <code>javax.jms.TopicSession</code> interface.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/03/18 03:36:37 $
 */
class JmsTopicSession
        extends JmsSession
        implements TopicSession {

    /**
     * Construct a new <code>JmsTopicSession</code>.
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
    public JmsTopicSession(JmsTopicConnection connection, boolean transacted,
                           int ackMode) throws JMSException {
        super(connection, transacted, ackMode);
    }

    /**
     * Create a non-durable subscriber for the specified topic.
     *
     * @param topic the topic to subscriber to
     * @return the new subscriber
     * @throws JMSException if the subscriber cannot be created
     */
    public TopicSubscriber createSubscriber(Topic topic) throws JMSException {
        return createSubscriber(topic, null, false);
    }

    /**
     * Create a non-durable subscriber for the specified topic.
     *
     * @param topic    the topic to subscriber to
     * @param selector the message selector to filter messages. May be
     *                 <code>null</code>
     * @param noLocal  if <code>true</code>, inhibits the delivery of messages
     *                 published by its own connection
     * @return the new subscriber
     * @throws JMSException if the subscriber cannot be created
     */
    public synchronized TopicSubscriber createSubscriber(Topic topic,
                                                         String selector,
                                                         boolean noLocal)
            throws JMSException {
        long consumerId = allocateConsumer(topic, selector, noLocal);
        JmsTopicSubscriber subscriber = new JmsTopicSubscriber(this,
                                                               consumerId,
                                                               topic, selector,
                                                               noLocal);
        addConsumer(subscriber);
        return subscriber;
    }

    /**
     * Create a publisher for the specified topic.
     *
     * @param topic the topic to publish to, or <code>null</code> if this is an
     *              unidentified producer
     * @return the new publisher
     * @throws JMSException if the publisher can't be created
     */
    public synchronized TopicPublisher createPublisher(Topic topic)
            throws JMSException {

        ensureOpen();

        if (topic != null && ((JmsTopic) topic).isWildCard()) {
            throw new JMSException(
                    "Cannot create a publisher using a wildcard topic");
        }

        JmsTopicPublisher publisher =
                new JmsTopicPublisher(this, (JmsTopic) topic);
        addProducer(publisher);

        return publisher;
    }

    /**
     * This implementation always throws <code>IllegalStateException</code>, as
     * per section 4.11 of the JMS specification.
     *
     * @throws IllegalStateException if invoked
     */
    public QueueBrowser createBrowser(Queue queue, String messageSelector)
            throws JMSException {
        throw new IllegalStateException("Invalid operation for TopicSession");
    }

    /**
     * This implementation always throws <code>IllegalStateException</code>, as
     * per section 4.11 of the JMS specification
     *
     * @throws IllegalStateException if invoked.
     */
    public TemporaryQueue createTemporaryQueue() throws JMSException {
        throw new IllegalStateException("Invalid operation for TopicSession");
    }

    /**
     * This implementation always throws <code>IllegalStateException</code>, as
     * per section 4.11 of the JMS specification
     *
     * @throws IllegalStateException if invoked.
     */
    public Queue createQueue(String queueName) throws JMSException {
        throw new IllegalStateException("Invalid operation for TopicSession");
    }

}

