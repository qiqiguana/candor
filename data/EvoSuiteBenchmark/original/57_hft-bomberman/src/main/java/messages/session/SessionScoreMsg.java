package messages.session;

import java.util.HashMap;

import client.BomberClient;
import client.ClientGameSession;
import messages.global.GlobalServerMsg;

public class SessionScoreMsg implements SessionServerMsg{

	HashMap<String,Integer> rndscore = new HashMap<String,Integer>();
	


	public SessionScoreMsg(HashMap<String, Integer> rndscore) {
		super();
		this.rndscore = rndscore;
	}



	@Override
	public void execute(ClientGameSession session) {
		session.setSessionScore(rndscore);
		
	}
	
	

}
