package macaw.businessLayer;

import java.util.ArrayList;


/**
 * External software clients such as the SWIFT governance tool or future information portal
 * services will use interact with the repository of Macaw variables via this interface.
 * 
 * <p>
 * For security reasons, the interface methods do not throw exceptions which may accidentally
 * reveal information about the underlying data.  Therefore, clients should ensure that when they
 * invoke this API, they anticipate returned results of null or an empty list.
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

public interface MacawRetrievalAPI {
	/**
	 * A collection of all available alias file paths.  
	 * @param user - registered user who is using the retrieval service
	 */
	public ArrayList<AliasFilePath> getAliasFilePaths(User user);
	
	/**
	 * given the name of a logical location for data, returns 
	 * an AliasFilePath object that can also describe the physical location.
	 * @param user - registered user who is using the retrieval service
	 * @param cardNumber
	 */
	public AliasFilePath getAliasFilePath(User user, String cardNumber);
	
	/**
	 * Searches for all alias file paths whose logical location names contain 
	 * a phrase.  Note that this search filter does not support regular expression symbols.
	 * @param user - registered user who is using the retrieval service
	 * @param phrase - a search phrase that represents part of the name for a logical location
	 * in an alias file path. 
	 */
	public ArrayList<AliasFilePath> getAliasFilePathsMatchingName(User user, String phrase);
	public ArrayList<Category> getCategoriesForVariable(User user, String variableName);
	public ArrayList<Category> getCategories(User user);
	
	/*
	 * Obtains the list of availability states 
	 */
	public ArrayList<AvailabilityState> getAvailabilityStates(User user);
	
	public ArrayList<CleaningState> getCleaningStates(User user);
	
	/**
	 * returns a variable record given a variable name
	 * @param user
	 * @param variableName
	 */
	public Variable getVariable(User user, String variableName);
	
	/**
	 * returns the supporting documents for a given variable
	 * @param user
	 * @param variableName
	 */
	public ArrayList<SupportingDocument> getSupportingDocuments(User user, String variableName);
	
	/**
	 * return the ontology terms for a given variable
	 * @param user
	 * @param variableName
	 */
	public ArrayList<OntologyTerm> getOntologyTerms(User user, String variableName);
	
	/**
	 * returns summary records of variables that are associated with a given category.
	 * @param user
	 * @param category
	 */
	public ArrayList<VariableSummary> getVariableSummariesForCategory(User user, String category);

	/**
	 * returns the value labels associated with a given variable
	 * @param user registered user who is using the retrieval service
	 * @param variableName the name of a variable
	 */
	public ArrayList<ValueLabel> getValueLabels(User user, String variableName);
	
	/**
	 * returns an alphabetically sorted list of all the variable names in the data repository
	 * @param user
	 */
	public String[] getVariableNames(User user);

	
	
	public User getUserFromID(User user, String userID);
	public ArrayList<User> getUnverifiedUsers(User admin);
	public ArrayList<User> getUsers(User admin);
	public User getUserFromEmail(User user, String email);	
}

