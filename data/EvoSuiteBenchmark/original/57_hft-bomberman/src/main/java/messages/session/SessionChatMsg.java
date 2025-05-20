package messages.session;

import org.apache.log4j.Logger;

import server.ClientInfo;
import server.ServerGameSession;
import client.ClientGameSession;
import db.DBGameUser;

/**
 * This message represents a chat message that is sent and received in the
 * SessionLobby, which players enter after joining a session.
 * 
 * @author Steffen
 */

public class SessionChatMsg implements SessionClientMsg, SessionServerMsg {

	private Logger logger;
	
	private String sender;
	private String msg;
	
	
	public SessionChatMsg(String sender, String msg) {
		this.sender = sender;
		this.msg = msg;
	}
	public SessionChatMsg( String msg) {
		this("",msg);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(ServerGameSession session, ClientInfo sender) {
		//System.out.println("test1");
		this.sender=sender.getName();
		session.broadcastMsg(this);
		//logger.debug("asd session chat msg executed");
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(ClientGameSession session) {
//		System.out.println("Tobitest2");
//		System.out.println("TEST");
//		DBGameUser[] users = session.getUserScores();
//		for (int i = 0; i < users.length; i++) {
//			System.out.println(users[i]);
//		}
//		System.out.println("/TEST");
		//logger.debug("session chat msg executed");
		session.addChat(sender,msg);
	}

}
