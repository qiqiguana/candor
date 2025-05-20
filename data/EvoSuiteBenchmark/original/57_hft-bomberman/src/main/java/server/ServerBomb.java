package server;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.Vector;

import messages.round.BombExplodedMsg;

import org.apache.log4j.Logger;

import common.Bomb;
import common.Constants;
import common.GameObject;
import common.Player;
import common.PowerUp;
import common.Tile;
import db.DBException;
import db.DBGameUser;
import db.DBServiceFactory;

/**
 * The Bomb Object of the Server. This Bomb ticks some times and then exploded.
 * Then it detects the player and the tile hits.
 * 
 * @author Björn, Steffen, Daniel
 */
public class ServerBomb extends Bomb {
	/**
	 * The negated number of frames rendered after an explosion
	 */
	private static final int POST_EXPLOSION_FRAMES = -40;
	private static final Logger logger = Logger.getLogger(ServerBomb.class);
	private int tickTime = 2500;
	private int tickFrames = tickTime / 16;
	private ServerGameSession session;
	private boolean hasAlreadyExploded;
	private ExplosionBounds bounds;
	private boolean explodesrightnow = false;

	public ServerBomb(Point position, ServerGameSession session) {
		super(position);
		this.session = session;
	}

	@Override
	protected synchronized void move() {

	}

	@Override
	public synchronized void updateMovement(Point targetPosition) {
		position.setLocation(targetPosition);
	}

	/**
	 * Handles the explosion and the hit-detection of the bomb
	 * 
	 * @return
	 */
	public void explode(Collection<GameObject> gameObjects) {
		logger.info("Bomb exploded: " + id);
		bounds = getExplosionBounds(gameObjects);
		explodesrightnow = true;
		determineHits(gameObjects);

		hasAlreadyExploded = true;
	}

	private void determineHits(Collection<GameObject> gameObjects) {
		Vector<Integer> playerHits = new Vector<Integer>();
		Vector<Integer> tileHits = new Vector<Integer>();
		Vector<Integer> powerupHits = new Vector<Integer>();

		for (GameObject gameObject : gameObjects) {
			// hit players?
			if (Player.class.isInstance(gameObject)) {
				Player player = (Player) gameObject;

				if (bounds.contain(player.getPosition())
						&& this.checkHit(gameObject)) {

					int playerid = player.getId();
					logger.info("exploded and check for hits "
							+ explodesrightnow);
					if (explodesrightnow == true) {
						if (getPlanterId() == playerid) {
							logger.info(playerid+" killed himself "+getPlanterId());
							session.getCurrentRound().addRoundScore(getPlanterId(),
									-1);
						} else {
							logger.info(playerid+" scored one "+getPlanterId());
							session.getCurrentRound()
									.addRoundScore(getPlanterId(), 1);
						}

					}

					logger.info("Hit player: " + gameObject.getId() + " at "
							+ gameObject.getPosition());
					playerHits.add(gameObject.getId());
					gameObject.setInactive();
				}
			}
			// hit tiles?
			if (Tile.class.isInstance(gameObject)) {
				Tile t = (Tile) gameObject;
				if (t.isBombable()) {
					if (this.checkHit(t) && bounds.contain(t.getPosition())) {
						logger.info("Hit tile: " + t.getId() + " at "
								+ t.getPosition().x + ", " + t.getPosition().y);
						tileHits.add(t.getId());
						t.setInactive();
					}
				}
			}
			// hit powerups?
			if (ServerPowerUp.class.isInstance(gameObject)) {
				ServerPowerUp powerUp = (ServerPowerUp) gameObject;
				if (!powerUp.isVisible() && powerUp.isActive()) {
					if (this.checkHit(powerUp)
							&& bounds.contain(powerUp.getPosition())) {
						logger.info("Hit powerup: " + powerUp.getId() + " at "
								+ powerUp.getPosition().x + ", "
								+ powerUp.getPosition().y);
						powerUp.setVisible();
						powerupHits.add(powerUp.getId());
					}
				}
			}
		}

		if (tickFrames == POST_EXPLOSION_FRAMES) {
			active = false;
		}

		// msg is only sent the first time or if somebody ran into the explosion
		if (!hasAlreadyExploded
				|| !(playerHits.isEmpty() && tileHits.isEmpty())) {
			BombExplodedMsg msg = new BombExplodedMsg(this.getId(), playerHits,
					tileHits, powerupHits, hasAlreadyExploded);
			msg.setExplosionBounds(bounds);
			session.broadcastMsg(msg);
		}

	}

