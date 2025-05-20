package server;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;

import org.apache.log4j.Logger;

import messages.round.PowerUpHitMsg;

import common.Actor;
import common.Constants;
import common.GameObject;
import common.Player;
import common.PowerUp;

/**
 * @author Björn
 * 
 */
public class ServerPowerUp extends PowerUp {
	private static final Logger logger = Logger.getLogger(Actor.class);

	private ServerGameSession session;

	public ServerPowerUp(Point position, ServerGameSession session) {
		super(position);
		this.session = session;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void updateWithCollisionCheck(Collection<GameObject> gameObjects) {
		if (isVisible()) {
			for (GameObject gameObject : gameObjects) {
				if (ServerPlayer.class.isInstance(gameObject)) {
					ServerPlayer player = (ServerPlayer) gameObject;
					if (checkHit(player)) {
						PowerUpHitMsg msg = new PowerUpHitMsg(getTypeInt(),
								getId(), gameObject.getId());
						session.broadcastMsg(msg);
						if (getTypeInt() == 1) {
							player.increaseSpeed();
						}
						setInactive();
						setInvisible();
						break;
					}
				}
			}
		}
	}

	/**
	 * check if a gameObject hits the powerup.
	 * 
	 * @param gameObject
	 * @return
	 */
	public boolean checkHit(ServerPlayer player) {
		Rectangle rect = getCollisionRectangle();
		Rectangle rect2 = player.getCollisionRectangle();
		if (this.getId() != player.getId()) {
			if (rect.intersects(rect2)) {
				logger.info("Player " + player.getId() + " hit Power UP "
						+ getId());
				return true;
			}
		}
		return false;
	}
}
