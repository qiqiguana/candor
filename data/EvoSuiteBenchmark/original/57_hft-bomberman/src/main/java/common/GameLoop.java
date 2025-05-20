package common;

import org.apache.log4j.Logger;


/**
 * 
 * @author Andi, Steffen
 *
 */
public abstract class GameLoop extends Thread {

	private static final Logger logger = Logger.getLogger(GameLoop.class);
	protected long delay = 16;
	protected GameModel gameModel;
	boolean gameOver = false;
	private int framesAfterGameOver = 0;
	protected int winnerId;
	private static int MAX_SKIPS = 5;

	/**
	 * @param gameModel
	 */
	public GameLoop(GameModel gameModel) {
		super();
		this.gameModel = gameModel;
	}

	/**
	 * Updates the game model including pre- and post-update processing.
	 * The game loop contains a mechanism to approximate a desired frame rate.
	 */
	@Override
	public void run() {
		long start, end, diff, sleepTime, overSleepTime = 0;
		int behind = 0; // amount of ms by which the desired delay was exceeded

		start = System.currentTimeMillis();

		while (!isInterrupted() && !gameOver) {
			// long start = System.currentTimeMillis();
			preUpdate();
			gameModel.update();
			postUpdate();
			checkGameOver();
			// long stop = System.currentTimeMillis() - start;
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				break;
			}

			end = System.currentTimeMillis();
			diff = end - start;
			sleepTime = delay - diff - overSleepTime;

			if (sleepTime > 0) { // frame was "too fast" sleep in order to match desired frame rate
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException ex) {
					break;
				}
				overSleepTime = System.currentTimeMillis() - end - sleepTime;
			} else { // frame was ok or too long
				behind -= sleepTime;
				overSleepTime = 0;
			}

			// frame ends here, the rest is just "catching up" with the desired frame rate
			start = System.currentTimeMillis();

			// if the game loop is "behind", update without rendering
			int skips = 0;
			while ((behind > delay) && (skips < MAX_SKIPS)) {
				behind -= delay;
				gameModel.update();
				skips++;
			}
		}
		
		doPostGameProcessing();
	}

	private void checkGameOver() {
		if ((winnerId = gameModel.checkForWinner() )!= -1) {
			// need a couple more frames after game over to render death
			if (framesAfterGameOver < 10) {
				framesAfterGameOver++;
			} else {
				gameOver = true;
			}
		}
	}
	
	protected abstract void doPostGameProcessing();

	/**
	 * Override to add functionality that should be performed after updating the
	 * model.
	 */
	protected void postUpdate() {
	}

	/**
	 * Override to add functionality that should be performed before updating
	 * the model.
	 */
	protected void preUpdate() {
	}

}
