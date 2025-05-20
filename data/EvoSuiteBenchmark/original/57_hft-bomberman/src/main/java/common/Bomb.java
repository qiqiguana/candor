package common;

import java.awt.Point;
import java.awt.Rectangle;

import org.apache.log4j.Logger;

public class Bomb extends Actor {
	private static final Logger logger = Logger.getLogger(Bomb.class);
	/**
	 * Explosion diameter of the bomb measured in tiles. Valid diameters are
	 * 1,3,5,7...2*n+1 ( n >= 0).
	 */
	protected int diameter = Constants.BOMB_DIAMETER;
	protected GameObject planter;
	protected int planterId;

	protected boolean accessible;

	protected boolean firstUpdate = true;

	protected boolean exploded = false;

	protected boolean stopped = false;
	
	private int updates = 0;

	// hitbox collision
	protected static final int COLLISION_WIDTH = 40;
	protected static final int COLLISION_HEIGHT = 40;
	protected static final int COLLISION_X_OFFSET = COLLISION_WIDTH / 2;
	protected static final int COLLISION_Y_OFFSET = COLLISION_HEIGHT / 2;

	// hitbox explosion
	protected static final int BODY_WIDTH = 38;
	protected static final int BODY_HEIGHT = 60;
	protected static final int BODY_Y_OFFSET = 55;
	protected static final int BODY_X_OFFSET = 19;

	public int getPlanterId() {
		return planterId;
	}

	public void setPlanterId(int planterId) {
		this.planterId = planterId;
	}

	public Bomb(Point position) {
		super(position);
	}

	/**
	 * @param position
	 * @param planter
	 * @param type
	 */
	public Bomb(Point position, GameObject planter, int type) {
		super(position);
		this.planter = planter;
		this.planterId = planter.getId();
	}

	/**
	 * @param position
	 * @param planter
	 */
	public Bomb(Point position, GameObject planter) {
		super(position);
		this.planter = planter;
		this.planterId = planter.getId();
	}

	@Override
	protected void preUpdate() {
		setChanged();
	}
	
	

	/* (non-Javadoc)
     * @see common.Actor#postUpdate()
     */
    @Override
    protected void postUpdate() {
        if(updates < 5){
            updates++;
        }
    }

    /*
     * don't ask...
     */
    public boolean hasJustBeenPlanted() {
		return updates < 5;
	}

	@Override
	public boolean isAccessable() {
		return accessible;
	}

	/**
	 * explode
	 */
	public void explode() {
		logger.info("Bomb " + id + " exploded!");
		exploded = true;
		active = false;
		setChanged();
		notifyObservers();
	}

	public int getDiameter() {
		return diameter;
	}

	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}

	@Override
	public Rectangle getCollisionRectangle() {
		return new Rectangle(position.x - COLLISION_X_OFFSET, position.y
				- COLLISION_Y_OFFSET, COLLISION_WIDTH, COLLISION_HEIGHT);
	}

	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}

	/**
	 * Returns weather the bomb has been exploded or not.
	 * 
	 * @return boolean
	 */
	public boolean isExploded() {
		return exploded;
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}
}
