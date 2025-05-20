package macaw.presentationLayer;

import macaw.system.*;
import macaw.businessLayer.Variable;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

/**
 * manages the UI for selecting an alternative variable
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

public class AlternativeVariableSelectionPanel implements ActionListener {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	
	private SessionProperties sessionProperties;
	private UserInterfaceFactory userInterfaceFactory;
	
	private JTextField selectedVariableNameField;
	private JButton clearAlternativeVariable;
	private JButton selectAlternativeVariable;
	
	private JPanel panel;
	
	private Variable alternativeVariable;
	
	
	// ==========================================
	// Section Construction
	// ==========================================
	public AlternativeVariableSelectionPanel(SessionProperties sessionProperties) {
		this.sessionProperties = sessionProperties;
		userInterfaceFactory
			= sessionProperties.getUserInterfaceFactory();
		
		panel = createPanel();
		
		alternativeVariable = null;		
	}
	
	private JPanel createPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC 
			= userInterfaceFactory.createGridBagConstraints();

		String alternativeVariableText
			= MacawMessages.getMessage("variable.alternativeVariable.label");
		JLabel label
			= userInterfaceFactory.createLabel(alternativeVariableText);
		panel.add(label, panelGC);
		
		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;		
		selectedVariableNameField
			= userInterfaceFactory.createImmutableTextField(20);
		panel.add(selectedVariableNameField, panelGC);
		
		panelGC.gridx++;		
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;		
	
		panelGC.gridx++;		
		String selectAlternativeText
			= MacawMessages.getMessage("variable.selectAlternativeVariable");
		selectAlternativeVariable
			= userInterfaceFactory.createButton(selectAlternativeText);
		selectAlternativeVariable.addActionListener(this);
		panel.add(selectAlternativeVariable, panelGC);
		
		panelGC.gridx++;		
		String clearText
			= MacawMessages.getMessage("general.buttons.clear");
		clearAlternativeVariable
			= userInterfaceFactory.createButton(clearText);
		clearAlternativeVariable.addActionListener(this);
		panel.add(clearAlternativeVariable, panelGC);

		return panel;
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public JPanel getPanel() {
		return panel;
	}
	
	public Variable getAlternativeVariable() {
		return alternativeVariable;
	}
	
	public void setData(Variable targetVariable) {
		alternativeVariable = targetVariable.getAlternativeVariable();
		updateDisplay();
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
		
		if (button == selectAlternativeVariable) {
			selectAlternativeVariable();
		}
		else if (button == clearAlternativeVariable) {
			clearAlternativeVariable();			
		}
	}

	private void selectAlternativeVariable() {
		VariableSelectionDialog dialog
			= new VariableSelectionDialog(sessionProperties);
		dialog.show();
		
		if (dialog.isCancelled() == false) {
			alternativeVariable = dialog.getAlternativeVariable();
			updateDisplay();
		}
	}

	private void clearAlternativeVariable() {
		alternativeVariable = null;
		updateDisplay();
	}

	private void updateDisplay() {
		if (alternativeVariable == null) {
			clearAlternativeVariable.setEnabled(false);
			selectedVariableNameField.setText("");
		}
		else {
			clearAlternativeVariable.setEnabled(true);
			selectedVariableNameField.setText(alternativeVariable.getDisplayName());
		}
	}
	
	// ==========================================
	// Section Overload
	// ==========================================

}

