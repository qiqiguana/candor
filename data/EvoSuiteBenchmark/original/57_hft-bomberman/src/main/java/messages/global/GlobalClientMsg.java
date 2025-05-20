package messages.global;

import messages.Message;
import server.BomberServer;
import server.ClientInfo;

/**
 * This interface defines a global message that a client sends to the server.
 * Global, in this context, means that the message is related to an event taking
 * place before the client joins a session.
 * 
 * @author Steffen
 *
 */
public interface GlobalClientMsg extends Message {

	/**
	 * Executes the action which the message is supposed to trigger.
	 * See description of the message class for details.
	 * 
	 * @param bomberSrv
	 * @param sender
	 */
	public void execute(BomberServer bomberSrv, ClientInfo sender);
	
}
