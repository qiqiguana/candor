package macaw.persistenceLayer.production;

import macaw.businessLayer.SupportingDocument;
import macaw.businessLayer.User;
import macaw.businessLayer.Variable;
import macaw.system.*;

import java.sql.*;
import java.util.ArrayList;

/**
 * A manager class that is called by various classes to make a record of editing changes
 * made to instances of objects found in <code>macaw.model</code>.  It stores records of 
 * these changes in a MySQL database.
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

public class SQLChangeEventManager {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private Log log;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public SQLChangeEventManager(Log log) {
		this.log = log;
	}

	public void createTable(Connection connection) throws MacawException {
		
		StringBuilder query = new StringBuilder();
		query.append("CREATE TABLE change_events (");
		query.append("identifier INT AUTO_INCREMENT NOT NULL,");
		query.append("date DATETIME NOT NULL,");
		query.append("message VARCHAR(255) NOT NULL,");
		query.append("user_id VARCHAR(255) NOT NULL,");
		query.append("owner_variable_id INT,");
		query.append("change_type VARCHAR(255) NOT NULL,");
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

	public void registerChangeEvent(Connection connection,
									MacawChangeEvent changeEvent) throws MacawException {
		ArrayList<MacawChangeEvent> changeEvents
			= new ArrayList<MacawChangeEvent>();
		changeEvents.add(changeEvent);
		registerChangeEvents(connection,
							 changeEvents);
	}
	
	public void registerChangeEvents(Connection connection,
									 ArrayList<MacawChangeEvent> changeEvents) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO change_events ");
		query.append("(date,");
		query.append("message,");
		query.append("user_id,");
		query.append("owner_variable_id,");
		query.append("change_type) ");
		query.append("VALUES (NOW(), ?, ?, ?, ?);");

		
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			
			for (MacawChangeEvent changeEvent : changeEvents) {
				java.util.Date utilDate = changeEvent.getDate();
				/*
				if (utilDate == null) {
					statement.setTime(1, null);					
				}
				else {
				}
				*/
				
				/**
				 * FIX - When I use the code below I get an error
				 * java.sql.SQLException: General error: Incorrect datetime value:
				 * '10:13:34' for column 'date' at row 1.  
				 * 
				 * I don't understand why it doesn't like the millisecond date
				 * I've provided it with the utilDate object.
				 */
				
				String changeMessage = changeEvent.getChangeMessage();
				if (changeMessage.length() > 200) {
					changeMessage = changeMessage.substring(0, 100);
				}
				statement.setString(1, changeMessage);
				statement.setString(2, changeEvent.getUserID());
				Integer ownerVariableID = changeEvent.getVariableOwnerID();
				if (ownerVariableID == null) {
					statement.setInt(3, 0);
				}
				else {
					statement.setInt(3, ownerVariableID.intValue());
				}
				ChangeEventType changeType
					= changeEvent.getChangeType();

				if (changeType == ChangeEventType.VARIABLE) {
					statement.setString(4, "variable");					
				}
				else if (changeType == ChangeEventType.VALUE_LABEL) {
					statement.setString(4, "value_label");					
				}
				else if (changeType == ChangeEventType.SUPPORTING_DOCUMENT) {
					statement.setString(4, "supporting_document");					
				}
				else {
					statement.setString(4, "list_choice");
				}
				
				statement.executeUpdate();
			}			
		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);

			String errorMessage 
				= MacawMessages.getMessage("sql.error.unableToRegisterChangeEvents");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_REGISTER_CHANGE_EVENTS,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}
	
	public ArrayList<MacawChangeEvent> getChangeHistoryForVariable(Connection connection,
																   Variable variable) throws MacawException {

		int parentVariableID = variable.getIdentifier();
																	   
		StringBuilder query = new StringBuilder();
		query.append("SELECT date, message, user_id ");
		query.append("FROM change_events ");
		query.append("WHERE owner_variable_id=? ");
		query.append("ORDER BY DATE DESC;");
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setInt(1, parentVariableID);
			resultSet = statement.executeQuery();
			return getChangeEvents(resultSet);
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.changeHistory.unableToGetChangesForVariable",
											variable.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_CHANGES_FOR_VARIABLE,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}
	
	public ArrayList<MacawChangeEvent> getChangeHistoryForSupportingDocument(Connection connection,
																			 SupportingDocument supportingDocument) throws MacawException {
						 														 
		int supportingDocumentID = supportingDocument.getIdentifier();
		StringBuilder query = new StringBuilder();
		query.append("SELECT date, message, user_id ");
		query.append("FROM change_events ");
		query.append("WHERE identifier=? AND change_type='supporting_document' ");
		query.append("ORDER BY DATE DESC;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setInt(1, supportingDocumentID);
			resultSet = statement.executeQuery();	
			return getChangeEvents(resultSet);
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.changeHistory.unableToGetChangesDocument",
											supportingDocument.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_CHANGES_FOR_DOCUMENT,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}

	public ArrayList<MacawChangeEvent> getChangeHistoryForValueLabels(Connection connection,
																	  Variable variable) throws MacawException {

		int variableID = variable.getIdentifier();
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT date, message, user_id ");
		query.append("FROM change_events ");
		query.append("WHERE owner_variable_id=? AND change_type='value_label' ");
		query.append("ORDER BY DATE DESC;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setInt(1, variableID);
			resultSet = statement.executeQuery();	
			return getChangeEvents(resultSet);
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.changeHistory.unableToGetChangesForValueLabels",
											variable.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_CHANGES_FOR_VALUE_LABEL,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}

	public ArrayList<MacawChangeEvent> getChangeHistoryForListChoices(Connection connection) throws MacawException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT date, message, user_id ");
		query.append("FROM change_events ");
		query.append("WHERE change_type='list_choice' ");
		query.append("ORDER BY DATE DESC;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			resultSet = statement.executeQuery();	
			return getChangeEvents(resultSet);
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.changeHistory.unableToGetChangesForListChoices");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_CHANGES_FOR_LIST_CHOICES,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}
	
	public ArrayList<MacawChangeEvent> getChangeHistoryForUser(Connection connection,
														   	   User user) throws MacawException {
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT date, message, user_id ");
		query.append("FROM change_events ");
		query.append("WHERE user_id=? AND change_type='variable' ");
		query.append("ORDER BY DATE DESC;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setString(1, user.getUserID());
			resultSet = statement.executeQuery();	
			return getChangeEvents(resultSet);
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.changeHistory.unableToGetChangesForUser",
											user.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_CHANGES_FOR_USER,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}
	
	private ArrayList<MacawChangeEvent> getChangeEvents(ResultSet resultSet) throws SQLException {
		ArrayList<MacawChangeEvent> changeEvents
			= new ArrayList<MacawChangeEvent>();
		while (resultSet.next() == true) {
			MacawChangeEvent changeEvent
				= new MacawChangeEvent();
			changeEvent.setDate(resultSet.getDate(1));
			changeEvent.setChangeMessage(resultSet.getString(2));
			changeEvent.setUserID(resultSet.getString(3));
			changeEvents.add(changeEvent);
		}
		return changeEvents;		
	}
	
	public void clear(Connection connection) throws MacawException {
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM change_events;");

		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
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

	public void clearAllChanges(Connection connection) throws MacawException {
		clear(connection);
	}
	
	public ArrayList<MacawChangeEvent> getAllChanges(Connection connection) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT date, message, user_id ");
		query.append("FROM change_events ");
		query.append("ORDER BY DATE DESC;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			resultSet = statement.executeQuery();	
			return getChangeEvents(resultSet);
		}
		catch(SQLException exception) {
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

	// ==========================================
	// Section Interfaces
	// ==========================================

	// ==========================================
	// Section Overload
	// ==========================================

}

