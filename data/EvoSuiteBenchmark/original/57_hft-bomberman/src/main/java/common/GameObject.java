package common;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

public abstract class GameObject extends Observable implements Serializable {

	protected Point position;
	protected int id;
	protected Vector<Observer> observers;
	protected boolean active = true;
	protected boolean visible = true;

	protected GameObject(Point position) {
		this.position = position;
	}

	public abstract void update();

	public abstract Rectangle getCollisionRectangle();

	public abstract void updateWithCollisionCheck(
			Collection<GameObject> gameObjects);

	public abstract boolean collide(GameObject gameobject);

	public abstract boolean isAccessable();

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the position
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Point position) {
		this.position.setLocation(position);
	}

	/**
	 * Returns the active status
	 * 
	 * @author Björn
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the object inactive
	 * 
	 * @author Björn
	 */
	public void setInactive() {
		this.active = false;
	}

	/**
	 * Sets the object active
	 * 
	 * @author Björn
	 */
	public void setActive() {
		this.active = true;
	}
	
	public void setVisible() {
		this.visible = true;
	}
	
	public void setInvisible() {
		this.visible = false;
	}
	
	public boolean isVisible() {
		return visible;
	}
}
