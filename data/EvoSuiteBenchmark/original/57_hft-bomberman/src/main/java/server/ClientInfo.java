package server;

import java.net.Socket;

import org.apache.log4j.Logger;

import messages.Message;
import messages.global.JoinAckMsg;
import messages.session.SessionParticipationMsg;
import server.network.ServerMsgReceiver;
import server.network.ServerMsgSender;

import common.network.MsgReceiver;
import common.network.MsgSender;

/**
 * This class holds the releveant information about one client. It holds the
 * MsgReceiver and MsgSender objects needed to communicate with this client as
 * well as what session it is currently participating in, the players name, etc.
 * 
 * @author Steffen
 *
 */
public class ClientInfo {
	
	private static final Logger logger = Logger.getLogger(ClientInfo.class);
	
	/**
	 * The client's name.
	 */
	private String name;
	private Socket socket;
	private ServerGameSession gameSession;
	private int idOffset;
	private MsgSender msgSender;
	private MsgReceiver msgReceiver;

	public ClientInfo(Socket clientSocket) {
		this.socket = clientSocket;
		this.msgReceiver = new ServerMsgReceiver(this);
		msgReceiver.start();
		this.msgSender = new ServerMsgSender(clientSocket);
		msgSender.start();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Socket getSocket() {
		return socket;
	}

	public ServerGameSession getGameSession() {
		return gameSession;
	}

	public int getIdOffset() {
		return idOffset;
	}

	public MsgSender getMsgSender() {
		return msgSender;
	}

	/**
	 * This is a convenience method that simply forwards the given message to the
	 * MsgSender that belongs to this client.
	 * 
	 * @param msg The message to be sent to the client.
	 */
	public void sendMsg(Message msg) {
		msgSender.sendMsg(msg);
	}
	
	/**
	 * Adds the player to the given gameSession. If this is successful a JoinAckMsg containg the idOffset
	 * for the client is sent back to the joining client and all others are notified of its joining, otherwise
	 * only a JoinAckMsg indicating the failure is sent back to the joining client.
	 * 
	 * @param gameSession The gameSession the client is supposed to join.
	 */
	synchronized public void joinGameSession(ServerGameSession gameSession) {
		int idOffset = gameSession.addParticipant(this);
		if (idOffset != -1) {
			logger.info("Player " + name + " is joining session " + gameSession);
			this.idOffset = idOffset;
			this.gameSession = gameSession;
			JoinAckMsg response = new JoinAckMsg(true, idOffset);
			response.setParticipants(gameSession.getParticipantInfos());
			sendMsg(response);
			BomberServer.getInstance().removeClientFromGlobal(this);
			gameSession.multicastMsg(new SessionParticipationMsg(true, idOffset, this.getName()), this);
			gameSession.beginRoundIfReady();
		} else {
			logger.info("Player " + name + " was rejected for session " + gameSession);
			sendMsg(new JoinAckMsg(false, idOffset));
		}
	}

	/**
	 * Removes the client from its current game session and notifies all participants.
	 * If the client is currently not participating in any session no action
	 * will be performed.
	 */
	public void leaveGameSession() {
		logger.info("on clientinfo serverside leave msg is called");
		if (gameSession!=null) {
			logger.info("Player " + name + " is leaving session " + gameSession);
			gameSession.removeParticipant(this);
			gameSession.multicastMsg(new SessionParticipationMsg(false, idOffset, this.getName()), this);
			gameSession = null;
			BomberServer.getInstance().addClientToGlobal(this);
		} else {
			logger.info("Couldn't leave game session (null)!");
		}
	}
}
