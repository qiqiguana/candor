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
 * $Id: Descriptor.java,v 1.1 2004/11/26 01:51:15 tanderson Exp $
 */

package org.exolab.jms.tools.db;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Types;
import java.util.HashMap;


/**
 * This class is a helper class for converting from string values to their
 * corresponding {@link java.sql.Types}
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:15 $
 * @author      <a href="mailto:tima@intalio.com">Tim Anderson</a>
 */
class Descriptor {

    /**
     * The type identifier, corresponding to one in java.sql.Types
     */
    private final int _type;

    /**
     * The name of the type
     */
    private final String _name;

    /**
     * A map of type identifiers to their names
     */
    private static HashMap TYPE_MAP = null;

    /**
     * A map of names to their corresponding type identifiers
     */
    private static HashMap NAME_MAP = null;

    /**
     * Construct a new descriptor
     *
     * @param type the type identifier
     * @param name the name of the type
     */
    private Descriptor(int type, String name) {
        _type = type;
        _name = name;
    }

    /**
     * Returns the type identifier
     *
     * @return the type identifier, corresponding to one in
     * {@link java.sql.Types}
     */
    public int getType() {
        return _type;
    }

    /**
     * Returns the type name
     */
    public String getName() {
        return _name;
    }

    /**
     * Returns the descriptor for a given type identifier
     *
     * @return the descriptor corresponding to the type identifier, or null
     * if it doesn't exist
     */
    public static Descriptor getDescriptor(int type) {
        return (Descriptor) TYPE_MAP.get(new Integer(type));
    }

    /**
     * Returns the descriptor for a given type name
     *
     * @return the descriptor corresponding to the type name, or null
     * if it doesn't exist
     */
    public static Descriptor getDescriptor(String name) {
        return (Descriptor) NAME_MAP.get(name.toUpperCase());
    }

    /**
     * Initialise the maps
     */
    static {
        TYPE_MAP = new HashMap();
        NAME_MAP = new HashMap();
        try {
            Field[] fields = Types.class.getFields();
            for (int i = 0; i < fields.length; ++i) {
                Field field = fields[i];
                if (Modifier.isStatic(field.getModifiers())) {
                    int type = ((Integer) field.get(null)).intValue();
                    String name = field.getName().toUpperCase();
                    Descriptor descriptor = new Descriptor(type, name);
                    TYPE_MAP.put(new Integer(type), descriptor);
                    NAME_MAP.put(name, descriptor);
                }
            }
        } catch (IllegalAccessException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

} //-- Descriptor
