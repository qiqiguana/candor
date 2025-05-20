package macaw.presentationLayer;

import macaw.system.*;
import macaw.businessLayer.*;
import macaw.util.MultiLineTableCellRenderer;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class ChangeHistoryViewer implements ActionListener {


	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private User currentUser;
	private MacawCurationAPI database;
	private UserInterfaceFactory userInterfaceFactory;
	private JTable changeHistoryTable;
	ChangeHistoryTableModel changeHistoryTableModel;
	
	private JDialog dialog;
	private Log log;
	
	private JButton close;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public ChangeHistoryViewer(SessionProperties sessionProperties) {
		log = sessionProperties.getLog();
		currentUser = (User) sessionProperties.getProperty(SessionProperties.CURRENT_USER);
		database = (MacawCurationAPI) sessionProperties.getProperty(SessionProperties.DATABASE);		
		userInterfaceFactory = sessionProperties.getUserInterfaceFactory();
		dialog = userInterfaceFactory.createDialog();
		
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC = userInterfaceFactory.createGridBagConstraints();

		changeHistoryTableModel = new ChangeHistoryTableModel();
		changeHistoryTable = userInterfaceFactory.createTable(changeHistoryTableModel);
		JTableHeader tableHeader 
			= changeHistoryTable.getTableHeader();
		tableHeader.setReorderingAllowed(false);
		DefaultTableColumnModel defaultTableColumnModel
			= (DefaultTableColumnModel) tableHeader.getColumnModel();
		TableColumn descriptionColumn
			= defaultTableColumnModel.getColumn(1);
		MultiLineTableCellRenderer multiLineRenderer
			= new MultiLineTableCellRenderer();
		descriptionColumn.setCellRenderer(multiLineRenderer);
		descriptionColumn.setPreferredWidth(200);
		
		
				
		JScrollPane scrollPane
			= userInterfaceFactory.createScrollPane(changeHistoryTable);
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weightx = 1.0;
		panelGC.weighty = 1.0;
		panel.add(scrollPane, panelGC);
		
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
		GridBagConstraints panelGC 
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		panelGC.fill = GridBagConstraints.NONE;
		
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

	public void showHistoryForVariable(Variable variable) throws MacawException {
		try {
			ArrayList<MacawChangeEvent> changeEvents
				= database.getChangeHistoryForVariable(currentUser, variable);
			changeHistoryTableModel.setChangeEvents(changeEvents);
			String title
				= MacawMessages.getMessage("changeHistoryViewer.variable.title",
										   variable.getDisplayName());
			dialog.setTitle(title);		
			dialog.setVisible(true);			
		}
		catch(MacawException exception) {
			log.displayErrorDialog(exception);
		}		
	}

	public void showHistoryForSupportingDocument(SupportingDocument supportingDocument) throws MacawException {
		try {
			ArrayList<MacawChangeEvent> changeEvents
				= database.getChangeHistoryForSupportingDocument(currentUser, supportingDocument);
			changeHistoryTableModel.setChangeEvents(changeEvents);
			String title
				= MacawMessages.getMessage("changeHistoryViewer.supportingDocument.title",
										   supportingDocument.getDisplayName());
			dialog.setTitle(title);		
			dialog.setVisible(true);			
		}
		catch(MacawException exception) {
			log.displayErrorDialog(exception);
		}				
	}

	public void showHistoryForValueLabel(Variable variable) throws MacawException {
		try {
			ArrayList<MacawChangeEvent> changeEvents
				= database.getChangeHistoryForValueLabels(currentUser, variable);
			changeHistoryTableModel.setChangeEvents(changeEvents);
			String title
				= MacawMessages.getMessage("changeHistoryViewer.valueLabels.title",
										   variable.getDisplayName());
			dialog.setTitle(title);		
			dialog.setVisible(true);			
		}
		catch(MacawException exception) {
			log.displayErrorDialog(exception);
		}				
	}

	public void showHistoryForListChoices() throws MacawException {
		try {
			ArrayList<MacawChangeEvent> changeEvents
				= database.getChangeHistoryForListChoices(currentUser);
			changeHistoryTableModel.setChangeEvents(changeEvents);
			String title
				= MacawMessages.getMessage("changeHistoryViewer.listChoices.title");
			dialog.setTitle(title);		
			dialog.setVisible(true);			
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

	//Interface: ActionListener
	public void actionPerformed(ActionEvent event) {
		dialog.setVisible(false);
	}

	// ==========================================
	// Section Overload
	// ==========================================

}

