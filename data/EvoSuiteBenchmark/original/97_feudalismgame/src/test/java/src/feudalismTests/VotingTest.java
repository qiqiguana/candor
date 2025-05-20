package src.feudalismTests;

import java.util.ArrayList;

import src.CommandBean;

import junit.framework.TestCase;

public class VotingTest extends TestCase {
	
	public void perform(){
		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("Demote Mathew");
		cmds.add("no");
		cmds.add("yes");
		cmds.add("no");
		cmds.add("yes");
		cmds.add("no");
		cmds.add("no");
		
		
		CommandBean cmd = new CommandBean("Voting", cmds);
	}
}
