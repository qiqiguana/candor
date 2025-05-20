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
 * Copyright 2000 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: CorrelationId.java,v 1.1 2004/11/26 01:50:43 tanderson Exp $
 *
 * Date         Author  Changes
 * 02/26/2000   jimm    Created
 */


package org.exolab.jms.message;

// java io

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.jms.JMSException;


/**
 * This class implements the JMSCorrelationID message header property
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:43 $
 * @author      <a href="mailto:mourikis@exolab.org">Jim Mourikis</a>
 * @see         javax.jms.Message
 */
class CorrelationId implements Externalizable {

    /**
     * Object version no. for serialization
     */
    static final long serialVersionUID = 1;

    /**
     * Possible usages
     */
    static final int APPLICATION_USE = 1;
    static final int PROVIDER_USE = 2;
    static final int PROVIDER_NATIVE = 3;

    /**
     * What is Correlation Id is used for
     */
    private int _usage = 0;

    /**
     * Link to another message
     */
    private MessageId _id = null;

    /**
     * Application specific link
     */
    private String _clientId = null;

    /**
     * Default constructor for externalization support
     */
    public CorrelationId() {
    }

    public CorrelationId(String id) throws JMSException {
        // Get our own copy
        if (id.startsWith(MessageId.PREFIX)) {
            // Linked with another message
            _usage = PROVIDER_USE;
            _id = new MessageId(id);
        } else {
            // Client application specific
            _usage = APPLICATION_USE;
            _clientId = id;
        }
    }

    // Provider Native not supported
    public CorrelationId(byte[] id) throws JMSException {
        throw new UnsupportedOperationException(
            "Provider native correlation identifier not supported");
    }

    // Marshall out
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(serialVersionUID);
        out.writeInt(_usage);
        if (_usage == APPLICATION_USE) {
            out.writeInt(_clientId.length());
            out.writeChars(_clientId);
        } else if (_usage == PROVIDER_USE) {
            _id.writeExternal(out);
        }
    }

    // Marshall in
    public void readExternal(ObjectInput in)
        throws IOException, ClassNotFoundException {
        long version = in.readLong();
        if (version == serialVersionUID) {
            _usage = in.readInt();
            if (_usage == APPLICATION_USE) {
                int len = in.readInt();
                int i;
                StringBuffer buf = new StringBuffer(len);
                for (i = 0; i < len; i++) {
                    buf.append(in.readChar());
                }
                _clientId = buf.toString();
            } else if (_usage == PROVIDER_USE) {
                _id = new MessageId();
                _id.readExternal(in);
            }
        } else {
            throw new IOException("Incorrect version enountered: " + version +
                ". This version = " + serialVersionUID);
        }
    }

    public String getString() throws JMSException {
        String result = null;
        if (_usage == APPLICATION_USE) {
            result = _clientId;
        } else if (_usage == PROVIDER_USE) {
            result = _id.toString();
        } else {
            throw new JMSException("Unknown correlation");
        }
        return result;
    }

    public byte[] getBytes() throws JMSException {
        throw new UnsupportedOperationException(
            "Provider native correlation identifier not supported");
    }

} // End CorrelationId
