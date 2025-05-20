package macaw.test.curation;


import macaw.businessLayer.*;
import macaw.system.*;

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

public class TestCurateListChoices extends MacawCurationTestCase {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================	
	
	// ==========================================
	// Section Construction
	// ==========================================
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public TestCurateListChoices() {
		super("TestListChoices");
	}
	
	//test cases for availability states
	
	public void testAddAvailabilityStateN1() {
		try {
			
			TestResultSorter<AvailabilityState> sorter
				= new TestResultSorter<AvailabilityState>();
			
			//test normal behaviour
			AvailabilityState availabilityState1
				= new AvailabilityState("available");
			curationService.addAvailabilityState(jsmith, availabilityState1);
			
			AvailabilityState availabilityState2
				= new AvailabilityState("not available");
			curationService.addAvailabilityState(jsmith, availabilityState2);
			
			AvailabilityState availabilityState3
				= new AvailabilityState("sometimes available");
			curationService.addAvailabilityState(jsmith, availabilityState3);
			
			ArrayList<AvailabilityState> availabilityStatesSoFar
				= curationService.getAvailabilityStates(jsmith);
			
			sorter.sort(availabilityStatesSoFar);
			
			int numberOfStates = availabilityStatesSoFar.size();
			assertEquals(3, numberOfStates);
			
			String firstState
				= availabilityStatesSoFar.get(0).getDisplayName();
			assertEquals("available", firstState);
			String secondState
				= availabilityStatesSoFar.get(1).getDisplayName();
			assertEquals("not available", secondState);
			String thirdState
				= availabilityStatesSoFar.get(2).getDisplayName();
			assertEquals("sometimes available", thirdState);					
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}
	
	public void testAddAvailabilityStateE1() {
		try {
			TestResultSorter<AvailabilityState> sorter
				= new TestResultSorter<AvailabilityState>();
			
			AvailabilityState availabilityState1
				= new AvailabilityState("sometimes available");
			curationService.addAvailabilityState(jsmith, availabilityState1);
			AvailabilityState availabilityState2
				= new AvailabilityState("SOMETIMES AVAILABLE");
			curationService.addAvailabilityState(jsmith, availabilityState2);
			ArrayList<AvailabilityState> availabilityStatesSoFar
				= curationService.getAvailabilityStates(jsmith);
			sorter.sort(availabilityStatesSoFar);
			assertEquals(2, availabilityStatesSoFar.size());
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors = exception.getNumberOfErrors(MacawErrorType.DUPLICATE_AVAILABILITY_STATE);
			assertEquals(1, numberOfErrors);
		}		
	}

	public void testAddAvailabilityStateE2() {
		try {
			TestResultSorter<AvailabilityState> sorter
				= new TestResultSorter<AvailabilityState>();
			
			AvailabilityState availabilityState1
				= new AvailabilityState("sometimes available");
			curationService.addAvailabilityState(jsmith, availabilityState1);
			AvailabilityState availabilityState2
				= new AvailabilityState("sometimes available");
			curationService.addAvailabilityState(jsmith, availabilityState2);
			ArrayList<AvailabilityState> availabilityStatesSoFar
				= curationService.getAvailabilityStates(jsmith);
			sorter.sort(availabilityStatesSoFar);
			assertEquals(2, availabilityStatesSoFar.size());
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int actualNumberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.DUPLICATE_AVAILABILITY_STATE);
			assertEquals(1, actualNumberOfErrors);
		}
	}

