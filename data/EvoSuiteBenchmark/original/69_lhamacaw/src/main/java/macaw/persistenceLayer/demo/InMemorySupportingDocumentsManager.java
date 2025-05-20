package macaw.persistenceLayer.demo;

import macaw.businessLayer.SupportingDocument;
import macaw.businessLayer.User;
import macaw.persistenceLayer.ChangeEventGenerator;
import macaw.system.*;
import macaw.util.SearchUtility;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * manages instances of {@link macaw.businessLayer.SupportingDocument} in memory rather than
 * managing them in a database.  This is a typical example of a manager class used
 * by delegation classes that implement the main APIs {@link macaw.businessLayer.MacawCurationAPI}
 * and {@link macaw.businessLayer.MacawRetrievalAPI}.
 * 
 * <p>
 * <code>InMemorySupportingDocumentsManager</code> supports the following operations:
 * <ul>
 * <li>get all supporting documents</li>
 * <li>add a supporting document</li>
 * <li>update a supporting document</li>
 * <li>delete a supporting document</li>
 * <li>check that a supporting document exists</li>
 * <li>check that a supporting document isn't a duplicate</li>
 * </ul>
 * 
 * <p>
 * Most of the editing operations have three parts:
 * <ul>
 * <li>validate parameters</li>
 * <li>perform the business operation</li>
 * <li>log any changes</li>
 * </ul>
 * <p>
 * The manager supports object persistence by adopting the following policies:
 * <ul>
 * <li>when a {@link macaw.businessLayer.SupportingDocument} is added, it is treated like 
 * the stored version of a record</li>
 * <li>when a{@link macaw.businessLayer.SupportingDocument} is retrieved, the manager
 * returns a clone copy.  The object returned is treated as the working copy.</li>
 * <li>when a {@link macaw.businessLayer.SupportingDocument} is updated, the manager uses
 * the value of an integer identifier field in the working copy to find the original in
 * a HashMap.  The manager replaces the original with the updated copy.</li>
 * </ul>
 * 
 * <p>
 * The manager generates unique identifiers for {@link macaw.businessLayer.SupportingDocument} in
 * much the same way as a relational database would use an autonumbered key.
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

