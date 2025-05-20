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
 * $Id: MessageState.java,v 1.1 2005/09/04 07:07:12 tanderson Exp $
 */
package org.exolab.jms.tools.migration.proxy;


/**
 * Manages the delivery state of a message for a particular consumer.
 *
 * @author <a href="mailto:tma@nespace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/09/04 07:07:12 $
 * @see Subscription
 */
public class MessageState {

    /**
     * The message identifier.
     */
    private String _messageId;

    /**
     * Determines if the message has been delivered to the consumer.
     */
    private boolean _delivered;

    /**
     * Construct a new <code>MessageState</code>.
     *
     * @param messageId the message identifier
     * @param delivered <code>true</code> if the message has been delivered,
     *                  otherwise <code>false</code>
     */
    public MessageState(String messageId, boolean delivered) {
        _messageId = messageId;
        _delivered = delivered;
    }

    /**
     * Returns the message identifier.
     *
     * @return the message identifier
     */
    public String getMessageId() {
        return _messageId;
    }

    /**
     * Returns the message delivery status.
     *
     * @return <code>true</code> if the message has been delivered to the
     *         consumer, otherwise <code>false</code>
     */
    public boolean getDelivered() {
        return _delivered;
    }

}

