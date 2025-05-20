package dsachat.server;

/**
 * Just a test class to separately start a server
 * @author bernshausen
 *
 */
public class ServerMain {

	/**
	 * Just start the server and create the listener
	 * @param args no args required
	 */
	public static void main(String[] args) {
		Handler h = new Handler();
		new CServer(20001,h).start();
	}
}
