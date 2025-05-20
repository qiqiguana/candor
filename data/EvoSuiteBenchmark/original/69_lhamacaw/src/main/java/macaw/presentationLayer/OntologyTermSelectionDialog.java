package macaw.presentationLayer;

import macaw.businessLayer.MacawCurationAPI;
import macaw.businessLayer.OntologyTerm;
import macaw.businessLayer.User;
import macaw.businessLayer.Variable;
import macaw.system.*;
import macaw.util.Displayable;
import macaw.util.DisplayableList;
import macaw.util.DisplayableListItemSelector;
import macaw.util.WindowSizeListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

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

public class OntologyTermSelectionDialog implements ActionListener,
													DisplayableListItemSelector,
													ListSelectionListener {


	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private SessionProperties sessionProperties;
	private UserInterfaceFactory userInterfaceFactory;
	private MacawCurationAPI database;
	private OntologyTermSearchTableModel ontologyTermTableModel;
	
	private Variable variable;
	private User currentUser;
	
	private JDialog dialog;

	private DisplayableList parentList;
	
	private JTextField termField;
	private JTextField descriptionField;
	private JButton applySearch;
	private JButton resetSearch;
	
	private JTable searchOntologyTermTable;
	
	private JButton addOntologyTerms;
	private JButton cancel;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public OntologyTermSelectionDialog(SessionProperties sessionProperties) {
		this.sessionProperties = sessionProperties;
		database = (MacawCurationAPI) sessionProperties.getProperty(SessionProperties.DATABASE);
		currentUser = (User) sessionProperties.getProperty(SessionProperties.CURRENT_USER);
		userInterfaceFactory = sessionProperties.getUserInterfaceFactory();
		dialog = userInterfaceFactory.createDialog();
		
		String title
			= MacawMessages.getMessage("ontologyTermsSelectionDialog.title");
		dialog.setTitle(title);
		
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC = userInterfaceFactory.createGridBagConstraints();
		
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panel.add(createSearchPanel(), panelGC);

		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weightx = 1.0;
		panelGC.weighty = 1.0;
		
		ontologyTermTableModel
			= new OntologyTermSearchTableModel(database);
		searchOntologyTermTable 
			= userInterfaceFactory.createTable(ontologyTermTableModel);
		ListSelectionModel tableSelectionModel
			= searchOntologyTermTable.getSelectionModel();
		tableSelectionModel.addListSelectionListener(this);
		JScrollPane scrollPane 
			= userInterfaceFactory.createScrollPane(searchOntologyTermTable);
		panel.add(scrollPane, panelGC);

		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		panel.add(createButtonPanel(), panelGC);
		
		updateButtonStates();
		
		dialog.getContentPane().add(panel);
		dialog.setModal(true);
		WindowSizeListener windowSizeListener 
			= new WindowSizeListener();
		dialog.addComponentListener(windowSizeListener);
		dialog.setSize(500, 330);
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
			= MacawMessages.getMessage("ontologyTerm.term.label");
		JLabel documentLabel
			= userInterfaceFactory.createLabel(documentText);
		panel.add(documentLabel, panelGC);
		panelGC.gridx++;
		panelGC.weightx = 1.0;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		termField
			= userInterfaceFactory.createTextField(20);
		panel.add(termField,
				  panelGC);
	
		//adding the document code search field
		panelGC.gridy++;
		panelGC.gridx = 0;
		panelGC.weightx = 0;
		panelGC.fill = GridBagConstraints.NONE;
		String documentCodeText
			= MacawMessages.getMessage("ontologyTerm.description.label");
		JLabel documentCodeLabel
			= userInterfaceFactory.createLabel(documentCodeText);
		panel.add(documentCodeLabel, panelGC);		
		panelGC.gridx++;
		panelGC.weightx = 1.0;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		descriptionField
			= userInterfaceFactory.createTextField(20);
		panel.add(descriptionField,
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
		
		String addOntologyTermsText
			= MacawMessages.getMessage("ontologyTermsSelectionDialog.addOntologyTerms");
		addOntologyTerms
			= userInterfaceFactory.createButton(addOntologyTermsText);
		addOntologyTerms.addActionListener(this);
		panel.add(addOntologyTerms, panelGC);
		
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
	
	private void addOntologyTerms() {
		try {
			//get selected items
			int[] selectedRowIndices
				= searchOntologyTermTable.getSelectedRows();
			ArrayList<OntologyTerm> supportingTermsToAssociate
				= new ArrayList<OntologyTerm>();
			for (int i = 0; i < selectedRowIndices.length; i++) {
				OntologyTerm ontologyTerm
					= ontologyTermTableModel.getRow(selectedRowIndices[i]);
				supportingTermsToAssociate.add(ontologyTerm);
			}
			
			//associate documents
			database.associateOntologyTermsWithVariable(currentUser, 
														variable, 
														supportingTermsToAssociate);
			updateList();
			dialog.setVisible(false);
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);			
		}
	}
	
	private void applySearch() {		
		String ontologyTermPhrase
			= termField.getText().trim();
		String ontologyDescriptionPhrase
			= descriptionField.getText().trim();
		
		if ((ontologyTermPhrase.equals("") == true) &&
			(ontologyDescriptionPhrase.equals("") == true) ) {
			//cannot have both fields null
			String errorMessage
				= MacawMessages.getMessage("ontologyTermsSelectionDialog.blankSearchFields");
			Log log
				= sessionProperties.getLog();
			log.displayErrorDialog(errorMessage);	
			return;
		}
		
		try {
			ArrayList<OntologyTerm> ontologyTerms
				= database.filterOntologyTerms(currentUser,
											   ontologyTermPhrase,
											   ontologyDescriptionPhrase);
			ontologyTermTableModel.setData(ontologyTerms);
			applySearch.setEnabled(false);
			resetSearch.setEnabled(true);

			termField.setEditable(false);
			descriptionField.setEditable(false);
			updateButtonStates();
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}
	}
	
	private void resetSearch() {
		
		try {
			ArrayList<OntologyTerm> allOntologyTerms
				= database.getAllOntologyTerms(currentUser);	
			ontologyTermTableModel.setData(allOntologyTerms);
			applySearch.setEnabled(true);
			resetSearch.setEnabled(false);
			
			termField.setEditable(true);
			termField.setText("");
			descriptionField.setEditable(true);
			descriptionField.setText("");
			updateButtonStates();
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
		ArrayList<OntologyTerm> ontologyTerms
			= database.getOntologyTerms(currentUser, variable);
				
		ArrayList<Displayable> displayableItems = new ArrayList<Displayable>();
		for (OntologyTerm currentOntologyTerm : ontologyTerms) {
			displayableItems.add((Displayable) currentOntologyTerm);			
		}
		parentList.setDisplayItems(displayableItems);
	}

	private void updateButtonStates() {
		int numberOfResults
			= ontologyTermTableModel.getRowCount();
		if (numberOfResults == 0) {
			addOntologyTerms.setEnabled(false);
		}
		else {			
			int numberOfSelectedRows
				= searchOntologyTermTable.getSelectedRowCount();
			if (numberOfSelectedRows > 0) {
				addOntologyTerms.setEnabled(true);				
			}
			else {
				addOntologyTerms.setEnabled(false);				
			}	
		}
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
		else if (button == addOntologyTerms) {
			addOntologyTerms();
		}
		else if (button == cancel) {
			cancel();
		}
	}

	//Interface: ListSelectionListener
	public void valueChanged(ListSelectionEvent event) {
		updateButtonStates();
	}
	
	// ==========================================
	// Section Overload
	// ==========================================

}

