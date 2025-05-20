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
 * $Id: TopicConsumerMessageHandle.java,v 1.2 2005/05/13 12:57:02 tanderson Exp $
 */
package org.exolab.jms.messagemgr;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;


/**
 * A {@link MessageHandle} used by the {@link TopicDestinationCache}.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/05/13 12:57:02 $
 */
class TopicConsumerMessageHandle extends AbstractConsumerMessageHandle {

    /**
     * If <code>true</code>, indicates that the message associated with the
     * handle has been delivered, but not acknowledged.
     * This overrides the delivery status of the underlying handle, which
     * may be shared between multiple consumers.
     */
    private boolean _delivered = false;


    /**
     * Construct a new <code>TopicConsumerMessageHandle</code>.
     *
     * @param handle   the underlying handle
     * @param consumer the consumer of the handle
     * @throws JMSException if the underlying message can't be referenced
     */
    public TopicConsumerMessageHandle(MessageHandle handle,
                                      ConsumerEndpoint consumer)
            throws JMSException {
        super(handle, consumer);
        init(handle);
    }

    /**
     * Construct a new <code>TopicConsumerMessageHandle</code>
     * for a durable consumer.
     *
     * @param handle       the underlying handle
     * @param persistentId the persistent identity of the consumer
     * @throws JMSException if the underlying message can't be referenced
     */
    public TopicConsumerMessageHandle(MessageHandle handle, String persistentId)
            throws JMSException {
        super(handle, persistentId);
        init(handle);
    }

    /**
     * Indicates if a message has been delivered to a {@link MessageConsumer},
     * but not acknowledged.
     *
     * @param delivered if <code>true</code> indicates that an attempt has been
     *                  made to deliver the message
     */
    public void setDelivered(boolean delivered) {
        _delivered = delivered;
    }

    /**
     * Returns if an attempt has already been made to deliver the message.
     *
     * @return <code>true</code> if delivery has been attempted
     */
    public boolean getDelivered() {
        return _delivered;
    }

    /**
     * Initialise this handle.
     *
     * @param handle the underlying handle
     */
    private void init(MessageHandle handle) {
        _delivered = handle.getDelivered();
    }

}
