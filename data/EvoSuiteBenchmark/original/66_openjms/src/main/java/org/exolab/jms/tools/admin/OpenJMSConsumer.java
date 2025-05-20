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
 * $Id: OpenJMSConsumer.java,v 1.1 2004/11/26 01:51:15 tanderson Exp $
 *
 * Date         Author  Changes
 * $Date	    jimm    Created
 */

package org.exolab.jms.tools.admin;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;


/**
 * This class controls all dispay characteristics and menus related to a
 * consumer. Currently only add, delete and edit are allowed. Add is added
 * from the QueueTopic handler. Only one menu is created for all consumers
 * since it is modal, it can be shared by all the consumer nodes.
 *
 * The menu is moved and displayed underneath the node that activated it.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:15 $
 * @author      <a href="mailto:mourikis@exolab.org">Jim Mourikis</a>
 * @see         AdminMgr
 * @see	        OpenJMSNode
 */
public class OpenJMSConsumer extends DefaultMutableTreeNode
    implements OpenJMSNode {

    // The unique consumer name.
    private String consumerName_;

    // A reference to the tree this node belongs to.
    static private JTree tree_ = null;

    // A flag indicating if the menu has been created yet.
    static private boolean commandsCreated_ = false;

    // The popup menu for all consumers.
    static private JPopupMenu commands_ = null;

    /**
     * The constructor gets its unique name for this consumer plus a
     * reference to its parent tree.
     *
     * <P>If this is the first consumer call the menu for all consumers
     * is created.
     *
     * @param consumerName This consumers unique name.
     * @param tree The parent tree this consumer belons to.
     *
     */
    public OpenJMSConsumer(String consumerName, JTree tree) {
        consumerName_ = consumerName;
        if (!commandsCreated_) {
            tree_ = tree;
            createCommands();
            commandsCreated_ = true;
        }
    }

    /**
     * Create the menu for all consumers and set up the Action events for
     * each menu item. Since menus are shared, the callbacks called are
     * static. Once a menu is slected, the slected node can be determined
     * from the parent object.
     *
     */
    protected void createCommands() {
        commands_ = new JPopupMenu();

        JMenuItem m = new JMenuItem("De-Activate Consumer");
        m.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                unregisterConsumer();
            }
        }
        );
        commands_.add(m);

        m = new JMenuItem("Delete Consumer");
        m.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                deleteConsumer();
            }
        }
        );
        commands_.add(m);
    }


    /**
     * No children are allowed for consumers at this point.
     *
     * @return boolean Always returns false.
     *
     */
    public boolean getAllowsChildren() {
        return false;
    }


    /**
     * All consumers are leaves in the tree for this release.
     *
     * @return boolean Always returns true.
     *
     */
    public boolean isLeaf() {
        return true;
    }


    /**
     * This method is defined by the interface, but is not required by
     * consumers for this release.
     *
     */
    public void update() {
        // do nothing for the moment
    }

    /**
     * This node has been right clicked. The locations of this node is given
     * by the loc object. Use this location to popup the consumer message
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
     * The unique name of this consumer.
     *
     * @return String the consumer name.
     *
     */
    public String toString() {
        return consumerName_;
    }

    /**
     * This node has changed. Inform the parent tree that it needs to be
     * re-drawn.
     *
     */
    private void refresh() {
        DefaultTreeModel model = (DefaultTreeModel) tree_.getModel();
        model.nodeStructureChanged((DefaultMutableTreeNode) this);
    }

    /**
     * Get the particular instance of the consumer that has been selected.
     *
     * @return OpenJMSConsumer the instance selected.
     *
     */
    static private OpenJMSConsumer getInstanceSelected() {
        Object loc = tree_.getLastSelectedPathComponent();
        return (OpenJMSConsumer) loc;
    }


    /**
     * The edit consumer option has been selected.
     * Not currently implemented.
     *
     */
    static private void editConsumer() {
        OpenJMSConsumer This = getInstanceSelected();
        System.out.println("editConsumer");
    }

    /**
     * Delete the selected consumer object. Display a confirmation dialog
     * and wait for its return. If the user has confirmed the action, first
     * delete it from the database and if that is successful remove the node
     * from the tree.
     *
     */
    static private void deleteConsumer() {
        OpenJMSConsumer This = getInstanceSelected();
        QueryDialog.instance().display
            ("Are you sure you want to delete \nselected Consumer: "
            + This.consumerName_);
        if (org.exolab.jms.tools.admin.QueryDialog.instance().isConfirmed()) {
            OpenJMSTopic topic = (OpenJMSTopic) This.parent;

            if (AbstractAdminConnection.instance().removeDurableConsumer(This.consumerName_)) {
                This.removeFromParent();
                This.refresh();
            } else {
                JOptionPane.showMessageDialog
                    (tree_, "Failed to remove Consumer",
                        "Remove Consumer Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    /**
     * Unregister the selected consumer object. Display a confirmation dialog
     * and wait for its return. If the user has confirmed the action, then
     * proceed with the action. This is currently the only way to unregister
     * a persistent consumer.
     *
     */
    static private void unregisterConsumer() {
        OpenJMSConsumer This = getInstanceSelected();
        org.exolab.jms.tools.admin.QueryDialog.instance().display
            ("Are you sure you want to De-Activate \nselected Consumer: "
            + This.consumerName_);
        if (org.exolab.jms.tools.admin.QueryDialog.instance().isConfirmed()) {
            OpenJMSTopic topic = (OpenJMSTopic) This.parent;

            if (!AbstractAdminConnection.instance().unregisterConsumer(This.consumerName_)) {
                JOptionPane.showMessageDialog
                    (tree_, "Consumer is not currently active",
                        "De-Activate Consumer Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

} // End OpenJMSConsumer
