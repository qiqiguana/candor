package macaw.persistenceLayer.production;

import macaw.businessLayer.*;

import macaw.system.*;

import java.util.*;
import java.sql.*;

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

public class SQLListChoiceManager extends SQLCurationConceptManager {

	// ==========================================
	// Section Constants
	// ==========================================
	private enum ListChoiceType {CATEGORY, CLEANING_STATE, AVAILABILITY_STATE, ALIAS_FILE_PATH};
	
	// ==========================================
	// Section Properties
	// ==========================================
	
	// ==========================================
	// Section Construction
	// ==========================================
	public SQLListChoiceManager(SQLChangeEventManager changeEventManager) {
		super(changeEventManager);
	}
		
	public void createTables(Connection connection) throws MacawException {
		try {
			createCategoriesTable(connection);
			createCleaningStatesTable(connection);
			createAvailabilityStatesTable(connection);
			createAliasFilePathTable(connection);					
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
	
	/**
	 * creates table used to store data from {@link macaw.businessLayer.Category} objects.
	 */
	private void createCategoriesTable(Connection connection) throws  SQLException {
		StringBuilder query = new StringBuilder();
		query.append("CREATE TABLE categories ( ");
		query.append("identifier INT AUTO_INCREMENT NOT NULL,");
		query.append("name VARCHAR(255),");
		query.append("deleted_at DATETIME,");
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
	 * creates table used to store data from {@link macaw.businessLayer.CleaningState} objects.
	 */
	private void createCleaningStatesTable(Connection connection) throws  SQLException {
		StringBuilder query = new StringBuilder();
		query.append("CREATE TABLE cleaning_states (");
		query.append("identifier INT AUTO_INCREMENT NOT NULL,");
		query.append("name VARCHAR(255),");
		query.append("deleted_at DATETIME,");
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
	 * creates table used to store data from {@link macaw.businessLayer.AvailabilityState} objects.
	 */
	private void createAvailabilityStatesTable(Connection connection) throws  SQLException {
		StringBuilder query = new StringBuilder();
		query.append("CREATE TABLE availability_states(");
		query.append("identifier INT AUTO_INCREMENT NOT NULL,");		
		query.append("name VARCHAR(255),");
		query.append("deleted_at DATETIME,");
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
	 * creates table used to store data from {@link macaw.model.DataLibrary} objects.
	 */
	private void createAliasFilePathTable(Connection connection) throws  SQLException {
		StringBuilder query = new StringBuilder();
		query.append("CREATE TABLE alias_file_paths(");
		query.append("identifier INT AUTO_INCREMENT NOT NULL,");
		query.append("alias VARCHAR(255),");
		query.append("file_path VARCHAR(255),");
		query.append("deleted_at DATETIME,");
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
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public String getCategoryName(Connection connection, 
								  Variable variable,
								  int categoryID) throws MacawException {
		
		if (categoryID == 0) {
			String unknownValue 
				= MacawMessages.getMessage("general.fieldValue.unknown");
			return unknownValue;
		}
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT name ");
		query.append("FROM categories ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier = ?;");
		try {
			return getMacawListChoice(connection,
									  variable,
									  ListChoiceType.CATEGORY,
									  categoryID, 
									  query.toString());
		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);
			String errorMessage 
				= MacawMessages.getMessage("sql.error.unableToGetCategoryName");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_GET_CATEGORY_NAME,
									 errorMessage);
			throw macawException;
		}
	}

	public ArrayList<Category> getCategories(Connection connection,
											 User user) throws MacawException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier, name ");
		query.append("FROM categories ");
		query.append("WHERE deleted_at IS NULL ");
		query.append("ORDER BY name ASC;");
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;		
		try {
			
			ArrayList<Category> categories = new ArrayList<Category>();
			
			statement = connection.prepareStatement(query.toString());
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
			exception.printStackTrace(System.out);

			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetCategories");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_CATEGORIES,
									 errorMessage);
			throw macawException;
		}	
		finally {			
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}
	
	public void addCategory(Connection connection,
							User user,
							Category category) throws MacawException {
		
		//Part I: Validate parameters
		Category.validateFields(category);
		checkCategoryDuplicates(connection, category);

		//Part II: Perform the add category
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO categories (name, deleted_at) ");
		query.append("VALUES (?, ?);");
		
		try {
			addListChoice(connection, 
						  query.toString(), 
						  category.getName());
		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToAddCategory",
											category.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CREATE_CATEGORY,
									 errorMessage);
			throw macawException;
		}
	}
	
	public void updateCategory(Connection connection, 
							   User user,
			   				   Category revisedCategory) throws MacawException {

		
		//Part I: Validate parameters
		Category.validateFields(revisedCategory);
		checkCategoryExists(connection, revisedCategory);
		checkCategoryDuplicates(connection, revisedCategory);
			
		//make sure that there are at least some changes
		Category originalCategory
			= getOriginalCategory(connection, revisedCategory);		
		ArrayList<MacawChangeEvent> changeEvents
			= Category.detectFieldChanges(user,
										  originalCategory,
										  revisedCategory);
		if (changeEvents.size() == 0) {
			return;
		}

		StringBuilder query = new StringBuilder();
		query.append("UPDATE categories ");
		query.append("SET name=? ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");
		
		try {
			updateListChoice(connection,
			  		 		 query.toString(),
			  		 		 revisedCategory.getIdentifier(),
			  		 		 revisedCategory.getName());			
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToUpdateCategory",
											revisedCategory.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_UPDATE_CATEGORY,
									 errorMessage);
			throw macawException;
		}		
	}
	
	public void deleteCategories(Connection connection, 
								 User user,
				 				 ArrayList<Category> categoriesToDelete) throws MacawException {
		
		//Part I: Validate parameters
		if (categoriesToDelete.size() == 0) {
			return;			
		}
		for (Category currentCategory : categoriesToDelete) {
			checkCategoryExists(connection, currentCategory);
		}
		
		//Part II: Perform the delete categories operation		
		StringBuilder query = new StringBuilder();
		query.append("UPDATE categories ");
		query.append("SET deleted_at=NOW() ");
		query.append("WHERE identifier=?;");
		
		try {
			for (Category currentCategory : categoriesToDelete) {
				deleteListChoice(connection, 
								 query.toString(), 
								 currentCategory.getIdentifier());
			}
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToDeleteCategories");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_DELETE_CATEGORY,
									 errorMessage);
			throw macawException;
		}
	}

