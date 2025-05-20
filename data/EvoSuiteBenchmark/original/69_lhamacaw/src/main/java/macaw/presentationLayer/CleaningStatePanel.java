package macaw.presentationLayer;

import macaw.system.*;
import macaw.businessLayer.User;
import macaw.businessLayer.Variable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

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

public class CleaningStatePanel implements ActionListener {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private boolean allowWriteAccess;
	
	private JPanel panel;

	private UserInterfaceFactory userInterfaceFactory;
	private String currentUserID;
	
	private ArrayList<MacawChangeEvent> changeEvents;
	
	private JCheckBox isCleaned;
	private JComboBox cleaningStatusChoices;
	private JTextArea cleaningDescriptionField;
	
	private boolean oldIsCleaned;
	private String oldCleaningStatusChoice;
	private String oldCleaningDescription;

	private Variable variable;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public CleaningStatePanel(SessionProperties sessionProperties,
							  String[] cleaningStates,
							  boolean allowWriteAccess) {
	
		this.allowWriteAccess = allowWriteAccess;
		this.userInterfaceFactory = sessionProperties.getUserInterfaceFactory();
		User currentUser 
			= (User) sessionProperties.getProperty(SessionProperties.CURRENT_USER);
		currentUserID = currentUser.getUserID();

		changeEvents 
			= new ArrayList<MacawChangeEvent>();

		String isCleanedText
			= MacawMessages.getMessage("variable.isCleaned.label");
		isCleaned = userInterfaceFactory.createCheckBox(isCleanedText);
		isCleaned.setEnabled(allowWriteAccess);
		
		panel = createCleaningDescriptionPanel(cleaningStates);
		panel.setBorder(LineBorder.createGrayLineBorder());
		
		isCleaned.setSelected(false);

		if (allowWriteAccess == true) {
			isCleaned.addActionListener(this);
		}		
		setEnabled(false);
	}
	
	private JPanel createCleaningDescriptionPanel(String[] cleaningStates) {
		JPanel panel = userInterfaceFactory.createPanel();
		
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 100;
		panelGC.insets = new Insets(2, 20, 2, 20);
		String cleaningStatusText
			= MacawMessages.getMessage("variable.cleaningStatus.label");
		JLabel cleaningStatusLabel
			= userInterfaceFactory.createLabel(cleaningStatusText);
		panel.add(cleaningStatusLabel, panelGC);
		panelGC.gridx++;
				
		cleaningStatusChoices
			= userInterfaceFactory.createComboBox(cleaningStates);
		cleaningStatusChoices.setEnabled(allowWriteAccess);
		panel.add(cleaningStatusChoices, panelGC);
		
		panelGC.gridy++;
		panelGC.gridx = 0;
		String cleaningDescriptionText
			= MacawMessages.getMessage("variable.cleaningDescription.label");
		JLabel cleaningDescriptionLabel
			= userInterfaceFactory.createLabel(cleaningDescriptionText);
		panel.add(cleaningDescriptionLabel, panelGC);
		
		panelGC.gridx++;
		cleaningDescriptionField = userInterfaceFactory.createTextArea(3, 30);
		cleaningDescriptionField.setEditable(allowWriteAccess);
		JScrollPane scrollPane
			= userInterfaceFactory.createScrollPane(cleaningDescriptionField);
		panel.add(scrollPane, panelGC);
		
		return panel;
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
		
	public boolean isCleaned() {
		return isCleaned.isSelected();
	}
	
	public JCheckBox getCheckBox() {
		return isCleaned;
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public void initialise(Variable variable) {
		this.variable = variable;
		setEnabled(variable.isCleaned());
		oldIsCleaned = variable.isCleaned();
		oldCleaningStatusChoice = variable.getCleaningStatus();		
		if (oldCleaningStatusChoice.equals("") == true) {
			oldCleaningStatusChoice 
				= MacawMessages.getMessage("general.fieldValue.unknown");
		}
		
		oldCleaningDescription = variable.getCleaningDescription();		
	}
	
	public String getCleaningStatus() {
		return oldCleaningStatusChoice;
	}
	
	public String getCleaningDescription() {
		return oldCleaningDescription;
	}
	
	public void save() {
		oldCleaningStatusChoice
			= (String) cleaningStatusChoices.getSelectedItem();
		oldCleaningDescription
			= cleaningDescriptionField.getText().trim();
		changeEvents.clear();
	}
	
	public void restore() {
		setEnabled(oldIsCleaned);
		if (oldIsCleaned == true) {
			if (allowWriteAccess == true) {
				isCleaned.removeActionListener(this);
			}
			isCleaned.setSelected(true);
			if (allowWriteAccess == true) {
				isCleaned.addActionListener(this);
			}
			
			cleaningStatusChoices.setSelectedItem(oldCleaningStatusChoice);
			cleaningDescriptionField.setText(oldCleaningDescription);
		}
		changeEvents.clear();
	}
		
	public void clearChanges() {
		changeEvents.clear();
	}
	
	public ArrayList<MacawChangeEvent> getChangeEvents() {		
		if (isCleaned.isSelected() != oldIsCleaned) {
			
			if (isCleaned.isSelected() == false) {
				//user has deselected it; we only care to report
				//change that record is no longer assessed for cleaning.
				//cleaning description and cleaning status should be
				//blank and irrelevant
				String changedToNotCleaned
					= MacawMessages.getMessage("variable.isCleaned.saveChanges.changedToNotClean",
												variable.getDisplayName());
				MacawChangeEvent changeEvent
					= new MacawChangeEvent(ChangeEventType.VARIABLE,
										   changedToNotCleaned, 
										   currentUserID);
				changeEvent.setVariableOwnerID(variable.getIdentifier());
				changeEvent.setChangedObjectIdentifier(variable.getIdentifier());
				changeEvents.add(changeEvent);
			}
			else {
				String newCleaningStatusChoice
					= (String) cleaningStatusChoices.getSelectedItem();
				if (oldCleaningStatusChoice.equals(newCleaningStatusChoice) == false) {
					
				}

				String newCleaningDescription
					= cleaningDescriptionField.getText().trim();
				if (oldCleaningDescription.equals(newCleaningDescription) == false) {
					
				}			
			}
		}
		
		return changeEvents;
	}
	
	// ==========================================
	// Section Errors and Validation
	// ==========================================

	// ==========================================
	// Section Interfaces
	// ==========================================

	//Interface: Action Listener
	public void actionPerformed(ActionEvent event) {
		//this can only be the check box
		setEnabled(isCleaned.isSelected());
	}
	
	private void setEnabled(boolean isEnabled) {
		if (isEnabled == true) {
			cleaningStatusChoices.setEnabled(true);
			cleaningDescriptionField.setEnabled(true);								
		}
		else {
			cleaningStatusChoices.setEnabled(false);
			String unknownStatus
				= MacawMessages.getMessage("general.fieldValue.unknown");
			cleaningStatusChoices.setSelectedItem(unknownStatus);
			cleaningDescriptionField.setText("");
			cleaningDescriptionField.setEnabled(false);					
		}
	}
	
	// ==========================================
	// Section Overload
	// ==========================================

}

