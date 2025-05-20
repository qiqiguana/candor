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

public class TestCurateDerivedVariables extends MacawCurationTestCase {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private DerivedVariable derivedVariable1;
	private DerivedVariable derivedVariable2;
	private DerivedVariable derivedVariable3;

	private RawVariable rawVariable1;
	private RawVariable rawVariable2;
	private RawVariable rawVariable3;

	private SupportingDocument supportingDocW;
	private SupportingDocument supportingDocX;
	private SupportingDocument supportingDocY;


	// ==========================================
	// Section Construction
	// ==========================================
	public TestCurateDerivedVariables() {
		super("Test derived variables");
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
		Category category2 = new Category("Anthropometry");
		curationService.addCategory(jsmith, category2);
		Category category3 = new Category("Respiratory Health");
		curationService.addCategory(jsmith, category3);
		
		//adding a few data libraries
		AliasFilePath dataLibrary1 = new AliasFilePath("bbb", "$BBB/other");
		curationService.addAliasFilePath(jsmith, dataLibrary1);
		AliasFilePath dataLibrary2 = new AliasFilePath("fff", "$FFF/lha");
		curationService.addAliasFilePath(jsmith, dataLibrary2);
		AliasFilePath dataLibrary3 = new AliasFilePath("hhh", "$HHH/general");
		curationService.addAliasFilePath(jsmith, dataLibrary3);
		
		supportingDocW = new SupportingDocument();
		supportingDocW.setTitle("General Document W");
		supportingDocW.setDocumentCode("LHA-DocW");
		supportingDocW.setDescription("Document description W");
		supportingDocW.setFileName("DocW.doc");
		supportingDocW.setFilePath("myStuff/cool");
		
		supportingDocX = new SupportingDocument();
		supportingDocX.setTitle("General Document X");
		supportingDocX.setDocumentCode("LHA-DocX");
		supportingDocX.setDescription("Document description X");
		supportingDocX.setFileName("DocX.doc");			
		supportingDocX.setFilePath("myStuff/cool");
		
		supportingDocY = new SupportingDocument();
		supportingDocY.setTitle("General Document Y");
		supportingDocY.setDocumentCode("LHA-DocY");
		supportingDocY.setDescription("Document description Y");
		supportingDocY.setFileName("DocY.doc");	
		supportingDocY.setFilePath("myStuff/cool");	
		
		derivedVariable1 = new DerivedVariable();
		derivedVariable1.setName("DerivedVariableNameA");
		derivedVariable1.setLabel("DerivedVariableLabelA");
		derivedVariable1.setCategory("Anthropometry");
		derivedVariable1.setCoded(true);
		derivedVariable1.setCleaned(true);
		derivedVariable1.setCleaningStatus("cleaned and verified");
		derivedVariable1.setAvailability("in-house staff");
		derivedVariable1.setYear("1996");
		derivedVariable1.setAlias("bbb");
		derivedVariable1.setFilePath("$BBB/other");
		curationService.addDerivedVariable(jsmith, derivedVariable1);
		int identifier1 = curationService.getDerivedVariableIdentifier(jsmith, derivedVariable1);
		derivedVariable1.setIdentifier(identifier1);

		derivedVariable2 = new DerivedVariable();
		derivedVariable2.setName("DerivedVariableNameB");
		derivedVariable2.setLabel("DerivedVariableLabelB");
		derivedVariable2.setCategory("Anthropometry");
		derivedVariable2.setCoded(true);
		derivedVariable2.setCleaned(true);
		derivedVariable2.setCleaningStatus("cleaned and verified");
		derivedVariable2.setAvailability("in-house staff");
		derivedVariable2.setYear("1996");
		derivedVariable2.setAlias("bbb");
		derivedVariable2.setFilePath("$BBB/other");
		curationService.addDerivedVariable(jsmith, derivedVariable2);
		int identifier2 = curationService.getDerivedVariableIdentifier(jsmith, derivedVariable2);
		derivedVariable2.setIdentifier(identifier2);
		
		derivedVariable3 = new DerivedVariable();
		derivedVariable3.setName("DerivedVariableNameC");
		derivedVariable3.setLabel("DerivedVariableLabelC");
		derivedVariable3.setCategory("Anthropometry");
		derivedVariable3.setCoded(true);
		derivedVariable3.setCleaned(true);
		derivedVariable3.setCleaningStatus("cleaned and verified");
		derivedVariable3.setAvailability("in-house staff");
		derivedVariable3.setYear("1996");
		derivedVariable3.setAlias("hhh");
		derivedVariable3.setFilePath("$HHH/general");
		curationService.addDerivedVariable(jsmith, derivedVariable3);
		int identifier3 = curationService.getDerivedVariableIdentifier(jsmith, derivedVariable3);
		derivedVariable3.setIdentifier(identifier3);

		rawVariable1 = new RawVariable();
		rawVariable1.setName("HT87");
		rawVariable1.setLabel("Height in 1987");
		rawVariable1.setCategory("Anthropometry");
		rawVariable1.setCoded(true);
		rawVariable1.setCleaned(true);
		rawVariable1.setCleaningStatus("cleaned and verified");
		rawVariable1.setAvailability("in-house staff");
		rawVariable1.setYear("1987");
		rawVariable1.setAlias("fff");
		rawVariable1.setFilePath("$FFF/lha");
		rawVariable1.setForm("5a");
		rawVariable1.setQuestionNumber("3i");
		rawVariable1.setColumnStart("10");
		rawVariable1.setColumnEnd("20");
		
		curationService.addRawVariable(jsmith, rawVariable1);
		int rawVariableIdentifier1
			= curationService.getRawVariableIdentifier(jsmith, rawVariable1);
		rawVariable1.setIdentifier(rawVariableIdentifier1);
		
		rawVariable2 = new RawVariable();
		rawVariable2.setName("WEIGHT87");
		rawVariable2.setLabel("weight in 1987");
		rawVariable2.setCategory("Anthropometry");
		rawVariable2.setCoded(true);
		rawVariable2.setCleaned(true);
		rawVariable2.setCleaningStatus("clean");
		rawVariable2.setAvailability("unrestricted");
		rawVariable2.setYear("1987");
		rawVariable2.setAlias("fff");
		rawVariable2.setFilePath("$FFF/lha");

		rawVariable2.setForm("8");
		rawVariable2.setQuestionNumber("1.2");
		curationService.addRawVariable(jsmith, rawVariable2);
		int rawVariableIdentifier2
			= curationService.getRawVariableIdentifier(jsmith, rawVariable2);
		rawVariable2.setIdentifier(rawVariableIdentifier2);

		rawVariable3 = new RawVariable();
		rawVariable3.setName("DEP78");
		rawVariable3.setLabel("Depression score in 1978");
		rawVariable3.setCategory("General Health");
		rawVariable3.setCleaned(false);
		rawVariable3.setAvailability("unrestricted");
		rawVariable3.setYear("1992");
		rawVariable3.setAlias("fff");
		rawVariable3.setFilePath("$FFF/lha");
		rawVariable3.setForm("55");
		rawVariable3.setQuestionNumber("6vi");
		curationService.addRawVariable(jsmith, rawVariable3);		
		int rawVariableIdentifier3
			= curationService.getRawVariableIdentifier(jsmith, rawVariable3);
		rawVariable3.setIdentifier(rawVariableIdentifier3);		
	}

	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
		
