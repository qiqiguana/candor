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

public class TestSupportingDocumentFilter extends MacawCurationTestCase {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private SupportingDocument supportingDocument1;
	private SupportingDocument supportingDocument2;
	private SupportingDocument supportingDocument3;
	private SupportingDocument supportingDocument4;
	private SupportingDocument supportingDocument5;
	private SupportingDocument supportingDocument6;
	private SupportingDocument supportingDocument7;
	private SupportingDocument supportingDocument8;

	
	// ==========================================
	// Section Construction
	// ==========================================
	public TestSupportingDocumentFilter() {
		super("Test Supporting Documents");
	}
	
	protected void setUp() throws Exception {
		curationService.clear(admin);
		
		supportingDocument1 = new SupportingDocument();
		supportingDocument1.setTitle("Life of limes and other notes");
		supportingDocument1.setDocumentCode("AAA-limes1.2");
		supportingDocument1.setFileName("Limes.doc");
		supportingDocument1.setFilePath("C://life");
		curationService.addSupportingDocument(jsmith, supportingDocument1);
		
		supportingDocument2 = new SupportingDocument();
		supportingDocument2.setTitle("treatment of scurving using limes");
		supportingDocument2.setDocumentCode("AAA-lime");
		supportingDocument2.setFileName("Scurvy-dogs.pdf");
		supportingDocument2.setFilePath("C://life//medical");
		curationService.addSupportingDocument(jsmith, supportingDocument2);

		supportingDocument3 = new SupportingDocument();
		supportingDocument3.setTitle("afterlife of fruits and vegetables");
		supportingDocument3.setDocumentCode("BBB-philosophy.2");
		supportingDocument3.setFileName("VegetablesAndFruits.html");
		supportingDocument3.setFilePath("E://diet");
		curationService.addSupportingDocument(jsmith, supportingDocument3);

		supportingDocument4 = new SupportingDocument();
		supportingDocument4.setTitle("That's LIFE!");
		supportingDocument4.setDocumentCode("BBB-philosophy.3");
		supportingDocument4.setFileName("VegetablesAndFruits.html");
		supportingDocument4.setFilePath("E://diet");
		curationService.addSupportingDocument(jsmith, supportingDocument4);

		supportingDocument5 = new SupportingDocument();
		supportingDocument5.setTitle("Lemons and other citrus fruits");
		supportingDocument5.setDocumentCode("Lemons1.2");
		supportingDocument5.setFileName("HistoryOfCitrus.doc");
		supportingDocument5.setFilePath("E://fruits_and_veg");
		curationService.addSupportingDocument(jsmith, supportingDocument5);

		supportingDocument6 = new SupportingDocument();
		supportingDocument6.setTitle("Words that rhyme with orange");
		supportingDocument6.setDocumentCode("CDE-ORANGE");
		supportingDocument6.setFileName("RhymingThings.pdf");
		supportingDocument6.setFilePath("E://poetry");
		curationService.addSupportingDocument(jsmith, supportingDocument6);

		supportingDocument7 = new SupportingDocument();
		supportingDocument7.setTitle("Strange things about orange clocks.");
		supportingDocument7.setDocumentCode("orange-A4");
		supportingDocument7.setFileName("Clocks.doc");
		supportingDocument7.setFilePath("E://devices");
		curationService.addSupportingDocument(jsmith, supportingDocument7);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	
	public void testSupportingDocumentFilterN1() {
		try {
			ArrayList<SupportingDocument> results
				= curationService.filterSupportingDocuments(jsmith, 
													 "life", 
													 "");
			
			TestResultSorter<SupportingDocument> sorter
				= new TestResultSorter<SupportingDocument>();
			
			sorter.sort(results);
			assertEquals(3, results.size());
			
			assertEquals("AAA-limes1.2",
						 results.get(0).getDocumentCode());
			assertEquals("BBB-philosophy.3",
					 	 results.get(1).getDocumentCode());
			assertEquals("BBB-philosophy.2",
				 	 	 results.get(2).getDocumentCode());	
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	public void testSupportingDocumentFilterN2() {
		try {
			ArrayList<SupportingDocument> results
				= curationService.filterSupportingDocuments(jsmith, 
													 "banana", 
													 "");
			
			TestResultSorter<SupportingDocument> sorter
				= new TestResultSorter<SupportingDocument>();
			sorter.sort(results);
			assertEquals(0, results.size());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	public void testSupportingDocumentFilterN3() {
		try {
			ArrayList<SupportingDocument> results
				= curationService.filterSupportingDocuments(jsmith, 
													 "cello", 
													 "walrus");
			
			TestResultSorter<SupportingDocument> sorter
				= new TestResultSorter<SupportingDocument>();
			sorter.sort(results);
			assertEquals(0, results.size());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	public void testSupportingDocumentFilterN4() {
		try {
			ArrayList<SupportingDocument> results
				= curationService.filterSupportingDocuments(jsmith, 
													 "life", 
													 "jupiter");
			
			TestResultSorter<SupportingDocument> sorter
				= new TestResultSorter<SupportingDocument>();
			sorter.sort(results);
			assertEquals(0, results.size());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	public void testSupportingDocumentFilterN5() {
		try {
			ArrayList<SupportingDocument> results
				= curationService.filterSupportingDocuments(jsmith, 
													 "", 
													 "pluto");
			
			TestResultSorter<SupportingDocument> sorter
				= new TestResultSorter<SupportingDocument>();
			sorter.sort(results);
			assertEquals(0, results.size());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	public void testSupportingDocumentFilterN6() {
		try {
			ArrayList<SupportingDocument> results
				= curationService.filterSupportingDocuments(jsmith, 
													 "", 
													 "bbb");
			
			TestResultSorter<SupportingDocument> sorter
				= new TestResultSorter<SupportingDocument>();
			sorter.sort(results);
			assertEquals(2, results.size());
			assertEquals("BBB-philosophy.3",
						 results.get(0).getDocumentCode());
			assertEquals("BBB-philosophy.2",
						 results.get(1).getDocumentCode());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	public void testSupportingDocumentFilterN7() {
		try {
			ArrayList<SupportingDocument> results
				= curationService.filterSupportingDocuments(jsmith, 
													 "fruits", 
													 "bbb");
			
			TestResultSorter<SupportingDocument> sorter
				= new TestResultSorter<SupportingDocument>();
			sorter.sort(results);
			assertEquals(1, results.size());
			assertEquals("BBB-philosophy.2",
						 results.get(0).getDocumentCode());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	public void testSupportingDocumentFilterN8() {
		try {
			ArrayList<SupportingDocument> results
				= curationService.filterSupportingDocuments(jsmith, 
													 "life", 
													 "philosophy");
			
			TestResultSorter<SupportingDocument> sorter
				= new TestResultSorter<SupportingDocument>();
			sorter.sort(results);
			assertEquals(2, results.size());
			assertEquals("BBB-philosophy.3",
						 results.get(0).getDocumentCode());
			assertEquals("BBB-philosophy.2",
						 results.get(1).getDocumentCode());

		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	public void testSupportingDocumentFilterE1() {
		try {
			ArrayList<SupportingDocument> results
				= curationService.filterSupportingDocuments(jsmith, 
													 "", 
												 	 "");
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NO_SUPPORTING_DOCUMENT_FILTER);
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

