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
 * Copyright 2001-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 */
package org.exolab.jms.server.net;

import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.jms.XAConnectionFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.jms.client.JmsConnectionFactory;
import org.exolab.jms.client.JmsServerStubIfc;
import org.exolab.jms.client.JmsXAConnectionFactory;
import org.exolab.jms.config.ConnectionFactories;
import org.exolab.jms.config.ConnectionFactory;
import org.exolab.jms.config.QueueConnectionFactory;
import org.exolab.jms.config.TopicConnectionFactory;
import org.exolab.jms.config.XAQueueConnectionFactory;
import org.exolab.jms.config.XATopicConnectionFactory;
import org.exolab.jms.config.ConnectionFactoryType;
import org.exolab.jms.server.ServerConnector;


/**
 * Helper class for binding connection factories in JNDI.
 *
 * @version     $Revision: 1.2 $ $Date: 2005/04/07 02:42:49 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
public class ConnectionFactoryHelper {

    /**
     * The logger.
     */
    private static final Log _log =
        LogFactory.getLog(ConnectionFactoryHelper.class);


    /**
     * Bind the connection factories to the supplied context.
     *
     * @param context the context to bind factories to
     * @param factories the connection factories to bind
     * @param implementation a class implementing the {@link ServerConnector}
     * interface
     * @param properties parameters to pass to the associated
     * {@link JmsConnectionFactory} implementation
     * @throws NamingException if a factory cannot be bound
     */
    public static void bind(Context context, ConnectionFactories factories,
                            Class implementation, Map properties)
        throws NamingException {
        if (context == null) {
            throw new IllegalArgumentException("Argument 'context' is null");
        }
        if (factories == null) {
            throw new IllegalArgumentException("Argument 'factories' is null");
        }
        if (implementation == null) {
            throw new IllegalArgumentException(
                "Argument 'implementation' is null");
        }
        if (!JmsServerStubIfc.class.isAssignableFrom(implementation)) {
            throw new IllegalArgumentException(
                "Class " + implementation.getName() + " does not implement " +
                JmsServerStubIfc.class.getName());
        }
        if (properties == null) {
            throw new IllegalArgumentException("Argument properties is null");
        }

        ConnectionFactoryType[] type = factories.getConnectionFactory();
        ConnectionFactoryType[] queue = factories.getQueueConnectionFactory();
        ConnectionFactoryType[] topic = factories.getTopicConnectionFactory();
        ConnectionFactoryType[] xatype = factories.getXAConnectionFactory();
        ConnectionFactoryType[] xaqueue =
                factories.getXAQueueConnectionFactory();
        ConnectionFactoryType[] xatopic =
                factories.getXATopicConnectionFactory();
        for (int i = 0; i < type.length; ++i) {
            bind(context, type[i], implementation, properties);
        }
        for (int i = 0; i < queue.length; ++i) {
            bind(context, queue[i], implementation, properties);
        }
        for (int i = 0; i < topic.length; ++i) {
            bind(context, topic[i], implementation, properties);
        }
        for (int i = 0; i < xatype.length; ++i) {
            bind(context, xatype[i], implementation, properties);
        }
        for (int i = 0; i < xaqueue.length; ++i) {
            bind(context, xaqueue[i], implementation, properties);
        }
        for (int i = 0; i < xatopic.length; ++i) {
            bind(context, xatopic[i], implementation, properties);
        }
    }

    /**
     * Bind a connection factory to the supplied context.
     *
     * @param context the context to bind factories to
     * @param factory the connection factory to bind
     * @param implementation a class implementing the {@link ServerConnector}
     * interface
     * @param properties parameters to pass to the associated
     * {@link JmsConnectionFactory} implementation
     * @throws NamingException if the factory cannot be bound
     */
    private static void bind(Context context, ConnectionFactoryType factory,
                             Class implementation, Map properties)
        throws NamingException {
        JmsConnectionFactory instance = null;
        if (factory instanceof ConnectionFactory
        || factory instanceof QueueConnectionFactory
        || factory instanceof TopicConnectionFactory) {
            instance = new JmsConnectionFactory(implementation.getName(),
                                                     properties, null);
        } else if (factory instanceof XAConnectionFactory
            || factory instanceof XAQueueConnectionFactory
            || factory instanceof XATopicConnectionFactory) {
            instance = new JmsXAConnectionFactory(
                implementation.getName(), properties);
        } else {
            throw new IllegalArgumentException(
                "Unknown connection factory type: " +
                factory.getClass().getName());
        }

        context.rebind(factory.getName(), instance);
        _log.debug("Bound connection factory " + factory.getName());
    }

}
