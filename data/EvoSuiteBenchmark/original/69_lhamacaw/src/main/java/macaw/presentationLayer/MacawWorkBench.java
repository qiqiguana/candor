package macaw.presentationLayer;


import macaw.persistenceLayer.demo.DemonstrationCurationService;
import macaw.persistenceLayer.demo.DemonstrationRetrievalService;
import macaw.persistenceLayer.production.ProductionCurationService;
import macaw.persistenceLayer.production.ProductionRetrievalService;
import macaw.system.SessionProperties;
import macaw.system.StartupOptions;
import macaw.system.UserInterfaceFactory;
import macaw.system.MacawMessages;
import macaw.businessLayer.User;
import macaw.io.ExportVariablesToMacawXML;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;

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

public class MacawWorkBench implements ActionListener {

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
		
			if (startupOptions.useDemo() == true) {
				DemonstrationCurationService database = new DemonstrationCurationService(false);
				DemonstrationRetrievalService demonstratinRetrievalService
					= new DemonstrationRetrievalService(sessionProperties);
				sessionProperties.setProperty(SessionProperties.RETRIEVAL_SERVICE,
											  demonstratinRetrievalService);
				sessionProperties.setProperty(SessionProperties.DATABASE, database);
			}
			else {
				ProductionCurationService database = new ProductionCurationService(sessionProperties);
				ProductionRetrievalService macawRetrievalAPI
					= new ProductionRetrievalService(sessionProperties);	
				sessionProperties.setProperty(SessionProperties.RETRIEVAL_SERVICE,
				  		  macawRetrievalAPI);
				sessionProperties.setProperty(SessionProperties.DATABASE, database);				
			}
			
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

			MacawWorkBench macawWorkBench 
				= new MacawWorkBench(sessionProperties);
			macawWorkBench.show();
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
	
	private JDialog dialog;
	private JButton editConstants;
	private JButton editVariables;
	private JButton exportVariableData;
	private JButton exit;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public MacawWorkBench(SessionProperties sessionProperties) {
		this.sessionProperties = sessionProperties;
		UserInterfaceFactory userInterfaceFactory
			= sessionProperties.getUserInterfaceFactory();
		dialog = userInterfaceFactory.createDialog();
		String dialogTitle
			= MacawMessages.getMessage("macawWorkBench.title");
		dialog.setTitle(dialogTitle);
		
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC 
			= userInterfaceFactory.createGridBagConstraints();

		panelGC.anchor = GridBagConstraints.NORTHWEST;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		JTextArea instructionsArea
			= userInterfaceFactory.createImmutableTextArea(3, 20);
		String instructionsText
			= MacawMessages.getMessage("macawWorkBench.instructions");
		instructionsArea.setText(instructionsText);
		instructionsArea.setBorder(LineBorder.createGrayLineBorder());
		panel.add(instructionsArea, panelGC);
		
		panelGC.gridy++;
		panelGC.anchor = GridBagConstraints.CENTER;
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;
		panel.add(createButtonPanel(userInterfaceFactory), 
				  panelGC);
		
		dialog.getContentPane().add(panel);
		dialog.setModal(true);
		dialog.setSize(300, 300);
	}
	
	private JPanel createButtonPanel(UserInterfaceFactory userInterfaceFactory) {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC 
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.ipady = 10;
		panelGC.insets = new Insets(2, 2, 2, 2);
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 1.0;
		
		String editConstantsText
			= MacawMessages.getMessage("macawWorkBench.editConstants");
		editConstants = userInterfaceFactory.createButton(editConstantsText);
		String editConstantsInformationText
			= MacawMessages.getMessage("macawWorkBench.editConstants.instructions");
		editConstants.setToolTipText(editConstantsInformationText);		
		editConstants.addActionListener(this);
		panel.add(editConstants, panelGC);
		
		panelGC.gridy++;
		String editVariablesText
			= MacawMessages.getMessage("macawWorkBench.editVariables");		
		editVariables = userInterfaceFactory.createButton(editVariablesText);
		editVariables.addActionListener(this);
		String editVariablesInformationText
			= MacawMessages.getMessage("macawWorkBench.editVariables.instructions");
		editVariables.setToolTipText(editVariablesInformationText);
		panel.add(editVariables, panelGC);
		
		panelGC.gridy++;
		String exportVariableText
			= MacawMessages.getMessage("macawWorkBench.exportVariableData");
		exportVariableData 
			= userInterfaceFactory.createButton(exportVariableText);
		exportVariableData.addActionListener(this);
		String exportVariableInformationText
			= MacawMessages.getMessage("macawWorkBench.exportVariableData.instructions");
		exportVariableData.setToolTipText(exportVariableInformationText);
		panel.add(exportVariableData, panelGC);
		
		panelGC.gridy++;
		String exitText
			= MacawMessages.getMessage("general.buttons.exit");
		exit = userInterfaceFactory.createButton(exitText);
		exit.addActionListener(this);
		panel.add(exit, panelGC);
		
		return panel;
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	public void show() {
		dialog.setVisible(true);
	}
	
	private void editConstants() {
		VariableConstantsEditor variableConstantsEditor
			= new VariableConstantsEditor(sessionProperties, true);
		variableConstantsEditor.show();
	}
	
	private void editVariables() {
		MacawVariableBrowser macawVariableBrowser 
			= new MacawVariableBrowser(sessionProperties);
		macawVariableBrowser.show();
	}
	
	private void exportVariableData() {
		/*
		ExportVariablesToMacawXML exportVariablesToMacawXML
			= new ExportVariablesToMacawXML(sessionProperties);
		exportVariablesToMacawXML.exportVariablesToMacawXMLFormat();
		*/
		ExportVariablesToMacawXML exportToDSSFormat
			= new ExportVariablesToMacawXML(sessionProperties);
		exportToDSSFormat.export();
	}
	
	private void exit() {
		System.exit(0);
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
		
		if (button == editConstants) {
			editConstants();
		}
		else if (button == editVariables) {
			editVariables();
		}
		else if (button == exportVariableData) {
			exportVariableData();
		}
		else {
			exit();
		}
	}

	// ==========================================
	// Section Overload
	// ==========================================

}

