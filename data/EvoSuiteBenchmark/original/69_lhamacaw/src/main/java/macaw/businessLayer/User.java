package macaw.businessLayer;

import macaw.system.*;
import macaw.util.ValidationUtility;
import macaw.util.Displayable;

import java.util.ArrayList;

/**
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

public class User implements Displayable {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private int identifier;
	private String userID;
	private String firstName;
	private String lastName;
	private String affiliation;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String postCode;
	private String county;
	private String phone;	
	private String email;
	private String status;
	private String password;
		
	// ==========================================
	// Section Construction
	// ==========================================
	public User() {
		init();
	}
	
	public User(String userID, String password) {
		init();
		identifier = 0;
		this.userID = userID;
		this.password = password;
	}
	
	private void init() {
		userID = "";
		identifier = 0;
		firstName = "";
		lastName = "";
		affiliation = "";
		addressLine1 = "";
		addressLine2 = "";
		city = "";
		postCode = "";
		county = "";
		phone = "";
		email = "";
			
		status = MacawMessages.getMessage("user.status.unverified");
		password = "";		
		
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	
	
	/**
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
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the affiliation
	 */
	public String getAffiliation() {
		return affiliation;
	}

	/**
	 * @param affiliation the affiliation to set
	 */
	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

	/**
	 * @param addressLine1 the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}

	/**
	 * @param addressLine2 the addressLine2 to set
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the postCode
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * @param postCode the postCode to set
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * @return the county
	 */
	public String getCounty() {
		return county;
	}

	/**
	 * @param county the county to set
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}	
	
	static public ArrayList<MacawChangeEvent> detectFieldChanges(User admin,
																 User originalUser,
																 User revisedUser) {

		ArrayList<MacawChangeEvent> changeEvents 
			= new ArrayList<MacawChangeEvent>();
		
		String oldUserID = originalUser.getUserID();
		String newUserID = revisedUser.getUserID();
		if (oldUserID.equals(newUserID) == false) {
			String changedTitleMessage
				= MacawMessages.getMessage("user.userID.saveChanges",
											originalUser.getDisplayName(),
											oldUserID,
											newUserID);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.USER,
									   changedTitleMessage,
									   admin.getUserID());

			changeEvent.setChangedObjectIdentifier(originalUser.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		String oldPassword = originalUser.getPassword();
		String newPassword = revisedUser.getPassword();
		if (oldPassword.equals(newPassword) == false) {
			String changedTitleMessage
				= MacawMessages.getMessage("user.password.saveChanges",
											originalUser.getDisplayName(),
											oldPassword,
											newPassword);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.USER,
									   changedTitleMessage,
									   admin.getUserID());

			changeEvent.setChangedObjectIdentifier(originalUser.getIdentifier());
			changeEvents.add(changeEvent);			
		}
	
		String oldFirstName = originalUser.getFirstName();
		String newFirstName = revisedUser.getFirstName();
		if (oldFirstName.equals(newFirstName) == false) {
			String changedTitleMessage
				= MacawMessages.getMessage("user.firstName.saveChanges",
											originalUser.getDisplayName(),
										    oldFirstName,
										    newFirstName);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.USER,
									   changedTitleMessage,
									   admin.getUserID());

			changeEvent.setChangedObjectIdentifier(originalUser.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		String oldLastName = originalUser.getLastName();
		String newLastName = revisedUser.getLastName();
		if (oldLastName.equals(newLastName) == false) {
			String changedTitleMessage
				= MacawMessages.getMessage("user.lastName.saveChanges",
											originalUser.getDisplayName(),
											oldLastName,
											newLastName);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.USER,
									   changedTitleMessage,
									   admin.getUserID());

			changeEvent.setChangedObjectIdentifier(originalUser.getIdentifier());
			changeEvents.add(changeEvent);			
		}
	
		//Detecting changes in field "Affiliation"
		String oldAffiliation = originalUser.getAffiliation();
		String newAffiliation = revisedUser.getAffiliation();
		if (oldAffiliation.equals(newAffiliation) == false) {
			String changedTitleMessage
				= MacawMessages.getMessage("user.affiliation.saveChanges",
											originalUser.getDisplayName(),
											oldAffiliation,
											newAffiliation);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.USER,
									   changedTitleMessage,
									   admin.getUserID());

			changeEvent.setChangedObjectIdentifier(originalUser.getIdentifier());
			changeEvents.add(changeEvent);			
		}
		
		//Detecting changes in field "Address Line 1"
		String oldAddressLine1 = originalUser.getAddressLine1();
		String newAddressLine1 = revisedUser.getAddressLine1();
		if (oldAddressLine1.equals(newAddressLine1) == false) {
			String changedTitleMessage
				= MacawMessages.getMessage("user.addressLine1.saveChanges",
											originalUser.getDisplayName(),
											oldAddressLine1,
											newAddressLine1);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.USER,
									   changedTitleMessage,
									   admin.getUserID());

			changeEvent.setChangedObjectIdentifier(originalUser.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		//Detecting changes in field "Address Line 2"
		String oldAddressLine2 = originalUser.getAddressLine2();
		String newAddressLine2 = revisedUser.getAddressLine2();
		if (oldAddressLine2.equals(newAddressLine2) == false) {
			String changedTitleMessage
				= MacawMessages.getMessage("user.addressLine2.saveChanges",
											originalUser.getDisplayName(),
											oldAddressLine2,
											newAddressLine2);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.USER,
									   changedTitleMessage,
									   admin.getUserID());

			changeEvent.setChangedObjectIdentifier(originalUser.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		//Detecting changes in field "City"
		String oldCity = originalUser.getCity();
		String newCity = revisedUser.getCity();
		if (oldCity.equals(newCity) == false) {
			String changedTitleMessage
				= MacawMessages.getMessage("user.city.saveChanges",
											originalUser.getDisplayName(),
											oldCity,
											newCity);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.USER,
									   changedTitleMessage,
									   admin.getUserID());

			changeEvent.setChangedObjectIdentifier(originalUser.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		//Detecting changes in field "County"
		String oldCounty = originalUser.getCounty();
		String newCounty = revisedUser.getCounty();
		if (oldCounty.equals(newCounty) == false) {
			String changedTitleMessage
				= MacawMessages.getMessage("user.county.saveChanges",
											originalUser.getDisplayName(),
											oldCounty,
											newCounty);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.USER,
									   changedTitleMessage,
									   admin.getUserID());

			changeEvent.setChangedObjectIdentifier(originalUser.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		//Detecting changes in field "Post Code"
		String oldPostCode = originalUser.getPostCode();
		String newPostCode = revisedUser.getPostCode();
		if (oldPostCode.equals(newPostCode) == false) {
			String changedTitleMessage
				= MacawMessages.getMessage("user.postCode.saveChanges",
											originalUser.getDisplayName(),
											oldPostCode,
											newPostCode);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.USER,
									   changedTitleMessage,
									   admin.getUserID());

			changeEvent.setChangedObjectIdentifier(originalUser.getIdentifier());
			changeEvents.add(changeEvent);			
		}
		
		//Detecting changes in field "Phone"
		String oldPhone = originalUser.getPhone();
		String newPhone = revisedUser.getPhone();
		if (oldPhone.equals(newPhone) == false) {
			String changedTitleMessage
				= MacawMessages.getMessage("user.phone.saveChanges",
											originalUser.getDisplayName(),
											oldPhone,
											newPhone);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.USER,
									   changedTitleMessage,
									   admin.getUserID());

			changeEvent.setChangedObjectIdentifier(originalUser.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		//Detecting changes in field "Email"
		String oldEmail = originalUser.getEmail();
		String newEmail = revisedUser.getEmail();
		if (oldEmail.equals(newEmail) == false) {
			String changedTitleMessage
				= MacawMessages.getMessage("user.email.saveChanges",
											originalUser.getDisplayName(),
											oldEmail,
											newEmail);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.USER,
									   changedTitleMessage,
									   admin.getUserID());

			changeEvent.setChangedObjectIdentifier(originalUser.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		//Detecting changes in field "Status"
		String oldStatus = originalUser.getStatus();
		String newStatus = revisedUser.getStatus();
		if (oldStatus.equals(newStatus) == false) {
			String changedTitleMessage
				= MacawMessages.getMessage("user.status.saveChanges",
											originalUser.getDisplayName(),
											oldStatus,
											newStatus);
			MacawChangeEvent changeEvent
				= new MacawChangeEvent(ChangeEventType.USER,
									   changedTitleMessage,
									   admin.getUserID());

			changeEvent.setChangedObjectIdentifier(originalUser.getIdentifier());
			changeEvents.add(changeEvent);			
		}

		return changeEvents;
	}
	
	public boolean hasSameDisplayName(User user) {
		if (getDisplayName().equals(user.getDisplayName()) == true) {
			return true;
		}
		return false;
	}


	// ==========================================
	// Section Errors and Validation
	// ==========================================

	static public void checkValidEmail(String candidateEmail) throws MacawException {
		if (ValidationUtility.isValidEmail(candidateEmail) == false) {
			//ERROR
			String errorMessage
				= MacawMessages.getMessage("general.error.invalidEmail",
											candidateEmail);
			MacawException exception
				= new MacawException(MacawErrorType.INVALID_USER,
									 errorMessage);
			throw exception;
		}
	}

	static public void checkValidUserIDField(String candidateUserID) throws MacawException {
		if (ValidationUtility.promotesSecurityRisk(candidateUserID) == true) {
			String userIDText 
				= MacawMessages.getMessage("user.userID.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.promotesSecurityRisk",
											userIDText,
											candidateUserID);
			MacawException exception
				= new MacawException(MacawErrorType.INVALID_USER,
									errorMessage);
			throw exception;
		}
	}
	
	static public void validateFields(User user) throws MacawException {
		
		ArrayList<String> errorMessages = new ArrayList<String>();
		String userID = user.getUserID();
		if (ValidationUtility.isBlank(userID) == true) {
			String userIDText 
				= MacawMessages.getMessage("user.userID.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
										   userIDText);
			errorMessages.add(errorMessage);
		}
		else if (ValidationUtility.promotesSecurityRisk(userID) == true) {
			String userIDText 
				= MacawMessages.getMessage("user.userID.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.promotesSecurityRisk",
										   userIDText,
										   userID);
			errorMessages.add(errorMessage);
		}
		
		String firstName = user.getFirstName();
		if (ValidationUtility.isBlank(firstName) == true) {
			String firstNameText 
				= MacawMessages.getMessage("user.firstName.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
											firstNameText);
			errorMessages.add(errorMessage);
		}
		else if (ValidationUtility.promotesSecurityRisk(firstName) == true) {
			String firstNameText 
				= MacawMessages.getMessage("user.firstName.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.promotesSecurityRisk",
											firstNameText,
											firstName);
			errorMessages.add(errorMessage);
		}
		
		String lastName = user.getLastName();
		if (ValidationUtility.isBlank(user.getLastName()) == true) {
			String lastNameText 
				= MacawMessages.getMessage("user.lastName.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
											lastNameText);
			errorMessages.add(errorMessage);
		}
		else if (ValidationUtility.promotesSecurityRisk(lastName) == true) {
			String lastNameText 
				= MacawMessages.getMessage("user.lastName.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.promotesSecurityRisk",
											lastNameText,
											lastName);
			errorMessages.add(errorMessage);
		}

		//Validating Affiliation
		String affiliation = user.getAffiliation();
		if (ValidationUtility.isBlank(affiliation) == true) {
			String affiliationText 
				= MacawMessages.getMessage("user.affiliation.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
										   affiliationText);
			errorMessages.add(errorMessage);
		}
		else if (ValidationUtility.promotesSecurityRisk(affiliation) == true) {
			String affiliationText 
				= MacawMessages.getMessage("user.affiliation.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.promotesSecurityRisk",
											affiliationText,
											affiliation);
			errorMessages.add(errorMessage);
		}

		//Validate field Address Line 1
		String addressLine1 = user.getAddressLine1();
		if (ValidationUtility.isBlank(addressLine1) == true) {
			String addressLine1Text 
				= MacawMessages.getMessage("user.addressLine1.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
										   addressLine1Text);
			errorMessages.add(errorMessage);
		}
		else if (ValidationUtility.promotesSecurityRisk(addressLine1) == true) {
			String addressLine1Text 
				= MacawMessages.getMessage("user.addressLine1.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.promotesSecurityRisk",
											addressLine1Text,
											addressLine1);
			errorMessages.add(errorMessage);
		}

		String addressLine2 = user.getAddressLine2();
		//Validate field Address Line 2
		/*
		String addressLine2 = user.getAddressLine2();
		if (ValidationUtility.isBlank(addressLine2) == true) {
			String addressLine2Text 
				= MacawMessages.getMessage("user.addressLine2.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
										   addressLine2Text);
			errorMessages.add(errorMessage);
		}
		*/


		if (addressLine2.equals("") == false) {
			if (ValidationUtility.promotesSecurityRisk(addressLine2) == true) {
				String addressLine2Text 
					= MacawMessages.getMessage("user.addressLine2.label");
				String errorMessage
					= MacawMessages.getMessage("general.error.promotesSecurityRisk",
												addressLine2Text,
												addressLine2);
				errorMessages.add(errorMessage);
			}
		}

		//Validate field City
		String city = user.getCity();
		if (ValidationUtility.isBlank(city) == true) {
			String cityText 
				= MacawMessages.getMessage("user.city.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
										   cityText);
			errorMessages.add(errorMessage);
		}
		else if (ValidationUtility.promotesSecurityRisk(city) == true) {
			String cityText 
				= MacawMessages.getMessage("user.city.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.promotesSecurityRisk",
											cityText,
											city);
			errorMessages.add(errorMessage);
		}		
		
		//Validate field County
		String county = user.getCounty();
		if (ValidationUtility.isBlank(county) == true) {
			String countyText 
				= MacawMessages.getMessage("user.county.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
										   countyText);
			errorMessages.add(errorMessage);
		}
		else if (ValidationUtility.promotesSecurityRisk(county) == true) {
			String userIDText 
				= MacawMessages.getMessage("user.county.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.promotesSecurityRisk",
									   		userIDText,
									   		userID);
			errorMessages.add(errorMessage);
		}
		
		//Validate field PostCode
		String postCode = user.getPostCode();
		if (ValidationUtility.isBlank(postCode) == true) {
			String postCodeText 
				= MacawMessages.getMessage("user.postCode.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
										   postCodeText);
			errorMessages.add(errorMessage);
		}
		else if (ValidationUtility.promotesSecurityRisk(postCode) == true) {
			String postCodeText 
				= MacawMessages.getMessage("user.postCode.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.promotesSecurityRisk",
											postCodeText,
											postCode);
			errorMessages.add(errorMessage);
		}

		//Validate field Phone
		String phone = user.getPhone();
		if (ValidationUtility.isBlank(phone) == true) {
			String phoneText 
				= MacawMessages.getMessage("user.phone.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
										   phoneText);
			errorMessages.add(errorMessage);
		}
		else if (ValidationUtility.promotesSecurityRisk(phone) == true) {
			String phoneText 
				= MacawMessages.getMessage("user.phone.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.promotesSecurityRisk",
											phoneText,
											phone);
			errorMessages.add(errorMessage);
		}
		
		//Validate field Email
		String email = user.getEmail();
		if (ValidationUtility.isBlank(email) == true) {
			String emailText 
				= MacawMessages.getMessage("user.email.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
										   emailText);
			errorMessages.add(errorMessage);
		}
		else if (ValidationUtility.isValidEmail(email) == false) {
			String errorMessage
				= MacawMessages.getMessage("general.error.invalidEmail",
											email);
			errorMessages.add(errorMessage);
		}

		//Validate field Status
		String status = user.getStatus();
		if (ValidationUtility.isBlank(status) == true) {
			String statusText 
				= MacawMessages.getMessage("user.status.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
											statusText);
			errorMessages.add(errorMessage);
		}
		else if (ValidationUtility.promotesSecurityRisk(status) == true) {
			String statusText
				= MacawMessages.getMessage("user.status.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.promotesSecurityRisk",
											statusText,
											status);
			errorMessages.add(errorMessage);
		}

		if(ValidationUtility.isBlank(user.getPassword()) == true) {
			String passwordText
				= MacawMessages.getMessage("user.password.label");
			String errorMessage
				= MacawMessages.getMessage("general.error.blankField",
										   passwordText);
			errorMessages.add(errorMessage);
		}
		
		if (errorMessages.size() > 0) {
			MacawException exception = new MacawException();
			for (String errorMessage : errorMessages) {
				exception.addErrorMessage(MacawErrorType.INVALID_USER,
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
		/**
		StringBuilder buffer = new StringBuilder();
		buffer.append(firstName);
		buffer.append(" ");
		buffer.append(lastName);
		return buffer.toString();
		*/
		return userID;
	}
	
	public String getDisplayItemIdentifier() {
		return String.valueOf(identifier);
	}
	
	public Object clone() {
		User cloneUser = new User();
		cloneUser.setIdentifier(identifier);
		cloneUser.setUserID(userID);
		cloneUser.setFirstName(firstName);
		cloneUser.setLastName(lastName);
		cloneUser.setAffiliation(affiliation);
		cloneUser.setAddressLine1(addressLine1);
		cloneUser.setAddressLine2(addressLine2);
		cloneUser.setCity(city);
		cloneUser.setPostCode(postCode);
		cloneUser.setCounty(county);
		cloneUser.setPhone(phone);
		cloneUser.setEmail(email);
		cloneUser.setStatus(status);
		cloneUser.setPassword(password);

		return cloneUser;
	}
	
	// ==========================================
	// Section Overload
	// ==========================================

}

