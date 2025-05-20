package macaw.system;

import java.util.HashMap;


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

public class SessionProperties {

	// ==========================================
	// Section Constants
	// ==========================================
	public static final String USER_INTERFACE_FACTORY = "userInterfaceFactory";
	public static final String LOG = "log";
	public static final String DATABASE = "database";
	public static final String CURRENT_USER = "currentUser";
	public static final String SECURITY_SERVICE ="securityService";
	public static final String STARTUP_OPTIONS = "startupOptions";
	public static final String RETRIEVAL_SERVICE = "retrievalService";
	//public static final String SAVE_CHANGES_MONITOR = "saveChangesMonitor";
	
	// ==========================================
	// Section Properties
	// ==========================================
	
	private Log log;
	private UserInterfaceFactory userInterfaceFactory;
	private HashMap<String, Object> propertyFromName;
	private StartupOptions startupOptions;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public SessionProperties() {
		propertyFromName = new HashMap<String, Object>();
		log = new Log();
		setProperty(LOG, log);
		
		userInterfaceFactory = new UserInterfaceFactory();
		setProperty(USER_INTERFACE_FACTORY, userInterfaceFactory);
		
		startupOptions = new StartupOptions();
		setProperty(STARTUP_OPTIONS, startupOptions);
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public UserInterfaceFactory getUserInterfaceFactory() {
		return userInterfaceFactory;
	}
	
	public Log getLog() {
		return log;
	}
	
	public void setProperty(String propertyName,
							Object object) {
		propertyFromName.put(propertyName, object);
	}
	
	public Object getProperty(String propertyName) {
		return propertyFromName.get(propertyName);
	}
		
	public StartupOptions getStartupOptions() {
		return startupOptions;
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

