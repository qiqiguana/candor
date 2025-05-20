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
 * Copyright 2004-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: SSLUtil.java,v 1.2 2005/05/03 13:46:03 tanderson Exp $
 */
package org.exolab.jms.net.util;

import java.io.IOException;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;


/**
 * Helper class for TCPS and HTTPS tests.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/05/03 13:46:03 $
 */
public class SSLUtil {

    /**
     * Helper to return connection properties for the HTTPS connector.
     *
     * @param keystore the name of the keystore
     * @param password the keystore password
     * @throws IOException if the keystore can't be found
     */
    public static Map getHTTPSProperties(String keystore, String password)
        throws IOException {
        String path = getKeyStorePath(keystore);
        Map result = new HashMap();
        result.put("org.exolab.jms.net.https.keyStore", path);
        result.put("org.exolab.jms.net.https.keyStorePassword", password);
        result.put("org.exolab.jms.net.https.trustStore", path);
        return result;
    }

    /**
     * Helper to return connection properties for the TCPS connector.
     *
     * @param keystore the name of the keystore
     * @param password the keystore password
     * @throws IOException if the keystore can't be found
     */
    public static Map getTCPSProperties(String keystore, String password)
        throws IOException {
        String path = getKeyStorePath(keystore);
        Map result = new HashMap();
        result.put("org.exolab.jms.net.tcps.keyStore", path);
        result.put("org.exolab.jms.net.tcps.keyStorePassword", password);
        result.put("org.exolab.jms.net.tcps.trustStore", path);
        return result;
    }

    /**
     * Helper to return a populated {@link SSLProperties}.
     * <p/>
     * The truststore is assumed to be the same as the keystore.
     *
     * @param keystore the name of the keystore
     * @param password the keystore password
     * @throws IOException if the keystore can't be found
     */
    public static SSLProperties getSSLProperties(String keystore,
                                                 String password)
            throws IOException {
        String path = getKeyStorePath(keystore);
        SSLProperties result = new SSLProperties();
        result.setKeyStore(path);
        result.setKeyStorePassword(password);
        result.setTrustStore(path);
        return result;
    }

    /**
     * Helper to locate a keystore wthin the filesystem
     *
     * @param name the name of the keystore
     * @throws IOException if the keystore can't be found
     */
    public static String getKeyStorePath(String name) throws IOException {
        File keystore = new File(name);
        if (!keystore.isAbsolute() || !keystore.exists()) {
            final String paths[] = {"modules", "net", "target"};
            String workingDir = System.getProperty("user.dir");
            for (int i = 0; i < paths.length; ++i) {
                if (workingDir.indexOf(paths[i]) == -1) {
                    if (!workingDir.endsWith(File.separator)) {
                        workingDir += File.separator;
                    }
                    workingDir += paths[i];
                }
            }
            keystore = new File(workingDir + File.separator + name);
        }
        if (!keystore.exists()) {
            throw new IOException("Failed to locate keystore: " + keystore);
        }
        return keystore.getPath();
    }

    /**
     * Clears javax.net.ssl.* properties set during tests, to help ensure
     * that they don't interfere in subsequent tests.
     */
    public static void clearProperties() {
        Properties properties = System.getProperties();
        properties.remove(SSLHelper.KEY_STORE);
        properties.remove(SSLHelper.KEY_STORE_PASSWORD);
        properties.remove(SSLHelper.KEY_STORE_TYPE);
        properties.remove(SSLHelper.TRUST_STORE);
        properties.remove(SSLHelper.TRUST_STORE_PASSWORD);
        properties.remove(SSLHelper.TRUST_STORE_TYPE);
    }
    

}
