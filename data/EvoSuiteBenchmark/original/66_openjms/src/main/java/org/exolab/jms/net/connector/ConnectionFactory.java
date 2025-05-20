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
 * Copyright 2003-2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: ConnectionFactory.java,v 1.3 2005/05/03 13:45:58 tanderson Exp $
 */
package org.exolab.jms.net.connector;

import java.security.Principal;
import java.util.Map;

import org.exolab.jms.net.uri.URI;


/**
 * A factory for establishing connections, and accepting them.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/05/03 13:45:58 $
 */
public interface ConnectionFactory {

    /**
     * Determines if this factory supports connections to the specified URI.
     *
     * @param uri the connection address
     * @return <code>true</code> if this factory supports the URI;
     *         <code>false</code> otherwise
     */
    boolean canConnect(URI uri);

    /**
     * Returns a connection to the specified URI, using the default connection
     * properties.
     *
     * @param principal the security principal. May be <code>null</code>
     * @param uri       the connection address
     * @return a connection to <code>uri</code>
     * @throws ResourceException if a connection cannot be established
     */
    Connection getConnection(Principal principal, URI uri)
            throws ResourceException;

    /**
     * Returns a connection to the specified URI, using the specified connection
     * properties.
     *
     * @param principal  the security principal. May be <code>null</code>
     * @param uri        the connection address
     * @param properties connection properties. If <code>null</code>, use the
     *                   default connection properties
     * @return a connection to <code>uri</code>
     * @throws ResourceException if a connection cannot be established
     */
    Connection getConnection(Principal principal, URI uri,
                             Map properties)
            throws ResourceException;

    /**
     * Determines if this factory supports listening for new connections on the
     * specified URI.
     *
     * @param uri the connection address
     * @return <code>true</code> if this factory supports the URI;
     *         <code>false</code> otherwise
     */
    boolean canAccept(URI uri);

    /**
     * Listen for new connections on the specified URI, using the default
     * connection acceptor properties.
     *
     * @param uri the connection address
     * @throws ResourceException if connections can't be accepted on the
     *                           specified URI
     */
    void accept(URI uri) throws ResourceException;

    /**
     * Listen for new connections on the specified URI, using the specified
     * acceptor properties
     *
     * @param uri        the connection address
     * @param properties acceptor properties. May be <code>null</code>
     * @throws ResourceException if connections can't be accepted on the
     *                           specified URI
     */
    void accept(URI uri, Map properties) throws ResourceException;

}
