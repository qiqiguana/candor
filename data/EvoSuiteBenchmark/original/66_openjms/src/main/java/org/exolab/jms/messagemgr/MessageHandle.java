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
 * $Id: MessageHandle.java,v 1.2 2005/08/30 07:26:49 tanderson Exp $
 */
package org.exolab.jms.messagemgr;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.message.MessageImpl;


/**
 * A message handle is used to indirectly reference a message.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/08/30 07:26:49 $
 * @see MessageHandleComparator
 */
public interface MessageHandle {

    /**
     * Returns the message identifier.
     *
     * @return the message identifier
     */
    String getMessageId();

    /**
     * Indicates if a message has been delivered to a {@link MessageConsumer},
     * but not acknowledged.
     *
     * @param delivered if <code>true</code> indicates that an attempt has been
     *                  made to deliver the message
     */
    void setDelivered(boolean delivered);

    /**
     * Returns if an attempt has already been made to deliver the message.
     *
     * @return <code>true</code> if delivery has been attempted
     */
    boolean getDelivered();

    /**
     * Returns the priority of the message.
     *
     * @return the message priority
     */
    int getPriority();

    /**
     * Returns the time that the corresponding message was accepted, in
     * milliseconds.
     *
     * @return the time that the corresponding message was accepted
     */
    long getAcceptedTime();

    /**
     * Returns the time that the message expires, in milliseconds.
     *
     * @return the expiry time
     */
    long getExpiryTime();

    /**
     * Determines if the message has expired.
     *
     * @return <code>true</code> if the message has expired, otherwise
     *         <code>false</code>
     */
    boolean hasExpired();

    /**
     * Returns the handle's sequence number.
     *
     * @return the sequence number
     */
    long getSequenceNumber();

    /**
     * Returns the message destination.
     *
     * @return the message destination
     */
    JmsDestination getDestination();

    /**
     * Returns the consumer identity associated with the message.
     *
     * @return the consumer identity associated with the message, or *
     *         <code>-1</code> if the message isn't associated with a consumer
     */
    long getConsumerId();

    /**
     * Returns the connection identity associated with the message.
     *
     * @return the connection identity associated with the message, or
     *         <code>-1</code> if the message isn't associated with a
     *         connection
     */
    long getConnectionId();

    /**
     * Returns the persistent identity of the the consumer endpoint that owns
     * this handle. If it is set, then a consumer owns it exclusively, otherwise
     * the handle may be shared across a number of consumers.
     *
     * @return the consumer's persistent identity, or <code>null</code>
     */
    String getConsumerPersistentId();

    /**
     * Determines if the handle is persistent.
     *
     * @return <code>true</code> if the handle is persistent; otherwise
     *         <code>false</code>
     */
    boolean isPersistent();

    /**
     * Returns the message associated with this handle.
     *
     * @return the associated message, or <code>null</code> if the handle is no
     *         longer valid
     * @throws JMSException for any error
     */
    MessageImpl getMessage() throws JMSException;

    /**
     * Make the handle persistent.
     *
     * @throws JMSException for any persistence error
     */
    void add() throws JMSException;

    /**
     * Update the persistent handle.
     *
     * @throws JMSException for any persistence error
     */
    void update() throws JMSException;

    /**
     * Destroy this handle. If this is the last handle to reference the message,
     * also destroys the message.
     *
     * @throws JMSException for any error
     */
    void destroy() throws JMSException;

    /**
     * Release the message handle back to the cache, to recover an unsent or
     * unacknowledged message.
     *
     * @throws JMSException for any error
     */
    void release() throws JMSException;

    /**
     * Returns the message reference.
     *
     * @return the message reference, or <code>null</code> if none has been set
     */
    MessageRef getMessageRef();


}

