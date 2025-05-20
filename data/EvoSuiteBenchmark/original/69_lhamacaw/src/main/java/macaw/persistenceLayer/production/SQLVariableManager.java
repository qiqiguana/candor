package macaw.persistenceLayer.production;

import java.sql.*;
import java.util.ArrayList;

import macaw.businessLayer.*;
import macaw.persistenceLayer.ChangeEventGenerator;
import macaw.system.*;

/**
 * <p></p>
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
 * 'Name(...)' that is both an interface method and an accessor 
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

public class SQLVariableManager extends SQLCurationConceptManager {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private SQLListChoiceManager listChoiceManager;
	private SQLOntologyTermManager ontologyTermManager;
	private SQLSupportingDocumentsManager supportingDocumentsManager;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public SQLVariableManager(SQLChangeEventManager changeEventManager,
							  SQLListChoiceManager listChoiceManager,
							  SQLOntologyTermManager ontologyTermManager,
							  SQLSupportingDocumentsManager supportingDocumentsManager) {
		super(changeEventManager);
		this.listChoiceManager = listChoiceManager;
		this.ontologyTermManager = ontologyTermManager;
		this.supportingDocumentsManager = supportingDocumentsManager;
	}
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	public void createTables(Connection connection) throws MacawException {
		try {
			//creating tables to support all variables
			createVariablesTable(connection);
			//createRawVariablesTable(connection);
			
			//creates source variables for the benefit of managing derived variables
			createSupportingDocumentsForVariables(connection);
			createOntologyTermsForVariablesTable(connection);
			createSourceVariablesTable(connection);		
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCreateTables");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CREATE_TABLES,
									 errorMessage);
			throw macawException;
		}
	}
	
	private void createVariablesTable(Connection connection) throws  SQLException {
		StringBuilder query = new StringBuilder();
		query.append("CREATE TABLE variables (");
		query.append("identifier INT AUTO_INCREMENT NOT NULL,");
		query.append("category_id INT,");
		query.append("name VARCHAR(255),");
		query.append("year VARCHAR(255),");
		query.append("form VARCHAR(255),");
		query.append("question_number VARCHAR(255),");
		query.append("label VARCHAR(255),");
		query.append("is_coded BOOLEAN,");
		query.append("is_cleaned BOOLEAN,");
		query.append("cleaning_status_id INT,");
		query.append("cleaning_description VARCHAR(255),");
		query.append("availability_id INT,");
		query.append("code_book_number VARCHAR(255),");
		query.append("column_start VARCHAR(255),");
		query.append("column_end VARCHAR(255),");
		query.append("alias_id INT,");
		query.append("file_path VARCHAR(255),");
		query.append("notes VARCHAR(255),");
		query.append("is_derived_variable BOOLEAN,");
		query.append("alternative_variable_id INT,");
		query.append("deleted_at DATETIME,");
		query.append("PRIMARY KEY(identifier));");
		
		PreparedStatement statement = null;
		statement
			= connection.prepareStatement(query.toString());
		statement.executeUpdate();							
	}

	public void createSupportingDocumentsForVariables(Connection connection) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("CREATE TABLE supporting_documents_for_variables (");
		query.append("identifier INT AUTO_INCREMENT NOT NULL,");
		query.append("variable_id INT NOT NULL,");
		query.append("document_id INT NOT NULL,");
		query.append("PRIMARY KEY(identifier));");
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.executeUpdate();							
		}
		finally {
			if (statement != null) {
				statement.close();
			}
		}		
	}

	public void createOntologyTermsForVariablesTable(Connection connection) throws SQLException {

		StringBuilder query = new StringBuilder();
		query.append("CREATE TABLE ontology_terms_for_variables (");
		query.append("identifier INT AUTO_INCREMENT NOT NULL,");
		query.append("ontology_term_id INT NOT NULL,");
		query.append("variable_id INT NOT NULL,");
		query.append("PRIMARY KEY(identifier));");

		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.executeUpdate();							
		}
		finally {
			if (statement != null) {
				statement.close();
			}
		}		
	}
	
	/**
	 * we don't need to create a derivedVariables table because there are no 
	 * concepts beyond the ones in Variables we need to retain.  The exception is
	 * the list of source variables for each derived variable but that is handled in 
	 * a separate table.
	 */
	
	/**
	 * stores associations of source variables with derived variables.
	 * These links correspond to these methods in {@link macaw.businessLayer.Variable}:
	 * <ul>
	 * <li><code>addSourceVariable(Variable sourceVariable)</code></li>
	 * <li><code>removeSourceVariables(ArrayList<Variable> variablesToDelete);</code></li>
	 * <li>...</li>
	 * </ul>
	 */
	public void createSourceVariablesTable(Connection connection) throws  SQLException {
		StringBuilder query = new StringBuilder();
		query.append("CREATE TABLE source_variables (");
		query.append("identifier INT AUTO_INCREMENT NOT NULL,");
		query.append("derived_variable_id INT NOT NULL,");
		query.append("source_variable_id INT NOT NULL,");
		query.append("PRIMARY KEY(identifier));");
		
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.executeUpdate();							
		}
		finally {
			if (statement != null) {
				statement.close();
			}
		}		
	}

	public void addRawVariable(Connection connection,
							   User user,
							   RawVariable rawVariable) throws MacawException {
		
		//Part I: Validate parameters
		RawVariable.validateFields(rawVariable);
		checkVariableDuplicates(connection, rawVariable);
		checkListChoices(connection, rawVariable);
		//check that at least one change was made.
		addVariable(connection, user, rawVariable);
	}

	public void addDerivedVariable(Connection connection,
								   User user,
								   DerivedVariable derivedVariable) throws MacawException {

		//Part I: Validate parameters
		DerivedVariable.validateFields(derivedVariable);
		checkVariableDuplicates(connection, derivedVariable);
		checkListChoices(connection, derivedVariable);
		
		//check that at least one change was made.
		addVariable(connection, user, derivedVariable);
	}
	
	private int addVariable(Connection connection,
							User user,
							Variable variable) throws MacawException {
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO variables ");
		query.append("(category_id,");
		query.append("name,");
		query.append("year,");
		query.append("form,");
		query.append("question_number,");
		query.append("label,");
		query.append("is_coded,");
		query.append("is_cleaned,");
		query.append("cleaning_status_id,");
		query.append("cleaning_description,");
		query.append("availability_id,");
		query.append("code_book_number,");
		query.append("column_start,");
		query.append("column_end,");
		query.append("alias_id,");
		query.append("file_path,");
		query.append("notes,");
		query.append("is_derived_variable,");
		query.append("alternative_variable_id,");
		query.append("deleted_at) ");
		query.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
		
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			Category category = new Category();
			category.setName(variable.getCategory());
			int categoryID
				= listChoiceManager.getCategoryIdentifier(connection,
														  variable,
														  category);			
			statement.setInt(1, categoryID);
			statement.setString(2, variable.getName());
			statement.setString(3, variable.getYear());
			statement.setString(4, variable.getForm());
			statement.setString(5, variable.getQuestionNumber());
			
			statement.setString(6, variable.getLabel());
			
			if (variable.isCoded() == true) {
				statement.setInt(7, 1);
			}
			else {
				statement.setInt(7, 0);				
			}

			if (variable.isCleaned() == true) {
				statement.setInt(8, 1);
			}
			else {
				statement.setInt(8, 0);				
			}
			
			if (variable.isCleaned() == true) {
				CleaningState cleaningState = new CleaningState();
				cleaningState.setName(variable.getCleaningStatus());
				int cleaningStatusID
					= listChoiceManager.getCleaningStateIdentifier(connection,
																   variable,
																   cleaningState);
				statement.setInt(9, cleaningStatusID);
			}
			else {
				statement.setInt(9, 0);				
			}
			statement.setString(10, variable.getCleaningDescription());
			AvailabilityState availabilityState = new AvailabilityState();
			availabilityState.setName(variable.getAvailability());
			int availabilityID
				= listChoiceManager.getAvailabilityStateIdentifier(connection,
																   variable,
																   availabilityState);
			statement.setInt(11, availabilityID);
			
			statement.setString(12, variable.getCodeBookNumber());
			statement.setString(13, variable.getColumnStart());
			statement.setString(14, variable.getColumnEnd());
			
			AliasFilePath aliasFilePath = new AliasFilePath();
			aliasFilePath.setAlias(variable.getAlias());
			int aliasFilePathID
				= listChoiceManager.getAliasFilePathIdentifier(connection,
															   variable,
															   aliasFilePath);
			statement.setInt(15, aliasFilePathID);
			statement.setString(16, variable.getFilePath());
			statement.setString(17, variable.getNotes());
			if (variable instanceof DerivedVariable) {
				statement.setInt(18, 1);				
			}
			else {
				statement.setInt(18, 0);				
			}

			Variable alternativeVariable = variable.getAlternativeVariable();
			if (alternativeVariable == null) {
				statement.setInt(19, 0);
			}
			else {
				statement.setInt(19, alternativeVariable.getIdentifier());
			}
			
			statement.setDate(20, null);
			
			statement.executeUpdate();
			
			int primaryKey = getVariablePrimaryKey(connection, variable);
			variable.setIdentifier(primaryKey);
			
			//Part III: Record changes
			ArrayList<MacawChangeEvent> changeEvents
				= ChangeEventGenerator.addVariableChange(user, variable);

			registerChangeEvents(connection, changeEvents);
			
			//now retrieve the identifier
			/**
			ResultSet primaryKeyResult
				= statement.getGeneratedKeys();
			*/
			//TODO there HAS to be a better way to retrieve the identity of an
			//inserted record.  Does not appear to be supported with the org.gjt.mm.mysql.jdbc2 driver
			
			//primaryKeyResult.next();
			//int primaryKey = primaryKeyResult.getInt(1);
			return primaryKey;
		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);

			log.logException(exception);
			if (variable instanceof RawVariable) {
				String errorMessage
					= MacawMessages.getMessage("sql.error.unableToAddRawVariable",
												variable.getDisplayName());
				MacawException macawException 
					= new MacawException(MacawErrorType.UNABLE_TO_CREATE_RAW_VARIABLE,
										 errorMessage);
				throw macawException;
			}
			else {
				String errorMessage
					= MacawMessages.getMessage("sql.error.unableToAddDerivedVariable",
												variable.getDisplayName());
				MacawException macawException 
					= new MacawException(MacawErrorType.UNABLE_TO_CREATE_DERIVED_VARIABLE,
										 errorMessage);
				throw macawException;				
			}
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, null);
		}		
	}
	
	public void deleteRawVariables(Connection connection,
								   User user,
			   					   ArrayList<RawVariable> rawVariablesToDelete) throws MacawException {

		//Part I: Validate parameters
		for (RawVariable rawVariableToDelete : rawVariablesToDelete) {
			checkVariableExists(connection, rawVariableToDelete);
		}

		//Part II: Perform delete operation
		for (RawVariable currentRawVariable : rawVariablesToDelete) {
			deleteVariable(connection, user, currentRawVariable);
		}		
	
		//Part III: Record changes
		ArrayList<MacawChangeEvent> changeEvents 
			= new ArrayList<MacawChangeEvent>();
		for (RawVariable rawVariableToDelete : rawVariablesToDelete) {
			MacawChangeEvent changeEvent
				= ChangeEventGenerator.deleteVariableChanges(user, 
															 rawVariableToDelete);
			changeEvents.add(changeEvent);
		}		
		registerChangeEvents(connection, changeEvents);		
	}
	
	public void deleteDerivedVariables(Connection connection,
									   User user,
									   ArrayList<DerivedVariable> derivedVariables) throws MacawException {

		//Part I: Validate parameters
		for (DerivedVariable derivedVariableToDelete : derivedVariables) {
			checkVariableExists(connection, derivedVariableToDelete);
		}

		//Part II: Perform delete operation (includes recording change)
		for (DerivedVariable currentDerivedVariable : derivedVariables) {
			deleteVariable(connection, user, currentDerivedVariable);
		}		
	}

	private void deleteVariable(Connection connection,
								User user,
								Variable variable) throws MacawException {
		
		StringBuilder query = new StringBuilder();
		query.append("UPDATE variables ");
		query.append("SET deleted_at=NOW() ");
		query.append("WHERE identifier=?;");
				
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			//Part II: Perform the delete operation
			Time deletionDate = new Time(System.currentTimeMillis());
			statement
				= connection.prepareStatement(query.toString());
			//statement.setTime(1, deletionDate);
			statement.setInt(1, variable.getIdentifier());
			statement.executeUpdate();			
		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);
			log.logException(exception);
			if (variable instanceof RawVariable) {
				String errorMessage
					= MacawMessages.getMessage("sql.error.unableToDeleteRawVariable",
												variable.getDisplayName());
				MacawException macawException 
					= new MacawException(MacawErrorType.UNABLE_TO_DELETE_RAW_VARIABLE,
										 errorMessage);
				throw macawException;				
			}
			else {
				String errorMessage
					= MacawMessages.getMessage("sql.error.unableToDeleteDerivedVariable");
				MacawException macawException 
					= new MacawException(MacawErrorType.UNABLE_TO_DELETE_DERIVED_VARIABLE,
										 errorMessage);
				throw macawException;								
			}
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}			
	}
		
	public void updateRawVariable(Connection connection,
								  User user,
								  RawVariable revisedRawVariable) throws MacawException {

		//Part I: Validate parameters
		RawVariable.validateFields(revisedRawVariable);
		checkVariableExists(connection, revisedRawVariable);
		checkVariableDuplicates(connection, revisedRawVariable);
		checkListChoices(connection, revisedRawVariable);

		//check that at least one change was made.
		RawVariable originalRawVariable
			= (RawVariable) getOriginalVariable(connection,
												revisedRawVariable);
		ArrayList<MacawChangeEvent> changeEvents
			= RawVariable.detectFieldChanges(user,
											 originalRawVariable, 
											 revisedRawVariable);	
		
		if (changeEvents.size() == 0) {
			return;
		}

		updateVariable(connection, user, revisedRawVariable);
		registerChangeEvents(connection, changeEvents);		
	}
	
	public void updateDerivedVariable(Connection connection,
									  User user,
									  DerivedVariable revisedDerivedVariable) throws MacawException {
		
		//check basic field errors
		DerivedVariable.validateFields(revisedDerivedVariable);
		//check that variable exists
		checkVariableExists(connection, revisedDerivedVariable);
		checkListChoices(connection, revisedDerivedVariable);
		
		//check that at least one change was made.
		DerivedVariable originalDerivedVariable
			= (DerivedVariable) getOriginalVariable(connection,
													revisedDerivedVariable);
		ArrayList<MacawChangeEvent> changeEvents
			= DerivedVariable.detectFieldChanges(user,
												 originalDerivedVariable, 
											 	 revisedDerivedVariable);	
		
		if (changeEvents.size() == 0) {
			return;
		}
		
		updateVariable(connection, user, revisedDerivedVariable);
		registerChangeEvents(connection, changeEvents);
	}

	public void updateVariable(Connection connection,
							   User user,
							   Variable revisedVariable) throws MacawException {

		//check for duplicates
		checkVariableDuplicates(connection, revisedVariable);
		
		StringBuilder query = new StringBuilder();
		query.append("UPDATE variables ");
		query.append("SET category_id=?,");
		query.append("name=?,");
		query.append("year=?,");
		query.append("form=?,");
		query.append("question_number=?,");
		query.append("label=?,");
		query.append("is_coded=?,");
		query.append("is_cleaned=?,");
		query.append("cleaning_status_id=?,");
		query.append("cleaning_description=?,");
		query.append("availability_id=?,");
		query.append("code_book_number=?,");
		query.append("column_start=?,");
		query.append("column_end=?,");
		query.append("alias_id=?,");
		query.append("file_path=?,");
		query.append("notes=?,");
		query.append("alternative_variable_id=? ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");

		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			Category category = new Category();
			category.setName(revisedVariable.getCategory());
			int categoryID
				= listChoiceManager.getCategoryIdentifier(connection,
														  revisedVariable,
														  category);			
			statement.setInt(1, categoryID);
			statement.setString(2, revisedVariable.getName());
			statement.setString(3, revisedVariable.getYear());
			statement.setString(4, revisedVariable.getForm());
			statement.setString(5, revisedVariable.getQuestionNumber());
			statement.setString(6, revisedVariable.getLabel());

			if (revisedVariable.isCoded() == true) {
				statement.setInt(7, 1);				
			}
			else {
				statement.setInt(7, 0);
			}

			if (revisedVariable.isCleaned() == true) {
				statement.setInt(8, 1);				
			}
			else {
				statement.setInt(8, 0);
			}

			CleaningState cleaningState = new CleaningState();
			cleaningState.setName(revisedVariable.getCleaningStatus());
			if (revisedVariable.isCleaned() == true) {
				int cleaningStatusID
					= listChoiceManager.getCleaningStateIdentifier(connection,
																   revisedVariable,
																   cleaningState);
				statement.setInt(9, cleaningStatusID);
			}
			else {
				statement.setInt(9, 0);				
			}
			
			statement.setString(10, revisedVariable.getCleaningDescription());
			AvailabilityState availabilityState = new AvailabilityState();
			availabilityState.setName(revisedVariable.getAvailability());
			int availabilityStateID
				= listChoiceManager.getAvailabilityStateIdentifier(connection,
																   revisedVariable,
																   availabilityState);			
			statement.setInt(11, availabilityStateID);
			AliasFilePath aliasFilePath = new AliasFilePath();
			aliasFilePath.setAlias(revisedVariable.getAlias());
			int aliasFilePathID
				= listChoiceManager.getAliasFilePathIdentifier(connection,
															   revisedVariable,
															   aliasFilePath);			
			
			statement.setString(12, revisedVariable.getCodeBookNumber());
			statement.setString(13, revisedVariable.getColumnStart());
			statement.setString(14, revisedVariable.getColumnEnd());
			
			statement.setInt(15, aliasFilePathID);
			statement.setString(16, revisedVariable.getFilePath());
			statement.setString(17, revisedVariable.getNotes());
			
			Variable alternativeVariable
				= revisedVariable.getAlternativeVariable();
			if (alternativeVariable == null) {
				statement.setInt(18, 0);				
			}
			else {
				statement.setInt(18, alternativeVariable.getIdentifier());								
			}
			
			statement.setInt(19, revisedVariable.getIdentifier());
			statement.executeUpdate();			
		}
		catch(SQLException exception) {
			log.logException(exception);
			if (revisedVariable instanceof RawVariable) {				
				String errorMessage
					= MacawMessages.getMessage("sql.error.unableToUpdateRawVariable");
				MacawException macawException 
					= new MacawException(MacawErrorType.UNABLE_TO_UPDATE_RAW_VARIABLE,
										 errorMessage);
				throw macawException;
			}
			else {
				String errorMessage
					= MacawMessages.getMessage("sql.error.unableToUpdateRawVariable");
				MacawException macawException 
					= new MacawException(MacawErrorType.UNABLE_TO_UPDATE_RAW_VARIABLE,
										 errorMessage);
				throw macawException;				
			}
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, null);
		}	
	}

	public int getDerivedVariableIdentifier(Connection connection,
											User user,
											DerivedVariable derivedVariable) throws MacawException {

		return getVariableIdentifier(connection,
									 user,
									 derivedVariable);
	}

	public int getRawVariableIdentifier(Connection connection,
										User user,
										RawVariable rawVariable) throws MacawException {

		return getVariableIdentifier(connection,
									 user,
									 rawVariable);
	}
	
	public int getVariableIdentifier(Connection connection,
									 User user,
									 Variable variable) throws MacawException {
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT variables.identifier ");
		query.append("FROM variables ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("variables.name=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setString(1, variable.getName());
			resultSet = statement.executeQuery();
			
			resultSet.last();
			resultSet.beforeFirst();
			if (resultSet.next() == false) {
				//does not exist
				return -1;
			}
			else {
				return resultSet.getInt(1);
			}
		}
		catch(SQLException exception) {
			log.logException(exception);
			if (variable instanceof RawVariable) {				
				String errorMessage
					= MacawMessages.getMessage("sql.error.unableToGetVariableIdentifier",
												variable.getDisplayName());
				MacawException macawException 
					= new MacawException(MacawErrorType.UNABLE_TO_GET_VARIABLE_IDENTIFIER,
										 errorMessage);
				throw macawException;
			}
			else {
				String errorMessage
					= MacawMessages.getMessage("sql.error.unableToGetVariableIdentifier",
												variable.getDisplayName());
				MacawException macawException 
					= new MacawException(MacawErrorType.UNABLE_TO_GET_VARIABLE_IDENTIFIER,
										 errorMessage);
				throw macawException;				
			}
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}			
	}
	
	
	public ArrayList<Category> getCategoriesForVariable(Connection connection,
														String variableName) throws MacawException {
		
		//check that variable exists
		checkVariableExists(connection, variableName);
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT categories.identifier, ");
		query.append("categories.name ");
		query.append("FROM categories, variables ");
		query.append("WHERE categories.deleted_at IS NULL AND ");
		query.append("variables.deleted_at IS NULL AND ");
		query.append("categories.identifier=variables.category_id ");
		query.append("AND variables.name=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		
		ArrayList<Category> categories = new ArrayList<Category>();
		
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setString(1, variableName);
			
			resultSet = statement.executeQuery();
			
			while (resultSet.next() == true) {
				Category category = new Category();
				category.setIdentifier(resultSet.getInt(1));
				category.setName(resultSet.getString(2));
				categories.add(category);
			}
			
			return categories;
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetCategoriesForVariable",
											variableName);
			MacawException macawException 
					= new MacawException(MacawErrorType.UNABLE_TO_GET_CATEGORIES_FOR_VARIABLE,
										 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}		
	}

	public ArrayList<VariableSummary> getVariableSummariesForCategory(Connection connection,
			  												   		  String categoryName) throws MacawException {


		StringBuilder query = new StringBuilder();
		query.append("SELECT variables.identifier,");
		query.append("variables.name,");
		query.append("variables.year,");
		query.append("variables.label,");
		query.append("variables.is_derived_variable,");
		query.append("variables.category_id ");
		query.append("FROM variables, categories ");
		query.append("WHERE variables.deleted_at IS NULL AND ");
		query.append("categories.deleted_at IS NULL AND ");
		query.append("variables.category_id=categories.identifier ");
		query.append("AND categories.name=? ");
		query.append("ORDER BY year ASC;");

		ArrayList<VariableSummary> variableSummaries 
			= new ArrayList<VariableSummary>();

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setString(1, categoryName);
			resultSet = statement.executeQuery();
				
			while (resultSet.next() == true) {
				
				VariableSummary variableSummary = new VariableSummary();
				variableSummary.setIdentifier(resultSet.getInt(1));
				variableSummary.setName(resultSet.getString(2));

				String year = resultSet.getString(3);
				variableSummary.setYear( year);
				variableSummary.setLabel(resultSet.getString(4));
				
				if (resultSet.getInt(5) == 1) {
					variableSummary.setDerived(true);
				}
				else {
					variableSummary.setDerived(false);
				}
				
				variableSummaries.add(variableSummary);
			}
			
			return variableSummaries;
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetSummaryVariableData");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ALL_SUMMARY_VARIABLES,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}		
	}

	
	public Variable getVariable(Connection connection,
								String variableName) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT variables.identifier ");
		query.append("FROM variables ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("name=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setString(1, variableName);
			resultSet = statement.executeQuery();
			
			resultSet.beforeFirst();
			if (resultSet.next() == false) {
				//does not exist
				return null;
			}
			else {
				int identifier = resultSet.getInt(1);
				if (isDerivedVariable(connection, identifier) == true) {
					return getDerivedVariable(connection, identifier);
				}	
				else {
					return getRawVariable(connection, identifier);
				}
			}
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetVariableIdentifier",
											variableName);
			MacawException macawException 
					= new MacawException(MacawErrorType.UNABLE_TO_GET_VARIABLE_IDENTIFIER,
										 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}		
	}
	
	public ArrayList<Variable> getSourceVariables(Connection connection,
												  User user,
			  									  DerivedVariable derivedVariable) throws MacawException {

		
		//Part I: Validate parameters
		checkVariableExists(connection, derivedVariable);
		
		//Part II: perform the get source variables operation
		StringBuilder query = new StringBuilder();
		query.append("SELECT variables.identifier, variables.is_derived_variable ");
		query.append("FROM source_variables, variables ");
		query.append("WHERE variables.deleted_at IS NULL AND ");
		query.append("source_variables.source_variable_id=variables.identifier AND ");
		query.append("source_variables.derived_variable_id = ?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setInt(1, derivedVariable.getIdentifier());
			resultSet = statement.executeQuery();

			ArrayList<Variable> sourceVariables = new ArrayList<Variable>();
			while (resultSet.next() == true) {
				int variableID = resultSet.getInt(1);
				boolean isDerivedVariable = resultSet.getBoolean(2);
				if (isDerivedVariable == true) {
					DerivedVariable derivedSourceVariable
						= getDerivedVariable(connection, variableID);
					sourceVariables.add(derivedSourceVariable);
				}
				else {
					RawVariable sourceRawVariable
						= getRawVariable(connection, variableID);
					sourceVariables.add(sourceRawVariable);					
				}
			}
			return sourceVariables;
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetSourceVariables",
											derivedVariable.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_SOURCE_VARIABLES,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}	
	}

	public void associateSourceVariables(Connection connection,
										 User user,
										 DerivedVariable derivedVariable,
										 ArrayList<Variable> sourceVariablesToAdd) throws MacawException {

		//Part I: Validate parameters
		checkVariableExists(connection, derivedVariable);		
		for (Variable currentVariable : sourceVariablesToAdd) {
			checkVariableExists(connection, currentVariable);
			checkDuplicateVariableAssociation(connection, 
											  derivedVariable, 
											  currentVariable);
		}
		
		//Part II: Perform association operation
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO source_variables ");
		query.append("(derived_variable_id,");
		query.append("source_variable_id) ");
		query.append("VALUES (?, ?);");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			int derivedVariableID = derivedVariable.getIdentifier();
			for (Variable sourceVariableToAdd : sourceVariablesToAdd) {
				statement.setInt(1, derivedVariableID);
				statement.setInt(2, sourceVariableToAdd.getIdentifier());
				statement.executeUpdate();
			}
			
			//Part III: Associate source variables
			//Part III: Record changes
			ArrayList<MacawChangeEvent> changeEvents
				= ChangeEventGenerator.associateSourceVariablesChanges(user,
																	   derivedVariable,
																	   sourceVariablesToAdd);
			registerChangeEvents(connection, changeEvents);				
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToAddSourceVariables",
											derivedVariable.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_ASSOCIATE_SOURCE_VARIABLE,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}	
	}
	
	public void disassociateSourceVariables(Connection connection,
											User user,
											DerivedVariable derivedVariable,
											ArrayList<Variable> sourceVariablesToDelete) throws MacawException {

		//Part I: Validate parameters
		checkVariableExists(connection, derivedVariable);		
		for (Variable currentVariable : sourceVariablesToDelete) {
			checkVariableExists(connection, currentVariable);
			checkVariableAssociationExists(connection, 
					  					   derivedVariable, 
					  					   currentVariable);
		}
				
		//Part II: Perform disassociation operation
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM source_variables ");
		query.append("WHERE derived_variable_id = ? ");
		query.append("AND source_variable_id = ?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			int derivedVariableID = derivedVariable.getIdentifier();
			for (Variable sourceVariableToDelete : sourceVariablesToDelete) {
				statement.setInt(1, derivedVariableID);
				statement.setInt(2, sourceVariableToDelete.getIdentifier());
				statement.executeUpdate();
			}
			
			//Part III: Record changes
			ArrayList<MacawChangeEvent> changeEvents
				= ChangeEventGenerator.disassociateSourceVariablesChanges(user,
				    													  derivedVariable,
				    													  sourceVariablesToDelete);		
			registerChangeEvents(connection,
								 changeEvents);	
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToDeleteSourceVariables",
											derivedVariable.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_DISASSOCIATE_SOURCE_VARIABLE,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}	
	}

	public String[] getStudyYears(Connection connection) throws MacawException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT DISTINCT year ");
		query.append("FROM variables ");
		query.append("WHERE deleted_at IS NULL ");
		query.append("ORDER BY year DESC;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			resultSet = statement.executeQuery();
			ArrayList<String> years = new ArrayList<String>();
			while (resultSet.next() == true) {
				years.add(resultSet.getString(1));				
			}

			String[] results
				= years.toArray(new String[0]);
			return results;
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetStudyYears");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_STUDY_YEARS,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}	
	}
	
	
	public ArrayList<SupportingDocument> getAssociatedSupportingDocuments(Connection connection,
																		  User user,
																		  String variableName) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT supporting_documents.identifier,");
		query.append("supporting_documents.title,");
		query.append("supporting_documents.document_code,");		
		query.append("supporting_documents.description,");
		query.append("supporting_documents.file_path,");
		query.append("supporting_documents.file_path ");
		query.append("FROM supporting_documents, supporting_documents_for_variables, variables ");
		query.append("WHERE supporting_documents.deleted_at IS NULL AND ");
		query.append("supporting_documents.identifier=");
		query.append("supporting_documents_for_variables.document_id AND ");
		query.append("supporting_documents_for_variables.variable_id=variables.identifier ");
		query.append("AND variables.name=? ");
		query.append("ORDER BY title ASC;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			ArrayList<SupportingDocument> results 
				= new ArrayList<SupportingDocument>();
			statement
				= connection.prepareStatement(query.toString());
			statement.setString(1, variableName);
			resultSet = statement.executeQuery();						

			while (resultSet.next() == true) {
				SupportingDocument supportingDocument = new SupportingDocument();
				supportingDocument.setIdentifier(resultSet.getInt(1));
				supportingDocument.setTitle(resultSet.getString(2));
				supportingDocument.setDocumentCode(resultSet.getString(3));
				supportingDocument.setDescription(resultSet.getString(4));
				supportingDocument.setFileName(resultSet.getString(5));
				supportingDocument.setFilePath(resultSet.getString(6));
				results.add(supportingDocument);
			}
			
			return results;
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetSupportingDocuments",
											variableName);
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_SUPPORTING_DOCUMENTS,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}

	public ArrayList<SupportingDocument> getAssociatedSupportingDocuments(Connection connection,
																		  User user,
																		  Variable variable) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT supporting_documents.identifier,");
		query.append("supporting_documents.title,");
		query.append("supporting_documents.document_code,");		
		query.append("supporting_documents.description,");
		query.append("supporting_documents.file_path,");
		query.append("supporting_documents.file_path ");
		query.append("FROM supporting_documents, supporting_documents_for_variables ");
		query.append("WHERE supporting_documents.deleted_at IS NULL AND ");
		query.append("supporting_documents.identifier=");
		query.append("supporting_documents_for_variables.document_id AND ");
		query.append("supporting_documents_for_variables.variable_id=? ");
		query.append("ORDER BY title ASC;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			ArrayList<SupportingDocument> results 
				= new ArrayList<SupportingDocument>();
			statement
				= connection.prepareStatement(query.toString());
			statement.setInt(1, variable.getIdentifier());
			resultSet = statement.executeQuery();						

			while (resultSet.next() == true) {
				SupportingDocument supportingDocument = new SupportingDocument();
				supportingDocument.setIdentifier(resultSet.getInt(1));
				supportingDocument.setTitle(resultSet.getString(2));
				supportingDocument.setDocumentCode(resultSet.getString(3));
				supportingDocument.setDescription(resultSet.getString(4));
				supportingDocument.setFileName(resultSet.getString(5));
				supportingDocument.setFilePath(resultSet.getString(6));
				results.add(supportingDocument);
			}

			return results;
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetSupportingDocuments",
						variable.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_SUPPORTING_DOCUMENTS,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}

	public void associateSupportingDocuments(Connection connection,
											 User user,
											 Variable variable,
											 ArrayList<SupportingDocument> supportingDocumentsToAdd) throws MacawException {

		checkVariableExists(connection,
							variable);

		if (supportingDocumentsToAdd.size() == 0) {
			return;
		}
		
		Variable originalVariable = getOriginalVariable(connection, variable);		
		for (SupportingDocument supportingDocument : supportingDocumentsToAdd) {
			supportingDocumentsManager.checkSupportingDocumentExists(connection,
																	 supportingDocument);
			checkDuplicateDocumentAssociation(connection, 
											  originalVariable, 
											  supportingDocument);
		}
	
		//Part II: Perform the associate supporting document operation
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO supporting_documents_for_variables ");
		query.append("(variable_id, document_id) ");
		query.append("VALUES (?,?)");
		
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			int variableIdentifier = variable.getIdentifier();
			for (SupportingDocument supportingDocument : supportingDocumentsToAdd) {
				statement.setInt(1, variableIdentifier);
				statement.setInt(2, supportingDocument.getIdentifier());
				statement.executeUpdate();
			}
			
			//Part III: Record changes
			ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
			for (SupportingDocument supportingDocument : supportingDocumentsToAdd) {
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

			registerChangeEvents(connection, changeEvents);
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToAddDocumentsToVariable",
											variable.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_ASSOCIATE_DOCUMENT,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, null);
		}	
	}

	public void disassociateSupportingDocuments(Connection connection,
												User user,
			 									Variable variable,
			 									ArrayList<SupportingDocument> supportingDocumentsToDelete) throws MacawException {


		checkVariableExists(connection, variable);

		if (supportingDocumentsToDelete.size() == 0) {
			return;
		}

		Variable originalVariable = getOriginalVariable(connection, variable);		
		for (SupportingDocument supportingDocument : supportingDocumentsToDelete) {
			supportingDocumentsManager.checkSupportingDocumentExists(connection,
																	 supportingDocument);
			checkDocumentAssociationExists(connection, 
								  		   originalVariable, 
								  		   supportingDocument);
		}

		//Part II: Perform the associate supporting document operation
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM supporting_documents_for_variables ");
		query.append("WHERE variable_id=? AND document_id=?;");

		PreparedStatement statement = null;
		try {
			int variableIdentifier = variable.getIdentifier();
			for (SupportingDocument supportingDocument : supportingDocumentsToDelete) {
				statement
					= connection.prepareStatement(query.toString());
				statement.setInt(1, variableIdentifier);
				statement.setInt(2, supportingDocument.getIdentifier());
				statement.executeUpdate();
			}

			//Part III: Record Changes	
			ArrayList<MacawChangeEvent> changeEvents
				= ChangeEventGenerator.disassociateSupportingDocumentsChanges(user,
						  													  variable,
						  													  supportingDocumentsToDelete);
			registerChangeEvents(connection, changeEvents);	
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToDeleteDocumentsFromVariable",
											variable.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_DISASSOCIATE_DOCUMENT,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, null);
		}	
	}

	public ArrayList<OntologyTerm> getAssociatedOntologyTerms(Connection connection,
															  User user,
															  String variableName) throws MacawException {
		
		checkVariableExists(connection, variableName);
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT DISTINCT ontology_terms.identifier,");
		query.append("ontology_terms.term,");
		query.append("ontology_terms.ontology_name,");
		query.append("ontology_terms.description,");
		query.append("ontology_terms.name_space ");
		query.append("FROM ontology_terms, ontology_terms_for_variables, variables ");
		query.append("WHERE ontology_terms.deleted_at IS NULL AND ");
		query.append("variables.deleted_at IS NULL AND ");
		query.append("ontology_terms.identifier=");
		query.append("ontology_terms_for_variables.ontology_term_id ");
		query.append("AND variables.identifier=ontology_terms_for_variables.variable_id ");
		query.append("AND variables.name=?;");
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());
			statement.setString(1, variableName);
			resultSet = statement.executeQuery();
			ArrayList<OntologyTerm> results
				= new ArrayList<OntologyTerm>();
			while (resultSet.next() == true) {
				OntologyTerm ontologyTerm = new OntologyTerm();
				ontologyTerm.setIdentifier(resultSet.getInt(1));
				ontologyTerm.setTerm(resultSet.getString(2));								
				ontologyTerm.setOntologyName(resultSet.getString(3));
				ontologyTerm.setDescription(resultSet.getString(4));
				ontologyTerm.setNameSpace(resultSet.getString(5));
				results.add(ontologyTerm);	
			}
			return results;
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetOntologyTerms",
											variableName);
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ONTOLOGY_TERMS,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}

	public ArrayList<OntologyTerm> getAssociatedOntologyTerms(Connection connection,
															  User user,
															  Variable variable) throws MacawException {

		
		checkVariableExists(connection, variable);

		StringBuilder query = new StringBuilder();
		query.append("SELECT DISTINCT ontology_terms.identifier,");
		query.append("ontology_terms.term,");
		query.append("ontology_terms.ontology_name,");
		query.append("ontology_terms.description,");
		query.append("ontology_terms.name_space ");
		query.append("FROM ontology_terms, ontology_terms_for_variables, ");
		query.append("variables ");
		query.append("WHERE ontology_terms.deleted_at IS NULL AND ");
		query.append("ontology_terms.identifier=");
		query.append("ontology_terms_for_variables.ontology_term_id ");
		query.append("AND ontology_terms_for_variables.variable_id=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());
			statement.setInt(1, variable.getIdentifier());
			resultSet = statement.executeQuery();
			ArrayList<OntologyTerm> results
				= new ArrayList<OntologyTerm>();
			while (resultSet.next() == true) {
				OntologyTerm ontologyTerm = new OntologyTerm();
				ontologyTerm.setIdentifier(resultSet.getInt(1));
				ontologyTerm.setTerm(resultSet.getString(2));

				ontologyTerm.setOntologyName(resultSet.getString(3));
				ontologyTerm.setDescription(resultSet.getString(4));
				ontologyTerm.setNameSpace(resultSet.getString(5));
				results.add(ontologyTerm);	
			}
			return results;
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetOntologyTerms",
											variable.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ONTOLOGY_TERMS,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}
	
	public void associateOntologyTerms(Connection connection,
			   						   User user,
			   						   Variable variable,
			   						   ArrayList<OntologyTerm> ontologyTermsToAssociate) throws MacawException {

		
		//Part I: Validate parameters
		checkVariableExists(connection, variable);	
		for (OntologyTerm ontologyTermToAssociate : ontologyTermsToAssociate) {
			ontologyTermManager.checkOntologyTermExists(connection, ontologyTermToAssociate);
			checkDuplicateTermAssociation(connection, variable, ontologyTermToAssociate);			
		}
		
		int variableID = variable.getIdentifier();

		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO ontology_terms_for_variables ");
		query.append("(ontology_term_id, variable_id) ");
		query.append("VALUES (?,?);");
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());			
			for (OntologyTerm currentOntologyTerm : ontologyTermsToAssociate) {
				statement.setInt(1, currentOntologyTerm.getIdentifier());
				statement.setInt(2, variableID);
				statement.executeUpdate();
			}

			//Part III: Record changes
			//
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToAddOntologyTermsToVariable",
											variable.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_ASSOCIATE_ONTOLOGY_TERM,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}

	public void disassociateOntologyTerms(Connection connection,
										  User user,
										  Variable variable,
										  ArrayList<OntologyTerm> ontologyTerms) throws MacawException {

		checkVariableExists(connection, variable);	
		for (OntologyTerm currentOntologyTerm : ontologyTerms) {
			ontologyTermManager.checkOntologyTermExists(connection, currentOntologyTerm);
			checkTermAssociationExists(connection, variable, currentOntologyTerm);
		}
		
		int variableID = variable.getIdentifier();

		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ontology_terms_for_variables ");
		query.append("WHERE variable_id=? AND ontology_term_id=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());			
			for (OntologyTerm currentOntologyTerm : ontologyTerms) {
				statement.setInt(1, variableID);
				statement.setInt(2, currentOntologyTerm.getIdentifier());
				statement.executeUpdate();
			}			

			//Part III: Record changes
			//
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToDeleteOntologyTermsFromVariable",
											variable.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_DISASSOCIATE_ONTOLOGY_TERM,
								 	errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}
	
	public Variable getCompleteVariableData(Connection connection,
											User user, 
											VariableSummary variableSummary) throws MacawException {
		
		
		int identifier = variableSummary.getIdentifier();
		if (variableSummary.isDerived() == true) {
			DerivedVariable derivedVariable
				= getDerivedVariable(connection, identifier);
			return derivedVariable;
		}
		else {
			RawVariable rawVariable
				= getRawVariable(connection, identifier);
			return rawVariable;
		}
	}

	//assumes that variable exists
	public RawVariable getRawVariable(Connection connection,
									  int variableID) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT ");
		query.append("category_id,");
		query.append("name,");
		query.append("year,");
		query.append("form,");
		query.append("question_number,");
		query.append("label,");
		query.append("is_coded,");
		query.append("is_cleaned,");
		query.append("cleaning_status_id,");
		query.append("cleaning_description,");
		query.append("availability_id,");
		query.append("code_book_number,");
		query.append("column_start,");
		query.append("column_end,");
		query.append("alias_id,");
		query.append("notes,");
		query.append("alternative_variable_id ");
		query.append("FROM variables ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;		
		try {
			statement = connection.prepareStatement(query.toString());
			statement.setInt(1, variableID);
			resultSet = statement.executeQuery();
			//there should be exactly one result
			resultSet.next();

			RawVariable rawVariable = new RawVariable();
			rawVariable.setIdentifier(variableID);

			int categoryID = resultSet.getInt(1);

			rawVariable.setName(resultSet.getString(2));
			rawVariable.setYear(resultSet.getString(3));
			rawVariable.setForm(resultSet.getString(4));
			rawVariable.setQuestionNumber(resultSet.getString(5));
			
			rawVariable.setLabel(resultSet.getString(6));
			rawVariable.setCoded(resultSet.getBoolean(7));
			rawVariable.setCleaned(resultSet.getBoolean(8));
			rawVariable.setCleaningDescription(resultSet.getString(10));

			int availabilityID = resultSet.getInt(11);

			rawVariable.setCodeBookNumber(resultSet.getString(12));
			rawVariable.setColumnStart(resultSet.getString(13));
			rawVariable.setColumnEnd(resultSet.getString(14));
			
			int aliasID = resultSet.getInt(15);			

			String categoryName 
				= listChoiceManager.getCategoryName(connection, 
													rawVariable,
													categoryID);
			rawVariable.setCategory(categoryName);
			if (rawVariable.isCleaned() == true) {
				int cleaningStatusID = resultSet.getInt(9);
				String cleaningStatus 
					= listChoiceManager.getCleaningStateName(connection, 
															 rawVariable,
															 cleaningStatusID);
				rawVariable.setCleaningStatus(cleaningStatus);				
			}
			else {
				rawVariable.setCleaningStatus(MacawMessages.getMessage("general.fieldValue.unknown"));
			}

			String availability 
				= listChoiceManager.getAvailabilityStateName(connection, 
															 rawVariable,
															 availabilityID);
			rawVariable.setAvailability(availability);

			rawVariable.setNotes(resultSet.getString(16));
			int alternativeVariableID = resultSet.getInt(17);			
			if (alternativeVariableID != 0) {
				if (isDerivedVariable(connection, alternativeVariableID) == true) {
					DerivedVariable alternativeVariable
						= this.getDerivedVariable(connection, alternativeVariableID);
					rawVariable.setAlternativeVariable(alternativeVariable);					
				}
				else {
					RawVariable alternativeVariable
						= getRawVariable(connection, alternativeVariableID);
					rawVariable.setAlternativeVariable(alternativeVariable);
				}
			}
			
			getAliasInformation(connection,
		 						aliasID,
		 						rawVariable);

			return rawVariable;
		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetRawVariable");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_GET_RAW_VARIABLE,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}	
	}

	//assumes that variable exists
	public DerivedVariable getDerivedVariable(Connection connection,
 	   	  									  int variableID) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT ");
		query.append("category_id,");
		query.append("name,");
		query.append("year,");
		query.append("form,");
		query.append("question_number,");
		query.append("label,");
		query.append("is_coded,");
		query.append("is_cleaned,");
		query.append("cleaning_status_id,");
		query.append("cleaning_description,");
		query.append("availability_id,");
		query.append("code_book_number,");
		query.append("column_start,");
		query.append("column_end,");
		query.append("alias_id,");
		query.append("notes,");
		query.append("alternative_variable_id ");
		query.append("FROM variables ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("variables.identifier=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;		
		try {
			statement = connection.prepareStatement(query.toString());
			statement.setInt(1, variableID);
			resultSet = statement.executeQuery();
			//there should be exactly one result
			resultSet.next();

			DerivedVariable derivedVariable = new DerivedVariable();
			derivedVariable.setIdentifier(variableID);

			int categoryID = resultSet.getInt(1);

			derivedVariable.setName(resultSet.getString(2));

			derivedVariable.setYear(resultSet.getString(3));
			derivedVariable.setForm(resultSet.getString(4));
			derivedVariable.setQuestionNumber(resultSet.getString(5));
			
			derivedVariable.setLabel(resultSet.getString(6));
			derivedVariable.setCoded(resultSet.getBoolean(7));
			derivedVariable.setCleaned(resultSet.getBoolean(8));

			derivedVariable.setCleaningDescription(resultSet.getString(10));

			int availabilityID = resultSet.getInt(11);

			derivedVariable.setCodeBookNumber(resultSet.getString(12));
			derivedVariable.setColumnStart(resultSet.getString(13));
			derivedVariable.setColumnEnd(resultSet.getString(14));

			int aliasID = resultSet.getInt(15);			
			derivedVariable.setAlias(resultSet.getString(16));

			//get list choices
			String categoryName 
				= listChoiceManager.getCategoryName(connection, 
													derivedVariable,
													categoryID);
			derivedVariable.setCategory(categoryName);
			String availability 
				= listChoiceManager.getAvailabilityStateName(connection, 
															 derivedVariable,
															 availabilityID);
			derivedVariable.setAvailability(availability);
			if (derivedVariable.isCleaned() == true) {
				int cleaningStatusID = resultSet.getInt(9);
				String cleaningStatus 
					= listChoiceManager.getCleaningStateName(connection, 
															 derivedVariable,
															 cleaningStatusID);
				derivedVariable.setCleaningStatus(cleaningStatus);				
			}
			else {
				derivedVariable.setCleaningStatus(MacawMessages.getMessage("general.fieldValue.unknown"));
			}

			derivedVariable.setNotes(resultSet.getString(16));
			int alternativeVariableID = resultSet.getInt(17);			
			if (alternativeVariableID != 0) {
				if (isDerivedVariable(connection, alternativeVariableID) == true) {
					DerivedVariable alternativeVariable
						= this.getDerivedVariable(connection, alternativeVariableID);
					derivedVariable.setAlternativeVariable(derivedVariable);					
				}
				else {
					RawVariable alternativeVariable
						= getRawVariable(connection, alternativeVariableID);
					derivedVariable.setAlternativeVariable(alternativeVariable);
				}
			}
			
			getAliasInformation(connection,
					 			aliasID,
					 			derivedVariable);

			return derivedVariable;
		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetDerivedVariable",
											String.valueOf(variableID));
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_GET_DERIVED_VARIABLE,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}			
	}
	
	private void getAliasInformation(Connection connection,
									 int aliasID,
									 Variable variable) throws SQLException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT alias,");
		query.append("file_path ");
		query.append("FROM alias_file_paths ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		statement
			= connection.prepareStatement(query.toString());
		statement.setInt(1, aliasID);
		resultSet = statement.executeQuery();
		if (resultSet.next() == true) {
			String alias = resultSet.getString(1);
			String filePath = resultSet.getString(2);
			variable.setAlias(alias);
			variable.setFilePath(filePath);
		}
	}
	
	public ArrayList<VariableSummary> getSummaryDataForAllVariables(Connection connection) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier,");
		query.append("name,");
		query.append("year,");
		query.append("label,");
		query.append("is_derived_variable ");
		query.append("FROM variables ");
		query.append("WHERE deleted_at IS NULL ");
		query.append("ORDER BY year ASC;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			
			ArrayList<VariableSummary> variableSummaries 
				= new ArrayList<VariableSummary>();
			statement
				= connection.prepareStatement(query.toString());			
			resultSet = statement.executeQuery();			
			
			while (resultSet.next() == true) {
				
				VariableSummary variableSummary = new VariableSummary();
				variableSummary.setIdentifier(resultSet.getInt(1));
				variableSummary.setName(resultSet.getString(2));

				String year = resultSet.getString(3);
				variableSummary.setYear( year);
				variableSummary.setLabel(resultSet.getString(4));
				
				
				if (resultSet.getInt(5) == 1) {
					variableSummary.setDerived(true);
				}
				else {
					variableSummary.setDerived(false);
				}
				
				variableSummaries.add(variableSummary);
			}
			
			return variableSummaries;
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetSummaryVariableData");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ALL_SUMMARY_VARIABLES,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}	
	}
	
	public String[] getVariableNames(Connection connection, User user) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT name ");
		query.append("FROM variables ");
		query.append("WHERE deleted_at IS NULL ");
		query.append("ORDER BY name ASC;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			ArrayList<String> variableNames = new ArrayList<String>();

			statement
				= connection.prepareStatement(query.toString());			
			resultSet = statement.executeQuery();
			
			while (resultSet.next() == true) {
				variableNames.add(resultSet.getString(1));
			}

			String[] results 
				= variableNames.toArray(new String[0]);
			return results;
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetVariableNames");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_VARIABLE_NAMES,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}	
	}

	public Variable getOriginalVariable(Connection connection,
										Variable targetVariable) throws MacawException {	

		checkVariableExists(connection, targetVariable);

		try {
			int identifier = targetVariable.getIdentifier();
			if (isDerivedVariable(connection, identifier) == true) {
				return getDerivedVariable(connection, identifier);
			}	
			else {
				return getRawVariable(connection, identifier);
			}
		}
		catch(SQLException exception) {
			
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetOriginalVariable",
											targetVariable.getDisplayName());
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ORIGINAL_VARIABLE,
									 errorMessage);
			throw macawException;
		}
	}

	private int getVariablePrimaryKey(Connection connection,
									  Variable variable) throws SQLException {

		//name, year, label should be enough to distinguish one 
		//variable from another.  Trying to save computation costs of
		//retrieving other things like category_id, 
		//
		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier ");
		query.append("FROM variables ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("name=? AND year=? AND label=? AND file_path=?;");
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setString(1, variable.getName());
			statement.setString(2, variable.getYear());
			statement.setString(3, variable.getLabel());
			statement.setString(4, variable.getFilePath());
			resultSet = statement.executeQuery();
			resultSet.next();
			return resultSet.getInt(1);			
		}
		finally {
			SQLUtilities.closeStatementsWithoutCatch(statement, resultSet);
		}		
	}
	
	/**
	 * assumes that variable exists
	 * @param connection
	 * @param variableIdentifier
	 * @return
	 * @throws SQLException
	 */
	private boolean isDerivedVariable(Connection connection,
									  int variableIdentifier) throws SQLException {
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT is_derived_variable ");
		query.append("FROM variables ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");
		
		PreparedStatement statement = connection.prepareStatement(query.toString());
		statement.setInt(1, variableIdentifier);
		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		return resultSet.getBoolean(1);
	}
	
	public void clear(Connection connection) throws MacawException {

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement("DELETE FROM variables;");
			statement.executeUpdate();

			statement
				= connection.prepareStatement("DELETE FROM supporting_documents_for_variables;");
			statement.executeUpdate();
			
			statement
				= connection.prepareStatement("DELETE FROM ontology_terms_for_variables;");
			statement.executeUpdate();

			statement
				= connection.prepareStatement("DELETE FROM source_variables;");
			statement.executeUpdate();
		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToClearTable");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_CLEAR_TABLE,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, null);
		}
	}
	
	public Variable getAlternativeVariable(Connection connection,
										   User user,
										   Variable targetVariable) throws MacawException {

		checkVariableExists(connection, targetVariable);

		StringBuilder query = new StringBuilder();
		query.append("SELECT alternative_variable_id ");
		query.append("FROM variables ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setInt(1, targetVariable.getIdentifier());
			resultSet = statement.executeQuery();
			
			resultSet.next();
			int alternativeVariableID = resultSet.getInt(1);
			
			Variable alternativeVariable = null;
			if (alternativeVariableID != 0) {
				if (isDerivedVariable(connection, alternativeVariableID) == true) {
					alternativeVariable = getDerivedVariable(connection, alternativeVariableID);
				}
				else {
					alternativeVariable = getRawVariable(connection, alternativeVariableID);
				}
			}
			return alternativeVariable;
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetAlternativeVariable",
											targetVariable.getDisplayName());
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ALTERNATIVE_VARIABLE,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}	
	}

	public void setAlternativeVariable(Connection connection,
									   User user, 
									   Variable targetVariable,
									   Variable updatedAlternativeVariable) throws MacawException {

		//Part I: Check parameters
		checkVariableExists(connection, targetVariable);

		if (updatedAlternativeVariable != null) {
			checkVariableExists(connection, updatedAlternativeVariable);

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
		
		Variable oldAlternativeVariable
			= targetVariable.getAlternativeVariable();
		
		MacawChangeEvent changeEvent
			= Variable.detectChangesInAlternativeVariable(user, 
														  targetVariable, 
														  updatedAlternativeVariable);
		if (changeEvent == null) {
			//no change so don't bother
			return;
		}
		
		StringBuilder query = new StringBuilder();
		query.append("UPDATE variables ");
		query.append("SET alternative_variable_id=? ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");

		PreparedStatement statement = null;
		try {
			
			//Part II: Perform Update
			statement
				= connection.prepareStatement(query.toString());
			if (updatedAlternativeVariable == null) {
				statement.setInt(1, 0);
			}
			else {
				statement.setInt(1, updatedAlternativeVariable.getIdentifier());
			}
			statement.setInt(2, targetVariable.getIdentifier());
			
			statement.executeUpdate();

			//Part III: Record changes
			registerChangeEvent(connection, changeEvent);
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToUpdateAlternativeVariable",
											updatedAlternativeVariable.getDisplayName(),
											targetVariable.getDisplayName());
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_UPDATE_ALTERNATIVE_VARIABLE,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, null);
		}	
	}

	// ==========================================
	// Section Errors and Validation
	// ==========================================
	
	private void checkVariableExists(Connection connection,
			   						 Variable targetVariable) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT 1 ");
		query.append("FROM variables ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			int identifier
				= targetVariable.getIdentifier();
			statement = connection.prepareStatement(query.toString());
			statement.setInt(1, identifier);
			resultSet = statement.executeQuery();
			if (resultSet.next() == false) {
				String errorMessage
					= MacawMessages.getMessage("general.error.nonExistentItem",
												targetVariable.getDisplayName());
				MacawException exception 
					= new MacawException(MacawErrorType.NON_EXISTENT_VARIABLE,
										 errorMessage);
				throw exception;					
			}
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckVariableExists",
											targetVariable.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_VARIABLE_EXISTS,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}					
	}

	private void checkVariableExists(Connection connection,
									String variableName) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT 1 ");
		query.append("FROM variables ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("name=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());
			statement.setString(1, variableName);
			resultSet = statement.executeQuery();
			if (resultSet.next() == false) {
				String errorMessage
					= MacawMessages.getMessage("general.error.nonExistentItem",
												variableName);
				MacawException exception 
					= new MacawException(MacawErrorType.NON_EXISTENT_VARIABLE,
										 errorMessage);
				throw exception;					
			}
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckVariableExists",
											variableName);
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_VARIABLE_EXISTS,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}					
	}
	
	private void checkVariableDuplicates(Connection connection,
										Variable candidateVariable) throws MacawException {


		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier ");
		query.append("FROM variables ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("name=?;");
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			String name
				= candidateVariable.getName();
			statement = connection.prepareStatement(query.toString());
			statement.setString(1, name);
			resultSet = statement.executeQuery();
			if (resultSet.next() == true) {
				int resultIdentifier = resultSet.getInt(1);
				if (resultIdentifier != candidateVariable.getIdentifier()) {	
					//items have same display name but different IDs. Therefore
					//the candidate is a duplicate
					String errorMessage
						= MacawMessages.getMessage("variable.error.duplicateExists",
													candidateVariable.getDisplayName());
					MacawException exception
						= new MacawException(MacawErrorType.DUPLICATE_VARIABLE,
											errorMessage);
					throw exception;				
				}
			}
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckVariableDuplicates");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_VARIABLE_DUPLICATES,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}		
	}

	private void checkDuplicateDocumentAssociation(Connection connection, 
			  									   Variable variable, 
			  									   SupportingDocument supportingDocument) throws MacawException {
		
		try {
			if (documentAssociationInDatabase(connection,
											  variable.getIdentifier(),
											  supportingDocument.getIdentifier()) == true) {

				String errorMessage
					= MacawMessages.getMessage("variable.error.duplicateDocumentAssociation",
												supportingDocument.getDisplayName(),
												variable.getDisplayName());
				MacawException exception 
					= new MacawException(MacawErrorType.DUPLICATE_DOCUMENT_ASSOCIATION,
										errorMessage);				
				throw exception;
			}
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.duplicate");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_DOCUMENT_ASSOCIATION_DUPLICATE,
									 errorMessage);
			throw macawException;
		}
	}

	private void checkDocumentAssociationExists(Connection connection, 
												Variable variable, 
												SupportingDocument supportingDocument) throws MacawException {

		try {
			if (documentAssociationInDatabase(connection,
											  variable.getIdentifier(),
											  supportingDocument.getIdentifier()) == false) {

				MacawException exception = new MacawException();
				String errorMessage
					= MacawMessages.getMessage("variable.error.nonExistentDocumentAssociation",
												supportingDocument.getDisplayName(),
												variable.getDisplayName());
				exception.addErrorMessage(MacawErrorType.NON_EXISTENT_DOCUMENT_ASSOCIATION,
										  errorMessage);
				throw exception;
			}
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.duplicate");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_DOCUMENT_ASSOCIATION_EXISTS,
									 errorMessage);
			throw macawException;
		}
	}

	private boolean documentAssociationInDatabase(Connection connection,
												  int variableID,
												  int supportingDocumentID) throws SQLException {

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT 1 ");
			query.append("FROM supporting_documents_for_variables ");
			query.append("WHERE variable_id=? AND document_id=?;");
		
			statement = connection.prepareStatement(query.toString());
			statement.setInt(1, variableID);
			statement.setInt(2, supportingDocumentID);
			resultSet = statement.executeQuery();
			return resultSet.next();
		}
		finally {
			SQLUtilities.closeStatementsWithoutCatch(statement, resultSet);
		}		
	}

	
	private void checkDuplicateTermAssociation(Connection connection, 
											   Variable variable, 
											   OntologyTerm ontologyTerm) throws MacawException {

		try {
			if (ontologyTermAssociationInDatabase(connection,
												  variable.getIdentifier(),
												  ontologyTerm.getIdentifier()) == true) {

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
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckOntologyTermAssociationDuplicate",
											variable.getDisplayName(),
											ontologyTerm.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_ONTOLOGY_TERM_ASSOCIATION_DUPLICATE,
									 errorMessage);
			throw macawException;
		}
	}

	private void checkTermAssociationExists(Connection connection, 
											Variable variable, 
											OntologyTerm ontologyTerm) throws MacawException {

		try {
			if (ontologyTermAssociationInDatabase(connection,
												  variable.getIdentifier(),
												  ontologyTerm.getIdentifier()) == false) {

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
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckOntologyTermAssociationExists",
											variable.getDisplayName(),
											ontologyTerm.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_ONTOLOGY_TERM_ASSOCIATION_EXISTS,
									 errorMessage);
			throw macawException;
		}
	}

	private boolean ontologyTermAssociationInDatabase(Connection connection,
			  										  int variableID,
			  										  int ontologyTermID) throws SQLException {

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT 1 ");
			query.append("FROM ontology_terms_for_variables ");
			query.append("WHERE variable_id=? AND ontology_term_id=?;");

			statement = connection.prepareStatement(query.toString());
			statement.setInt(1, variableID);
			statement.setInt(2, ontologyTermID);
			resultSet = statement.executeQuery();
			return resultSet.next();
		}
		finally {
			SQLUtilities.closeStatementsWithoutCatch(statement, resultSet);
		}		
	}

	private void checkDuplicateVariableAssociation(Connection connection, 
			   									   DerivedVariable derivedVariable, 
			   									   Variable sourceVariable) throws MacawException {

		try {
			if (variableAssociationInDatabase(connection,
											  derivedVariable.getIdentifier(),
											  sourceVariable.getIdentifier()) == true) {

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
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckVariableAssociationDuplicate",
											derivedVariable.getDisplayName(),
											sourceVariable.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_VARIABLE_ASSOCIATION_DUPLICATE,
									 errorMessage);
			throw macawException;
		}
	}

	private void checkVariableAssociationExists(Connection connection, 
												DerivedVariable derivedVariable, 
												Variable sourceVariable) throws MacawException {

		try {
			if (variableAssociationInDatabase(connection,
											  derivedVariable.getIdentifier(),
											  sourceVariable.getIdentifier()) == false) {

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
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckVariableAssociationExists",
											derivedVariable.getDisplayName(),
											sourceVariable.getDisplayName());
			
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_VARIABLE_ASSOCIATION_EXISTS,
									 errorMessage);
			throw macawException;
		}
	}

	private boolean variableAssociationInDatabase(Connection connection,
												  int derivedVariableID,
												  int sourceVariableID) throws SQLException {

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT 1 ");
			query.append("FROM source_variables ");
			query.append("WHERE derived_variable_id=? AND source_variable_id=?;");

			statement = connection.prepareStatement(query.toString());
			statement.setInt(1, derivedVariableID);
			statement.setInt(2, sourceVariableID);
			resultSet = statement.executeQuery();
			return resultSet.next();
		}
		finally {
			SQLUtilities.closeStatementsWithoutCatch(statement, resultSet);
		}		
	}
	
	private void checkListChoices(Connection connection, 
								 Variable variable) throws MacawException {
		
		Category category = new Category();
		category.setName(variable.getCategory());
		/**
		listChoiceManager.getCategoryIdentifier(connection, 
												variable, 
												category);
		 */
		
		if (variable.isCleaned() == true) {
			CleaningState cleaningState = new CleaningState();
			cleaningState.setName(variable.getCleaningStatus());
			listChoiceManager.getCleaningStateIdentifier(connection, 
														 variable,
														 cleaningState);
		}
		
		AvailabilityState availabilityState = new AvailabilityState();
		availabilityState.setName(variable.getAvailability());
		listChoiceManager.getAvailabilityStateIdentifier(connection, 
														 variable,
														 availabilityState);	
		AliasFilePath aliasFilePath = new AliasFilePath();
		aliasFilePath.setAlias(variable.getAlias());
		listChoiceManager.getAliasFilePathIdentifier(connection, 
													 variable,
													 aliasFilePath);
	}

	
	// ==========================================
	// Section Interfaces
	// ==========================================

	// ==========================================
	// Section Overload
	// ==========================================

}
