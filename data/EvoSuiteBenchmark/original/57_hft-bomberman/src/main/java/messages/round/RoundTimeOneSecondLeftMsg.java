package messages.round;

import org.apache.log4j.Logger;

import client.ClientGameRound;

/**
 * message to syncronize the time of the gameRound
 * 
 * @author Björn
 * 
 */
public class RoundTimeOneSecondLeftMsg implements RoundServerMsg {
	private static final Logger logger = Logger
			.getLogger(RoundTimeOneSecondLeftMsg.class);

	private long time;

	public RoundTimeOneSecondLeftMsg(long time) {
		this.time = time;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void execute(ClientGameRound round) {
		round.setTime(time);
		long seconds = round.getTime() / 1000;
		long minutes = seconds / 60;
		seconds = seconds % 60;
		if(seconds < 10) {

			logger.debug("Zeit: " + minutes + ":0" + seconds);	
		}
		else {

			logger.debug("Zeit: " + minutes + ":" + seconds);

		}
	}
}
