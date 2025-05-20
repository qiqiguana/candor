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
 * Copyright 2000 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: OpenJMSServer.java,v 1.1 2004/11/26 01:51:15 tanderson Exp $
 *
 * Date         Author  Changes
 * $Date	    jimm    Created
 */


package org.exolab.jms.tools.admin;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.exolab.jms.client.JmsDestination;
import org.exolab.jms.client.JmsQueue;
import org.exolab.jms.client.JmsTopic;


/**
 * This class controls all dispay characteristics and menus related to an
 * OpenJMSServer. Currently only add queue/topic is supported.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:15 $
 * @author      <a href="mailto:mourikis@exolab.org">Jim Mourikis</a>
 * @see         OpenJMSConsumer
 * @see         AdminMgr
 * @see         QueryDialog
 */
public class OpenJMSServer extends DefaultMutableTreeNode {

    // The server name.
    private String serverName_;

    //  A reference to the tree this node belongs to.
    static private JTree tree_ = null;

    // A flag indicating if the menu has been created yet.
    static private boolean commandsCreated_ = false;

    // The popup menu for all queue/topics
    static private JPopupMenu commands_ = null;

    /**
     * The OpenJMS server connection. Currently there is only one
     * OpenJMSServer connection at a time.
     *
     * @param serverName The name of this server
     * @param tree The parent tree this root node belongs to.
     *
     */
    public OpenJMSServer(String serverName, JTree tree) {
        serverName_ = serverName;

        if (!commandsCreated_) {
            tree_ = tree;

            // construct the top level folders
            //add(new OpenJMSConsumerFolder(tree_));
            //add(new OpenJMSQueueFolder(tree_));
            //add(new OpenJMSTopicFolder(tree_));

            createCommands();
            commandsCreated_ = true;
        }
    }

