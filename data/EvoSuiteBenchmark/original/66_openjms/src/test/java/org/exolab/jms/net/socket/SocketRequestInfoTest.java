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
 * $Id: SocketRequestInfoTest.java,v 1.3 2005/12/01 13:44:39 tanderson Exp $
 */
package org.exolab.jms.net.socket;

import junit.framework.TestCase;

import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.util.Properties;
import org.exolab.jms.net.orb.ORB;


/**
 * Tests the {@link SocketRequestInfo} class.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/12/01 13:44:39 $
 */
public class SocketRequestInfoTest extends TestCase {

    /**
     * Construct a new <code>SocketRequestInfoTest</code>.
     *
     * @param name the name of the test to run
     */
    public SocketRequestInfoTest(String name) {
        super(name);
    }

    /**
     * Tests accessors.
     *
     * @throws Exception for any error
     */
    public void testAccessors() throws Exception {
        final String uri = "tcp://localhost:8050";
        final String alternativeURI = "tcp://foo.org:9090";
        final boolean bindAll = true;
        final boolean bindOne = false;

        SocketRequestInfo info = populate(uri, alternativeURI, bindAll);

        assertEquals(uri, info.getURI().toString());
        assertEquals(alternativeURI, info.getAlternativeHost().toString());
        assertEquals(bindAll, info.getBindAll());
        info.setBindAll(bindOne);
        assertEquals(bindOne, info.getBindAll());
    }

    /**
     * Tests {@link SocketRequestInfo#equals}.
     *
     * @throws Exception for any error
     */
    public void testEquals() throws Exception {
        final String uri = "tcp://localhost:8050";
        final String alternativeURI = "tcp://foo.org:9090";
        final boolean bindAll = true;

        SocketRequestInfo info1 = populate(uri, alternativeURI, bindAll);
        SocketRequestInfo info2 = populate(uri, alternativeURI, bindAll);
        assertEquals(info1, info2);

        SocketRequestInfo info3 = populate(uri, null, bindAll);
        assertFalse(info1.equals(info3));

        SocketRequestInfo info4 = populate(uri, alternativeURI, !bindAll);
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

        Properties properties = new Properties(prefix);
        SocketRequestInfo info1 = populate(uri, alternativeHost, bindAll);
        info1.export(properties);

        SocketRequestInfo info2 = new SocketRequestInfo(
                new URI(properties.get(ORB.PROVIDER_URI)),
                properties);
        assertEquals(info1, info2);

        assertEquals(uri, info2.getURI().toString());
        assertEquals(alternativeHost, info2.getAlternativeHost());
        assertEquals(bindAll, info2.getBindAll());
    }

    /**
     * Helper to populate an {@link SocketRequestInfo}.
     *
     * @param uri            the URI
     * @param alternativeHost the alternative host
     * @param bindAll        indicates how socket connections should be
     *                       accepted, on a multi-homed host
     * @return a new <code>SocketRequestInfo</code>
     * @throws Exception for any error
     */
    private SocketRequestInfo populate(String uri, String alternativeHost,
                                       boolean bindAll)
            throws Exception {
        SocketRequestInfo info = new SocketRequestInfo(new URI(uri));
        info.setAlternativeHost(alternativeHost);
        info.setBindAll(bindAll);
        return info;
    }

}
