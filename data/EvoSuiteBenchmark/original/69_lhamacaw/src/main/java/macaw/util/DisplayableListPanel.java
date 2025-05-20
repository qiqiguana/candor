package macaw.util;

import macaw.businessLayer.User;
import macaw.system.*;

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Provides a general class for add, delete, view, edit operations for a list
 * capable of supporting items that implement the "Displayable" interface.  DisplayableListPanel
 * will change its display depending on the value of "allowWriteAccess".  This parameter
 * means whether or not users should be able to do any of add, edit or delete operations.
 * If allowWriteAccess is set to true, then one or more of these buttons will appear sensitised.
 * 
 * The class can also be parameterised using "allowRecordEditing", which only indicates whether
 * if people can invoke an editor to change a record.  In cases such as SourceVariablePanel,
 * which allow curators to build a list from predefined immutable items, the edit button will be
 * changed to "View".
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

public class DisplayableListPanel implements ActionListener {


	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private boolean isNewRecord;
	
	private ArrayList<Displayable> masterList;
	
	private SessionProperties sessionProperties;
	private Log log;
	private String panelTitleText;
	private String currentUserID;
	private UserInterfaceFactory userInterfaceFactory;

	private DisplayableListParentForm parentForm;
	private DisplayableListItemSelector listItemSelector;
	private DisplayableListItemAdder listItemAdder;
	private DisplayableListItemEditor listItemEditor;
	private DisplayableListItemViewer listItemViewer;
	private DisplayableListItemDeleter listItemDeleter;
	
	private String listOwnerName;
	private boolean allowWriteAccess;
	private boolean allowRecordEditing;
	
	private ArrayList<Displayable> masterSourceVariableCollection;
	
	private JPanel panel;
	private DisplayableList displayableList;
	private JButton select;
	private JButton add;
	private JButton edit;
	private JButton view;
	private JButton delete;

	
	// ==========================================
	// Section Construction
	// ==========================================
	
	public DisplayableListPanel(SessionProperties sessionProperties,
								DisplayableListParentForm parentForm,
								String panelTitleText,
								boolean allowWriteAccess) {

		init(sessionProperties,
			 parentForm,
			 panelTitleText,
			 allowWriteAccess,
			 allowWriteAccess);			 
	}	
	
	public DisplayableListPanel(SessionProperties sessionProperties,
								DisplayableListParentForm parentForm,
								String panelTitleText,
								boolean allowWriteAccess,
								boolean allowRecordEditing) {

		init(sessionProperties,
			 parentForm,
			 panelTitleText,
			 allowWriteAccess,
			 allowRecordEditing);
		
		//if allowWriteAccess is true, then a separate flag is used
		//to see if writing privileges include editing a specific record.
		//Otherwise, writing could be limited to add or delete from a list
		//using items which are themselves immutable.
	}

	private void init(SessionProperties sessionProperties,
					  DisplayableListParentForm parentForm,
					  String panelTitleText,
					  boolean allowWriteAccess,
					  boolean allowRecordEditing) {
		
		this.sessionProperties = sessionProperties;
		log = sessionProperties.getLog();
		this.parentForm = parentForm;
		this.panelTitleText = panelTitleText;
		this.allowWriteAccess = allowWriteAccess;
		this.allowRecordEditing = allowRecordEditing;

		this.userInterfaceFactory 
			= sessionProperties.getUserInterfaceFactory();
		User currentUser 
			= (User) sessionProperties.getProperty(SessionProperties.CURRENT_USER);
		currentUserID = currentUser.getUserID();		
		masterList = new ArrayList<Displayable>();
		displayableList = new DisplayableList(userInterfaceFactory);

		String selectText
			= MacawMessages.getMessage("general.buttons.select");
		select = userInterfaceFactory.createButton(selectText);
		select.addActionListener(this);

		String addText
			= MacawMessages.getMessage("general.buttons.add");
		add = userInterfaceFactory.createButton(addText);
		add.addActionListener(this);

		String editText
			= MacawMessages.getMessage("general.buttons.edit");
		edit = userInterfaceFactory.createButton(editText);
		edit.addActionListener(this);

		String viewText
			= MacawMessages.getMessage("general.buttons.view");
		view = userInterfaceFactory.createButton(viewText);
		view.addActionListener(this);

		String deleteText
			= MacawMessages.getMessage("general.buttons.delete");
		delete = userInterfaceFactory.createButton(deleteText);
		delete.addActionListener(this);
	}
	
