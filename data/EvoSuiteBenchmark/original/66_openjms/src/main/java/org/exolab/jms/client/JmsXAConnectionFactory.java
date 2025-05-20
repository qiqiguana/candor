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
 *
 * $Id: JmsXAConnectionFactory.java,v 1.1 2005/03/18 03:36:37 tanderson Exp $
 */
package org.exolab.jms.client;

import java.util.Map;
import javax.jms.JMSException;
import javax.jms.JMSSecurityException;
import javax.jms.XAConnection;
import javax.jms.XAConnectionFactory;
import javax.jms.XAQueueConnection;
import javax.jms.XAQueueConnectionFactory;
import javax.jms.XATopicConnection;
import javax.jms.XATopicConnectionFactory;

import org.exolab.jms.common.uuid.UUID;


/**
 * Client implementation of the <code>javax.jms.XAConnectionFactory</code>
 * interface.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/03/18 03:36:37 $
 */
public class JmsXAConnectionFactory
        extends JmsConnectionFactory
        implements XAConnectionFactory, XAQueueConnectionFactory,
        XATopicConnectionFactory {

    /**
     * Default constructor required for serialization.
     */
    public JmsXAConnectionFactory() {
    }

    /**
     * Construct a new <code>JmsXAConnectionFactory</code>.
     *
     * @param className  the server proxy class name
     * @param properties properties to initialise the server proxy with proxy.
     *                   May be <code>null</code>
     */
    public JmsXAConnectionFactory(String className, Map properties) {
        this(className, properties, null);
    }

    /**
     * Construct a new <code>JmsXAConnectionFactory</code>.
     *
     * @param className   the server proxy class name
     * @param properties  properties to initialise the server proxy with
     * @param environment the environment used in creating the server proxy. May
     *                    be <code>null</code>
     */
    public JmsXAConnectionFactory(String className, Map properties,
                                  Map environment) {
        super(className, properties, environment);
    }

    /**
     * Creates an XA connection with the default user identity. The
     * connection is created in stopped mode. No messages will be delivered
     * until the <code>Connection.start</code> method is explicitly called.
     *
     * @return a newly created <code>XAConnection</code>
     * @throws JMSException         if the JMS provider fails to create an XA
     *                              connection due to some internal error.
     * @throws JMSSecurityException if client authentication fails due to an
     *                              invalid user name or password.
     */
    public XAConnection createXAConnection() throws JMSException {
        return createXAConnection(null, null);
    }

    /**
     * Creates an XA connection with the specified user identity.
     * The connection is created in stopped mode. No messages will be delivered
     * until the <code>Connection.start</code> method is explicitly called.
     *
     * @param userName the caller's user name
     * @param password the caller's password
     * @return a newly created XA connection
     * @throws JMSException         if the JMS provider fails to create an XA
     *                              connection due to some internal error.
     * @throws JMSSecurityException if client authentication fails due to an
     *                              invalid user name or password.
     */
    public XAConnection createXAConnection(String userName, String password)
            throws JMSException {
        String id = UUID.next();
        JmsXAConnection connection = new JmsXAConnection(this, id, userName,
                                                         password);
        addConnection(connection);
        return connection;
    }

    /**
     * Creates an XA queue connection with the default user identity. The
     * connection is created in stopped mode. No messages will be delivered
     * until the <code>Connection.start</code> method is explicitly called.
     *
     * @return a newly created XA queue connection
     * @throws JMSException         if the JMS provider fails to create an XA
     *                              queue connection due to some internal
     *                              error.
     * @throws JMSSecurityException if client authentication fails due to an
     *                              invalid user name or password.
     */
    public XAQueueConnection createXAQueueConnection() throws JMSException {
        return createXAQueueConnection(null, null);
    }

    /**
     * Creates an XA queue connection with the specified user identity. The
     * connection is created in stopped mode. No messages will be delivered
     * until the <code>Connection.start</code> method is explicitly called.
     *
     * @param userName the caller's user name
     * @param password the caller's password
     * @return a newly created XA queue connection
     * @throws JMSException         if the JMS provider fails to create an XA
     *                              queue connection due to some internal
     *                              error.
     * @throws JMSSecurityException if client authentication fails due to an
     *                              invalid user name or password.
     */

    public XAQueueConnection createXAQueueConnection(String userName,
                                                     String password)
            throws JMSException {
        String id = UUID.next();
        JmsXAQueueConnection connection = new JmsXAQueueConnection(this, id,
                                                                   userName,
                                                                   password);
        addConnection(connection);
        return connection;
    }

    /**
     * Creates an XA topic connection with the default user identity. The
     * connection is created in stopped mode. No messages will be delivered
     * until the <code>Connection.start</code> method is explicitly called.
     *
     * @return a newly created XA topic connection
     * @throws JMSException         if the JMS provider fails to create an XA
     *                              topic connection due to some internal
     *                              error.
     * @throws JMSSecurityException if client authentication fails due to an
     *                              invalid user name or password.
     */

    public XATopicConnection createXATopicConnection() throws JMSException {
        return createXATopicConnection(null, null);
    }

    /**
     * Creates an XA topic connection with the specified user identity. The
     * connection is created in stopped mode. No messages will be delivered
     * until the <code>Connection.start</code> method is explicitly called.
     *
     * @param userName the caller's user name
     * @param password the caller's password
     * @return a newly created XA topic connection
     * @throws JMSException         if the JMS provider fails to create an XA
     *                              topic connection due to some internal
     *                              error.
     * @throws JMSSecurityException if client authentication fails due to an
     *                              invalid user name or password.
     */

    public XATopicConnection createXATopicConnection(String userName,
                                                     String password)
            throws JMSException {
        String id = UUID.next();
        JmsXATopicConnection connection = new JmsXATopicConnection(this, id,
                                                                   userName,
                                                                   password);
        addConnection(connection);
        return connection;
    }

}
