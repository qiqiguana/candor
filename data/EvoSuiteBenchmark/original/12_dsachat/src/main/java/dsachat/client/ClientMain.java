package dsachat.client;

import dsachat.client.gui.MainFrame;

/**
 * This is just for starting the client separately
 * @author bernshausen
 *
 */
public class ClientMain {

	/**
	 * Start the client
	 * @param args no arguments.
	 */
	public static void main(String argv[]) {
		MainFrame mf = new MainFrame();
		mf.setVisible(true);
    }

}