	private void buildUI() {
		panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		JLabel titleLabel
			= userInterfaceFactory.createLabel(panelTitleText);
		panel.add(titleLabel, panelGC);
		
		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weightx = 100;
		panelGC.weighty = 100;
		JScrollPane scrollPane = displayableList.getListScrollPane();
		panel.add(scrollPane, panelGC);
	
		panelGC.gridy++;
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;
		panelGC.weighty = 0;
		panel.add(createControlButtonPanel(), panelGC);
				
	}		
	
	private JPanel createControlButtonPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();

		if ((listItemSelector != null) && (allowWriteAccess == true))  {
			panel.add(select, panelGC);		
			panelGC.gridx++;
		}

		if (listItemAdder != null) {
			panel.add(add, panelGC);		
			panelGC.gridx++;
		}

		if ((allowRecordEditing == true) && 
			(listItemEditor != null)) {				
				panel.add(edit, panelGC);
				panelGC.gridx++;
		}
		else if (listItemViewer != null) {
			panel.add(view, panelGC);
			panelGC.gridx++;
		}
				
		if (listItemDeleter != null) {	
			panel.add(delete, panelGC);		
		}		
		return panel;
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	
	public void setNewRecord(boolean isNewRecord) {
		this.isNewRecord = isNewRecord;
		updateButtonStates();
	}
	
	public JPanel getPanel() {
		buildUI();
		return panel;
	}

	public void setDisplayableListItemSelector(DisplayableListItemSelector listItemSelector) {
		this.listItemSelector = listItemSelector;
	}
	
	public void setDisplayableListItemAdder(DisplayableListItemAdder listItemAdder) {
		this.listItemAdder = listItemAdder;	
	}
	
	public void setDisplayableListItemEditor(DisplayableListItemEditor listItemEditor) {
		this.listItemEditor = listItemEditor;
	}
	
	public void setDisplayableListItemViewer(DisplayableListItemViewer listItemViewer) {
		this.listItemViewer = listItemViewer;	
	}

	public void setDisplayableListItemDeleter(DisplayableListItemDeleter listItemDeleter) {
		this.listItemDeleter = listItemDeleter;	
	}
			
	public void addListItem() {
		try {
			if (listItemAdder == null) {
				String errorMessage 
					= MacawMessages.getMessage("displayListPanel.error.missingAdder");
				MacawException exception
					= new MacawException(MacawErrorType.MISSING_DISPLAY_LIST_ADDER,
										 errorMessage);
				throw exception;
			}

			parentForm.commitChanges();

			//assume modal list adder
			listItemAdder.addItems(listOwnerName,
					 			   displayableList);		
			updateButtonStates();			
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}
	}
	
	private void editSelectedListItem() {
		if (displayableList.itemsAreSelected() == false) {
			//no item selected
			String errorMessage
				= MacawMessages.getMessage("general.error.unselectedItems");
			Log log
				= (Log) sessionProperties.getProperty(SessionProperties.LOG);
			log.displayErrorDialog(errorMessage);
			return;
		}

		try {			
			if (listItemEditor == null) {
				String errorMessage 
					= MacawMessages.getMessage("displayListPanel.error.missingEditor");
				MacawException exception
					= new MacawException(MacawErrorType.MISSING_DISPLAY_LIST_EDITOR,
										 errorMessage);
				throw exception;
			}

			parentForm.commitChanges();

			//assume modal control for editor
			Displayable selectedListItem
				= displayableList.getSelectedItem();
		
			String oldDisplayName = selectedListItem.getDisplayName();
			listItemEditor.editListItem(listOwnerName,
										selectedListItem, 
										displayableList);		
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}
	}
	
