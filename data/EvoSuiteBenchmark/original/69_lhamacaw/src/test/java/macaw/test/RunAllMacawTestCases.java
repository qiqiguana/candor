package macaw.test;

import macaw.businessLayer.MacawCurationAPI;
import macaw.businessLayer.MacawRetrievalAPI;
import macaw.persistenceLayer.demo.*;
import macaw.persistenceLayer.production.*;
import macaw.system.*;
import junit.framework.*;


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

public class RunAllMacawTestCases extends TestSuite {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	public static final boolean USE_DEMO = false;
	
	private static boolean servicesInitialised = false;
	public static MacawCurationAPI curationService = null;
	public static MacawRetrievalAPI retrievalService = null;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public RunAllMacawTestCases() {

		//testing features for the MacawCurationAPI
		
	}
	
	public static MacawCurationAPI getMacawCurationService() throws MacawException {
		initialiseServices();
		return curationService;			
	}

	public static MacawRetrievalAPI getMacawRetrievalService() throws MacawException {
		initialiseServices();
		return retrievalService;
	}

	public static void initialiseServices() throws MacawException {
		if (servicesInitialised == false) {
			servicesInitialised = true;
			
			if (RunAllMacawTestCases.USE_DEMO == true) {
				SessionProperties sessionProperties = new SessionProperties();
				DemonstrationRetrievalService service
					= new DemonstrationRetrievalService(sessionProperties);
				curationService = service;
				retrievalService = service;
			}
			else {
				SessionProperties sessionProperties = new SessionProperties();
				curationService = new ProductionCurationService(sessionProperties);
				retrievalService = new ProductionRetrievalService(sessionProperties);
			}			
		}
		
	}
	
	
	public static Test suite() {
		
		TestSuite suite = new TestSuite();
		suite.addTestSuite(macaw.test.curation.TestCurateDerivedVariables.class);
		suite.addTestSuite(macaw.test.curation.TestCurateRawVariables.class);
		suite.addTestSuite(macaw.test.curation.TestCurateListChoices.class);
		suite.addTestSuite(macaw.test.curation.TestCurateOntologyTerms.class);
		suite.addTestSuite(macaw.test.curation.TestCurateUsers.class);
		suite.addTestSuite(macaw.test.curation.TestCurateValueLabels.class);
		suite.addTestSuite(macaw.test.curation.TestOntologyTermFilter.class);
		suite.addTestSuite(macaw.test.curation.TestSupportingDocumentFilter.class);
		//suite.addTestSuite(test.curation.TestVariableFilter.class);	

		//testing features for the MacawRetrievalAPI
		suite.addTestSuite(macaw.test.retrieval.TestRetrieveAliasFilePaths.class);	
		suite.addTestSuite(macaw.test.retrieval.TestRetrieveUsers.class);	
		suite.addTestSuite(macaw.test.retrieval.TestRetrieveVariables.class);	

		return suite;
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

