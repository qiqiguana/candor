package macaw.persistenceLayer;

import macaw.businessLayer.*;
import macaw.system.*;

import java.util.ArrayList;

/**
 * A convenience class that generates collections of change event descriptions
 * that are used by both the InMemory and SQL-based implementations of 
 * {@link macaw.businessLayer.MacawCurationAPI}.  Early in development, implementations of the main API 
 * began to share common blocks of code, particularly for processing change events associated
 * with "add", "delete", "associate" and "dissociate" operations.  To record changes
 * made in "update" operations, a different procedure was followed.  Old and revised
 * versions of a record were passed to the static method <code>detectFieldChanges(...)</code>
 * that is supported in most of the <code>macaw.model.*</code> classes.   
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

public class ChangeEventGenerator {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================

	// ==========================================
	// Section Construction
	// ==========================================
	public ChangeEventGenerator() {
		
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	
	/**
	 * produce change events for operations which involve adding support documents
	 * to the database
	 */
	static public ArrayList<MacawChangeEvent> addSupportingDocumentChange(User user,
																		  SupportingDocument supportingDocument) {
		
		String changeMessage
			= MacawMessages.getMessage("supportingDocument.saveChanges.newRecord",
										supportingDocument.getDisplayName());

		MacawChangeEvent changeEvent
			= new MacawChangeEvent(ChangeEventType.SUPPORTING_DOCUMENT, 
								   changeMessage, 
								   user.getUserID());

		changeEvent.setChangedObjectIdentifier(supportingDocument.getIdentifier());

		ArrayList<MacawChangeEvent> changeEvents
			= new ArrayList<MacawChangeEvent>();
		changeEvents.add(changeEvent);
		return changeEvents;
	}

	/**
	 * produce change events for operations which involve deleting support documents
	 * from the database
	 */
	static public ArrayList<MacawChangeEvent> deleteSupportingDocumentsChange(User user,
																			  ArrayList<SupportingDocument> supportingDocumentsToDelete) {
		String userID = user.getUserID();
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		for (SupportingDocument supportingDocument : supportingDocumentsToDelete) {
			String changeMessage
				= MacawMessages.getMessage("supportingDocument.saveChanges.deleteRecord",
										   supportingDocument.getDisplayName());
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.SUPPORTING_DOCUMENT,
									   changeMessage, 
									   userID);	
			changeEvent.setChangedObjectIdentifier(supportingDocument.getIdentifier());
			changeEvents.add(changeEvent);
		}
		
		return changeEvents;
	}

	/**
	 * produce change events for operations which involve adding value labels
	 * to the database
	 */
	static public ArrayList<MacawChangeEvent> addValueLabelsChange(User user,
																   Variable variable,
																   ArrayList<ValueLabel> valueLabels) {
		ArrayList<MacawChangeEvent> changeEvents 
			= new ArrayList<MacawChangeEvent>();
		for (ValueLabel currentValueLabel : valueLabels) {
			String changeMessage
				= MacawMessages.getMessage("valueLabel.saveChanges.newRecord",
											currentValueLabel.getDisplayName(),
											variable.getDisplayName());
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VALUE_LABEL,
										changeMessage,
										user.getUserID());
			changeEvent.setVariableOwnerID(variable.getIdentifier());
			changeEvent.setChangedObjectIdentifier(currentValueLabel.getIdentifier());
			changeEvents.add(changeEvent);
		}
		
		return changeEvents;
	}

	/**
	 * produce change events for operations which involve deleting value labels
	 * from the database
	 */
	static public ArrayList<MacawChangeEvent> deleteValueLabelsChange(User user,
																	  Variable variable,
																	  ArrayList<ValueLabel> valueLabels) {
	
		String userID = user.getUserID();
		ArrayList<MacawChangeEvent> changeEvents
			= new ArrayList<MacawChangeEvent>();
		for (ValueLabel currentValueLabel : valueLabels) {
			String changeMessage
				= MacawMessages.getMessage("valueLabel.saveChanges.deleteRecord",
										   currentValueLabel.getDisplayName(),
										   variable.getDisplayName());
			MacawChangeEvent changeEvent	
				= new MacawChangeEvent(ChangeEventType.VALUE_LABEL,
									   changeMessage,
									   userID);
			changeEvent.setVariableOwnerID(variable.getIdentifier());
			changeEvent.setChangedObjectIdentifier(currentValueLabel.getIdentifier());
			changeEvents.add(changeEvent);
		}	
		
		return changeEvents;
	}

	/**
	 * produce change events for operations which involve adding ontology terms
	 * to the database
	 */
	static public ArrayList<MacawChangeEvent> addOntologyTermChange(User user,
																	OntologyTerm ontologyTerm) {
		String userID = user.getUserID();
		String changeMessage 
			= MacawMessages.getMessage("ontologyTerm.saveChanges.newRecord",
										ontologyTerm.getDisplayName());
		MacawChangeEvent changeEvent
			= new MacawChangeEvent(ChangeEventType.ONTOLOGY_TERM,
									changeMessage,
									userID);
		
		ArrayList<MacawChangeEvent> changeEvents 
			= new ArrayList<MacawChangeEvent>();
		changeEvents.add(changeEvent);
		return changeEvents;
	}
	
	/**
	 * produce change events for operations which involve deleting ontology terms
	 * from the database
	 */
	static public ArrayList<MacawChangeEvent> deleteOntologyTermsChange(User user,
																		ArrayList<OntologyTerm> ontologyTerms) {
		
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		String userID = user.getUserID();
		for (OntologyTerm currentOntologyTerm : ontologyTerms) {
			String changeMessage
				= MacawMessages.getMessage("ontologyTerm.saveChanges.deleteRecord",
											currentOntologyTerm.getDisplayName());
			
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.ONTOLOGY_TERM,
									   changeMessage,
									   userID);
			changeEvents.add(changeEvent);
		}	
		return changeEvents;
	}

	/**
	 * produce change events for operations which involve deleting raw or derived variables
	 * from the database
	 */
	static public MacawChangeEvent deleteVariableChanges(User user,
														 Variable variable) {
		String userID = user.getUserID();

		if (variable instanceof RawVariable) {
			String changeMessage
				= MacawMessages.getMessage("rawVariable.saveChanges.deleteRecord",
											variable.getDisplayName());
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE,
									   changeMessage, 
									   userID);	
			changeEvent.setChangedObjectIdentifier(variable.getIdentifier());
			changeEvent.setVariableOwnerID(variable.getIdentifier());
			return changeEvent;
		}
		else {
			String changeMessage
				= MacawMessages.getMessage("derivedVariable.saveChanges.deleteRecord",
											variable.getDisplayName());
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE,
										changeMessage, 
										userID);	
			changeEvent.setChangedObjectIdentifier(variable.getIdentifier());
			changeEvent.setVariableOwnerID(variable.getIdentifier());
			return changeEvent;
		}
	}	
	
	static public ArrayList<MacawChangeEvent> deleteRawVariablesChanges(User user,
																		ArrayList<RawVariable> rawVariables) {
		String userID = user.getUserID();
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		for (RawVariable currentRawVariable : rawVariables) {
			String changeMessage
				= MacawMessages.getMessage("rawVariable.saveChanges.deleteRecord",
										   currentRawVariable.getDisplayName());
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE,
									   changeMessage, 
									   userID);	
			changeEvent.setChangedObjectIdentifier(currentRawVariable.getIdentifier());
			changeEvent.setVariableOwnerID(currentRawVariable.getIdentifier());
			changeEvents.add(changeEvent);
		}	
		
		return changeEvents;
	}
	
	/**
	 * produce change events for operations which involve adding raw or derived variables
	 * to the database
	 */
	static public ArrayList<MacawChangeEvent> addVariableChange(User user,
																Variable variable) {
		ArrayList<MacawChangeEvent> changeEvents 
			= new ArrayList<MacawChangeEvent>();
		
		if (variable instanceof RawVariable) {			
			String changeMessage
				= MacawMessages.getMessage("rawVariable.saveChanges.newRecord",
											variable.getDisplayName());
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE, 
										changeMessage, 
										user.getUserID());
			changeEvent.setChangedObjectIdentifier(variable.getIdentifier());
			changeEvent.setVariableOwnerID(variable.getIdentifier());
			changeEvents.add(changeEvent);			
		}
		else {
			String changeMessage
				= MacawMessages.getMessage("derivedVariable.saveChanges.newRecord",
											variable.getDisplayName());
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE, 
										changeMessage, 
										user.getUserID());
			changeEvent.setChangedObjectIdentifier(variable.getIdentifier());
			changeEvent.setVariableOwnerID(variable.getIdentifier());
			changeEvents.add(changeEvent);
		}
		
		return changeEvents;
	}

	/**
	 * produce change events for operations which involve associating source variables
	 * with a derived variable
	 */
	static public ArrayList<MacawChangeEvent> associateSourceVariablesChanges(User user,
																			  DerivedVariable derivedVariable,
																			  ArrayList<Variable> sourceVariablesToAdd) {
		
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		String userID = user.getUserID();
		String derivedVariableDisplayName
			= derivedVariable.getDisplayName();
		for (Variable sourceVariableToAdd : sourceVariablesToAdd) {
			String changeMessage
				= MacawMessages.getMessage("derivedVariable.sourceVariables.saveChanges.add",
									   		sourceVariableToAdd.getDisplayName(),
									   		derivedVariableDisplayName);
			
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE, 
									   changeMessage, 
									   userID);
			changeEvent.setChangedObjectIdentifier(derivedVariable.getIdentifier());
			changeEvent.setVariableOwnerID(derivedVariable.getIdentifier());
			changeEvents.add(changeEvent);
		}
		return changeEvents;		
	}

	/**
	 * produce change events for operations which involve disassociating source variables
	 * with a derived variable
	 */
	static public ArrayList<MacawChangeEvent> disassociateSourceVariablesChanges(User user,
																			     DerivedVariable derivedVariable,
																			     ArrayList<Variable> sourceVariablesToDelete) {

		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		String userID = user.getUserID();
		String derivedVariableDisplayName
			= derivedVariable.getDisplayName();
		for (Variable sourceVariableToDelete : sourceVariablesToDelete) {
			String changeMessage
				= MacawMessages.getMessage("derivedVariable.sourceVariables.saveChanges.delete",
											sourceVariableToDelete.getDisplayName(),
											derivedVariableDisplayName);

			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE, 
									   changeMessage, 
									   userID);
			changeEvent.setChangedObjectIdentifier(derivedVariable.getIdentifier());
			changeEvent.setVariableOwnerID(derivedVariable.getIdentifier());
			changeEvents.add(changeEvent);
		}
		return changeEvents;		
	}

	/**
	 * produce change events for operations which involve associating supporting documents
	 * with a derived variable
	 */
	static public ArrayList<MacawChangeEvent> associateSupportingDocumentChanges(User user,
																				 Variable variable,
																				 ArrayList<SupportingDocument> supportingDocumentsToAdd) throws MacawException {
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		for (SupportingDocument supportingDocument : supportingDocumentsToAdd) {
			if (variable.containsSupportingDocument(supportingDocument) == false) {
				variable.addSupportingDocument(supportingDocument);
				String changeMessage
					= MacawMessages.getMessage("variable.saveChanges.associateDocument",
												supportingDocument.getDisplayName(),
												variable.getDisplayName());
			
				MacawChangeEvent changeEvent
					= new MacawChangeEvent(ChangeEventType.VARIABLE, 
											changeMessage, 
											user.getUserID());

				changeEvent.setChangedObjectIdentifier(supportingDocument.getIdentifier());
				changeEvent.setVariableOwnerID(variable.getIdentifier());
				changeEvents.add(changeEvent);
			}
		}
		
		return changeEvents;
	}
	
	/**
	 * produce change events for operations which involve disassociating supporting documents
	 * with a derived variable
	 */
	static public ArrayList<MacawChangeEvent> disassociateSupportingDocumentsChanges(User user,
			 										   						  		 Variable variable,
			 										   						  		 ArrayList<SupportingDocument> supportingDocumentsToDelete) {
		
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		for (SupportingDocument supportingDocument : supportingDocumentsToDelete) {			
			String changeMessage
				= MacawMessages.getMessage("variable.saveChanges.associateDocument",
											supportingDocument.getDisplayName(),
											variable.getDisplayName());
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE, 
									   changeMessage, 
									   user.getUserID());	

			changeEvent.setChangedObjectIdentifier(supportingDocument.getIdentifier());
			changeEvent.setVariableOwnerID(variable.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		return changeEvents;
	}
	
	/**
	 * creates change event when a new user is added
	 * @param admin
	 * @param user
	 * @throws MacawException
	 */
	static public ArrayList<MacawChangeEvent> addUserChange(User admin,
															User user) throws MacawException {

		String changeMessage
			= MacawMessages.getMessage("user.saveChanges.newRecord",
										admin.getDisplayName(),
										user.getDisplayName());
		MacawChangeEvent changeEvent
			= new MacawChangeEvent(ChangeEventType.USER,
								   changeMessage,
								   admin.getDisplayName());

		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		changeEvents.add(changeEvent);
		return changeEvents;		
	}
	
	/**
	 * creates a collection of change events for users which are deleted
	 * @param admin
	 * @param usersToDelete
	 * @throws MacawException
	 */
	static public ArrayList<MacawChangeEvent> deleteUsersChanges(User admin,
																 ArrayList<User> usersToDelete) throws MacawException {
	
		String adminID = admin.getUserID();
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		for (User userToDelete : usersToDelete) {
			String changeMessage
				= MacawMessages.getMessage("user.saveChanges.deleteRecord",
											admin.getDisplayName(),
											userToDelete.getDisplayName());
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.USER,
									   changeMessage,
									   adminID);
			changeEvents.add(changeEvent);
		}	
		
		return changeEvents;
	}	
	
	/*
	static public MacawChangeEvent setAlternativeVariable(User user,
														  Variable targetVariable,
														  Variable oldAlternativeVariable,
														  Variable updatedAlternativeVariable) throws MacawException {

		String userID = user.getUserID();

		String blankFieldValue
			= MacawMessages.getMessage("general.fieldValue.blank");
		String fieldName
			= MacawMessages.getMessage("variable.alternativeVariable.label");
		String oldAlternativeVariableName = blankFieldValue;
		if (oldAlternativeVariable != null) {
			oldAlternativeVariableName = oldAlternativeVariable.getDisplayName();
		}
		
		String updatedAlternativeVariableName = blankFieldValue;
		if (updatedAlternativeVariable != null) {
			updatedAlternativeVariableName = updatedAlternativeVariable.getDisplayName();
		}
		
		String changeMessage
			= MacawMessages.getMessage("variable.saveChanges.valueChanged",
										fieldName,
										oldAlternativeVariableName,
										updatedAlternativeVariableName);
		
		MacawChangeEvent changeEvent
			= new MacawChangeEvent(ChangeEventType.VARIABLE,
								   changeMessage,
								   user.getUserID());
		
		int targetVariableID = targetVariable.getIdentifier();
		changeEvent.setVariableOwnerID(targetVariableID);
		changeEvent.setChangedObjectIdentifier(targetVariableID);
		
		return changeEvent;		
	}
	*/
	
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

