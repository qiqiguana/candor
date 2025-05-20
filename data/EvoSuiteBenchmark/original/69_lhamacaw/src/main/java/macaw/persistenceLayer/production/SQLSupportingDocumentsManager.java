package macaw.persistenceLayer.production;


import macaw.businessLayer.*;
import macaw.persistenceLayer.ChangeEventGenerator;
import macaw.system.MacawChangeEvent;
import macaw.system.MacawErrorType;
import macaw.system.MacawException;
import macaw.system.MacawMessages;

import java.sql.*;
import java.util.ArrayList;

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

public class SQLSupportingDocumentsManager extends SQLCurationConceptManager {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	
	// ==========================================
	// Section Construction
	// ==========================================
	public SQLSupportingDocumentsManager(SQLChangeEventManager changeEventManager) {
		super(changeEventManager);
	}
	
	public void createTable(Connection connection) throws MacawException {
		StringBuilder query = new StringBuilder();
		query.append("CREATE TABLE supporting_documents (");
		query.append("identifier INT AUTO_INCREMENT NOT NULL,");
		query.append("title VARCHAR(255) NOT NULL,");
		query.append("document_code VARCHAR(255) NOT NULL,");
		query.append("description VARCHAR(255),");
		query.append("file_name VARCHAR(255),");
		query.append("file_path VARCHAR(255),");
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
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	public ArrayList<SupportingDocument> getAllSupportingDocuments(Connection connection,
																   User user) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier,");
		query.append("title,");
		query.append("document_code,");		
		query.append("description,");
		query.append("file_name,");
		query.append("file_path ");
		query.append("FROM supporting_documents ");
		query.append("WHERE deleted_at IS NULL ");
		query.append("ORDER BY title ASC;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			ArrayList<SupportingDocument> results 
				= new ArrayList<SupportingDocument>();
			statement
				= connection.prepareStatement(query.toString());
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
				= MacawMessages.getMessage("sql.error.unableToGetAllSupportingDocuments");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ALL_SUPPORTING_DOCUMENTS,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}



	public void addSupportingDocument(Connection connection,
									  User user,
									  SupportingDocument supportingDocument) throws MacawException {


		//Part I: Validate parameters
		//check basic field errors
		SupportingDocument.validateFields(supportingDocument);	
		//check for duplicates
		checkSupportingDocumentCodeDuplicates(connection, supportingDocument);
		checkSupportingDocumentDuplicates(connection, supportingDocument);

		//Part II: Perform the add supporting document operation
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO supporting_documents ");
		query.append("(title,");
		query.append("document_code,");
		query.append("description,");
		query.append("file_name,");
		query.append("file_path,");
		query.append("deleted_at) ");
		query.append("VALUES (?, ?, ?, ?, ?, ?);");

		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setString(1, supportingDocument.getTitle());
			statement.setString(2, supportingDocument.getDocumentCode());
			statement.setString(3, supportingDocument.getDescription());
			statement.setString(4, supportingDocument.getFileName());
			statement.setString(5, supportingDocument.getFilePath());
			statement.setDate(6, null);
			
			statement.executeUpdate();	
			
			//Part III: Record changes
			ArrayList<MacawChangeEvent> changeEvents
				= ChangeEventGenerator.addSupportingDocumentChange(user, 
																   supportingDocument);
			registerChangeEvents(connection, changeEvents);
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToAddSupportingDocument",
											supportingDocument.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CREATE_SUPPORTING_DOCUMENT,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, null);
		}
	}

	public void updateSupportingDocument(Connection connection,
										 User user,
										 SupportingDocument revisedSupportingDocument) throws MacawException {

		//Part I: Validate parameters
		//check that document exists
		checkSupportingDocumentExists(connection,
									  revisedSupportingDocument);
		//check basic field errors
		SupportingDocument.validateFields(revisedSupportingDocument);		
		//check for duplicates
		checkSupportingDocumentCodeDuplicates(connection,
											  revisedSupportingDocument);
		checkSupportingDocumentDuplicates(connection,
										  revisedSupportingDocument);

		//check that at least one change was made
		SupportingDocument originalSupportingDocument
			= getOriginalSupportingDocument(connection,
											revisedSupportingDocument);
		ArrayList<MacawChangeEvent> changeEvents
			= SupportingDocument.detectFieldChanges(user,
													originalSupportingDocument, 
													revisedSupportingDocument);	
		if (changeEvents.size() == 0) {
			return;
		}
		
		//Part II: Perform update operation
		StringBuilder query = new StringBuilder();
		query.append("UPDATE supporting_documents ");
		query.append("SET title = ?,");
		query.append("document_code = ?,");
		query.append("description = ?,");
		query.append("file_name= ?,");
		query.append("file_path= ? ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");
		
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setString(1, revisedSupportingDocument.getTitle());
			statement.setString(2, revisedSupportingDocument.getDocumentCode());
			statement.setString(3, revisedSupportingDocument.getDescription());
			statement.setString(4, revisedSupportingDocument.getFileName());
			statement.setString(5, revisedSupportingDocument.getFilePath());
			statement.setInt(6, revisedSupportingDocument.getIdentifier());
			statement.executeUpdate();
			
			//Part III: Record changes
			registerChangeEvents(connection, changeEvents);
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToUpdateSupportingDocument",
											revisedSupportingDocument.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_UPDATE_SUPPORTING_DOCUMENT,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, null);
		}	
	}

	public void deleteSupportingDocuments(Connection connection,
										  User user,
										  ArrayList<SupportingDocument> supportingDocumentsToDelete) throws MacawException {
		
		//Part I: Validate parameters
		//verify that supporting documents are associated with variable
		for (SupportingDocument supportingDocument : supportingDocumentsToDelete) {
			checkSupportingDocumentExists(connection,
										  supportingDocument);
		}

		//Part II: Perform the delete operation
		StringBuilder query = new StringBuilder();
		query.append("UPDATE supporting_documents ");
		query.append("SET deleted_at=NOW() ");
		query.append("WHERE identifier=?;");

		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());

			Time deletionDate = new Time(System.currentTimeMillis());
			for (SupportingDocument currentSupportingDocument : supportingDocumentsToDelete) {
				//statement.setTime(1, deletionDate);
				statement.setInt(1, currentSupportingDocument.getIdentifier());
				statement.executeUpdate();		
			}		
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToDeleteSupportingDocuments");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_DELETE_SUPPORTING_DOCUMENT,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, null);
		}			
	}

	public int getSupportingDocumentIdentifier(Connection connection,
			   								   SupportingDocument supportingDocument) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier ");
		query.append("FROM supporting_documents ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("title=? AND document_code=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setString(1, supportingDocument.getTitle());
			statement.setString(2, supportingDocument.getDocumentCode());
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
				= MacawMessages.getMessage("sql.error.unableToGetSupportingDocumentIdentifier");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_SUPPORTING_DOCUMENT_IDENTIFIER,
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
				= connection.prepareStatement("DELETE FROM supporting_documents;");
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
	// Section Errors and Validation
	// ==========================================
	public void checkSupportingDocumentExists(Connection connection,
											  SupportingDocument targetSupportingDocument) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT 1 ");
		query.append("FROM supporting_documents ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setInt(1, targetSupportingDocument.getIdentifier());
			resultSet = statement.executeQuery();
			if (resultSet.next() == false) {
				//items have same display name but different IDs. Therefore
				//the candidate is a duplicate
				String errorMessage
					= MacawMessages.getMessage("general.error.nonExistentItem",
												targetSupportingDocument.getDisplayName());
				MacawException exception
					= new MacawException(MacawErrorType.NON_EXISTENT_SUPPORTING_DOCUMENT,
										 errorMessage);
				throw exception;
			}
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckSupportingDocumentExists");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_DOCUMENT_EXISTS,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}					
	}

	private void checkSupportingDocumentDuplicates(Connection connection,
			   								   	   SupportingDocument candidateSupportingDocument) throws MacawException {

		
		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier ");
		query.append("FROM supporting_documents ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("title=? AND document_code=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());
			statement.setString(1, candidateSupportingDocument.getTitle());
			statement.setString(2, candidateSupportingDocument.getDocumentCode());
			resultSet = statement.executeQuery();
			if (resultSet.next() == true) {
				int resultIdentifier = resultSet.getInt(1);
				if (resultIdentifier != candidateSupportingDocument.getIdentifier()) {
					//items have same display name but different IDs. Therefore
					//the candidate is a duplicate
					String errorMessage
						= MacawMessages.getMessage("valueLabel.error.duplicateExists",
													candidateSupportingDocument.getDisplayName());
					MacawException exception
						= new MacawException(MacawErrorType.DUPLICATE_VALUE_LABEL,
											 errorMessage);
					throw exception;
				}
			}
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckDocumentDuplicates");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_DOCUMENT_DUPLICATES,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}		
	}

	private void checkSupportingDocumentCodeDuplicates(Connection connection,
			  										   SupportingDocument supportingDocument) throws MacawException {
	
		StringBuilder query = new StringBuilder();

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			query.append("SELECT identifier ");
			query.append("FROM supporting_documents ");
			query.append("WHERE deleted_at IS NULL AND ");
			query.append("document_code=?;");
			statement
				= connection.prepareStatement(query.toString());
			statement.setString(1, supportingDocument.getDocumentCode());
			resultSet = statement.executeQuery();
			if (resultSet.next() == true) {
				int resultIdentifier = resultSet.getInt(1);
				if (resultIdentifier != supportingDocument.getIdentifier()) {				
					//items have same display name but different IDs. Therefore
					//the candidate is a duplicate
					String errorMessage
						= MacawMessages.getMessage("supportingDocument.error.duplicateDocumentCodeExists",
													supportingDocument.getDocumentCode());
					MacawException exception 
						= new MacawException(MacawErrorType.DUPLICATE_SUPPORTING_DOCUMENT_CODE,
											errorMessage);
					throw exception;
				}
			}
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckDocumentCodeDuplicates");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_DOCUMENT_DUPLICATES,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}		
	}

	public SupportingDocument getOriginalSupportingDocument(Connection connection,
															SupportingDocument targetSupportingDocument) throws MacawException {

		
		checkSupportingDocumentExists(connection,
				   					  targetSupportingDocument);

		int identifier = targetSupportingDocument.getIdentifier();
		StringBuilder query = new StringBuilder();
		query.append("SELECT title,");
		query.append("document_code,");
		query.append("description,");
		query.append("file_name,");
		query.append("file_path ");
		query.append("FROM supporting_documents ");
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
			SupportingDocument originalSupportingDocument
				= new SupportingDocument();
			originalSupportingDocument.setIdentifier(identifier);
			originalSupportingDocument.setTitle(resultSet.getString(1));
			originalSupportingDocument.setDescription(resultSet.getString(2));
			originalSupportingDocument.setTitle(resultSet.getString(3));
			originalSupportingDocument.setTitle(resultSet.getString(4));
			originalSupportingDocument.setTitle(resultSet.getString(5));
			return originalSupportingDocument;
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetOriginalDocument");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ORIGINAL_SUPPORTING_DOCUMENT,
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

