package macaw.businessLayer;

import macaw.system.*;

import java.util.ArrayList;



/**
 * Macaw is a data curation application that stores and retrieves its data via methods of this interface.  
 * MacawCurationAPI is designed to hide much of the details about data persistence from the GUI
 * code. 
 * <p>
 * Parameters for many of the API methods come from data container classes that are defined in the Macaw Data Model.  The concepts
 * such as {@link macaw.businessLayer.Variable}, {@link macaw.businessLayer.RawVariable}, {@link macaw.businessLayer.DerivedVariable}, {@link macaw.businessLayer.Category}, and {@link macaw.businessLayer.ValueLabel} represent ideas related to the domain
 * addressed by the NSHD archive.  The application packages form data into instances of these classes and submits them to an implementation
 * of MacawDatabase.  
 * <p>
 * Macaw anticipates three main implementations:
 * <ul>
 * <li>a Production SQL Database (see {@link macaw.persistenceLayer.production.ProductionCurationService} </li>
 * <li>an in-memory database ( see {@link macaw.persistenceLayer.demo.DemonstrationCurationService} </li>
 * <li>a Test SQL Database, which may be either one of the other two, but loaded with data that is intended to be used to test methods more than demonstrate features.</li>
 * </ul>
 * 
 * The {@link macaw.persistenceLayer.production.ProductionCurationService} will be the SQL database that holds the data about all NSHD variables that will
 * be advertised using SWIFT.  A Test SQL database will be created which uses the same relational schema but the records
 * it contains will be designed to provide a minimal test data set that can be used in automated testing
 * efforts.
 * 
 * <p>
 * The API has been designed to anticipate key operations that would be involved with retrieving or committing
 * changes to a relational database.  However, the abstraction is also designed to permit the development of a {@link macaw.persistenceLayer.demo.DemonstrationCurationService}
 * that is managed entirely in-memory using collections of data objects instantiated from the Application Model.
 * The 'database' is designed to hold a minimal number of records that can demonstrate to domain users the features
 * provided by Macaw.  The in-memory database is used to allow the LHA to run a demonstration version of
 * the application on a memory stick without the need for MySQL to be installed on a client machine and without relying
 * on a database filled with sensitive data about case studies.
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
 *
 * @author Kevin Garwood (kgarwood@users.sourceforge.net)
 * @version 1.0	
 */


public interface MacawCurationAPI {
	
	public void addRawVariable(User user,
							   RawVariable rawVariable) throws MacawException;
	public void deleteRawVariables(User user,
			   					   ArrayList<RawVariable> rawVariables) throws MacawException;
	public void updateRawVariable(User user,
				  				  RawVariable rawVariable) throws MacawException;
	public int getRawVariableIdentifier(User user,
										RawVariable rawVariable) throws MacawException;
	public void addDerivedVariable(User user,
							   	   DerivedVariable derivedVariable) throws MacawException;
	public void deleteDerivedVariables(User user,
								  	   ArrayList<DerivedVariable> derivedVariables) throws MacawException;
	public void updateDerivedVariable(User user,
								  	  DerivedVariable derivedVariable) throws MacawException;
	public int getDerivedVariableIdentifier(User user,
											DerivedVariable derivedVariable) throws MacawException;	
	public Variable getVariable(User user, String variableName) throws MacawException;
	public ArrayList<ValueLabel> getValueLabels(User user,
												Variable variable) throws MacawException;
	public void addValueLabels(User user,
							   Variable variable,
							   ArrayList<ValueLabel> valueLabels) throws MacawException;
	public void updateValueLabels(User user,
			   					 Variable variable,
			   					 ArrayList<ValueLabel> valueLabels) throws MacawException;
	public void deleteValueLabels(User user,
				  				  Variable variable,
				  				  ArrayList<ValueLabel> valueLabels) throws MacawException;
	public int getValueLabelIdentifier(User user,
									   Variable variable,
									   ValueLabel valueLabel) throws MacawException;	
	public ArrayList<SupportingDocument> getAllSupportingDocuments(User user) throws MacawException;
	
	//DONE
	public ArrayList<SupportingDocument> getSupportingDocuments(User user,
																Variable variable) throws MacawException;
	public void addSupportingDocument(User user,
			  						  SupportingDocument supportingDocument) throws MacawException;

