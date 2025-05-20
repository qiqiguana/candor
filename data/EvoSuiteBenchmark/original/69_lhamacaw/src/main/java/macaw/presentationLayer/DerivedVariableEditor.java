package macaw.presentationLayer;

import macaw.businessLayer.*;
import macaw.system.*;

import macaw.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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

public class DerivedVariableEditor extends VariableEditor implements ActionListener {	
	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	
	private DerivedVariable derivedVariable;	
	private SourceVariablesPanel sourceVariablesPanel;

	// ==========================================
	// Section Construction
	// ==========================================
	public DerivedVariableEditor(SessionProperties sessionProperties,
								 boolean allowWriteAccess) throws MacawException {
		
		super(sessionProperties, allowWriteAccess);	
		
		if (allowWriteAccess == true) {
			String title
				= MacawMessages.getMessage("derivedVariableLabelEditor.title");
			dialog.setTitle(title);
		}
		else {
			String title
				= MacawMessages.getMessage("derivedVariableLabelEditor.title.readOnly");
			dialog.setTitle(title);			
		}
				
		JPanel panel
			= userInterfaceFactory.createPanel();
		GridBagConstraints panelGC	
			= userInterfaceFactory.createGridBagConstraints();	
		panelGC.insets = new Insets(2, 10, 2, 10);

		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		panel.add(instructionsTextArea, panelGC);
		
		panelGC.gridy++;
		panel.add(createUpperPanel(), panelGC);
		
		panelGC.gridy++;
		panel.add(isCodedField, panelGC);

		panelGC.fill = GridBagConstraints.BOTH;

		panelGC.gridy++;
		panelGC.weightx = 1.0;
		panelGC.weighty = 0.2;
		panel.add(createIsCleanedPanel(), panelGC);
		
		panelGC.gridy++;				

		panelGC.weighty = 0.2;
		sourceVariablesPanel 
			= new SourceVariablesPanel(sessionProperties, 
									   this,
									   allowWriteAccess);
		panel.add(sourceVariablesPanel.getPanel(), panelGC);

		panelGC.gridy++;
		panel.add(ontologyTermsPanel.getPanel(), panelGC);
		panelGC.gridy++;		
		panel.add(supportingDocumentsPanel.getPanel(), panelGC);
		
		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weightx = 1.0;
		panel.add(createLowerPanel(), panelGC);
		
		//add the save, restore, cancel buttons
		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;
		panelGC.weighty = 0;
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		
		
		okCancelPanel.buildUI();
		panel.add(okCancelPanel.getPanel(), panelGC);
		
		
		dialog.getContentPane().add(panel);
		dialog.setSize(640, 800);
		dialog.setModal(true);
		
	}
	
	private JPanel createUpperPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC	
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.insets = new Insets(2, 2, 2, 2);
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		
		//panelGC.fill = GridBagConstraints.HORIZONTAL;
		//panelGC.weightx = 100;
		panel.add(createYearFormQuestionNumberPanel(), panelGC);
		
		panelGC.gridy++;
		panel.add(createNameLabelPanel(), panelGC);

		panelGC.gridy++;
		addField(categoryLabelText,
				 categoryChoicesField,
				 panel,
				 panelGC);		

		return panel;
	}

	private JPanel createLowerPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC	
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.insets = new Insets(2, 2, 2, 2);
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
			
		//add the availability field
		panelGC.gridy++;
		addField(availabilityLabelText,
				 availabilityChoicesField,
				 panel,
				 panelGC);		

		panelGC.gridy++;
		panelGC.weighty = 0;
		panel.add(createRubricCardPanel(), panelGC);
		
		panelGC.gridy++;
		panel.add(createDataLibraryAndFilePanel(), panelGC);
		
		panelGC.gridy++;
		panel.add(alternativeVariableSelectionPanel.getPanel(), panelGC);
		
		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weighty = 1.0;
		panel.add(createNotesFieldPanel(), panelGC);
		return panel;
	}

	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	public void setData(DerivedVariable derivedVariable, boolean isNewRecord) {
		this.derivedVariable = derivedVariable;
		super.setData(derivedVariable, isNewRecord);
		restore();
		sourceVariablesPanel.setNewRecord(derivedVariable);
		ontologyTermsPanel.setNewRecord(derivedVariable);
		supportingDocumentsPanel.setNewRecord(derivedVariable);	
		updateValueLabelsButtonState(derivedVariable);
	}

	public Displayable getData() {
		return derivedVariable;
	}
		
	private void save() {		
		try {
			commitChanges();	
			dialog.setVisible(false);
		}
		catch(MacawException exception) {
			log.displayErrorDialog(exception);
		}	
	}
	
	private void restore() {
		super.restoreVariableFields(derivedVariable);
		sourceVariablesPanel.setData(derivedVariable);	
		updateValueLabelsButtonState(derivedVariable);
	}
		
	// ==========================================
	// Section Errors and Validation
	// ==========================================
		
	// ==========================================
	// Section Interfaces
	// ==========================================

	//Interface: Action Listener
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if (source == editLabelValues) {
			editLabelValues();
		}
		else if (source == showChangeHistory) {
			showChangeHistory();
		}
		if (source == save) {
			save();
		}
		else if (source == close) {
			close();
		}
		else if (source == aliasFilePathChoicesField) {
			updateFilePath();
		}		
	}

	//Interface: DisplayableListParentForm
	public void commitChanges() throws MacawException {
		super.saveVariableFields(derivedVariable);

		if (derivedVariable.isNewRecord() == true) {
			database.addDerivedVariable(currentUser, derivedVariable);
		}
		else {
			database.updateDerivedVariable(currentUser, derivedVariable);
		}
		
		Variable alternativeVariable
			= derivedVariable.getAlternativeVariable();
		database.setAlternativeVariable(currentUser, 
										derivedVariable, 
										alternativeVariable);
		
		ontologyTermsPanel.setNewRecord(derivedVariable);
		supportingDocumentsPanel.setNewRecord(derivedVariable);
	}

	
	// ==========================================
	// Section Overload
	// ==========================================

}

