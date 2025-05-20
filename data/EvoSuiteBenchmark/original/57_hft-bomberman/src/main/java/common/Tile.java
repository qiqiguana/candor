package common;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;

/** 
 * tile object can be specified for each tile of map
 * 
 * @author christian, Daniel Tunjic
 * 
 */
public class Tile extends GameObject {
	// tile properties
	private String type;
	private boolean accessible = true;
	private boolean bombable = false;
	private boolean visible = true;
	private double speedFactor = 1.0;

	/**
	 * create tile object at coordinates p
	 * 
	 * @param p
	 */
	public Tile(Point p) {
		super(p);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	/** gets type of tile, important for tile image
	 * @return
	 */
	public String getType() {
		return this.type;
	}

	/** sets type of tile, important for tile image
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/** sets a bunch of properties
	 * @param type - String, type of tile
	 * @param accessible - Boolean, is tile accessible
	 * @param bombable - Boolean, is tile bombable
	 */
	public void setProperties(String type, boolean accessible, boolean bombable){
		this.type = type;
		this.bombable = bombable;
		this.accessible = accessible;
	}

	/** returns weather tile accessible or not
	 *  isAccessable is identical but wrong spelled
	 * @return
	 */
	public boolean isAccessible() {
		return this.accessible;
	}

	/** returns weather tile accessible or not
	 *  isAccessable is identical but wrong spelled
	 * @return
	 */
	@Override
	public boolean isAccessable() {
		return this.accessible;
	}

	/** sets if tile is accessible or not
	 * @param a
	 */
	public void setAccessible(boolean a) {
		this.accessible = a;
	}

	/** checks if tile can be bombed
	 * @return
	 */
	public boolean isBombable() {
		return this.bombable;
	}

	/** sets if tile can be bombed
	 * @param b
	 */
	public void setBombable(boolean b) {
		this.bombable = b;
	}

	/** checks if tile is visible
	 * @return
	 */
	public boolean isVisible() {
		return visible;
	}

	/** sets if tile is visible
	 * @param visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/** returns speedfactor for tile
	 * @return
	 */
	public double getSpeedFactor() {
		return speedFactor;
	}

	@Override
	public void updateWithCollisionCheck(Collection<GameObject> gameObjects) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean collide(GameObject gameobject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Rectangle getCollisionRectangle() {
		return new Rectangle(position.getLocation().x - Constants.HALF_TILE,
				position.getLocation().y - Constants.HALF_TILE,
				Constants.TILE_BORDER, Constants.TILE_BORDER);
	}

	public void die() {
		active = false;
		if(bombable){
			accessible = true;
		}
	}

	public void destroy() {
		die();
	}

}
