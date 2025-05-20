package server.network;

import org.apache.log4j.Logger;

import messages.Message;
import messages.global.GlobalClientMsg;
import messages.round.RoundClientMsg;
import messages.session.SessionClientMsg;
import server.BomberServer;
import server.ClientInfo;
import server.ServerGameRound;
import server.ServerGameSession;

import common.network.MsgReceiver;

/**
 * This class is a specialization of MsgReceiver for the server side. It
 * inherits the run method that waits for msgs in a loop and extends its
 * superclass by the appopriate processMsg-method.
 * 
 * @author Steffen, Daniel, Björn
 */
public class ServerMsgReceiver extends MsgReceiver {

	private static final Logger logger = Logger.getLogger(ServerMsgReceiver.class);
	private ClientInfo clientInfo;
	private final BomberServer bomberSrv;

	/**
	 * Creates a new ServerMsgReceiver object.
	 * 
	 * @param clientInfo
	 */
	public ServerMsgReceiver(ClientInfo clientInfo) {
		super(clientInfo.getSocket());
		this.clientInfo = clientInfo;
		this.bomberSrv = BomberServer.getInstance();
	}

	/**
	 * Processes the given message by making a call to its execute-method.
	 * We have to make a distinction between global, session, and round messages
	 * in order to be able to pass the parameters that the message needs in order
	 * to execute its action.
	 */
	protected void processMsg(Message m) {
		if (GlobalClientMsg.class.isInstance(m)) {
			GlobalClientMsg msg = (GlobalClientMsg) m;
			msg.execute(bomberSrv, clientInfo);
		}
		ServerGameSession gameSession = clientInfo.getGameSession();
		// have to check for null because otherwise delayed messages may cause NullPointer
		if (gameSession != null) {
			if (SessionClientMsg.class.isInstance(m)) {
				SessionClientMsg msg = (SessionClientMsg) m;
				msg.execute(gameSession, clientInfo);
			}
			if (RoundClientMsg.class.isInstance(m)) {
				RoundClientMsg msg = (RoundClientMsg) m;
				ServerGameRound round = gameSession.getCurrentRound();
				if (round != null) {
					msg.execute(round, clientInfo);
				}
			}
		}
	}

	/**
	 * Removes the clientInfo-object from the sever and prints appropriate log
	 * messages.
	 */
	@Override
	protected void handleConnectionLoss(Exception e) {
		logger.info("Connection lost", e);
		clientInfo.leaveGameSession();
		bomberSrv.removeClientFromGlobal(clientInfo);
		bomberSrv.removeClient(clientInfo);
	}
}
