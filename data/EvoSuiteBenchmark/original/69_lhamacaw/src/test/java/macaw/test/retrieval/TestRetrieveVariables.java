package macaw.test.retrieval;


import macaw.businessLayer.*;
import macaw.system.*;
import macaw.test.curation.TestResultSorter;

import java.util.ArrayList;
import java.util.Arrays;



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

public class TestRetrieveVariables extends MacawRetrievalTestCase {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private RawVariable ht87;
	private RawVariable wt87;
	private RawVariable dep78;
	private DerivedVariable bmi87;
	
	private Category generalHealth;
	private Category anthropometry;
	private Category mentalWellbeing;
	
	private CleaningState cleanedAndVerified;
	private CleaningState clean;

	private AvailabilityState inhouseStaff;
	private AvailabilityState unrestricted;
	
	private AliasFilePath bbb;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public TestRetrieveVariables() {
		super("Test retrieving variables");
		
		generalHealth = new Category("General Health");
		anthropometry = new Category("Anthropometry");
		mentalWellbeing = new Category("Mental Wellbeing");

		cleanedAndVerified = new CleaningState("cleaned and verified");
		clean = new CleaningState("clean");

		inhouseStaff = new AvailabilityState("in-house staff");
		unrestricted = new AvailabilityState("unrestricted");
		
		bbb = new AliasFilePath("bbb", "$lib/bbb");
		
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
		
		bmi87 = new DerivedVariable();
		bmi87.setName("BMI87");
		bmi87.setLabel("Body Mass Index 1961");
		bmi87.setCategory("General Health");
		bmi87.setCoded(true);
		bmi87.setAvailability("unrestricted");
		bmi87.setAlias("bbb");
		bmi87.setFilePath("$der/final/45pi.sv");
		bmi87.setCleaned(true);
		bmi87.setCleaningStatus("clean");
		bmi87.setYear("1961");
		bmi87.setCleaningDescription("aaa");		
			
		dep78 = new RawVariable();
		dep78.setName("DEP78");
		dep78.setLabel("Depression score in 1978");
		dep78.setCategory("Mental Wellbeing");
		dep78.setCoded(false);
		dep78.setCleaned(false);
		dep78.setAvailability("unrestricted");
		dep78.setYear("1992");
		dep78.setAlias("bbb");
		dep78.setFilePath("$BBB/other");
		dep78.setForm("55");
		dep78.setQuestionNumber("6vi");
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	
	//DONE public Variable getVariable(User user, String name) throws MacawException;
	//DONE public ArrayList<VariableSummary> getVariableSummariesForCategory(User user, String category) throws MacawException;
	//DONE public ArrayList<Category> getCategoriesForVariable(User user, String variableName) throws MacawException;
	//DONE public ArrayList<ValueLabel> getValueLabels(User user, String variableName) throws MacawException;
	//DONE public String[] getVariableNames(User user) throws MacawException;

	
	public void testGetVariableNamesN1() {
		try {
			addDefaultVariables();
			String[] variableNames
				= retrievalService.getVariableNames(jsmith);
			Arrays.sort(variableNames);
			
			assertEquals(4, variableNames.length);
			assertEquals("BMI87", variableNames[0]);
			assertEquals("DEP78", variableNames[1]);
			assertEquals("HT87", variableNames[2]);
			assertEquals("WT87", variableNames[3]);
		}
		catch(MacawException exception) {
			exception.printErrors();
			fail();
		}
	}

	public void testGetVariableNamesA1() {
		String[] variableNames
			= retrievalService.getVariableNames(jsmith);
		Arrays.sort(variableNames);			
		assertEquals(0, variableNames.length);
	}

	public void testGetVariableN1() {
		try {
			addDefaultVariables();

			Variable variable
				= retrievalService.getVariable(jsmith, "BMI87");
			assertNotNull(variable);
			assertEquals("BMI87", variable.getName());
			
		}
		catch(MacawException exception) {
			exception.printErrors();
			fail();
		}		
	}
	
	/**
	 * retrieve a non-existent variable.  This shouldn't cause an error
	 * but just a NULL result
	 */
	public void testGetVariableA1() {
		try {
			addDefaultVariables();

			Variable variable
				= retrievalService.getVariable(jsmith, "ZZZ");
			assertNull(variable);
		}
		catch(MacawException exception) {
			exception.printErrors();
			fail();
		}		
	}
	
	public void testGetValueLabelsN1() {
		try {
			addDefaultVariables();

			ArrayList<ValueLabel> valueLabels
				= retrievalService.getValueLabels(jsmith, "BMI87");
			assertEquals(4, valueLabels.size());
			
			TestResultSorter<ValueLabel> sorter = new TestResultSorter<ValueLabel>();
			sorter.sort(valueLabels);
			
			assertEquals("1-Male", valueLabels.get(0).getDisplayName());
			assertEquals("2-Female", valueLabels.get(1).getDisplayName());
			assertEquals("3-Hermaphrodite", valueLabels.get(2).getDisplayName());
			assertEquals("4-Unknown", valueLabels.get(3).getDisplayName());

		}
		catch(MacawException exception) {
			exception.printErrors();
			fail();
		}		
	}

	public void testGetValueLabelsA1() {
		try {
			addDefaultVariables();

			ArrayList<ValueLabel> valueLabels
				= retrievalService.getValueLabels(jsmith, "DEP78");
			assertEquals(0, valueLabels.size());			
		}
		catch(MacawException exception) {
			exception.printErrors();
			fail();
		}		
	}

	public void testGetVariableSummariesForCategoryN1() {
		try {
			addDefaultVariables();

			ArrayList<VariableSummary> variableSummaries
				= retrievalService.getVariableSummariesForCategory(jsmith, "General Health");
			assertEquals(3, variableSummaries.size());
			
			TestResultSorter<VariableSummary> sorter = new TestResultSorter<VariableSummary>();
			sorter.sort(variableSummaries);
			
			assertEquals("BMI87", variableSummaries.get(0).getName());
			assertEquals("HT87", variableSummaries.get(1).getName());
			assertEquals("WT87", variableSummaries.get(2).getName());	
		}
		catch(MacawException exception) {
			exception.printErrors();			
			fail();
		}		
	}

	public void testGetVariableSummariesForCategoryA1() {
		try {
			addDefaultVariables();

			ArrayList<VariableSummary> variableSummaries
				= retrievalService.getVariableSummariesForCategory(jsmith, "Anthropometry");
			assertEquals(0, variableSummaries.size());
		}
		catch(MacawException exception) {
			exception.printErrors();
			fail();
		}		
	}
	
	public void testGetVariableSummariesForCategoryA2() {
		try {
			addDefaultVariables();
			ArrayList<VariableSummary> variableSummaries
				= retrievalService.getVariableSummariesForCategory(jsmith, "XXX");
			assertEquals(0, variableSummaries.size());
		}
		catch(MacawException exception) {
			exception.printErrors();
			fail();
		}		
	}

	private void addDefaultVariables() throws MacawException {
		curationService.addCleaningState(admin, cleanedAndVerified);
		curationService.addCleaningState(admin, clean);
		
		curationService.addAvailabilityState(admin, inhouseStaff);
		curationService.addAvailabilityState(admin, unrestricted);
		
		curationService.addAliasFilePath(admin, bbb);
		
		curationService.addCategory(admin, anthropometry);
		curationService.addCategory(admin, generalHealth);
		curationService.addCategory(admin, mentalWellbeing);
		
		curationService.addRawVariable(jsmith, ht87);
		curationService.addRawVariable(jsmith, wt87);
		curationService.addRawVariable(jsmith, dep78);		
		curationService.addDerivedVariable(jsmith, bmi87);
		
		ArrayList<ValueLabel> valueLabelsForBMI87 = new ArrayList<ValueLabel>();
		ValueLabel label1 = new ValueLabel();
		label1.setValue("1");
		label1.setLabel("Male");
		label1.setMissingValue(false);
		valueLabelsForBMI87.add(label1);

		ValueLabel label2 = new ValueLabel();
		label2.setValue("2");
		label2.setLabel("Female");
		label2.setMissingValue(false);
		valueLabelsForBMI87.add(label2);

		ValueLabel label3 = new ValueLabel();
		label3.setValue("3");
		label3.setLabel("Hermaphrodite");
		label3.setMissingValue(false);
		valueLabelsForBMI87.add(label3);

		ValueLabel label4 = new ValueLabel();
		label4.setValue("4");
		label4.setLabel("Unknown");
		label4.setMissingValue(true);
		valueLabelsForBMI87.add(label4);

		curationService.addValueLabels(jsmith, bmi87, valueLabelsForBMI87);
	}
	
	public void testGetCategoriesForVariableN1() {
		try {
			addDefaultVariables();
			ArrayList<Category> categories
				= retrievalService.getCategoriesForVariable(jsmith, "HT87");			
			assertEquals(1, categories.size());
			assertEquals("General Health", categories.get(0).getName());

			categories
				= retrievalService.getCategoriesForVariable(jsmith, "DEP78");			
			assertEquals(1, categories.size());
			assertEquals("Mental Wellbeing", categories.get(0).getName());
		}
		catch(MacawException exception) {
			exception.printErrors();
			fail();
		}	
	}
	
	public void testGetCategoriesForVariableA1() {
		try {
			addDefaultVariables();
			ArrayList<Category> categories
				= retrievalService.getCategoriesForVariable(jsmith, "XXX");	
			assertEquals(0, categories.size());
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