	public void testUpdateAvailabilityStateN1() {
		try {
			
			TestResultSorter<AvailabilityState> sorter
				= new TestResultSorter<AvailabilityState>();

			AvailabilityState availabilityState1
				= new AvailabilityState("sometimes available");
			curationService.addAvailabilityState(jsmith, availabilityState1);
			AvailabilityState availabilityState2
				= new AvailabilityState("never available");
			curationService.addAvailabilityState(jsmith, availabilityState2);
			int identifier
				= curationService.getAvailabilityStateIdentifier(jsmith, 
														  null,
														  availabilityState2);
			availabilityState2.setIdentifier(identifier);
			availabilityState2.setName("always available");
			curationService.updateAvailabilityState(jsmith, availabilityState2);

			ArrayList<AvailabilityState> availabilityStatesSoFar
				= curationService.getAvailabilityStates(jsmith);
			sorter.sort(availabilityStatesSoFar);
			assertEquals(2, availabilityStatesSoFar.size());
			
			String actualValue1
				= availabilityStatesSoFar.get(0).getName();
			assertEquals("always available", actualValue1);
			
			String actualValue2
				= availabilityStatesSoFar.get(1).getName();
			assertEquals("sometimes available", actualValue2);			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}		

	public void testUpdateAvailabilityStateE1() {
		try {
			//update a non-existent record
			
			TestResultSorter<AvailabilityState> sorter
				= new TestResultSorter<AvailabilityState>();

			AvailabilityState availabilityState1
				= new AvailabilityState("sometimes available");
			curationService.addAvailabilityState(jsmith, availabilityState1);
			
			AvailabilityState availabilityState2
				= new AvailabilityState("never available");
			availabilityState2.setIdentifier(4566);
			curationService.updateAvailabilityState(jsmith, availabilityState2);

			ArrayList<AvailabilityState> availabilityStatesSoFar
				= curationService.getAvailabilityStates(jsmith);
			sorter.sort(availabilityStatesSoFar);
			assertEquals(1, availabilityStatesSoFar.size());
			fail();	
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_AVAILABILITY_STATE);
			assertEquals(1, numberOfErrors);
		}		
	}		
	
	public void testDeleteAvailabilityStateN1() {
		try {
			
			TestResultSorter<AvailabilityState> sorter
				= new TestResultSorter<AvailabilityState>();

			AvailabilityState availabilityState1
				= new AvailabilityState("sometimes available");
			curationService.addAvailabilityState(jsmith, availabilityState1);
			
			AvailabilityState availabilityState2
				= new AvailabilityState("always available");
			curationService.addAvailabilityState(jsmith, availabilityState2);
			
			AvailabilityState availabilityState3
				= new AvailabilityState("never available");
			curationService.addAvailabilityState(jsmith, availabilityState3);

			AvailabilityState availabilityState4
				= new AvailabilityState("maybe available");
			curationService.addAvailabilityState(jsmith, availabilityState4);

			ArrayList<AvailabilityState> availabilityStatesSoFar
				= curationService.getAvailabilityStates(jsmith);
			sorter.sort(availabilityStatesSoFar);
			assertEquals(4, availabilityStatesSoFar.size());
			
			int identifier1
				= curationService.getAvailabilityStateIdentifier(jsmith, null, availabilityState1);			
			availabilityState1.setIdentifier(identifier1);
			int identifier2
				= curationService.getAvailabilityStateIdentifier(jsmith, null, availabilityState2);
			availabilityState2.setIdentifier(identifier2);
			ArrayList<AvailabilityState> availabilityStatesToDelete
				= new ArrayList<AvailabilityState>();
			availabilityStatesToDelete.add(availabilityState1);
			availabilityStatesToDelete.add(availabilityState2);
			
			curationService.deleteAvailabilityStates(jsmith, availabilityStatesToDelete);
			
			ArrayList<AvailabilityState> availabilityStatesSoFar2
				= curationService.getAvailabilityStates(jsmith);
			sorter.sort(availabilityStatesSoFar2);
			assertEquals(2, availabilityStatesSoFar2.size());
			
			AvailabilityState firstState
				= availabilityStatesSoFar2.get(0);
			assertEquals("maybe available", firstState.getName());
			
			AvailabilityState secondState
				= availabilityStatesSoFar2.get(1);

			assertEquals("never available", secondState.getName());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}		

	public void testDeleteAvailabilityStateE1() {
		try {
			
			TestResultSorter<AvailabilityState> sorter
				= new TestResultSorter<AvailabilityState>();

			AvailabilityState availabilityState1
				= new AvailabilityState("available");
			curationService.addAvailabilityState(jsmith, availabilityState1);

			AvailabilityState availabilityState2
				= new AvailabilityState("not available");
			
			ArrayList<AvailabilityState> availabilityStatesSoFar
				= curationService.getAvailabilityStates(jsmith);
			sorter.sort(availabilityStatesSoFar);
			assertEquals(1, availabilityStatesSoFar.size());

			ArrayList<AvailabilityState> availabilityStatesToDelete
				= new ArrayList<AvailabilityState>();
			availabilityStatesToDelete.add(availabilityState2);
			
			curationService.deleteAvailabilityStates(jsmith, availabilityStatesToDelete);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors 
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_AVAILABILITY_STATE);
			assertEquals(1, numberOfErrors);
		}		
	}		

