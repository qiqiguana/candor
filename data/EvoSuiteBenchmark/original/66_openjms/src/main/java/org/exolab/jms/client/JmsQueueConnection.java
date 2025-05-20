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
 * $Id: JmsQueueConnection.java,v 1.2 2005/03/18 03:36:37 tanderson Exp $
 */
package org.exolab.jms.client;


import javax.jms.ConnectionConsumer;
import javax.jms.InvalidSelectorException;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.ServerSessionPool;
import javax.jms.Topic;


/**
 * Client implementation of the <code>javax.jms.QueueConnection</code>
 * interface.
 *
 * @author <a href="mailto:jima@exoffice.com">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/03/18 03:36:37 $
 * @see javax.jms.QueueConnection
 */
class JmsQueueConnection extends JmsConnection implements QueueConnection {

    /**
     * Construct a new <code>JmsQueueConnection</code>.
     * <p/>
     * This attempts to establish a connection to the JMS server
     *
     * @param factory  the connection factory responsible for creating this
     * @param clientID the pre-configured client identifier. May be
     *                 <code>null</code>
     * @param username the client username
     * @param password the client password
     * @throws JMSException if a connection cannot be established
     */
    public JmsQueueConnection(JmsConnectionFactory factory, String clientID,
                              String username, String password)
            throws JMSException {
        super(factory, clientID, username, password);
    }

    /**
     * Create a new queue session.
     *
     * @param transacted if <code>true</code>, the session is transacted.
     * @param ackMode    indicates whether the consumer or the client will
     *                   acknowledge any messages it receives. This parameter
     *                   will be ignored if the session is transacted. Legal
     *                   values are <code>Session.AUTO_ACKNOWLEDGE</code>,
     *                   <code>Session.CLIENT_ACKNOWLEDGE</code> and
     *                   <code>Session.DUPS_OK_ACKNOWLEDGE</code>.
     * @return the new queue session
     * @throws JMSException if the session cannot be created
     */
    public QueueSession createQueueSession(boolean transacted, int ackMode)
            throws JMSException {

        ensureOpen();
        setModified();

        JmsQueueSession session = new JmsQueueSession(this, transacted,
                                                      ackMode);

        // if the connection is started then also start the session
        if (!isStopped()) {
            session.start();
        }

        // add it to the list of managed sessions for this connection
        addSession(session);

        return session;
    }

    /**
     * Create a connection consumer for this connection
     *
     * @param queue       the queue to access
     * @param selector    the message selector. May be <code>null</code>
     * @param pool        the server session pool to associate with the
     *                    consumer
     * @param maxMessages the maximum number of messages that can be assigned to
     *                    a server session at one time
     * @return the new connection consumer
     * @throws InvalidSelectorException if the message selector is invalid
     * @throws JMSException             if the connection consumer cannot be
     *                                  created
     */
    public ConnectionConsumer createConnectionConsumer(Queue queue,
                                                       String selector,
                                                       ServerSessionPool pool,
                                                       int maxMessages)
            throws JMSException {
        return super.createConnectionConsumer(queue, selector, pool,
                                              maxMessages);
    }

    /**
     * This implementation always throws <code>IllegalStateException</code>,
     * as per section 4.11 of the JMS specification.
     *
     * @throws IllegalStateException if invoked
     */
    public ConnectionConsumer createDurableConnectionConsumer(
            Topic topic, String subscriptionName, String messageSelector,
            ServerSessionPool sessionPool, int maxMessages)
            throws JMSException {
        throw new IllegalStateException("Invalid operation for QueueConnection");
    }

}
