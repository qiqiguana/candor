package dsachat.share;

import java.io.Serializable;

/**
 * This class is transfered to signal that a user has connected or disconnected
 * @author bernshausen
 *
 */
public class UserEvent implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -577246011601232791L;
	private boolean disconnect;
	private String name;
	
	/**
	 * is it a connect or disconnect event
	 * @return true if the user is disconnected
	 */
	public boolean isDisconnect() {
		return disconnect;
	}

	/**
	 * get the name of the user
	 */
	public String getName() {
		return name;
	}

	/**
	 * create a new user event
	 * @param discon is the user connected or disconnected
	 * @param username the name of the user
	 */
	public UserEvent(boolean discon, String username){
		disconnect=discon;
		name =username;
	}
	
	
}
