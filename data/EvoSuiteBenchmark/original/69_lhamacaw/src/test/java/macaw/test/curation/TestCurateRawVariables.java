package macaw.test.curation;

import java.util.ArrayList;

import macaw.businessLayer.*;
import macaw.system.*;


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

public class TestCurateRawVariables extends MacawCurationTestCase {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private RawVariable ht87;
	private RawVariable wt87;
	private RawVariable dep78;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public TestCurateRawVariables() {
		super("Test variables");
		
		ht87 = new RawVariable();
		ht87.setName("HT87");
		ht87.setLabel("Height in 1987");
		ht87.setCategory("General Health");
		ht87.setCoded(true);
		ht87.setCleaned(true);
		ht87.setCleaningStatus("cleaned and verified");
		ht87.setAvailability("in-house staff");
		ht87.setYear("1987");
		ht87.setAlias("bbb");
		ht87.setFilePath("$BBB/other");
		ht87.setForm("5a");
		ht87.setQuestionNumber("3i");
		ht87.setCodeBookNumber("B7");
		ht87.setColumnStart("40");
		ht87.setColumnEnd("45");
		
		wt87 = new RawVariable();
		wt87.setName("WT87");
		wt87.setLabel("weight in 1987");
		wt87.setCategory("General Health");
		wt87.setCoded(true);
		wt87.setCleaned(true);
		wt87.setCleaningStatus("clean");
		wt87.setAvailability("unrestricted");
		wt87.setYear("1987");
		wt87.setAlias("bbb");
		wt87.setFilePath("$BBB/other");
		wt87.setForm("8");
		wt87.setQuestionNumber("1.2");

		dep78 = new RawVariable();
		dep78.setName("DEP78");
		dep78.setLabel("Depression score in 1978");
		dep78.setCategory("Wellbeing");
		dep78.setCoded(false);
		dep78.setCleaned(false);
		dep78.setAvailability("unrestricted");
		dep78.setYear("1992");
		dep78.setAlias("bbb");
		dep78.setFilePath("$BBB/other");
		dep78.setForm("55");
		dep78.setQuestionNumber("6vi");
		
	}
	
	
	protected void setUp() throws Exception {
		curationService.clear(admin);
		
		//adding a few availability states
		AvailabilityState availabilityState1
			= new AvailabilityState("collaborators");
		curationService.addAvailabilityState(jsmith, availabilityState1);
		AvailabilityState availabilityState2
			= new AvailabilityState("in-house staff");
		curationService.addAvailabilityState(jsmith, availabilityState2);
		AvailabilityState availabilityState3
			= new AvailabilityState("unrestricted");
		curationService.addAvailabilityState(jsmith, availabilityState3);
		
		//adding cleaning states
		CleaningState cleaningState1 = new CleaningState("clean");
		curationService.addCleaningState(jsmith, cleaningState1);
		CleaningState cleaningState2 = new CleaningState("cleaned and verified");
		curationService.addCleaningState(jsmith, cleaningState2);
		CleaningState cleaningState3 = new CleaningState("semi-cleaned");
		curationService.addCleaningState(jsmith, cleaningState3);
		
		//adding a few categories
		Category category1 = new Category("General Health");
		curationService.addCategory(jsmith, category1);
		Category category2 = new Category("Wellbeing");
		curationService.addCategory(jsmith, category2);
		Category category3 = new Category("Respiratory Health");
		curationService.addCategory(jsmith, category3);
		
		//adding a few data libraries
		AliasFilePath aliasFilePath1 = new AliasFilePath("bbb", "$BBB/other");
		curationService.addAliasFilePath(jsmith, aliasFilePath1);
		AliasFilePath aliasFilePath2 = new AliasFilePath("fff", "$FFF/lha");
		curationService.addAliasFilePath(jsmith, aliasFilePath2);
		AliasFilePath aliasFilePath3 = new AliasFilePath("hhh", "$HHH/general");
		curationService.addAliasFilePath(jsmith, aliasFilePath3);
		AliasFilePath aliasFilePath4 = new AliasFilePath("A2", "$rty/height-87-1.txt");
		curationService.addAliasFilePath(jsmith, aliasFilePath4);

	}

	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	
	/**
	 * check that name is not blank
	 */
	public void testRawVariableValidationN1() {
		try {
			RawVariable rawVariable1 = (RawVariable) ht87.clone();
			rawVariable1.setName("");
			curationService.addRawVariable(jsmith, rawVariable1);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.INVALID_RAW_VARIABLE);
			assertEquals(1, numberOfErrors);
		}
	}

	/**
	 * check that label is not blank
	 */
	/*
	public void testRawVariableValidationN2() {
		try {
		    RawVariable rawVariable1 = (RawVariable) ht87.clone();
			rawVariable1.setLabel("");
			database.addRawVariable(jsmith, rawVariable1);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.INVALID_RAW_VARIABLE);
			assertEquals(1, numberOfErrors);
		}
	}
	*/
	
	/**
	 * check that category is not blank
	 */
	/*
	public void testRawVariableValidationN3() {
		try {
			RawVariable rawVariable1 = (RawVariable) ht87.clone();
			rawVariable1.setCategory("");
			database.addRawVariable(jsmith, rawVariable1);			
			fail();
		}
		catch(MacawException exception) {
			exception.printErrors();

			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.INVALID_RAW_VARIABLE);
			assertEquals(1, numberOfErrors);
		}
	}
	*/
	
	/**
	 * check that if is cleaned is true, cleaning status is not blank
	 */
	public void testRawVariableValidationN4() {
		try {
			RawVariable rawVariable1 = (RawVariable) ht87.clone();
			rawVariable1.setCleaned(true);
			rawVariable1.setCleaningStatus("");
			
			curationService.addRawVariable(jsmith, rawVariable1);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.INVALID_RAW_VARIABLE);
			assertEquals(1, numberOfErrors);
		}
	}

	/**
	 * test that availability is not blank
	 */
	public void testRawVariableValidationN5() {
		try {
			RawVariable rawVariable1 = (RawVariable) ht87.clone();
			rawVariable1.setAvailability("");
			curationService.addRawVariable(jsmith, rawVariable1);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.INVALID_RAW_VARIABLE);
			assertEquals(1, numberOfErrors);
		}
	}

	

	/**
	 * check that alias is not blank
	 */
	
	/*
	public void testRawVariableValidationN6() {
		try {
			RawVariable rawVariable1 = (RawVariable) ht87.clone();
			rawVariable1.setAlias("");
			
			database.addRawVariable(jsmith, rawVariable1);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.INVALID_RAW_VARIABLE);
			assertEquals(1, numberOfErrors);
		}
	}
	*/

	/**
	 * check year is not blank
	 */
	/*
	public void testRawVariableValidationN7() {
		try {
			RawVariable rawVariable1 = (RawVariable) ht87.clone();
			rawVariable1.setYear("");
			database.addRawVariable(jsmith, rawVariable1);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.INVALID_RAW_VARIABLE);
			assertEquals(1, numberOfErrors);
		}
	}
	*/
		
	/**
	 * check column start is a legal number
	 */
	/*
	public void testRawVariableValidationN12() {
		try {			
			RawVariable rawVariable1 = (RawVariable) ht87.clone();
			rawVariable1.setYear("");
			rawVariable1.setColumnStart("xxx");
			rawVariable1.setColumnEnd("20");
			database.addRawVariable(jsmith, rawVariable1);			
			fail();
		}
		catch(MacawException exception) {
			exception.printErrors();
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.INVALID_RAW_VARIABLE);
			assertEquals(1, numberOfErrors);
		}
	}
	*/

	/**
	 * check column end is a legal number
	 */
	/*
	public void testRawVariableValidationN13() {
		try {			
			RawVariable rawVariable1 = (RawVariable) ht87.clone();
			rawVariable1.setYear("");
			rawVariable1.setColumnStart("10");
			rawVariable1.setColumnEnd("xxx");

			database.addRawVariable(jsmith, rawVariable1);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.INVALID_RAW_VARIABLE);
			assertEquals(1, numberOfErrors);
		}
	}
	*/
	
	
	/**
	 * check that validation detects multiple field errors
	 */
	public void testRawVariableValidationN14() {
		try {
			RawVariable rawVariable1 = new RawVariable();
			
			//error
			rawVariable1.setName("");
			
			rawVariable1.setLabel("");
			
			rawVariable1.setCategory("");
			rawVariable1.setCoded(true);
			rawVariable1.setCleaned(true);
			//error
			rawVariable1.setCleaningStatus("");
			//error
			rawVariable1.setAvailability("");

			rawVariable1.setYear("");
			rawVariable1.setAlias("");
			rawVariable1.setFilePath("");

			rawVariable1.setForm("");
			rawVariable1.setQuestionNumber("3i");
			rawVariable1.setCodeBookNumber("44");
			
			rawVariable1.setColumnStart("xxx");
			
			rawVariable1.setColumnEnd("yyy");
			
			curationService.addRawVariable(jsmith, rawVariable1);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			exception.printErrors();
			assertEquals(3, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.INVALID_RAW_VARIABLE);
			assertEquals(3, numberOfErrors);
		}
	}

	
	public void testAddRawVariableN1() {
		
		try {			
			RawVariable rawVariable1 = (RawVariable) ht87.clone();			
			curationService.addRawVariable(jsmith, rawVariable1);

			RawVariable rawVariable2 = new RawVariable();
			rawVariable2.setName("WT87");
			rawVariable2.setLabel("weight in 1987");
			rawVariable2.setCategory("General Health");
			rawVariable2.setCoded(true);
			rawVariable2.setCleaned(true);
			rawVariable2.setCleaningStatus("clean");
			rawVariable2.setAvailability("unrestricted");
			rawVariable2.setYear("1987");
			rawVariable2.setAlias("bbb");
			rawVariable2.setFilePath("$BBB/other");
			rawVariable2.setForm("8");
			rawVariable2.setQuestionNumber("1.2");
			curationService.addRawVariable(jsmith, rawVariable2);

			RawVariable rawVariable3 = new RawVariable();
			rawVariable3.setName("DEP78");
			rawVariable3.setLabel("Depression score in 1978");
			rawVariable3.setCategory("Wellbeing");
			rawVariable3.setCoded(false);
			rawVariable3.setCleaned(false);
			rawVariable3.setAvailability("unrestricted");
			rawVariable3.setYear("1992");
			rawVariable3.setAlias("bbb");
			rawVariable3.setFilePath("$BBB/other");
			rawVariable3.setForm("55");
			rawVariable3.setQuestionNumber("6vi");
			curationService.addRawVariable(jsmith, rawVariable3);

			ArrayList<VariableSummary> variableSummariesSoFar
				= curationService.getSummaryDataForAllVariables(jsmith);
			
			TestResultSorter<VariableSummary> sorter = new TestResultSorter<VariableSummary>();
			sorter.sort(variableSummariesSoFar);
			assertEquals(3, variableSummariesSoFar.size());

			assertEquals("DEP78",
						 variableSummariesSoFar.get(0).getName());
			assertEquals("HT87",
						 variableSummariesSoFar.get(1).getName());
			assertEquals("WT87",
						 variableSummariesSoFar.get(2).getName());
		}
		catch(MacawException exception) {
			log.logException(exception);
		}
	}		


	public void testAddRawVariableA1() {
		
		try {
			RawVariable rawVariable1
				= (RawVariable) ht87.clone();			
			curationService.addRawVariable(jsmith, rawVariable1);

			ArrayList<VariableSummary> variableSummariesSoFar
				= curationService.getSummaryDataForAllVariables(jsmith);

			TestResultSorter<VariableSummary> sorter = new TestResultSorter<VariableSummary>();
			sorter.sort(variableSummariesSoFar);
			assertEquals(1, variableSummariesSoFar.size());
		}
		catch(MacawException exception) {
			log.logException(exception);
		}
	}		


	public void testAddRawVariableE1() {
		
		try {
			RawVariable rawVariable1
				= (RawVariable) ht87.clone();
			curationService.addRawVariable(jsmith, rawVariable1);

			RawVariable rawVariable2
				= (RawVariable) ht87.clone();
			curationService.addRawVariable(jsmith, rawVariable2);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors = exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.DUPLICATE_VARIABLE);
			assertEquals(1, numberOfErrors);
		}
	}		
	
	public void testUpdateRawVariableN1() {
		
		try {
			RawVariable rawVariable1
				= (RawVariable) ht87.clone();
			
			curationService.addRawVariable(jsmith, rawVariable1);
			int identifier = curationService.getRawVariableIdentifier(jsmith, rawVariable1);
			rawVariable1.setIdentifier(identifier);
			
			//adjust the name
			rawVariable1.setName("HGHT-1987");
			curationService.updateRawVariable(jsmith, rawVariable1);
			RawVariable alteredVariable1
				= (RawVariable) curationService.getOriginalVariable(jsmith, rawVariable1);	
			assertEquals("HGHT-1987", alteredVariable1.getName());

			alteredVariable1.setLabel("aaa");
			curationService.updateRawVariable(jsmith, alteredVariable1);
			RawVariable alteredVariable2
				= (RawVariable) curationService.getOriginalVariable(jsmith, alteredVariable1);	
			assertEquals("aaa", alteredVariable2.getLabel());

			alteredVariable2.setCategory("Respiratory Health");
			curationService.updateRawVariable(jsmith, alteredVariable2);
			RawVariable alteredVariable3
				= (RawVariable) curationService.getOriginalVariable(jsmith, alteredVariable2);	
			assertEquals("Respiratory Health", alteredVariable3.getCategory());

			alteredVariable3.setCoded(false);
			curationService.updateRawVariable(jsmith, alteredVariable3);
			RawVariable alteredVariable333
				= (RawVariable) curationService.getOriginalVariable(jsmith, alteredVariable3);	
			assertEquals(false, alteredVariable333.isCoded());
						
			alteredVariable333.setCleaned(true);
			alteredVariable333.setCleaningStatus("cleaned and verified");			
			curationService.updateRawVariable(jsmith, alteredVariable333);
			RawVariable alteredVariable4
				= (RawVariable) curationService.getOriginalVariable(jsmith, alteredVariable333);	
			assertEquals(true, alteredVariable4.isCleaned());
			assertEquals("cleaned and verified", alteredVariable4.getCleaningStatus());

			alteredVariable4.setAvailability("in-house staff");			
			curationService.updateRawVariable(jsmith, alteredVariable4);
			RawVariable alteredVariable6
				= (RawVariable) curationService.getOriginalVariable(jsmith, alteredVariable4);
			assertEquals("in-house staff", alteredVariable6.getAvailability());
			
			alteredVariable6.setYear("1966");
			curationService.updateRawVariable(jsmith, alteredVariable6);
			RawVariable alteredVariable7
				= (RawVariable) curationService.getOriginalVariable(jsmith, alteredVariable6);	
			assertEquals("1966", alteredVariable7.getYear());


			alteredVariable7.setAlias("hhh");
			alteredVariable7.setFilePath("$HHH/general");
			curationService.updateRawVariable(jsmith, alteredVariable7);
			RawVariable alteredVariable777
				= (RawVariable) curationService.getOriginalVariable(jsmith, alteredVariable7);	
			assertEquals("hhh", alteredVariable777.getAlias());
		
			alteredVariable777.setForm("formX");
			curationService.updateRawVariable(jsmith, alteredVariable777);
			RawVariable alteredVariable9
				= (RawVariable) curationService.getOriginalVariable(jsmith, alteredVariable777);	
			assertEquals("formX", alteredVariable9.getForm());

			alteredVariable9.setQuestionNumber("question 1");
			curationService.updateRawVariable(jsmith, alteredVariable9);
			RawVariable alteredVariable10
				= (RawVariable) curationService.getOriginalVariable(jsmith, alteredVariable9);	
			assertEquals("question 1", alteredVariable10.getQuestionNumber());

			alteredVariable10.setCodeBookNumber("767");
			curationService.updateRawVariable(jsmith, alteredVariable10);
			RawVariable alteredVariable11
				= (RawVariable) curationService.getOriginalVariable(jsmith, alteredVariable10);	
			assertEquals("767", alteredVariable11.getCodeBookNumber());
			
			alteredVariable11.setColumnStart("66");
			curationService.updateRawVariable(jsmith, alteredVariable11);
			RawVariable alteredVariable12
				= (RawVariable) curationService.getOriginalVariable(jsmith, alteredVariable11);	
			assertEquals("66", alteredVariable12.getColumnStart());

			alteredVariable12.setColumnEnd("88");
			curationService.updateRawVariable(jsmith, alteredVariable12);
			RawVariable alteredVariable13
				= (RawVariable) curationService.getOriginalVariable(jsmith, alteredVariable12);	
			assertEquals("88", alteredVariable13.getColumnEnd());
		}
		catch(MacawException exception) {
			exception.printErrors();
			log.logException(exception);
			fail();
		}
	}		

	/**
	 * detect update with an illegal category
	 */
	/*
	public void testUpdateRawVariableE1() {
		
		try {
			RawVariable rawVariable1 = (RawVariable) ht87.clone();			
			database.addRawVariable(jsmith, rawVariable1);
			int identifier = database.getRawVariableIdentifier(jsmith, rawVariable1);
			rawVariable1.setIdentifier(identifier);
			rawVariable1.setCategory("This category doesn't exist");
			database.updateRawVariable(jsmith, rawVariable1);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors = exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_CATEGORY);
			assertEquals(1, numberOfErrors);
		}
	}
	*/		

	/**
	 * detect update with an illegal cleaning state
	 */
	public void testUpdateRawVariableE2() {
		
		try {
			RawVariable rawVariable1 = (RawVariable) ht87.clone();			
			curationService.addRawVariable(jsmith, rawVariable1);
			int identifier = curationService.getRawVariableIdentifier(jsmith, rawVariable1);
			rawVariable1.setIdentifier(identifier);
			rawVariable1.setCleaned(true);
			rawVariable1.setCleaningStatus("This cleaning state doesn't exist");
			curationService.updateRawVariable(jsmith, rawVariable1);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors = exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_CLEANING_STATE);
			assertEquals(1, numberOfErrors);
		}
	}		

	/**
	 * detect update with an illegal availability state
	 */
	public void testUpdateRawVariableE3() {
		
		try {
			RawVariable rawVariable1 = (RawVariable) ht87.clone();			
			curationService.addRawVariable(jsmith, rawVariable1);
			int identifier = curationService.getRawVariableIdentifier(jsmith, rawVariable1);
			rawVariable1.setIdentifier(identifier);
			rawVariable1.setAvailability("This availability state doesn't exist");
			curationService.updateRawVariable(jsmith, rawVariable1);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors = exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_AVAILABILITY_STATE);
			assertEquals(1, numberOfErrors);
		}
	}		

	/**
	 * detect update with an illegal data library
	 */
	
	/*
	public void testUpdateRawVariableE4() {
		
		try {
			RawVariable rawVariable1 = (RawVariable) ht87.clone();			
			database.addRawVariable(jsmith, rawVariable1);
			int identifier = database.getRawVariableIdentifier(jsmith, rawVariable1);
			rawVariable1.setIdentifier(identifier);
			rawVariable1.setAlias("Xwerijlkj");
			database.updateRawVariable(jsmith, rawVariable1);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors = exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_ALIAS_FILE_PATH);
			assertEquals(1, numberOfErrors);
		}
	}		
	*/

	/**
	 * detect update with a non-existent variable
	 */
	public void testUpdateRawVariableE5() {
		
		try {
			RawVariable rawVariable1 = (RawVariable) ht87.clone();			
			curationService.addRawVariable(jsmith, rawVariable1);

			RawVariable rawVariable2 = (RawVariable) wt87.clone();
			rawVariable2.setIdentifier(3243);
			curationService.updateRawVariable(jsmith, rawVariable2);			
			fail();
		}
		catch(MacawException exception) {
			exception.printStackTrace(System.out);
			
			int totalNumberOfErrors = exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_VARIABLE);
			assertEquals(1, numberOfErrors);
		}
	}		

	public void testDeleteRawVariableN1() {
		
		try {
			RawVariable rawVariable1 = (RawVariable) ht87.clone();			
			curationService.addRawVariable(jsmith, rawVariable1);

			RawVariable rawVariable2 = (RawVariable) wt87.clone();
			curationService.addRawVariable(jsmith, rawVariable2);
			int identifier2 
				= curationService.getRawVariableIdentifier(jsmith, rawVariable2);
			rawVariable2.setIdentifier(identifier2);

			RawVariable rawVariable3 = (RawVariable) dep78.clone();
			curationService.addRawVariable(jsmith, rawVariable3);
			int identifier3
				= curationService.getRawVariableIdentifier(jsmith, rawVariable3);
			rawVariable3.setIdentifier(identifier3);
			
			ArrayList<VariableSummary> variableSummariesSoFar
				= curationService.getSummaryDataForAllVariables(jsmith);
			assertEquals(3, variableSummariesSoFar.size());
			
			ArrayList<RawVariable> itemsToDelete = new ArrayList<RawVariable>();
			itemsToDelete.add(rawVariable2);
			itemsToDelete.add(rawVariable3);
			curationService.deleteRawVariables(jsmith, itemsToDelete);
			
			variableSummariesSoFar
				= curationService.getSummaryDataForAllVariables(jsmith);
			assertEquals(1, variableSummariesSoFar.size());

			assertEquals("HT87",
						 variableSummariesSoFar.get(0).getName());
		}
		catch(MacawException exception) {
			log.logException(exception);
		}
	}		
	
	/**
	 * delete item from a one item list
	 */
	public void testDeleteRawVariableA1() {
		try {	
			RawVariable rawVariable1 = (RawVariable) ht87.clone();			
			curationService.addRawVariable(jsmith, rawVariable1);
			int identifier 
				= curationService.getRawVariableIdentifier(jsmith, rawVariable1);
			rawVariable1.setIdentifier(identifier);
			
			ArrayList<RawVariable> itemsToDelete 
				= new ArrayList<RawVariable>();
			itemsToDelete.add(rawVariable1);
			curationService.deleteRawVariables(jsmith, itemsToDelete);

			ArrayList<VariableSummary> variableSummariesSoFar
				= curationService.getSummaryDataForAllVariables(jsmith);
			assertEquals(0, variableSummariesSoFar.size());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}		

	/**
	 * detect delete applied to a non-existent variable
	 */
	public void testDeleteRawVariableE1() {		
		try {
			RawVariable rawVariable1 = (RawVariable) ht87.clone();			
			curationService.addRawVariable(jsmith, rawVariable1);

			RawVariable rawVariable2 = (RawVariable) ht87.clone();			
			rawVariable2.setIdentifier(3243);
			
			ArrayList<RawVariable> itemsToDelete
				= new ArrayList<RawVariable>();
			itemsToDelete.add(rawVariable2);
			
			curationService.deleteRawVariables(jsmith, itemsToDelete);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors = exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_VARIABLE);
			assertEquals(1, numberOfErrors);
		}
	}		
	
	public void testSetAlternativeVariable() {
		try {
			RawVariable rawVariable1 = (RawVariable) ht87.clone();			
			curationService.addRawVariable(jsmith, rawVariable1);

			RawVariable rawVariable2 = (RawVariable) ht87.clone();
			rawVariable2.setName("height87-improved");
			curationService.addRawVariable(jsmith, rawVariable2);

			RawVariable rawVariable3 = (RawVariable) wt87.clone();			
			curationService.addRawVariable(jsmith, rawVariable3);

			Variable oldAlternativeVariable
				= curationService.getAlternativeVariable(jsmith, rawVariable1);
			assertNull(oldAlternativeVariable);
			
			curationService.setAlternativeVariable(jsmith, rawVariable1, rawVariable2);
			
			Variable actualAlternativeVariable
				= curationService.getAlternativeVariable(jsmith, rawVariable1);
			assertNotNull(actualAlternativeVariable);
			assertEquals("height87-improved", actualAlternativeVariable.getName());			
		}
		catch(MacawException exception) {
			exception.printErrors();
			fail();
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