	/**
	 * associate one or supporting documents with a derived variable
	 */
	public void testDerivedVariableAssociateDocumentN1() {
		try {
			
			SupportingDocument docW
				= (SupportingDocument) supportingDocW.clone();
			curationService.addSupportingDocument(jsmith, docW);
			int identifierW
				= curationService.getSupportingDocumentIdentifier(jsmith, docW);
			docW.setIdentifier(identifierW);
			
			SupportingDocument docX
				= (SupportingDocument) supportingDocX.clone();
			curationService.addSupportingDocument(jsmith, docX);
			int identifierX
				= curationService.getSupportingDocumentIdentifier(jsmith, 
																  docX);
			docX.setIdentifier(identifierX);

			SupportingDocument docY
				= (SupportingDocument) supportingDocY.clone();
			curationService.addSupportingDocument(jsmith, docY);
			
			ArrayList<SupportingDocument> documentsToAdd
				= new ArrayList<SupportingDocument>();
			documentsToAdd.add(docW);
			documentsToAdd.add(docX);

			//now associate two of the documents with the variable
			curationService.associateSupportingDocumentsWithVariable(jsmith, 
															  derivedVariable1, 
															  documentsToAdd);
			
			ArrayList<SupportingDocument> associatedDocs1
				= curationService.getSupportingDocuments(jsmith, derivedVariable1);
			TestResultSorter<SupportingDocument> sorter
				= new TestResultSorter<SupportingDocument>();
			sorter.sort(associatedDocs1);
			assertEquals(2, associatedDocs1.size());
			
			assertEquals("LHA-DocW",
						 associatedDocs1.get(0).getDocumentCode());

			assertEquals("LHA-DocX",
					 associatedDocs1.get(1).getDocumentCode());			
		}
		catch(MacawException exception) {
			exception.printErrors();
			log.logException(exception);
			fail();
		}
	}

