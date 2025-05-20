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
 * $Id: QueueBrowserEndpoint.java,v 1.3 2005/08/30 07:26:49 tanderson Exp $
 */
package org.exolab.jms.messagemgr;

import javax.jms.InvalidSelectorException;
import javax.jms.JMSException;

import org.exolab.jms.message.MessageImpl;


/**
 * A QueueBrowserEndpoint is a QueueListener to a QueueDestinationCache. This
 * enables it to receive all the messages, which it then feeds down to the
 * client side.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson
 * @version $Revision: 1.3 $ $Date: 2005/08/30 07:26:49 $
 */
public class QueueBrowserEndpoint extends AbstractConsumerEndpoint {

    /**
     * Cache of all handles for this consumer.
     */
    private MessageQueue _handles = new MessageQueue();

    /**
     * The destination that this consumer subscribes to.
     */
    private QueueDestinationCache _cache;


    /**
     * Create a new <code>QueueBrowserEndpoint</code>.
     *
     * @param consumerId the identity of this consumer
     * @param cache      the destination cache
     * @param selector   the message selector. May be <code>null</code>
     * @throws InvalidSelectorException if <code>selector</code> is invalid
     * @throws JMSException             if the destination cache can't be
     *                                  created
     */
    public QueueBrowserEndpoint(long consumerId, QueueDestinationCache cache,
                                String selector)
            throws InvalidSelectorException, JMSException {
        super(consumerId, cache.getDestination(), selector, false);

        _cache = cache;

        // set up the message cache and register itself as a listener to the
        // cache
        _cache.addQueueListener(this);
        _cache.playbackMessages(this);
    }

    /**
     * This event is called when a non-persistent message is added to a
     * <code>DestinationCache</code>..
     *
     * @param handle  a handle to the added message
     * @param message the added message
     * @return <code>true</code> if the listener accepted the message; otherwise
     *         <code>false</ode>
     */
    public boolean messageAdded(MessageHandle handle, MessageImpl message) {
        _handles.add(handle);

        // notify the consumer
        notifyMessageAvailable();
        return true;
    }

    /**
     * This event is called when a persistent message is added to the
     * <code>DestinationCache</code>.
     *
     * @param handle  a handle to the added message
     * @param message the added message
     * @return <code>true</code>
     */
    public boolean persistentMessageAdded(MessageHandle handle,
                                          MessageImpl message) {
        return messageAdded(handle, message);
    }

    /**
     * This event is called when a message is removed from the
     * <code>DestinationCache</code>.
     *
     * @param messageId the identifier of the removed message
     */
    public void messageRemoved(String messageId) {
        _handles.remove(messageId);
    }

    /**
     * This event is called when a message is removed from the
     * <code>DestinationCache</code>.
     *
     * @param messageId a handle to the removed message
     */
    public void persistentMessageRemoved(String messageId) {
        messageRemoved(messageId);
    }

    /**
     * Return the number of unsent messages in the cache for this consumer.
     *
     * @return the number of unsent messages
     */
    public int getMessageCount() {
        return _handles.size();
    }

    /**
     * Return the next available message to the client.
     *
     * @param cancel
     * @return the next message, or <code>null</code> if none is available
     * @throws JMSException for any error
     */
    protected MessageHandle doReceive(Condition cancel) throws JMSException {
        MessageHandle result = null;
        MessageHandle handle;
        while (!cancel.get() && (handle = _handles.removeFirst()) != null) {
            // ensure the message still exists
            MessageImpl message = handle.getMessage();
            if (message != null) {
                if (selects(message)) {
                    result = handle;
                    // got a message which is applicable for the endpoint
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Closes this endpoint.
     */
    protected void doClose() {
        // unregister from the DestinationCache
        _cache.removeQueueListener(this);
    }

}
