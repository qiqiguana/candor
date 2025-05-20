package macaw.presentationLayer;

import macaw.system.*;
import macaw.util.*;
import macaw.businessLayer.RawVariable;
import macaw.businessLayer.Variable;

import java.awt.event.ActionEvent;

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

public class RawVariableEditor extends VariableEditor {

	// ==========================================
	// Section Constants
	// ==========================================
	
	// ==========================================
	// Section Properties
	// ==========================================
	
	private RawVariable rawVariable;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public RawVariableEditor(SessionProperties sessionProperties, 
							 boolean allowWriteAccess) {
		super(sessionProperties, allowWriteAccess);
		
		UserInterfaceFactory userInterfaceFactory
			= sessionProperties.getUserInterfaceFactory();
		
		okCancelPanel.buildUI();

		if (allowWriteAccess == true) {
			String title
				= MacawMessages.getMessage("rawVariableLabelEditor.title");
			dialog.setTitle(title);
		}
		else {
			String title
				= MacawMessages.getMessage("rawVariableLabelEditor.title.readOnly");
			dialog.setTitle(title);			
		}
		
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weightx = 1.0;
		panelGC.weighty = 1.0;
		panelGC.insets = new Insets(2, 2, 2, 2);
		
		JPanel panel 
			= userInterfaceFactory.createPanel();
		panel.add(createMainFormPanel(), panelGC);

		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;
		panelGC.weighty = 0;
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		
		panelGC.gridy++;
		

		panel.add(okCancelPanel.getPanel(), panelGC);
				
		dialog.getContentPane().add(panel);
		
		WindowSizeListener windowSizeListener
			= new WindowSizeListener();
		dialog.addComponentListener(windowSizeListener);
		
		dialog.setModal(true);
		dialog.setSize(680, 770);
		//dialog.setResizable(false);
		
	}
	
	private JPanel createMainFormPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.insets = new Insets(2, 0, 2, 0);
		panelGC.anchor = GridBagConstraints.NORTHWEST;

		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		panel.add(instructionsTextArea, panelGC);
		
		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		panelGC.weighty = 0;

		panel.add(createYearFormQuestionNumberPanel(), panelGC);
		panelGC.gridy++;
		
		panel.add(createNameLabelPanel(), panelGC);

		//add in category
		panelGC.gridy++;
		addField(categoryLabelText,
				 categoryChoicesField,
				 panel,
				 panelGC);		

		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weighty = 0.2;
		panel.add(createIsCleanedPanel(), panelGC);

		panelGC.gridy++;
		panel.add(isCodedField, panelGC);		
		
		panelGC.gridy++;
		panel.add(ontologyTermsPanel.getPanel(), panelGC);
		
		panelGC.gridy++;
		panel.add(supportingDocumentsPanel.getPanel(), panelGC);

		panelGC.gridy++;
		panel.add(createRubricCardPanel(), panelGC);

		//add the availability field
		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		panelGC.weighty = 0;
		addField(availabilityLabelText,
				 availabilityChoicesField,
				 panel,
				 panelGC);		


		//add the card number field		
		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		panel.add(createDataLibraryAndFilePanel(), panelGC);

		panelGC.gridy++;
		panel.add(alternativeVariableSelectionPanel.getPanel(), panelGC);
		
		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weighty = 0.2;
		panel.add(createNotesFieldPanel(), panelGC);
		
		
		return panel;
	}
			
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	public void setData(RawVariable rawVariable, boolean isNewRecord) {
		this.rawVariable = rawVariable;
		super.setData(rawVariable, isNewRecord);	

		super.restoreVariableFields(rawVariable);
		
		isCodedField.setSelected(rawVariable.isCoded());
		
		ontologyTermsPanel.setNewRecord(rawVariable);
		supportingDocumentsPanel.setNewRecord(rawVariable);	
		updateValueLabelsButtonState(rawVariable);
	}

	public Displayable getData() {
		return rawVariable;
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

	// ==========================================
	// Section Errors and Validation
	// ==========================================

	// ==========================================
	// Section Interfaces
	// ==========================================

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if (source == editLabelValues) {
			editLabelValues();
		}
		else if (source == showChangeHistory) {
			showChangeHistory();
		}
		else if (source == save) {
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
		super.saveVariableFields(rawVariable);
		
		if (rawVariable.isNewRecord() == true) {
			database.addRawVariable(currentUser, rawVariable);
			rawVariable.setIsNewRecord(false);
		}
		else {
			database.updateRawVariable(currentUser, rawVariable);
		}	
		
		Variable alternativeVariable
			= rawVariable.getAlternativeVariable();
		database.setAlternativeVariable(currentUser, 
										rawVariable, 
										alternativeVariable);
		
		ontologyTermsPanel.setNewRecord(rawVariable);
		supportingDocumentsPanel.setNewRecord(rawVariable);
		updateValueLabelsButtonState(rawVariable);
	}

	// ==========================================
	// Section Overload
	// ==========================================

}

