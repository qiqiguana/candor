package messages.global;

import java.util.Vector;

import client.BomberClient;

/**
 * This message is sent from the server to the client when the latter enters the waiting room
 * or when the session list is refreshed. The SessionDetailsMsg is being abused as a data structure
 * for the info about each session.
 * 
 * @author Steffen, Andi
 * @see SessionDetailsMsg
 *
 */
public class SessionListMsg implements GlobalServerMsg {

	Vector<SessionDetailsMsg> sessionInfos = new Vector<SessionDetailsMsg>();
	
	public void addSessionInfo(SessionDetailsMsg sessionInfo) {
		sessionInfos.add(sessionInfo);
	}

	/**
	 * Returns the sessionInfo-Objects. Only use this to extract information.
	 * Use addSessionInfo() to add new objects.
	 * 
	 * @return
	 */
	public Vector<SessionDetailsMsg> getSessionInfos() {
		return sessionInfos;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(BomberClient bomberClient) {
		bomberClient.setAvailableSessions(sessionInfos);
	}
}
