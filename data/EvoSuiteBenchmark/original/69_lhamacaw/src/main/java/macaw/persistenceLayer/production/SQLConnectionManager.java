package macaw.persistenceLayer.production;

import macaw.system.*;

import java.sql.*;
import java.util.*;

/**
 * A class designed specifically to manage SQL connection pooling for the 
 * {@link macaw.persistenceLayer.production.ProductionCurationService} and 
 * {@link macaw.persistenceLayer.production.ProductionRetrievalService} and 
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

public class SQLConnectionManager {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private Log log;
	private ArrayList<Connection> availableConnections;
	private int maximumConnections;
		
	private String databaseDriverClassName;
	private String databaseURL;
	private String dbUser;
	private String dbPassword;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public SQLConnectionManager(SessionProperties sessionProperties) throws MacawException {

		StartupOptions startupOptions
			= (StartupOptions) sessionProperties.getProperty(SessionProperties.STARTUP_OPTIONS);
		databaseDriverClassName
			= startupOptions.getDbDriverName();
		
		this.log = (Log) sessionProperties.getProperty(SessionProperties.LOG);		
		
		//establish URL string that will be used to create connections
		databaseURL = startupOptions.getDatabaseURI();
		
		dbUser = startupOptions.getDbUser(); 
		dbPassword = startupOptions.getDbPassword();
		
		availableConnections = new ArrayList<Connection>();		
		maximumConnections = startupOptions.getMaximumNumberOfConnections();		
	}
	
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	

	public synchronized Connection getConnection() throws MacawException {
		try {
			
			if (availableConnections.size() == maximumConnections) {

				//no available connections
				String errorMessage
					= MacawMessages.getMessage("sqlDB.noConnectionsAvailable");
				MacawException macawException 
					= new MacawException(MacawErrorType.UNABLE_TO_GET_CONNECTION,
										 errorMessage);
				throw macawException;
			}		

			if (availableConnections.size() > 0) {
				Connection connection
					= availableConnections.get(0);
				availableConnections.remove(connection);				
				return connection;
			}
			else {				
				//create a new one				
				Class.forName(databaseDriverClassName).newInstance();
				if (dbUser == null) {
					Connection connection = DriverManager.getConnection(databaseURL);
					availableConnections.add(connection);					
					return connection;
				}
				else {
					Connection connection 
						= DriverManager.getConnection(databaseURL, 
													  dbUser,
													  dbPassword);
					availableConnections.add(connection);
					return connection;
				}
			}						
		}
		catch(Exception exception) {
			exception.printStackTrace(System.out);
			log.logException(exception);

			String errorMessage
				= MacawMessages.getMessage("sqlDB.error.unableToCreateConnection");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_CREATE_CONNECTION,
									 errorMessage);
			throw macawException;			
		}		
	}

	/**
	 * This method exists solely to help make it easier to use an SQL connection
	 * manager within the SQLUserManager class.  The method was developed as part
	 * of an effort to cleanly separate its concerns from a service that would help
	 * validate end-users.
	 * @throws SQLException
	 */
	public synchronized Connection getSQLConnection() throws Exception {
		if (availableConnections.size() == maximumConnections) {
			return null;
		}		

		if (availableConnections.size() > 0) {
			Connection connection = availableConnections.get(0);
			availableConnections.remove(connection);				
			return connection;
		}
		else {							
			//create a new one
			 Class.forName(databaseDriverClassName).newInstance();
			if (dbUser == null) {
				Connection connection = DriverManager.getConnection(databaseURL);
				availableConnections.add(connection);					
				return connection;
			}
			else {
				Connection connection 
					= DriverManager.getConnection(databaseURL, 
												  dbUser,
												  dbPassword);
				availableConnections.add(connection);
				return connection;
			}
		}	
	}

	
	public void releaseConnection(Connection connection) {
		if (connection != null) {
			availableConnections.add(connection);			
		}
	}
	
	public int getNumberOfConnections() {
		return availableConnections.size();
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

