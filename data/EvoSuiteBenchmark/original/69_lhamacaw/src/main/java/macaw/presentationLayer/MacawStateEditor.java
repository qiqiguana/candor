package macaw.presentationLayer;

import macaw.system.*;
import macaw.util.*;
import macaw.businessLayer.MacawCurationAPI;
import macaw.businessLayer.MacawListChoice;
import macaw.businessLayer.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.border.LineBorder;
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

abstract public class MacawStateEditor implements ActionListener,
													   DisplayableListItemAdder,
													   DisplayableListItemEditor,
													   DisplayableListItemViewer,
													   DisplayableListItemDeleter {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================	
	protected SessionProperties sessionProperties;
	protected UserInterfaceFactory userInterfaceFactory;
	protected MacawCurationAPI database;
	protected User currentUser;
	protected JDialog dialog;
	
	private JTextArea informationTextArea;
	
	protected String nameFieldLabelText;
	protected JTextField nameField;
	
	private MacawListChoice listChoiceItem;

	private DisplayableList parentList;
	protected String listNameOwner;
	private boolean isEditingCancelled;
	private OKClosePanel okCancelPanel;
	
	private JButton save;
	private JButton close;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public MacawStateEditor(SessionProperties sessionProperties) {
		this.sessionProperties = sessionProperties;
		userInterfaceFactory = sessionProperties.getUserInterfaceFactory();
		database = (MacawCurationAPI) sessionProperties.getProperty(SessionProperties.DATABASE);
		currentUser = (User) sessionProperties.getProperty(SessionProperties.CURRENT_USER);
		dialog = userInterfaceFactory.createDialog();
		dialog.setModal(true);
		
		WindowSizeListener windowSizeListener = new WindowSizeListener();
		dialog.addComponentListener(windowSizeListener);
		//create parts of the name field
		nameFieldLabelText
			= MacawMessages.getMessage("variableConstantsEditor.nameFieldText");
		nameField = userInterfaceFactory.createTextField(20);

		okCancelPanel 
			= new OKClosePanel(userInterfaceFactory, this);
		save = okCancelPanel.getSaveButton();
		close = okCancelPanel.getCloseButton();
		
		dialog.getContentPane().add(createDefaultMainFormPanel());
		
		dialog.addComponentListener(windowSizeListener);
	}
	
	protected JPanel createDefaultMainFormPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC = userInterfaceFactory.createGridBagConstraints();
		panelGC.insets = new Insets(2, 2, 2, 2);
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		panel.add(createInformationPanel(), panelGC);
		panelGC.gridy++;
		panel.add(createNameFieldPanel(), panelGC);
		panelGC.gridy++;
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;
		panelGC.weighty = 0;
		okCancelPanel.buildUI();
		panel.add(okCancelPanel.getPanel(), panelGC);
		return panel;
	}
	
	/**
	 * creates a panel with text describing what the rest of the dialog is about.
	 * @return
	 */
	protected JPanel createInformationPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC = userInterfaceFactory.createGridBagConstraints();
		panelGC.insets = new Insets(2, 2, 2, 2);
		informationTextArea = userInterfaceFactory.createImmutableTextArea(2, 20);
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weightx = 1.0;
		panelGC.weighty = 1.0;
		
		panel.add(informationTextArea, panelGC);
		
		panel.setBorder(LineBorder.createGrayLineBorder());
		return panel;
	}
	
	protected JPanel createNameFieldPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC = userInterfaceFactory.createGridBagConstraints();
		
		panelGC.ipadx = 2;
		panelGC.fill = GridBagConstraints.NONE;		
		panelGC.weightx = 0;		
		JLabel nameFieldLabel
			= userInterfaceFactory.createLabel(nameFieldLabelText);
		panel.add(nameFieldLabel, panelGC);
		
		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;		
		nameField = userInterfaceFactory.createTextField(30);
		panel.add(nameField, panelGC);
		
		return panel;
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	
	protected void setDialogTitle(String title) {
		dialog.setTitle(title);
	}
	
	protected void setDialogInformation(String dialogInformation) {
		informationTextArea.setText(dialogInformation);
	}
	
	public void show() {
		dialog.setVisible(true);
	}
	
	protected void restore() {
		nameField.setText(listChoiceItem.getName());
	}
	
	protected void save() {
		listChoiceItem.setName(nameField.getText().trim());					
		dialog.setVisible(false);
	}
			
	/**
	 * method invoked when the user presses the Cancel button
	 */
	protected void close() {
		isEditingCancelled = true;
		dialog.setVisible(false);
	}
	
	/**
	 * indicates whether the dialog was closed because editing changes
	 * were approved or if the user pressed the "Cancel" button.
	 */
	protected boolean isEditingCancelled() {
		return isEditingCancelled;
	}
	
	/*
	 * @param listChoiceItem - the item being displayed in the editor
	 * @param parentList - the list that is supposed to contain the listChoiceItem.
	 * This list used in validation to ensure that the current display name for
	 * the listChoiceItem does not already appear in its parent list.
	 * @param listTypeName - eg: "Category", "Data Library" etc... this is used to 
	 * record change events.  For example, "Category 'Women's Health' created"
	 */
	protected void setEditableData(MacawListChoice listChoiceItem,
						   		   DisplayableList parentList,
						   		   String listNameOwner) {
		this.listChoiceItem = listChoiceItem;
		this.parentList = parentList;
		this.listNameOwner = listNameOwner;	
		nameField.setEditable(true);
		okCancelPanel.setAllowWriteAccess(true);
		
		restore();
	}
	
	protected void setViewableData(MacawListChoice listChoiceItem) {
		this.listChoiceItem = listChoiceItem;
		nameField.setEditable(false);		
		okCancelPanel.setAllowWriteAccess(false);
		restore();
	}
	
	
	// ==========================================
	// Section Errors and Validation
	// ==========================================
	protected boolean isFormValid() {
		
		ArrayList<String> errorMessages = new ArrayList<String>();

		String newFieldValue = nameField.getText().trim();

		MacawException exception = new MacawException();
		//check that name field is not blank
		if (newFieldValue.equals("") == true) {
			String errorMessage
				= MacawMessages.getMessage("variableConstantsEditor.error.blankNameField");
			exception.addErrorMessage(MacawErrorType.EMPTY_LIST_CHOICE, errorMessage);
		}
		
		//check that name value does not appear in the list
		String oldFieldValue = listChoiceItem.getName();
		if (oldFieldValue.equals(newFieldValue) == false) {		
			//title has changed from how it is currently displayed in the list
			if (parentList.containsDisplayName(newFieldValue) == true) {
				//new title has the same name as another item in the list
				String errorMessage
					= MacawMessages.getMessage("supportingDocumentEditor.error.duplicateTitle");
				exception.addErrorMessage(MacawErrorType.EMPTY_LIST_CHOICE, errorMessage);
				errorMessages.add(errorMessage);
			}
		}

		if (exception.getNumberOfErrors() > 0) {
			Log log = sessionProperties.getLog();
			
			//the messages are formatted differently if there
			//is one or multiple errors
			log.displayErrorDialog(exception);
			return false;
		}
		else {
			return true;
		}
	}
	
	// ==========================================
	// Section Interfaces
	// ==========================================

	//Interface: Displayable List Item Adder
	abstract public void addItems(String listOwnerName,
			 			 		  DisplayableList displayableList) throws MacawException;
	
	//Interface: Displayable List Item Editor
	abstract public void editListItem(String listItemOwner,
									  Displayable listItem, 
									  DisplayableList displayableList) throws MacawException;
	
	public Displayable getItem() {
		return listChoiceItem;
	}

	//Interface: Displayable List Item Viewer
	abstract public void viewListItem(Displayable listItem) throws MacawException;

	//Interface: Displayable List Item Viewer
	abstract public void deleteListItems(String listOwnerName, 
										 DisplayableList parentList) throws MacawException;
		
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
	
	// ==========================================
	// Section Overload
	// ==========================================

}