	private void viewSelectedListItem() {
		if (displayableList.itemsAreSelected() == false) {
			//no item selected
			String errorMessage
				= MacawMessages.getMessage("general.error.unselectedItems");
			Log log
				= (Log) sessionProperties.getProperty(SessionProperties.LOG);
			log.displayErrorDialog(errorMessage);
			return;
		}

		try {

			if (listItemViewer == null) {
				String errorMessage 
					= MacawMessages.getMessage("displayListPanel.error.missingViewer");
				MacawException exception
					= new MacawException(MacawErrorType.MISSING_DISPLAY_LIST_SELECTOR,
										 errorMessage);
				throw exception;
			}
			
			Displayable selectedListItem
				= displayableList.getSelectedItem();
			//invoke a viewer
			listItemViewer.viewListItem(selectedListItem);
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}
	}
	
	
	public void deleteSelectedListItems() {
		if (displayableList.itemsAreSelected() == false) {
			//no item selected
			String errorMessage
				= MacawMessages.getMessage("general.error.unselectedItems");
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(errorMessage);
			return;
		}

		try {		
			parentForm.commitChanges();
			
			//record change events associated with deletion
			if (listItemDeleter == null) {
				String errorMessage 
					= MacawMessages.getMessage("displayListPanel.error.missingDeleter");
				MacawException exception
					= new MacawException(MacawErrorType.MISSING_DISPLAY_LIST_DELETER,
										 errorMessage);
				throw exception;
			}

			String dialogTitle
				= MacawMessages.getMessage("general.warning.title");
			String warningMessage
				= MacawMessages.getMessage("general.warning.deletionIsPermanent");
		
			int result = JOptionPane.showConfirmDialog(null,
													   warningMessage,
													   dialogTitle,
													   JOptionPane.YES_NO_OPTION,
													   JOptionPane.WARNING_MESSAGE);
			if (result != JOptionPane.YES_OPTION) {
				return;
			}
			
			listItemDeleter.deleteListItems(listOwnerName,
											displayableList);

			//KLG MIGRATE
			updateButtonStates();
		}
		catch(MacawException exception) {
			log.displayErrorDialog(exception);
		}

	}

	private void selectListItems() {
		try {
			listItemSelector.selectListItems(listOwnerName,
											 displayableList);		
			updateButtonStates();
		}
		catch(MacawException exception) {
			log.displayErrorDialog(exception);
		}
	}
	
	public void updateButtonStates() {
		if (allowWriteAccess == true) {
			if (isNewRecord == true) {
				add.setEnabled(false);
				delete.setEnabled(false);
				edit.setEnabled(false);
				view.setEnabled(false);
				select.setEnabled(false);
			}
			else {				
				//"Add" should be visible
				add.setEnabled(true);
				select.setEnabled(true);
			
				if (displayableList.isEmpty() == true) {
				
					//"Delete" should not be sensitive for an empty list
					delete.setEnabled(false);
				
					//edit or view - whichever one is showing - 
					//should not be enabled if the list is empty.
					if (allowRecordEditing == true) {
						edit.setEnabled(false);
					}
					else {
						view.setEnabled(false);
					}			
				}
				else {
					delete.setEnabled(true);
				
					if (allowRecordEditing == true) {
						edit.setEnabled(true);
					}
					else {
						view.setEnabled(true);				
					}
				}
			}
		}
		else {
			//"Add" and "Delete" buttons should appear disabled
			add.setEnabled(false);
			delete.setEnabled(false);

			//in this case, only view will appear
			if (displayableList.isEmpty() == true) {
				view.setEnabled(false);
			}
			else {
				if (isNewRecord == false) {
					view.setEnabled(true);		
				}
			}
		}		
	}
	
	public void setDisplayableItems(ArrayList<Displayable> masterList) {
		this.masterList = masterList;
		displayableList.setDisplayItems(masterList);		
		updateButtonStates();				

		//restore();
	}
	
	public void save() {
		masterList.clear();
		ArrayList<Displayable> currentList
			= displayableList.getAllItems();
		masterList.addAll(currentList);
	}
	
	/**
	private void restore() {
		ArrayList<Displayable> currentList = new ArrayList<Displayable>();			
		for (Displayable displayableItem : masterList) {
			Displayable cloneItem = (Displayable) displayableItem.clone();
			currentList.add(cloneItem);
		}
		
		displayableList.setDisplayItems(currentList);
	
		updateButtonStates();				
	}
	*/
	
	public ArrayList<Displayable> getAllListItems() {
		return displayableList.getAllItems();		
	}

	public void setListOwnerName(String listOwnerName) {
		this.listOwnerName = listOwnerName;
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

		if (button == select) {
			selectListItems();
		}
		else if (button == add) {
			addListItem();
		}
		else if (button == edit) {
			editSelectedListItem();
		}
		else if (button == view) {
			viewSelectedListItem();
		}
		else if (button == delete) {
			deleteSelectedListItems();
		}
	}

	
	// ==========================================
	// Section Overload
	// ==========================================

}

