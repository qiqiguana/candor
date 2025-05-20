package macaw.persistenceLayer.production;

import macaw.businessLayer.*;
import macaw.businessLayer.ValueLabel.EditingOperationType;
import macaw.persistenceLayer.ChangeEventGenerator;
import macaw.system.*;

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

public class SQLValueLabelManager extends SQLCurationConceptManager {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	
	// ==========================================
	// Section Construction
	// ==========================================
	public SQLValueLabelManager(SQLChangeEventManager changeEventManager) {
		super(changeEventManager);
	}
	
	public void createTable(Connection connection) throws MacawException {
		StringBuilder query = new StringBuilder();
		query.append("CREATE TABLE value_labels (");
		query.append("identifier INT AUTO_INCREMENT NOT NULL,");
		query.append("value VARCHAR(255) NOT NULL,");
		query.append("label VARCHAR(255),");
		query.append("is_missing_value BOOLEAN NOT NULL,");
		query.append("variable_id INT NOT NULL,");
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

	public ArrayList<ValueLabel> getValueLabels(Connection connection,
												User user,
												String variableName) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT value_labels.identifier,");
		query.append("value_labels.value,");
		query.append("value_labels.label,");
		query.append("value_labels.is_missing_value ");
		query.append("FROM variables,value_labels ");
		query.append("WHERE value_labels.deleted_at IS NULL AND ");
		query.append("variables.deleted_at IS NULL AND ");
		query.append("variables.identifier=value_labels.variable_id AND ");
		query.append("variables.name=?");
		query.append(" ORDER BY value ASC;");

		ArrayList<ValueLabel> results = new ArrayList<ValueLabel>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setString(1, variableName);
			resultSet = statement.executeQuery();
			while (resultSet.next() == true) {
				ValueLabel valueLabel = new ValueLabel();
				valueLabel.setIdentifier(resultSet.getInt(1));
				valueLabel.setValue(resultSet.getString(2));
				valueLabel.setLabel(resultSet.getString(3));
				valueLabel.setMissingValue(resultSet.getBoolean(4));
				valueLabel.setEditingOperationType(EditingOperationType.EDIT);
				results.add(valueLabel);
			}
			return results;
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetValueLabels",
						variableName);
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_VALUE_LABELS,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}
	
	public ArrayList<ValueLabel> getValueLabels(Connection connection,
			   				   				    User user,
			   				   				    Variable variable) throws MacawException {

		
		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier,");
		query.append("value,");
		query.append("label,");
		query.append("is_missing_value ");
		query.append("FROM value_labels ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("variable_id=? ");
		query.append(" ORDER BY value ASC;");
		
		ArrayList<ValueLabel> results = new ArrayList<ValueLabel>();
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setInt(1, variable.getIdentifier());
			resultSet = statement.executeQuery();
			while (resultSet.next() == true) {
				ValueLabel valueLabel = new ValueLabel();
				valueLabel.setIdentifier(resultSet.getInt(1));
				valueLabel.setValue(resultSet.getString(2));
				valueLabel.setLabel(resultSet.getString(3));
				valueLabel.setMissingValue(resultSet.getBoolean(4));
				valueLabel.setEditingOperationType(EditingOperationType.EDIT);
				variable.addValueLabel(valueLabel);
				results.add(valueLabel);
			}
			return results;
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetValueLabels",
										   variable.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_VALUE_LABELS,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}
	
	public void addValueLabels(Connection connection,
							   User user,
							   Variable variable,
		   	 				   ArrayList<ValueLabel> valueLabels) throws MacawException {
		
		//Part I: Validate parameters

		//check that there are no duplicates within the list of revised labels
		//they may be unique with respect to values already in the DB but
		//they may contain some duplicate values

		//checkValueLabelDuplicateWithinList(valueLabels);
		
		for (ValueLabel revisedValueLabel : valueLabels) {
			ValueLabel.validateFields(revisedValueLabel);			
			/*
			checkValueLabelDuplicate(connection,
									 variable, 
									 revisedValueLabel);
			*/
		}
		
		//Part II: Perform the add value labels operation
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO value_labels ");
		query.append("(value, label, is_missing_value, variable_id, deleted_at) ");
		query.append("VALUES (?, ?, ?, ?, ?);");
		
		PreparedStatement statement = null;
		try {
			int variableID = variable.getIdentifier();
			for (ValueLabel valueLabel : valueLabels) {
				statement
					= connection.prepareStatement(query.toString());
				statement.setString(1, valueLabel.getValue());
				statement.setString(2, valueLabel.getLabel());
				if (valueLabel.isMissingValue() == true) {
					statement.setInt(3, 1);					
				}
				else {
					statement.setInt(3, 0);										
				}
				statement.setInt(4, variableID);
				statement.setDate(5, null);
				statement.executeUpdate();
			}

			ArrayList<MacawChangeEvent> changeEvents
				= ChangeEventGenerator.addValueLabelsChange(user,
															variable,
															valueLabels);
			registerChangeEvents(connection, changeEvents);

		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToAddValueLabels",
											variable.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_UPDATE_VALUE_LABELS,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, null);
		}
	}

	public void updateValueLabels(Connection connection,
								  User user,
								  Variable variable,
								  ArrayList<ValueLabel> revisedValueLabels) throws MacawException {

		//Part I: Validate parameters
		//check that there are no duplicates within the list of revised labels
		//they may be unique with respect to values already in the DB but
		//they may contain some duplicate values
		for (ValueLabel revisedValueLabel : revisedValueLabels) {
			ValueLabel.validateFields(revisedValueLabel);
		}

		//checkValueLabelDuplicateWithinList(revisedValueLabels);
		
		for (ValueLabel revisedValueLabel : revisedValueLabels) {
			ValueLabel.validateFields(revisedValueLabel);
			checkValueLabelExists(connection, 
								  revisedValueLabel);
			/*
			checkValueLabelDuplicate(connection,
									 variable, 
									 revisedValueLabel);
			*/
		}
		
		//Part II: Perform the update value labels operation
		StringBuilder query = new StringBuilder();
		query.append("UPDATE value_labels ");
		query.append("SET value=?,");
		query.append("label=?,");
		query.append("is_missing_value=? ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=? AND variable_id=?;");
		
		PreparedStatement statement = null;
		try {
			int variableID = variable.getIdentifier();
			statement
				= connection.prepareStatement(query.toString());

			for (ValueLabel valueLabel : revisedValueLabels) {
				statement.setString(1, valueLabel.getValue());
				statement.setString(2, valueLabel.getLabel());
				if (valueLabel.isMissingValue() == true) {
					statement.setInt(3, 1);					
				}
				else {
					statement.setInt(3, 0);					
				}
				statement.setInt(4, valueLabel.getIdentifier());
				statement.setInt(5, variableID);
				statement.executeUpdate();				
			}
			
			//Part II: Perform the update operations
			ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
			for (ValueLabel revisedValueLabel : revisedValueLabels) {			
				ValueLabel originalValueLabel
					= getOriginalValueLabel(connection,											
											revisedValueLabel);		
				ArrayList<MacawChangeEvent> changeEventsForValueLabel
					= ValueLabel.detectFieldChanges(user, 
													variable, 
													originalValueLabel, 
													revisedValueLabel);
				changeEvents.addAll(changeEventsForValueLabel);
			}

			//Part III: record changes
			registerChangeEvents(connection, changeEvents);			
		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToUpdateValueLabels",
											variable.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_UPDATE_VALUE_LABELS,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, null);
		}
	}

	public void deleteValueLabels(Connection connection,
								  User user,
								  Variable variable,
								  ArrayList<ValueLabel> valueLabels) throws MacawException {

		//Part I: Validate Parameters
		for (ValueLabel currentValueLabel : valueLabels) {
			checkValueLabelExists(connection,
								  currentValueLabel);
		}
		
		//Part II: Perform the delete values operation
		StringBuilder query = new StringBuilder();
		query.append("UPDATE value_labels ");
		query.append("SET deleted_at=NOW() ");
		query.append("WHERE identifier=? AND variable_id=?;");
		
		PreparedStatement statement = null;
		try {
			
			Time deletionDate = new Time(System.currentTimeMillis());
			
			statement
				= connection.prepareStatement(query.toString());
			int variableID = variable.getIdentifier();
			for (ValueLabel valueLabel : valueLabels) {
				//statement.setTime(1, deletionDate);
				statement.setInt(1, valueLabel.getIdentifier());
				statement.setInt(2, variableID);
				statement.executeUpdate();				
			}
			
			//Part III: Record changes
			ArrayList<MacawChangeEvent> changeEvents
				= ChangeEventGenerator.deleteValueLabelsChange(user,
														 	   variable,
														 	   valueLabels);
			registerChangeEvents(connection, 
								 changeEvents);
		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToDeleteValueLabels",
										   variable.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_DELETE_VALUE_LABELS,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, null);
		}
	}

	public ValueLabel getOriginalValueLabel(Connection connection,
											ValueLabel targetValueLabel) throws MacawException {

		int identifier = targetValueLabel.getIdentifier();
		
		checkValueLabelExists(connection,
							  targetValueLabel);

		StringBuilder query = new StringBuilder();
		query.append("SELECT value,");
		query.append("label,");
		query.append("is_missing_value ");
		query.append("FROM value_labels ");	
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
			ValueLabel originalValueLabel = new ValueLabel();
			originalValueLabel.setIdentifier(identifier);
			originalValueLabel.setValue(resultSet.getString(1));
			originalValueLabel.setLabel(resultSet.getString(2));
			originalValueLabel.setMissingValue(resultSet.getBoolean(3));			
			return originalValueLabel;
			
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetOriginalValueLabel",
											targetValueLabel.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ORIGINAL_VALUE_LABEL,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}		
	}
	
	
	public int getValueLabelIdentifier(Connection connection,
			 						   Variable variable,
			 						   ValueLabel candidateValueLabel) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier ");
		query.append("FROM value_labels ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("variable_id=? AND value=? AND label=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setInt(1, variable.getIdentifier());
			statement.setString(2, candidateValueLabel.getValue());
			statement.setString(3, candidateValueLabel.getLabel());

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
				= MacawMessages.getMessage("sql.error.unableToGetValueLabelIdentifier");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_VALUE_LABEL_IDENTIFIER,
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
				= connection.prepareStatement("DELETE FROM value_labels;");
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

	/**
	public void checkValueLabelDuplicate(Connection connection,
										 Variable variable,
			   							 ValueLabel candidateValueLabel) throws MacawException {

		
		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier ");
		query.append("FROM value_labels ");
		query.append("WHERE variable_id=? AND (value=? OR label=?);");

		//TO DO
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setInt(1, variable.getIdentifier());
			statement.setString(2, candidateValueLabel.getValue());
			statement.setString(3, candidateValueLabel.getLabel());
		
			resultSet = statement.executeQuery();
			if (resultSet.next() == true) {
				int resultIdentifier = resultSet.getInt(1);
				if (resultIdentifier != candidateValueLabel.getIdentifier()) {
					//items have same display name but different IDs. Therefore
					//the candidate is a duplicate
					String errorMessage
						= MacawMessages.getMessage("valueLabel.error.duplicateExists",
													candidateValueLabel.getDisplayName());
					MacawException exception
						= new MacawException(MacawErrorType.DUPLICATE_VALUE_LABEL,
											 errorMessage);
					throw exception;
				}
			}
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckValueLabelDuplicates");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_VALUE_LABEL_DUPLICATES,
									 errorMessage);
			throw macawException;
		}
		finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
			}
			catch(SQLException exception) {
				String errorMessage
					= MacawMessages.getMessage("sql.error.unableToCloseConnection");
				MacawException macawException 
					= new MacawException(MacawErrorType.UNABLE_TO_CLOSE_CONNECTION,
									 	 errorMessage);
				throw macawException;
			}
		}
	}
	 */
	public void checkValueLabelExists(Connection connection,
				 					  ValueLabel candidateValueLabel) throws MacawException {

		
		StringBuilder query = new StringBuilder();
		query.append("SELECT 1 ");
		query.append("FROM value_labels ");
		query.append("WHERE deleted_at IS NULL AND ");
		query.append("identifier=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setInt(1, candidateValueLabel.getIdentifier());			
			resultSet = statement.executeQuery();
			if (resultSet.next() == false) {
				//items have same display name but different IDs. Therefore
				//the candidate is a duplicate
				String errorMessage
					= MacawMessages.getMessage("general.error.nonExistentItem",
												candidateValueLabel.getDisplayName());
				MacawException exception
					= new MacawException(MacawErrorType.NON_EXISTENT_VALUE_LABELS,
										 errorMessage);
				throw exception;
			}
		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCheckValueLabelDuplicates");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_VALUE_LABEL_DUPLICATES,
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

