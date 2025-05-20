package macaw.presentationLayer;

import macaw.system.*;
import macaw.businessLayer.MacawCurationAPI;
import macaw.businessLayer.User;
import macaw.util.WindowSizeListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;


/**
 * <p></p>
 * <hr>
 * Copyright 2010 Medical Research Council Unit for Lifelong Health and Ageing
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  
 * <hr>
 * @author Kevin Garwood (kgarwood@users.sourceforge.net)
 * @version 1.0	
 */

/*
 * Code Road Map:
 * --------------
 * Code is organised into the following sections.  Wherever possible, 
 * methods are classified based on an order of precedence described in 
 * parentheses (..).  For example, if you're trying to find a method 
 * 'getName(...)' that is both an interface method and an accessor 
 * method, the order tells you it should appear under interface.
 * 
 * Order of 
 * Precedence     Section
 * ==========     ======
 * (1)            Section Constants
 * (2)            Section Properties
 * (3)            Section Construction
 * (7)            Section Accessors and Mutators
 * (6)            Section Errors and Validation
 * (5)            Section Interfaces
 * (4)            Section Overload
 *
 */

public class LoginDialog implements ActionListener {


	// ==========================================
	// Section Constants
	// ==========================================
	public static final int MAXIMUM_ATTEMPTS = 3;
	
	// ==========================================
	// Section Properties
	// ==========================================
	private SessionProperties sessionProperties;
	private int remainingAttempts;
	private UserInterfaceFactory userInterfaceFactory;
	private MacawCurationAPI database;
	
	private JDialog dialog;
	private JTextField userIDField;
	private JPasswordField passwordField;
	
	private JButton login;
	private JButton close;

	private boolean isCancelled;
	private boolean loginSuccessful;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public LoginDialog(SessionProperties sessionProperties,
					   int ithAttempt) {
		this.sessionProperties = sessionProperties;
		remainingAttempts = MAXIMUM_ATTEMPTS - ithAttempt;
			
		userInterfaceFactory = sessionProperties.getUserInterfaceFactory();
		database = (MacawCurationAPI) sessionProperties.getProperty(SessionProperties.DATABASE);
		
		
		dialog = userInterfaceFactory.createDialog();
		String title
			= MacawMessages.getMessage("loginDialog.title");
		dialog.setTitle(title);
		dialog.setModal(true);
	
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weightx = 1.0;
		panelGC.weighty = 1.0;
		panel.add(createInstructionPanel(), panelGC);
		panelGC.gridy++;
		panel.add(createMainFormPanel(), panelGC);
		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		panel.add(createButtonPanel(), panelGC);
		
		dialog.getContentPane().add(panel);
		dialog.setSize(340, 200);
		dialog.setResizable(false);
		
		WindowSizeListener windowSizeListener 
			= new WindowSizeListener();
		dialog.addComponentListener(windowSizeListener);
	}

	private JPanel createInstructionPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weightx = 1.0;
		panelGC.weighty = 1.0;
		
		String loginInstructions
			= MacawMessages.getMessage("loginDialog.instructions",
										String.valueOf(remainingAttempts));
		JTextArea textArea
			= userInterfaceFactory.createImmutableTextArea(3, 10);
		textArea.setText(loginInstructions);
		panel.add(textArea, panelGC);
		return panel;
	}
	
	private JPanel createMainFormPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		
		String userIDLabelText
			= MacawMessages.getMessage("user.userID.label");
		JLabel userIDLabel
			= userInterfaceFactory.createLabel(userIDLabelText);
		panel.add(userIDLabel, panelGC);

		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		userIDField = userInterfaceFactory.createTextField(10);
		panel.add(userIDField, panelGC);
				
		panelGC.gridy++;
		panelGC.gridx = 0;
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;
		String passwordFieldLabelText
			= MacawMessages.getMessage("user.password.label");
		JLabel passwordFieldLabel
			= userInterfaceFactory.createLabel(passwordFieldLabelText);
		panel.add(passwordFieldLabel, panelGC);
		
		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		passwordField = userInterfaceFactory.createPasswordField(10);
		panel.add(passwordField, panelGC);
		
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		
		String loginText
			= MacawMessages.getMessage("loginDialog.login");
		login = userInterfaceFactory.createButton(loginText);
		login.addActionListener(this);
		panel.add(login, panelGC);
		
		panelGC.gridx++;
		String closeText
			= MacawMessages.getMessage("general.buttons.close");
		close = userInterfaceFactory.createButton(closeText);
		close.addActionListener(this);
		panel.add(close, panelGC);
		
		return panel;		
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public void show() {
		dialog.setVisible(true);
	}
	
	public boolean isCancelled() {
		return isCancelled;
	}
	
	public boolean isLoginSuccessful() {
		return loginSuccessful;
	}
	
	private void login() {
		loginSuccessful = false;
		try {
			User user = new User();
			user.setUserID(userIDField.getText().trim());
			char[] password = passwordField.getPassword();
			user.setPassword(new String(password));
				
			database.checkValidUser(user);
			loginSuccessful = true;
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
			userIDField.setText("");
			passwordField.setText("");
			loginSuccessful = false;
		}
		dialog.setVisible(false);
	}
	
	private void close() {
		isCancelled = true;
		dialog.setVisible(false);
	}
	
	// ==========================================
	// Section Errors and Validation
	// ==========================================

	// ==========================================
	// Section Interfaces
	// ==========================================

	//Interface: ActionListener
	public void actionPerformed(ActionEvent event) {
		Object button = event.getSource();
		if (button == login) {
			login();
		}
		else if (button == close) {
			close();
		}
	}

	
	// ==========================================
	// Section Overload
	// ==========================================

}

