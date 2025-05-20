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
 * $Id: StreamMessageImpl.java,v 1.2 2005/05/24 13:27:10 tanderson Exp $
 */
package org.exolab.jms.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.jms.JMSException;
import javax.jms.MessageEOFException;
import javax.jms.MessageFormatException;
import javax.jms.MessageNotReadableException;
import javax.jms.MessageNotWriteableException;
import javax.jms.StreamMessage;


/**
 * This class implements the {@link javax.jms.StreamMessage} interface.
 * <p>
 * A StreamMessage is used to send a stream of Java primitives.
 * It is filled and read sequentially. It inherits from <code>Message</code>
 * and adds a stream message body. It's methods are based largely on those
 * found in <code>java.io.DataInputStream</code> and
 * <code>java.io.DataOutputStream</code>.
 * <p>
 * The primitive types can be read or written explicitly using methods
 * for each type. They may also be read or written generically as objects.
 * For instance, a call to <code>StreamMessage.writeInt(6)</code> is
 * equivalent to <code>StreamMessage.writeObject(new Integer(6))</code>.
 * Both forms are provided because the explicit form is convenient for
 * static programming and the object form is needed when types are not known
 * at compile time.
 * <p>
 * When the message is first created, and when {@link #clearBody}
 * is called, the body of the message is in write-only mode. After the
 * first call to {@link #reset} has been made, the message body is in
 * read-only mode. When a message has been sent, by definition, the
 * provider calls <code>reset</code> in order to read it's content, and
 * when a message has been received, the provider has called
 * <code>reset</code> so that the message body is in read-only mode for the
 * client.
 * <p>
 * If {@link #clearBody} is called on a message in read-only mode,
 * the message body is cleared and the message body is in write-only mode.
 * <p>
 * If a client attempts to read a message in write-only mode, a
 * MessageNotReadableException is thrown.
 * <p>
 * If a client attempts to write a message in read-only mode, a
 * MessageNotWriteableException is thrown.
 * <p>
 * Stream messages support the following conversion table. The marked cases
 * must be supported. The unmarked cases must throw a JMSException. The
 * String to primitive conversions may throw a runtime exception if the
 * primitives <code>valueOf()</code> method does not accept it as a valid
 * String representation of the primitive.
 * <p>
 * A value written as the row type can be read as the column type.
 *
 * <pre>
 * |        | boolean byte short char int long float double String byte[]
 * |----------------------------------------------------------------------
 * |boolean |    X                                            X
 * |byte    |          X     X         X   X                  X
 * |short   |                X         X   X                  X
 * |char    |                     X                           X
 * |int     |                          X   X                  X
 * |long    |                              X                  X
 * |float   |                                    X     X      X
 * |double  |                                          X      X
 * |String  |    X     X     X         X   X     X     X      X
 * |byte[]  |                                                        X
 * |----------------------------------------------------------------------
 * </pre>
 * <p>
 * Attempting to read a null value as a Java primitive type must be treated
 * as calling the primitive's corresponding <code>valueOf(String)</code>
 * conversion method with a null value. Since char does not support a String
 * conversion, attempting to read a null value as a char must throw
 * NullPointerException.
 *
 * @version     $Revision: 1.2 $ $Date: 2005/05/24 13:27:10 $
 * @author      <a href="mailto:mourikis@intalio.com">Jim Mourikis</a>
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         javax.jms.StreamMessage
 */
