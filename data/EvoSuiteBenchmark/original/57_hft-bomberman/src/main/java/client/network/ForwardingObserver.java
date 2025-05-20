/**
 * 
 */
package client.network;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import messages.Message;
import messages.round.BombMoveMsg;
import messages.round.PlayerStateMsg;
import messages.round.NewBombMsg;
import client.BomberClient;
import client.ClientBomb;
import client.ClientGameRound;

import common.Bomb;
import common.Player;
import common.Actor;

/**
 * An observer implementation that will process notifications and forward them
 * to the server.
 * 
 * @author Andi, Björn
 * 
 */
public class ForwardingObserver implements Observer {

	/**
	 * The BomberClient that this ForwardingObserver belongs to.
	 */
	private BomberClient bomberClient;
	private final ClientGameRound clientGameRound;

	/**
	 * Creates a new ForwardingOberver belonging to the given BomberClient.
	 * 
	 * @param bomberClient
	 *            The BomberClient that will be used to send the messages.
	 * @param clientGameRound
	 */
	public ForwardingObserver(BomberClient bomberClient,
			ClientGameRound clientGameRound) {
		super();
		this.bomberClient = bomberClient;
		this.clientGameRound = clientGameRound;
	}

	/**
	 * Called when an observable has requested an update. Possible update types
	 * are:
	 * <ul>
	 * <li>Bomb has been planted
	 * <li>Bomb has changed his position
	 * <li>Player has changed his position
	 * </ul>
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (Player.class.isInstance(o)) {
			Player p = (Player) o;
			if (ClientBomb.class.isInstance(arg)) {
				ClientBomb newBomb = (ClientBomb) arg;
				clientGameRound.snapToGrid(newBomb);
				clientGameRound.addBomb(newBomb.getId(), newBomb);
				NewBombMsg msg = new NewBombMsg(newBomb.getPosition(), newBomb
						.getId(), p.getId(), newBomb.getDiameter(), newBomb
						.isStopped());
				bomberClient.sendMsg(msg);
			} else {
				PlayerStateMsg msg = new PlayerStateMsg(p.getId(), p
						.getPosition(), p.getMoveVector());
				bomberClient.sendMsg(msg);
			}
		}
		if (ClientBomb.class.isInstance(o)) {
			ClientBomb bomb = (ClientBomb) o;
			if (!bomb.isRemote()) {
				if (bomb.getMoveVector().x == 0 && bomb.getMoveVector().y == 0) {
					bomb.setStopped(true);
				}
				int[] position = new int[2];
				position[0] = bomb.getPosition().x;
				position[1] = bomb.getPosition().y;
				int[] moveVector = new int[2];
				moveVector[0] = bomb.getMoveVector().x;
				moveVector[1] = bomb.getMoveVector().y;
				BombMoveMsg msg = new BombMoveMsg(bomb.getId(), position,
						moveVector, bomb.isStopped());
				bomberClient.sendMsg(msg);
			}
		}
	}
}