	public void updateSupportingDocument(User user,
									  	 SupportingDocument supportingDocument) throws MacawException;
	
	public void deleteSupportingDocuments(User user,
				  						  ArrayList<SupportingDocument> supportingDocuments) throws MacawException;
	public int getSupportingDocumentIdentifier(User user,
									 		   SupportingDocument supportingDocument) throws MacawException;	
	//DONE
	public void associateSupportingDocumentsWithVariable(User user,
														 Variable variable,
														 ArrayList<SupportingDocument> supportingDocument) throws MacawException;
	//DONE
	public void disassociateSupportingDocumentsFromVariable(User user,
															Variable variable,
															ArrayList<SupportingDocument> supportingDocuments) throws MacawException;
	//DONE
	public ArrayList<Variable> getSourceVariables(User user,
												  DerivedVariable derivedVariable) throws MacawException;
	//DONE
	public void associateSourceVariables(User user,
										 DerivedVariable derivedVariable,
										 ArrayList<Variable> sourceVariablesToAdd) throws MacawException;
	
	//DONE
	/**
	 * removes references to either {@link macaw.businessLayer.RawVariable} or {@link macaw.businessLayer.DerivedVariable} variables that
	 * were used to create a given derivedVariable
	 * @param user
	 * @param derivedVariable contains source variables to delete
	 * @param sourceVariablesToDelete the raw or derived variables that should no longer be associated with <code>derivedVariable</code>.
	 * @throws MacawException
	 */
	public void disassociateSourceVariables(User user,
										 DerivedVariable derivedVariable,
										 ArrayList<Variable> sourceVariablesToDelete) throws MacawException;

	//DONE
	/**
	 * retrieves a collection of unique years associated with at least one variable in the data repository.
	 * @param user
	 * @throws MacawException
	 */
	public String[] getStudyYears(User user) throws MacawException;
	
	//DONE
	/**
	 * retrieves a collection of all variables managed by the system.  These variables will include 
	 * instances of {@link macaw.businessLayer.RawVariable}s and {@link macaw.businessLayer.DerivedVariable}.  Note
	 * that only summary data are included:  
	 * @param user
	 * @throws MacawException
	 */
	public ArrayList<VariableSummary> getSummaryDataForAllVariables(User user) throws MacawException;
	
	public Variable getCompleteVariableData(User user, VariableSummary variableSummary) throws MacawException;
	//DONE
	public ArrayList<VariableSummary> filterVariableSummaries(User user,
											   		  		  String searchText,
											   		  		  String year,
											   		  		  String category,
											   		  		  VariableTypeFilter variableTypeFilter) throws MacawException;
	public ArrayList<SupportingDocument> filterSupportingDocuments(User user,
																   String documentTitle,
																   String documentCode) throws MacawException;
	public ArrayList<OntologyTerm> filterOntologyTerms(User user,
													   String term,
													   String description) throws MacawException;
	
	// Create, Read, Update, Delete (C.R.U.D) features to maintain the concept of a category
	/**
	 * 
	 * @param user
	 * @return a collection of categories used to classify NSHD variables.  eg: "General Health" and
	 * "Education".
	 * @throws MacawException
	 */
	public ArrayList<Category> getCategories(User user) throws MacawException;		
	public void addCategory(User user, 
							Category category) throws MacawException;
	public void updateCategory(User user, 
							   Category category) throws MacawException;
	public void deleteCategories(User user, 
								 ArrayList<Category> categories) throws MacawException;
	public int getCategoryIdentifier(User user,
									 Variable variable,
			 						 Category category) throws MacawException;

	//DONE
	// Create, Read, Update, Delete (C.R.U.D) features to maintain the concept of a cleaning state
	public ArrayList<CleaningState> getCleaningStates(User user) throws MacawException;
	public void addCleaningState(User user, 
			 					 CleaningState cleaningState) throws MacawException;
	public void updateCleaningState(User user, 
										CleaningState cleaningState) throws MacawException;
	public void deleteCleaningStates(User user, 
				 						 ArrayList<CleaningState> cleaningStates) throws MacawException;
	public int getCleaningStateIdentifier(User user,
										  Variable variable,
			 							  CleaningState cleaningState) throws MacawException;
	