	public String getCleaningStateName(Connection connection,
									   Variable variable,
									   int cleaningStateID) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT name ");
		query.append("FROM cleaning_states ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier = ?;");
		
		try {
			return getMacawListChoice(connection, 
									  variable,
									  ListChoiceType.CLEANING_STATE,
									  cleaningStateID, 
									  query.toString());
		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);
			String errorMessage 
				= MacawMessages.getMessage("sql.error.unableToGetCleaningStateName");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_GET_CLEANING_STATE_NAME,
									 errorMessage);
			throw macawException;
		}
	}
	
	public ArrayList<CleaningState> getCleaningStates(Connection connection,
													  User user) throws MacawException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier, name ");
		query.append("FROM cleaning_states ");
		query.append("WHERE deleted_at IS NULL ");
		query.append("ORDER BY name ASC;");
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;		
		try {
			
			ArrayList<CleaningState> cleaningStates = new ArrayList<CleaningState>();
			
			statement = connection.prepareStatement(query.toString());
			resultSet = statement.executeQuery();
			while (resultSet.next() == true) {
				CleaningState cleaningState = new CleaningState();
				cleaningState.setIdentifier(resultSet.getInt(1));
				cleaningState.setName(resultSet.getString(2));
				cleaningStates.add(cleaningState);
			}
			return cleaningStates;
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetCleaningStates");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_CLEANING_STATES,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}

	public void addCleaningState(Connection connection,
								 CleaningState cleaningState) throws MacawException {

		//Part I: Validate parameters
		CleaningState.validateFields(cleaningState);
		checkCleaningStateDuplicates(connection, cleaningState);

		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO cleaning_states (name, deleted_at) ");
		query.append("VALUES (?, ?);");
		
		try {
			addListChoice(connection, 
						  query.toString(),
						  cleaningState.getName());
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToAddCleaningState",
											cleaningState.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CREATE_CLEANING_STATE,
									 errorMessage);
			throw macawException;
		}	
	}

	public void updateCleaningState(Connection connection, 
									User user,
			   				   	    CleaningState revisedCleaningState) throws MacawException {


		//Part I: Validate parameters
		CleaningState.validateFields(revisedCleaningState);
		checkCleaningStateExists(connection, revisedCleaningState);
		checkCleaningStateDuplicates(connection, revisedCleaningState);

		StringBuilder query = new StringBuilder();
		query.append("UPDATE cleaning_states ");
		query.append("SET name=? ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");
		try {			
			updateListChoice(connection,
							 query.toString(),
							 revisedCleaningState.getIdentifier(),
							 revisedCleaningState.getName());
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToUpdateCleaningState",
											revisedCleaningState.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_UPDATE_CLEANING_STATE,
									 errorMessage);
			throw macawException;
		}	
	}
	
	public void deleteCleaningStates(Connection connection, 
									 ArrayList<CleaningState> cleaningStatesToDelete) throws MacawException {

		
		//Part I: Validate parameters
		if (cleaningStatesToDelete.size() == 0) {
			return;			
		}
		for (CleaningState currentCleaningState : cleaningStatesToDelete) {
			checkCleaningStateExists(connection, currentCleaningState);
		}

		StringBuilder query = new StringBuilder();
		query.append("UPDATE cleaning_states ");
		query.append("SET deleted_at=NOW() ");
		query.append("WHERE identifier=?;");

		try {
			for (CleaningState currentCleaningState : cleaningStatesToDelete) {
				deleteListChoice(connection, 
								 query.toString(), 
								 currentCleaningState.getIdentifier());
			}
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToDeleteCleaningStates");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_DELETE_CLEANING_STATE,
									 errorMessage);
			throw macawException;
		}
	}

	public String getAvailabilityStateName(Connection connection, 
										   Variable variable,
										   int availabilityStateID) throws MacawException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT name ");
		query.append("FROM availability_states ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");
		
		try {
			return getMacawListChoice(connection, 
									  variable,
					  				  ListChoiceType.AVAILABILITY_STATE,
									  availabilityStateID, 
									  query.toString());
		}
		catch(SQLException exception) {
			String errorMessage 
				= MacawMessages.getMessage("sql.error.unableToGetAvailabilityStateName");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_GET_AVAILABILITY_STATE_NAME,
									 errorMessage);
			throw macawException;
		}
	}

	public ArrayList<AvailabilityState> getAvailabilityStates(Connection connection) throws MacawException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier, name ");
		query.append("FROM availability_states ");
		query.append("WHERE deleted_at IS NULL ");
		query.append("ORDER BY name ASC;");
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;		
		try {
			
			ArrayList<AvailabilityState> availabilityStates 
				= new ArrayList<AvailabilityState>();
			
			statement = connection.prepareStatement(query.toString());
			resultSet = statement.executeQuery();
			while (resultSet.next() == true) {
				AvailabilityState availabilityState = new AvailabilityState();
				availabilityState.setIdentifier(resultSet.getInt(1));
				availabilityState.setName(resultSet.getString(2));
				availabilityStates.add(availabilityState);
			}
			return availabilityStates;
		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);
			//log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetAvailabilityStates");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_AVAILABILITY_STATES,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}
	
	public void addAvailabilityState(Connection connection,
									 User user,
			 					 	 AvailabilityState availabilityState) throws MacawException {

		//Part I: Validate parameters
		AvailabilityState.validateFields(availabilityState);
		checkAvailabilityStateDuplicates(connection, availabilityState);

		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO availability_states (name, deleted_at) ");
		query.append("VALUES (?, ?);");
		try {
			addListChoice(connection,
						  query.toString(),
						  availabilityState.getName());
			
			//Part III: Record changes
			String changeMessage
				= MacawMessages.getMessage("availabilityState.saveChanges.newRecord",
										   availabilityState.getDisplayName() );
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.LIST_CHOICE,
									   changeMessage,
									   user.getUserID() );
			registerChangeEvent(connection,
								changeEvent);
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToAddAvailabilityState",
											availabilityState.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CREATE_AVAILABILITY_STATE,
									 errorMessage);
			throw macawException;
		}
	}

	public void updateAvailabilityState(Connection connection, 
		   	    						AvailabilityState revisedAvailabilityState) throws MacawException {


		//Part I: Validate parameters
		AvailabilityState.validateFields(revisedAvailabilityState);
		checkAvailabilityStateExists(connection, revisedAvailabilityState);
		checkAvailabilityStateDuplicates(connection, revisedAvailabilityState);

		StringBuilder query = new StringBuilder();
		query.append("UPDATE availability_states ");
		query.append("SET name=? ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");
		try {			
			updateListChoice(connection,
							 query.toString(),
							 revisedAvailabilityState.getIdentifier(),
							 revisedAvailabilityState.getName());
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToUpdateAvailabilityState",
											revisedAvailabilityState.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_UPDATE_AVAILABILITY_STATE,
									 errorMessage);
			throw macawException;
		}
	}

	public void deleteAvailabilityStates(Connection connection, 
										 ArrayList<AvailabilityState> availabilityStatesToDelete) throws MacawException {

		//Part I: Validate parameters
		if (availabilityStatesToDelete.size() == 0) {
			return;			
		}
		
		for (AvailabilityState currentAvailabilityState : availabilityStatesToDelete) {
			checkAvailabilityStateExists(connection, currentAvailabilityState);
		}
				
		StringBuilder query = new StringBuilder();
		query.append("UPDATE availability_states ");
		query.append("SET deleted_at=NOW() ");
		query.append("WHERE identifier=?;");
		
		try {
			for (AvailabilityState currentAvailabilityState : availabilityStatesToDelete) {
				deleteListChoice(connection, 
								 query.toString(), 
								 currentAvailabilityState.getIdentifier());
			}
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToDeleteAvailabilityStates");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_DELETE_AVAILABILITY_STATE,
									 errorMessage);
			throw macawException;
		}
	}

	public String getAliasFilePathName(Connection connection, 
									   Variable variable,
									   int dataLibraryID) throws MacawException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT alias ");
		query.append("FROM alias_file_paths ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");
		
		try {
			return getMacawListChoice(connection, 
									  variable,
	  				  				  ListChoiceType.ALIAS_FILE_PATH,
									  dataLibraryID, 
									  query.toString());
		}
		catch(SQLException exception) {
			String errorMessage 
				= MacawMessages.getMessage("sql.error.unableToGetAliasFilePathName");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ALIAS_FILE_PATH_NAME,
									 errorMessage);
			throw macawException;
		}
	}

	public ArrayList<AliasFilePath> getAliasFilePaths(Connection connection) throws MacawException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier,");
		query.append("alias,");
		query.append("file_path ");
		query.append("FROM alias_file_paths ");
		query.append("WHERE deleted_at IS NULL ");
		query.append("ORDER BY alias ASC;");
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;		
		try {
			
			ArrayList<AliasFilePath> aliasFilePaths 
				= new ArrayList<AliasFilePath>();
			
			statement = connection.prepareStatement(query.toString());
			resultSet = statement.executeQuery();
			while (resultSet.next() == true) {
				AliasFilePath aliasFilePath = new AliasFilePath();
				aliasFilePath.setIdentifier(resultSet.getInt(1));
				aliasFilePath.setAlias(resultSet.getString(2));
				aliasFilePath.setFilePath(resultSet.getString(3));
				aliasFilePaths.add(aliasFilePath);
			}
			return aliasFilePaths;
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetDataLibraries");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ALIAS_FILE_PATH,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}

	public AliasFilePath getAliasFilePath(Connection connection, String cardNumber) throws MacawException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier,");
		query.append("file_path ");
		query.append("FROM alias_file_paths ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("alias=? ");
		query.append("ORDER BY alias ASC;");
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;		
		try {
			
			statement = connection.prepareStatement(query.toString());
			statement.setString(1, cardNumber);
			resultSet = statement.executeQuery();
			if (resultSet.next() == true) {
				AliasFilePath aliasFilePath = new AliasFilePath();
				aliasFilePath.setIdentifier(resultSet.getInt(1));
				aliasFilePath.setFilePath(resultSet.getString(2));
				aliasFilePath.setAlias(cardNumber);
				return aliasFilePath;
			}
			else {
				return null;
			}
		}
		catch(SQLException exception) {		
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetAliasFilePaths");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ALIAS_FILE_PATH,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}

	public ArrayList<AliasFilePath> getAliasFilePathsMatchingName(Connection connection, 
													 			  String cardNumberExpression) throws MacawException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier,");
		query.append("alias,");
		query.append("file_path ");
		query.append("FROM alias_file_paths ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("alias LIKE ? ");
		query.append("ORDER BY alias ASC;");
		
		ArrayList<AliasFilePath> aliasFilePaths = new ArrayList<AliasFilePath>();
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;		
		try {
			
			statement = connection.prepareStatement(query.toString());
			StringBuilder searchCriterion = new StringBuilder();
			searchCriterion.append("%");
			searchCriterion.append(cardNumberExpression);
			searchCriterion.append("%");
			
			statement.setString(1, searchCriterion.toString());
			resultSet = statement.executeQuery();
			while (resultSet.next() == true) {
				AliasFilePath aliasFilePath = new AliasFilePath();
				aliasFilePath.setIdentifier(resultSet.getInt(1));
				aliasFilePath.setAlias(resultSet.getString(2));
				aliasFilePath.setFilePath(resultSet.getString(3));
				aliasFilePaths.add(aliasFilePath);
			}
			
			return aliasFilePaths;
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetDataLibraries");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ALIAS_FILE_PATH,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}

	public void addAliasFilePath(Connection connection,
		 	 				     AliasFilePath aliasFilePath) throws MacawException {

		//Part I: Validate parameters
		AliasFilePath.validateFields(aliasFilePath);
		checkAliasFilePathDuplicates(connection, aliasFilePath);

		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO alias_file_paths (alias, file_path, deleted_at) ");
		query.append("VALUES (?, ?, ?);");
		ResultSet resultSet = null;
		PreparedStatement statement = null;		
		try {
			statement = connection.prepareStatement(query.toString());
			statement.setString(1, aliasFilePath.getAlias());
			statement.setString(2, aliasFilePath.getFilePath());
			statement.setDate(3, null);
			statement.executeUpdate();
		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToAddAliasFilePath",
											aliasFilePath.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CREATE_ALIAS_FILE_PATH,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}

	public void updateAliasFilePath(Connection connection, 
								    User user,
		   	    				  	AliasFilePath revisedAliasFilePath) throws MacawException {

		//Part I: Validate parameters
		AliasFilePath.validateFields(revisedAliasFilePath);
		checkAliasFilePathExists(connection, revisedAliasFilePath);
		checkAliasFilePathDuplicates(connection, revisedAliasFilePath);

		//ensure there are at least some changes
		AliasFilePath originalAliasFilePath
			= getOriginalAliasFilePath(connection, revisedAliasFilePath);		
		ArrayList<MacawChangeEvent> changeEvents
			= AliasFilePath.detectFieldChanges(user,
											   originalAliasFilePath,
											   revisedAliasFilePath);
		if (changeEvents.size() == 0) {
			return;
		}
		
		StringBuilder query = new StringBuilder();
		query.append("UPDATE alias_file_paths ");
		query.append("SET alias=?,file_path=? ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");
		
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(query.toString());
			statement.setString(1, revisedAliasFilePath.getAlias());
			statement.setString(2, revisedAliasFilePath.getFilePath());
			statement.setInt(3, revisedAliasFilePath.getIdentifier());
			statement.executeUpdate();
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToUpdateAliasFilePath",
											revisedAliasFilePath.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_UPDATE_ALIAS_FILE_PATH,
									 errorMessage);
			throw macawException;
		}
	}
	
	public void deleteAliasFilePaths(Connection connection, 
									 ArrayList<AliasFilePath> aliasFilePathsToDelete) throws MacawException {

		
		//Part I: Validate parameters
		if (aliasFilePathsToDelete.size() == 0) {
			return;			
		}
		for (AliasFilePath currentAliasFilePath : aliasFilePathsToDelete) {
			checkAliasFilePathExists(connection, currentAliasFilePath);
		}

		StringBuilder query = new StringBuilder();
		query.append("UPDATE alias_file_paths ");
		query.append("SET deleted_at=NOW() ");
		query.append("WHERE identifier=?;");
						
		try {
			for (AliasFilePath currentAliasFilePath : aliasFilePathsToDelete) {
				deleteListChoice(connection, 
								 query.toString(),				 
								 currentAliasFilePath.getIdentifier());
			}
		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToDeleteAliasFilePaths");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_DELETE_ALIAS_FILE_PATH,
									 errorMessage);
			throw macawException;
		}
	}
	
	private String getMacawListChoice(Connection connection, 
									  Variable variable,
									  ListChoiceType listChoiceType,
									  int listChoiceIdentifier,
									  String query) throws SQLException, MacawException {
		
		String variableName 
			= MacawMessages.getMessage("listChoices.unknownVariableName");	
		if (variable != null) {
			variableName = variable.getDisplayName();
		}
				
		if (listChoiceIdentifier == 0) {
			//will be the case if someone hasn't assigned a category
			return(MacawMessages.getMessage("general.fieldValue.unknown"));			
		}
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;		
		try {
			statement = connection.prepareStatement(query.toString());
			statement.setInt(1, listChoiceIdentifier);
			resultSet = statement.executeQuery();
			//assume exactly one result comes back
			if (resultSet.next() == true) {
				String name = resultSet.getString(1);
				return name;				
			}
			else {
				MacawException macawException = new MacawException();
				if (listChoiceType == ListChoiceType.CATEGORY) {
					String errorMessage = MacawMessages.getMessage("category.error.nonExistent",
																	variableName);
					macawException.addErrorMessage(MacawErrorType.NON_EXISTENT_CATEGORY,
												   errorMessage);
				}
				else if (listChoiceType == ListChoiceType.AVAILABILITY_STATE) {
					String errorMessage = MacawMessages.getMessage("availability.error.nonExistent",
																	variableName);
					macawException.addErrorMessage(MacawErrorType.NON_EXISTENT_AVAILABILITY_STATE,
												   errorMessage);					
				}
				else if (listChoiceType == ListChoiceType.CLEANING_STATE) {
					String errorMessage = MacawMessages.getMessage("cleaningState.error.nonExistent",
																	variableName);
					macawException.addErrorMessage(MacawErrorType.NON_EXISTENT_CLEANING_STATE,
												   errorMessage);					
				}
				else {
					String errorMessage = MacawMessages.getMessage("dataLibrary.error.nonExistent",
																	variableName);
					macawException.addErrorMessage(MacawErrorType.NON_EXISTENT_ALIAS_FILE_PATH,
												   errorMessage);					
				}
				throw macawException;
			}
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}

	private void addListChoice(Connection connection,
			  				   String query,
			  				   String name) throws SQLException, MacawException {

		ResultSet resultSet = null;
		PreparedStatement statement = null;		
		try {			
			statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setDate(2, null);
			statement.executeUpdate();
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}

	private void deleteListChoice(Connection connection,
			   					  String query,
			   					  int listChoiceIdentifier) throws SQLException {

		PreparedStatement statement = null;		
		try {			
			statement = connection.prepareStatement(query);
			//Time deletionDate = new Time(System.currentTimeMillis());
			//statement.setTime(1, deletionDate);
			statement.setInt(1, listChoiceIdentifier);
			statement.executeUpdate();
		}
		finally {
			if (statement != null) {
				statement.close();
			}
		}	
	}
	
	private void updateListChoice(Connection connection,
								  String query,
								  int identifier,
								  String name) throws SQLException {

		ResultSet resultSet = null;
		PreparedStatement statement = null;		
		try {			
			statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setInt(2, identifier);
			statement.executeUpdate();
		}
		finally {
			SQLUtilities.closeStatementsWithoutCatch(statement, resultSet);
		}	
	}

	public int getAvailabilityStateIdentifier(Connection connection,
											  Variable variable,
			  								  AvailabilityState availabilityState) throws MacawException {
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier ");
		query.append("FROM availability_states ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("name=?;");

		try {	
			int identifier
				= getIdentifierFromName(connection,
										variable,
										ListChoiceType.AVAILABILITY_STATE,
										query.toString(),
										availabilityState.getName());
			return identifier;
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetAvailabilityStateIdentifier",
											availabilityState.getDisplayName());
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_GET_AVAILABILITY_STATE_IDENTIFIER,
									 errorMessage);
			throw macawException;			
		}
	}

	public int getCategoryIdentifier(Connection connection,
									 Variable variable,
								     Category category) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier ");
		query.append("FROM categories ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("name=?;");
		
		try {
			int identifier
				= getIdentifierFromName(connection, 
										variable,
										ListChoiceType.CATEGORY,
										query.toString(),
										category.getName());
			return identifier;
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetCategoryIdentifier",
										   category.getDisplayName());
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_GET_CATEGORY_IDENTIFIER,
									 errorMessage);
			throw macawException;			
		}
	}

	public int getCleaningStateIdentifier(Connection connection,
										  Variable variable,
			  							  CleaningState cleaningState) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier ");
		query.append("FROM cleaning_states ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("name=?;");

		try {
			int identifier
				= getIdentifierFromName(connection, 
										variable,
										ListChoiceType.CLEANING_STATE,
										query.toString(),
										cleaningState.getName());
			return identifier;			
		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetCleaningStateIdentifier",
											cleaningState.getDisplayName());
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_GET_CLEANING_STATE_IDENTIFIER,
									 errorMessage);
			throw macawException;			
		}
	}

	public int getAliasFilePathIdentifier(Connection connection,
										  Variable variable,
										  AliasFilePath aliasFilePath) throws MacawException {


		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier ");
		query.append("FROM alias_file_paths ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("alias=?;");

		try {
			int identifier
				= getIdentifierFromName(connection, 
										variable,
										ListChoiceType.ALIAS_FILE_PATH,
										query.toString(),
										aliasFilePath.getAlias());
			return identifier;			
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetAliasFilePathIdentifier",
											aliasFilePath.getDisplayName());
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ALIAS_FILE_PATH_IDENTIFIER,
									 errorMessage);
			throw macawException;			
		}

	}

	public int getIdentifierFromName(Connection connection,
									 Variable variable,
									 ListChoiceType listChoiceType,
									 String query,
									 String name) throws SQLException, MacawException {
		
		String variableName
			= MacawMessages.getMessage("listChoices.unknownVariableName");
		if (variable != null) {
			variableName = variable.getDisplayName();
		}
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;		
		try {			
			statement = connection.prepareStatement(query);
			statement.setString(1, name);
			resultSet = statement.executeQuery();
			if (resultSet.next() == true) {
				return resultSet.getInt(1);
			}
			else {
				MacawException macawException = new MacawException();
				if (listChoiceType == ListChoiceType.CATEGORY) {
					return 0;
				}
				else if (listChoiceType == ListChoiceType.AVAILABILITY_STATE) {
					String errorMessage = MacawMessages.getMessage("availabilityState.error.nonExistent",
																   name,
																   variableName);
					macawException.addErrorMessage(MacawErrorType.NON_EXISTENT_AVAILABILITY_STATE,
												   errorMessage);					
				}
				else if (listChoiceType == ListChoiceType.CLEANING_STATE) {
					String errorMessage = MacawMessages.getMessage("cleaningState.error.nonExistent",
																   name,
																   variableName);
					macawException.addErrorMessage(MacawErrorType.NON_EXISTENT_CLEANING_STATE,
												   errorMessage);					
				}
				else {
					return 0;				
				}
				throw macawException;
			}
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}		
	}

	public Category getOriginalCategory(Connection connection,
										Category revisedCategory) throws MacawException {
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT name ");
		query.append("FROM categories ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");
		
		try {
			Category originalCategory = new Category();
			setOriginalListChoiceValues(connection,
										query.toString(),
										revisedCategory,
										originalCategory);
			return originalCategory;
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetOriginalCategory");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ORIGINAL_CATEGORY,
									 errorMessage);
			throw macawException;			
		}
	}

	public CleaningState getOriginalCleaningState(Connection connection,
												  CleaningState revisedCleaningState) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT name ");
		query.append("FROM cleaning_states ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");

		try {
			CleaningState originalCleaningState = new CleaningState();
			setOriginalListChoiceValues(connection,
										query.toString(),
										revisedCleaningState,
										originalCleaningState);
			return originalCleaningState;
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetOriginalCleaningState");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ORIGINAL_CLEANING_STATE,
									 errorMessage);
			throw macawException;			
		}
	}

	public AvailabilityState getOriginalAvailabilityState(Connection connection,
			AvailabilityState revisedAvailabilityState) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT name ");
		query.append("FROM availability_states ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");

		try {
			AvailabilityState originalAvailabilityState = new AvailabilityState();
			setOriginalListChoiceValues(connection,
										query.toString(),
										revisedAvailabilityState,
										originalAvailabilityState);
			return originalAvailabilityState;
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetOriginalAvailabilityState");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ORIGINAL_AVAILABILITY_STATE,
									 errorMessage);
			throw macawException;			
		}
	}

	public AliasFilePath getOriginalAliasFilePath(Connection connection,
											  	  AliasFilePath revisedAliasFilePath) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT alias,");
		query.append("file_path ");
		query.append("FROM alias_file_paths ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			AliasFilePath originalAliasFilePath = new AliasFilePath();
			statement = connection.prepareStatement(query.toString());
			statement.setInt(1, revisedAliasFilePath.getIdentifier());
			resultSet = statement.executeQuery();
			if (resultSet.next() == true) {
				originalAliasFilePath.setIdentifier(revisedAliasFilePath.getIdentifier());
				originalAliasFilePath.setAlias(resultSet.getString(1));
				originalAliasFilePath.setFilePath(resultSet.getString(2));
			}
			return originalAliasFilePath;
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetOriginalAliasFilePath");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ORIGINAL_ALIAS_FILE_PATH,
									 errorMessage);
			throw macawException;			
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}		
	}

	private void setOriginalListChoiceValues(Connection connection,
											 String query,
											 MacawListChoice currentListChoice,
											 MacawListChoice originalListChoice) throws SQLException, MacawException {
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {	
			int identifier = currentListChoice.getIdentifier();
			statement
				= connection.prepareStatement(query.toString());
			statement.setInt(1, identifier);
			resultSet = statement.executeQuery();
			resultSet.next();
			originalListChoice.setIdentifier(identifier);
			originalListChoice.setName(resultSet.getString(1));
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}		
	}
	

	public String getFilePathFromAlias(Connection connection,
									   String currentAlias) throws MacawException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT file_path ");
		query.append("FROM alias_file_paths ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("alias=?;");
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(query.toString());
			statement.setString(1, currentAlias);
			
			resultSet = statement.executeQuery();
			if (resultSet.next() == true) {
				return (resultSet.getString(1));
			}
			else {
				//non existent alias
				String errorMessage 
					= MacawMessages.getMessage("aliasFilePath.error.nonExistent",
												currentAlias);
				MacawException macawException 
					= new MacawException(MacawErrorType.NON_EXISTENT_ALIAS_FILE_PATH,
										 errorMessage);
				throw macawException;
			}
		}
		catch(SQLException exception) {
			MacawException macawException = new MacawException();
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}		
	}

	// ==========================================
	// Section Errors and Validation
	// ==========================================

	private void checkCategoryExists(Connection connection, 
			 						 Category category) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT 1 ");
		query.append("FROM categories ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());
			statement.setInt(1, category.getIdentifier());
			resultSet = statement.executeQuery();
			if (resultSet.next() == false) {				
				String errorMessage
					= MacawMessages.getMessage("general.error.nonExistentItem",
												category.getDisplayName());
				MacawException exception
					= new MacawException(MacawErrorType.NON_EXISTENT_CATEGORY,
										 errorMessage);				
				throw exception;				
			}
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckCategoryExists");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_CATEGORY_EXISTS,
									 errorMessage);
			throw macawException;			
		}		
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}
	
	private void checkCategoryDuplicates(Connection connection, 
										 Category category) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT 1 ");
		query.append("FROM categories ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("name=? AND ");
		query.append("identifier != ?;");

		
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());
			statement.setString(1, category.getName());
			statement.setInt(2, category.getIdentifier());
			resultSet = statement.executeQuery();
			if (resultSet.next() == true) {				
				String errorMessage
					= MacawMessages.getMessage("category.error.duplicateItem",
												category.getDisplayName());
				MacawException macawException
					= new MacawException(MacawErrorType.DUPLICATE_CATEGORY,
										 errorMessage);
				throw macawException;				
			}
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckCategoryDuplicate");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_CATEGORY_DUPLICATE,
									 errorMessage);
			throw macawException;			
		}		
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}				
	}

	private void checkCleaningStateExists(Connection connection, 
										  CleaningState cleaningState) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT 1 ");
		query.append("FROM cleaning_states ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());
			statement.setInt(1, cleaningState.getIdentifier());
			resultSet = statement.executeQuery();
			if (resultSet.next() == false) {
				String errorMessage
					= MacawMessages.getMessage("general.error.nonExistentItem",
												cleaningState.getDisplayName());
				MacawException exception
					= new MacawException(MacawErrorType.NON_EXISTENT_CLEANING_STATE,
										 errorMessage);
				throw exception;				
			}
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckCleaningStateExists");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_CLEANING_STATE_EXISTS,
									 errorMessage);
			throw macawException;			
		}		
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}

	private void checkCleaningStateDuplicates(Connection connection, 
			  								  CleaningState cleaningState) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT 1 ");
		query.append("FROM cleaning_states ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("name=? AND ");
		query.append("identifier != ?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());
			statement.setString(1, cleaningState.getName());
			statement.setInt(2, cleaningState.getIdentifier());
			resultSet = statement.executeQuery();
			if (resultSet.next() == true) {
				String errorMessage
					= MacawMessages.getMessage("category.error.duplicateItem",
												cleaningState.getDisplayName());
				MacawException macawException
					= new MacawException(MacawErrorType.DUPLICATE_CLEANING_STATE,
										 errorMessage);
				throw macawException;				
			}
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckCleaningStateDuplicate");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_CLEANING_STATE_DUPLICATE,
									 errorMessage);
			throw macawException;			
		}		
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}		
	}

	private void checkAvailabilityStateExists(Connection connection, 
										  	  AvailabilityState availabilityState) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT 1 ");
		query.append("FROM availability_states ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());
			statement.setInt(1, availabilityState.getIdentifier());
			resultSet = statement.executeQuery();
			if (resultSet.next() == false) {
				String errorMessage
					= MacawMessages.getMessage("general.error.nonExistentItem",
											   availabilityState.getDisplayName());
				MacawException exception
					= new MacawException(MacawErrorType.NON_EXISTENT_AVAILABILITY_STATE,
										 errorMessage);
				throw exception;
			}
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckAvailabilityStateExists");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_AVAILABILITY_STATE_EXISTS,
									 errorMessage);
			throw macawException;			
		}		
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}
	
	private void checkAvailabilityStateDuplicates(Connection connection, 
												  AvailabilityState availabilityState) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT 1 ");
		query.append("FROM availability_states ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("name=? AND ");
		query.append("identifier != ?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());
			statement.setString(1, availabilityState.getName());
			statement.setInt(2, availabilityState.getIdentifier());
			resultSet = statement.executeQuery();
			if (resultSet.next() == true) {
				String errorMessage
					= MacawMessages.getMessage("category.error.duplicateItem",
												availabilityState.getDisplayName());
				MacawException macawException
					= new MacawException(MacawErrorType.DUPLICATE_AVAILABILITY_STATE,
										 errorMessage);
				throw macawException;				
			}
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckAvailabilityStateDuplicate");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_AVAILABILITY_STATE_DUPLICATE,
									 errorMessage);
			throw macawException;			
		}		
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}		
	}

	private void checkAliasFilePathExists(Connection connection, 
										  AliasFilePath aliasFilePath) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT 1 ");
		query.append("FROM alias_file_paths ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());
			statement.setInt(1, aliasFilePath.getIdentifier());
			resultSet = statement.executeQuery();
			if (resultSet.next() == false) {
				String errorMessage
					= MacawMessages.getMessage("general.error.nonExistentItem",
												aliasFilePath.getDisplayName());
				MacawException exception
					= new MacawException(MacawErrorType.NON_EXISTENT_ALIAS_FILE_PATH,
										errorMessage);
				throw exception;				
			}
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckAliasFilePathExists");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_ALIAS_FILE_PATH_EXISTS,
									 errorMessage);
			throw macawException;			
		}		
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}

	private void checkAliasFilePathDuplicates(Connection connection, 
											  AliasFilePath aliasFilePath) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT 1 ");
		query.append("FROM alias_file_paths ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("alias=? AND ");
		query.append("identifier != ?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());
			statement.setString(1, aliasFilePath.getAlias());
			statement.setInt(2, aliasFilePath.getIdentifier());
			resultSet = statement.executeQuery();
			if (resultSet.next() == true) {
				String errorMessage
					= MacawMessages.getMessage("aliasFilePath.error.duplicateItem",
												aliasFilePath.getDisplayName());
				MacawException macawException
					= new MacawException(MacawErrorType.DUPLICATE_ALIAS_FILE_PATH,
										 errorMessage);
				throw macawException;				
			}
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckAliasFilePathDuplicate");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_ALIAS_FILE_PATH_EXISTS,
									 errorMessage);
			throw macawException;			
		}		
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}		
	}

	public void clear(Connection connection) throws MacawException {

		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement("DELETE FROM categories;");
			statement.executeUpdate();

			statement
				= connection.prepareStatement("DELETE FROM cleaning_states;");
			statement.executeUpdate();

			statement
				= connection.prepareStatement("DELETE FROM availability_states;");
			statement.executeUpdate();

			statement
				= connection.prepareStatement("DELETE FROM alias_file_paths;");
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

	// ==========================================
	// Section Interfaces
	// ==========================================

	// ==========================================
	// Section Overload
	// ==========================================

}

