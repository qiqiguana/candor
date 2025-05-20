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
 * $Id: JmsConnectionFactory.java,v 1.2 2005/03/18 03:36:37 tanderson Exp $
 */
package org.exolab.jms.client;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.JMSSecurityException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicConnection;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;


/**
 * Client implementation of the <code>javax.jms.ConnectionFactory</code>
 * interface.
 *
 * @author <a href="mailto:jima@exoffice.com">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/03/18 03:36:37 $
 */
public class JmsConnectionFactory
        implements ConnectionFactory, QueueConnectionFactory,
        TopicConnectionFactory, ExceptionListener, Externalizable,
        Referenceable {

    /**
     * The class name of the server proxy.
     */
    private String _className;

    /**
     * Properties to initialise the server proxy with.
     */
    private Map _properties;

    /**
     * The environment to use when creating the server proxy. May be
     * <code>null</code>
     */
    private Map _environment;

    /**
     * The server proxy.
     */
    private JmsServerStubIfc _proxy;

    /**
     * The set of connections created via this factory
     */
    private List _connections = new ArrayList();

    /**
     * Object version no. for serialization
     */
    private static final long serialVersionUID = 3;


    /**
     * Default constructor required for serialization
     */
    public JmsConnectionFactory() {
    }

    /**
     * Construct a new <code>JmsConnectionFactory</code>
     *
     * @param className   the class name of the server proxy
     * @param properties  properties to initialise the server proxy with
     * @param environment the environment to use when creating the server proxy.
     *                    May be <code>null</code>
     */
    public JmsConnectionFactory(String className, Map properties,
                                Map environment) {
        if (className == null) {
            throw new IllegalArgumentException("Argument 'className' is null");
        }
        if (properties == null) {
            throw new IllegalArgumentException("Argument 'properties' is null");
        }
        _className = className;
        _properties = properties;
        _environment = environment;
    }

    /**
     * Returns the server proxy
     *
     * @return the server proxy
     * @throws JMSException if the proxy cannot be created
     */
    public synchronized JmsServerStubIfc getProxy() throws JMSException {
        if (_proxy == null) {
            try {
                Class[] argTypes = {Map.class, Map.class};
                Object[] args = {_properties, _environment};

                Class factoryClass = Class.forName(_className);
                Constructor constructor =
                        factoryClass.getDeclaredConstructor(argTypes);
                _proxy = (JmsServerStubIfc) constructor.newInstance(args);
                _proxy.setExceptionListener(this);
            } catch (InvocationTargetException exception) {
                if (exception.getTargetException() != null) {
                    throw new JMSException("Failed to create proxy: "
                                           + exception.getTargetException());
                } else {
                    throw new JMSException("Failed to create proxy: "
                                           + exception);
                }
            } catch (Exception exception) {
                throw new JMSException("Failed to create proxy: "
                                       + exception);
            }
        }

        return _proxy;
    }

    /**
     * Notifies user of a JMS exception.
     *
     * @param exception the JMS exception
     */
    public void onException(JMSException exception) {
        // iterate through the list of connection and call
        // notifyExceptionListener
        JmsConnection[] connections = getConnections();
        for (int i = 0; i < connections.length; ++i) {
            JmsConnection connection = connections[i];
            connection.notifyExceptionListener(exception);
        }

        synchronized (this) {
            _connections.clear();
            _proxy = null;
        }
    }

    /**
     * Retrieves the reference of this object.
     *
     * @return the reference of this object
     */
    public Reference getReference() {
        Reference reference = new Reference(getClass().getName(),
                                            new StringRefAddr("serverClass",
                                                              _className),
                                            JmsConnectionFactoryBuilder.class.getName(),
                                            null);

        // all properties are strings so add them to the reference
        Iterator iterator = _properties.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            reference.add(new StringRefAddr(key, value));
        }

        return reference;
    }

    /**
     * Writes the object state to a stream.
     *
     * @param stream the stream to write the state to
     * @throws IOException for any I/O error
     */
    public void writeExternal(ObjectOutput stream) throws IOException {
        stream.writeLong(serialVersionUID);
        stream.writeObject(_className);
        stream.writeObject(_properties);
    }

    /**
     * Reads the object state from a stream.
     *
     * @param stream the stream to read the state from
     * @throws IOException            for any I/O error
     * @throws ClassNotFoundException if the class for an object being restored
     *                                cannot be found
     */
    public void readExternal(ObjectInput stream)
            throws IOException, ClassNotFoundException {
        long version = stream.readLong();
        if (version == serialVersionUID) {
            _className = (String) stream.readObject();
            _properties = (Map) stream.readObject();
        } else {
            throw new IOException(JmsConnectionFactory.class.getName()
                                  + " with version " + version
                                  + " is not supported.");
        }
    }

    /**
     * Creates a connection with the default user identity. The connection is
     * created in stopped mode. No messages will be delivered until the
     * <code>Connection.start</code> method is explicitly called.
     *
     * @return a newly created connection
     * @throws JMSException         if the JMS provider fails to create the
     *                              connection due to some internal error.
     * @throws JMSSecurityException if client authentication fails due to an
     *                              invalid user name or password.
     */
    public Connection createConnection() throws JMSException {
        return createConnection(null, null);
    }

    /**
     * Creates a connection with the specified user identity. The connection is
     * created in stopped mode. No messages will be delivered until the
     * <code>Connection.start</code> method is explicitly called.
     *
     * @param userName the caller's user name
     * @param password the caller's password
     * @return a newly created  connection
     * @throws JMSException         if the JMS provider fails to create the
     *                              connection due to some internal error.
     * @throws JMSSecurityException if client authentication fails due to an
     *                              invalid user name or password.
     */
    public Connection createConnection(String userName, String password)
            throws JMSException {
        JmsConnection connection = new JmsConnection(this, null, userName,
                                                     password);
        addConnection(connection);
        return connection;
    }

    /**
     * Create a queue connection with the default user identity.
     *
     * @return a newly created queue connection
     * @throws JMSException         if the connection can't be created due to
     *                              some internal error
     * @throws JMSSecurityException if client authentication fails due to
     *                              invalid user name or password
     */
    public QueueConnection createQueueConnection() throws JMSException {
        return createQueueConnection(null, null);
    }

    /**
     * Create a queue connection with the specified user identity.
     *
     * @param userName the caller's user name
     * @param password tghe caller's password
     * @return a newly created queue connection
     * @throws JMSException         if the connection can't be created due to
     *                              some internal error
     * @throws JMSSecurityException if client authentication fails due to
     *                              invalid user name or password
     */
    public QueueConnection createQueueConnection(String userName,
                                                 String password)
            throws JMSException {

        JmsQueueConnection connection = new JmsQueueConnection(this, null,
                                                               userName,
                                                               password);
        addConnection(connection);
        return connection;
    }

    /**
     * Create a topic connection with the default user identity.
     *
     * @return a newly created topic connection
     * @throws JMSException if the connection can't be created due to some
     * internal error
     * @throws JMSSecurityException if client authentication fails due to
     * invalid user name or password
     */
    public TopicConnection createTopicConnection() throws JMSException {
        return createTopicConnection(null, null);
    }

    /**
     * Create a topic connection with the specified user identity.
     *
     * @param userName the caller's user name
     * @param password tghe caller's password
     * @return a newly created topic connection
     * @throws JMSException if the connection can't be created due to some
     * internal error
     * @throws JMSSecurityException if client authentication fails due to
     * invalid user name or password
     */
    public TopicConnection createTopicConnection(String userName,
                                                 String password)
        throws JMSException {

        JmsTopicConnection connection = new JmsTopicConnection(
            this, null, userName, password);
        addConnection(connection);
        return connection;
    }

    /**
     * Add a connection.
     *
     * @param connection the connection to add
     */
    protected synchronized void addConnection(JmsConnection connection) {
        _connections.add(connection);
    }

    /**
     * Remove a connection.
     *
     * @param connection the connection to remove
     */
    protected synchronized void removeConnection(JmsConnection connection) {
        _connections.remove(connection);
    }

    /**
     * Returns the set of active connections.
     *
     * @return the set of active connections
     */
    protected synchronized JmsConnection[] getConnections() {
        return (JmsConnection[]) _connections.toArray(new JmsConnection[0]);
    }

}
