package macaw.util;

import macaw.system.MacawMessages;
import macaw.system.UserInterfaceFactory;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.ArrayList;

/**
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

public class OKClosePanel {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private UserInterfaceFactory userInterfaceFactory;
	private ActionListener actionListener;
	private boolean allowWriteAccess;
	
	private JPanel panel;
	private ArrayList<JButton> otherButtons;
	private JButton ok;
	private JButton close;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public OKClosePanel(UserInterfaceFactory userInterfaceFactory,
						 ActionListener actionListener) {
		
		this.userInterfaceFactory = userInterfaceFactory;
		this.actionListener = actionListener;
		otherButtons = new ArrayList<JButton>();
		
		String saveText
			= MacawMessages.getMessage("general.buttons.save");
		ok = userInterfaceFactory.createButton(saveText);
		ok.setEnabled(allowWriteAccess);
		ok.addActionListener(actionListener);
		
		String closeText
			= MacawMessages.getMessage("general.buttons.close");
		close = userInterfaceFactory.createButton(closeText);
		close.addActionListener(actionListener);
				
		panel = userInterfaceFactory.createPanel();
		userInterfaceFactory.setComponentProperties(panel);
		allowWriteAccess = true;
	}
	
	public void buildUI() {	
		panel.setLayout(new GridBagLayout() );
		
		panel.removeAll();
		
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;

		for (JButton otherButton: otherButtons) {
			panel.add(otherButton, panelGC);
			panelGC.gridx++;
		}
	
		if (allowWriteAccess == true) {
			ok.setEnabled(true);
			panel.add(ok, panelGC);			
			panelGC.gridx++;
		}
		else {
			ok.setEnabled(false);
		}

		close.setEnabled(true);
		panel.add(close, panelGC);	
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public void setAllowWriteAccess(boolean allowWriteAccess) {
		this.allowWriteAccess = allowWriteAccess;	
		buildUI();
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public void addButton(JButton otherButton) {
		otherButton.addActionListener(actionListener);
		otherButtons.add(otherButton);
	}
	
	public JButton getSaveButton() {
		return ok;
	}
	
	/*
	public JButton getCancelButton() {
		return cancel;
	}
	*/
	public JButton getCloseButton() {
		return close;
	}
	
	// ==========================================
	// Section Errors and Validation
	// ==========================================

	// ==========================================
	// Section Interfaces
	// ==========================================

	// ==========================================
	// Section Overload
	// ==========================================

}

