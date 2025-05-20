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

public class TestCurateValueLabels extends MacawCurationTestCase {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private RawVariable rawVariable1;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public TestCurateValueLabels() {
		super("Test Value Label Manager");
	}
	
	
	protected void setUp() throws Exception {
		curationService.clear(admin);

		Category category3 = new Category("Anthropometry");
		curationService.addCategory(jsmith, category3);

		AvailabilityState availabilityState11
			= new AvailabilityState("unrestricted");
		curationService.addAvailabilityState(jsmith, availabilityState11);
		AliasFilePath aliasFilePath4 = new AliasFilePath("fff", "$FFF/lha");
		curationService.addAliasFilePath(jsmith, aliasFilePath4);

		rawVariable1 = new RawVariable();
		rawVariable1.setName("ETHNICITY-88");
		rawVariable1.setLabel("Ethnicity of survey participant");
		rawVariable1.setCategory("Anthropometry");
		rawVariable1.setCleaned(false);
		rawVariable1.setAvailability("unrestricted");
		rawVariable1.setYear("1992");
		rawVariable1.setAlias("fff");
		rawVariable1.setFilePath("$FFF/lha");
		rawVariable1.setForm("2");
		rawVariable1.setQuestionNumber("3j");
		rawVariable1.setCodeBookNumber("4");
		rawVariable1.setColumnStart("13");
		rawVariable1.setColumnEnd("87");
		curationService.addRawVariable(jsmith, rawVariable1);
	}

	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	
	/**
	 * reject value label that has a blank value
	 */
	/*
	public void testFieldValidationN1() {
		try {			
				
			ArrayList<ValueLabel> valueLabels = new ArrayList<ValueLabel>();
			
			ValueLabel valueLabel1 = new ValueLabel();
			valueLabel1.setValue("");
			valueLabel1.setLabel("Irish");
			valueLabel1.setMissingValue(false);
			valueLabels.add(valueLabel1);
			
			database.addValueLabels(jsmith, 
									rawVariable1,
									valueLabels);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.INVALID_VALUE_LABEL);
			assertEquals(1, numberOfErrors);
		}		
	}
	*/

	/**
	 * reject value label that has a blank value
	 */
	/*
	public void testFieldValidationN2() {
		try {			

			ArrayList<ValueLabel> valueLabels = new ArrayList<ValueLabel>();
			
			ValueLabel valueLabel1 = new ValueLabel();
			valueLabel1.setValue("C1");
			valueLabel1.setLabel("");
			valueLabel1.setMissingValue(false);
			valueLabels.add(valueLabel1);
			
			database.addValueLabels(jsmith, 
									rawVariable1,
									valueLabels);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.INVALID_VALUE_LABEL);
			assertEquals(1, numberOfErrors);
		}		
	}
	*/
	
