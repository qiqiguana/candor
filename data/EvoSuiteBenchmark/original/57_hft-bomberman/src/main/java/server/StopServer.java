package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import messages.Message;
import messages.global.StopServerMsg;

/**
 * Sends a Message to the Server, that he should closed
 * 
 * @author Björn
 * 
 */
public class StopServer {
	private ObjectOutputStream out;
	private Socket server;
	private String serverName = "193.196.141.182";
	private int serverPort = 6666;

	/**
	 * sends a message
	 * 
	 * @param msg
	 */
	public void sendMsg(Message msg) {
		try {
			out.writeObject(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Connect to the Server
	 * 
	 */
	public boolean connectToSrv() {
		try {

			//System.out.println("Connecting...");
			System.out.println("Establishing a connection ");
			server = new Socket(serverName, serverPort);
			out = new ObjectOutputStream(server.getOutputStream());

			System.out.println("connected to " + serverName);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block

			// e.printStackTrace();
			System.out.println("Connection failed");
			return false;
		}
	}

	/**
	 * Connect to the server, and send the message
	 * 
	 * @param stop
	 */
	public void callingStopServer(StopServer stop) {
		if (stop.connectToSrv()) {
			System.out.println("stopping Server...");
			StopServerMsg msg = new StopServerMsg();
			stop.sendMsg(msg);
			System.out.println("Server stopped");
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StopServer stop = new StopServer();
		stop.callingStopServer(stop);
	}

}
