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
 * $Id: AdminInfo.java,v 1.1 2004/11/26 01:51:15 tanderson Exp $
 *
 * Date         Author  Changes
 * $Date	    jimm    Created
 */


package org.exolab.jms.tools.admin;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;


/**
 * Extracts information about a queue/topic and consumer from the OpenJMSServer
 * and displays it next to the appropriate cell, in a JTextField.
 *
 * The class inherits from DefaultTreeCellrenderer, and is called each time
 * the tree object needs to redraw itself.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:15 $
 * @author      <a href="mailto:mourikis@exolab.org">Jim Mourikis</a>
 */
public class AdminInfo extends DefaultTreeCellRenderer {

    // The text field used to display the info for the cell.
    protected JTextField field_ = new JTextField("No Messages");

    // The Horizontal box structure the JTextField is inserted into,
    // to make the cell and text field appear next to each other.
    private Component strut_ = Box.createHorizontalStrut(5);

    // The panel use to contain the above two swing objects.
    private JPanel panel_ = new JPanel();

    /**
     * Construct the panel, and set the background to be that of the tree.
     * Add the cell, display text field and the horizontal layout to the panel.
     *
     */
    public AdminInfo() {
        panel_.setBackground(UIManager.getColor("Tree.textBackground"));
        setOpaque(false);
        field_.setOpaque(false);
        panel_.setOpaque(false);

        panel_.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel_.add(this);
        panel_.add(strut_);
        panel_.add(field_);
    }


    /**
     * A draw request for the cell has been made. If the cell is a queue/topic
     * fetch all the relevant details, and write the string to the textfield.
     * If the cell is a consumer, fetch all the consumer details, and write
     * them into the text field.
     * For all other cell types do not display the textfield.
     *
     * @param tree	The JTree this cell belongs to.
     * @param value The cell being rendered.
     * @param selected True if this cell has been selected.
     * @param expanded True if this cell has been opened up.
     * @param leaf True if this is a leaf node.
     * @param row The row this node is in.
     * @param hasFocus True is this node currently has the focus.
     * @return Component The panel all the object belong to.
     *
     */
    public Component getTreeCellRendererComponent
        (JTree tree, Object value, boolean selected, boolean expanded,
         boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded,
            leaf, row, hasFocus);
        if (value instanceof OpenJMSNode) {
            field_.setForeground(java.awt.Color.black);
            if (value instanceof OpenJMSQueue) {
                if (AbstractAdminConnection.instance() != null) {
                    OpenJMSQueue queue = (OpenJMSQueue) value;

                    int num = AbstractAdminConnection.instance().getQueueMessageCount(
                        queue.toString());
                    String st = Integer.toString(num);
                    field_.setText(st);
                    field_.setVisible(true);
                }
            } else if (value instanceof OpenJMSTopic) {
                // do nothing
                field_.setVisible(false);
            } else if (value instanceof OpenJMSUser) {
                // do nothing
                field_.setVisible(false);
            } else if (value instanceof OpenJMSConsumer) {
                if (AbstractAdminConnection.instance() != null) {
                    OpenJMSConsumer consumer = (OpenJMSConsumer) value;
                    OpenJMSTopic topic = (OpenJMSTopic) consumer.getParent();

                    int num = AbstractAdminConnection.instance().getDurableConsumerMessageCount(
                        topic.toString(), consumer.toString());
                    String st = Integer.toString(num);

                    if (AbstractAdminConnection.instance().isConnected(
                        consumer.toString())) {
                        field_.setForeground(java.awt.Color.red);
                    }
                    field_.setText(st);
                    field_.setVisible(true);
                }
            }
        } else {
            field_.setVisible(false);
        }

        return panel_;
    }

} // End AdminInfo
