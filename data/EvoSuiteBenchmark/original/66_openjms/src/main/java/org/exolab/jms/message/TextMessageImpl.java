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
 * Copyright 2000-2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: TextMessageImpl.java,v 1.1 2004/11/26 01:50:43 tanderson Exp $
 *
 * Date         Author  Changes
 * 02/26/2000   jimm    Created
 */

package org.exolab.jms.message;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.jms.JMSException;
import javax.jms.MessageNotWriteableException;
import javax.jms.TextMessage;


/**
 * This class implements the <code>javax.jms.TextMessage</code> interface
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:43 $
 * @author      <a href="mailto:mourikis@exolab.org">Jim Mourikis</a>
 * @see         javax.jms.TextMessage
 */
public final class TextMessageImpl extends MessageImpl implements TextMessage {

    /**
     * Object version no. for serialization
     */
    static final long serialVersionUID = 1;

    /**
     * The message body
     */
    private String _text = null;

    /**
     * Construct a new TextMessage
     *
     * @throws JMSException if the message type can't be set
     */
    public TextMessageImpl() throws JMSException {
        setJMSType("TextMessage");
    }

    /**
     * Clone an instance of this object
     *
     * @return a copy of this object
     * @throws CloneNotSupportedException if object or attributes aren't
     * cloneable
     */
    public final Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Serialize out this message's data
     *
     * @param out the stream to serialize out to
     * @throws IOException if any I/O exceptions occurr
     */
    public final void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeLong(serialVersionUID);
        out.writeObject(_text);
    }

    /**
     * Serialize in this message's data
     *
     * @param in the stream to serialize in from
     * @throws ClassNotFoundException if the class for an object being
     * restored cannot be found.
     * @throws IOException if any I/O exceptions occur
     */
    public final void readExternal(ObjectInput in)
        throws ClassNotFoundException, IOException {
        super.readExternal(in);
        long version = in.readLong();
        if (version == serialVersionUID) {
            _text = (String) in.readObject();
        } else {
            throw new IOException("Incorrect version enountered: " + version +
                ". This version = " + serialVersionUID);
        }
    }

    /**
     * Set the string containing this message's data.
     *
     * @param string the String containing the message's data
     * @throws MessageNotWriteableException if message in read-only mode.
     */
    public final void setText(String string)
        throws MessageNotWriteableException {
        checkWrite();
        _text = string;
    }

    /**
     * Get the string containing this message's data. The default value is
     * null.
     *
     * @return the String containing the message's data
     */
    public final String getText() {
        return _text;
    }

    /**
     * Clear out the message body. Clearing a message's body does not clear
     * its header values or property entries.
     * If this message body was read-only, calling this method leaves the
     * message body is in the same state as an empty body in a newly created
     * message
     */
    public final void clearBody() throws JMSException {
        super.clearBody();
        _text = null;
    }

    /**
     * Returns the message text
     */
    public final String toString() {
        return getText();
    }

} //-- TextMessageImpl
