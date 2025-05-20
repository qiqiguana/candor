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
 * Copyright 2000-2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: MessageId.java,v 1.1 2004/11/26 01:50:43 tanderson Exp $
 */
package org.exolab.jms.message;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.exolab.jms.common.uuid.UUID;


/**
 * The MessageId is a serializable object that uniquely identifies a message
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:43 $
 * @author      <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 */
final public class MessageId
        implements Externalizable {

    /**
     * Version Id used for streaming
     */
    static final long serialVersionUID = 2;

    /**
     * The JMS message identifier
     */
    private String _id = null;

    /**
     * The JMS message identifier prefix
     */
    public final static String PREFIX = "ID:";

    /**
     * A null object identity
     */
    private static final String NULL_ID = "ID:0";


    /**
     * Constructor provided for serialization
     */
    public MessageId() {
    }

    /**
     * Construct a new <code>MessageId</code>
     *
     * @param id - the message identity
     */
    public MessageId(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }

    // implementation of Object.hashCode
    public int hashCode() {
        return _id.hashCode();
    }

    // implementation of Externalizable.writeExternal
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(serialVersionUID);
        out.writeUTF(_id);
    }

    // implementation of Externalizable.readExternal
    public void readExternal(ObjectInput in)
            throws IOException, ClassNotFoundException {

        // read the serial number of the object and ensure that
        // we can process it.
        long version = in.readLong();
        if (version == serialVersionUID) {
            _id = in.readUTF();
        } else if (version == 1) {
            // for backwards compatibility
            long oldID = in.readLong();  // discard this
            String id = (String) in.readObject();
            _id = PREFIX + id;
        } else {
            throw new IOException("Incorrect version enountered: " +
                                  version + " This version = " +
                                  serialVersionUID);
        }
    }

    // override Object.toString
    public String toString() {
        return _id;
    }

    // override Object,equals
    public boolean equals(Object object) {
        boolean equal = (object == this);
        if (!equal) {
            if (object instanceof MessageId &&
                    ((MessageId) object)._id.equals(_id)) {
                equal = true;
            }
        }

        return equal;
    }

    /**
     * Allocate a new globally unique message identifier
     *
     * @return a globally unique message identifier
     */
    public static String create() {
        return UUID.next(PREFIX);
    }

    /**
     * Return the 'null' JMSMessageID.
     * This is the first Id recognised by OpenJMS, but not assigned
     * to messages.
     *
     * @return      String              the 'null' message Id
     */
    public static String getNull() {
        return NULL_ID;
    }

}

