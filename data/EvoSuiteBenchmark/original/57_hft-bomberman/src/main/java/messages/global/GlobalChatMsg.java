package messages.global;

import org.apache.log4j.Logger;

import common.Bomb;

import server.BomberServer;
import server.ClientInfo;
import client.BomberClient;
import client.gui.StartFrame;
import db.DBGameUser;

/**
 * This message represents a chat message that is sent and received in the
 * GlobalLobby, which players enter right after connecting to the server.
 * 
 * @author Steffen
 */
public class GlobalChatMsg implements GlobalClientMsg, GlobalServerMsg {
	private static final Logger logger = Logger.getLogger(Bomb.class);
	
	
	private String msg;
	private String sender;
	//private Logger logger;

	public GlobalChatMsg(String msg) {
		this("", msg);
	}

	public GlobalChatMsg(String sender, String msg) {
		super();
		this.msg = msg;
		this.sender = sender;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(BomberServer bomberSrv, ClientInfo sender) {
		//System.out.println("test3");
		this.sender=sender.getName();
		bomberSrv.broadcastMsg(this);
		//logger.debug("asd global chat msg executed");
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(BomberClient bomberClient) {
		//System.out.println("test4");
		
		//logger.debug("global chat msg executed");
		logger.info("globalchatmessage gets executed");
		bomberClient.addChatGlobal(sender,msg);

	}

}
