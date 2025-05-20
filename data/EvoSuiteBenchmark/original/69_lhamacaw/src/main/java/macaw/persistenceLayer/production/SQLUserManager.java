package macaw.persistenceLayer.production;

import macaw.businessLayer.MacawSecurityAPI;
import macaw.businessLayer.User;
import macaw.persistenceLayer.ChangeEventGenerator;
import macaw.system.*;

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

public class SQLUserManager extends SQLCurationConceptManager implements MacawSecurityAPI {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private String verified;
	private String unverified;
	private SQLConnectionManager sqlConnectionManager;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public SQLUserManager(SQLChangeEventManager changeEventManager,
						  SQLConnectionManager sqlConnectionManager) {
		super(changeEventManager);
		this.sqlConnectionManager = sqlConnectionManager;
	}
	
	public void createTable(Connection connection) throws MacawException {
		StringBuilder query = new StringBuilder();
		query.append("CREATE TABLE users (");
		query.append("identifier INT AUTO_INCREMENT NOT NULL,");
		query.append("user_id VARCHAR(255) NOT NULL,");
		query.append("first_name VARCHAR(255) NOT NULL,");
		query.append("last_name VARCHAR(255) NOT NULL,");
		query.append("affiliation VARCHAR(255) NOT NULL,");
		query.append("address_line1 VARCHAR(255) NOT NULL,");
		query.append("address_line2 VARCHAR(255) NOT NULL,");
		query.append("city VARCHAR(255) NOT NULL,");
		query.append("county VARCHAR(255) NOT NULL,");
		query.append("post_code VARCHAR(255) NOT NULL,");
		query.append("phone VARCHAR(255) NOT NULL,");
		query.append("email VARCHAR(255) NOT NULL,");
		query.append("status VARCHAR(255) NOT NULL,");
		query.append("password VARCHAR(255) NOT NULL,");
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
	
	public void validateAdministrator(User admin) throws MacawException {
		Connection connection = getConnection();
		StringBuilder query = new StringBuilder();
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToValidateAdministrator",
											admin.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_VALIDATE_ADMINISTRATOR,
									 errorMessage);
			throw macawException;
		}
		finally {
			releaseConnection(connection);
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}
	
	
	public void validateUser(User user) throws MacawException {

		/**
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToValidateUser",
											user.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_VALIDATE_USER,
									 errorMessage);
			throw macawException;
		}	
		*/
	}
	
	
	public ArrayList<User> getUsers(User admin) throws MacawException {

		//Part I: Validate parameters
		validateAdministrator(admin);

		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier,");
		query.append("user_id,");
		query.append("first_name,");
		query.append("last_name,");
		query.append("affiliation,");
		query.append("address_line1,");
		query.append("address_line2,");
		query.append("city,");
		query.append("county,");
		query.append("post_code,");
		query.append("phone,");
		query.append("email,");
		query.append("status,");
		query.append("password ");
		query.append("FROM users ");
		query.append("ORDER BY user_id ASC;");

		Connection connection = getConnection();
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			ArrayList<User> results = new ArrayList<User>();
			statement
				= connection.prepareStatement(query.toString());			
			resultSet = statement.executeQuery();
			while(resultSet.next() == true) {
				User user = new User();
				user.setIdentifier(resultSet.getInt(1));
				user.setUserID(resultSet.getString(2));
				user.setFirstName(resultSet.getString(3));
				user.setLastName(resultSet.getString(4));
				user.setAffiliation(resultSet.getString(5));
				user.setAddressLine1(resultSet.getString(6));
				user.setAddressLine2(resultSet.getString(7));
				user.setCity(resultSet.getString(8));
				user.setCounty(resultSet.getString(9));
				user.setPostCode(resultSet.getString(10));
				user.setPhone(resultSet.getString(11));
				user.setEmail(resultSet.getString(12));
				user.setStatus(resultSet.getString(13));
				user.setPassword(resultSet.getString(14));	
				results.add(user);
			}
			return results;
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetUsers");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_USERS,
									 errorMessage);
			throw macawException;
		}
		finally {
			releaseConnection(connection);
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}		
	}
	
	public User getUserFromEmail(Connection connection,
								 User user,
								 String email) throws MacawException {
		
		validateUser(user);
		User.checkValidEmail(email);
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier,");
		query.append("user_id,");
		query.append("first_name,");
		query.append("last_name,");
		query.append("affiliation,");
		query.append("address_line1,");
		query.append("address_line2,");
		query.append("city,");
		query.append("county,");
		query.append("post_code,");
		query.append("phone,");
		query.append("status,");
		query.append("password ");
		query.append("FROM users ");
		query.append("WHERE email=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			User result = new User();
			statement
				= connection.prepareStatement(query.toString());			
			statement.setString(1, email);
			resultSet = statement.executeQuery();
			if (resultSet.next() == true) {
				User userForEmail = new User();
				userForEmail.setIdentifier(resultSet.getInt(1));
				userForEmail.setUserID(resultSet.getString(2));
				userForEmail.setFirstName(resultSet.getString(3));
				userForEmail.setLastName(resultSet.getString(4));
				userForEmail.setAffiliation(resultSet.getString(5));
				userForEmail.setAddressLine1(resultSet.getString(6));
				userForEmail.setAddressLine2(resultSet.getString(7));
				userForEmail.setCity(resultSet.getString(8));
				userForEmail.setCounty(resultSet.getString(9));
				userForEmail.setPostCode(resultSet.getString(10));
				userForEmail.setPhone(resultSet.getString(11));
				userForEmail.setStatus(resultSet.getString(12));
				userForEmail.setPassword(resultSet.getString(13));
				return userForEmail;
			}
			else {
				return null;
			}			
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetUsers");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_USERS,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}		
	}

	public User getUserFromID(Connection connection,
							  User user,
							  String userID) throws MacawException {

		User.checkValidUserIDField(userID);
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier,");
		query.append("first_name,");
		query.append("last_name,");
		query.append("affiliation,");
		query.append("address_line1,");
		query.append("address_line2,");
		query.append("city,");
		query.append("county,");
		query.append("post_code,");
		query.append("phone,");
		query.append("email,");
		query.append("status,");
		query.append("password ");
		query.append("FROM users ");
		query.append("WHERE user_id=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			User result = new User();
			statement
				= connection.prepareStatement(query.toString());			
			statement.setString(1, userID);
			resultSet = statement.executeQuery();
			if (resultSet.next() == true) {
				User userForEmail = new User();
				userForEmail.setIdentifier(resultSet.getInt(1));
				userForEmail.setFirstName(resultSet.getString(2));
				userForEmail.setLastName(resultSet.getString(3));
				userForEmail.setAffiliation(resultSet.getString(4));
				userForEmail.setAddressLine1(resultSet.getString(5));
				userForEmail.setAddressLine2(resultSet.getString(6));
				userForEmail.setCity(resultSet.getString(7));
				userForEmail.setCounty(resultSet.getString(8));
				userForEmail.setPostCode(resultSet.getString(9));
				userForEmail.setPhone(resultSet.getString(10));
				userForEmail.setEmail(resultSet.getString(11));
				userForEmail.setStatus(resultSet.getString(12));
				userForEmail.setPassword(resultSet.getString(13));
				return userForEmail;
			}
			else {
				return null;
			}			
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetUsers");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_USERS,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}		
	}
	
	public ArrayList<User> getUnverifiedUsers(Connection connection,
											  User admin) throws MacawException {
	
		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier,");
		query.append("user_id,");
		query.append("first_name,");
		query.append("last_name,");
		query.append("affiliation,");
		query.append("address_line1,");
		query.append("address_line2,");
		query.append("city,");
		query.append("county,");
		query.append("post_code,");
		query.append("phone,");
		query.append("email,");
		query.append("password ");
		query.append("FROM users ");
		query.append("WHERE status=?;");

		ArrayList<User> unverifiedUsers = new ArrayList<User>();
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			User result = new User();
			statement
				= connection.prepareStatement(query.toString());			
			
			String unverifiedValue
				= MacawMessages.getMessage("user.status.unverified");
			statement.setString(1, unverifiedValue);
			
			resultSet = statement.executeQuery();
			while (resultSet.next() == true) {
				User unverifiedUser = new User();
				unverifiedUser.setIdentifier(resultSet.getInt(1));
				unverifiedUser.setUserID(resultSet.getString(2));
				unverifiedUser.setFirstName(resultSet.getString(3));
				unverifiedUser.setLastName(resultSet.getString(4));
				unverifiedUser.setAffiliation(resultSet.getString(5));
				unverifiedUser.setAddressLine1(resultSet.getString(6));
				unverifiedUser.setAddressLine2(resultSet.getString(7));
				unverifiedUser.setCity(resultSet.getString(8));
				unverifiedUser.setCounty(resultSet.getString(9));
				unverifiedUser.setPostCode(resultSet.getString(10));
				unverifiedUser.setPhone(resultSet.getString(11));
				unverifiedUser.setEmail(resultSet.getString(12));
				unverifiedUser.setPassword(resultSet.getString(13));

				unverifiedUsers.add(unverifiedUser);
			}
			
			return unverifiedUsers;
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetUsers");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_USERS,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}		
	}
		
	public void addUser(Connection connection,
						User admin,
						User user) throws MacawException {

		//Part I: Validate parameters
		User.validateFields(user);
		
		checkUserDuplicates(connection, user);

		StringBuilder query = new StringBuilder();
		query.append("INSERT into users ");
		query.append("(user_id,");
		query.append("first_name,");
		query.append("last_name,");
		query.append("affiliation,");
		query.append("address_line1,");
		query.append("address_line2,");
		query.append("city,");
		query.append("county,");
		query.append("post_code,");
		query.append("phone,");
		query.append("email,");
		query.append("status,");
		query.append("password) ");
		query.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
				
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setString(1, user.getUserID());
			statement.setString(2, user.getFirstName());
			statement.setString(3, user.getLastName());
			statement.setString(4, user.getAffiliation());
			statement.setString(5, user.getAddressLine1());
			statement.setString(6, user.getAddressLine2());
			statement.setString(7, user.getCity());
			statement.setString(8, user.getCounty());
			statement.setString(9, user.getPostCode());
			statement.setString(10, user.getPhone());
			statement.setString(11, user.getEmail());
			statement.setString(12, user.getStatus());
			statement.setString(13, user.getPassword());	
			statement.executeUpdate();
			
			ArrayList<MacawChangeEvent> changeEvents
				= ChangeEventGenerator.addUserChange(admin, 
													 user);
			registerChangeEvents(connection, changeEvents);
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToAddUser",
											user.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_CREATE_USER,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, null);
		}
	}
	
	public void updateUser(Connection connection,
						   User admin,
						   User revisedUser) throws MacawException {

		//Part I: Validate parameters
		User.validateFields(revisedUser);
		checkUserExists(connection, revisedUser);

		User originalUser
			= getOriginalUser(connection, revisedUser);
		ArrayList<MacawChangeEvent> changeEvents
			= User.detectFieldChanges(admin, originalUser, revisedUser);
		
		if (changeEvents.size() == 0) {
			//no changes to commit
			return;
		}

		StringBuilder query = new StringBuilder();
		query.append("UPDATE users ");
		query.append("SET user_id = ?,");
		query.append("first_name=?,");
		query.append("last_name=?,");		
		query.append("affiliation=?,");		
		query.append("address_line1=?,");		
		query.append("address_line2=?,");		
		query.append("city=?,");
		query.append("county=?,");
		query.append("post_code=?,");
		query.append("phone=?,");
		query.append("email=?,");
		query.append("status=?,");
		query.append("password=? ");
		query.append("WHERE identifier=?;");

		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());			
			statement.setString(1, revisedUser.getUserID());
			statement.setString(2, revisedUser.getFirstName());
			statement.setString(3, revisedUser.getLastName());
			statement.setString(4, revisedUser.getAffiliation());
			statement.setString(5, revisedUser.getAddressLine1());
			statement.setString(6, revisedUser.getAddressLine2());
			statement.setString(7, revisedUser.getCity());
			statement.setString(8, revisedUser.getCounty());
			statement.setString(9, revisedUser.getPostCode());
			statement.setString(10, revisedUser.getPhone());
			statement.setString(11, revisedUser.getEmail());
			statement.setString(12, revisedUser.getStatus());
			statement.setString(13, revisedUser.getPassword());	
			statement.setInt(14, revisedUser.getIdentifier());
			statement.executeUpdate();	
		
			registerChangeEvents(connection, changeEvents);
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToUpdateUser",
											revisedUser.getDisplayName());
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_UPDATE_USER,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, null);
		}
	}

	public void deleteUsers(Connection connection,
							User admin,
							ArrayList<User> usersToDelete) throws MacawException {

		//Part I: Validate parameters
		for (User userToDelete : usersToDelete) {
			checkUserExists(connection, userToDelete);
		}

		//Part II: Perform the delete users operation
		for (User userToDelete : usersToDelete) {
			deleteUser(connection, admin, userToDelete);
		}
		
		//Part III: Record changes
		ArrayList<MacawChangeEvent> changeEvents
			= ChangeEventGenerator.deleteUsersChanges(admin, usersToDelete);
		registerChangeEvents(connection, changeEvents);		
	}

	private void deleteUser(Connection connection,
							User admin,
							User userToDelete) throws MacawException {
		
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM users ");
		query.append("WHERE identifier=?;");
		
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setInt(1, userToDelete.getIdentifier());
			statement.executeUpdate();				
		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToDeleteUsers");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_DELETE_USER,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, null);
		}
	}

	public int getUserIdentifier(Connection connection,
								 User admin,
		 	 					 User user) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier ");
		query.append("FROM users ");
		query.append("WHERE user_id=?;");
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setString(1, user.getUserID());
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
				= MacawMessages.getMessage("sql.error.unableToGetUserIdentifier",
											user.getDisplayName());
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_GET_USER_IDENTIFIER,
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
				= connection.prepareStatement("DELETE FROM users;");
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


	public User getOriginalUser(Connection connection,
								User targetUser) throws MacawException {


		checkUserExists(connection, targetUser);
		int identifier = targetUser.getIdentifier();
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT user_id,");
		query.append("first_name,");
		query.append("last_name,");
		query.append("affiliation,");
		query.append("address_line1,");
		query.append("address_line2,");
		query.append("city,");
		query.append("county,");
		query.append("post_code,");
		query.append("phone,");
		query.append("email,");
		query.append("status,");
		query.append("password ");
		query.append("FROM users ");
		query.append("WHERE identifier=?;");

		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setInt(1, identifier);
			resultSet = statement.executeQuery();
			resultSet.next();

			User originalUser = new User();
			originalUser.setIdentifier(identifier);
			originalUser.setUserID(resultSet.getString(1));
			originalUser.setFirstName(resultSet.getString(2));
			originalUser.setLastName(resultSet.getString(3));
			originalUser.setAffiliation(resultSet.getString(4));
			originalUser.setAddressLine1(resultSet.getString(5));
			originalUser.setAddressLine2(resultSet.getString(6));
			originalUser.setCity(resultSet.getString(7));
			originalUser.setCounty(resultSet.getString(8));
			originalUser.setPostCode(resultSet.getString(9));
			originalUser.setPhone(resultSet.getString(10));
			originalUser.setEmail(resultSet.getString(11));
			originalUser.setStatus(resultSet.getString(12));
			originalUser.setPassword(resultSet.getString(13));	
			
			return originalUser;
		}
		catch(SQLException exception) {
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToGetOriginalUser");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_GET_ORIGINAL_USER,
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
	
	private void checkUserExists(Connection connection,
								 User candidateUser) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT 1 ");
		query.append("FROM users ");
		query.append("WHERE identifier=?;");
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setInt(1, candidateUser.getIdentifier());
			resultSet = statement.executeQuery();
			if (resultSet.next() == false) {
				String errorMessage
					= MacawMessages.getMessage("general.error.nonExistentItem",
												candidateUser.getDisplayName());
				MacawException macawException
					= new MacawException(MacawErrorType.NON_EXISTENT_USER,
										 errorMessage);
				throw macawException;
			}
		}
		catch(SQLException exception) {
			String errorMessage 
				= MacawMessages.getMessage("sql.error.unableToCheckUserExists",
										   candidateUser.getDisplayName());
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_USER_EXISTS,
									 errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}

	private void checkUserDuplicates(Connection connection,
								 	 User candidateUser) throws MacawException {

		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier ");
		query.append("FROM users ");
		query.append("WHERE user_id=? OR email=?;");
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement
				= connection.prepareStatement(query.toString());
			statement.setString(1, candidateUser.getUserID());
			statement.setString(2, candidateUser.getEmail());
			resultSet = statement.executeQuery();							
			if (resultSet.next() == true) {
				int resultIdentifier
					= resultSet.getInt(1);
				if (resultIdentifier != candidateUser.getIdentifier()) {
					String errorMessage
						= MacawMessages.getMessage("user.error.duplicateExists",
													candidateUser.getDisplayName());
					MacawException macawException
						= new MacawException(MacawErrorType.DUPLICATE_USER,
											 errorMessage);
					throw macawException;					
				}				
			}
		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);
			String errorMessage 
				= MacawMessages.getMessage("sql.error.unableToCheckUserDuplicate",
										   candidateUser.getDisplayName());
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_USER_DUPLICATE,
									 errorMessage);
			throw macawException;
		}		
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}
	
	public Connection getConnection() throws MacawException {

		String errorMessage
			= MacawMessages.getMessage("sql.error.noConnectionsAvailable");
		MacawException macawException 
			= new MacawException(MacawErrorType.UNABLE_TO_GET_CONNECTION,
								 errorMessage);

		try {
			Connection connection = sqlConnectionManager.getSQLConnection();
			if (connection == null) {
				throw macawException;
			}
			return connection;
		}
		catch(Exception sqlException) {
			throw macawException;
		}
	}

	public void releaseConnection(Connection connection) {
		sqlConnectionManager.releaseConnection(connection);
	}
	
	// ==========================================
	// Section Interfaces
	// ==========================================

	// ==========================================
	// Section Overload
	// ==========================================

}

