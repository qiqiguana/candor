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
 * Copyright 2003-2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: SerializationHelper.java,v 1.1 2004/11/26 01:51:06 tanderson Exp $
 */
package org.exolab.jms.net.util;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


/**
 * Helper class for serializing and deserializing objects efficiently
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2004/11/26 01:51:06 $
 * @see Delegate
 */
public final class SerializationHelper {

    /**
     * Prevent construction of helper class
     */
    private SerializationHelper() {
    }

    /**
     * Write an object of the specified type to a stream
     *
     * @param type   the type of the object
     * @param object the object to write
     * @param out    the stream to write to
     * @throws IOException if the object can't be written
     */
    public static void write(Class type, Object object, ObjectOutput out)
            throws IOException {

        if (type.isPrimitive()) {
            if (type == boolean.class) {
                out.writeBoolean(((Boolean) object).booleanValue());
            } else if (type == byte.class) {
                out.writeByte(((Byte) object).byteValue());
            } else if (type == char.class) {
                out.writeChar(((Character) object).charValue());
            } else if (type == short.class) {
                out.writeShort(((Short) object).shortValue());
            } else if (type == int.class) {
                out.writeInt(((Integer) object).intValue());
            } else if (type == long.class) {
                out.writeLong(((Long) object).longValue());
            } else if (type == float.class) {
                out.writeFloat(((Float) object).floatValue());
            } else if (type == double.class) {
                out.writeDouble(((Double) object).doubleValue());
            } else {
                throw new IOException("Unsupported primitive type: " + type);
            }
        } else {
            out.writeObject(object);
        }
    }

    /**
     * Read an object of the specified type from a stream
     *
     * @param type the type of the object
     * @param in   the stream to read from
     * @return the deserialized object
     * @throws ClassNotFoundException if the class for a serialized object
     *                                cannot be found
     * @throws IOException            if the object can't be read
     */
    public static Object read(Class type, ObjectInput in)
            throws ClassNotFoundException, IOException {
        Object result;
        if (type.isPrimitive()) {
            if (type == boolean.class) {
                result = new Boolean(in.readBoolean());
            } else if (type == byte.class) {
                result = new Byte(in.readByte());
            } else if (type == char.class) {
                result = new Character(in.readChar());
            } else if (type == short.class) {
                result = new Short(in.readShort());
            } else if (type == int.class) {
                result = new Integer(in.readInt());
            } else if (type == long.class) {
                result = new Long(in.readLong());
            } else if (type == float.class) {
                result = new Float(in.readFloat());
            } else if (type == double.class) {
                result = new Double(in.readDouble());
            } else {
                throw new IOException("Unsupported primitive type: " + type);
            }
        } else {
            result = in.readObject();
        }
        return result;
    }

}
