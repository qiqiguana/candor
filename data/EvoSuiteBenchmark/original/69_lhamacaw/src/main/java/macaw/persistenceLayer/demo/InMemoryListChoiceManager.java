package macaw.persistenceLayer.demo;

import macaw.businessLayer.*;
import macaw.system.*;
import macaw.util.SearchUtility;
import macaw.util.ValidationUtility;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * an In-memory class that manages instances of subclasses of
 * {@link macaw.businessLayer.MacawListChoice}.  For each of the {@link macaw.businessLayer.Category},
 * {@link macaw.businessLayer.AliasFilePath}, {@link macaw.businessLayer.AvailabilityState} and 
 * {@link macaw.businessLayer.CleaningState}, InMemoryListChoiceManager supports the following
 * kinds of operations  
 * <ul>
 * <li>add, update, delete {@link macaw.businessLayer.MacawListChoice} objects</li>
 * <li>ensure that update or deletion operations cause appropriate action with all
 * variable instances that rely on the altered list choices.</li>
 * </ul>
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

public class InMemoryListChoiceManager extends InMemoryCurationConceptManager {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	
	private int currentAvailabilityKey;
	private HashMap<Integer, AvailabilityState> availabilityFromKey;
	private ArrayList<AvailabilityState> availabilityStates;

	private int currentCategoryKey;
	private HashMap<Integer, Category> categoryFromKey;
	private ArrayList<Category> categories;

	private int currentCleaningStateKey;
	private HashMap<Integer, CleaningState> cleaningStateFromKey;
	private ArrayList<CleaningState> cleaningStates;

	private int currentAliasFilePathKey;
	private HashMap<Integer, AliasFilePath> aliasFilePathFromKey;
	private ArrayList<AliasFilePath> aliasFilePaths;

	// ==========================================
	// Section Construction
	// ==========================================
	public InMemoryListChoiceManager(InMemoryChangeEventManager changeEventManager) {
		super(changeEventManager);
		initialiseCategories();
		initialiseCleaningStates();
		initialiseAvailabilityStates();
		initialiseAliasFilePaths();
	}

	private void initialiseCategories() {
		//add categories
		categoryFromKey = new HashMap<Integer, Category>();
		categories = new ArrayList<Category>();
	}
	
	private void initialiseCleaningStates() {
		cleaningStateFromKey = new HashMap<Integer, CleaningState>();
		cleaningStates = new ArrayList<CleaningState>();
	}
	
	private void initialiseAvailabilityStates() {
		
		//add availability states
		availabilityFromKey = new HashMap<Integer, AvailabilityState>();
		availabilityStates = new ArrayList<AvailabilityState>();
	}
	
	private void initialiseAliasFilePaths() {
		aliasFilePathFromKey = new HashMap<Integer, AliasFilePath>();
		aliasFilePaths = new ArrayList<AliasFilePath>();
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	//methods for managing categories
	public ArrayList<Category> getCategories(User user) throws MacawException {		
		ArrayList<Category> cloneCategories
			= new ArrayList<Category>();
		for (Category originalCategory : categories) {
			Category cloneCategory 
				= (Category) originalCategory.clone();
			cloneCategories.add(cloneCategory);
		}

		return cloneCategories;
	}
	
	
	public void addCategory(User user,
							Category category) throws MacawException {
			
		//Part I: Validate parameters
		Category.validateFields(category);
		checkCategoryDuplicates(category);

		//Part II: Perform add operation
		Category cloneCategory 
			= (Category) category.clone();
		currentCategoryKey++;
		cloneCategory.setIdentifier(currentCategoryKey);
		categoryFromKey.put(currentCategoryKey, cloneCategory);	
		categories.add(cloneCategory);		
		
		//Part III: Record changes
		String changeMessage
			= MacawMessages.getMessage("category.saveChanges.newRecord",
									   category.getDisplayName() );
		MacawChangeEvent changeEvent
			= new MacawChangeEvent(ChangeEventType.LIST_CHOICE, 
								   changeMessage,
								   user.getUserID() );
		registerChangeEvent(changeEvent);
	}
	
	//assumes that key exists
	public void updateCategory(User user,
							   Category revisedCategory,
							   ArrayList<Variable> variables) throws MacawException {

		
		//Part I: Validate parameters
		Category.validateFields(revisedCategory);
		checkCategoryExists(revisedCategory);
		checkCategoryDuplicates(revisedCategory);
			
		//make sure that there are at least some changes
		Category originalCategory
			= getOriginalCategory(revisedCategory);		
		ArrayList<MacawChangeEvent> changeEvents
			= Category.detectFieldChanges(user,
										  originalCategory,
										  revisedCategory);
		if (changeEvents.size() == 0) {
			return;
		}
		
		//Part II: Update the category
		int identifier = revisedCategory.getIdentifier();
		int foundIndex = categories.indexOf(originalCategory);
		int numberOfCategories = categories.size();
		
		categories.remove(originalCategory);
		if (foundIndex == numberOfCategories - 1) {
			categories.add(revisedCategory);
		}
		else {
			categories.add(foundIndex, revisedCategory);
		}
		categoryFromKey.remove(identifier);
		categoryFromKey.put(identifier, revisedCategory);
				
		//Part III: Change all variables and Record changes
	
		String oldCategoryPhrase = originalCategory.getDisplayName();
		String revisedCategoryPhrase = revisedCategory.getDisplayName();
		for (Variable currentVariable : variables) {
			String currentCategory = currentVariable.getCategory();
			if (currentCategory.equals(oldCategoryPhrase) == true) {
				currentVariable.setCategory(revisedCategoryPhrase);
				
				String changeMessage
					= MacawMessages.getMessage("category.saveChanges.searchAndReplace",
										 	   currentVariable.getDisplayName(),
										 	   oldCategoryPhrase,
										 	   revisedCategoryPhrase);
				MacawChangeEvent changeEvent 
					= new MacawChangeEvent(ChangeEventType.VARIABLE,
										   changeMessage,
										   user.getUserID());
				changeEvent.setVariableOwnerID(currentVariable.getIdentifier());
				changeEvents.add(changeEvent);	
			}	
		}
		registerChangeEvents(changeEvents);
	}
	
	public void deleteCategories(User user,
								 ArrayList<Category> categoriesToDelete,
								 ArrayList<Variable> variables) throws MacawException {

		
		//Part I: Validate parameters
		if (categoriesToDelete.size() == 0) {
			return;			
		}

		for (Category currentCategory : categoriesToDelete) {
			checkCategoryExists(currentCategory);
		}
		
		//Part II: Perform deletion
		for (Category currentCategory : categoriesToDelete) {
			Category originalCategory
				= getOriginalCategory(currentCategory);
			categoryFromKey.remove(currentCategory.getIdentifier());
			categories.remove(originalCategory);
		}
		
		//Part III: Change all variables and record changes
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		String userID = user.getUserID();
		for (Category currentCategory : categoriesToDelete) {
			String changeMessage
				= MacawMessages.getMessage("category.saveChanges.deleteRecord",
										   currentCategory.getDisplayName());			
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.LIST_CHOICE, changeMessage, userID);
			changeEvents.add(changeEvent);
			
			
			deleteCategoryFromVariables(currentCategory,
										variables,
										user,
										changeEvents);
		}
		
		registerChangeEvents(changeEvents);
	}

