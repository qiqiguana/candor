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
 * $Id: DestinationCacheFactory.java,v 1.2 2005/11/12 12:27:40 tanderson Exp $
 */
package org.exolab.jms.messagemgr;

import javax.jms.JMSException;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsQueue;
import org.exolab.jms.client.JmsTopic;
import org.exolab.jms.lease.LeaseManager;
import org.exolab.jms.server.ServerConnectionManager;
import org.exolab.jms.persistence.DatabaseService;


/**
 * Factory for {@link DestinationCache} instances.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/11/12 12:27:40 $
 */
public class DestinationCacheFactory {

    /**
     * The lease manager.
     */
    private final LeaseManager _leases;

    /**
     * The server connection manager.
     */
    private final ServerConnectionManager _connections;

    /**
     * The database service.
     */
    private final DatabaseService _database;

    /**
     * The logger.
     */
    private static final Log _log
            = LogFactory.getLog(DestinationCacheFactory.class);


    /**
     * Construct a new <code>DestinationCacheFactory</code>.
     *
     * @param leases      the lease mananger
     * @param connections the connection manager
     */
    public DestinationCacheFactory(LeaseManager leases,
                                   DatabaseService database,
                                   ServerConnectionManager connections) {

        if (leases == null) {
            throw new IllegalArgumentException("Argument 'leases' is null");
        }
        if (database == null) {
            throw new IllegalArgumentException("Argument 'database' is null");
        }
        if (connections == null) {
            throw new IllegalArgumentException(
                    "Argument 'connections' is null");
        }
        _leases = leases;
        _database = database;
        _connections = connections;
    }

    /**
     * Create a new destination cache.
     *
     * @param destination the destination to create the cache for
     * @return a new cache
     * @throws JMSException if the cache can't be created
     */
    public DestinationCache createDestinationCache(
            JmsDestination destination) throws JMSException {
        DestinationCache result;
        if (destination instanceof JmsTopic) {
            result = new TopicDestinationCache((JmsTopic) destination,
                                               _database, _leases);
        } else {
            result = new QueueDestinationCache((JmsQueue) destination,
                                                _database, _leases,
                                               _connections);
        }
        return result;
    }

}
