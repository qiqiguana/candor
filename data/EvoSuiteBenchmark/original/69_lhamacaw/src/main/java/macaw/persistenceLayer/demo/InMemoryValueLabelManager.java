package macaw.persistenceLayer.demo;

import macaw.businessLayer.*;
import macaw.persistenceLayer.ChangeEventGenerator;
import macaw.system.*;
import macaw.util.SearchUtility;

import java.util.*;


/**
 * Manages instances of {@link macaw.businessLayer.ValueLabel} in memory.  The manager has two
 * minor features that distinguish it from other manager classes:
 * <ul>
 * <li>it tends to update collections of items rather than just one at a time.</li>
 * <li>it checks to make sure that operations which add or edit a collection of 
 * value labels to a variable do not have duplicate value labels within the list of 
 * items to be added.</li>
 * </ul>
 * 
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

public class InMemoryValueLabelManager extends InMemoryCurationConceptManager {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	
	private int valueLabelKey;
	private HashMap<Integer, ValueLabel> valueLabelFromIdentifier;
	// ==========================================
	// Section Construction
	// ==========================================
	public InMemoryValueLabelManager(InMemoryChangeEventManager changeEventManager) {
		super(changeEventManager);
		valueLabelFromIdentifier = new HashMap<Integer, ValueLabel>();
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	public ArrayList<ValueLabel> getValueLabels(User user,
			   									Variable variable) throws MacawException {

		ArrayList<ValueLabel> valueLabels = variable.getValueLabels();
		ArrayList<ValueLabel> cloneLabels = new ArrayList<ValueLabel>();
		for (ValueLabel valueLabel : valueLabels) {
			cloneLabels.add((ValueLabel) valueLabel.clone());
		}
		return cloneLabels;
	}
	
	public void addValueLabels(User user,
							   Variable variable,
		   	 				   ArrayList<ValueLabel> valueLabels) throws MacawException {
				
		//Part I: Validate parameters
		//check that there are no duplicates within the list of revised labels
		//they may be unique with respect to values already in the DB but
		//they may contain some duplicate values
		for (ValueLabel revisedValueLabel : valueLabels) {
			ValueLabel.validateFields(revisedValueLabel);
		}

		checkValueLabelDuplicateWithinList(valueLabels);
		
		for (ValueLabel revisedValueLabel : valueLabels) {
			ValueLabel.validateFields(revisedValueLabel);			
			checkValueLabelDuplicates(variable, 
									  revisedValueLabel);
		}
		
		//Part II: add value labels
		for (ValueLabel currentValueLabelToAdd : valueLabels) {
			valueLabelKey++;
			currentValueLabelToAdd.setIdentifier(valueLabelKey);
			currentValueLabelToAdd.setEditingOperationType(ValueLabel.EditingOperationType.EDIT);
			valueLabelFromIdentifier.put(valueLabelKey, currentValueLabelToAdd);			
			variable.addValueLabel(currentValueLabelToAdd);
		}
		
		//Part III: record the changes
		ArrayList<MacawChangeEvent> changeEvents
			= ChangeEventGenerator.addValueLabelsChange(user,
														variable,
														valueLabels);
		registerChangeEvents(changeEvents);
	}

	public void updateValueLabels(User user,
								  Variable variable,
								  ArrayList<ValueLabel> revisedValueLabels) throws MacawException {

		//Part I: Validate parameters
		//check that there are no duplicates within the list of revised labels
		//they may be unique with respect to values already in the DB but
		//they may contain some duplicate values
		for (ValueLabel revisedValueLabel : revisedValueLabels) {
			ValueLabel.validateFields(revisedValueLabel);
		}

		checkValueLabelDuplicateWithinList(revisedValueLabels);
		
		for (ValueLabel revisedValueLabel : revisedValueLabels) {
			ValueLabel.validateFields(revisedValueLabel);
			checkValueLabelExists(revisedValueLabel);			
			checkValueLabelDuplicates(variable, 
									  revisedValueLabel);
		}
				
		//Part II: Perform the update operations
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		for (ValueLabel revisedValueLabel : revisedValueLabels) {			
			ValueLabel originalValueLabel
				= getOriginalValueLabel(revisedValueLabel);	
			
			ArrayList<MacawChangeEvent> changeEventsForValueLabel
				= ValueLabel.detectFieldChanges(user, 
												variable, 
												originalValueLabel, 
												revisedValueLabel);
			
			
			if (changeEventsForValueLabel.size() > 0) {
				variable.removeValueLabel(originalValueLabel);
				variable.addValueLabel(revisedValueLabel);	
				changeEvents.addAll(changeEventsForValueLabel);
			}		
		}

		//Part III: record changes
		registerChangeEvents(changeEvents);	
	}

	public void deleteValueLabels(User user,
								  Variable variable,
								  ArrayList<ValueLabel> valueLabels) throws MacawException {

		//Part I: Validate parameters
		for (ValueLabel currentValueLabel : valueLabels) {
			checkValueLabelExists(currentValueLabel);
		}

		//Part II: Perform delete operations
		for (ValueLabel currentValueLabel : valueLabels) {
			ValueLabel originalValueLabel
				= getOriginalValueLabel(currentValueLabel);
			variable.removeValueLabel(originalValueLabel);
			valueLabelFromIdentifier.remove(originalValueLabel.getIdentifier());
		}
	
		//Part III: Record changes	
		ArrayList<MacawChangeEvent> changeEvents
			= ChangeEventGenerator.deleteValueLabelsChange(user,
													 	   variable,
													 	   valueLabels);
		registerChangeEvents(changeEvents);
	}
	
	private ValueLabel getOriginalValueLabel(ValueLabel valueLabel) {
		ValueLabel originalValueLabel
			= valueLabelFromIdentifier.get(valueLabel.getIdentifier());
		return originalValueLabel;
	}
		
	public int getValueLabelIdentifier(Variable variable,
									   ValueLabel valueLabel) {		

		ArrayList<ValueLabel> valueLabels
			= variable.getValueLabels();
		
		for (ValueLabel currentValueLabel : valueLabels) {
			if (valueLabel.hasSameDisplayName(currentValueLabel) == true) {
				return currentValueLabel.getIdentifier();
			}
		}
		
		return -1;
	}

	public void clear() {
		valueLabelKey = 0;
		valueLabelFromIdentifier.clear();
	}
	
	// ==========================================
	// Section Errors and Validation
	// ==========================================
	
	private void checkValueLabelDuplicateWithinList(ArrayList<ValueLabel>revisedValueLabels) throws MacawException {


		return;

		/*
		HashSet<String> values = new HashSet<String>();
		HashSet<String> labels = new HashSet<String>();
		
		for (ValueLabel valueLabel : revisedValueLabels) {
			String value = valueLabel.getValue().toUpperCase();
			if (values.contains(value) == true) {
				String errorMessage
					= MacawMessages.getMessage("valueLabel.error.duplicateExists",
												valueLabel.getDisplayName());
				MacawException exception 
					= new MacawException(MacawErrorType.DUPLICATE_VALUE_LABELS_WITHIN_LIST,
										 errorMessage);
				throw exception;
			}
			else {
				values.add(value);
			}
			
			String label = valueLabel.getLabel().toUpperCase();
			if (labels.contains(label) == true) {
				String errorMessage
					= MacawMessages.getMessage("valueLabel.error.duplicateExists",
												valueLabel.getDisplayName());
				MacawException exception 
					= new MacawException(MacawErrorType.DUPLICATE_VALUE_LABELS_WITHIN_LIST,
										 errorMessage);
				throw exception;
			}
			else {
				labels.add(label);
			}	
		}
		
		*/
	}

	private void checkValueLabelExists(ValueLabel valueLabel) throws MacawException {
		int identifier = valueLabel.getIdentifier();
		if (valueLabelFromIdentifier.containsKey(identifier) == false) {
			String errorMessage
				= MacawMessages.getMessage("general.error.nonExistentItem",
										   valueLabel.getDisplayName());
			MacawException exception
				= new MacawException(MacawErrorType.NON_EXISTENT_VALUE_LABELS,
									 errorMessage);
			throw exception;
		}
	}
	
	private void checkValueLabelDuplicates(Variable targetVariable,
										   ValueLabel valueLabel) throws MacawException {
				
		SearchUtility valueSearchUtility = new SearchUtility(valueLabel.getValue());		
		SearchUtility labelSearchUtility = new SearchUtility(valueLabel.getLabel());		

		int targetIdentifier = valueLabel.getIdentifier();

		ArrayList<ValueLabel> valueLabels
			= targetVariable.getValueLabels();
		for (ValueLabel currentValueLabel : valueLabels) {		
			boolean duplicateValue
				= valueSearchUtility.valueExactlyMatches(currentValueLabel.getValue());
			boolean duplicateLabel
				= labelSearchUtility.valueExactlyMatches(currentValueLabel.getLabel());
			if ( (duplicateValue == true) || (duplicateLabel == true) ) {
				
				//either the label or the value match.  Eliminate the case
				//where they have the same identifier (are the same record)
				int currentIdentifier = currentValueLabel.getIdentifier();
				if (currentIdentifier != targetIdentifier) {				
					String valueLabelDisplayName
						= valueLabel.getDisplayName();
					String errorMessage
						= MacawMessages.getMessage("valueLabel.error.duplicateExists",
													valueLabelDisplayName);
					MacawException exception
						= new MacawException(MacawErrorType.DUPLICATE_VALUE_LABEL,
											 errorMessage);
					throw exception;					 
				}
			}			
		}	
	}
	
	// ==========================================
	// Section Interfaces
	// ==========================================

	// ==========================================
	// Section Overload
	// ==========================================

}