	public Category getCategory(String categoryName) throws MacawException {
		if (ValidationUtility.isEmptyValue(categoryName) == true) {
			return null;
		}

		SearchUtility searchUtility = new SearchUtility(categoryName);		
		for (Category currentCategory : categories) {
			String currentCategoryName = currentCategory.getName();
			if (searchUtility.valueExactlyMatches(currentCategoryName) == true) {
				Category result = (Category) currentCategory.clone();
				return result;
			}
		}	

		return null;		
	}
	
	public int getCategoryIdentifier(Category category,
									 Variable variable) throws MacawException {

		if (category.getName() == null) {
			return 0;
		}

		for (Category currentCategory : categories) {
			if (currentCategory.hasSameDisplayName(category) == true) {
				return currentCategory.getIdentifier();
			}			
		}	
				
		String variableName 
			= MacawMessages.getMessage("listChoices.unknownVariableName");	
		if (variable != null) {
			variableName = variable.getDisplayName();
		}
				
		//no identifier found.
		String errorMessage = MacawMessages.getMessage("category.error.nonExistent",
														variableName);
		
		MacawException exception
			= new MacawException(MacawErrorType.NON_EXISTENT_CATEGORY,
								 errorMessage);					
		throw exception;
	}
		
	private Category getOriginalCategory(Category copyOfCategory) {
		int currentIdentifier = copyOfCategory.getIdentifier();
		Category originalCategory
			= categoryFromKey.get(currentIdentifier);
		return originalCategory;
	}
		
	private void deleteCategoryFromVariables(Category categoryToDelete,
											 ArrayList<Variable> variables,
											 User user,
											 ArrayList<MacawChangeEvent> changeEvents) throws MacawException {

		//Part I: Validate parameters	
		//	we know the categoryToDelete no longer appears in the list of categories
		String oldCategoryPhrase = categoryToDelete.getDisplayName();
		String revisedCategoryPhrase 
			= MacawMessages.getMessage("general.fieldValue.unknown");
		for (Variable currentVariable : variables) {
			String currentCategory = currentVariable.getCategory();
			if (currentCategory.equals(oldCategoryPhrase) == true) {
				currentVariable.setCategory(revisedCategoryPhrase);

				String changeMessage
					= MacawMessages.getMessage("category.saveChanges.searchAndReplace",
												currentVariable.getDisplayName(),
												oldCategoryPhrase,
												revisedCategoryPhrase);
				MacawChangeEvent changeEvent 
					= new MacawChangeEvent(ChangeEventType.VARIABLE,
											changeMessage,
											user.getUserID());
				changeEvent.setVariableOwnerID(currentVariable.getIdentifier());
				changeEvents.add(changeEvent);	
			}	
		}		
	}

	//methods for managing cleaning states

	public ArrayList<CleaningState> getCleaningStates(User user) throws MacawException {
		
		ArrayList<CleaningState> cloneCleaningStates
			= new ArrayList<CleaningState>();
		for (CleaningState originalCleaningState : cleaningStates) {
			CleaningState cloneCleaningState 
				= (CleaningState) originalCleaningState.clone();
			cloneCleaningStates.add(cloneCleaningState);
		}

		return cloneCleaningStates;
	}
	
