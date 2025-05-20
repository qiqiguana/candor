package macaw.presentationLayer;

import macaw.system.*;
import macaw.businessLayer.Variable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;


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

public class VariableSelectionDialog implements ActionListener {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private SessionProperties sessionProperties;
	private UserInterfaceFactory userInterfaceFactory;
	
	private VariableSearchPanel variableSearchPanel;
	
	private JDialog dialog;

	private JButton ok;
	private JButton cancel;

	private boolean isCancelled;
	private Log log;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public VariableSelectionDialog(SessionProperties sessionProperties) {
		this.sessionProperties = sessionProperties;
		this.userInterfaceFactory 
			= sessionProperties.getUserInterfaceFactory();
		
		dialog = userInterfaceFactory.createDialog();
		
		String dialogTitle
			= MacawMessages.getMessage("variableSelectionDialog.title");
		dialog.setTitle(dialogTitle);
	
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC 
			= userInterfaceFactory.createGridBagConstraints();
		
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weightx = 1.0;
		panelGC.weighty = 1.0;
		variableSearchPanel = new VariableSearchPanel(sessionProperties);
		panel.add(variableSearchPanel.getPanel(), panelGC);
		
		
		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;
		panelGC.weighty = 0;
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		panel.add(createButtonPanel(), panelGC);
		
		dialog.getContentPane().add(panel);
		dialog.setModal(true);
		dialog.setSize(300, 300);
		
		isCancelled = false;
		this.log = sessionProperties.getLog();
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC 
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		
		String okText
			= MacawMessages.getMessage("general.buttons.ok");
		ok = userInterfaceFactory.createButton(okText);
		ok.addActionListener(this);
		panel.add(ok, panelGC);
		
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
	public Variable getAlternativeVariable() {
		try {
			Variable selectedVariable
				= variableSearchPanel.getSelectedVariable();
			return selectedVariable;
		}
		catch(MacawException exception) {
			log.displayErrorDialog(exception);
			return null;
		}
	}
	
	public void show() {
		dialog.setVisible(true);
	}
	
	private void ok() {
		dialog.setVisible(false);		
	}
	
	private void cancel() {
		isCancelled = true;
		dialog.setVisible(false);
	}
	
	public boolean isCancelled() {
		return isCancelled;
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
		
		if (button == ok) {
			ok();
		}
		else if (button == cancel) {
			cancel();
		}

	}


	// ==========================================
	// Section Overload
	// ==========================================

}

