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
 * $Id: CreateConsumerDialog.java,v 1.1 2004/11/26 01:51:15 tanderson Exp $
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.text.Keymap;


/**
 * A simple dialog to collect information for creating a durable consumer
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:15 $
 * @author      <a href="mailto:jima@exolab.org">Jim Alateras</a>
 * @see         AdminMgr
 */
public class CreateConsumerDialog extends JDialog {

    // The name chosen for this object
    protected String consumerName_;
    protected String topicSubscription_;

    // shared gui fields
    protected JTextField displayText;

    // The two possible states of theis dialog.
    final static public int CANCELED = 1;
    final static public int CONFIRMED = 2;

    // The command status used to shutdown this window.
    protected int status_;

    // All the gui objects for this dialog
    private JPanel jPanel1;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JSeparator jSeparator2;
    private JLabel jLabel1;
    private JTextField jTextField1;
    private JLabel jLabel2;
    private JTextField jTextField2;

    // The one and only instance of this object.
    static private CreateConsumerDialog instance_;

    /**
     * Creates new form TopicDialog
     *
     * @param parent The parent form.
     */
    public CreateConsumerDialog(JFrame parent) {
        super(parent, true);
        initComponents();
        pack();
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
        jLabel1 = new JLabel();
        jLabel1.setText("Enter the consumer name");
        jLabel2 = new JLabel();
        jLabel2.setText("Enter the topic");
        jTextField1 = new JTextField();
        jTextField2 = new JTextField();
        jSeparator2 = new JSeparator();
        setTitle("Create Durable Consumer");
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
        jPanel3.setLayout(new BorderLayout(0, 15));
        jPanel4.setLayout(new BorderLayout(0, 15));
        jTextField1.setToolTipText
            ("Enter the unique consumer name");
        jTextField2.setToolTipText
            ("Enter the topic or wildcard subscription");

        Border loweredbevel = BorderFactory.createLoweredBevelBorder();

        jTextField1.setBorder(loweredbevel);
        jTextField1.setEditable(true);
        jTextField1.setText("");
        jTextField1.setHorizontalAlignment(SwingConstants.LEFT);

        jTextField2.setBorder(loweredbevel);
        jTextField2.setEditable(true);
        jTextField2.setText("");
        jTextField2.setHorizontalAlignment(SwingConstants.LEFT);

        jPanel2.add(jLabel1, BorderLayout.NORTH);
        jPanel2.add(jTextField1, BorderLayout.CENTER);
        jPanel2.add(jSeparator2, BorderLayout.SOUTH);

        jPanel3.add(jLabel2, BorderLayout.NORTH);
        jPanel3.add(jTextField2, BorderLayout.CENTER);
        jPanel3.add(jSeparator2, BorderLayout.SOUTH);

        jPanel4.add(jPanel2, BorderLayout.NORTH);
        jPanel4.add(jPanel2, BorderLayout.CENTER);
        jPanel4.add(jSeparator2, BorderLayout.SOUTH);

        getContentPane().add(jPanel4, BorderLayout.CENTER);

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
     * Display the create consumer dialog box
     */
    public void displayCreateConsumer() {
        jTextField1.setText("");
        jTextField2.setText("");
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
     * Get the consumer name
     *
     * @return String
     */
    public String getConsumerName() {
        return consumerName_;
    }

    /**
     * Get the topic subscription
     *
     * @return String
     */
    public String getTopicSubscription() {
        return topicSubscription_;
    }

    /**
     *	Closes the dialog
     *
     * @param evt the window event that triggered this call.
     *
     */
    protected void closeDialog(WindowEvent evt) {
        setVisible(false);
        dispose();
    }

    /**
     * Whether this dialog was confirmed or canceled.
     *
     * @return boolena true if the OK button was pressed.
     *
     */
    public boolean isConfirmed() {
        return status_ == CONFIRMED;
    }

    /**
     * The cancel button was pressed. Close the GUI, and recored that cancel
     * was pressed.
     *
     */
    protected void cancel() {
        status_ = CANCELED;
        setVisible(false);
        dispose();
    }

    /**
     * The OK button was pressed. Get the name and confirm its not null.
     * if it is null or empty display an error dialog.
     *
     * if a consumer name and a topic sub have been entered then close the
     * dialog box otherwise display an error.
     */
    protected void confirm() {
        consumerName_ = jTextField1.getText();
        topicSubscription_ = jTextField2.getText();

        if ((consumerName_ == null) ||
            (consumerName_.length() == 0) ||
            (topicSubscription_ == null) ||
            (topicSubscription_.length() == 0)) {
            JOptionPane.showMessageDialog
                (this, "A consumer name and topic subscription must be suplied",
                    "Create Error", JOptionPane.ERROR_MESSAGE);
        } else {
            status_ = CONFIRMED;
            setVisible(false);
            dispose();
        }
    }

    /**
     * Get the one and only instance of this dialog. The dialog must first
     * have been created with the create call below.
     *
     * @return TopicDialog the one and only instance
     *
     */
    public static CreateConsumerDialog instance() {
        return instance_;
    }

    /**
     * Create the one and only instance of the Consumer Dialog.
     *
     * @param parent the parent of this dialog
     * @return TopicDialog the one and only instance
     *
     */
    public static CreateConsumerDialog create(JFrame parent) {
        if (instance_ == null) {
            instance_ = new CreateConsumerDialog(parent);
        }
        return instance_;
    }

    /**
     * Display the consumer dialog box
     */
    public void display() {
        JOptionPane.showInputDialog
            (getParent(), "Enter a unique consumer name",
                "Create Durable Consumer", JOptionPane.PLAIN_MESSAGE);
    }
}
