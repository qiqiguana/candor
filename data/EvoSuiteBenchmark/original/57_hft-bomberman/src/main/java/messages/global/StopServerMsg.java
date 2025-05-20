package messages.global;

import org.apache.log4j.Logger;

import client.BomberClient;
import server.BomberServer;
import server.ClientInfo;

/**
 * This message is sent to the server to shut it down. This will never happen
 * due to an event triggered in the game but only due to a message sent from
 * outside of the game. This mechanism is used to automatically shut the old
 * server down after a fresh build and start a new one.
 * 
 * @author Bjoern, Steffen
 * 
 */
public class StopServerMsg implements GlobalClientMsg {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(BomberServer bomberSrv, ClientInfo sender) {
		System.out.println("Server will stop...");
		bomberSrv.stopServer();
	}

}