public class InMemorySupportingDocumentsManager extends InMemoryCurationConceptManager {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	
	private int supportingDocumentKey;
	private HashMap<Integer, SupportingDocument> documentFromIdentifier;
	// ==========================================
	// Section Construction
	// ==========================================
	public InMemorySupportingDocumentsManager(InMemoryChangeEventManager changeEventManager) {
		super(changeEventManager);
		
		documentFromIdentifier
			= new HashMap<Integer, SupportingDocument>();
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public ArrayList<SupportingDocument> getAllSupportingDocuments(User user) throws MacawException {
		ArrayList<SupportingDocument> results 
			= new ArrayList<SupportingDocument>();
		
		//KLG - INEFFICIENT
		//This way of sorting the documents may or may not be efficient
		//But the InMemoryDB is not intended to hold a huge database in memory,
		//just one that nominally demonstrates functionality of the application
		ArrayList<SupportingDocument> supportingDocuments
			= new ArrayList<SupportingDocument>();
		supportingDocuments.addAll(documentFromIdentifier.values());
		for (SupportingDocument currentSupportingDocument : supportingDocuments) {
			String currentDisplayName 
				= currentSupportingDocument.getDisplayName();
			
			int numberOfResults = results.size();
			int insertionIndex = 0;
			for (int i = 0; i < numberOfResults; i++) {
				String currentResultDisplayName 
					= results.get(i).getDisplayName();
				if (currentResultDisplayName.compareTo(currentDisplayName) <= 0) {
					insertionIndex = i;
					break;
				}			
			}
			results.add(insertionIndex, (SupportingDocument) currentSupportingDocument.clone());
		}
		
		return results;
	}
	
	public void addSupportingDocument(User user,
									  SupportingDocument supportingDocument) throws MacawException {
		
		//Part I: Validate parameters
		//check basic field errors
		SupportingDocument.validateFields(supportingDocument);	
		//check for duplicates
		checkSupportingDocumentCodeDuplicates(supportingDocument);
		checkSupportingDocumentDuplicates(supportingDocument);

		//Part II: Add Document
		supportingDocumentKey++;
		supportingDocument.setIdentifier(supportingDocumentKey);
		documentFromIdentifier.put(supportingDocumentKey, 
								   supportingDocument);
		
		ArrayList<MacawChangeEvent> changeEvents
			= ChangeEventGenerator.addSupportingDocumentChange(user, 
															   supportingDocument);
		
		//Part III: Record Changes
		registerChangeEvents(changeEvents);	
	}
	
	public void updateSupportingDocument(User user,
									     SupportingDocument revisedSupportingDocument) throws MacawException {

		//Part I: Validate parameters

		//check that document exists
		checkSupportingDocumentExists(revisedSupportingDocument);
		//check basic field errors
		SupportingDocument.validateFields(revisedSupportingDocument);		
		//check for duplicates
		checkSupportingDocumentCodeDuplicates(revisedSupportingDocument);
		checkSupportingDocumentDuplicates(revisedSupportingDocument);
		
		//check that at least one change was made
		SupportingDocument originalSupportingDocument
			= getOriginalSupportingDocument(revisedSupportingDocument);
		ArrayList<MacawChangeEvent> changeEvents
			= SupportingDocument.detectFieldChanges(user,
													originalSupportingDocument, 
													revisedSupportingDocument);	
		if (changeEvents.size() == 0) {
			return;
		}
		
		//Part II: Perform Update

		//obtain the original object for the supporting document that
		//is requested
		
		//replace original with revised in the list that tracks all managed
		//supporting document instances
		int identifier = originalSupportingDocument.getIdentifier();
		documentFromIdentifier.remove(originalSupportingDocument);
		documentFromIdentifier.put(identifier, revisedSupportingDocument);
		
		//Part III: Record changes
		registerChangeEvents(changeEvents);
	}
	
	public void deleteSupportingDocuments(User user,
										  ArrayList<SupportingDocument> supportingDocumentsToDelete) throws MacawException {


		//Part I: Validate parameters
		//verify that supporting documents are associated with variable
		for (SupportingDocument supportingDocument : supportingDocumentsToDelete) {
			checkSupportingDocumentExists(supportingDocument);
		}
		
		//Part II: Perform the deletion operation
		for (SupportingDocument supportingDocument : supportingDocumentsToDelete) {
			SupportingDocument originalSupportingDocument
				= getOriginalSupportingDocument(supportingDocument);
			documentFromIdentifier.remove(originalSupportingDocument.getIdentifier());
		}		
		
		//Part III: Record changes
		ArrayList<MacawChangeEvent> changeEvents
			= ChangeEventGenerator.deleteSupportingDocumentsChange(user,
																   supportingDocumentsToDelete);
		registerChangeEvents(changeEvents);
	}
		
	public SupportingDocument getOriginalSupportingDocument(SupportingDocument revisedSupportingDocument) {
		int identifier = revisedSupportingDocument.getIdentifier();
		SupportingDocument originalSupportingDocument
			= documentFromIdentifier.get(identifier);
		return originalSupportingDocument;
	}
	
	public int getSupportingDocumentIdentifier(SupportingDocument targetSupportingDocument) {	
		SearchUtility searchUtility 
			= new SearchUtility(targetSupportingDocument.getDisplayName());		
		
		ArrayList<SupportingDocument> supportingDocuments 
			= new ArrayList<SupportingDocument>();
		supportingDocuments.addAll(documentFromIdentifier.values());
		
		for (SupportingDocument currentSupportingDocument : supportingDocuments) {
			String currentDisplayName = currentSupportingDocument.getDisplayName();
			if (searchUtility.valueExactlyMatches(currentDisplayName) == true) {
				return currentSupportingDocument.getIdentifier();
			}
		}
		
		return -1;
	}

	public void clear() {
		supportingDocumentKey = 0;
		documentFromIdentifier.clear();
	}
	
	// ==========================================
	// Section Errors and Validation
	// ==========================================

	public void checkSupportingDocumentExists(SupportingDocument candidateSupportingDocument) throws MacawException {
		int identifier = candidateSupportingDocument.getIdentifier();
		if (documentFromIdentifier.containsKey(identifier) == false) {
			String errorMessage
				= MacawMessages.getMessage("general.error.nonExistentItem",
											candidateSupportingDocument.getDisplayName());
			MacawException exception
				= new MacawException(MacawErrorType.NON_EXISTENT_SUPPORTING_DOCUMENT,
									errorMessage);
			throw exception;
		}
	}
		
	private void checkSupportingDocumentCodeDuplicates(SupportingDocument candidateSupportingDocument) throws MacawException {
		String candidateDocumentCode
			= candidateSupportingDocument.getDocumentCode();
	
		ArrayList<SupportingDocument> supportingDocuments
			= new ArrayList<SupportingDocument>();
		supportingDocuments.addAll(documentFromIdentifier.values());

		for (SupportingDocument currentSupportingDocument : supportingDocuments) {
			String currentDocumentCode = currentSupportingDocument.getDocumentCode();
			if (candidateDocumentCode.equals(currentDocumentCode) == true) {
				int candidateIdentifier = candidateSupportingDocument.getIdentifier();
				int currentIdentifier = currentSupportingDocument.getIdentifier();
				if (candidateIdentifier != currentIdentifier) {
					//items have same display name but different IDs. Therefore
					//the candidate is a duplicate
					String errorMessage
						= MacawMessages.getMessage("supportingDocument.error.duplicateDocumentCodeExists",
													candidateSupportingDocument.getDocumentCode());
					MacawException exception 
						= new MacawException(MacawErrorType.DUPLICATE_SUPPORTING_DOCUMENT_CODE,
											 errorMessage);
					throw exception;
				}
			}
		}
	}
	
	private void checkSupportingDocumentDuplicates(SupportingDocument candidateSupportingDocument) throws MacawException {		
		if (documentInDatabase(candidateSupportingDocument) == true) {
			//items have same display name but different IDs. Therefore
			//the candidate is a duplicate
			String errorMessage
				= MacawMessages.getMessage("supportingDocument.error.duplicateExists",
											candidateSupportingDocument.getDisplayName());
			MacawException exception
				= new MacawException(MacawErrorType.DUPLICATE_SUPPORTING_DOCUMENT,
									 errorMessage);
			throw exception;
		}
	}
	
	private boolean documentInDatabase(SupportingDocument candidateSupportingDocument) {
		SearchUtility searchUtility 
			= new SearchUtility(candidateSupportingDocument.getDisplayName());		
		
		ArrayList<SupportingDocument> supportingDocuments
			= new ArrayList<SupportingDocument>();
		supportingDocuments.addAll(documentFromIdentifier.values());

		for (SupportingDocument currentSupportingDocument : supportingDocuments) {
			String currentDisplayName 
				= currentSupportingDocument.getDisplayName();
			if (searchUtility.valueExactlyMatches(currentDisplayName) == true) {
				int candidateIdentifier = candidateSupportingDocument.getIdentifier();
				int currentIdentifier = currentSupportingDocument.getIdentifier();
				if (candidateIdentifier != currentIdentifier) {
					return true;
				}
			}
		}		
		return false;
	}

	// ==========================================
	// Section Interfaces
	// ==========================================

	// ==========================================
	// Section Overload
	// ==========================================

}

