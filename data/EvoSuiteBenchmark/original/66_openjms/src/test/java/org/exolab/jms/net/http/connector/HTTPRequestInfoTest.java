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
 * $Id: HTTPRequestInfoTest.java,v 1.1 2005/05/03 13:46:00 tanderson Exp $
 */
package org.exolab.jms.net.http.connector;

import junit.framework.TestCase;

import org.exolab.jms.net.http.HTTPRequestInfo;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.util.Properties;
import org.exolab.jms.net.util.SSLProperties;


/**
 * Tests the {@link HTTPRequestInfo} class.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/05/03 13:46:00 $
 */
public class HTTPRequestInfoTest extends TestCase {

    /**
     * Construct a new <code>HTTPRequestInfoTest</code>.
     *
     * @param name the name of the test to run
     */
    public HTTPRequestInfoTest(String name) {
        super(name);
    }

    /**
     * Tests accessors.
     *
     * @throws Exception for any error
     */
    public void testAccessors() throws Exception {
        final String uri = "http://localhost:80";
        final String proxyHost = "foo";
        final int proxyPort = 1030;
        final String proxyUser = "bar";
        final String proxyPassword = "fly";
        final SSLProperties ssl = new SSLProperties();

        HTTPRequestInfo info = populate(uri, proxyHost, proxyPort, proxyUser,
                                        proxyPassword, ssl);

        assertEquals(uri, info.getURI().toString());
        assertEquals(proxyHost, info.getProxyHost());
        assertEquals(proxyPort, info.getProxyPort());
        assertEquals(proxyUser, info.getProxyUser());
        assertEquals(proxyPassword, info.getProxyPassword());
        assertEquals(ssl, info.getSSLProperties());
    }

    /**
     * Tests {@link HTTPRequestInfo#equals}.
     *
     * @throws Exception for any error
     */
    public void testEquals() throws Exception {
        final String uri = "http://exolab.org:80";
        final String proxyHost = "boo";
        final int proxyPort = 9090;
        final String proxyUser = "hoo";
        final String proxyPassword = "shoo";
        final SSLProperties ssl = new SSLProperties();

        HTTPRequestInfo info1 = populate(uri, proxyHost, proxyPort, proxyUser,
                                         proxyPassword, null);
        HTTPRequestInfo info2 = populate(uri, proxyHost, proxyPort, proxyUser,
                                         proxyPassword, null);

        assertEquals(info1, info2);

        info2.setSSLProperties(ssl);
        assertFalse(info1.equals(info2));
    }

    /**
     * Tests properties.
     *
     * @throws Exception for any error
     */
    public void testProperties() throws Exception {
        final String prefix = "org.exolab.jms.net.https.";
        final String uri = "https://exolab.org";
        final String proxyHost = "binky";
        final int proxyPort = 1032;
        final String proxyUser = "gum";
        final String proxyPassword = "ball";
        final SSLProperties ssl = new SSLProperties();
        ssl.setKeyStore("keyStore");
        ssl.setKeyStorePassword("keyStorePassword");
        ssl.setKeyStoreType("JKS");
        ssl.setTrustStore("trustStore");
        ssl.setTrustStorePassword("trustStorePassword");
        ssl.setTrustStoreType("PCKS12");

        Properties properties = new Properties(prefix);
        HTTPRequestInfo info1 = populate(uri, proxyHost, proxyPort, proxyUser,
                                         proxyPassword, ssl);
        info1.export(properties);
        HTTPRequestInfo info2 = new HTTPRequestInfo(new URI(uri), properties);
        assertEquals(info1, info2);

        assertEquals(uri, info2.getURI().toString());
        assertEquals(proxyHost, info2.getProxyHost());
        assertEquals(proxyPort, info2.getProxyPort());
        assertEquals(proxyUser, info2.getProxyUser());
        assertEquals(proxyPassword, info2.getProxyPassword());
        assertEquals(ssl, info2.getSSLProperties());
    }

    /**
     * Helper to populate an {@link HTTPRequestInfo}.
     *
     * @param uri      the URI
     * @param host     the proxy host
     * @param port     the proxy port
     * @param user     the proxy user
     * @param password the proxy password
     * @param ssl      SSL properties
     * @return a new <code>HTTPRequestInfo</code>
     * @throws Exception for any error
     */
    private HTTPRequestInfo populate(String uri, String host, int port,
                                     String user, String password,
                                     SSLProperties ssl)
            throws Exception {
        HTTPRequestInfo info = new HTTPRequestInfo(new URI(uri));
        info.setProxyHost(host);
        info.setProxyPort(port);
        info.setProxyUser(user);
        info.setProxyPassword(password);
        info.setSSLProperties(ssl);
        return info;
    }

}
