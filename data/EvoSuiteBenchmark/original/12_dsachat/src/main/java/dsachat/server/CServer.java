package dsachat.server;

import java.io.IOException;
import java.net.ServerSocket;

import dsachat.server.net.SecServer;


/**
 * This class is waiting for connections and starts a new thread
 *  when a connection is accepted.
 * It also adds the Listener to that connection thread.
 * 
 * @author bernshausen
 *
 */
public class CServer extends Thread{
	
	private ServerSocket ss;
	private Handler l;
	
	/**
	 * Create a server and bind it to the specified port
	 * @param port the port to bind
	 * @param l the listener for the connections
	 */
	public CServer(int port, Handler l){
		try {
			ss = new ServerSocket(port);
			this.l = l;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * wait for new connections and start new Server-Threads
	 * Add the listener to the new connection.
	 */
	public void run(){
		System.out.println("Server started at port " + ss.getLocalPort());
		while(true){
			try {
				SecServer srv = new SecServer(ss.accept());
				srv.addInputListener(l);
				srv.start();
				l.addConnection(srv);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