public final class StreamMessageImpl extends MessageImpl
    implements StreamMessage {

    /**
     * Object version no. for serialization
     */
    static final long serialVersionUID = 2;

    /**
     * Type codes
     */
    private static final byte NULL = 0;
    private static final byte BOOLEAN = 1;
    private static final byte BYTE = 2;
    private static final byte BYTE_ARRAY = 3;
    private static final byte SHORT = 4;
    private static final byte CHAR = 5;
    private static final byte INT = 6;
    private static final byte LONG = 7;
    private static final byte FLOAT = 8;
    private static final byte DOUBLE = 9;
    private static final byte STRING = 10;

    /**
     * String values representing the above type codes, for error reporting
     * purposes
     */
    private static final String[] TYPE_NAMES = {
        "null", "boolean", "byte", "byte[]", "short", "char", "int", "long",
        "float", "double", "String"};

    /**
     * Empty byte array for initialisation purposes
     */
    private static final byte[] EMPTY = new byte[]{};

    /**
     * The byte stream to store data
     */
    private byte[] _bytes = EMPTY;

    /**
     * The stream used for writes
     */
    private DataOutputStream _out = null;

    /**
     * The byte stream backing the output stream
     */
    private ByteArrayOutputStream _byteOut = null;

    /**
     * The stream used for reads
     */
    private DataInputStream _in = null;

    /**
     * The byte stream backing the input stream
     */
    private ByteArrayInputStream _byteIn = null;

    /**
     * Non-zero if incrementally reading a byte array using
     * {@link #readBytes(byte[])}
     */
    private int _readBytes = 0;

    /**
     * The length of the byte array being read using {@link #readBytes(byte[])}
     */
    private int _byteArrayLength = 0;

    /**
     * The offset of the byte stream to start reading from. This defaults
     * to 0, and only applies to messages that are cloned from a
     * read-only instance where part of the stream had already been read.
     */
    private int _offset = 0;


    /**
     * Construct a new StreamMessage. When first created, the message is in
     * write-only mode.
     *
     * @throws JMSException if the message type can't be set, or an I/O error
     * occurs
     */
    public StreamMessageImpl() throws JMSException {
        setJMSType("StreamMessage");
    }

    /**
     * Clone an instance of this object
     *
     * @return a copy of this object
     * @throws CloneNotSupportedException if object or attributes aren't
     * cloneable
     */
    public final Object clone() throws CloneNotSupportedException {
        StreamMessageImpl result = (StreamMessageImpl) super.clone();
        if (_bodyReadOnly) {
            result._bytes = new byte[_bytes.length];
            System.arraycopy(_bytes, 0, result._bytes, 0, _bytes.length);
            if (_byteIn != null) {
                // if a client subsequently reads from the cloned object,
                // start reading from offset of the original stream
                _offset = _bytes.length - _byteIn.available();
            }
            result._byteIn = null;
            result._in = null;
        } else {
            if (_out != null) {
                try {
                    _out.flush();
                } catch (IOException exception) {
                    throw new CloneNotSupportedException(
                        exception.getMessage());
                }
                result._bytes = _byteOut.toByteArray();
                result._byteOut = null;
                result._out = null;
            } else {
                result._bytes = new byte[_bytes.length];
                System.arraycopy(_bytes, 0, result._bytes, 0, _bytes.length);
            }
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
        // If it was in write mode, extract the byte array
        if (!_bodyReadOnly && _out != null) {
            _out.flush();
            _bytes = _byteOut.toByteArray();
        }

        super.writeExternal(out);
        out.writeLong(serialVersionUID);
        out.writeInt(_bytes.length);
        out.write(_bytes);
        out.flush();
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
            int length = in.readInt();
            _bytes = new byte[length];
            in.readFully(_bytes);
        } else {
            throw new IOException("Incorrect version enountered: " + version
                                  + ". This version = " + serialVersionUID);
        }
    }

    /**
     * Read a <code>boolean</code> from the bytes message stream
     *
     * @return the <code>boolean</code> value read
     * @throws JMSException if JMS fails to read message due to some internal
     * JMS error
     * @throws MessageEOFException if end of message stream
     * @throws MessageFormatException if this type conversion is invalid
     * @throws MessageNotReadableException if message is in write-only mode
     */
    public final boolean readBoolean() throws JMSException {
        boolean result = false;
        prepare();
        try {
            result = FormatConverter.getBoolean(readNext());
        } catch (MessageFormatException exception) {
            revert(exception);
        }
        return result;
    }

    /**
     * Read a byte value from the stream message
     *
     * @return the next byte from the stream message as an 8-bit
     * <code>byte</code>
     * @throws JMSException if JMS fails to read message due to some internal
     * JMS error
     * @throws MessageEOFException if end of message stream
     * @throws MessageFormatException if this type conversion is invalid
     * @throws MessageNotReadableException if message is in write-only mode
     * @throws NumberFormatException if numeric conversion is invalid
     */
    public final byte readByte() throws JMSException {
        byte result = 0;
        prepare();
        try {
            result = FormatConverter.getByte(readNext());
        } catch (MessageFormatException exception) {
            revert(exception);
        } catch (NumberFormatException exception) {
            revert(exception);
        }
        return result;
    }

    /**
     * Read a 16-bit number from the stream message.
     *
     * @return a 16-bit number from the stream message
     * @throws JMSException if JMS fails to read message due to some internal
     * JMS error
     * @throws MessageEOFException if end of message stream
     * @throws MessageFormatException if this type conversion is invalid
     * @throws MessageNotReadableException if message is in write-only mode
     * @throws NumberFormatException if numeric conversion is invalid
     */
    public final short readShort() throws JMSException {
        short result = 0;
        prepare();
        try {
            result = FormatConverter.getShort(readNext());
        } catch (MessageFormatException exception) {
            revert(exception);
        } catch (NumberFormatException exception) {
            revert(exception);
        }
        return result;
    }

    /*
     * Read a Unicode character value from the stream message
     *
     * @return a Unicode character from the stream message
     * @throws JMSException if JMS fails to read message due to some internal
     * JMS error
     * @throws MessageEOFException if end of message stream
     * @throws MessageFormatException if this type conversion is invalid
     * @throws MessageNotReadableException if message is in write-only mode
     */
    public final char readChar() throws JMSException {
        char result = 0;
        prepare();
        try {
            result = FormatConverter.getChar(readNext());
        } catch (MessageFormatException exception) {
            revert(exception);
        } catch (NullPointerException exception) {
            revert(exception);
        }
        return result;
    }

    /*
     * Read a 32-bit integer from the stream message
     *
     * @return a 32-bit integer value from the stream message, interpreted
     * as an <code>int</code>
     * @throws JMSException if JMS fails to read message due to some internal
     * JMS error
     * @throws MessageEOFException if end of message stream
     * @throws MessageFormatException if this type conversion is invalid
     * @throws MessageNotReadableException if message is in write-only mode
     * @throws NumberFormatException if numeric conversion is invalid
     */
    public final int readInt() throws JMSException {
        int result = 0;
        prepare();
        try {
            result = FormatConverter.getInt(readNext());
        } catch (MessageFormatException exception) {
            revert(exception);
        } catch (NumberFormatException exception) {
            revert(exception);
        }
        return result;
    }

    /*
     * Read a 64-bit integer from the stream message
     *
     * @return a 64-bit integer value from the stream message, interpreted as
     * a <code>long</code>
     * @throws JMSException if JMS fails to read message due to some internal
     * JMS error
     * @throws MessageEOFException if end of message stream
     * @throws MessageFormatException if this type conversion is invalid
     * @throws MessageNotReadableException if message is in write-only mode
     * @throws NumberFormatException if numeric conversion is invalid
     */
    public final long readLong() throws JMSException {
        long result = 0;
        prepare();
        try {
            result = FormatConverter.getLong(readNext());
        } catch (MessageFormatException exception) {
            revert(exception);
        } catch (NumberFormatException exception) {
            revert(exception);
        }
        return result;
    }

    /**
     * Read a <code>float</code> from the stream message
     *
     * @return a <code>float</code> value from the stream message
     * @throws JMSException if JMS fails to read message due to some internal
     * JMS error
     * @throws MessageEOFException if end of message stream
     * @throws MessageFormatException if this type conversion is invalid
     * @throws MessageNotReadableException if message is in write-only mode
     * @throws NullPointerException if the value is null
     * @throws NumberFormatException if numeric conversion is invalid
     */
    public final float readFloat() throws JMSException {
        float result = 0;
        prepare();
        try {
            result = FormatConverter.getFloat(readNext());
        } catch (MessageFormatException exception) {
            revert(exception);
        } catch (NullPointerException exception) {
            revert(exception);
        } catch (NumberFormatException exception) {
            revert(exception);
        }
        return result;
    }

    /**
     * Read a <code>double</code> from the stream message
     *
     * @return a <code>double</code> value from the stream message
     * @throws JMSException if JMS fails to read message due to some internal
     * JMS error
     * @throws MessageEOFException if end of message stream
     * @throws MessageFormatException if this type conversion is invalid
     * @throws MessageNotReadableException if message is in write-only mode
     * @throws NullPointerException if the value is null
     * @throws NumberFormatException if numeric conversion is invalid
     */
    public final double readDouble() throws JMSException {
        double result = 0;
        prepare();
        try {
            result = FormatConverter.getDouble(readNext());
        } catch (MessageFormatException exception) {
            revert(exception);
        } catch (NullPointerException exception) {
            revert(exception);
        } catch (NumberFormatException exception) {
            revert(exception);
        }
        return result;
    }

    /**
     * Read in a string from the stream message
     *
     * @return a Unicode string from the stream message
     * @throws JMSException if JMS fails to read message due to some internal
     * JMS error
     * @throws MessageEOFException if end of message stream
     * @throws MessageFormatException if this type conversion is invalid
     * @throws MessageNotReadableException if message is in write-only mode
     */
    public final String readString() throws JMSException {
        String result = null;
        prepare();
        try {
            result = FormatConverter.getString(readNext());
        } catch (MessageFormatException exception) {
            revert(exception);
        }
        return result;
    }

    /**
     * Read a byte array field from the stream message into the
     * specified byte[] object (the read buffer).
     * <p>
     * To read the field value, readBytes should be successively called
     * until it returns a value less than the length of the read buffer.
     * The value of the bytes in the buffer following the last byte
     * read are undefined.
     * <p>
     * If readBytes returns a value equal to the length of the buffer, a
     * subsequent readBytes call must be made. If there are no more bytes
     * to be read this call will return -1.
     * <p>
     * If the bytes array field value is null, readBytes returns -1.
     * <p>
     * If the bytes array field value is empty, readBytes returns 0.
     * <p>
     * Once the first readBytes call on a byte[] field value has been done,
     * the full value of the field must be read before it is valid to read
     * the next field. An attempt to read the next field before that has
     * been done will throw a MessageFormatException.
     * <p>
     * To read the byte field value into a new byte[] object, use the
     * {@link #readObject} method.
     *
     * @param value the buffer into which the data is read.
     * @return the total number of bytes read into the buffer, or -1 if
     * there is no more data because the end of the byte field has been
     * reached.
     * @throws JMSException if JMS fails to read message due to some internal
     * JMS error
     * @throws MessageEOFException if an end of message stream
     * @throws MessageFormatException if this type conversion is invalid
     * @throws MessageNotReadableException if message is in write-only mode
     */
    public final int readBytes(byte[] value) throws JMSException {
        checkRead();
        getInputStream();
        int read = 0; // the number of bytes read
        if (_readBytes == 0) {
            // read the next byte array field
            try {
                _in.mark(_bytes.length - _in.available());
                byte type = (byte) (_in.readByte() & 0x0F);
                if (type == NULL) {
                    return -1;
                } else if (type != BYTE_ARRAY) {
                    _in.reset();
                    if (type < TYPE_NAMES.length) {
                        throw new MessageFormatException(
                            "Expected type=" + TYPE_NAMES[BYTE_ARRAY]
                            + ", but got type=" + TYPE_NAMES[type]);
                    } else {
                        throw new MessageFormatException(
                            "StreamMessage corrupted");
                    }
                }
            } catch (IOException exception) {
                raise(exception);
            }
            try {
                _byteArrayLength = _in.readInt();
            } catch (IOException exception) {
                raise(exception);
            }
        }

        if (_byteArrayLength == 0) {
            // No bytes to read. Return -1 if this is an incremental read
            // or 0 if the byte array was empty
            if (_readBytes != 0) {
                // completing an incremental read
                read = -1;
            }
            _readBytes = 0;   // indicates finished reading the byte array
        } else if (value.length <= _byteArrayLength) {
            // bytes to read >= size of target
            read = value.length;
            try {
                _in.readFully(value);
            } catch (IOException exception) {
                raise(exception);
            }
            _byteArrayLength -= value.length;
            ++_readBytes;
        } else {
            // bytes to read < size of target
            read = _byteArrayLength;
            try {
                _in.readFully(value, 0, _byteArrayLength);
            } catch (IOException exception) {
                raise(exception);
            }
            _readBytes = 0;
        }
        return read;
    }

    /**
     * Read a Java object from the stream message
     * <p>
     * Note that this method can be used to return in objectified format,
     * an object that had been written to the stream with the equivalent
     * <code>writeObject</code> method call, or it's equivalent primitive
     * write<type> method.
     * <p>
     * Note that byte values are returned as byte[], not Byte[].
     *
     * @return a Java object from the stream message, in objectified
     * format (eg. if it set as an int, then a Integer is returned).
     * @throws JMSException if JMS fails to read message due to some internal
     * JMS error
     * @throws MessageEOFException if end of message stream
     * @throws MessageNotReadableException if message is in write-only mode
     */
    public final Object readObject() throws JMSException {
        Object result = null;
        prepare();
        try {
            result = readNext();
        } catch (MessageFormatException exception) {
            revert(exception);
        }
        return result;
    }

    /**
     * Write a <code>boolean</code> to the stream message.
     * The value <code>true</code> is written out as the value
     * <code>(byte)1</code>; the value <code>false</code> is written out as
     * the value <code>(byte)0</code>.
     *
     * @param value the <code>boolean</code> value to be written.
     * @throws JMSException if JMS fails to write message due to
     * some internal JMS error
     * @throws MessageNotWriteableException if message in read-only mode
     */
    public final void writeBoolean(boolean value) throws JMSException {
        checkWrite();
        try {
            getOutputStream();
            // encode the boolean value in the type byte
            _out.writeByte(BOOLEAN | ((value) ? 1 << 4 : 0));
        } catch (IOException exception) {
            raise(exception);
        }
    }

    /**
     * Write out a <code>byte</code> to the stream message
     *
     * @param value the <code>byte</code> value to be written
     * @throws JMSException if JMS fails to write message due to
     * some internal JMS error
     * @throws MessageNotWriteableException if message in read-only mode
     */
    public final void writeByte(byte value) throws JMSException {
        checkWrite();
        try {
            getOutputStream();
            _out.writeByte(BYTE);
            _out.writeByte(value);
        } catch (IOException exception) {
            raise(exception);
        }
    }

    /**
     * Write a <code>short</code> to the stream message
     *
     * @param value the <code>short</code> to be written
     * @throws JMSException if JMS fails to write message due to
     * some internal JMS error
     * @throws MessageNotWriteableException if message in read-only mode
     */
    public final void writeShort(short value) throws JMSException {
        checkWrite();
        try {
            getOutputStream();
            _out.writeByte(SHORT);
            _out.writeShort(value);
        } catch (IOException exception) {
            raise(exception);
        }
    }

    /**
     * Write a <code>char</code> to the stream message
     *
     * @param value the <code>char</code> value to be written
     * @throws JMSException if JMS fails to write message due to
     * some internal JMS error
     * @throws MessageNotWriteableException if message in read-only mode
     */
    public final void writeChar(char value) throws JMSException {
        checkWrite();
        try {
            getOutputStream();
            _out.writeByte(CHAR);
            _out.writeChar(value);
        } catch (IOException exception) {
            raise(exception);
        }
    }

    /**
     * Write an <code>int</code> to the stream message
     *
     * @param value the <code>int</code> to be written
     * @throws JMSException if JMS fails to write message due to
     * some internal JMS error
     * @throws MessageNotWriteableException if message in read-only mode
     */
    public final void writeInt(int value) throws JMSException {
        checkWrite();
        try {
            getOutputStream();
            _out.writeByte(INT);
            _out.writeInt(value);
        } catch (IOException exception) {
            raise(exception);
        }
    }

    /**
     * Write a <code>long</code> to the stream message
     *
     * @param value the <code>long</code> to be written
     * @throws JMSException if JMS fails to write message due to
     * some internal JMS error
     * @throws MessageNotWriteableException if message in read-only mode
     */
    public final void writeLong(long value) throws JMSException {
        checkWrite();
        try {
            getOutputStream();
            _out.writeByte(LONG);
            _out.writeLong(value);
        } catch (IOException exception) {
            raise(exception);
        }
    }

    /**
     * Write a <code>float</code> to the stream message
     *
     * @param value the <code>float</code> value to be written
     * @throws JMSException if JMS fails to write message due to
     * some internal JMS error
     * @throws MessageNotWriteableException if message in read-only mode
     */
    public final void writeFloat(float value) throws JMSException {
        checkWrite();
        try {
            getOutputStream();
            _out.writeByte(FLOAT);
            _out.writeFloat(value);
        } catch (IOException exception) {
            raise(exception);
        }
    }

    /**
     * Write a <code>double</code> to the stream message
     *
     * @param value the <code>double</code> value to be written
     * @throws JMSException if JMS fails to write message due to
     * some internal JMS error
     * @throws MessageNotWriteableException if message in read-only mode
     */
    public final void writeDouble(double value) throws JMSException {
        checkWrite();
        try {
            getOutputStream();
            _out.writeByte(DOUBLE);
            _out.writeDouble(value);
        } catch (IOException exception) {
            raise(exception);
        }
    }

    /**
     * Write a string to the stream message
     *
     * @param value the <code>String</code> value to be written
     * @throws JMSException if JMS fails to write message due to
     * some internal JMS error
     * @throws MessageNotWriteableException if message in read-only mode
     * @throws NullPointerException if value is null
     */
    public final void writeString(String value) throws JMSException {
        checkWrite();
        if (value == null) {
            // could throw IllegalArgumentException, but this is in keeping
            // with that thrown by DataOutputStream
            throw new NullPointerException("Argument value is null");
        }
        try {
            getOutputStream();
            _out.writeByte(STRING);
            _out.writeUTF(value);
        } catch (IOException exception) {
            raise(exception);
        }
    }

    /**
     * Write a byte array field to the stream message
     * <p>
     * The byte array <code>value</code> is written as a byte array field
     * into the StreamMessage. Consecutively written byte array fields are
     * treated as two distinct fields when reading byte array fields.
     *
     * @param value the byte array to be written
     * @throws JMSException if JMS fails to write message due to
     * some internal JMS error
     * @throws MessageNotWriteableException if message in read-only mode
     * @throws NullPointerException if value is null
     */
    public final void writeBytes(byte[] value) throws JMSException {
        checkWrite();
        if (value == null) {
            // could throw IllegalArgumentException, but this is in keeping
            // with that thrown by DataOutputStream
            throw new NullPointerException("Argument value is null");
        }
        try {
            getOutputStream();
            _out.writeByte(BYTE_ARRAY);
            _out.writeInt(value.length);
            _out.write(value);
        } catch (IOException exception) {
            raise(exception);
        }
    }

    /**
     * Write a portion of a byte array as a byte array field to the stream
     * message
     * <p>
     * The a portion of the byte array <code>value</code> is written as a
     * byte array field into the StreamMessage. Consecutively written byte
     * array fields are treated as two distinct fields when reading byte
     * array fields.
     *
     * @param value the byte array value to be written
     * @param offset the initial offset within the byte array
     * @param length the number of bytes to write
     * @throws JMSException if JMS fails to write message due to
     * some internal JMS error
     * @throws MessageNotWriteableException if message in read-only mode
     * @throws NullPointerException if value is null
     */
    public void writeBytes(byte[] value, int offset, int length)
        throws JMSException {
        checkWrite();
        if (value == null) {
            // could throw IllegalArgumentException, but this is in keeping
            // with that thrown by DataOutputStream
            throw new NullPointerException("Argument value is null");
        }
        try {
            getOutputStream();
            _out.writeByte(BYTE_ARRAY);
            _out.writeInt(length);
            _out.write(value, offset, length);
        } catch (IOException exception) {
            raise(exception);
        }
    }

    /**
     * Write a Java object to the stream message
     * <p>
     * Note that this method only works for the objectified primitive
     * object types (Integer, Double, Long ...), String's and byte arrays.
     *
     * @param value the Java object to be written
     * @throws JMSException if JMS fails to write message due to
     * some internal JMS error
     * @throws MessageFormatException if the object is invalid
     * @throws MessageNotWriteableException if message in read-only mode
     */
    public void writeObject(Object value) throws JMSException {
        if (value == null) {
            try {
                checkWrite();
                getOutputStream();
                _out.writeByte(NULL);
            } catch (IOException exception) {
                raise(exception);
            }
        } else if (value instanceof Boolean) {
            writeBoolean(((Boolean) value).booleanValue());
        } else if (value instanceof Byte) {
            writeByte(((Byte) value).byteValue());
        } else if (value instanceof byte[]) {
            writeBytes((byte[]) value);
        } else if (value instanceof Short) {
            writeShort(((Short) value).shortValue());
        } else if (value instanceof Character) {
            writeChar(((Character) value).charValue());
        } else if (value instanceof Integer) {
            writeInt(((Integer) value).intValue());
        } else if (value instanceof Long) {
            writeLong(((Long) value).longValue());
        } else if (value instanceof Float) {
            writeFloat(((Float) value).floatValue());
        } else if (value instanceof Double) {
            writeDouble(((Double) value).doubleValue());
        } else if (value instanceof String) {
            writeString((String) value);
        } else {
            throw new MessageFormatException(
                "Objects of type " + value.getClass().getName()
                + " are not supported by StreamMessage");
        }
    }

    /**
     * Put the message body in read-only mode, and reposition the stream
     * to the beginning
     *
     * @throws JMSException if JMS fails to reset the message due to
     * some internal JMS error
     */
    public void reset() throws JMSException {
        try {
            if (!_bodyReadOnly) {
                _bodyReadOnly = true;
                if (_out != null) {
                    _out.flush();
                    _bytes = _byteOut.toByteArray();
                    _byteOut = null;
                    _out.close();
                    _out = null;
                }
            } else {
                if (_in != null) {
                    _byteIn = null;
                    _in.close();
                    _in = null;
                }
            }
            _readBytes = 0;
        } catch (IOException exception) {
            raise(exception);
        }
    }

    /**
     * Overide the super class method to reset the streams, and put the
     * message body in write only mode
     *
     * @throws JMSException if JMS fails to reset the message due to
     * some internal JMS error.
     */
    public void clearBody() throws JMSException {
        try {
            if (_bodyReadOnly) {
                // in read-only mode
                _bodyReadOnly = false;
                if (_in != null) {
                    _byteIn = null;
                    _in.close();
                    _in = null;
                    _offset = 0;
                }
            } else if (_out != null) {
                // already in write-only mode
                _byteOut = null;
                _out.close();
                _out = null;
            }
            _bytes = EMPTY;
            _readBytes = 0;
        } catch (IOException exception) {
            raise(exception);
        }
    }

    /**
     * Set the read-only mode of the message. If read-only, resets the message
     * for reading
     *
     * @param readOnly if true, make the message body and properties
     * @throws JMSException if the read-only mode cannot be changed
     */
    public final void setReadOnly(boolean readOnly) throws JMSException {
        if (readOnly) {
            reset();
        }
        super.setReadOnly(readOnly);
    }

    /**
     * Prepare to do a read
     *
     * @throws JMSException if the current position in the stream can't be
     * marked
     * @throws MessageNotReadableException if the message is in write-only mode
     */
    private final void prepare() throws JMSException {
        checkRead();
        getInputStream();
        try {
            _in.mark(_bytes.length - _in.available());
        } catch (IOException exception) {
            raise(exception);
        }
    }

    /**
     * Reverts the stream to its prior position if a MessageFormatException is
     * thrown, and propagates the exception.
     *
     * @param exception the exception that caused the reset
     * @throws MessageFormatException
     */
    private void revert(MessageFormatException exception)
        throws MessageFormatException {
        try {
            _in.reset();
        } catch (IOException ignore) {
            // can't reset the stream, but need to propagate the original
            // exception
        }
        throw exception;
    }

    /**
     * Reverts the stream to its prior position if a NumberFormatException or
     * NullPointerException is thrown, and propagates the exception.
     *
     * @param exception the exception that caused the reset
     * @throws NullPointerException
     * @throws NumberFormatException
     */
    private void revert(RuntimeException exception) {
        try {
            _in.reset();
        } catch (IOException ignore) {
            // can't reset the stream, but need to propagate the original
            // exception
        }
        throw exception;
    }

    /**
     * Read the next object from the stream message
     *
     * @return a Java object from the stream message, in objectified
     * format (eg. if it set as an int, then a Integer is returned).
     * @throws JMSException if JMS fails to read message due to some internal
     * JMS error
     * @throws MessageEOFException if end of message stream
     * @throws MessageFormatException if a byte array has not been fully read
     * by {@link #readBytes(byte[])}
     * @throws MessageNotReadableException if the message is in write-only mode
     */
    private Object readNext() throws JMSException {
        if (_readBytes != 0) {
            throw new MessageFormatException(
                "Cannot read the next field until the byte array is read");
        }

        byte type = 0;
        try {
            type = _in.readByte();
        } catch (IOException exception) {
            raise(exception);
        }
        if ((type & 0x0F) > TYPE_NAMES.length) {
            throw new JMSException("StreamMessage corrupted");
        }
        Object result = null;

        try {
            switch (type & 0x0F) {
                case BOOLEAN:
                    boolean value = ((type & 0xF0) != 0) ? true : false;
                    result = new Boolean(value);
                    break;
                case BYTE:
                    result = new Byte(_in.readByte());
                    break;
                case BYTE_ARRAY:
                    int length = _in.readInt();
                    byte[] bytes = new byte[length];
                    _in.readFully(bytes);
                    result = bytes;
                    break;
                case SHORT:
                    result = new Short(_in.readShort());
                    break;
                case CHAR:
                    result = new Character(_in.readChar());
                    break;
                case INT:
                    result = new Integer(_in.readInt());
                    break;
                case LONG:
                    result = new Long(_in.readLong());
                    break;
                case FLOAT:
                    result = new Float(_in.readFloat());
                    break;
                case DOUBLE:
                    result = new Double(_in.readDouble());
                    break;
                case STRING:
                    result = _in.readUTF();
                    break;
            }
        } catch (IOException exception) {
            raise(exception);
        }

        return result;
    }

    /**
     * Initialise the input stream if it hasn't been intialised
     *
     * @return the input stream
     */
    private DataInputStream getInputStream() {
        if (_in == null) {
            _byteIn = new ByteArrayInputStream(_bytes, _offset,
                _bytes.length - _offset);
            _in = new DataInputStream(_byteIn);
        }
        return _in;
    }

    /**
     * Initialise the output stream if it hasn't been intialised
     *
     * @return the output stream
     * @throws IOException if the output stream can't be created
     */
    private final DataOutputStream getOutputStream() throws IOException {
        if (_out == null) {
            _byteOut = new ByteArrayOutputStream();
            _out = new DataOutputStream(_byteOut);
            _out.write(_bytes);
        }
        return _out;
    }

    /**
     * Helper to raise a JMSException when an I/O error occurs
     *
     * @param exception the exception that caused the failure
     * @throws JMSException
     */
    private final void raise(IOException exception) throws JMSException {
        JMSException error = null;
        if (exception instanceof EOFException) {
            error = new MessageEOFException(exception.getMessage());
        } else {
            error = new JMSException(exception.getMessage());
        }
        error.setLinkedException(exception);
        throw error;
    }

}
