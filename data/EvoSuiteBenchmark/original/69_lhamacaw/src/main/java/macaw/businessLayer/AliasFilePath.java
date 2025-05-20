package macaw.businessLayer;

import java.util.ArrayList;

import macaw.system.*;
import macaw.util.Displayable;
import macaw.util.ValidationUtility;


/**
 * is an association between a logical location for data associated 
 * with a variable and a physical location
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

public class AliasFilePath implements Displayable, Cloneable {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	
	private int identifier;
	private String alias;
	private String filePath;
	
	private boolean isNewRecord;
	
	// ==========================================
	// Section Construction
	// ==========================================
	
	public AliasFilePath(String alias, 
						 String filePath) {
		
		identifier = 0;
		this.alias = alias;
		this.filePath = filePath;
		isNewRecord = false;		
	}
	
	public AliasFilePath() {		
		identifier = 0;
		alias = "";
		filePath = "";
		isNewRecord = false;
	}

	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	
	/**
	 * @param isNewRecord the isNewRecord to set
	 */
	public void setNewRecord(boolean isNewRecord) {
		this.isNewRecord = isNewRecord;
	}

	
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
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
		
	
	static public ArrayList<MacawChangeEvent> detectFieldChanges(User user,
																 AliasFilePath originalAliasFilePath,
																 AliasFilePath revisedAliasFilePath) {

		String userID = user.getUserID();
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();

		String oldAlias = originalAliasFilePath.getAlias();
		String revisedAlias = revisedAliasFilePath.getAlias();
		if (oldAlias.equals(revisedAlias) == false) {
			String changedMessage
				= MacawMessages.getMessage("ontologyTerm.term.saveChanges",
											oldAlias,
											revisedAlias);

			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.ALIAS_FILE_PATH,
									   changedMessage, 
									   userID);
			changeEvent.setChangedObjectIdentifier(revisedAliasFilePath.getIdentifier());
			changeEvents.add(changeEvent);
		}
		
		String oldFilePath = originalAliasFilePath.getFilePath();
		String revisedFilePath = revisedAliasFilePath.getFilePath();
		if (oldFilePath.equals(revisedFilePath) == false) {
			String changedMessage
				= MacawMessages.getMessage("ontologyTerm.term.saveChanges",
											oldAlias,
											revisedAlias);

			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.ALIAS_FILE_PATH,
										changedMessage, 
										userID);
			changeEvent.setChangedObjectIdentifier(revisedAliasFilePath.getIdentifier());
			changeEvents.add(changeEvent);
		}
	
		return changeEvents;
	}
		
	// ==========================================
	// Section Errors and Validation
	// ==========================================

	public static void validateFields(AliasFilePath aliasFilePath) throws MacawException {
		ArrayList<String> errorMessages = new ArrayList<String>();

		//check that the term has been filled in
		if (ValidationUtility.isBlank(aliasFilePath.getAlias()) == true) {	
			String termText = MacawMessages.getMessage("aliasFilePath.alias.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
										   termText);
			errorMessages.add(errorMessage);
		}
		
		if (ValidationUtility.isBlank(aliasFilePath.getFilePath()) == true) {	
			String termText = MacawMessages.getMessage("aliasFilePath.filePath.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
										   termText);
			errorMessages.add(errorMessage);
		}		
	}
	// ==========================================
	// Section Interfaces
	// ==========================================

	//Interface: Displayable
	public String getDisplayName() {
		return alias;
	}
	
	public String getDisplayItemIdentifier() {
		return String.valueOf(identifier);		
	}

	//Interface: Cloneable
	public Object clone() {
		AliasFilePath cloneAliasPath = new AliasFilePath();
		cloneAliasPath.setAlias(alias);
		cloneAliasPath.setFilePath(filePath);
		
		return cloneAliasPath;
	}
	
	
	// ==========================================
	// Section Overload
	// ==========================================

	
	
}

