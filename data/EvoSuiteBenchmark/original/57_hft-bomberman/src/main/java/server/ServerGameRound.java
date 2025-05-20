package server;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map.Entry;

import messages.round.CountdownMsg;
import messages.round.NewTileMsg;
import messages.round.RoundStateMsg;
import messages.round.TileHitPlayerMsg;
import messages.session.InitializeRoundMsg;
import messages.session.PlayerInfo;

import org.apache.log4j.Logger;

import common.Constants;
import common.GameLoop;
import common.GameObject;
import common.GameRound;
import common.Map;
import common.Player;
import common.PowerUp;
import common.Tile;

/**
 * 
 * @author Steffen, Bjoern, Tobias
 * 
 */
public class ServerGameRound extends GameRound {

	private static final Logger logger = Logger
			.getLogger(ServerGameRound.class);
	private Vector<ClientInfo> participants;
	private ServerGameSession session;
	private ServerGameModel gameModel;
	private String mapName;
	private GameLoop gameLoop;
	protected HashMap<String, Integer> roundScores = new HashMap<String, Integer>();

	public HashMap<String, Integer> getRoundScores() {
		return roundScores;
	}

	public ServerGameRound(ServerGameSession session,
			Vector<ClientInfo> participants, String mapName) {
		this.session = session;
		this.participants = participants;
		this.mapName = mapName;
		initializeGameWorld();
	}

	/**
	 * Initializes the game world for this round. This includes reading the map,
	 * creating players and assigning them start positions as well as sending
	 * the InitializeRoundMsg to all participants.
	 */
	private void initializeGameWorld() {
		logger.info("Initializing Game World!");
		gameModel = new ServerGameModel(this);

		// load map
		Map map = new Map(Constants.MAP_PATH + mapName);
		for (Tile tile : map) {
			gameModel.addGameObject(tile.getId(), tile);
		}

		// load powerups
		for (PowerUp powerUp : map.powerupiterator) {
			ServerPowerUp serverPowerUp = new ServerPowerUp(powerUp
					.getPosition(), session);
			serverPowerUp.setType(powerUp.getType());
			serverPowerUp.setId(powerUp.getId());
			gameModel.addGameObject(serverPowerUp.getId(), serverPowerUp);
		}

		// create players
		Vector<PlayerInfo> players = new Vector<PlayerInfo>();
		int i = 1;
		for (ClientInfo participant : participants) {
			Point position = map.getStartPoint(i);
			int id = participant.getIdOffset();
			ServerPlayer player = new ServerPlayer(position);
			player.setName(participant.getName());
			PlayerInfo pi = new PlayerInfo(id, position, player.getName());
			player.setId(id);
			player.setRemote(true);
			player.resetSpeed();
			players.add(pi);
			gameModel.addGameObject(id, player);
			i++;
		}

		session.broadcastMsg(new InitializeRoundMsg(players, map));

		gameLoop = new ServerGameLoop(gameModel, this);
	}

	/**
	 * Starts the game round preceeded by a countdown.
	 */
	public void begin() {
		initRoundScores();
		countDown();

		logger.info("Starting Game!");
		gameLoop.start();

		session.broadcastMsg(new RoundStateMsg(RoundStateMsg.START_GAME_ROUND));
	}

	/**
	 * Sends a ready message to all clients and then one message for each second
	 * in the countdown. This provides for better synchronization.
	 */
	private void countDown() {
		logger.info("Starting Countdown!");

		session.broadcastMsg(new RoundStateMsg(RoundStateMsg.READY));

		for (int i = Constants.COUNTDOWN; i >= 0; i--) {
			try {
				session.broadcastMsg(new CountdownMsg(i));
				logger.info("Countdown: " + i);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("Countdown was interrupted!", e);
			}
		}
	}

	public ServerGameSession getSession() {
		return session;
	}

	public void addGameObject(int id, GameObject gameObject) {
		gameModel.addGameObject(id, gameObject);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doPostRoundProcessing() {
		gameModel.setTime(Constants.time);
		session.doPostRoundProcessing();
	}

	public void initRoundScores() {
		for (int i = 0; i < participants.size(); i++) {
			int participantId = participants.get(i).getIdOffset();
			String name = participants.get(i).getName();
			Player actPlayer = (Player) gameModel
					.getGameObjectById(participantId);
			scores.put(participantId, 0);
			roundScores.put(name, 0);

		}

	}

	public GameObject getGameObject(int id) {
		return gameModel.getGameObjectById(id);
	}

	public void addRoundScore(int playerid, int score) {

		int oldscore = 0;
		if (scores.containsKey(playerid)) {
			oldscore = scores.get(playerid);
		}

		
		Player player = (Player) gameModel.getGameObjectById(playerid);
		if (player != null) {

			roundScores.put(player.getName(), score + oldscore);
		} else {
			logger.info("ServerGameRound#addRoundSCore --> Player ist null");
		}
		scores.put(playerid, score + oldscore);
		logger.info("servergameround addscore tobias: name id " + playerid
				+ " score " + score + " new score " + (oldscore + score));
	}

	@Override
	public void createNewTile(Point point, int id, String type,
			boolean bombable, boolean accessible, boolean active) {
		Tile t = new Tile(point);
		t.setId(id);
		t.setBombable(bombable);
		if (active) {
			t.setActive();
		} else {
			t.setInactive();
		}
		t.setType(type);
		t.setAccessible(accessible);
		gameModel.addGameObject(t.getId(), t);
		NewTileMsg msg = new NewTileMsg(t.getId(), t.getPosition(),
				t.getType(), bombable, accessible, active);
		session.broadcastMsg(msg);
		Rectangle rect = t.getCollisionRectangle();
		for (Player player : gameModel.getPlayers()) {
			if (rect.intersects(player.getCollisionRectangle())) {
				logger.info("Hit player: " + player.getId() + " at "
						+ player.getPosition());
				TileHitPlayerMsg msg2 = new TileHitPlayerMsg(player.getId());
				session.broadcastMsg(msg2);
				player.setInactive();
			}
		}
	}

}
