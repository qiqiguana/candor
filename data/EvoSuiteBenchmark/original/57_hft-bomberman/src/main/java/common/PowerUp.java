/**
 * 
 */
package common;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.postgresql.core.VisibleBufferedInputStream;

/**
 * @author Björn
 * 
 */
public class PowerUp extends GameObject {
	/**
	 * @param position
	 *            the position of the powerup
	 */
	public PowerUp(Point position) {
		super(position);
		setActive();
		setInvisible();
	}

	/**
	 * Type of this powerup.
	 */
	private String type;

	@Override
	public boolean collide(GameObject gameobject) {
		return false;
	}

	@Override
	public Rectangle getCollisionRectangle() {
		return new Rectangle(getPosition().x - Constants.HALF_POWERUP,
				getPosition().y - Constants.HALF_POWERUP,
				Constants.POWERUP_BORDER, Constants.POWERUP_BORDER);
	}

	@Override
	public boolean isAccessable() {
		return true;
	}

	@Override
	public void update() {

	}

	@Override
	public void updateWithCollisionCheck(Collection<GameObject> gameObjects) {

	}

	/**
	 * Returns the type of this powerup as a string.
	 * 
	 * @return String type of powerup
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type of this powerup.
	 * 
	 * @param type,
	 *            which should be set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 */
	public void die() {
		active = true;
	}

	/**
	 * Returns the type of this powerup as a integer.
	 * 
	 * @return int type of powerup
	 * 
	 * missing powerup-types: bomb, poison, timer
	 */
	public int getTypeInt() {
		if (getType().equals("speed"))
			return 1;
		else if (getType().equals("bowl"))
			return 2;
		else if (getType().equals("longray"))
			return 3;
		else if (getType().equals("multibomb"))
			return 4;
		else
			return 0;
	}
}
