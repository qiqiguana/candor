package server;

import org.apache.log4j.Logger;

import db.DBException;
import db.DBGameUser;
import db.DBServiceFactory;

/**
 * The Class for the login and the registration
 * 
 * @author Björn
 */
public class ServerLogin {
	public static final int LOGIN_SUCCESSFUL = 1;
	public static final int USERNAME_TAKEN = 2;
	public static final int WRONG_PASSWORD = 3;
	public static final int WRONG_USERNAME = 4;
	public static final int USER_STILL_LOGGED_IN = 5;

	/**
	 * the DBGameUser object
	 */
	private DBGameUser user = null;

	/**
	 * the status of the login
	 */
	private int status;

	/**
	 * the logger for login
	 */
	private static final Logger logger = Logger.getLogger(BomberServer.class);

	/**
	 * Handles the register of a new user.
	 * 
	 * @param username
	 * @param password
	 * @return boolean status, true if register is correct
	 */
	public int register(String username, String password) {
		try {
			user = DBServiceFactory.getInstance().getDBUser(username);
			if (user.getName() == null) {
				for (ClientInfo client : BomberServer.getInstance()
						.getClients()) {
					if (client.getName() != null) {
						if (client.getName().equals(username)) {
							status = USERNAME_TAKEN;
							break;
						} else {
							status = LOGIN_SUCCESSFUL;
						}
					} else {
						status = LOGIN_SUCCESSFUL;
					}
				}
			} else {
				status = USERNAME_TAKEN;
			}
			if (status == LOGIN_SUCCESSFUL) {
				logger.info("creating user "+username);
				user = new DBGameUser();
				user.setName(username);
				user.setPassword(password);
				user.setScore(0);
				DBServiceFactory.getInstance().saveGameUser(user);
				status = LOGIN_SUCCESSFUL;
				String userstr = DBServiceFactory.getInstance().getDBUser(user.getName()).toString();
				logger.info(userstr);
				logger.info("register as " + username + " correct!");
			}
		} catch (DBException e) {
		}

		return status;
	}

	/**
	 * Handles the login as a registered user.
	 * 
	 * @param username
	 * @param password
	 * @return boolean status, true if login is correct
	 */
	public int login(String username, String password) {
		try {
			user = DBServiceFactory.getInstance().getDBUser(username);
			if (user.getName() != null) {
				if (password.equals(user.getPassword())) {
					for (ClientInfo client : BomberServer.getInstance()
							.getClients()) {
						if (client.getName() != null) {
							if (client.getName().equals(username)) {
								status = USER_STILL_LOGGED_IN;
								break;
							} else {
								status = LOGIN_SUCCESSFUL;
							}
						} else {
							status = LOGIN_SUCCESSFUL;
						}
					}
				} else {
					status = WRONG_PASSWORD;
				}
			} else {
				status = WRONG_USERNAME;
			}

			if (status == LOGIN_SUCCESSFUL) {
				logger.info("login as " + username + " correct!");
			}
		} catch (DBException e) {
		}

		return status;
	}

	/**
	 * Handles the login as a guest.
	 * 
	 * @param username
	 * @return boolean status, true if login is correct
	 */
	public int loginGuest(String username) {
		try {
			if(username.equals("")){
				return WRONG_USERNAME;
			}
			logger.info("loginGuest called "+username);
			user = DBServiceFactory.getInstance().getDBUser(username);
			
			if (user == null || user.getName()==null || user.getName().equals("")) {
				logger.info("loginGuest user not in db "+username);
				register(username, "");
				user = DBServiceFactory.getInstance().getDBUser(username);
			}

			if (user != null
					&& (user.getPassword() == null || user.getPassword()
							.equals(""))) {
				logger.info("user "+username+" (Guest) logged with score "+user.getScore());
				for (ClientInfo client : BomberServer.getInstance()
						.getClients()) {
					if (client.getName() != null) {
						if (client.getName().equals(username)) {
							status = USERNAME_TAKEN;
							break;
						} else {
							status = LOGIN_SUCCESSFUL;
						}
					} else {
						status = LOGIN_SUCCESSFUL;
					}
				}
			} else {
				status = USERNAME_TAKEN;
			}
		} catch (DBException e) {
		}
		if (status == LOGIN_SUCCESSFUL) {
			logger.info("login as " + username + " correct!");
		}
		return status;
	}
}
