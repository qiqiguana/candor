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
 * $Id: CreateUserDialog.java,v 1.1 2004/11/26 01:51:15 tanderson Exp $
 *
 * Date         Author  Changes
 * $Date	    jimm    Created
 */

package org.exolab.jms.tools.admin;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.text.Keymap;


/**
 * A simple dialog to collect information for creating a User
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:15 $
 * @author      <a href="mailto:knut@lerpold.no">Knut Lerpold</a>
 * @see         AdminMgr
 */
public class CreateUserDialog extends BaseDialog {

    // All the gui objects for this dialog
    private JPanel jPanel1;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel jPanel2;
    private JSeparator jSeparator2;
    private JLabel jLabel1;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JPasswordField jPasswordField1;
    private JPasswordField jPasswordField2;


    protected String password;
    protected String confirmedPassword;

    // The one and only instance of this object.
    static private CreateUserDialog instance_;


    /**
     * Creates new form UserDialog
     *
     * @param parent The parent form.
     */
    public CreateUserDialog(JFrame parent) {
        super(parent);
    }

    /**
     * Create all the gui components that comprise this form, and setup all
     * action handlers.
     *
     */
    protected void initComponents() {
        jPanel1 = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();
        jPanel4 = new JPanel();
        jPanel5 = new JPanel();
        jLabel2 = new JLabel();
        jLabel2.setText("Enter password");
        jLabel3 = new JLabel();
        jLabel3.setText("Confirm password");
        jPasswordField1 = new JPasswordField();
        jPasswordField2 = new JPasswordField();


        jLabel1 = new JLabel();
        jLabel1.setText("Enter the user name");
        displayText = new JTextField();
        jSeparator2 = new JSeparator();
        setTitle("Create User");
        setModal(true);
        setResizable(true);
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent evt) {
                closeDialog(evt);
            }
        }
        );

        jPanel1.setLayout(new FlowLayout(1, 50, 10));
        okButton.setToolTipText("Press to confirm Action");
        okButton.setText("OK");
        getRootPane().setDefaultButton(okButton);
        jPanel1.add(okButton);
        cancelButton.setToolTipText("Press to abort Action");
        cancelButton.setText("Cancel");
        jPanel1.add(cancelButton);
        getContentPane().add(jPanel1, BorderLayout.SOUTH);

        jPanel2.setLayout(new BorderLayout(0, 15));
        jPanel2.add(jPanel3, BorderLayout.NORTH);
        jPanel2.add(jPanel4, BorderLayout.CENTER);
        jPanel2.add(jPanel5, BorderLayout.SOUTH);
        getContentPane().add(jPanel2, BorderLayout.CENTER);

        //Username
        jPanel3.setLayout(new BorderLayout(0, 15));
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        displayText.setBorder(loweredbevel);
        displayText.setEditable(true);
        displayText.setText("");
        displayText.setToolTipText
            ("Enter the unique name for this user");
        displayText.setHorizontalAlignment(SwingConstants.LEFT);
        jPanel3.add(jLabel1, BorderLayout.NORTH);
        jPanel3.add(displayText, BorderLayout.CENTER);
        jPanel3.add(jSeparator2, BorderLayout.SOUTH);
        //getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        //Password
        jPanel4.setLayout(new BorderLayout(0, 15));
        jPasswordField1.setBorder(loweredbevel);
        jPasswordField1.setEditable(true);
        jPasswordField1.setText("");
        jPasswordField1.setHorizontalAlignment(SwingConstants.LEFT);
        jPanel4.add(jLabel2, BorderLayout.NORTH);
        jPanel4.add(jPasswordField1, BorderLayout.CENTER);
        jPanel4.add(jSeparator2, BorderLayout.SOUTH);
        //getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        //Confirm password
        jPanel5.setLayout(new BorderLayout(0, 15));
        jPasswordField2.setBorder(loweredbevel);
        jPasswordField2.setEditable(true);
        jPasswordField2.setText("");
        jPasswordField2.setHorizontalAlignment(SwingConstants.LEFT);
        jPanel5.add(jLabel3, BorderLayout.NORTH);
        jPanel5.add(jPasswordField2, BorderLayout.CENTER);
        jPanel5.add(jSeparator2, BorderLayout.SOUTH);
        //getContentPane().add(jPanel5, java.awt.BorderLayout.CENTER);

        okButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                confirm();
            }
        }
        );

        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                cancel();
            }
        }
        );

        // Have default button get the keypress event.
        // This is broken with jdk1.3rc1
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        Keymap km = displayText.getKeymap();
        km.removeKeyStrokeBinding(enter);
    }

    /**
     * Display the create user dialog box
     */
    public void displayCreateUser() {
        clearPasswords();
        displayText.setText("");

        setLocationRelativeTo(getParent());
        status_ = CANCELED;
        setVisible(true);

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                cancelButton.requestFocus();
            }
        }
        );
    }

    /**
     * Override confirm to be able to check password
     *
     */
    protected void confirm() {
        name_ = displayText.getText();
        password = String.valueOf(jPasswordField1.getPassword());
        confirmedPassword = String.valueOf(jPasswordField2.getPassword());

        if (name_ == null || name_.equals("")) {
            JOptionPane.showMessageDialog
                (this, "A name must be suplied", "Create Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (password == null || password.equals("")) {
            clearPasswords();
            JOptionPane.showMessageDialog
                (this, "A password must be suplied", "Create Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (confirmedPassword == null || confirmedPassword.equals("")) {
            clearPasswords();
            JOptionPane.showMessageDialog
                (this, "A confirmed password must be suplied", "Create Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!password.equals(confirmedPassword)) {
            clearPasswords();
            JOptionPane.showMessageDialog
                (this, "Confirmed password don't match password", "Create Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            status_ = CONFIRMED;
            setVisible(false);
            dispose();
        }
    }

    private void clearPasswords() {
        jPasswordField1.setText("");
        jPasswordField2.setText("");
    }

    /**
     * Get the one and only instance of this dialog. The dialog must first
     * have been created with the create call below.
     *
     * @return UserDialog the one and only instance
     *
     */
    public static CreateUserDialog instance() {
        return instance_;
    }

    /**
     * Create the one and only instance of the User Dialog.
     *
     * @param parent the parent of this dialog
     * @return QueueDialog the one and only instance
     *
     */
    public static CreateUserDialog create(JFrame parent) {
        if (instance_ == null) {
            instance_ = new CreateUserDialog(parent);
        }
        return instance_;
    }

    /** Getter for property password.
     * @return Value of property password.
     *
     */
    public String getPassword() {
        return password;
    }

}
