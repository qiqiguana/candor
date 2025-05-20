package messages.global;

import server.BomberServer;
import server.ClientInfo;

/**
 * This message is sent to the server to indicate that a client wants to 
 * join an existing session. A JoinAckMsg will be sent back to the client
 * indicating whether or not this was successful.
 * 
 * @author Steffen
 * @see JoinAckMsg
 */
public class JoinSessionMsg implements GlobalClientMsg {

	private String sessionName;
	
	public JoinSessionMsg(String sessionName, String playerName) {
		super();
		this.sessionName = sessionName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(BomberServer bomberSrv, ClientInfo sender) {
		sender.joinGameSession(bomberSrv.getGameSession(sessionName));
	}

}
