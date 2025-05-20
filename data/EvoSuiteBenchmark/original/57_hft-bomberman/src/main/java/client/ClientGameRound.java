/**
 * 
 */
package client;

import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import org.apache.log4j.Logger;

import client.controller.LocalControl;
import client.gui.StartFrame;
import client.network.ForwardingObserver;
import client.view.BombView;
import client.view.GameCanvas;
import client.view.GameView;
import client.view.GfxFactory;
import client.view.OffGamePainter;
import client.view.PlayerView;
import client.view.PowerUpView;
import client.view.StatusView;
import client.view.TileView;

import common.Bomb;
import common.Constants;
import common.GameModel;
import common.GameObject;
import common.GameRound;
import common.Map;
import common.Player;
import common.PowerUp;
import common.Tile;

/**
 * Represents a game on the client side. This class serves as a connection
 * between the model, view and game loop.
 * 
 * @author Andi, Björn
 * 
 */
public class ClientGameRound extends GameRound implements Observer {

	/**
	 * The GameModel instance for this game.
	 */
	private GameModel model;

	/**
	 * The GameView instance for this game.
	 */
	private GameView view;

	/**
	 * The GameLoop instance for this game.
	 */
	private ClientGameLoop gameLoop;

	/**
	 * The GfxFactory instance for this game.
	 */
	private GfxFactory gfxFactory;

	private static final Logger logger = Logger.getLogger(ClientGameRound.class);

	private Player localPlayer;

	private final ClientGameSession session;

	private Thread offGamePainter;

	private final GameCanvas gameCanvas;

	private ForwardingObserver forwardingObserver;

	/**
	 * Creates a new game that will be painted on the passed GameCanvas object.
	 * When this constructor finishes, all necessary game objects will be
	 * initialized, but they will lack player and map objects.
	 * 
	 * @param gameCanvas
	 *            The canvas that will be used for painting the view.
	 */
	public ClientGameRound(ClientGameSession session, GameCanvas gameCanvas) {
		this.session = session;
		this.gameCanvas = gameCanvas;
		model = new GameModel();
		view = new GameView(gameCanvas);
		gameLoop = new ClientGameLoop(this, model, view);
		gfxFactory = new GfxFactory();
		forwardingObserver = new ForwardingObserver(BomberClient.getInstance(), this);
		offGamePainter = new OffGamePainter(view);
		offGamePainter.start();
	}

	/**
	 * Adds a player to the game. Adds the player object to GameModel and
	 * creates a view object for the player and adds it to the GameView.
	 * 
	 * @param player
	 *            The player to add to this game.
	 */
	public void addPlayer(int id, ClientPlayer player) {
		player.addObserver(this);
		if (id == session.getId()) {
			player.addObserver(forwardingObserver);
			StatusView statusView = new StatusView(player, gfxFactory);
			view.addGameObjectView(statusView);
		}
		model.addGameObject(id, player);
		view.addGameObjectView(new PlayerView(player, gfxFactory));
	}

	public void addBomb(int id, Bomb bomb) {
		if (model.getGameObjectById(id) != null) {
			return;
		}
		bomb.addObserver(forwardingObserver);
		model.addGameObject(id, bomb);
		view.addGameObjectView(new BombView(bomb, gfxFactory));
	}

	/**
	 * Sets the map for this game. All tiles that are contained in the map are
	 * added to the GameModel. For every tile a TileView is created and added to
	 * the GameView.
	 * 
	 * @param map=
	 *            The map that will be used in this game.
	 */
	public void setMap(Map map) {
		Vector<Tile> tiles = new Vector<Tile>();
		String imageSet = map.getImageSet();
		for (Tile tile : map) {
			model.addGameObject(tile.getId(), tile);
			tiles.add(tile);
			gfxFactory.setTileSet(map.getImageSet());
			view.addGameObjectView(new TileView(tile, gfxFactory));
		}
		for (PowerUp powerUp : map.powerupiterator) {
			model.addGameObject(powerUp.getId(), powerUp);
			view.addGameObjectView(new PowerUpView(powerUp, gfxFactory));
		}
	}

