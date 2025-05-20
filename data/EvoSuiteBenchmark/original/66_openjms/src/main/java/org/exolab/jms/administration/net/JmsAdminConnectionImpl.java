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
 * $Id: JmsAdminConnectionImpl.java,v 1.4 2006/05/24 07:59:19 tanderson Exp $
 */
package org.exolab.jms.administration.net;

import org.exolab.jms.administration.AdminConnection;
import org.exolab.jms.administration.JmsAdminServerIfc;
import org.exolab.jms.client.net.SharedORB;
import org.exolab.jms.net.orb.ORB;
import org.exolab.jms.net.proxy.Proxy;
import org.exolab.jms.net.registry.Registry;
import org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc;
import org.exolab.jms.server.net.RemoteJmsAdminServerIfc;

import javax.jms.JMSException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


/**
 * This class is repsonsible for an admin connection to the server
 *
 * @author <a href="mailto:mourikis@intalio.com">Jim Mourikis</a>
 * @version $Revision: 1.4 $ $Date: 2006/05/24 07:59:19 $
 * @see org.exolab.jms.administration.AdminConnectionFactory
 */
public class JmsAdminConnectionImpl
        implements JmsAdminServerIfc, AdminConnection {

    /**
     * The admin connection.
     */
    private RemoteJmsAdminConnectionIfc _connection;


    /**
     * Construct a new <code>JmsAdminConnectionImpl</code>
     *
     * @param url      the server URI
     * @param username the client's username
     * @param password the client's password
     */
    public JmsAdminConnectionImpl(String url, String username, String password)
            throws JMSException {
        Map properties = new HashMap();
        properties.put(ORB.PROVIDER_URI, url);
        if (username != null) {
            properties.put(ORB.SECURITY_PRINCIPAL, username);
        }
        if (password != null) {
            properties.put(ORB.SECURITY_CREDENTIALS, password);
        }

        Registry registry;
        try {
            ORB orb = SharedORB.getInstance();
            registry = orb.getRegistry(properties);
        } catch (RemoteException exception) {
            JMSException error = new JMSException(
                    "Failed to get registry service for URL: " + url);
            error.setLinkedException(exception);
            throw error;
        }

        RemoteJmsAdminServerIfc admin = null;
        try {
            admin = (RemoteJmsAdminServerIfc) registry.lookup("admin");
            _connection = admin.createConnection(username, password);
        } catch (NotBoundException exception) {
            throw new JMSException("Administration server is not bound in the registry for "
                    + "URL: " + url);
        } catch (RemoteException exception) {
            JMSException error = new JMSException("Failed to lookup OpenJMS administration server at URL: "
                    + url);
            error.setLinkedException(exception);
            throw error;
        } finally {
            if (admin instanceof Proxy) {
                ((Proxy) admin).disposeProxy();
            }
            if (registry instanceof Proxy) {
                ((Proxy) registry).disposeProxy();
            }
        }
    }

    // implementation of JmsAdminServerIfc.addDurableConsumer
    public boolean addDurableConsumer(String topic, String name)
            throws JMSException {
        boolean result = false;
        try {
            result = _connection.addDurableConsumer(topic, name);
        } catch (Exception exception) {
            raise(exception);
        }
        return result;
    }

    // implementation of JmsAdminServerIfc.removeDurableConsumer
    public boolean removeDurableConsumer(String name) throws JMSException {
        boolean result = false;
        try {
            result = _connection.removeDurableConsumer(name);
        } catch (Exception exception) {
            raise(exception);
        }
        return result;
    }

    // implementation of JmsAdminServerIfc.durableConsumerExists
    public boolean durableConsumerExists(String name) throws JMSException {
        boolean result = false;
        try {
            result = _connection.durableConsumerExists(name);
        } catch (Exception exception) {
            raise(exception);
        }
        return result;
    }

    // implementation of JmsAdminServerIfc.getDurableConsumers
    public Vector getDurableConsumers(String topic) throws JMSException {
        Vector result = null;
        try {
            result = _connection.getDurableConsumers(topic);
        } catch (Exception exception) {
            raise(exception);
        }
        return result;
    }

    // implementation of JmsAdminServerIfc.unregisterConsumer
    public boolean unregisterConsumer(String name) throws JMSException {
        boolean result = false;
        try {
            result = _connection.unregisterConsumer(name);
        } catch (Exception exception) {
            raise(exception);
        }
        return result;
    }

    // implementation of JmsAdminServerIfc.isConnected
    public boolean isConnected(String name) throws JMSException {
        boolean result = false;
        try {
            result = _connection.isConnected(name);
        } catch (Exception exception) {
            raise(exception);
        }
        return result;
    }

    // implementation of JmsAdminServerIfc.addDestination
    public boolean addDestination(String destination, Boolean queue)
            throws JMSException {
        boolean result = false;
        try {
            result = _connection.addDestination(destination, queue);
        } catch (Exception exception) {
            raise(exception);
        }
        return result;
    }

    // implementation of JmsAdminServerIfc.removeDestination
    public boolean removeDestination(String name) throws JMSException {
        boolean result = false;
        try {
            result = _connection.removeDestination(name);
        } catch (Exception exception) {
            raise(exception);
        }
        return result;
    }

    // implementation of JmsAdminServerIfc.destinationExists
    public boolean destinationExists(String name) throws JMSException {
        boolean result = false;
        try {
            result = _connection.destinationExists(name);
        } catch (Exception exception) {
            raise(exception);
        }
        return result;
    }

    // implementation of JmsAdminServerIfc.getAllDestinations
    public Vector getAllDestinations() throws JMSException {
        Vector result = null;
        try {
            result = _connection.getAllDestinations();
        } catch (Exception exception) {
            raise(exception);
        }
        return result;
    }

    // implementation of JmsAdminServerIfc.getDurableConsumerMessageCount
    public int getDurableConsumerMessageCount(String topic, String name)
            throws JMSException {
        int result = 0;
        try {
            result = _connection.getDurableConsumerMessageCount(topic, name);
        } catch (Exception exception) {
            raise(exception);
        }
        return result;
    }

    // implementation of JmsAdminServerIfc.getDurableConsumerMessageCount
    public int getQueueMessageCount(String queue) throws JMSException {
        int result = 0;
        try {
            result = _connection.getQueueMessageCount(queue);
        } catch (Exception exception) {
            raise(exception);
        }
        return result;
    }

    // implementation of JmsAdminServerIfc.purgeMessages
    public int purgeMessages() throws JMSException {
        int result = 0;
        try {
            result = _connection.purgeMessages();
        } catch (Exception exception) {
            raise(exception);
        }
        return result;
    }

    // implementation of JmsAdminServerIfc.stopServer
    public void stopServer() throws JMSException {
        try {
            _connection.stopServer();
        } catch (Exception exception) {
            raise(exception);
        }
    }

    // implementation of JmsAdminServerIfc.close
    public void close() {
        if (_connection instanceof Proxy) {
            ((Proxy) _connection).disposeProxy();
            _connection = null;
        }
    }

    // implementation of JmsAdminServerIfc.addUser
    public boolean addUser(String username, String password)
            throws JMSException {
        boolean result = false;
        try {
            result = _connection.addUser(username, password);
        } catch (Exception exception) {
            raise(exception);
        }
        return result;
    }

    // implementation of JmsAdminServerIfc.getAllUsers
    public Vector getAllUsers() throws JMSException {
        Vector result = null;
        try {
            result = _connection.getAllUsers();
        } catch (Exception exception) {
            raise(exception);
        }
        return result;
    }

    // implementation of JmsAdminServerIfc.removeUser
    public boolean removeUser(String username)
            throws JMSException {
        boolean result = false;
        try {
            result = _connection.removeUser(username);
        } catch (Exception exception) {
            raise(exception);
        }
        return result;
    }

    // implementation of JmsAdminServerIfc.changePassword
    public boolean changePassword(String username, String password)
            throws JMSException {
        boolean result = false;
        try {
            result = _connection.changePassword(username, password);
        } catch (Exception exception) {
            raise(exception);
        }
        return result;
    }

    private void raise(Exception exception) throws JMSException {
        if (exception instanceof JMSException) {
            throw (JMSException) exception;
        } else {
            JMSException error = new JMSException(exception.getMessage());
            error.setLinkedException(exception);
            throw error;
        }
    }

}
