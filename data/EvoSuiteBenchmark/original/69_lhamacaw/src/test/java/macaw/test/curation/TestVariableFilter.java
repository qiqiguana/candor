package macaw.test.curation;

import macaw.businessLayer.*;

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

public class TestVariableFilter extends MacawCurationTestCase {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private RawVariable rawVariable1;
	private RawVariable rawVariable2;
	private RawVariable rawVariable3;
	private RawVariable rawVariable4;
	private DerivedVariable derivedVariable1;
	private DerivedVariable derivedVariable2;
	private DerivedVariable derivedVariable3;
	private DerivedVariable derivedVariable4;
	
	
	// ==========================================
	// Section Construction
	// ==========================================
	public TestVariableFilter() {
		super("Test Variable Filter");
	}
	
	protected void setUp() throws Exception {
		curationService.clear(admin);
		
		RawVariable rawVariable1 = new RawVariable();
		rawVariable1.setName("CHEST-77");
		rawVariable1.setLabel("Chest pains");
		rawVariable1.setCategory("General Health");
		rawVariable1.setCleaned(true);
		rawVariable1.setCleaningStatus("cleaned and verified");
		rawVariable1.setAvailability("in-house staff");
		rawVariable1.setYear("1977");
		rawVariable1.setAlias("d01");
		rawVariable1.setFilePath("height-87-1.txt");
		rawVariable1.setForm("5a");
		rawVariable1.setQuestionNumber("3i");
		curationService.addRawVariable(jsmith, rawVariable1);

		RawVariable rawVariable2 = new RawVariable();
		rawVariable2.setName("BCK-77");
		rawVariable2.setLabel("Pains in the lower back");
		rawVariable2.setCategory("General Health");
		rawVariable2.setCleaned(true);
		rawVariable2.setCleaningStatus("cleaned and verified");
		rawVariable2.setAvailability("in-house staff");
		rawVariable2.setYear("1977");
		rawVariable2.setAlias("d02");	
		rawVariable2.setFilePath("height-87-1.txt");
		rawVariable2.setForm("5a");
		rawVariable2.setQuestionNumber("3i");
		curationService.addRawVariable(jsmith, rawVariable2);
		
		RawVariable rawVariable3 = new RawVariable();
		rawVariable3.setName("PAINKILLERS-1968");
		rawVariable3.setLabel("use of pain killers in 1968");
		rawVariable3.setCategory("General Health");
		rawVariable3.setCleaned(true);
		rawVariable3.setCleaningStatus("cleaned and verified");
		rawVariable3.setAvailability("in-house staff");
		rawVariable3.setYear("1977");
		rawVariable3.setAlias("d03");
		rawVariable3.setFilePath("height-87-1.txt");
		rawVariable3.setForm("5a");
		rawVariable3.setQuestionNumber("3i");
		curationService.addRawVariable(jsmith, rawVariable3);

		
		RawVariable rawVariable4 = new RawVariable();
		rawVariable4.setName("HEIGHT-85");
		rawVariable4.setLabel("Height in centimetres.");
		rawVariable4.setCategory("Anthropometry");
		rawVariable4.setCleaned(true);
		rawVariable4.setCleaningStatus("cleaned and verified");
		rawVariable4.setAvailability("in-house staff");
		rawVariable4.setYear("1985");
		rawVariable4.setAlias("d04");
		rawVariable4.setFilePath("height-87-1.txt");
		rawVariable4.setForm("5a");
		rawVariable4.setQuestionNumber("3i");
		curationService.addRawVariable(jsmith, rawVariable4);

		RawVariable rawVariable5 = new RawVariable();
		rawVariable5.setName("WEIGHT-85");
		rawVariable5.setLabel("Height in kilograms in 1985.");
		rawVariable5.setCategory("Anthropometry");
		rawVariable5.setCleaned(true);
		rawVariable5.setCleaningStatus("cleaned and verified");
		rawVariable5.setAvailability("in-house staff");
		rawVariable5.setYear("1985");
		rawVariable5.setAlias("d05");
		rawVariable5.setFilePath("height-87-1.txt");
		rawVariable5.setForm("5a");
		rawVariable5.setQuestionNumber("3i");
		curationService.addRawVariable(jsmith, rawVariable5);
		
		
		DerivedVariable derivedVariable1 = new DerivedVariable();
		derivedVariable1.setName("BMI-88");
		derivedVariable1.setLabel("Body mass index, which is a ratio of height to weight.");
		derivedVariable1.setCategory("Anthropometry");
		derivedVariable1.setCleaned(true);
		derivedVariable1.setCleaningStatus("cleaned and verified");
		derivedVariable1.setAvailability("in-house staff");
		derivedVariable1.setYear("1985");
		derivedVariable1.setAlias("e6");
		derivedVariable1.setFilePath("height-87-1.txt");
		curationService.addDerivedVariable(jsmith, derivedVariable1);

		DerivedVariable derivedVariable2 = new DerivedVariable();
		derivedVariable2.setName("LUNG_RATIO-58");
		derivedVariable2.setLabel("Lung capacity based on a ratio of total size compared to used volume of lungs.");
		derivedVariable2.setCategory("Anthropometry");
		derivedVariable2.setYear("1958");
		derivedVariable2.setCleaned(true);
		derivedVariable2.setCleaningStatus("cleaned and verified");
		derivedVariable2.setAvailability("in-house staff");
		derivedVariable1.setAlias("e7");
		derivedVariable2.setFilePath("lungs-1.txt");
		curationService.addDerivedVariable(jsmith, derivedVariable2);
			
		DerivedVariable derivedVariable3 = new DerivedVariable();
		derivedVariable3.setName("INT_DEP-98");
		derivedVariable3.setLabel("intelligence score over depression score.");
		derivedVariable3.setCategory("Anthropometry");
		derivedVariable3.setYear("1998");
		derivedVariable3.setCleaned(true);
		derivedVariable3.setCleaningStatus("cleaned and verified");
		derivedVariable3.setAvailability("in-house staff");
		derivedVariable3.setAlias("e8");
		derivedVariable3.setFilePath("depression.txt");
		curationService.addDerivedVariable(jsmith, derivedVariable3);

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	/**
	public void testVariableFilterN1() {
		try {
			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}
	
	public void testVariableFilterN2() {
		try {
			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	public void testVariableFilterN3() {
		try {
			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	public void testVariableFilterN4() {
		try {
			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	public void testVariableFilterN5() {
		try {
			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	public void testVariableFilterN6() {
		try {
			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	public void testVariableFilterN7() {
		try {
			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	public void testVariableFilterN8() {
		try {
			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	public void testVariableFilterN9() {
		try {
			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	public void testVariableFilterN10() {
		try {
			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	public void testVariableFilterN11() {
		try {
			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

*/
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

