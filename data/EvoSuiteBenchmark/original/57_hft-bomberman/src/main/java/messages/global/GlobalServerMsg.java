package messages.global;

import messages.Message;
import client.BomberClient;


/**
 * This interface defines a global message that the server sends to a client.
 * Global, in this context, means that the message is related to an event taking
 * place before the client joins a session.
 * 
 * @author Steffen
 * 
 */
public interface GlobalServerMsg extends Message {
	
	/**
	 * Executes the action which the message is supposed to trigger.
	 * See description of the message class for details.
	 * 
	 * @param bomberClient
	 */
	public void execute(BomberClient bomberClient);

}
