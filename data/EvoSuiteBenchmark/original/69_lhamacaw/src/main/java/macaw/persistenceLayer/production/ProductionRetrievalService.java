package macaw.persistenceLayer.production;

import macaw.system.Log;
import macaw.system.MacawException;
import macaw.system.SessionProperties;
import macaw.businessLayer.*;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Implements the {@link macaw.businessLayer.MacawRetrievalAPI} interface as a service which retrieves
 * all of its data from a database.    
 * 
 * <code>DemonstrationRetrievalService</code> delegates the implementations 
 * of {@link macaw.businessLayer.MacawRetrievalAPI} methods to manager classes whose names are prefixed
 * with "SQL" (eg: {@link macaw.persistenceLayer.production.SQLVariableManager}).
 * 
 * The main duties of this class are 
 * <ol>
 * <li>validate users before the methods are allowed to proceed</li> 
 * <li>log any exceptions that are thrown.</li>
 * </ol>
 * 
 * <p>
 * For security reasons, exceptions are caught rather than thrown to the calling class.  
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

public class ProductionRetrievalService implements MacawRetrievalAPI {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private SQLConnectionManager sqlConnectionManager;
	private SQLUserManager userManager;
	private SQLListChoiceManager listChoiceManager;
	private SQLVariableManager variableManager;
	private SQLValueLabelManager valueLabelsManager;
	private SQLOntologyTermManager ontologyTermManager;
	private SQLSupportingDocumentsManager documentsManager;
	
