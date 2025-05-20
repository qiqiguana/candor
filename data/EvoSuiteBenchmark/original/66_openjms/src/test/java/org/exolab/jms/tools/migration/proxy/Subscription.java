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
 * Copyright 2002 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: Subscription.java,v 1.1 2005/09/04 07:07:12 tanderson Exp $
 */

package org.exolab.jms.tools.migration.proxy;

import java.util.ArrayList;
import java.util.List;

import org.exolab.jms.client.JmsDestination;


/**
 * Maintains the state of a consumer's subcription to a single destination.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/09/04 07:07:12 $
 */
public class Subscription {

    /**
     * A list of {@link MessageState} instances.
     */
    private ArrayList _messages = new ArrayList();

    /**
     * The destination that messages are being consumed from.
     */
    private JmsDestination _destination;


    /**
     * Construct a new <code>Subscription</code>
     *
     * @param destination the destination being subscribed to
     */
    public Subscription(JmsDestination destination) {
        _destination = destination;
    }

    /**
     * Returns the destination being subscribed to.
     *
     * @return the destination
     */
    public JmsDestination getDestination() {
        return _destination;
    }

    /**
     * Add a reference to a message.
     *
     * @param messageId the message identifier
     * @param delivered determines if the message has been delivered or not
     */
    public void addMessage(String messageId, boolean delivered) {
        _messages.add(new MessageState(messageId, delivered));
    }

    /**
     * Returns the messages that the subscription references.
     *
     * @return a list of {@link MessageState} instances
     */
    public List getMessages() {
        return _messages;
    }

}
