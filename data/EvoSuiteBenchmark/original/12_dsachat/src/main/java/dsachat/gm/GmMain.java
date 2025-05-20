package dsachat.gm;

import dsachat.gm.gui.GmFrame;

/**
 * Just start the gm view separately
 * @author bernshausen
 *
 */
public class GmMain {

	/**
	 * Start the game master view
	 * @param args no arguments are computed
	 */
	public static void main(String[] args) {
		GmFrame gf = new GmFrame();
		gf.setVisible(true);
	}

}
