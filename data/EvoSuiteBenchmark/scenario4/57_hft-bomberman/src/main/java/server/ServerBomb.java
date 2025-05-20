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
 * @author Bj�rn, Steffen, Daniel
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
    }

    @Override
    protected synchronized void move();

    @Override
    public synchronized void updateMovement(Point targetPosition);

    /**
     * Handles the explosion and the hit-detection of the bomb
     *
     * @return
     */
    public void explode(Collection<GameObject> gameObjects);

    private void determineHits(Collection<GameObject> gameObjects);

    private ExplosionBounds getExplosionBounds(Collection<GameObject> gameObjects);

    /**
     * Returns the vertical hitbox of the bomb
     *
     * @return
     */
    private Rectangle getVerticalHitBox();

    /**
     * Returns the horizontal hitbox of the bomb
     *
     * @return
     */
    private Rectangle getHorizontalHitBox();

    @Override
    public void updateWithCollisionCheck(Collection<GameObject> gameObjects);

    /**
     * handles the tick of the bomb
     *
     * @return
     */
    public boolean tick();

    /**
     * check if a gameObject is hit by the bomb
     *
     * @param gameObject
     * @return
     */
    private boolean checkHit(GameObject gameObject);
}
