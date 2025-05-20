package client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.ImageIcon;

import messages.session.PlayerInfo;
import messages.session.PlayerLeftMsg;

import org.apache.log4j.Logger;

import client.gui.StartFrame;
import client.view.GameCanvas;

import common.GameSession;
import common.Map;
import common.Player;

import db.DBGameUser;

/**
 * Represents a session on the client. A session consists of several game
 * rounds.
 * 
 * @author andi, Bj�rn, daniel
 * 
 */
public class ClientGameSession extends GameSession {

	private int id;
	private Set<ClientPlayer> players = new HashSet<ClientPlayer>();

	private HashMap<Integer, String> playerNames;
	private ClientGameRound currentRound;
	private Logger logger = Logger.getLogger(ClientGameSession.class);

	/**
	 * Creates a new game session from the arguments.
	 * 
	 * @param name
	 *            The name of this session.
	 * @param maps
	 *            A list of map names that will be used for this session.
	 * @param nrOfPlayers
	 *            The number of players that will participate
	 * @param totalRounds
	 *            The number of rounds to play
	 * @param idOffset
	 *            The id offset for this session.
	 */
	public ClientGameSession(String name, List<String> maps,
			ImageIcon mapPreview, int nrOfPlayers, int totalRounds, int idOffset) {
		super(name, maps, mapPreview, nrOfPlayers, totalRounds);
		this.id = idOffset;
		this.playerNames = new HashMap<Integer, String>();
	}

	/**
	 * Starts the active game round.
	 * 
	 * @see common.GameSession#beginRound()
	 */
	@Override
	public void beginRound() {
		currentRound.start();
	}

	/**
	 * Return the next unqiue id.
	 * 
	 * @return a unique id.
	 */
	public int getNextId() {
		return ++id;
	}

	/**
	 * Adds a player to this session.
	 * 
	 * @param player
	 */
	public void addPlayer(ClientPlayer player) {
		players.add(player);
	}

	/**
	 * Removes a player from this session.
	 * 
	 * @param player
	 */
	public void removePlayer(ClientPlayer player) {
		players.remove(player);
	}

	/**
	 * Cleans up after a round has finished. This involves calculation of
	 * scores.
	 * 
	 * @see common.GameSession#doPostRoundProcessing()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see common.GameSession#doPostRoundProcessing()
	 */
	@Override
	public void doPostRoundProcessing() {
		logger.info("in ClientGameSession#doPostRoundProcessing()");
		StartFrame.getInstance().removeChatMessageSession();

		// register user with score for round highscore
		logger.info("before nameforscore");

		currentRound.endRound();

		for (ClientPlayer player : players) {

			player.resetSpeed();
			player.resetBowlBombs();
			player.resetMaxBombs();
			player.resetBombDiameter();
		}

		// currentRound.terminateRound();
		if (currentRoundNr < totalRounds && playerNames.size() > 1) {
			currentRound.setStatus("Waiting");
			StartFrame.getInstance().showHighscoreLobby();
			StartFrame.getInstance().removeSessionHighScoreData();
		} else {

			doPostSessionProcessing();
		}

	}

	/**
	 * 
	 * @see common.GameSession#doPostSessionProcessing()
	 */
	@Override
	protected void doPostSessionProcessing() {
		logger.info("in ClientGameSession#doPostSessionProcessing()");
		currentRound = null;
		BomberClient.getInstance().closeSessionAndShowHighscores();
		StartFrame.getInstance().jPanelHighscoreLobby.jButtonNextRound
				.setVisible(false);
		StartFrame.getInstance().jPanelHighscoreLobby.jLabelGameOver
				.setVisible(true);
		StartFrame.getInstance().jPanelHighscoreLobby.repaint();
		
	}

	/**
	 * Returns the active round.
	 * 
	 * @return the active round.
	 */
	public ClientGameRound getCurrentRound() {
		return currentRound;
	}

	/**
	 * Creates a new game round and configures it. After this method returns the
	 * newly created round will be stored in currentRound and the round number
	 * will be increased by 1.
	 * 
	 * @param players
	 *            the players that participate in this round.
	 * @param map
	 *            The map that is used in this round.
	 */
	public void initializeRound(Vector<PlayerInfo> players, Map map) {
		GameCanvas gameCanvas = StartFrame.getInstance().getGameCanvas();
		ClientGameRound round = new ClientGameRound(this, gameCanvas);
		round.setMap(map);
		for (PlayerInfo pi : players) {
			ClientPlayer player = new ClientPlayer(pi.getPosition(), pi.getId());
			player.setRemote(true);
			player.setName(pi.getName());
			round.addPlayer(player.getId(), player);
			if (pi.getId() == id) {
				logger.debug("Attaching LocalControl to ClientPlayer #"
						+ player.getId());
				player.setRemote(false);
				round.setLocalPlayer(player);
			}
		}

		currentRound = round;
		currentRoundNr++;
	}

	/**
	 * Registers a player with the given id and name for this session.
	 * 
	 * @param playerId
	 *            The id that is used to identify the player.
	 * @param playerName
	 *            The human friendly identification of the player.
	 */
	public void join(int playerId, String playerName) {
		playerNames.put(playerId, playerName);
		logger.info(playerName + " has joined the game");

	}

	/**
	 * Removes the player identified by the given id from this session.
	 * 
	 * @param playerId
	 *            The id of the player that should be kicked.
	 */
	public void leave(int playerId) {
		logger.info(playerNames.get(id) + " is leaving the game right now");
		playerNames.remove(playerId);
	}

	/**
	 * @return user round scores within a session
	 */
	public DBGameUser[] getUserScores() {

		DBGameUser[] dbgu = new DBGameUser[players.size()];
		int i = 0;
		for (Player player : players) {
			DBGameUser dbguser = new DBGameUser();
			dbguser.setName(player.getName());
			dbguser.setScore(player.getRoundScore());
			dbgu[i++] = dbguser;

		}
		return dbgu;
	}

	/**
	 * @param idOffset
	 */
	public void setIdOffset(int idOffset) {
		id = idOffset;

	}

	/**
	 * @param sender
	 * @param msg
	 */
	public void addChat(String sender, String msg) {
		StartFrame.getInstance().addChatMessageSession(sender, msg);
	}

	public int getId() {
		return id;
	}

	/**
	 * 
	 */
	public void leave() {
		logger.info("player left msg sent");
		BomberClient.getInstance().sendMsg(new PlayerLeftMsg());
	}

	public Set<ClientPlayer> getPlayers() {
		return players;
	}

	public java.util.Map<Integer, String> getPlayerNames() {
		return playerNames;
	}

	public void setSessionScore(HashMap<String, Integer> sessionscore) {
		StartFrame.getInstance().updateSessionScoreData(sessionscore);

	}
}
