package messages.global;

import javax.swing.JOptionPane;

import client.BomberClient;
import client.gui.LoginDialog;
import client.gui.StartFrame;

/**
 * This class is a general message type to provide feedback about the status of
 * certain operations that were requested through network communication, such as
 * the creation of a session or the login procedure.
 * 
 * @author Steffen, Bj�rn, Andi
 * 
 */
public class GlobalStateMsg implements GlobalServerMsg {

	public static final int SESSION_CREATION_SUCCESSFUL = 1;
	public static final int SESSION_NAME_TAKEN = 2;
	public static final int LOGIN_SUCCESSFUL = 3;
	public static final int USERNAME_TAKEN = 4;
	public static final int WRONG_PASSWORD = 5;
	public static final int WRONG_USERNAME = 6;
	public static final int USER_STILL_LOGGED_IN = 7;

	/**
	 * The type of state this message indicates. See constants above.
	 */
	int type;

	public GlobalStateMsg(int type) {
		super();
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(BomberClient bomberClient) {
		if (this.type == LOGIN_SUCCESSFUL) {
			LoginDialog.init().loginSuccessful();
		}
		if (this.type == USERNAME_TAKEN) {
			LoginDialog.init().loginUsernameTaken();
		}
		if (this.type == WRONG_PASSWORD) {
			LoginDialog.init().loginWrongPassword();
		}
		if (this.type == WRONG_USERNAME) {
			LoginDialog.init().loginWrongUsername();
		}
		if (this.type == USER_STILL_LOGGED_IN) {
			LoginDialog.init().loginUserStillLogedIn();
		}
		if (this.type == SESSION_CREATION_SUCCESSFUL) {
			StartFrame.getInstance().showSessionLobby();
		}
		if(this.type == SESSION_NAME_TAKEN){
			BomberClient.getInstance().discardSessionRequest();
			JOptionPane.showMessageDialog(StartFrame.getInstance(), "A session with the same name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		StartFrame.getInstance().setCursorNormal();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		switch (type) {
		case SESSION_CREATION_SUCCESSFUL:
			return "SESSION_CREATION_SUCCESSFUL";

		case SESSION_NAME_TAKEN:
			return "SESSION_CREATION_SUCCESSFUL";

		default:
			return super.toString();
		}
	}
}
