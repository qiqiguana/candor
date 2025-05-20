/**
 * 
 */
package client.view;

/**
 * @author andi
 *
 */
public class OffGamePainter extends Thread {

	private GameView view;
	
	/**
	 * @param view
	 */
	public OffGamePainter(GameView view) {
		super();
		this.view = view;
	}



	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while(!interrupted()){
			view.paint();
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	
}
