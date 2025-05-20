package macaw.test.curation;

import macaw.util.Displayable;

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

public class TestResultSorter<T> {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================

	// ==========================================
	// Section Construction
	// ==========================================
	public void sort(ArrayList<T> originalList) {
		
		ArrayList<T> unsortedList
			= new ArrayList<T>();
		unsortedList.addAll(originalList);
		originalList.clear();
		
		for (T unsortedItem : unsortedList) {
			Displayable unsortedDisplayableItem 
				= (Displayable) unsortedItem;			
			String unsortedName = unsortedDisplayableItem.getDisplayName();
			
			int insertIndex = 0;
			int numberOfSortedItems = originalList.size();
			for (insertIndex = 0; insertIndex < numberOfSortedItems; insertIndex++) {
				Displayable originalDisplayItem
					= (Displayable) originalList.get(insertIndex);				
				String currentDisplayName
					= originalDisplayItem.getDisplayName();
				if (unsortedName.compareTo(currentDisplayName) <= 0) {
					break;
				}
			}
			originalList.add(insertIndex, unsortedItem);
		}		
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

}

