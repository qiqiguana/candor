package common;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;

import org.apache.log4j.Logger;

/**
 * 
 * @author Andi, Steffen, Tobi, Daniel, Björn
 * 
 */
public abstract class Actor extends GameObject {

    private static final Logger logger = Logger.getLogger(Actor.class);

    protected int speed = 1;
    protected Point moveVector;
    // protected Point previousMoveVector = NULL_MOVE_VECTOR;
    protected Point currTargetPosition;

    /**
     * Direction of the actor. Up = 1, Down = 2, Left = 3, Right = 4
     */
    protected int direction;

    /**
     * Indicates whether this object represents a remote Actor or not. TODO it
     * is probably better to derive a RemoteActor class instead of doing this
     */
    protected boolean isRemote = false;

    // fields for movement calculation
    private boolean updated;
    private int calcX;
    private int calcY;
    private boolean remoteCollided;

    /**
     * The basic movement distance. An actor with speed = 1 will move MOVE_DELTA
     * units forward.
     */
    protected static final int MOVE_DELTA = 2;

    /**
     * Neutral movement vector.
     */
    private static final Point NULL_MOVE_VECTOR = new Point(0, 0);

    protected Actor(Point position) {
        super(position);
        moveVector = NULL_MOVE_VECTOR;
        currTargetPosition = new Point(position);
    }

    public void moveUp() {
        moveVector = new Point(0, -MOVE_DELTA - speed);
        direction = 1;
    }

    public void moveDown() {
        moveVector = new Point(0, MOVE_DELTA + speed);
        direction = 2;
    }

    public void moveLeft() {
        moveVector = new Point(-MOVE_DELTA - speed, 0);
        direction = 3;
    }

    public void moveRight() {
        moveVector = new Point(MOVE_DELTA + speed, 0);
        direction = 4;
    }

    public void stop() {
        this.moveVector = NULL_MOVE_VECTOR;
    }

    /*
     * (non-Javadoc)
     * 
     * @see common.GameObject#update()
     */
    @Override
    public void update() {
        // Mark this actor as changed so the observers will
        // be notified when calling notifyObservers().
        preUpdate();

        // previousMoveVector = moveVector;

        move();

        notifyObservers();
        postUpdate();
    }

    synchronized protected void move() {
        if (isRemote) {
            if (currTargetPosition.equals(position)) {
                moveVector.setLocation(NULL_MOVE_VECTOR);
            } else if (currTargetPosition.x == position.x || remoteCollided) {
                moveVector = new Point(0, calcY);
            }

            // radical position correction if the difference from the actual
            // position is too big
            if (position.distance(currTargetPosition) > 80) {
                position.setLocation(currTargetPosition);
            }
        }

        int newX = position.x + moveVector.x;
        int newY = position.y + moveVector.y;
        setPosition(new Point(newX, newY));
        remoteCollided = false;
    }

    /**
     * 
     */
    protected void preUpdate() {
    }

    /**
     * 
     */
    protected void postUpdate() {
    }

    public Point getMoveVector() {
        return moveVector;
    }

    /**
     * Calculates and sets the new moveVector. If currTargetPosition has been
     * updated, the extrapolated X and Y values are determined. If not, the
     * movement axis is changed to the Y-axis. This is necessary because X- and
     * Y- movement have to be executed sequentially in order to avoid diagonal
     * movement.
     * 
     * This method depends on frequent ClientStatusMsgs.
     * 
     * This method is only used for remote players.
     */
    private void calcMoveVector() {
        if (updated) {
            calcX = currTargetPosition.x - position.x;
            calcY = currTargetPosition.y - position.y;
            if (calcY != 0) {
                calcY = calcY / Math.abs(calcY) * MOVE_DELTA;
                if (calcY < 0) {
                    calcY = calcY - speed;
                } else {
                    calcY = calcY + speed;
                }
            }
            updated = false;
        }

        if (calcX != 0) {
            calcX = calcX / Math.abs(calcX) * MOVE_DELTA;
            if (calcX < 0) {
                calcX = calcX - speed;
            } else {
                calcX = calcX + speed;
            }
            moveVector = new Point(calcX, 0);
        } else {
            moveVector = new Point(0, calcY);
        }
    }

    /**
     * Updates the movement of a remote Actor.
     * 
     * This method is only used by RemoteControl.
     * 
     * @param targetPosition
     */
    synchronized public void updateMovement(Point targetPosition) {
        // logger.info("Current position: " + position + "; Target position: " +
        // targetPosition);
        this.currTargetPosition = targetPosition;
        updated = true;
        calcMoveVector();
    }

    /**
     * updates and checks other gameObjects for collision
     * 
     * @see common.GameObject#update_universe(java.util.Vector)
     */
    /*
     * ALL MODIFICATIONS THAT ARE SPECIFIC FOR SUBCLASSES HAVE TO BE IMPLEMENTED
     * THERE.
     * NO MORE POOR MAN'S INHERITANCE!
     * -andi-
     * 
     * see also:
     * * this.extendedCollisionCheck()
     
     */
    @Override
    synchronized public void updateWithCollisionCheck(Collection<GameObject> gameObjects) {
        /*
         * read the comment above before changing anything here
         */
        boolean collision = false;

        for (GameObject gameObject : gameObjects) {
            if (Tile.class.isInstance(gameObject) && this.collide(gameObject)) {
                collision = true;
                break;
            }
            if (extendedCollisionCheck(gameObject)) {
                collision = true;
                break;
            }
        }

        if (collision) {
            stop();
        }

        update();

    }

    /**
     * Perform specific collision check that goes beyond wall collisions.
     * Override this in a subclass that needs this kind of behaviour.
     * @param gameObject The candidate for a collision
     * @return true if the extended check yields a collision, false otherwise
     */
    protected boolean extendedCollisionCheck(GameObject gameObject) {
        return false;
    }

    /**
     * checks whether the actor would collide with a gameobject or not
     * 
     * @see common.GameObject#collide(common.GameObject)
     */
    @Override
    public boolean collide(GameObject gameobject) {

        if (gameobject.isAccessable() == false) {
            Rectangle myrect = this.getCollisionRectangle();
            Rectangle rect = new Rectangle(myrect.x + moveVector.x, myrect.y + moveVector.y, myrect.width,
                    myrect.height);

            if (this.id != gameobject.id) {
                if (rect.intersects(gameobject.getCollisionRectangle())) { // collision?
                    remoteMovementHelper();
                    return true;
                }
            }
        }
        return false;
    }

    protected void remoteMovementHelper() {
        if (moveVector.x != 0 && calcY != 0) {
            remoteCollided = true;
        }
    }

    public void setRemote(boolean isRemote) {
        this.isRemote = isRemote;
    }

    /**
     * Sets the moveVector of this actor
     * 
     * @param moveVector,
     *            which should be set
     */
    public void setMoveVector(Point moveVector) {
        this.moveVector = moveVector;
    }

    /**
     * Returns the actual direction of this actor
     * 
     * @return int direction of actor
     */
    public int getDirection() {
        return direction;
    }

    public boolean isRemote() {
        return isRemote;
    }
}
