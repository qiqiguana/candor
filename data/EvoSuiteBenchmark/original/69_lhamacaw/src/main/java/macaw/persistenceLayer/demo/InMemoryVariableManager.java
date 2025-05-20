package macaw.persistenceLayer.demo;

import macaw.businessLayer.*;
import macaw.persistenceLayer.ChangeEventGenerator;
import macaw.system.*;
import macaw.util.SearchUtility;
import macaw.util.ValidationUtility;


import java.util.ArrayList;
import java.util.HashMap;



/**
 * manages instances of {@link macaw.businessLayer.Variable} in memory.  It also supports
 * adding and deleting associations between a {@link macaw.businessLayer.Variable} and:
 * <ul>
 * <li>>=1 {@link macaw.businessLayer.SupportingDocument} records.</li>
 * <li>>=1 {@link macaw.businessLayer.OntologyTerm} records. </li>
 * <li>>=1 {@link macaw.businessLayer.Variable} records (for {@link macaw.businessLayer.DerivedVariable} 
 * records only</li>
 * </ul>
 * 
 * <p> In the Macaw data entry forms, users add or remove associations through list 
 * management buttons.  Other variable associations are less obvious.  Each variable is
 * also associated with:
 * <ul>
 * <li>1 {@link macaw.businessLayer.Category} (in future it will be >=1 category)</li>
 * <li>1 {@link macaw.businessLayer.AvailabilityState}</li>
 * <li>1 {@link macaw.businessLayer.CleaningState}</li>
 * <li>1 {@link macaw.businessLayer.AliasFilePath}</li>
 * </ul>
 * 
 * <p>
 * In the Macaw forms, these items are shown to users in a drop down list.  Items in
 * this list can be edited through separate data entry forms (see 
 * {@link macaw.presentationLayer.CategoryStateEditor},{@link macaw.presentationLayer.CleaningStateEditor},
 * {@link macaw.presentationLayer.AvailabilityStateEditor}, {@link macaw.businessLayer.AliasFilePath}. 
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

public class InMemoryVariableManager extends InMemoryCurationConceptManager {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private ArrayList<Variable> variables;
	private InMemoryListChoiceManager listChoiceManager;
	private InMemoryOntologyTermManager ontologyTermManager;
	private InMemorySupportingDocumentsManager supportingDocumentsManager;
	private HashMap<Integer, Variable> variableFromIdentifier;
	private int variableKey;

	// ==========================================
	// Section Construction
	// ==========================================
	public InMemoryVariableManager(InMemoryChangeEventManager changeEventManager,
								   InMemoryListChoiceManager listChoiceManager,
								   InMemoryOntologyTermManager ontologyTermManager,
								   InMemorySupportingDocumentsManager supportingDocumentsManager) {
		
		super(changeEventManager);
		this.listChoiceManager = listChoiceManager;
		this.ontologyTermManager = ontologyTermManager;
		this.supportingDocumentsManager = supportingDocumentsManager;
		variables = new ArrayList<Variable>();		
		variableFromIdentifier = new HashMap<Integer, Variable>();		
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	public ArrayList<Variable> getOriginalVariables(User user) throws MacawException {
		return variables;
	}
	
	public ArrayList<Variable> getVariables(User user) throws MacawException {
		ArrayList<Variable> cloneVariables = new ArrayList<Variable>();
		for (Variable currentVariable : variables) {
			Variable cloneVariable
				= (Variable) currentVariable.clone();
			cloneVariables.add(cloneVariable);
		}
		return cloneVariables;
	}
	
	public void addRawVariable(User user, 
							   RawVariable rawVariable) throws MacawException {

		RawVariable.validateFields(rawVariable);
		addVariable(user, rawVariable);
	}

	public void addDerivedVariable(User user, 
								   DerivedVariable derivedVariable) throws MacawException {

		addVariable(user, derivedVariable);
		DerivedVariable.validateFields(derivedVariable);
	}

	
	public void addVariable(User user, 
							Variable variable) throws MacawException {
		
		//Part I: Validate parameters
		checkVariableDuplicates(variable);
		checkListChoices(variable);

		//Part II: Add Variable
		variableKey++;
		variable.setIdentifier(variableKey);
		
		variable.setIsNewRecord(false);
		variables.add(variable);
		variableFromIdentifier.put(variable.getIdentifier(), variable);
		
		//Part III: Record Changes
		ArrayList<MacawChangeEvent> changeEvents
			= ChangeEventGenerator.addVariableChange(user, variable);
		registerChangeEvents(changeEvents);
	}

	public void updateRawVariable(User user, 
								  RawVariable revisedRawVariable) throws MacawException {

		//Part I: Validate parameters
		
		//check basic field errors
		RawVariable.validateFields(revisedRawVariable);
		//check that variable exists
		checkVariableExists(revisedRawVariable);
		//check for duplicates
		checkVariableDuplicates(revisedRawVariable);
		checkListChoices(revisedRawVariable);

		
		//check that at least one change was made.
		RawVariable originalRawVariable
			= (RawVariable) getOriginalVariable(revisedRawVariable);
		ArrayList<MacawChangeEvent> changeEvents
			= RawVariable.detectFieldChanges(user,
											 originalRawVariable, 
											 revisedRawVariable);	
		
		if (changeEvents.size() == 0) {
			return;
		}
		
		//Part II: Perform Update
		//obtain the original object for the supporting document that
		//is requested
		
		//replace original with revised copies in the list of documents
		//associated with a given variable
		int foundIndex
			= variables.indexOf(originalRawVariable);
		variables.remove(foundIndex);

		int currentNumberOfVariables = variables.size();
		if (foundIndex == currentNumberOfVariables - 1) {
			//add to the end to avoid array out of index errors
			variables.add(revisedRawVariable);
		}
		else {
			variables.add(foundIndex, revisedRawVariable);			
		}
		//replace original with revised in the list that tracks all managed
		//supporting document instances
		int identifier = originalRawVariable.getIdentifier();
		variableFromIdentifier.remove(originalRawVariable);
		variableFromIdentifier.put(identifier, revisedRawVariable);
		
		//Part III: Record changes
		registerChangeEvents(changeEvents);
	}

	public void deleteRawVariables(User user, 
								   ArrayList<RawVariable> rawVariablesToDelete) throws MacawException {
		//Part I: Validate parameters

		//check that raw variables exist		
		for (RawVariable rawVariableToDelete : rawVariablesToDelete) {
			checkVariableExists(rawVariableToDelete);
		}
		
		//Part II: Perform the deletion operation
		for (RawVariable currentRawVariable : rawVariablesToDelete) {
			int identifier = currentRawVariable.getIdentifier();
			RawVariable originalRawVariable
				= (RawVariable) variableFromIdentifier.get(identifier);
			variables.remove(originalRawVariable);
			variableFromIdentifier.remove(identifier);
		}		
		
		//Part III: Record changes
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		for (RawVariable currentRawVariable : rawVariablesToDelete) {
			MacawChangeEvent changeEvent
				= ChangeEventGenerator.deleteVariableChanges(user, 
															 currentRawVariable);
			changeEvents.add(changeEvent);
		}
		registerChangeEvents(changeEvents);		
	}

	public void updateDerivedVariable(User user, 
									  DerivedVariable revisedDerivedVariable) throws MacawException {

		//Part I: Validate parameters
		
		//check basic field errors
		DerivedVariable.validateFields(revisedDerivedVariable);
		//check that variable exists
		checkVariableExists(revisedDerivedVariable);
		//check for duplicates
		checkVariableDuplicates(revisedDerivedVariable);
		checkListChoices(revisedDerivedVariable);
		
		//check that at least one change was made.
		DerivedVariable originalDerivedVariable
			= (DerivedVariable) getOriginalVariable(revisedDerivedVariable);
		ArrayList<MacawChangeEvent> changeEvents
			= RawVariable.detectFieldChanges(user,
											 originalDerivedVariable, 
											 revisedDerivedVariable);	
		if (changeEvents.size() == 0) {
			return;
		}
		
		//Part II: Perform Update
		//obtain the original object for the supporting document that
		//is requested
		
		//replace original with revised copies in the list of documents
		//associated with a given variable
		int foundIndex
			= variables.indexOf(originalDerivedVariable);
		variables.remove(foundIndex);

		int currentNumberOfVariables = variables.size();
		if (foundIndex == currentNumberOfVariables - 1) {
			//add to the end to avoid array out of index errors
			variables.add(revisedDerivedVariable);
		}
		else {
			variables.add(foundIndex, revisedDerivedVariable);			
		}
		//replace original with revised in the list that tracks all managed
		//supporting document instances
		int identifier = originalDerivedVariable.getIdentifier();
		variableFromIdentifier.remove(originalDerivedVariable);
		variableFromIdentifier.put(identifier, revisedDerivedVariable);
		
		//Part III: Record changes
		registerChangeEvents(changeEvents);		
	}

	public void deleteDerivedVariables(User user, 
									   ArrayList<DerivedVariable> derivedVariablesToDelete) throws MacawException {

		//Part I: Validate parameters
		//check that derived variables exist		
		for (DerivedVariable derivedVariableToDelete : derivedVariablesToDelete) {
			checkVariableExists(derivedVariableToDelete);
		}
		
		//Part II: Perform the deletion operation
		for (DerivedVariable currentDerivedVariable : derivedVariablesToDelete) {
			int identifier = currentDerivedVariable.getIdentifier();
			DerivedVariable originalDerivedVariable
				= (DerivedVariable) variableFromIdentifier.get(identifier);
			variables.remove(originalDerivedVariable);
			variableFromIdentifier.remove(identifier);
		}		
		
		//Part III: Record changes
		String userID = user.getUserID();
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		for (DerivedVariable currentDerivedVariable : derivedVariablesToDelete) {
			String changeMessage
				= MacawMessages.getMessage("derivedVariable.saveChanges.deleteRecord",
										   currentDerivedVariable.getDisplayName());
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE,
									   changeMessage, 
									   userID);	
			changeEvent.setChangedObjectIdentifier(currentDerivedVariable.getIdentifier());
			changeEvent.setVariableOwnerID(currentDerivedVariable.getIdentifier());
			changeEvents.add(changeEvent);
		}	
		
		registerChangeEvents(changeEvents);	
	}
	
	public ArrayList<VariableSummary> getSummaryDataForAllVariables(User user) throws MacawException {
		ArrayList<VariableSummary> variableSummaries = new ArrayList<VariableSummary>();
		for (Variable currentVariable : variables) {
			variableSummaries.add(currentVariable.createVariableSummary());
		}
		return variableSummaries;		
	}

	public Variable getCompleteVariableData(User user, VariableSummary variableSummary) throws MacawException {
		int identifier = variableSummary.getIdentifier();
		Variable variable = variableFromIdentifier.get(identifier);
		
		if (variable == null) {
			//ERROR: derived variable does not exist
			String errorMessage
				= MacawMessages.getMessage("variable.error.nonExistent",
											variableSummary.getDisplayName());
			MacawException exception = new MacawException();
			exception.addErrorMessage(MacawErrorType.NON_EXISTENT_VARIABLE_FOR_SUMMARY,
									  errorMessage);
			throw exception;			
		}		

		Variable cloneVariable = (Variable) variable.clone();
		return cloneVariable;
	}

	/**
	public Variable getVariable(User user,
								String variableName) throws MacawException {
		
		if (variableName == null) {
			return null;
		}
		
		for (Variable variable : variables) {
			if (variable.getName().equals(variableName) == true) {
				return variable;
			}
		}		
		
		return null;
	}
	*/
	
	public ArrayList<OntologyTerm> getAssociatedOntologyTerms(User user,
															  Variable variable) throws MacawException {

		//PartI: Check derived variable exists
		checkVariableExists(variable);

		//Part II get source variables for derived variable
		Variable originalVariable
			= (Variable) getOriginalVariable(variable);
		//make a copy of the list of source variables
		ArrayList<OntologyTerm> ontologyTerms = originalVariable.getOntologyTerms();
		
		ArrayList<OntologyTerm> cloneOntologyTerms = new ArrayList<OntologyTerm>();
		for (OntologyTerm currentOntologyTerm : ontologyTerms) {
			OntologyTerm originalOntologyTerm
				= ontologyTermManager.getOriginalOntologyTerm(currentOntologyTerm);
			OntologyTerm cloneSourceVariable = (OntologyTerm) originalOntologyTerm.clone();
			cloneOntologyTerms.add(cloneSourceVariable);
		}

		return cloneOntologyTerms;
	}
	
	public void associateOntologyTerms(User user,
									   Variable variable,
									   ArrayList<OntologyTerm> ontologyTermsToAssociate) throws MacawException {

		//Part I: Validate parameters
		checkVariableExists(variable);	
		for (OntologyTerm ontologyTermToAssociate : ontologyTermsToAssociate) {
			ontologyTermManager.checkOntologyTermExists(ontologyTermToAssociate);
			checkDuplicateTermAssociation(variable, 
										  ontologyTermToAssociate);
		}
		
		//Part II: Perform associate ontology terms operation
		Variable originalVariable
			= (Variable) getOriginalVariable(variable);		
		for (OntologyTerm ontologyTermToAdd : ontologyTermsToAssociate) {
			/*
			String changeMessage
				= MacawMessages.getMessage("ontologyTerm.addTermToVariable.saveChanges.add",
											originalVariable.getDisplayName(),
											ontologyTermToAdd.getDisplayName());
			*/
			originalVariable.addOntologyTerm(ontologyTermToAdd);
		}

		//Part III: Record changes
		
		//the code below is commented out until we have settled more on the design
		//of ontology terms.
		
		/**
		String userID = user.getUserID();
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		for (OntologyTerm ontologyTermToAdd : ontologyTermsToAssociate) {
			String changeMessage
				= MacawMessages.getMessage("ontologyTerm.addTermToVariable.saveChanges.add",
											originalVariable.getDisplayName(),
											ontologyTermToAdd.getDisplayName());
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.ONTOLOGY_TERM,
										changeMessage,
										userID);
			changeEvent.setChangedObjectIdentifier(ontologyTermToAdd.getIdentifier());
			changeEvent.setVariableOwnerID(originalVariable.getIdentifier());
			originalVariable.addOntologyTerm(ontologyTermToAdd);
		}
		*/

		
		
	}
	
	public void disassociateOntologyTerms(User user,
									  	  Variable variable,
									  	  ArrayList<OntologyTerm> ontologyTermsToDisassociate) throws MacawException {

		//Part I: Validate parameters
		checkVariableExists(variable);	
		for (OntologyTerm ontologyTermToDisAssociate : ontologyTermsToDisassociate) {
			ontologyTermManager.checkOntologyTermExists(ontologyTermToDisAssociate);
			checkTermAssociationExists(variable, 
									   ontologyTermToDisAssociate);
		}
		
		//Part II: Perform associate ontology terms operation
		Variable originalVariable
			= (Variable) getOriginalVariable(variable);		
		for (OntologyTerm ontologyTermToDisAssociate : ontologyTermsToDisassociate) {
			originalVariable.removeOntologyTerm(ontologyTermToDisAssociate);
		}

		//Part III: Record changes
		//NOTE: NOT committing provenance, defer until better ontology term system
		//implemented		
		/**
		String userID = user.getUserID();
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		for (OntologyTerm ontologyTermToDisAssociate : ontologyTermsToDisassociate) {
			String changeMessage
				= MacawMessages.getMessage("ontologyTerm.addTermToVariable.saveChanges.add",
											originalVariable.getDisplayName(),
											ontologyTermToDisAssociate.getDisplayName());
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.ONTOLOGY_TERM,
										changeMessage,
										userID);
			changeEvent.setChangedObjectIdentifier(ontologyTermToDisAssociate.getIdentifier());
			changeEvent.setVariableOwnerID(originalVariable.getIdentifier());
			originalVariable.addOntologyTerm(ontologyTermToDisAssociate);
		}
		*/
				
	}
	
	public ArrayList<Variable> getSourceVariables(User user,
			  									  DerivedVariable derivedVariable) throws MacawException {

		//PartI: Check derived variable exists
		checkVariableExists(derivedVariable);

		//Part II get source variables for derived variable
		DerivedVariable originalDerivedVariable
			= (DerivedVariable) getOriginalVariable(derivedVariable);
		//make a copy of the list of source variables
		ArrayList<Variable> originalSourceVariables = originalDerivedVariable.getSourceVariables();
		ArrayList<Variable> cloneSourceVariables = new ArrayList<Variable>();
		for (Variable originalSourceVariable : originalSourceVariables) {
			Variable cloneSourceVariable = (Variable) originalSourceVariable.clone();
			cloneSourceVariables.add(cloneSourceVariable);
		}

		return cloneSourceVariables;
	}
	
	public void associateSourceVariables(User user,
										 DerivedVariable derivedVariable,
										 ArrayList<Variable> sourceVariablesToAdd) throws MacawException {

		//Part I: Validate parameters
		checkVariableExists(derivedVariable);
		for (Variable sourceVariableToAdd : sourceVariablesToAdd) {
			checkVariableExists(sourceVariableToAdd);
			checkDuplicateVariableAssociation(derivedVariable,
											  sourceVariableToAdd);
		}
		
		//Part II: Perform add source variables operation
		DerivedVariable originalDerivedVariable
			= (DerivedVariable) getOriginalVariable(derivedVariable);		
		for (Variable sourceVariableToAdd : sourceVariablesToAdd) {
			Variable originalSourceVariable
				= getOriginalVariable(sourceVariableToAdd);
			originalDerivedVariable.addSourceVariable(originalSourceVariable);
		}

		//Part III: Record changes
		ArrayList<MacawChangeEvent> changeEvents
			= ChangeEventGenerator.associateSourceVariablesChanges(user,
																   derivedVariable,
																   sourceVariablesToAdd);
		registerChangeEvents(changeEvents);				
	}


	public void disassociateSourceVariables(User user,
				  					  		DerivedVariable derivedVariable,
				  					  		ArrayList<Variable> sourceVariablesToDelete) throws MacawException {

		//Part I: Validate parameters
		checkVariableExists(derivedVariable);
		for (Variable sourceVariableToDelete : sourceVariablesToDelete) {
			checkVariableExists(sourceVariableToDelete);
			checkVariableAssociationExists(derivedVariable, sourceVariableToDelete);
		}
		
		//Part II: Perform add source variables operation
		DerivedVariable originalDerivedVariable
			= (DerivedVariable) getOriginalVariable(derivedVariable);		
		for (Variable sourceVariableToDelete : sourceVariablesToDelete) {
			Variable originalSourceVariable
				= getOriginalVariable(sourceVariableToDelete);
			originalDerivedVariable.removeSourceVariable(originalSourceVariable);
		}

		//Part III: Record changes
		ArrayList<MacawChangeEvent> changeEvents
			= ChangeEventGenerator.disassociateSourceVariablesChanges(user,
			    													  derivedVariable,
			    													  sourceVariablesToDelete);		
		registerChangeEvents(changeEvents);		
	}

	public Variable getOriginalVariable(Variable variable) {
		int identifier = variable.getIdentifier();
		Variable originalVariable
			= variableFromIdentifier.get(identifier);
		return originalVariable;
	}

	public Variable getVariable(User user,
								String variableName) throws MacawException {
		if (ValidationUtility.isEmptyValue(variableName) == true) {
			return null;
		}
		
		SearchUtility searchUtility = new SearchUtility(variableName);		

		for (Variable currentVariable : variables) {
			String currentVariableName 
				= currentVariable.getName();
			if (searchUtility.valueExactlyMatches(currentVariableName) == true) {
				Variable result = (Variable) currentVariable.clone(); 
				return result;
			}
		}

		return null;
	}
	
	public ArrayList<VariableSummary> getVariableSummariesForCategory(User user,
																	  String categoryName) throws MacawException {	
		
		SearchUtility searchUtility = new SearchUtility(categoryName);		

		ArrayList<VariableSummary> variableSummaries = new ArrayList<VariableSummary>();
		for (Variable currentVariable : variables) {
			String currentVariableCategory = currentVariable.getCategory();
			if (searchUtility.valueExactlyMatches(currentVariableCategory) == true) {
				variableSummaries.add(currentVariable.createVariableSummary());
			}
		}
		
		return variableSummaries;	
	}
	
	public String[] getStudyYears(User user) throws MacawException {
		ArrayList<String> studyYears = new ArrayList<String>();
		
		for (Variable currentVariable : variables) {
			String yearOfVariable = currentVariable.getYear();
			if ( (ValidationUtility.isBlank(yearOfVariable) == false) &&
				 (studyYears.contains(yearOfVariable) == false)) {
				studyYears.add(yearOfVariable);				
			}			
		}
				
		String[] results
			= studyYears.toArray(new String[0]);
		return results;
	}

	public String[] getVariableNames(User user) throws MacawException {
		ArrayList<String> variableNames = new ArrayList<String>();
		
		for (Variable currentVariable : variables) {
			variableNames.add(currentVariable.getName());				
		}
				
		String[] results
			= variableNames.toArray(new String[0]);
		return results;		
	}

	
	public ArrayList<Category> getCategoriesForVariable(User user,
														String variableName) throws MacawException {

		ArrayList<Category> categories = new ArrayList<Category>();
		
		Variable variable = getVariable(user, variableName);
		if (variable != null) {
			String categoryName = variable.getCategory();
			Category category 
				= listChoiceManager.getCategory(categoryName);
			if (category != null) {
				categories.add(category);
			}
		}		
		return categories;
	}
	
	public ArrayList<SupportingDocument> getSupportingDocuments(User user,
																Variable variable) throws MacawException {
		Variable originalVariable = getOriginalVariable(variable);		
		ArrayList<SupportingDocument> supportingDocuments
			= originalVariable.getSupportingDocuments();
		ArrayList<SupportingDocument> cloneSupportingDocuments
			= new ArrayList<SupportingDocument>();
		for (SupportingDocument currentSupportingDocument : supportingDocuments) {
			SupportingDocument originalSupportingDocument
				= supportingDocumentsManager.getOriginalSupportingDocument(currentSupportingDocument);
			SupportingDocument cloneSupportingDocument
				= (SupportingDocument) originalSupportingDocument.clone();
			cloneSupportingDocuments.add(cloneSupportingDocument);
		}

		return cloneSupportingDocuments;		
	}

	public void associateSupportingDocuments(User user,
											 Variable variable,
											 ArrayList<SupportingDocument> supportingDocumentsToAdd) throws MacawException {

		//Part I: Validate parameters
		//check basic field errors
		checkVariableExists(variable);
		if (supportingDocumentsToAdd.size() == 0) {
			return;
		}
		
		Variable originalVariable = getOriginalVariable(variable);		
		for (SupportingDocument supportingDocument : supportingDocumentsToAdd) {
			supportingDocumentsManager.checkSupportingDocumentExists(supportingDocument);
			checkDuplicateDocumentAssociation(originalVariable, supportingDocument);
		}

		//Part II: Add Document and Record Changes
		originalVariable.addSupportingDocuments(supportingDocumentsToAdd);
				
		//Part III: Record Changes	
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		for (SupportingDocument supportingDocument : supportingDocumentsToAdd) {
			if (originalVariable.containsSupportingDocument(supportingDocument) == false) {
				originalVariable.addSupportingDocument(supportingDocument);
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

		registerChangeEvents(changeEvents);
	}
	
	public void disassociateSupportingDocuments(User user,
												Variable variable,
												ArrayList<SupportingDocument> supportingDocumentsToDelete) throws MacawException {

		//Part I: Validate parameters
		checkVariableExists(variable);
		if (supportingDocumentsToDelete.size() == 0) {
			return;
		}
		for (SupportingDocument supportingDocumentToDelete : supportingDocumentsToDelete) {
			supportingDocumentsManager.checkSupportingDocumentExists(supportingDocumentToDelete);
		}
		for (SupportingDocument supportingDocument : supportingDocumentsToDelete) {
			checkDocumentAssociationExists(variable, 
								  		   supportingDocument);
		}
		
		//Part II: Add Document
		variable.removeSupportingDocuments(supportingDocumentsToDelete);

		//Part III: Record Changes	
		ArrayList<MacawChangeEvent> changeEvents
			= ChangeEventGenerator.disassociateSupportingDocumentsChanges(user,
					  													  variable,
					  													  supportingDocumentsToDelete);
		registerChangeEvents(changeEvents);	
	}
	
	public ArrayList<ValueLabel> getValueLabels(User user,
												Variable variable) throws MacawException {
		Variable originalVariable = getOriginalVariable(variable);		
		ArrayList<ValueLabel> valueLabels
			= originalVariable.getValueLabels();
		ArrayList<ValueLabel> cloneValueLabels
			= new ArrayList<ValueLabel>();
		for (ValueLabel currentValueLabel : valueLabels) {
			ValueLabel cloneValueLabel
				= (ValueLabel) currentValueLabel.clone();
			cloneValueLabels.add(cloneValueLabel);
		}

		return cloneValueLabels;		
	}
	
	public void updateOntologyTermReferences(OntologyTerm targetOntologyTerm) {
		int targetIdentifier = targetOntologyTerm.getIdentifier();
		
		for (Variable currentVariable : variables) {
			ArrayList<OntologyTerm> ontologyTerms
				= currentVariable.getOntologyTerms();
			int numberOfOntologyTerms = ontologyTerms.size();
			int foundIndex = -1;
			for (int i = 0; i < numberOfOntologyTerms; i++) {
				int currentIdentifier
					= ontologyTerms.get(i).getIdentifier();
				if (currentIdentifier == targetIdentifier) {
					foundIndex = i;
					break;
				}
			}
			
			if (foundIndex != -1) {
				//item was found
				ontologyTerms.remove(foundIndex);
				if (foundIndex == numberOfOntologyTerms -1) {
					//item was found at the end
					ontologyTerms.add(targetOntologyTerm);					
				}
				else {
					ontologyTerms.add(foundIndex, targetOntologyTerm);
				}				
			}
		}
	}
	
	public void deleteOntologyTermReferences(ArrayList<OntologyTerm> targetOntologyTerms) {
		
		for (OntologyTerm ontologyTermToDelete : targetOntologyTerms) {
			deleteOntologyTermReference(ontologyTermToDelete);
		}
	}
	
	private void deleteOntologyTermReference(OntologyTerm targetOntologyTerm) {
		int targetIdentifier = targetOntologyTerm.getIdentifier();
		
		for (Variable currentVariable : variables) {
			ArrayList<OntologyTerm> ontologyTerms
				= currentVariable.getOntologyTerms();
			int numberOfOntologyTerms = ontologyTerms.size();
			int foundIndex = -1;
			for (int i = 0; i < numberOfOntologyTerms; i++) {
				int currentIdentifier
					= ontologyTerms.get(i).getIdentifier();
				if (currentIdentifier == targetIdentifier) {
					foundIndex = i;
					break;
				}
			}
			
			if (foundIndex != -1) {
				//item was found
				ontologyTerms.remove(foundIndex);
			}
		}		
	}
	
	public int getRawVariableIdentifier(User user,
										RawVariable rawVariable) throws MacawException {
		
		for (Variable currentVariable : variables) {
			if (rawVariable.hasSameDisplayName(currentVariable) == true) {
				return currentVariable.getIdentifier();
			}
		}
		
		return -1;
	}

	public int getDerivedVariableIdentifier(User user,
											DerivedVariable derivedVariable) throws MacawException {
		
		for (Variable currentVariable : variables) {
			if (derivedVariable.hasSameDisplayName(currentVariable) == true) {
				return currentVariable.getIdentifier();
			}
		}
		
		return -1;		
	}
	
	public void clear() {
		variableKey = 0;
		variables.clear();
		variableFromIdentifier.clear();
	}

	public Variable getAlternativeVariable(Variable targetVariable) throws MacawException {	
		Variable originalVariable
			= (Variable) getOriginalVariable(targetVariable);
		return originalVariable.getAlternativeVariable();
	}

	public void setAlternativeVariable(User user,
									   Variable targetVariable,
									   Variable updatedAlternativeVariable) throws MacawException {

		checkVariableExists(targetVariable);
		if (updatedAlternativeVariable != null) {
			checkVariableExists(updatedAlternativeVariable);
		
			if (targetVariable.getIdentifier() == updatedAlternativeVariable.getIdentifier()) {
				//prevents a rare but nasty case of recursion!
				String errorMessage
					= MacawMessages.getMessage("variable.error.selfReferencingAlternativeVariable",
												targetVariable.getDisplayName());
				MacawException exception
					= new MacawException(MacawErrorType.SELF_REFERENCING_ALTERNATIVE_VARIABLE,
										 errorMessage);
				throw exception;
			}
		}

		MacawChangeEvent changeEvent
			= Variable.detectChangesInAlternativeVariable(user, 
														  targetVariable, 
														  updatedAlternativeVariable);
		if (changeEvent == null) {
			//no change so don't bother
			return;
		}

		Variable originalVariable
			= (Variable) getOriginalVariable(targetVariable);				
		Variable originalAlternativeVariable
			= (Variable) getOriginalVariable(updatedAlternativeVariable);
		originalVariable.setAlternativeVariable(originalAlternativeVariable);
		
		//Part III: Record changes
		registerChangeEvent(changeEvent);
	}

	// ==========================================
	// Section Errors and Validation
	// ==========================================
	
	public void checkVariableExists(Variable variable) throws MacawException {
		Variable originalVariable
			= (Variable) getOriginalVariable(variable);
		if (originalVariable == null) {
			//ERROR: derived variable does not exist
			String errorMessage
				= MacawMessages.getMessage("general.error.nonExistentItem",
										   variable.getDisplayName());
			MacawException exception 
				= new MacawException(MacawErrorType.NON_EXISTENT_VARIABLE,
									 errorMessage);
			throw exception;			
		}		
	}

	private void checkVariableDuplicates(Variable targetVariable) throws MacawException {
		String targetVariableDisplayName
			= targetVariable.getDisplayName();
		
		for (Variable currentVariable : variables) {
			String currentVariableDisplayName
				= currentVariable.getDisplayName();
			
			if (targetVariableDisplayName.equals(currentVariableDisplayName) == true) {
				int targetVariableIdentifier = targetVariable.getIdentifier();
				int currentVariableIdentifier = currentVariable.getIdentifier();
				if (targetVariableIdentifier != currentVariableIdentifier) {
					String errorMessage
						= MacawMessages.getMessage("variable.error.duplicateExists",
												   targetVariableDisplayName);
					MacawException exception
						= new MacawException(MacawErrorType.DUPLICATE_VARIABLE,
											 errorMessage);
					throw exception;
				}				
			}
		}		
	}
	
	private void checkDuplicateVariableAssociation(DerivedVariable derivedVariable, 
			   									   Variable sourceVariable) throws MacawException {

		DerivedVariable originalDerivedVariable
			= (DerivedVariable) getOriginalVariable(derivedVariable);
		if (originalDerivedVariable.containsSourceVariable(sourceVariable) == true) {
			//already contains
			String errorMessage
				= MacawMessages.getMessage("variable.error.duplicateTermAssociation",
											derivedVariable.getDisplayName(),
											sourceVariable.getDisplayName());

			MacawException exception 
				= new MacawException(MacawErrorType.DUPLICATE_VARIABLE_ASSOCIATION,
									 errorMessage);				
			throw exception;
		}
	}

	private void checkVariableAssociationExists(DerivedVariable derivedVariable, 
												Variable sourceVariable) throws MacawException {

		DerivedVariable originalDerivedVariable
			= (DerivedVariable) getOriginalVariable(derivedVariable);
		if (originalDerivedVariable.containsSourceVariable(sourceVariable) == false) {	
			MacawException exception = new MacawException();
			String errorMessage
				= MacawMessages.getMessage("derivedVariable.error.nonExistentVariableAssociation",
											derivedVariable.getDisplayName(),
											sourceVariable.getDisplayName());
			exception.addErrorMessage(MacawErrorType.NON_EXISTENT_VARIABLE_ASSOCIATION,
									  errorMessage);
			throw exception;
		}
	}
		
	private void checkDocumentAssociationExists(Variable variable,
												SupportingDocument targetSupportingDocument) throws MacawException {

		MacawException exception = new MacawException();
		String errorMessage
			= MacawMessages.getMessage("variable.error.nonExistentDocumentAssociation",
										targetSupportingDocument.getDisplayName(),
										variable.getDisplayName());
		exception.addErrorMessage(MacawErrorType.NON_EXISTENT_DOCUMENT_ASSOCIATION,
								  errorMessage);

		ArrayList<SupportingDocument> existingSupportingDocuments
			= variable.getSupportingDocuments();

		boolean associationExists = false;
		int targetIdentifier = targetSupportingDocument.getIdentifier();

		for (SupportingDocument existingSupportingDocument : existingSupportingDocuments) {
			int currentIdentifier
				= existingSupportingDocument.getIdentifier();
			if (currentIdentifier == targetIdentifier) {
				associationExists = true;
				break;
			}
		}

		if (associationExists == false) {
			throw exception;
		}		
	}

	private void checkDuplicateDocumentAssociation(Variable variable,
												   SupportingDocument targetSupportingDocument) throws MacawException {
	
		String errorMessage
			= MacawMessages.getMessage("variable.error.duplicateDocumentAssociation",
										targetSupportingDocument.getDisplayName(),
										variable.getDisplayName());
		MacawException exception 
			= new MacawException(MacawErrorType.DUPLICATE_DOCUMENT_ASSOCIATION,
								 errorMessage);

		ArrayList<SupportingDocument> existingSupportingDocuments
			= variable.getSupportingDocuments();
		if (existingSupportingDocuments.size() == 0) {
			//no chance of duplicates because variable doesn't have any
			//supporting documents yet
			return;
		}

		boolean duplicateAssociationExists = false;
		int targetIdentifier = targetSupportingDocument.getIdentifier();

		for (SupportingDocument existingSupportingDocument : existingSupportingDocuments) {
			int currentIdentifier = existingSupportingDocument.getIdentifier();
			if (currentIdentifier == targetIdentifier) {
				duplicateAssociationExists = true;
				break;
			}
		}

		if (duplicateAssociationExists == true) {
			throw exception;
		}		
	}

	private void checkDuplicateTermAssociation(Variable variable, 
			   								   OntologyTerm ontologyTerm) throws MacawException {

		Variable originalVariable
			= (Variable) getOriginalVariable(variable);		
		if (variable.containsOntologyTerm(ontologyTerm) == true) {
			//association already exists
			String errorMessage
				= MacawMessages.getMessage("variable.error.duplicateTermAssociation",
											variable.getDisplayName(),
											ontologyTerm.getDisplayName());
			MacawException exception 
				= new MacawException(MacawErrorType.DUPLICATE_ONTOLOGY_TERM_ASSOCIATION,
									 errorMessage);
			throw exception;
		}	
	}

	private void checkTermAssociationExists(Variable variable, 
			   								OntologyTerm ontologyTerm) throws MacawException {

		Variable originalVariable
			= (Variable) getOriginalVariable(variable);		
		if (variable.containsOntologyTerm(ontologyTerm) == false) {
			MacawException exception = new MacawException();
			String errorMessage
				= MacawMessages.getMessage("variable.error.nonExistentTermAssociation",
											variable.getDisplayName(),
											ontologyTerm.getDisplayName());
			exception.addErrorMessage(MacawErrorType.NON_EXISTENT_ONTOLOGY_TERM_ASSOCIATION,
									  errorMessage);
			throw exception;			
		}	
	}
	
	private void checkListChoices(Variable variable) throws MacawException {
		Category category = new Category();
		category.setName(variable.getCategory());
		//listChoiceManager.getCategoryIdentifier(category, variable);

		if (variable.isCleaned() == true) {
			CleaningState cleaningState = new CleaningState();
			cleaningState.setName(variable.getCleaningStatus());
			listChoiceManager.getCleaningStateIdentifier(cleaningState, variable);
		}

		AvailabilityState availabilityState = new AvailabilityState();
		availabilityState.setName(variable.getAvailability());

		listChoiceManager.getAvailabilityStateIdentifier(availabilityState, variable);
		
		AliasFilePath aliasFilePath = new AliasFilePath();
		aliasFilePath.setAlias(variable.getAlias());
		aliasFilePath.setFilePath(variable.getFilePath());
		listChoiceManager.getAliasFilePathIdentifier(aliasFilePath, variable);
	}

	
	// ==========================================
	// Section Interfaces
	// ==========================================

	// ==========================================
	// Section Overload
	// ==========================================

}

