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

public class TestCurateSupportingDocuments extends MacawCurationTestCase {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private SupportingDocument lifeCoursePDF;
	private SupportingDocument veryImportantDoc;
	private SupportingDocument bodyMassDoc;
	
	
	// ==========================================
	// Section Construction
	// ==========================================
	public TestCurateSupportingDocuments() {
		super("Test Supporting Documents");
		
		lifeCoursePDF = new SupportingDocument();
		lifeCoursePDF.setTitle("Lifecourse Analysis");
		lifeCoursePDF.setDocumentCode("LHA-1.1");
		lifeCoursePDF.setFileName("lifecourse_guidelines.pdf");
		lifeCoursePDF.setFilePath("C://lha_materials");
		
		veryImportantDoc = new SupportingDocument();
		veryImportantDoc.setTitle("Very Important Document");
		veryImportantDoc.setDocumentCode("LHA-1.2");
		veryImportantDoc.setFileName("very_important_doc.pdf");
		veryImportantDoc.setFilePath("E://docs");
		
		bodyMassDoc = new SupportingDocument();
		bodyMassDoc.setTitle("Body mass measurements");
		bodyMassDoc.setDocumentCode("LHA-1.6");
		bodyMassDoc.setFileName("mass94.pdf");
		bodyMassDoc.setFilePath("F://mystuff");
		
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	public void testFieldValidationN1() {
		try {
			SupportingDocument document1
				= (SupportingDocument) lifeCoursePDF.clone();
			document1.setTitle("");
			curationService.addSupportingDocument(jsmith, document1);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors 
				= exception.getNumberOfErrors(MacawErrorType.INVALID_SUPPORTING_DOCUMENT);
			assertEquals(1, numberOfErrors);		
		}
	}

	public void testFieldValidationN2() {
		try {
			SupportingDocument document1
				= (SupportingDocument) lifeCoursePDF.clone();
			document1.setDocumentCode("");

			curationService.addSupportingDocument(jsmith, document1);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);			
			int numberOfErrors 
				= exception.getNumberOfErrors(MacawErrorType.INVALID_SUPPORTING_DOCUMENT);
			assertEquals(1, numberOfErrors);
		}
	}

