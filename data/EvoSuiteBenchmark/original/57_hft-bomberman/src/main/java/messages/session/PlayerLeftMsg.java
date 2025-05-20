package messages.session;

import server.ClientInfo;
import server.ServerGameSession;

/**
 * This message is sent to the server when a client leaves its current
 * session.
 * 
 * @author Steffen, Andi
 */
public class PlayerLeftMsg implements SessionClientMsg {
	
	public PlayerLeftMsg() {
		super();	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(ServerGameSession session, ClientInfo sender) {
		sender.leaveGameSession();
	}
	
}
