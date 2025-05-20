/**
 * GUIAuthenticator.java
 * 
 * $Revision: 846 $
 * 
 * $Date: 2009-04-22 18:45:17 +0100 (Wed, 22 Apr 2009) $
 * 
 * Copyright (c) 2009, University Library, University of Illinois at 
 * Urbana-Champaign. All rights reserved. 
 * 
 * Developed by: The Hub and Spoke Project Group 
 *               University of Illinois Urbana-Champaign Library
 *               http://dli.grainger.uiuc.edu/echodep/hands/ 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the 
 * "Software"), to deal with the Software without restriction, including 
 * without limitation the rights to use, copy, modify, merge, publish, 
 * distribute, sublicense, and/or sell copies of the Software, and to 
 * permit persons to whom the Software is furnished to do so, subject to 
 * the following conditions: 
 * 
 * - Redistributions of source code must retain the above copyright notice, 
 * this list of conditions and the following disclaimers. 
 * 
 * - Redistributions in binary form must reproduce the above copyright 
 * notice, this list of conditions and the following disclaimers in the 
 * documentation and/or other materials provided with the distribution. 
 * 
 * - Neither the names of The Hub and Spoke Project Group, University of 
 * Illinois Urbana-Champaign Library, nor the names of its contributors may 
 * be used to endorse or promote products derived from this Software 
 * without specific prior written permission. 
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS 
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE CONTRIBUTORS OR COPYRIGHT HOLDERS BE LIABLE FOR 
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 * SOFTWARE OR THE USE OR OTHER DEALINGS WITH THE SOFTWARE. 
 */

package edu.uiuc.ndiipp.hubandspoke.workflow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

import javax.swing.JFrame;

/**
 * @author Bill Ingram
 *
 */
public class GUIAuthenticator extends Authenticator implements ActionListener {
    //private javax.swing.JPanel panel;
    private String name;
    private javax.swing.JDialog dialog;
    private javax.swing.JButton exitButton;
    private javax.swing.JPanel inputPane;
    private javax.swing.JButton loginButton;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordPasswordField;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTextField;
    private PasswordAuthentication passAuth = null;

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == loginButton) {
	    String un = usernameTextField.getText();
	    char[] pass = passwordPasswordField.getPassword();
	    passAuth = new PasswordAuthentication(un, pass);
	} else {
	    passAuth = null;
	}

	passwordPasswordField.setText("");
	dialog.setVisible(false);
    }

    public GUIAuthenticator(String title) {
	name = title;
	initComponents(); // set up the gui

	loginButton.addActionListener(this);
	exitButton.addActionListener(this);
    }
    
    public GUIAuthenticator() {
	name = null;
	initComponents(); // set up the gui

	loginButton.addActionListener(this);
	exitButton.addActionListener(this);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
	dialog.setTitle(super.getRequestingPrompt());
	dialog.setVisible(true);
	return passAuth;
    }

    private void initComponents() {

	dialog = new javax.swing.JDialog(new JFrame(), true);
	inputPane = new javax.swing.JPanel();
	usernameLabel = new javax.swing.JLabel();
	passwordLabel = new javax.swing.JLabel();
	usernameTextField = new javax.swing.JTextField();
	passwordPasswordField = new javax.swing.JPasswordField();
	loginButton = new javax.swing.JButton();
	exitButton = new javax.swing.JButton();

	dialog.setDefaultCloseOperation(
		javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	//dialog.setTitle("Login");
	
	inputPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());

	usernameLabel.setText("Username");

	passwordLabel.setText("Password");

	javax.swing.GroupLayout inputPaneLayout = new javax.swing.GroupLayout(
		inputPane);
	inputPane.setLayout(inputPaneLayout);
	inputPaneLayout.setHorizontalGroup(
		inputPaneLayout.createParallelGroup(
		javax.swing.GroupLayout.Alignment.LEADING).addGroup(inputPaneLayout.
		createSequentialGroup().addContainerGap().addGroup(inputPaneLayout.
		createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).
		addGroup(inputPaneLayout.createSequentialGroup().addComponent(
		usernameLabel).addPreferredGap(
		javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
		usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 154,
		javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(inputPaneLayout.
		createSequentialGroup().addComponent(passwordLabel).
		addPreferredGap(
		javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
		passwordPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE,
		javax.swing.GroupLayout.DEFAULT_SIZE,
		javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap(26,
		Short.MAX_VALUE)));

	inputPaneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
		new java.awt.Component[]{passwordLabel, usernameLabel});

	inputPaneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
		new java.awt.Component[]{passwordPasswordField,
		    usernameTextField});

	inputPaneLayout.setVerticalGroup(
		inputPaneLayout.createParallelGroup(
		javax.swing.GroupLayout.Alignment.LEADING).addGroup(inputPaneLayout.
		createSequentialGroup().addContainerGap().addGroup(inputPaneLayout.
		createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).
		addComponent(usernameLabel).addComponent(usernameTextField,
		javax.swing.GroupLayout.PREFERRED_SIZE,
		javax.swing.GroupLayout.DEFAULT_SIZE,
		javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
		javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(inputPaneLayout.
		createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).
		addComponent(passwordLabel).addComponent(passwordPasswordField,
		javax.swing.GroupLayout.PREFERRED_SIZE,
		javax.swing.GroupLayout.DEFAULT_SIZE,
		javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(
		javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

	loginButton.setText("Login");

	exitButton.setText("Cancel");

	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(dialog.
		getContentPane());
	dialog.getContentPane().setLayout(layout);
	layout.setHorizontalGroup(
		layout.createParallelGroup(
		javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.
		createSequentialGroup().addGap(56, 56, 56).addComponent(
		loginButton).addPreferredGap(
		javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
		exitButton).addContainerGap(76, Short.MAX_VALUE)).addComponent(
		inputPane, javax.swing.GroupLayout.DEFAULT_SIZE,
		javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
	layout.setVerticalGroup(
		layout.createParallelGroup(
		javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.
		createSequentialGroup().addComponent(inputPane,
		javax.swing.GroupLayout.PREFERRED_SIZE,
		javax.swing.GroupLayout.DEFAULT_SIZE,
		javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
		javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.
		createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER).
		addComponent(exitButton).addComponent(loginButton)).
		addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
		Short.MAX_VALUE)));

	java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().
		getScreenSize();
	dialog.setBounds((screenSize.width - 254) / 2,
		(screenSize.height - 146) / 2, 254, 146);

    }// </editor-fold>
}
