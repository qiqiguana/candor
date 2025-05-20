package macaw.test.curation;


import macaw.businessLayer.MacawCurationAPI;
import macaw.businessLayer.User;
import macaw.persistenceLayer.production.ProductionCurationService;
import macaw.system.*;
import macaw.test.RunAllMacawTestCases;
import junit.framework.TestCase;


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

public class MacawCurationTestCase extends TestCase {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================

	protected MacawCurationAPI curationService;
	
	protected User jsmith;
	protected User admin;
	protected Log log;

	// ==========================================
	// Section Construction
	// ==========================================

	public MacawCurationTestCase(String name) {
		super(name);
		
		//System.out.println(name);

		try {
			log = new Log();
			jsmith = new User("jsmith", "cool");
			admin = new User("admin", "admin");

			curationService = RunAllMacawTestCases.getMacawCurationService();

			if (RunAllMacawTestCases.USE_DEMO == false) {
				ProductionCurationService service
					= (ProductionCurationService) curationService;
			}			
		}
		catch(Exception exception) {
			exception.printStackTrace(System.out);
		}
	}

	protected void setUp() throws Exception {
		RunAllMacawTestCases.curationService.clear(admin);
		if (RunAllMacawTestCases.USE_DEMO == false) {
			ProductionCurationService service
				= (ProductionCurationService) RunAllMacawTestCases.curationService;
		}
		
		super.setUp();
	}

	protected void tearDown() throws Exception {
		if (RunAllMacawTestCases.USE_DEMO == false) {
			ProductionCurationService service
				= (ProductionCurationService) RunAllMacawTestCases.curationService;
		}
		super.tearDown();
	}


	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

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

