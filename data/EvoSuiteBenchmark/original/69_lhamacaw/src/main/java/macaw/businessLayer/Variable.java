package macaw.businessLayer;

import macaw.system.*;
import macaw.util.Displayable;
import macaw.util.ValidationUtility;

import java.util.ArrayList;

/**
 * A concept related to the NSHD study.  
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

abstract public class Variable implements Cloneable, Displayable {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private int identifier;
	private String category;
	private String name;
	private String label;
	private boolean isCleaned;
	private String year;
	private String cleaningStatus;
	private String cleaningDescription;
	private ArrayList<ValueLabel> valueLabels;
	private String availability;
	private String alias;
	private String filePath;
	private boolean isCoded;
	protected String form;
	protected String questionNumber;
	
	protected String codeBookNumber;
	protected String columnStart;
	protected String columnEnd;
	protected String notes;
		
	private ArrayList<SupportingDocument> supportingDocuments;
	private ArrayList<OntologyTerm> ontologyTerms;
	private boolean isNewRecord;
	
	private Variable alternativeVariable;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public Variable() {	
		String unknownMessage
			= MacawMessages.getMessage("general.fieldValue.unknown");

		identifier = 0;
		category = unknownMessage;
		name = "";
		year = "";
		label = "";
		isCleaned = false;
		cleaningStatus = unknownMessage;
		cleaningDescription = "";
		valueLabels = new ArrayList<ValueLabel>();
		availability = unknownMessage;
		alias = "";
		filePath = "";	
		supportingDocuments = new ArrayList<SupportingDocument>();
		ontologyTerms = new ArrayList<OntologyTerm>();
		isCoded = false;
		isNewRecord = false;
		
		codeBookNumber = "";
		columnStart = "";
		columnEnd = "";
		
		form = "";
		questionNumber = "";
		notes = "";		
		
		alternativeVariable = null;
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	
	/**
	 * produces a variable summary suitable for showing in a collection
	 * of search results.
	 */
	public VariableSummary createVariableSummary() {
		VariableSummary variableSummary = new VariableSummary();
		variableSummary.setIdentifier(identifier);
		variableSummary.setName(name);
		variableSummary.setLabel(label);
		variableSummary.setYear(year);
		return variableSummary;
	}
	
	/**
	 * an integer which uniquely identifies an instance in the database.  
	 * All primary key values in the database use a single auto-incremented 
	 * auto-generated value.
	 * @return the identifier
	 */
	public int getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	/**
	 * variable name, as specified using the NSHD variable naming convention
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * a one-line description of the variable
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * sweep in which the variable was collected
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	/**
	 * flag for whether the variable has been cleaned in some way
	 * @return the isCleaned
	 */
	public boolean isCleaned() {
		return isCleaned;
	}

	/**
	 * @param isCleaned the isCleaned to set
	 */
	public void setCleaned(boolean isCleaned) {
		this.isCleaned = isCleaned;
	}

	/**
	 * @return the cleaningStatus
	 */
	public String getCleaningStatus() {
		return cleaningStatus;
	}

	/**
	 * @param cleaningStatus the cleaningStatus to set
	 */
	public void setCleaningStatus(String cleaningStatus) {
		this.cleaningStatus = cleaningStatus;
	}

	/**
	 * free text description of how the variable data were cleaned.
	 * @return the cleaningDescription
	 */
	public String getCleaningDescription() {
		return cleaningDescription;
	}

	/**
	 * @param cleaningDescription the cleaningDescription to set
	 */
	public void setCleaningDescription(String cleaningDescription) {
		this.cleaningDescription = cleaningDescription;
	}
	
	public ArrayList<ValueLabel> getValueLabels() {
		return valueLabels;
	}
	
	public void setValueLabels(ArrayList<ValueLabel> valueLabels) {
		this.valueLabels = valueLabels;
	}
	
	public void addValueLabel(ValueLabel valueLabel) {
		valueLabels.add(valueLabel);
	}
	
	public void removeValueLabel(ValueLabel valueLabel) {
		valueLabels.remove(valueLabel);
	}
	
	public ArrayList<SupportingDocument> getSupportingDocuments() {
		return supportingDocuments;
	}

	public boolean containsSupportingDocument(SupportingDocument targetSupportingDocument) {
		int targetIdentifier = targetSupportingDocument.getIdentifier();
		for (SupportingDocument currentSupportingDocument : supportingDocuments) {
			int currentIdentifier
				= currentSupportingDocument.getIdentifier();
			if (targetIdentifier == currentIdentifier) {
				return true;
			}
		}
		
		return false;
	}

	public void setSupportingDocuments(ArrayList<SupportingDocument> supportingDocuments) {
		this.supportingDocuments = supportingDocuments;
	}
	public void addSupportingDocuments(ArrayList<SupportingDocument> supportingDocumentsToAdd) {
		supportingDocuments.addAll(supportingDocumentsToAdd);
	}
	
	public void addSupportingDocument(SupportingDocument supportingDocument) {
		supportingDocuments.add(supportingDocument);
	}
	
	public void removeSupportingDocument(SupportingDocument supportingDocument) {
		supportingDocuments.remove(supportingDocument);		
	}
	
	public void removeSupportingDocuments(ArrayList<SupportingDocument> supportingDocumentsToDelete) {
		for (SupportingDocument targetVariable : supportingDocumentsToDelete) {
			supportingDocuments.remove(targetVariable);
		}
	}

	public ArrayList<OntologyTerm> getOntologyTerms() {
		return ontologyTerms;
	}

	public boolean containsOntologyTerm(OntologyTerm targetOntologyTerm) {
		int targetIdentifier = targetOntologyTerm.getIdentifier();
		for (OntologyTerm currentOntologyTerm : ontologyTerms) {
			int currentIdentifier
				= currentOntologyTerm.getIdentifier();
			if (targetIdentifier == currentIdentifier) {
				return true;
			}
		}
		
		return false;
	}
	
	public void setOntologyTerms(ArrayList<OntologyTerm> ontologyTerms) {
		this.ontologyTerms = ontologyTerms;
	}
	public void addOntologyTerms(ArrayList<OntologyTerm> ontologyTermsToAdd) {
		ontologyTerms.addAll(ontologyTermsToAdd);
	}
	
	public void addOntologyTerm(OntologyTerm ontologyTerm) {
		ontologyTerms.add(ontologyTerm);
	}
	
	public void removeOntologyTerm(OntologyTerm copyOfOntologyTermToDelete) {
		OntologyTerm itemToDelete = findOntologyTerm(copyOfOntologyTermToDelete);
		if (itemToDelete != null) {
			ontologyTerms.remove(itemToDelete);			
		}
	}
	
	public void removeOntologyTerms(ArrayList<OntologyTerm> ontologyTermsToDelete) {
		for (OntologyTerm targetOntologyTerm : ontologyTermsToDelete) {
			removeOntologyTerm(targetOntologyTerm);
		}
	}

	public void updateOntologyTerm(OntologyTerm revisedOntologyTerm) {
		OntologyTerm itemToUpdate = findOntologyTerm(revisedOntologyTerm);
		if (itemToUpdate != null) {
			int numberOfTerms = ontologyTerms.size();			
			int index = ontologyTerms.indexOf(itemToUpdate);			
			ontologyTerms.remove(itemToUpdate);
			if (index == numberOfTerms -1) {
				//item to update was at the end
				ontologyTerms.add(revisedOntologyTerm);
			}
			else {
				ontologyTerms.add(index, revisedOntologyTerm);
			}
		}	
	}

	private OntologyTerm findOntologyTerm(OntologyTerm copyofOntologyTerm) {
		int targetIdentifier = copyofOntologyTerm.getIdentifier();
		for (OntologyTerm currentOntologyTerm : ontologyTerms) {
			if (currentOntologyTerm.getIdentifier() == targetIdentifier) {
				//found the item to delete
				return currentOntologyTerm;
			}
		}		
		return null;
	}
	
	/**
	 * @return the availability
	 */
	public String getAvailability() {
		return availability;
	}

	/**
	 * @param availability the availability to set
	 */
	public void setAvailability(String availability) {
		this.availability = availability;
	}

	/**
	 * @return the codeBookNumber
	 */
	public String getCodeBookNumber() {
		return codeBookNumber;
	}

	/**
	 * reference to the physical code book entry
	 * @param codeBookNumber the codeBookNumber to set
	 */
	public void setCodeBookNumber(String codeBookNumber) {
		this.codeBookNumber = codeBookNumber;
	}
	
	/**
	 * starting column in library file for this variable
	 * @return the columnStart
	 */
	public String getColumnStart() {
		return columnStart;
	}

	/** 
	 * @param columnStart the columnStart to set
	 */
	public void setColumnStart(String columnStart) {
		this.columnStart = columnStart;
	}

	/**
	 * ending column in library file for this variable
	 * @return the columnEnd
	 */
	public String getColumnEnd() {
		return columnEnd;
	}

	/**
	 * @param columnEnd the columnEnd to set
	 */
	public void setColumnEnd(String columnEnd) {
		this.columnEnd = columnEnd;
	}

	
	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	/**
	 * @return the file Name
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the dataLibraryFileName to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * flag for whether the variable has been coded electronically (or not)
	 * @return the isCoded
	 */
	public boolean isCoded() {
		return isCoded;
	}

	/**
	 * @param isCoded the isCoded to set
	 */
	public void setCoded(boolean isCoded) {
		this.isCoded = isCoded;
	}
	
	public boolean isNewRecord() {
		return isNewRecord;
	}

	public void setIsNewRecord(boolean isNewRecord) {
		this.isNewRecord = isNewRecord;
	}
	
	/**
	 * questionnaire form containing the question whose response is stored in the variable
	 * @return the form
	 */
	public String getForm() {
		return form;
	}

	/**
	 * @param form the form to set
	 */
	public void setForm(String form) {
		this.form = form;
	}

	/**
	 * question number from the questionnaire for this variable
	 * @return the questionNumber
	 */
	public String getQuestionNumber() {
		return questionNumber;
	}

	/**
	 * @param questionNumber the questionNumber to set
	 */
	public void setQuestionNumber(String questionNumber) {
		this.questionNumber = questionNumber;
	}

	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Variable getAlternativeVariable() {
		return alternativeVariable;
	}
	
	public void setAlternativeVariable(Variable alterativeVariable) {
		this.alternativeVariable = alterativeVariable;
	}
	
	// ==========================================
	// Section Errors and Validation
	// ==========================================
	static protected ArrayList<String> validateFields(Variable variable) {
		ArrayList<String> errorMessages = new ArrayList<String>();
		
		//specify a name
		if (ValidationUtility.isBlank(variable.getName()) == true) {
			String nameLabelText
				= MacawMessages.getMessage("variable.name.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
										   nameLabelText);
			errorMessages.add(errorMessage);
		}
		
		//specify a label
		/**
		if (ValidationUtility.isBlank(variable.getLabel()) == true) {
			String labelLabelText
				= MacawMessages.getMessage("variable.label.label"); 
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
									   		labelLabelText);
			errorMessages.add(errorMessage);
		}
		*/
		
		//specify a category
		
		/*
		if (ValidationUtility.isBlank(variable.getCategory()) == true) {
			String categoryLabelText
				= MacawMessages.getMessage("variable.category.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
											categoryLabelText);	
			errorMessages.add(errorMessage);
		}
		
		if (ValidationUtility.isUnknown(variable.getCategory()) == true) {
			String categoryLabelText
				= MacawMessages.getMessage("variable.category.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.unselectedItem",
											categoryLabelText);	
			errorMessages.add(errorMessage);
		}
		*/
		
		//specify an availability
		if (ValidationUtility.isBlank(variable.getAvailability()) == true) {
			String availabilityLabelText
				= MacawMessages.getMessage("variable.availability.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
											availabilityLabelText);	
			errorMessages.add(errorMessage);
		}

		/**
		if (ValidationUtility.isUnknown(variable.getAvailability()) == true) {
			String availabilityLabelText
				= MacawMessages.getMessage("variable.availability.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.unselectedItem",
											availabilityLabelText);	
			errorMessages.add(errorMessage);
		}
		*/

		//specify cleaning status
		if (variable.isCleaned() == true) {
			if (ValidationUtility.isBlank(variable.getCleaningStatus()) == true) {
				String cleaningStatusLabelText
					= MacawMessages.getMessage("variable.cleaningStatus.label");
				String errorMessage
					= MacawMessages.getMessage("general.error.blankField",
												cleaningStatusLabelText);	
				errorMessages.add(errorMessage);
			}
			
			/**
			if (ValidationUtility.isUnknown(variable.getCleaningStatus()) == true) {
				String cleaningStatusLabelText
					= MacawMessages.getMessage("variable.cleaningStatus.label");
				String errorMessage
					= MacawMessages.getMessage("general.error.unselectedItem",
												cleaningStatusLabelText);	
				errorMessages.add(errorMessage);
			}	
			*/		
		}
		
		//specify a year
		/**
		if (ValidationUtility.isBlank(variable.getYear()) == true) {
			String yearLabelText
				= MacawMessages.getMessage("variable.year.label"); 
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
									   		yearLabelText);
			errorMessages.add(errorMessage);
		}
		else {
			//make sure year is numeric
			String yearValue = variable.getYear();
		
			try {
				Date currentDate = new Date();
				GregorianCalendar gregorianCalendar = new GregorianCalendar();
				gregorianCalendar.setTime(currentDate);
				int currentYearNumber = gregorianCalendar.get(Calendar.YEAR);
			
				int calendarYear = new Integer(yearValue).intValue();
				if (calendarYear < 1946) {
					String errorMessage
						= MacawMessages.getMessage("variable.year.error.tooOld",
												   yearValue);
					errorMessages.add(errorMessage);
				}
				else if (calendarYear > currentYearNumber) {
					String errorMessage
						= MacawMessages.getMessage("variable.year.error.tooNew",
												   yearValue);
					errorMessages.add(errorMessage);
				}
			}
			catch(NumberFormatException exception) {
				String illegalNumberFormatException
					= MacawMessages.getMessage("variable.year.error.illegalYear",
											   yearValue);
				errorMessages.add(illegalNumberFormatException); 
			}
		}
		*/
		
		/**
		String columnStart = variable.getColumnStart();
		if (ValidationUtility.isBlank(columnStart) == false) {
			//check that column start is an integer
			try {
				//ensure that values are integers
				Integer.valueOf(columnStart);
			}
			catch(Exception exception) {
				String columnStartLabelText
					= MacawMessages.getMessage("variable.columnStart.label");
				String errorMessage
					= MacawMessages.getMessage("general.error.illegalNumber",
										       columnStart,
										       columnStartLabelText);
				errorMessages.add(errorMessage);
			}			
		}

		String columnEnd = variable.getColumnEnd();
		if (ValidationUtility.isBlank(columnEnd) == false) {
			//check that column end is an integer
			try {
				//ensure that values are integers
				Integer.valueOf(columnEnd);
			}
			catch(Exception exception) {
				String columnEndLabelText
					= MacawMessages.getMessage("variable.columnEnd.label");
				String errorMessage
					= MacawMessages.getMessage("general.error.illegalNumber",
												columnEnd,
												columnEndLabelText);
				errorMessages.add(errorMessage);
			}			
		}
		*/
		
		//specify an alias
		/**
		if (ValidationUtility.isBlank(variable.getAlias()) == true) {
			String aliasLabelText
				= MacawMessages.getMessage("variable.alias.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
											aliasLabelText);
			errorMessages.add(errorMessage);			
		}
		*/
		
		//specify a file name
		/**
		if (ValidationUtility.isBlank(variable.getFilePath()) == true) {
			String filePathLabelText
				= MacawMessages.getMessage("variable.filePath.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
											filePathLabelText);
			errorMessages.add(errorMessage);			
		}
		*/
				
		return errorMessages;
	}

	static public ArrayList<MacawChangeEvent> detectFieldChanges(User user,
			 													 Variable originalVariable,
			 													 Variable revisedVariable) {
		String userID = user.getUserID();
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		String ownerVariableName = originalVariable.getDisplayName();
		
		//detect changes in name
		String oldName = originalVariable.getName();
		String newName = revisedVariable.getName();
		if (oldName.equals(newName) == false) {
			oldName
				= ValidationUtility.convertEmptyValueToBlank(oldName);
			newName
				= ValidationUtility.convertEmptyValueToBlank(newName);

			String nameLabelText
				= MacawMessages.getMessage("variable.name.label");
			String fieldChangedMessage
				= MacawMessages.getMessage("variable.saveChanges.valueChanged",
										   ownerVariableName,
										   nameLabelText,
										   oldName,
										   newName);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE,
									   fieldChangedMessage, 
									   userID);
			changeEvent.setVariableOwnerID(originalVariable.getIdentifier());
			changeEvent.setChangedObjectIdentifier(originalVariable.getIdentifier());
			changeEvents.add(changeEvent);
		}

		//=Changed variable {0}, field {1}, from {2} to {3}
		
		//detect changes in label
		String oldLabel = originalVariable.getLabel();
		String newLabel = revisedVariable.getLabel();
		if (oldLabel.equals(newLabel) == false) {
			oldLabel
				= ValidationUtility.convertEmptyValueToBlank(oldName);
			newLabel
				= ValidationUtility.convertEmptyValueToBlank(newLabel);

			String labelLabelText
				= MacawMessages.getMessage("variable.label.label");
			String fieldChangedMessage
				= MacawMessages.getMessage("variable.saveChanges.valueChanged",
										   ownerVariableName,
										   labelLabelText,
										   oldLabel,
										   newLabel);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE,
									   fieldChangedMessage, 
									   userID);
			changeEvent.setVariableOwnerID(originalVariable.getIdentifier());
			changeEvent.setChangedObjectIdentifier(originalVariable.getIdentifier());
			changeEvents.add(changeEvent);			
		}
		
		//detect changes in year
		String oldYear = originalVariable.getYear();
		String newYear = revisedVariable.getYear();
		if (oldYear.equals(newYear) == false) {
			oldYear
				= ValidationUtility.convertEmptyValueToBlank(oldYear);
			newYear
				= ValidationUtility.convertEmptyValueToBlank(newYear);
			String yearLabelText
				= MacawMessages.getMessage("variable.year.label"); 
			String fieldChangedMessage
				= MacawMessages.getMessage("variable.saveChanges.valueChanged",
										   ownerVariableName,
										   yearLabelText,
										   oldYear,
										   newYear);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE,
									   fieldChangedMessage, 
									   userID);
			changeEvent.setVariableOwnerID(originalVariable.getIdentifier());
			changeEvent.setChangedObjectIdentifier(originalVariable.getIdentifier());
			changeEvents.add(changeEvent);						
		}

		//detect changes in category
		String originalCategory = originalVariable.getCategory();
		String revisedCategory = revisedVariable.getCategory();
		if (originalCategory.equals(revisedCategory) == false) {
			originalCategory
				= ValidationUtility.convertEmptyValueToBlank(originalCategory);
			revisedCategory
				= ValidationUtility.convertEmptyValueToBlank(revisedCategory);
			
			String categoryLabelText
				= MacawMessages.getMessage("variable.category.label"); 
			String fieldChangedMessage
				= MacawMessages.getMessage("variable.saveChanges.valueChanged",
										   ownerVariableName,
									   	   categoryLabelText,
									   	   originalCategory,
									   	   revisedCategory);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE,
									   fieldChangedMessage, 
									   userID);
			
			changeEvent.setVariableOwnerID(originalVariable.getIdentifier());
			changeEvent.setChangedObjectIdentifier(originalVariable.getIdentifier());
			changeEvents.add(changeEvent);									
		}
						
		boolean originalIsCleaned = originalVariable.isCleaned();
		boolean revisedIsCleaned = revisedVariable.isCleaned();
		
		if ( (originalIsCleaned != revisedIsCleaned) && 
			 (revisedIsCleaned == false)) {		
				String changedToNotCleaned
					= MacawMessages.getMessage("variable.isCleaned.saveChanges.changedToNotClean",
											   ownerVariableName);
				MacawChangeEvent changeEvent
					= new MacawChangeEvent(ChangeEventType.VARIABLE,
										   changedToNotCleaned, 
										   userID);
				changeEvent.setVariableOwnerID(originalVariable.getIdentifier());
				changeEvent.setChangedObjectIdentifier(originalVariable.getIdentifier());
				changeEvents.add(changeEvent);			
		}
		else {
							
			String oldCleaningChoice = originalVariable.getCleaningStatus();
			String revisedCleaningChoice = revisedVariable.getCleaningStatus();

				
			if (oldCleaningChoice.equals(revisedCleaningChoice) == false) {
				oldCleaningChoice
					= ValidationUtility.convertEmptyValueToBlank(oldCleaningChoice);
				revisedCleaningChoice
					= ValidationUtility.convertEmptyValueToBlank(revisedCleaningChoice);
					
				String cleaningStatusText
					= MacawMessages.getMessage("variable.cleaningStatus.label"); 
				String fieldChangedMessage
					= MacawMessages.getMessage("variable.saveChanges.valueChanged",
											   ownerVariableName,
											   cleaningStatusText,
											   oldCleaningChoice,
											   revisedCleaningChoice);
				MacawChangeEvent changeEvent
					= new MacawChangeEvent(ChangeEventType.VARIABLE,
										   fieldChangedMessage, 
										   userID);
				changeEvent.setVariableOwnerID(originalVariable.getIdentifier());
				changeEvent.setChangedObjectIdentifier(originalVariable.getIdentifier());
				changeEvents.add(changeEvent);													
			}
			
			String oldCleaningDescription = originalVariable.getCleaningDescription();
			String revisedCleaningDescription = originalVariable.getCleaningDescription();

			if (oldCleaningDescription.equals(revisedCleaningDescription) == false) {
				oldCleaningDescription
					= ValidationUtility.convertEmptyValueToBlank(oldCleaningDescription);
				revisedCleaningDescription
					= ValidationUtility.convertEmptyValueToBlank(revisedCleaningDescription);

				String cleaningDescriptionText
					= MacawMessages.getMessage("variable.cleaningDescription.label"); 
				String fieldChangedMessage
					= MacawMessages.getMessage("variable.saveChanges.valueChanged",
										   	   ownerVariableName,
										   	   cleaningDescriptionText,
										   	   oldCleaningDescription,
										   	   revisedCleaningDescription);

				MacawChangeEvent changeEvent
					= new MacawChangeEvent(ChangeEventType.VARIABLE,
										   fieldChangedMessage, 
										   userID);
				changeEvent.setVariableOwnerID(originalVariable.getIdentifier());
				changeEvent.setChangedObjectIdentifier(originalVariable.getIdentifier());
				changeEvents.add(changeEvent);													
			}
		}
		
		//detect changes in code book number
		String originalCodeBookNumber
			= originalVariable.getCodeBookNumber();
		String revisedCodeBookNumber
			= revisedVariable.getCodeBookNumber();
		if (originalCodeBookNumber.equals(revisedCodeBookNumber) == false) {
			originalCodeBookNumber
				= ValidationUtility.convertEmptyValueToBlank(originalCodeBookNumber);
			revisedCodeBookNumber
				= ValidationUtility.convertEmptyValueToBlank(revisedCodeBookNumber);

			String formLabelText
				= MacawMessages.getMessage("variable.codeBookNumber.label"); 
			String fieldChangedMessage
				= MacawMessages.getMessage("variable.saveChanges.valueChanged",
											ownerVariableName,
											formLabelText,
											originalCodeBookNumber,
											revisedCodeBookNumber);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE,
										fieldChangedMessage, 
										userID);
			changeEvent.setVariableOwnerID(originalVariable.getIdentifier());
			changeEvent.setChangedObjectIdentifier(originalVariable.getIdentifier());
			changeEvents.add(changeEvent);														
		}
		
		//detect changes in column start
		String columnStartLabelText
			= MacawMessages.getMessage("variable.columnStart.label");
		String originalColumnStart = originalVariable.getColumnStart();
		if (originalColumnStart == null) {
			originalColumnStart
				= MacawMessages.getMessage("general.fieldValue.blank"); 			
		}
		
		String revisedColumnStart = revisedVariable.getColumnStart();
		if (revisedColumnStart == null) {
			revisedColumnStart
				= MacawMessages.getMessage("general.fieldValue.blank"); 
		}

		if (originalColumnStart.equals(revisedColumnStart) == false) {
			//change occured
			String fieldChangedMessage
				= MacawMessages.getMessage("variable.saveChanges.valueChanged",
										   ownerVariableName,
										   columnStartLabelText,
										   originalColumnStart,
										   revisedColumnStart);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE,
									   fieldChangedMessage, 
									   userID);

			changeEvent.setVariableOwnerID(originalVariable.getIdentifier());
			changeEvent.setChangedObjectIdentifier(originalVariable.getIdentifier());
			changeEvents.add(changeEvent);			
		}
		
		//detect changes in column end
		String columnEndLabelText
			= MacawMessages.getMessage("variable.columnEnd.label");
		String originalColumnEnd = originalVariable.getColumnEnd();
		if (originalColumnEnd.equals("") == true) {
			originalColumnEnd
				= MacawMessages.getMessage("general.fieldValue.blank");
		}

		String revisedColumnEnd = revisedVariable.getColumnEnd();
		if (revisedColumnEnd.equals("") == true) {
			revisedColumnEnd
				= MacawMessages.getMessage("general.fieldValue.blank"); 
		}


		if (originalColumnEnd.equals(revisedColumnEnd) == false) {			
			//change occured
			String fieldChangedMessage
				= MacawMessages.getMessage("variable.saveChanges.valueChanged",
										   ownerVariableName,
										   columnEndLabelText,
										   originalColumnEnd,
										   revisedColumnEnd);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE,
									   fieldChangedMessage, 
									   userID);

			changeEvent.setVariableOwnerID(originalVariable.getIdentifier());
			changeEvent.setChangedObjectIdentifier(originalVariable.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		//check alias
		String oldAlias = originalVariable.getAlias();
		String newAlias = revisedVariable.getAlias();
		if (oldAlias.equals(newAlias) == false) {
			oldAlias
				= ValidationUtility.convertEmptyValueToBlank(oldAlias);
			newAlias
				= ValidationUtility.convertEmptyValueToBlank(newAlias);

			String aliasText
				= MacawMessages.getMessage("variable.alias.label");
			String fieldChangedMessage
				= MacawMessages.getMessage("variable.saveChanges.valueChanged",
										   ownerVariableName,
										   aliasText,
										   oldAlias,
										   newAlias);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE,
									   fieldChangedMessage, 
									   userID);
			changeEvent.setVariableOwnerID(originalVariable.getIdentifier());
			changeEvent.setChangedObjectIdentifier(originalVariable.getIdentifier());
			changeEvents.add(changeEvent);										
		}

		//check file name
		/**
		String oldFilePath = originalVariable.getFilePath();
		String newFilePath = revisedVariable.getFilePath();
		if (oldFilePath.equals(newFilePath) == false) {
			oldFilePath
				= ValidationUtility.convertEmptyValueToBlank(oldFilePath);
			newFilePath
				= ValidationUtility.convertEmptyValueToBlank(newFilePath);

			String filePathText
				= MacawMessages.getMessage("variable.filePath.label");
			String fieldChangedMessage
				= MacawMessages.getMessage("variable.saveChanges.valueChanged",
										   ownerVariableName,
										   filePathText,
										   oldFilePath,
										   newFilePath);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE,
									   fieldChangedMessage, 
									   userID);
			changeEvent.setVariableOwnerID(originalVariable.getIdentifier());
			changeEvent.setChangedObjectIdentifier(originalVariable.getIdentifier());
			changeEvents.add(changeEvent);										
		}
	 	*/
		
		String oldAvailability = originalVariable.getAvailability();
		String revisedAvailability = revisedVariable.getAvailability();
		if (oldAvailability.equals(revisedAvailability) == false) {
			oldAvailability
				= ValidationUtility.convertEmptyValueToBlank(oldAvailability);
			revisedAvailability
				= ValidationUtility.convertEmptyValueToBlank(revisedAvailability);

			String availabilityText
				= MacawMessages.getMessage("variable.availability.label");
			String fieldChangedMessage
				= MacawMessages.getMessage("variable.saveChanges.valueChanged",
										   ownerVariableName,
										   availabilityText,
										   oldAvailability,
										   revisedAvailability);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE,
									   fieldChangedMessage, 
									   userID);
			changeEvent.setVariableOwnerID(originalVariable.getIdentifier());
			changeEvent.setChangedObjectIdentifier(originalVariable.getIdentifier());
			changeEvents.add(changeEvent);										
		}
			
		boolean originalIsCoded = originalVariable.isCoded();
		boolean revisedIsCoded = revisedVariable.isCoded();
		if (originalIsCoded != revisedIsCoded) {
			String oldIsCodedValue
				= Boolean.toString(originalIsCoded);
			String revisedIsCodedValue
				= Boolean.toString(revisedIsCoded);
			
			String isCodedLabel
				= MacawMessages.getMessage("variable.isCoded.label");
			String fieldChangedMessage
				= MacawMessages.getMessage("variable.saveChanges.valueChanged",
											ownerVariableName,
											isCodedLabel,
											oldIsCodedValue,
											revisedIsCodedValue);
			
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE,
									   fieldChangedMessage, 
									   userID);
			changeEvent.setVariableOwnerID(originalVariable.getIdentifier());
			changeEvent.setChangedObjectIdentifier(originalVariable.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		String originalForm = originalVariable.getForm();
		String revisedForm = revisedVariable.getForm();
		if (originalForm.equals(revisedForm) == false) {
			originalForm
				= ValidationUtility.convertEmptyValueToBlank(originalForm);
			revisedForm
				= ValidationUtility.convertEmptyValueToBlank(revisedForm);

			String formLabelText
				= MacawMessages.getMessage("variable.form.label"); 
			String fieldChangedMessage
				= MacawMessages.getMessage("variable.saveChanges.valueChanged",
										   ownerVariableName,
									   	   formLabelText,
									   	   originalForm,
									   	   revisedForm);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE,
									   fieldChangedMessage, 
									   userID);
			changeEvent.setVariableOwnerID(originalVariable.getIdentifier());
			changeEvent.setChangedObjectIdentifier(originalVariable.getIdentifier());
			changeEvents.add(changeEvent);											
		}
		
		String originalQuestionNumber = originalVariable.getQuestionNumber();
		String revisedQuestionNumber = revisedVariable.getQuestionNumber();
		if (originalQuestionNumber.equals(revisedQuestionNumber) == false) {
			originalQuestionNumber
				= ValidationUtility.convertEmptyValueToBlank(originalQuestionNumber);
			revisedQuestionNumber
				= ValidationUtility.convertEmptyValueToBlank(revisedQuestionNumber);

			String formLabelText
				= MacawMessages.getMessage("variable.questionNumber.label"); 
			String fieldChangedMessage
				= MacawMessages.getMessage("variable.saveChanges.valueChanged",
										   ownerVariableName,
										   formLabelText,
										   originalQuestionNumber,
										   revisedQuestionNumber);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE,
									   fieldChangedMessage, 
									   userID);
			changeEvent.setVariableOwnerID(originalVariable.getIdentifier());
			changeEvent.setChangedObjectIdentifier(originalVariable.getIdentifier());
			changeEvents.add(changeEvent);														
		}
		
		String originalNotes = originalVariable.getNotes();
		String revisedNotes = revisedVariable.getNotes();
		if (originalNotes.equals(revisedNotes) == false) {
			originalNotes
				= ValidationUtility.convertEmptyValueToBlank(originalNotes);
			revisedNotes
				= ValidationUtility.convertEmptyValueToBlank(revisedNotes);

			String notesLabelText
				= MacawMessages.getMessage("variable.notes.label"); 
			String fieldChangedMessage
				= MacawMessages.getMessage("variable.saveChanges.valueChanged",
										   ownerVariableName,
										   notesLabelText,
										   originalNotes,
										   revisedNotes);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE,
									   fieldChangedMessage, 
									   userID);
			changeEvent.setVariableOwnerID(originalVariable.getIdentifier());
			changeEvent.setChangedObjectIdentifier(originalVariable.getIdentifier());
			changeEvents.add(changeEvent);														
		}
		
		/**
		MacawChangeEvent changeEvent
			= detectChangesInAlternativeVariable(user,
												 originalVariable,
												 revisedVariable);
		if (changeEvent != null) {
			changeEvents.add(changeEvent);
		}
		*/
		
		return changeEvents;		
	}

	static public MacawChangeEvent detectChangesInAlternativeVariable(User user,
																	  Variable originalVariable,
																	  Variable revisedAlternativeVariable) {

		Variable oldAlternativeVariable 
			= originalVariable.getAlternativeVariable();
		boolean alternativeVariableChanged = false;
		if ((oldAlternativeVariable == null) && (revisedAlternativeVariable == null) ) {
			alternativeVariableChanged = false;		
		}
		else if ((oldAlternativeVariable == null) || (revisedAlternativeVariable == null)) {
			alternativeVariableChanged = true;
		}
		else {
			//both are non-null
			if (oldAlternativeVariable.getIdentifier() == revisedAlternativeVariable.getIdentifier()) {
				alternativeVariableChanged = true;				
			}
			else {
				alternativeVariableChanged = false;
			}
		}

		if (alternativeVariableChanged == true) {
			String blankFieldValue
				= MacawMessages.getMessage("general.fieldValue.blank");
			String fieldName
				= MacawMessages.getMessage("variable.alternativeVariable.label");
			String oldAlternativeVariableName = blankFieldValue;
			if (oldAlternativeVariable != null) {
				oldAlternativeVariableName = oldAlternativeVariable.getDisplayName();
			}
	
			String revisedAlternativeVariableName = blankFieldValue;
			if ( revisedAlternativeVariable != null) {
				revisedAlternativeVariableName = revisedAlternativeVariable.getDisplayName();
			}
	
			String changeMessage
				= MacawMessages.getMessage("variable.saveChanges.valueChanged",
											fieldName,
											oldAlternativeVariableName,
											revisedAlternativeVariableName);
	
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.VARIABLE,
							   			changeMessage,
							   			user.getUserID());
	
			int targetVariableID = originalVariable.getIdentifier();
			changeEvent.setVariableOwnerID(targetVariableID);
			changeEvent.setChangedObjectIdentifier(targetVariableID);
			return changeEvent;
		}
		
		return null;
	}
	
	// ==========================================
	// Section Interfaces
	// ==========================================
	//Interface: Displayable
	public String getDisplayName() {
		return getName();
	}

	public String getDisplayItemIdentifier() {
		return String.valueOf(identifier);
	}

	//Interface: Clonable
	abstract public Object clone();

	// ==========================================
	// Section Overload
	// ==========================================

	protected void cloneAttributes(Variable cloneVariable) {
		cloneVariable.setIdentifier(identifier);
		cloneVariable.setCategory(category);
		cloneVariable.setName(name);
		cloneVariable.setLabel(label);
		
		cloneVariable.setForm(form);
		cloneVariable.setQuestionNumber(questionNumber);

		cloneVariable.setYear(year);
		cloneVariable.setCleaned(isCleaned);
		cloneVariable.setCoded(isCoded);
		cloneVariable.setCleaningStatus(cleaningStatus);
		cloneVariable.setCleaningDescription(cleaningDescription);		
		
		cloneVariable.setCodeBookNumber(codeBookNumber);
		cloneVariable.setColumnStart(columnStart);
		cloneVariable.setColumnEnd(columnEnd);

		cloneVariable.setAvailability(availability);
		
		for (ValueLabel valueLabel : valueLabels) {
			ValueLabel clonedValueLabel
				= (ValueLabel) valueLabel.clone();
			cloneVariable.addValueLabel(clonedValueLabel);
		}
		
		cloneVariable.setAlias(alias);
		cloneVariable.setFilePath(filePath);
		cloneVariable.setIsNewRecord(isNewRecord);
		
		//make copies of references to supporting documents
		ArrayList<SupportingDocument> cloneSupportingDocuments 
			= new ArrayList<SupportingDocument>();
		for(SupportingDocument supportingDocument : supportingDocuments) {
			cloneSupportingDocuments.add((SupportingDocument) supportingDocument.clone());
		}
		cloneVariable.setSupportingDocuments(cloneSupportingDocuments);

		ArrayList<OntologyTerm> cloneOntologyTerms 
			= new ArrayList<OntologyTerm>();
		for(OntologyTerm ontologyTerm : ontologyTerms) {
			cloneOntologyTerms.add((OntologyTerm) ontologyTerm.clone());
		}
		cloneVariable.setOntologyTerms(cloneOntologyTerms);

		//we don't need 
		if (alternativeVariable != null) {
			Variable cloneAlternativeVariable
				= (Variable) alternativeVariable.clone();
			cloneVariable.setAlternativeVariable(cloneAlternativeVariable);
		}
		
		cloneVariable.setNotes(notes);
	}
}