	/**
	 * associate a single supporting documents with a derived variable
	 */
	public void testDerivedVariableAssociateDocumentA1() {
		try {
			
			SupportingDocument docW
				= (SupportingDocument) supportingDocW.clone();
			curationService.addSupportingDocument(jsmith, docW);
			int identifierW
				= curationService.getSupportingDocumentIdentifier(jsmith, docW);
			docW.setIdentifier(identifierW);
			
			ArrayList<SupportingDocument> documentsToAdd
				= new ArrayList<SupportingDocument>();
			documentsToAdd.add(docW);

			//now associate two of the documents with the variable
			curationService.associateSupportingDocumentsWithVariable(jsmith, 
															  derivedVariable1, 
															  documentsToAdd);
			
			ArrayList<SupportingDocument> associatedDocs1
				= curationService.getSupportingDocuments(jsmith, derivedVariable1);
			TestResultSorter<SupportingDocument> sorter
				= new TestResultSorter<SupportingDocument>();
			sorter.sort(associatedDocs1);
			assertEquals(1, associatedDocs1.size());
			
			assertEquals("LHA-DocW",
						 associatedDocs1.get(0).getDocumentCode());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}
	
	/**
	 * associate a document with a non-existent derived variable
	 */
	public void testDerivedVariableAssociateDocumentE1() {
		try {

			//create a non-existent derived variable
			DerivedVariable derivedVariable4 = new DerivedVariable();
			derivedVariable4.setIdentifier(35342);
			derivedVariable4.setName("DerivedVariableNameD");
			derivedVariable4.setLabel("DerivedVariableLabelD");
			derivedVariable4.setCategory("Anthropometry");
			derivedVariable4.setCoded(true);
			derivedVariable4.setCleaned(true);
			derivedVariable4.setCleaningStatus("cleaned and verified");
			derivedVariable4.setAvailability("in-house staff");
			derivedVariable4.setYear("1996");
			derivedVariable4.setAlias("kwc-4");
			derivedVariable4.setFilePath("$try/DerivedVariableFileD");
			
			SupportingDocument docW
				= (SupportingDocument) supportingDocW.clone();
			curationService.addSupportingDocument(jsmith, docW);
			int identifierW
				= curationService.getSupportingDocumentIdentifier(jsmith, docW);
			docW.setIdentifier(identifierW);
			
			ArrayList<SupportingDocument> documentsToAdd
				= new ArrayList<SupportingDocument>();
			documentsToAdd.add(docW);

			//now associate two of the documents with the variable
			curationService.associateSupportingDocumentsWithVariable(jsmith, 
															  derivedVariable4, 
															  documentsToAdd);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_VARIABLE);
			assertEquals(1, numberOfErrors);
		}
	}

	
	/**
	 * associate a non-existent document with a derived variable
	 */
	public void testDerivedVariableAssociateDocumentE2() {
		try {

			//create a non-existent derived variable			
			SupportingDocument supportingDocumentW
				= new SupportingDocument();
			supportingDocumentW.setIdentifier(1026);
			supportingDocumentW.setTitle("General Document W");
			supportingDocumentW.setDocumentCode("LHA-DocW");
			supportingDocumentW.setDescription("Document description W");
			supportingDocumentW.setFileName("DocW.doc");
			
			ArrayList<SupportingDocument> documentsToAdd
				= new ArrayList<SupportingDocument>();
			documentsToAdd.add(supportingDocumentW);

			//now associate two of the documents with the variable
			curationService.associateSupportingDocumentsWithVariable(jsmith, 
															  derivedVariable1, 
															  documentsToAdd);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_SUPPORTING_DOCUMENT);
			assertEquals(1, numberOfErrors);
		}
	}

	/**
	 * associate a single supporting documents with a derived variable
	 * where the association already exists
	 */
	public void testDerivedVariableAssociateDocumentE3() {
		try {	
			SupportingDocument docW
				= (SupportingDocument) supportingDocW.clone();
			curationService.addSupportingDocument(jsmith, docW);
			int identifierW
				= curationService.getSupportingDocumentIdentifier(jsmith, docW);
			docW.setIdentifier(identifierW);
			
			ArrayList<SupportingDocument> documentsToAdd
				= new ArrayList<SupportingDocument>();
			documentsToAdd.add(docW);

			//now associate two of the documents with the variable
			curationService.associateSupportingDocumentsWithVariable(jsmith, 
															  derivedVariable1, 
															  documentsToAdd);
			curationService.associateSupportingDocumentsWithVariable(jsmith, 
															  derivedVariable1, 
															  documentsToAdd);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors = exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.DUPLICATE_DOCUMENT_ASSOCIATION);
			assertEquals(1, numberOfErrors);
		}
	}

	/**
	 * disassociate supporting document that is associated with a derived variable
	 */
	public void testDerivedVariableDisassociateDocumentN1() {
		try {
			
			//Part I: set up associations first
			SupportingDocument docW
				= (SupportingDocument) supportingDocW.clone();
			curationService.addSupportingDocument(jsmith, docW);
			int identifierW
				= curationService.getSupportingDocumentIdentifier(jsmith, docW);
			docW.setIdentifier(identifierW);
			
			SupportingDocument docX
				= (SupportingDocument) supportingDocX.clone();
			curationService.addSupportingDocument(jsmith, docX);
			int identifierX
				= curationService.getSupportingDocumentIdentifier(jsmith, 
															      docX);

			docX.setIdentifier(identifierX);

			SupportingDocument docY
				= (SupportingDocument) supportingDocY.clone();
			curationService.addSupportingDocument(jsmith, docY);
			int identifierY
				= curationService.getSupportingDocumentIdentifier(jsmith,
																  docY);
			docY.setIdentifier(identifierY);
			
			ArrayList<SupportingDocument> documentsToAssociate
				= new ArrayList<SupportingDocument>();
			documentsToAssociate.add(docW);
			documentsToAssociate.add(docX);
			documentsToAssociate.add(docY);

			//now associate two of the documents with the variable
			curationService.associateSupportingDocumentsWithVariable(jsmith, 
															  derivedVariable1, 
															  documentsToAssociate);
		
			ArrayList<SupportingDocument> associatedDocs1
				= curationService.getSupportingDocuments(jsmith, derivedVariable1);
			assertEquals(3, associatedDocs1.size());
			assertEquals("LHA-DocW",
						 associatedDocs1.get(0).getDocumentCode());			
			assertEquals("LHA-DocX",
					 	 associatedDocs1.get(1).getDocumentCode());
			assertEquals("LHA-DocY",
						 associatedDocs1.get(2).getDocumentCode());
			
			//Part II: Disassociate two of the documents
			ArrayList<SupportingDocument> documentsToDisassociate
				= new ArrayList<SupportingDocument>();
			documentsToDisassociate.add(docW);
			documentsToDisassociate.add(docY);
			
			curationService.disassociateSupportingDocumentsFromVariable(jsmith, 
																 derivedVariable1, 
																 documentsToDisassociate);
			
			associatedDocs1
				= curationService.getSupportingDocuments(jsmith, derivedVariable1);
			assertEquals(1, associatedDocs1.size());
			assertEquals("LHA-DocX",
						 associatedDocs1.get(0).getDocumentCode());
		}
		catch(MacawException exception) {
			exception.printErrors();
			log.logException(exception);
			fail();
		}
	}

	
	/**
	 * disassociate the only supporting document that is associated with a derived variable
	 */
	public void testDerivedVariableDisassociateDocumentA1() {
		try {
			
			//Part I: set up associations first
			SupportingDocument docW
				= (SupportingDocument) supportingDocW.clone();
			curationService.addSupportingDocument(jsmith, docW);
			int identifierW
				= curationService.getSupportingDocumentIdentifier(jsmith, docW);
			docW.setIdentifier(identifierW);
			
			ArrayList<SupportingDocument> documentsToAssociate
				= new ArrayList<SupportingDocument>();
			documentsToAssociate.add(docW);

			//now associate two of the documents with the variable
			curationService.associateSupportingDocumentsWithVariable(jsmith, 
															  derivedVariable1, 
															  documentsToAssociate);
			
			ArrayList<SupportingDocument> associatedDocs1
				= curationService.getSupportingDocuments(jsmith, derivedVariable1);
			assertEquals(1, associatedDocs1.size());
			
			//Part II: Disassociate two of the documents
			ArrayList<SupportingDocument> documentsToDisassociate
				= new ArrayList<SupportingDocument>();
			documentsToDisassociate.add(docW);
			
			curationService.disassociateSupportingDocumentsFromVariable(jsmith, 
																 derivedVariable1, 
																 documentsToDisassociate);
			
			associatedDocs1
				= curationService.getSupportingDocuments(jsmith, derivedVariable1);
			assertEquals(0, associatedDocs1.size());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}
	
	/**
	 * disassociate a supporting document with a non-existent derived variables
	 */
	public void testDerivedVariableDisassociateDocumentE1() {
		try {
			
			//Part I: set up associations first
			SupportingDocument docW
				= (SupportingDocument) supportingDocW.clone();
			docW.setTitle("General Document W");
			docW.setDocumentCode("LHA-DocW");
			docW.setDescription("Document description W");
			docW.setFileName("DocW.doc");
			curationService.addSupportingDocument(jsmith, docW);
			int identifierW
				= curationService.getSupportingDocumentIdentifier(jsmith, docW);
			docW.setIdentifier(identifierW);
	
			ArrayList<SupportingDocument> documentsToDisassociate
				= new ArrayList<SupportingDocument>();
			documentsToDisassociate.add(docW);

			//create a non-existent variable
			DerivedVariable derivedVariable4 = new DerivedVariable();
			derivedVariable4.setIdentifier(7675);
			derivedVariable4.setName("DerivedVariableNameC");
			derivedVariable4.setLabel("DerivedVariableLabelC");
			derivedVariable4.setCategory("Anthropometry");
			derivedVariable4.setCleaned(true);
			derivedVariable4.setCleaningStatus("cleaned and verified");
			derivedVariable4.setAvailability("in-house staff");
			derivedVariable4.setYear("1996");
			derivedVariable4.setAlias("kwc-4");
			derivedVariable4.setFilePath("$try/DerivedVariableFileC");

			curationService.disassociateSupportingDocumentsFromVariable(jsmith, 
																 derivedVariable4, 
																 documentsToDisassociate);
			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_VARIABLE);
			assertEquals(1, numberOfErrors);
		}
	}
	
	/**
	 * disassociate a non-existent supporting document with a derived variables
	 */
	public void testDerivedVariableDisassociateDocumentE2() {
		try {
			
			//Part I: set up associations first
			SupportingDocument supportingDocumentW
				= new SupportingDocument();
			supportingDocumentW.setTitle("General Document W");
			supportingDocumentW.setDocumentCode("LHA-DocW");
			supportingDocumentW.setDescription("Document description W");
			supportingDocumentW.setFileName("DocW.doc");
	
			ArrayList<SupportingDocument> documentsToDisassociate
				= new ArrayList<SupportingDocument>();
			documentsToDisassociate.add(supportingDocumentW);

			//now disassociate two of the documents with the variable
			//note that none exist but the errors thrown need to be checked
			curationService.disassociateSupportingDocumentsFromVariable(jsmith, 
																 derivedVariable1, 
																 documentsToDisassociate);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_SUPPORTING_DOCUMENT);
			assertEquals(1, numberOfErrors);
		}
	}

	/**
	 * disassociate a supporting document with a derived variable where
	 * no associations exist
	 */
	public void testDerivedVariableDisassociateDocumentE3() {
		try {
			
			//Part I: set up associations first
			SupportingDocument docW
				= (SupportingDocument) supportingDocW.clone();
			curationService.addSupportingDocument(jsmith, docW);
			int identifierW
				= curationService.getSupportingDocumentIdentifier(jsmith, docW);
			docW.setIdentifier(identifierW);
			
			SupportingDocument docX
				= (SupportingDocument) supportingDocX.clone();
			curationService.addSupportingDocument(jsmith, docX);
			int identifierX
				= curationService.getSupportingDocumentIdentifier(jsmith, 
																  docX);
			docX.setIdentifier(identifierX);
					
			ArrayList<SupportingDocument> documentsToDisassociate
				= new ArrayList<SupportingDocument>();
			documentsToDisassociate.add(docW);
			documentsToDisassociate.add(docX);

			//now disassociate two of the documents with the variable
			//note that none exist but the errors thrown need to be checked
			curationService.disassociateSupportingDocumentsFromVariable(jsmith, 
																 		derivedVariable1, 
																 		documentsToDisassociate);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_DOCUMENT_ASSOCIATION);
			assertEquals(1, numberOfErrors);
		}
	}

	/**
	 * associate multiple ontology terms with a variable
	 */
	public void testAssociateOntologyTermN1() {
		try {
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("morbidity");
			ontologyTerm1.setDescription("The rate of incidence of a disease.");
			ontologyTerm1.setOntologyName("LHA");
			ontologyTerm1.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);	
			int identifier1 
				= curationService.getOntologyTermIdentifier(jsmith, ontologyTerm1);
			ontologyTerm1.setIdentifier(identifier1);
			
			OntologyTerm ontologyTerm2 = new OntologyTerm();
			ontologyTerm2.setTerm("allergies");
			ontologyTerm2.setDescription("A misguided reaction to foreign substances by the immune system.");
			ontologyTerm2.setOntologyName("LHA");
			ontologyTerm2.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm2);	
			int identifier2
				= curationService.getOntologyTermIdentifier(jsmith, ontologyTerm2);
			ontologyTerm2.setIdentifier(identifier2);
		
			OntologyTerm ontologyTerm3 = new OntologyTerm();
			ontologyTerm3.setTerm("dexterity");
			ontologyTerm3.setDescription("Skill and grace in physical movement, especially in the use of the hands.");
			ontologyTerm3.setOntologyName("LHA");
			ontologyTerm3.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm3);	
			int identifier3
				= curationService.getOntologyTermIdentifier(jsmith, ontologyTerm3);
			ontologyTerm3.setIdentifier(identifier3);
			
			ArrayList<OntologyTerm> termsToAssociate
				= new ArrayList<OntologyTerm>();
			termsToAssociate.add(ontologyTerm1);
			termsToAssociate.add(ontologyTerm2);
			

			curationService.associateOntologyTermsWithVariable(jsmith, 
																derivedVariable1, 
																termsToAssociate);

			ArrayList<OntologyTerm> termsForVariable
				= curationService.getOntologyTerms(jsmith, derivedVariable1);
			
			TestResultSorter<OntologyTerm> sorter 
				= new TestResultSorter<OntologyTerm>();
			sorter.sort(termsForVariable);
			
			assertEquals(2, termsForVariable.size());
			assertEquals("allergies",
						 termsForVariable.get(0).getTerm());
			assertEquals("morbidity",
					 	 termsForVariable.get(1).getTerm());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();				
		}
	}
	
	/**
	 * associate one ontology term with a variable
	 */
	public void testAssociateOntologyTermA1() {
		try {
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("morbidity");
			ontologyTerm1.setDescription("The rate of incidence of a disease.");
			ontologyTerm1.setOntologyName("LHA");
			ontologyTerm1.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);	
			int identifier1
				= curationService.getOntologyTermIdentifier(jsmith, ontologyTerm1);
			ontologyTerm1.setIdentifier(identifier1);
			
			ArrayList<OntologyTerm> termsToAssociate
				= new ArrayList<OntologyTerm>();
			termsToAssociate.add(ontologyTerm1);
			
			curationService.associateOntologyTermsWithVariable(jsmith, 
														derivedVariable1, 
														termsToAssociate);
		
			ArrayList<OntologyTerm> termsForVariable
				= curationService.getOntologyTerms(jsmith, derivedVariable1);
			
			assertEquals(1, termsForVariable.size());
			assertEquals("morbidity",
					 	 termsForVariable.get(0).getTerm());			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();				
		}		
	}
	
	/**
	 * associate a non-existent ontology term with a variable
	 */
	public void testAssociateOntologyTermE1() {
		try {
			OntologyTerm ontologyTerm = new OntologyTerm();
			ontologyTerm.setTerm("aaa");
			ontologyTerm.setDescription("aaa");
			ontologyTerm.setOntologyName("LHA");
			ontologyTerm.setNameSpace("www.lha.ac.uk:LHA");
						
			ArrayList<OntologyTerm> termsToAssociate
				= new ArrayList<OntologyTerm>();
			termsToAssociate.add(ontologyTerm);
			
			curationService.associateOntologyTermsWithVariable(jsmith, 
														derivedVariable1, 
														termsToAssociate);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_ONTOLOGY_TERM);
			assertEquals(1, numberOfErrors);
		}	
	}
	
	/**
	 * associate an ontology term with a non-existent variable
	 */
	public void testAssociateOntologyTermE2() {
		try {
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("morbidity");
			ontologyTerm1.setDescription("The rate of incidence of a disease.");
			ontologyTerm1.setOntologyName("LHA");
			ontologyTerm1.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);	
			int identifier1 
				= curationService.getOntologyTermIdentifier(jsmith, ontologyTerm1);
			ontologyTerm1.setIdentifier(identifier1);
			
			//create a non-existent variable
			DerivedVariable derivedVariable4 = new DerivedVariable();
			derivedVariable4.setIdentifier(7675);
			derivedVariable4.setName("DerivedVariableNameC");
			derivedVariable4.setLabel("DerivedVariableLabelC");
			derivedVariable4.setCategory("Anthropometry");
			derivedVariable4.setCleaned(true);
			derivedVariable4.setCleaningStatus("cleaned and verified");
			derivedVariable4.setAvailability("in-house staff");
			derivedVariable4.setYear("1996");
			derivedVariable4.setAlias("kwc-4");
			derivedVariable4.setFilePath("$try/DerivedVariableFileC");


			ArrayList<OntologyTerm> termsToAssociate
				= new ArrayList<OntologyTerm>();
			termsToAssociate.add(ontologyTerm1);
			
			curationService.associateOntologyTermsWithVariable(jsmith, 
														derivedVariable4, 
														termsToAssociate);

			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_VARIABLE);
			assertEquals(1, numberOfErrors);			
		}
	}
	
	/**
	 * associate multiple ontology terms with a variable when those associations
	 * already exist.
	 */
	public void testAssociateOntologyTermE3() {
		try {
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("morbidity");
			ontologyTerm1.setDescription("The rate of incidence of a disease.");
			ontologyTerm1.setOntologyName("LHA");
			ontologyTerm1.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);	
			int identifier1 
				= curationService.getOntologyTermIdentifier(jsmith, ontologyTerm1);
			ontologyTerm1.setIdentifier(identifier1);
			
			OntologyTerm ontologyTerm2 = new OntologyTerm();
			ontologyTerm2.setTerm("allergies");
			ontologyTerm2.setDescription("A misguided reaction to foreign substances by the immune system.");
			ontologyTerm2.setOntologyName("LHA");
			ontologyTerm2.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm2);	
			int identifier2
				= curationService.getOntologyTermIdentifier(jsmith, ontologyTerm2);
			ontologyTerm2.setIdentifier(identifier2);
		
			OntologyTerm ontologyTerm3 = new OntologyTerm();
			ontologyTerm3.setTerm("dexterity");
			ontologyTerm3.setDescription("Skill and grace in physical movement, especially in the use of the hands.");
			ontologyTerm3.setOntologyName("LHA");
			ontologyTerm3.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm3);	
			int identifier3
				= curationService.getOntologyTermIdentifier(jsmith, ontologyTerm3);
			ontologyTerm3.setIdentifier(identifier3);
			
			ArrayList<OntologyTerm> termsToAssociate
				= new ArrayList<OntologyTerm>();
			termsToAssociate.add(ontologyTerm1);
			termsToAssociate.add(ontologyTerm2);
			
			curationService.associateOntologyTermsWithVariable(jsmith, 
														derivedVariable1, 
														termsToAssociate);

			curationService.associateOntologyTermsWithVariable(jsmith, 
														derivedVariable1, 
														termsToAssociate);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors = exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.DUPLICATE_ONTOLOGY_TERM_ASSOCIATION);
			assertEquals(1, numberOfErrors);
		}
	}

	/**
	 * disassociate multiple ontology terms from a variable
	 */
	public void testDisassociateOntologyTermN1() {
		try {
			//Part I: associate the terms
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("morbidity");
			ontologyTerm1.setDescription("The rate of incidence of a disease.");
			ontologyTerm1.setOntologyName("LHA");
			ontologyTerm1.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);	
			int identifier1 
				= curationService.getOntologyTermIdentifier(jsmith, ontologyTerm1);
			ontologyTerm1.setIdentifier(identifier1);
			
			OntologyTerm ontologyTerm2 = new OntologyTerm();
			ontologyTerm2.setTerm("allergies");
			ontologyTerm2.setDescription("A misguided reaction to foreign substances by the immune system.");
			ontologyTerm2.setOntologyName("LHA");
			ontologyTerm2.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm2);	
			int identifier2
				= curationService.getOntologyTermIdentifier(jsmith, ontologyTerm2);
			ontologyTerm2.setIdentifier(identifier2);
		
			OntologyTerm ontologyTerm3 = new OntologyTerm();
			ontologyTerm3.setTerm("dexterity");
			ontologyTerm3.setDescription("Skill and grace in physical movement, especially in the use of the hands.");
			ontologyTerm3.setOntologyName("LHA");
			ontologyTerm3.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm3);	
			int identifier3
				= curationService.getOntologyTermIdentifier(jsmith, ontologyTerm3);
			ontologyTerm3.setIdentifier(identifier3);
			
			ArrayList<OntologyTerm> termsToAssociate
				= new ArrayList<OntologyTerm>();
			termsToAssociate.add(ontologyTerm1);
			termsToAssociate.add(ontologyTerm2);
			termsToAssociate.add(ontologyTerm3);
			
			curationService.associateOntologyTermsWithVariable(jsmith, 
														derivedVariable1, 
														termsToAssociate);
			ArrayList<OntologyTerm> termsForVariable
				= curationService.getOntologyTerms(jsmith, derivedVariable1);
			TestResultSorter<OntologyTerm> sorter 
				= new TestResultSorter<OntologyTerm>();
			sorter.sort(termsForVariable);		
			assertEquals(3, termsForVariable.size());
			
			//Part II: choose some of the terms to disassociate
			ArrayList<OntologyTerm> termsToDisassociate
				= new ArrayList<OntologyTerm>();
			termsToDisassociate.add(ontologyTerm1);
			termsToDisassociate.add(ontologyTerm3);
			curationService.disassociateOntologyTermsFromVariable(jsmith, 
														   derivedVariable1, 
														   termsToDisassociate);
			termsForVariable
				= curationService.getOntologyTerms(jsmith, derivedVariable1);
			assertEquals(1, termsForVariable.size());
			assertEquals("allergies",
						 termsForVariable.get(0).getTerm());
			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}
	
	/**
	 * disassociate the only ontology term that is associated with a variable
	 */
	public void testDisassociateOntologyTermA1() {
		try {
			//Part I: associate the terms
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("morbidity");
			ontologyTerm1.setDescription("The rate of incidence of a disease.");
			ontologyTerm1.setOntologyName("LHA");
			ontologyTerm1.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);	
			int identifier1 
				= curationService.getOntologyTermIdentifier(jsmith, ontologyTerm1);
			ontologyTerm1.setIdentifier(identifier1);
			
			ArrayList<OntologyTerm> termsToAssociate
				= new ArrayList<OntologyTerm>();
			termsToAssociate.add(ontologyTerm1);
			
			curationService.associateOntologyTermsWithVariable(jsmith, 
														derivedVariable1, 
														termsToAssociate);
			ArrayList<OntologyTerm> termsForVariable
				= curationService.getOntologyTerms(jsmith, derivedVariable1);
			TestResultSorter<OntologyTerm> sorter 
				= new TestResultSorter<OntologyTerm>();
			sorter.sort(termsForVariable);		
			assertEquals(1, termsForVariable.size());
			
			//Part II: choose some of the terms to disassociate
			ArrayList<OntologyTerm> termsToDisassociate
				= new ArrayList<OntologyTerm>();
			termsToDisassociate.add(ontologyTerm1);
			curationService.disassociateOntologyTermsFromVariable(jsmith, 
														   derivedVariable1, 
														   termsToDisassociate);
			termsForVariable
				= curationService.getOntologyTerms(jsmith, derivedVariable1);
			assertEquals(0, termsForVariable.size());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();			
		}	
	}

	/**
	 * disassociate a non-existent ontology term with a variable
	 */
	public void testDisassociateOntologyTermE1() {
		try {
			//Part I: associate terms with the variable
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("morbidity");
			ontologyTerm1.setDescription("The rate of incidence of a disease.");
			ontologyTerm1.setOntologyName("LHA");
			ontologyTerm1.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);	
			int identifier1 
				= curationService.getOntologyTermIdentifier(jsmith, ontologyTerm1);
			ontologyTerm1.setIdentifier(identifier1);
			
			OntologyTerm ontologyTerm2 = new OntologyTerm();
			ontologyTerm2.setTerm("allergies");
			ontologyTerm2.setDescription("A misguided reaction to foreign substances by the immune system.");
			ontologyTerm2.setOntologyName("LHA");
			ontologyTerm2.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm2);	
			int identifier2
				= curationService.getOntologyTermIdentifier(jsmith, ontologyTerm2);
			ontologyTerm2.setIdentifier(identifier2);
		
			ArrayList<OntologyTerm> termsToAssociate
				= new ArrayList<OntologyTerm>();
			termsToAssociate.add(ontologyTerm1);
			termsToAssociate.add(ontologyTerm2);
			
			curationService.associateOntologyTermsWithVariable(jsmith, 
														derivedVariable1, 
														termsToAssociate);
			ArrayList<OntologyTerm> termsForVariable
				= curationService.getOntologyTerms(jsmith, derivedVariable1);
			TestResultSorter<OntologyTerm> sorter 
				= new TestResultSorter<OntologyTerm>();
			sorter.sort(termsForVariable);		
			assertEquals(2, termsForVariable.size());
			
			//Part II: choose a non-existent term to disassociate
			OntologyTerm ontologyTerm44 = new OntologyTerm();
			ontologyTerm44.setTerm("ccc");
			ontologyTerm44.setDescription("ddd");
			ontologyTerm44.setOntologyName("LHA");
			ontologyTerm44.setNameSpace("www.lha.ac.uk:LHA");
			ArrayList<OntologyTerm> termsToDisassociate
				= new ArrayList<OntologyTerm>();
			termsToDisassociate.add(ontologyTerm44);
			curationService.disassociateOntologyTermsFromVariable(jsmith, 
														   derivedVariable1, 
														   termsToDisassociate);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_ONTOLOGY_TERM);
			assertEquals(1, numberOfErrors);			
		}
	}

	/**
	 * disassociate an ontology term with a non-existent variable
	 */
	public void testDisassociateOntologyTermE2() {
		try {
			//Part I: associate the terms
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("morbidity");
			ontologyTerm1.setDescription("The rate of incidence of a disease.");
			ontologyTerm1.setOntologyName("LHA");
			ontologyTerm1.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);	
			int identifier1 
				= curationService.getOntologyTermIdentifier(jsmith, ontologyTerm1);
			ontologyTerm1.setIdentifier(identifier1);
			
			OntologyTerm ontologyTerm2 = new OntologyTerm();
			ontologyTerm2.setTerm("allergies");
			ontologyTerm2.setDescription("A misguided reaction to foreign substances by the immune system.");
			ontologyTerm2.setOntologyName("LHA");
			ontologyTerm2.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm2);	
			int identifier2
				= curationService.getOntologyTermIdentifier(jsmith, ontologyTerm2);
			ontologyTerm2.setIdentifier(identifier2);
		

			//Part II: Disassociate terms with a non-existent variable

			//create a non-existent variable
			DerivedVariable derivedVariable4 = new DerivedVariable();
			derivedVariable4.setIdentifier(7675);
			derivedVariable4.setName("DerivedVariableNameC");
			derivedVariable4.setLabel("DerivedVariableLabelC");
			derivedVariable4.setCategory("Anthropometry");
			derivedVariable4.setCleaned(true);
			derivedVariable4.setCleaningStatus("cleaned and verified");
			derivedVariable4.setAvailability("in-house staff");
			derivedVariable4.setYear("1996");
			derivedVariable4.setAlias("kwc-4");
			derivedVariable4.setFilePath("$try/DerivedVariableFileC");

			ArrayList<OntologyTerm> termsToDissassociate
				= new ArrayList<OntologyTerm>();
			termsToDissassociate.add(ontologyTerm1);
			termsToDissassociate.add(ontologyTerm2);
			curationService.disassociateOntologyTermsFromVariable(jsmith, 
														   derivedVariable4, 
														   termsToDissassociate);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_VARIABLE);
			assertEquals(1, numberOfErrors);			
		}
	}

	/**
	 * disassociate an ontology term with a variable where no associations exist
	 */
	public void testDisassociateOntologyTermE3() {
		try {
			//Part I: associate the terms
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("morbidity");
			ontologyTerm1.setDescription("The rate of incidence of a disease.");
			ontologyTerm1.setOntologyName("LHA");
			ontologyTerm1.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);	
			int identifier1 
				= curationService.getOntologyTermIdentifier(jsmith, ontologyTerm1);
			ontologyTerm1.setIdentifier(identifier1);
			
			ArrayList<OntologyTerm> termsToDissassociate
				= new ArrayList<OntologyTerm>();
			termsToDissassociate.add(ontologyTerm1);
			curationService.disassociateOntologyTermsFromVariable(jsmith, 
														   derivedVariable1, 
														   termsToDissassociate);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_ONTOLOGY_TERM_ASSOCIATION);
			assertEquals(1, numberOfErrors);			
		}
	}

	/**
	 * associate multiple source variables with a derived variable
	 */
	public void testAssociateSourceVariableN1() {
		try {
			ArrayList<Variable> sourceVariables
				= new ArrayList<Variable>();
			sourceVariables.add(derivedVariable1);
			sourceVariables.add(rawVariable1);
			
			curationService.associateSourceVariables(jsmith, 
											  derivedVariable2, 
											  sourceVariables);
			
			ArrayList<Variable> sourceVariablesSoFar
				= curationService.getSourceVariables(jsmith, derivedVariable2);
			TestResultSorter<Variable> sorter
				= new TestResultSorter<Variable>();
			sorter.sort(sourceVariablesSoFar);
			
			assertEquals(2, sourceVariablesSoFar.size());
			assertEquals("DerivedVariableNameA",
						 sourceVariablesSoFar.get(0).getName());
			
			assertEquals("HT87",
					 sourceVariablesSoFar.get(1).getName());	
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();			
		}	
	}
	
	/**
	 * associate a single source variable with a derived variable
	 */
	public void testAssociateSourceVariableA1() {
		try {
			ArrayList<Variable> sourceVariables
				= new ArrayList<Variable>();
			sourceVariables.add(derivedVariable1);
		
			curationService.associateSourceVariables(jsmith, 
											  derivedVariable2, 
											  sourceVariables);
		
			ArrayList<Variable> sourceVariablesSoFar
				= curationService.getSourceVariables(jsmith, derivedVariable2);
		
			assertEquals(1, sourceVariablesSoFar.size());
			assertEquals("DerivedVariableNameA",
						sourceVariablesSoFar.get(0).getName());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();			
		}	
	}
	
	/**
	 * associate a source variable with a non-existent derived variable
	 */
	public void testAssociateSourceVariableE1() {
		try {
			
			ArrayList<Variable> sourceVariables
				= new ArrayList<Variable>();
			sourceVariables.add(derivedVariable1);
	
			//create a non-existent derived variable
			DerivedVariable derivedVariable4 = new DerivedVariable();
			derivedVariable4.setIdentifier(35342);
			derivedVariable4.setName("DerivedVariableNameD");
			derivedVariable4.setLabel("DerivedVariableLabelD");
			derivedVariable4.setCategory("Anthropometry");
			derivedVariable4.setCleaned(true);
			derivedVariable4.setCleaningStatus("cleaned and verified");
			derivedVariable4.setAvailability("in-house staff");
			derivedVariable4.setYear("1996");
			derivedVariable4.setAlias("kwc-4");
			derivedVariable4.setFilePath("$try/DerivedVariableFileD");
						
			curationService.associateSourceVariables(jsmith, 
											  derivedVariable4, 
											  sourceVariables);
			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_VARIABLE);
			assertEquals(1, numberOfErrors);			
		}
	}
	
	/**
	 * associate a non-existent source variable with a derived variable
	 */
	public void testAssociateSourceVariableE2() {
		try {
			
			//create a non-existent derived variable
			DerivedVariable derivedVariable4 = new DerivedVariable();
			derivedVariable4.setIdentifier(35342);
			derivedVariable4.setName("DerivedVariableNameD");
			derivedVariable4.setLabel("DerivedVariableLabelD");
			derivedVariable4.setCategory("Anthropometry");
			derivedVariable4.setCleaned(true);
			derivedVariable4.setCleaningStatus("cleaned and verified");
			derivedVariable4.setAvailability("in-house staff");
			derivedVariable4.setYear("1996");
			derivedVariable4.setAlias("kwc-4");
			derivedVariable4.setFilePath("$try/DerivedVariableFileD");

			ArrayList<Variable> sourceVariables
				= new ArrayList<Variable>();
			sourceVariables.add(derivedVariable4);

			curationService.associateSourceVariables(jsmith, 
											  derivedVariable4, 
											  sourceVariables);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_VARIABLE);
			assertEquals(1, numberOfErrors);			
		}
	}

	/**
	 * associate a source variable with a derived variable where the 
	 * association already exists
	 */
	public void testAssociateSourceVariableE3() {
		try {
			ArrayList<Variable> sourceVariables
				= new ArrayList<Variable>();
			sourceVariables.add(derivedVariable1);
	
			curationService.associateSourceVariables(jsmith, 
											  derivedVariable2, 
											  sourceVariables);
			curationService.associateSourceVariables(jsmith, 
					  						  derivedVariable2, 
					  						  sourceVariables);	
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.DUPLICATE_VARIABLE_ASSOCIATION);
			assertEquals(1, numberOfErrors);			
		}
	}

	
	/**
	 * disassociate source variables from a derived variable
	 */
	public void testDisassociateSourceVariableN1() {
		try {
			
			//Part I: Associate source variables
			ArrayList<Variable> sourceVariablesToAssociate
				= new ArrayList<Variable>();
			sourceVariablesToAssociate.add(rawVariable1);
			sourceVariablesToAssociate.add(rawVariable2);
			sourceVariablesToAssociate.add(rawVariable3);

			
			//Part II: Pick two of them to disassociate
			curationService.associateSourceVariables(jsmith, 
											  derivedVariable2, 
											  sourceVariablesToAssociate);
			
			ArrayList<Variable> sourceVariablesToDisassociate
				= new ArrayList<Variable>();
			sourceVariablesToDisassociate.add(rawVariable1);
			sourceVariablesToDisassociate.add(rawVariable3);
			
			curationService.disassociateSourceVariables(jsmith, 
												 derivedVariable2, 
												 sourceVariablesToDisassociate);
			
			ArrayList<Variable> sourceVariablesSoFar
				= curationService.getSourceVariables(jsmith, derivedVariable2);
			assertEquals(1, sourceVariablesSoFar.size());
			assertEquals("WEIGHT87",
						  sourceVariablesSoFar.get(0).getName());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();			
		}	
	}

	/**
	 * disassociate the only source variable associated
	 * with a derived variable
	 */
	public void testDisassociateSourceVariableA1() {
		try {
			//Part I: Associate source variables
			ArrayList<Variable> sourceVariablesToAssociate
				= new ArrayList<Variable>();
			sourceVariablesToAssociate.add(rawVariable1);
			
			//Part II: Pick two of them to disassociate
			curationService.associateSourceVariables(jsmith, 
											  derivedVariable2, 
											  sourceVariablesToAssociate);
			
			ArrayList<Variable> sourceVariablesToDisassociate
				= new ArrayList<Variable>();
			sourceVariablesToDisassociate.add(rawVariable1);
			
			curationService.disassociateSourceVariables(jsmith, 
												 derivedVariable2, 
												 sourceVariablesToDisassociate);
			
			ArrayList<Variable> sourceVariablesSoFar
				= curationService.getSourceVariables(jsmith, derivedVariable2);
			assertEquals(0, sourceVariablesSoFar.size());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();			
		}	
	}

	/**
	 * disassociate a non-existent source variable from a
	 * a derived variable
	 */
	public void testDisassociateSourceVariableE1() {
		try {
			
			//create a non-existent derived variable
			DerivedVariable derivedVariable4 = new DerivedVariable();
			derivedVariable4.setIdentifier(35342);
			derivedVariable4.setName("DerivedVariableNameD");
			derivedVariable4.setLabel("DerivedVariableLabelD");
			derivedVariable4.setCategory("Anthropometry");
			derivedVariable4.setCleaned(true);
			derivedVariable4.setCleaningStatus("cleaned and verified");
			derivedVariable4.setAvailability("in-house staff");
			derivedVariable4.setYear("1996");
			derivedVariable4.setAlias("kwc-4");
			derivedVariable4.setFilePath("$try/DerivedVariableFileD");
			
			ArrayList<Variable> sourceVariablesToDisassociate
				= new ArrayList<Variable>();
			sourceVariablesToDisassociate.add(derivedVariable4);

			curationService.disassociateSourceVariables(jsmith, 
												 derivedVariable2, 
												 sourceVariablesToDisassociate);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			exception.printErrors();
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_VARIABLE);

			assertEquals(1, numberOfErrors);			
		}
	}

	/**
	 * disassociate source variables from a
	 * a non-existent derived variable
	 */
	public void testDisassociateSourceVariableE2() {
		try {
			//create a non-existent derived variable
			DerivedVariable derivedVariable55 = new DerivedVariable();
			derivedVariable55.setIdentifier(35342);
			derivedVariable55.setName("DerivedVariableNameD");
			derivedVariable55.setLabel("DerivedVariableLabelD");
			derivedVariable55.setCategory("Anthropometry");
			derivedVariable55.setCleaned(true);
			derivedVariable55.setCleaningStatus("cleaned and verified");
			derivedVariable55.setAvailability("in-house staff");
			derivedVariable55.setYear("1996");
			derivedVariable55.setAlias("kwc-4");
			derivedVariable55.setFilePath("$try/DerivedVariableFileD");
			
			ArrayList<Variable> sourceVariablesToDisassociate
				= new ArrayList<Variable>();
			sourceVariablesToDisassociate.add(rawVariable1);
			sourceVariablesToDisassociate.add(rawVariable2);

			curationService.disassociateSourceVariables(jsmith, 
												 derivedVariable55, 
												 sourceVariablesToDisassociate);
			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			exception.printErrors();
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_VARIABLE);

			assertEquals(1, numberOfErrors);			
		}
	}

	/**
	 * disassociate a source variable from a
	 * a derived variable where no association exists
	 */
	public void testDisassociateSourceVariableE3() {
		try {
			ArrayList<Variable> sourceVariablesToDisassociate
				= new ArrayList<Variable>();
			sourceVariablesToDisassociate.add(rawVariable1);

			curationService.disassociateSourceVariables(jsmith, 
												 derivedVariable2, 
												 sourceVariablesToDisassociate);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_VARIABLE_ASSOCIATION);
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

