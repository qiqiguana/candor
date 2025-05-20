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
 * Copyright 2001,2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: Type.java,v 1.1 2004/11/26 01:51:16 tanderson Exp $
 */

package org.exolab.jms.tools.db;

import org.exolab.jms.persistence.PersistenceException;


/**
 * This class is a helper class for converting from string values to their
 * corresponding <code>java.sql.Types</code>
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:16 $
 * @author      <a href="mailto:tima@intalio.com">Tim Anderson</a>
 */
public class Type {

    /**
     * The type descriptor
     */
    private final Descriptor _descriptor;

    /**
     * The type name
     */
    private final String _name;

    /**
     * The precision of the type
     */
    private final long _precision;

    /**
     * If true, denotes that the type takes parameters
     */
    private final boolean _parameters;

    /**
     * Construct an instance, using the default name for the type
     *
     * @param type a type corresponding to one in <code>java.sql.Types</code>
     * @param precision the precision of the type. A precision &lt;= 0
     * indicates that the type has no precision
     * @param parameters if true, denotes that the type takes parameters
     * @throws IllegalArgumentException if type is invalid
     */
    public Type(int type, long precision, boolean parameters) {
        _descriptor = Descriptor.getDescriptor(type);
        if (_descriptor == null) {
            throw new IllegalArgumentException("Type id=" + type +
                " is not a valid type");
        }
        _name = _descriptor.getName();
        _precision = precision;
        _parameters = parameters;
    }

    /**
     * Construct an instance specifying the database specific type name
     *
     * @param type a type corresponding to one in <code>java.sql.Types</code>
     * @param name the RDBMS name of the type
     * @param precision the precision of the type. A precision &lt;= 0
     * indicates that the type has no precision
     * @param parameters if true, denotes that the type takes parameters
     * @throws IllegalArgumentException if type is invalid
     */
    public Type(int type, String name, long precision, boolean parameters) {
        _descriptor = Descriptor.getDescriptor(type);
        if (_descriptor == null) {
            throw new IllegalArgumentException("Type id=" + type +
                " is not a valid type");
        }
        _name = name;
        _precision = precision;
        _parameters = parameters;
    }

    /**
     * Returns the type identifier
     *
     * @return the type identifier, corresponding to one in
     * <code>java.sql.Types</code>
     */
    public int getType() {
        return _descriptor.getType();
    }

    /**
     * Returns the name of the type
     *
     * @return the type name
     */
    public String getName() {
        return _name;
    }

    /**
     * Returns the precision of the type
     *
     * @return the type precision
     */
    public long getPrecision() {
        return _precision;
    }

    /**
     * Returns if the type takes parameters when created
     *
     * @return true if the type takes parameters when created, false otherwise
     */
    public boolean getParameters() {
        return _parameters;
    }

    /**
     * Returns a symbolic representation of the type
     *
     * @return a symbolic representation of the type
     */
    public String getSymbolicType() {
        String result = _descriptor.getName();
        if (_parameters && _precision > 0) {
            result += "(" + _precision + ")";
        }
        return result;
    }

    /**
     * Returns an SQL representation of the type
     *
     * @return an SQL string representation of the type
     */
    public String getSQL() {
        String result = _name;
        if (_parameters && _precision > 0) {
            result += "(" + _precision + ")";
        }
        return result;
    }

    /**
     * Returns a string representation of the type, for debugging purposes
     */
    public String toString() {
        return "type=" + _descriptor.getName() + ", name=" + _name +
            ", precision=" + _precision + ", parameters=" +
            _parameters;
    }

    /**
     * Returns a new type corresponding to its string representation
     *
     * @param type the string representation of the type
     * @return the type corresponding to the string
     * @throws PersistenceException if the string is invalid
     */
    public static Type getType(String type) throws PersistenceException {
        int start = type.indexOf('(');
        String name = type;
        long precision = -1;
        boolean parameters = false;
        if (start != -1) {
            name = type.substring(0, start);
            int end = type.indexOf(')', start);
            if (end == -1) {
                throw new PersistenceException("Illegal type: " + type);
            }
            precision = Long.parseLong(type.substring(start + 1, end));
            parameters = true;
        }

        Descriptor descriptor = Descriptor.getDescriptor(name.trim());
        if (descriptor == null) {
            throw new PersistenceException("Type name=" + type +
                " is not a valid type");
        }
        return new Type(descriptor.getType(), precision, parameters);
    }

} //-- Type
