package dsachat.share;

/**
 * this Listener handles the events from a connection endpoint
 * @author bernshausen
 *
 */
public interface InputListener {
	
	/**
	 * Handle the data of the event
	 * @param e the event
	 */
	public void handleInput(InputEvent e);
	
}
