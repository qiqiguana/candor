package macaw.system;


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

public class StartupOptions {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private boolean useDemo;
	private String databaseName;
	private String dbDriverName;
	private String dbUser;
	private String dbPassword;
	private String server;
	private String port;
	private int maximumNumberOfConnections;
	

	// ==========================================
	// Section Construction
	// ==========================================
	public StartupOptions() {
		useDemo = true;
		dbUser = null;
		dbPassword = null;
		server = "localhost";
		port = null;
		dbDriverName = "com.mysql.jdbc.Driver";
		databaseName = "macaw";
		maximumNumberOfConnections = 20;
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public void processCommandLineArguments(String[] arguments) {
		useDemo = true;
		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i].equals("-production") == true) {
				useDemo = false;
			}
			else if (arguments[i].equals("-dbUser") == true) {
				dbUser = arguments[i+1];
			}
			else if (arguments[i].equals("-dbPassword") == true) {
				dbPassword = arguments[i+1];
			}
			else if (arguments[i].equals("-dbName") == true) {
				databaseName = arguments[i+1];
			}
			else if (arguments[i].equals("-dbServer") == true) {
				server = arguments[i+1];				
			}
			else if (arguments[i].equals("-dbPort") == true) {
				port = arguments[i+1];
			}
		}		
	}
	
	public String getDatabaseURI() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("jdbc:mysql://");
		if (server == null) {
			buffer.append("localhost");
		}
		else {
			buffer.append(server);
		}

		if (port != null) {
			buffer.append(":");
			buffer.append(port);
		}
		buffer.append("/");
		buffer.append(databaseName);		

		return buffer.toString();
	}
	
	/**
	 * @return the dbUser
	 */
	public String getDbUser() {
		return dbUser;
	}


	/**
	 * @param dbUser the dbUser to set
	 */
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}


	/**
	 * @return the dbPassword
	 */
	public String getDbPassword() {
		return dbPassword;
	}


	/**
	 * @param dbPassword the dbPassword to set
	 */
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}


	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}


	/**
	 * @param server the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}


	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}


	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}
		
	/**
	 * @return the useDemo
	 */
	public boolean useDemo() {
		return useDemo;
	}

	/**
	 * @param useDemo the useDemo to set
	 */
	public void setUseDemo(boolean useDemo) {
		this.useDemo = useDemo;
	}

	/**
	 * @return the databaseName
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * @param databaseName the databaseName to set
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * @return the dbDriverName
	 */
	public String getDbDriverName() {
		return dbDriverName;
	}

	/**
	 * @param dbDriverName the dbDriverName to set
	 */
	public void setDbDriverName(String dbDriverName) {
		this.dbDriverName = dbDriverName;
	}

	/**
	 * @return the maximumNumberOfConnections
	 */
	public int getMaximumNumberOfConnections() {
		return maximumNumberOfConnections;
	}

	/**
	 * @param maximumNumberOfConnections the maximumNumberOfConnections to set
	 */
	public void setMaximumNumberOfConnections(int maximumNumberOfConnections) {
		this.maximumNumberOfConnections = maximumNumberOfConnections;
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

