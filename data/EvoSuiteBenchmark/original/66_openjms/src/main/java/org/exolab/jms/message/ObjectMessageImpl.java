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
 * $Id: ObjectMessageImpl.java,v 1.2 2006/06/10 23:21:32 tanderson Exp $
 *
 * Date         Author  Changes
 * 02/26/2000   jimm    Created
 */

package org.exolab.jms.message;

import javax.jms.JMSException;
import javax.jms.MessageFormatException;
import javax.jms.MessageNotWriteableException;
import javax.jms.ObjectMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.io.InputStream;
import java.io.StreamCorruptedException;


/**
 * This class implements the <code>javax.jms.ObjectMessage</code> interface.
 * <p/>
 * An ObjectMessage is used to send a message that contains a serializable
 * Java object. It inherits from <code>Message</code> and adds a body
 * containing a single Java reference. Only <code>Serializable</code> Java
 * objects can be used.
 * <p/>
 * If a collection of Java objects must be sent, one of the collection
 * classes provided in JDK 1.2 can be used.
 * <p/>
 * When a client receives an ObjectMessage, it is in read-only mode. If a
 * client attempts to write to the message at this point, a
 * MessageNotWriteableException is thrown. If <code>clearBody</code> is
 * called, the message can now be both read from and written to.
 *
 * @author <a href="mailto:mourikis@intalio.com">Jim Mourikis</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2006/06/10 23:21:32 $
 */
public final class ObjectMessageImpl extends MessageImpl
        implements ObjectMessage {

    /**
     * Object version no. for serialization
     */
    static final long serialVersionUID = 1;

    /**
     * The byte stream to store data
     */
    private byte[] _bytes = null;

    /**
     * Construct a new ObjectMessage
     *
     * @throws JMSException if the message type can't be set
     */
    public ObjectMessageImpl() throws JMSException {
        setJMSType("ObjectMessage");
    }

    /**
     * Clone an instance of this object
     *
     * @return a copy of this object
     * @throws CloneNotSupportedException if object or attributes aren't
     *                                    cloneable
     */
    public final Object clone() throws CloneNotSupportedException {
        ObjectMessageImpl result = (ObjectMessageImpl) super.clone();
        if (_bytes != null) {
            result._bytes = new byte[_bytes.length];
            System.arraycopy(_bytes, 0, result._bytes, 0, _bytes.length);
        }
        return result;
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
        if (_bytes != null) {
            out.writeInt(_bytes.length);
            out.write(_bytes);
        } else {
            out.writeInt(0);
        }
    }

    /**
     * Serialize in this message's data
     *
     * @param in the stream to serialize in from
     * @throws ClassNotFoundException if the class for an object being
     *                                restored cannot be found.
     * @throws IOException            if any I/O exceptions occur
     */
    public final void readExternal(ObjectInput in)
            throws ClassNotFoundException, IOException {
        super.readExternal(in);
        long version = in.readLong();
        if (version == serialVersionUID) {
            int length = in.readInt();
            if (length != 0) {
                _bytes = new byte[length];
                in.readFully(_bytes);
            } else {
                _bytes = null;
            }
        } else {
            throw new IOException(
                    "Incorrect version enountered: " + version +
                            ". This version = " + serialVersionUID);
        }
    }

    /**
     * Set the serializable object containing this message's data.
     * It is important to note that an <code>ObjectMessage</code>
     * contains a snapshot of the object at the time <code>setObject()</code>
     * is called - subsequent modifications of the object will have no
     * affect on the <code>ObjectMessage</code> body.
     *
     * @param object the message's data
     * @throws MessageFormatException       if object serialization fails
     * @throws MessageNotWriteableException if the message is read-only
     */
    public final void setObject(Serializable object)
            throws MessageFormatException, MessageNotWriteableException {
        checkWrite();

        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);

            out.writeObject(object);
            out.flush();
            _bytes = byteOut.toByteArray();
            out.close();
        } catch (IOException exception) {
            MessageFormatException error = new MessageFormatException(
                    exception.getMessage());
            error.setLinkedException(exception);
            throw error;
        }
    }

    /**
     * Get the serializable object containing this message's data. The
     * default value is null.
     *
     * @return the serializable object containing this message's data
     * @throws MessageFormatException if object deserialization fails
     */
    public final Serializable getObject() throws MessageFormatException {
        Serializable result = null;
        if (_bytes != null) {
            try {
                ByteArrayInputStream byteIn =
                        new ByteArrayInputStream(_bytes);
                ObjectInputStream in = new ObjectStream(byteIn);

                result = (Serializable) in.readObject();
                in.close();
            } catch (IOException exception) {
                MessageFormatException error =
                        new MessageFormatException(exception.getMessage());
                error.setLinkedException(exception);
                throw error;
            } catch (ClassNotFoundException exception) {
                MessageFormatException error =
                        new MessageFormatException(exception.getMessage());
                error.setLinkedException(exception);
                throw error;
            }
        }
        return result;
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
        _bytes = null;
    }

    /**
     * <code>ObjectInputStream</code> implementation that supports loading
     * classes from the context class loader.
     */
    private class ObjectStream extends ObjectInputStream {

        public ObjectStream(InputStream inputStream)
                throws IOException, StreamCorruptedException {
            super(inputStream);
        }

        protected Class resolveClass(ObjectStreamClass desc)
                throws IOException, ClassNotFoundException {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            if (loader != null) {
                try {
                    return loader.loadClass(desc.getName());
                } catch (ClassNotFoundException ignore) {
                    // no-op
                }
            }
            return super.resolveClass(desc);
        }
    }

}
