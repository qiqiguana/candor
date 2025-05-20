package messages.round;

import common.Player;

import client.ClientGameRound;
import client.ClientPlayer;

public class TileHitPlayerMsg implements RoundServerMsg {
	private int playerID;

	/**
	 * @param playerID
	 */
	public TileHitPlayerMsg(int playerID) {
		super();
		this.playerID = playerID;
	}

	@Override
	public void execute(ClientGameRound round) {
		ClientPlayer player = (ClientPlayer) round.getGameObjectById(playerID);
		player.die();
	}
}
