/**
 * 
 */
package client;

import org.apache.log4j.Logger;

import client.controller.LocalControl;
import client.view.GameView;

import common.GameLoop;
import common.GameModel;

/**
 * The client specific implemenation of the GameLoop class.
 * It offers functionality to control the rendering of a GameView.
 * @author Andi
 *
 */
public class ClientGameLoop extends GameLoop {

	private GameView gameView;
	LocalControl localControl;
	private final ClientGameRound round;
	
	private static final Logger logger = Logger.getLogger(ClientGameLoop.class);

	/**
	 * @param round The GameRound that is associated with this loop.
	 * @param gameModel The GameModel that simulates the game.
	 * @param gameView The GameView that renders the game.
	 */
	public ClientGameLoop(ClientGameRound round, GameModel gameModel, GameView gameView) {
		super(gameModel);
		this.round = round;
		this.gameView = gameView;
	}
	
	
	/**
	 * Returns the GameView object.
	 * @return the GameView object.
	 */
	public GameView getGameView()
	{
		return gameView;
	}
	
	/**
	 * Processes the user input before the model is updated.
	 * @see common.GameLoop#preUpdate()
	 */
	@Override
	protected void preUpdate() {
		localControl.processInput();
	}

	/**
	 * Renders the associated view after the model was updated.
	 *  @see common.GameLoop#postUpdate()
	 */
	@Override
	protected void postUpdate() {
		gameView.paint();
	}
	
	/**
	 * Sets the local control that is used to control the local player.
	 * @param localControl
	 */
	public void setLocalControl(LocalControl localControl) {
		this.localControl = localControl;
	}


	/** 
	 * Triggers the end of this game round.
	 * @see common.GameLoop#doPostGameProcessing()
	 */
	@Override
	protected void doPostGameProcessing() {
		logger.info("game over");
		round.doPostRoundProcessing();
	}
}
