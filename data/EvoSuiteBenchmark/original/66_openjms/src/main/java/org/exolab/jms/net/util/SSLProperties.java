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
 * $Id: SSLProperties.java,v 1.4 2005/05/03 13:45:59 tanderson Exp $
 */
package org.exolab.jms.net.util;

import org.exolab.jms.net.connector.ResourceException;


/**
 * Helper class to hold the SSL properties (used by TPCS & HTTPS connectors).
 *
 * @author <a href="mailto:daniel.otero@mac.com">Daniel Otero</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $
 */
public final class SSLProperties {

    /**
     * The keystore to use. If <code>null</code>, indicates to use the default
     * keystore.
     */
    private String _keyStore;

    /**
     * The keystore password. If <code>null</code>, indicates to use the default
     * password.
     */
    private String _keyStorePassword;

    /**
     * The keystore type. If <code>null</code>, indicates to use the default
     * type.
     */
    private String _keyStoreType;

    /**
     * The truststore to use. If <code>null</code>, indicates to use the default
     * truststore.
     */
    private String _trustStore;

    /**
     * The truststore password. If <code>null</code>, indicates to use the
     * default password.
     */
    private String _trustStorePassword;

    /**
     * The truststore type. If <code>null</code>, indicates to use the default
     * type.
     */
    private String _trustStoreType;

    /**
     * Connection property to indicate the keystore to use.
     */
    private static final String KEY_STORE = "keyStore";

    /**
     * Connection property to indicate the keystore password.
     */
    private static final String KEY_STORE_PASSWORD = "keyStorePassword";

    /**
     * Connection property to indicate the keystore type.
     */
    private static final String KEY_STORE_TYPE = "keyStoreType";

    /**
     * Connection property to indicate the truststore to use.
     */
    private static final String TRUST_STORE = "trustStore";

    /**
     * Connection property to indicate the truststore password.
     */
    private static final String TRUST_STORE_PASSWORD = "trustStorePassword";

    /**
     * Connection property to indicate the truststore type.
     */
    private static final String TRUST_STORE_TYPE = "trustStoreType";

    /**
     * Construct a new <code>SSLProperties</code>.
     */
    public SSLProperties() {
    }

    /**
     * Construct a new <code>SSLProperties</code>.
     *
     * @param properties the properties to populate this from
     * @throws ResourceException if any of the properties are invalid
     */
    public SSLProperties(Properties properties) throws ResourceException {
        setKeyStore(properties.get(KEY_STORE));
        setKeyStorePassword(properties.get(KEY_STORE_PASSWORD));
        setKeyStoreType(properties.get(KEY_STORE_TYPE));
        setTrustStore(properties.get(TRUST_STORE));
        setTrustStorePassword(properties.get(TRUST_STORE_PASSWORD));
        setTrustStoreType(properties.get(TRUST_STORE_TYPE));
    }

    /**
     * Returns the keystore.
     *
     * @return the keystore, or <code>null</code> if unset
     */
    public String getKeyStore() {
        return _keyStore;
    }

    /**
     * Sets the keystore..
     *
     * @param store the keystore
     */
    public void setKeyStore(String store) {
        _keyStore = store;
    }

    /**
     * Returns the keystore password.
     *
     * @return the keystore password, or <code>null</code> if unset
     */
    public String getKeyStorePassword() {
        return _keyStorePassword;
    }

    /**
     * Sets the keystore password.
     *
     * @param password the keystore password
     */
    public void setKeyStorePassword(String password) {
        _keyStorePassword = password;
    }

    /**
     * Returns the keystore type.
     *
     * @return the keystore type, or <code>null</code> if unset
     */
    public String getKeyStoreType() {
        return _keyStoreType;
    }

    /**
     * Sets the keystore type.
     *
     * @param type the the keystore type
     */
    public void setKeyStoreType(String type) {
        _keyStoreType = type;
    }

    /**
     * Returns the truststore.
     *
     * @return the truststore, or <code>null</code> if unset
     */
    public String getTrustStore() {
        return _trustStore;
    }

    /**
     * Sets the truststore.
     *
     * @param store the truststore
     */
    public void setTrustStore(String store) {
        _trustStore = store;
    }

    /**
     * Returns the truststore password.
     *
     * @return the truststore passowrd, or <code>null</code> if unset
     */
    public String getTrustStorePassword() {
        return _trustStorePassword;
    }

    /**
     * Sets the truststore password.
     *
     * @param password the truststore password
     */
    public void setTrustStorePassword(String password) {
        _trustStorePassword = password;
    }

    /**
     * Returns the truststore type.
     *
     * @return the truststore type, or <code>null</code> if unset
     */
    public String getTrustStoreType() {
        return _trustStoreType;
    }

    /**
     * Sets the truststore type.
     *
     * @param type the the trusstore type
     */
    public void setTrustStoreType(String type) {
        _trustStoreType = type;
    }

    /**
     * Test to see if this instance has been populated.
     *
     * @return <code>true</code> if this hasn't been populated
     */
    public boolean isEmpty() {
        return _keyStore == null && _keyStorePassword == null
                && _keyStoreType == null && _trustStore == null
                && _trustStorePassword == null && _trustStoreType == null;
    }

    /**
     * Checks whether this instance is equal to another.
     *
     * @param other the object to compare
     * @return <code>true</code> if the two instances are equal; otherwise
     *         <code>false</code>
     */
    public boolean equals(Object other) {
        boolean equal = (this == other);
        if (!equal) {
            if (other instanceof SSLProperties) {
                SSLProperties props = (SSLProperties) other;
                if (equals(_keyStore, props._keyStore)
                        && equals(_keyStorePassword, props._keyStorePassword)
                        && equals(_keyStoreType, props._keyStoreType)
                        && equals(_trustStore, props._trustStore)
                        && equals(_trustStorePassword,
                                  props._trustStorePassword)
                        && equals(_trustStoreType, props._trustStoreType)) {
                    equal = true;
                }
            } else {
                equal = false;
            }
        }
        return equal;
    }

    /**
     * Helper to export this to a {@link Properties} instance.
     *
     * @param properties the properties to export to.
     */
    public void export(Properties properties) {
        properties.setNonNull(KEY_STORE, getKeyStore());
        properties.setNonNull(KEY_STORE_PASSWORD, getKeyStorePassword());
        properties.setNonNull(KEY_STORE_TYPE, getKeyStoreType());
        properties.setNonNull(TRUST_STORE, getTrustStore());
        properties.setNonNull(TRUST_STORE_PASSWORD, getTrustStorePassword());
        properties.setNonNull(TRUST_STORE_TYPE, getTrustStoreType());
    }

    /**
     * Helper to compare two objects for equality.
     *
     * @param o1 the first object to compare
     * @param o2 the second object to compare
     * @return <code>true</code> if the objects are equal, otherwise
     *         <code>false</code>
     */
    private boolean equals(Object o1, Object o2) {
        boolean equal = (o1 == null && o2 == null);
        if (!equal) {
            if (o1 != null && o1.equals(o2)) {
                equal = true;
            }
        }
        return equal;
    }

}
