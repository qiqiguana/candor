package macaw.persistenceLayer.production;

import macaw.businessLayer.*;
import macaw.system.MacawChangeEvent;
import macaw.system.MacawErrorType;
import macaw.system.MacawException;
import macaw.system.MacawMessages;

import java.util.ArrayList;
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

public class SQLOntologyTermManager extends SQLCurationConceptManager {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================

	
	// ==========================================
	// Section Construction
	// ==========================================
	public SQLOntologyTermManager(SQLChangeEventManager changeEventManager) {
		super(changeEventManager);
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	public void createTable(Connection connection) throws MacawException {
						   					   
		StringBuilder query = new StringBuilder();
		query.append("CREATE TABLE ontology_terms (");
		query.append("identifier INT AUTO_INCREMENT NOT NULL,");
		query.append("term VARCHAR(255) NOT NULL,");
		query.append("ontology_name VARCHAR(255) NOT NULL,");
		query.append("description VARCHAR(255),");
		query.append("name_space VARCHAR(255) NOT NULL,");
		query.append("deleted_at DATETIME,");
		query.append("PRIMARY KEY(identifier));");
				
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.executeUpdate();							
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
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, null);
		}
	}

	public ArrayList<OntologyTerm> getAllOntologyTerms(Connection connection,
													   User user) throws MacawException {
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier,");
		query.append("term,");
		query.append("ontology_name,");
		query.append("description,");
		query.append("name_space ");
		query.append("FROM ontology_terms ");
		query.append("WHERE deleted_at IS NULL ");
		query.append("ORDER BY term ASC;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());
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
				= MacawMessages.getMessage("sql.error.unableToGetAllOntologyTerms");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ALL_ONTOLOGY_TERMS,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}	

	public void addOntologyTerm(Connection connection,
								User user,
								OntologyTerm ontologyTerm) throws MacawException {
		
		//Part I: Validate parameters
		//check basic field errors
		OntologyTerm.validateFields(ontologyTerm);		
		//check for duplicates
		checkOntologyTermDuplicates(connection, ontologyTerm);
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO ontology_terms ");
		query.append("(term,");
		query.append("ontology_name,");
		query.append("description,");
		query.append("name_space,");
		query.append("deleted_at) ");
		query.append("VALUES (?, ?, ?, ?, ?);");

		PreparedStatement statement = null;
		try {
			//Part II: Perform the add operation
			statement = connection.prepareStatement(query.toString());
			statement.setString(1, ontologyTerm.getTerm());
			statement.setString(2, ontologyTerm.getOntologyName());
			statement.setString(3, ontologyTerm.getDescription());
			statement.setString(4, ontologyTerm.getNameSpace());
			statement.setDate(5, null);
			statement.executeUpdate();
			
			//Part III: Record changes
			/**
			ArrayList<MacawChangeEvent> changeEvents 
				= ChangeEventGenerator.addOntologyTermChange(user, ontologyTerm);

			registerChangeEvents(connection, changeEvents);
			*/
		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToAddOntologyTerm",
											ontologyTerm.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CREATE_ONTOLOGY_TERM,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, null);
		}
	}

	public void updateOntologyTerm(Connection connection,
								   User user,
								   OntologyTerm revisedOntologyTerm) throws MacawException {
		
		//Part I: Validate parameters
		//check basic field errors
		OntologyTerm.validateFields(revisedOntologyTerm);
		//check for duplicates
		checkOntologyTermExists(connection, revisedOntologyTerm);
		checkOntologyTermDuplicates(connection, revisedOntologyTerm);

		OntologyTerm originalOntologyTerm
			= getOriginalOntologyTerm(connection, revisedOntologyTerm);
		ArrayList<MacawChangeEvent> changeEvents
			= OntologyTerm.detectFieldChanges(user, 
											  originalOntologyTerm, 
											  revisedOntologyTerm);
		//check that at least one change happened
		if (changeEvents.size() == 0) {
			return;
		}

		StringBuilder query = new StringBuilder();
		query.append("UPDATE ontology_terms ");
		query.append("SET term = ?,");
		query.append("ontology_name = ?,");
		query.append("description = ?,");
		query.append("name_space= ? ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");
		
		PreparedStatement statement = null;
		try {
			//Part II: Perform the add operation
			statement = connection.prepareStatement(query.toString());
			statement.setString(1, revisedOntologyTerm.getTerm());
			statement.setString(2, revisedOntologyTerm.getOntologyName());
			statement.setString(3, revisedOntologyTerm.getDescription());
			statement.setString(4, revisedOntologyTerm.getNameSpace());
			statement.setInt(5, revisedOntologyTerm.getIdentifier());
			statement.executeUpdate();
			
			//Part III: Record changes
			/**
			 * 
			registerChangeEvents(changeEvents);
			 */
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToUpdateOntologyTerm",
											revisedOntologyTerm.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_UPDATE_ONTOLOGY_TERM,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, null);
		}
	}

	public void deleteOntologyTerms(Connection connection,
								    User user,
								    ArrayList<OntologyTerm> ontologyTermsToDelete) throws MacawException {
		
		//Part I: Validate parameters
		for (OntologyTerm currentOntologyTerm : ontologyTermsToDelete) {
			checkOntologyTermExists(connection, currentOntologyTerm);
		}
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			
			Time deletionDate = new Time(System.currentTimeMillis());
			StringBuffer query = new StringBuffer();
			query.append("UPDATE ontology_terms ");
			query.append("SET deleted_at=NOW() ");
			query.append("WHERE identifier=?;");
			statement = connection.prepareStatement(query.toString());

			//Part II: Delete term from table			
			for (OntologyTerm currentOntologyTerm : ontologyTermsToDelete) {
				//statement.setTime(1, deletionDate);
				statement.setInt(1, currentOntologyTerm.getIdentifier());
				statement.executeUpdate();
			}
			
			//Part III: Record changes
			/**
			ArrayList<MacawChangeEvent> changeEvents
				= ChangeEventGenerator.deleteOntologyTermsChange(user,
																 ontologyTermsToDelete);
			registerChangeEvents(changeEvents);
			*/			
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToDeleteOntologyTerms");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_DELETE_ONTOLOGY_TERM,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}

	public int getOntologyTermIdentifier(Connection connection,
										 OntologyTerm targetOntologyTerm) throws MacawException {
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier ");
		query.append("FROM ontology_terms ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("term=? AND name_space=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setString(1, targetOntologyTerm.getTerm());
			statement.setString(2, targetOntologyTerm.getNameSpace());
			resultSet = statement.executeQuery();
			if (resultSet.next() == true) {
				return resultSet.getInt(1);
			}
			else {
				return -1;
			}
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckOntologyTermIdentifier");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ONTOLOGY_TERM_IDENTIFIER,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}		
	}

	public void clear(Connection connection) throws MacawException {

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement("DELETE FROM ontology_terms;");
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
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}

	// ==========================================
	// Section Errors and Validation
	// ==========================================

	public void checkOntologyTermExists(Connection connection,
			   							OntologyTerm targetOntologyTerm) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT 1 ");
		query.append("FROM ontology_terms ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());
			statement.setInt(1, targetOntologyTerm.getIdentifier());
			resultSet = statement.executeQuery();
			if (resultSet.next() == false) {
				//items have same display name but different IDs. Therefore
				//the candidate is a duplicate
				String errorMessage
					= MacawMessages.getMessage("general.error.nonExistentItem",
												targetOntologyTerm.getDisplayName());
				MacawException exception
					= new MacawException(MacawErrorType.NON_EXISTENT_ONTOLOGY_TERM,
										 errorMessage);
				throw exception;
			}
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckOntologyTermExists");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_ONTOLOGY_TERM_EXISTS,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}					
	}

	private void checkOntologyTermDuplicates(Connection connection,
											 OntologyTerm targetOntologyTerm) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier ");
		query.append("FROM ontology_terms ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("term=? AND (name_space=? OR ontology_name=?);");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setString(1, targetOntologyTerm.getTerm());
			statement.setString(2, targetOntologyTerm.getNameSpace());
			statement.setString(3, targetOntologyTerm.getOntologyName());
			resultSet = statement.executeQuery();
			if (resultSet.next() == true) {
				int resultIdentifier = resultSet.getInt(1);
				if (resultIdentifier != targetOntologyTerm.getIdentifier()) {
					//items have same display name but different IDs. Therefore
					//the candidate is a duplicate
					String errorMessage
						= MacawMessages.getMessage("ontologyTerm.error.duplicateExists",
													targetOntologyTerm.getDisplayName());
					MacawException exception
						= new MacawException(MacawErrorType.DUPLICATE_ONTOLOGY_TERM,
											 errorMessage);
					throw exception;
				}
			}
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckOntologyTermDuplicates");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_ONTOLOGY_TERM_DUPLICATES,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}		
	}

	public OntologyTerm getOriginalOntologyTerm(Connection connection,
												OntologyTerm targetOntologyTerm) throws MacawException {

		
		checkOntologyTermExists(connection, targetOntologyTerm);
		
		int identifier = targetOntologyTerm.getIdentifier();
		StringBuilder query = new StringBuilder();
		query.append("SELECT term,");
		query.append("ontology_name,");
		query.append("description,");
		query.append("name_space ");
		query.append("FROM ontology_terms ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setInt(1, identifier);
			resultSet = statement.executeQuery();
			resultSet.next();
			
			OntologyTerm originalOntologyTerm = new OntologyTerm();
			originalOntologyTerm.setIdentifier(identifier);
			originalOntologyTerm.setTerm(resultSet.getString(1));
			originalOntologyTerm.setOntologyName(resultSet.getString(2));
			originalOntologyTerm.setDescription(resultSet.getString(3));
			originalOntologyTerm.setNameSpace(resultSet.getString(4));

			return originalOntologyTerm;
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetOriginalOntologyTerm");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ORIGINAL_ONTOLOGY_TERM,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}	
	}

	// ==========================================
	// Section Interfaces
	// ==========================================

	// ==========================================
	// Section Overload
	// ==========================================

}
