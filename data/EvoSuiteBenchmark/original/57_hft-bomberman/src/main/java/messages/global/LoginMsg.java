package messages.global;

import server.BomberServer;
import server.ClientInfo;
import server.ServerLogin;

/**
 * LoginMsg.java
 * 
 * Message from Client to Server, to request the login on the server
 * 
 * @author Björn
 */
public class LoginMsg implements GlobalClientMsg {
	public static int REGISTER = 1;
	public static int LOGIN = 2;
	public static int GUEST_LOGIN = 3;

	/**
	 * username
	 * 
	 */
	private String username;

	/**
	 * password
	 * 
	 */
	private String password;

	/**
	 * type of the login (register, login, guest-login)
	 * 
	 */
	private int action;

	/**
	 * Constructor
	 * 
	 * @param username
	 * @param password
	 * @param action
	 */
	public LoginMsg(String username, String password, int action) {
		super();
		this.action = action;
		this.password = password;
		this.username = username;
	}

	/**
	 * Constructor
	 * 
	 * @param username
	 * @param action
	 */
	public LoginMsg(String username, int action) {
		super();
		this.action = action;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(BomberServer bomberSrv, ClientInfo sender) {
		ServerLogin login = new ServerLogin();
		if (action == REGISTER) {
			if (login.register(this.username, this.password) == ServerLogin.LOGIN_SUCCESSFUL) {
				sender.setName(username);
				GlobalStateMsg msg = new GlobalStateMsg(
						GlobalStateMsg.LOGIN_SUCCESSFUL);
				sender.sendMsg(msg);
			} else {
				GlobalStateMsg msg = new GlobalStateMsg(
						GlobalStateMsg.USERNAME_TAKEN);
				sender.sendMsg(msg);
			}
		}
		if (action == LOGIN) {
			if (login.login(this.username, this.password) == ServerLogin.LOGIN_SUCCESSFUL) {
				sender.setName(username);
				GlobalStateMsg msg = new GlobalStateMsg(
						GlobalStateMsg.LOGIN_SUCCESSFUL);
				sender.sendMsg(msg);
			} else if (login.login(this.username, this.password) == ServerLogin.WRONG_PASSWORD) {
				GlobalStateMsg msg = new GlobalStateMsg(
						GlobalStateMsg.WRONG_PASSWORD);
				sender.sendMsg(msg);
			} else if (login.login(this.username, this.password) == ServerLogin.WRONG_USERNAME) {
				GlobalStateMsg msg = new GlobalStateMsg(
						GlobalStateMsg.WRONG_USERNAME);
				sender.sendMsg(msg);
			} else if (login.login(this.username, this.password) == ServerLogin.USER_STILL_LOGGED_IN) {
				GlobalStateMsg msg = new GlobalStateMsg(
						GlobalStateMsg.USER_STILL_LOGGED_IN);
				sender.sendMsg(msg);
			}
		}
		if (action == GUEST_LOGIN) {
			if (login.loginGuest(this.username) == ServerLogin.LOGIN_SUCCESSFUL) {
				sender.setName(username);
				GlobalStateMsg msg = new GlobalStateMsg(
						GlobalStateMsg.LOGIN_SUCCESSFUL);
				sender.sendMsg(msg);
			} else {
				GlobalStateMsg msg = new GlobalStateMsg(
						GlobalStateMsg.USERNAME_TAKEN);
				sender.sendMsg(msg);
			}
		}
	}
}
