package macaw.test.retrieval;

import java.util.ArrayList;


import macaw.businessLayer.AliasFilePath;
import macaw.businessLayer.AvailabilityState;
import macaw.businessLayer.Category;
import macaw.businessLayer.CleaningState;
import macaw.system.MacawException;
import macaw.test.curation.TestResultSorter;


/**
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

public class TestRetrieveAliasFilePaths extends MacawRetrievalTestCase {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================

	private AvailabilityState allPeople;
	private AvailabilityState somePeople;
	private AvailabilityState nobody;
	
	private AliasFilePath b22;
	private AliasFilePath b23;
	private AliasFilePath c35;

	private Category cognitiveFunction;
	private Category medication;
	private Category education;

	private CleaningState sortOfCleaned;
	private CleaningState fullyRevised;
	private CleaningState notReallyCleaned;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public TestRetrieveAliasFilePaths() {
		super("Test Retrieving alias file paths");
		
		b22 = new AliasFilePath("b22", "$lb/b22/c.sv");
		b23 = new AliasFilePath("b23", "$lb/b23/c.dat");
		c35 = new AliasFilePath("c35", "$lb/c35/c.sv");
		
		cognitiveFunction = new Category("Cognitive Function");
		medication = new Category("Medication");
		education = new Category("Education");
		
		allPeople = new AvailabilityState("All People");
		nobody = new AvailabilityState("Nobody");		
		somePeople = new AvailabilityState("Some People");

		sortOfCleaned = new CleaningState("Sort of cleaned");
		fullyRevised = new CleaningState("Fully revised");
		notReallyCleaned = new CleaningState("Not really cleaned");
		
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
		
	public void testGetAliasFilePathsN1() {
		try {
			addDefaultAliasFilePaths();
			ArrayList<AliasFilePath> allPaths
				= retrievalService.getAliasFilePaths(jsmith);
			assertEquals(3, allPaths.size());
			
			TestResultSorter<AliasFilePath> sorter 
				= new TestResultSorter<AliasFilePath>();
			sorter.sort(allPaths);
		
			assertEquals("b22", allPaths.get(0).getAlias());
			assertEquals("b23", allPaths.get(1).getAlias());
			assertEquals("c35", allPaths.get(2).getAlias());
		}
		catch(MacawException exception) {
			exception.printStackTrace(System.out);
			fail();
		}
	}

	public void testGetAliasFilePathsA1() {
		ArrayList<AliasFilePath> allPaths
			= retrievalService.getAliasFilePaths(jsmith);
		assertEquals(0, allPaths.size());
	}

	public void testGetAliasFilePathN1() {
		try {
			addDefaultAliasFilePaths();
			AliasFilePath aliasFilePath
				= retrievalService.getAliasFilePath(jsmith, "b22");
			assertNotNull(aliasFilePath);
			assertEquals("$lb/b22/c.sv", aliasFilePath.getFilePath());
		}
		catch(MacawException exception) {
			exception.printStackTrace(System.out);
			fail();
		}
	}

	public void testGetAliasFilePathA1() {
		try {
			addDefaultAliasFilePaths();
			AliasFilePath aliasFilePath
				= retrievalService.getAliasFilePath(jsmith, "zzz");
			assertNull(aliasFilePath);
		}
		catch(MacawException exception) {
			exception.printStackTrace(System.out);
			fail();
		}
	}

	public void testGetAliasFilePathsMatchingNameN1() {
		try {
			addDefaultAliasFilePaths();
			ArrayList<AliasFilePath> aliasFilePaths
				= retrievalService.getAliasFilePathsMatchingName(jsmith, "b");
			assertEquals(2, aliasFilePaths.size());
			
			TestResultSorter<AliasFilePath> sorter 
				= new TestResultSorter<AliasFilePath>();
		
			sorter.sort(aliasFilePaths);
			assertEquals("b22", aliasFilePaths.get(0).getAlias());
			assertEquals("b23", aliasFilePaths.get(1).getAlias());

			aliasFilePaths
				= retrievalService.getAliasFilePathsMatchingName(jsmith, "3");
			assertEquals(2, aliasFilePaths.size());
			sorter.sort(aliasFilePaths);
			assertEquals("b23", aliasFilePaths.get(0).getAlias());
			assertEquals("c35", aliasFilePaths.get(1).getAlias());
		}
		catch(MacawException exception) {
			exception.printStackTrace(System.out);
			fail();
		}		
	}
	
	public void testGetAliasFilePathsMatchingNameN2() {
		try {
			addDefaultAliasFilePaths();
			ArrayList<AliasFilePath> aliasFilePaths
				= retrievalService.getAliasFilePathsMatchingName(jsmith, "b");
			assertEquals(2, aliasFilePaths.size());
		}
		catch(MacawException exception) {
			exception.printStackTrace(System.out);
			fail();
		}		
	}
	
	public void testGetCategoriesN1() {
		try {
			addDefaultCategories();
			ArrayList<Category> allCategories
				= retrievalService.getCategories(jsmith);
			assertEquals(3, allCategories.size());
			
			TestResultSorter<Category> sorter 
				= new TestResultSorter<Category>();
			sorter.sort(allCategories);
			
			assertEquals("Cognitive Function", allCategories.get(0).getName());
			assertEquals("Education", allCategories.get(1).getName());
			assertEquals("Medication", allCategories.get(2).getName());			
		}
		catch(MacawException exception) {
			exception.printErrors();
			fail();
		}
	}

	public void testGetCategoriesA1() {
			ArrayList<Category> allCategories
				= retrievalService.getCategories(jsmith);
			assertEquals(0, allCategories.size());			
	}
	
	public void testGetAvailabilityStatesN1() {
		try {
			addDefaultAvailabilityStates();
			ArrayList<AvailabilityState> allAvailabilityStates
				= retrievalService.getAvailabilityStates(jsmith);
			assertEquals(3, allAvailabilityStates.size());
			
			TestResultSorter<AvailabilityState> sorter 
				= new TestResultSorter<AvailabilityState>();
			sorter.sort(allAvailabilityStates);
			
			assertEquals("All People", allAvailabilityStates.get(0).getName());
			assertEquals("Nobody", allAvailabilityStates.get(1).getName());			
			assertEquals("Some People", allAvailabilityStates.get(2).getName());
		}
		catch(MacawException exception) {
			exception.printErrors();
			fail();
		}
	}

	public void testGetAvailabilityStatesA1() {
		ArrayList<AvailabilityState> allAvailabilityStates
			= retrievalService.getAvailabilityStates(jsmith);
		assertEquals(0, allAvailabilityStates.size());
	}

	public void testGetCleaningStatesN1() {
		try {
			addDefaultCleaningStates();
			ArrayList<CleaningState> allCleaningStates
				= retrievalService.getCleaningStates(jsmith);
			assertEquals(3, allCleaningStates.size());
			
			TestResultSorter<CleaningState> sorter = new TestResultSorter<CleaningState>();
			sorter.sort(allCleaningStates);
			
			assertEquals("Fully revised", allCleaningStates.get(0).getName());
			assertEquals("Not really cleaned", allCleaningStates.get(1).getName());		
			assertEquals("Sort of cleaned", allCleaningStates.get(2).getName());
		}
		catch(MacawException exception) {
			exception.printErrors();
			fail();
		}
	}

	public void testGetCleaningStatesA1() {
		ArrayList<CleaningState> allCleaningStates
			= retrievalService.getCleaningStates(jsmith);
		assertEquals(0, allCleaningStates.size());
	}

	private void addDefaultAliasFilePaths() throws MacawException {
		curationService.addAliasFilePath(admin, b22);
		curationService.addAliasFilePath(admin, b23);
		curationService.addAliasFilePath(admin, c35);
	}

	private void addDefaultCategories() throws MacawException {
		curationService.addCategory(admin, cognitiveFunction);
		curationService.addCategory(admin, medication);
		curationService.addCategory(admin, education);
	}

	private void addDefaultAvailabilityStates() throws MacawException {
		curationService.addAvailabilityState(jsmith, allPeople);
		curationService.addAvailabilityState(jsmith, nobody);
		curationService.addAvailabilityState(jsmith, somePeople);
	}

	private void addDefaultCleaningStates() throws MacawException {
		curationService.addCleaningState(jsmith, fullyRevised);
		curationService.addCleaningState(jsmith, notReallyCleaned);
		curationService.addCleaningState(jsmith, sortOfCleaned);	
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

