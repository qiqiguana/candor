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
 * $Id: Consumer.java,v 1.2 2005/10/20 14:07:03 tanderson Exp $
 */

package org.exolab.jms.tools.migration.proxy;

import java.util.ArrayList;
import java.util.List;

import org.exolab.jms.client.JmsQueue;


/**
 * Manages the state of an individual consumer.
 *
 * @author <a href="mailto:tma@nespace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/10/20 14:07:03 $
 */
public class Consumer {

    /**
     * The consumer name.
     */
    private final String _name;

    /**
     * The client identifier.
     */
    private final String _clientId;

    /**
     * Determines if this is a queue consumer.
     */
    private final boolean _queueConsumer;

    /**
     * A list of the consumer's {@link Subscription}s.
     */
    private ArrayList _subscriptions = new ArrayList();


    /**
     * Construct a new <code>Consumer</code> for a queue.
     *
     * @param queue the queue
     */
    public Consumer(JmsQueue queue) {
        this(queue.getName(), null, true);
    }

    /**
     * Construct a new <code>Consumer</code> for one or more topics.
     *
     * @param name     the name of the consumer
     * @param clientId the client identifier. May be <code>null</code>
     */
    public Consumer(String name, String clientId) {
        this(name, clientId, false);
    }

    /**
     * Construct a new <code>Consumer</code>.
     *
     * @param name          the name of the consumer
     * @param clientId      the client identifier. May be <code>null</code>
     * @param queueConsumer determines if this is a queue consumer
     */
    private Consumer(String name, String clientId, boolean queueConsumer) {
        _name = name;
        _clientId = clientId;
        _queueConsumer = queueConsumer;
    }

    /**
     * Returns the consumer name. If this is a queue consumer, the name is
     * the same as the queue being consumed.
     *
     * @return the name of the consumer
     */
    public String getName() {
        return _name;
    }

    /**
     * Returns the client identifier. If the consumer represents a queue,
     * then the client identifier is always <code>null</code>.
     *
     * @return the client identifier. May be <code>null</code>
     */
    public String getClientID() {
        return _clientId;
    }

    /**
     * Determines if this is a queue consumer.
     *
     * @return <code>true</code> if this is a queue consumer; otherwise
     * <code>false</code>
     */
    public boolean isQueueConsumer() {
        return _queueConsumer;
    }

    /**
     * Add a subscription.
     *
     * @param subscription the subscription
     */
    public void addSubscription(Subscription subscription) {
        if (_queueConsumer) {
            if (!_subscriptions.isEmpty()) {
                throw new IllegalStateException(
                    "Consumer cannot have multiple subscriptions");
            }
            if (!subscription.getDestination().getName().equals(_name)) {
                throw new IllegalStateException(
                        "Queue consumer subscription mismatch");
            }
        }
        _subscriptions.add(subscription);
    }

    /**
     * Returns the subscriptions.
     *
     * @return a list of {@link Subscription} instances
     */
    public List getSubscriptions() {
        return _subscriptions;
    }

}
