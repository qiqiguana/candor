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
 * $Id: HTTPMultiplexer.java,v 1.4 2005/07/22 23:40:36 tanderson Exp $
 */
package org.exolab.jms.net.http;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.Principal;

import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.connector.SecurityException;
import org.exolab.jms.net.multiplexer.Endpoint;
import org.exolab.jms.net.multiplexer.Multiplexer;
import org.exolab.jms.net.multiplexer.MultiplexerListener;
import org.exolab.jms.net.uri.URI;
import org.exolab.jms.net.uri.URIHelper;


/**
 * Multiplexes data over an HTTP connection.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.4 $ $Date: 2005/07/22 23:40:36 $
 */
class HTTPMultiplexer extends Multiplexer {

    /**
     * The client URI.
     */
    private URI _clientURI;


    /**
     * Construct a new client-side <code>HTTPMultiplexer</code>.
     *
     * @param listener  the multiplexer listener
     * @param endpoint  the endpoint to multiplex messages over
     * @param clientURI the local address that the connection is bound to
     * @param principal the security principal
     * @throws IOException       if an I/O error occurs
     * @throws SecurityException if connection is refused by the server
     */
    public HTTPMultiplexer(MultiplexerListener listener, Endpoint endpoint,
                           URI clientURI, Principal principal)
            throws IOException, SecurityException {
        if (clientURI == null) {
            throw new IllegalArgumentException("Argument 'clientURI' is null");
        }
        _clientURI = clientURI;
        initialise(listener, endpoint, true);
        authenticate(principal);
    }

    /**
     * Construct a new server-side <code>HTTPMultiplexer</code>.
     *
     * @param listener      the multiplexer listener
     * @param endpoint      the endpoint to multiplex messages over
     * @param authenticator the connection authetnicator
     * @throws IOException       if an I/O error occurs
     * @throws ResourceException if the authenticator cannot authenticate
     */
    public HTTPMultiplexer(MultiplexerListener listener, Endpoint endpoint,
                           Authenticator authenticator)
            throws IOException, ResourceException {
        super(listener, endpoint, authenticator);
    }

    /**
     * Returns the client URI.
     *
     * @return the client URI
     */
    public URI getClientURI() {
        return _clientURI;
    }

    /**
     * Perform handshaking on initial connection, to verify protocol, and
     * establish the client URI.
     *
     * @param out the endpoint's output stream
     * @param in  the endpoint's input stream
     * @throws IOException for any I/O error
     */
    protected void handshake(DataOutputStream out, DataInputStream in)
            throws IOException {
        super.handshake(out, in);
        if (isClient()) {
            out.writeUTF(_clientURI.toString());
        } else {
            String uri = in.readUTF();
            _clientURI = URIHelper.parse(uri);
        }
    }

}
