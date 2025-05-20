package messages.round;

import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import server.ExplosionBounds;
import client.ClientBomb;
import client.ClientGameRound;
import client.ClientPlayer;
import client.gui.StartFrame;

import common.Player;
import common.PowerUp;
import common.Tile;

/**
 * BombExplodedMsg.java
 * 
 * Message from the Server to the clients to send, that the a bomb has been
 * exploded
 * 
 * @author Björn, Steffen, Daniel
 */
public class BombExplodedMsg implements RoundServerMsg {
	private static final Logger logger = Logger
			.getLogger(BombExplodedMsg.class);
	/**
	 * Position of the exploded bomb
	 * 
	 */
	private int bombID;

	/**
	 * The player-hits
	 * 
	 */
	private Vector<Integer> playerHits;

	/**
	 * The tile-hits
	 * 
	 */
	private Vector<Integer> tileHits;

	/**
	 * The powerup-hits
	 * 
	 */
	private Vector<Integer> powerupHits;

	// fields indicating the explosion bounds (pixel values)
	private int up;
	private int down;
	private int right;
	private int left;

	private boolean hasAlreadyExploded = false;

	/**
	 * @param bombID
	 * @param playerHits
	 * @param tileHits
	 */
	public BombExplodedMsg(int bombID, Vector<Integer> playerHits,
			Vector<Integer> tileHits, Vector<Integer> powerupHits,
			boolean hasAlreadyExploded) {
		super();
		this.bombID = bombID;
		this.playerHits = playerHits;
		this.tileHits = tileHits;
		this.powerupHits = powerupHits;
		this.hasAlreadyExploded = hasAlreadyExploded;
	}

	public void setExplosionBounds(ExplosionBounds bounds) {
		up = bounds.getUp();
		down = bounds.getDown();
		right = bounds.getRight();
		left = bounds.getLeft();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @author andi
	 */
	@Override
	public void execute(ClientGameRound round) {
		if (!hasAlreadyExploded) {
			ClientBomb bomb = (ClientBomb) round.getGameObjectById(bombID);
			if (bomb != null) {
				ClientPlayer planter = (ClientPlayer) round
						.getGameObjectById(bomb.getPlanterId());
				bomb.setSpread(new int[] { up, down, left, right });
				bomb.explode();
				if (planter != null) {
					planter.plantedBombExploded();
				}else{
					logger.info("planter is null");
				}
			}else{
				logger.info("bomb is null");
			}
		}

		for (Integer i : playerHits) {
			Player p = (Player) round.getGameObjectById(i);
			if (p != null) {
				p.die();
			} else {
				logger.info("no player");
			}
		}

		for (Integer i : tileHits) {
			Tile t = (Tile) round.getGameObjectById(i);
			if (t != null) {
				t.die();
			} else {
				logger.info("no tile");
			}
		}
		for (Integer i : powerupHits) {
			PowerUp p = (PowerUp) round.getGameObjectById(i);
			if (p != null) {
				p.setVisible();
			} else {
				logger.info("no powerup");
			}
		}
	}
}
