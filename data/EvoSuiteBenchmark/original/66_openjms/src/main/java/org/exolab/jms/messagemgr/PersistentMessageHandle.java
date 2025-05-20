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
 * $Id: PersistentMessageHandle.java,v 1.4 2005/10/20 14:20:27 tanderson Exp $
 */
package org.exolab.jms.messagemgr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.message.MessageImpl;
import org.exolab.jms.persistence.DatabaseService;
import org.exolab.jms.persistence.PersistenceException;

import javax.jms.JMSException;
import java.sql.Connection;


/**
 * A persistent message handle extends {@link MessageHandle} and references a
 * persistent message. These messages can be discarded from the cache and later
 * faulted in.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2005/10/20 14:20:27 $
 */
public class PersistentMessageHandle extends AbstractMessageHandle {

    /**
     * The persistent identity of the message consumer.
     */
    private final String _persistentId;

    /**
     * The logger.
     */
    private static final Log _log
            = LogFactory.getLog(PersistentMessageHandle.class);


    /**
     * Construct a new <code>PersistentMessageHandle</code>, for a particular
     * consumer.
     *
     * @param message      the message to construct the handle for
     * @param persistentId the persistent identity of the consumer.
     *                     May be <code>null</code>.
     * @throws JMSException for any error
     */
    public PersistentMessageHandle(MessageImpl message, String persistentId)
            throws JMSException {
        this(message.getJMSMessageID(), message.getJMSPriority(),
             message.getAcceptedTime(), message.getSequenceNumber(),
             message.getJMSExpiration(),
             (JmsDestination) message.getJMSDestination(), persistentId);
    }

    /**
     * Construct a new <code>PersistentMessageHandle</code>.
     *
     * @param messageId      the message identifier
     * @param priority       the message priority
     * @param acceptedTime   the time the message was accepted by the server
     * @param sequenceNumber the message sequence number
     * @param expiryTime     the time that the message will expire
     */
    public PersistentMessageHandle(String messageId, int priority,
                                   long acceptedTime, long sequenceNumber,
                                   long expiryTime,
                                   JmsDestination destination) {
        this(messageId, priority, acceptedTime, sequenceNumber, expiryTime,
             destination, null);
    }

    /**
     * Construct a new <code>PersistentMessageHandle</code>, for a particular
     * consumer.
     *
     * @param messageId      the message identifier
     * @param priority       the message priority
     * @param acceptedTime   the time the message was accepted by the server
     * @param sequenceNumber the message sequence number
     * @param expiryTime     the time that the message will expire
     * @param persistentId   the persistent identity of the consumer. May be
     *                       <code>null</code>.
     */
    public PersistentMessageHandle(String messageId, int priority,
                                   long acceptedTime, long sequenceNumber,
                                   long expiryTime,
                                   JmsDestination destination,
                                   String persistentId) {
        super(messageId, priority, acceptedTime, sequenceNumber, expiryTime,
              destination);
        _persistentId = persistentId;
    }

    /**
     * Determines if the handle is persistent.
     *
     * @return <code>true</code>
     */
    public boolean isPersistent() {
        return true;
    }

    /**
     * Make the handle persistent.
     *
     * @throws JMSException for any persistence error
     */
    public void add() throws JMSException {
        try {
            DatabaseService service = DatabaseService.getInstance();
            Connection connection = service.getConnection();
            service.getAdapter().addMessageHandle(connection, this);
        } catch (PersistenceException exception) {
            final String msg = "Failed to make handle persistent";
            _log.error(msg, exception);
            throw new JMSException(msg + ": " + exception.getMessage());
        }
    }

    /**
     * Update this handle.
     *
     * @throws JMSException for any persistence error
     */
    public void update() throws JMSException {
        try {
            DatabaseService service = DatabaseService.getInstance();
            Connection connection = service.getConnection();
            service.getAdapter().updateMessageHandle(connection, this);
        } catch (PersistenceException exception) {
            final String msg = "Failed to update persistent handle";
            _log.error(msg, exception);
            throw new JMSException(msg + ": " + exception.getMessage());
        }
    }

    /**
     * Reference a message.
     *
     * @throws JMSException for any error
     */
    public void reference(MessageRef reference) throws JMSException {
        reference.reference();
        setMessageRef(reference);
    }

    /**
     * Returns the persistent identity of the the consumer endpoint that owns
     * this handle. If it is set, then a consumer owns it exclusively, otherwise
     * the handle may be shared across a number of consumers.
     *
     * @return the consumer's persistent identity, or <code>null</code>
     */
    public String getConsumerPersistentId() {
        return _persistentId;
    }

    /**
     * Destroy this handle. If this is the last handle to reference the message,
     * also destroys the message.
     *
     * @throws JMSException for any error
     */
    public void destroy() throws JMSException {
        try {
            DatabaseService service = DatabaseService.getInstance();
            Connection connection = service.getConnection();
            service.getAdapter().removeMessageHandle(connection, this);
        } catch (PersistenceException exception) {
            final String msg = "Failed to destroy persistent handle";
            _log.error(msg, exception);
            throw new JMSException(msg + ": " + exception.getMessage());
        }
        super.destroy();
    }

}

