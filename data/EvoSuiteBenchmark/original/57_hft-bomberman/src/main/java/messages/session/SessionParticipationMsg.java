package messages.session;

import org.apache.log4j.Logger;

import server.ServerGameSession;
import messages.global.GlobalServerMsg;
import client.BomberClient;
import client.ClientPlayer;
import client.gui.StartFrame;

/**
 * This message is sent by the server to the clients in a session when a client
 * joins or leaves their session.
 * 
 * @author Steffen, Andi
 */
public class SessionParticipationMsg implements GlobalServerMsg {
	
	private static final Logger logger = Logger
	.getLogger(SessionParticipationMsg.class);
	
	/**
	 * True if the client has joined, false if it has left the session.
	 */
	private boolean hasJoined;
	
	/**
	 * The playerId of the joining/leaving player. (Same as its idOffset)
	 */
	private int playerId;
	
	/**
	 * The name of the player.
	 */
	private String playerName;
	

	/**
	 * Creates a new SessionParticipationMsg.
	 * 
	 * @param hasJoined True if the client has joined, false if it has left the session.
	 * @param playerId The playerId of the joining/leaving player. (Same as its idOffset)
	 * @param playerName The name of the player.
	 */
	public SessionParticipationMsg(boolean hasJoined, int playerId, String playerName) {
		super();
		this.hasJoined = hasJoined;
		this.playerId = playerId;
		this.playerName = playerName;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(BomberClient bomberClient) {
	    if(bomberClient.getCurrentSession() == null){
	        // Session has been terminated
	        return;
	    }
		if(hasJoined){
			bomberClient.getCurrentSession().join(playerId, playerName);
		}else{
		    bomberClient.getCurrentSession().leave(playerId);
		}
		StartFrame.getInstance().getSessionLobby().updateInfo();
	}

}
