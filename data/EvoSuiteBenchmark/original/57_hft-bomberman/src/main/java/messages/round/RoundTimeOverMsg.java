package messages.round;

import org.apache.log4j.Logger;

import client.ClientGameRound;

/**
 * message, that the time of the gameRound has expired.
 * 
 * @author Björn
 * 
 */
public class RoundTimeOverMsg implements RoundServerMsg {
	private static final Logger logger = Logger
			.getLogger(RoundTimeOverMsg.class);

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void execute(ClientGameRound round) {
		logger.info("game over");
		round.doPostRoundProcessing();
	}

}
