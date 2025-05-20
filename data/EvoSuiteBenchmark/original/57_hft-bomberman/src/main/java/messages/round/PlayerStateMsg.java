package messages.round;

import java.awt.Point;

import org.apache.log4j.Logger;

import common.Player;

import server.ClientInfo;
import server.ServerGameLoop;
import server.ServerGameRound;
import client.ClientGameRound;

/**
 * Message, which sends the position and direction of a player
 * 
 * @author Bjoern, Steffen, Andi
 */
public class PlayerStateMsg implements RoundClientMsg, RoundServerMsg {

	private static Logger logger = Logger.getLogger(PlayerStateMsg.class);

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
	 * Constructor
	 * 
	 * @param positio
	 * @param moveVector
	 */
	public PlayerStateMsg(int gameObjectId, Point position, Point moveVector) {
		super();
		this.gameObjectId = gameObjectId;
		this.moveVector = new int[] { moveVector.x, moveVector.y };
		this.position = new int[] { position.x, position.y };
	}

	private Point getPosition() {
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
		if (round != null) {
			// forward message to all clients
			round.getSession().multicastMsg(this, sender);

			// update corresponding player on server
			Player player = (Player) round.getGameObject(gameObjectId);
			if (player != null) {
				player.updateMovement(getPosition());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(ClientGameRound round) {
		if (round != null) {
			Player player = (Player) round.getGameObjectById(gameObjectId);
			if (player != null) {
				player.updateMovement(getPosition());
			}
		}
	}
}
