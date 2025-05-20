package macaw.persistenceLayer.demo;

import macaw.system.*;
import macaw.businessLayer.*;

import java.util.ArrayList;
import java.util.Date;

/**
 * A manager class that is called by various classes to make a record of editing changes
 * made to instances of objects found in <code>macaw.model</code>.  It stores records of 
 * these changes in memory.
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

public class InMemoryChangeEventManager {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private ArrayList<MacawChangeEvent> changeEvents;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public InMemoryChangeEventManager() {
		changeEvents = new ArrayList<MacawChangeEvent>();
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	/**
	 * provides a collection of change events that are associated with the curation of
	 * a given variable.  These changes can include:
	 * <ul>
	 * <li>adding or deleting a variable</li>
	 * <li>changing form fields for a variable</li>
	 * <li>associating and disassociating ontology terms</li>
	 * <li>associating and disassociating supporting documents</li>
	 * <li>associating and disassociating source variables with a derived variable</li>
	 * </ul>
	 */
	public ArrayList<MacawChangeEvent> getChangeHistoryForVariable(User user,
				   												   Variable variable) throws MacawException {
		int variableID = variable.getIdentifier();
		
		ArrayList<MacawChangeEvent> results 
			= new ArrayList<MacawChangeEvent>();
		for (MacawChangeEvent changeEvent : changeEvents) {
			Integer currentVariableOwnerID = changeEvent.getVariableOwnerID();
			if (currentVariableOwnerID != null) {
				if ((currentVariableOwnerID.intValue() > 0) && 
					(currentVariableOwnerID.intValue() == variableID) ) {
					results.add(changeEvent);
				}
			}
		}	
		return sortChangeEventsByDate(results);
	}
	
	/**
	 * provides the collection of change events associated with curating a given 
	 * supporting document.
	 * 
	 * @param user
	 * @param supportingDocument the target of change.
	 * @throws MacawException
	 */
	public ArrayList<MacawChangeEvent> getChangeHistoryForSupportingDocument(User user,
						 													 SupportingDocument supportingDocument) throws MacawException {
		int supportingDocumentID = supportingDocument.getIdentifier();
		ArrayList<MacawChangeEvent> results 
			= new ArrayList<MacawChangeEvent>();
		
		for (MacawChangeEvent changeEvent : changeEvents) {
			if (changeEvent.getChangeType() == ChangeEventType.SUPPORTING_DOCUMENT) {				
				int changedObjectIdentifier = changeEvent.getChangedObjectIdentifier();
				if (changedObjectIdentifier == supportingDocumentID) {
					results.add(changeEvent);
				}
			}
		}

		return sortChangeEventsByDate(results);
	}

	/**
	 * provides a collection of change events for value labels associated with
	 * a given variable
	 * @param user
	 * @param variable - the variable that owns the value labels
	 * @throws MacawException
	 */
	public ArrayList<MacawChangeEvent> getChangeHistoryForValueLabels(User user,
																	  Variable variable) throws MacawException {

		int variableID = variable.getIdentifier();
		ArrayList<MacawChangeEvent> results 
			= new ArrayList<MacawChangeEvent>();

		for (MacawChangeEvent changeEvent : changeEvents) {
			if (changeEvent.getVariableOwnerID().equals(variableID)) {
				results.add(changeEvent);
			}
		}

		return sortChangeEventsByDate(results);
	}

	/*
	 * provides a collection of change events made by a given user.  The results are
	 * sorted in reverse chronological order
	 */
	public ArrayList<MacawChangeEvent> getChangeHistoryByUser(User user) throws MacawException {
		String userID = user.getUserID();
		
		ArrayList<MacawChangeEvent> results 
			= new ArrayList<MacawChangeEvent>();
	
		for (MacawChangeEvent changeEvent : changeEvents) {
			if (changeEvent.getUserID().equals(userID) ) {
				results.add(changeEvent);
			}
		}
	
		return sortChangeEventsByDate(results);
	}

	/**
	 * retrieve all change events that are associated with curation changes made to list choice
	 * classes, such as {@link macaw.businessLayer.AvailabilityState}, {@link macaw.businessLayer.CleaningState},
	 * {@link macaw.businessLayer.Category} and {@link macaw.businessLayer.AliasFilePath}.
	 * 
	 * @throws MacawException
	 */
	public ArrayList<MacawChangeEvent> getChangeHistoryForListChoices() throws MacawException {
		ArrayList<MacawChangeEvent> results 
			= new ArrayList<MacawChangeEvent>();
		
		for (MacawChangeEvent changeEvent : changeEvents) {
			if (changeEvent.getChangeType() == ChangeEventType.LIST_CHOICE) {
				results.add(changeEvent);
			}
		}
		
		return sortChangeEventsByDate(results);
	}

	/**
	 * add a change event that is a result of some curation operation.
	 * @param event
	 * @throws MacawException
	 */
	public void registerChangeEvent(MacawChangeEvent event) throws MacawException {
		changeEvents.add(event);
	}
	
	/**
	 * add a collection of change events that are a result of some curation operation.
	 * @param events
	 * @throws MacawException
	 */
	public void registerChangeEvents(ArrayList<MacawChangeEvent> events) throws MacawException {
		changeEvents.addAll(events);
	}
	
	/**
	 * Sort change events by descending order of dates.
	 * @param changeEvents
	 * @return
	 */
	private ArrayList<MacawChangeEvent> sortChangeEventsByDate(ArrayList<MacawChangeEvent> changeEvents) {
		ArrayList<MacawChangeEvent> sortedChangeEvents 
			= new ArrayList<MacawChangeEvent>();
		
		//sort should be improved later on...
		for (MacawChangeEvent unsortedChangeEvent : changeEvents) {
			Date unsortedDate = unsortedChangeEvent.getDate();
			int numberOfSortedEvents = sortedChangeEvents.size();
			int sortPosition = 0;
			for (sortPosition = 0; sortPosition < numberOfSortedEvents; sortPosition++) {
				Date sortedDate
					= sortedChangeEvents.get(sortPosition).getDate();
				if (unsortedDate.compareTo(sortedDate) < 0) {
					break;
				}
			}
			sortedChangeEvents.add(sortPosition, unsortedChangeEvent);			
		}
		
		return sortedChangeEvents;		
	}
	
	/**
	 * remove all change events
	 */
	public void clear() {
		changeEvents.clear();
	}
	
	public ArrayList<MacawChangeEvent> getAllChanges() {
		return changeEvents;
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

