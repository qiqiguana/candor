package macaw.util;

import macaw.system.UserInterfaceFactory;

import javax.swing.*;
import java.util.*;


/**
 * A general purpose list that displays a collection of objects that implement the
 * {@link macaw.util.Displayable} interface.  The list displays items in alphabetical
 * ordering.  It knows nothing else about the nature of list items, which may be implemented
 * using any number of different classes.  DisplayableList manages its own instance
 * of {@link javax.swing.JScrollPane}.
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

public class DisplayableList {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================

	//Default list model.  I would have used a Vector or an 
	//ArrayList but found fewer problems with updating the DefaultListModel
	//than trying to update a Collection. Note that as of JDK1.5, DefaultListModel
	//is not actually part of Collections but its API is loosely based on it.	
	private DefaultListModel listData;
	
	private HashMap<String, Displayable> itemFromDisplayName;	
	private JList list;
	private JScrollPane scrollPane;
	
	// ==========================================
	// Section Construction
	// ==========================================
	
	public DisplayableList(UserInterfaceFactory userInterfaceFactory) {
		init(userInterfaceFactory);
	}
	
	public DisplayableList(UserInterfaceFactory userInterfaceFactory,
						   ArrayList<Displayable> displayableItems) {
		
		init(userInterfaceFactory);
		
		for (Displayable displayableItem : displayableItems) {
			addDisplayableItem(displayableItem);
		}
	}

	private void init(UserInterfaceFactory userInterfaceFactory) {
		list = userInterfaceFactory.createList();
		listData = new DefaultListModel();
		list.setModel(listData);
		scrollPane = userInterfaceFactory.createScrollPane(list);
		itemFromDisplayName = new HashMap<String, Displayable>();
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	public void addDisplayableItem(Displayable displayableItem) {
		String displayName = displayableItem.getDisplayName();
		if (listData.contains(displayName) == false) {			
			//ensure that display names are unique
			listData.addElement(displayName);
			itemFromDisplayName.put(displayName,
									displayableItem);		
		}

		//list.updateUI();		
		//selectItem(displayableItem);
	}

	public void addDisplayableItems(ArrayList<Displayable> displayableItems) {
		int numberOfAddedItems = displayableItems.size();
		if (numberOfAddedItems == 0) {
			return;
		}
		
		for (Displayable displayableItem : displayableItems) {
			String displayName = displayableItem.getDisplayName();
			if (listData.contains(displayName) == false) {
				//ensure that display names are unique
				listData.addElement(displayName);
				itemFromDisplayName.put(displayName,
										displayableItem);
			}
		}

		list.updateUI();
		
		//pick the last item in the list of displayable items and 
		//make that item the selected item in the list
		Displayable lastAddedItem = displayableItems.get(numberOfAddedItems - 1);
		selectItem(lastAddedItem);		
	}
	
	public void clear() {
		listData.clear();
		itemFromDisplayName.clear();	
	}

	public boolean containsDisplayName(String displayName) {
		return listData.contains(displayName);
	}

	public void deleteSelectedItems() {
		if (isEmpty() == true) {
			return;
		}

		Object[] selectedValues = list.getSelectedValues();
		for (int i = 0; i < selectedValues.length; i++) {
			String displayName
				= (String) selectedValues[i];
			listData.removeElement(displayName);
			itemFromDisplayName.remove(displayName);			
		}		
		updateUI();
		list.clearSelection();
	}

	public ArrayList<Displayable> getAllItems() {
		ArrayList<Displayable> allItems
			= new ArrayList<Displayable>();
		Enumeration elements = listData.elements();
		while (elements.hasMoreElements()) {
			String currentDisplayName = (String) elements.nextElement();
				Displayable selectedItem
					= (Displayable) itemFromDisplayName.get(currentDisplayName);
				allItems.add(selectedItem);
		}
		return allItems;
	}
	
	public JScrollPane getListScrollPane() {
		return scrollPane;
	}

	public Displayable getSelectedItem() {
		String selectedDisplayName
			= (String) list.getSelectedValue();
		if (selectedDisplayName == null) {
			return null;
		}
		
		Displayable selectedItem
			= (Displayable) itemFromDisplayName.get(selectedDisplayName);
		return selectedItem;
	}
	
	public ArrayList<Displayable> getSelectedItems() {
		ArrayList<Displayable> results = new ArrayList<Displayable>();
		if (isEmpty() == true) {
			return results;
		}
		
		Object[] selectedDisplayNames
			= (Object[]) list.getSelectedValues();
		
		for (int i = 0; i < selectedDisplayNames.length; i++) {
			Displayable selectedItem
				= (Displayable) itemFromDisplayName.get(selectedDisplayNames[i]);
			results.add(selectedItem);
		}
		
		return results;
	}	
	
	public boolean isEmpty() {
		if (listData.size() == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean itemsAreSelected() {
		if (isEmpty() == true) {
			return false;
		}
		
		Object[] selectedDisplayNames
			= (Object[]) list.getSelectedValues();
		if (selectedDisplayNames.length == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	public void refreshList() {
		listData.clear();
		
		ArrayList<Displayable> listItems = new ArrayList<Displayable>();
		listItems.addAll(itemFromDisplayName.values());
		for (Displayable currentListItem : listItems) {
			listData.addElement(currentListItem.getDisplayName());
		}
		sort();
		updateUI();
	}

	public void selectItem(Displayable displayableItem) {
		if (isEmpty() == true) {
			return;
		}
		
		list.setSelectedValue(displayableItem.getDisplayName(), true);
	}

	public void setDisplayItems(ArrayList<Displayable> displayableItems) {		
		clear();				
				
		int numberOfAddedItems = displayableItems.size();
		if (numberOfAddedItems == 0) {
			return;
		}
		
		for (Displayable displayableItem : displayableItems) {
			String displayName = displayableItem.getDisplayName();
			if (listData.contains(displayName) == false) {
				//ensure that display names are unique
				listData.addElement(displayName);
				itemFromDisplayName.put(displayName,
										displayableItem);
			}
		}
		
		list.updateUI();
				
		if (displayableItems.size() > 0) {
			list.setSelectedIndex(0);
		}
	}
	
	public void sort() {
		ListModel model = list.getModel();
		int numItems = model.getSize();
		String[] displayNames = new String[numItems];
		for (int i=0;i<numItems;i++) {
			displayNames[i] = (String)model.getElementAt(i);
		}
		
		Arrays.sort(displayNames);
		list.setListData(displayNames);
		list.revalidate();
	}
	
	public void updateDisplayName(String oldDisplayName,
								  Displayable revisedListItem) {

		itemFromDisplayName.remove(oldDisplayName);
		listData.removeElement(oldDisplayName);

		String newDisplayName = revisedListItem.getDisplayName();
		listData.addElement(newDisplayName);
		itemFromDisplayName.put(newDisplayName, revisedListItem);		
		sort();
		updateUI();
		selectItem(revisedListItem);
	}

	public void updateUI() {
		list.updateUI();
	}

	// ==========================================
	// Section Errors and Validation
	// ==========================================

	// ==========================================
	// Section Interfaces
	// ==========================================

	// ==========================================
	// Section Overload
	// ==========================================

}

