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
 * $Id: BytesMessageHandler.java,v 1.2 2005/10/20 14:07:03 tanderson Exp $
 */
package org.exolab.jms.tools.migration.proxy;

import java.sql.Connection;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;

import org.exolab.jms.message.BytesMessageImpl;
import org.exolab.jms.persistence.PersistenceException;


/**
 * Handler for messages of type <code>javax.jms.TextMessage</code>.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/10/20 14:07:03 $
 */
class BytesMessageHandler extends AbstractMessageHandler {

    /**
     * Construct a new <code>BytesMessageHandle</code>.
     *
     * @param destinations the destination store
     * @param connection   the database connection
     */
    public BytesMessageHandler(DestinationStore destinations,
                               Connection connection) {
        super(destinations, connection);
    }

    /**
     * Returns the type of message that this handler supports.
     *
     * @return the type of message
     */
    protected String getType() {
        return "BytesMessage";
    }

    /**
     * Create a new message.
     *
     * @return a new message
     * @throws JMSException for any JMS error
     */
    protected Message newMessage() throws JMSException {
        return new BytesMessageImpl();
    }

    /**
     * Populate the message body.
     *
     * @param body    the message body
     * @param message the message to populate
     * @throws JMSException         for any JMS error
     * @throws PersistenceException for any persistence error
     */
    protected void setBody(Object body, Message message) throws JMSException,
            PersistenceException {
        if (body != null && !(body instanceof byte[])) {
            throw new JMSException(
                    "Expected byte[] body for BytesMessage with JMSMessageID="
                    + message.getJMSMessageID()
                    + " but got type "
                    + body.getClass().getName());
        }
        BytesMessage bytes = (BytesMessage) message;
        bytes.writeBytes((byte[]) body);
    }

    /**
     * Returns the body of the message.
     *
     * @param message the message
     * @return the body of the message
     * @throws JMSException for any JMS error
     */
    protected Object getBody(Message message) throws JMSException {
        BytesMessage bytes = (BytesMessage) message;
        bytes.reset();
        long length = bytes.getBodyLength();
        if (length > Integer.MAX_VALUE) {
            throw new JMSException("Can't handle BytesMessage, JMSMessageID="
                                   + message.getJMSMessageID()
                                   + ", length=" + length
                                   + " - message too large ");
        }
        byte[] result = new byte[(int) length];
        if (bytes.readBytes(result) != result.length) {
            throw new JMSException("Failed to read BytesMessage");
        }
        return result;
    }

}
