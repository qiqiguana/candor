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
 * Copyright 2000,2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: OnlineConnection.java,v 1.2 2005/08/30 14:04:11 tanderson Exp $
 *
 * Date         Author  Changes
 * $Date	    jimm    Created
 */


package org.exolab.jms.tools.admin;

import java.awt.Component;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.exolab.jms.administration.AdminConnectionFactory;
import org.exolab.jms.administration.JmsAdminServerIfc;
import org.exolab.jms.config.ConfigHelper;
import org.exolab.jms.config.Connector;
import org.exolab.jms.config.SecurityConfiguration;
import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.types.SchemeType;


/**
 * Connects to the OpenJMSServer for all updates and requests.
 *
 * <P>Note: The OpenJMSServer must be active and in a running state for this
 * type of connection to succeed.
 *
 * @version     $Revision: 1.2 $ $Date: 2005/08/30 14:04:11 $
 * @author      <a href="mailto:mourikis@exolab.org">Jim Mourikis</a>
 */
public class OnlineConnection extends AbstractAdminConnection {

    // The connection to the OpenJMS server.
    private JmsAdminServerIfc _admin = null;

    // The parent Gui
    private Component _parent;

    /**
     * Connect to the Admin Server
     *
     * @exception OnlineConnectionException When online connection fails.
     *
     */
    public OnlineConnection(Component parent, Configuration config) throws OnlineConnectionException {
        String username = "anonymous";
        String password = "anonymous";

        try {
            if (_instance == null) {
                Connector connector = config.getConnectors().getConnector(0);
                SchemeType scheme = connector.getScheme();
                String url = ConfigHelper.getAdminURL(scheme, config);

                SecurityConfiguration security =
                    config.getSecurityConfiguration();
                if (security.getSecurityEnabled()) {
                    CreateLogonDialog.instance().displayCreateLogon();

                    if (CreateLogonDialog.instance().isConfirmed()) {
                        username = CreateLogonDialog.instance().getName();
                        password = CreateLogonDialog.instance().getPassword();
                    }
                }

                //Perform logon
                _admin = AdminConnectionFactory.create(url, username, password);

                _parent = parent;
                _instance = this;
            } else {
                throw new OnlineConnectionException("Already connected");
            }
        } catch (Exception err) {
            throw new OnlineConnectionException("Failed to connect: " + err.toString());
        }
    }

    /**
     * Connect to the Admin Server, special constructor to be able to stop the server
     *
     * @exception OnlineConnectionException When online connection fails.
     *
     */
    public OnlineConnection(String username, String password, Configuration config) throws OnlineConnectionException {
        try {
            if (_instance == null) {
                Connector connector = config.getConnectors().getConnector(0);
                SchemeType scheme = connector.getScheme();
                String url = ConfigHelper.getAdminURL(
                    scheme, config);

                //Perform logon
                _admin = AdminConnectionFactory.create(url, username, password);

                _instance = this;
            } else {
                throw new org.exolab.jms.tools.admin.OnlineConnectionException("Already connected");
            }
        } catch (Exception err) {
            throw new org.exolab.jms.tools.admin.OnlineConnectionException("Failed to connect: " + err.toString());
        }
    }

    /**
     * Display the error in a JOptionPane.
     *
     * @param err The Error to display.
     * @param st The string to use as a title on the JOptionPane.
     *
     */
    private void displayError(Exception err, String st) {
        JOptionPane.showMessageDialog
            (_parent, st + "\n" + err, st, JOptionPane.ERROR_MESSAGE);
    }

    // implementation of AbstractAdminConnection.close
    public void close() {
        _admin.close();
        _instance = null;
        _admin = null;
    }

    // implementation of AbstractAdminConnection.addDurableConsumer
    public boolean addDurableConsumer(String topic, String name) {
        try {
            return _admin.addDurableConsumer(topic, name);
        } catch (javax.jms.JMSException error) {
            if (error.getLinkedException() != null) {
                error.getLinkedException().printStackTrace();
            }
            displayError(error, "Failed to add consumer");
            return false;
        } catch (Exception err) {
            displayError(err, "Failed to add consumer " + name);
            return false;
        }
    }

    // implementation of AbstractAdminConnection.unregisterConsumer
    public boolean removeDurableConsumer(String name) {
        try {
            return _admin.removeDurableConsumer(name);
        } catch (Exception err) {
            displayError(err, "Failed to remove consumer " + name);
            return false;
        }
    }

    // implementation of AbstractAdminConnection.unregisterConsumer
    public boolean unregisterConsumer(String name) {
        try {
            return _admin.unregisterConsumer(name);
        } catch (Exception err) {
            displayError(err, "Failed to De-Activate consumer " + name);
            return false;
        }
    }

