package macaw.businessLayer;

import java.util.ArrayList;

import macaw.system.*;
import macaw.util.*;

/**
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

public class OntologyTerm implements Displayable, Cloneable {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private int identifier;
	private String term;
	private String ontologyName;
	private String description;
	private String nameSpace;
	private boolean isNewRecord;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public OntologyTerm() {
		identifier = 0;
		term = "";
		ontologyName = "LHA";
		description = "";
		nameSpace = "lha.mrc.ac.uk:LHA";
		isNewRecord = true;
	}

	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	/**
	 * a unique numeric identifier that is used by software clients 
	 * to manage the term eg: 10453.
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
	 * a phrase that represents a concept to the user eg: “sex”
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}

	/**
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
	}

	/**
	 * the name of an ontology to which this term belongs.  
	 * eg: “The LHA dictionary”
	 * @return the ontologyName
	 */
	public String getOntologyName() {
		return ontologyName;
	}

	/**
	 * @param ontologyName the ontologyName to set
	 */
	public void setOntologyName(String ontologyName) {
		this.ontologyName = ontologyName;
	}

	/**
	 * the meaning of the phrase eg: “a person’s gender”
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * a URI fragment that is used to uniquely identify ontology 
	 * concepts for software clients eg: www.nshd.mrc.ac.uk:LHA/variables
	 * @return the nameSpace
	 */
	public String getNameSpace() {
		return nameSpace;
	}

	/**
	 * @param nameSpace the nameSpace to set
	 */
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
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

	public boolean hasSameDisplayName(OntologyTerm ontologyTerm) {
		if (term.equals(ontologyTerm.getTerm()) == true) {
			return true;
		}
		return false;
	}

	// ==========================================
	// Section Errors and Validation
	// ==========================================
	public static void validateFields(OntologyTerm ontologyTerm) throws MacawException {
		ArrayList<String> errorMessages = new ArrayList<String>();

		//check that the term has been filled in
		String term = ontologyTerm.getTerm();
		if (ValidationUtility.isBlank(term) == true) {	
			String termText = MacawMessages.getMessage("ontologyTerm.term.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
										   termText);
			errorMessages.add(errorMessage);
		}

		//check that the field value avoids potential security errors
		if (ValidationUtility.promotesSecurityRisk(term) == true) {	
			String termText = MacawMessages.getMessage("ontologyTerm.term.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.promotesSecurityRisk",
										   termText);
			errorMessages.add(errorMessage);
		}
		
		//ensure that ontology name has been filled in.  This will be used in displaying
		//term to user
		String ontologyName = ontologyTerm.getOntologyName();
		
		if (ValidationUtility.isBlank(ontologyName) == true) {	
			String ontologyNameText = MacawMessages.getMessage("ontologyTerm.ontologyName.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
											ontologyNameText);
			errorMessages.add(errorMessage);
		}

		//check that the field value avoids potential security errors
		if (ValidationUtility.promotesSecurityRisk(ontologyName) == true) {	
			String ontologyNameText 
				= MacawMessages.getMessage("ontologyTerm.ontologyName.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.promotesSecurityRisk",
											ontologyNameText);
			errorMessages.add(errorMessage);
		}

		//ensure that namespace is present; this will be used by software services
		if (ValidationUtility.isBlank(ontologyTerm.getNameSpace()) == true) {	
			String nameSpaceText = MacawMessages.getMessage("ontologyTerm.nameSpace.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
											nameSpaceText);
			errorMessages.add(errorMessage);
		}

		if (errorMessages.size() > 0) {
			MacawException exception
				= new MacawException();
			for (String errorMessage : errorMessages) {
				exception.addErrorMessage(MacawErrorType.INVALID_ONTOLOGY_TERM, errorMessage);			
			}
			throw exception;
		}
	}
	
	static public ArrayList<MacawChangeEvent> detectFieldChanges(User user,
			 													 OntologyTerm originalOntologyTerm,
			 													 OntologyTerm revisedOntologyTerm) {

		String userID = user.getUserID();
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();

		String oldTerm = originalOntologyTerm.getTerm();
		String newTerm = revisedOntologyTerm.getTerm();
		if (oldTerm.equals(newTerm) == false) {
			oldTerm = ValidationUtility.convertEmptyValueToBlank(oldTerm);
			newTerm = ValidationUtility.convertEmptyValueToBlank(newTerm);
			
			String changedMessage
				= MacawMessages.getMessage("ontologyTerm.term.saveChanges",
										   oldTerm,
										   newTerm);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.ONTOLOGY_TERM,
									   changedMessage, 
									   userID);
			changeEvent.setChangedObjectIdentifier(originalOntologyTerm.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		String oldOntologyName = originalOntologyTerm.getOntologyName();
		String newOntologyName = revisedOntologyTerm.getOntologyName();
		if (oldOntologyName.equals(newOntologyName) == false) {
			oldOntologyName 
				= ValidationUtility.convertEmptyValueToBlank(oldOntologyName);
			newOntologyName 
				= ValidationUtility.convertEmptyValueToBlank(newOntologyName);
			
			String changedMessage
				= MacawMessages.getMessage("ontologyTerm.ontologyName.saveChanges",
											oldOntologyName,
											newOntologyName);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.ONTOLOGY_TERM,
									   changedMessage, 
									   userID);
			changeEvent.setChangedObjectIdentifier(originalOntologyTerm.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		String oldNameSpace = originalOntologyTerm.getNameSpace();
		String newNameSpace = revisedOntologyTerm.getNameSpace();
		if (oldNameSpace.equals(newNameSpace) == false) {
			oldNameSpace = ValidationUtility.convertEmptyValueToBlank(oldNameSpace);
			newNameSpace = ValidationUtility.convertEmptyValueToBlank(newNameSpace);
			
			String changedMessage
				= MacawMessages.getMessage("ontologyTerm.term.saveChanges",
										   oldTerm,
										   newTerm);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.ONTOLOGY_TERM,
									   changedMessage, 
									   userID);
			changeEvent.setChangedObjectIdentifier(originalOntologyTerm.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		String oldDescription = originalOntologyTerm.getDescription();
		String newDescription = revisedOntologyTerm.getDescription();
		if (oldDescription.equals(newDescription) == false) {
			oldDescription = ValidationUtility.convertEmptyValueToBlank(oldDescription);
			newDescription = ValidationUtility.convertEmptyValueToBlank(newDescription);
			
			String changeMessage
				= MacawMessages.getMessage("ontologyTerm.description.saveChanges",
										   oldDescription,
										   newDescription);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.ONTOLOGY_TERM,
									   changeMessage, 
									   userID);
			changeEvent.setChangedObjectIdentifier(originalOntologyTerm.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		return changeEvents;
	}

	// ==========================================
	// Section Interfaces
	// ==========================================
	//Interface: Displayable
	public String getDisplayName() {
		StringBuilder displayName = new StringBuilder();
		displayName.append(term);
		displayName.append("-");
		displayName.append(ontologyName);
		return displayName.toString();
	}
	
	public String getDisplayItemIdentifier() {
		return String.valueOf(identifier);		
	}
	
	public Object clone() {
		OntologyTerm cloneTerm = new OntologyTerm();
		cloneTerm.setIdentifier(identifier);
		cloneTerm.setOntologyName(ontologyName);
		cloneTerm.setNameSpace(nameSpace);
		cloneTerm.setTerm(term);
		cloneTerm.setDescription(description);
		cloneTerm.setNewRecord(isNewRecord);
		
		return cloneTerm;
	}
	// ==========================================
	// Section Overload
	// ==========================================

}

