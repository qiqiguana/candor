package client.network;

import java.net.Socket;
import java.util.Observable;

import common.network.MsgSender;

/**
 * The message sender used by the client.
 * @see MsgSender
 * @author andi
 *
 */
public class ClientMsgSender extends MsgSender {

	
	/**
	 * Creates a new ClientMsgSender that uses the given port to send
	 * its messages.
	 * @param socket The socket that will be used to send the messages via an ObjectOutputStream.
	 */
	public ClientMsgSender(Socket socket) {
		super(socket);
	}

	/**
	 * Unused
	 * @deprecated 
	 */
	@Override
	public void update(Observable observable, Object obj) {
		// TODO Auto-generated method stub
	}

}
