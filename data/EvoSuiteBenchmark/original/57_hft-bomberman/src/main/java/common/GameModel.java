package common;

import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map.Entry;

import messages.round.RoundTimeOverMsg;

import org.apache.log4j.Logger;

/**
 * @author Steffen, Andi, Björn
 * 
 */
public class GameModel {

	protected HashMap<Integer, GameObject> gameObjects = new HashMap<Integer, GameObject>();
	protected Vector<Player> players = new Vector<Player>();
	protected long time = Constants.time;

	protected static final Logger logger = Logger.getLogger(GameModel.class);

	public void update() {
		synchronized (gameObjects) {
			HashMap<Integer, GameObject> inactiveObjects = new HashMap<Integer, GameObject>();

			for (Entry<Integer, GameObject> entry : gameObjects.entrySet()) {
				GameObject gameObject = entry.getValue();
				if (gameObject.isActive()) {
					gameObject.updateWithCollisionCheck(gameObjects.values());
				}
			}

			for (Entry<Integer, GameObject> entry : gameObjects.entrySet()) {
				if (!entry.getValue().isActive()) {
					inactiveObjects.put(entry.getKey(), entry.getValue());
				}
			}

			// remove inactive objects now
			for (Entry<Integer, GameObject> entry : inactiveObjects.entrySet()) {
				logger.info("Removing game object: " + entry.getKey());
				gameObjects.remove(entry.getKey());
				if (Player.class.isInstance(entry.getValue())) {
					logger.info("Removing player: " + entry.getKey());
					players.remove(entry.getValue());
				}
			}
			inactiveObjects = null;
		}
	}

	/**
	 * Checks if the game is over and if yes returns the gameObjectId of the
	 * winner.
	 * 
	 * @return -1 if game still running, 0 in case of a tie, gameObjectId of the
	 *         winner in all other cases
	 */
	public int checkForWinner() {
		if (players.size() < 2) {
			int winnerId = 0; // 0 means it's a tie (all remaining players got
			// killed by the same bomb)
			if (players.size() == 1) { // only the winner is left
				winnerId = players.get(0).getId();
			}
			return winnerId;
		} else { // game still running
			return -1;
		}
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.util.Vector#add(java.lang.Object)
	 */

	public void addGameObject(int id, GameObject newGameObject) {
		synchronized (gameObjects) {
			this.gameObjects.put(id, newGameObject);
			if (Player.class.isInstance(newGameObject)) {
				players.add((Player) newGameObject);
			}
		}
	}

	/**
	 * @param id
	 * @return
	 */
	public GameObject getGameObjectById(int id) {
		return gameObjects.get(id);
	}

	public void snapToGrid(Bomb bomb) {
		Point pos = bomb.getPosition();
		int newX = (pos.x / 40) * 40 + 20;
		int newY = (pos.y / 40) * 40 + 20;
		pos.setLocation(newX, newY);
	}

	/**
	 * Returns the actual time of this GameModel.
	 * 
	 * @return long - actual time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Sets the time of this GameModel.
	 * 
	 * @param time
	 *            the time, which should be set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	public Vector<Player> getPlayers() {
		return players;
	}
}