	public void addCleaningState(User user,
								 CleaningState cleaningState) throws MacawException {
		
		
		//Part I: Validate parameters
		CleaningState.validateFields(cleaningState);
		checkCleaningStateDuplicates(cleaningState);
		
		//Part II: Perform add operation
		CleaningState cloneCleaningState 
			= (CleaningState) cleaningState.clone();
		currentCleaningStateKey++;
		cloneCleaningState.setIdentifier(currentCleaningStateKey);
		cleaningStateFromKey.put(currentCleaningStateKey, cloneCleaningState);	
		cleaningStates.add(cloneCleaningState);		
		
		//Part III: Record changes
		String changeMessage
			= MacawMessages.getMessage("cleaningState.saveChanges.newRecord",
									   cleaningState.getDisplayName() );
		MacawChangeEvent changeEvent
			= new MacawChangeEvent(ChangeEventType.LIST_CHOICE,
								   changeMessage,
								   user.getUserID() );
		registerChangeEvent(changeEvent);	
	}
	
	//assumes that key exists
	public void updateCleaningState(User user,
								 	CleaningState revisedCleaningState,
								 	ArrayList<Variable> variables) throws MacawException {

		
		//Part I: Validate parameters
		CleaningState.validateFields(revisedCleaningState);
		checkCleaningStateExists(revisedCleaningState);
		checkCleaningStateDuplicates(revisedCleaningState);
		
		//ensure that there is at least some change
		CleaningState originalCleaningState
			= getOriginalCleaningState(revisedCleaningState);		
		ArrayList<MacawChangeEvent> changeEvents
			= CleaningState.detectFieldChanges(user,
											   originalCleaningState,
											   revisedCleaningState);
		if (changeEvents.size() == 0) {
			return;
		}
		
		//Part II: Update the cleaning state
		int identifier = revisedCleaningState.getIdentifier();
		int foundIndex = cleaningStates.indexOf(originalCleaningState);
		int numberOfCleaningStates = cleaningStates.size();
		
		cleaningStates.remove(originalCleaningState);
		if (foundIndex == numberOfCleaningStates - 1) {
			cleaningStates.add(revisedCleaningState);
		}
		else {
			cleaningStates.add(foundIndex, revisedCleaningState);
		}
		cleaningStateFromKey.remove(identifier);
		cleaningStateFromKey.put(identifier, revisedCleaningState);
				
		//Part III: Update all variables and record changes
		
		String oldCleaningStatePhrase = originalCleaningState.getDisplayName();
		String revisedCleaningStatePhrase = revisedCleaningState.getDisplayName();
		for (Variable currentVariable : variables) {
			String currentCleaningState = currentVariable.getCleaningStatus();
			if (currentCleaningState.equals(oldCleaningStatePhrase) == true) {
				currentVariable.setCleaningStatus(revisedCleaningStatePhrase);
				
				String changeMessage
					= MacawMessages.getMessage("cleaningState.saveChanges.searchAndReplace",
										 	   currentVariable.getDisplayName(),
										 	   oldCleaningStatePhrase,
										 	   revisedCleaningStatePhrase);
				MacawChangeEvent changeEvent 
					= new MacawChangeEvent(ChangeEventType.VARIABLE,
										   changeMessage,
										   user.getUserID());
				changeEvent.setVariableOwnerID(currentVariable.getIdentifier());
				changeEvents.add(changeEvent);	
			}	
		}
		registerChangeEvents(changeEvents);	
	}
	
	public void deleteCleaningStates(User user,
									 ArrayList<CleaningState> cleaningStatesToDelete,
									 ArrayList<Variable> variables) throws MacawException {

		//Part I: Validate parameters
		if (cleaningStatesToDelete.size() == 0) {
			return;
		}
		for (CleaningState currentCleaningState : cleaningStatesToDelete) {
			checkCleaningStateExists(currentCleaningState);
		}
		
		//Part II: Perform deletion
		for (CleaningState currentCleaningState : cleaningStatesToDelete) {
			CleaningState originalCleaningState
				= getOriginalCleaningState(currentCleaningState);
			cleaningStateFromKey.remove(currentCleaningState.getIdentifier());
			cleaningStates.remove(originalCleaningState);
		}
		
		//Part III: Update all variables and record changes
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		String userID = user.getUserID();
		for (CleaningState currentCleaningState : cleaningStatesToDelete) {
			String changeMessage
				= MacawMessages.getMessage("cleaningState.saveChanges.deleteRecord",
										   currentCleaningState.getDisplayName());			
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.LIST_CHOICE, changeMessage, userID);
			changeEvents.add(changeEvent);
			
			deleteCleaningStateFromVariables(currentCleaningState,
											 variables,
											 user,
											 changeEvents);
		}
		
