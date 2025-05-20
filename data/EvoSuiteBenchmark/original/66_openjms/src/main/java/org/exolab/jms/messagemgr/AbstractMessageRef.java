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
 * $Id: AbstractMessageRef.java,v 1.3 2005/12/20 20:31:59 tanderson Exp $
 */

package org.exolab.jms.messagemgr;

import javax.jms.JMSException;


/**
 * Abstract implementation of the {@link MessageRef} interface.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/12/20 20:31:59 $
 */
abstract class AbstractMessageRef implements MessageRef {

    /**
     * The message identifier.
     */
    private String _messageId;

    /**
     * Determines if the message is persistent.
     */
    private final boolean _persistent;

    /**
     * The message reference count.
     */
    private volatile int _count;


    /**
     * Construct a new <code>AbstractMessageRef</code>, with a zero reference
     * count.
     *
     * @param messageId  the message identifier
     * @param persistent determines if the message is persistent
     */
    public AbstractMessageRef(String messageId, boolean persistent) {
        _messageId = messageId;
        _persistent = persistent;
        _count = 0;
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
     * Determines if the underlying message is persistent.
     *
     * @return <code>true</code> if the message is persistent; otherwise
     *         <code>false</code>
     */
    public boolean isPersistent() {
        return _persistent;
    }

    /**
     * Increment the reference.
     *
     * @throws JMSException if the handle has been destroyed
     */
    public synchronized void reference() throws JMSException {
        if (isDestroyed()) {
            throw new JMSException("Cannot reference message, JMSMessageID="
                                   + _messageId
                                   + ". Message has been destroyed");
        }
        ++_count;
    }

    /**
     * Decrement the reference. If there are no references to the message, it
     * will be destroyed.
     *
     * @throws JMSException for any error
     */
    public synchronized void dereference() throws JMSException {
        if (!isDestroyed()) {
            if (--_count <= 0) {
                destroy();
                setDestroyed();
            }
        }
    }

    /**
     * Determines if this has been destroyed.
     *
     * @return <code>true</code> if this has been destroyed
     */
    protected boolean isDestroyed() {
        return (_count < 0);
    }

    /**
     * Mark this as being destroyed.
     */
    protected void setDestroyed() {
        _count = -1;
    }

}
