package server.network;

import java.net.Socket;
import java.util.Observable;

import common.network.MsgSender;
/**
 * This class is a specialization of MsgSender for the server side.
 * 
 * @author Steffen
 * @see MsgSender
 */
public class ServerMsgSender extends MsgSender {

	public ServerMsgSender(Socket socket) {
		super(socket);
	}

	@Override
	public void update(Observable observable, Object obj) {
		
	}

}