	public void testAddValueLabelN1() {
		try {			

			TestResultSorter<ValueLabel> sorter = new TestResultSorter<ValueLabel>();
			
			ArrayList<ValueLabel> valueLabels = new ArrayList<ValueLabel>();
			
			ValueLabel valueLabel1 = new ValueLabel();
			valueLabel1.setValue("1C");
			valueLabel1.setLabel("Irish");
			valueLabel1.setMissingValue(false);
			valueLabels.add(valueLabel1);
			
			ValueLabel valueLabel2 = new ValueLabel();
			valueLabel2.setValue("3A");
			valueLabel2.setLabel("Indian");
			valueLabel2.setMissingValue(false);
			valueLabels.add(valueLabel2);
			
			ValueLabel valueLabel3 = new ValueLabel();
			valueLabel3.setValue("4B");
			valueLabel3.setLabel("African");
			valueLabel3.setMissingValue(false);
			valueLabels.add(valueLabel3);

			ValueLabel valueLabel4 = new ValueLabel();
			valueLabel4.setValue("99");
			valueLabel4.setLabel("Not Known");
			valueLabel4.setMissingValue(true);
			valueLabels.add(valueLabel4);

			curationService.addValueLabels(jsmith, 
									rawVariable1,
									valueLabels);
			
			ArrayList<ValueLabel> valueLabelsSoFar
				= curationService.getValueLabels(jsmith, rawVariable1);
			sorter.sort(valueLabelsSoFar);
			assertEquals(4, valueLabelsSoFar.size());			
			assertEquals("1C-Irish",
						 valueLabelsSoFar.get(0).getDisplayName());
			assertEquals("3A-Indian",
					 valueLabelsSoFar.get(1).getDisplayName());
			assertEquals("4B-African",
					 valueLabelsSoFar.get(2).getDisplayName());
			assertEquals("99-Not Known",
					 valueLabelsSoFar.get(3).getDisplayName());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}
	
	/**
	 * adding duplicate value labels causes error
	 */
	/*
	public void testAddValueLabelE1() {
		try {			
			ArrayList<ValueLabel> valueLabels = new ArrayList<ValueLabel>();
			
			ValueLabel valueLabel1 = new ValueLabel();
			valueLabel1.setValue("1C");
			valueLabel1.setLabel("Irish");
			valueLabel1.setMissingValue(false);
			valueLabels.add(valueLabel1);
			
			ValueLabel valueLabel2 = new ValueLabel();
			valueLabel2.setValue("1C");
			valueLabel2.setLabel("Indian");
			valueLabel2.setMissingValue(false);
			valueLabels.add(valueLabel2);
			
			database.addValueLabels(jsmith, 
									rawVariable1,
									valueLabels);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.DUPLICATE_VALUE_LABELS_WITHIN_LIST);
			assertEquals(1, numberOfErrors);
			log.logException(exception);
		}		
	}
	*/

	/**
	 * detect error when two value labels have the same label and are within the same
	 * list of values that are meant to be added.
	 */
	/*
	public void testAddValueLabelE2() {
		try {			
			RawVariable ethnicity = new RawVariable();
			ethnicity.setIdentifier(2);
			ethnicity.setName("ETHNICITY-88");
			ethnicity.setLabel("Ethnicity of survey participant");
					
			ArrayList<ValueLabel> valueLabels = new ArrayList<ValueLabel>();
			
			ValueLabel valueLabel1 = new ValueLabel();
			valueLabel1.setValue("1B");
			valueLabel1.setLabel("Indian");
			valueLabel1.setMissingValue(false);
			valueLabels.add(valueLabel1);
			
			ValueLabel valueLabel2 = new ValueLabel();
			valueLabel2.setValue("1C");
			valueLabel2.setLabel("Indian");
			valueLabel2.setMissingValue(false);
			valueLabels.add(valueLabel2);
			
			database.addValueLabels(jsmith, 
									rawVariable1,
									valueLabels);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();			
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.DUPLICATE_VALUE_LABELS_WITHIN_LIST);
			assertEquals(1, numberOfErrors);
			log.logException(exception);
		}		
	}
	*/

	/**
	 * you've added value labels and now you're about to add another batch.
	 * check for duplicate values in the new batch against the database.
	 */
	/*
	public void testAddValueLabelE3() {
		try {			
			ArrayList<ValueLabel> valueLabels1 = new ArrayList<ValueLabel>();
			
			ValueLabel valueLabel1 = new ValueLabel();
			valueLabel1.setValue("1B");
			valueLabel1.setLabel("Indian");
			valueLabel1.setMissingValue(false);
			valueLabels1.add(valueLabel1);

			database.addValueLabels(jsmith, 
									rawVariable1,
									valueLabels1);

			
			ArrayList<ValueLabel> valueLabels2 = new ArrayList<ValueLabel>();

			ValueLabel valueLabel2 = new ValueLabel();
			valueLabel2.setValue("1B");
			valueLabel2.setLabel("Caribbean");
			valueLabel2.setMissingValue(false);
			valueLabels2.add(valueLabel2);
			database.addValueLabels(jsmith, 
									rawVariable1,
									valueLabels2);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();			
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.DUPLICATE_VALUE_LABEL);
			assertEquals(1, numberOfErrors);
			log.logException(exception);
		}		
	}
	*/

	/**
	 * you've added value labels and now you're about to add another batch.
	 * check for duplicate values in the new batch against the database.
	 */
	
	/*
	public void testAddValueLabelE4() {
		try {			
			RawVariable ethnicity = new RawVariable();
			ethnicity.setIdentifier(2);
			ethnicity.setName("ETHNICITY-88");
			ethnicity.setLabel("Ethnicity of survey participant");
					
			ArrayList<ValueLabel> valueLabels1 = new ArrayList<ValueLabel>();
			
			ValueLabel valueLabel1 = new ValueLabel();
			valueLabel1.setValue("1B");
			valueLabel1.setLabel("Indian");
			valueLabel1.setMissingValue(false);
			valueLabels1.add(valueLabel1);

			database.addValueLabels(jsmith, 
									rawVariable1,
									valueLabels1);

			
			ArrayList<ValueLabel> valueLabels2 = new ArrayList<ValueLabel>();

			ValueLabel valueLabel2 = new ValueLabel();
			valueLabel2.setValue("1C");
			valueLabel2.setLabel("Indian");
			valueLabel2.setMissingValue(false);
			valueLabels2.add(valueLabel2);
			database.addValueLabels(jsmith, 
									rawVariable1,
									valueLabels2);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();			
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.DUPLICATE_VALUE_LABEL);
			assertEquals(1, numberOfErrors);
			log.logException(exception);
		}		
	}
	*/

	/**
	 * Update the value, label and isMissingValue fields of a VariableLabel
	 */
	public void testUpdateValueLabelN1() {
		try {			
			TestResultSorter<ValueLabel> sorter = new TestResultSorter<ValueLabel>();
			
			ArrayList<ValueLabel> valueLabels = new ArrayList<ValueLabel>();

			ValueLabel valueLabel1 = new ValueLabel();
			valueLabel1.setValue("1C");
			valueLabel1.setLabel("Irish");
			valueLabel1.setMissingValue(false);
			valueLabels.add(valueLabel1);
			
			ValueLabel valueLabel2 = new ValueLabel();
			valueLabel2.setValue("3A");
			valueLabel2.setLabel("Indian");
			valueLabel2.setMissingValue(false);
			valueLabels.add(valueLabel2);
			
			curationService.addValueLabels(jsmith, 
									rawVariable1,
									valueLabels);

			int identifier1
				= curationService.getValueLabelIdentifier(jsmith,
												   rawVariable1,
												   valueLabel1);
			valueLabel1.setIdentifier(identifier1);
			valueLabel1.setLabel("Don't know");
			valueLabel1.setValue("1A");
			valueLabel1.setMissingValue(true);

			ArrayList<ValueLabel> updatedList = new ArrayList<ValueLabel>();
			updatedList.add(valueLabel1);
			curationService.updateValueLabels(jsmith, rawVariable1, updatedList);
						
			ArrayList<ValueLabel> valueLabelsSoFar
				= curationService.getValueLabels(jsmith, rawVariable1);
			sorter.sort(valueLabelsSoFar);
			
			assertEquals(2, valueLabelsSoFar.size());
			assertEquals("1A",
						 valueLabelsSoFar.get(0).getValue());
			assertEquals("Don't know",
					 	 valueLabelsSoFar.get(0).getLabel());
			assertEquals(true,
				 	 	 valueLabelsSoFar.get(0).isMissingValue());			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}

	/**
	 * Update non-existent variable
	 */
	public void testUpdateValueLabelE1() {
		try {			
			ArrayList<ValueLabel> valueLabels = new ArrayList<ValueLabel>();

			ValueLabel valueLabel1 = new ValueLabel();
			valueLabel1.setValue("1C");
			valueLabel1.setLabel("Irish");
			valueLabel1.setMissingValue(false);
			valueLabels.add(valueLabel1);
			
			curationService.addValueLabels(jsmith, 
									rawVariable1,
									valueLabels);

			ValueLabel valueLabel2 = new ValueLabel();
			valueLabel2.setIdentifier(2345);
			valueLabel2.setValue("3A");
			valueLabel2.setLabel("Indian");
			valueLabel2.setMissingValue(false);
			valueLabels.add(valueLabel2);
						
			ArrayList<ValueLabel> updatedList = new ArrayList<ValueLabel>();
			updatedList.add(valueLabel2);			
			curationService.updateValueLabels(jsmith, rawVariable1, updatedList);						
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_VALUE_LABELS);
			assertEquals(1, numberOfErrors);
		}		
	}


	/**
	 * delete value labels from a list
	 */
	public void testDeleteValueLabelN1() {
		try {			
			TestResultSorter<ValueLabel> sorter 
				= new TestResultSorter<ValueLabel>();
			
			ArrayList<ValueLabel> valueLabels = new ArrayList<ValueLabel>();
			
			ValueLabel valueLabel1 = new ValueLabel();
			valueLabel1.setValue("1C");
			valueLabel1.setLabel("Irish");
			valueLabel1.setMissingValue(false);
			valueLabels.add(valueLabel1);
			
			ValueLabel valueLabel2 = new ValueLabel();
			valueLabel2.setValue("3A");
			valueLabel2.setLabel("Indian");
			valueLabel2.setMissingValue(false);
			valueLabels.add(valueLabel2);
			
			ValueLabel valueLabel3 = new ValueLabel();
			valueLabel3.setValue("4B");
			valueLabel3.setLabel("African");
			valueLabel3.setMissingValue(false);
			valueLabels.add(valueLabel3);

			ValueLabel valueLabel4 = new ValueLabel();
			valueLabel4.setValue("99");
			valueLabel4.setLabel("Not Known");
			valueLabel4.setMissingValue(true);
			valueLabels.add(valueLabel4);

			curationService.addValueLabels(jsmith, 
									rawVariable1,
									valueLabels);

			ArrayList<ValueLabel> itemsToDelete = new ArrayList<ValueLabel>();
			int identifier2 = curationService.getValueLabelIdentifier(jsmith, rawVariable1, valueLabel2);
			valueLabel2.setIdentifier(identifier2);
			itemsToDelete.add(valueLabel2);
			int identifier3 = curationService.getValueLabelIdentifier(jsmith, rawVariable1, valueLabel3);
			valueLabel3.setIdentifier(identifier3);
			itemsToDelete.add(valueLabel3);		
			curationService.deleteValueLabels(jsmith, rawVariable1, itemsToDelete);

			ArrayList<ValueLabel> valueLabelsSoFar
				= curationService.getValueLabels(jsmith, rawVariable1);
			sorter.sort(valueLabelsSoFar);
			assertEquals(2, valueLabelsSoFar.size());
			assertEquals("1C",
						 valueLabelsSoFar.get(0).getValue());
			assertEquals("99",
					 valueLabelsSoFar.get(1).getValue());
			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}		
	}

	/**
	 * delete a non-existent value label from a list
	 */
	public void testDeleteValueLabelE1() {
		try {			
			TestResultSorter<ValueLabel> sorter 
				= new TestResultSorter<ValueLabel>();
			
			ArrayList<ValueLabel> valueLabels = new ArrayList<ValueLabel>();
			
			ValueLabel valueLabel1 = new ValueLabel();
			valueLabel1.setValue("1C");
			valueLabel1.setLabel("Irish");
			valueLabel1.setMissingValue(false);
			valueLabels.add(valueLabel1);
			
			ValueLabel valueLabel2 = new ValueLabel();
			valueLabel2.setValue("3A");
			valueLabel2.setLabel("Indian");
			valueLabel2.setMissingValue(false);
			valueLabels.add(valueLabel2);
			
			ValueLabel valueLabel3 = new ValueLabel();
			valueLabel3.setValue("4B");
			valueLabel3.setLabel("African");
			valueLabel3.setMissingValue(false);
			valueLabels.add(valueLabel3);

			ValueLabel valueLabel4 = new ValueLabel();
			valueLabel4.setValue("99");
			valueLabel4.setLabel("Not Known");
			valueLabel4.setMissingValue(true);
			valueLabels.add(valueLabel4);

			curationService.addValueLabels(jsmith, 
									rawVariable1,
									valueLabels);

			ArrayList<ValueLabel> itemsToDelete = new ArrayList<ValueLabel>();
			int identifier2 = curationService.getValueLabelIdentifier(jsmith, rawVariable1, valueLabel2);
			valueLabel2.setIdentifier(identifier2);
			itemsToDelete.add(valueLabel2);
			int identifier3 = curationService.getValueLabelIdentifier(jsmith, rawVariable1, valueLabel3);
			valueLabel3.setIdentifier(identifier3);
			itemsToDelete.add(valueLabel3);		
			curationService.deleteValueLabels(jsmith, rawVariable1, itemsToDelete);

			ArrayList<ValueLabel> valueLabelsSoFar
				= curationService.getValueLabels(jsmith, rawVariable1);
			sorter.sort(valueLabelsSoFar);
			assertEquals(2, valueLabelsSoFar.size());
			assertEquals("1C",
						 valueLabelsSoFar.get(0).getValue());
			assertEquals("99",
					 valueLabelsSoFar.get(1).getValue());
			
		}
		catch(MacawException exception) {
			log.logException(exception);
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