	private MacawSecurityAPI securityValidationService;
	private Log log;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public ProductionRetrievalService(SessionProperties sessionProperties) throws MacawException {

		log = sessionProperties.getLog();
		
		sqlConnectionManager 
			= new SQLConnectionManager(sessionProperties);

		
		userManager = new SQLUserManager(null, sqlConnectionManager);
		securityValidationService = userManager;
		valueLabelsManager 
			= new SQLValueLabelManager(null);
		valueLabelsManager.setLog(log);
		
		ontologyTermManager
			= new SQLOntologyTermManager(null);
		ontologyTermManager.setLog(log);

		listChoiceManager 
			= new SQLListChoiceManager(null);
		listChoiceManager.setLog(log);
		
		
		documentsManager 
			= new SQLSupportingDocumentsManager(null);
		documentsManager.setLog(log);
	
		variableManager
			= new SQLVariableManager(null,
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

	public User getUserFromID(User user, String userID) {		
		Connection connection = null;
		try {
			checkValidUser(user);
			connection = sqlConnectionManager.getConnection();
			return userManager.getUserFromID(connection, user, userID);
		}
		catch(MacawException exception) {
			log.logException(exception);
			return null;
		}
		
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public ArrayList<User> getUnverifiedUsers(User user) {
		Connection connection = null;
		try {
			checkValidUser(user);
			connection = sqlConnectionManager.getConnection();
			return userManager.getUnverifiedUsers(connection, user);
		}
		catch(MacawException exception) {
			log.logException(exception);
			ArrayList<User> emptyUserList = new ArrayList<User>();
			return emptyUserList;
		}
		
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}	
	}

	public ArrayList<User> getUsers(User admin) {
		try {
			checkValidAdministrator(admin);
			return userManager.getUsers(admin);
		}
		catch(MacawException exception) {
			log.logException(exception);
			ArrayList<User> emptyUserList = new ArrayList<User>();
			return emptyUserList;
		}
	}
	
	public User getUserFromEmail(User user, String email) {
		Connection connection = null;
		try {
			checkValidUser(user);
			connection = sqlConnectionManager.getConnection();
			return userManager.getUserFromEmail(connection, user, email);

		}
		catch(MacawException exception) {
			log.logException(exception);
			return null;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	public ArrayList<Category> getCategories(User user) {
		Connection connection = null;
		try {
			connection = sqlConnectionManager.getConnection();
			checkValidUser(user);
			return listChoiceManager.getCategories(connection, user);
		}
		catch(MacawException exception) {
			log.logException(exception);
			return null;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	public ArrayList<AvailabilityState> getAvailabilityStates(User user) {
		Connection connection = null;
		try {
			checkValidUser(user);
			connection = sqlConnectionManager.getConnection();
			return listChoiceManager.getAvailabilityStates(connection);
		}
		catch(MacawException exception) {
			log.logException(exception);
			return null;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}

	/**
	 * Methods for managing cleaning states
	 */
	public ArrayList<CleaningState> getCleaningStates(User user) {	
		Connection connection = null;
		try {
			checkValidUser(user);
			connection = sqlConnectionManager.getConnection();
			return listChoiceManager.getCleaningStates(connection, user);
		}
		catch(MacawException exception) {
			log.logException(exception);
			return null;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}	
	}
	
	public ArrayList<ValueLabel> getValueLabels(User user,
												String variableName) {

		Connection connection = null;
		try {
			checkValidUser(user);
			connection = sqlConnectionManager.getConnection();
			return valueLabelsManager.getValueLabels(connection, 
													 user,
													 variableName); 
		}
		catch(MacawException exception) {
			log.logException(exception);
			return null;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}
	
	public ArrayList<OntologyTerm> getOntologyTerms(User user,
													String variableName) {

		Connection connection = null;
		try {
			checkValidUser(user);
			connection = sqlConnectionManager.getConnection();
			return variableManager.getAssociatedOntologyTerms(connection,
															  user,
															  variableName);
		}
		catch(MacawException exception) {
			log.logException(exception);
			return null;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public ArrayList<OntologyTerm> getOntologyTerms(User user,
													Variable variable) {

		Connection connection = null;
		try {
			checkValidUser(user);
			connection = sqlConnectionManager.getConnection();
			return variableManager.getAssociatedOntologyTerms(connection,
					  										  user,
					  										  variable);
		}
		catch(MacawException exception) {
			log.logException(exception);
			return null;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public ArrayList<SupportingDocument> getSupportingDocuments(User user,
														  		String variableName) {

		Connection connection = null;
		try {
			checkValidUser(user);
			connection = sqlConnectionManager.getConnection();
			return variableManager.getAssociatedSupportingDocuments(connection,
						  									  		user,
						  									  		variableName);
		}
		catch(MacawException exception) {
			log.logException(exception);
			return null;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	
	
	/**
	 * Methods for data libraries
	 */
	public ArrayList<AliasFilePath> getAliasFilePaths(User user) {		
		Connection connection = null;
		try {
			checkValidUser(user);
			connection = sqlConnectionManager.getConnection();
			return listChoiceManager.getAliasFilePaths(connection);
		}
		catch(MacawException exception) {
			log.logException(exception);
			return null;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}
	
	public AliasFilePath getAliasFilePath(User user, String cardNumber) {

		Connection connection = null;
		try {
			checkValidUser(user);
			connection = sqlConnectionManager.getConnection();			
			return listChoiceManager.getAliasFilePath(connection, cardNumber);
		}
		catch(MacawException exception) {
			log.logException(exception);
			return null;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}

	public ArrayList<AliasFilePath> getAliasFilePathsMatchingName(User user, 
																  String regularExpression) {

		Connection connection = null;
		try {
			checkValidUser(user);
			connection = sqlConnectionManager.getConnection();
			return listChoiceManager.getAliasFilePathsMatchingName(connection, regularExpression);
		}
		catch(MacawException exception) {
			log.logException(exception);
			return null;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}
	}	

	public ArrayList<Category> getCategoriesForVariable(User user,
														String variableName){

		Connection connection = null;
		try {
			checkValidUser(user);
			connection = sqlConnectionManager.getConnection();
			return variableManager.getCategoriesForVariable(connection, variableName);
		}
		catch(MacawException exception) {
			log.logException(exception);
			ArrayList<Category> emptyList
				= new ArrayList<Category>();
			return emptyList;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	public ArrayList<VariableSummary> getVariableSummariesForCategory(User user, 
																	  String categoryName) {

		Connection connection = null;
		try {
			checkValidUser(user);
			connection = sqlConnectionManager.getConnection();
			return variableManager.getVariableSummariesForCategory(connection, categoryName);
		}
		catch(MacawException exception) {
			log.logException(exception);
			ArrayList<VariableSummary> emptyList
				= new ArrayList<VariableSummary>();
			return emptyList;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	public String[] getVariableNames(User user) {
		Connection connection = null;
		
		try {
			checkValidUser(user);
			connection = sqlConnectionManager.getConnection();
			return variableManager.getVariableNames(connection, user);
		}
		catch(MacawException exception) {
			log.logException(exception);
			return(new String[0]);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}

	public Variable getVariable(User user, String variableName) {
		Connection connection = null;
		try {
			checkValidUser(user);
			connection = sqlConnectionManager.getConnection();
			return variableManager.getVariable(connection, variableName);
		}
		catch(MacawException exception) {
			log.logException(exception);
			return null;
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	private void checkValidUser(User user) throws MacawException {
		securityValidationService.validateUser(user);	
	}

	private void checkValidAdministrator(User user) throws MacawException {
		securityValidationService.validateAdministrator(user);	
	}
	
	// ==========================================
	// Section Overload
	// ==========================================
}

