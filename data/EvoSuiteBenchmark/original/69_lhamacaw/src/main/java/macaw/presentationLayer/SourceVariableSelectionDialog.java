package macaw.presentationLayer;

import macaw.system.*;
import macaw.util.Displayable;
import macaw.util.DisplayableList;
import macaw.util.DisplayableListItemSelector;
import macaw.util.DisplayableListItemDeleter;
import macaw.businessLayer.*;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.ArrayList;

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

public class SourceVariableSelectionDialog implements ActionListener,
													  DisplayableListItemSelector,
													  DisplayableListItemDeleter {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private SessionProperties sessionProperties;
	private MacawCurationAPI database;
	private User currentUser;
	private SourceVariablesPanel sourceVariablesPanel;
	private UserInterfaceFactory userInterfaceFactory;
	private boolean isEditingCancelled;
	private ArrayList<Displayable> selectedItems;
	
	private JDialog dialog;
	private VariableSearchPanel variableSearchPanel;
	private String listOwnerName;
	private DisplayableList parentList;
	
	private JButton addSourceVariables;
	private JButton cancel;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public SourceVariableSelectionDialog(SessionProperties sessionProperties,
										 SourceVariablesPanel sourceVariablesPanel) {
		this.sessionProperties = sessionProperties;
		
		database = (MacawCurationAPI) sessionProperties.getProperty(SessionProperties.DATABASE);
		currentUser = (User) sessionProperties.getProperty(SessionProperties.CURRENT_USER);
		
		this.sourceVariablesPanel = sourceVariablesPanel;
		userInterfaceFactory = sessionProperties.getUserInterfaceFactory();
		isEditingCancelled = false;
		selectedItems = new ArrayList<Displayable>();
		
		String title
			= MacawMessages.getMessage("sourceVariableSelectionDialog.title");
		
		dialog
			= userInterfaceFactory.createDialog();
		dialog.setTitle(title);
		
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC = userInterfaceFactory.createGridBagConstraints();
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weightx = 100;
		panelGC.weighty = 100;
		
		variableSearchPanel = new VariableSearchPanel(sessionProperties);
		panel.add(variableSearchPanel.getPanel(), panelGC);
		
		panelGC.gridy++;
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		panelGC.fill = GridBagConstraints.NONE;
		panel.add(createButtonPanel(), panelGC);
		
		dialog.getContentPane().add(panel);
		dialog.setModal(true);
		dialog.setSize(500, 500);		
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC = userInterfaceFactory.createGridBagConstraints();
		
		String addSourceVariablesText
			= MacawMessages.getMessage("sourceVariableSelectionDialog.addSourceVariables");
		addSourceVariables
			= userInterfaceFactory.createButton(addSourceVariablesText);
		addSourceVariables.addActionListener(this);
		panel.add(addSourceVariables, panelGC);
		
		panelGC.gridx++;
		String cancelText
			= MacawMessages.getMessage("general.buttons.cancel");
		cancel = userInterfaceFactory.createButton(cancelText);
		cancel.addActionListener(this);
		panel.add(cancel, panelGC);

		return panel;
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	public void show() {
		dialog.setVisible(true);
	}
	
	public void addSourceVariables() {			
		try {
			DerivedVariable derivedVariable
				= sourceVariablesPanel.getData();
			
			ArrayList<Variable> sourceVariablesToAdd = variableSearchPanel.getSelectedVariables();
			database.associateSourceVariables(currentUser,
											  derivedVariable, 
											  sourceVariablesToAdd);
			dialog.setVisible(false);
			updateList();
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}
	}
			
	public void cancel() {
		isEditingCancelled = true;
		dialog.setVisible(false);
	}
	
	private void updateList() throws MacawException {
		DerivedVariable derivedVariable
			= sourceVariablesPanel.getData();
		ArrayList<Variable> sourceVariables
			= database.getSourceVariables(currentUser, derivedVariable);
				
		ArrayList<Displayable> displayableItems = new ArrayList<Displayable>();
		for (Variable currentSourceVariables : sourceVariables) {
			displayableItems.add((Displayable) currentSourceVariables);			
		}
		parentList.setDisplayItems(displayableItems);
	}

	// ==========================================
	// Section Errors and Validation
	// ==========================================

	// ==========================================
	// Section Interfaces
	// ==========================================

	//Interface: Action Listener
	public void actionPerformed(ActionEvent event) {
		Object button = event.getSource();
		
		if (button == addSourceVariables) {
			addSourceVariables();
		}
		else if (button == cancel) {
			cancel();
		}
	}

	//Interface: DisplayableListItemSelector	
	public void selectListItems(String listOwnerName,
						 		DisplayableList parentList) throws MacawException {
		this.listOwnerName = listOwnerName;
		this.parentList = parentList;
		
		show();
		if (isEditingCancelled() == false) {
			ArrayList<Variable> sourceVariablesToAdd
				= new ArrayList<Variable>();
			for (Displayable currentItem : selectedItems) {
				sourceVariablesToAdd.add((Variable) currentItem);
			}
			
			DerivedVariable derivedVariable
				= sourceVariablesPanel.getData();
			database.associateSourceVariables(currentUser, 
											  derivedVariable, 
											  sourceVariablesToAdd);
			updateList();			
		}
	}

	public void deleteListItems(String listOwnerName,
								DisplayableList parentList) throws MacawException {
		this.parentList = parentList;
		ArrayList<Displayable> listItemsToRemove
			= parentList.getSelectedItems();
		ArrayList<Variable> sourceVariablesToRemove = new ArrayList<Variable>();
		for (Displayable currentItemToRemove : listItemsToRemove) {
			sourceVariablesToRemove.add((Variable) currentItemToRemove);			
		}
		
		DerivedVariable derivedVariable
			= sourceVariablesPanel.getData();			
		database.disassociateSourceVariables(currentUser, 
									   		 derivedVariable, 
									   		 sourceVariablesToRemove);
		updateList();
	}
	
	public boolean isEditingCancelled() {
		return isEditingCancelled;
	}
	
	// ==========================================
	// Section Overload
	// ==========================================

}

