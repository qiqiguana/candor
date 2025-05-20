package messages.session;

import messages.Message;
import server.ClientInfo;
import server.ServerGameSession;

/**
 * This interface defines a session message that a client sends to the server.
 * Session, in this context, means that the message is related to an event that
 * takes place after the client has joined a session but isn't actually in a game
 * round yet.
 * 
 * @author Steffen
 *
 */
public interface SessionClientMsg extends Message {

	/**
	 * Executes the action which the message is supposed to trigger.
	 * See description of the message class for details.
	 * 
	 * @param session
	 * @param sender
	 */
	public void execute(ServerGameSession session, ClientInfo sender);
	
}
