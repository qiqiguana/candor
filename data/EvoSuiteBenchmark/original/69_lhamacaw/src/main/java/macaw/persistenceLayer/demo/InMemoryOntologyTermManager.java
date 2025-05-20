package macaw.persistenceLayer.demo;

import macaw.businessLayer.*;
import macaw.system.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * manages instances of {@link macaw.businessLayer.OntologyTerm} in memory.
 * 
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

public class InMemoryOntologyTermManager extends InMemoryCurationConceptManager {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private int ontologyTermKey;
	private HashMap<Integer, OntologyTerm> ontologyTermFromIdentifier;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public InMemoryOntologyTermManager(InMemoryChangeEventManager changeEventManager) {
		super(changeEventManager);
		ontologyTermFromIdentifier
			= new HashMap<Integer,OntologyTerm>();		
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	public ArrayList<OntologyTerm> getAllOntologyTerms(User user) throws MacawException {
		ArrayList<OntologyTerm> ontologyTerms 
			= new ArrayList<OntologyTerm>();
		ontologyTerms.addAll(ontologyTermFromIdentifier.values());
		
		ArrayList<OntologyTerm> cloneOntologyTerms
			= new ArrayList<OntologyTerm>();
		for (OntologyTerm ontologyTerm : ontologyTerms) {
			cloneOntologyTerms.add( (OntologyTerm) ontologyTerm.clone());
		}
		
		return cloneOntologyTerms;
	}
	
	public void addOntologyTerm(User user,
			  					OntologyTerm ontologyTerm) throws MacawException {
	
		//Part I: Validate parameters
		//check basic field errors
		OntologyTerm.validateFields(ontologyTerm);		
		//check for duplicates
		checkOntologyTermDuplicates(ontologyTerm);

		//Part II: Add Document
		ontologyTermKey++;
		ontologyTerm.setNewRecord(false);
		ontologyTerm.setIdentifier(ontologyTermKey);
		ontologyTermFromIdentifier.put(ontologyTermKey, ontologyTerm);
		
		//Part III: Record changes
		//ArrayList<MacawChangeEvent> changeEvents 
		//	= ChangeEventGenerator.addOntologyTermChange(user, ontologyTerm);
		//registerChangeEvents(changeEvents);
	}
		
	public void updateOntologyTerm(User user,							
								   OntologyTerm revisedOntologyTerm) throws MacawException {
			
		//Part I: Validate parameters
		//check basic field errors
		OntologyTerm.validateFields(revisedOntologyTerm);
		//check for duplicates
		checkOntologyTermExists(revisedOntologyTerm);
		checkOntologyTermDuplicates(revisedOntologyTerm);

		OntologyTerm originalOntologyTerm
			= getOriginalOntologyTerm(revisedOntologyTerm);
		ArrayList<MacawChangeEvent> changeEvents
			= OntologyTerm.detectFieldChanges(user, 
											  originalOntologyTerm, 
											  revisedOntologyTerm);
		//check that at least one change happened
		if (changeEvents.size() == 0) {
			return;
		}
		
		//Part II: Perform the update operation
		//this will effect the ontology terms managed by this manager
		//and references that may be found in variables
		int identifier = revisedOntologyTerm.getIdentifier();
		ontologyTermFromIdentifier.remove(identifier);
		ontologyTermFromIdentifier.put(identifier, revisedOntologyTerm);

		//now update references in all variables		
		//Part III: Record changes
		//NOTE: Not going to record provenance
		//registerChangeEvents(changeEvents);
	}
	
	public void deleteOntologyTerms(User user,
								    ArrayList<OntologyTerm> ontologyTermsToDelete) throws MacawException {
		
		//Part I: Validate parameters
		for (OntologyTerm currentOntologyTerm : ontologyTermsToDelete) {
			checkOntologyTermExists(currentOntologyTerm);
		}
		
		//Part II: Delete
		for (OntologyTerm currentOntologyTerm : ontologyTermsToDelete) {
			ontologyTermFromIdentifier.remove(currentOntologyTerm.getIdentifier());	
		}
		
		//Part III: Record changes
		/**
		ArrayList<MacawChangeEvent> changeEvents
			= ChangeEventGenerator.deleteOntologyTermsChange(user,
															 ontologyTermsToDelete);
		registerChangeEvents(changeEvents);
		*/
	}
	
	public OntologyTerm getOriginalOntologyTerm(OntologyTerm ontologyTerm) {	
		int identifier = ontologyTerm.getIdentifier();
		return ontologyTermFromIdentifier.get(identifier);
	}

	public int getOntologyTermIdentifier(OntologyTerm ontologyTerm) {
		ArrayList<OntologyTerm> ontologyTerms
			= new ArrayList<OntologyTerm>();
		ontologyTerms.addAll(ontologyTermFromIdentifier.values());
		
		String targetOntologyIdentifier 
			= deriveUniqueOntologyKey(ontologyTerm);
		for (OntologyTerm currentOntologyTerm : ontologyTerms) {
			String currentOntologyIdentifier
				= deriveUniqueOntologyKey(currentOntologyTerm);
			if (targetOntologyIdentifier.equals(currentOntologyIdentifier) == true) {
				return currentOntologyTerm.getIdentifier();
			}
		}
		
		return -1;
	}
	
	private String deriveUniqueOntologyKey(OntologyTerm ontologyTerm) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(ontologyTerm.getNameSpace());
		buffer.append(ontologyTerm.getTerm());
		return buffer.toString();
	}
	// ==========================================
	// Section Errors and Validation
	// ==========================================
	
	public void checkOntologyTermExists(OntologyTerm ontologyTerm) throws MacawException {	
		int identifier = ontologyTerm.getIdentifier();
		if (ontologyTermFromIdentifier.containsKey(identifier) == false) {
			String errorMessage
				= MacawMessages.getMessage("general.error.nonExistentItem",
										   ontologyTerm.getDisplayName());
			MacawException exception
				= new MacawException(MacawErrorType.NON_EXISTENT_ONTOLOGY_TERM,
									 errorMessage);
			throw exception;
		}
	}

	private void checkOntologyTermDuplicates(OntologyTerm targetOntologyTerm) throws MacawException {
		int targetTermID = targetOntologyTerm.getIdentifier();
		String targetTerm = targetOntologyTerm.getTerm();
		String targetNameSpace = targetOntologyTerm.getNameSpace();
		String targetOntologyName = targetOntologyTerm.getOntologyName();
		
		ArrayList<OntologyTerm> ontologyTerms = new ArrayList<OntologyTerm>();
		ontologyTerms.addAll(ontologyTermFromIdentifier.values());
		for (OntologyTerm currentOntologyTerm : ontologyTerms) {
			if (currentOntologyTerm.getTerm().equals(targetTerm) == true) {
				if ( (currentOntologyTerm.getNameSpace().equals(targetNameSpace) == true) ||
					 (currentOntologyTerm.getOntologyName().equals(targetOntologyName) == true) ) {
				
					//one last check make sure that the identifier values are different
					if (targetTermID != currentOntologyTerm.getIdentifier()) {
						String errorMessage
							= MacawMessages.getMessage("ontologyTerm.error.duplicateExists",
														targetOntologyTerm.getDisplayName());
						MacawException exception
							= new MacawException(MacawErrorType.DUPLICATE_ONTOLOGY_TERM,
												errorMessage);
						throw exception;
					}
				}
			}
		}
	}
	
	public void clear() {
		ontologyTermKey = 0;
		ontologyTermFromIdentifier.clear();
	}
	
	// ==========================================
	// Section Interfaces
	// ==========================================

	// ==========================================
	// Section Overload
	// ==========================================

}

