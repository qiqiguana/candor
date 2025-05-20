package macaw.persistenceLayer.production;

import macaw.businessLayer.*;
import macaw.persistenceLayer.DummyDataProvider;
import macaw.system.*;

import java.sql.*;
import java.io.*;
import java.util.PropertyResourceBundle;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * A convenience class that can be used to create all the database tables used by
 * the production services.
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

public class SetupCurationDatabase {

	public static final void main(String[] arguments) {
		
		SessionProperties sessionProperties 
			= new SessionProperties();
		StartupOptions startupOptions
			= (StartupOptions) sessionProperties.getProperty(SessionProperties.STARTUP_OPTIONS);
		startupOptions.setDatabaseName("macaw");
		
		SetupCurationDatabase setupTool
			= new SetupCurationDatabase(sessionProperties);
		
		startupOptions.processCommandLineArguments(arguments);
				
		try {			
			setupTool.setup();
		}
		catch(MacawException exception) {
			exception.printErrors();
		}
	}
	
	
	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private String databaseName;
	private String databaseServer;
	private String databasePort;
	
	private StartupOptions startupOptions;
	
	private SessionProperties sessionProperties;
	private SQLConnectionManager sqlConnectionManager;
	private String startupPropertiesFileName;
	private SQLListChoiceManager listChoiceManager;
	private SQLSupportingDocumentsManager documentsManager;
	private SQLValueLabelManager valueLabelsManager;
	private SQLChangeEventManager changeEventManager;
	private SQLUserManager userManager;
	private SQLVariableManager variableManager;
	private SQLOntologyTermManager ontologyTermManager;
	private Log log;

	// ==========================================
	// Section Construction
	// ==========================================
	public SetupCurationDatabase(SessionProperties sessionProperties) {
		log = new Log();
		this.sessionProperties = sessionProperties;
	}
		
