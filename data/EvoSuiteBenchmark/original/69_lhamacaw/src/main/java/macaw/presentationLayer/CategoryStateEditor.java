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

public class CategoryStateEditor extends MacawStateEditor {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	
	// ==========================================
	// Section Construction
	// ==========================================
	public CategoryStateEditor(SessionProperties sessionProperties) {
		super(sessionProperties);		
		String dialogTitle 
			= MacawMessages.getMessage("categoryStateEditor.title");
		setDialogTitle(dialogTitle);
		
		//set the text which will be displayed above the editable fields.
		String dialogInformation
			= MacawMessages.getMessage("categoryStateEditor.info");
		setDialogInformation(dialogInformation);
		
		dialog.setSize(315, 120);
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

		Category category = new Category();
		category.setNewRecord(true);
		super.setEditableData(category,
					  	  	  parentList,
					  	  	  listOwnerName);
		show();
		if (isEditingCancelled() == false) {			
			database.addCategory(currentUser,
								 category);
			updateList(parentList);
		}
	}
		
	//Interface: Displayable List Item Editor
	public void editListItem(String listItemOwner,
							 Displayable listItem, 
							 DisplayableList parentList) throws MacawException {

		Category category = (Category) listItem;
		super.setEditableData(category,
					  		  parentList,
					  		  listItemOwner);
		show();
		if (isEditingCancelled() == false) {			
			database.updateCategory(currentUser,
									category);
			updateList(parentList);
		}
	}
	
	//Interface: Displayable List Item Viewer
	public void viewListItem(Displayable listItem) throws MacawException {
		Category category = (Category) listItem;
		super.setViewableData(category);
		show();
	}

	//Interface: Displayable List Item Deleter
	public void deleteListItems(String listOwnerName, 
			 					DisplayableList parentList) throws MacawException {
	
		ArrayList<Displayable> itemsToDelete = parentList.getSelectedItems();
		if (itemsToDelete.size() == 0) {
			return;
		}

		ArrayList<Category> categoriesToDelete 
			= new ArrayList<Category>();
		for (Displayable itemToDelete : itemsToDelete) {
			categoriesToDelete.add((Category) itemToDelete);
		}		

		database.deleteCategories(currentUser, categoriesToDelete);
		updateList(parentList);		
	}
	
	private void updateList(DisplayableList parentList) throws MacawException {
		ArrayList<Category> categories
			= database.getCategories(currentUser);
				
		ArrayList<Displayable> displayableItems = new ArrayList<Displayable>();
		for (Category currentCategory : categories) {
			displayableItems.add((Displayable) currentCategory);			
		}
		parentList.setDisplayItems(displayableItems);
	}

	
}

