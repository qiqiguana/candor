package macaw.presentationLayer;


import macaw.businessLayer.*;
import macaw.persistenceLayer.production.ProductionCurationService;
import macaw.system.*;
import macaw.util.WindowSizeListener;


import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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

public class MacawVariableBrowser implements ActionListener, 
											 ChangeListener {

	public static void main(String[] arguments) {

		String dbUser = null;
		String dbPassword = null;
		try {
			
			SessionProperties sessionProperties = new SessionProperties();
			StartupOptions startupOptions
				= (StartupOptions) sessionProperties.getProperty(SessionProperties.STARTUP_OPTIONS);			
			startupOptions.processCommandLineArguments(arguments);

			/*
			if (dbUser != null) {
				sessionProperties.setProperty(SessionProperties.DB_USER, dbUser);
				sessionProperties.setProperty(SessionProperties.DB_PASSWORD, dbPassword);
			}
			*/
			
			User currentUser = new User("jsmith", "cool");		
			sessionProperties.setProperty(SessionProperties.CURRENT_USER, currentUser);
		
			/*
			if (useDemo == true) {
				MacawDemonstrationDatabase database = new MacawDemonstrationDatabase(false);
				sessionProperties.setProperty(SessionProperties.DATABASE, database);				
			}
			else {
				MacawProductionDatabase database = new MacawProductionDatabase(sessionProperties);
				sessionProperties.setProperty(SessionProperties.DATABASE, database);
			}
			*/
			//if (startupOptions.useDemo() == true) {
			//	DemonstrationCurationService database = new DemonstrationCurationService(false);
			//	sessionProperties.setProperty(SessionProperties.DATABASE, database);
			//}
			//else {
				ProductionCurationService database = new ProductionCurationService(sessionProperties);
				sessionProperties.setProperty(SessionProperties.DATABASE, database);				
			//}
			
			//MacawProductionDatabase database = new MacawProductionDatabase(sessionProperties);
			//sessionProperties.setProperty(SessionProperties.DATABASE, database);
			
			int numberOfAttempts = 0;
			for (numberOfAttempts = 0; numberOfAttempts < 3; numberOfAttempts++) {			
				LoginDialog loginDialog 
					= new LoginDialog(sessionProperties,
									  numberOfAttempts);
				loginDialog.show();
				if (loginDialog.isLoginSuccessful() == true) {
					break;
				}
				else if (loginDialog.isCancelled() == true) {
					System.exit(0);
				}
			}
		
			if (numberOfAttempts == LoginDialog.MAXIMUM_ATTEMPTS) {
				System.exit(0);
			}

			MacawVariableBrowser macawVariableBrowser 
				= new MacawVariableBrowser(sessionProperties);
			macawVariableBrowser.show();
		}
		catch(Exception exception) {
			exception.printStackTrace(System.out);
		}
		

	}
	
	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================

	private SessionProperties sessionProperties;
	private UserInterfaceFactory userInterfaceFactory;
	
	private JDialog dialog;
	private VariableSearchPanel variableSearchPanel;
	
	private JButton addRawVariable;
	private JButton editRawVariable;
	private JButton deleteRawVariable;
	
	private JButton addDerivedVariable;
	private JButton editDerivedVariable;
	private JButton deleteDerivedVariable;
	
	private JButton close;
	
	private boolean useDemo;
	// ==========================================
	// Section Construction
	// ==========================================
	public MacawVariableBrowser(SessionProperties sessionProperties) {
		this.sessionProperties = sessionProperties;		
		userInterfaceFactory = sessionProperties.getUserInterfaceFactory();
			
		String title
			= MacawMessages.getMessage("macawVariableBrowser.title");
		
		dialog = userInterfaceFactory.createDialog();
		dialog.setTitle(title);
		
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC = userInterfaceFactory.createGridBagConstraints();
		panelGC.insets = new Insets(2, 2, 2, 2);
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		panel.add(createInstructionPanel(), panelGC);
		
		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weighty = 1.0;
		variableSearchPanel = new VariableSearchPanel(sessionProperties);
		variableSearchPanel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		panel.add(variableSearchPanel.getPanel(), panelGC);
		
		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		panelGC.weighty = 0;
		panel.add(createEditingButtonsPanel(), panelGC);
		
		panelGC.gridy++;
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;
		panel.add(createCloseButtonPanel(), panelGC);
		
		variableSearchPanel.setChangeListener(this);
		
		dialog.setSize(680, 505);
		dialog.getContentPane().add(panel);
		dialog.setResizable(false);
		dialog.setModal(true);
		
		WindowSizeListener windowSizeListener = new WindowSizeListener();
		dialog.addComponentListener(windowSizeListener);
		
		//to set the initial selection of buttons
		updateButtonStates();
	}

	private JPanel createInstructionPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC = userInterfaceFactory.createGridBagConstraints();
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		
		String instructionsText	
			= MacawMessages.getMessage("macawVariableBrowser.instructions");
		JTextArea instructionsTextArea
			= userInterfaceFactory.createImmutableTextArea(2, 30);
		instructionsTextArea.setText(instructionsText);
		
		panel.add(instructionsTextArea, panelGC);
		
		return panel;		
	}
	
	private JPanel createEditingButtonsPanel() {
		JPanel panel = userInterfaceFactory.createBorderLayoutPanel();
		JPanel rawVariableEditingButtonPanel
			= createRawVariableEditingButtonPanel();
		rawVariableEditingButtonPanel.setBorder(LineBorder.createGrayLineBorder());
		JPanel derivedVariableEditingButtonPanel
			= createDerivedVariableEditingButtonPanel();
		derivedVariableEditingButtonPanel.setBorder(LineBorder.createGrayLineBorder());
		
		panel.add(BorderLayout.WEST, rawVariableEditingButtonPanel);
		panel.add(BorderLayout.EAST, derivedVariableEditingButtonPanel);
		
		return panel;
	}
	
	private JPanel createRawVariableEditingButtonPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC = userInterfaceFactory.createGridBagConstraints();
		panelGC.insets = new Insets(2, 2, 2, 2);

		String rawVariableLabelText
			= MacawMessages.getMessage("macawVariableBrowser.rawVariable");
		JLabel rawVariableLabel
			= userInterfaceFactory.createLabel(rawVariableLabelText);
		panel.add(rawVariableLabel, panelGC);

		panelGC.gridx++;
		String addText
			= MacawMessages.getMessage("general.buttons.add");
		addRawVariable = userInterfaceFactory.createButton(addText);
		addRawVariable.addActionListener(this);
		panel.add(addRawVariable, panelGC);
		
		panelGC.gridx++;
		String editText
			= MacawMessages.getMessage("general.buttons.edit");
		editRawVariable = userInterfaceFactory.createButton(editText);
		editRawVariable.addActionListener(this);
		panel.add(editRawVariable, panelGC);
		
		panelGC.gridx++;
		String deleteText
			= MacawMessages.getMessage("general.buttons.delete");
		deleteRawVariable = userInterfaceFactory.createButton(deleteText);
		deleteRawVariable.addActionListener(this);
		panel.add(deleteRawVariable, panelGC);	
		
		return panel;
	}
	
	private JPanel createDerivedVariableEditingButtonPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC = userInterfaceFactory.createGridBagConstraints();
		panelGC.ipadx = 2;
		panelGC.insets = new Insets(2, 2, 2, 2);
		
		String derivedVariableLabelText
			= MacawMessages.getMessage("macawVariableBrowser.derivedVariable");
		JLabel derivedVariableLabel
			= userInterfaceFactory.createLabel(derivedVariableLabelText);
		panel.add(derivedVariableLabel, panelGC);
		
		panelGC.gridx++;
		String addText
			= MacawMessages.getMessage("general.buttons.add");
		addDerivedVariable = userInterfaceFactory.createButton(addText);
		addDerivedVariable.addActionListener(this);
		panel.add(addDerivedVariable, panelGC);
		
		panelGC.gridx++;
		String editText
			= MacawMessages.getMessage("general.buttons.edit");
		editDerivedVariable = userInterfaceFactory.createButton(editText);
		editDerivedVariable.addActionListener(this);
		panel.add(editDerivedVariable, panelGC);
		
		panelGC.gridx++;
		String deleteText
			= MacawMessages.getMessage("general.buttons.delete");
		deleteDerivedVariable = userInterfaceFactory.createButton(deleteText);
		deleteDerivedVariable.addActionListener(this);
		panel.add(deleteDerivedVariable, panelGC);	
		
		return panel;
	}

	private JPanel createCloseButtonPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC = userInterfaceFactory.createGridBagConstraints();
		panelGC.anchor = GridBagConstraints.SOUTHEAST;

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
	
	private void addRawVariable() {
		
		RawVariableEditor rawVariableEditor
			= new RawVariableEditor(sessionProperties, true);

		RawVariable rawVariable = new RawVariable();
		rawVariable.setIsNewRecord(true);
		rawVariableEditor.setData(rawVariable, false);	
		rawVariableEditor.show();
		if (rawVariableEditor.isCancelled() == false) {
			variableSearchPanel.refreshList();			
		}
		variableSearchPanel.selectVariable(rawVariable);
	}
	
	private void editRawVariable() {

		try {
			 RawVariable selectedRawVariable
			 	= (RawVariable) variableSearchPanel.getSelectedVariable();
			 
			 RawVariableEditor rawVariableEditor
			 	= new RawVariableEditor(sessionProperties, true);
			 rawVariableEditor.setData(selectedRawVariable, false);
			 rawVariableEditor.show();		
			 if (rawVariableEditor.isCancelled() == false) {
				 
			 }			
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}
	}

	private void deleteRawVariable() {
		String dialogTitle
			= MacawMessages.getMessage("general.warning.title");
		String warningMessage
			= MacawMessages.getMessage("general.warning.deletionIsPermanent");

		int result = JOptionPane.showConfirmDialog(null,
												   warningMessage,
												   dialogTitle,
												   JOptionPane.YES_NO_OPTION,
												   JOptionPane.WARNING_MESSAGE);
		if (result != JOptionPane.YES_OPTION) {
			return;
		}

		try {
			variableSearchPanel.deleteSelectedItems();			
			variableSearchPanel.refreshList();			
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}
	}

	private void addDerivedVariable() {
		try {			
			DerivedVariableEditor derivedVariableEditor
				= new DerivedVariableEditor(sessionProperties, true);

			DerivedVariable derivedVariable = new DerivedVariable();
			derivedVariable.setIsNewRecord(true);
			derivedVariableEditor.setData(derivedVariable, false);	
			derivedVariableEditor.show();
			variableSearchPanel.refreshList();			
			variableSearchPanel.selectVariable(derivedVariable);		
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}
	}

	private void editDerivedVariable() {
		try {
			 DerivedVariable selectedDerivedVariable
			 	= (DerivedVariable) variableSearchPanel.getSelectedVariable();
			 DerivedVariableEditor derivedVariableEditor
			 	= new DerivedVariableEditor(sessionProperties, true);
			 derivedVariableEditor.setData(selectedDerivedVariable, false);
			 derivedVariableEditor.show();		
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}		
	}

	private void deleteDerivedVariable() {
		String dialogTitle
			= MacawMessages.getMessage("general.warning.title");
		String warningMessage
			= MacawMessages.getMessage("general.warning.deletionIsPermanent");

		int result = JOptionPane.showConfirmDialog(null,
												   warningMessage,
												   dialogTitle,
												   JOptionPane.YES_NO_OPTION,
												   JOptionPane.WARNING_MESSAGE);
		if (result != JOptionPane.YES_OPTION) {
			return;
		}

		try {
			variableSearchPanel.deleteSelectedItems();	
			variableSearchPanel.refreshList();			
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}
	}
	
	private void editVariableConstants() {
		VariableConstantsEditor variableConstantsEditor
			= new VariableConstantsEditor(sessionProperties, true);
		variableConstantsEditor.show();
	}
	

	private void close() {
		dialog.setVisible(false);
	}	
	
	// ==========================================
	// Section Errors and Validation
	// ==========================================

	// ==========================================
	// Section Interfaces
	// ==========================================

	public void actionPerformed(ActionEvent event) {
		Object button = event.getSource();
		
		if (button == addRawVariable) {
			addRawVariable();
		}
		else if (button == editRawVariable) {
			editRawVariable();
		}
		else if (button == deleteRawVariable) {
			deleteRawVariable();
		}
		else if (button == addDerivedVariable) {
			addDerivedVariable();
		}
		else if (button == editDerivedVariable) {
			editDerivedVariable();
		}
		else if (button == deleteDerivedVariable) {
			deleteDerivedVariable();
		}
		else if (button == close) {
			close();
		}
	}

	//Interface: ChangeListener
	
	public void stateChanged(ChangeEvent event) {
		updateButtonStates();
	}
	
	private void updateButtonStates() {
		 //determine the state of the add buttons
		 if (variableSearchPanel.showRawVariables() == true) {
			 addRawVariable.setEnabled(true);					 
		 }
		 else {
			 addRawVariable.setEnabled(false);					 
		 }
		 
		 if (variableSearchPanel.showDerivedVariables() == true) {
			 addDerivedVariable.setEnabled(true);					 
		 }
		 else {
			 addDerivedVariable.setEnabled(false);					 
		 }
		
		 //edit and delete buttons tied to selected item
		 try {
			 Variable selectedVariable
			 	= variableSearchPanel.getSelectedVariable();
			 if (selectedVariable == null) {	 
				 //no item selected - desensitise all edit,delete buttons
				 editRawVariable.setEnabled(false);
				 deleteRawVariable.setEnabled(false);
				 editDerivedVariable.setEnabled(false);
				 deleteDerivedVariable.setEnabled(false);				 
			 }
			 else if (selectedVariable instanceof RawVariable) {
				 editRawVariable.setEnabled(true);
				 deleteRawVariable.setEnabled(true);				 
				 editDerivedVariable.setEnabled(false);
				 deleteDerivedVariable.setEnabled(false);				 
			 }
			 else if (selectedVariable instanceof DerivedVariable) {
				 editRawVariable.setEnabled(false);
				 deleteRawVariable.setEnabled(false);				 
				 editDerivedVariable.setEnabled(true);
				 deleteDerivedVariable.setEnabled(true);				 
			 }					 
		 }
		 catch(MacawException exception) {
			 Log log = sessionProperties.getLog();
			 log.displayErrorDialog(exception);
		 }	 
	}
	
	// ==========================================
	// Section Overload
	// ==========================================

}

