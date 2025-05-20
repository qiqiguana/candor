package common.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import messages.Message;

import org.apache.log4j.Logger;

/**
 * The abstact class MsgReceiver waits for incoming messages in a loop and then
 * calls the processMsg-method for each received message.
 * 
 * @author Steffen
 */
public abstract class MsgReceiver extends Thread {

	private static final Logger logger = Logger.getLogger(MsgReceiver.class);
	protected ObjectInputStream in;

	/**
	 * Creates a new MsgReceiver.
	 * 
	 * @param socket The Socket the client is communicating with.
	 * TODO it may be better to throw the exception, so the client can be notified
	 */
	protected MsgReceiver(Socket socket) {
		try {
			this.in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			logger.fatal("Couldn't create ObjectInputStream!", e);
		}
	}

	/**
	 * Waits for incoming messages in a loop and calls processMsg for every valid msg.
	 */
	public void run() {
		try { // for IOException -> terminate loop (connection lost)
			while (!isInterrupted()) {
				try { // for ClassNotFoundException -> just read next Object
					Object obj = in.readObject();

					if (!Message.class.isInstance(obj)) {
						logger.warn("Received invalid message! Ignoring...");
						continue;
					}

					Message m = (Message) obj;
					if (m == null) {// InputStream has been closed
						break;
					} else {
						logger.debug("received: " + m);
						processMsg(m);
					}
				} catch (ClassNotFoundException e) {
					logger.info("Class not found", e);
				}
			}
		} catch (IOException e) {
			handleConnectionLoss(e);
		}
	}
	
	/**
	 * Takes appropriate action when the connection has been lost.
	 */
	protected abstract void handleConnectionLoss(Exception e);

	/**
	 * Processes the message given as a parameter.
	 * 
	 * @param msg The message to be processed.
	 */
	protected abstract void processMsg(Message msg);

}
