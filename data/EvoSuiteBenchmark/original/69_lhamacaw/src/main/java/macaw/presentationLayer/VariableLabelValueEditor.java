package macaw.presentationLayer;

import macaw.system.*;
import macaw.util.OKClosePanel;
import macaw.businessLayer.*;

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

public class VariableLabelValueEditor implements ActionListener {
	
	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private SessionProperties sessionProperties;
	private User currentUser;
	private UserInterfaceFactory userInterfaceFactory;
	private MacawCurationAPI database;
	private JDialog dialog;
	

	private VariableLabelTable variableLabelTable;
	private JButton addLabelValue;
	private JButton deleteLabelValue;

	
	private OKClosePanel okCancelPanel;
	private JButton save;
	private JButton cancel;
	private JButton close;
	
	private Variable variable;
	
	// ==========================================
	// Section Construction
	// ==========================================
	
	public VariableLabelValueEditor(SessionProperties sessionProperties,
									boolean allowWriteAccess) {
		this.sessionProperties = sessionProperties;
		database
			= (MacawCurationAPI) sessionProperties.getProperty(SessionProperties.DATABASE);
			
		userInterfaceFactory
			= sessionProperties.getUserInterfaceFactory();
		
		currentUser 
			= (User) sessionProperties.getProperty(SessionProperties.CURRENT_USER);
		dialog = userInterfaceFactory.createDialog();
		if (allowWriteAccess == true) {
			String title
				= MacawMessages.getMessage("variableLabelValueEditor.title");
			dialog.setTitle(title);
		}
		else {
			String title
				= MacawMessages.getMessage("variableLabelValueEditor.title.readOnly");
			dialog.setTitle(title);			
		}
			
		dialog.setModal(true);

		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC = userInterfaceFactory.createGridBagConstraints();

		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weightx = 100;
		panelGC.weighty = 100;		
		variableLabelTable 
			= new VariableLabelTable(database,
									 allowWriteAccess);		
		JScrollPane tableScrollPane 
			= userInterfaceFactory.createScrollPane(variableLabelTable);
		panel.add(tableScrollPane, panelGC);
		
		panelGC.gridy++;
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;		
		panelGC.weighty = 0;
		
		String addLabelValueText
			= MacawMessages.getMessage("general.buttons.add");
		addLabelValue = userInterfaceFactory.createButton(addLabelValueText);
		addLabelValue.setEnabled(allowWriteAccess);
		
		String deleteLabelValueText
			= MacawMessages.getMessage("general.buttons.delete");
		deleteLabelValue = userInterfaceFactory.createButton(deleteLabelValueText);
		deleteLabelValue.setEnabled(allowWriteAccess);
		
		okCancelPanel
			= new OKClosePanel(userInterfaceFactory, 
										 this);
		okCancelPanel.addButton(addLabelValue);
		okCancelPanel.addButton(deleteLabelValue);

		
		save = okCancelPanel.getSaveButton();
		close = okCancelPanel.getCloseButton();

		okCancelPanel.setAllowWriteAccess(allowWriteAccess);
		
		panel.add(okCancelPanel.getPanel(), panelGC);
		
		dialog.getContentPane().add(panel);
		dialog.setSize(400, 300);
		dialog.setModal(true);
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	
	
	public void setData(Variable variable) throws MacawException {
		this.variable = variable;
		variableLabelTable.setData(currentUser,
								   variable);
	}
	
	public void show() {
		dialog.setVisible(true);
	}
	
	public boolean isCancelled() {
		return isCancelled();
	}
	
	private void addLabelValue() {
		variableLabelTable.addValueLabel();
	}

	private void deleteLabelValue() {
		variableLabelTable.deleteValueLabel();
	}

	private void save() {
		try {
			variableLabelTable.save();		
			dialog.setVisible(false);			
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}
	}
	
	private void close() {
		variableLabelTable.cancel();
		dialog.setVisible(false);
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
		
		if (button == addLabelValue) {
			addLabelValue();
		}
		else if (button == deleteLabelValue) {
			deleteLabelValue();
		}
		else if (button == save) {
			save();
		}
		else if (button == close) {
			close();
		}
	}
	
	// ==========================================
	// Section Overload
	// ==========================================

}

