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
 * $Id: JmsQueueSender.java,v 1.2 2005/03/18 03:36:37 tanderson Exp $
 */
package org.exolab.jms.client;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.QueueSender;
import javax.jms.Queue;


/**
 * Client implementation of the <code>javax.jms.QueueSender</code> interface.
 *
 * @version     $Revision: 1.2 $ $Date: 2005/03/18 03:36:37 $
 * @author      <a href="mailto:jima@comare.com.au">Jim Alateras</a>
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
class JmsQueueSender
    extends JmsMessageProducer
    implements QueueSender {

    /**
     * Construct a new <code>JmsQueueSender</code>.
     *
     * @param session the session constructing this object
     * @param queue the sender's destination. May be <code>null</code>.
     */
    public JmsQueueSender(JmsSession session, JmsQueue queue) {
        super(session, queue);
    }

    /**
     * Returns the queue associated with this sender.
     *
     * @return the queue associated with this sender, or <code>null</code>
     * if this is an unidentified producer
     */
    public Queue getQueue() {
        return (Queue) getDestination();
    }

    /**
     * Send a message to a queue for an unidentified message producer, using
     * the default delivery mode, priority and time to live.
     *
     * @param queue the queue to send the message to
     * @param message the message to send
     * @throws JMSException if the message can't be sent
     */
    public void send(Queue queue, Message message) throws JMSException {
        send(queue, message, getDeliveryMode(), getPriority(),
             getTimeToLive());
    }

    /**
     * Send a message to a queue for an unidentified message producer,
     * specifying the default delivery mode, priority and time to live.
     *
     * @param queue the queue to send the message to
     * @param message the message to send
     * @param deliveryMode the delivery mode to use
     * @param priority the message priority
     * @param timeToLive the message's lifetime (in milliseconds).
     * @throws JMSException if the message can't be sent
     */
    public void send(Queue queue, Message message, int deliveryMode,
                     int priority, long timeToLive) throws JMSException {
        super.send(queue, message, deliveryMode, priority, timeToLive);
    }

}
