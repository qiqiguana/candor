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
 * Copyright 2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: OpenJMSUser.java,v 1.1 2004/11/26 01:51:15 tanderson Exp $
 */
package org.exolab.jms.tools.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;


/**
 * Extends the OpenJMSObject node
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:15 $
 * @author      <a href="mailto:knut@lerpold.no">Knut Lerpold</a>
 * @see         OpenJMSObject
 * @see         AdminMgr
 */
public class OpenJMSUser extends OpenJMSObject {

    /**
     * Construct a node with the specified username and the a reference
     * to the parent node in the tree.
     *
     * @param name This username
     * @param tree The parent tree that this node will belong too
     */
    public OpenJMSUser(String name, JTree tree) {
        super(name, tree);
    }

    /**
     * No operation
     */
    public void update() {
    }

    /**
     * Create the menu for all queue and set up the Action events for
     * each menu item. Since menus are shared, the callbacks called are
     * static. Once a menu is slected, the slected node can be determined
     * from the parent object.
     */
    protected void createCommands() {
        _commands = new JPopupMenu();
        JMenuItem m = new JMenuItem("Delete user");
        m.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                deleteUser();
            }
        }
        );
        _commands.add(m);

        m = new JMenuItem("Change password");
        m.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                changePassword();
            }
        }
        );
        _commands.add(m);
    }

    /**
     * Changes password for the selected user.
     * If the user has confirmed the action, first
     * delete it from the database and if that is successful remove the node
     * from the tree.
     */
    private static void changePassword() {
        OpenJMSUser This = (OpenJMSUser) getInstanceSelected();
        ChangePasswordDialog.instance().displayChangePassword(This._name);

        if (ChangePasswordDialog.instance().isConfirmed()) {
            if (AbstractAdminConnection.instance().changePassword(
                This._name,
                ChangePasswordDialog.instance().getPassword())) {
                This.refresh();
            } else {
                JOptionPane.showMessageDialog
                    (_tree, "User already exists", "Create Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Delete the selected user object. Display a confirmation dialog
     * and wait for its return. If the user has confirmed the action, first
     * delete it from the database and if that is successful remove the node
     * from the tree.
     */
    private static void deleteUser() {
        OpenJMSUser This = (OpenJMSUser) getInstanceSelected();
        QueryDialog.instance().display
            ("Are you sure you want to delete \nselected User: "
            + This._name);
        if (org.exolab.jms.tools.admin.QueryDialog.instance().isConfirmed()) {
            if (AbstractAdminConnection.instance().removeUser(
                This._name)) {
                This.removeFromParent();
                This.refresh();
            } else {
                JOptionPane.showMessageDialog
                    (_tree, "Failed to destroy User",
                        "Destroy User Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

} //-- OpenJMSUser
