package messages.session;

import java.util.Vector;

import client.ClientGameSession;

import common.Map;

/**
 * Message, which contains all information about the actual game
 * 
 * @author Björn, Andi
 */
public class InitializeRoundMsg implements SessionServerMsg {

	private Vector<PlayerInfo> players;
	private Map map;

	public InitializeRoundMsg(Vector<PlayerInfo> players, Map map) {
		super();
		this.players = players;
		this.map = map;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(ClientGameSession session) {
		session.initializeRound(players, map);
	}
}
