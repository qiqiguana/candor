/**
 * 
 */
package client.network;

import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import messages.Message;
import messages.global.GlobalServerMsg;
import messages.round.RoundServerMsg;
import messages.session.SessionServerMsg;

import org.apache.log4j.Logger;

import client.BomberClient;
import client.ClientGameRound;
import client.ClientGameSession;
import client.gui.StartFrame;

import common.network.MsgReceiver;


/**
 * This is the central message receiver. All messages that are sent to the client
 * arrive here. The messages are forwarded to the objects that are
 * responsible for processing them.
 * @author Andi, Daniel
 * 
 */
public class ClientMsgReceiver extends MsgReceiver {

	private static final Logger logger = Logger.getLogger(ClientMsgReceiver.class);
	
	/**
	 * The BomberClient that is associated with this dispatcher.
	 */
	private final BomberClient bomberClient;
	protected final String error = "CONNECTION LOST";

	private boolean logout = false;

	/**
	 * Creates a new ClientMsgDispatcher that listens on the given socket.
	 * Messages are forwarded to the specified BomberClient and its children.
	 * @param socket The socket that will be listened on.
	 * @param bomberClient The BomberClient that the received messages belong to
	 */
	public ClientMsgReceiver(Socket socket, BomberClient bomberClient) {
		super(socket);
		this.bomberClient = bomberClient;
	}

	/**
	 * Inspects the given message and forwards it depending on its type.
	 * Messages are routed as follows:
	 * <ul>
	 * <li>GlobalServerMsg-> BomberClient
	 * <li>SessionServerMsg-> ClientGameSession
	 * <li>RoundServerMsg-> ClientGameRound
	 * </ul>
	 * @see common.MsgReceiver#processMsg(messages.Message)
	 */
	@Override
	protected void processMsg(Message msg) {
		if(GlobalServerMsg.class.isInstance(msg)){
			GlobalServerMsg globalMsg = (GlobalServerMsg) msg;
			globalMsg.execute(bomberClient);
		} else {
			ClientGameSession session = bomberClient.getCurrentSession();
			//have to check for null because otherwise delayed messages may cause NullPointer
			if (session != null) {
				if(SessionServerMsg.class.isInstance(msg)){
					SessionServerMsg sessionMsg = (SessionServerMsg) msg;
					sessionMsg.execute(session);
				}else if(RoundServerMsg.class.isInstance(msg)){
					RoundServerMsg roundMsg = (RoundServerMsg) msg;
					ClientGameRound round = session.getCurrentRound();
					if (round != null) {
						roundMsg.execute(round);
					}
				}else{
					throw new IllegalArgumentException("Unknown message type: " + msg.getClass());
				}
			}
		}
	}

	@Override
	protected void handleConnectionLoss(Exception e) {
		displayErrorMessage();
		StartFrame startFrame = StartFrame.getInstance();
		startFrame.changePanel(startFrame.jPanelSelectServer);
		logger.fatal("Lost connection to server!", e);
	}
	
	public void displayErrorMessage() {
		if (!logout) {
			JOptionPane.showMessageDialog(StartFrame.getInstance(), error, "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void closeConnection() {
		try {
			logout = true;
			in.close();
		} catch (IOException e) {
			logger.info("Couldn't close input stream", e);
		}
	}

}
