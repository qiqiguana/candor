package messages.round;

import java.awt.Point;

import org.apache.log4j.Logger;

import common.Bomb;
import common.GameObject;

import server.ClientInfo;
import server.ServerBomb;
import server.ServerGameRound;
import client.ClientBomb;
import client.ClientGameRound;

/**
 * ClientBombMsg.java
 * 
 * Message to send, that a new bomb was placed
 * 
 * @author Bjoern, Steffen
 */
public class NewTileMsg implements RoundServerMsg {
	private static final Logger logger = Logger.getLogger(NewTileMsg.class);

	/**
	 * ID of the bomb
	 * 
	 */
	private int tileID;

	/**
	 * position of the client
	 * 
	 */
	private Point position;

	/**
	 * Type of the new tile
	 */
	private String type;

	/**
	 * Weather the tile is "bombable" or not
	 */
	private boolean bombable;

	/**
	 * Wheater the tile is accessible or not
	 */
	private boolean accessible;

	/**
	 * Wheater the tile is active or not
	 */
	private boolean active;

	/**
	 * @param tileID
	 * @param position
	 * @param bombable
	 * @param accessible
	 * @param active
	 * @param type
	 */
	public NewTileMsg(int tileID, Point position, String type,
			boolean bombable, boolean accessible, boolean active) {
		super();
		this.tileID = tileID;
		this.position = position;
		this.bombable = bombable;
		this.accessible = accessible;
		this.active = active;
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(ClientGameRound round) {
		round.createNewTile(position, tileID, type, bombable, accessible,
				active);
	}
}