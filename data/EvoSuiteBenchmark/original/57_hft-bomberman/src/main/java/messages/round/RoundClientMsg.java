package messages.round;

import server.ClientInfo;
import server.ServerGameRound;
import messages.Message;

/**
 * This interface defines a round message that a client sends to the server.
 * Round, in this context, means that the message is related to an in game event
 * or another event directly related to a game round.
 * 
 * @author Steffen
 *
 */
public interface RoundClientMsg extends Message {

	/**
	 * Executes the action which the message is supposed to trigger.
	 * See description of the message class for details.
	 * 
	 * @param round
	 * @param sender
	 */
	public void execute(ServerGameRound round, ClientInfo sender);
	
}
