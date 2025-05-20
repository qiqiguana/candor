package messages.round;

import sound.SoundPlayer;
import client.ClientGameRound;
import client.gui.StartFrame;

/**
 * This message is used to deliver trivial state information during a game round,
 * such as "are you read", "start game round", "ready for next round" etc..
 * 
 * @author Steffen
 */
public class RoundStateMsg implements RoundServerMsg {
	
	// message types sent by server
	/**
	 * Triggers the "Are you ready" sound on the client.
	 */
	public static final int READY = 1;
	
	/**
	 * Signals the actual beginning of a game round. Triggers the "GOOO" sound. 
	 */
	public static final int START_GAME_ROUND = 2;
	
	/**
	 * The type of this message. See constants above.
	 */
	private int type;

	public RoundStateMsg(int type) {
		super();
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(ClientGameRound round) {
		// @TODOS Tobias: play sounds
		switch (type) {
		case READY:
			StartFrame.getInstance().showGameCanvas();
			SoundPlayer.getInstance().ready();
			break;
		case START_GAME_ROUND:
			round.countdown(0);
			round.start();
			break;
		default:
			throw new IllegalArgumentException("Unknown RoundStateMsg type: " + type);
		}
	}
	
}
