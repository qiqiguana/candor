package macaw.presentationLayer;

import macaw.system.*;
import macaw.util.*;
import macaw.businessLayer.*;

import javax.swing.*;

import java.awt.*;
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

public class SupportingDocumentEditor implements DisplayableListItemAdder,
												 DisplayableListItemEditor, 
												 DisplayableListItemViewer,
												 DisplayableListItemDeleter,
												 ActionListener {

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
	private UserInterfaceFactory userInterfaceFactory;
		
	private JDialog dialog;
	
	private SupportingDocument supportingDocument;
	
	private JTextField titleField;
	private JTextField documentCodeField;
	private JTextArea descriptionField;
	private JTextField fileNameField;
	
	private OKClosePanel oKClosePanel;	
	private JButton save;
	private JButton close;
	
	private boolean isEditingCancelled;

	private Variable parentVariable;

	private DisplayableList parentList;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public SupportingDocumentEditor(SessionProperties sessionProperties,
									boolean allowWriteAccess) {
		
		this.sessionProperties = sessionProperties;
		this.allowWriteAccess = allowWriteAccess;
			
		currentUser = (User) sessionProperties.getProperty(SessionProperties.CURRENT_USER);
		
		this.userInterfaceFactory 
			= sessionProperties.getUserInterfaceFactory();
		database 
			= (MacawCurationAPI) sessionProperties.getProperty(SessionProperties.DATABASE);

		User currentUser 
			= (User) sessionProperties.getProperty(SessionProperties.CURRENT_USER);
		
		isEditingCancelled = false;
		
		dialog = userInterfaceFactory.createDialog();
		
		if (allowWriteAccess == true) {
			String title 
				= MacawMessages.getMessage("supportingDocumentEditor.title");
											dialog.setTitle(title);
			dialog.setTitle(title);
		}
		else {
			String title 
				= MacawMessages.getMessage("supportingDocumentEditor.title.readOnly");
			dialog.setTitle(title);
		}

		JPanel panel 		
			= userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.ipady = 10;
		
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weightx = 100;
		panelGC.weighty = 100;
		panel.add(createMainFormPanel(), panelGC);
		
		panelGC.gridy++;
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;
		panelGC.weighty = 0;
		oKClosePanel 
			= new OKClosePanel(userInterfaceFactory, this);
		oKClosePanel.setAllowWriteAccess(allowWriteAccess);
		save = oKClosePanel.getSaveButton();
		close = oKClosePanel.getCloseButton();
		panel.add(oKClosePanel.getPanel(), panelGC);
				
		//TODO take out
		WindowSizeListener listener = new WindowSizeListener();
		dialog.addComponentListener(listener);
		
		dialog.getContentPane().add(panel);
		dialog.setSize(440, 220);
		//dialog.setResizable(false);
		dialog.setModal(true);
	}
	
	private JPanel createMainFormPanel() {
		JPanel panel 		
			= userInterfaceFactory.createPanel();	
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.insets = new Insets(2, 10, 2, 10);

		//adding the document title field
		String titleLabelText
			= MacawMessages.getMessage("supportingDocument.title.label");
		JLabel titleLabel
			= userInterfaceFactory.createLabel(titleLabelText);
		panel.add(titleLabel, panelGC);
		
		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 100;
		titleField = userInterfaceFactory.createTextField(20);
		panel.add(titleField, panelGC);

		//adding the document title field
		panelGC.gridy++;
		panelGC.gridx = 0;
		String documentCodeLabelText
			= MacawMessages.getMessage("supportingDocument.documentCode.label");
		JLabel documentCodeLabel
			= userInterfaceFactory.createLabel(documentCodeLabelText);
		panel.add(documentCodeLabel, panelGC);
		
		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 100;
		documentCodeField = userInterfaceFactory.createTextField(20);
		panel.add(documentCodeField, panelGC);

		//adding the description field
		panelGC.gridy++;
		panelGC.gridx = 0;
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;
		
		String descriptionLabelText
			= MacawMessages.getMessage("supportingDocument.description.label");
		JLabel descriptionLabel
			= userInterfaceFactory.createLabel(descriptionLabelText);
		panel.add(descriptionLabel, panelGC);
	
		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 100;
		descriptionField = userInterfaceFactory.createTextArea(3, 20);
		JScrollPane descriptionScrollPane
			= userInterfaceFactory.createScrollPane(descriptionField);
		descriptionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);		
		panel.add(descriptionScrollPane, panelGC);

		//adding the file name field
		panelGC.gridy++;
		panelGC.gridx = 0;		
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;
		String fileNameLabelText
			= MacawMessages.getMessage("supportingDocument.fileName.label");
		JLabel fileNameLabel
			= userInterfaceFactory.createLabel(fileNameLabelText);
		panel.add(fileNameLabel, panelGC);
	
		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 100;
		fileNameField = userInterfaceFactory.createTextField(20);
		panel.add(fileNameField, panelGC);

		titleField.setEditable(allowWriteAccess);
		documentCodeField.setEditable(allowWriteAccess);
		descriptionField.setEditable(allowWriteAccess);
		fileNameField.setEditable(allowWriteAccess);
		
		return panel;
	}
	
	private void setAllowWriteAccess(boolean allowWriteAccess) {
		if (allowWriteAccess == true) {
			String title
				= MacawMessages.getMessage("supportingDocumentEditor.title");
			dialog.setTitle(title);
		}
		else {
			String title
				= MacawMessages.getMessage("supportingDocumentEditor.title.readOnly");
			dialog.setTitle(title);			
		}
	
		titleField.setEditable(allowWriteAccess);
		documentCodeField.setEditable(allowWriteAccess);
		descriptionField.setEditable(allowWriteAccess);
		fileNameField.setEditable(allowWriteAccess);
		
		oKClosePanel.setAllowWriteAccess(allowWriteAccess);
	}

	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	public void setVariable(Variable parentVariable) {
		this.parentVariable = parentVariable;
	}
	
	public void setSupportingDocument(SupportingDocument supportingDocument) {
		this.supportingDocument = supportingDocument;
		
		isEditingCancelled = false;
		titleField.setText(supportingDocument.getTitle());
		documentCodeField.setText(supportingDocument.getDocumentCode());
		descriptionField.setText(supportingDocument.getDescription());
		fileNameField.setText(supportingDocument.getFileName());				
	}
	
	public void show() {
		dialog.setVisible(true);
	}
	
	private void save() {
		supportingDocument.setTitle(titleField.getText().trim());
		supportingDocument.setDocumentCode(documentCodeField.getText().trim());		
		supportingDocument.setDescription(descriptionField.getText().trim());
		supportingDocument.setFileName(fileNameField.getText().trim());		
		isEditingCancelled = false;
		
		try {
			if (supportingDocument.isNewRecord() == true) {		
				database.addSupportingDocument(currentUser, 
											   supportingDocument);
				int documentIdentifier
					= database.getSupportingDocumentIdentifier(currentUser, supportingDocument);
				supportingDocument.setIdentifier(documentIdentifier);
			}
			else {
				database.updateSupportingDocument(currentUser, 
						  						  supportingDocument);				
			}
			
			ArrayList<SupportingDocument> supportingDocuments
				= new ArrayList<SupportingDocument>();
			supportingDocuments.add(supportingDocument);

			database.associateSupportingDocumentsWithVariable(currentUser, 
															  parentVariable, 
															  supportingDocuments);

			updateList();
			dialog.setVisible(false);
		}
		catch(MacawException exception) {			
			//ensure aborted save is treated as a cancel
			isEditingCancelled = true;

			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}	
	}
	
	private void close() {
		isEditingCancelled = true;		
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
		
		if (button == save) {
			save();
		}
		else if (button == close) {
			close();
		}		
	}

	//Interface: Displayable List Item Adder
	
	public void addItems(String listOwnerName,
						 DisplayableList parentList) {

		setAllowWriteAccess(true);
		SupportingDocument supportingDocument
			= new SupportingDocument();
		supportingDocument.setIsNewRecord(true);		
		setSupportingDocument(supportingDocument);
		this.parentList = parentList;
		show();
	}
	
	public void editListItem(String variableIdentifier,
							 Displayable listItem,
							 DisplayableList parentList) {

		setAllowWriteAccess(true);
		SupportingDocument supportingDocument
			= (SupportingDocument) listItem;
		setSupportingDocument(supportingDocument);
		this.parentList = parentList;
		
		show();
	}
	
	//Interface: Displayable List Item Viewer
	public void viewListItem(Displayable listItem) {
		setAllowWriteAccess(false);
		SupportingDocument supportingDocument
			= (SupportingDocument) listItem;
		setSupportingDocument(supportingDocument);
		show();
	}
	
	public void deleteListItems(String listOwnerName,
								DisplayableList parentList) throws MacawException {
		
		ArrayList<Displayable> itemsToDelete
			= parentList.getSelectedItems();
		if (itemsToDelete.size() == 0) {
			return;
		}

		this.parentList = parentList;
		
		ArrayList<SupportingDocument> supportingDocumentsToDelete 
			= new ArrayList<SupportingDocument>();
		for (Displayable itemToDelete : itemsToDelete) {
			supportingDocumentsToDelete.add((SupportingDocument) itemToDelete);
		}

		String warningMessage
			= MacawMessages.getMessage("supportingDocument.chooseDeletionMethod");
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
			database.deleteSupportingDocuments(currentUser, 
											   supportingDocumentsToDelete);			
			updateList();
		}
		else if (result == JOptionPane.NO_OPTION) {
			database.disassociateSupportingDocumentsFromVariable(currentUser, 
														   		 parentVariable, 
														   		 supportingDocumentsToDelete);			
			updateList();
		}

		updateList();
		dialog.setVisible(false);
	}
	
	public Displayable getItem() {
		Displayable displayableItem = (Displayable) supportingDocument;
		return displayableItem;
	}
	
	private boolean isEditingCancelled() {
		return isEditingCancelled;
	}

	
	private void updateList() throws MacawException {
		ArrayList<SupportingDocument> supportingDocuments
			= database.getSupportingDocuments(currentUser, parentVariable);
				
		ArrayList<Displayable> displayableItems = new ArrayList<Displayable>();
		for (SupportingDocument currentSupportingDocument : supportingDocuments) {
			displayableItems.add((Displayable) currentSupportingDocument);			
		}
		parentList.setDisplayItems(displayableItems);
		//parentList.refreshList();
	}

	// ==========================================
	// Section Overload
	// ==========================================

}

