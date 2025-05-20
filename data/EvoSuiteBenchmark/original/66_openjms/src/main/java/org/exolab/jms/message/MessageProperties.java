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
 * $Id: MessageProperties.java,v 1.1 2004/11/26 01:50:43 tanderson Exp $
 *
 * Date         Author  Changes
 * 02/26/2000   jimm    Created
 */

package org.exolab.jms.message;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

import javax.jms.JMSException;
import javax.jms.MessageFormatException;


/**
 * This class provides properties for messages
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:43 $
 * @author      <a href="mailto:mourikis@intalio.com">Jim Mourikis</a>
 * @author      <a href="mailto:tima@intalio.com">Tim Anderson</a>
 */
class MessageProperties implements Cloneable, Externalizable {

    /**
     * The map containing of properties
     */
    private HashMap _properties = new HashMap(20);

    /**
     * Object version no. for serialization
     */
    static final long serialVersionUID = 1;

    /**
     * Illegal names
     */
    private static final String[] RESERVED_WORDS = {
        "and", "between", "escape", "in", "is", "like", "false", "null", "or",
        "not", "true"};

    private static final String GROUP_ID = "JMSXGroupID";
    private static final String GROUP_SEQ = "JMSXGroupSeq";

    /**
     * Recognized provider property names that may be set by clients, and
     * their expected types
     */
    private static final Object[][] JMSX_CLIENT_NAMES = {
        {GROUP_ID, String.class},
        {GROUP_SEQ, Integer.class}};

    /**
     * Default constructor to support externalization
     */
    public MessageProperties() {
    }

    /**
     * Clone an instance of this object
     *
     * @return a copy of this object
     * @throws CloneNotSupportedException if object or attributes not cloneable
     */
    public Object clone() throws CloneNotSupportedException {
        MessageProperties result = new MessageProperties();
        result._properties = (HashMap) _properties.clone();
        return result;
    }

    /**
     * Handle serialization.
     * Just serialize the whole thing for this release.
     *
     * @param out the stream to write the object to
     * @throws IOException if the object cannot be written
     */
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(serialVersionUID);
        out.writeObject(_properties);
    }

    /**
     * Read in the serialized object and assign all values from the stream.
     * Ensure the version is one that is currently supported.
     *
     * @param in the input stream
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class for an object being
     * restored cannot be found.
     */
    public void readExternal(ObjectInput in)
        throws IOException, ClassNotFoundException {
        long version = in.readLong();
        if (version == serialVersionUID) {
            _properties = (HashMap) in.readObject();
        } else {
            throw new IOException("Incorrect version enountered: " +
                version + " This version = " +
                serialVersionUID);
        }
    }

    /**
     * Clear out all existing properties.
     */
    public void clearProperties() {
        _properties.clear();
    }

    public boolean propertyExists(String name) {
        return _properties.containsKey(name);
    }

    public boolean getBooleanProperty(String name) throws JMSException {
        return FormatConverter.getBoolean(_properties.get(name));
    }

    public byte getByteProperty(String name) throws JMSException {
        return FormatConverter.getByte(_properties.get(name));
    }

    public short getShortProperty(String name) throws JMSException {
        return FormatConverter.getShort(_properties.get(name));
    }

    public int getIntProperty(String name) throws JMSException {
        return FormatConverter.getInt(_properties.get(name));
    }

    public long getLongProperty(String name) throws JMSException {
        return FormatConverter.getLong(_properties.get(name));
    }

    public float getFloatProperty(String name) throws JMSException {
        return FormatConverter.getFloat(_properties.get(name));
    }

    public double getDoubleProperty(String name) throws JMSException {
        return FormatConverter.getDouble(_properties.get(name));
    }

    public String getStringProperty(String name) throws JMSException {
        return FormatConverter.getString(_properties.get(name));
    }

    public Object getObjectProperty(String name) throws JMSException {
        return _properties.get(name);
    }

    public Enumeration getPropertyNames() {
        return Collections.enumeration(_properties.keySet());
    }

    public void setBooleanProperty(String name, boolean value)
        throws JMSException {
        setProperty(name, new Boolean(value));
    }

    public void setByteProperty(String name, byte value) throws JMSException {
        setProperty(name, new Byte(value));
    }

    public void setShortProperty(String name, short value)
        throws JMSException {
        setProperty(name, new Short(value));
    }

    public void setIntProperty(String name, int value) throws JMSException {
        setProperty(name, new Integer(value));
    }

    public void setLongProperty(String name, long value) throws JMSException {
        setProperty(name, new Long(value));
    }

    public void setFloatProperty(String name, float value)
        throws JMSException {
        setProperty(name, new Float(value));
    }

    public void setDoubleProperty(String name, double value)
        throws JMSException {
        setProperty(name, new Double(value));
    }

    public void setStringProperty(String name, String value)
        throws JMSException {
        setProperty(name, value);
    }

    public void setObjectProperty(String name, Object value)
        throws JMSException {
        if (value instanceof Boolean || value instanceof Byte ||
            value instanceof Short || value instanceof Integer ||
            value instanceof Long || value instanceof Float ||
            value instanceof Double || value instanceof String ||
            (value == null)) {
            setProperty(name, value);
        } else {
            throw new MessageFormatException(
                "Message.setObjectProperty() does not support objects of " +
                "type=" + value.getClass().getName());
        }
    }

    public void setJMSXRcvTimestamp(long value) {
        _properties.put("JMSXRcvTimestamp", new Long(value));
    }

    protected void setProperty(String name, Object value) throws JMSException {
        if (name == null) {
            throw new JMSException("<null> is not a valid property name");
        }
        char[] chars = name.toCharArray();
        if (chars.length == 0) {
            throw new JMSException("zero-length name is not a valid " +
                "property name");
        }
        if (!Character.isJavaIdentifierStart(chars[0])) {
            throw new JMSException("name=" + name + " is not a valid " +
                "property name");
        }
        for (int i = 1; i < chars.length; ++i) {
            if (!Character.isJavaIdentifierPart(chars[i])) {
                throw new JMSException("name=" + name + " is not a valid " +
                    "property name");
            }
        }
        for (int i = 0; i < RESERVED_WORDS.length; ++i) {
            if (name.equalsIgnoreCase(RESERVED_WORDS[i])) {
                throw new JMSException("name=" + name + " is a reserved " +
                    "word; it cannot be used as a " +
                    "property name");
            }
        }

        if (name.startsWith("JMSX")) {
            boolean found = false;
            for (int i = 0; i < JMSX_CLIENT_NAMES.length; ++i) {
                Object[] types = JMSX_CLIENT_NAMES[i];
                if (types[0].equals(name)) {
                    if (value == null) {
                        throw new MessageFormatException("Property=" + name +
                            " may not be null");
                    }
                    Class type = (Class) types[1];
                    if (!type.equals(value.getClass())) {
                        throw new MessageFormatException(
                            "Expected type=" + type.getName() +
                            " for property=" + name + ", but got type=" +
                            value.getClass().getName());
                    }
                    if (name.equals(GROUP_SEQ) &&
                        ((Integer) value).intValue() <= 0) {
                        throw new JMSException(
                            GROUP_SEQ + " must have a value > 0");
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new JMSException("Property=" + name +
                    " cannot be set by clients");
            }
        }

        _properties.put(name, value);
    }

} //-- MessageProperties
