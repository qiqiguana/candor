package messages.round;

import common.GameObject;
import client.ClientPlayer;
import common.PowerUp;

import client.ClientGameRound;

/**
 * Message, that a player has hit a powerup
 * 
 * @author Björn
 * 
 */
public class PowerUpHitMsg implements RoundServerMsg {
	private static int SPEED = 1;
	private static int BOWL_BOMB = 2;
	private static int BOMB_LONGER = 3;
	private static int MORE_BOMBS = 4;

	private int type;
	private int playerID;
	private int powerUpID;

	/**
	 * @return
	 */
	public int getType() {
		return type;
	}

	/**
	 * @return
	 */
	public int getPlayerID() {
		return playerID;
	}

	/**
	 * @return
	 */
	public int getPowerUpID() {
		return powerUpID;
	}

	/**
	 * @param type,
	 *            the type of the powerup
	 * @param powerUpID,
	 *            the id of the powerup
	 * @param playerID,
	 *            the id of the player
	 */
	public PowerUpHitMsg(int type, int powerUpID, int playerID) {
		this.type = type;
		this.playerID = playerID;
		this.powerUpID = powerUpID;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void execute(ClientGameRound round) {
		if (ClientPlayer.class.isInstance(round.getGameObjectById(playerID))) {
			ClientPlayer player = (ClientPlayer) round
					.getGameObjectById(playerID);
			if (getType() == SPEED) {
				player.increaseSpeed();
				//player.resetBombDiameter();
				player.resetBowlBombs();
				//player.resetMaxBombs();
			}
			if (getType() == BOWL_BOMB) {
				player.setBowlBombs();
				//player.resetBombDiameter();
				player.resetSpeed();
				//player.resetMaxBombs();
			}
			if (getType() == BOMB_LONGER) {
				player.increaseBombDiameter();
				player.resetSpeed();
				player.resetBowlBombs();
				//player.resetMaxBombs();
			}
			if (getType() == MORE_BOMBS) {
				player.increaseMaxBombs();
				//player.resetBombDiameter();
				player.resetBowlBombs();
				player.resetSpeed();
			}
		}
		if (PowerUp.class.isInstance(round.getGameObjectById(powerUpID))) {
			PowerUp powerUp = (PowerUp) round.getGameObjectById(powerUpID);
			powerUp.setInactive();
			powerUp.setInvisible();
		}
	}
}
