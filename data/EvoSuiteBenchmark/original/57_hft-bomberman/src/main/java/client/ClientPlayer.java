/**
 * 
 */
package client;

import java.awt.Point;

import org.apache.log4j.Logger;

import sound.SoundPlayer;

import common.Actor;
import common.Constants;
import common.Player;

/**
 * The client specific implemetation of a Player. This implementation will
 * notify any number of registered observers about changes to their state.
 * 
 * @author Andi, Björn
 */
public class ClientPlayer extends Player {
	private boolean bowlBombs = false;

	private Logger logger = Logger.getLogger(ClientPlayer.class);

	private int bombsPlanted = 0;

	private int bombDiameter = Constants.BOMB_DIAMETER;

	private int maxBombs = Constants.MIN_BOMBS;
	
	private long lastPlant;

	/**
	 * Creates a new ClientPlayer that starts on a fixed position.
	 * 
	 * @param position
	 *            The starting location for this player.
	 */
	public ClientPlayer(Point position, int id) {
		super(position);
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.Actor#preUpdate()
	 */
	@Override
	protected void preUpdate() {
		// if (!moveVector.equals(previousMoveVector)) {
		setChanged();
		// }
	}

	/**
	 * Plants a bomb on the battlefield. The bomb will be located at the current
	 * position. If the user can bowl bombs the bomb get a moveVector. This will
	 * trigger a notification for the observers.
	 */
	public void plantBomb() {
		long time = System.currentTimeMillis();
		if ((bombsPlanted >= maxBombs) || (time - lastPlant < 100)) {
			return;
		}
		lastPlant = time;
		setChanged();
		Point posClone = (Point) getPosition().clone();
		ClientBomb bomb = new ClientBomb(posClone, this);
		bomb.setId(BomberClient.getInstance().getNextId());
		bomb.setDiameter(bombDiameter);
		bomb.setRemote(false);
		Point moveVec = new Point(0, 0);
		if (bowlBombs) {
			bomb.setStopped(false);
			if (getDirection() == 1) {
				moveVec.y = -Actor.MOVE_DELTA * speed;
			} else if (getDirection() == 2) {
				moveVec.y = Actor.MOVE_DELTA * speed;
			} else if (getDirection() == 3) {
				moveVec.x = -Actor.MOVE_DELTA * speed;
			} else if (getDirection() == 4) {
				moveVec.x = Actor.MOVE_DELTA * speed;
			}
		} else {
			bomb.setStopped(true);
		}
		bomb.setMoveVector(moveVec);
		bombsThatSpawnedOnMe.add(bomb);
		notifyObservers(bomb);
		SoundPlayer.getInstance().bWerfen();
		/*
		 * This needs synchronization to prevent lost updates. A lost update
		 * could occur when another thread changes the bombsPlanted variable
		 * between the read/write operation of the increment.
		 */
		synchronized (this) {
			bombsPlanted = bombsPlanted + 1;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.Player#die()
	 */
	@Override
	public void die() {
		super.die();
		SoundPlayer.getInstance().stirbt();
		logger.info("#" + getId() + " died");
	}

	/**
	 * Increases the maximum number of bombs this player can plant by one. If
	 * the player can already plant MAX_BOMDS bombs, this method will do
	 * nothing.
	 */
	public void increaseMaxBombs() {
		if (maxBombs < Constants.MAX_BOMBS) {
			maxBombs = maxBombs + 1;
		}
	}

	/**
	 * Resets the maximum number of bombs this player can plant by one.
	 */
	public void resetMaxBombs() {
		maxBombs = Constants.MIN_BOMBS;
	}

	/**
	 * Notifies this player that a previously planted bomb has exploded. This
	 * will decrease the count of currently planted bombs.
	 * 
	 */
	public void plantedBombExploded() {
		/*
		 * This needs synchronization to prevent lost updates. A lost update
		 * could occur when another thread changes the bombsPlanted variable
		 * between the read/write operation of the decrement.
		 */
		synchronized (this) {
			bombsPlanted = bombsPlanted - 1;
		}
	}

	/**
	 * Increases the diameter of the bomb, that this player can plant.
	 */
	public void increaseBombDiameter() {
		bombDiameter += 2;
	}

	/**
	 * Resets the diameter of the bomb, that this player can plant.
	 */
	public void resetBombDiameter() {
		bombDiameter = Constants.BOMB_DIAMETER;
	}

	/**
	 * Sets, that player can bowl bombs
	 */
	public void setBowlBombs() {
		bowlBombs = true;
	}

	/**
	 * Sets, that player can't bowl bombs
	 */
	public void resetBowlBombs() {
		bowlBombs = false;
	}

	/**
	 * Returns weather the player can bowl bombs
	 * 
	 * @return boolean can bowl bombs
	 */
	public boolean canBowlBombs() {
		return bowlBombs;
	}
}
