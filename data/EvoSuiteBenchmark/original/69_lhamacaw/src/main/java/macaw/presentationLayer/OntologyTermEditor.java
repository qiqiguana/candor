package macaw.presentationLayer;

import macaw.system.*;
import macaw.util.*;
import macaw.businessLayer.*;

import java.awt.*;
import javax.swing.*;

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

public class OntologyTermEditor implements ActionListener,
										   DisplayableListItemAdder,
										   DisplayableListItemEditor,
										   DisplayableListItemDeleter {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private SessionProperties sessionProperties;
	private boolean allowWriteAccess;
	private MacawCurationAPI database;
	private User currentUser;
	
	private Variable parentVariable;
	private OntologyTerm ontologyTerm;
	
	private UserInterfaceFactory userInterfaceFactory;
	private OKClosePanel okClosePanel;

	private JDialog dialog;
	private DisplayableList parentList;
	
	private JTextField termField;
	private JTextField ontologyNameField;
	private JTextField descriptionField;
	private JTextField nameSpaceField;
	
	private JButton save;
	private JButton close;

	// ==========================================
	// Section Construction
	// ==========================================
	public OntologyTermEditor(SessionProperties sessionProperties,
							  boolean allowWriteAccess) {
		this.sessionProperties = sessionProperties;
		this.allowWriteAccess = allowWriteAccess;
		
		userInterfaceFactory = sessionProperties.getUserInterfaceFactory();
		currentUser = (User) sessionProperties.getProperty(SessionProperties.CURRENT_USER);
		database = (MacawCurationAPI) sessionProperties.getProperty(SessionProperties.DATABASE);

		dialog = userInterfaceFactory.createDialog();

		if (allowWriteAccess == true) {
			String title 
				= MacawMessages.getMessage("ontologyTermsEditor.title");
											dialog.setTitle(title);
			dialog.setTitle(title);
		}
		else {
			String title 
				= MacawMessages.getMessage("ontologyTermsEditor.title.readOnly");
			dialog.setTitle(title);
		}
		
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC = userInterfaceFactory.createGridBagConstraints();
		
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weightx = 1.0;
		panelGC.weighty = 1.0;
		panelGC.insets = new Insets(2, 2, 2, 2);
	
		panel.add(createMainFormPanel(), panelGC);

		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;
		panelGC.weighty = 0;
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
	
		panelGC.gridy++;
	
		okClosePanel 
			= new OKClosePanel(userInterfaceFactory, this);
		okClosePanel.setAllowWriteAccess(allowWriteAccess);
		save = okClosePanel.getSaveButton();
		close = okClosePanel.getCloseButton();
		okClosePanel.buildUI();
		panel.add(okClosePanel.getPanel(), panelGC);
		
		WindowSizeListener windowSizeListener = new WindowSizeListener();
		dialog.addComponentListener(windowSizeListener);
		
		dialog.getContentPane().add(panel);
		dialog.setModal(true);
		dialog.setSize(380, 170);	
	}
	
	private JPanel createMainFormPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC = userInterfaceFactory.createGridBagConstraints();

		//adding the term field
		String termText
			= MacawMessages.getMessage("ontologyTerm.term.label");
		JLabel termLabel
			= userInterfaceFactory.createLabel(termText);
		panel.add(termLabel, panelGC);		
		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		termField = userInterfaceFactory.createTextField(20);
		panel.add(termField, panelGC);
		
		//adding the ontology name field		
		panelGC.gridy++;
		panelGC.gridx = 0;
		String ontologyNameText
			= MacawMessages.getMessage("ontologyTerm.ontologyName.label");
		JLabel ontologyNameLabel
			= userInterfaceFactory.createLabel(ontologyNameText);
		panel.add(ontologyNameLabel, panelGC);		
		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		ontologyNameField = userInterfaceFactory.createTextField(20);
		panel.add(ontologyNameField, panelGC);	

		//adding the description field
		panelGC.gridy++;
		panelGC.gridx = 0;
		String descriptionText
			= MacawMessages.getMessage("ontologyTerm.description.label");
		JLabel descriptionLabel
			= userInterfaceFactory.createLabel(descriptionText);
		panel.add(descriptionLabel, panelGC);		
		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		descriptionField = userInterfaceFactory.createTextField(20);
		panel.add(descriptionField, panelGC);

		//adding in the name space field
		panelGC.gridy++;
		panelGC.gridx = 0;
		String nameSpaceText
			= MacawMessages.getMessage("ontologyTerm.nameSpace.label");
		JLabel nameSpaceLabel
			= userInterfaceFactory.createLabel(nameSpaceText);
		panel.add(nameSpaceLabel, panelGC);		
		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		nameSpaceField = userInterfaceFactory.createTextField(20);
		panel.add(nameSpaceField, panelGC);
		
		termField.setEditable(allowWriteAccess);
		ontologyNameField.setEditable(allowWriteAccess);
		descriptionField.setEditable(allowWriteAccess);
		nameSpaceField.setEditable(allowWriteAccess);

		return panel;
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public void setParentVariable(Variable parentVariable) {
		this.parentVariable = parentVariable;
	}
	
	public void setOntologyTerm(OntologyTerm ontologyTerm) {
		this.ontologyTerm = ontologyTerm;

		termField.setText(ontologyTerm.getTerm());
		ontologyNameField.setText(ontologyTerm.getOntologyName());
		descriptionField.setText(ontologyTerm.getDescription());
		nameSpaceField.setText(ontologyTerm.getNameSpace());		
	}
	
	public void show() {
		dialog.setVisible(true);
	}
	
	private void save() {
		try {
			ontologyTerm.setTerm(termField.getText().trim());
			ontologyTerm.setOntologyName(ontologyNameField.getText().trim());
			ontologyTerm.setDescription(descriptionField.getText().trim());
			ontologyTerm.setNameSpace(nameSpaceField.getText().trim());
			
			if (ontologyTerm.isNewRecord() == true) {
				//we have just created the record
				database.addOntologyTerm(currentUser, ontologyTerm);
				int ontologyTermID
					= database.getOntologyTermIdentifier(currentUser, ontologyTerm);
				ontologyTerm.setIdentifier(ontologyTermID);
				
				//associate the term with the variable
				ArrayList<OntologyTerm> ontologyTerms 
					= new ArrayList<OntologyTerm>();
				ontologyTerms.add(ontologyTerm);
				database.associateOntologyTermsWithVariable(currentUser, 
															parentVariable,
															ontologyTerms);
				
			}
			else {
				//we are updating an existing record
				database.updateOntologyTerm(currentUser, ontologyTerm);
			}
			dialog.setVisible(false);
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}
	}
	
	private void close() {
		dialog.setVisible(false);
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
		
		if (button == save) {
			save();
		}
		else if (button == close) {
			close();
		}
	}

	//Interface: DisplayableListItemAdder
	public void addItems(String listOwnerName,
			 			 DisplayableList parentList) throws MacawException {

		this.parentList = parentList;
		OntologyTerm ontologyTerm = new OntologyTerm();
		setOntologyTerm(ontologyTerm);
		show();		
		updateList();
	}

	
	//Interface: DisplayableListItemEditor
	public void editListItem(String listItemOwner,
							 Displayable listItem, 
							 DisplayableList parentList) throws MacawException {

		this.parentList = parentList;
		OntologyTerm ontologyTerm = (OntologyTerm) listItem;
		setOntologyTerm(ontologyTerm);
		show();
		updateList();
	}

	//Interface: DisplayableListItemDeleter
	public void deleteListItems(String listOwnerName,
								DisplayableList parentList) throws MacawException {
	
		this.parentList = parentList;
		ArrayList<Displayable> itemsToDelete
			= parentList.getSelectedItems();
		if (itemsToDelete.size() == 0) {
			return;
		}

		ArrayList<OntologyTerm> ontologyTermsToDelete
			= new ArrayList<OntologyTerm>();
		for (Displayable currentItem : itemsToDelete) {
			ontologyTermsToDelete.add((OntologyTerm) currentItem);			
		}
		
		String warningMessage
			= MacawMessages.getMessage("ontologyTerm.chooseDeletionMethod");
		String dialogTitle
			= MacawMessages.getMessage("general.warning.title");
		
		int result = JOptionPane.showConfirmDialog(null,
				   warningMessage,
				   dialogTitle,
				   JOptionPane.YES_NO_CANCEL_OPTION,
				   JOptionPane.WARNING_MESSAGE);
		
		if (result == JOptionPane.YES_OPTION) {
			//YES - means delete the ontology term as well as all
			//references to it		
			database.deleteOntologyTerms(currentUser, 
										 ontologyTermsToDelete);			
			updateList();
		}
		else if (result == JOptionPane.NO_OPTION) {
			database.disassociateOntologyTermsFromVariable(currentUser, 
													 	   parentVariable, 
													 	   ontologyTermsToDelete);			
			updateList();
		}
		
		dialog.setVisible(false);
		return;				

	}
	
	private void updateList() throws MacawException {
		ArrayList<OntologyTerm> ontologyTerms
			= database.getOntologyTerms(currentUser, parentVariable);
				
		ArrayList<Displayable> displayableItems = new ArrayList<Displayable>();
		for (OntologyTerm currentOntologyTerm : ontologyTerms) {
			displayableItems.add((Displayable) currentOntologyTerm);			
		}
		parentList.setDisplayItems(displayableItems);
		//parentList.refreshList();
	}

	
	// ==========================================
	// Section Overload
	// ==========================================

}