	//cleaning state test cases	
	public void testAddCleaningStateN1() {
		try {
			
			TestResultSorter<CleaningState> sorter
				= new TestResultSorter<CleaningState>();
			
			//test normal behaviour
			CleaningState cleaningState1
				= new CleaningState("clean");
			curationService.addCleaningState(jsmith, cleaningState1);
			
			CleaningState cleaningState2
				= new CleaningState("not very clean");
			curationService.addCleaningState(jsmith, cleaningState2);
			
			CleaningState cleaningState3
				= new CleaningState("downright dirty");
			curationService.addCleaningState(jsmith, cleaningState3);
			
			ArrayList<CleaningState> cleaningStatesSoFar
				= curationService.getCleaningStates(jsmith);
			
			sorter.sort(cleaningStatesSoFar);
			
			int numberOfStates = cleaningStatesSoFar.size();
			assertEquals(3, numberOfStates);
			
			String firstState
				= cleaningStatesSoFar.get(0).getDisplayName();
			assertEquals("clean", firstState);
			String secondState
				= cleaningStatesSoFar.get(1).getDisplayName();
			assertEquals("downright dirty", secondState);
			String thirdState
				= cleaningStatesSoFar.get(2).getDisplayName();
			assertEquals("not very clean", thirdState);					
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}

	public void testAddCleaningStateE1() {
		try {
			TestResultSorter<CleaningState> sorter
				= new TestResultSorter<CleaningState>();
			
			//test abnormal behaviour
			//add the same item but in different cases.  This is unusual
			//but legally allowed.
			CleaningState cleaningState1
				= new CleaningState("clean");
			curationService.addCleaningState(jsmith, cleaningState1);
			CleaningState cleaningState2
				= new CleaningState("CLEAN");
			curationService.addCleaningState(jsmith, cleaningState2);
			ArrayList<CleaningState> cleaningStatesSoFar
				= curationService.getCleaningStates(jsmith);
			sorter.sort(cleaningStatesSoFar);
			assertEquals(2, cleaningStatesSoFar.size());
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.DUPLICATE_CLEANING_STATE);
			assertEquals(1, numberOfErrors);
		}		
	}