	private ExplosionBounds getExplosionBounds(
			Collection<GameObject> gameObjects) {
		int distance = (diameter * Constants.TILE_BORDER) / 2;
		int top, bottom, left, right;
		top = position.y - distance;
		bottom = position.y + distance;
		left = position.x - distance;
		right = position.x + distance;
		for (GameObject gameObject : gameObjects) {
			if (Tile.class.isInstance(gameObject)) {
				Tile t = (Tile) gameObject;
				// if (!t.isBombable()) {
				Point pos = t.getPosition();
				Point p = new Point(pos.x, pos.y);
				if (getVerticalHitBox().contains(p)) {
					int y = p.y - position.y;
					if (y < 0) {
						top = Math.max(top, p.y);
					} else {
						bottom = Math.min(bottom, p.y);
					}
				}
				if (getHorizontalHitBox().contains(p)) {
					int x = p.x - position.x;
					if (x > 0) {
						right = Math.min(right, p.x);
					} else {
						left = Math.max(left, p.x);
					}
				}
				// }
			}
		}
		logger.info("Explosion bounds: ");
		logger.info("top: " + top);
		logger.info("bottom: " + bottom);
		logger.info("left: " + left);
		logger.info("right: " + right);
		return new ExplosionBounds(top, bottom, right, left);
	}

	/**
	 * Returns the vertical hitbox of the bomb
	 * 
	 * @return
	 */
	private Rectangle getVerticalHitBox() {
		int x = position.getLocation().x - (Constants.TILE_BORDER / 2);
		int y = position.getLocation().y
				- ((diameter * Constants.TILE_BORDER) / 2);
		Rectangle rectangle = new Rectangle(x + 5, y,
				Constants.EXPLOSION_WIDTH, diameter
						* Constants.EXPLOSION_LENGTH - 1);
		return rectangle;
	}

	/**
	 * Returns the horizontal hitbox of the bomb
	 * 
	 * @return
	 */
	private Rectangle getHorizontalHitBox() {
		int x = position.getLocation().x
				- ((diameter * Constants.TILE_BORDER) / 2);
		int y = position.getLocation().y - (Constants.TILE_BORDER / 2);
		Rectangle rectangle = new Rectangle(x, y + 5, diameter
				* Constants.EXPLOSION_LENGTH - 1, Constants.EXPLOSION_WIDTH);
		return rectangle;
	}

	@Override
	public void updateWithCollisionCheck(Collection<GameObject> gameObjects) {
		super.updateWithCollisionCheck(gameObjects);
		if (stopped) {
			if (tick()) {
				explode(gameObjects);
			} else if (tickFrames > POST_EXPLOSION_FRAMES && tickFrames < 0) {
				// detect if a player ran into the explosion afterwards
				// (explosion
				// rendering
				// lasts 8 frames)
				determineHits(gameObjects);
			}
		}
	}

	/**
	 * handles the tick of the bomb
	 * 
	 * @return
	 */
	public boolean tick() {
		this.tickFrames--;

		if (tickFrames == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * check if a gameObject is hit by the bomb
	 * 
	 * @param gameObject
	 * @return
	 */
	private boolean checkHit(GameObject gameObject) {
		Rectangle[] rects = new Rectangle[] { this.getHorizontalHitBox(),
				this.getVerticalHitBox() };

		for (Rectangle rect : rects) {
			if (this.id != gameObject.getId()) {
				if (rect.intersects(gameObject.getCollisionRectangle())) {
					return true;
				}
			}
		}
		return false;
	}
}
