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
 * @author Bjoern, Steffen, Andi
 */
public class NewBombMsg implements RoundClientMsg, RoundServerMsg {
	private static final Logger logger = Logger.getLogger(NewBombMsg.class);

	/**
	 * ID of the bomb
	 * 
	 */
	private int bombID;

	/**
	 * position of the client
	 * 
	 */
	private Point position;

	private int planterId;

	private int bombDiameter;

	private boolean start;

	/**
	 * @param position
	 * @param moveVector
	 * @param bombID
	 * @param planterId
	 * @param bombDiameter
	 */
	public NewBombMsg(Point position, int bombID,
			int planterId, int bombDiameter, boolean start) {
		super();
		this.position = position;
		this.bombID = bombID;
		this.planterId = planterId;
		this.bombDiameter = bombDiameter;
		this.start = start;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(ServerGameRound round, ClientInfo sender) {
		logger.info("New bomb at: " + position.x + ", " + position.y);
		ServerBomb bomb = new ServerBomb(position, round.getSession());
		bomb.setId(bombID);
		bomb.setPlanterId(planterId);
		bomb.setDiameter(bombDiameter);
		bomb.setRemote(true);
		bomb.setStopped(start);
		round.addGameObject(bombID, bomb);
		round.getSession().multicastMsg(this, sender);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(ClientGameRound round) {
		logger.info("New bomb at: " + position.x + ", " + position.y);
		GameObject planter = round.getGameObjectById(planterId);
		ClientBomb bomb = new ClientBomb(position, planter);
		bomb.setId(bombID);
		bomb.setPlanterId(planterId);
		bomb.setDiameter(bombDiameter);
		bomb.setRemote(true);
		round.addBomb(bombID, bomb);
	}
}