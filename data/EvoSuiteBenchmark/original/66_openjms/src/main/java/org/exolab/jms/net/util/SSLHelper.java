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
 * $Id: SSLHelper.java,v 1.3 2005/07/22 23:38:04 tanderson Exp $
 */
package org.exolab.jms.net.util;

import java.util.Properties;


/**
 * Helper class for configuring the secure socket layer (SSL).
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $
 */
public class SSLHelper {

    /**
     * System property to indicate the keystore to use.
     */
    public static final String KEY_STORE = "javax.net.ssl.keyStore";

    /**
     * System property to indicate the keystore type.
     */
    public static final String KEY_STORE_TYPE
            = "javax.net.ssl.keyStoreType";

    /**
     * System property to indicate the keystore password.
     */
    public static final String KEY_STORE_PASSWORD
            = "javax.net.ssl.keyStorePassword";

    /**
     * System property to indicate the truststore to use.
     */
    public static final String TRUST_STORE = "javax.net.ssl.trustStore";

    /**
     * System property to indicate the truststore password.
     */
    public static final String TRUST_STORE_PASSWORD
            = "javax.net.ssl.trustStorePassword";

    /**
     * System property to indicate the truststore type.
     */
    public static final String TRUST_STORE_TYPE
            = "javax.net.ssl.trustStoreType";

    /**
     * Configure the secure socket layer.
     * <p/>
     * This sets system properties corresponding to those specified by
     * <code>properties</code>
     *
     * @param properties the properties to use
     * @throws SecurityException if the security manager doesn't allow a
     *                           property to be updated.
     */
    public static void configure(SSLProperties properties)
            throws SecurityException {
        update(KEY_STORE, properties.getKeyStore());
        update(KEY_STORE_PASSWORD, properties.getKeyStorePassword());
        update(KEY_STORE_TYPE, properties.getKeyStoreType());
        update(TRUST_STORE, properties.getTrustStore());
        update(TRUST_STORE_PASSWORD, properties.getTrustStorePassword());
        update(TRUST_STORE_TYPE,  properties.getTrustStoreType());
    }

    /**
     * Update a system property, setting it if the corresponding value isn't
     * null, or removing it if it is.
     *
     * @param key   the property name
     * @param value the property value
     * @throws SecurityException if the security manager doesn't allow a
     *                           property to be updated.
     */
    private static void update(String key, String value)
        throws SecurityException {
        if (value != null) {
            System.setProperty(key, value);
        } else if (System.getProperty(key) != null) {
            Properties properties = System.getProperties();
            properties.remove(key);
        }
    }
}
