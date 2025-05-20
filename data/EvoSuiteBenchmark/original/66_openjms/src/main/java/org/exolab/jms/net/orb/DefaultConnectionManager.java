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
 * Copyright 2003-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: DefaultConnectionManager.java,v 1.3 2005/06/04 14:52:05 tanderson Exp $
 */
package org.exolab.jms.net.orb;

import java.util.Map;

import org.exolab.jms.net.connector.AbstractConnectionManager;
import org.exolab.jms.net.connector.Authenticator;
import org.exolab.jms.net.connector.ConnectionManager;
import org.exolab.jms.net.connector.InvocationHandler;
import org.exolab.jms.net.connector.ResourceException;
import org.exolab.jms.net.http.HTTPManagedConnectionFactory;
import org.exolab.jms.net.http.HTTPSManagedConnectionFactory;
import org.exolab.jms.net.rmi.RMIManagedConnectionFactory;
import org.exolab.jms.net.tcp.TCPManagedConnectionFactory;
import org.exolab.jms.net.tcp.TCPSManagedConnectionFactory;
import org.exolab.jms.net.vm.VMManagedConnectionFactory;


/**
 * Default implementation of the {@link ConnectionManager} interface.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.3 $ $Date: 2005/06/04 14:52:05 $
 */
public class DefaultConnectionManager extends AbstractConnectionManager {


    /**
     * Construct a new <code>DefaultConnectionManager</code>.
     *
     * @param handler       the invocation handler
     * @param authenticator the connection authenticator
     * @param properties     configuration properties. May be <code>null</code>
     * @throws ResourceException for any error
     */
    public DefaultConnectionManager(InvocationHandler handler,
                                    Authenticator authenticator,
                                    Map properties)
            throws ResourceException {
        super(handler, authenticator, properties);
        addManagedConnectionFactory(new TCPManagedConnectionFactory());
        addManagedConnectionFactory(new TCPSManagedConnectionFactory());
        addManagedConnectionFactory(new VMManagedConnectionFactory());
        addManagedConnectionFactory(new RMIManagedConnectionFactory());
        addManagedConnectionFactory(new HTTPManagedConnectionFactory());
        addManagedConnectionFactory(new HTTPSManagedConnectionFactory());
    }

}