	//DONE	
	// Create, Read, Update, Delete (C.R.U.D) features to maintain the concept of an availability state	
	//DONE
	public ArrayList<AvailabilityState> getAvailabilityStates(User user) throws MacawException;
	//DONE
	public void addAvailabilityState(User user, 
									 AvailabilityState availabilityState) throws MacawException;
	public void updateAvailabilityState(User user, 
										AvailabilityState availabilityState) throws MacawException;
	public void deleteAvailabilityStates(User user, 
										 ArrayList<AvailabilityState> availabilityStates) throws MacawException;
	public int getAvailabilityStateIdentifier(User user,
											  Variable variable,
											  AvailabilityState availabilityState) throws MacawException;	

	// Create, Read, Update, Delete (C.R.U.D) features to maintain the concept of an alias file path		
	public ArrayList<AliasFilePath> getAliasFilePaths(User user) throws MacawException;
	public void addAliasFilePath(User user, 
								 AliasFilePath aliasFilePath) throws MacawException;
	public void updateAliasFilePath(User user, 
								    AliasFilePath aliasFilePath) throws MacawException;
	public void deleteAliasFilePaths(User user, 
									 ArrayList<AliasFilePath> aliasFilePaths) throws MacawException;
	public int getAliasFilePathIdentifier(User user,
										  Variable variable,
			 							  AliasFilePath aliasFilePath) throws MacawException;	
	public String getFilePathFromAlias(User user,
									   String currentAlias) throws MacawException;
	public ArrayList<OntologyTerm> getAllOntologyTerms(User user) throws MacawException;
	
	public ArrayList<OntologyTerm> getOntologyTerms(User user,
													Variable variable) throws MacawException;
	public void addOntologyTerm(User user,
								OntologyTerm ontologyTerm) throws MacawException;
	public void updateOntologyTerm(User user,
								   OntologyTerm ontologyTerm) throws MacawException;
	public void deleteOntologyTerms(User user,
			   					    ArrayList<OntologyTerm> ontologyTerms) throws MacawException;
	public int getOntologyTermIdentifier(User user,
										 OntologyTerm ontologyTerm) throws MacawException;	
	public void associateOntologyTermsWithVariable(User user,
										   		   Variable variable,
										   		   ArrayList<OntologyTerm> ontologyTerms) throws MacawException;
	public void disassociateOntologyTermsFromVariable(User user,
			   							   	  		  Variable variable,
			   							   	  		  ArrayList<OntologyTerm> ontologyTerms) throws MacawException;
	
	
	public ArrayList<MacawChangeEvent> getChangeHistoryForVariable(User user,
															  	   Variable variable) throws MacawException;
	public ArrayList<MacawChangeEvent> getChangeHistoryForSupportingDocument(User user,
		  	   																 SupportingDocument supportingDocument) throws MacawException;
	public ArrayList<MacawChangeEvent> getChangeHistoryForValueLabels(User user,
				 													  Variable variable) throws MacawException;
	public ArrayList<MacawChangeEvent> getChangeHistoryForListChoices(User user) throws MacawException;
	public ArrayList<MacawChangeEvent> getChangeHistoryByUser(User user) throws MacawException;
	
	public ArrayList<User> getUsers(User admin) throws MacawException;
	public void addUser(User admin, User user) throws MacawException;
	public void updateUser(User admin, User user) throws MacawException;
	public void deleteUsers(User admin, ArrayList<User> usersToDelete) throws MacawException;	
	public int getUserIdentifier(User admin, User user) throws MacawException;
	
	public void checkValidUser(User user) throws MacawException;
	public void checkValidAdministrator(User administrator) throws MacawException;
	public void clear(User admin) throws MacawException;
	
	public void clearAllChanges(User admin) throws MacawException;
	public ArrayList<MacawChangeEvent> getAllChanges(User admin) throws MacawException;
	public Variable getOriginalVariable(User user, Variable variable) throws MacawException;

	public Variable getAlternativeVariable(User user,
									   	   Variable targetVariable) throws MacawException;
	public void setAlternativeVariable(User user, 
									   Variable targetVariable,
									   Variable alternativeVariable) throws MacawException;									
}

