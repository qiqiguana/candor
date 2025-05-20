package macaw.persistenceLayer.demo;

import macaw.businessLayer.*;
import macaw.persistenceLayer.DummyDataProvider;
import macaw.system.*;

import java.util.ArrayList;


/**
 * Implements the {@link macaw.businessLayer.MacawCurationAPI} interface as a service which manages
 * all of its data in-memory instead of in a database.  This class allows Macaw to run in 
 * a demonstration mode off a pen drive without requiring the client machine to have MySQL
 * installed.  DemonstrationCurationService is useful in testing as well.  All of the classes
 * in the <code> macaw.test</code> sub-packages can be applied to either in-memory or SQL
 * versions of Macaw services.  The difference in implementations make it easy to rapidly
 * isolate the causes of failed test cases.  In general, the in-memory implementation is
 * treated as the gold test standard.
 * 
 * <p>
 * DemonstrationCurationService delegates implementations of the API methods to manager
 * classes for major concepts.  All of these manager classes have names that are prefixed
 * with "InMemory", eg: {@link macaw.persistenceLayer.demo.InMemoryVariableManager}.
 * 
 * <p>
 * The manager classes support persistence using the following policy
 * <ul>
 * <li>when an item is added to a collection, treat it as the stored copy </li>
 * <li>when calling classes request the item, return a cloned version that serves
 * as a working copy.</li>
 * </ul>
 * <p>
 * A more detailed description of how this works is given 
 * in {@link macaw.persistenceLayer.demo.InMemorySupportingDocumentsManager}.
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

public class DemonstrationCurationService implements MacawCurationAPI {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	protected InMemoryListChoiceManager listChoiceManager;
	protected InMemorySupportingDocumentsManager supportingDocumentsManager;
	protected InMemoryChangeEventManager changeEventManager;
	protected InMemoryVariableManager variableManager;
	protected InMemoryValueLabelManager valueLabelManager;
	protected InMemoryOntologyTermManager ontologyTermManager;
	protected InMemoryUserManager userManager;

	protected InMemoryVariableFilter variableFilter;
	protected InMemorySupportingDocumentFilter supportingDocumentFilter;
	protected InMemoryOntologyTermFilter ontologyTermFilter;

	protected MacawSecurityAPI securityValidationService;
	protected User admin;
	protected Log log;
	// ==========================================
	// Section Construction
	// ==========================================
	public DemonstrationCurationService() {
		init(false);
	}
	
	public DemonstrationCurationService(boolean automatedTestingMode) {
		init(automatedTestingMode);
	}
	
	private void init(boolean automatedTestingMode) {
		
		admin = new User("admin", "admin");
		log = new Log();
		changeEventManager = new InMemoryChangeEventManager();	
		userManager = new InMemoryUserManager(changeEventManager,
											  log,
											  admin);
		securityValidationService = userManager;
		
		ontologyTermManager
			= new InMemoryOntologyTermManager(changeEventManager);

		supportingDocumentsManager 
			= new InMemorySupportingDocumentsManager(changeEventManager);
		listChoiceManager 
			= new InMemoryListChoiceManager(changeEventManager);
		variableManager
			= new InMemoryVariableManager(changeEventManager,
										  listChoiceManager,
										  ontologyTermManager,
										  supportingDocumentsManager);	
		valueLabelManager
			= new InMemoryValueLabelManager(changeEventManager);

		variableFilter
			= new InMemoryVariableFilter();
		supportingDocumentFilter 
			= new InMemorySupportingDocumentFilter();
		ontologyTermFilter
			= new InMemoryOntologyTermFilter();
		
		try {
			User jsmith = new User("jsmith", "cool");
		
			if (automatedTestingMode == false) {
				DummyDataProvider dummyDataProvider
					= new DummyDataProvider(this, admin);
				dummyDataProvider.populateDatabase(jsmith);
				
			}
		}
		catch(MacawException exception) {
			exception.printErrors();
			Log log = new Log();
			log.logException(exception);			
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

	//Interface: MacawDatabase
	
	public void addRawVariable(User user,
			   				   RawVariable rawVariable) throws MacawException {
		checkValidUser(user);
		variableManager.addRawVariable(user, rawVariable);
	}

	public void deleteRawVariables(User user,
								   ArrayList<RawVariable> rawVariables) throws MacawException {
		checkValidUser(user);
		variableManager.deleteRawVariables(user, rawVariables);
	}

	public void updateRawVariable(User user,
								  RawVariable rawVariable) throws MacawException {

		checkValidUser(user);
		variableManager.updateRawVariable(user, rawVariable);
	}

	public void addDerivedVariable(User user,
								   DerivedVariable derivedVariable) throws MacawException {
		checkValidUser(user);
		variableManager.addDerivedVariable(user, derivedVariable);
	}

	public void deleteDerivedVariables(User user,
									   ArrayList<DerivedVariable> derivedVariables) throws MacawException {
		checkValidUser(user);
		variableManager.deleteDerivedVariables(user, derivedVariables);
	}

	public void updateDerivedVariable(User user,
									  DerivedVariable derivedVariable) throws MacawException {
		checkValidUser(user);
		variableManager.updateDerivedVariable(user, derivedVariable);
	}

	public int getRawVariableIdentifier(User user,
										RawVariable rawVariable) throws MacawException {
		checkValidUser(user);
		return variableManager.getRawVariableIdentifier(user, rawVariable);
	}
	
	public int getDerivedVariableIdentifier(User user,
											DerivedVariable derivedVariable) throws MacawException {
		checkValidUser(user);
		return variableManager.getDerivedVariableIdentifier(user, derivedVariable);		
	}
	
	public Variable getVariable(User user,
							  	String variableName) throws MacawException {
		checkValidUser(user);
		return variableManager.getVariable(user, variableName);
	}
	
	public ArrayList<Variable> getSourceVariables(User user,
												  DerivedVariable derivedVariable) throws MacawException {
		checkValidUser(user);
		return variableManager.getSourceVariables(user, derivedVariable);
	}
	
	public void associateSourceVariables(User user,
										 DerivedVariable derivedVariable,
										 ArrayList<Variable> sourceVariablesToAdd) throws MacawException {

		checkValidUser(user);
		variableManager.associateSourceVariables(user, 
												 derivedVariable,
												 sourceVariablesToAdd);		
	}

	public void disassociateSourceVariables(User user,
											DerivedVariable derivedVariable,
											ArrayList<Variable> sourceVariablesToDelete) throws MacawException {
		
		checkValidUser(user);
		variableManager.disassociateSourceVariables(user, 
											  		derivedVariable, 
											  		sourceVariablesToDelete);
	}

	public ArrayList<ValueLabel> getValueLabels(User user,
			   				   					Variable variable) throws MacawException {
		checkValidUser(user);
		Variable originalVariable
			= variableManager.getOriginalVariable(variable);
		return valueLabelManager.getValueLabels(user,
												originalVariable);
	}
	
	public void addValueLabels(User user,
			   				   Variable variable,
			   				   ArrayList<ValueLabel> valueLabels) throws MacawException {
		checkValidUser(user);
		Variable originalVariable
			= variableManager.getOriginalVariable(variable);
		valueLabelManager.addValueLabels(user,
										 originalVariable,
									   	 valueLabels);
	}
	
	public void updateValueLabels(User user,
				  				  Variable variable,
				  				  ArrayList<ValueLabel> valueLabels) throws MacawException {
		checkValidUser(user);
		Variable originalVariable
			= variableManager.getOriginalVariable(variable);
		valueLabelManager.updateValueLabels(user,
										   originalVariable,
										   valueLabels);
	}
	
	public void deleteValueLabels(User user,
				  				  Variable variable,
				  				  ArrayList<ValueLabel> valueLabels) throws MacawException {
		checkValidUser(user);
		checkVariableExists(variable);
		
		Variable originalVariable
			= variableManager.getOriginalVariable(variable);
		valueLabelManager.deleteValueLabels(user,
											originalVariable,
				  						  	valueLabels);
	}
	
	public int getValueLabelIdentifier(User user,
									   Variable variable,
									   ValueLabel valueLabel) {
		return valueLabelManager.getValueLabelIdentifier(variable, valueLabel);
	}
	
	public String[] getStudyYears(User user) throws MacawException {
		checkValidUser(user);
		return variableManager.getStudyYears(user);
	}

	public ArrayList<VariableSummary> getSummaryDataForAllVariables(User user) throws MacawException {
		checkValidUser(user);
		return variableManager.getSummaryDataForAllVariables(user);
	}
	
	public Variable getCompleteVariableData(User user, VariableSummary variableSummary) throws MacawException {
		checkValidUser(user);
		return variableManager.getCompleteVariableData(user,
													   variableSummary);
	}

	public Variable getOriginalVariable(User user, Variable variable) throws MacawException {
		checkValidUser(user);
		return variableManager.getOriginalVariable(variable);
	}
	
	public ArrayList<SupportingDocument> getAllSupportingDocuments(User user) throws MacawException {
		checkValidUser(user);
		
		ArrayList<SupportingDocument> allSupportingDocuments
			= supportingDocumentsManager.getAllSupportingDocuments(user);
		return allSupportingDocuments;
	}
	
	public ArrayList<SupportingDocument> getSupportingDocuments(User user, 
																Variable variable) throws MacawException {
		checkValidUser(user);
		checkVariableExists(variable);
		
		ArrayList<SupportingDocument> results
			= variableManager.getSupportingDocuments(user,
													 variable);
		return results;
	}
	
	public void addSupportingDocument(User user,
									  SupportingDocument supportingDocument) throws MacawException {

		checkValidUser(user);
		supportingDocumentsManager.addSupportingDocument(user,
														 supportingDocument);
	}
		
	public void updateSupportingDocument(User user,
										 SupportingDocument revisedSupportingDocument) throws MacawException {		

		checkValidUser(user);
		supportingDocumentsManager.updateSupportingDocument(user,
															revisedSupportingDocument);	
	}
	
	public void deleteSupportingDocuments(User user,
										  ArrayList<SupportingDocument> supportingDocuments) throws MacawException {

		checkValidUser(user);
		supportingDocumentsManager.deleteSupportingDocuments(user,
															 supportingDocuments);
	}

	public int getSupportingDocumentIdentifier(User user,
											   SupportingDocument supportingDocument) throws MacawException {
		return supportingDocumentsManager.getSupportingDocumentIdentifier(supportingDocument);
	}

	public void associateSupportingDocumentsWithVariable(User user,
											 			 Variable variable,
											 			 ArrayList<SupportingDocument> supportingDocuments) throws MacawException {

		checkValidUser(user);
		variableManager.associateSupportingDocuments(user,
													 variable,	
													 supportingDocuments);
	}	

	public void disassociateSupportingDocumentsFromVariable(User user,
															Variable variable,
															ArrayList<SupportingDocument> supportingDocuments) throws MacawException {

		checkValidUser(user);
		variableManager.disassociateSupportingDocuments(user,
														variable, 
														supportingDocuments);
	}
	
	public ArrayList<Category> getCategories(User user) throws MacawException {
		checkValidUser(user);
		return listChoiceManager.getCategories(user);
	}
		
	/**
	 * methods for managing categories
	 */
	public void addCategory(User user, 
							Category dataLibrary) throws MacawException {
		checkValidUser(user);
		listChoiceManager.addCategory(user, dataLibrary);
	}

	public void updateCategory(User user, 
							   Category dataLibrary) throws MacawException {
		checkValidUser(user);
		ArrayList<Variable> variables 
			= variableManager.getOriginalVariables(user);
		listChoiceManager.updateCategory(user,
										 dataLibrary,
										 variables);		
	}

	public void deleteCategories(User user, 
								 ArrayList<Category> categories) throws MacawException {
		checkValidUser(user);
		ArrayList<Variable> variables 
			= variableManager.getOriginalVariables(user);
		listChoiceManager.deleteCategories(user, 
										   categories, 
										   variables);		
	}	

	public int getCategoryIdentifier(User user,
									 Variable variable,
			 						 Category category) throws MacawException {
		checkValidUser(user);
		return listChoiceManager.getCategoryIdentifier(category, variable);
	}
	
	/**
	 * Methods for managing cleaning states
	 */
	public ArrayList<CleaningState> getCleaningStates(User user) throws MacawException {		
		checkValidUser(user);
		return listChoiceManager.getCleaningStates(user);
	}

	public void addCleaningState(User user, 
			 						 CleaningState cleaningState) throws MacawException {
		checkValidUser(user);
		listChoiceManager.addCleaningState(user, cleaningState);
	}
	
	public void updateCleaningState(User user, 
										CleaningState cleaningState) throws MacawException {
		checkValidUser(user);
		ArrayList<Variable> variables 
			= variableManager.getOriginalVariables(user);
		listChoiceManager.updateCleaningState(user, cleaningState, variables);		
	}
	
	public void deleteCleaningStates(User user, 
				 						 ArrayList<CleaningState> cleaningStates) throws MacawException {
		checkValidUser(user);
		ArrayList<Variable> variables 
			= variableManager.getOriginalVariables(user);
		listChoiceManager.deleteCleaningStates(user, 
											   cleaningStates,
											   variables);		
	}

	public int getCleaningStateIdentifier(User user,
										  Variable variable,
									 	  CleaningState cleaningState) throws MacawException {
		checkValidUser(user);
		return listChoiceManager.getCleaningStateIdentifier(cleaningState, variable);
	}

	/**
	 * Methods for managing availability states
	 */
	public ArrayList<AvailabilityState> getAvailabilityStates(User user) throws MacawException {
		checkValidUser(user);
		return listChoiceManager.getAvailabilityStates(user);
	}

	public void addAvailabilityState(User user, 
			 						 AvailabilityState availabilityState) throws MacawException {
		checkValidUser(user);
		listChoiceManager.addAvailabilityState(user, 
											   availabilityState);
	}
	
	public void updateAvailabilityState(User user, 
										AvailabilityState availabilityState) throws MacawException {

		checkValidUser(user);
		ArrayList<Variable> variables 
			= variableManager.getOriginalVariables(user);
		listChoiceManager.updateAvailabilityState(user, 
												  availabilityState,
												  variables);		
	}
	
	public void deleteAvailabilityStates(User user, 
				 						 ArrayList<AvailabilityState> availabilityStatesToDelete) throws MacawException {

		checkValidUser(user);
		ArrayList<Variable> variables 
			= variableManager.getOriginalVariables(user);
		listChoiceManager.deleteAvailabilityStates(user, 
												   availabilityStatesToDelete,
												   variables);		
	}

	public int getAvailabilityStateIdentifier(User user,
										      Variable variable,
											  AvailabilityState availabilityState) throws MacawException {
		checkValidUser(user);
		return listChoiceManager.getAvailabilityStateIdentifier(availabilityState, variable);
	}

	/**
	 * Methods for data libraries
	 */
	public ArrayList<AliasFilePath> getAliasFilePaths(User user) throws MacawException {		
		checkValidUser(user);
		return listChoiceManager.getAliasFilePaths(user);
	}

	public void addAliasFilePath(User user, 
							     AliasFilePath aliasFilePath) throws MacawException {
		checkValidUser(user);
		listChoiceManager.addAliasFilePath(user, aliasFilePath);
	}
	
	public void updateAliasFilePath(User user, 
									AliasFilePath aliasFilePath) throws MacawException {
		checkValidUser(user);
		ArrayList<Variable> variables 
			= variableManager.getOriginalVariables(user);
		listChoiceManager.updateAliasFilePath(user, 
											  aliasFilePath,
											  variables);		
	}
	
	public void deleteAliasFilePaths(User user, 
				 					 ArrayList<AliasFilePath> aliasFilePaths) throws MacawException {

		checkValidUser(user);
		ArrayList<Variable> variables 
			= variableManager.getOriginalVariables(user);
		listChoiceManager.deleteAliasFilePaths(user, 
											   aliasFilePaths,
											   variables);		
	}

	public int getAliasFilePathIdentifier(User user,
										  Variable variable,
										  AliasFilePath aliasFilePath) throws MacawException {
		checkValidUser(user);
		return listChoiceManager.getAliasFilePathIdentifier(aliasFilePath, variable);
	}

	public ArrayList<VariableSummary> filterVariableSummaries(User user,
			   								   				  String searchText,
			   								   				  String year,
			   								   				  String category,
			   								   				  VariableTypeFilter variableTypeFilter) throws MacawException {
				
		checkValidUser(user);				
		InMemoryVariableFilter inMemoryVariableFilter
			= new InMemoryVariableFilter();
		ArrayList<Variable> variables 
			= variableManager.getOriginalVariables(user);
		inMemoryVariableFilter.setVariables(variables);
		ArrayList<VariableSummary> results
			= inMemoryVariableFilter.filterVariableSummaries(user,
													 		 searchText,
													 		 year,
													 		 category,
													 		 variableTypeFilter);
		return results;				
	}

	public String getFilePathFromAlias(User user,
									   String currentAlias) throws MacawException {

		checkValidUser(user);
		return listChoiceManager.getFilePathFromAlias(currentAlias);
	}

	public ArrayList<SupportingDocument> filterSupportingDocuments(User user,
			   													   String documentTitleFilter,
			   													   String documentCodeFilter) throws MacawException {
		checkValidUser(user);
		ArrayList<SupportingDocument> allSupportingDocuments
			= supportingDocumentsManager.getAllSupportingDocuments(user);
		supportingDocumentFilter.setSupportingDocuments(allSupportingDocuments);
		return supportingDocumentFilter.filterSupportingDocuments(user, 
																  documentTitleFilter, 
																  documentCodeFilter);
	}
	
	public ArrayList<OntologyTerm> filterOntologyTerms(User user,
													   String termFilter,
													   String descriptionFilter) throws MacawException {
		ArrayList<OntologyTerm> results = new ArrayList<OntologyTerm>();
		
		checkValidUser(user);
		ArrayList<OntologyTerm> allOntologyTerms
			= ontologyTermManager.getAllOntologyTerms(user);
		ontologyTermFilter.setOntologyTerms(allOntologyTerms);
		return ontologyTermFilter.filterOntologyTerms(user, 
													  termFilter, 
													  descriptionFilter);
	}

	
	public ArrayList<OntologyTerm> getAllOntologyTerms(User user) throws MacawException {
		checkValidUser(user);
		return ontologyTermManager.getAllOntologyTerms(user);
	}

	public ArrayList<OntologyTerm> getOntologyTerms(User user,
													Variable variable) throws MacawException {
		checkValidUser(user);
		return variableManager.getAssociatedOntologyTerms(user, variable);
	}
	
	public void addOntologyTerm(User user,
								OntologyTerm ontologyTerm) throws MacawException {
		checkValidUser(user);
		ontologyTermManager.addOntologyTerm(user,
											ontologyTerm);
	}
	
	public void updateOntologyTerm(User user,
								   OntologyTerm ontologyTerm) throws MacawException {
		checkValidUser(user);
		ontologyTermManager.updateOntologyTerm(user,
											   ontologyTerm);		
		variableManager.updateOntologyTermReferences(ontologyTerm);
	}
	
	public void deleteOntologyTerms(User user,
								    ArrayList<OntologyTerm> ontologyTerms) throws MacawException {
		checkValidUser(user);
		ontologyTermManager.deleteOntologyTerms(user,
											    ontologyTerms);		
		variableManager.deleteOntologyTermReferences(ontologyTerms);
	}

	public int getOntologyTermIdentifier(User user,
										OntologyTerm ontologyTerm) throws MacawException {
		checkValidUser(user);
		return ontologyTermManager.getOntologyTermIdentifier(ontologyTerm);
	}

	public void associateOntologyTermsWithVariable(User user,
										   		   Variable variable,
										   		   ArrayList<OntologyTerm> ontologyTerms) throws MacawException {
		checkValidUser(user);
		variableManager.associateOntologyTerms(user,
											   variable,
											   ontologyTerms);		
	}
	
	public void disassociateOntologyTermsFromVariable(User user,
	   	  											  Variable variable,
	   	  											  ArrayList<OntologyTerm> ontologyTerms) throws MacawException {
		checkValidUser(user);
		variableManager.disassociateOntologyTerms(user,
												variable,
												ontologyTerms);		
	}

	public ArrayList<MacawChangeEvent> getChangeHistoryForVariable(User user,
		  	   													   Variable variable) throws MacawException {
		checkValidUser(user);
		return changeEventManager.getChangeHistoryForVariable(user, variable);		
	}
	public ArrayList<MacawChangeEvent> getChangeHistoryForSupportingDocument(User user,
																			 SupportingDocument supportingDocument) throws MacawException {
		checkValidUser(user);
		return changeEventManager.getChangeHistoryForSupportingDocument(user, supportingDocument);		
	}
	
	public ArrayList<MacawChangeEvent> getChangeHistoryForValueLabels(User user,
																	  Variable variable) throws MacawException {
		checkValidUser(user);
		return changeEventManager.getChangeHistoryForValueLabels(user, variable);		
	}
	
	public ArrayList<MacawChangeEvent> getChangeHistoryForListChoices(User user) throws MacawException {
		checkValidUser(user);
		return changeEventManager.getChangeHistoryForListChoices();				
	}

	public ArrayList<MacawChangeEvent> getChangeHistoryByUser(User user) throws MacawException {
		checkValidUser(user);
		return changeEventManager.getChangeHistoryByUser(user);
	}

	public void addChangeEvents(MacawChangeEvent changeEvent) throws MacawException {
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		changeEvents.add(changeEvent);
		changeEventManager.registerChangeEvents(changeEvents);
	}

	//admin features for management of users.
	public ArrayList<User> getUsers(User admin) throws MacawException {
		return userManager.getUsers(admin);
	}
	
	public void addUser(User admin, User user) throws MacawException {
		userManager.addUser(admin, user);
	}
	
	public void updateUser(User admin, User user) throws MacawException {
		userManager.updateUser(admin, user);		
	}
	
	public void deleteUsers(User admin, ArrayList<User> usersToDelete) throws MacawException {
		userManager.deleteUsers(admin, usersToDelete);		
	}

	public int getUserIdentifier(User admin,
								 User user) throws MacawException {
		return userManager.getUserIdentifier(user);
	}

	public void checkValidUser(User user) throws MacawException {
		securityValidationService.validateUser(user);
	}
	
	private void checkVariableExists(Variable variable) throws MacawException {
		variableManager.checkVariableExists(variable);
	}
	
	public void checkValidAdministrator(User administrator) throws MacawException {
		securityValidationService.validateAdministrator(administrator);
	}

	public void clear(User admin) throws MacawException {
		userManager.validateAdministrator(admin);
		listChoiceManager.clear();
		supportingDocumentsManager.clear();
		changeEventManager.clear();	
		variableManager.clear();
		valueLabelManager.clear();
		ontologyTermManager.clear();
		userManager.clear();
	}
	
	public void clearAllChanges(User admin) throws MacawException {
		userManager.validateAdministrator(admin);
		changeEventManager.clear();
	}
	
	public ArrayList<MacawChangeEvent> getAllChanges(User admin) throws MacawException {
		userManager.validateAdministrator(admin);
		return changeEventManager.getAllChanges();
	}

	public Variable getAlternativeVariable(User user,
										   Variable targetVariable) throws MacawException {
		return variableManager.getAlternativeVariable(targetVariable);
	}

	public void setAlternativeVariable(User user, 
									   Variable targetVariable,
									   Variable alternativeVariable) throws MacawException {

		variableManager.setAlternativeVariable(user,
											   targetVariable,
											   alternativeVariable);
	}

	
	// ==========================================
	// Section Overload
	// ==========================================

}