    // implementation of AbstractAdminConnection.isConnected
    public boolean isConnected(String name) {
        try {
            return _admin.isConnected(name);
        } catch (Exception err) {
            return false;
        }
    }

    // implementation of AbstractAdminConnection.getAllDestinations
    public Enumeration getAllDestinations() {
        try {
            Vector v = _admin.getAllDestinations();
            Enumeration e = null;
            if (v != null) {
                e = v.elements();
            }
            return e;
        } catch (Exception err) {
            displayError(err, "Failed to getAllQueueTopics");
            return null;
        }
    }

    // implementation of AbstractAdminConnection.addDestination
    public boolean addDestination(String destination, boolean isQueue) {
        try {
            return _admin.addDestination(destination, new Boolean(isQueue));
        } catch (Exception err) {
            displayError(err, "Failed to add destination");
            return false;
        }
    }

    // implementation of AbstractAdminConnection.getDurableConsumerMessageCount
    public int getDurableConsumerMessageCount(String topic, String name) {
        try {
            return _admin.getDurableConsumerMessageCount(topic, name);
        } catch (Exception err) {
            displayError(err, "Failed in getDurableConsumerMessageCount");
            return -1;
        }
    }

    // implementation of AbstractAdminConnection.getQueueMessageCount
    public int getQueueMessageCount(String queue) {
        try {
            return _admin.getQueueMessageCount(queue);
        } catch (Exception err) {
            displayError(err, "Failed in getQueueMessageCount");
            return -1;
        }
    }

    // implementation of AbstractAdminConnection.durableConsumerExists
    public boolean durableConsumerExists(String name) {
        try {
            return _admin.durableConsumerExists(name);
        } catch (Exception err) {
            displayError(err, "Failed in durableConsumerExists");
        }

        return false;
    }

    // implementation of AbstractAdminConnection.getDurableConsumers
    public Enumeration getDurableConsumers(String topic) {
        try {
            Vector v = _admin.getDurableConsumers(topic);
            Enumeration e = null;
            if (v != null) {
                e = v.elements();
            }
            return e;
        } catch (Exception err) {
            displayError(err, "Failed in getDurableConsumers");
            return null;
        }
    }

    // implementation of AbstractAdminConnection.removeDestination
    public boolean removeDestination(String destination) {
        try {
            return _admin.removeDestination(destination);
        } catch (Exception err) {
            displayError(err, "Failed to destroy destination");
            return false;
        }
    }

    // implementation of AbstractAdminConnection.purgeMessages
    public int purgeMessages() {
        int result = -1;
        try {
            result = _admin.purgeMessages();
        } catch (Exception err) {
            displayError(err, "Failed to purge messages from database");
        }

        return result;
    }

    // implementation of AbstractAdminConnection.stopServer
    public void stopServer() {
        try {
            if (_admin == null) {
                JOptionPane.showMessageDialog
                    (_parent, "Must connect with online mode \nto "
                    + "shutdown server", "Shutdown Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                _admin.stopServer();
                _instance = null;
                _admin = null;
            }
        } catch (Exception err) {
            displayError(err, "Failed to Stop server");
        }
    }

    // implementation of AbstractAdminConnection.addUser
    public boolean addUser(String username, String password) {
        try {
            return _admin.addUser(username, password);
        } catch (javax.jms.JMSException error) {
            if (error.getLinkedException() != null) {
                error.getLinkedException().printStackTrace();
            }
            displayError(error, "Failed to add user");
            return false;
        } catch (Exception err) {
            displayError(err, "Failed to add user " + username);
            return false;
        }
    }

    // implementation of AbstractAdminConnection.changePassord
    public boolean changePassword(String username, String password) {
        try {
            return _admin.changePassword(username, password);
        } catch (javax.jms.JMSException error) {
            if (error.getLinkedException() != null) {
                error.getLinkedException().printStackTrace();
            }
            displayError(error, "Failed to change password");
            return false;
        } catch (Exception err) {
            displayError(err, "Failed to change password for user " + username);
            return false;
        }
    }

    // implementation of AbstractAdminConnection.removeUser
    public boolean removeUser(String username) {
        try {
            return _admin.removeUser(username);
        } catch (javax.jms.JMSException error) {
            if (error.getLinkedException() != null) {
                error.getLinkedException().printStackTrace();
            }
            displayError(error, "Failed to remove user");
            return false;
        } catch (Exception err) {
            displayError(err, "Failed to remove user " + username);
            return false;
        }
    }

    // implementation of AbstractAdminConnection.getAllUsers
    public Enumeration getAllUsers() {
        try {
            Vector v = _admin.getAllUsers();
            Enumeration e = null;
            if (v != null) {
                e = v.elements();
            }
            return e;
        } catch (Exception err) {
            displayError(err, "Failed to getAllUsers");
            return null;
        }
    }

} // End OnlineConnection
