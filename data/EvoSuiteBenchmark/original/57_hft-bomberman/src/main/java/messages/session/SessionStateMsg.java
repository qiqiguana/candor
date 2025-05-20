package messages.session;

import server.ClientInfo;
import server.ServerGameSession;

public class SessionStateMsg implements SessionClientMsg {

	/**
	 * Indicates to the server that the client is ready to start the next round.
	 */
	public static final int READY_FOR_NEXT_ROUND = 1;
	
	/**
	 * The type of this message. See constants above.
	 */
	private int type;

	public SessionStateMsg(int type) {
		super();
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(ServerGameSession session, ClientInfo sender) {
		switch (type) {
		case READY_FOR_NEXT_ROUND:
			session.markClientReady(sender);
			session.multicastMsg(new SessionChatMsg(sender.getName(), "READY FOR NEXT ROUND"), null);
			break;
		}

	}

}