	public void setup() throws MacawException {

		readPropertiesFile();

		Connection connection = null;
		sqlConnectionManager
			= new SQLConnectionManager(sessionProperties);
		if (databaseExists() == true) {
			String databaseAlreadyExists
				= MacawMessages.getMessage("setupDatabase.databaseAlreadyExists");
			JOptionPane.showMessageDialog(null, databaseAlreadyExists);
			return;
		}
		createDatabase();
		createManagerObjects();
		

		try {						
			connection = sqlConnectionManager.getConnection();			
			changeEventManager.createTable(connection);

			userManager.createTable(connection);
			listChoiceManager.createTables(connection);
			documentsManager.createTable(connection);
			ontologyTermManager.createTable(connection);
			valueLabelsManager.createTable(connection);
			variableManager.createTables(connection);
			
			String finishedInstallation
				= MacawMessages.getMessage("setupDatabase.databaseSuccesfullyInstalled");
			JOptionPane.showMessageDialog(null, finishedInstallation);
		}
		finally {
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	private Connection getConnection(String databaseURI) throws Exception {
		Connection connection = null;
		
		StartupOptions startupOptions
			= (StartupOptions) sessionProperties.getProperty(SessionProperties.STARTUP_OPTIONS);
		String dbUserID
			= startupOptions.getDbUser();
		String dbPassword
			= startupOptions.getDbPassword();

		if (dbUserID == null) {
			connection 
				= DriverManager.getConnection(databaseURI);
		}
		else {
			connection 
				= DriverManager.getConnection(databaseURI,
											  dbUserID,
											  dbPassword);				
		}		
		return connection;
	}

	
	private void readPropertiesFile() {
		databaseName = "macaw";
		databaseServer = "localhost";
		//databasePort = "3306";		
		//databaseServer = "prometheus";
		//databasePort = "3306";		

	}
	
	public boolean databaseExists() throws MacawException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			
			//String databaseURI = "jdbc:mysql://localhost:3306/mysql";
			//String databaseURI = "jdbc:mysql://localhost/mysql";

			String databaseURI = "jdbc:mysql://localhost/";
			connection = getConnection(databaseURI);
			
			StringBuilder query = new StringBuilder();
			query.append("SELECT SCHEMA_NAME ");
			query.append("FROM INFORMATION_SCHEMA.SCHEMATA ");
			query.append("WHERE SCHEMA_NAME = '");
			query.append(databaseName);
			query.append("';");

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query.toString());
			return (resultSet.next());			
		}
		catch(Exception exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sqlDB.error.unableToCreateDatabase",
										   databaseName);
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_CHECK_DATABASE_EXISTS,
								     errorMessage);
			throw macawException;
		}
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, null);
			try {
				connection.close();
			}
			catch(Exception exception) {

			}
		}		
	}

	
	private void createDatabase() throws MacawException {

		PreparedStatement statement = null;
		try {		
			StringBuilder databaseURI = new StringBuilder();
			databaseURI.append("jdbc:mysql://");
			if (databaseServer == null) {
				databaseURI.append("localhost");
			}
			else {
				databaseURI.append(databaseServer);
			}
			
			if (databasePort != null) {
				databaseURI.append(":");
				databaseURI.append(databasePort);				
			}
		
			databaseURI.append("/");
			
			
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			StartupOptions startupOptions
				= (StartupOptions) sessionProperties.getProperty(SessionProperties.STARTUP_OPTIONS);
			String dbUser
				= startupOptions.getDbUser();
			String dbPassword
				= startupOptions.getDbPassword();

			Connection connection = null;
			if (dbUser == null) {
				connection 
					= DriverManager.getConnection(databaseURI.toString());
			}
			else {
				connection 
					= DriverManager.getConnection(databaseURI.toString(),
												  dbUser,
												  dbPassword);				
			}
		
			StringBuilder query = new StringBuilder();
			query.append("CREATE DATABASE ");
			query.append(databaseName);
			query.append(";");
			
			statement
				= connection.prepareStatement(query.toString());
			statement.executeUpdate();	
		}
		catch(Exception exception) {
			exception.printStackTrace(System.out);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToCreateDatabase");
			MacawException macawException
				= new MacawException(MacawErrorType.UNABLE_TO_CREATE_DATABASE,
									 errorMessage);
			throw macawException;
		}
		finally {
			try {
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
	
	private void createManagerObjects() throws MacawException {

		log = sessionProperties.getLog();

		changeEventManager = new SQLChangeEventManager(sessionProperties.getLog());
		userManager = new SQLUserManager(changeEventManager, sqlConnectionManager);
		userManager.setLog(log);

		documentsManager 
			= new SQLSupportingDocumentsManager(changeEventManager);
		documentsManager.setLog(log);
		listChoiceManager 
			= new SQLListChoiceManager(changeEventManager);
		//listChoiceManager.setLog(log);
		valueLabelsManager 
			= new SQLValueLabelManager(changeEventManager);
		valueLabelsManager.setLog(log);
		
		ontologyTermManager
			= new SQLOntologyTermManager(changeEventManager);
		ontologyTermManager.setLog(log);

		variableManager
			= new SQLVariableManager(changeEventManager,
									 listChoiceManager,
									 ontologyTermManager,
									 documentsManager);
		variableManager.setLog(log);
	}
	
	private void initiallyPopulate() {
			try {				
				ProductionCurationService database
					= new ProductionCurationService(sessionProperties);

				User admin = new User("admin", "admin");
				User jsmith = new User("jsmith", "cool");

				DummyDataProvider dummyDataProvider
					= new DummyDataProvider(database, admin);
				dummyDataProvider.populateDatabase(jsmith);
			}
			catch(MacawException exception) {
				exception.printErrors();
				Log log = new Log();
				log.logException(exception);			
			}
	}
	
	public void dropDatabase() throws MacawException {
		User user = new User("user", "user");
		
		StringBuilder query = new StringBuilder();
		query.append("DROP DATABASE macaw;");

		Connection connection = sqlConnectionManager.getConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());
			statement.executeQuery();
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
			sqlConnectionManager.releaseConnection(connection);
		}		
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public void setStartUpPropertiesFileName(String startupPropertiesFileName) {
		this.startupPropertiesFileName = startupPropertiesFileName;
	}
	
	public void run() {
		try {
			try {
				//Part I: Obtain the properties file that contains
				//all the startup properties
				File setupFile = new File(startupPropertiesFileName);
				if (setupFile.exists() == false) {
					UserInterfaceFactory userInterfaceFactory
						= new UserInterfaceFactory();
					JFileChooser fileChooser
						= userInterfaceFactory.createFileChooser();
					
					int result
						= fileChooser.showOpenDialog(null);
					if (result == JFileChooser.APPROVE_OPTION) {
						setupFile 
							= fileChooser.getSelectedFile();
					}
					else {
						String errorMessage
							= MacawMessages.getMessage("");
						log.displayErrorDialog(errorMessage);
						System.exit(0);
					}

					//Part II: Extract important properties from the file
					FileInputStream fileInputStream
						= new FileInputStream(setupFile);
					PropertyResourceBundle startupProperties
						= new PropertyResourceBundle(fileInputStream);
					
					String databaseServer
						= startupProperties.getString("macaw.persistenceLayer.server");
					String databaseName
						= startupProperties.getString("macaw.persistenceLayer.name");
					StringBuilder buffer = new StringBuilder();
					buffer.append("jdbc:mysql://");
					
					if (databaseServer == null) {
						buffer.append("localhost");
					}
					else {
						buffer.append(databaseServer);
					}
					
					/**
					if (databasePort != null) {
						buffer.append(":");
						buffer.append(databasePort);
					}
					 */
					
					StringBuilder databaseURL = new StringBuilder();
					//String 
					
					
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					//Connection connection = DriverManager.getConnection(databaseURL);

					//
				}
			}
			catch(Exception exception) {
				log.logException(exception);
			}
						
			createDatabase();
		}
		catch(MacawException exception) {
			Log log = new Log();
			log.logException(exception);
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

