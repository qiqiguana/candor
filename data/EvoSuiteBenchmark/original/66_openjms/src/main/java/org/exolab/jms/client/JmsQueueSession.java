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
 */
package org.exolab.jms.client;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TemporaryTopic;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;


/**
 * Client implementation of the <code>javax.jms.QueueSession</code> interface.
 *
 * @author <a href="mailto:jima@exoffice.com">Jim Alateras</a>
 * @version $Revision: 1.2 $ $Date: 2005/03/18 03:36:37 $
 */
class JmsQueueSession
        extends JmsSession
        implements QueueSession {

    /**
     * Construct a new <code>JmsQueueSession</code>.
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
    public JmsQueueSession(JmsQueueConnection connection, boolean transacted,
                           int ackMode) throws JMSException {
        super(connection, transacted, ackMode);
    }

    /**
     * Create a receiver to receive messages from the specified queue.
     *
     * @param queue the queue to access
     * @return the new receiver
     * @throws JMSException if the receiver cannot be created
     */
    public QueueReceiver createReceiver(Queue queue) throws JMSException {
        return createReceiver(queue, null);
    }

    /**
     * Create a receiver to receive messages from the specified queue.
     *
     * @param queue    the queue to access
     * @param selector the message selector to filter messages. May be
     *                 <code>null</code>
     * @return the new receiver
     * @throws JMSException if the receiver cannot be created
     */
    public QueueReceiver createReceiver(Queue queue, String selector)
            throws JMSException {
        long consumerId = allocateConsumer(queue, selector, false);
        JmsQueueReceiver receiver = new JmsQueueReceiver(this, consumerId,
                                                         queue, selector);
        addConsumer(receiver);
        return receiver;
    }

    /**
     * Create a sender to send messages to the specified queue.
     *
     * @param queue the queue to access, or <code>null</code> if this is an
     *              unidentified producer
     * @return the new sender
     * @throws JMSException if the sender can't be created
     */
    public QueueSender createSender(Queue queue) throws JMSException {
        ensureOpen();
        JmsQueueSender sender = new JmsQueueSender(this, (JmsQueue) queue);
        addProducer(sender);
        return sender;
    }

    /**
     * This implementation always throws <code>IllegalStateException</code>, as
     * per section 4.11 of the JMS specification.
     *
     * @throws IllegalStateException if invoked
     */
    public TopicSubscriber createDurableSubscriber(Topic topic, String name,
                                                   String messageSelector,
                                                   boolean noLocal)
            throws JMSException {
        throw new IllegalStateException("Invalid operation for QueueSession");
    }

    /**
     * This implementation always throws <code>IllegalStateException</code>, as
     * per section 4.11 of the JMS specification.
     *
     * @throws IllegalStateException if invoked
     */
    public Topic createTopic(String topicName) throws JMSException {
        throw new IllegalStateException("Invalid operation for QueueSession");
    }

    /**
     * This implementation always throws <code>IllegalStateException</code>, as
     * per section 4.11 of the JMS specification.
     *
     * @throws IllegalStateException if invoked
     */
    public TemporaryTopic createTemporaryTopic() throws JMSException {
        throw new IllegalStateException("Invalid operation for QueueSession");
    }

    /**
     * This implementation always throws <code>IllegalStateException</code>, as
     * per section 4.11 of the JMS specification.
     *
     * @throws IllegalStateException if invoked
     */
    public void unsubscribe(String name) throws JMSException {
        throw new IllegalStateException("Invalid operation for QueueSession");
    }

}
