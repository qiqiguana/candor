/**
 * 
 */
package client;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import org.apache.log4j.Logger;

import sound.SoundPlayer;

import common.Bomb;
import common.GameObject;

/**
 * @author andi
 * 
 */
public class ClientBomb extends Bomb {

	private Rectangle spreadBox;

	private static final Logger logger = Logger.getLogger(ClientBomb.class);

	/**
	 * Creates a new ClientBomb with the given parameters.
	 * 
	 * @param position
	 *            The position of the bomb.
	 * @param gameObject
	 *            The Player that has planted this bomb.
	 */
	public ClientBomb(Point position, GameObject gameObject) {
		super(position, gameObject);
	}

	/**
	 * Sets the spread.
	 * 
	 * @param spread
	 *            the spread to set [up,down,left,right]
	 */
	public void setSpread(int[] spread) {
		this.spreadBox = new Rectangle(spread[2], spread[0], spread[3]-spread[2], spread[1]-spread[0]);
		logger.info(spreadBox);
	}

	/**
	 * @return
	 */
	public Shape getSpreadBox() {
		return spreadBox;
	}

	/* (non-Javadoc)
	 * @see common.Bomb#explode()
	 */
	@Override
	public void explode() {
		super.explode();
		SoundPlayer.getInstance().explosion1();
	}
	
	

}
