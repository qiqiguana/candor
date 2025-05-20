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
 * $Id: SSLPropertiesTest.java,v 1.1 2005/05/03 13:46:03 tanderson Exp $
 */
package org.exolab.jms.net.util;

import junit.framework.TestCase;


/**
 * Tests the {@link SSLProperties} class.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/05/03 13:46:03 $
 */
public class SSLPropertiesTest extends TestCase {

    final String keyStore = "keyStore";
    final String keyStorePassword = "keyStorePassword";
    final String keyStoreType = "JKS";
    final String trustStore = "trustStore";
    final String trustStorePassword = "trustStorePassword";
    final String trustStoreType = "PCKS12";
    

    /**
     * Construct a new <code>SSLPropertiesTest</code>.
     *
     * @param name the name of the test to run
     */
    public SSLPropertiesTest(String name) {
        super(name);
    }

    /**
     * Tests accessors.
     */
    public void testAccessors() {
        SSLProperties properties = populate(keyStore, keyStorePassword,
                                            keyStoreType, trustStore,
                                            trustStorePassword, trustStoreType);

        assertEquals(keyStore, properties.getKeyStore());
        assertEquals(keyStorePassword, properties.getKeyStorePassword());
        assertEquals(keyStoreType, properties.getKeyStoreType());
        assertEquals(trustStore, properties.getTrustStore());
        assertEquals(trustStorePassword, properties.getTrustStorePassword());
        assertEquals(trustStoreType, properties.getTrustStoreType());
    }

    /**
     * Tests {@link SSLProperties#isEmpty}.
     */
    public void testIsEmpty() {
        SSLProperties properties = new SSLProperties();
        assertTrue(properties.isEmpty());

        properties.setKeyStore("foo");
        assertFalse(properties.isEmpty());

        properties = new SSLProperties();
        properties.setKeyStorePassword("bar");
        assertFalse(properties.isEmpty());

        properties = new SSLProperties();
        properties.setKeyStoreType("JKS");
        assertFalse(properties.isEmpty());

        properties = new SSLProperties();
        properties.setTrustStore("foo");
        assertFalse(properties.isEmpty());

        properties = new SSLProperties();
        properties.setTrustStorePassword("bar");
        assertFalse(properties.isEmpty());

        properties = new SSLProperties();
        properties.setTrustStoreType("PCKS12");
        assertFalse(properties.isEmpty());
    }

    /**
     * Tests {@link SSLProperties#equals}.
     */
    public void testEquals() {

        SSLProperties empty = new SSLProperties();
        assertEquals(empty, empty);

        SSLProperties ssl1 = populate(keyStore, keyStorePassword, keyStoreType,
                                      trustStore, trustStorePassword,
                                      trustStoreType);
        assertFalse(ssl1.equals(empty));

        SSLProperties ssl2 = populate(keyStore, keyStorePassword, keyStoreType,
                                      trustStore, trustStorePassword,
                                      trustStoreType);
        assertEquals(ssl1, ssl2);

        SSLProperties ssl3 = populate(null, keyStorePassword, keyStoreType,
                                      trustStore, trustStorePassword,
                                      trustStoreType);
        assertFalse(ssl1.equals(ssl3));

        SSLProperties ssl4 = populate(keyStore, null, keyStoreType,
                                      trustStore, trustStorePassword,
                                      trustStoreType);
        assertFalse(ssl1.equals(ssl4));

        SSLProperties ssl5 = populate(keyStore, keyStorePassword, null,
                                      trustStore, trustStorePassword,
                                      trustStoreType);
        assertFalse(ssl1.equals(ssl5));

        SSLProperties ssl6 = populate(keyStore, keyStorePassword,
                                      keyStoreType, null, trustStorePassword,
                                      trustStoreType);
        assertFalse(ssl1.equals(ssl6));

        SSLProperties ssl7 = populate(keyStore, keyStorePassword, keyStoreType,
                                      trustStore, null, trustStoreType);
        assertFalse(ssl1.equals(ssl7));

        SSLProperties ssl8 = populate(keyStore, keyStorePassword, keyStoreType,
                                      trustStore, trustStorePassword, null);
        assertFalse(ssl1.equals(ssl8));
    }

    /**
     * Tests properties.
     *
     * @throws Exception for any error
     */
    public void testProperties() throws Exception {
        final String prefix = "org.exolab.jms.net.https.";
        SSLProperties ssl1 = populate(keyStore, keyStorePassword, keyStoreType,
                                      trustStore, trustStorePassword,
                                      trustStoreType);

        Properties properties = new Properties(prefix);
        ssl1.export(properties);

        SSLProperties ssl2 = new SSLProperties(properties);
        assertEquals(ssl1, ssl2);

        assertEquals(keyStore, ssl2.getKeyStore());
        assertEquals(keyStorePassword, ssl2.getKeyStorePassword());
        assertEquals(keyStoreType, ssl2.getKeyStoreType());
        assertEquals(trustStore, ssl2.getTrustStore());
        assertEquals(trustStorePassword, ssl2.getTrustStorePassword());
        assertEquals(trustStoreType, ssl2.getTrustStoreType());
    }

    /**
     * Helper to populate an {@link SSLProperties}.
     *
     * @param keyStore           the keystore
     * @param keyStorePassword   the keystore password
     * @param keyStoreType       the keystore type
     * @param trustStore         the truststore
     * @param trustStorePassword the truststore password
     * @param trustStoreType     the truststore type
     * @return a new <code>SSLProperties</code>
     */
    private SSLProperties populate(String keyStore, String keyStorePassword,
                                   String keyStoreType, String trustStore,
                                   String trustStorePassword,
                                   String trustStoreType) {
        SSLProperties result = new SSLProperties();
        result.setKeyStore(keyStore);
        result.setKeyStorePassword(keyStorePassword);
        result.setKeyStoreType(keyStoreType);
        result.setTrustStore(trustStore);
        result.setTrustStorePassword(trustStorePassword);
        result.setTrustStoreType(trustStoreType);
        return result;
    }

}