	/**
	 * Starts this game by creating a new thread and running the game loop in
	 * it.
	 */
	public void start() {
		offGamePainter.interrupt();
		gameLoop.start();
	}

	/**
	 * Set the local control for this game. This control will be used to move
	 * the local player on the screen.
	 * 
	 * @param localControl
	 *            The local control to use.
	 */
	public void setLocalControl(LocalControl localControl) {
		gameLoop.setLocalControl(localControl);
	}

	/**
	 * Called when an observable has changed. Usages:
	 * <ul>
	 * <li>Player plants a bomb
	 * </ul>
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {

	}

	/**
	 * Searches and returns a GameObject by its id.
	 * 
	 * @param id
	 *            The id of the GameObject that should be returned.
	 * @return The matching GameObject or null if the id is unknown.
	 */
	public GameObject getGameObjectById(int id) {
		return model.getGameObjectById(id);
	}

	/**
	 * Updates the countdown for this GameRound. Visual and acoustic
	 * notifications will be triggered.
	 * 
	 * @param countdown
	 *            The current countdown.
	 */
	public void countdown(int countdown) {
		String overlayText;
		if (countdown == 0) {
			overlayText = "";
		} else {
			overlayText = String.valueOf(countdown);
		}
		view.setOverlayText(overlayText);
	}

	/**
	 * @param player
	 */
	public void setLocalPlayer(Player localPlayer) {
		this.localPlayer = localPlayer;
		LocalControl lControl = new LocalControl(localPlayer);
		gameCanvas.addKeyListener(lControl);
		gameLoop.setLocalControl(lControl);
	}



	/**
	 * Finishes this game round by calculating the score and notifying the
	 * session that the round has ended.
	 * 
	 * @see common.GameRound#doPostRoundProcessing()
	 */
	@Override
	public void doPostRoundProcessing() {
		setTime(Constants.time);

		logger.info("in ClientGameRound#doPostRoundProcessing()");

		StartFrame.getInstance().removeChatMessageSession();
		session.doPostRoundProcessing();
	}

	/**
	 * Snaps the location of the given bomb to the next intersection on the
	 * grid.
	 * 
	 * @param bomb
	 *            The bomb that should be relocated on the grid.
	 */
	public void snapToGrid(Bomb bomb) {
		model.snapToGrid(bomb);
	}

	/**
	 * Sets the status of the round. This is used to inform the user about
	 * what's happening.
	 * 
	 * @param string
	 *            The string that will be displayed to the user.
	 */
	public void setStatus(String string) {
		view.setOverlayText(string);
	}

	/**
	 * End the game round. The action will stop but the game will still be
	 * painted on the canvas.
	 */
	public void endRound() {

	}

	/**
	 * Terminate this round. This will stop the rendering of the game.
	 */
	public void terminateRound() {
		try {
			gameLoop.interrupt();
			view.resetGfx();
			session.doPostSessionProcessing();
			logger.info("interrupted game loop");
		} catch (RuntimeException e) {
			// whatever...
			logger.info("game loop already interrupted");
		}
	}

	/**
	 * Returns the actual time of this GameRound.
	 * 
	 * @return long - actual time
	 */
	public long getTime() {
		return model.getTime();
	}

	/**
	 * Sets the time of this GameRound.
	 * 
	 * @param time
	 *            the time, which should be set
	 */
	public void setTime(long time) {
		model.setTime(time);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void createNewTile(Point point, int id, String type, boolean bombable, boolean accessible, boolean active) {
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
		t.setVisible();
		model.addGameObject(t.getId(), t);
		view.addGameObjectView(new TileView(t, gfxFactory));
	}

	public void toggleDisplayNames() {
		view.toggleDisplayNames();
	}

	public void setRoundScore(HashMap<String, Integer> rndscore) {
		StartFrame.getInstance().updateRoundScoreData(rndscore);
		
	}
}
