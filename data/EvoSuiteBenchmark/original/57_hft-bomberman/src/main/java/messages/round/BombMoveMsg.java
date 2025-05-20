package messages.round;

import java.awt.Point;

import org.apache.log4j.Logger;

import common.Bomb;

import server.ClientInfo;
import server.ServerBomb;
import server.ServerGameRound;
import client.ClientBomb;
import client.ClientGameRound;

/**
 * Message, which sends the position and direction of a bomb
 * 
 * @author Bjoern, Steffen
 */
public class BombMoveMsg implements RoundClientMsg, RoundServerMsg {

	private static Logger logger = Logger.getLogger(BombMoveMsg.class);

	/**
	 * position
	 */
	private int[] position;

	/**
	 * direction
	 */
	private int[] moveVector;

	/**
	 * playerID
	 */
	private int gameObjectId;

	/**
	 * wheater move has been stopped or not
	 */
	private boolean stopped;

	/**
	 * Constructor
	 * 
	 * @param positio
	 * @param moveVector
	 */
	public BombMoveMsg(int gameObjectId, int[] position, int[] moveVector,
			boolean stopped) {
		super();
		this.gameObjectId = gameObjectId;
		this.stopped = stopped;
		this.position = position;
		this.moveVector = moveVector;
	}

	public Point getPosition() {
		return new Point(position[0], position[1]);
	}

	private Point getMoveVector() {
		return new Point(moveVector[0], moveVector[1]);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(ServerGameRound round, ClientInfo sender) {
		// forward message to all clients
		round.getSession().multicastMsg(this, sender);

		// update corresponding bomb on server
		if (ServerBomb.class.isInstance(round.getGameObject(gameObjectId))) {
			ServerBomb bomb = (ServerBomb) round.getGameObject(gameObjectId);
			bomb.setStopped(stopped);
			bomb.updateMovement(getPosition());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(ClientGameRound round) {
		if (ClientBomb.class.isInstance(round.getGameObjectById(gameObjectId))) {
			ClientBomb bomb = (ClientBomb) round
					.getGameObjectById(gameObjectId);
			bomb.setStopped(stopped);
			bomb.updateMovement(getPosition());
		}
	}
}
