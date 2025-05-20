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
 * $Id: JmsTopicPublisher.java,v 1.2 2005/03/18 03:36:37 tanderson Exp $
 */
package org.exolab.jms.client;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.jms.TopicPublisher;


/**
 * Client implementation of the <code>javax.jms.TopicPublisher</code> interface.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/03/18 03:36:37 $
 */
class JmsTopicPublisher
        extends JmsMessageProducer
        implements TopicPublisher {

    /**
     * Construct a new <code>JmsTopicPublisher</code>.
     *
     * @param session the session constructing this object
     * @param topic   the publisher's destination. May be <code>null</code>.
     */
    public JmsTopicPublisher(JmsSession session, JmsTopic topic) {
        super(session, topic);
    }

    /**
     * Returns the topic associated with this publisher.
     *
     * @return the topic associated with this publisher, or <code>null</code> if
     *         this is an unidentified producer
     */
    public Topic getTopic() {
        return (Topic) getDestination();
    }

    /**
     * Publish a message, using the default delivery mode, priority and time to
     * live.
     *
     * @param message the message to send
     * @throws JMSException if the message can't be sent
     */
    public void publish(Message message) throws JMSException {
        publish(getTopic(), message, getDeliveryMode(), getPriority(),
                getTimeToLive());
    }

    /**
     * Publish a message to a topic for an unidentified message producer, using
     * the default delivery mode, priority and time to live.
     *
     * @param topic   the topic to publish the message to
     * @param message the message to send
     * @throws JMSException if the message can't be sent
     */
    public void publish(Topic topic, Message message) throws JMSException {
        publish(topic, message, getDeliveryMode(), getPriority(),
                getTimeToLive());
    }

    /**
     * Publish a message, specifying the delivery mode, priority and time to
     * live.
     *
     * @param message      the message to send
     * @param deliveryMode the delivery mode to use
     * @param priority     the message priority
     * @param timeToLive   the message's lifetime (in milliseconds).
     * @throws JMSException if the message can't be sent
     */
    public void publish(Message message, int deliveryMode, int priority,
                        long timeToLive) throws JMSException {
        publish(getTopic(), message, deliveryMode, priority, timeToLive);
    }

    /**
     * Publish a message to a topic for an unidentified message producer,
     * specifying the delivery mode, priority and time to live.
     *
     * @param topic        the topic to publish the message to
     * @param message      the message to send
     * @param deliveryMode the delivery mode to use
     * @param priority     the message priority
     * @param timeToLive   the message's lifetime (in milliseconds).
     * @throws JMSException if the message can't be sent
     */
    public void publish(Topic topic, Message message, int deliveryMode,
                        int priority, long timeToLive) throws JMSException {
        send(topic, message, deliveryMode, priority, timeToLive);
    }

}
