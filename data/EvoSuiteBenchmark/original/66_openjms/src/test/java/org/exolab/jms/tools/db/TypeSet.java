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
 * Copyright 2001-2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: TypeSet.java,v 1.1 2004/11/26 01:51:16 tanderson Exp $
 */

package org.exolab.jms.tools.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.persistence.PersistenceException;
import org.exolab.jms.persistence.SQLHelper;


/**
 * A helper class for managing the set of types supported by an RDBMS
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:16 $
 * @author      <a href="mailto:tima@intalio.com">Tim Anderson</a>
 */
class TypeSet {

    /**
     * A map of type identifiers to an ArrayList of corresponding RDBMS types
     */
    private HashMap _types = new HashMap();

    /**
     * The logger
     */
    private static final Log _log = LogFactory.getLog(TypeSet.class);


    /**
     * Construct a new instance
     *
     * @param connection the database connection to obtain meta-data from
     * @throws PersistenceException if meta-data cannot be accessed
     */
    public TypeSet(Connection connection) throws PersistenceException {
        ResultSet set = null;
        try {
            set = connection.getMetaData().getTypeInfo();
            while (set.next()) {
                int type = set.getInt("DATA_TYPE");
                String name = set.getString("TYPE_NAME");
                long precision = set.getLong("PRECISION");
                String createParams = set.getString("CREATE_PARAMS");

                Descriptor descriptor = Descriptor.getDescriptor(type);
                if (descriptor != null) {
                    addType(type, name, precision, createParams);
                } else {
                    _log.debug(
                        "TypeSet: skipping unknown type, type id=" + type
                        + ", name=" + name + ", precision=" + precision
                        + ", create params=" + createParams);
                }
            }
        } catch (SQLException exception) {
            throw new PersistenceException(
                "Failed to get type meta-data", exception);
        } finally {
            SQLHelper.close(set);
        }
    }

    /**
     * Return the closest type matching the requested type id and precision
     *
     * @param type the type identifier
     * @param precision the requested precision
     * @return the closest matching type, or null if none exists
     */
    public Type getType(int type, long precision) {
        Type result = null;
        ArrayList types = (ArrayList) _types.get(new Integer(type));
        if (types != null) {
            Iterator iter = types.iterator();
            while (iter.hasNext()) {
                Type option = (Type) iter.next();
                if (precision == -1 && (option.getPrecision() != -1 &&
                    option.getParameters())) {
                    // no precision was requested, but the type requires
                    // parameters
                    result = new Type(type, option.getName(),
                        option.getPrecision(),
                        option.getParameters());
                    break;
                } else if (precision <= option.getPrecision()) {
                    // use the requested precision
                    result = new Type(type, option.getName(), precision,
                        option.getParameters());
                    break;
                } else {
                    _log.debug("TypeSet: requested type=" + type
                        + " exceeds precision for supported " + option);
                }
            }
        } else {
            _log.debug("TypeSet: no types matching type id=" + type
                + ", type=" + Descriptor.getDescriptor(type).getName());
        }
        return result;
    }

    /**
     * Return the near type matching the supplied type id and precision.
     * This should only be invoked if the requested precision exceeds that
     * supported by the database.
     *
     * @param type the type identifier
     * @return the type, or null, if none exists
     */
    public Type getNearestType(int type, long precision) {
        Type result = null;
        ArrayList types = (ArrayList) _types.get(new Integer(type));
        if (types != null) {
            Iterator iter = types.iterator();
            Type nearest = null;
            while (iter.hasNext()) {
                Type option = (Type) iter.next();
                if (precision <= option.getPrecision()) {
                    // use the requested precision
                    result = new Type(type, option.getName(), precision,
                        option.getParameters());
                    break;
                } else {
                    nearest = option;
                }
            }
            if (result == null && nearest != null) {
                // use the closest precision
                result = new Type(type, nearest.getName(),
                    nearest.getPrecision(),
                    nearest.getParameters());
                _log.warn(
                    "TypeSet: requested type=" + type + ", precision="
                    + precision + " exceeds precision supported by database. "
                    + "Falling back to " + nearest);
            }
        } else {
            _log.debug("TypeSet: no types matching type id=" + type
                + ", type=" + Descriptor.getDescriptor(type).getName());
        }
        return result;
    }

    /**
     * Returns true if the type is supported
     *
     * @param type the type identifier
     * @return <code>true</code> if the type is supported
     */
    public boolean exists(int type) {
        return _types.containsKey(new Integer(type));
    }


    private void addType(int type, String name, long precision,
                         String createParams) {
        Descriptor descriptor = Descriptor.getDescriptor(type);
        boolean parameters = false;
        if (createParams != null && createParams.trim().length() != 0) {
            parameters = true;
        }

        Integer key = new Integer(type);
        ArrayList types = (ArrayList) _types.get(key);
        if (types == null) {
            types = new ArrayList();
            _types.put(key, types);
        }

        _log.debug("TypeSet: type id=" + type
            + ", type=" + descriptor.getName()
            + ", name=" + name
            + ", precision=" + precision
            + ", createParams=" + createParams);
        types.add(new Type(type, name, precision, parameters));
    }

} //-- TypeSet
