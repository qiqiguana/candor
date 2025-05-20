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
 * Copyright 2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: AbstractMessageConverter.java,v 1.1 2004/11/26 01:50:42 tanderson Exp $
 */

package org.exolab.jms.message;

import java.util.Enumeration;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.exolab.jms.client.JmsDestination;


/**
 * Convert messages to OpenJMS implementations
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:42 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         MessageConverterFactory
 */
abstract class AbstractMessageConverter implements MessageConverter {

    /**
     * Convert a message to its corresponding OpenJMS implementation
     * 
     * @param message the message to convert
     * @return the converted message
     * @throws JMSException for any error
     */
    public Message convert(Message message) throws JMSException {
        Message result = create();
        populate(message, result);
        return result;
    }

    /**
     * Construct an OpenJMS implementation of a message
     *
     * @return the OpenJMS implementation of the message
     * @throws JMSException if the message cannot be created
     */
    protected abstract Message create() throws JMSException;

    /**
     * Populate an OpenJMS message from another message of the same type
     *
     * @param source the message to convert
     * @param target the message to populate
     * @throws JMSException for any error
     */
    protected void populate(Message source, Message target) 
        throws JMSException {

        target.setJMSMessageID(source.getJMSMessageID());
        target.setJMSDestination(source.getJMSDestination());
        target.setJMSTimestamp(source.getJMSTimestamp());
        target.setJMSPriority(source.getJMSPriority());
        target.setJMSExpiration(source.getJMSExpiration());
        target.setJMSDeliveryMode(source.getJMSDeliveryMode());
        target.setJMSCorrelationID(source.getJMSCorrelationID());
        target.setJMSType(source.getJMSType());

        Destination replyTo = source.getJMSReplyTo();
        if (replyTo instanceof JmsDestination) {
            target.setJMSReplyTo(replyTo);
        }
        
        // populate properties
        Enumeration iterator = source.getPropertyNames();
        while (iterator.hasMoreElements()) {
            String name = (String) iterator.nextElement();
            if (!name.startsWith("JMSX") || name.equals("JMSXGroupID")
                || name.equals("JMSXGroupSeq")) {
                // only populate user and supported JMSX properties

                Object value = (Object) source.getObjectProperty(name);
                target.setObjectProperty(name, value);
            }
        }
    }

} 
