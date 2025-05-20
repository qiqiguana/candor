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
 * $Id: HTTPEndpoint.java,v 1.4 2005/04/17 13:41:49 tanderson Exp $
 */
package org.exolab.jms.net.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.ConnectException;

import org.exolab.jms.net.multiplexer.Endpoint;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.util.SSLHelper;


/**
 * HTTP implementation of the {@link Endpoint} interface.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2005/04/17 13:41:49 $
 */
class HTTPEndpoint implements Endpoint {

    /**
     * The connection to the servlet.
     */
    private final URL _url;

    /**
     * The connection identifier.
     */
    private final String _id;

    /**
     * The http URI.
     */
    private final URI _uri;

    /**
     * The input stream.
     */
    private HTTPInputStream _in;

    /**
     * The output stream.
     */
    private HTTPOutputStream _out;

    /**
     * The request info.
     */
    private HTTPRequestInfo _info;

    /**
     * The response prefix to a successfull open request.
     */
    private static final String OPEN_RESPONSE = "OPEN ";

    /**
     * System property to indicate the proxy server that the http protocol
     * handler will use.
     */
    private static final String HTTP_PROXY_HOST = "http.proxyHost";

    /**
     * System property to indicate the proxy port that the http protocol
     * handler will use.
     */
    private static final String HTTP_PROXY_PORT = "http.proxyPort";

    /**
     * System property to indicate the proxy server that the https protocol
     * handler will use.
     */
    private static final String HTTPS_PROXY_HOST = "https.proxyHost";

    /**
     * System property to indicate the proxy port that the https protocol
     * handler will use.
     */
    private static final String HTTPS_PROXY_PORT = "https.proxyPort";


    /**
     * Construct a new <code>HTTPEndpoint</code>.
     *
     * @param info the connection request info
     * @param info the http request information
     * @throws IOException for any I/O error
     */
    public HTTPEndpoint(HTTPRequestInfo info) throws IOException {
        final int bufferSize = 2048;

        _uri = info.getURI();
        _url = new URL(_uri.toString());
        _info = info;

        boolean isSSL = _uri.getScheme().equals("https");

        if (isSSL && info.getSSLProperties() != null) {
            SSLHelper.configure(info.getSSLProperties());
        }

        if (_info.getProxyHost() != null) {
            // set proxy system properties
            System.setProperty("proxySet", "true");

            String hostProp = (isSSL) ? HTTPS_PROXY_HOST : HTTP_PROXY_HOST;
            String portProp = (isSSL) ? HTTPS_PROXY_PORT : HTTP_PROXY_PORT;

            try {
                System.setProperty(hostProp, _info.getProxyHost());
                if (_info.getProxyPort() != 0) {
                    System.setProperty(portProp, "" + _info.getProxyPort());
                }
            } catch (SecurityException exception) {
                throw new ConnectException(
                        "Failed to set proxy system properties: "
                        + exception.getMessage());
            }
        }

        HttpURLConnection connection = getConnection("open");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line = reader.readLine();
            if (line == null || !line.startsWith(OPEN_RESPONSE)) {
                throw new IOException("Invalid response returned from URL="
                                      + _url + ": " + line);

            }
            _id = line.substring(line.indexOf(OPEN_RESPONSE)
                                 + OPEN_RESPONSE.length());

        } finally {
            reader.close();
        }
        _in = new HTTPInputStream(_id, _url, info);
        _out = new HTTPOutputStream(_id, _url, bufferSize, info);
    }

    /**
     * Returns the URI that the endpoint is connected to.
     *
     * @return the URI that the endpoint is connected to
     */
    public URI getURI() {
        return _uri;
    }

    /**
     * Returns an input stream that reads from this endpoint.
     *
     * @return an input stream that reads from this endpoint
     * @throws IOException if an I/O error occurs while creating the input
     *                     stream.
     */
    public InputStream getInputStream() throws IOException {
        return _in;
    }

    /**
     * Returns an output stream that writes to this endpoint.
     *
     * @return an output stream that writes to this endpoint
     * @throws IOException if an I/O error occurs while creating the output
     *                     stream.
     */
    public OutputStream getOutputStream() throws IOException {
        return _out;
    }

    /**
     * Closes the endpoint.
     *
     * @throws IOException if an I/O error occurs while closing the endpoint
     */
    public void close() throws IOException {
        try {
            getConnection("close");
        } finally {
            _in.close();
            _out.close();
        }
    }

    /**
     * Opens a connection to the tunnel servlet.
     *
     * @param action the action to invoke
     * @return a connection to the tunnel servlet
     * @throws IOException for any I/O error
     */
    private HttpURLConnection getConnection(String action) throws IOException {
        return TunnelHelper.connect(_url, _id, action, _info);
    }

}
