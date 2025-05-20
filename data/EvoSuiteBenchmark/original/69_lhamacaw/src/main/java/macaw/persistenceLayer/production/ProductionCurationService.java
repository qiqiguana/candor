package macaw.persistenceLayer.production;

import macaw.businessLayer.*;
import macaw.persistenceLayer.*;
import macaw.system.*;

import java.sql.*;
import java.util.ArrayList;


/**
 * Implements the interface {@link macaw.businessLayer.MacawCurationAPI} as a curation service
 * which stores data in a MySQL database.  The structure of ProductionCurationService
 * is very similar to that of {@link macaw.persistenceLayer.demo.DemonstrationCurationService}.
 * Both classes delegate handling API calls to manager classes that are each designed
 * to support a major concept from the package <code>macaw.model</code> 
 * (eg: {@link macaw.businessLayer.SupportingDocument}, {@link macaw.businessLayer.Variable}, 
 * {@link macaw.businessLayer.User}) 
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

public class ProductionCurationService implements MacawCurationAPI {
	
	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private SQLConnectionManager sqlConnectionManager;
	private SQLFilterQueries filterQueries;
	private SQLListChoiceManager listChoiceManager;
	private SQLSupportingDocumentsManager documentsManager;
	private SQLValueLabelManager valueLabelsManager;
	private SQLChangeEventManager changeEventManager;
	private SQLUserManager userManager;
	private SQLVariableManager variableManager;
	private SQLOntologyTermManager ontologyTermManager;
	private MacawSecurityAPI securityValidationService;
	private Log log;
	// ==========================================
	// Section Construction
	// ==========================================
	public ProductionCurationService(SessionProperties sessionProperties) throws MacawException {
		changeEventManager = new SQLChangeEventManager(sessionProperties.getLog());

		sqlConnectionManager 
			= new SQLConnectionManager(sessionProperties);
		userManager = new SQLUserManager(changeEventManager,
										 sqlConnectionManager);
		securityValidationService = userManager;
		sessionProperties.setProperty(SessionProperties.SECURITY_SERVICE, securityValidationService);
		
		log = sessionProperties.getLog();
		userManager.setLog(log);
		
		documentsManager 
			= new SQLSupportingDocumentsManager(changeEventManager);
		documentsManager.setLog(log);
		listChoiceManager 
			= new SQLListChoiceManager(changeEventManager);
		listChoiceManager.setLog(log);
		valueLabelsManager 
			= new SQLValueLabelManager(changeEventManager);
		valueLabelsManager.setLog(log);
		
		ontologyTermManager
			= new SQLOntologyTermManager(changeEventManager);
		ontologyTermManager.setLog(log);

		filterQueries 
			= new SQLFilterQueries(log, listChoiceManager);
		
		variableManager
			= new SQLVariableManager(changeEventManager,
									 listChoiceManager,
									 ontologyTermManager,
									 documentsManager);
		variableManager.setLog(log);
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
	
	public void addRawVariable(User user,
							   RawVariable rawVariable) throws MacawException {
		
		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			variableManager.addRawVariable(connection,
										   user,
										   rawVariable);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}
	
	public void deleteRawVariables(User user,
				  				   ArrayList<RawVariable> rawVariables) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			variableManager.deleteRawVariables(connection,
											   user,
											   rawVariables);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public void updateRawVariable(User user,
								  RawVariable rawVariable) throws MacawException {
	
		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			variableManager.updateRawVariable(connection,
											  user,
											  rawVariable);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	
	public int getRawVariableIdentifier(User user,
										RawVariable rawVariable) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return variableManager.getRawVariableIdentifier(connection,
															user,
															rawVariable);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	public void addDerivedVariable(User user,
								   DerivedVariable derivedVariable) throws MacawException {
	

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			variableManager.addDerivedVariable(connection,
											   user,
											   derivedVariable);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public void deleteDerivedVariables(User user,
				  	  				   ArrayList<DerivedVariable> derivedVariables) throws MacawException {
	
		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			variableManager.deleteDerivedVariables(connection,
											   	   user,
											   	   derivedVariables);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);	
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public void updateDerivedVariable(User user,
									  DerivedVariable derivedVariable) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			variableManager.updateDerivedVariable(connection,
											   	  user,
											   	  derivedVariable);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}

	public int getDerivedVariableIdentifier(User user,
											DerivedVariable derivedVariable) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return variableManager.getDerivedVariableIdentifier(connection,
																user,
																derivedVariable);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	public Variable getVariable(User user, String variableName) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			Variable variable = variableManager.getVariable(connection, variableName);
			return variable;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public Variable getOriginalVariable(User user, Variable variable) throws MacawException {
		checkValidUser(user);

		Connection connection = sqlConnectionManager.getConnection();
		try {
			return variableManager.getOriginalVariable(connection, variable);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public ArrayList<ValueLabel> getValueLabels(User user,
			   									Variable variable) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return valueLabelsManager.getValueLabels(connection, 
													 user,
													 variable); 
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}

	public void addValueLabels(User user,
			   				   Variable variable,
			   				   ArrayList<ValueLabel> valueLabels) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			valueLabelsManager.addValueLabels(connection, 
											  user,
											  variable, 
											  valueLabels);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}
	
	public void updateValueLabels(User user,
								  Variable variable,
								  ArrayList<ValueLabel> valueLabels) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();

		try {
			initialiseConnection(connection);
			valueLabelsManager.updateValueLabels(connection, 
											 	 user,
											 	 variable, 
											 	 valueLabels);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	public void deleteValueLabels(User user,
								  Variable variable,
								  ArrayList<ValueLabel> valueLabels) throws MacawException {
	

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			valueLabelsManager.deleteValueLabels(connection, 
											 	 user,
											 	 variable, 
											 	 valueLabels);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	public int getValueLabelIdentifier(User user,
			  						   Variable variable,
			  						   ValueLabel valueLabel) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return valueLabelsManager.getValueLabelIdentifier(connection, 
						 	 								  variable, 
						 	 								  valueLabel);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	public ArrayList<SupportingDocument> getAllSupportingDocuments(User user) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			ArrayList<SupportingDocument> results
				= documentsManager.getAllSupportingDocuments(connection, 
														  	 user);
			return results;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public ArrayList<SupportingDocument> getSupportingDocuments(User user,
																Variable variable) throws MacawException{
	

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			ArrayList<SupportingDocument> results
				= variableManager.getAssociatedSupportingDocuments(connection, 
																   user,
																   variable);
			return results;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public void addSupportingDocument(User user,
									  SupportingDocument supportingDocument) throws MacawException{
		
		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			documentsManager.addSupportingDocument(connection,
												   user,
												   supportingDocument);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}
	
	public void updateSupportingDocument(User user,
										 SupportingDocument supportingDocument) throws MacawException{
		
		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			documentsManager.updateSupportingDocument(connection,
													  user,
												  	  supportingDocument);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public void deleteSupportingDocuments(User user,
										  ArrayList<SupportingDocument> supportingDocuments) throws MacawException{
		
		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			documentsManager.deleteSupportingDocuments(connection,
													   user,
												  	   supportingDocuments);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public int getSupportingDocumentIdentifier(User user,
			  								   SupportingDocument supportingDocument) throws MacawException{

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return documentsManager.getSupportingDocumentIdentifier(connection,
																	supportingDocument);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
		
	public void associateSupportingDocumentsWithVariable(User user,
														 Variable variable,
														 ArrayList<SupportingDocument> supportingDocuments) throws MacawException{

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			variableManager.associateSupportingDocuments(connection,
														 user,
												  		 variable,
												  		 supportingDocuments);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public void disassociateSupportingDocumentsFromVariable(User user,
														 	Variable variable,
														 	ArrayList<SupportingDocument> supportingDocuments) throws MacawException{

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			variableManager.disassociateSupportingDocuments(connection,
															user,
															variable,
															supportingDocuments);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	public ArrayList<Variable> getSourceVariables(User user,
												  DerivedVariable derivedVariable) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return variableManager.getSourceVariables(connection,
													  user,
											   		  derivedVariable);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public void associateSourceVariables(User user,
										 DerivedVariable derivedVariable,
										 ArrayList<Variable> sourceVariablesToAdd) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			variableManager.associateSourceVariables(connection,
													 user,
													 derivedVariable,
													 sourceVariablesToAdd);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}	
	}

	public void disassociateSourceVariables(User user,
											DerivedVariable derivedVariable,
											ArrayList<Variable> sourceVariablesToDelete) throws MacawException {
		
		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			variableManager.disassociateSourceVariables(connection,
														user,
												  		derivedVariable,
												  		sourceVariablesToDelete);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	public String[] getStudyYears(User user) throws MacawException {
		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return variableManager.getStudyYears(connection);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public ArrayList<VariableSummary> getSummaryDataForAllVariables(User user) throws MacawException {
		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return variableManager.getSummaryDataForAllVariables(connection);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	public Variable getCompleteVariableData(User user, VariableSummary variableSummary) throws MacawException {
		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return variableManager.getCompleteVariableData(connection, 
														   user, 
														   variableSummary);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	public ArrayList<Category> getCategories(User user) throws MacawException {
		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return listChoiceManager.getCategories(connection, user);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	public void addCategory(User user, 
							Category category) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			listChoiceManager.addCategory(connection, 
										  user,
										  category);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public void updateCategory(User user, 
							   Category category) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			listChoiceManager.updateCategory(connection, 
											 user,
											 category);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public void deleteCategories(User user, 
								 ArrayList<Category> categories) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			listChoiceManager.deleteCategories(connection, user, categories);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}

	public int getCategoryIdentifier(User user,
									 Variable variable,
				  					 Category category) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return listChoiceManager.getCategoryIdentifier(connection, 
														   variable,
														   category);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}

	/**
	 * Methods for data libraries
	 */
	public ArrayList<AliasFilePath> getAliasFilePaths(User user) throws MacawException {		

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return listChoiceManager.getAliasFilePaths(connection);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}

	public void addAliasFilePath(User user, 
			 				   AliasFilePath aliasFilePath) throws MacawException {
		
		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			listChoiceManager.addAliasFilePath(connection, aliasFilePath);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public void updateAliasFilePath(User user, 
								  AliasFilePath aliasFilePath) throws MacawException {
		
		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			listChoiceManager.updateAliasFilePath(connection, 
												  user,
												  aliasFilePath);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}
	
	public void deleteAliasFilePaths(User user, 
				 					ArrayList<AliasFilePath> aliasFilePaths) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			listChoiceManager.deleteAliasFilePaths(connection, aliasFilePaths);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}	
	}

	public int getAliasFilePathIdentifier(User user, 
									      Variable variable,
									      AliasFilePath aliasFilePath) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return listChoiceManager.getAliasFilePathIdentifier(connection, 
																variable,
																aliasFilePath);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}

	public String getFilePathFromAlias(User user,
									   String currentAlias) throws MacawException {
		
		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return listChoiceManager.getFilePathFromAlias(connection, 
														  currentAlias);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	/**
	 * Methods for managing cleaning states
	 */
	public ArrayList<CleaningState> getCleaningStates(User user) throws MacawException {	

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return listChoiceManager.getCleaningStates(connection, user);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}	
	}

	public void addCleaningState(User user, 
			 					 CleaningState cleaningState) throws MacawException {
		
		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			listChoiceManager.addCleaningState(connection, cleaningState);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}	
	}
	
	public void updateCleaningState(User user, 
									CleaningState cleaningState) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			listChoiceManager.updateCleaningState(connection, user, cleaningState);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}
	
	public void deleteCleaningStates(User user, 
				 					 ArrayList<CleaningState> cleaningStates) throws MacawException {
		
		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			listChoiceManager.deleteCleaningStates(connection, cleaningStates);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}

	public int getCleaningStateIdentifier(User user, 
										  Variable variable,
										  CleaningState cleaningState) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return listChoiceManager.getCleaningStateIdentifier(connection, 
																variable,
																cleaningState);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}
	
	/**
	 * Methods for managing availability states
	 */
	public ArrayList<AvailabilityState> getAvailabilityStates(User user) throws MacawException {
		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return listChoiceManager.getAvailabilityStates(connection);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}

	public void addAvailabilityState(User user, 
			 						 AvailabilityState availabilityState) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			listChoiceManager.addAvailabilityState(connection,
												   user,
												   availabilityState);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public void updateAvailabilityState(User user, 
										AvailabilityState availabilityState) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			listChoiceManager.updateAvailabilityState(connection, availabilityState);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}
	
	public void deleteAvailabilityStates(User user, 
								   		 ArrayList<AvailabilityState> availabilityStates) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			listChoiceManager.deleteAvailabilityStates(connection, availabilityStates);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}

	public int getAvailabilityStateIdentifier(User user, 
											  Variable variable,
	   		 								  AvailabilityState availabilityState) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return listChoiceManager.getAvailabilityStateIdentifier(connection, 
																	variable,
																	availabilityState);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}

	public ArrayList<VariableSummary> filterVariableSummaries(User user,
			   										  		  String searchText,
			   										  		  String year,
			   										  		  String category,
			   										  		  VariableTypeFilter variableTypeFilter) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return filterQueries.filterVariableSummaries(connection,
														 searchText,
														 year,
														 category,
														 variableTypeFilter);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}
	
	public ArrayList<SupportingDocument> filterSupportingDocuments(User user,
			   													   String documentTitle,
			   													   String documentCode) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return filterQueries.filterSupportingDocuments(connection,
														   documentTitle,
														   documentCode);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}

	public ArrayList<OntologyTerm> filterOntologyTerms(User user,
													   String term,
													   String description) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return filterQueries.filterOntologyTerms(connection,
													 term,
													 description);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}
	
	public ArrayList<OntologyTerm> getAllOntologyTerms(User user) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return ontologyTermManager.getAllOntologyTerms(connection,
														   user);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public ArrayList<OntologyTerm> getOntologyTerms(User user,
													Variable variable) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return variableManager.getAssociatedOntologyTerms(connection,
															  user,
															  variable);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public void addOntologyTerm(User user,
								OntologyTerm ontologyTerm) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			ontologyTermManager.addOntologyTerm(connection,
												user,
												ontologyTerm);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}				
	}
	
	public void updateOntologyTerm(User user,
								   OntologyTerm ontologyTerm) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			ontologyTermManager.updateOntologyTerm(connection,
												   user,
												   ontologyTerm);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public void deleteOntologyTerms(User user,
								    ArrayList<OntologyTerm> ontologyTermsToDelete) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			ontologyTermManager.deleteOntologyTerms(connection,
												    user,
												    ontologyTermsToDelete);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	public int getOntologyTermIdentifier(User user,
		    							 OntologyTerm ontologyTerm) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return ontologyTermManager.getOntologyTermIdentifier(connection,
																 ontologyTerm);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	public void associateOntologyTermsWithVariable(User user,
										   		   Variable variable,
										   		   ArrayList<OntologyTerm> ontologyTerms) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			variableManager.associateOntologyTerms(connection,
												   user,
												   variable,
												   ontologyTerms);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public void disassociateOntologyTermsFromVariable(User user,
	   	  											  Variable variable,
	   	  											  ArrayList<OntologyTerm> ontologyTerms) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			variableManager.disassociateOntologyTerms(connection,
													  user,
													  variable,
													  ontologyTerms);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	public ArrayList<MacawChangeEvent> getChangeHistoryForVariable(User user,
				   												   Variable variable) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return changeEventManager.getChangeHistoryForVariable(connection,
																  variable);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}
	public ArrayList<MacawChangeEvent> getChangeHistoryForSupportingDocument(User user,
																			 SupportingDocument supportingDocument) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return changeEventManager.getChangeHistoryForSupportingDocument(connection,
																			supportingDocument);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}

	public ArrayList<MacawChangeEvent> getChangeHistoryForValueLabels(User user,
																	  Variable variable) throws MacawException {

		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return changeEventManager.getChangeHistoryForValueLabels(connection,
																	 variable);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	public ArrayList<MacawChangeEvent> getChangeHistoryForListChoices(User user) throws MacawException {
		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return changeEventManager.getChangeHistoryForListChoices(connection);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}	
	}

	
	public ArrayList<MacawChangeEvent> getChangeHistoryByUser(User user) throws MacawException {
		checkValidUser(user);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return changeEventManager.getChangeHistoryForUser(connection,
															  user);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	public ArrayList<User> getUsers(User admin) throws MacawException {
		checkValidAdministrator(admin);
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return userManager.getUsers(admin);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	public void addUser(User admin, 
						User user) throws MacawException {
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			userManager.addUser(connection, 
								admin, 
								user);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public void updateUser(User admin, User user) throws MacawException {
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			userManager.updateUser(connection, 
									admin,
									user);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public void deleteUsers(User admin, ArrayList<User> usersToDelete) throws MacawException {
		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			userManager.deleteUsers(connection, 
									admin,
									usersToDelete);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}				
	}

	public int getUserIdentifier(User admin, 
								 User user) throws MacawException {
		
		Connection connection = sqlConnectionManager.getConnection();
		try {
			return userManager.getUserIdentifier(connection, 
												 admin,
												 user);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}				
	}
		
	public void checkValidAdministrator(User admin) throws MacawException {
		Connection connection = sqlConnectionManager.getConnection();
		try {
			securityValidationService.validateAdministrator(admin);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}				
	}
	
	public void clear(User admin) throws MacawException {
		Connection connection = sqlConnectionManager.getConnection();
		try {			
			initialiseConnection(connection);
			checkValidAdministrator(admin);

			changeEventManager.clear(connection);
			userManager.clear(connection);
			valueLabelsManager.clear(connection);
			listChoiceManager.clear(connection);
			documentsManager.clear(connection);
			ontologyTermManager.clear(connection);
			variableManager.clear(connection);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}
	
	public void clearAllChanges(User admin) throws MacawException {
		Connection connection = sqlConnectionManager.getConnection();
		try {
			securityValidationService.validateAdministrator(admin);
			changeEventManager.clearAllChanges(connection);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}				
	}
	
	public ArrayList<MacawChangeEvent> getAllChanges(User admin) throws MacawException {
		Connection connection = sqlConnectionManager.getConnection();
		try {
			securityValidationService.validateAdministrator(admin);
			return changeEventManager.getAllChanges(connection);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}				
	}


	public Variable getAlternativeVariable(User user,
			   Variable targetVariable) throws MacawException {
		Connection connection = sqlConnectionManager.getConnection();
		try {
			securityValidationService.validateAdministrator(user);
			return variableManager.getAlternativeVariable(connection, user, targetVariable);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}				
	}

	public void setAlternativeVariable(User user, 
									   Variable targetVariable,
									   Variable alternativeVariable) throws MacawException {

		Connection connection = sqlConnectionManager.getConnection();
		try {
			initialiseConnection(connection);
			checkValidUser(user);
			variableManager.setAlternativeVariable(connection, 
												   user, 
												   targetVariable,
												   alternativeVariable);
			commitDatabaseChanges(connection);
		}
		catch(MacawException exception) {
			rollBack(connection);
			throw exception;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}			
	}
	
	public void checkValidUser(User user) throws MacawException {
		securityValidationService.validateUser(user);		
	}

	public int getNumberOfConnections() {
		return sqlConnectionManager.getNumberOfConnections();
	}
	
	
	public void initialiseConnection(Connection connection) throws MacawException {
		try {
			connection.setAutoCommit(false);
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToInitialiseConnection");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_INITIALISE_CONNECTION,
									 errorMessage);
			throw macawException;
		}	
	}

	public void commitDatabaseChanges(Connection connection) throws MacawException {
		try {
			connection.commit();
			connection.setAutoCommit(true);
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCommitChanges");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_COMMIT_CHANGES,
									 errorMessage);
			throw macawException;
		}
	}

	
	public void rollBack(Connection connection) throws MacawException {
		try {
			connection.rollback();
			connection.setAutoCommit(true);
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToRollbackChanges");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_ROLLBACK,
									 errorMessage);
			throw macawException;
		}
	}
	
	// ==========================================
	// Section Overload
	// ==========================================

}