	public void testFieldValidationN3() {
		try {
			SupportingDocument document1
				= (SupportingDocument) lifeCoursePDF.clone();
			document1.setFileName("");

			curationService.addSupportingDocument(jsmith, document1);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);			
			int numberOfErrors 
				= exception.getNumberOfErrors(MacawErrorType.INVALID_SUPPORTING_DOCUMENT);
			assertEquals(1, numberOfErrors);
		}
	}

	public void testFieldValidationN4() {
		try {
			SupportingDocument document1
				= (SupportingDocument) lifeCoursePDF.clone();
			document1.setFilePath("");
			curationService.addSupportingDocument(jsmith, document1);			

			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);			
			int numberOfErrors 
				= exception.getNumberOfErrors(MacawErrorType.INVALID_SUPPORTING_DOCUMENT);
			assertEquals(1, numberOfErrors);
		}
	}

	
	public void testAddDocumentN1() {
		try {
			
			TestResultSorter<SupportingDocument> sorter
				= new TestResultSorter<SupportingDocument>();

			SupportingDocument document1
				= (SupportingDocument) lifeCoursePDF.clone();
			curationService.addSupportingDocument(jsmith, document1);
			
			SupportingDocument document2
				= (SupportingDocument) bodyMassDoc.clone();
			curationService.addSupportingDocument(jsmith, document2);

			SupportingDocument document3
				= (SupportingDocument) veryImportantDoc.clone();
			curationService.addSupportingDocument(jsmith, document3);			
			
			ArrayList<SupportingDocument> documentsSoFar
				= curationService.getAllSupportingDocuments(jsmith);
			sorter.sort(documentsSoFar);

			assertEquals(3, documentsSoFar.size());
			assertEquals("LHA-1.6",
						 documentsSoFar.get(0).getDocumentCode());
			assertEquals("LHA-1.1",
					 documentsSoFar.get(1).getDocumentCode());
			assertEquals("LHA-1.2",
					 documentsSoFar.get(2).getDocumentCode());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();			
		}
	}
	
	public void testAddDocumentA1() {
		try {
			SupportingDocument document1
				= (SupportingDocument) lifeCoursePDF.clone();
			curationService.addSupportingDocument(jsmith, document1);

			SupportingDocument document2
				= new SupportingDocument();
			document2.setTitle("LIFECOURSE ANALYSIS");
			document2.setDocumentCode("LHA-1.2");
			document2.setFileName("lifecourse_guidelines.pdf");
			document2.setFilePath("xx//yy//zzz");

			curationService.addSupportingDocument(jsmith, document2);			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();			
		}
	}

	public void testAddDocumentE1() {
		try {
			SupportingDocument document1
				= (SupportingDocument) bodyMassDoc.clone();
			curationService.addSupportingDocument(jsmith, document1);

			SupportingDocument document2
				= (SupportingDocument) veryImportantDoc.clone();
			document2.setDocumentCode(bodyMassDoc.getDocumentCode());
			curationService.addSupportingDocument(jsmith, document2);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.DUPLICATE_SUPPORTING_DOCUMENT_CODE);
			assertEquals(1, numberOfErrors);
		}
	}

	public void testUpdateDocumentN1() {
		try {
			
			TestResultSorter<SupportingDocument> sorter
				= new TestResultSorter<SupportingDocument>();

			SupportingDocument document1
				= (SupportingDocument) lifeCoursePDF.clone();
			curationService.addSupportingDocument(jsmith, document1);
			int identifier = curationService.getSupportingDocumentIdentifier(jsmith, document1);
			document1.setIdentifier(identifier);

			//testing title update
			document1.setTitle("XXX");
			curationService.updateSupportingDocument(jsmith, document1);
			ArrayList<SupportingDocument> documentsSoFar
				= curationService.getAllSupportingDocuments(jsmith);
			sorter.sort(documentsSoFar);
			
			SupportingDocument document2
				= documentsSoFar.get(0);
			assertEquals("XXX", document2.getTitle());

			//testing description update
			document2.setDescription("yyy yyy");
			curationService.updateSupportingDocument(jsmith, document2);
			documentsSoFar
				= curationService.getAllSupportingDocuments(jsmith);
			sorter.sort(documentsSoFar);
			SupportingDocument document3
				= documentsSoFar.get(0);
			assertEquals("yyy yyy", document3.getDescription());
			
			//testing document code update
			document3.setDocumentCode("ABC123.6");
			curationService.updateSupportingDocument(jsmith, document3);
			documentsSoFar
				= curationService.getAllSupportingDocuments(jsmith);
			SupportingDocument document4
				= documentsSoFar.get(0);
			assertEquals("ABC123.6", document4.getDocumentCode());
			
			//testing file name update
			document4.setFileName("foo.txt");
			curationService.updateSupportingDocument(jsmith, document4);
			documentsSoFar
				= curationService.getAllSupportingDocuments(jsmith);
			SupportingDocument document5
				= documentsSoFar.get(0);
			assertEquals("ABC123.6", document5.getDocumentCode());
				
			//testing file path
			document5.setFilePath("zzz");
			curationService.updateSupportingDocument(jsmith, document5);
			documentsSoFar
				= curationService.getAllSupportingDocuments(jsmith);
			SupportingDocument document6
				= documentsSoFar.get(0);
			assertEquals("zzz", document6.getFilePath());
		}
		catch(MacawException exception) {
			log.logException(exception);
			exception.printStackTrace(System.out);
			fail();			
		}
	}

	/**
	 * detect when update changes result in a duplicate record
	 */
	public void testUpdateDocumentA1() {
		try {
			
			TestResultSorter<SupportingDocument> sorter
				= new TestResultSorter<SupportingDocument>();

			SupportingDocument document1
				= (SupportingDocument) lifeCoursePDF.clone();
			curationService.addSupportingDocument(jsmith, document1);

			SupportingDocument document2
				= (SupportingDocument) veryImportantDoc.clone();
			curationService.addSupportingDocument(jsmith, document2);
			int identifier2 = curationService.getSupportingDocumentIdentifier(jsmith, document2);
			document2.setIdentifier(identifier2);
			
			document2.setTitle(lifeCoursePDF.getTitle());
			curationService.updateSupportingDocument(jsmith, document2);
			
			ArrayList<SupportingDocument> documentsSoFar
				= curationService.getAllSupportingDocuments(jsmith);
			sorter.sort(documentsSoFar);
			
			assertEquals(lifeCoursePDF.getTitle(),
						 documentsSoFar.get(0).getTitle());
			assertEquals("LHA-1.1",
					 documentsSoFar.get(0).getDocumentCode());
			
			assertEquals(lifeCoursePDF.getTitle(),
					 documentsSoFar.get(1).getTitle());
			assertEquals("LHA-1.2",
					 documentsSoFar.get(1).getDocumentCode());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();			
		}
	}

	/**
	 * update a non-existent supporting document
	 */
	public void testUpdateDocumentE1() {
		try {			
			SupportingDocument document1
				= (SupportingDocument) lifeCoursePDF.clone();
			curationService.addSupportingDocument(jsmith, document1);

			SupportingDocument document2
				= (SupportingDocument) veryImportantDoc.clone();
			document2.setIdentifier(34255);
			curationService.updateSupportingDocument(jsmith, document2);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors = exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_SUPPORTING_DOCUMENT);
			assertEquals(1, numberOfErrors);
		}
	}

	/**
	 * delete multiple items from a list
	 */
	public void testDeleteDocumentN1() {
		try {
			SupportingDocument document1
				= (SupportingDocument) lifeCoursePDF.clone();
			curationService.addSupportingDocument(jsmith, document1);
			
			SupportingDocument document2
				= (SupportingDocument) veryImportantDoc.clone();
			curationService.addSupportingDocument(jsmith, document2);
			int identifier2 
				= curationService.getSupportingDocumentIdentifier(jsmith, document2);
			document2.setIdentifier(identifier2);
			
			SupportingDocument document3
				= (SupportingDocument) bodyMassDoc.clone();
			curationService.addSupportingDocument(jsmith, document3);			
			int identifier3 
				= curationService.getSupportingDocumentIdentifier(jsmith, document3);
			document3.setIdentifier(identifier3);

			ArrayList<SupportingDocument> itemsToDelete 
				= new ArrayList<SupportingDocument>();
			itemsToDelete.add(document2);
			itemsToDelete.add(document3);
			
			curationService.deleteSupportingDocuments(jsmith, itemsToDelete);
			
			ArrayList<SupportingDocument> documentsSoFar
				= curationService.getAllSupportingDocuments(jsmith);
			assertEquals(1, documentsSoFar.size());
			
			assertEquals("LHA-1.1",
						 documentsSoFar.get(0).getDocumentCode());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}
	
	/**
	 * delete all items in a one item list
	 */
	public void testDeleteDocumentA1() {
		try {
			SupportingDocument document1
				= (SupportingDocument) lifeCoursePDF.clone();
			curationService.addSupportingDocument(jsmith, document1);
			int identifier1
				= curationService.getSupportingDocumentIdentifier(jsmith, document1);
			document1.setIdentifier(identifier1);

			ArrayList<SupportingDocument> itemsToDelete 
				= new ArrayList<SupportingDocument>();
			itemsToDelete.add(document1);
			curationService.deleteSupportingDocuments(jsmith, itemsToDelete);
			
			ArrayList<SupportingDocument> documentsSoFar
				= curationService.getAllSupportingDocuments(jsmith);
			assertEquals(0, documentsSoFar.size());			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}
	
	public void testDeleteDocumentE1() {
		try {
			
			SupportingDocument document1
				= (SupportingDocument) lifeCoursePDF.clone();
			curationService.addSupportingDocument(jsmith, document1);
			int identifier1
				= curationService.getSupportingDocumentIdentifier(jsmith, document1);
			document1.setIdentifier(identifier1);

			SupportingDocument document2
				= (SupportingDocument) veryImportantDoc.clone();
			document2.setIdentifier(986567);

			ArrayList<SupportingDocument> itemsToDelete 
				= new ArrayList<SupportingDocument>();
			itemsToDelete.add(document2);
			curationService.deleteSupportingDocuments(jsmith, itemsToDelete);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors 
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_SUPPORTING_DOCUMENT);
			assertEquals(1, numberOfErrors);
			log.logException(exception);
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

