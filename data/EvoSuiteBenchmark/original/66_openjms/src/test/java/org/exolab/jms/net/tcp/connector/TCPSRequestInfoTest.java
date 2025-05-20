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
 * $Id: TCPSRequestInfoTest.java,v 1.3 2005/12/01 13:44:39 tanderson Exp $
 */
package org.exolab.jms.net.tcp.connector;

import junit.framework.TestCase;

import org.exolab.jms.net.socket.SocketRequestInfo;
import org.exolab.jms.net.socket.SocketRequestInfoTest;
import org.exolab.jms.net.tcp.TCPSRequestInfo;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.util.Properties;
import org.exolab.jms.net.util.SSLProperties;
import org.exolab.jms.net.orb.ORB;


/**
 * Tests the {@link TCPSRequestInfo} class.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/12/01 13:44:39 $
 */
public class TCPSRequestInfoTest extends TestCase {

    /**
     * Construct a new <code>TCPSRequestInfoTest</code>.
     *
     * @param name the name of the test to run
     */
    public TCPSRequestInfoTest(String name) {
        super(name);
    }

    /**
     * Tests accessors.
     * <p/>
     * NOTE: accessors provided by {@link SocketRequestInfo} are tested in
     * {@link SocketRequestInfoTest}.
     *
     * @throws Exception for any error
     */
    public void testAccessors() throws Exception {
        final String uri = "tcps://localhost:8050";
        final SSLProperties ssl = new SSLProperties();
        final boolean clientAuthReqd = true;
        final boolean clientAuthNotReqd = false;

        TCPSRequestInfo info = populate(uri, null, true, ssl, clientAuthReqd);

        assertEquals(ssl, info.getSSLProperties());
        assertEquals(clientAuthReqd, info.getNeedClientAuth());

        info.setNeedClientAuth(clientAuthNotReqd);
        assertEquals(clientAuthNotReqd, info.getNeedClientAuth());
    }

    /**
     * Tests {@link TCPSRequestInfo#equals}.
     *
     * @throws Exception for any error
     */
    public void testEquals() throws Exception {
        final String uri = "tcp://localhost:8050";
        final String alternativeURI = "tcp://foo.org:9090";
        final boolean bindAll = true;
        final SSLProperties ssl = new SSLProperties();
        final boolean clientAuthReqd = true;
        final boolean clientAuthNotReqd = false;

        TCPSRequestInfo info1 = populate(uri, alternativeURI, bindAll,
                                         ssl, clientAuthReqd);
        TCPSRequestInfo info2 = populate(uri, alternativeURI, bindAll,
                                         ssl, clientAuthReqd);
        assertEquals(info1, info2);

        TCPSRequestInfo info3 = populate(uri, alternativeURI, bindAll,
                                         null, clientAuthReqd);
        assertFalse(info1.equals(info3));

        TCPSRequestInfo info4 = populate(uri, alternativeURI, bindAll,
                                         ssl, clientAuthNotReqd);
        assertFalse(info1.equals(info4));
    }

    /**
     * Tests properties.
     *
     * @throws Exception for any error
     */
    public void testProperties() throws Exception {
        final String prefix = "org.exolab.jms.net.tcps.";
        final String uri = "tcps://exolab.org:4040/";
        final String alternativeHost = "localhost";
        final boolean bindAll = false;
        final SSLProperties ssl = new SSLProperties();
        final boolean clientAuthReqd = true;

        ssl.setKeyStore("keyStore");
        ssl.setKeyStorePassword("keyStorePassword");
        ssl.setKeyStoreType("JKS");
        ssl.setTrustStore("trustStore");
        ssl.setTrustStorePassword("trustStorePassword");
        ssl.setTrustStoreType("PCKS12");

        Properties properties = new Properties(prefix);
        TCPSRequestInfo info1 = populate(uri, alternativeHost, bindAll,
                                         ssl, clientAuthReqd);
        info1.export(properties);

        TCPSRequestInfo info2 = new TCPSRequestInfo(
                new URI(properties.get(ORB.PROVIDER_URI)),
                properties);

        assertEquals(info1, info2);

        assertEquals(ssl, info2.getSSLProperties());
        assertEquals(clientAuthReqd, info2.getNeedClientAuth());
    }

    /**
     * Helper to populate an {@link TCPSRequestInfo}.
     *
     * @param uri            the URI
     * @param alternativeHost the alternative URI
     * @param bindAll        indicates how socket connections should be
     *                       accepted, on a multi-homed host
     * @param ssl            SSL properties
     * @param needClientAuth if <code>true</code>, the clients must authenticate
     *                       themselves.
     * @return a new <code>TCPSRequestInfo</code>
     * @throws Exception for any error
     */
    private TCPSRequestInfo populate(String uri, String alternativeHost,
                                     boolean bindAll, SSLProperties ssl,
                                     boolean needClientAuth)
            throws Exception {
        TCPSRequestInfo info = new TCPSRequestInfo(new URI(uri));
        info.setAlternativeHost(alternativeHost);
        info.setBindAll(bindAll);
        info.setSSLProperties(ssl);
        info.setNeedClientAuth(needClientAuth);
        return info;
    }

}