    /**
     * Create the menu for all servers and set up the Action events for
     * each menu item. Since menus are shared, the callbacks called are
     * static. Once a menu is slected, the slected node can be determined
     * from the parent object.
     *
     */
    protected void createCommands() {
        commands_ = new JPopupMenu();
        JMenuItem m = new JMenuItem("Add Queue");
        m.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                addQueue();
            }
        }
        );
        commands_.add(m);

        m = new JMenuItem("Add Topic");
        m.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                addTopic();
            }
        }
        );
        commands_.add(m);

        m = new JMenuItem("Add User");
        m.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                addUser();
            }
        }
        );
        commands_.add(m);

        m = new JMenuItem("Purge Messages");
        m.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                purgeMessages();
            }
        }
        );
        commands_.add(m);
    }

    /**
     * Determine all known OpenJMSServers. For the moment only the offline
     * mode is supported, which is basically opening up the database directly.
     *
     * @param tree The parent tree this root node belongs to.
     *
     */
    static public DefaultTreeModel createServerList(JTree tree) {
        // todo try and connect to all known servers.
        // for the moment just add one.
        OpenJMSServer server = new OpenJMSServer("OpenJMSServer", tree);
        return new DefaultTreeModel(server);
    }

    /**
     * Get all queue/topics from the database for this JMS server and display
     * them as children of the root node.
     *
     */
    public void displayConnections() {
        Enumeration e = AbstractAdminConnection.instance().getAllDestinations();
        if (e != null) {
            while (e.hasMoreElements()) {
                JmsDestination destination = (JmsDestination) e.nextElement();
                if (destination instanceof JmsQueue) {
                    add(new OpenJMSQueue(destination.getName(), tree_));
                } else if (destination instanceof JmsTopic) {
                    add(new OpenJMSTopic(destination.getName(), tree_));
                }
            }
        }
        //Users
        e = AbstractAdminConnection.instance().getAllUsers();
        if (e != null) {
            while (e.hasMoreElements()) {
                add(new OpenJMSUser(e.nextElement().toString(), tree_));
            }
        }
        refresh();
    }

    /**
     * Children are allowed for all servers
     *
     * @return boolean Always returns true.
     *
     */
    public boolean getAllowsChildren() {
        return true;
    }

    /**
     * This node has been right clicked. The locations of this node is given
     * by the loc object. Use this location to popup the server message
     * menu.
     *
     * @param The location of this Consumer node.
     *
     */
    public void displayCommands(Rectangle loc) {
        double x;
        double y;

        x = loc.getX();
        y = loc.getY();
        y += loc.getHeight();

        commands_.show(tree_, (int) x, (int) y);
    }

    /**
     * The unique name of this server
     *
     * @return String the server name.
     *
     */
    public String toString() {
        return serverName_;
    }

    /**
     * This node has changed. Inform the parent tree that it needs to be
     * re-drawn.
     *
     */
    public void refresh() {
        DefaultTreeModel model = (DefaultTreeModel) tree_.getModel();
        model.nodeStructureChanged((DefaultMutableTreeNode) this);
    }

    /**
     * Get the particular instance of the server that has been selected.
     *
     * @return OpenJMSServer the instance selected.
     *
     */
    static private OpenJMSServer getInstanceSelected() {
        Object loc = tree_.getLastSelectedPathComponent();
        return (OpenJMSServer) loc;
    }

    /**
     * A new queue is being added for this server. Popup a add queue
     * destination dialog, to collect relevent information, then update the
     * database. If the database update is successful, add the queue node as a
     * a child of this server node, and refresh.
     */
    static private void addQueue() {
        OpenJMSServer This = getInstanceSelected();
        CreateQueueDialog.instance().displayCreateQueue();

        if (CreateQueueDialog.instance().isConfirmed()) {
            if (AbstractAdminConnection.instance().addDestination(
                CreateQueueDialog.instance().getName(), true)) {

                This.add(new OpenJMSQueue(
                    CreateQueueDialog.instance().getName(), tree_));
                This.refresh();
            } else {
                JOptionPane.showMessageDialog
                    (tree_, "Queue already exists", "Create Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * A new queue is being added for this server. Popup a add queue
     * destination dialog, to collect relevent information, then update the
     * database. If the database update is successful, add the queue node as a
     * a child of this server node, and refresh.
     */
    static private void addUser() {
        OpenJMSServer This = getInstanceSelected();
        CreateUserDialog.instance().displayCreateUser();

        if (CreateUserDialog.instance().isConfirmed()) {
            if (AbstractAdminConnection.instance().addUser(
                CreateUserDialog.instance().getName(),
                CreateUserDialog.instance().getPassword())) {

                This.add(new OpenJMSUser(
                    CreateUserDialog.instance().getName(), tree_));
                This.refresh();
            } else {
                JOptionPane.showMessageDialog
                    (tree_, "User already exists", "Create Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * A new topic is being added for this server. Popup a add topic
     * destination dialog, to collect relevent information, then update the
     * database. If the database update is successful, add the topic node as a
     * a child of this server node, and refresh.
     */
    static private void addTopic() {
        OpenJMSServer This = getInstanceSelected();
        CreateTopicDialog.instance().displayCreateTopic();

        if (CreateTopicDialog.instance().isConfirmed()) {
            if (AbstractAdminConnection.instance().addDestination(
                CreateTopicDialog.instance().getName(), false)) {

                This.add(new OpenJMSTopic(
                    CreateTopicDialog.instance().getName(), tree_));
                This.refresh();
            } else {
                JOptionPane.showMessageDialog
                    (tree_, "Topic already exists", "Create Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Purge all processed messages from the databse.
     */
    static private void purgeMessages() {
        QueryDialog.instance().display
            ("Are you sure you want to purge all\n processed messages.");
        if (org.exolab.jms.tools.admin.QueryDialog.instance().isConfirmed()) {
            int count = AbstractAdminConnection.instance().purgeMessages();
            JOptionPane.showMessageDialog
                (tree_, count + " messages were purged.", "Info",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog
                (tree_, "Purge Messages Aborted.", "Purge Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
} // End ServerList
