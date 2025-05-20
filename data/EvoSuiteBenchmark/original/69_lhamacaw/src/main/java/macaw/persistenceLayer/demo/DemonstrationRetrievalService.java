package macaw.persistenceLayer.demo;
 
import macaw.system.*;
import macaw.businessLayer.*;

import java.util.ArrayList;

/**
 * Implements the {@link macaw.businessLayer.MacawRetrievalAPI} interface as a service which retrieves
 * all of its data from in-memory objects rather than from a database.  This class allows 
 * Macaw to run in a demonstration mode off a pen drive without requiring the client machine 
 * to have MySQL installed.  
 * 
 * <code>DemonstrationRetrievalService</code> delegates the implementations 
 * of {@link macaw.businessLayer.MacawRetrievalAPI} methods to manager classes whose names are prefixed
 * with "InMemory" (eg: {@link macaw.persistenceLayer.demo.InMemoryVariableManager}).
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

public class DemonstrationRetrievalService extends DemonstrationCurationService implements MacawRetrievalAPI {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	
	// ==========================================
	// Section Construction
	// ==========================================
	public DemonstrationRetrievalService(SessionProperties sessionProperties) throws MacawException {
	}	

	public DemonstrationRetrievalService(SessionProperties sessionProperties,
										 boolean automatedTestingMode) throws MacawException {
		super(automatedTestingMode);
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
		try {
			checkValidUser(user);
			return userManager.getUserFromID(user, userID);			
		}
		catch(MacawException exception) {
			log.logException(exception);
			return null;
		}
	}
	
	public ArrayList<User> getUnverifiedUsers(User admin){
		try {
			checkValidAdministrator(admin);
			return userManager.getUnverifiedUsers(admin);			
		}
		catch(MacawException exception) {
			log.logException(exception);
			ArrayList<User> emptyUserList = new ArrayList<User>();
			return emptyUserList;
		}
	}

	public User getUserFromEmail(User user, String email) {
		try {
			checkValidUser(user);
			return userManager.getUserFromEmail(user, email);
		}
		catch(MacawException exception) {
			log.logException(exception);
			return null;
		}		
	}

	public AliasFilePath getAliasFilePath(User user, String cardNumber) {
		try {
			checkValidUser(user);
			return listChoiceManager.getAliasFilePath(user, cardNumber);			
		}
		catch(MacawException exception) {
			log.logException(exception);
			return null;
		}
	}

	public ArrayList<AliasFilePath> getAliasFilePathsMatchingName(User user, 
																  String regularExpression) {

		try {
			checkValidUser(user);
			return listChoiceManager.getAliasFilePathsMatchingName(user, regularExpression);
		}
		catch(MacawException exception) {
			log.logException(exception);
			ArrayList<AliasFilePath> emptyList = new ArrayList<AliasFilePath>();
			return emptyList;
		}

	}	

	public ArrayList<Category> getCategoriesForVariable(User user,
														String variableName) {
		
		try {
			checkValidUser(user);
			return variableManager.getCategoriesForVariable(user, variableName);
		}
		catch(MacawException exception) {
			log.logException(exception);
			ArrayList<Category> emptyList = new ArrayList<Category>();
			return emptyList;
		}
	}
	
	public ArrayList<VariableSummary> getVariableSummariesForCategory(User user, 
																	  String categoryName) {

		try {
			checkValidUser(user);
			return variableManager.getVariableSummariesForCategory(user, categoryName);
		}
		catch(MacawException exception) {
			log.logException(exception);
			ArrayList<VariableSummary> emptyList
				= new ArrayList<VariableSummary>();
			return emptyList;
		}	
	}
		
	public String[] getVariableNames(User user) {
		try {
			checkValidUser(user);
			return variableManager.getVariableNames(user);
		}
		catch(MacawException exception) {
			log.logException(exception);
			return( new String[0]);
		}
	}

	public ArrayList<ValueLabel> getValueLabels(User user, String variableName) {

		try {
			checkValidUser(user);
			//String get Variable
			Variable variable
				= variableManager.getVariable(user, variableName);
			return valueLabelManager.getValueLabels(user, variable);
		}
		catch(MacawException exception) {
			log.logException(exception);
			ArrayList<ValueLabel> emptyValueLabels = new ArrayList<ValueLabel>();
			return emptyValueLabels;
		}
	}
	
	public ArrayList<OntologyTerm> getOntologyTerms(User user, String variableName) {
		try {
			checkValidUser(user);
			//String get Variable
			Variable variable
				= variableManager.getVariable(user, variableName);
			return variableManager.getAssociatedOntologyTerms(user, variable);
		}
		catch(MacawException exception) {
			log.logException(exception);
			ArrayList<OntologyTerm> emptyOntologyTerms = new ArrayList<OntologyTerm>();
			return emptyOntologyTerms;
		}		
	}

	public ArrayList<SupportingDocument> getSupportingDocuments(User user, String variableName) {
		try {
			checkValidUser(user);
			//String get Variable
			Variable variable
				= variableManager.getVariable(user, variableName);
			return variableManager.getSupportingDocuments(user, variable);
		}
		catch(MacawException exception) {
			log.logException(exception);
			ArrayList<SupportingDocument> emptySupportingDocuments 
				= new ArrayList<SupportingDocument>();
			return emptySupportingDocuments;
		}		
	}
	
	public ArrayList<AliasFilePath> getAliasFilePaths(User user) {
		try {
			checkValidUser(user);
			return super.getAliasFilePaths(user);
		}
		catch(MacawException exception) {
			log.logException(exception);
			ArrayList<AliasFilePath> emptyList 
				= new ArrayList<AliasFilePath>();
			return emptyList;
		}
	}

	public ArrayList<Category> getCategories(User user) {
		try {
			checkValidUser(user);
			return super.getCategories(user);
		}
		catch(MacawException exception) {
			log.logException(exception);
			ArrayList<Category> emptyList 
				= new ArrayList<Category>();
			return emptyList;
		}
	}

	public ArrayList<CleaningState> getCleaningStates(User user) {
		try {
			checkValidUser(user);
			return super.getCleaningStates(user);
		}
		catch(MacawException exception) {
			log.logException(exception);
			ArrayList<CleaningState> emptyList 
				= new ArrayList<CleaningState>();
			return emptyList;
		}
	}

	public ArrayList<AvailabilityState> getAvailabilityStates(User user) {
		try {
			checkValidUser(user);
			return super.getAvailabilityStates(user);
		}
		catch(MacawException exception) {
			log.logException(exception);
			ArrayList<AvailabilityState> emptyList 
				= new ArrayList<AvailabilityState>();
			return emptyList;
		}
	}
	
	public Variable getVariable(User user, String variableName) {
		try {
			return super.getVariable(user, variableName);
		}
		catch(MacawException exception) {
			log.logException(exception);
			return null;
		}		
	}

	public ArrayList<User> getUsers(User user) {
		try {
			checkValidUser(user);
			return super.getUsers(user);
		}
		catch(MacawException exception) {
			log.logException(exception);
			ArrayList<User> emptyList 
				= new ArrayList<User>();
			return emptyList;
		}
	}	
	// ==========================================
	// Section Overload
	// ==========================================
}

