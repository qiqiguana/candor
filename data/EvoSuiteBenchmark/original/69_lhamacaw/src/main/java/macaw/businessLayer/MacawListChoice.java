package macaw.businessLayer;

import java.util.ArrayList;

import macaw.system.*;
import macaw.util.Displayable;
import macaw.util.ValidationUtility;

/**
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

abstract public class MacawListChoice implements Displayable {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private boolean isNewRecord;
	private int identifier;
	private String name;

	// ==========================================
	// Section Construction
	// ==========================================
	
	public MacawListChoice(int identifier, String name) {
		this.identifier = identifier;
		this.name = name;
	}
	public MacawListChoice(String name) {
		isNewRecord = false;
		this.name = name;		
	}
	
	public MacawListChoice() {
		isNewRecord = false;
		name = "";
	}

	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	public int getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}
	
	public String getDisplayName() {
		return name;
	}

	public String getDisplayItemIdentifier() {
		return String.valueOf(identifier);
	}

	
	/**
	 * @return the isNewRecord
	 */
	public boolean isNewRecord() {
		return isNewRecord;
	}

	/**
	 * @param isNewRecord the isNewRecord to set
	 */
	public void setNewRecord(boolean isNewRecord) {
		this.isNewRecord = isNewRecord;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	// ==========================================
	// Section Errors and Validation
	// ==========================================
	
	static protected ArrayList<String> validateFields(MacawListChoice listChoice) {

		ArrayList<String> errorMessages = new ArrayList<String>();

		if (ValidationUtility.isBlank(listChoice.getName()) == true) {
			String fieldName 
				= MacawMessages.getMessage("general.fields.name");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
										   fieldName);
			errorMessages.add(errorMessage);
		}
			
		return errorMessages;
	}
	
	static public ArrayList<MacawChangeEvent> detectFieldChanges(User user,
																 String messageProperty,
				 												 MacawListChoice originalListChoice,
				 												 MacawListChoice revisedListChoice) { 

		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		String originalName = originalListChoice.getName();
		String revisedName = revisedListChoice.getName();
		if (originalName.equals(revisedName) == false) {
			originalName
				= ValidationUtility.convertEmptyValueToBlank(originalName);
			revisedName
				= ValidationUtility.convertEmptyValueToBlank(revisedName);

			String changeMessage
				= MacawMessages.getMessage(messageProperty,
										   originalName,
										   revisedName);
			MacawChangeEvent changeEvent 
				= new MacawChangeEvent(ChangeEventType.LIST_CHOICE,
									   changeMessage,
									   user.getUserID() );
			changeEvents.add(changeEvent);
		}

		return changeEvents;
	}
	
	public boolean hasSameDisplayName(MacawListChoice listChoice) {
		if (name.equals(listChoice.getName()) == true) {
			return true;
		}
		return false;
	}
	// ==========================================
	// Section Interfaces
	// ==========================================

	//Interface: Cloneable
	protected void cloneAttributes(MacawListChoice macawListChoice) {
		macawListChoice.setIdentifier(identifier);
		macawListChoice.setNewRecord(isNewRecord);
		macawListChoice.setName(name);
	}
	
	// ==========================================
	// Section Overload
	// ==========================================

	abstract public Object clone();
}

