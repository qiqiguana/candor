package macaw.presentationLayer;

import macaw.system.*;
import macaw.util.Displayable;
import macaw.util.DisplayableList;
import macaw.util.DisplayableListItemAdder;
import macaw.util.DisplayableListItemDeleter;
import macaw.util.DisplayableListItemEditor;
import macaw.util.DisplayableListItemViewer;
import macaw.util.OKClosePanel;
import macaw.businessLayer.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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

public class AliasFilePathEditor implements ActionListener,
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
	private SessionProperties sessionProperties;
	private JDialog dialog;

	private JTextField aliasField;
	private JTextField filePathField;
	private boolean isEditingCancelled;

	private AliasFilePath listChoiceItem;
	private DisplayableList parentList;
	protected String listNameOwner;
	
	private OKClosePanel okCancelPanel;
	private JButton save;
	private JButton close;
	
	private AliasFilePath aliasFilePath;

	
	
	private MacawCurationAPI database;
	private User currentUser;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public AliasFilePathEditor(SessionProperties sessionProperties) {
		UserInterfaceFactory userInterfaceFactory
			= sessionProperties.getUserInterfaceFactory();
		dialog = userInterfaceFactory.createDialog();
		String dialogTitle 
			= MacawMessages.getMessage("aliasFilePathEditor.title");
		dialog.setTitle(dialogTitle);
		
		database 
			= (MacawCurationAPI) sessionProperties.getProperty(SessionProperties.DATABASE);
		currentUser 
			= (User) sessionProperties.getProperty(SessionProperties.CURRENT_USER);
	
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC 
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		panel.add(createFieldPanel(userInterfaceFactory), panelGC);
		
		panelGC.gridy++;
		panelGC.gridx = 0;
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		okCancelPanel = new OKClosePanel(userInterfaceFactory, this);
		save = okCancelPanel.getSaveButton();
		save.addActionListener(this);
		close = okCancelPanel.getCloseButton();
		close.addActionListener(this);
		
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 1.0;		
		panel.add(okCancelPanel.getPanel(), panelGC);
		dialog.getContentPane().add(panel);
		
		dialog.setSize(315, 120);
		dialog.setModal(true);
		dialog.setResizable(false);
	}

	private JPanel createFieldPanel(UserInterfaceFactory userInterfaceFactory) {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC 
			= userInterfaceFactory.createGridBagConstraints();
		String aliasFieldLabelText
			= MacawMessages.getMessage("aliasFilePath.alias.label");
		JLabel aliasFieldLabel 
			= userInterfaceFactory.createLabel(aliasFieldLabelText);
		panel.add(aliasFieldLabel, panelGC);
		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		aliasField = userInterfaceFactory.createTextField(10);
		panel.add(aliasField, panelGC);
		
		panelGC.gridy++;
		panelGC.gridx = 0;
		String filePathFieldLabelText
			= MacawMessages.getMessage("aliasFilePath.filePath.label");
		JLabel filePathFieldLabel
			= userInterfaceFactory.createLabel(filePathFieldLabelText);
		panel.add(filePathFieldLabel, panelGC);
		
		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		filePathField = userInterfaceFactory.createTextField(10);
		panel.add(filePathField, panelGC);

		return panel;
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public void setData(AliasFilePath aliasFilePath) {
		this.aliasFilePath = aliasFilePath;
	}
	
	public void show() {
		dialog.setVisible(true);
	}

	protected void restore() {
		aliasField.setText(listChoiceItem.getAlias());
		filePathField.setText(listChoiceItem.getFilePath());
	}
		
	protected void save() {
		listChoiceItem.setAlias(aliasField.getText().trim());					
		listChoiceItem.setFilePath(filePathField.getText().trim());					
		dialog.setVisible(false);
	}
	
	protected void close() {
		isEditingCancelled = true;
		dialog.setVisible(false);
	}
	
	// ==========================================
	// Section Errors and Validation
	// ==========================================

	
	// ==========================================
	// Section Interfaces
	// ==========================================

	//Interface: Displayable List Item Adder
	public void addItems(String listOwnerName,
						 DisplayableList parentList) throws MacawException {

		AliasFilePath aliasFilePath = new AliasFilePath();
		aliasFilePath.setNewRecord(true);
		setEditableData(aliasFilePath,
					  	parentList,
					  	listOwnerName);
		show();
		
		if (isEditingCancelled == false) {
			database.addAliasFilePath(currentUser,
									  aliasFilePath);
			updateList(parentList);
		}	
	}
		
	//Interface: Displayable List Item Editor
	public void editListItem(String listItemOwner,
							 Displayable listItem, 
							 DisplayableList parentList) throws MacawException {

		AliasFilePath aliasFilePath = (AliasFilePath) listItem;
		setEditableData(aliasFilePath,
					  	parentList,
					  	listItemOwner);
		show();
		if (isEditingCancelled == false) {
			database.updateAliasFilePath(currentUser,
										 aliasFilePath);
			updateList(parentList);
		}
	}
	
	//Interface: Displayable List Item Viewer
	public void viewListItem(Displayable listItem) throws MacawException {
		AliasFilePath aliasFilePath = (AliasFilePath) listItem;
		setViewableData(aliasFilePath);
		show();
	}

	//Interface: Displayable List Item Deleter
	public void deleteListItems(String listOwnerName, 
								DisplayableList parentList) throws MacawException {
				
		ArrayList<Displayable> itemsToDelete
			= parentList.getSelectedItems();
		if (itemsToDelete.size() == 0) {
			return;
		}
		
		ArrayList<AliasFilePath> aliasFilePathsToDelete 
			= new ArrayList<AliasFilePath>();
		for (Displayable itemToDelete : itemsToDelete) {
			aliasFilePathsToDelete.add((AliasFilePath) itemToDelete);
		}
		database.deleteAliasFilePaths(currentUser, 
									  aliasFilePathsToDelete);
		updateList(parentList);
	}
	
	private void updateList(DisplayableList parentList) throws MacawException {
		ArrayList<AliasFilePath> aliasFilePaths
			= database.getAliasFilePaths(currentUser);
				
		ArrayList<Displayable> displayableItems = new ArrayList<Displayable>();
		for (AliasFilePath currentAliasFilePath : aliasFilePaths) {
			displayableItems.add((Displayable) currentAliasFilePath);			
		}
		parentList.setDisplayItems(displayableItems);

	}
	
	protected void setEditableData(AliasFilePath listChoiceItem,
	   		   					   DisplayableList parentList,
	   		   					   String listNameOwner) {
		
		this.listChoiceItem = listChoiceItem;
		this.parentList = parentList;
		this.listNameOwner = listNameOwner;	
		aliasField.setEditable(true);
		filePathField.setEditable(true);
		okCancelPanel.setAllowWriteAccess(true);
		
		restore();
	}
	
	protected void setViewableData(AliasFilePath listChoiceItem) {
		this.listChoiceItem = listChoiceItem;
		aliasField.setEditable(false);		
		filePathField.setEditable(false);
		okCancelPanel.setAllowWriteAccess(false);
		restore();
	}
	
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

	// ==========================================
	// Section Overload
	// ==========================================

}

