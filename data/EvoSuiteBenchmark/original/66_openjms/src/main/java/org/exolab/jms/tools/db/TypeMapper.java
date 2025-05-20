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
 * Copyright 2001-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: TypeMapper.java,v 1.2 2005/09/04 07:17:05 tanderson Exp $
 */
package org.exolab.jms.tools.db;

import java.sql.Types;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * A helper class for mapping between SQL types.
 * <p/>
 * NOTE: this mapping is not complete, only reflecting those types required
 * by OpenJMS
 *
 * @version     $Revision: 1.2 $ $Date: 2005/09/04 07:17:05 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
class TypeMapper {

    /**
     * The set of types supported by the RDBMS.
     */
    private final TypeSet _set;

    /**
     * A mapping of SQL type identifiers to their corresponding alternative
     * types. The alternatives are represented as an array of Type
     */
    private final HashMap _mappings = new HashMap();

    /**
     * The logger.
     */
    private static final Log _log = LogFactory.getLog(TypeMapper.class);


    /**
     * Construct the type mapper with the set of types supported by the
     * RDBMS.
     */
    public TypeMapper(TypeSet set) {
        _set = set;

        // alternatives for the Types.BINARY type
        Type[] binaries = {new Type(Types.VARBINARY, 0, true),
                           new Type(Types.LONGVARBINARY, 0, true)};
        add(Types.BINARY, binaries);

        // alternatives for the Types.VARBINARY type
        Type[] varbinaries = {new Type(Types.LONGVARBINARY, 0, true)};
        add(Types.VARBINARY, varbinaries);

        // alternatives for the Types.BIT type
        Type[] bits = {new Type(Types.CHAR, 0, false),
                       new Type(Types.TINYINT, 0, false),
                       new Type(Types.SMALLINT, 0, false),
                       new Type(Types.INTEGER, 0, false),
                       new Type(Types.NUMERIC, 1, true)};
        add(Types.BIT, bits);

        // alternatives for the Types.TINYINT type
        Type[] tinyints = {new Type(Types.SMALLINT, 0, false),
                           new Type(Types.INTEGER, 0, false),
                           new Type(Types.NUMERIC, 0, true)};
        add(Types.TINYINT, tinyints);

        // alternatives for the Types.DECIMAL type
        Type[] decimals = {new Type(Types.NUMERIC, 0, false)};
        add(Types.DECIMAL, decimals);

        // alternatives for the Types.DATE type
        Type[] dates = {new Type(Types.TIMESTAMP, 0, false)};
        add(Types.DATE, dates);

        // alternatives for the Types.BIGINT type
        long precision = Long.toString(Long.MAX_VALUE).length();
        Type[] bigints = {new Type(Types.NUMERIC, precision, true)};
        add(Types.BIGINT, bigints);
    }

    public Type getType(int type, long precision) {
        // check if type set supports the requested type directly
        Type result = _set.getType(type, precision);
        if (result == null) {
            // type not supported so return the mapping, if one is available
            result = (Type) _mappings.get(new Integer(type));
        }
        return result;
    }

    private void add(int type, Type[] mappings) {
        for (int i = 0; i < mappings.length; ++i) {
            Type requested = mappings[i];
            Type supported = _set.getType(requested.getType(),
                requested.getPrecision());
            if (supported == null) {
                _log.debug(
                    "TypeMapper: alternative mapping for type=" +
                    Descriptor.getDescriptor(type).getName() +
                    " is not supported by the database");
            } else {
                long precision = requested.getPrecision();
                long maxPrecision = supported.getPrecision();
                _log.debug(
                    "TypeMapper: alternative mapping for type=" +
                    Descriptor.getDescriptor(type).getName() +
                    ", precision=" + precision +
                    ", is supported by the database as " + supported);
                if (type == supported.getType() &&
                    ((precision > 0) && (precision < maxPrecision))) {
                    // if the requested type is supported by the database, and
                    // has a smaller precision, override the maximum precision
                    // defined by the supported type
                    supported = new Type(
                        supported.getType(), supported.getName(),
                        precision, supported.getParameters());
                }
                _mappings.put(new Integer(type), supported);
                break;
            }
        }
    }

}
