package macaw.test.curation;
import macaw.system.*;
import macaw.businessLayer.*;

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

public class TestOntologyTermFilter extends MacawCurationTestCase {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private OntologyTerm ontologyTerm1;
	private OntologyTerm ontologyTerm2;
	private OntologyTerm ontologyTerm3;
	private OntologyTerm ontologyTerm4;
	private OntologyTerm ontologyTerm5;

	// ==========================================
	// Section Construction
	// ==========================================
	public TestOntologyTermFilter() {
		super("Test Ontology Term");
	}
	
	protected void setUp() throws Exception {
		curationService.clear(admin);

		ontologyTerm1 = new OntologyTerm();
		ontologyTerm1.setTerm("Fired");
		ontologyTerm1.setDescription("to stop the employment of someone.");
		ontologyTerm1.setOntologyName("OurOntology");
		ontologyTerm1.setNameSpace("www.ourontology.org/term-456/Fired");
		curationService.addOntologyTerm(jsmith, ontologyTerm1);
		
		ontologyTerm2 = new OntologyTerm();
		ontologyTerm2.setTerm("Fired");
		ontologyTerm2.setDescription("caused to explode");
		ontologyTerm2.setOntologyName("YourOntology");
		ontologyTerm2.setNameSpace("www.aaa.your_ontology.ca/435/Fired");
		curationService.addOntologyTerm(jsmith, ontologyTerm2);

		ontologyTerm3 = new OntologyTerm();
		ontologyTerm3.setTerm("FIREMAN");
		ontologyTerm3.setDescription("A member of a fire department who fights fires.");
		ontologyTerm3.setOntologyName("OntologyABC");
		ontologyTerm3.setNameSpace("www.ontologyabc.edu/fireman");
		curationService.addOntologyTerm(jsmith, ontologyTerm3);

		ontologyTerm4 = new OntologyTerm();
		ontologyTerm4.setTerm("Wildfire");
		ontologyTerm4.setDescription("Fire that burns uncontrollably in a natural setting");
		ontologyTerm4.setOntologyName("OntologyDEF");
		ontologyTerm4.setNameSpace("www.ontologydef.edu/wildfire");
		curationService.addOntologyTerm(jsmith, ontologyTerm4);

		ontologyTerm5 = new OntologyTerm();
		ontologyTerm5.setTerm("thief");
		ontologyTerm5.setDescription("Someone who steals");
		ontologyTerm5.setOntologyName("OntologyABC");
		ontologyTerm5.setNameSpace("www.ontologyabc.edu/thief");
		curationService.addOntologyTerm(jsmith, ontologyTerm5);
		
	}

	protected void tearDown() throws Exception {
	}

	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	/**
	 * Term=unspecified, Description=no match
	 */
	public void testOntologyTermN1() {
		try {
			ArrayList<OntologyTerm> results
				= curationService.filterOntologyTerms(jsmith, 
											   "", 
											   "oil-fired");
			TestResultSorter<OntologyTerm> sorter
				= new TestResultSorter<OntologyTerm>();
			sorter.sort(results);
			assertEquals(0, results.size());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	/**
	 * Term=unspecified, Description=match
	 */
	public void testOntologyTermN2() {
		try {
			ArrayList<OntologyTerm> results
				= curationService.filterOntologyTerms(jsmith, 
											   "", 
											   "fires");
			TestResultSorter<OntologyTerm> sorter
				= new TestResultSorter<OntologyTerm>();
			sorter.sort(results);
			assertEquals(1, results.size());
			assertEquals("www.ontologyabc.edu/fireman",
						 results.get(0).getNameSpace());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}
	
	/**
	 * Term=unmatched, Description=unspecified
	 */
	public void testOntologyTermN3() {
		try {
			ArrayList<OntologyTerm> results
				= curationService.filterOntologyTerms(jsmith, 
											   "Firebrand", 
											   "");
			TestResultSorter<OntologyTerm> sorter
				= new TestResultSorter<OntologyTerm>();
			sorter.sort(results);
			assertEquals(0, results.size());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}
	
	
	/**
	 * Term=no match, Description=no match
	 */
	public void testOntologyTermN4() {
		try {
			ArrayList<OntologyTerm> results
				= curationService.filterOntologyTerms(jsmith, 
											   "giraffe", 
											   "sparrow");
			TestResultSorter<OntologyTerm> sorter
				= new TestResultSorter<OntologyTerm>();
			sorter.sort(results);
			assertEquals(0, results.size());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}
	
	/**
	 * Term=no match, Description=match
	 */
	public void testOntologyTermN5() {
		try {
			ArrayList<OntologyTerm> results
				= curationService.filterOntologyTerms(jsmith, 
											   "walrus", 
											   "fire");
			TestResultSorter<OntologyTerm> sorter
				= new TestResultSorter<OntologyTerm>();
			sorter.sort(results);
			assertEquals(0, results.size());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}
	
	/**
	 * Term=match, Description=unspecified
	 */
	public void testOntologyTermN6() {
		try {
			ArrayList<OntologyTerm> results
				= curationService.filterOntologyTerms(jsmith, 
											   "fire", 
											   "");
			TestResultSorter<OntologyTerm> sorter
				= new TestResultSorter<OntologyTerm>();
			sorter.sort(results);
			
			assertEquals(4, results.size());
			assertEquals("www.ontologyabc.edu/fireman",
						 results.get(0).getNameSpace());
			assertEquals("www.ourontology.org/term-456/Fired",
					 	 results.get(1).getNameSpace());
			assertEquals("www.aaa.your_ontology.ca/435/Fired",
				 	 	 results.get(2).getNameSpace());			
			assertEquals("www.ontologydef.edu/wildfire",
			 	 	 	 results.get(3).getNameSpace());			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}
	
	/**
	 * Term=match, Description=no match
	 */
	public void testOntologyTermN7() {
		try {
			ArrayList<OntologyTerm> results
				= curationService.filterOntologyTerms(jsmith, 
											   "fire", 
											   "tangerine");
			TestResultSorter<OntologyTerm> sorter
				= new TestResultSorter<OntologyTerm>();
			sorter.sort(results);
			assertEquals(0, results.size());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}
	
	/**
	 * Term=match, Description=match
	 */
	public void testOntologyTermN8() {
		try {
			ArrayList<OntologyTerm> results
				= curationService.filterOntologyTerms(jsmith, 
											   "fire", 
											   "ment");
			TestResultSorter<OntologyTerm> sorter
				= new TestResultSorter<OntologyTerm>();
			sorter.sort(results);
			assertEquals(2, results.size());
			assertEquals("www.ontologyabc.edu/fireman",
						 results.get(0).getNameSpace());
			assertEquals("www.ourontology.org/term-456/Fired",
					 	 results.get(1).getNameSpace());			
			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}
	
	/**
	 * Term=unspecified, Description=unspecified
	 */
	public void testOntologyTermE1() {
		try {
			ArrayList<OntologyTerm> results
				= curationService.filterOntologyTerms(jsmith, 
											   "", 
											   "");
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors 
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NO_ONTOLOGY_TERM_FILTER);
			assertEquals(1, numberOfErrors);
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

