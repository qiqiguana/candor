package messages.round;

import client.ClientGameRound;

/**
 * This message is sent to a client to trigger the given countdown number.
 * This is used in order to achieve a sychronous countdown on all machines.
 * The execution of this message triggers the playing of a sound and displaying of
 * an on screen text corresponding to the given number.
 * 
 * @author Steffen
 *
 */
public class CountdownMsg implements RoundServerMsg {
	
	/**
	 * The current countdown number.
	 */
	private int number;

	public CountdownMsg(int number) {
		this.number = number;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(ClientGameRound round) {
		round.countdown(number);
	}

}
