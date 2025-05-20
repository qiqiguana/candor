package macaw.test.retrieval;

import macaw.businessLayer.User;
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

public class TestRetrieveUsers extends MacawRetrievalTestCase {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	
	private String verified;
	private String unverified;
	
	private User sallyMcLaren;
	private User brianFarnsworth;
	private User jenineHawthorn;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public TestRetrieveUsers() {
		super("Testing Retrieve Users");
		
		unverified
			= MacawMessages.getMessage("user.status.unverified");
		verified
			= MacawMessages.getMessage("user.status.verified");
		
		sallyMcLaren = new User();
		sallyMcLaren.setUserID("smclaren");
		sallyMcLaren.setFirstName("Sally");
		sallyMcLaren.setLastName("McLaren");
		sallyMcLaren.setAffiliation("GSTH");
		sallyMcLaren.setAddressLine1("345 5th Avenue");
		sallyMcLaren.setCity("London");
		sallyMcLaren.setCounty("None");
		sallyMcLaren.setPostCode("NW2 4R6");
		sallyMcLaren.setPostCode("SE4 3S8");
		sallyMcLaren.setPhone("444 444 44444");
		sallyMcLaren.setEmail("sm@companyX.com");
		sallyMcLaren.setStatus(verified);
		sallyMcLaren.setPassword("pswd1");
		
		brianFarnsworth = new User();
		brianFarnsworth.setUserID("bfarnsworth");
		brianFarnsworth.setFirstName("Brian");
		brianFarnsworth.setLastName("Farnsworth");
		brianFarnsworth.setAffiliation("Manchester MRI");
		brianFarnsworth.setAddressLine1("555 Oxford Rd.");
		brianFarnsworth.setCity("Manchester");
		brianFarnsworth.setCounty("Lancashire");
		brianFarnsworth.setPostCode("M15 0R4");
		brianFarnsworth.setPhone("333 333 33333");
		brianFarnsworth.setEmail("brian@mri.org.uk");
		brianFarnsworth.setStatus(unverified);
		brianFarnsworth.setPassword("tig1ger2");
		
		jenineHawthorn = new User();
		jenineHawthorn.setUserID("jhawthorn");
		jenineHawthorn.setFirstName("Jenine");
		jenineHawthorn.setLastName("Hawthorn");
		jenineHawthorn.setAffiliation("Royal Hospital");
		jenineHawthorn.setAddressLine1("123 Yellow Brick Lane");
		jenineHawthorn.setCity("London");
		jenineHawthorn.setCounty("None");
		jenineHawthorn.setPostCode("SE4 3S8");
		jenineHawthorn.setPhone("222 222 22222");
		jenineHawthorn.setEmail("jhawthorn@royalHospital.org.uk");
		jenineHawthorn.setStatus(verified);
		jenineHawthorn.setPassword("t0x1n");
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	//DONE public User getUserFromID(User user, String name) throws MacawException;
	//DONE public ArrayList<User> getUnverifiedUsers(User admin) throws MacawException;
	//DONE public ArrayList<User> getUsers(User user) throws MacawException;
	//DONE public User getUserFromEmail(User user, String email) throws MacawException;
	
	/**
	 * retrieves a known user
	 */
	public void testGetUserFromIDN1() {
		try {
			addDefaultUsers();
			
			User user = retrievalService.getUserFromID(admin, "jhawthorn");
			assertEquals("Hawthorn", user.getLastName());

			user = retrievalService.getUserFromID(admin, "bfarnsworth");
			assertEquals("Farnsworth", user.getLastName());	
		}
		catch(MacawException exception) {
			exception.printErrors();
			exception.printStackTrace(System.out);
			fail();
		}
	}
	
	/**
	 * retrieves an unknown user
	 */
	public void testGetUserFromIDA1() {
		try {
			addDefaultUsers();
			
			User user = retrievalService.getUserFromID(admin, "mickey111");
			assertNull(user);			
		}
		catch(MacawException exception) {
			exception.printStackTrace(System.out);
			fail();
		}
	}
		
	/**
	 * retrieve a list of unverified users
	 */
	public void testUnverifiedUsersN1() {
		try {
			addDefaultUsers();
			ArrayList<User> unverifiedUsers
				= retrievalService.getUnverifiedUsers(admin);

			assertEquals(1, unverifiedUsers.size());
			
			String actualUserID 
				= unverifiedUsers.get(0).getUserID();
			assertEquals("bfarnsworth", actualUserID);
		}
		catch(MacawException exception) {
			exception.printStackTrace(System.out);
			fail();
		}		
	}
	
	/**
	 * retrieve a list of unverified users from a collection of users that
	 * doesn't have any.
	 */
	public void testUnverifiedUsersA1() {
		try {	
			curationService.addUser(admin, sallyMcLaren);
			User farnsworthCopy = (User) brianFarnsworth.clone();
			farnsworthCopy.setStatus(verified);
			curationService.addUser(admin, farnsworthCopy);
			curationService.addUser(admin, jenineHawthorn);	
			ArrayList<User> unverifiedUsers
				= retrievalService.getUnverifiedUsers(admin);
			assertEquals(0, unverifiedUsers.size());
		}
		catch(MacawException exception) {
			exception.printStackTrace(System.out);
			fail();
		}		
	}

	public void testGetUsersN1() {
		try {	
			addDefaultUsers();
			ArrayList<User> allUsers
				= retrievalService.getUsers(admin);
			assertEquals(3, allUsers.size());

			String currentID
				= allUsers.get(0).getUserID();
			assertEquals("bfarnsworth", currentID);
			currentID
				= allUsers.get(1).getUserID();
			assertEquals("jhawthorn", currentID);
			currentID
				= allUsers.get(2).getUserID();
			assertEquals("smclaren", currentID);		
		}
		catch(MacawException exception) {
			exception.printStackTrace(System.out);
			fail();
		}		
	}

	/**
	 * get users when none are registered
	 */
	public void testGetUsersA1() {
		ArrayList<User> allUsers
			= retrievalService.getUsers(admin);
		assertEquals(0, allUsers.size());
	}

	/**
	 * get user based on email
	 */
	public void testGetUserFromEmailN1() {
		try {	
			addDefaultUsers();
			User user
				= retrievalService.getUserFromEmail(admin, 
													"brian@mri.org.uk");
			assertEquals("bfarnsworth", user.getUserID());
			
		}
		catch(MacawException exception) {
			exception.printStackTrace(System.out);
			fail();
		}		
	}

	/**
	 * get user based on non-existent email
	 */
	public void testGetUserFromEmailA1() {
		try {	
			addDefaultUsers();
			User user
				= retrievalService.getUserFromEmail(admin, 
													"walley@abc.com");
			assertNull(user);
		}
		catch(MacawException exception) {
			exception.printStackTrace(System.out);
			fail();
		}		
	}
	
	/**
	 * get user based an invalid email
	 */
	public void testGetUserFromEmailE1() {
		try {			
			addDefaultUsers();
			User user
				= retrievalService.getUserFromEmail(admin, 
													"walley");
			assertNull(user);
		}
		catch(MacawException exception) {
			exception.printErrors();
			fail();
		}
	}
	
	
	private void addDefaultUsers() throws MacawException {
		curationService.addUser(admin, sallyMcLaren);
		curationService.addUser(admin, brianFarnsworth);
		curationService.addUser(admin, jenineHawthorn);		
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

