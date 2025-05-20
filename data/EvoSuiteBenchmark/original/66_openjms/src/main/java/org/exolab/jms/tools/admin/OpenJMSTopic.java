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
 * Copyright 2001,2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: OpenJMSTopic.java,v 1.1 2004/11/26 01:51:15 tanderson Exp $
 *
 * Date         Author  Changes
 * $Date	    jimm    Created
 */


package org.exolab.jms.tools.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;


/**
 * Extends the OpenJMSDestination node and defines a topic specific node
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:15 $
 * @author      <a href="mailto:mourikis@exolab.org">Jim Mourikis</a>
 * @see         OpenJMSObject
 */
public class OpenJMSTopic extends OpenJMSObject {

    /**
     * Construct a node with the specified topic name and the a reference
     * to the parent node in the tree.
     *
     * @param name This topic name
     * @param tree The parent tree that this node will belong too
     */
    public OpenJMSTopic(String topic, JTree tree) {
        super(topic, tree);
    }

    // implementation of OpenJMSDestination.createCommands
    protected void createCommands() {
        _commands = new JPopupMenu();
        JMenuItem m = new JMenuItem("Add Consumer");
        m.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                addConsumer();
            }
        }
        );
        _commands.add(m);

        m = new JMenuItem("Delete Topic");
        m.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                deleteTopic();
            }
        }
        );
        _commands.add(m);
    }

    /**
     * The edit queue/topic option has been selected.
     * Not currently implemented.
     *
     */
    static private void editTopic() {
        OpenJMSTopic This = (OpenJMSTopic) getInstanceSelected();
        System.out.println("editTopic");
    }

    /**
     * As a performance enhancement, no consumer is added to a topic
     * node until a request is made to expand the node, or a new consumer
     * is to be added to the node. This metgod is then called to fetch
     * all consumers from the database, and display the as children of
     * this node.
     *
     */
    public void update() {
        if (!_isExplored) {
            Enumeration e = AbstractAdminConnection.instance().getDurableConsumers(
                _name);

            if (e != null) {
                while (e.hasMoreElements()) {
                    String consumer = (String) e.nextElement();
                    add(new OpenJMSConsumer(consumer, _tree));
                }
                refresh();
            }

            _isExplored = true;
        }
    }

    /**
     * A new consumer is being added for this queue/topic. Popup a consumer
     * add dialog, to collect relevent information, then update the database.
     * If the database update is successful, add the consumer node as a child
     * of this queue/topic node, and refresh.
     *
     */
    static private void addConsumer() {

        OpenJMSTopic This = (OpenJMSTopic) getInstanceSelected();
        CreateTopicDialog.instance().displayCreateConsumer();
        if (CreateTopicDialog.instance().isConfirmed()) {
            boolean err = false;
            String errMessage = null;

            if (!This._isExplored) {
                This.update();
            }

            // First check to see that the durable consumer does not already
            // exist. If it does then display an error. If the durable
            // consumer does not exist then create it.
            if (!AbstractAdminConnection.instance().durableConsumerExists(
                CreateTopicDialog.instance().getName())) {
                if (AbstractAdminConnection.instance().addDurableConsumer(
                    This._name, CreateTopicDialog.instance().getName())) {
                    This.add(new OpenJMSConsumer
                        (CreateTopicDialog.instance().getName(), _tree));
                    This.refresh();
                } else {
                    err = true;
                    errMessage = "Failed to update database";
                }
            } else {
                err = true;
                errMessage = "Consumer already exists";
            }
            if (err) {
                JOptionPane.showMessageDialog
                    (_tree, errMessage, "Create Consumer Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Delete the selected topic object. Display a confirmation dialog
     * and wait for its return. If the user has confirmed the action, first
     * delete it from the database and if that is successful remove the node
     * from the tree.
     *
     * <P>Note: deleting a queue/topic also deletes all consumers of this
     * queue/topic.
     *
     */
    static private void deleteTopic() {
        OpenJMSTopic This = (OpenJMSTopic) getInstanceSelected();
        QueryDialog.instance().display(
            "Are you sure you want to delete \nselected Topic: "
            + This._name);
        if (org.exolab.jms.tools.admin.QueryDialog.instance().isConfirmed()) {
            if (AbstractAdminConnection.instance().removeDestination(
                This._name)) {
                This.removeFromParent();
                This.refresh();
            } else {
                JOptionPane.showMessageDialog
                    (_tree, "Failed to destroy Topic",
                        "Destroy Topic Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
