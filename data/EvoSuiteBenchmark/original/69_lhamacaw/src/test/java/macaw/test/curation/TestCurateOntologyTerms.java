package macaw.test.curation;

import macaw.businessLayer.OntologyTerm;
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

public class TestCurateOntologyTerms extends MacawCurationTestCase {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================

	// ==========================================
	// Section Construction
	// ==========================================
	public TestCurateOntologyTerms() {
		super("Test Ontology Term");
	}
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	/**
	 * validation fails if term is left blank
	 */
	public void testValidationN1() {
		try {
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("");
			ontologyTerm1.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors = exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.INVALID_ONTOLOGY_TERM);
			assertEquals(1, numberOfErrors);
			log.logException(exception);
		}
	}
	
	/**
	 * validation fails if ontology name is left blank
	 */
	public void testValidationN2() {
		try {
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("morbidity");
			ontologyTerm1.setOntologyName("");
			ontologyTerm1.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.INVALID_ONTOLOGY_TERM);
			assertEquals(1, numberOfErrors);
			log.logException(exception);
		}
	}

	/**
	 * validation fails if ontology name space is left blank
	 */
	public void testValidationN3() {
		try {
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("morbidity");
			ontologyTerm1.setOntologyName("LHA");
			ontologyTerm1.setNameSpace("");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.INVALID_ONTOLOGY_TERM);
			assertEquals(1, numberOfErrors);
			log.logException(exception);
		}
	}

	/**
	 * validation fails if multiple errors happen
	 */
	public void testValidationN4() {
		try {
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("");
			ontologyTerm1.setOntologyName("");
			ontologyTerm1.setNameSpace("");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors = exception.getNumberOfErrors();
			assertEquals(3, totalNumberOfErrors);
			
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.INVALID_ONTOLOGY_TERM);
			assertEquals(3, numberOfErrors);
		}
	}

	/**
	 * add ontology term to list
	 */	
	public void testAddOntologyTermN1() {
		try {
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("morbidity");
			ontologyTerm1.setDescription("The rate of incidence of a disease.");
			ontologyTerm1.setOntologyName("LHA");
			ontologyTerm1.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);	
			
			OntologyTerm ontologyTerm2 = new OntologyTerm();
			ontologyTerm2.setTerm("allergies");
			ontologyTerm2.setDescription("A misguided reaction to foreign substances by the immune system.");
			ontologyTerm2.setOntologyName("LHA");
			ontologyTerm2.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm2);	

			OntologyTerm ontologyTerm3 = new OntologyTerm();
			ontologyTerm3.setTerm("dexterity");
			ontologyTerm3.setDescription("Skill and grace in physical movement, especially in the use of the hands.");
			ontologyTerm3.setOntologyName("LHA");
			ontologyTerm3.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm3);	

			ArrayList<OntologyTerm> termsSoFar
				= curationService.getAllOntologyTerms(jsmith);
			TestResultSorter<OntologyTerm> sorter 
				= new TestResultSorter<OntologyTerm>();
			sorter.sort(termsSoFar);
			
			assertEquals(3, termsSoFar.size());

			assertEquals("allergies-LHA",
						 termsSoFar.get(0).getDisplayName());
			assertEquals("dexterity-LHA",
						 termsSoFar.get(1).getDisplayName());
			assertEquals("morbidity-LHA",
					 	 termsSoFar.get(2).getDisplayName());	
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	/**
	 * add two ontology terms that have the same term but come from different
	 * ontology names 
	 */
	public void testAddOntologyTermA1() {
		try {
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("dexterity");
			ontologyTerm1.setDescription("Skill and grace in physical movement, especially in the use of the hands.");
			ontologyTerm1.setOntologyName("LHA");
			ontologyTerm1.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);	

			OntologyTerm ontologyTerm2 = new OntologyTerm();
			ontologyTerm2.setTerm("dexterity");
			ontologyTerm2.setDescription("Mental skill or adroitness; cleverness.");
			ontologyTerm2.setOntologyName("CP");
			ontologyTerm2.setNameSpace("www.cognitive-psychology.org");
			curationService.addOntologyTerm(jsmith, ontologyTerm2);
			
			ArrayList<OntologyTerm> termsSoFar
				= curationService.getAllOntologyTerms(jsmith);
			TestResultSorter<OntologyTerm> sorter 
				= new TestResultSorter<OntologyTerm>();
			sorter.sort(termsSoFar);
			assertEquals(2, termsSoFar.size());

			assertEquals("dexterity-CP",
						 termsSoFar.get(0).getDisplayName());
			assertEquals("dexterity-LHA",
						 termsSoFar.get(1).getDisplayName());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	/**
	 * add two ontology terms that have the same term and the same ontology
	 * names 
	 */
	public void testAddOntologyTermE1() {
		try {
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("dexterity");
			ontologyTerm1.setDescription("Skill and grace in physical movement, especially in the use of the hands.");
			ontologyTerm1.setOntologyName("LHA");
			ontologyTerm1.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);	

			OntologyTerm ontologyTerm2 = new OntologyTerm();
			ontologyTerm2.setTerm("dexterity");
			ontologyTerm2.setDescription("Mental skill or adroitness; cleverness.");
			ontologyTerm2.setOntologyName("LHA");
			ontologyTerm2.setNameSpace("www.cognitive-psychology.org");
			curationService.addOntologyTerm(jsmith, ontologyTerm2);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors = exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.DUPLICATE_ONTOLOGY_TERM);
			assertEquals(1, numberOfErrors);
		}
	}
	
	/**
	 * update ontology term to list
	 */	
	public void testUpdateOntologyTermN1() {
		try {
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("morbidity");
			ontologyTerm1.setDescription("The rate of incidence of a disease.");
			ontologyTerm1.setOntologyName("LHA");
			ontologyTerm1.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);	
			int identifier = curationService.getOntologyTermIdentifier(jsmith, ontologyTerm1);
			ontologyTerm1.setIdentifier(identifier);
			ontologyTerm1.setTerm("aaa");
			curationService.updateOntologyTerm(jsmith, ontologyTerm1);
			ArrayList<OntologyTerm> ontologyTermsSoFar
				= curationService.getAllOntologyTerms(jsmith);
			assertEquals("aaa",
						 ontologyTermsSoFar.get(0).getTerm());

			ontologyTerm1.setDescription("bbb bbb");
			curationService.updateOntologyTerm(jsmith, ontologyTerm1);
			ontologyTermsSoFar
				= curationService.getAllOntologyTerms(jsmith);
			assertEquals("bbb bbb",
						 ontologyTermsSoFar.get(0).getDescription());
			
			ontologyTerm1.setOntologyName("bbb bbb");
			curationService.updateOntologyTerm(jsmith, ontologyTerm1);
			ontologyTermsSoFar
				= curationService.getAllOntologyTerms(jsmith);
			assertEquals("bbb bbb",
						 ontologyTermsSoFar.get(0).getOntologyName());

			ontologyTerm1.setNameSpace("ccc");
			curationService.updateOntologyTerm(jsmith, ontologyTerm1);
			ontologyTermsSoFar
				= curationService.getAllOntologyTerms(jsmith);
			assertEquals("ccc",
						 ontologyTermsSoFar.get(0).getNameSpace());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	/**
	 * make sure update doesn't result in a duplicate by changing the name space
	 */	
	public void testUpdateOntologyTermE1() {
		try {
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("dexterity");
			ontologyTerm1.setDescription("Skill and grace in physical movement, especially in the use of the hands.");
			ontologyTerm1.setOntologyName("LHA");
			ontologyTerm1.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);	
			
			OntologyTerm ontologyTerm2 = new OntologyTerm();
			ontologyTerm2.setTerm("dexterity");
			ontologyTerm2.setDescription("Mental skill or adroitness; cleverness.");
			ontologyTerm2.setOntologyName("CP");
			ontologyTerm2.setNameSpace("www.cognitive-psychology.org");
			curationService.addOntologyTerm(jsmith, ontologyTerm2);			
			int identifier2
				= curationService.getOntologyTermIdentifier(jsmith, ontologyTerm2);
			ontologyTerm2.setIdentifier(identifier2);
			ontologyTerm2.setNameSpace("www.lha.ac.uk:LHA");
			curationService.updateOntologyTerm(jsmith, ontologyTerm2);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.DUPLICATE_ONTOLOGY_TERM);
			assertEquals(1, numberOfErrors);
		}
	}

	/**
	 * make sure update doesn't result in a duplicate by changing the ontology name
	 */	
	public void testUpdateOntologyTermE2() {
		try {
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("dexterity");
			ontologyTerm1.setDescription("Skill and grace in physical movement, especially in the use of the hands.");
			ontologyTerm1.setOntologyName("LHA");
			ontologyTerm1.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);	
			
			OntologyTerm ontologyTerm2 = new OntologyTerm();
			ontologyTerm2.setTerm("dexterity");
			ontologyTerm2.setDescription("Mental skill or adroitness; cleverness.");
			ontologyTerm2.setOntologyName("CP");
			ontologyTerm2.setNameSpace("www.cognitive-psychology.org");
			curationService.addOntologyTerm(jsmith, ontologyTerm2);			
			int identifier2
				= curationService.getOntologyTermIdentifier(jsmith, ontologyTerm2);
			ontologyTerm2.setIdentifier(identifier2);
			ontologyTerm2.setOntologyName("LHA");
			curationService.updateOntologyTerm(jsmith, ontologyTerm2);			
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors
				= exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.DUPLICATE_ONTOLOGY_TERM);
			assertEquals(1, numberOfErrors);
		}
	}

	/**
	 * reject update of non-existent term
	 */	
	public void testUpdateOntologyTermE3() {
		try {
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("dexterity");
			ontologyTerm1.setDescription("Skill and grace in physical movement, especially in the use of the hands.");
			ontologyTerm1.setOntologyName("LHA");
			ontologyTerm1.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);	
			
			OntologyTerm ontologyTerm2 = new OntologyTerm();
			ontologyTerm2.setIdentifier(2342);
			ontologyTerm2.setTerm("dexterity");
			ontologyTerm2.setDescription("Mental skill or adroitness; cleverness.");
			ontologyTerm2.setOntologyName("CP");
			ontologyTerm2.setNameSpace("www.cognitive-psychology.org");
			curationService.updateOntologyTerm(jsmith, ontologyTerm2);			
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
	 * delete ontology terms from list
	 */	
	public void testDeleteOntologyTermN1() {
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

			ArrayList<OntologyTerm> itemsToDelete
				= new ArrayList<OntologyTerm>();
			itemsToDelete.add(ontologyTerm1);
			itemsToDelete.add(ontologyTerm2);
			curationService.deleteOntologyTerms(jsmith, itemsToDelete);
			
			ArrayList<OntologyTerm> termsSoFar
				= curationService.getAllOntologyTerms(jsmith);
			assertEquals(1, termsSoFar.size());
			
			assertEquals("dexterity",
						 termsSoFar.get(0).getTerm());
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	/**
	 * delete item from a one-item list
	 */	
	public void testDeleteOntologyTermA1() {
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

			ArrayList<OntologyTerm> itemsToDelete
				= new ArrayList<OntologyTerm>();
			itemsToDelete.add(ontologyTerm1);

			curationService.deleteOntologyTerms(jsmith, itemsToDelete);
			
			ArrayList<OntologyTerm> termsSoFar
				= curationService.getAllOntologyTerms(jsmith);
			assertEquals(0, termsSoFar.size());			
		}
		catch(MacawException exception) {
			log.logException(exception);
			fail();
		}
	}

	/**
	 * delete non-existent ontology term from list
	 */	
	public void testDeleteOntologyTermE1() {
		try {
			OntologyTerm ontologyTerm1 = new OntologyTerm();
			ontologyTerm1.setTerm("morbidity");
			ontologyTerm1.setDescription("The rate of incidence of a disease.");
			ontologyTerm1.setOntologyName("LHA");
			ontologyTerm1.setNameSpace("www.lha.ac.uk:LHA");
			curationService.addOntologyTerm(jsmith, ontologyTerm1);	

			OntologyTerm ontologyTerm2 = new OntologyTerm();
			ontologyTerm2.setIdentifier(2432);
			ontologyTerm2.setTerm("allergies");
			ontologyTerm2.setDescription("A misguided reaction to foreign substances by the immune system.");
			ontologyTerm2.setOntologyName("LHA");
			ontologyTerm2.setNameSpace("www.lha.ac.uk:LHA");
			
			ArrayList<OntologyTerm> itemsToDelete
				= new ArrayList<OntologyTerm>();
			itemsToDelete.add(ontologyTerm2);
			curationService.deleteOntologyTerms(jsmith, itemsToDelete);
			fail();
		}
		catch(MacawException exception) {
			int totalNumberOfErrors = exception.getNumberOfErrors();
			assertEquals(1, totalNumberOfErrors);
			int numberOfErrors
				= exception.getNumberOfErrors(MacawErrorType.NON_EXISTENT_ONTOLOGY_TERM);
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

