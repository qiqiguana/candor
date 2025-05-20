package macaw.presentationLayer;

import macaw.businessLayer.MacawCurationAPI;
import macaw.businessLayer.SupportingDocument;
import macaw.businessLayer.User;
import macaw.businessLayer.Variable;
import macaw.system.*;
import macaw.util.Displayable;
import macaw.util.DisplayableList;
import macaw.util.DisplayableListItemSelector;
import macaw.util.WindowSizeListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

public class SupportingDocumentSelectionDialog implements ActionListener,
														  DisplayableListItemSelector {


	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private SessionProperties sessionProperties;
	private UserInterfaceFactory userInterfaceFactory;
	private MacawCurationAPI database;
	private SupportingDocumentSearchTableModel searchDocumentTableModel;
	
	private Variable variable;
	private User currentUser;
	
	private JDialog dialog;

	private DisplayableList parentList;
	
	private JTextField documentTitleField;
	private JTextField documentCodeField;
	private JButton applySearch;
	private JButton resetSearch;
	
	private JTable searchDocumentTable;
	
	private JButton addSupportingDocuments;
	private JButton cancel;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public SupportingDocumentSelectionDialog(SessionProperties sessionProperties) {
		this.sessionProperties = sessionProperties;
		database = (MacawCurationAPI) sessionProperties.getProperty(SessionProperties.DATABASE);
		currentUser = (User) sessionProperties.getProperty(SessionProperties.CURRENT_USER);
		userInterfaceFactory = sessionProperties.getUserInterfaceFactory();
		dialog = userInterfaceFactory.createDialog();
		
		String title
			= MacawMessages.getMessage("supportingDocumentsSelectionDialog.title");
		dialog.setTitle(title);
		
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC = userInterfaceFactory.createGridBagConstraints();
		
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panel.add(createSearchPanel(), panelGC);

		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weightx = 1.0;
		panelGC.weighty = 1.0;
		
		searchDocumentTableModel
			= new SupportingDocumentSearchTableModel(database);
		searchDocumentTable 
			= userInterfaceFactory.createTable(searchDocumentTableModel);
		JScrollPane scrollPane 
			= userInterfaceFactory.createScrollPane(searchDocumentTable);
		panel.add(scrollPane, panelGC);

		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		panel.add(createButtonPanel(), panelGC);
		
		dialog.getContentPane().add(panel);
		dialog.setModal(true);
		WindowSizeListener windowSizeListener 
			= new WindowSizeListener();
		dialog.addComponentListener(windowSizeListener);
		dialog.setSize(590, 350);
	}
	
	private JPanel createSearchPanel() {
		JPanel panel 
			= userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		panel.add(createSearchFieldPanel(), panelGC);
		
		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		panelGC.weightx = 1.0;
		panel.add(createSearchButtonPanel(), panelGC);
		
		return panel;
	}
	
	private JPanel createSearchFieldPanel() {
		JPanel panel 
			= userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		
		//adding the document title search field
		panelGC.gridx = 0;
		panelGC.weightx = 0;
		panelGC.fill = GridBagConstraints.NONE;
		String documentText 
			= MacawMessages.getMessage("supportingDocument.title.label");
		JLabel documentLabel
			= userInterfaceFactory.createLabel(documentText);
		panel.add(documentLabel, panelGC);
		panelGC.gridx++;
		panelGC.weightx = 1.0;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		documentTitleField
			= userInterfaceFactory.createTextField(20);
		panel.add(documentTitleField,
				  panelGC);
	
		//adding the document code search field
		panelGC.gridy++;
		panelGC.gridx = 0;
		panelGC.weightx = 0;
		panelGC.fill = GridBagConstraints.NONE;
		String documentCodeText
			= MacawMessages.getMessage("supportingDocument.documentCode.label");
		JLabel documentCodeLabel
			= userInterfaceFactory.createLabel(documentCodeText);
		panel.add(documentCodeLabel, panelGC);		
		panelGC.gridx++;
		panelGC.weightx = 1.0;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		documentCodeField
			= userInterfaceFactory.createTextField(20);
		panel.add(documentCodeField,
				  panelGC);
		return panel;
	}
	
	private JPanel createSearchButtonPanel() {
		JPanel panel 
			= userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
	
		String applySearchText
			= MacawMessages.getMessage("general.labels.search");
		applySearch
			= userInterfaceFactory.createButton(applySearchText);
		applySearch.addActionListener(this);
		panel.add(applySearch, panelGC);
		
		panelGC.gridx++;
		String resetSearchText
			= MacawMessages.getMessage("general.buttons.reset");
		resetSearch
			= userInterfaceFactory.createButton(resetSearchText);
		resetSearch.addActionListener(this);
		panel.add(resetSearch, panelGC);
		
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel
			= userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		
		String addSupportingDocumentsText
			= MacawMessages.getMessage("supportingDocumentsSelectionDialog.addSupportingDocuments");
		addSupportingDocuments
			= userInterfaceFactory.createButton(addSupportingDocumentsText);
		addSupportingDocuments.addActionListener(this);
		panel.add(addSupportingDocuments, panelGC);
		
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
	
	public void setVariable(Variable variable) {
		this.variable = variable;		
	}
	
	public void show() {
		dialog.setVisible(true);
	}
	
	private void addSupportingDocuments() {
		try {
			//get selected items
			int[] selectedRowIndices
				= searchDocumentTable.getSelectedRows();
			ArrayList<SupportingDocument> supportingDocumentsToAssociate
				= new ArrayList<SupportingDocument>();
			for (int i = 0; i < selectedRowIndices.length; i++) {
				SupportingDocument supportingDocument
					= searchDocumentTableModel.getRow(selectedRowIndices[i]);
				supportingDocumentsToAssociate.add(supportingDocument);
			}
			
			//associate documents
			database.associateSupportingDocumentsWithVariable(currentUser, 
															  variable, 
															  supportingDocumentsToAssociate);
			updateList();
			dialog.setVisible(false);
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);			
		}
	}
	
	private void applySearch() {
		String documentTitlePhrase
			= documentTitleField.getText().trim();
		String documentCodePhrase
			= documentCodeField.getText().trim();
		try {
			ArrayList<SupportingDocument> supportingDocuments
				= database.filterSupportingDocuments(currentUser,
													 documentTitlePhrase,
													 documentCodePhrase);
			searchDocumentTableModel.setData(supportingDocuments);
			
			documentTitleField.setEditable(false);
			documentCodeField.setEditable(false);
			
			applySearch.setEnabled(false);
			resetSearch.setEnabled(true);
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}
	}
	
	private void resetSearch() {
		
		try {
			ArrayList<SupportingDocument> supportingDocuments
				= database.getAllSupportingDocuments(currentUser);	
			searchDocumentTableModel.setData(supportingDocuments);
			
			documentTitleField.setText("");
			documentTitleField.setEditable(true);
			documentCodeField.setText("");
			documentCodeField.setEditable(true);
	
			applySearch.setEnabled(true);
			resetSearch.setEnabled(false);
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}

		applySearch.setEnabled(true);
		resetSearch.setEnabled(false);	
	}
	
	private void cancel() {
		dialog.setVisible(false);
	}
	
	private void updateList() throws MacawException {
		ArrayList<SupportingDocument> supportingDocuments
			= database.getSupportingDocuments(currentUser, variable);
				
		ArrayList<Displayable> displayableItems = new ArrayList<Displayable>();
		for (SupportingDocument currentSupportingDocument : supportingDocuments) {
			displayableItems.add((Displayable) currentSupportingDocument);			
		}
		parentList.setDisplayItems(displayableItems);
	}

	// ==========================================
	// Section Errors and Validation
	// ==========================================

	// ==========================================
	// Section Interfaces
	// ==========================================

	//Interface: DisplayableListItemSelector
	public void selectListItems(String listOwnerName,
								DisplayableList parentList) throws MacawException {
		
		this.parentList = parentList;
		resetSearch();

		//start with 		
		show();
		updateList();
	}
	
	//Interface: ActionListener
	public void actionPerformed(ActionEvent event) {
		Object button = event.getSource();
		
		if (button == applySearch) {
			applySearch();			
		}
		else if (button == resetSearch) {
			resetSearch();
		}
		else if (button == addSupportingDocuments) {
			addSupportingDocuments();
		}
		else if (button == cancel) {
			cancel();
		}
	}

	// ==========================================
	// Section Overload
	// ==========================================

}