		registerChangeEvents(changeEvents);
	}

	public int getCleaningStateIdentifier(CleaningState cleaningState, 
										  Variable variable) throws MacawException {
		for (CleaningState currentCleaningState : cleaningStates) {
			if (currentCleaningState.hasSameDisplayName(cleaningState) == true) {
				return currentCleaningState.getIdentifier();
			}			
		}	
		
		String variableName 
			= MacawMessages.getMessage("listChoices.unknownVariableName");
		if (variable != null) {
			variableName = variable.getDisplayName();
		}
		
		//no identifier found.
		String errorMessage 
			= MacawMessages.getMessage("cleaningState.error.nonExistent",
										variableName);
		MacawException exception
			= new MacawException(MacawErrorType.NON_EXISTENT_CLEANING_STATE,
								 errorMessage);					
		throw exception;
	}
		

	private void deleteCleaningStateFromVariables(CleaningState cleaningStateToDelete,
			  									  ArrayList<Variable> variables,
			  									  User user,
			  									  ArrayList<MacawChangeEvent> changeEvents) throws MacawException {

		String oldCleaningStatePhrase = cleaningStateToDelete.getDisplayName();
		String revisedCleaningStatePhrase 
			= MacawMessages.getMessage("general.fieldValue.unknown");
		for (Variable currentVariable : variables) {
			String currentCleaningStatus = currentVariable.getCleaningStatus();
			if (currentCleaningStatus.equals(oldCleaningStatePhrase) == true) {
				currentVariable.setCleaningStatus(revisedCleaningStatePhrase);

				String changeMessage
					= MacawMessages.getMessage("cleaningState.saveChanges.searchAndReplace",
												currentVariable.getDisplayName(),
												oldCleaningStatePhrase,
												revisedCleaningStatePhrase);
				MacawChangeEvent changeEvent 
					= new MacawChangeEvent(ChangeEventType.VARIABLE,
											changeMessage,
											user.getUserID());
				changeEvent.setVariableOwnerID(currentVariable.getIdentifier());
				changeEvents.add(changeEvent);	
			}	
		}		
	}

	private CleaningState getOriginalCleaningState(CleaningState copyOfCleaningState) {
		int currentIdentifier = copyOfCleaningState.getIdentifier();
		CleaningState originalCleaningState
			= cleaningStateFromKey.get(currentIdentifier);
		return originalCleaningState;
	}

	//methods for managing availability states	
	public ArrayList<AvailabilityState> getAvailabilityStates(User user) throws MacawException {
		ArrayList<AvailabilityState> cloneAvailabilityStates
			= new ArrayList<AvailabilityState>();
		for (AvailabilityState originalAvailabilityState : availabilityStates) {
			AvailabilityState cloneAvailabilityState 
				= (AvailabilityState) originalAvailabilityState.clone();
			cloneAvailabilityStates.add(cloneAvailabilityState);
		}

		return cloneAvailabilityStates;
	}
	
	public void addAvailabilityState(User user,
									 AvailabilityState availabilityState) throws MacawException {
		
		//Part I: Validate parameters
		AvailabilityState.validateFields(availabilityState);
		checkAvailabilityStateDuplicates(availabilityState);

		//Part II: Perform add operation
		AvailabilityState cloneAvailabilityState 
			= (AvailabilityState) availabilityState.clone();
		currentAvailabilityKey++;
		cloneAvailabilityState.setIdentifier(currentAvailabilityKey);
		availabilityFromKey.put(currentAvailabilityKey, cloneAvailabilityState);	
		availabilityStates.add(cloneAvailabilityState);		
		
		//Part III: Record changes
		String changeMessage
			= MacawMessages.getMessage("availabilityState.saveChanges.newRecord",
									   availabilityState.getDisplayName() );
		MacawChangeEvent changeEvent
			= new MacawChangeEvent(ChangeEventType.LIST_CHOICE,
								   changeMessage,
								   user.getUserID() );
		registerChangeEvent(changeEvent);
	}
	
	//assumes that key exists
	public void updateAvailabilityState(User user,
										AvailabilityState revisedAvailabilityState,
										ArrayList<Variable> variables) throws MacawException {
		
		//Part I: Validate parameters
		AvailabilityState.validateFields(revisedAvailabilityState);
		checkAvailabilityStateExists(revisedAvailabilityState);
		checkAvailabilityStateDuplicates(revisedAvailabilityState);

		//ensure that at least some changes occurred
		AvailabilityState originalAvailabilityState
			= getOriginalAvailabilityState(revisedAvailabilityState);		
		ArrayList<MacawChangeEvent> changeEvents
			= AvailabilityState.detectFieldChanges(user,
											   	   originalAvailabilityState,
											   	   revisedAvailabilityState);
		if (changeEvents.size() == 0) {
			return;
		}
		
		//Part II: Update the availability state
		int identifier = revisedAvailabilityState.getIdentifier();
		int foundIndex = availabilityStates.indexOf(originalAvailabilityState);
		int numberOfAvailabilityStates = availabilityStates.size();
		
		availabilityStates.remove(originalAvailabilityState);
		if (foundIndex == numberOfAvailabilityStates - 1) {
			availabilityStates.add(revisedAvailabilityState);
		}
		else {
			availabilityStates.add(foundIndex, revisedAvailabilityState);
		}
		availabilityFromKey.remove(identifier);
		availabilityFromKey.put(identifier, revisedAvailabilityState);
				
		//Part III: Update All Variables and Record changes
				
		String oldAvailabilityPhrase = originalAvailabilityState.getDisplayName();
		String revisedAvailabilityPhrase = revisedAvailabilityState.getDisplayName();
		for (Variable currentVariable : variables) {
			String currentAvailability = currentVariable.getAvailability();
			if (currentAvailability.equals(oldAvailabilityPhrase) == true) {
				currentVariable.setAvailability(revisedAvailabilityPhrase);
				
				String changeMessage
					= MacawMessages.getMessage("availabilityState.saveChanges.searchAndReplace",
										 	   currentVariable.getDisplayName(),
										 	   oldAvailabilityPhrase,
										 	   revisedAvailabilityPhrase);
				MacawChangeEvent changeEvent 
					= new MacawChangeEvent(ChangeEventType.VARIABLE,
										   changeMessage,
										   user.getUserID());
				changeEvent.setVariableOwnerID(currentVariable.getIdentifier());
				changeEvents.add(changeEvent);	
			}	
			registerChangeEvents(changeEvents);
		}
		
	}
	
	public void deleteAvailabilityStates(User user,
										 ArrayList<AvailabilityState> availabilityStatesToDelete,
										 ArrayList<Variable> variables) throws MacawException {
		
		//Part I: Validate parameters
		if (availabilityStatesToDelete.size() == 0) {
			return;
		}
		
		for (AvailabilityState currentAvailabilityState : availabilityStatesToDelete) {
			checkAvailabilityStateExists(currentAvailabilityState);
		}
		
		//Part II: Perform deletion
		for (AvailabilityState currentAvailabilityState : availabilityStatesToDelete) {
			AvailabilityState originalAvailabilityState
				= getOriginalAvailabilityState(currentAvailabilityState);
			availabilityFromKey.remove(currentAvailabilityState.getIdentifier());
			availabilityStates.remove(originalAvailabilityState);
		}
		
		//Part III: Update All Variables and Record changes
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		registerChangeEvents(changeEvents);

		String userID = user.getUserID();
		for (AvailabilityState currentAvailabilityState : availabilityStatesToDelete) {
			String changeMessage
				= MacawMessages.getMessage("availabilityState.saveChanges.deleteRecord",
										   currentAvailabilityState.getDisplayName());			
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.LIST_CHOICE, changeMessage, userID);
			changeEvents.add(changeEvent);
			
			deleteAvailabilityStateFromVariables(currentAvailabilityState,
												 variables,
												 user,
												 changeEvents);
		}		
	}

	public int getAvailabilityStateIdentifier(AvailabilityState availabilityState,
											  Variable variable) throws MacawException {
		for (AvailabilityState currentAvailabilityState : availabilityStates) {
			if (currentAvailabilityState.hasSameDisplayName(availabilityState) == true) {
				return currentAvailabilityState.getIdentifier();
			}			
		}
		
		String variableName 
			= MacawMessages.getMessage("listChoices.unknownVariableName");	
		if (variable != null) {
			variableName = variable.getDisplayName();
		}
		
		//no identifier found.
		String errorMessage = MacawMessages.getMessage("availabilityState.error.nonExistent",
														variableName);
		MacawException exception
			= new MacawException(MacawErrorType.NON_EXISTENT_AVAILABILITY_STATE,
								 errorMessage);					
		throw exception;
	}

	private void deleteAvailabilityStateFromVariables(AvailabilityState availabilityStateToDelete,
													  ArrayList<Variable> variables,
													  User user,
													  ArrayList<MacawChangeEvent> changeEvents) throws MacawException {
		
		String oldAvailabilityPhrase = availabilityStateToDelete.getDisplayName();
		String revisedAvailabilityPhrase 
			= MacawMessages.getMessage("general.fieldValue.unknown");
		for (Variable currentVariable : variables) {
			String currentAvailability = currentVariable.getAvailability();
			if (currentAvailability.equals(oldAvailabilityPhrase) == true) {
				currentVariable.setAvailability(revisedAvailabilityPhrase);
				
				String changeMessage
					= MacawMessages.getMessage("availabilityState.saveChanges.searchAndReplace",
										 	   currentVariable.getDisplayName(),
										 	   oldAvailabilityPhrase,
										 	   revisedAvailabilityPhrase);
				MacawChangeEvent changeEvent 
					= new MacawChangeEvent(ChangeEventType.VARIABLE,
										   changeMessage,
										   user.getUserID());
				changeEvent.setVariableOwnerID(currentVariable.getIdentifier());
				changeEvents.add(changeEvent);	
			}	
		}		
	}
	
	private AvailabilityState getOriginalAvailabilityState(AvailabilityState copyOfAvailabilityState) {
		int currentIdentifier = copyOfAvailabilityState.getIdentifier();
		AvailabilityState originalAvailabilityState
			= availabilityFromKey.get(currentIdentifier);
		return originalAvailabilityState;
	}
	

	//methods for managing data libraries
	public ArrayList<AliasFilePath> getAliasFilePaths(User user) throws MacawException {		
		ArrayList<AliasFilePath> cloneAliasFilePaths
			= new ArrayList<AliasFilePath>();
		for (AliasFilePath originalAliasFilePath : aliasFilePaths) {
			AliasFilePath cloneAliasFilePath 
				= (AliasFilePath) originalAliasFilePath.clone();
			cloneAliasFilePaths.add(cloneAliasFilePath);
		}

		return cloneAliasFilePaths;
	}
	
	public AliasFilePath getAliasFilePath(User user, String alias) throws MacawException {
		SearchUtility searchUtility = new SearchUtility(alias);		

		for (AliasFilePath originalAliasFilePath : aliasFilePaths) {
			String currentAliasName
				= originalAliasFilePath.getAlias();
			if (searchUtility.valueExactlyMatches(currentAliasName) == true) {
				return( (AliasFilePath) originalAliasFilePath.clone());
			}
		}
		
		return null;
	}
	
	public ArrayList<AliasFilePath> getAliasFilePathsMatchingName(User user, 
													   			  String aliasExpression) throws MacawException {
		SearchUtility searchUtility = new SearchUtility();
		searchUtility.setDefaultSearchPattern(aliasExpression);

		ArrayList<AliasFilePath> results 
			= new ArrayList<AliasFilePath>();
		for (AliasFilePath originalAliasFilePath : aliasFilePaths) {
			String currentAliasName
				= originalAliasFilePath.getAlias();
			if (searchUtility.valueContainsPattern(currentAliasName) == true) {
				AliasFilePath result = (AliasFilePath) originalAliasFilePath.clone();
				results.add(result);
			}
		}
		
		return results;
	}
	
	public void addAliasFilePath(User user,
							   	 AliasFilePath aliasFilePath) throws MacawException {
				
		//Part I: Validate parameters
		AliasFilePath.validateFields(aliasFilePath);
		checkAliasFilePathDuplicates(aliasFilePath);

		//Part II: Perform add operation
		AliasFilePath cloneAliasFilePath 
			= (AliasFilePath) aliasFilePath.clone();
		currentAliasFilePathKey++;
		cloneAliasFilePath.setIdentifier(currentAliasFilePathKey);
		aliasFilePathFromKey.put(currentAliasFilePathKey, cloneAliasFilePath);	
		aliasFilePaths.add(cloneAliasFilePath);		
		
		//Part III: Record changes
		String changeMessage
			= MacawMessages.getMessage("aliasFilePath.saveChanges.newRecord",
									   aliasFilePath.getDisplayName() );
		MacawChangeEvent changeEvent
			= new MacawChangeEvent(ChangeEventType.LIST_CHOICE,
								   changeMessage,
								   user.getUserID() );
		
		registerChangeEvent(changeEvent);
	}
	
	//assumes that key exists
	public void updateAliasFilePath(User user,
								  AliasFilePath revisedAliasFilePath,
								  ArrayList<Variable> variables) throws MacawException {

		
		//Part I: Validate parameters
		AliasFilePath.validateFields(revisedAliasFilePath);
		checkAliasFilePathExists(revisedAliasFilePath);
		checkAliasFilePathDuplicates(revisedAliasFilePath);

		//ensure there are at least some changes
		AliasFilePath originalAliasFilePath
			= getOriginalAliasFilePath(revisedAliasFilePath);		
		ArrayList<MacawChangeEvent> changeEvents
			= AliasFilePath.detectFieldChanges(user,
											   originalAliasFilePath,
											   revisedAliasFilePath);
		if (changeEvents.size() == 0) {
			return;
		}

		//Part II: Update the alias file path
		int identifier = revisedAliasFilePath.getIdentifier();
		int foundIndex = aliasFilePaths.indexOf(originalAliasFilePath);
		int numberOfAliasFilePaths = aliasFilePaths.size();
		
		aliasFilePaths.remove(originalAliasFilePath);
		if (foundIndex == numberOfAliasFilePaths - 1) {
			aliasFilePaths.add(revisedAliasFilePath);
		}
		else {
			aliasFilePaths.add(foundIndex, revisedAliasFilePath);
		}
		aliasFilePathFromKey.remove(identifier);
		aliasFilePathFromKey.put(identifier, revisedAliasFilePath);
				
		//Part III: Update variables and record changes		
		String oldAliasFilePathDisplayName = originalAliasFilePath.getDisplayName();
		String revisedAliasFilePathDisplayName = revisedAliasFilePath.getDisplayName();

		String oldAlias = originalAliasFilePath.getAlias();
		String revisedAlias = revisedAliasFilePath.getAlias();
		
		for (Variable currentVariable : variables) {
			String currentAlias = currentVariable.getAlias();
			if (currentAlias.equals(oldAlias) == true) {
				
				//perform a search and replace
				currentVariable.setAlias(revisedAlias);
				currentVariable.setFilePath(revisedAliasFilePath.getFilePath());
				
				String changeMessage
					= MacawMessages.getMessage("aliasFilePath.saveChanges.searchAndReplace",
										 	   currentVariable.getDisplayName(),
										 	   oldAliasFilePathDisplayName,
										 	   revisedAliasFilePathDisplayName);
				MacawChangeEvent changeEvent 
					= new MacawChangeEvent(ChangeEventType.VARIABLE,
										   changeMessage,
										   user.getUserID());
				changeEvent.setVariableOwnerID(currentVariable.getIdentifier());
				changeEvents.add(changeEvent);	
			}	
		}
		registerChangeEvents(changeEvents);
	}
	
	public void deleteAliasFilePaths(User user,
									ArrayList<AliasFilePath> aliasFilePathsToDelete,
									ArrayList<Variable> variables) throws MacawException {

		//Part I: Validate parameters
		for (AliasFilePath currentAliasFilePath : aliasFilePathsToDelete) {
			checkAliasFilePathExists(currentAliasFilePath);
		}
		
		//Part II: Perform deletion
		for (AliasFilePath currentAliasFilePath : aliasFilePathsToDelete) {
			AliasFilePath originalAliasFilePath
				= getOriginalAliasFilePath(currentAliasFilePath);
			aliasFilePathFromKey.remove(currentAliasFilePath.getIdentifier());
			aliasFilePaths.remove(originalAliasFilePath);
		}
		
		//Part III: Record changes
		ArrayList<MacawChangeEvent> changeEvents = new ArrayList<MacawChangeEvent>();
		String userID = user.getUserID();
		for (AliasFilePath currentAliasFilePath : aliasFilePathsToDelete) {
			String changeMessage
				= MacawMessages.getMessage("aliasFilePath.saveChanges.deleteRecord",
										   currentAliasFilePath.getDisplayName());			
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.LIST_CHOICE, 
									   changeMessage, 
									   userID);
			changeEvents.add(changeEvent);
			
			deleteAliasFilePathFromVariables(currentAliasFilePath,
											 variables,
											 user,
											 changeEvents);
		}
		
		registerChangeEvents(changeEvents);
	}

	public int getAliasFilePathIdentifier(AliasFilePath targetAliasFilePath,
										  Variable variable) throws MacawException {
		String targetAlias = targetAliasFilePath.getAlias();
		
		for (AliasFilePath currentAliasFilePath : aliasFilePaths) {
			if (currentAliasFilePath.getAlias().equals(targetAlias) == true) {
				return currentAliasFilePath.getIdentifier();
			}			
		}	
		
		String variableName 
			= MacawMessages.getMessage("listChoices.unknownVariableName");	
		if (variable != null) {
			variableName = variable.getDisplayName();
		}

		
		//no identifier found.
		String errorMessage 
			= MacawMessages.getMessage("aliasFilePath.error.nonExistent",
										targetAlias,
										variableName);
		MacawException exception
			= new MacawException(MacawErrorType.NON_EXISTENT_ALIAS_FILE_PATH,
								 errorMessage);					
		throw exception;
	}

	private void deleteAliasFilePathFromVariables(AliasFilePath aliasFilePathToDelete,
			  									  ArrayList<Variable> variables,
			  									  User user,
			  									  ArrayList<MacawChangeEvent> changeEvents) throws MacawException {

		String oldAliasFilePathPhrase = aliasFilePathToDelete.getDisplayName();
		String revisedAliasFilePathPhrase 
			= MacawMessages.getMessage("general.fieldValue.unknown");
		for (Variable currentVariable : variables) {
			String currentAlias = currentVariable.getAlias();
			if (currentAlias.equals(oldAliasFilePathPhrase) == true) {
				currentVariable.setAlias(revisedAliasFilePathPhrase);

				String changeMessage
					= MacawMessages.getMessage("aliasFilePath.saveChanges.searchAndReplace",
											   currentVariable.getDisplayName(),
											   oldAliasFilePathPhrase,
											   revisedAliasFilePathPhrase);
				MacawChangeEvent changeEvent 
					= new MacawChangeEvent(ChangeEventType.VARIABLE,
										   changeMessage,
										   user.getUserID());
				changeEvent.setVariableOwnerID(currentVariable.getIdentifier());
				changeEvents.add(changeEvent);	
			}
		}		
	}

	private AliasFilePath getOriginalAliasFilePath(AliasFilePath copyOfAliasFilePath) {
		int currentIdentifier = copyOfAliasFilePath.getIdentifier();
		AliasFilePath originalAliasFilePath
			= aliasFilePathFromKey.get(currentIdentifier);
		return originalAliasFilePath;
	}

	public String getFilePathFromAlias(String currentAlias) throws MacawException {
		for (AliasFilePath aliasFilePath: aliasFilePaths) {
			if (aliasFilePath.getAlias().equals(currentAlias) == true) {
				return aliasFilePath.getFilePath();				
			}
		}
		
		return "";		
	}
	
	// ==========================================
	// Section Errors and Validation
	// ==========================================

	public void checkCategoryExists(Category category) throws MacawException {
		int identifier = category.getIdentifier();
		
		if (categoryFromKey.containsKey(identifier) == false) {
			String variableName 
				= MacawMessages.getMessage("listChoices.unknownVariableName");

			String errorMessage
				= MacawMessages.getMessage("general.error.nonExistentItem",
										   category.getDisplayName(),
										   variableName);
			
			MacawException exception
				= new MacawException(MacawErrorType.NON_EXISTENT_CATEGORY,
									 errorMessage);
			throw exception;
		}
	}

	private void checkCategoryDuplicates(Category targetCategory) throws MacawException {
		SearchUtility searchUtility 
			= new SearchUtility(targetCategory.getDisplayName());		
		for (Category currentCleaningState : categories) {
			String currentDisplayName = currentCleaningState.getDisplayName();
			if (searchUtility.valueExactlyMatches(currentDisplayName) == true) {
				int targetCategoryIdentifier
					= targetCategory.getIdentifier();
				int currentCategoryIdentifier
					= currentCleaningState.getIdentifier();
				if (targetCategoryIdentifier != currentCategoryIdentifier) {
					//they have identical display names but have 
					//different identifier values
					String errorMessage
						= MacawMessages.getMessage("category.error.duplicateItem",
								targetCategory.getDisplayName());
					MacawException exception
						= new MacawException(MacawErrorType.DUPLICATE_CATEGORY,
											 errorMessage);
					throw exception;
				}
			}
		}
	}

	private void checkCleaningStateExists(CleaningState cleaningState) throws MacawException {
		int identifier = cleaningState.getIdentifier();
		if (cleaningStateFromKey.containsKey(identifier) == false) {
			
			String variableName 
				= MacawMessages.getMessage("listChoices.unknownVariableName");
			
			String errorMessage
				= MacawMessages.getMessage("general.error.nonExistentItem",
										   cleaningState.getDisplayName(),
										   variableName);
			MacawException exception
				= new MacawException(MacawErrorType.NON_EXISTENT_CLEANING_STATE,
									 errorMessage);
			throw exception;
		}
	}

	private void checkCleaningStateDuplicates(CleaningState targetCleaningState) throws MacawException {
		SearchUtility searchUtility 
			= new SearchUtility(targetCleaningState.getDisplayName());		

		for (CleaningState currentCleaningState : cleaningStates) {
			String currentDisplayName = currentCleaningState.getDisplayName();
			if (searchUtility.valueExactlyMatches(currentDisplayName) == true) {
				int targetCleaningStateIdentifier
					= targetCleaningState.getIdentifier();
				int currentCleaningStateIdentifier
					= currentCleaningState.getIdentifier();
				if (targetCleaningStateIdentifier != currentCleaningStateIdentifier) {
					//they have identical display names but have 
					//different identifier values
					String errorMessage
						= MacawMessages.getMessage("cleaningState.error.duplicateItem",
												   targetCleaningState.getDisplayName());
					MacawException exception
						= new MacawException(MacawErrorType.DUPLICATE_CLEANING_STATE,
											 errorMessage);
					throw exception;
				}
			}
		}
	}

	private void checkAvailabilityStateExists(AvailabilityState availabilityState) throws MacawException {
		int identifier = availabilityState.getIdentifier();
		
		
		if (availabilityFromKey.containsKey(identifier) == false) {
			String variableName 
				= MacawMessages.getMessage("listChoices.unknownVariableName");

			String errorMessage
				= MacawMessages.getMessage("general.error.nonExistentItem",
										   availabilityState.getDisplayName(),
										   variableName);
			MacawException exception
				= new MacawException(MacawErrorType.NON_EXISTENT_AVAILABILITY_STATE,
									 errorMessage);
			throw exception;
		}
	}
	
	private void checkAvailabilityStateDuplicates(AvailabilityState targetAvailabilityState) throws MacawException {

		SearchUtility searchUtility 
			= new SearchUtility(targetAvailabilityState.getDisplayName());
		for (AvailabilityState currentAvailabilityState : availabilityStates) {
			String currentDisplayName = currentAvailabilityState.getDisplayName();
			if (searchUtility.valueExactlyMatches(currentDisplayName) == true) {
				int targetAvailabilityStateIdentifier
					= targetAvailabilityState.getIdentifier();
				int currentAvailabilityStateIdentifier
					= currentAvailabilityState.getIdentifier();
				if (targetAvailabilityStateIdentifier != currentAvailabilityStateIdentifier) {
					//they have identical display names but have 
					//different identifier values
					String errorMessage
						= MacawMessages.getMessage("category.error.duplicateItem",
												   targetAvailabilityState.getDisplayName());
					MacawException exception
						= new MacawException(MacawErrorType.DUPLICATE_AVAILABILITY_STATE,	
											 errorMessage);
					throw exception;
				}
			}
		}
	}

	private void checkAliasFilePathExists(AliasFilePath aliasFilePath) throws MacawException {
		int identifier = aliasFilePath.getIdentifier();
		if (aliasFilePathFromKey.containsKey(identifier) == false) {

			String variableName 
				= MacawMessages.getMessage("listChoices.unknownVariableName");

			String errorMessage
				= MacawMessages.getMessage("general.error.nonExistentItem",
										   aliasFilePath.getDisplayName(),
										   variableName);
			MacawException exception
				= new MacawException(MacawErrorType.NON_EXISTENT_ALIAS_FILE_PATH,
									 errorMessage);
			throw exception;
		}
	}

	private void checkAliasFilePathDuplicates(AliasFilePath targetAliasFilePath) throws MacawException {
		SearchUtility searchUtility 
			= new SearchUtility(targetAliasFilePath.getDisplayName());		
		
		for (AliasFilePath currentAliasFilePath : aliasFilePaths) {
			String currentDisplayName = currentAliasFilePath.getDisplayName();
			if (searchUtility.valueExactlyMatches(currentDisplayName) == true) {
				int targetAliasFilePathIdentifier
					= targetAliasFilePath.getIdentifier();
				int currentAliasFilePathIdentifier
					= currentAliasFilePath.getIdentifier();
				if (targetAliasFilePathIdentifier != currentAliasFilePathIdentifier) {
					//they have identical display names but have 
					//different identifier values
					String errorMessage
						= MacawMessages.getMessage("aliasFilePath.error.duplicateItem",
								targetAliasFilePath.getDisplayName());
					MacawException exception
						= new MacawException(MacawErrorType.DUPLICATE_ALIAS_FILE_PATH,
											 errorMessage);
					throw exception;
				}
			}
		}
	}
		
	public void clear() {
		currentAvailabilityKey = 0;
		availabilityFromKey.clear();
		availabilityStates.clear();

		currentCategoryKey = 0;
		categoryFromKey.clear();
		categories.clear();

		currentCleaningStateKey = 0;
		cleaningStateFromKey.clear();
		cleaningStates.clear();

		currentAliasFilePathKey = 0;
		aliasFilePathFromKey.clear();
		aliasFilePaths.clear();		
	}
	
	// ==========================================
	// Section Interfaces
	// ==========================================

	// ==========================================
	// Section Overload
	// ==========================================

}

