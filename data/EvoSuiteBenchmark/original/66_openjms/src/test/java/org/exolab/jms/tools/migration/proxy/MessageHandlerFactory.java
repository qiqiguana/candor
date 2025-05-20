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
 * $Id: MessageHandlerFactory.java,v 1.1 2005/09/04 07:07:12 tanderson Exp $
 */
package org.exolab.jms.tools.migration.proxy;

import java.sql.Connection;
import javax.jms.BytesMessage;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;


/**
 * Factory for {@link MessageHandler} instances.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/09/04 07:07:12 $
 */
class MessageHandlerFactory {

    /**
     * Create a new <code>MessageHandler</code> for a message.
     *
     * @param message the message
     * @param destinations the destination store
     * @param connection the database connection
     * @return a new handler
     */
    public static MessageHandler create(Message message,
                                        DestinationStore destinations,
                                        Connection connection) {
        MessageHandler result;

        if (message instanceof BytesMessage) {
            result = new BytesMessageHandler(destinations, connection);
        } else if (message instanceof MapMessage) {
            result = new MapMessageHandler(destinations, connection);
        } else if (message instanceof ObjectMessage) {
            result = new ObjectMessageHandler(destinations, connection);
        } else if (message instanceof StreamMessage) {
            result = new StreamMessageHandler(destinations, connection);
        } else if (message instanceof TextMessage) {
            result = new TextMessageHandler(destinations, connection);
        } else {
            result = new DefaultMessageHandler(destinations, connection);
        }
        return result;
    }

    /**
     * Create a new <code>MessageHandler</code> given a message type.
     *
     * @param type    the fully qualified message class name
     * @param destinations the destination store
     * @param connection the database connection
     * @return a new handler
     */
    public static MessageHandler create(String type,
                                        DestinationStore destinations,
                                        Connection connection) {
        AbstractMessageHandler result = null;

        if (type.equals(Message.class.getName())) {
            result = new DefaultMessageHandler(destinations, connection);
        } else if (type.equals(BytesMessage.class.getName())) {
            result = new BytesMessageHandler(destinations, connection);
        } else if (type.equals(MapMessage.class.getName())) {
            result = new MapMessageHandler(destinations, connection);
        } else if (type.equals(ObjectMessage.class.getName())) {
            result = new ObjectMessageHandler(destinations, connection);
        } else if (type.equals(StreamMessage.class.getName())) {
            result = new StreamMessageHandler(destinations, connection);
        } else if (type.equals(TextMessage.class.getName())) {
            result = new TextMessageHandler(destinations, connection);
        }
        return result;
    }

}
