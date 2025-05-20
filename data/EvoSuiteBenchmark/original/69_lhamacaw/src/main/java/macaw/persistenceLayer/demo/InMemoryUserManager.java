package macaw.persistenceLayer.demo;

import macaw.persistenceLayer.ChangeEventGenerator;
import macaw.system.*;
import macaw.util.SearchUtility;
import macaw.businessLayer.MacawSecurityAPI;
import macaw.businessLayer.User;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * manages instances of {@link macaw.businessLayer.User} in-memory.  Note that in future 
 * Macaw will rely on an external service for managing User identities.  For now,
 * Macaw uses its own built-in system for editing user data. 
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

public class InMemoryUserManager implements MacawSecurityAPI {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private InMemoryChangeEventManager changeEventManager;
	private User admin;
	
	private int userKey;
	private HashMap<Integer, User> userFromIdentifier;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public InMemoryUserManager(InMemoryChangeEventManager changeEventManager,
							   Log log,
							   User admin) {
		this.changeEventManager = changeEventManager;
		this.admin = admin;
		userFromIdentifier = new HashMap<Integer ,User>();
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public void validateUser(User candidateUser) throws MacawException {
		
		/**
		if ((candidateUser.getUserID().equals("jsmith") == false) ||
			(candidateUser.getPassword().equals("cool") == false)) {
			String errorMessage
				= MacawMessages.getMessage("user.error.invalidUser",
											candidateUser.getUserID());
			MacawException exception 
				= new MacawException(MacawErrorType.INVALID_USER,
									 errorMessage);
			throw exception;
		}
		
		*/
		
		/**
		String candidateUserID = candidateUser.getUserID();
		String candidatePassword = candidateUser.getPassword();
		
		ArrayList<User> users = new ArrayList<User>();
		users.addAll(userFromIdentifier.values());
		for (User currentUser : users) {
			if ( (currentUser.getUserID().equals(candidateUserID) == true) &&
				 (currentUser.getPassword().equals(candidatePassword) ==true)) {
				return;
			}
		}
		
		//no user was found. Therefore, trigger an exception
		*/
	}
	
	public void addUser(User admin,
						User user) throws MacawException {

		//Part I: Validate parameters
		validateAdministrator(admin);
		User.validateFields(user);
		checkUserDuplicates(user);

		//Part II: Perform add operation
		userKey++;
		user.setIdentifier(userKey);
		userFromIdentifier.put(userKey, user);
				
		//Part III: Record changes
		ArrayList<MacawChangeEvent> changeEvents
			= ChangeEventGenerator.addUserChange(admin, 
												 user);
		changeEventManager.registerChangeEvents(changeEvents);
	}

	public void updateUser(User admin,
						   User revisedUser) throws MacawException {

		//Part I: Validate parameters
		validateAdministrator(admin);
		User.validateFields(revisedUser);
		checkUserExists(revisedUser);

		User originalUser = getOriginalUser(revisedUser);

		ArrayList<MacawChangeEvent> changeEvents
			= User.detectFieldChanges(admin, 
									  originalUser, 
									  revisedUser);
		
		//make sure at least one change has been made
		if (changeEvents.size() == 0) {
			return;
		}
		
		//Part II: Perform the update operation
		int identifier = revisedUser.getIdentifier();
		userFromIdentifier.remove(identifier);
		userFromIdentifier.put(identifier, revisedUser);
		
		//Part III: Record changes
		changeEventManager.registerChangeEvents(changeEvents);
	}
	
	public void deleteUsers(User admin,
							ArrayList<User> usersToDelete) throws MacawException {
		
		//Part I: Validate Parameters
		validateAdministrator(admin);
		for (User userToDelete : usersToDelete) {
			checkUserExists(userToDelete);
		}
		
		//Part II: Perform delete users operation
		for (User userToDelete : usersToDelete) {
			int identifier = userToDelete.getIdentifier();
			userFromIdentifier.remove(identifier);
		}
			
		//Part III: Record changes
		ArrayList<MacawChangeEvent> changeEvents
			= ChangeEventGenerator.deleteUsersChanges(admin, usersToDelete);
		changeEventManager.registerChangeEvents(changeEvents);				
	}

	/**
	 * this is a utility 
	 */
	public ArrayList<User> getUsers(User admin) {				
		ArrayList<User> users = new ArrayList<User>();		
		users.addAll(userFromIdentifier.values());
		
		ArrayList<User> cloneUsers = new ArrayList<User>();
		for (User currentUser : users) {
			addUserInOrder(cloneUsers, currentUser);
		}
		
		return cloneUsers;
	}
	
	private void addUserInOrder(ArrayList<User> currentUserList, User userToAdd) {
		String targetUserID = userToAdd.getUserID();
		
		int insertionIndex = 0;
		for (insertionIndex = 0; insertionIndex < currentUserList.size(); insertionIndex++) {
			User currentUser = currentUserList.get(insertionIndex);
			String currentUserID = currentUser.getUserID();
			if ( currentUserID.compareTo(targetUserID) >= 0) {
				break;
			}
		}
		
		User cloneUser = (User) userToAdd.clone();
		currentUserList.add(insertionIndex, cloneUser);
	}
	
	private User getOriginalUser(User user) {
		return userFromIdentifier.get(user.getIdentifier());
	}
	
	
	public int getUserIdentifier(User user) {		
		ArrayList<User> users 
			= new ArrayList<User>();
		users.addAll(userFromIdentifier.values());
		
		for (User currentUser : users) {
			if (user.hasSameDisplayName(currentUser) == true) {
				return currentUser.getIdentifier();
			}
		}
		
		return -1;
	}

	public User getUserFromEmail(User user, String email) throws MacawException {
		User.checkValidEmail(email);
		
		SearchUtility searchUtility 
			= new SearchUtility(email);		
		
		ArrayList<User> users = new ArrayList<User>();
		users.addAll(userFromIdentifier.values());
	
		for (User currentUser : users) {
			String currentEmail = currentUser.getEmail();
			if (searchUtility.valueExactlyMatches(currentEmail) == true) {
				return currentUser;
			}
		}
		
		return null;
	}
	
	public User getUserFromID(User user,
			  				  String userID) throws MacawException {
		User.checkValidUserIDField(userID);
		ArrayList<User> users 
			= new ArrayList<User>();
		users.addAll(userFromIdentifier.values());

		for (User currentUser : users) {
			String currentUserID = currentUser.getUserID();
			
			if (currentUserID.equals(userID) == true) {
				return currentUser;
			}
		}
	
		return null;		
	}
	
	public ArrayList<User> getUnverifiedUsers(User admin) throws MacawException {

		ArrayList<User> allUsers 
			= new ArrayList<User>();
		allUsers.addAll(userFromIdentifier.values());
				
		ArrayList<User> unverifiedUsers 
			= new ArrayList<User>();

		String unverifiedValue
			= MacawMessages.getMessage("user.status.unverified");
		
		for (User currentUser : allUsers) {
			String currentStatus = currentUser.getStatus();

			if (currentStatus.equals(unverifiedValue) == true) {
				unverifiedUsers.add(currentUser);
			}
		}
		
		return unverifiedUsers;
	}
	
	public void clear() {
		userKey = 0;
		userFromIdentifier.clear();
	}
	
	// ==========================================
	// Section Errors and Validation
	// ==========================================
	
	public void validateAdministrator(User candidateAdmin) throws MacawException {
		if ( (admin.getUserID().equals(candidateAdmin.getUserID()) == false) ||
			 (admin.getPassword().equals(candidateAdmin.getPassword()) == false)) {
			String errorMessage
				= MacawMessages.getMessage("user.error.invalidAdministrator",
											candidateAdmin.getDisplayName());
			MacawException exception
				= new MacawException(MacawErrorType.INVALID_ADMINISTRATOR,
									 errorMessage);
			throw exception;
		}
	}
	
	private void checkUserExists(User candidateUser) throws MacawException {
		if (userFromIdentifier.containsKey(candidateUser.getIdentifier()) == false) {
			String errorMessage
				= MacawMessages.getMessage("general.error.nonExistentItem",
											candidateUser.getDisplayName());
			MacawException exception
				= new MacawException(MacawErrorType.NON_EXISTENT_USER,
									 errorMessage);
			throw exception;
		}
	}

	private void checkUserDuplicates(User candidateUser) throws MacawException {
		SearchUtility displayNameSearchUtility 
			= new SearchUtility(candidateUser.getDisplayName());		
		SearchUtility emailSearchUtility 
			= new SearchUtility(candidateUser.getEmail());		

		ArrayList<User> users = new ArrayList<User>();
		users.addAll(userFromIdentifier.values());
		for (User currentUser : users) {
			String currentUserDisplayName
				= currentUser.getDisplayName();
			String currentEmail = currentUser.getEmail();
			if ((displayNameSearchUtility.valueExactlyMatches(currentUserDisplayName) == true) ||
				(emailSearchUtility.valueExactlyMatches(currentEmail) == true) ) {
				//duplicate display name or duplicate email found
				String errorMessage
					= MacawMessages.getMessage("user.error.duplicateExists",
												candidateUser.getDisplayName(),
												candidateUser.getEmail());
				MacawException exception
					= new MacawException(MacawErrorType.DUPLICATE_USER,
										 errorMessage);
				throw exception;
			}
		}
	}

	
	// ==========================================
	// Section Interfaces
	// ==========================================

	// ==========================================
	// Section Overload
	// ==========================================

}

