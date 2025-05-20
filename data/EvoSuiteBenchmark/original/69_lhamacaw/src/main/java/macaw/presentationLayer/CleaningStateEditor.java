package macaw.presentationLayer;

import java.util.ArrayList;

import macaw.system.*;
import macaw.businessLayer.*;
import macaw.util.Displayable;
import macaw.util.DisplayableList;


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

public class CleaningStateEditor extends MacawStateEditor {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	
	// ==========================================
	// Section Construction
	// ==========================================
	public CleaningStateEditor(SessionProperties sessionProperties) {
		super(sessionProperties);		
		String dialogTitle 
			= MacawMessages.getMessage("cleaningStateEditor.title");
		setDialogTitle(dialogTitle);
		
		//set the text which will be displayed above the editable fields.
		String dialogInformation
			= MacawMessages.getMessage("cleaningStateEditor.info");
		setDialogInformation(dialogInformation);
		
		dialog.setSize(310, 140);
		dialog.setResizable(false);

	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	
	// ==========================================
	// Section Errors and Validation
	// ==========================================
	
	// ==========================================
	// Section Interfaces
	// ==========================================

	// ==========================================
	// Section Overload
	// ==========================================

	//Interface: Displayable List Item Adder
	public void addItems(String listOwnerName,
						 DisplayableList parentList) throws MacawException {


		CleaningState cleaningState = new CleaningState();
		cleaningState.setNewRecord(true);
		super.setEditableData(cleaningState,
					  	  	  parentList,
					  	  	  listOwnerName);
		show();
		if (isEditingCancelled() == false) {			
			database.addCleaningState(currentUser,
									  cleaningState);
			updateList(parentList);
		}
	}
	
	//Interface: Displayable List Item Editor
	public void editListItem(String listItemOwner,
							 Displayable listItem, 
							 DisplayableList parentList) throws MacawException {

		CleaningState cleaningState = (CleaningState) listItem;
		super.setEditableData(cleaningState,
					  		  parentList,
					  		  listItemOwner);
		show();
		if (isEditingCancelled() == false) {			
			database.updateCleaningState(currentUser,
										 cleaningState);
			updateList(parentList);
		}
	}
	
	//Interface: Displayable List Item Viewer
	public void viewListItem(Displayable listItem) throws MacawException {
		CleaningState cleaningState = (CleaningState) listItem;
		super.setViewableData(cleaningState);
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
		
		ArrayList<CleaningState> cleaningStatesToDelete = new ArrayList<CleaningState>();
		for (Displayable itemToDelete : cleaningStatesToDelete) {
			cleaningStatesToDelete.add((CleaningState) itemToDelete);
		}
		database.deleteCleaningStates(currentUser, 
									  cleaningStatesToDelete);
		updateList(parentList);
	}

	private void updateList(DisplayableList parentList) throws MacawException {
		ArrayList<CleaningState> cleaningStates
			= database.getCleaningStates(currentUser);
				
		ArrayList<Displayable> displayableItems = new ArrayList<Displayable>();
		for (CleaningState currentCleaningState : cleaningStates) {
			displayableItems.add((Displayable) currentCleaningState);			
		}
		
		parentList.setDisplayItems(displayableItems);
	}

}

