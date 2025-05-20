package common;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

public class Player extends Actor {

    private static final Logger logger = Logger.getLogger(Player.class);
    private static final int MIN_SPEED = 1;
    private static final int MAX_SPEED = 3;
    // hitbox collision
    protected static final int COLLISION_WIDTH = 26;
    protected static final int COLLISION_HEIGHT = 25;
    protected static final int COLLISION_X_OFFSET = COLLISION_WIDTH / 2;
    protected static final int COLLISION_Y_OFFSET = 19;

    // hitbox explosion
    protected static final int BODY_WIDTH = 38;
    protected static final int BODY_HEIGHT = 60;
    private static final int BODY_Y_OFFSET = 55;
    private static final int BODY_X_OFFSET = 19;
    /**
     * The tolerance for tile collision detection. This value is used to shrink
     * the hit box by TILE_COLLISION_TOLERANCE units on each side.
     */
    private static final int TILE_COLLISION_TOLERANCE = 4;

    private int roundScore;
    private boolean accessToBombs = false;

    private int totalscore;

    private String name = null;

    protected Set<Bomb> bombsThatSpawnedOnMe = Collections.synchronizedSet(new HashSet<Bomb>());

    public Player(Point position) {
        super(position);
    }

    public int getRoundScore() {
        return roundScore;
    }

    @Override
    public void update() {
        super.update();
    }

    public void increaseSpeed() {
        if (speed < MAX_SPEED) {
            speed = speed + 1;
        }
    }

    public void resetSpeed() {
        speed = MIN_SPEED;
    }

    public void die() {
        active = false;
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean isAccessable() {
        return true;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return new Rectangle(position.x - COLLISION_X_OFFSET, position.y - COLLISION_Y_OFFSET, COLLISION_WIDTH,
                COLLISION_HEIGHT);
    }

    public void plantBomb() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addRoundScore(int roundScore) {
        this.roundScore += roundScore;

    }

    /**
     * Checks for a collision involving this {@link Actor} and the specified
     * {@link GameObject}. The calculation is done with a greater 'tolerance' than 
     * in the {@link Actor} implementation.
     * 
     * @param gameObject
     * @return
     */

    public boolean collide(GameObject gameObject) {
        if (gameObject.isAccessable() == false) {
            Rectangle myrect = this.getCollisionRectangle();
            int tolerance = TILE_COLLISION_TOLERANCE;
            int xSign = moveVector.x < 0 ? -1 : 1;
            int ySign = moveVector.y < 0 ? -1 : 1;
            
            Rectangle rect = new Rectangle(myrect.x + moveVector.x + tolerance*xSign, myrect.y + moveVector.y+tolerance*ySign,
                    myrect.width - tolerance * 2, myrect.height - tolerance * 2);
            
            if (this.id != gameObject.id) {
                if (rect.intersects(gameObject.getCollisionRectangle())) { // collision?
                    remoteMovementHelper();
                    return true;
                }
            }
        }
        return false;
    }

    /* (non-Javadoc)
     * @see common.Actor#extendedCollisionCheck()
     */
    @Override
    protected boolean extendedCollisionCheck(GameObject gameObject) {
        boolean collision = false;
        if (Bomb.class.isInstance(gameObject)) {
            
            Bomb bomb = (Bomb) gameObject;
            if (this.collide(bomb)) {
                /*
                 * This player collides with the bomb.
                 * If the bomb has not been updated yet, it must have spawned
                 * on this player.
                 */
                if (bomb.hasJustBeenPlanted()) {
                    bombsThatSpawnedOnMe.add(bomb);
                } else if (!bombsThatSpawnedOnMe.contains(bomb)) {
                    collision = true;
                }
            } else {
                /* This player doesn't collide with the bomb,
                 * so we remove it from the collection of bombs that
                 * have spawned on this player.
                 * If the bomb is not a member of the bombsThatSpawnedOnMe
                 * set, this will do nothing.
                 */
            	if(bombsThatSpawnedOnMe.contains(bomb)){
            		bombsThatSpawnedOnMe.remove(bomb);
            	}
                
            }
            
        }
        return collision;

    }

}
