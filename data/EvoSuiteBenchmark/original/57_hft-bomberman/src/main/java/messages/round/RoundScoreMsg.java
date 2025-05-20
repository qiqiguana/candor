package messages.round;

import java.util.HashMap;

import client.BomberClient;
import client.ClientGameRound;
import messages.Message;
import messages.global.GlobalServerMsg;

public class RoundScoreMsg implements RoundServerMsg{

	HashMap<String,Integer> rndscore = new HashMap<String,Integer>();
	


	public RoundScoreMsg(HashMap<String, Integer> rndscore ) {
		super();
		this.rndscore = rndscore;
	}

	@Override
	public void execute(ClientGameRound round) {
		round.setRoundScore(rndscore);
		
	}
	
	



}