	public void testAddCleaningStateE2() {
		try {
			TestResultSorter<CleaningState> sorter
				= new TestResultSorter<CleaningState>();
			
			CleaningState cleaningState1
				= new CleaningState("clean");
			curationService.addCleaningState(jsmith, cleaningState1);
			CleaningState cleaningState2
				= new CleaningState("clean");
			curationService.addCleaningState(jsmith, cleaningState2);
			ArrayList<CleaningState> cleaningStatesSoFar
				= curationService.getCleaningStates(jsmith);
			sorter.sort(cleaningStatesSoFar);
			assertEquals(2, cleaningStatesSoFar.size());
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int actualNumberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.DUPLICATE_CLEANING_STATE);
			assertEquals(1, actualNumberOfErrors);
		}
	}

	public void testUpdateCleaningStateN1() {
		try {
			
			TestResultSorter<CleaningState> sorter
				= new TestResultSorter<CleaningState>();

			CleaningState cleaningState1
				= new CleaningState("clean");
			curationService.addCleaningState(jsmith, cleaningState1);
			CleaningState cleaningState2
				= new CleaningState("downright dirty");
			curationService.addCleaningState(jsmith, cleaningState2);
			int identifier
				= curationService.getCleaningStateIdentifier(jsmith, null, cleaningState2);
			cleaningState2.setIdentifier(identifier);
			cleaningState2.setName("cleaner");
			curationService.updateCleaningState(jsmith, cleaningState2);

			ArrayList<CleaningState> cleaningStatesSoFar
				= curationService.getCleaningStates(jsmith);
			sorter.sort(cleaningStatesSoFar);
			assertEquals(2, cleaningStatesSoFar.size());
			
			String actualValue1
				= cleaningStatesSoFar.get(0).getName();
			assertEquals("clean", actualValue1);
			
			String actualValue2
				= cleaningStatesSoFar.get(1).getName();
			assertEquals("cleaner", actualValue2);
			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}		

	public void testDeleteCleaningStateN1() {
		try {
			
			TestResultSorter<CleaningState> sorter
				= new TestResultSorter<CleaningState>();

			CleaningState cleaningState1
				= new CleaningState("clean");
			curationService.addCleaningState(jsmith, cleaningState1);
			
			CleaningState cleaningState2
				= new CleaningState("dirty");
			curationService.addCleaningState(jsmith, cleaningState2);
			
			CleaningState cleaningState3
				= new CleaningState("semi-processed");
			curationService.addCleaningState(jsmith, cleaningState3);

			ArrayList<CleaningState> cleaningStatesSoFar
				= curationService.getCleaningStates(jsmith);
			sorter.sort(cleaningStatesSoFar);
			assertEquals(3, cleaningStatesSoFar.size());
			
			int identifier1
				= curationService.getCleaningStateIdentifier(jsmith, null, cleaningState1);			
			cleaningState1.setIdentifier(identifier1);

			ArrayList<CleaningState> cleaningStatesToDelete
				= new ArrayList<CleaningState>();
			cleaningStatesToDelete.add(cleaningState1);
			
			curationService.deleteCleaningStates(jsmith, cleaningStatesToDelete);
			
			ArrayList<CleaningState> cleaningStatesSoFar2
				= curationService.getCleaningStates(jsmith);
			sorter.sort(cleaningStatesSoFar2);
			assertEquals(2, cleaningStatesSoFar2.size());
			
			CleaningState firstState
				= cleaningStatesSoFar2.get(0);
			assertEquals("dirty", firstState.getName());
			
			CleaningState secondState
				= cleaningStatesSoFar2.get(1);

			assertEquals("semi-processed", secondState.getName());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}		
	
	public void testDeleteCleaningStateE1() {
		try {
			
			TestResultSorter<CleaningState> sorter
				= new TestResultSorter<CleaningState>();

			CleaningState cleaningState1
				= new CleaningState("clean");
			curationService.addCleaningState(jsmith, cleaningState1);
			
			CleaningState cleaningState2
				= new CleaningState("dirty");
			cleaningState2.setIdentifier(7896);
		
			ArrayList<CleaningState> cleaningStatesSoFar
				= curationService.getCleaningStates(jsmith);
			sorter.sort(cleaningStatesSoFar);
			assertEquals(1, cleaningStatesSoFar.size());
			
			ArrayList<CleaningState> cleaningStatesToDelete
				= new ArrayList<CleaningState>();
			cleaningStatesToDelete.add(cleaningState2);
			
			curationService.deleteCleaningStates(jsmith, cleaningStatesToDelete);
			fail();			
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors 
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_CLEANING_STATE);
			assertEquals(1, numberOfErrors);
		}		
	}		
	
	//test cases for categories
	public void testAddCategoryN1() {
		try {
			
			TestResultSorter<Category> sorter
				= new TestResultSorter<Category>();
			
			//test normal behaviour
			Category category1
				= new Category("Household Structure");
			curationService.addCategory(jsmith, category1);
			
			Category category2
				= new Category("Education");
			curationService.addCategory(jsmith, category2);
			
			Category category3
				= new Category("Social Class");
			curationService.addCategory(jsmith, category3);
			
			ArrayList<Category> categoriesSoFar
				= curationService.getCategories(jsmith);
			
			sorter.sort(categoriesSoFar);
			
			int numberOfStates = categoriesSoFar.size();
			assertEquals(3, numberOfStates);
			
			String firstState
				= categoriesSoFar.get(0).getDisplayName();
			assertEquals("Education", firstState);
			String secondState
				= categoriesSoFar.get(1).getDisplayName();
			assertEquals("Household Structure", secondState);
			String thirdState
				= categoriesSoFar.get(2).getDisplayName();
			assertEquals("Social Class", thirdState);					
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}

	public void testAddCategoriesE1() {
		try {
			Category category1
				= new Category("Household Structure");
			curationService.addCategory(jsmith, category1);
		
			Category category2
				= new Category("Education");
			curationService.addCategory(jsmith, category2);

			ArrayList<Category> categoriesSoFar
				= curationService.getCategories(jsmith);
			assertEquals(2, categoriesSoFar.size());

			Category category3
				= new Category("Education");
			curationService.addCategory(jsmith, category3);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.DUPLICATE_CATEGORY);
			assertEquals(1, numberOfErrors);
		}		
	}

	public void testAddCategoriesE2() {
		try {
			
			Category category1
				= new Category("Household Structure");
			curationService.addCategory(jsmith, category1);
	
			Category category2
				= new Category("HOUSEHOLD STRUCTURE");
			curationService.addCategory(jsmith, category2);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int actualNumberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.DUPLICATE_CATEGORY);
			assertEquals(1, actualNumberOfErrors);
		}
	}

	public void testUpdateCategoryN1() {
		try {
			
			TestResultSorter<Category> sorter
				= new TestResultSorter<Category>();

			Category category1
				= new Category("Household Structure");
			curationService.addCategory(jsmith, category1);
			Category category2
				= new Category("Education");
			curationService.addCategory(jsmith, category2);
			int identifier
				= curationService.getCategoryIdentifier(jsmith, null, category2);
			category2.setIdentifier(identifier);
			category2.setName("Social Class");
			curationService.updateCategory(jsmith, category2);

			ArrayList<Category> categoriesSoFar
				= curationService.getCategories(jsmith);
			sorter.sort(categoriesSoFar);
			assertEquals(2, categoriesSoFar.size());
			
			String actualValue1
				= categoriesSoFar.get(0).getName();
			assertEquals("Household Structure", actualValue1);
			
			String actualValue2
				= categoriesSoFar.get(1).getName();
			assertEquals("Social Class", actualValue2);
			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}		

	public void testDeleteCategoryN1() {
		try {
			
			TestResultSorter<Category> sorter
				= new TestResultSorter<Category>();

			Category category1
				= new Category("Household Structure");
			curationService.addCategory(jsmith, category1);
		
			Category category2
				= new Category("Education");
			curationService.addCategory(jsmith, category2);
		
			Category category3
				= new Category("Social Class");
			curationService.addCategory(jsmith, category3);

			int identifier1
				= curationService.getCategoryIdentifier(jsmith, null, category1);			
			category1.setIdentifier(identifier1);

			int identifier3
				= curationService.getCategoryIdentifier(jsmith, null, category3);			
			category3.setIdentifier(identifier3);
			
			ArrayList<Category> categoriesToDelete
				= new ArrayList<Category>();
			categoriesToDelete.add(category1);
			categoriesToDelete.add(category3);
			
			curationService.deleteCategories(jsmith, categoriesToDelete);
			
			ArrayList<Category> categoriesSoFar2
				= curationService.getCategories(jsmith);
			sorter.sort(categoriesSoFar2);
			assertEquals(1, categoriesSoFar2.size());
			
			Category firstCategory
				= categoriesSoFar2.get(0);
			assertEquals("Education", firstCategory.getName());			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}		

	public void testDeleteCategoryE1() {
		try {
			
			TestResultSorter<Category> sorter
				= new TestResultSorter<Category>();

			Category category1
				= new Category("Household Structure");
			curationService.addCategory(jsmith, category1);
	
			Category category2
				= new Category("Education");
			curationService.addCategory(jsmith, category2);
			category2.setIdentifier(556);
			
			ArrayList<Category> categoriesSoFar
				= curationService.getCategories(jsmith);
			sorter.sort(categoriesSoFar);
			assertEquals(2, categoriesSoFar.size());
			
			ArrayList<Category> categoriesToDelete
				= new ArrayList<Category>();
			categoriesToDelete.add(category2);
			
			curationService.deleteCategories(jsmith, categoriesToDelete);
			fail();			
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors 
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_CATEGORY);
			assertEquals(1, numberOfErrors);
		}		
	}		

	//test cases for data libraries
	
	public void testAddAliasFilePathN1() {
		try {
			TestResultSorter<AliasFilePath> sorter
				= new TestResultSorter<AliasFilePath>();
			
			//test normal behaviour
			AliasFilePath aliasFilePath1
				= new AliasFilePath("ddd", "$DDD");
			curationService.addAliasFilePath(jsmith, aliasFilePath1);
			
			AliasFilePath aliasFilePath2
				= new AliasFilePath("ggg", "$GGG");
			curationService.addAliasFilePath(jsmith, aliasFilePath2);
			
			AliasFilePath aliasFilePath3
				= new AliasFilePath("hhh", "$HHH");
			curationService.addAliasFilePath(jsmith, aliasFilePath3);
			
			ArrayList<AliasFilePath> aliasFilePathsSoFar
				= curationService.getAliasFilePaths(jsmith);
			
			sorter.sort(aliasFilePathsSoFar);
			
			int numberOfStates = aliasFilePathsSoFar.size();
			assertEquals(3, numberOfStates);
			
			String firstAliasFilePath
				= aliasFilePathsSoFar.get(0).getDisplayName();
			assertEquals("ddd", firstAliasFilePath);
			String secondAliasFilePath
				= aliasFilePathsSoFar.get(1).getDisplayName();
			assertEquals("ggg", secondAliasFilePath);
			String thirdAliasFilePath
				= aliasFilePathsSoFar.get(2).getDisplayName();
			assertEquals("hhh", thirdAliasFilePath);					
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}

	public void testAddAliasFilePathsE1() {
		try {		
			AliasFilePath aliasFilePath1
				= new AliasFilePath("ddd", "$DDD");
			curationService.addAliasFilePath(jsmith, aliasFilePath1);
		
			AliasFilePath aliasFilePath2
				= new AliasFilePath("ggg", "$GGG");
			curationService.addAliasFilePath(jsmith, aliasFilePath2);

			AliasFilePath aliasFilePath3
				= new AliasFilePath("ddd", "$DDD");
			curationService.addAliasFilePath(jsmith, aliasFilePath3);
			fail();			
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.DUPLICATE_ALIAS_FILE_PATH);
			assertEquals(1, numberOfErrors);
		}		
	}

	public void testAddAliasFilePathsE2() {
		try {
			AliasFilePath aliasFilePath1
				= new AliasFilePath("ddd", "$ddd");
			curationService.addAliasFilePath(jsmith, aliasFilePath1);
	
			AliasFilePath aliasFilePath2
				= new AliasFilePath("ddd", "$DDD");
			curationService.addAliasFilePath(jsmith, aliasFilePath2);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int actualNumberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.DUPLICATE_ALIAS_FILE_PATH);
			assertEquals(1, actualNumberOfErrors);
		}
	}

	public void testUpdateAliasFilePathN1() {
		try {

			TestResultSorter<AliasFilePath> sorter
				= new TestResultSorter<AliasFilePath>();
			
			AliasFilePath aliasFilePath1
				= new AliasFilePath("ddd", "$ddd");
			curationService.addAliasFilePath(jsmith, aliasFilePath1);

			AliasFilePath aliasFilePath2
				= new AliasFilePath("eee", "$eee");
			curationService.addAliasFilePath(jsmith, aliasFilePath2);
			int identifier = curationService.getAliasFilePathIdentifier(jsmith, null, aliasFilePath2);
			aliasFilePath2.setIdentifier(identifier);
			aliasFilePath2.setAlias("fff");
			curationService.updateAliasFilePath(jsmith, aliasFilePath2);			
			
			ArrayList<AliasFilePath> aliasFilePathsSoFar
				= curationService.getAliasFilePaths(jsmith);
			sorter.sort(aliasFilePathsSoFar);
			assertEquals(2, aliasFilePathsSoFar.size());
			
			String actualValue1
				= aliasFilePathsSoFar.get(0).getAlias();
			assertEquals("ddd", actualValue1);
			
			String actualValue2
				= aliasFilePathsSoFar.get(1).getAlias();
			assertEquals("fff", actualValue2);
			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}		

	public void testDeleteAliasFilePathN1() {
		try {			
			TestResultSorter<AliasFilePath> sorter
				= new TestResultSorter<AliasFilePath>();
	
			AliasFilePath aliasFilePath1
				= new AliasFilePath("ddd", "$DDD");
			curationService.addAliasFilePath(jsmith, aliasFilePath1);
			
			AliasFilePath aliasFilePath2
				= new AliasFilePath("ggg", "$GGG");
			curationService.addAliasFilePath(jsmith, aliasFilePath2);
			int identifier2 = curationService.getAliasFilePathIdentifier(jsmith, null, aliasFilePath2);
			aliasFilePath2.setIdentifier(identifier2);
			
			AliasFilePath aliasFilePath3
				= new AliasFilePath("jjj", "$JJJ");
			curationService.addAliasFilePath(jsmith, aliasFilePath3);

			ArrayList<AliasFilePath> aliasFilePathsToDelete
				= new ArrayList<AliasFilePath>();
			aliasFilePathsToDelete.add(aliasFilePath2);
			
			curationService.deleteAliasFilePaths(jsmith, aliasFilePathsToDelete);
			
			ArrayList<AliasFilePath> aliasFilePathsSoFar2
				= curationService.getAliasFilePaths(jsmith);
			sorter.sort(aliasFilePathsSoFar2);
			assertEquals(2, aliasFilePathsSoFar2.size());
			
			AliasFilePath firstAliasFilePath
				= aliasFilePathsSoFar2.get(0);
			assertEquals("ddd", firstAliasFilePath.getAlias());			
			
			AliasFilePath secondAliasFilePath
				= aliasFilePathsSoFar2.get(1);
			assertEquals("jjj", secondAliasFilePath.getAlias());
		
		}
		catch(MacawException exception) {
			exception.printErrors();

			log.logException(exception);
			fail();
		}		
	}		

	public void testDeleteAliasFilePathE1() {
		try {
			TestResultSorter<AliasFilePath> sorter
				= new TestResultSorter<AliasFilePath>();

			AliasFilePath aliasFilePath1
				= new AliasFilePath("ddd", "$DDD");
			curationService.addAliasFilePath(jsmith, aliasFilePath1);
		
			AliasFilePath aliasFilePath2
				= new AliasFilePath("ggg", "$GGG");
			aliasFilePath2.setIdentifier(98340);
			
			ArrayList<AliasFilePath> aliasFilePathsSoFar
				= curationService.getAliasFilePaths(jsmith);
			sorter.sort(aliasFilePathsSoFar);
			assertEquals(1, aliasFilePathsSoFar.size());
			
			ArrayList<AliasFilePath> aliasFilePathsToDelete
				= new ArrayList<AliasFilePath>();
			aliasFilePathsToDelete.add(aliasFilePath2);
			
			curationService.deleteAliasFilePaths(jsmith, aliasFilePathsToDelete);
			fail();			
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors 
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_ALIAS_FILE_PATH);
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

