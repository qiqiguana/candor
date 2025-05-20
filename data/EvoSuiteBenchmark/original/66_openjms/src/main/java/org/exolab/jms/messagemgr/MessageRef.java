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
 * $Id: MessageRef.java,v 1.2 2005/08/30 07:26:49 tanderson Exp $
 */
package org.exolab.jms.messagemgr;

import javax.jms.JMSException;

import org.exolab.jms.message.MessageImpl;


/**
 * A <code>MessageRef</code> is used to indirectly reference a message by {@link
 * MessageHandle} instances. When there are no MessageHandles referencing the
 * message, the message can be destroyed
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/08/30 07:26:49 $
 */
public interface MessageRef {

    /**
     * Returns the message identifier.
     *
     * @return the message identifier
     */
    String getMessageId();

    /**
     * Returns the message associated with this reference.
     *
     * @return the associated message, or <code>null</code> if the reference is
     *         no longer valide
     * @throws JMSException for any error
     */
    MessageImpl getMessage() throws JMSException;

    /**
     * Determines if the underlying message is persistent.
     *
     * @return <code>true</code> if the message is persistent; otherwise
     *         <code>false</code>
     */
    boolean isPersistent();

    /**
     * Increment the reference.
     *
     * @throws JMSException if the handle has been destroyed
     */
    void reference() throws JMSException;

    /**
     * Decrement the reference. If there are no references to the message, it
     * will be destroyed.
     *
     * @throws JMSException for any error
     */
    void dereference() throws JMSException;

    /**
     * Destroy the message, irrespective of the number of current references.
     *
     * @throws JMSException for any error
     */
    void destroy() throws JMSException;

}
