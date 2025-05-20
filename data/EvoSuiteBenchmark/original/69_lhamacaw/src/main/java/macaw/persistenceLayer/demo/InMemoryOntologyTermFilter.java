package macaw.persistenceLayer.demo;

import macaw.businessLayer.*;
import macaw.system.*;
import macaw.util.SearchUtility;
import macaw.util.ValidationUtility;

import java.util.ArrayList;

/**
 * holds code used to support the search feature that lets end-users
 * select an ontology term from an existing list.  When the list 
 * pops up (see {@link macaw.presentationLayer.OntologyTermsPanel}) and users press the
 * "Select" button, a new dialog pops up showing all the ontology terms that
 * are known in the system.  This filter helps limit the list of all ontology
 * terms so that users can rapidly find the one they want to reference for a 
 * variable record.
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

public class InMemoryOntologyTermFilter {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private ArrayList<OntologyTerm> ontologyTerms;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public InMemoryOntologyTermFilter() {
		ontologyTerms = new ArrayList<OntologyTerm>();
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public void setOntologyTerms(ArrayList<OntologyTerm> ontologyTerms) {
		this.ontologyTerms = ontologyTerms;
	}
	
	/**
	 * finds ontology terms that match based on term and based on description.  
	 * 
	 * @param user
	 * @param ontologyTermFilter - the search phrase that is applied to the term field
	 * of an ontology term object.
	 * @param ontologyDescriptionFilter - the search phrase that is applied to the 
	 * description field of the ontology term object.
	 * @throws MacawException
	 */
	public ArrayList<OntologyTerm> filterOntologyTerms(User user,
													   String ontologyTermFilter,
													   String ontologyDescriptionFilter) throws MacawException {
	
		if ((ValidationUtility.isEmptyValue(ontologyTermFilter) == true) &&
			(ValidationUtility.isEmptyValue(ontologyDescriptionFilter) == true)) {

			String errorMessage
				= MacawMessages.getMessage("ontologyTermFilter.error");
			MacawException exception
				= new MacawException(MacawErrorType.NO_ONTOLOGY_TERM_FILTER,
									 errorMessage);
			throw exception;
		}
		
		ArrayList<OntologyTerm> termResults
			= filterByTerm(ontologyTermFilter, ontologyTerms);
		ArrayList<OntologyTerm> descriptionFilterResults
			= filterByDescription(ontologyDescriptionFilter, termResults);
	
		ArrayList<OntologyTerm> finalResults
			= new ArrayList<OntologyTerm>();
		
		//put them in an alphabetical order
		for (OntologyTerm currentOntologyTerm : descriptionFilterResults) {
			String currentDocumentDisplayName = currentOntologyTerm.getDisplayName();
			
			int insertionIndex = -1;
			int numberOfResults = finalResults.size();
			for (int i = 0; i < numberOfResults; i++) {
				String currentResultDisplayName = finalResults.get(i).getDisplayName();
				if (currentDocumentDisplayName.compareTo(currentResultDisplayName) > 0) {
					insertionIndex = i;
					break;
				}
			}
			
			OntologyTerm cloneOntologyTerm
				= (OntologyTerm) currentOntologyTerm.clone();
			
			if (insertionIndex == -1) {
				//item belongs at the end
				finalResults.add(cloneOntologyTerm);
			}
			else {
				finalResults.add(insertionIndex, cloneOntologyTerm);
			}
		}
		
		return finalResults;
	}
	
	private ArrayList<OntologyTerm> filterByTerm(String ontologyTermFilter,
												 ArrayList<OntologyTerm> results) {
		
		ArrayList<OntologyTerm> refinedResults = new ArrayList<OntologyTerm>();

		if (ValidationUtility.isEmptyValue(ontologyTermFilter) == true) {
			//return everything so just return the original result list
			return results;
		}
		
		SearchUtility searchUtility = new SearchUtility();
		searchUtility.setDefaultSearchPattern(ontologyTermFilter);
		
		for (OntologyTerm ontologyTerm : results) {
			String term = ontologyTerm.getTerm();			
			if (searchUtility.valueContainsPattern(term) == true) {
				refinedResults.add(ontologyTerm);
			}
		}

		return refinedResults;
	}

	private ArrayList<OntologyTerm> filterByDescription(String descriptionFilter, 
														ArrayList<OntologyTerm> results) {

		
		if (ValidationUtility.isEmptyValue(descriptionFilter) == true) {
			//return everything so just return the original result list
			return results;
		}
		
		SearchUtility searchUtility = new SearchUtility();
		searchUtility.setDefaultSearchPattern(descriptionFilter);

		ArrayList<OntologyTerm> refinedResults = new ArrayList<OntologyTerm>();
		for (OntologyTerm currentResult : results) {
			String currentDescription 
				= currentResult.getDescription();
			if (searchUtility.valueContainsPattern(currentDescription) == true) {
				refinedResults.add(currentResult);
			}
		}

		return refinedResults;
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

