package macaw.businessLayer;

import macaw.system.*;
import macaw.util.Displayable;
import macaw.util.ValidationUtility;

import java.util.ArrayList;

/**
 * describes an answer to a survey question
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

public class ValueLabel implements Cloneable, Displayable {

	// ==========================================
	// Section Constants
	// ==========================================
	public enum EditingOperationType {NEW, EDIT, DELETE, CANCEL};
	
	// ==========================================
	// Section Properties
	// ==========================================
	private int identifier;
	private String value;
	private String label;
	private boolean isMissingValue;
	private EditingOperationType editingOperationType;
	// ==========================================
	// Section Construction
	// ==========================================
	public ValueLabel() {
		identifier = 0;
		value = "";
		label = "";
		isMissingValue = false;
		editingOperationType = EditingOperationType.NEW;
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	/**
	 * @return the identifier
	 */
	public int getIdentifier() {
		return identifier;
	}


	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}


	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}


	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the isMissingValue
	 */
	public boolean isMissingValue() {
		return isMissingValue;
	}

	/**
	 * @param isMissingValue the isMissingValue to set
	 */
	public void setMissingValue(boolean isMissingValue) {
		this.isMissingValue = isMissingValue;
	}	
	
	public void setEditingOperationType(EditingOperationType editingOperationType) {
		this.editingOperationType = editingOperationType;		
	}
	
	public EditingOperationType getEditingOperationType() {
		return editingOperationType;
	}
	
	public boolean isNewRecord() {
		if (editingOperationType == EditingOperationType.NEW) {
			return true;
		}
		return false;
	}

	public boolean isEditingExistingRecord() {
		if (editingOperationType == EditingOperationType.EDIT) {
			return true;
		}
		return false;
	}

	public boolean isDeletedNewRecord() {
		if (editingOperationType == EditingOperationType.CANCEL) {
			return true;
		}
		return false;
	}

	public boolean isDeletedRecord() {
		if (editingOperationType == EditingOperationType.DELETE) {
			return true;
		}
		return false;
	}

	public boolean hasSameDisplayName(ValueLabel valueLabel) {
		if (getDisplayName().equals(valueLabel.getDisplayName()) == true) {
			return true;
		}
		return false;
	}
	

	// ==========================================
	// Section Errors and Validation
	// ==========================================
	public static void validateFields(ValueLabel valueLabel) throws MacawException {
		ArrayList<String> errorMessages = new ArrayList<String>();

		/**
		if (ValidationUtility.isBlank(valueLabel.getValue()) == true) {
			String valueText
				= MacawMessages.getMessage("valueLabel.value.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
										   valueText);
			errorMessages.add(errorMessage);
		}
		*/
	
		//some legacy variables do not appear to have a value.
		/*
		if (ValidationUtility.isBlank(valueLabel.getLabel()) == true) {
			String labelText
				= MacawMessages.getMessage("valueLabel.label.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
										   labelText);
			errorMessages.add(errorMessage);
		}
		*/
		
		if (errorMessages.size() > 0) {
			MacawException exception
				= new MacawException();
			for (String errorMessage : errorMessages) {
				exception.addErrorMessage(MacawErrorType.INVALID_VALUE_LABEL, 
										  errorMessage);
			}
			exception.printErrors();
			throw exception;
		}
	}
	
	static public boolean differencesExist(User user,
										   Variable ownerVariable,
										   ValueLabel originalValueLabel,
										   ValueLabel revisedValueLabel) {
	
		ArrayList<MacawChangeEvent> changeEvents
			= ValueLabel.detectFieldChanges(user, 
											ownerVariable, 
											originalValueLabel, 
											revisedValueLabel);
		if (changeEvents.size() > 0) {
			return true;
		}
		return false;		
	}
	
	static public ArrayList<MacawChangeEvent> detectFieldChanges(User user,
															     Variable ownerVariable,
															     ValueLabel originalValueLabel,
															     ValueLabel revisedValueLabel) {

		String userID = user.getUserID();
		String ownerVariableName = ownerVariable.getDisplayName();
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		
		String originalValue = originalValueLabel.getValue();
		String revisedValue = revisedValueLabel.getValue();
		if (originalValue.equals(revisedValue) == false) {
			originalValue
				= ValidationUtility.convertEmptyValueToBlank(originalValue);
			revisedValue
				= ValidationUtility.convertEmptyValueToBlank(revisedValue);
		
			String changeMessage
				= MacawMessages.getMessage("valueLabel.value.saveChanges",
										   originalValueLabel.getDisplayName(),
										   ownerVariableName,
										   originalValue,
										   revisedValue);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VALUE_LABEL,
									   changeMessage, 
									   userID);
			changeEvent.setVariableOwnerID(ownerVariable.getIdentifier());
			changeEvent.setChangedObjectIdentifier(originalValueLabel.getIdentifier());
			changeEvents.add(changeEvent);
		}
		
		String originalLabel = originalValueLabel.getLabel();
		String revisedLabel = revisedValueLabel.getLabel();
		if (originalLabel.equals(revisedLabel) == false) {
			String changeMessage
				= MacawMessages.getMessage("valueLabel.label.saveChanges",
										   originalValueLabel.getDisplayName(),
										   ownerVariableName,
										   originalLabel,
										   revisedLabel);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VALUE_LABEL,
									   changeMessage, 
									   userID);
			changeEvent.setVariableOwnerID(ownerVariable.getIdentifier());
			changeEvent.setChangedObjectIdentifier(originalValueLabel.getIdentifier());
			changeEvents.add(changeEvent);			
		}
		
		boolean originalIsMissingValue = originalValueLabel.isMissingValue();
		boolean revisedIsMissingValue = revisedValueLabel.isMissingValue();
		if (originalIsMissingValue != revisedIsMissingValue) {
			String changeMessage
				= MacawMessages.getMessage("valueLabel.isMissingValue.saveChanges",
										   originalValueLabel.getDisplayName(),
										   ownerVariableName,
										   String.valueOf(originalIsMissingValue),
										   String.valueOf(revisedIsMissingValue));
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VALUE_LABEL,
									   changeMessage, 
									   userID);
			changeEvent.setVariableOwnerID(ownerVariable.getIdentifier());
			changeEvent.setChangedObjectIdentifier(originalValueLabel.getIdentifier());
			changeEvents.add(changeEvent);			
		}
		
		return changeEvents;
	}
	
	
	// ==========================================
	// Section Interfaces
	// ==========================================

	//Interface: Displayable
	public String getDisplayName() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(getValue());
		buffer.append("-");
		buffer.append(getLabel());
		return buffer.toString();
	}

	public String getDisplayItemIdentifier() {
		return String.valueOf(identifier);
	}

	//Interface: Cloneable
	public Object clone() {
		ValueLabel cloneValueLabel = new ValueLabel();
		cloneValueLabel.setIdentifier(identifier);
		cloneValueLabel.setLabel(label);
		cloneValueLabel.setMissingValue(isMissingValue);
		cloneValueLabel.setValue(value);
		cloneValueLabel.setEditingOperationType(editingOperationType);
		
		return cloneValueLabel;
	}
	
	// ==========================================
	// Section Overload
	// ==========================================

}

