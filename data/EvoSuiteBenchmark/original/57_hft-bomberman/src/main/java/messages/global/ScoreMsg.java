package messages.global;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import client.BomberClient;

public class ScoreMsg implements  GlobalServerMsg{

	private ArrayList scores = new ArrayList();

	
	@Override
	public void execute(BomberClient bomberClient) {
		bomberClient.setGlobalscores(scores);
		
	}


	public ScoreMsg(ArrayList scores) {
		super();
		this.scores = scores;
	}
	
	

}
