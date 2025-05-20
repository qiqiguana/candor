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
 * Copyright 2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: Properties.java,v 1.2 2005/05/03 13:45:59 tanderson Exp $
 */

package org.exolab.jms.net.util;

import java.util.Map;
import java.util.HashMap;

import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.uri.InvalidURIException;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.uri.URIHelper;


/**
 * Helper class for manipulating string property maps.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/05/03 13:45:59 $
 */
public final class Properties {

    /**
     * The properties.
     */
    private final Map _properties;

    /**
     * The property prefix. If non-null, this is prepended to unqualified
     * property names.
     */
    private final String _prefix;


    /**
     * Construct a new <code>Properties</code>.
     *
     * @param prefix     the property name prefix. If non-null, this is
     *                   prepended to unqualified property names.
     */
    public Properties(String prefix) {
        this(null, prefix);
    }

    /**
     * Construct a new <code>Properties</code>.
     *
     * @param properties the properties to use. May be <code>null</code>
     * @param prefix     the property name prefix. If non-null, this is
     *                   prepended to property names before performing lookups
     */
    public Properties(Map properties, String prefix) {
        _properties = (properties != null) ? properties : new HashMap();
        _prefix = prefix;
    }

    /**
     * Adds a property to the underlying map.
     * If the property already exists, it will be replaced.
     *
     * @param name  the property name
     * @param value the property value. May be <code>null</code>
     */
    public void set(String name, String value) {
        _properties.put(getName(name), value);
    }

    /**
     * Adds a property to the underlying map, iff its value is non-null.
     * If the non-null, and the property already exists, it will be replaced.
     *
     * @param name  the property name
     * @param value the property value
     */
    public void setNonNull(String name, String value) {
        if (value != null) {
            _properties.put(getName(name), value);
        }
    }

    /**
     * Adds a boolean property to the underlying map, as a String.
     * If the property already exists, it will be replaced.
     *
     * @param name  the property name
     * @param value the property value
     */
    public void set(String name, boolean value) {
        Boolean bool = (value) ? Boolean.TRUE : Boolean.FALSE;
        set(name, bool.toString());
    }

    /**
     * Adds an integer property to the underlying map, as a String.
     * If the property already exists, it will be replaced.
     *
     * @param name  the property name
     * @param value the property value
     */
    public void set(String name, int value) {
        set(name, Integer.toString(value));
    }

    /**
     * Adds an object property to the underlying map, as a String.
     * If the property already exists, it will be replaced.
     *
     * @param name  the property name
     * @param value the property value
     */
    public void set(String name, Object value) {
        if (value != null) {
            set(name, value.toString());
        } else {
            set(name, null);
        }
    }

    /**
     * Adds an object property to the underlying map, as a String,
     * iff its value is non-null.
     * If non-null, and the property already exists, it will be replaced.
     *
     * @param name  the property name
     * @param value the property value
     */
    public void setNonNull(String name, Object value) {
        if (value != null) {
            set(name, value.toString());
        }
    }

    /**
     * Returns the value of a property.
     *
     * @param name the property name
     * @return the corresponding value, or <code>null</code> if none can be
     *         found
     * @throws ResourceException if the property isn't a string
     */
    public String get(String name) throws ResourceException {
        Object result = null;
        name = getName(name);
        result = _properties.get(name);
        if (result != null && !(result instanceof String)) {
            throw new ResourceException("Invalid type for property=" + name);
        }
        return (String) result;
    }

    /**
     * Returns the value of a boolean property.
     *
     * @param name         the property name
     * @param defaultValue the value to return if the property doesn't exist.
     * @return the corresponding value, or <code>defaultValue</code> if none can
     *         be  found
     * @throws ResourceException if the property isn't a valid boolean
     */
    public boolean getBoolean(String name, boolean defaultValue)
            throws ResourceException {
        boolean result = defaultValue;
        String value = get(name);
        if (value != null) {
            if (value.equalsIgnoreCase("true")) {
                result = true;
            } else if (value.equalsIgnoreCase("false")) {
                result = false;
            } else {
                throw new ResourceException("Invalid boolean for property="
                                            + getName(name)
                                            + ": " + value);
            }
        }
        return result;
    }

    /**
     * Returns the value of an integer property.
     *
     * @param name         the property name
     * @param defaultValue the value to return if the property doesn't exist.
     * @return the corresponding value, or <code>defaultValue</code> if none can
     *         be  found
     * @throws ResourceException if the property isn't a valid integer
     */
    public int getInt(String name, int defaultValue)
            throws ResourceException {
        int result = defaultValue;
        String value = get(name);
        if (value != null) {
            try {
                result = Integer.parseInt(value);
            } catch (NumberFormatException exception) {
                throw new ResourceException("Invalid int for property="
                                            + getName(name)
                                            + ": " + value);
            }
        }
        return result;
    }

    /**
     * Returns the value of an URI property.
     *
     * @param name the property name
     * @return the corresponding URI, or <code>null</code> if none can be found
     * @throws ResourceException if the URI is invalid
     */
    public URI getURI(String name) throws ResourceException {
        URI result = null;
        String uri = get(name);
        if (uri != null) {
            try {
                result = URIHelper.parse(uri);
            } catch (InvalidURIException exception) {
                throw new ResourceException("Invalid URI for property="
                                            + getName(name)
                                            + ": " + uri);
            }
        }
        return result;
    }

    /**
     * Returns the underlying properties.
     *
     * @return the underlying properties
     */
    public Map getProperties() {
        return _properties;
    }

    /**
     * Prepends the supplied name with the property prefix, if it is
     * unqualified (i.e, contains no "."). If the prefix is null, returns the
     * name unchanged.
     *
     * @param name the property name
     * @return the fully qualified property name.
     */
    private String getName(String name) {
        String result;
        if (_prefix != null && name.indexOf('.') == -1) {
            result = _prefix + name;
        } else {
            result = name;
        }
        return result;
    }

}
