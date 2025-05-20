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
 * $Id: OpenJMSConsumerFolder.java,v 1.1 2004/11/26 01:51:15 tanderson Exp $
 *
 * Date         Author  Changes
 * $Date	jimm    Created
 */


package org.exolab.jms.tools.admin;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;


/**
 * This is a folder node, which holds all durable consumers for a particular
 * server.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:15 $
 * @author      <a href="mailto:jima@exolab.org">Jim Alateras</a>
 */
public class OpenJMSConsumerFolder
    extends DefaultMutableTreeNode
    implements OpenJMSNode {

    // A reference to the tree this node belongs to.
    static private JTree tree_ = null;

    // A flag indicating if the menu has been created yet.
    static private boolean commandsCreated_ = false;

    // The popup menu for all consumers.
    static private JPopupMenu commands_ = null;

    /**
     * Construct an instance of this folder given the parent node.
     *
     * @param tree - The parent tree this consumer belons to.
     */
    public OpenJMSConsumerFolder(JTree tree) {
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

        JMenuItem m = new JMenuItem("Create Consumer");
        m.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                createConsumer();
            }
        }
        );
        commands_.add(m);
    }

    /**
     * Consumer folders are allowed on this node
     *
     * @return boolean Always returns true.
     */
    public boolean getAllowsChildren() {
        return true;
    }


    /**
     * The consumer folder is not a leaf node
     *
     * @return boolean Always returns false.
     *
     */
    public boolean isLeaf() {
        return false;
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
     * This node has changed. Inform the parent tree that it needs to be
     * re-drawn.
     *
     */
    private void refresh() {
        DefaultTreeModel model = (DefaultTreeModel) tree_.getModel();
        model.nodeStructureChanged((DefaultMutableTreeNode) this);
    }

    /**
     * Update all children belonging to this node.
     *
     */
    public void update() {
    }

    /**
     * The unique name of this queue/topic.
     *
     * @return String the queue/topic name.
     *
     */
    public String toString() {
        return "Consumers";
    }

    /**
     * Get the particular instance of the consumer that has been selected.
     *
     * @return OpenJMSConsumerFolder the instance selected.
     *
     */
    static private OpenJMSConsumerFolder getInstanceSelected() {
        Object loc = tree_.getLastSelectedPathComponent();
        return (OpenJMSConsumerFolder) loc;
    }

    /**
     * Delete the selected consumer object. Display a confirmation dialog
     * and wait for its return. If the user has confirmed the action, first
     * delete it from the database and if that is successful remove the node
     * from the tree.
     *
     */
    static private void createConsumer() {
    }
} // End OpenJMSConsumerFolder
