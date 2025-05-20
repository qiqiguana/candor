package messages.round;

import messages.Message;
import client.ClientGameRound;

/**
 * This interface defines a round message that the server sends to a client.
 * Round, in this context, means that the message is related to an in game event
 * or another event directly related to a game round.
 * 
 * @author Steffen
 *
 */
public interface RoundServerMsg extends Message {
	
	/**
	 * Executes the action which the message is supposed to trigger.
	 * See description of the message class for details.
	 * 
	 * @param round
	 */
	public void execute(ClientGameRound round);

}
