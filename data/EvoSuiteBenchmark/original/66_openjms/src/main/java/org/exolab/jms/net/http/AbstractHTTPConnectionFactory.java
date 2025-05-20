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
 * $Id: AbstractHTTPConnectionFactory.java,v 1.5 2005/05/03 13:45:58 tanderson Exp $
 */
package org.exolab.jms.net.http;

import java.util.Map;

import org.exolab.jms.net.connector.AbstractConnectionFactory;
import org.exolab.jms.net.connector.ConnectionManager;
import org.exolab.jms.net.connector.ConnectionRequestInfo;
import org.exolab.jms.net.connector.ManagedConnectionFactory;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.socket.SocketRequestInfo;
import org.exolab.jms.net.uri.URI;


/**
 * A factory for establishing and accepting HTTP/HTTPS connections.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.5 $ $Date: 2005/05/03 13:45:58 $
 */
public class AbstractHTTPConnectionFactory extends AbstractConnectionFactory {

    /**
     * Construct a new <code>AbstractHTTPConnectionFactory</code>.
     *
     * @param connectScheme the connect scheme that this factory supports
     * @param acceptScheme  the accept scheme that this factory supports
     * @param factory       the managed connection factory
     * @param manager       the connection manager
     */
    public AbstractHTTPConnectionFactory(String connectScheme,
                                         String acceptScheme,
                                         ManagedConnectionFactory factory,
                                         ConnectionManager manager) {
        super(connectScheme, acceptScheme, factory, manager);
    }

    /**
     * Returns connection request info for the specified URI and connection
     * properties.
     *
     * @param uri        the connection address
     * @param properties the connection properties. If <code>null</code>, use
     *                   the default connection properties
     * @return connection request info corresponding to <code>uri</code> and
     *         <code>properties</code>
     * @throws ResourceException for any error
     */
    protected ConnectionRequestInfo getConnectionRequestInfo(URI uri,
                                                             Map properties)
            throws ResourceException {
        HTTPRequestInfo info;
        if (properties != null) {
            info = new HTTPRequestInfo(uri, getProperties(properties));
        } else {
            info = new HTTPRequestInfo(uri);
        }
        return info;
    }

    /**
     * Returns connection request info for the specified URI and connection
     * acceptor properties.
     *
     * @param uri        the connection address
     * @param properties acceptor properties. May be <code>null</code>
     * @return connection request info corresponding to <code>uri</code> and
     *         <code>properties</code>
     * @throws ResourceException for any error
     */
    protected ConnectionRequestInfo getAcceptorRequestInfo(URI uri,
                                                           Map properties)
            throws ResourceException {
        SocketRequestInfo info;
        if (properties != null) {
            info = new SocketRequestInfo(uri, getProperties(properties));
        } else {
            info = new SocketRequestInfo(uri);
        }
        return info;
    }
}
