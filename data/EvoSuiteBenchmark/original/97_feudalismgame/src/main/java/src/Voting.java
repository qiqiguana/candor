package src;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Voting extends Actions implements Runnable{
	private String threadName = null;
	private String vote = null;
	HashMap<String, Integer> votesResults = new HashMap<String, Integer>();

	public void perform(Collection col){
		Iterator colIter = col.iterator();
		threadName = (String) colIter.next();
		vote = (String) colIter.next();
		Thread thread = new Thread(threadName);
		thread.start();
	}

	public void run() {
		if(votesResults.containsKey(vote)){
			votesResults.put(vote, new Integer(votesResults.get(vote)).intValue()+1);
		}
		else{
			votesResults.put(vote, 1);
		}
	}
}