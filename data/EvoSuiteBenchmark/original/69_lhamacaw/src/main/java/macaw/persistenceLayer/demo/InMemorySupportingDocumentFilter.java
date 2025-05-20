package macaw.persistenceLayer.demo;

import macaw.businessLayer.*;
import macaw.system.*;
import macaw.util.SearchUtility;
import macaw.util.ValidationUtility;

import java.util.ArrayList;


/**
 * filter class that performs different searches to retrieve instances of 
 * {@link macaw.businessLayer.SupportingDocument}.  The Macaw data entry forms for 
 * raw and derived variables (see package <code> macaw.view </code>) allow 
 * users to add, edit, delete or select supporting documents. The select 
 * operation allows users to choose an existing supporting document.  
 * 
 * <p>
 * Macaw provides a search facility to help users find a particular supporting document 
 * to associate with a variable.  This class contains the code which supports
 * that feature.
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

public class InMemorySupportingDocumentFilter {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private ArrayList<SupportingDocument> supportingDocuments;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public InMemorySupportingDocumentFilter() {
		supportingDocuments = new ArrayList<SupportingDocument>();
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public void setSupportingDocuments(ArrayList<SupportingDocument> supportingDocuments) {
		this.supportingDocuments = supportingDocuments;
	}
	
	public ArrayList<SupportingDocument> filterSupportingDocuments(User user,
			   								   		  		  	   String documentTitleFilter,
			   								   		  		  	   String documentCodeFilter) throws MacawException {
		
		if ((ValidationUtility.isEmptyValue(documentTitleFilter) == true) &&
			(ValidationUtility.isEmptyValue(documentCodeFilter) == true) ) {
			String errorMessage
				= MacawMessages.getMessage("supportingDocumentFilter.error");
			MacawException exception
				= new MacawException(MacawErrorType.NO_SUPPORTING_DOCUMENT_FILTER,
									 errorMessage);
			throw exception;
		}
		
		ArrayList<SupportingDocument> documentTitleResults
			= filterByDocumentTitle(documentTitleFilter, supportingDocuments);
		ArrayList<SupportingDocument> documentCodeFilterResults
			= filterByDocumentCode(documentCodeFilter, documentTitleResults);
	
		ArrayList<SupportingDocument> finalResults
			= new ArrayList<SupportingDocument>();
		
		//put them in an alphabetical order
		for (SupportingDocument currentSupportingDocument : documentCodeFilterResults) {
			String currentDocumentDisplayName = currentSupportingDocument.getDisplayName();
			
			int insertionIndex = -1;
			int numberOfResults = finalResults.size();
			for (int i = 0; i < numberOfResults; i++) {
				String currentResultDisplayName = finalResults.get(i).getDisplayName();
				if (currentDocumentDisplayName.compareTo(currentResultDisplayName) > 0) {
					insertionIndex = i;
					break;
				}
			}
			
			SupportingDocument cloneSupportingDocument
				= (SupportingDocument) currentSupportingDocument.clone();
			
			if (insertionIndex == -1) {
				//item belongs at the end
				finalResults.add(cloneSupportingDocument);
			}
			else {
				finalResults.add(insertionIndex, cloneSupportingDocument);
			}
		}
		
		return finalResults;
	}
	
	private ArrayList<SupportingDocument> filterByDocumentTitle(String documentTitleFilter,
													  			ArrayList<SupportingDocument> results) {
		
		ArrayList<SupportingDocument> refinedResults = new ArrayList<SupportingDocument>();

		if (ValidationUtility.isEmptyValue(documentTitleFilter) == true) {
			//return everything so just return the original result list
			return results;
		}
		
		SearchUtility searchUtility = new SearchUtility();
		searchUtility.setDefaultSearchPattern(documentTitleFilter);

		for (SupportingDocument supportingDocument : results) {
			if (searchUtility.valueContainsPattern(supportingDocument.getTitle()) == true) {
				refinedResults.add(supportingDocument);
			}
		}

		return refinedResults;
	}

	private ArrayList<SupportingDocument> filterByDocumentCode(String documentCodeFilter, 
											 	 	 		   ArrayList<SupportingDocument> results) {


		if (ValidationUtility.isEmptyValue(documentCodeFilter) == true) {
			return results;
		}

		SearchUtility searchUtility = new SearchUtility();
		searchUtility.setDefaultSearchPattern(documentCodeFilter);

		ArrayList<SupportingDocument> refinedResults = new ArrayList<SupportingDocument>();
		for (SupportingDocument currentResult : results) {
			String currentDocumentCode = currentResult.getDocumentCode();
			if (searchUtility.valueContainsPattern(currentDocumentCode) == true) {
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

