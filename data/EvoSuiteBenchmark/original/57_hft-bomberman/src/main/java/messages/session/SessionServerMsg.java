package messages.session;

import client.ClientGameSession;
import messages.Message;

/**
 * This interface defines a session message that the server sends to a client.
 * Session, in this context, means that the message is related to an event that
 * takes place after the client has joined a session but isn't actually in a game
 * round yet.
 * 
 * @author Steffen
 *
 */
public interface SessionServerMsg extends Message {
	
	/**
	 * Executes the action which the message is supposed to trigger.
	 * See description of the message class for details.
	 * 
	 * @param session
	 */
	public void execute(ClientGameSession session);

}
