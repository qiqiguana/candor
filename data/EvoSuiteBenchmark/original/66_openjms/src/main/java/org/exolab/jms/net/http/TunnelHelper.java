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
 * $Id: TunnelHelper.java,v 1.2 2005/04/08 15:16:46 tanderson Exp $
 */
package org.exolab.jms.net.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;


/**
 * Helper class for establishing connections to the tunnel servlet.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/04/08 15:16:46 $
 */
class TunnelHelper {

    /**
     * Create an {@link HttpURLConnection} to the tunnel servlet.
     * <p/>
     * If the supplied connection request info contains a proxy user and
     * password, these will be encoded and passed in the "Proxy-Authorization"
     * request property.
     *
     * @param url    the tunnel servlet URL
     * @param id     the connection identifier. May be <code>null</code>.
     * @param action the action to perform
     * @param info   the connection request info.
     */
    public static HttpURLConnection create(URL url, String id, String action,
                                           HTTPRequestInfo info)
            throws IOException {
        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        if (id != null) {
            connection.setRequestProperty("id", id);
        }
        connection.setRequestProperty("action", action);
        connection.setUseCaches(false);
        if (info.getProxyUser() != null && info.getProxyPassword() != null) {
            String pwd = info.getProxyUser() + ":" + info.getProxyPassword();
            String encoded = new String(Base64.encodeBase64(pwd.getBytes()));
            connection.setRequestProperty("Proxy-Authorization",
                                          "Basic " + encoded);
        }
        return connection;
    }

    /**
     * Create an {@link HttpURLConnection} to the tunnel servlet, and connect
     * to it.
     * <p/>
     * If the supplied connection request info contains a proxy user and
     * password, these will be encoded and passed in the "Proxy-Authorization"
     * header.
     *
     * @param url    the tunnel servlet URL
     * @param id     the connection identifier. May be <code>null</code>.
     * @param action the action to perform
     * @param info   the connection request info.
     * @throws IOException if an I/O error occurs
     */
    public static HttpURLConnection connect(URL url, String id, String action,
                                            HTTPRequestInfo info)
            throws IOException {

        HttpURLConnection connection = create(url, id, action, info);
        connection.setRequestProperty("Content-Length", "0");
        connection.connect();
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException(connection.getResponseCode() + " "
                                  + connection.getResponseMessage());
        }
        return connection;
    }
}
