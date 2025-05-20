package messages.session;

import java.awt.Point;
import java.io.Serializable;

/**
 * Data structure used to transmit info about players when initializing a round.
 * 
 * @author Steffen
 */
public class PlayerInfo implements Serializable {
	private int id;
	private Point position;
	private String name;

	public PlayerInfo(int id, Point position, String name) {
		super();
		this.id = id;
		this.position = position;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public Point getPosition() {
		return position;
	}

	public String getName() {
		return name;
	}
	
}
