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
 * Copyright 2000-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 */
package org.exolab.jms.server;

import javax.jms.JMSException;
import javax.jms.JMSSecurityException;

import org.exolab.jms.authentication.AuthenticationMgr;
import org.exolab.jms.config.Configuration;
import org.exolab.jms.messagemgr.ConsumerManager;
import org.exolab.jms.messagemgr.DestinationManager;
import org.exolab.jms.service.Services;
import org.exolab.jms.persistence.DatabaseService;


/**
 * <code>AdminConnectionFactory</code> is responsible for creating {@link
 * AdminConnection} instances.
 *
 * @author <a href="mailto:knut@lerpold.no">Knut Lerpold</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/08/30 14:24:26 $
 */
public class AdminConnectionFactory {

    /**
     * The configuration.
     */
    private final Configuration _config;

    /**
     * The authentication manager.
     */
    private final AuthenticationMgr _authenticator;

    /**
     * The destination manager.
     */
    private final DestinationManager _destinations;

    /**
     * The consumer manager.
     */
    private final ConsumerManager _consumers;

    /**
     * The database service.
     */
    private final DatabaseService _database;

    /**
     * The services to stop, when shutting down the server.
     */
    private final Services _services;


    /**
     * Construct a new <code>AdminConnectionManager</code>.
     */
    public AdminConnectionFactory(Configuration config,
                                  AuthenticationMgr authMgr,
                                  DestinationManager destMgr,
                                  ConsumerManager consumers,
                                  DatabaseService database,
                                  Services services) {
        _config = config;
        _authenticator = authMgr;
        _destinations = destMgr;
        _consumers = consumers;
        _database = database;
        _services = services;
    }

    /**
     * Create a new admin connection.
     *
     * @return a new admin connection
     * @throws JMSException if the connection cannot be created
     */
    public AdminConnection create()
            throws JMSSecurityException, JMSException {

        return new AdminConnection(_config, _authenticator, _destinations, _consumers,
                                   _database, _services);
    }

}

