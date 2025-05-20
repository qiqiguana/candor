package common;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map.Entry;

import messages.round.BombExplodedMsg;

import org.apache.log4j.Logger;

public abstract class GameRound {
	/**
	 * Key: playerId, Value: playerScore (round score)
	 */
	protected HashMap<Integer, Integer> scores = new HashMap<Integer, Integer>();
	protected int border = 0;
	private static final Logger logger = Logger.getLogger(GameRound.class);


	public int getScore(int id) {
		return scores.get(id);
	}

	public HashMap<Integer, Integer> getScores() {
		return scores;
	}

	public void setBorder(int border) {
		this.border = border;
	}

	public int getBorder() {
		return border;
	}

	/**
	 * Takes all the necessary actions after a game round has finished.
	 */
	public abstract void doPostRoundProcessing();

	/**
	 * Creates a new Tile
	 * 
	 * @param point
	 * @param id
	 * @param bombable
	 */
	public abstract void createNewTile(Point point, int id, String type,
			boolean bombable, boolean accessible, boolean active);
}