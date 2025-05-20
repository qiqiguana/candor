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
 * $Id: DurableConsumerEndpoint.java,v 1.4 2005/11/12 12:27:40 tanderson Exp $
 */
package org.exolab.jms.messagemgr;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import javax.jms.IllegalStateException;
import javax.jms.InvalidSelectorException;
import javax.jms.JMSException;

import org.exolab.jms.client.JmsTopic;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.persistence.PersistenceException;


/**
 * A {@link ConsumerEndpoint} for durable topic consumers. The state of durable
 * topic consumers is maintained across server invocations by the persistent
 * layer.
 * <p/>
 * DurableConsumerEndpoints are always loaded in memory, whether they are active
 * or inactive. When they are inactive they simply process persistent messages.
 * Non-persistent message are ignored when the durable consumer is inactive.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2005/11/12 12:27:40 $
 */
public class DurableConsumerEndpoint
        extends AbstractTopicConsumerEndpoint {

    /**
     * The persistent name of the durable subscriber.
     */
    private final String _name;

    /**
     * Determines if this active.
     */
    private boolean _active = false;

    /**
     * Synchronization helper when activating the consumer.
     */
    private final Object _activateLock = new Object();


    /**
     * Construct a new <code>DurableConsumerEndpoint</code>.
     * <p/>
     * The consumer is inactive until made active via {@link #activate}.
     *
     * @param consumerId   the identity of this consumer
     * @param topic        the topic to access
     * @param destinations the destination manager
     * @param name         the well known name of the durable subscriber
     * @throws InvalidSelectorException if the selector is invalid
     * @throws JMSException             if the destination caches can't be
     *                                  constructed
     * @throws PersistenceException     for any persistence error
     */
    public DurableConsumerEndpoint(long consumerId, JmsTopic topic,
                                   String name,
                                   DestinationManager destinations)
            throws InvalidSelectorException, JMSException,
                   PersistenceException {
        super(consumerId, -1, topic, null, false, destinations);
        _name = name;

        // register this with the available caches. Note that the consumer
        // may begin receiving messages immediately.
        init();

        DatabaseService service = DatabaseService.getInstance();
        Connection connection = service.getConnection();

        // remove expired messages
        service.getAdapter().removeExpiredMessageHandles(connection, _name);

        TopicDestinationCache cache = (TopicDestinationCache)
                getDestinationManager().getDestinationCache(topic);
        // @todo - broken for wildcard subscriptions
        // getMessageHandles() needs to return all handles for a given
        // subscription name
        List handles = cache.getDurableMessageHandles(_name);

        // iterate over each handle and add them to the list of messages
        // for the durable consumer
        Iterator iterator = handles.iterator();
        while (iterator.hasNext()) {
            MessageHandle handle = (MessageHandle) iterator.next();
            TopicConsumerMessageHandle consumer =
                    new TopicConsumerMessageHandle(handle, this);
            addMessage(consumer);
        }
    }

    /**
     * Determines if this is a persistent or non-persistent consumer.
     * <p/>
     * If persistent, then the consumer is persistent accross subscriptions and
     * server restarts, and {@link #getPersistentId} returns a non-null value
     *
     * @return <code>true</code>
     */
    public boolean isPersistent() {
        return true;
    }

    /**
     * Returns the persistent identifier for this consumer.
     * <p/>
     * This is the identity of the consumer which is persistent across
     * subscriptions and server restarts.
     * <p/>
     * This implementation returns the consumer name.
     *
     * @return the persistent identifier for this consumer
     */
    public String getPersistentId() {
        return _name;
    }

    /**
     * Activate this durable consumer.
     *
     * @param connectionId the identity of the connection that owns this
     *                     consumer
     * @param selector     the message selector. May be <code>null</code>
     * @param noLocal      if true, inhibits the delivery of messages published
     *                     by its own connection.
     * @throws JMSException             if the consumer can't be activated
     * @throws InvalidSelectorException if the selector is invalid
     */
    public void activate(long connectionId, String selector, boolean noLocal)
            throws JMSException {
        synchronized (_activateLock) {
            if (_active) {
                throw new IllegalStateException(
                        "Durable consumer " + _name + " is alrady active");
            }
            setConnectionId(connectionId);
            setSelector(selector);
            setNoLocal(noLocal);
            _active = true;
        }
    }

    /**
     * Deactivate this durable consumer.
     *
     * @throws JMSException if the consumer can't be deactivated
     */
    public void deactivate() throws JMSException {
        synchronized (_activateLock) {
            if (!_active) {
                throw new IllegalStateException(
                        "Durable consumer " + _name + " is alrady inactive");
            }
            setConnectionId(-1);
            setSelector(null);
            _active = false;
        }
    }

    /**
     * Determines if the endpoint is active.
     *
     * @return <code>true</code> if the endpoint is active, <code>false</code>
     *         if it is inactive
     */
    public boolean isActive() {
        synchronized (_activateLock) {
            return _active;
        }
    }

}
