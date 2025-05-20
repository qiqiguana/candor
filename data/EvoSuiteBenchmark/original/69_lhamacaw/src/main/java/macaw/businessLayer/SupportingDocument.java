package macaw.businessLayer;

import macaw.system.*;
import macaw.util.Displayable;
import macaw.util.ValidationUtility;

import java.util.ArrayList;

/**
 * describes a document used to provide background information about
 * a derived variable.
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

public class SupportingDocument implements Displayable, Cloneable {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private int identifier;
	private String title;
	private String documentCode;
	private String description;
	private String fileName;
	private String filePath;
	private boolean isNewRecord;
	
	// ==========================================
	// Section Construction
	// ==========================================
	
	public SupportingDocument() {
		identifier = 0;
		title = "";
		documentCode= "";
		description = "";
		fileName = "";
		filePath = "";
		isNewRecord = false;
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	
	/**
	 * the primary key value of the instance stored in the relational database.
	 */
	public int getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}
	
	/**
	 * title of the document
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * a code used to help uniquely identify a document for the end-user.  
	 * Two documents could have the same title but be published in different 
	 * years or by different authors.  The purpose of the code is simply to 
	 * uniquely identify records in a list of displayed documents.
	 * @return the documentCode
	 */
	public String getDocumentCode() {
		return documentCode;
	}

	/**
	 * @param documentCode the documentCode to set
	 */
	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}

	/**
	 * description of the document
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * name of the file
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * the logical location of the file
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public boolean isNewRecord() {
		return isNewRecord;
	}
	
	public void setIsNewRecord(boolean isNewRecord) {
		this.isNewRecord = isNewRecord;
	}
	
	// ==========================================
	// Section Errors and Validation
	// ==========================================
	public static void validateFields(SupportingDocument supportingDocument) throws MacawException {
		ArrayList<String> errorMessages = new ArrayList<String>();

		//check that the title has been filled in
		String title = supportingDocument.getTitle();
		if (ValidationUtility.isBlank(title) == true) {	
			String titleNameText = MacawMessages.getMessage("supportingDocument.title.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
										   titleNameText);
			errorMessages.add(errorMessage);
		}

		/*
		if (ValidationUtility.promotesSecurityRisk(title) == true) {	
			String titleNameText 
				= MacawMessages.getMessage("supportingDocument.title.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.promotesSecurityRisk",
											titleNameText);
			errorMessages.add(errorMessage);
		}
		*/

		String documentCode = supportingDocument.getDocumentCode();
		if (ValidationUtility.isBlank(documentCode) == true) {	
			String documentCodeNameText = MacawMessages.getMessage("supportingDocument.documentCode.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
											documentCodeNameText);
			errorMessages.add(errorMessage);
		}
		
		/*
		String filePath = supportingDocument.getFilePath();
		if (ValidationUtility.isBlank(filePath) == true) {	
			String filePathNameText = MacawMessages.getMessage("supportingDocument.filePath.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
											filePathNameText);
			errorMessages.add(errorMessage);
		}
		*/

		String fileName = supportingDocument.getFileName();
		if (ValidationUtility.isBlank(fileName) == true) {	
			String fileNameText = MacawMessages.getMessage("supportingDocument.fileName.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
											fileNameText);
			errorMessages.add(errorMessage);
		}
	
		if (errorMessages.size() > 0) {
			MacawException exception
				= new MacawException();
			for (String errorMessage : errorMessages) {
				exception.addErrorMessage(MacawErrorType.INVALID_SUPPORTING_DOCUMENT, 
										  errorMessage);				
			}
			throw exception;
		}	
	}
	
	// ==========================================
	// Section Interfaces
	// ==========================================
	
	//Interface: Displayable
	public String getDisplayName() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(title);
		buffer.append("-");
		buffer.append(documentCode);
		return buffer.toString();
	}

	public String getDisplayItemIdentifier() {
		return String.valueOf(identifier);
	}

	//Interface: Cloneable
	public Object clone() {
		SupportingDocument cloneDocument = new SupportingDocument();
		cloneDocument.setIdentifier(identifier);
		cloneDocument.setTitle(title);
		cloneDocument.setDocumentCode(documentCode);
		cloneDocument.setDescription(description);
		cloneDocument.setFileName(fileName);
		cloneDocument.setFilePath(filePath);
		return cloneDocument;
	}
	
	static public ArrayList<MacawChangeEvent> detectFieldChanges(User user,
			 													 SupportingDocument originalDocument,
			 													 SupportingDocument revisedDocument) {

		String userID = user.getUserID();
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		
		String oldTitle = originalDocument.getTitle();
		String newTitle = revisedDocument.getTitle();
		if (oldTitle.equals(newTitle) == false) {
			String changedTitleMessage
				= MacawMessages.getMessage("supportingDocument.title.saveChanges",
										   oldTitle,
										   newTitle);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.SUPPORTING_DOCUMENT,
									   changedTitleMessage, 
									   userID);
			changeEvent.setChangedObjectIdentifier(originalDocument.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		String oldDocumentCode = originalDocument.getDocumentCode();
		String newDocumentCode = revisedDocument.getDocumentCode();
		if (oldDocumentCode.equals(newDocumentCode) == false) {
			String changedTitleMessage
				= MacawMessages.getMessage("supportingDocument.documentCode.saveChanges",
										   oldDocumentCode,
										   newDocumentCode);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.SUPPORTING_DOCUMENT,
									   changedTitleMessage, 
									   userID);
			changeEvent.setChangedObjectIdentifier(originalDocument.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		
		String oldDescription = originalDocument.getDescription();
		String newDescription = revisedDocument.getDescription();
		if (oldDescription.equals(newDescription) == false) {
			String changedDescriptionMessage
				= MacawMessages.getMessage("supportingDocument.description.saveChanges",
									   	   oldDescription,
									   	   newDescription);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.SUPPORTING_DOCUMENT,
									   changedDescriptionMessage, 
									   userID);
			changeEvent.setChangedObjectIdentifier(originalDocument.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		String oldFileName = originalDocument.getFileName();
		String newFileName = revisedDocument.getFileName();
		if (oldFileName.equals(newFileName) == false) {
			String changedFileNameMessage
				= MacawMessages.getMessage("supportingDocument.fileName.saveChanges",
									   	   oldFileName,
									   	   newFileName);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.SUPPORTING_DOCUMENT,
									   changedFileNameMessage, 
									   userID);
			changeEvent.setChangedObjectIdentifier(originalDocument.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		String oldFilePath = originalDocument.getFilePath();
		String newFilePath = revisedDocument.getFilePath();
		if (oldFilePath.equals(newFilePath) == false) {
			String changedFileNameMessage
				= MacawMessages.getMessage("supportingDocument.filePath.saveChanges",
									   	   oldFilePath,
									   	   newFilePath);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.SUPPORTING_DOCUMENT,
									   changedFileNameMessage, 
									   userID);
			changeEvent.setChangedObjectIdentifier(originalDocument.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		
		return changeEvents;
	}
	// ==========================================
	// Section Overload
	// ==========================================

}

