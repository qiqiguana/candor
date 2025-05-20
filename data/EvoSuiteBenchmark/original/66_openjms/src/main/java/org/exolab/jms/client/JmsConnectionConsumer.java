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
 *
 * $Id: JmsConnectionConsumer.java,v 1.2 2005/03/18 03:36:37 tanderson Exp $
 */
package org.exolab.jms.client;

import javax.jms.Connection;
import javax.jms.ConnectionConsumer;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ServerSession;
import javax.jms.ServerSessionPool;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Implementation of the <code>javax.jms.ConnectionConsumer</code> interface.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/03/18 03:36:37 $
 */
class JmsConnectionConsumer
        implements ConnectionConsumer, MessageListener {

    /**
     * The session to receive messages via.
     */
    private Session _session;

    /**
     * The consumer of messages.
     */
    private MessageConsumer _consumer;

    /**
     * The server session pool.
     */
    private ServerSessionPool _pool;

    /**
     * The logger
     */
    private static final Log _log =
            LogFactory.getLog(JmsConnectionConsumer.class);


    /**
     * Construct a new <code>JmsConnectionConsumer</code>.
     *
     * @param connection       the connection which created this
     * @param destination      the destination to access
     * @param pool             the server session pool
     * @param selector         the message selector. May be <code>null</code>
     * @param maxMessages      the maximum number of messages that can be
     *                         assigned to a server session at one time
     * @throws JMSException if the consumer cannot be constructed
     */
    public JmsConnectionConsumer(Connection connection, Destination destination,
                                 ServerSessionPool pool, String selector,
                                 int maxMessages)
            throws JMSException {
        this(connection, destination, null, pool, selector, maxMessages);
    }

    /**
     * Construct a new <code>JmsConnectionConsumer</code>.
     *
     * @param connection       the connection which created this
     * @param destination      the destination to access
     * @param subscriptionName the durable subscription name. May be
     *                         <code>null</code>
     * @param pool             the server session pool
     * @param selector         the message selector. May be <code>null</code>
     * @param maxMessages      the maximum number of messages that can be
     *                         assigned to a server session at one time
     * @throws JMSException if the consumer cannot be constructed
     */
    public JmsConnectionConsumer(Connection connection, Destination destination,
                                 String subscriptionName,
                                 ServerSessionPool pool, String selector,
                                 int maxMessages)
            throws JMSException {
        if (connection == null) {
            throw new IllegalArgumentException("Argument 'connection' is null");
        }
        if (destination == null) {
            throw new IllegalArgumentException(
                    "Argument 'destination' is null");
        }
        if (pool == null) {
            throw new IllegalArgumentException("Argument 'pool' is null");
        }
        if (maxMessages <= 0) {
            throw new IllegalArgumentException(
                    "Argument 'maxMessages' must be > 0");
        }

        _pool = pool;

        _session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        if (subscriptionName == null) {
            _consumer = _session.createConsumer(destination, selector, false);
        } else {
            _consumer = _session.createDurableSubscriber((Topic) destination,
                                                         subscriptionName,
                                                         selector, false);
        }

        _consumer.setMessageListener(this);
    }

    /**
     * Returns the server session pool associated with this connection consumer.
     *
     * @return the server session pool used by this connection consumer
     */
    public ServerSessionPool getServerSessionPool() {
        return _pool;
    }

    /**
     * Close the connection consumer, freeing any allocated resources.
     *
     * @throws JMSException if the consumer cannot be closed
     */
    public void close() throws JMSException {
        try {
            _consumer.close();
            _session.close();
        } finally {
            _pool = null;
            _consumer = null;
            _session = null;
        }
    }

    /**
     * Impmentation of MessageListener.onMessage, to receive messages
     * from the server. In this most simple case, it loads each message into a
     * server session and calls the start method.
     *
     * @param message the message
     */
    public void onMessage(Message message) {
        try {
            // not very sophisticated at this point. Simply get a session
            // from the pool, put the message in it, and start it.
            ServerSession serverSession = _pool.getServerSession();
            JmsSession session = (JmsSession) serverSession.getSession();
            message.acknowledge();
            session.addMessage(message);
            serverSession.start();
        } catch (Exception exception) {
            _log.error(exception, exception);
        }
    }

}
